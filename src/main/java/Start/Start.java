package Start;

import Business.DeliveryService;
import DataAccess.FileWriterProducts;
import DataAccess.ProductsDAO;
import Model.BaseProduct;
import Model.CompositeProduct;
import Presentation.AdministratorController;
import Presentation.ClientController;
import Presentation.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Start extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loginLoader = new FXMLLoader(getClass().getClassLoader().getResource("login.fxml"));
        Parent root = loginLoader.load();

        LoginController loaderController = loginLoader.getController();
        loaderController.setDeliveryService(new DeliveryService());

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}

