package DataAccess;

import Model.BaseProduct;
import Model.CompositeProduct;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsDAO {
    public List<BaseProduct> getBaseProducts() {
        String path = "C:/Users/Lora/OneDrive/Desktop/laura/products.csv";
        String line = "";
        List<BaseProduct> products = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            line = br.readLine();
            Map<String, BaseProduct> titleProducts = new HashMap<>();
            while((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (!titleProducts.containsKey(values[0]) && values.length == 7){
                    BaseProduct product = stringToProduct(values);
                    products.add(product);
                    titleProducts.put(values[0], product);
                }
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<CompositeProduct> getCompositeProducts() {
        String path = "C:/Users/Lora/OneDrive/Desktop/laura/compositeProducts.txt";
        String line = "";
        List<CompositeProduct> compositeProducts = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while((line = br.readLine()) != null) {
                List<String> productsStr = List.of(line.split(";"));
                List<BaseProduct> products = new ArrayList<>();
                for(var str:productsStr){
                    String[] values = str.split(",");
                    if(values.length == 7)
                        products.add(stringToProduct(values));
                }
                CompositeProduct compositeProduct = new CompositeProduct(products);
                compositeProducts.add(compositeProduct);
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return compositeProducts;
    }

    private BaseProduct stringToProduct(String[] values)
    {
        String title = values[0];
        double rating = Double.parseDouble(values[1]);
        int calories = Integer.parseInt(values[2]);
        int protein = Integer.parseInt(values[3]);
        int fat = Integer.parseInt(values[4]);
        int sodium = Integer.parseInt(values[5]);
        int price = Integer.parseInt(values[6]);
        return new BaseProduct(title, rating, calories, protein, fat, sodium, price);
    }
}
