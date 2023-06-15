package sorivma.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import sorivma.TechStore;
import sorivma.entity.Manufactuer;
import sorivma.entity.Product;
import sorivma.repo.HibernateManufactuersRepo;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class FindProductsController implements Initializable {
    @FXML
    private Label currentManufactuer;
    @FXML
    private VBox manufactuers;
    @FXML
    private VBox products;
    @FXML
    private Button backBtn;
    private HibernateManufactuersRepo manufactuersRepo;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manufactuersRepo = new HibernateManufactuersRepo();
        backBtn.getStyleClass().setAll("btn", "btn-default");
        initializeManufactuers();
    }
    
    private void initializeManufactuers(){
        manufactuers.getChildren().clear();
        List<Manufactuer> manufactuerList = manufactuersRepo.getManufactuers();
        for (Manufactuer manufactuer : manufactuerList){
            Label label = new Label();
            label.setPrefWidth(1000000);
            label.setText(manufactuer.getName().trim() + " " + manufactuer.getCountry().trim());
            label.setOnMouseClicked(event -> {
                initializeProducts(manufactuer);
                currentManufactuer.setText("Текущий производитель: " + manufactuer.getName());
            }
            );
            manufactuers.getChildren().add(label);
        }
    }

    private void initializeProducts(Manufactuer manufactuer) {
        products.getChildren().clear();
        List<Product> productList = manufactuer.getProductList();
        for (Product product : productList){
            Label label = new Label();
            label.setText(product.getName().trim() + " Цена: "
                    + product.getPrice().toString() + " Категория: "
                    + product.getCategory().getName());
            products.getChildren().add(label);
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
