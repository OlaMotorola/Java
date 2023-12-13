package org.smartcity;

public abstract class Building {
    private final String address;
    private final int floors;

    public Building(String address, int floors) {
        this.address = address;
        this.floors = floors;
    }

    public abstract void operate();

    // Getters and Setters
    public String getAddress() {
        return address;
    }

    public int getFloors() {
        return floors;
    }
}


