package org.smartcity;

public class Apartment extends Building {
    private final int residents;

    public Apartment(String address, int floors, int residents) {
        super(address, floors);
        this.residents = residents;
    }

    @Override
    public void operate() {
        System.out.println("Apartment at " + getAddress() + " has " + getFloors() + " floors and " + residents + " residents.");
    }
}
