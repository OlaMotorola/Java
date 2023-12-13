package org.smartcity;

public class Shop extends Building {
    private final String type;

    public Shop(String address, int floors, String type) {
        super(address, floors);
        this.type = type;
    }

    @Override
    public void operate() {
        System.out.println("Shop at " + getAddress() + " has " + getFloors() + " floors and is a " + type + " shop.");
    }
}
