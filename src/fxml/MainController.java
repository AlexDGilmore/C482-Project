package fxml;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import inventory.*;

public class MainController {

    private Inventory inventory;
    @FXML private TableView<Part> partTable;
    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Part,Integer> partIDCol, stockPartCol;
    @FXML private TableColumn<Product,Integer> productIDCol, stockProductCol;
    @FXML private TableColumn<Part,Double> pricePartCol;
    @FXML private TableColumn<Product,Double> priceProductCol;
    @FXML private TableColumn<Part,String> namePartCol;
    @FXML private TableColumn<Product,String> nameProductCol;
    @FXML private Button exitMain;
    @FXML private TextField searchProductsT, searchPartsT;

    //opens Add Part Window
    @FXML private void addPartWindow() throws IOException {
        AddPartController apc = new AddPartController(this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPart.fxml"));
        loader.setController(apc);
        Parent addPart = loader.load();
        Stage window = new Stage();
        window.setTitle("Add Part Screen");
        window.setScene(new Scene(addPart));
        window.initModality(Modality.APPLICATION_MODAL);
        window.show();
    }

    //opens Modify Part Window
    @FXML private void modifyPartWindow() throws IOException{
        Part part = partTable.getSelectionModel().getSelectedItem();
        if(part == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Please select a Part from the Parts table before selecting Modify.",ButtonType.OK);
            alert.showAndWait();
        }else {
            if(inventory.lookupPart(part.getPartID()) == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "The part you have selected no longer exists, please search again.", ButtonType.OK);
                alert.showAndWait();
            }else{
                ModifyPartController mpc = new ModifyPartController(this);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPart.fxml"));
                loader.setController(mpc);
                Parent modifyPart = loader.load();
                Stage window = new Stage();
                window.setTitle("Modify Part Screen");
                window.setScene(new Scene(modifyPart));
                window.initModality(Modality.APPLICATION_MODAL);
                window.show();
                mpc.initData(part);
            }
        }
    }

    //opens Add Product Window
    @FXML private void addProductWindow() throws IOException {
        AddProductController apc = new AddProductController(new Product(), this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProduct.fxml"));
        loader.setController(apc);
        Parent addProduct = loader.load();
        Stage window = new Stage();
        window.setTitle("Add Product Screen");
        window.setScene(new Scene(addProduct));
        window.initModality(Modality.APPLICATION_MODAL);
        window.show();
        apc.initData();
    }

    //opens Modify Product Window
    @FXML private void modifyProductWindow() throws IOException{
        Product product = productTable.getSelectionModel().getSelectedItem();
        if(product == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Please select a Product from the Product table before selecting Modify.",ButtonType.OK);
            alert.showAndWait();
        }else {
            if(inventory.lookupProduct(product.getProductID()) == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "The Product you have selected no longer exists, please search again.", ButtonType.OK);
                alert.showAndWait();
            }else {
                ModifyProductController mpc = new ModifyProductController(product, this);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyProduct.fxml"));
                loader.setController(mpc);
                Parent modifyProduct = loader.load();
                Stage window = new Stage();
                window.setTitle("Modify Product Screen");
                window.setScene(new Scene(modifyProduct));
                window.initModality(Modality.APPLICATION_MODAL);
                window.show();
                mpc.initData();
            }
        }
    }

    //searches part ArrayList for part as specified by part ID
    @FXML private void searchPartsMain(){
        try{
            int id = Integer.parseInt(searchPartsT.getText());
            Part part = inventory.lookupPart(id);
            if(part == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,"There was no Part found for the Part ID entered.",ButtonType.OK);
                alert.showAndWait();
                refreshPartTable(part);
            }else{
                refreshPartTable(part);
            }
        }catch (NumberFormatException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Please input an integer value into the Search field.",ButtonType.OK);
            alert.showAndWait();
            refreshPartTable(null);
        }
    }

    //searches product ArrayList for product as specified by product ID
    @FXML private void searchProductsMain(){
        try{
            int id = Integer.parseInt(searchProductsT.getText());
            Product product = inventory.lookupProduct(id);
            if(product == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,"There was no Product found for the Product ID entered.",ButtonType.OK);
                alert.showAndWait();
                refreshProductTable(product);
            }else{
                refreshProductTable(product);
            }
        }catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please input an integer value into the Search field.", ButtonType.OK);
            alert.showAndWait();
            refreshProductTable(null);
        }
    }

    //deletes selected part from part ArrayList
    @FXML private void deletePartMain(){
        Part part = partTable.getSelectionModel().getSelectedItem();
        if(part == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Please select a Part from the Parts table before selecting Delete.",ButtonType.OK);
            alert.showAndWait();
        }else {
            if(inventory.lookupPart(part.getPartID()) == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "The part you have selected no longer exists, please search again.", ButtonType.OK);
                alert.showAndWait();
            }else{
                if(inventory.deletePart(part)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,"The selected part has been deleted.",ButtonType.OK);
                    alert.showAndWait();
                    refreshPartTable(null);
                }else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "The selected part has not been deleted.", ButtonType.OK);
                    alert.showAndWait();
                }
            }
        }
    }

    //deletes selected product from product ArrayList
    @FXML private void deleteProductMain(){
        Product product = productTable.getSelectionModel().getSelectedItem();
        if(product == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Please select a Product from the Products table before selecting Delete.",ButtonType.OK);
            alert.showAndWait();
        }else {
            if(inventory.lookupProduct(product.getProductID()) == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "The product you have selected no longer exists, please search again.", ButtonType.OK);
                alert.showAndWait();
            }else{
                if(inventory.removeProduct(product.getProductID())){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,"The selected product has been deleted.",ButtonType.OK);
                    alert.showAndWait();
                    refreshProductTable(null);
                }else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "The selected product has not been deleted.", ButtonType.OK);
                    alert.showAndWait();
                }
            }
        }
    }

    //exits program
    @FXML private void mainExit(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you wish to exit?",ButtonType.YES,ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            exitMain.getScene().getWindow().hide();
        }
    }

    //refreshes part TableView on call with either a single part or all parts if part is null
    public void refreshPartTable(Part part){
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        namePartCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockPartCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        pricePartCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        if(part == null){
            partTable.getItems().setAll(inventory.getPartList());
        }else{
            partTable.getItems().setAll(part);
        }
        partTable.refresh();
    }

    //refreshes product TableView on call with either a single product or all products if product is null
    public void refreshProductTable(Product product){
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
        nameProductCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockProductCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        priceProductCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        if(product == null){
            productTable.getItems().setAll(inventory.getProductList());
        }else{
            productTable.getItems().setAll(product);
        }
        productTable.refresh();
    }
}