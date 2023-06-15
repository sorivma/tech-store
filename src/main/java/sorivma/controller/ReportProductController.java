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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import sorivma.TechStore;
import sorivma.entity.Product;
import sorivma.entity.Report;
import sorivma.repo.HibernateCatalogRepo;
import sorivma.repo.HibernateCategoryRepo;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ReportProductController implements Initializable {
    @FXML
    private Button goBackBtn;

    @FXML
    private VBox products;

    @FXML
    private VBox reports;
    @FXML
    private Label selectedProduct;
    private HibernateCatalogRepo catalogRepo;

    @FXML
    void goBack() {
        changeScene("main_scene.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        catalogRepo = new HibernateCatalogRepo();
        goBackBtn.getStyleClass().setAll("btn","btn-default");

        updateProducts();
    }

    private void updateProducts() {
        products.getChildren().clear();
        List<Product> productList = catalogRepo.getProducts();
        for (Product product: productList){
            Label label = new Label();
            label.setText(product.getName());
            label.setOnMouseClicked(event -> {
                selectedProduct.setText("Выбран: " + product.getName());
                updateReports(product);
            });
            products.getChildren().add(label);
        }
    }

    private void updateReports(Product product) {
        reports.getChildren().clear();
        List<Report> reportList = product.getReportList();
        for (Report report : reportList){
            Label customerName = new Label();
            customerName.setText(report.getHibernateCustomer().getName());
            Text text = new Text();
            text.setText(report.getText());
            Label grade = new Label();
            grade.setText("Оценка: " + report.getRate());
            Line line = new Line();
            line.setEndX(700);
            reports.getChildren().add(customerName);
            reports.getChildren().add(text);
            reports.getChildren().add(grade);
            reports.getChildren().add(line);
        }
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
