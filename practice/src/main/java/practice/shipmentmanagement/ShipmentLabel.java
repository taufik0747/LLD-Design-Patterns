package practice.shipmentmanagement;

public class ShipmentLabel {

    private final String shipmentId;
    private final long barcode;

    public ShipmentLabel(String shipmentId,long barcode){
        if(shipmentId==null || shipmentId.isBlank()){
            throw new IllegalArgumentException("Shipment Id is invalid");
        }
        this.shipmentId=shipmentId;
        if(barcode<=0){
            throw new IllegalArgumentException("barcode has wrong format");
        }
        this.barcode=barcode;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public long getBarcode() {
        return barcode;
    }
}
