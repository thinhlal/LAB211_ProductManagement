
package model;

import java.util.List;

public interface IProduct {
    void createProduct();
    boolean checkExistProduct(String id);
    List searchProductByName(String name);
    boolean updateProduct(String id);
    void deleteProduct();
    void printAllListFromFile();
    void saveProductsToFile();
}
