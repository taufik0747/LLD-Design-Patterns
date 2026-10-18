package practice.shipmentmanagement;

public abstract class Shipment {
    private final String shipmentId;
    private double weight;
    private double distance;
    private ShipmentStatus status=ShipmentStatus.CREATED;
    private InsurancePolicy insurancePolicy;
    private final ShipmentLabel shipmentLabel;


    public Shipment(String shipmentId, double weight, double distance){
        if(shipmentId==null || shipmentId.isBlank()){
            throw  new IllegalArgumentException("Shipment id is invalid");
        }
        this.shipmentId=shipmentId;
        setWeight(weight);
        setDistance(distance);
        long barcode = 0;
        this.shipmentLabel = new ShipmentLabel(shipmentId,barcode);
    }


    public String getShipmentId(){
        return shipmentId;
    }

    public double getDistance(){
        return distance;
    }

    public double getWeight(){
        return weight;
    }

    public ShipmentStatus getStatus(){
        return status;
    }

    public  void setWeight(double weight){
        if(weight<=0){
           throw new IllegalArgumentException("Weight is invalid");
        }
        this.weight=weight;
    }
    public void setDistance(double distance){
        if(distance<=0){
            throw new IllegalArgumentException("Distance is invalid");
        }

        this.distance=distance;
    }

    public void pickup(){
        if(status == ShipmentStatus.CREATED){
            status=ShipmentStatus.PICKED_UP;
        }
        else{
            throw new IllegalStateException("Shipment cannot be picked up from the status "+status);
        }
    }

    public void markInTransit(){
        if(status==ShipmentStatus.PICKED_UP){
            status=ShipmentStatus.IN_TRANSIT;
        }
        else{
            throw new IllegalStateException("Shipment cannot be in transit from the status "+status);
        }
    }

    public void deliver(){
        if(status==ShipmentStatus.IN_TRANSIT){
            status=ShipmentStatus.DELIVERED;
        }
        else{
            throw new IllegalStateException("Shipment cannot be delivered from the status "+status);
        }
    }

    public void cancel(){
        if(status==ShipmentStatus.CREATED || status==ShipmentStatus.PICKED_UP ){
            status=ShipmentStatus.CANCELLED;
        }
        else{
            throw new IllegalStateException("Shipment cannot be cancelled from the status "+status);
        }
    }

    public abstract double calculateCharge();

    public void attachInsurance(InsurancePolicy insurancePolicy){
        if (insurancePolicy == null) {
            throw new IllegalArgumentException("Insurance policy cannot be null");
        }
        if(this.insurancePolicy!=null){
            throw new IllegalStateException("Shipment already has an insurance Policy");
        }
            this.insurancePolicy=insurancePolicy;
    }
}
