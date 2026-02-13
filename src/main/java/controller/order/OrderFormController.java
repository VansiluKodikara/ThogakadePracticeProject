package controller.order;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.Order;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {
    @FXML
    private JFXButton btnAddOrder;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnReload;

    @FXML
    private TableView<Order> tableOrders;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXTextField txtOrderId;

    @FXML
    private DatePicker dateOrderDate;

    @FXML
    private JFXTextField txtCustomerId;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colOrderDate;

    @FXML
    private TableColumn<?, ?> colCustId;

    @FXML
    private Label lblCurrentDate;

    @FXML
    private Label lblCurrentTime;

    @FXML
    private Label lblCurrentDay;

    @FXML
    void btnAddOrderOnAction(ActionEvent event) throws SQLException {
        String OrderId = txtOrderId.getText();
        LocalDate OrderDate = dateOrderDate.getValue();
        String CustomerId = txtCustomerId.getText();
        Order order = new Order(OrderId, OrderDate, CustomerId.toString());

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement("INSERT INTO Orders values(?,?,?,?,?)");
        System.out.println(connection);
        System.out.println("connected to DB");
        psTm.setString(1,order.getOrderId());
        psTm.setObject(2,order.getOrderDate());
        psTm.setString(3,order.getCustId());

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
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("DELETE FROM Orders where id=? ");
            psTm.setString(1, txtOrderId.getText());

            if(psTm.executeUpdate()>0){
                new Alert(Alert.AlertType.INFORMATION,"Order Deleted").show();
                loadTable();
            }
            else{
                new Alert(Alert.AlertType.ERROR,"Order Not Deleted").show();
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
        PreparedStatement psTm = connection.prepareStatement("SELECT * FROM Orders where id=?");
        psTm.setString(1, txtOrderId.getText());
        ResultSet resultSet = psTm.executeQuery();
        resultSet.next();

        Order order = new Order(
                resultSet.getString(1),
                resultSet.getDate(2).toLocalDate(),
                resultSet.getString(3)
        );
        setTextValue(order);

    }

    private void setTextValue(Order order) {
        txtOrderId.setText(order.getOrderId());
        dateOrderDate.setValue(order.getOrderDate());
        txtCustomerId.setText(order.getCustId().toString());
    }

    ArrayList<Order> orderArrayList=new ArrayList<>();

    private void loadTable(){
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("OrderDate"));
        colCustId.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            System.out.println(connection);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Orders");
            while(resultSet.next()){
                orderArrayList.add(
                        new Order(
                                resultSet.getString(1),
                                resultSet.getDate(2).toLocalDate(),
                                resultSet.getString(3)
                        )
                );

            }

            ObservableList<Order> observableList = FXCollections.observableArrayList(orderArrayList);
            tableOrders.setItems(observableList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadDateAndTime(){
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/mm/yyyy");
        lblCurrentDate.setText(dateFormat.format(date));

        lblCurrentDay.setText(LocalDate.now().getDayOfWeek().name());

        Timeline timeline=new Timeline(new KeyFrame(Duration.ZERO, event ->{
            LocalTime now=LocalTime.now();
            lblCurrentTime.setText(now.getHour()+":"+now.getMinute()+":"+now.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
        loadDateAndTime();
    }
}
