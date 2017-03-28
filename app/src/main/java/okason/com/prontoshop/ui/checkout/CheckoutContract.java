package okason.com.prontoshop.ui.checkout;

import okason.com.prontoshop.core.listeners.OnDatabaseOperationCompleteListener;
import okason.com.prontoshop.model.LineItem;
import okason.com.prontoshop.model.SalesTransaction;

import java.util.List;

/**
 * Created by Valentine on 5/3/2016.
 */
public interface CheckoutContract {
    public interface View{
        void showLineItem(List<LineItem> items);
        void showEmptyText();
        void showCartTotals(double tax, double subTotal, double total);
        void showConfirmCheckout();
        void showConfirmClearCart();
        void hideText();
        void showMessage(String message);

    }

    public interface Actions{
        void loadLineItems();
        void onCheckoutButtonClicked();
        void onDeleteItemButtonClicked(LineItem item);
        void checkout();
        void onClearButtonClicked();
        void clearShoppingCart();
        void setPaymentType(String paymentType);
        void markAsPaid(boolean paid);
        void onItemQuantityChanged(LineItem item, int qty);

    }

    public interface Repository{
        List<LineItem> getAllLineItems();
        void saveTransaction(SalesTransaction transaction, OnDatabaseOperationCompleteListener listener);
        void updateTransaction(SalesTransaction transaction, OnDatabaseOperationCompleteListener listener);
    }
}
