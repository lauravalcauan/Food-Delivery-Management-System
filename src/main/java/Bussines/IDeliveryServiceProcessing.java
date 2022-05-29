package Business;

import Model.BaseProduct;
import Model.CompositeProduct;
import Model.MenuItem;
import Model.Order;

import java.util.List;

public interface IDeliveryServiceProcessing {
    void importProducts();
    void createOrder(Order order);

    void addCompositeProduct(CompositeProduct compositeProduct);

    void addBaseProduct(BaseProduct baseProduct);

    void saveAllProducts(List<MenuItem> products);
}
