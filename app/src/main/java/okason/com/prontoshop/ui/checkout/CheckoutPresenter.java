package okason.com.prontoshop.ui.checkout;

import okason.com.prontoshop.common.ShoppingCart;
import okason.com.prontoshop.core.listeners.OnDatabaseOperationCompleteListener;
import okason.com.prontoshop.model.LineItem;
import okason.com.prontoshop.model.SalesTransaction;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Valentine on 5/3/2016.
 */
public class CheckoutPresenter implements CheckoutContract.Actions, OnDatabaseOperationCompleteListener {
    private final CheckoutContract.View mView;
    @Inject ShoppingCart mCart;
    @Inject CheckoutContract.Repository mRepository;

    private double subTotal = 0.0;
    private double total;
    private double tax;
    private double taxRate = 0.08;
    private String selectedPaymentType = "";
    private boolean paid = false;


    public CheckoutPresenter(CheckoutContract.View view) {
        mView = view;
    }


    @Override
    public void loadLineItems() {
        List<LineItem> availableLineItems = mCart.getShoppingCart();
        calculateTotals(availableLineItems);
        if (availableLineItems != null && availableLineItems.size() > 0){
            mView.hideText();
            mView.showLineItem(availableLineItems);
        } else {
            mView.showEmptyText();
        }
    }

    private void calculateTotals(List<LineItem> availableLineItems) {
        subTotal = 0.0;
        total = 0.0;
        tax = 0.0;

        for (LineItem item: availableLineItems){
            subTotal += item.getSumPrice();
        }

        tax = subTotal * taxRate;
        total = tax + subTotal;
        mView.showCartTotals(tax, subTotal, total);
    }

    @Override
    public void onCheckoutButtonClicked() {
        mView.showConfirmCheckout();
    }

    @Override
    public void onDeleteItemButtonClicked(LineItem item) {
        mCart.removeItemFromCart(item);
        loadLineItems();
    }

    @Override
    public void checkout() {
        //Ensure a customer is selected
        if (mCart.getShoppingCart() == null || mCart.getShoppingCart().size() == 0){
            mView.showMessage("Cart is empty");
            return;
        }

        if (mCart.getSelectedCustomer() == null || mCart.getSelectedCustomer().getId() == 0){
            mView.showMessage("No Customer is selected");
            return;
        }

        SalesTransaction transaction = new SalesTransaction();
        transaction.setCustomerId(mCart.getSelectedCustomer().getId());
        transaction.setLineItems(mCart.getShoppingCart());
        transaction.setTaxAmount(tax);
        transaction.setSubTotalAmount(subTotal);
        transaction.setTotalAmount(total);
        transaction.setPaymentType(selectedPaymentType);
        transaction.setPaid(paid);
        mRepository.saveTransaction(transaction, this);

        mCart.clearAllItemsFromCart();
        loadLineItems();
    }

    @Override
    public void onClearButtonClicked() {
        mView.showConfirmClearCart();
    }

    @Override
    public void clearShoppingCart() {
        mCart.clearAllItemsFromCart();
        loadLineItems();
    }

    @Override
    public void setPaymentType(String paymentType) {
        selectedPaymentType = paymentType;
    }

    @Override
    public void markAsPaid(boolean paid) {
        this.paid = paid;
    }

    @Override
    public void onItemQuantityChanged(LineItem item, int qty) {
        mCart.updateItemQty(item, qty);
    }

    @Override
    public void onDatabaseOperationFailed(String error) {
        mView.showMessage("Error Message: " + error);
    }

    @Override
    public void onDatabaseOperationSucceded(String message) {
        mView.showMessage(message);
    }
}
