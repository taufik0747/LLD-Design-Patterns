package paymentadapter;

public class Main {
    public static void main(String[] args) {
        LegacyPaymentGateway obj = new LegacyPaymentGateway();
        PaymentAdapter p = new PaymentAdapter(obj);
        CheckoutService instance = new CheckoutService(p);
        instance.checkout(500);

    }
}
