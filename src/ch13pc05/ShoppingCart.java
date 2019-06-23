package ch13pc05;
import java.util.ArrayList;
/**
 *
 * @author Frank
 */
public class ShoppingCart {
    //fields
    private ArrayList<Product> products;
    private final double salesTax = 0.06;
    
    //constructor
    public ShoppingCart() {
        products = new ArrayList<>();
    }
    
    //accessors and mutators
    public void addItem(Product item) {
        products.add(item);
    }
    public void removeItem(int index) {
        products.remove(index);
    }
    
    public ArrayList<Product> getCart() {
        return products;
    }
    public double getSubtotal() {
        double total = 0.0;
        for (Product prod : products) {
            total += prod.getPrice();
        }
        return total;
    }
    public double getSalesTax() {
        return (getSubtotal() * salesTax);
    }
    public double getTotalPrice() {
        return (getSubtotal() + getSalesTax());
    }
    
    public void clearCart() {
        products.clear();
    }
}
