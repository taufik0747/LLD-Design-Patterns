public class OrderNotification extends Notification{

    public OrderNotification(MessageSender sender) {
        super(sender);
    }
    @Override
    public void notifyUser() {
        System.out.println("Your order is shipped ");
    }
}
