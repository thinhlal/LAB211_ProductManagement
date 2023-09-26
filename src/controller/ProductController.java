package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import model.IProduct;
import model.Product;
import model.Utils;

public class ProductController implements IProduct {

    private final Scanner sc = new Scanner(System.in);
    private final String FILE_PRODUCTS = "products.txt";
    private final List<Product> listProduct = new ArrayList<>();

    public boolean askToBackToMenu() {
        System.out.print("Do you want to back to Main Menu(Y/N): ");
        return sc.nextLine().trim().equalsIgnoreCase("Y");
    }

    private boolean askToBackToEnterAgain() {
        System.out.print("Do you want to type again?(Y/N): ");
        return sc.nextLine().trim().equalsIgnoreCase("Y");
    }

    public Product getProductFromProductID(String idProduct) {
        List<Product> listAllProduct = addProductToListFromFile();
        for (Product line : listAllProduct) {
            if (line.getProductID().equalsIgnoreCase(idProduct)) {
                return line;
            }
        }
        return null;
    }

    private boolean checkIDUniqueFromFile(String code) {
        try {
            FileReader file = new FileReader(FILE_PRODUCTS);
            BufferedReader br = new BufferedReader(file);
            String line;
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                String[] lineWords = line.split("_");
                if (lineWords[0].equalsIgnoreCase(code) && lineWords[0] != null) {
                    return false;
                }
            }
            br.close();
            file.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    private boolean checkUniqueCodeFromList(String code) {
        for (Product product : listProduct) {
            if (product.getProductID().equalsIgnoreCase(code)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkUniqecodeFromFileAndList(String code) {
        return checkUniqueCodeFromList(code) && checkIDUniqueFromFile(code);
    }

    private void writeToFileFromList(List<Product> list) {
        try {
            FileWriter fw = new FileWriter(FILE_PRODUCTS);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            listProduct.clear();
            for (int i = 0; i < list.size(); i++) {
                listProduct.add(i, list.get(i));
                pw.println(list.get(i).toString());
            }
            pw.flush();
            pw.close();
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private List addProductToListFromFile() {
        saveProductsToFile();
        List<Product> list = new ArrayList<>();
        try {
            FileReader fr = new FileReader(FILE_PRODUCTS);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while (true) {
                line = br.readLine().trim();
                if (line == null) {
                    break;
                }
                String words[] = line.split("_");
                list.add(new Product(words[0], words[1], Double.parseDouble(words[2]), Integer.parseInt(words[3]), words[4]));

            }
            br.close();
            fr.close();
        } catch (Exception e) {
        }
        return list;
    }

    private String createProductID() {
        System.out.print("ID: ");
        return sc.nextLine().trim();
    }

    private String createProductName() {
        System.out.print("Name: ");
        return sc.nextLine().trim();
    }

    private double createUnitPrice() {
        return Utils.getDouble("Price: ");
    }

    private int createQuantity() {
        return Utils.getInt("Quantity: ");
    }

    private String createStatus() {
        System.out.print("Status: ");
        return sc.nextLine().trim();
    }

    @Override
    public void createProduct() {
        String id = createProductID();
        while (true) {
            if (!checkUniqecodeFromFileAndList(id)) {
                System.out.println("--------------------------------");
                System.out.println("Id is duplicated");
                if (askToBackToEnterAgain()) {
                    id = createProductID();
                } else {
                    id = null;
                    break;
                }
            } else {
                break;
            }
        }
        String name = createProductName();
        while (true) {
            if (!name.matches("^[a-zA-Z]{5,}$")) {
                System.out.println("--------------------------------");
                System.out.println("Product Name must be at least five characters and no spaces.");
                if (askToBackToEnterAgain()) {
                    name = createProductName();
                } else {
                    name = null;
                    break;
                }
            } else {
                break;
            }
        }
        double price = createUnitPrice();
        while (true) {
            if (price < 0 || price > 10000) {
                System.out.println("--------------------------------");
                System.out.println("Price must in 0 - 10000");
                if (askToBackToEnterAgain()) {
                    price = createUnitPrice();
                } else {
                    price = -1;
                    break;
                }
            } else {
                break;
            }
        }
        int quantity = createQuantity();
        while (true) {
            if (quantity < 0 || quantity > 1000) {
                System.out.println("--------------------------------");
                System.out.println("Quantity must in 0 - 1000");
                if (askToBackToEnterAgain()) {
                    quantity = createQuantity();
                } else {
                    quantity = -1;
                    break;
                }
            } else {
                break;
            }
        }

        String status = createStatus();

        listProduct.add(new Product(id, name, price, quantity, status));
    }

    @Override
    public boolean checkExistProduct(String productIDToCheck) {
        saveProductsToFile();
        try {
            FileReader file = new FileReader(FILE_PRODUCTS);
            BufferedReader br = new BufferedReader(file);
            String line;
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                String[] lineWords = line.split("_");
                if (lineWords[0].equalsIgnoreCase(productIDToCheck)) {
                    return true;
                }
            }
            br.close();
            file.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public List searchProductByName(String idToCheck) {
        List<Product> listProductMatchWithName = new ArrayList<>();
        List<Product> listAllProduct = addProductToListFromFile();
        String idToCheckIgnoreCase = idToCheck.toLowerCase();
        for (Product product : listAllProduct) {
            if (product.getProductName().toLowerCase().contains(idToCheckIgnoreCase) && product.getProductName() != null) {
                listProductMatchWithName.add(product);
            }
        }
        Collections.sort(listProductMatchWithName, (Product o1, Product o2) -> o1.getProductName().compareTo(o2.getProductName()));
        return listProductMatchWithName;
    }

    @Override
    public boolean updateProduct(String updateProductID) {
        List<Product> listAllProduct = addProductToListFromFile();
        boolean check = false;
        for (Product product : listAllProduct) {
            if (product.getProductID().equalsIgnoreCase(updateProductID)) {
                check = true;
                System.out.print("Old information: ");
                System.out.println(product.toString());
                System.out.println("Enter updated information for the product (leave blank to keep existing information):");
                String updatedNameStr = Utils.getStringOrBlank("Product Name: ", "^[a-zA-Z]{5,}$", "Product Name must be at least five characters and no spaces.");

                if (!updatedNameStr.isEmpty()) {
                    product.setProductName(updatedNameStr);
                }
                String updatedPriceStr = Utils.getStringOrBlank("Unit Price: ", "^\\d+(\\.\\d+)?$", "Please enter only digit");
                if (!updatedPriceStr.isEmpty() && (Double.parseDouble(updatedPriceStr) > 0 && Double.parseDouble(updatedPriceStr) < 10000)) {
                    product.setUnitPrice(Double.parseDouble(updatedPriceStr));
                } else {
                    System.out.println("Because out range 0 > 10000 to update so we keep old value");
                }
                String updatedQuantityStr = Utils.getStringOrBlank("Quantity: ", "^[0-9]+$", "Please enter only digit");
                if (!updatedQuantityStr.isEmpty() && (Integer.parseInt(updatedQuantityStr) > 0 && Integer.parseInt(updatedQuantityStr) < 1000)) {
                    product.setQuantity(Integer.parseInt(updatedQuantityStr));
                } else {
                    System.out.println("Because out range 0 > 1000 to update so we keep old value");
                }
                System.out.print("Status (Available/Not Available): ");
                String updatedStatus = sc.nextLine().trim();
                if (!updatedStatus.isEmpty()) {
                    product.setStatus(updatedStatus);
                }
                break;
            }
        }
        writeToFileFromList(listAllProduct);
        return check;
    }

    @Override
    public void deleteProduct() {
        List<Product> listAllProduct = addProductToListFromFile();
        String deleteProductID = Utils.getString("Product ID to delete: ", "Not empty");
        boolean checkDelete = false;
        boolean sureDelete = true;
        for (Product product : listAllProduct) {
            if (product.getProductID().equalsIgnoreCase(deleteProductID)) {
                System.out.println("Information before delete: ");
                System.out.print(product.toString());
                System.out.print("Are you sure to delete(Y/N): ");
                String check = sc.nextLine().trim();
                if (check.equalsIgnoreCase("y")) {
                    sureDelete = true;
                    checkDelete = true;
                    listAllProduct.remove(product);
                    break;
                } else {
                    sureDelete = false;
                    break;
                }
            }
        }
        if (checkDelete && sureDelete) {
            System.out.println("Deleted successfully");
        } else if (!sureDelete) {
            System.out.println("You dont want to delete it");
        } else if (!checkDelete) {
            System.out.println("Not found product to delete");
        }
        writeToFileFromList(listAllProduct);
    }

    @Override
    public void printAllListFromFile() {
        List<Product> printListProductOrder = addProductToListFromFile();

        Collections.sort(printListProductOrder, (Product t, Product t1) -> {
            if (t.getQuantity() == t1.getQuantity()) {
                return Double.compare(t.getUnitPrice(), t1.getUnitPrice());
            }
            return Integer.compare(t1.getQuantity(), t.getQuantity());
        });
        for (Product product : printListProductOrder) {
            System.out.println(product.toString());
        }
    }

    public int saveToFileAfterIndex() {
        int index = -1;
        try {
            FileReader file = new FileReader(FILE_PRODUCTS);
            BufferedReader br = new BufferedReader(file);
            String line;
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                for (int i = 0; i < listProduct.size(); i++) {
                    if (listProduct.get(i).toString().equals(line)) {
                        index = i + 1;
                    }
                }
            }
            br.close();
            file.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return index;
    }

    @Override
    public void saveProductsToFile() {
        try {
            FileWriter fw = new FileWriter(FILE_PRODUCTS, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            for (int i = 0; i < listProduct.size(); i++) {
                if (i >= saveToFileAfterIndex()) {
                    pw.println(listProduct.get(i).toString());
                }
            }
            pw.flush();
            pw.close();
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
