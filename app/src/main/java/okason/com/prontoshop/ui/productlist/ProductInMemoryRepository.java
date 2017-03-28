package okason.com.prontoshop.ui.productlist;

import okason.com.prontoshop.core.listeners.OnDatabaseOperationCompleteListener;
import okason.com.prontoshop.data.SampleProductData;
import okason.com.prontoshop.model.Category;
import okason.com.prontoshop.model.Product;

import java.util.List;

/**
 * Created by Valentine on 5/3/2016.
 */
public class ProductInMemoryRepository implements ProductListContract.Repository{

    public ProductInMemoryRepository(){}

    @Override
    public List<Product> getAllProducts() {
        return SampleProductData.getSampleProducts();
    }

    @Override
    public Product getProductById(long id) {
        return null;
    }

    @Override
    public void deleteProduct(Product product, OnDatabaseOperationCompleteListener listener) {

    }

    @Override
    public void addProduct(Product product, OnDatabaseOperationCompleteListener listener) {

    }

    @Override
    public void updateProduct(Product product, OnDatabaseOperationCompleteListener listener) {

    }

    @Override
    public List<Category> getAllCategories() {
        return null;
    }
}
