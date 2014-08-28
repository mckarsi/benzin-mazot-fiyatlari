package model;

/**
 * Created by mertcan on 13.08.2014.
 */
public class FuelModel {

    private String fuelName;
    private String company;
    private String fuelPrice;
    private String city;

    public String getFuelName() {
        return fuelName;
    }

    public void setFuelName(String fuelName) {
        this.fuelName = fuelName;
    }

    public String getFuelPrice() {
        return fuelPrice;
    }

    public void setFuelPrice(String fuelPrice) {
        this.fuelPrice = fuelPrice;
    }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getCompany() { return company; }

    public void setCompany(String company) { this.company = company; }

    @Override
    public String toString(){
        return "fuelName: " + this.getFuelName() + ", city: " + this.getCity() + ", fuelPrice: " + this.getFuelPrice();
    }

}
