public abstract class Notification {
    protected final MessageSender sender;

    public Notification(MessageSender sender) {
        this.sender = sender;
    }

    public abstract void notifyUser();
}
