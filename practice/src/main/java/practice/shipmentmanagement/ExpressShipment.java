package practice.shipmentmanagement;

public class ExpressShipment extends Shipment{

    public ExpressShipment(String shipmentId, double weight, double distance){
        super(shipmentId,weight,distance);
    }

    @Override
    public double calculateCharge(){
        return getWeight()*15+getDistance()*4;
    }
}
