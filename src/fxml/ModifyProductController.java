package fxml;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import inventory.*;

public class ModifyProductController {

    @FXML private TextField productIDT, nameT, inventoryLevelT, minT, maxT, priceT, searchPartsT;
    @FXML private Button exit;
    @FXML private TableView<Part> allParts, productParts;
    @FXML private TableColumn<Part,Integer> partIDCol, partIDCol2,stockPartCol, stockPartCol2;
    @FXML private TableColumn<Part,String> namePartCol, namePartCol2;
    @FXML private TableColumn<Part,Double> pricePartCol, pricePartCol2;
    private MainController mc;
    private Product product;
    private Inventory inventory;

    //constructor allows for passing of main controller to refresh product table and pass in existing product to modify
    public ModifyProductController(Product product, MainController mc){
        this.product = product;
        this.mc = mc;
    }

    //populates all fields with data from product parameter of constructor and refreshes both TableViews
    public void initData(){
        productIDT.setText(Integer.toString(product.getProductID()));
        nameT.setText(product.getName());
        inventoryLevelT.setText(Integer.toString(product.getInStock()));
        minT.setText(Integer.toString(product.getMin()));
        maxT.setText(Integer.toString(product.getMax()));
        priceT.setText(Double.toString(product.getPrice()));
        refreshAllParts(null);
        refreshProductParts(null);
    }

    //searches all parts ArrayList for part matching specified part ID
    @FXML private void searchButton(){
        try{
            int id = Integer.parseInt(searchPartsT.getText());
            Part part = inventory.lookupPart(id);
            if(part == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,"There was no Part found for the Part ID entered.",ButtonType.OK);
                alert.showAndWait();
                refreshAllParts(part);
            }else{
                refreshAllParts(part);
            }
        }catch (NumberFormatException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Please input an integer value into the Search field.",ButtonType.OK);
            alert.showAndWait();
            refreshAllParts(null);
        }
    }

    //adds selected part from all parts TableView to products associated parts and refreshes product parts TableView
    @FXML private void addButton(){
        Part part = allParts.getSelectionModel().getSelectedItem();
        if(part == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Please select a Part from the upper table before selecting Add.",ButtonType.OK);
            alert.showAndWait();
        }else {
            if (inventory.lookupPart(part.getPartID()) == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "The part you have selected no longer exists, please search again.", ButtonType.OK);
                alert.showAndWait();
            }else {
                product.addAssociatedPart(part);
                refreshProductParts(null);
            }
        }
    }

    //deletes selected part from product associated parts and refreshes product parts TableView
    @FXML private void deleteButton(){
        Part part = productParts.getSelectionModel().getSelectedItem();
        if(part == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Please select a Part from the bottom table before selecting Delete.",ButtonType.OK);
            alert.showAndWait();
        }else {
            if (inventory.lookupPart(part.getPartID()) == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "The part you have selected no longer exists, please search again.", ButtonType.OK);
                alert.showAndWait();
            }else {
                product.removeAssociatedPart(part.getPartID());
                refreshProductParts(null);
            }
        }
    }

    //saves changes to product to products ArrayList and refreshes product table
    @FXML private void saveButton(){
        String error = "";
        try {
            error = "Inventory Level";
            int inventoryLevel = Integer.parseInt(inventoryLevelT.getText());
            error = "Minimum Stock Level";
            int min = Integer.parseInt(minT.getText());
            error = "Maximum Stock Level";
            int max = Integer.parseInt(maxT.getText());
            error = "Price";
            double price = Double.parseDouble(priceT.getText());
            if (min > max){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please ensure that the Maximum Stock Level is greater than the Minimum Stock Level.", ButtonType.OK);
                alert.showAndWait();
            } else if(min > inventoryLevel || inventoryLevel > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please ensure that Inventory Level is within the range of Stock Levels.", ButtonType.OK);
                alert.showAndWait();
            } else if(product.getAssociatedParts().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please add at least one Part to the Product.", ButtonType.OK);
                alert.showAndWait();
            } else if(price < productParts.getItems().stream().mapToDouble(Part::getPrice).sum()){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please ensure that total Product Price is at least equal to sum of Part Prices.", ButtonType.OK);
                alert.showAndWait();
            } else if(nameT.getText().trim().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a name for this Product before saving.", ButtonType.OK);
                alert.showAndWait();
            } else{
                inventory.updateProduct(Integer.parseInt(productIDT.getText()),new Product(product.getAssociatedParts(),
                        Integer.parseInt(productIDT.getText()), nameT.getText(), Double.parseDouble(priceT.getText()),
                        Integer.parseInt(inventoryLevelT.getText()), Integer.parseInt(minT.getText()), Integer.parseInt(maxT.getText())));
                exit.getScene().getWindow().hide();
                mc.refreshProductTable(null);
            }
        } catch (NumberFormatException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Please input an acceptable value into the " + error + " field.",ButtonType.OK);
            alert.showAndWait();
        }
    }

    //exits window
    @FXML private void cancelButton(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you wish to cancel?", ButtonType.YES,ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            exit.getScene().getWindow().hide();
        }
    }

    //refreshes all part TableView on call with either a single part or all parts if part is null
    private void refreshAllParts(Part part){
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        namePartCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockPartCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        pricePartCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        if(part == null){
            allParts.getItems().setAll(inventory.getPartList());
        }else{
            allParts.getItems().setAll(part);
        }
        allParts.refresh();
    }

    //refreshes associated parts TableView on call with either a single part or all parts if part is null
    private void refreshProductParts(Part part){
        partIDCol2.setCellValueFactory(new PropertyValueFactory<>("partID"));
        namePartCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockPartCol2.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        pricePartCol2.setCellValueFactory(new PropertyValueFactory<>("price"));
        if(part == null){
            productParts.getItems().setAll(product.getAssociatedParts());
        }else{
            productParts.getItems().setAll(part);
        }
        productParts.refresh();
    }
}