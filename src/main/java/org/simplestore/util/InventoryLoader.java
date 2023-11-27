package org.simplestore.util;

import org.simplestore.model.Inventory;
import org.simplestore.model.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InventoryLoader {

    public static void loadInventory(String filePath, Inventory inventory) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(",");
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());
                    inventory.addProduct(new Product(id, name, price));
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Error reading line: " + line);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
        }
    }
}
