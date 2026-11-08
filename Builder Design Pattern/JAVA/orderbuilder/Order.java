package orderbuilder;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final String customerName;
    private final List<String> items;

    private Order(Builder builder){
        this.customerName=builder.customerName;
        this.items=new ArrayList<>(builder.items);
    }

    public int itemCount(){
        return items.size();
    }

    public List<String> getItems(){
        return new ArrayList<>(items);
    }

    public static class Builder{
        private final String customerName;
        private final List<String> items;
        public Builder(String customerName){
            this.customerName = customerName;
            this.items=new ArrayList<>();
        }

        public Builder addItem(String item) {
            // TODO 5:
           items.add(item);
           return this;
        }

        public Order build(){
            return new Order(this);
        }
    }
    public static void main(String[] args) {

        Order.Builder builder = new Order.Builder("Alice");

        builder.addItem("Laptop");
        Order first = builder.build();

        builder.addItem("Mouse");
        Order second = builder.build();

        System.out.println(first.itemCount());
        System.out.println(second.itemCount());

        List<String> exposed = first.getItems();
        exposed.add("Keyboard");

        System.out.println(first.itemCount());
    }

}
