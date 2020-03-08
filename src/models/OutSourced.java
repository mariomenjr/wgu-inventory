package models;

public class OutSourced extends Part {

    private String companyName;

    public OutSourced(int id, String name, Double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
    }

    public void setCompanyName(String value) {
        this.companyName = value;
    }

    public String getCompanyName() {
        return this.companyName;
    }
}
