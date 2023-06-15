package sorivma.controller.catalog;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import sorivma.TechStore;
import sorivma.entity.Category;
import sorivma.entity.Characteristic;
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

public class ProductInfoController implements Initializable {
    private Product product;
    @FXML
    private TextField productName;
    @FXML
    private TextField quantity;
    @FXML
    private ImageView image;
    @FXML
    private TextArea description;
    @FXML
    private TextField manufactuerName;
    @FXML
    private TextField country;
    @FXML
    private VBox characteristics;
    @FXML
    private TextField characteristicNameInp;
    @FXML
    private TextField characteristicValInp;
    @FXML
    private VBox manufactuers;
    @FXML
    private VBox categories;
    @FXML
    private TextField categoryName;
    @FXML
    private TextField basePrice;
    @FXML
    private TextField imageURL;
    @FXML
    private Button saveBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Button addCharacteristicBtn;
    @FXML
    private Label nameVal;
    @FXML
    private Label priceVal;
    @FXML
    private Label quantityVal;
    @FXML
    private Label charNameVal;
    @FXML
    private Label charValueVal;
    private HibernateCatalogRepo catalogRepo;
    private HibernateManufactuersRepo manufactuersRepo;
    private HibernateCategoryRepo categoryRepo;
    private static final String noImageURL = "https://yoomag.ru/image/cache/no_image-1500x1500.png";


    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        description.setWrapText(true);
        catalogRepo = new HibernateCatalogRepo();
        manufactuersRepo = new HibernateManufactuersRepo();
        categoryRepo = new HibernateCategoryRepo();
        manufactuerName.setDisable(true);
        country.setDisable(true);
        categoryName.setDisable(true);
        saveBtn.getStyleClass().setAll("btn", "btn-primary");
        deleteBtn.getStyleClass().setAll("btn", "btn-danger");
        backBtn.getStyleClass().setAll("btn", "btn-default");
        addCharacteristicBtn.getStyleClass().setAll("btn", "btn-default");
    }

    public void postInitialize(){
        productName.setText(product.getName());
        quantity.setText(product.getStoraged().toString());
        String loaded_url = product.getDescription().getImageUrl().replace(".webp","");
        if (!loaded_url.trim().equals(noImageURL)){
            imageURL.setText(loaded_url);
        }
        Image loaded_image = new Image(loaded_url);

        System.out.println(product.getDescription().getImageUrl());

        image.setImage(loaded_image);
        description.setText(product.getDescription().getText());
        manufactuerName.setText(product.getManufactuer().getName());
        country.setText(product.getManufactuer().getCountry());
        categoryName.setText(product.getCategory().getName());
        basePrice.setText(String.valueOf(product.getPrice()));
        initializeCharacteristics();
        initializeManufactuers();
        initializeCategory();
    }

    private void initializeCharacteristics(){
        List<Characteristic> characteristicList = product.getDescription().getCharacteristicList();
        for (Characteristic characteristic : characteristicList){
            Label item = new Label(characteristic.getName() + ": " + characteristic.getValue());
            item.setOnMouseClicked(event -> {
                characteristic.setDescription(null);
                characteristicList.remove(characteristic);
                product.getDescription().setCharacteristicList(characteristicList);
                characteristics.getChildren().clear();
                initializeCharacteristics();
            });
            characteristics.getChildren().add(item);
        }
    }

    private void initializeManufactuers(){
        List<Manufactuer> manufactuerList = manufactuersRepo.getManufactuers();
        for (Manufactuer manufactuer : manufactuerList){
            Label item = new Label(manufactuer.getName());
            item.setOnMouseClicked(event -> {
                product.setManufactuer(manufactuer);
                manufactuerName.setText(manufactuer.getName());
                country.setText(manufactuer.getCountry());
            });
            manufactuers.getChildren().add(item);
        }
    }

    private void initializeCategory(){
        List<Category> categoryList = categoryRepo.getCategories();
        for (Category category : categoryList){
            Label item = new Label(category.getName());
            item.setOnMouseClicked(event -> {
                product.setCategory(category);
                categoryName.setText(category.getName());
            });
            categories.getChildren().add(item);
        }
    }

    @FXML
    private void saveProduct(){
        boolean validated = true;
        if (productName.getText().trim().length() < 1){
            nameVal.setText("*");
            validated = false;
        } else {
            product.setName(productName.getText());
            nameVal.setText("");
        }
        try {
            BigInteger price = new BigInteger(basePrice.getText());
            product.setPrice(price);
            priceVal.setText("");
        } catch (Exception e){
            priceVal.setText("*");
            validated = false;
        }
        try {
            long storaged = (long) Integer.parseInt(quantity.getText());
            product.setStoraged(storaged);
            quantityVal.setText("");
        } catch (Exception e){
            quantityVal.setText("*");
            validated = false;
        }
        product.getDescription().setText(description.getText());
        if(!imageURL.getText().equals("")){
            product.getDescription().setImageUrl(imageURL.getText());
        } else {
            product.getDescription().setImageUrl(noImageURL);
        }
        if (validated) {
            catalogRepo.updateProduct(product);
            System.out.println(product.getDescription().getCharacteristicList());
            System.out.println("save product");
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"", ButtonType.OK);
            alert.setHeaderText("Измнения сохранены");
            alert.setTitle("Изменения");
            alert.show();
        }
    }

    @FXML
    private void deleteProduct(){
        Alert alert = new Alert(Alert.AlertType.WARNING, "Изменения нельзя обратить", ButtonType.OK, ButtonType.CANCEL);
        alert.setHeaderText("Вы точно собираетесь удалить данную запись?");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK){
            catalogRepo.deleteProduct(product);
            goBack();
        }
    }

    @FXML
    private void addCharacteristic(){
        boolean validated = true;
        Characteristic newCharacteristic = new Characteristic();
        if (characteristicNameInp.getText().trim().length()>1){
            newCharacteristic.setName(characteristicNameInp.getText());
            charNameVal.setText("");
        } else {
            validated = false;
            charNameVal.setText("*");
        }
        if (characteristicValInp.getText().trim().length()>1){
            newCharacteristic.setValue(characteristicValInp.getText());
            charValueVal.setText("");
        } else {
            validated = false;
            charValueVal.setText("*");
        }
        if (validated){
            newCharacteristic.setDescription(product.getDescription());
            product.getDescription().getCharacteristicList().add(newCharacteristic);
            characteristics.getChildren().clear();
            initializeCharacteristics();
        }
    }

    @FXML
    private void goBack(){
        changeScene("catalog_scene.fxml");
    }

    public void changeScene(String fxml){
        try {
            Parent pane = FXMLLoader.load(
                    Objects.requireNonNull(TechStore.class.getResource(fxml))
            );
            Stage stage = (Stage) manufactuerName.getScene().getWindow();
            Scene scene = new Scene(pane);
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("error initializing scene with name: " + fxml );
        }
    }
}
