package org.simplestore;

import org.simplestore.model.Inventory;
import org.simplestore.model.Product;
import org.simplestore.service.ShoppingCart;
import org.simplestore.util.InventoryLoader;

public class Main {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        InventoryLoader.loadInventory("path/to/inventory.txt", inventory);  // Uwaga: zmień na właściwą ścieżkę (przykładowy plik znajduje się w zasobach projektu)


        ShoppingCart cart1 = new ShoppingCart(inventory);
        ShoppingCart cart2 = new ShoppingCart(inventory);

        try {
            cart1.addItem(1, 2);
            cart1.addItem(2, 1);

            cart2.addItem(3, 3);
            cart2.addItem(1, 1);

            double totalCostCart1 = cart1.calculateTotalCost();
            System.out.println("Total cost of cart1: " + totalCostCart1);

            double totalCostCart2 = cart2.calculateTotalCost();
            System.out.println("Total cost of cart2: " + totalCostCart2);

            cart1.clearCart();

            inventory.addProduct(new Product(4, "New Product", 19.99));
            System.out.println("All products in inventory:");
            inventory.listAllProducts().forEach(System.out::println);

            inventory.removeProduct(2);
            System.out.println("All products in inventory after removing product with ID 2:");
            inventory.listAllProducts().forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("Exception occurred: " + e.getMessage());
        }
    }
}
