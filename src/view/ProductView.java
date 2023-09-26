package view;

import controller.ProductController;
import java.util.List;
import java.util.Scanner;
import model.Product;

public class ProductView {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ProductController pc = new ProductController();
        int check = 0;
        while (true) {
            try {
                System.out.println("\n1. Create a Product\n"
                        + "2. Check exits Product.\n"
                        + "3. Search Product’ information by Name\n"
                        + "4. Update Product:\n"
                        + "5. Delete Product.\n"
                        + "6. Save Products to file.\n"
                        + "7. Print list Products from the file.\n"
                        + "Others- Quit");
                System.out.print("Your choice: ");
                check = Integer.parseInt(sc.nextLine());
                if (check == 1) {
                    while (true) {
                        pc.createProduct();
                        if (pc.askToBackToMenu()) {
                            break;
                        }
                    }
                } else if (check == 2) {
                    while (true) {
                        System.out.print("ID to check exits: ");
                        String idToCheck = sc.nextLine();
                        if (pc.checkExistProduct(idToCheck)) {
                            System.out.println("Exits product");
                            System.out.println(pc.getProductFromProductID(idToCheck).toString());
                        } else {
                            System.out.println("No product found");
                        }
                        if (pc.askToBackToMenu()) {
                            break;
                        }
                    }
                } else if (check == 3) {
                    while (true) {
                        System.out.print("Name to find: ");
                        String nameToFind = sc.nextLine().trim();
                        List<Product> listProductMatchWithName = pc.searchProductByName(nameToFind);
                        if (!listProductMatchWithName.isEmpty()) {
                            for (Product product : listProductMatchWithName) {
                                System.out.println(product.toString());
                            }
                        } else {
                            System.out.println("No product match with name");
                        }
                        if (pc.askToBackToMenu()) {
                            break;
                        }
                    }
                } else if (check == 4) {
                    while (true) {
                        System.out.print("ID to update: ");
                        String productIDToUpdate = sc.nextLine().trim();
                        if(pc.updateProduct(productIDToUpdate)){
                            System.out.println("Updated success");
                        }else{
                            System.out.println("Product not found");
                        }
                        if (pc.askToBackToMenu()) {
                            break;
                        }
                    }
                } else if (check == 5) {
                    while (true) {
                        pc.deleteProduct();
                        if (pc.askToBackToMenu()) {
                            break;
                        }
                    }
                } else if (check == 6) {
                    while (true) {
                        pc.saveProductsToFile();
                        if (pc.askToBackToMenu()) {
                            break;
                        }
                    }
                } else if (check == 7) {
                    while (true) {
                        pc.printAllListFromFile();
                        if (pc.askToBackToMenu()) {
                            break;
                        }
                    }
                } else {
                    pc.saveProductsToFile();
                    break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
