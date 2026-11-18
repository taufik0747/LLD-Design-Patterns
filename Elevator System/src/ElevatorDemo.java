import enums.Direction;
import strategy.ZoneBasedStrategy;
public class ElevatorDemo {
    public static void main(String[] args) throws InterruptedException {
        try(ElevatorSystem system=new ElevatorSystem(3,10,100,150)) {
            System.out.println("10 floors, 3 elevators; floors and car IDs start at 1.");
            system.requestElevator(3,Direction.UP);
            system.requestElevator(7,Direction.DOWN);
            system.requestFloor(2,10);
            system.requestFloor(3,5);
            Thread.sleep(2500);
            system.setDispatchStrategy(new ZoneBasedStrategy(10,3));
            system.requestElevator(9,Direction.DOWN);
            System.out.println("Invalid floor accepted? "+system.requestFloor(1,11));
            Thread.sleep(2500);
            system.getCabinDisplays().forEach(observer.Display::show);
            System.out.println("Floor 1 panel:");
            system.getFloors().get(0).getDisplay().show();
        }
        System.out.println("All controller threads stopped.");
    }
}
