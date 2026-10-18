package practice.shipmentmanagement;

public class InternationalShipment extends Shipment{

    private double customsFee;

    public InternationalShipment(String shipmentId, double weight, double distance,long barcode){
        super(shipmentId,weight,distance);
        if(customsFee<0){
            throw new IllegalArgumentException("customsFee is Invalid");
        }
        this.customsFee=customsFee;
    }

    @Override
    public double calculateCharge(){
        return getWeight()*25+getDistance()*8+customsFee;
    }
}
