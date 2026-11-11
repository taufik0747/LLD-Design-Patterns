package orderstate;

public class ShippedState implements OrderState{
    @Override
    public void next(Order order) {
        order.setState(new DeliveredState());
        System.out.println("move to deliver "+order);
    }
}
