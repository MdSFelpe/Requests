import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public static void saveProducts(List<Product> products) {
        try {
            FileWriter writer = new FileWriter("products.txt");

            for (Product p : products) {
                writer.write(p.getName() + "," + p.getPrice() + "\n");
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving products!");
        }
    }

    public static List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("products.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                String name = parts[0];
                double price = Double.parseDouble(parts[1]);

                products.add(new Product(name, price));
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("No existing file found.");
        }

        return products;
    }


}

