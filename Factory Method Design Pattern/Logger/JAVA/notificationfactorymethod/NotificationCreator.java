package notificationfactorymethod;

public abstract class NotificationCreator {

   protected abstract Notification createNotification();

   public String notifyUser(String message){
     Notification notification = createNotification();
       return notification.send(message);
   }
}
