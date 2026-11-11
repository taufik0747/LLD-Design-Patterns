package orderstate;

public class DeliveredState implements OrderState{
    @Override
    public void next(Order order) {
        System.out.println("Already Delivered "+order);
    }
}
