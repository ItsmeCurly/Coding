import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    private final static String FILENAME = "inventory.txt";
    private static final int SIZE = 50;

    public static void main(String[] args) {
        int arrayLoc = 0;
        Scanner fScan = null;
        try {
            fScan = new Scanner(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Product[] products = new Product[SIZE];
        for (int i = 0; i < products.length; i++) {
            products[i] = new Product();
        }

        while (fScan.hasNextLine()) {
            products[arrayLoc] = new Product(fScan.next(), fScan.nextInt(), fScan.nextDouble());
            arrayLoc += 1;
        }
        fScan.close();

        String choice;
        Scanner uScan = new Scanner(System.in);

        do {
            System.out.print("Enter letter of choice: ");
            choice = uScan.nextLine();
            String productName;
            switch (choice) {
                case "E":
                    System.out.print("Enter product: ");
                    productName = uScan.nextLine();
                    int index = find(productName, products);
                    if (index > -1) {
                        Product product = products[index];
                        System.out.print("Enter amount to add (+) or subtract (-): ");
                        String units = uScan.nextLine();
                        if (units.contains("+")) {
                            product.setStock(product.getStock() + Integer.parseInt(units.substring(1)));
                        } else {
                            product.setStock(product.getStock() - Integer.parseInt(units.substring(1)));
                        }
                        System.out.printf("Item %s has new amount in stock %d\n", product.getName(), product.getStock());
                    } else {
                        System.out.printf("Item %s is not found\n", productName);
                        break;
                    }
                    break;
                case "I":
                    System.out.print("Enter new product ID, stock, and unit price: ");
                    productName = uScan.next();
                    if (find(productName, products) == -1) {
                        products[arrayLoc] = new Product(productName, uScan.nextInt(), uScan.nextDouble());
                        uScan.nextLine();
                        arrayLoc += 1;
                        System.out.printf("Item %s is inserted.\n", productName);
                    } else {
                        System.out.printf("Item %s already exists\n", productName);
                    }
                    break;
                case "R":
                    System.out.print("Enter ID of product to remove: ");
                    productName = uScan.nextLine();
                    index = find(productName, products);
                    if (index > -1) {
                        products[index] = new Product();
                        System.out.printf("Item %s is removed\n", productName);
                    } else {
                        System.out.printf("Item %s does not exist\n", productName);
                    }
                    break;
                case "D":
                    for (Product p : products) {
                        if (!p.getName().equals(""))
                            System.out.println(p);
                    }
                    System.out.printf("Total inventory: %f\n", computeSum(products));
                    break;
            }
        } while (!choice.equals("Q"));
    }

    private static int find(String product, Product[] products) {
        for (int i = 0; i < products.length; i++) {
            if ((products[i].getName()).equals(product)) {
                return i;
            }
        }
        return -1;
    }

    private static double computeSum(Product[] products) {
        double sum = 0;
        for (Product p : products) {
            sum += p.getPrice() * p.getStock();
        }
        return sum;
    }
}
