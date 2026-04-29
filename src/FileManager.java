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

    public static void saveRequests(List<Request> requests) {

        try (FileWriter writer = new FileWriter("requests.txt")) {

            for (Request r : requests) {

                writer.write(r.getCreatedAt() + "," + r.getStatus() + "\n");

                for (RequestItem item : r.getItems()) {
                    writer.write(
                            item.getProduct().getName() + "," +
                                    item.getQuantity() + "," +
                                    item.getProduct().getPrice() + "\n"
                    );
                }

                writer.write("---\n");
            }

        } catch (IOException e) {
            System.out.println("Error saving requests!");
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


    public static List<Request> loadRequests() {

        List<Request> requests = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("requests.txt"))) {

            String line;
            Request current = null;

            while ((line = reader.readLine()) != null) {

                if (line.equals("---")) {
                    if (current != null) {
                        requests.add(current);
                    }
                    current = null;
                    continue;
                }

                if (current == null) {
                    String[] parts = line.split(",");

                    current = new Request();
                    current.setStatus(OrderStatus.valueOf(parts[1]));
                } else {
                    String[] parts = line.split(",");

                    String name = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    double price = Double.parseDouble(parts[2]);

                    Product p = new Product(name, price);
                    current.addItem(new RequestItem(p, quantity));
                }
            }

        } catch (IOException e) {
            System.out.println("No saved requests found.");
        }

        return requests;
    }


}

