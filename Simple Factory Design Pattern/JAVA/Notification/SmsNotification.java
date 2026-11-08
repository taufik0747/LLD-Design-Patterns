package Notification;

public class SmsNotification implements Notification{

    @Override
    public String send(String message){
        return "SMS: Hello "+message;
    }
}
