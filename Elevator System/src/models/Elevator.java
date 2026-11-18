package models;
import enums.*;
import observer.ElevatorObserver;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/** All mutable car state is guarded by this car's monitor. */
public class Elevator {
    private final int id, totalFloors;
    private int currentFloor = 1;
    private Direction direction = Direction.IDLE;
    private ElevatorState state = ElevatorState.IDLE;
    private final Door door = new Door();
    private final NavigableMap<Integer, List<Request>> requests = new TreeMap<>();
    private final List<ElevatorObserver> observers = new CopyOnWriteArrayList<>();
    public Elevator(int id, int totalFloors) {
        if (id < 1 || totalFloors < 1) throw new IllegalArgumentException("Positive counts required");
        this.id=id; this.totalFloors=totalFloors;
    }
    public synchronized boolean addRequest(Request r) {
        if (r==null || r.getType()==null || state==ElevatorState.OUT_OF_SERVICE) return false;
        int f=r.getFloor();
        if (f<1 || f>totalFloors) return false;
        if (r.getType()==RequestType.EXTERNAL && (r.getDirection()==null || r.getDirection()==Direction.IDLE ||
            f==1 && r.getDirection()==Direction.DOWN || f==totalFloors && r.getDirection()==Direction.UP)) return false;
        if (f==currentFloor) return true;
        List<Request> pending=requests.computeIfAbsent(f,k->new ArrayList<>());
        if (pending.stream().noneMatch(x->x.getType()==r.getType() && x.getDirection()==r.getDirection())) pending.add(r);
        notifyAll(); return true;
    }
    public boolean requestFloor(int f) { return addRequest(new Request(f,Direction.IDLE,RequestType.INTERNAL)); }
    public boolean addRequest(int f, Direction ignored) { return requestFloor(f); }
    /** LOOK reverses only when no requested stop remains ahead. */
    public synchronized int getNextStop() {
        if (requests.isEmpty() || state==ElevatorState.OUT_OF_SERVICE) return -1;
        Integer above=requests.ceilingKey(currentFloor), below=requests.floorKey(currentFloor), next;
        if (direction==Direction.UP) next=above!=null?above:below;
        else if (direction==Direction.DOWN) next=below!=null?below:above;
        else next=below==null?above:above==null?below:currentFloor-below<=above-currentFloor?below:above;
        if (next!=null && next!=currentFloor) setDirection(next>currentFloor?Direction.UP:Direction.DOWN);
        return next==null?-1:next;
    }
    public synchronized void moveToFloor(int f) {
        if (state==ElevatorState.OUT_OF_SERVICE) return;
        if (f<1 || f>totalFloors || Math.abs(f-currentFloor)!=1 || door.isOpen()) throw new IllegalStateException("Invalid movement");
        direction=f>currentFloor?Direction.UP:Direction.DOWN; currentFloor=f;
        state=direction==Direction.UP?ElevatorState.MOVING_UP:ElevatorState.MOVING_DOWN; notifyObservers();
    }
    public synchronized void serveCurrentFloor() {
        List<Request> served=requests.remove(currentFloor);
        if (served==null) return;
        openDoor();
        for(Request r:served) for(ElevatorObserver o:observers) {
            try { o.onRequestServed(id,r); } catch(RuntimeException ignored) { }
        }
    }
    public synchronized void openDoor() { if(state==ElevatorState.OUT_OF_SERVICE)return; door.open(); state=ElevatorState.DOOR_OPEN; notifyObservers(); }
    public synchronized void closeDoor() { door.close(); if(state!=ElevatorState.OUT_OF_SERVICE)state=ElevatorState.IDLE; notifyObservers(); }
    public void addObserver(ElevatorObserver o) { observers.add(Objects.requireNonNull(o)); }
    public void removeObserver(ElevatorObserver o) { observers.remove(o); }
    public synchronized void notifyObservers() {
        for(ElevatorObserver o:observers) try { o.onElevatorStateChanged(id,currentFloor,direction); } catch(RuntimeException ignored) { }
    }
    public synchronized void awaitWork() throws InterruptedException { while(!hasRequests() || state==ElevatorState.OUT_OF_SERVICE)wait(); }
    public synchronized boolean hasRequests() { return !requests.isEmpty(); }
    public synchronized boolean hasUpRequests() { return requests.ceilingKey(currentFloor)!=null; }
    public synchronized boolean hasDownRequests() { return requests.floorKey(currentFloor)!=null; }
    public int getId() { return id; }
    public int getTotalFloors() { return totalFloors; }
    public synchronized int getCurrentFloor() { return currentFloor; }
    public synchronized Direction getDirection() { return direction; }
    public synchronized ElevatorState getState() { return state; }
    public synchronized boolean isDoorOpen() { return door.isOpen(); }
    public synchronized void setDirection(Direction d) { direction=Objects.requireNonNull(d); notifyObservers(); }
    public synchronized void setState(ElevatorState s) { state=Objects.requireNonNull(s); notifyObservers(); notifyAll(); }
}
