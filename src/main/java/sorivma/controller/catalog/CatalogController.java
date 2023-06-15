package sorivma.controller.catalog;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import sorivma.TechStore;
import sorivma.entity.Category;
import sorivma.entity.Description;
import sorivma.entity.Manufactuer;
import sorivma.entity.Product;
import sorivma.repo.HibernateCatalogRepo;
import sorivma.repo.HibernateCategoryRepo;
import sorivma.repo.HibernateManufactuersRepo;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CatalogController implements Initializable {
    private HibernateCatalogRepo catalogRepo;
    private List<Product> products;
    @FXML
    ScrollPane catalog;
    @FXML
    TextField productName;
    @FXML
    TextField productPrice;
    @FXML
    TextField curManufactuer;
    @FXML
    TextField curCategory;
    @FXML
    VBox content;
    @FXML
    VBox manufactuers;
    @FXML
    VBox categories;
    @FXML
    Button addBtn;
    @FXML
    Button backButton;
    @FXML
    Label val;
    private Manufactuer selectedManufactuer;
    private Category selectedCategory;
    private HibernateManufactuersRepo manufactuersRepo;
    private HibernateCategoryRepo categoryRepo;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        curManufactuer.setDisable(true);
        curCategory.setDisable(true);
        catalogRepo = new HibernateCatalogRepo();
        manufactuersRepo = new HibernateManufactuersRepo();
        categoryRepo = new HibernateCategoryRepo();
        addBtn.getStyleClass().setAll("btn", "btn-primary");
        backButton.getStyleClass().setAll("btn", "btn-default");
        updateScrollPane();
        updateManufactuers();
        updateCategories();
    }

    public void updateScrollPane(){
        content.getChildren().clear();
        products = catalogRepo.getProducts();
        for (Product product: products){
            Label productLabel = new Label(product.getName());
            productLabel.setOnMouseClicked(event -> {
                FXMLLoader loader = new FXMLLoader(TechStore.class.getResource("product_info.fxml"));
                try {
                    Parent root = loader.load();

                    ProductInfoController productInfoController = loader.getController();
                    productInfoController.setProduct(product);
                    productInfoController.postInitialize();

                    Stage stage = (Stage) productLabel.getScene().getWindow();
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
                    stage.setScene(scene);

                } catch (IOException e) {
                    System.out.println("Unable to load scene for exact product");
                }
            });
            content.getChildren().add(productLabel);
        }
    }

    public void updateManufactuers(){
        List<Manufactuer> manufactuerList = manufactuersRepo.getManufactuers();
        for (Manufactuer manufactuer : manufactuerList){
            Label label = new Label();
            label.setText(manufactuer.getName());
            label.setOnMouseClicked(event -> {
                selectedManufactuer = manufactuer;
                curManufactuer.setText(manufactuer.getName());
            });
            manufactuers.getChildren().add(label);
        }
    }

    public void updateCategories(){
        List<Category> categoryList = categoryRepo.getCategories();
        for (Category category : categoryList){
            Label label = new Label();
            label.setText(category.getName());
            label.setOnMouseClicked(event -> {
                selectedCategory = category;
                curCategory.setText(category.getName());
            });
            categories.getChildren().add(label);
        }
    }

    @FXML
    private void addProduct(){
        boolean validated = true;
        Product product = new Product();
        if (productName.getText().trim().length() > 1){
            product.setName(productName.getText());
        } else {
            validated = false;
            val.setText("Введите название!");
        }
        try {
            BigInteger price = new BigInteger(productPrice.getText());
            product.setPrice(price);
        } catch (Exception e){
            validated = false;
            val.setText("Введите корректную цену!");
        }
        if (selectedManufactuer == null){
            validated = false;
            val.setText("Производитель должен быть выбран!");
        } else {
            product.setManufactuer(selectedManufactuer);
        }
        if (selectedCategory == null){
            validated = false;
            val.setText("Категория должна быть выбрана!");
        } else {
            product.setCategory(selectedCategory);
        }
        Description description = new Description();
        description.setProduct(product);
        product.setDescription(description);

        if (validated){
            catalogRepo.addProduct(product);
            updateScrollPane();
            val.setText("");
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
            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(pane);
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("error initializing scene with name: " + fxml );
        }
    }
}
