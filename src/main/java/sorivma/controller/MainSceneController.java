package sorivma.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import sorivma.TechStore;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {
    @FXML
    private Button catalogBtn;
    @FXML
    private Button userBtn;
    @FXML
    private Button manufactuerBtn;
    @FXML
    private Button ordersBtn;
    @FXML
    private Button reportBtn;
    @FXML
    private Button saleBtn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        catalogBtn.getStyleClass().setAll("btn","btn-primary");
        userBtn.getStyleClass().setAll("btn","btn-primary");
        manufactuerBtn.getStyleClass().setAll("btn", "btn-primary");
        ordersBtn.getStyleClass().setAll("btn", "btn-primary");
        reportBtn.getStyleClass().setAll("btn", "btn-primary");
        saleBtn.getStyleClass().setAll("btn","btn-primary");
    }

    @FXML
    private void loadCatalog(){
        System.out.println("initialize catalog");
        changeScene("catalog_scene.fxml");
    }

    @FXML
    private void loadUser(){
        System.out.println("initialize user list");
        changeScene("find_user.fxml");
    }


    @FXML
    private void loadManufactuers(){
        System.out.println("initialize manufactuer list");
        changeScene("find_products.fxml");
    }

    @FXML
    private void openOrders(){
        System.out.println("initialize orders list");
        changeScene("customer_orders.fxml");
    }

    @FXML
    private void openReports(){
        System.out.println("open reports");
        changeScene("report_product.fxml");
    }

    @FXML
    private void openSales(){
        System.out.println("open sales");
        changeScene("sales_product.fxml");
    }

    public void changeScene(String fxml){
        try {
            Parent pane = FXMLLoader.load(
                    Objects.requireNonNull(TechStore.class.getResource(fxml))
            );
            Stage stage = (Stage) catalogBtn.getScene().getWindow();
            Scene scene = new Scene(pane);
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
