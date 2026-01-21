package controller.item;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
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
import java.util.ResourceBundle;

public class ItemFormController  implements Initializable {

    @FXML
    private JFXButton btnAddItem;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnReload;
    @FXML
    private TableView<Item> tableItems;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtPackSize;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colPckSize;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colQOnHand;

    @FXML
    void btnAddItemOnAction(ActionEvent event) throws SQLException {
        String ItemCode = txtItemCode.getText();
        String Description = txtDescription.getText();
        String PackSize = txtPackSize.getText();
        Double UnitPrice = Double.parseDouble(txtUnitPrice.getText());
        Integer QtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        Item item = new Item(ItemCode, Description, PackSize, UnitPrice, QtyOnHand);

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement("INSERT INTO Item values(?,?,?,?,?)");
        System.out.println(connection);
        System.out.println("connected to DB");
        psTm.setString(1,item.getItemCode());
        psTm.setString(2,item.getDescription());
        psTm.setString(3,item.getPackSize());
        psTm.setDouble(4,item.getUnitPrice());
        psTm.setInt(5,item.getQtyOnHand());

        if(psTm.executeUpdate()>0){
            new Alert(Alert.AlertType.INFORMATION,"Item Added").show();
            loadTable();
        }
        else{
            new Alert(Alert.AlertType.ERROR,"Item Not Added").show();
        }


    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("DELETE FROM Item where id=? ");
            psTm.setString(1,txtItemCode.getText());

            if(psTm.executeUpdate()>0){
                new Alert(Alert.AlertType.INFORMATION,"Item Deleted").show();
                loadTable();
            }
            else{
                new Alert(Alert.AlertType.ERROR,"Item Not Deleted").show();
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

            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("SELECT * FROM Item where id=?");
            psTm.setString(1,txtItemCode.getText());
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
        txtItemCode.setText(item.getItemCode());
        txtDescription.setText(item.getDescription());
        txtPackSize.setText(item.getPackSize().toString());
        txtUnitPrice.setText(item.getUnitPrice().toString());
        txtQtyOnHand.setText(item.getQtyOnHand().toString());
    }

    ArrayList<Item> itemArrayList=new ArrayList<>();


    private void loadTable(){
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        colPckSize.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("PackSize"));
        colQOnHand.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("QtyOnHand"));

        try {
            Connection connection = DBConnection.getInstance().getConnection();
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
           tableItems.setItems(observableList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
    }
}
