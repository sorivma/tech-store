package sorivma.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import sorivma.TechStore;
import sorivma.entity.Product;
import sorivma.entity.Sale;
import sorivma.repo.HibernateCatalogRepo;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

public class SalesController implements Initializable {
    @FXML
    private Button goBackBtn;

    @FXML
    private Label price;

    @FXML
    private VBox products;

    @FXML
    private VBox sales;

    @FXML
    private Label selectedProduct;

    @FXML
    private Label priceNoSale;
    private HibernateCatalogRepo catalogRepo;
    @FXML
    void goBack() {
        changeScene("main_scene.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        catalogRepo = new HibernateCatalogRepo();
        goBackBtn.getStyleClass().setAll("btn", "btn-default");

        updateProducts();
    }

    private void updateProducts() {
        List<Product> productList = catalogRepo.getProducts();
        products.getChildren().clear();
        for (Product product : productList){
            Label label = new Label();
            label.setText(product.getName());
            label.setOnMouseClicked(event -> {
                selectedProduct.setText("Выбранный продукт: " + product.getName());
                updateSales(product);
            });
            products.getChildren().add(label);
        }
    }

    private void updateSales(Product product) {
        Set<Sale> saleSet = product.getSales();
        sales.getChildren().clear();
        double price = product.getPrice().intValue();
        for(Sale sale : saleSet){
            price -= price*sale.getSalePercentage();
            Label description = new Label();
            description.setText(sale.getDescription());
            Label percentage = new Label();
            percentage.setText("Процент скидки: " + sale.getSalePercentage().toString());
            Line line = new Line();
            line.setEndX(600);
            sales.getChildren().add(description);
            sales.getChildren().add(percentage);
            sales.getChildren().add(line);
        }
        Double doublePrice = price;
        this.price.setText("Цена с учетом скидки: " + doublePrice);
        this.priceNoSale.setText("Цена без скидки: " + product.getPrice());
    }

    public void changeScene(String fxml){
        try {
            Parent pane = FXMLLoader.load(
                    Objects.requireNonNull(TechStore.class.getResource(fxml))
            );
            Stage stage = (Stage) goBackBtn.getScene().getWindow();
            Scene scene = new Scene(pane);
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("error initializing scene with name: " + fxml );
        }
    }
}
