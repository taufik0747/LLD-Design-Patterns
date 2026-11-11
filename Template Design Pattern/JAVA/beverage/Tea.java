package beverage;

public class Tea extends Beverage{
    @Override
    protected void brew() {
        System.out.println("adding tea leaves");
    }

    @Override
    protected void addCondiments() {
        System.out.println("add lemon");
    }
}
