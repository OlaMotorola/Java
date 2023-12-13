package org.simplestore.service;

import org.simplestore.model.Inventory;
import org.simplestore.model.Product;
import org.simplestore.model.ProductNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private final Inventory inventory;
    private final Map<Integer, Integer> cartItems = new HashMap<>();

    public ShoppingCart(Inventory inventory) {
        this.inventory = inventory;
    }

    public synchronized void addItem(int productId, int quantity) {
        cartItems.merge(productId, quantity, Integer::sum);
    }

    public synchronized void removeItem(int productId, int quantity) {
        cartItems.computeIfPresent(productId, (key, value) -> value > quantity ? value - quantity : null);
    }


    public synchronized int getItemQuantity(int productId) {
        return cartItems.getOrDefault(productId, 0);
    }

    public synchronized double calculateTotalCost() throws ProductNotFoundException {
        double totalPrice = 0;
        for (Map.Entry<Integer, Integer> entry : cartItems.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();
            Product product = null;
            try {
                product = inventory.getProduct(productId);
            } catch (ProductNotFoundException e) {
                throw new ProductNotFoundException(productId);
            }
            totalPrice += product.getPrice() * quantity;
        }
        return totalPrice;
    }

    public synchronized void clearCart() {
        cartItems.clear();
    }
}
