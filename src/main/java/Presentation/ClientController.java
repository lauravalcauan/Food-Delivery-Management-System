package Presentation;

import Business.DeliveryService;
import Model.BaseProduct;
import Model.CompositeProduct;
import Model.MenuItem;
import Model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ClientController {

    ObservableList<MenuItem> productsList = FXCollections.observableArrayList();

    private DeliveryService deliveryService;

    private int clientId;

    @FXML
    ListView<MenuItem> listViewProducts;

    @FXML
    ListView<MenuItem> listViewOrder;

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
                    private final Label labelPrice = new Label();

                    {
                        anchorPane.getChildren().add(labelTitle);

                        AnchorPane.setRightAnchor(labelPrice, 3d);
                        anchorPane.getChildren().add(labelPrice);

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
                            labelPrice.setText(String.valueOf(item.computePrice()));
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
        listViewOrder.setCellFactory(cellFactory);
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


    @FXML
    public void handleAddToOrder(){
        var product = listViewProducts.getSelectionModel().getSelectedItem();
        if (!listViewOrder.getItems().contains(product)){
            listViewOrder.getItems().add(product);
        }
    }

    @FXML
    public void handleGetAll(){
        listViewProducts.getItems().setAll(deliveryService.getMenuItems());
    }

    @FXML
    public void handleRemove(){
        listViewOrder.getItems().remove(listViewOrder.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void handleCreateOrder() {
        List<MenuItem> products = new ArrayList<>(listViewOrder.getItems());
        List<BaseProduct> basedProducts = new ArrayList<>();
        for (var p : products) {
            if (p instanceof CompositeProduct) {
                basedProducts.addAll(((CompositeProduct) p).getProducts());
            } else {
                basedProducts.add((BaseProduct) p);
            }
        }
        deliveryService.createOrder(new Order(clientId, LocalDateTime.now(), basedProducts));
        listViewOrder.getItems().clear();
    }

    @FXML
    public void handleSearch(){
        var allProducts = deliveryService.getMenuItems();
        List<MenuItem> products = new ArrayList<>();
        for (var p:allProducts){
            if (p instanceof BaseProduct)
                products.add(p);
        }
        try {
            if (!textFieldTitle.getText().isEmpty()) {
                products = products.stream()
                        .filter(x -> ((BaseProduct)x).getTitle().toLowerCase(Locale.ROOT).contains(textFieldTitle.getText().toLowerCase(Locale.ROOT)))
                        .collect(Collectors.toList());
            }
            if (!textFieldRating.getText().isEmpty()) {
                double rating = Double.parseDouble(textFieldRating.getText());
                products = products.stream()
                        .filter(x -> ((BaseProduct)x).getRating() == rating)
                        .collect(Collectors.toList());
            }
            if (!textFieldCalories.getText().isEmpty()){
                int calories = Integer.parseInt(textFieldCalories.getText());
                products = products.stream()
                        .filter(x -> ((BaseProduct)x).getCalories() == calories)
                        .collect(Collectors.toList());
            }
            if (!textFieldProtein.getText().isEmpty()){
                int proteins = Integer.parseInt(textFieldProtein.getText());
                products = products.stream()
                        .filter(x -> ((BaseProduct)x).getProtein() == proteins)
                        .collect(Collectors.toList());
            }
            if(!textFieldFat.getText().isEmpty()){
                int fats = Integer.parseInt(textFieldFat.getText());
                products = products.stream()
                        .filter(x -> ((BaseProduct)x).getFat() == fats)
                        .collect(Collectors.toList());
            }
            if(!textFieldSodium.getText().isEmpty()) {
                int sodium = Integer.parseInt(textFieldSodium.getText());
                products = products.stream()
                        .filter(x -> ((BaseProduct)x).getSodium() == sodium)
                        .collect(Collectors.toList());
            }
            if(!textFieldPrice.getText().isEmpty()) {
                int price = Integer.parseInt(textFieldPrice.getText());
                products = products.stream()
                        .filter(x -> ((BaseProduct)x).getPrice() == price)
                        .collect(Collectors.toList());
            }
            listViewProducts.getItems().setAll(products);
        }
        catch (Exception ex)
        {
            Util.showWarning("Search error", ex.getMessage());
        }

    }

    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
