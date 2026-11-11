public class PaymentNotification extends Notification{
    public PaymentNotification(MessageSender sender) {
        super(sender);
    }

    @Override
    public void notifyUser() {
        System.out.println("payment succeeded");
    }
}
