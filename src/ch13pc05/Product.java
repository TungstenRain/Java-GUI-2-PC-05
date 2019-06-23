package ch13pc05;

/**
 *
 * @author Frank
 */
public class Product {
    //fields
    private String name;
    private double price;
    
    //constructors
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
    
    //accessors and mutators
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
}
