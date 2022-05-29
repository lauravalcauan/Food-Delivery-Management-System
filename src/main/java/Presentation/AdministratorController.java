package Presentation;

import Business.DeliveryService;
import Model.BaseProduct;
import Model.CompositeProduct;
import Model.MenuItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;


public class AdministratorController {
    ObservableList<MenuItem> productsList = FXCollections.observableArrayList();

    private DeliveryService deliveryService;

    @FXML
    ListView<MenuItem> listViewProducts;

    @FXML
    ListView<MenuItem> listViewMenu;

    @FXML
    TableView<MenuItem> tableViewProduct;

    @FXML
    TextField textFieldTitle;

    @FXML
    TextField textFieldRating;

    @FXML
    TextField textFieldCalories;

    @FXML
    TextField textFieldProtein;

    @FXML
    TextField textFieldFat;

    @FXML
    TextField textFieldSodium;

    @FXML
    TextField textFieldPrice;

    @FXML
    public void initialize(){
        listViewProducts.setItems(productsList);
        setProducts();

        tableViewProduct.getColumns().clear();

        TableColumn<MenuItem, String> titleColumn = new TableColumn<>("Title");
        TableColumn<MenuItem, String> ratingColumn = new TableColumn<>("Rating");
        TableColumn<MenuItem, String> caloriesColumn = new TableColumn<>("Calories");
        TableColumn<MenuItem, String> proteinColumn = new TableColumn<>("Protein");
        TableColumn<MenuItem, String> fatColumn = new TableColumn<>("Fat");
        TableColumn<MenuItem, String> sodiumColumn = new TableColumn<>("Sodium");
        TableColumn<MenuItem, String> priceColumn = new TableColumn<>("Price");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        caloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        proteinColumn.setCellValueFactory(new PropertyValueFactory<>("protein"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        fatColumn.setCellValueFactory(new PropertyValueFactory<>("fat"));
        sodiumColumn.setCellValueFactory(new PropertyValueFactory<>("sodium"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableViewProduct.getColumns().add(titleColumn);
        tableViewProduct.getColumns().add(ratingColumn);
        tableViewProduct.getColumns().add(caloriesColumn);
        tableViewProduct.getColumns().add(proteinColumn);
        tableViewProduct.getColumns().add(fatColumn);
        tableViewProduct.getColumns().add(sodiumColumn);
        tableViewProduct.getColumns().add(priceColumn);


    }

    private void setProducts()
    {
        var cellFactory = new Callback<ListView<MenuItem>, ListCell<MenuItem>>() {
            @Override
            public ListCell<MenuItem> call(ListView<MenuItem> param) {
                ListCell<MenuItem> cell = new ListCell<MenuItem>() {
                    private final AnchorPane anchorPane = new AnchorPane();
                    private final Label labelTitle = new Label();

                    {
                        anchorPane.getChildren().add(labelTitle);
                    }

                    @Override
                    public void updateItem(MenuItem item, boolean empty) {

                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            if (item instanceof BaseProduct)
                                labelTitle.setText(((BaseProduct) item).getTitle());
                            if (item instanceof CompositeProduct) {
                                String nameProduct = "";
                                for (var p:((CompositeProduct) item).getProducts())
                                {
                                    nameProduct += p.getTitle() + ";";
                                }
                                labelTitle.setText(nameProduct);
                            }
                            this.setOnMouseClicked(event -> {
                                setTableViewProductDetails(item);
                            });
                            setGraphic(anchorPane);
                        }
                    }
                };
                return cell;
            }
        };
        listViewProducts.setCellFactory(cellFactory);
        listViewMenu.setCellFactory(cellFactory);
    }

    private void setTableViewProductDetails(MenuItem menuItem) {
        List<BaseProduct> tableProducts = new ArrayList<>();
        if(menuItem instanceof BaseProduct) {
            tableProducts.add((BaseProduct) menuItem);
        }

        if(menuItem instanceof CompositeProduct) {
            tableProducts.addAll(((CompositeProduct) menuItem).getProducts());
        }

        tableViewProduct.getItems().clear();
        tableViewProduct.getItems().setAll(tableProducts);
    }

    public void showProducts()
    {
        productsList.setAll(deliveryService.getMenuItems());
    }

    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @FXML
    public void handleAddToMenu() {

        var product = listViewProducts.getSelectionModel().getSelectedItem();
        if(!listViewMenu.getItems().contains(product) && product instanceof BaseProduct) {
            listViewMenu.getItems().add(product);
        }
    }

    @FXML
    public void handleCreateMenu() {

        var products = listViewMenu.getItems();
        if (products.size()>1){
            List<BaseProduct> baseProducts = new ArrayList<>();
            for (var p : products) {
                baseProducts.add((BaseProduct) p);
            }
            deliveryService.addCompositeProduct(new CompositeProduct(baseProducts));
            deliveryService.importProducts();
            showProducts();
            listViewMenu.getItems().clear();
        }
    }

    @FXML
    public void handleAddProduct() {
        try
        {
            String title = textFieldTitle.getText();
            double rating = Double.parseDouble(textFieldRating.getText());
            int calories = Integer.parseInt(textFieldCalories.getText());
            int protein = Integer.parseInt(textFieldProtein.getText());
            int fat = Integer.parseInt(textFieldFat.getText());
            int sodium = Integer.parseInt(textFieldSodium.getText());
            int price = Integer.parseInt(textFieldPrice.getText());
            BaseProduct product = new BaseProduct(title, rating, calories, protein, fat, sodium, price);
            deliveryService.addBaseProduct(product);
            listViewProducts.getItems().add(product);
        }
        catch (Exception ex)
        {
            Util.showWarning("Add error", ex.getMessage());
        }
    }


    @FXML
    public void handleDeleteProduct() {
        if (listViewProducts.getSelectionModel().isEmpty())
        {
            Util.showWarning("Delete error", "Select a product from list!");
        }
        else
        {
            var product = listViewProducts.getSelectionModel().getSelectedItem();
            listViewProducts.getItems().remove(product);
            var products = listViewProducts.getItems();
            List<MenuItem> list = new ArrayList<>(products);
            deliveryService.saveAllProducts(list);
        }
    }


    @FXML
    public void handleUpdateProduct() {
        if (listViewProducts.getSelectionModel().isEmpty())
        {
            Util.showWarning("Update error", "Select a product from list!");
        }
        else if (listViewProducts.getSelectionModel().getSelectedItem() instanceof BaseProduct)
        {
            var productIndex = listViewProducts.getSelectionModel().getSelectedIndex();
            String title = textFieldTitle.getText();
            double rating = Double.parseDouble(textFieldRating.getText());
            int calories = Integer.parseInt(textFieldCalories.getText());
            int protein = Integer.parseInt(textFieldProtein.getText());
            int fat = Integer.parseInt(textFieldFat.getText());
            int sodium = Integer.parseInt(textFieldSodium.getText());
            int price = Integer.parseInt(textFieldPrice.getText());
            BaseProduct product = new BaseProduct(title, rating, calories, protein, fat, sodium, price);
            listViewProducts.getItems().set(productIndex, product);
            var products = listViewProducts.getItems();
            List<MenuItem> list = new ArrayList<>(products);
            deliveryService.saveAllProducts(list);
        }
    }
}
