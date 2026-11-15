package strategy;

import enums.VehicleSize;
import model.ParkingFloor;
import model.ParkingSpot;

import java.util.List;

public class BestFitStrategy implements SpotAllocationStrategy {
    @Override
    public ParkingSpot findSpot(List<ParkingFloor> floors, VehicleSize size) {
        // Walk sizes from the requested size upward, so an exact match wins
        // before any larger spot is considered
        for (VehicleSize spotSize : VehicleSize.values()) {
            if (spotSize.ordinal() < size.ordinal()) {
                continue;  // Spot too small
            }
            for (ParkingFloor floor : floors) {
                for (ParkingSpot spot : floor.getSpots()) {
                    if (spot.isAvailable() && spot.getSize() == spotSize) {
                        return spot;
                    }
                }
            }
        }
        return null;
    }

}
