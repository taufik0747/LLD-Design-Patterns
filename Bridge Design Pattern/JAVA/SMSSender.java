public class SMSSender implements MessageSender{
    @Override
    public void send(String message) {
        System.out.println("SMS: " + message);
    }
}
