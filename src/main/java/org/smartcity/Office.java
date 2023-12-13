package org.smartcity;

public class Office extends Building {
    private final int employees;

    public Office(String address, int floors, int employees) {
        super(address, floors);
        this.employees = employees;
    }

    @Override
    public void operate() {
        System.out.println("Office at " + getAddress() + " has " + getFloors() + " floors and " + employees + " employees.");
    }
}

