package paymentStrategy;

public class PaymentService {
    private PaymentStrategy paymentStrategy;

    public PaymentService(PaymentStrategy paymentStrategy){
        this.paymentStrategy=paymentStrategy;
    }

    public void setStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void checkout(double amount) {
        paymentStrategy.pay(amount);
    }

    public static void main(String[] args) {
        PaymentService service =
                new PaymentService(new CardPayment());

        service.checkout(1000);

        service.setStrategy(new WalletPayment());
        service.checkout(500);
    }
}
