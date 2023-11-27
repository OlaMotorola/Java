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

    public void addItem(int productId, int quantity) {
        cartItems.merge(productId, quantity, Integer::sum);  // Equivalent of lambda (a, b) -> Integer.sum(a, b)
    }

    public void removeItem(int productId, int quantity) {
        cartItems.computeIfPresent(productId, (key, value) -> value > quantity ? value - quantity : 0);
    }

    public int getItemQuantity(int productId) {
        return cartItems.getOrDefault(productId, 0);
    }

    public double calculateTotalCost() throws ProductNotFoundException {
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

    public void clearCart() {
        cartItems.clear();
    }


}