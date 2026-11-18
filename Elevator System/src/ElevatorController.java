import enums.*;
import models.Elevator;
public class ElevatorController implements Runnable {
    private final Elevator elevator;
    private final long travelMillis, doorMillis;
    private volatile boolean running=true;
    public ElevatorController(Elevator e) { this(e,500,1000); }
    public ElevatorController(Elevator e,long travel,long door) {
        if(travel<0 || door<0)throw new IllegalArgumentException("Negative delay");
        elevator=e; travelMillis=travel; doorMillis=door;
    }
    public void stop() { running=false; }
    public void run() {
        try {
            while(running && !Thread.currentThread().isInterrupted()) {
                synchronized(elevator) {
                    if(!elevator.hasRequests() && elevator.getState()!=ElevatorState.OUT_OF_SERVICE) {
                        elevator.setDirection(Direction.IDLE); elevator.setState(ElevatorState.IDLE);
                    }
                }
                elevator.awaitWork();
                if(!running)break;
                Thread.sleep(travelMillis);
                synchronized(elevator) {
                    if(!running || elevator.getState()==ElevatorState.OUT_OF_SERVICE)continue;
                    int next=elevator.getNextStop(), current=elevator.getCurrentFloor();
                    if(next<0)continue;
                    if(next!=current)elevator.moveToFloor(current+Integer.signum(next-current));
                    elevator.serveCurrentFloor();
                }
                if(elevator.isDoorOpen())try { Thread.sleep(doorMillis); } finally { elevator.closeDoor(); }
            }
        } catch(InterruptedException e) { Thread.currentThread().interrupt(); }
        finally { elevator.closeDoor(); if(elevator.getState()!=ElevatorState.OUT_OF_SERVICE) {
            elevator.setDirection(Direction.IDLE); elevator.setState(ElevatorState.IDLE);
        } }
    }
}
