import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;




 public class Main {

     public static int readInt(Scanner sc, String message) {
         while (true) {
             try {
                 System.out.println(message);
                 return sc.nextInt();
             } catch (Exception e) {
                 System.out.println("Invalid input!");
                 sc.nextLine();
             }
         }
     }


     public static double readDouble(Scanner sc) {
         while (true) {
             try {
                 double value = sc.nextDouble();
                 return value;
             } catch (Exception e) {
                 System.out.println("Invalid value! Please enter a number.");
                 sc.nextLine();
             }
         }
     }


     public static void main(String[] args) {


         List<Product> products = FileManager.loadProducts();
         List<Request> requests = new ArrayList<>();
         Scanner sc = new Scanner(System.in);
         DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

         int option = 0;
         while (option != 5) {

             System.out.println("1 - Register product");
             System.out.println("2 - Create order");
             System.out.println("3 - List products");
             System.out.println("4 - Order List");
             System.out.println("5 - Exit");

             option = readInt(sc, "Choose an option:");
             sc.nextLine(); // Buffer

             if (option == 1) {
                 createProduct(products, sc);
             }

             if (option == 2) {
                 createOrder(products, requests, sc);
             }

             if (option == 3) {
                 listProducts(products);
             }

             if (option == 4) {
                 listOrders(requests, fmt);
             }


         }

     }

     //Create Product OPT 1
     public static void createProduct(List<Product> products, Scanner sc) {
         int again = 1;

         while (again == 1) {
             System.out.println("Product name:");
             String nome = sc.nextLine();

             System.out.println("Price:");
             double preco = readDouble(sc);
             sc.nextLine();

             boolean exists = false;

             for (Product p : products) {
                 if (p.getName().equalsIgnoreCase(nome)) {
                     exists = true;
                     break;
                 }
             }

             if (exists) {
                 System.out.println("Product already exists!");
             } else {
                 products.add(new Product(nome, preco));
                 FileManager.saveProducts(products);
             }

             again = readInt(sc, "Add another item? (1 = yes / 0 = no)");
             sc.nextLine();
         }

         System.out.println("Produto cadastrado!");
     }

     //Create Order OPT 2
     public static void createOrder(List<Product> products, List<Request> requests, Scanner sc) {
         if (products.isEmpty()) {
             System.out.println("No products available!");
             return;
         }

         Request request = new Request();
         int again = 1;

         while (again == 1) {

             System.out.println("Choose the product by number:");

             for (int i = 0; i < products.size(); i++) {
                 System.out.println(i + " - " + products.get(i).getName() + " - $" + products.getFirst().getPrice());
             }

             int choice = readInt(sc, "Choose an option:");

             if (choice >= 0 && choice < products.size()) {

                 Product produtoEscolhido = products.get(choice);
                 int quantity = readInt(sc, "Enter quantity:");

                 if (quantity > 0) {
                     request.addItem(new RequestItem(produtoEscolhido, quantity));
                 } else {
                     System.out.println("Invalid quantity!");
                 }

             } else {
                 System.out.println("Invalid product!");
             }

             again = readInt(sc, "Add another item? (1 = yes / 0 = no)");
         }

         if (request.getItems().isEmpty()) {
             System.out.println("Empty order! Not saved.");
         } else {
             requests.add(request);
             System.out.printf("Order created! Total: $%.2f\n", request.calculateTotal());
         }
     }

     //List products OPT 3
     public static void listProducts(List<Product> products) {
         if (products.isEmpty()) {
             System.out.println("No products available!");
             return;
         }

         for (Product p : products) {
             System.out.printf("%s - $%.2f\n", p.getName(), p.getPrice());
         }
         System.out.println("----------------------");
     }


     // List orders OPT 4
     public static void listOrders(List<Request> requests, DateTimeFormatter fmt) {

         if (requests.isEmpty()) {
             System.out.println("No orders found!");
             return;
         }

         for (int i = 0; i < requests.size(); i++) {
             Request r = requests.get(i);

             System.out.println("Order " + (i + 1) + ":");
             System.out.println("Order time: " + r.getCreatedAt().format(fmt));

             for (RequestItem item : r.getItems()) {
                 System.out.printf(
                         "%s - %dx - $%.2f\n",
                         item.getProduct().getName(),
                         item.getQuantity(),
                         item.getProduct().getPrice()
                 );
             }

             System.out.printf("Total: $%.2f\n", r.calculateTotal());
             System.out.println("----------------------");
         }
     }

 }


