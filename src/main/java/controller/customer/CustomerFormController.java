package controller.customer;

import TM.CustomerTM;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.animation.Animation;
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
import javafx.util.converter.LocalDateStringConverter;
import model.Customer;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.Date;

public class CustomerFormController implements Initializable  {
    @FXML
    private JFXButton btnAddCustomer;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnReload;

    @FXML
    private JFXButton btnSearch;
    @FXML
    private JFXComboBox cmbTitle;

    @FXML
    private TableColumn colAddress;
    @FXML
    private TableView tableCustomers;
    @FXML
    private TableColumn colCity;

    @FXML
    private TableColumn colDob;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colPostalCode;

    @FXML
    private TableColumn colProvince;

    @FXML
    private TableColumn colSalary;

    @FXML
    private DatePicker dateDob;

    @FXML
    private TableView tblCustomers;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPostalCode;

    @FXML
    private JFXTextField txtProvince;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    private Label lblCurrentDate;

    @FXML
    private Label lblCurrentTime;

    @FXML
    private Label lblCurrentDay;

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {

        String id = txtId.getText();
        String title = cmbTitle.getValue().toString();
        String name = txtName.getText();
        LocalDate dob = dateDob.getValue();
        Double salary = Double.parseDouble(txtSalary.getText());
        String address = txtAddress.getText();
        String city = txtCity.getText();
        String province = txtProvince.getText();
        String postalCode = txtPostalCode.getText();
        Customer customer = new Customer(id, name,title, dob, salary,address, city, province, postalCode);
        System.out.println(customer);

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            System.out.println("Connection " + connection);
            PreparedStatement psTm = connection.prepareStatement("INSERT INTO Customer values (?,?,?,?,?,?,?,?,?) ");
            psTm.setString(1,customer.getId());
            psTm.setString(2,customer.getTitle());
            psTm.setString(3,customer.getName());
            psTm.setObject(4,customer.getDob());
            psTm.setDouble(5,customer.getSalary());
            psTm.setString(6,customer.getAddress());
            psTm.setString(7,customer.getCity());
            psTm.setString(8,customer.getProvince());
            psTm.setString(9,customer.getPostalCode());


            if (psTm.executeUpdate()>0) {
                    new Alert(Alert.AlertType.INFORMATION,"Customer Added").show();
                    loadTable();
            }
            else{
    new Alert(Alert.AlertType.ERROR,"Customer Not Added").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadTable();
    }

    private void loadTable() throws RuntimeException {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        ArrayList<CustomerTM> customerArrayList = new ArrayList<>();


        try {
            Connection connection = DBConnection.getInstance().getConnection();
            System.out.println("Connection " + connection);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Customer");

            System.out.println(resultSet);
            while (resultSet.next()){
                customerArrayList.add(
                        new CustomerTM(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getDate(4),
                                resultSet.getDouble(5),
                                resultSet.getString(6),
                                resultSet.getString(7),
                                resultSet.getString(8),
                                resultSet.getString(9)
                        )
                );
            }

            ObservableList<CustomerTM> observableList = FXCollections.observableArrayList(customerArrayList);
            tblCustomers.setItems(observableList);




        } catch (SQLException e) {
            System.out.println("Cannot run");
            throw new RuntimeException(e);

        }


    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement("DELETE FROM Customer WHERE CustID=?");
            psTm.setString(1,txtId.getText());

            if(psTm.executeUpdate()>0){
                new Alert(Alert.AlertType.INFORMATION,"Customer Deleted!").show();
                loadTable();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement("SELECT * FROM Customer WHERE CustId=?");
        psTm.setString(1,txtId.getText());
        ResultSet resultSet = psTm.executeQuery();
        resultSet.next();
        Customer customer = new Customer(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getDate(4).toLocalDate(),
                resultSet.getDouble(5),
                resultSet.getString(6),
                resultSet.getString(7),
                resultSet.getString(8),
                resultSet.getString(9)
        );
        setTextValue(customer);
    }

    private void loadDayAndDateAndTime(){
        lblCurrentDate.setText(String.valueOf(LocalDate.now()));

        lblCurrentDay.setText(LocalDate.now().getDayOfWeek().name());

        Timeline timeline=new Timeline(new KeyFrame(Duration.ZERO, event -> {
            LocalTime now=LocalTime.now();
            lblCurrentTime.setText(now.getHour()+":"+now.getMinute()+":"+now.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(timeline.INDEFINITE);
        timeline.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbTitle.setItems(
                FXCollections.observableArrayList(
                        Arrays.asList("Mr","Miss","Ms")
                )
        );

        loadTable();
        loadDayAndDateAndTime();
    }
    public void setTextValue(Customer customer){
         txtId.setText(customer.getId());
         cmbTitle.setValue(customer.getTitle());
         txtName.setText(customer.getName());
         dateDob.setValue(customer.getDob());
         txtSalary.setText(customer.getSalary().toString());
         txtAddress.setText(customer.getAddress());
         txtCity.setText(customer.getCity());
         txtProvince.setText(customer.getProvince());
         txtPostalCode.setText(customer.getPostalCode());
    }
}
