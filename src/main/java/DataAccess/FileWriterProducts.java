package DataAccess;

import Model.BaseProduct;
import Model.CompositeProduct;
import Model.MenuItem;
import Model.Order;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileWriterProducts {

    public void storeBaseProduct(BaseProduct product){

        try {
            FileWriter file = new FileWriter("C:/Users/Lora/OneDrive/Desktop/laura/products.csv", true);

            BufferedWriter output = new BufferedWriter(file);

            String data = "\n" + productToString(product);

            output.write(data);

            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void storeCompositeProduct(CompositeProduct product){

        try {
            FileWriter file = new FileWriter("C:/Users/Lora/OneDrive/Desktop/laura/compositeProducts.txt", true);

            BufferedWriter output = new BufferedWriter(file);

            String data = "";
            for(var p:product.getProducts()){
                data += productToString(p) + ";";
            }
            data += "\n";

            output.write(data);

            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void storeAll(List<MenuItem> products) {

        try {
            FileWriter basedFile = new FileWriter("C:/Users/Lora/OneDrive/Desktop/laura/products.csv");
            FileWriter compositeFile = new FileWriter("C:/Users/Lora/OneDrive/Desktop/laura/compositeProducts.txt");


            BufferedWriter baseOutput = new BufferedWriter(basedFile);
            BufferedWriter compositeOutput = new BufferedWriter(compositeFile);

            baseOutput.write("Title,Rating,Calories,Protein,Fat,Sodium,Price");

            for(var p : products) {
                if (p instanceof BaseProduct) {
                    String data = "\n" + productToString((BaseProduct) p);
                    baseOutput.write(data);
                }
                if (p instanceof CompositeProduct)
                {
                    var prods = ((CompositeProduct) p).getProducts();
                    String dataComp = "";
                    for (var pc:prods)
                    {
                        dataComp += productToString(pc) + ";";
                    }
                    dataComp += "\n";
                    compositeOutput.write(dataComp);
                }

            }
            compositeOutput.close();
            baseOutput.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private String productToString(BaseProduct product){
        return product.getTitle() + "," +
                String.valueOf(product.getRating()) + "," +
                String.valueOf(product.getCalories()) + "," +
                String.valueOf(product.getProtein()) + "," +
                String.valueOf(product.getFat()) + "," +
                String.valueOf(product.getSodium()) + "," +
                String.valueOf(product.getPrice());
    }

}
