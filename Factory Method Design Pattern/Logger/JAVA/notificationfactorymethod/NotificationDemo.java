package notificationfactorymethod;

public class NotificationDemo {
    public static void main(String[] args) {
        NotificationCreator instance = new EmailCreator();
        System.out.println(instance.notifyUser("Order placed"));
        instance= new SmsCreator();
        System.out.println(instance.notifyUser("OTP sent"));
    }
}
