package org.smartcity;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class CitySimulation {

    public static void main(String[] args) {
        SmartCity city = new SmartCity();

        // Symulacja trwa przez 10 "dni"
        int day;
        for (day = 1; day <= 10; day++) {
            System.out.println("---------------------------------------------------------");
            System.out.println("Day " + day + " in Smart City");

            Building newBuilding = createRandomBuilding(day);
            Random random = new Random();
            boolean shouldAddBuilding = random.nextBoolean();

            if (shouldAddBuilding) {
                city.addBuilding(newBuilding);
                System.out.println("New building added: " + newBuilding.getAddress());
            } else {
                System.out.println("No new building");
            }

            List<Thread> threads = new ArrayList<>();

            for (Building building : city.getBuildings()) {
                Thread thread = new Thread(building::operate);
                threads.add(thread);
                thread.start();
            }

            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static Building createRandomBuilding(int day) {
        Random random = new Random();
        int buildingType = random.nextInt(3);
        String address = "Building " + day + "-" + (random.nextInt(100) + 1);

        return switch (buildingType) {
            case 0 -> new Apartment(address, random.nextInt(10) + 5, random.nextInt(100) + 1);
            case 1 -> new Office(address, random.nextInt(20) + 10, random.nextInt(300) + 50);
            case 2 -> new Shop(address, random.nextInt(5) + 1, "Type " + (random.nextInt(3) + 1));
            default -> new Apartment(address, random.nextInt(10) + 5, random.nextInt(100) + 1);
        };
    }
}
