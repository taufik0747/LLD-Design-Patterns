package logisticsfactorymethod;

public class Ship implements Transport{


    @Override
    public String deliver() {
        return "Delivering by sea";
    }
}
