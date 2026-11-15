package strategy;

import model.ParkingTicket;

public interface FeeStrategy {
    public double calculateFee(ParkingTicket ticket);
}
