package strategy;

import enums.VehicleSize;
import model.ParkingFloor;
import model.ParkingSpot;

import java.util.List;

public interface SpotAllocationStrategy {
    public ParkingSpot findSpot(List<ParkingFloor> floors, VehicleSize size);
}
