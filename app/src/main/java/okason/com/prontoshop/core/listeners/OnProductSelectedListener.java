package okason.com.prontoshop.core.listeners;

import okason.com.prontoshop.model.Product;

/**
 * Created by Valentine on 4/26/2016.
 */
public interface OnProductSelectedListener {
    void onSelectedProduct(Product selectedProduct);
    void onLongClickProduct(Product clickedProduct);
}
