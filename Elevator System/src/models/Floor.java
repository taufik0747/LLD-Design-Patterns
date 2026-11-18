package models;

import observer.Display;

public class Floor {
    private final int floorNumber;
    private boolean upButtonPressed;
    private boolean downButtonPressed;
    private final Display display;

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.upButtonPressed = false;
        this.downButtonPressed = false;
        this.display = new Display(0); // Floor display, not tied to specific elevator
    }

    public synchronized void pressUpButton() {
        this.upButtonPressed = true;
    }

    public synchronized void pressDownButton() {
        this.downButtonPressed = true;
    }

    public synchronized void resetButtons() {
        this.upButtonPressed = false;
        this.downButtonPressed = false;
    }

    public synchronized void resetButton(enums.Direction direction) {
        if(direction==enums.Direction.UP)upButtonPressed=false;
        if(direction==enums.Direction.DOWN)downButtonPressed=false;
    }
    public int getFloorNumber() {
        return floorNumber;
    }

    public synchronized boolean isUpButtonPressed() {
        return upButtonPressed;
    }

    public synchronized boolean isDownButtonPressed() {
        return downButtonPressed;
    }

    public Display getDisplay() {
        return display;
    }
}
