package uth.edu.jpa.models;
import jakarta.persistence.*;
@Entity
@Table (name="Product")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productID; //Id
    private String ProductName;
    private double prices;
    private int ProductType;
    private int Inventory;
    //getter và setter


    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        productID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public double getPrices() {
        return prices;
    }

    public void setPrices(double prices) {
        this.prices = prices;
    }

    public int getProductType() {
        return ProductType;
    }

    public void setProductType(int productType) {
        ProductType = productType;
    }

    public int getInventory() {
        return Inventory;
    }

    public void setInventory(int inventory) {
        Inventory = inventory;
    }
}

