package models;

import javafx.collections.ObservableList;

public class Product {

    private int id;
    private String name;
    private Double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts;

    public Product(int id, String name, Double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    public void setId(int value) {
        this.id = value;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return this.name;
    }

    public void setPrice(Double value) {
        this.price = value;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setStock(int value) {
        this.stock = value;
    }

    public int getStock() {
        return this.stock;
    }

    public void setMin(int value) {
        this.min = value;
    }

    public int getMin() {
        return this.min;
    }

    public void setMax(int value) {
        this.max = value;
    }

    public int getMax() {
        return this.max;
    }

    public void addAssociatedPart(Part value) {
        this.associatedParts.add(value);
    }

    public boolean deleteAssociatedPart(Part value) {
        return this.associatedParts.remove(value);
    }

    public ObservableList<Part> getAllAssociatedParts() {
        return this.associatedParts;
    }
}
