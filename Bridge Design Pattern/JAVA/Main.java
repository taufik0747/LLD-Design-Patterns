public class Main {
    public static void main(String[] args) {
        OrderNotification email = new OrderNotification(new EmailSender());
        Notification pay = new PaymentNotification(new SMSSender());
        Notification sms = new OrderNotification(new SMSSender());
       email.notifyUser();
       pay.notifyUser();
        sms.notifyUser();
    }
}
