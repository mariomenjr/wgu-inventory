package models;

import javafx.collections.ObservableList;

public class Product {

    private Integer id;
    private String name;
    private Double price;
    private Integer stock;
    private Integer min;
    private Integer max;
    private ObservableList<Part> associatedParts;

    public Product(Integer id, String name, Double price, Integer stock, Integer min, Integer max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    public Integer getId() {
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

    public void setStock(Integer value) {
        this.stock = value;
    }

    public Integer getStock() {
        return this.stock;
    }

    public void setMin(Integer value) {
        this.min = value;
    }

    public Integer getMin() {
        return this.min;
    }

    public void setMax(Integer value) {
        this.max = value;
    }

    public Integer getMax() {
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
