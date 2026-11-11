package discountStrategy;

public class DiscountService {

    private DiscountStrategy discountStrategy;

    public DiscountService(DiscountStrategy discountStrategy){
        this.discountStrategy=discountStrategy;
    }

    public void setStrategy(DiscountStrategy discountStrategy){
        this.discountStrategy=discountStrategy;
    }

    public double calculatePrice(double price){
      return   discountStrategy.applyDiscount(price);
    }

    public static void main(String[] args) {
        DiscountService instance = new DiscountService(new NoDiscount());
        System.out.println(instance.calculatePrice(80));

        instance.setStrategy(new FestivalDiscount());
        System.out.println(instance.calculatePrice(100));

        instance.setStrategy(new PremiumDiscount());
        System.out.println(instance.calculatePrice(10));
    }

}
