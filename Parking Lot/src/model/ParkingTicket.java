package model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ParkingTicket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSpot assignedSpot;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;

    public ParkingTicket(String ticketId, Vehicle vehicle, ParkingSpot assignedSpot){
        this.ticketId=ticketId;
        this.vehicle=vehicle;
        this.assignedSpot=assignedSpot;
        this.entryTime=LocalDateTime.now();
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public long getDurationInHours(){
      LocalDateTime end = (exitTime!=null) ? exitTime:LocalDateTime.now();
      long minutes = ChronoUnit.MINUTES.between(entryTime,end);
      long hours = (minutes+59)/60;
      return Math.max(1,hours);
    }

    public String getTicketId() { return ticketId; }
    public Vehicle getVehicle() { return vehicle; }
    public ParkingSpot getSpot() { return assignedSpot; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public LocalDateTime getExitTime() { return exitTime; }


}
