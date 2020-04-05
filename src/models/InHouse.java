package models;

public class InHouse extends Part {

    private int machineId;

    public InHouse(int id, String name, Double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    public void setMachineId(int value) {
        this.machineId = value;
    }

    public int getMachineId() {
        return this.machineId;
    }
}
