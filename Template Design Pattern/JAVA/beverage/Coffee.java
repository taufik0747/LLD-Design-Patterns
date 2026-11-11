package beverage;

public class Coffee extends Beverage {
    @Override
    protected void brew() {
        System.out.println("adding coffee powder");
    }

    @Override
    protected void addCondiments() {
        System.out.println("add milk");
    }
}
