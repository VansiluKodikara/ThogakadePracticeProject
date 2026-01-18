package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.ItemDBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ItemFormController  implements Initializable {

    @FXML
    private JFXButton btnAddtem;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnReload;
    @FXML
    private TableView<Item> tableProduct;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXComboBox cmbCategory;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtQuantity;
    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    void btnAddItemOnAction(ActionEvent event) throws SQLException {
        String ItemCode = txtId.getText();
        String Description = txtName.getText();
        String PackSize = txtPrice.getText();
        Double UnitPrice = Double.parseDouble(txtQuantity.getText());
        Integer QtyOnHand = Integer.parseInt(cmbCategory.getValue().toString());
        Item item = new Item(ItemCode, Description, PackSize, UnitPrice, QtyOnHand);

        Connection connection = ItemDBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement("insert into products values(?,?,?,?,?)");
        System.out.println(connection);
        System.out.println("connected to DB");
        psTm.setString(1,item.getItemCode());
        psTm.setString(2,item.getDescription());
        psTm.setString(3,item.getPackSize());
        psTm.setDouble(4,item.getUnitPrice());
        psTm.setInt(5,item.getQtyOnHand());

        if(psTm.executeUpdate()>0){
            new Alert(Alert.AlertType.INFORMATION,"Product Added").show();
            loadTable();
        }
        else{
            new Alert(Alert.AlertType.ERROR,"Product Not Added").show();
        }


    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            Connection connection = ItemDBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("delete from Products where id=? ");
            psTm.setString(1,txtId.getText());

            if(psTm.executeUpdate()>0){
                new Alert(Alert.AlertType.INFORMATION,"Product Deleted").show();
                loadTable();
            }
            else{
                new Alert(Alert.AlertType.ERROR,"Product Not Deleted").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
    loadTable();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) throws SQLException {

            Connection connection = ItemDBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("Select * from products where id=?");
            psTm.setString(1,txtId.getText());
            ResultSet resultSet = psTm.executeQuery();
            resultSet.next();

                Item item = new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getInt(5)

                );
                setTextValue(item);




    }

    private void setTextValue(Item item) {
        txtId.setText(item.getItemCode());
        txtName.setText(item.getDescription());
        txtPrice.setText(item.getPackSize().toString());
        txtQuantity.setText(item.getUnitPrice().toString());
        cmbCategory.setValue(item.getQtyOnHand());
    }

    ArrayList<Item> itemArrayList=new ArrayList<>();


    private void loadTable(){
        colId.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("PackSize"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("QtyOnHand"));

        try {
            Connection connection = ItemDBConnection.getInstance().getConnection();
            System.out.println(connection);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Item");
            while(resultSet.next()){
                itemArrayList.add(
                        new Item(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getDouble(4),
                resultSet.getInt(5)
                        )
                );

            }

            ObservableList<Item> observableList = FXCollections.observableArrayList(itemArrayList);
           tableProduct.setItems(observableList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbCategory.setItems(
                FXCollections.observableArrayList(
                        Arrays.asList("Electronics","Furniture","Stationery ","Kitchen","Bags","Fashion","Accessories","Home Appliances")

                )
        );
        loadTable();
    }
}
