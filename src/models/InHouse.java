package models;

public class InHouse extends Part {

    private int machineId;

    public InHouse(int id, String name, Double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
    }

    public void setMachineId(int value) {
        this.machineId = value;
    }

    public int getMachineId() {
        return this.machineId;
    }
}
