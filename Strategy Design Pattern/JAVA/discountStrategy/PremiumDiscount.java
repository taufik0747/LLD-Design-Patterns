package discountStrategy;

public class PremiumDiscount implements DiscountStrategy{
    @Override
    public double applyDiscount(double price) {
        return (price*0.10);
    }
}
