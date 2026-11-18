import enums.Direction;
import exception.ElevatorException;
import models.Elevator;
import models.Floor;
import strategy.DispatchStrategy;
import strategy.NearestElevatorStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ElevatorSystem {
    // volatile ensures visibility of the fully constructed instance across threads
    private static volatile ElevatorSystem instance;
    private static final Object lock = new Object();
    private final List<Elevator> elevators;
    private final List<ElevatorController> controllers;
    private final List<Thread> controllerThreads;
    private final List<Floor> floors;
    private DispatchStrategy dispatchStrategy;

    private ElevatorSystem(int numElevators, int numFloors) {
        this.elevators = new ArrayList<>();
        this.controllers = new ArrayList<>();
        this.controllerThreads = new ArrayList<>();
        this.floors = new ArrayList<>();

        // Create floors
        for (int i = 1; i <= numFloors; i++) {
            floors.add(new Floor(i));
        }

        // Create elevators and their controllers
        for (int i = 1; i <= numElevators; i++) {
            Elevator elevator = new Elevator(i, numFloors);
            elevators.add(elevator);

            ElevatorController controller = new ElevatorController(elevator);
            controllers.add(controller);

            Thread thread = new Thread(controller, "Elevator-" + i);
            // Daemon threads won't prevent JVM shutdown
            thread.setDaemon(true);
            controllerThreads.add(thread);
            thread.start();
        }

        // Default dispatch strategy
        this.dispatchStrategy = new NearestElevatorStrategy(numFloors);
    }

    public static ElevatorSystem getInstance(int numElevators, int numFloors) {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ElevatorSystem(numElevators, numFloors);
                }
            }
        }
        return instance;
    }

    public synchronized void requestElevator(int floor, Direction direction) {
        if (floor < 1 || floor > floors.size()) {
            throw new ElevatorException("Invalid floor: " + floor +
                    ". Building has floors 1 to " + floors.size());
        }
        if (direction == Direction.IDLE) {
            throw new ElevatorException("External request must specify UP or DOWN direction");
        }

        Elevator selected = dispatchStrategy.selectElevator(elevators, floor, direction);
        selected.addRequest(floor, direction);
        System.out.println("Dispatching Elevator " + selected.getId() +
                " to floor " + floor + " (" + direction + ")");
    }

    public void setDispatchStrategy(DispatchStrategy strategy) {
        this.dispatchStrategy = strategy;
    }

    public void shutdown() {
        for (ElevatorController controller : controllers) {
            controller.stop();
        }
        for (Thread thread : controllerThreads) {
            try {
                thread.join(2000); // Wait up to 2 seconds for each thread
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public List<Elevator> getElevators() {
        return Collections.unmodifiableList(elevators);
    }

    public List<Floor> getFloors() {
        return Collections.unmodifiableList(floors);
    }
}