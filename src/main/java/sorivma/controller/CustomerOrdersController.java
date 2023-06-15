package sorivma.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import sorivma.TechStore;
import sorivma.entity.Customer;
import sorivma.entity.HibernateCheckLine;
import sorivma.entity.HibernateCustomer;
import sorivma.entity.HibernateOrders;
import sorivma.repo.HibernateCustomersRepo;


import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomerOrdersController implements Initializable {
    @FXML
    private VBox customers;
    @FXML
    private VBox orders;
    @FXML
    private VBox positions;
    @FXML
    private Label cost;
    @FXML
    private Button backBtn;
    private HibernateCustomersRepo customersRepo;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customersRepo = new HibernateCustomersRepo();
        backBtn.getStyleClass().setAll("btn", "btn-default");
        initializeCustomers();
    }

    private void initializeCustomers() {
        customers.getChildren().clear();
        List<HibernateCustomer> customerList = customersRepo.getCustomers();
        for (HibernateCustomer customer : customerList){
            Label label = new Label();
            label.setText(customer.getName().trim() +
                    " " + customer.getMiddleName().trim() +
                    " " + customer.getLastName().trim());
            label.setOnMouseClicked(event -> {
                initializeOrders(customer);
                positions.getChildren().clear();
            });
            customers.getChildren().add(label);
        }
    }

    private void initializeOrders(HibernateCustomer customer) {
        orders.getChildren().clear();
        List<HibernateOrders> ordersList = customer.getOrders();
        for (HibernateOrders order : ordersList){
            Label label = new Label();
            label.setText(order.getDate().toString());
            label.setOnMouseClicked(event -> initializePositions(order));
            orders.getChildren().add(label);
        }
    }

    private void initializePositions(HibernateOrders order) {
        positions.getChildren().clear();
        List<HibernateCheckLine> checkLines = order.getHibernateCheckLineList();
        BigInteger cur_cost = BigInteger.valueOf(0);
        for (HibernateCheckLine checkLine : checkLines){
            Label label = new Label();
            BigInteger quantity = BigInteger.valueOf(checkLine.getQuantity());
            BigInteger price = checkLine.getProduct().getPrice();
            cur_cost = cur_cost.add(quantity.multiply(price));
            label.setText(checkLine.getProduct().getName().trim()
                    + " Количество: " + checkLine.getQuantity() + " Цена за единицу: " + checkLine.getProduct().getPrice());
            positions.getChildren().add(label);
        }
        this.cost.setText("Сумма заказа: " + cur_cost);
        TableView<Customer> tableView = new TableView<>();
    }

    @FXML
    private void goBack(){
        changeScene("main_scene.fxml");
    }

    public void changeScene(String fxml){
        try {
            Parent pane = FXMLLoader.load(
                    Objects.requireNonNull(TechStore.class.getResource(fxml))
            );
            Stage stage = (Stage) backBtn.getScene().getWindow();
            Scene scene = new Scene(pane);
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("error initializing scene with name: " + fxml );
        }
    }
}
