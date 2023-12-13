package org.simplestore.model;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class Inventory {

    private final Map<Integer, Product> products = new HashMap<>();

    public synchronized void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public synchronized Product getProduct(int id) throws ProductNotFoundException {
        Product product = products.get(id);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        return product;
    }

    public synchronized List<Product> listAllProducts() {
        return new ArrayList<>(products.values());
    }

    public void removeProduct(int id) throws ProductNotFoundException {
        Product productToRemove = getProduct(id);
        products.remove(id);
        System.out.println("Removed product: " + productToRemove);
    }

}