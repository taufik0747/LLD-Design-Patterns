package computerbuilder;

public class Computer {
    private final int ram;
    private final String processor;
    private final int storage;
    private final boolean wifi;

    private Computer(Builder builder) {
        this.processor = builder.processor;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.wifi = builder.wifi;
    }

    public String describe() {
        return processor + " | RAM: " + ram + "GB | Storage: " + storage + "GB | WiFi: " + wifi;
    }


    public static class Builder {
        private  int ram;
        private  String processor;
        private  int storage=256;
        private  boolean wifi=false;

       public Builder(String processor, int ram){
           this.processor=processor;
           this.ram=ram;
       }

        public Builder storage(int storage) {
            this.storage = storage;
            return this;
        }

        public Builder wifi(boolean wifi) {
            this.wifi = wifi;
            return this;
        }

        public Computer build(){
            return new Computer(this);
        }


            public static void main(String[] args) {

                Computer c1 = new Computer.Builder("Intel i7", 16)
                        .storage(512)
                        .wifi(true)
                        .build();

                Computer c2 = new Computer.Builder("Ryzen 5", 8)
                        .build();

                System.out.println(c1.describe());
                System.out.println(c2.describe());

                Computer.Builder builder = new Computer.Builder("Intel",16);
                Computer a = builder.storage(512).build();
                Computer b= builder.storage(256).wifi(true).build();
                System.out.println(a.describe());
                System.out.println(b.describe());
                System.out.println(a==b);

                ComputerDirector director = new ComputerDirector();

                Computer gaming = director.buildGamingComputer();
                Computer office = director.buildOfficeComputer();
                System.out.println(gaming.describe());
                System.out.println(office.describe());

        }
    }
}
