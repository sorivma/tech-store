package sorivma.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import sorivma.TechStore;
import sorivma.entity.Customer;
import sorivma.repo.JDBCRepo;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class FindUserController implements Initializable {
    @FXML
    private VBox users;
    @FXML
    private TextField quantity;
    @FXML
    private Button findBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Label val;
    private JDBCRepo repo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        repo = JDBCRepo.getInstance();
        findBtn.getStyleClass().setAll("btn", "btn-default");
        backBtn.getStyleClass().setAll("btn","btn-default");
    }

    @FXML
    private void find(){
        users.getChildren().clear();
        try {
            int quantity = Integer.parseInt(this.quantity.getText());
            List<Customer> customerList = repo.findUsers(quantity);
            for (Customer customer : customerList){
                users.getChildren().add(new Label(customer.getFirstName() + " " + customer.getLastName()));
            }
            val.setText("");
        } catch (Exception e){
            val.setText("*");
        }
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
