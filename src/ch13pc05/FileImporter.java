package ch13pc05;
import java.io.*;
import java.util.*;

/**
 *
 * @author Frank
 */
public class FileImporter {
    //fields
    private ArrayList<Product> products;
    
    //constructors
    public FileImporter(File file) throws FileNotFoundException, NoSuchElementException {
        //
        String str, title;
        double price;
        products = new ArrayList<>();
        Product item;
        
        try {
            Scanner scanner = new Scanner(file);
            
            while (scanner.hasNext()) {
                str = scanner.nextLine();
                String[] result = str.split(",\\s");
                
                title = result[0];
                price = Double.parseDouble(result[1]);
                item = new Product (title, price);
                products.add(item);
            }
            
            //close the file
            scanner.close();
        }
        catch (FileNotFoundException|NoSuchElementException ex) {
            throw ex;
        }
        
    }
    
    public ArrayList<Product> getProducts() {
        return products;
    }
}
