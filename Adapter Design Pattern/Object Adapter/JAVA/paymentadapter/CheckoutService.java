package paymentadapter;

public class CheckoutService {
    private final PaymentProcessor processor;

    public CheckoutService(PaymentProcessor processor) {
        this.processor = processor;
    }

    public void checkout(int amount) {
        processor.pay(amount);
    }
}
