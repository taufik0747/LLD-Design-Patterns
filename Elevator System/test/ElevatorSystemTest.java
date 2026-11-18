import enums.*;
import models.*;
import observer.*;
import strategy.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.BooleanSupplier;

public class ElevatorSystemTest {
    static void check(boolean ok,String message) { if(!ok)throw new AssertionError(message); }
    static void await(BooleanSupplier condition) throws Exception {
        long end=System.nanoTime()+TimeUnit.SECONDS.toNanos(5);
        while(!condition.getAsBoolean() && System.nanoTime()<end)Thread.sleep(5);
        check(condition.getAsBoolean(),"Timed out waiting for completion");
    }
    public static void main(String[] args) throws Exception {
        Elevator e=new Elevator(1,10);
        List<Integer> stops=new CopyOnWriteArrayList<>();
        e.addObserver(new ElevatorObserver() {
            public void onElevatorStateChanged(int id,int f,Direction d) { }
            public void onRequestServed(int id,Request r) { stops.add(r.getFloor()); }
        });
        e.requestFloor(8); e.requestFloor(4); e.requestFloor(6);
        ElevatorController controller=new ElevatorController(e,2,2);
        Thread worker=new Thread(controller);
        worker.start();
        try {
            await(()->stops.size()==3);
            check(stops.equals(List.of(4,6,8)),"LOOK ascending order");
            check(e.addRequest(new Request(2,Direction.UP,RequestType.EXTERNAL)),"Accept pickup below car");
            await(()->stops.size()==4);
            check(stops.get(3)==2,"UP hall call below car must not get stranded");
        } finally { controller.stop(); worker.interrupt(); worker.join(2000); }
        check(!worker.isAlive(),"Controller terminates");
        try(ElevatorSystem s=new ElevatorSystem(3,10,2,2)) {
            check(!s.requestElevator(0,Direction.UP),"Invalid floor");
            check(!s.requestElevator(10,Direction.UP),"Impossible top-floor direction");
            check(!s.requestElevator(1,Direction.DOWN),"Impossible bottom-floor direction");
            check(!s.requestElevator(2,null),"Null direction");
            check(!s.requestFloor(0,3) && !s.requestFloor(1,11),"Invalid cabin request");
            check(s.requestFloor(1,1) && !s.getElevators().get(0).hasRequests(),"Same floor is no-op");
            s.setDispatchStrategy(new ZoneBasedStrategy(10,3));
            check(s.requestElevator(9,Direction.DOWN),"Zone request");
            await(()->!s.getFloors().get(8).isDownButtonPressed());
            check(s.getElevators().get(2).getCurrentFloor()==9,"Zone owns floor 9");
            check(s.getFloors().get(0).getDisplay().getSnapshots().get(3).floor()==9,"Hall display observes car");
            ExecutorService clients=Executors.newFixedThreadPool(4);
            try {
                List<Callable<Boolean>> calls=new ArrayList<>();
                for(int i=0;i<100;i++){ final int f=i%10+1, id=i%3+1; calls.add(()->s.requestFloor(id,f)); }
                for(Future<Boolean> result:clients.invokeAll(calls))check(result.get(),"Concurrent request accepted");
            } finally { clients.shutdownNow(); }
            await(()->s.getElevators().stream().allMatch(car->!car.hasRequests() && !car.isDoorOpen()));
            s.getElevators().forEach(car->car.setState(ElevatorState.OUT_OF_SERVICE));
            check(!s.requestElevator(5,Direction.UP),"No available cars");
            s.shutdown(); check(s.isTerminated(),"All threads joined");
            check(!s.requestFloor(1,2),"Reject after shutdown");
        }
        System.out.println("All elevator tests passed.");
    }
}
