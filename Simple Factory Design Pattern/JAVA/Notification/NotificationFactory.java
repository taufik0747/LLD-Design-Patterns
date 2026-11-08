package Notification;

public class NotificationFactory {

    public static Notification create(String type){
        if ("EMAIL".equals(type)) {
            return new EmailNotification();
        }

        if("SMS".equals(type)){
            return new SmsNotification();
        }

        throw new IllegalArgumentException("Unknown type");
    }

    public static void main(String[] args) {
        Notification email = NotificationFactory.create("EMAIL");
        Notification sms = NotificationFactory.create("SMS");

        System.out.println(email.send("Order Placed"));
        System.out.println(sms.send("OTP sent"));
    }
}
