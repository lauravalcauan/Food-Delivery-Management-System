package Model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Order {
    private int clientId;
    private LocalDateTime orderDate;
    private List<BaseProduct> products;

    public Order(int clientId, LocalDateTime orderDate, List<BaseProduct> products) {
        this.clientId = clientId;
        this.orderDate = orderDate;
        this.products = products;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public List<BaseProduct> getProducts() {
        return products;
    }

    public void setProducts(List<BaseProduct> products) {
        this.products = products;
    }


    @Override
    public String toString() {
        return "Order{" +
                "clientId=" + clientId +
                ", orderDate=" + orderDate +
                ", products=" + products +
                '}';
    }
}
