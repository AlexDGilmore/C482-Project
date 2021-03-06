package fxml;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import inventory.*;

public class ModifyPartController {

    private MainController mc;
    private Inventory inventory;
    @FXML private RadioButton select;
    @FXML private Button exit, exitMain;
    @FXML private ToggleGroup group;
    @FXML private Label inHouse, outsourced;
    @FXML private TextField inHouseT, outsourcedT, nameT, inventoryLevelT, minT, maxT, priceT, partIDT;

    //constructor allows for passing of main controller instance in order to call refreshPartTable on saving a part
    public ModifyPartController(MainController mc){
        this.mc = mc;
    }

    //passes selected part from part TableView to controller in order to set values of text fields and select radio
    public void initData(Part part){
        partIDT.setText(Integer.toString(part.getPartID()));
        nameT.setText(part.getName());
        inventoryLevelT.setText(Integer.toString(part.getInStock()));
        minT.setText(Integer.toString(part.getMin()));
        maxT.setText(Integer.toString(part.getMax()));
        priceT.setText(Double.toString(part.getPrice()));
        if (part.getClass().getName() == "inventory.Inhouse") {
            Inhouse partAsInhouse = ((Inhouse) part);
            inHouseT.setText(Integer.toString(partAsInhouse.getMachineID()));
        } else {
            select.setSelected(true);
            radioSelect();
            Outsourced partAsOutsourced = ((Outsourced) part);
            outsourcedT.setText(partAsOutsourced.getCompanyName());
        }
    }

    //switches between Outsourced and In House labels and text fields
    @FXML private void radioSelect(){
        select = (RadioButton)group.getSelectedToggle();
        if(select.getText().equals("In-House")){
            inHouse.setVisible(true);
            inHouseT.setVisible(true);
            outsourced.setVisible(false);
            outsourcedT.setVisible(false);
        }else{
            outsourced.setVisible(true);
            outsourcedT.setVisible(true);
            inHouse.setVisible(false);
            inHouseT.setVisible(false);
        }
    }

    //add part to part ArrayList and refreshes part table
    @FXML private void saveButton(){
        String error = "";
        select = (RadioButton)group.getSelectedToggle();
        try {
            error = "Inventory Level";
            int inventoryLevel = Integer.parseInt(inventoryLevelT.getText());
            error = "Minimum Stock Level";
            int min = Integer.parseInt(minT.getText());
            error = "Maximum Stock Level";
            int max = Integer.parseInt(maxT.getText());
            error = "Price";
            double price = Double.parseDouble(priceT.getText());
            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please ensure that the Maximum Stock Level is greater than the Minimum Stock Level.", ButtonType.OK);
                alert.showAndWait();
            } else if(min > inventoryLevel || inventoryLevel > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please ensure that Inventory Level is within the range of Stock Levels.", ButtonType.OK);
                alert.showAndWait();
            } else if(nameT.getText().trim().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a name for this Part before saving.", ButtonType.OK);
                alert.showAndWait();
            } else {
                if (select.getText().equals("In-House")) {
                    error = "Machine ID";
                    int machineID = Integer.parseInt(inHouseT.getText());
                    Inhouse part = new Inhouse(Integer.parseInt(partIDT.getText()), machineID, nameT.getText(), price, inventoryLevel, min, max);
                    inventory.updatePart(Integer.parseInt(partIDT.getText()),part);
                    select.getScene().getWindow().hide();
                    mc.refreshPartTable(null);
                } else {
                    if (outsourcedT.getText().trim().equals("")){
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a name for the source company before saving.", ButtonType.OK);
                        alert.showAndWait();
                    } else {
                        Outsourced part = new Outsourced(Integer.parseInt(partIDT.getText()), outsourcedT.getText(), nameT.getText(), price, inventoryLevel, min, max);
                        inventory.updatePart(Integer.parseInt(partIDT.getText()), part);
                        select.getScene().getWindow().hide();
                        mc.refreshPartTable(null);
                    }
                }
            }
        } catch (NumberFormatException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Please input an acceptable value into the " + error + " field.",ButtonType.OK);
            alert.showAndWait();
        }
    }

    //exits window
    @FXML private void cancelButton(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you wish to cancel?",ButtonType.YES,ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            exit.getScene().getWindow().hide();
        }
    }
}
