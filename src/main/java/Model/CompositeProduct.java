package Model;

import java.util.List;

public class CompositeProduct extends MenuItem {
    private List<BaseProduct> products;

    public CompositeProduct(List<BaseProduct> products) {
        this.products = products;
    }

    @Override
    public int computePrice() {
        int price = 0;
        for(var p:products)
        {
            price += p.getPrice();
        }
        return price;
    }


    public List<BaseProduct> getProducts() {
        return products;
    }

    public void setProducts(List<BaseProduct> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "CompositeProduct{" +
                ", products=" + products +
                '}';
    }
}
