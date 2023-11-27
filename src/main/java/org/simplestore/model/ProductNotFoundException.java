package org.simplestore.model;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(int id) {
        super("Product with ID " + id + " not found.");
    }
}
