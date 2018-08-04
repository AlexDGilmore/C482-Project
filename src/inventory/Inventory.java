package inventory;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;

public class Inventory {

    private static ArrayList<Product> products = new ArrayList<>();
    private static ArrayList<Part> allParts = new ArrayList<>();
    private static int partCounter = 0;
    private static int productCounter = 0;

    public static void addProduct(Product product){
        productCounter ++;
        products.add(product);
        product.setProductID(productCounter);
    }

    public static boolean removeProduct(int id){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you wish to delete the selected part?",ButtonType.YES,ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            products.remove(lookupProduct(id));
            return true;
        }else {
            return false;
        }
    }

    public static Product lookupProduct(int id){
        return products.stream().filter(product -> product.getProductID() == id).findFirst().orElse(null);
    }

    public static void updateProduct(int id, Product product){
        products.set(products.indexOf(lookupProduct(id)),product);
    }

    public static void addPart(Part part){
        partCounter++;
        if(part.getClass().getName().equals("inventory.Inhouse")){
            Inhouse temp = ((Inhouse)part);
            allParts.add(temp);
            temp.setPartID(partCounter);
        }else{
            Outsourced temp = ((Outsourced) part);
            allParts.add(temp);
            temp.setPartID(partCounter);
        }
    }

    public static boolean deletePart(Part part){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you wish to delete the selected part?",ButtonType.YES,ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            allParts.remove(part);
            return true;
        }else {
            return false;
        }
    }

    public static Part lookupPart(int id){
        return allParts.stream().filter(part -> part.getPartID() == id).findFirst().orElse(null);
    }

    public static void updatePart(int id, Part part){
        if(part.getClass().getName().equals("inventory.Inhouse")){
            Inhouse temp = ((Inhouse)part);
            allParts.set(allParts.indexOf(lookupPart(id)),temp);
        }else{
            Outsourced temp = ((Outsourced) part);
            allParts.set(allParts.indexOf(lookupPart(id)),temp);
        }
    }

    public static ArrayList getPartList(){
        return allParts;
    }

    public static ArrayList getProductList(){
        return products;
    }
}
