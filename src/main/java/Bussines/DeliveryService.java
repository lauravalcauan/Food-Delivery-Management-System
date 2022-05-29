package Business;

import DataAccess.FileWriterProducts;
import DataAccess.OrdersDAO;
import DataAccess.ProductsDAO;
import DataAccess.UsersDAO;
import Model.*;

import java.util.ArrayList;
import java.util.List;

public class DeliveryService implements IDeliveryServiceProcessing{

    private FileWriterProducts fileWriterProducts = new FileWriterProducts();
    private ProductsDAO productsDAO = new ProductsDAO();
    private OrdersDAO ordersDAO = new OrdersDAO();
    private UsersDAO usersDAO = new UsersDAO();
    private List<MenuItem> menuItems = new ArrayList<>();


    public DeliveryService() {
        importProducts();
    }

    @Override
    public void importProducts() {
        menuItems.clear();
        menuItems.addAll(productsDAO.getBaseProducts());
        menuItems.addAll(productsDAO.getCompositeProducts());
    }

    @Override
    public void createOrder(Order order) {
        ordersDAO.storeOrder(order);
        ordersDAO.createBill(order);
    }

    public List<MenuItem> getMenuItems()
    {
        return menuItems;
    }


    @Override
    public void addCompositeProduct(CompositeProduct compositeProduct) {
        fileWriterProducts.storeCompositeProduct(compositeProduct);
    }

    @Override
    public void addBaseProduct(BaseProduct baseProduct) {
        fileWriterProducts.storeBaseProduct(baseProduct);
    }

    @Override
    public void saveAllProducts(List<MenuItem> products){
        fileWriterProducts.storeAll(products);
    }

    public List<User> getAllUsers(){
        return usersDAO.getUsers();
    }

}
