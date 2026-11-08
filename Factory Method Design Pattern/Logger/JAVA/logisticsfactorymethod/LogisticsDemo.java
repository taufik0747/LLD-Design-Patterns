package logisticsfactorymethod;

public class LogisticsDemo {

    public static void main(String[] args) {
        Logistics creator = new RoadLogistics();
        System.out.println(creator.planDelivery());
        Logistics seaCreator = new SeaLogistics();
        System.out.println(seaCreator.planDelivery());
    }
}
