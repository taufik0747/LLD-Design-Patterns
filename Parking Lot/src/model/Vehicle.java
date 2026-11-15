package model;

import enums.VehicleSize;

public abstract class Vehicle {
    private final String licensePlate;
    private final VehicleSize size;

    protected Vehicle(String licensePlate, VehicleSize size){
        this.licensePlate=licensePlate;
        this.size=size;
    }

    public String getLicensePlate(){
        return licensePlate;
    }

    public VehicleSize getSize(){
        return size;
    }
}
