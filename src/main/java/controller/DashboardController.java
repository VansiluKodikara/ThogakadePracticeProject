package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class DashboardController {

    @FXML
    private AnchorPane dashRoot;

    @FXML
    void btnCustomerFormOnAction(ActionEvent event) {
        try{
            URL resource = this.getClass().getResource("/view/customer_form.fxml");

            assert resource!=null;
            Parent parent=FXMLLoader.load(resource);

            dashRoot.getChildren().clear();
            dashRoot.getChildren().add(parent);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnItemFormOnAction(ActionEvent event) {
        try{
            URL resource = this.getClass().getResource("/view/Item_Form.fxml");

            assert resource!=null;
            Parent parent=FXMLLoader.load(resource);

            dashRoot.getChildren().clear();
            dashRoot.getChildren().add(parent);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnOrderFormOnAction(ActionEvent event){
        try{
            URL resource = this.getClass().getResource("/view/Order_Form.fxml");

            assert resource!=null;
            Parent parent=FXMLLoader.load(resource);

            dashRoot.getChildren().clear();
            dashRoot.getChildren().add(parent);
        }catch (IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Cannot load Order Form\n" + e.getMessage()).show();
            throw new RuntimeException();
        }

    }

}