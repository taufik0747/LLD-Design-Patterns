package practice.shipmentmanagement;

public class StandardShipment extends Shipment{

    public StandardShipment(String shipmentId, double weight, double distance){
        super(shipmentId,weight,distance);
    }

    @Override
    public double calculateCharge(){
        return getWeight()*10+getDistance()*2;
    }
}
