import enums.*;
import models.*;
import observer.*;
import strategy.*;
import java.util.*;

/** Facade and lifecycle owner; instances can also be created independently for tests. */
public class ElevatorSystem implements AutoCloseable {
    private static ElevatorSystem instance;
    private final List<Elevator> elevators=new ArrayList<>();
    private final List<Floor> floors=new ArrayList<>();
    private final List<Display> cabinDisplays=new ArrayList<>();
    private final List<ElevatorController> controllers=new ArrayList<>();
    private final List<Thread> threads=new ArrayList<>();
    private DispatchStrategy strategy;
    private boolean closed;
    public ElevatorSystem(int cars,int floorCount) { this(cars,floorCount,500,1000); }
    public ElevatorSystem(int cars,int floorCount,long travelMillis,long doorMillis) {
        if(cars<1 || floorCount<1 || travelMillis<0 || doorMillis<0)throw new IllegalArgumentException("Invalid configuration");
        strategy=new NearestElevatorStrategy(floorCount);
        for(int f=1;f<=floorCount;f++)floors.add(new Floor(f));
        for(int id=1;id<=cars;id++) {
            Elevator e=new Elevator(id,floorCount); elevators.add(e);
            Display display=new Display(id); cabinDisplays.add(display); e.addObserver(display);
            for(Floor floor:floors)e.addObserver(floor.getDisplay());
            e.addObserver(new ElevatorObserver() {
                public void onElevatorStateChanged(int id,int floor,Direction direction) { }
                public void onRequestServed(int id,Request request) {
                    if(request.getType()==RequestType.EXTERNAL)floors.get(request.getFloor()-1).resetButton(request.getDirection());
                }
            });
            e.notifyObservers();
            ElevatorController controller=new ElevatorController(e,travelMillis,doorMillis);
            controllers.add(controller);
            Thread thread=new Thread(controller,"Elevator-"+id); thread.setDaemon(true); threads.add(thread);
        }
        threads.forEach(Thread::start);
    }
    public static synchronized ElevatorSystem getInstance(int cars,int floors) {
        if(instance==null || instance.isClosed())instance=new ElevatorSystem(cars,floors);
        else if(instance.elevators.size()!=cars || instance.floors.size()!=floors)throw new IllegalArgumentException("Existing singleton has a different configuration");
        return instance;
    }
    public synchronized boolean requestElevator(int floor,Direction direction) {
        if(closed || floor<1 || floor>floors.size() || direction==null || direction==Direction.IDLE ||
           floor==1 && direction==Direction.DOWN || floor==floors.size() && direction==Direction.UP)return false;
        Floor hall=floors.get(floor-1);
        if(direction==Direction.UP?hall.isUpButtonPressed():hall.isDownButtonPressed())return true;
        Elevator selected;
        try { selected=strategy.selectElevator(List.copyOf(elevators),floor,direction); }
        catch(exception.ElevatorException unavailable) { return false; }
        if(selected==null || !elevators.contains(selected))return false;
        synchronized(selected) {
            if(!selected.addRequest(new Request(floor,direction,RequestType.EXTERNAL)))return false;
            if(selected.getCurrentFloor()!=floor) {
                if(direction==Direction.UP)hall.pressUpButton(); else hall.pressDownButton();
            }
        }
        return true;
    }
    public synchronized boolean requestFloor(int elevatorId,int destination) {
        return !closed && elevatorId>=1 && elevatorId<=elevators.size() && elevators.get(elevatorId-1).requestFloor(destination);
    }
    public synchronized void setDispatchStrategy(DispatchStrategy strategy) { this.strategy=Objects.requireNonNull(strategy); }
    public synchronized boolean isClosed() { return closed; }
    public void shutdown() {
        synchronized(this) { closed=true; controllers.forEach(ElevatorController::stop); threads.forEach(Thread::interrupt); }
        boolean interrupted=false;
        for(Thread thread:threads) {
            while(thread.isAlive())try { thread.join(); } catch(InterruptedException e) { interrupted=true; }
        }
        if(interrupted)Thread.currentThread().interrupt();
    }
    public boolean isTerminated() { return threads.stream().noneMatch(Thread::isAlive); }
    public void close() { shutdown(); }
    public List<Elevator> getElevators() { return List.copyOf(elevators); }
    public List<Floor> getFloors() { return List.copyOf(floors); }
    public List<Display> getCabinDisplays() { return List.copyOf(cabinDisplays); }
}
