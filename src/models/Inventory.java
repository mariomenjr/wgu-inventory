package models;

import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private ObservableList<Part> allParts;
    private ObservableList<Product> allProducts;

    public Inventory() {
        allParts = FXCollections.observableArrayList();
        allProducts = FXCollections.observableArrayList();
    }

    public void addPart(final Part value) {
        this.allParts.add(value);
    }

    public void addProduct(final Product value) {
        this.allProducts.add(value);
    }

    public Part lookupPart(final int partId) {
        return this.allParts.get(partId);
    }

    public Product lookupProduct(final int productId) {
        return this.allProducts.get(productId);
    }

    public ObservableList<Part> lookupPart(final String partName) {
        return FXCollections.observableList(
                this.allParts.stream().filter(part -> part.getName().equals(partName)).collect(Collectors.toList()));
    }

    public ObservableList<Product> lookupProduct(final String productName) {
        return FXCollections.observableList(this.allProducts.stream().filter(part -> part.getName().equals(productName))
                .collect(Collectors.toList()));
    }

    public void updatePart(final int index, final Part selectedPart) {
        this.allParts.set(index, selectedPart);
    }

    public void updateProduct(final int index, final Product selectedProduct) {
        this.allProducts.set(index, selectedProduct);
    }

    public boolean deletePart(final Part selectedPart) {
        return this.allParts.remove(selectedPart);
    }

    public boolean deleteProduct(final Product selectedProduct) {
        return this.allProducts.remove(selectedProduct);
    }

    public ObservableList<Part> getAllParts() {
        return this.allParts;
    }

    public ObservableList<Product> getAllProducts() {
        return this.allProducts;
    }
}