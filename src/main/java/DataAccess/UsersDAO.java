package DataAccess;

import Model.BaseProduct;
import Model.CompositeProduct;
import Model.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO {
    public List<User> getUsers() {
        String path = "C:/Users/Lora/OneDrive/Desktop/laura/users.txt";
        String line = "";
        List<User> users = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while((line = br.readLine()) != null) {
                List<String> userStr = List.of(line.split(";"));
                if(userStr.size() == 4) {
                    int id = Integer.parseInt(userStr.get(0));
                    String username = userStr.get(1);
                    String password = userStr.get(2);
                    String type = userStr.get(3);
                    User user = new User(id, username, password, type);
                    users.add(user);
                }
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}
