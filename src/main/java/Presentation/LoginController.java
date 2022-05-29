package Presentation;

import Business.DeliveryService;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Objects;

public class LoginController {

    private DeliveryService deliveryService;

    @FXML
    TextField textFieldUsername;

    @FXML
    TextField textFieldPassword;

    @FXML
    public void initialize(){

    }

    @FXML
    public void handleConnect(ActionEvent actionEvent) throws Exception {
        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();
        var users = deliveryService.getAllUsers();
        User user = null;
        for (var u:users)
        {
            if (Objects.equals(u.getUsername(), username))
                user = u;
        }
        if (user == null){
            Util.showWarning("Authentication failure", "Invalid username!");
        }
        else{
            if (!Objects.equals(user.getPassword(), password))
            {
                Util.showWarning("Authentication failure", "Invalid password!");
            }
            else{
                switch (user.getType()){
                    case "administrator":{
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("administrator.fxml"));
                        Parent root = loader.load();

                        AdministratorController controller = loader.getController();
                        controller.setDeliveryService(deliveryService);
                        controller.showProducts();

                        Stage stage = new Stage();
                        stage.setTitle("Administrator");
                        stage.setScene(new Scene(root));
                        stage.show();
                        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
                        break;
                    }
                    case "client":{
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("client.fxml"));
                        Parent root = loader.load();

                        ClientController controller = loader.getController();
                        controller.setDeliveryService(deliveryService);
                        controller.showProducts();
                        controller.setClientId(user.getId());

                        Stage stage = new Stage();
                        stage.setTitle("Client");
                        stage.setScene(new Scene(root));
                        stage.show();
                        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
                        break;
                    }

                }
            }
        }
    }


    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }
}
