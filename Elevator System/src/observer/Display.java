package observer;
import enums.Direction;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/** Id 0 is a hall panel showing all observed cars. */
public class Display implements ElevatorObserver {
    public record Snapshot(int floor,Direction direction) { }
    private final int elevatorId;
    private final Map<Integer,Snapshot> snapshots=new ConcurrentHashMap<>();
    public Display(int id) { elevatorId=id; }
    public void onElevatorStateChanged(int id,int floor,Direction direction) {
        if(elevatorId==0 || elevatorId==id)snapshots.put(id,new Snapshot(floor,direction));
    }
    public Map<Integer,Snapshot> getSnapshots() { return Map.copyOf(snapshots); }
    public void show() { System.out.println("Display: "+snapshots); }
    public int getElevatorId() { return elevatorId; }
}
