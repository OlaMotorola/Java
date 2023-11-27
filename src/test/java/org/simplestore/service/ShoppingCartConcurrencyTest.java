package org.simplestore.service;

import org.junit.jupiter.api.Test;
import org.simplestore.model.Inventory;
import org.simplestore.model.Product;
import org.simplestore.model.ProductNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShoppingCartConcurrencyTest {
    private final Inventory inventory = new Inventory();

    @Test
    void addAndRemoveItemsConcurrently() throws InterruptedException {
        ShoppingCart shoppingCart = new ShoppingCart(inventory);
        inventory.addProduct(new Product(1, "Test Product", 10.0));

        Object lock = new Object();
        int[] addedCount = {0};
        int[] removedCount = {0};

        // Add items concurrently
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                synchronized (lock) {
                    for (int j = 0; j < 10; j++) {
                        shoppingCart.addItem(1, 1);
                        addedCount[0]++;
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    lock.notify();
                }
            }).start();
        }

        // Remove items concurrently
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                synchronized (lock) {
                    while (removedCount[0] < addedCount[0] / 2) {
                        shoppingCart.removeItem(1, 1);
                        removedCount[0]++;
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    lock.notify();
                }
            }).start();
        }

        Thread.sleep(100); // Give some time for threads to finish
        synchronized (lock) {
            lock.notifyAll(); // Notify waiting threads to finish
        }

        // Check if the final quantity is as expected
        assertEquals(50, shoppingCart.getItemQuantity(1));
    }

    @Test
    void calculateTotalCostConcurrently() throws InterruptedException, ProductNotFoundException {
        ShoppingCart shoppingCart = new ShoppingCart(inventory);
        inventory.addProduct(new Product(1, "Test Product", 10.0));

        Object lock = new Object();
        int[] addedCount = {0};

        // Add items concurrently
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                synchronized (lock) {
                    for (int j = 0; j < 10; j++) {
                        shoppingCart.addItem(1, 1);
                        addedCount[0]++;
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    lock.notify();
                }
            }).start();
        }

        Thread.sleep(100); // Give some time for threads to finish
        synchronized (lock) {
            lock.notifyAll(); // Notify waiting threads to finish
        }

        // Check if the total cost calculation is correct
        assertEquals(1000.0, shoppingCart.calculateTotalCost());
    }
}
