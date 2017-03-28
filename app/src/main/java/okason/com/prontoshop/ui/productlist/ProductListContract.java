package okason.com.prontoshop.ui.productlist;

import okason.com.prontoshop.core.listeners.OnDatabaseOperationCompleteListener;
import okason.com.prontoshop.model.Category;
import okason.com.prontoshop.model.Product;

import java.util.List;

/**
 * Created by Valentine on 4/29/2016.
 */
public interface ProductListContract {

    public interface View {
        void showProducts(List<Product> products);

        void showAddProductForm();

        void showEditProductForm(Product product);

        void showDeleteProductPrompt(Product product);

        void showGoogleSearch(Product product);

        void showEmptyText();

        void hideEmptyText();

        void showMessage(String message);

    }

    public interface Actions {
        void loadProducts();

        void onAddProductButtonClicked();

        void onAddToCartButtonClicked(Product product);

        Product getProduct(long id);

        void addProduct(Product product);

        void onDeleteProductButtonClicked(Product product);

        void deleteProduct(Product product);

        void onEditProductButtonClicked(Product product);

        void updateProduct(Product product);

        void onGoogleSearchButtonClicked(Product product);


    }

    public interface Repository {
        List<Product> getAllProducts();
        Product getProductById(long id);
        void deleteProduct(Product product, OnDatabaseOperationCompleteListener listener);
        void addProduct(Product product, OnDatabaseOperationCompleteListener listener);
        void updateProduct(Product product, OnDatabaseOperationCompleteListener listener);
        List<Category> getAllCategories();
    }
}
