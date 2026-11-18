package observer;
import enums.Direction;
import models.Request;
public interface ElevatorObserver {
    void onElevatorStateChanged(int elevatorId,int floor,Direction direction);
    default void onRequestServed(int elevatorId,Request request) { }
}
