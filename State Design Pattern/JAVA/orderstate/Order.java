package orderstate;

public class Order {
    private OrderState state;

    public Order(){
        state = new CreatedState();
    }

    public void setState(OrderState state){
        this.state=state;
    }

    public void next(){
        state.next(this);
    }

    public static void main(String[] args) {
        Order order = new Order();

        order.next(); // CREATED -> SHIPPED
        order.next(); // SHIPPED -> DELIVERED
        order.next(); // Already delivered
    }
}
