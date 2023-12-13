package org.simplestore.service;

import org.junit.jupiter.api.Test;
import org.simplestore.model.Inventory;
import org.simplestore.model.Product;
import org.simplestore.model.ProductNotFoundException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ShoppingCartConcurrencyTest {
    private final Inventory inventory = new Inventory();

    @Test
    void addAndRemoveItemsConcurrently() throws InterruptedException {
        ShoppingCart shoppingCart = new ShoppingCart(inventory);
        inventory.addProduct(new Product(1, "Test Product", 10.0));

        int threadsCount = 10;
        int addItemsPerThread = 10;
        int removeItemsPerThread = 5;

        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount * 2);

        for (int i = 0; i < threadsCount; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < addItemsPerThread; j++) {
                    shoppingCart.addItem(1, 1);
                }
            });

            executorService.submit(() -> {
                for (int j = 0; j < removeItemsPerThread; j++) {
                    shoppingCart.removeItem(1, 1);
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);


        assertEquals(50, shoppingCart.getItemQuantity(1));
    }

    @Test
    void calculateTotalCostConcurrently() throws InterruptedException, ProductNotFoundException {
        ShoppingCart shoppingCart = new ShoppingCart(inventory);
        inventory.addProduct(new Product(1, "Test Product", 10.0));

        int threadsCount = 10;
        int addItemsPerThread = 10;

        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);

        for (int i = 0; i < threadsCount; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < addItemsPerThread; j++) {
                    shoppingCart.addItem(1, 1);
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        assertEquals(1000.0, shoppingCart.calculateTotalCost());
    }
}
