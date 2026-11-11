package orderstate;

public class CreatedState implements OrderState {

    @Override
    public void next(Order order) {
        order.setState(new ShippedState());
        System.out.println("move to shipped state "+order);
    }
}
