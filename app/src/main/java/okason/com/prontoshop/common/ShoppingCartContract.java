package okason.com.prontoshop.common;

import okason.com.prontoshop.model.Customer;
import okason.com.prontoshop.model.LineItem;

import java.util.List;

/**
 * Created by Valentine on 5/2/2016.
 */
public interface ShoppingCartContract {
    void addItemToCart(LineItem item);
    void removeItemFromCart(LineItem item);
    void clearAllItemsFromCart();
    List<LineItem> getShoppingCart();
    void setCustomer(Customer customer);
    void updateItemQty(LineItem item, int qty);
    Customer getSelectedCustomer();
    void completeCheckout();
}
