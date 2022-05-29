package DataAccess;

import Model.BaseProduct;
import Model.CompositeProduct;
import Model.Order;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OrdersDAO {

    public void storeOrder(Order order){

        try {
            FileWriter file = new FileWriter("C:/Users/Lora/OneDrive/Desktop/laura/orders.txt", true);

            BufferedWriter output = new BufferedWriter(file);

            String data = "\n" + orderToString(order);

            output.write(data);

            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void storeAll(List<Order> orders) {

        try {
            FileWriter file = new FileWriter("C:/Users/Lora/OneDrive/Desktop/laura/orders.txt");

            BufferedWriter output = new BufferedWriter(file);

            for(var o : orders) {
                String data = orderToString(o) + "\n";
                output.write(data);
            }
            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String orderToString(Order order){

        String productsStr = "";
        for (var p:order.getProducts()){
            productsStr += productToString(p) + ";";
        }

        return String.valueOf(order.getClientId()) + "," +
                String.valueOf(order.getOrderDate()) + "," +
                productsStr;
    }

    public void createBill(Order order) {

        try {
            FileWriter file = new FileWriter("C:/Users/Lora/OneDrive/Desktop/laura/bill.txt");


            BufferedWriter output = new BufferedWriter(file);

            output.write("BILL\n");
            output.write("Client id:" + String.valueOf(order.getClientId()) + "\n" );
            output.write("Date:" + String.valueOf(order.getOrderDate()) + "\n");
            int total = 0;
            for (var product: order.getProducts())
            {
                output.write(product.getTitle() + " " + String.valueOf(product.getPrice()) + "\n");
                total += product.getPrice();
            }
            output.write("Total:" + String.valueOf(total));
            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String productToString(BaseProduct product){
        return product.getTitle() + "." +
                String.valueOf(product.getRating()) + "." +
                String.valueOf(product.getCalories()) + "." +
                String.valueOf(product.getProtein()) + "." +
                String.valueOf(product.getFat()) + "." +
                String.valueOf(product.getSodium()) + "." +
                String.valueOf(product.getPrice());
    }
}
