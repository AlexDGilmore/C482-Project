package inventory;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;

public class Product {

    private ArrayList<Part> associatedParts = new ArrayList<>();
    private int productID;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;

    public Product(){}

    public Product(ArrayList associatedParts, int productID, String name, double price, int inStock, int min, int max){
        this.associatedParts = associatedParts;
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
    }

    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    public boolean removeAssociatedPart(int id){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you wish to delete the selected part?", ButtonType.YES,ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            associatedParts.remove(lookupAssociatedPart(id));
            return true;
        }else {
            return false;
        }
    }

    public Part lookupAssociatedPart(int id) {
        return associatedParts.stream().filter(part -> part.getPartID() == id).findFirst().orElse(null);
    }

    public ArrayList getAssociatedParts(){
        return associatedParts;
    }

    public String getName(){
        return name;
    }

    public void setName(String product){
        name = product;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int id) {
        productID = id;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double cost){
        price = cost;
    }

    public int getInStock(){
        return inStock;
    }

    public void setInStock(int amount){
        inStock = amount;
    }

    public int getMin(){
        return min;
    }

    public void setMin(int bottom){
        min = bottom;
    }

    public int getMax(){
        return max;
    }

    public void setMax(int top){
        max = top;
    }
}
