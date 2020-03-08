package models;

import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    
    private ObservableList<Part> allParts;
    private ObservableList<Product> allProducts;

    public void addPart(Part value) {
        this.allParts.add(value);
    }

    public void addProduct(Product value) {
        this.allProducts.add(value);
    }

    public Part lookupPart(int partId) {
        return this.allParts.get(partId);
    }

    public Product lookupProduct(int productId) {
        return this.allProducts.get(productId);
    }

    public ObservableList<Part> lookupPart(String partName) {
        return FXCollections.observableList(
            this.allParts
                .stream()
                .filter(part -> part.getName().equals(partName))
                .collect(Collectors.toList())
        );
    }

    public ObservableList<Product> lookupProduct(String productName) {
        return FXCollections.observableList(
            this.allProducts
                .stream()
                .filter(part -> part.getName().equals(productName))
                .collect(Collectors.toList())
        );
    }

    public void updatePart(int index, Part selectedPart) {
        this.allParts.set(index, selectedPart);
    }

    public void updateProduct(int index, Product selectedProduct) {
        this.allProducts.set(index, selectedProduct);
    }

    public boolean deletePart(Part selectedPart) {
        return this.allParts.remove(selectedPart);
    }

    public boolean deleteProduct(Product selectedProduct) {
        return this.allProducts.remove(selectedProduct);
    }

    public ObservableList<Part> getAllParts() {
        return this.allParts;
    }

    public ObservableList<Product> getAllProducts() {
        return this.allProducts;
    }
}