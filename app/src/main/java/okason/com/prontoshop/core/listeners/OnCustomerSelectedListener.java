package okason.com.prontoshop.core.listeners;

import okason.com.prontoshop.model.Customer;

/**
 * Created by Valentine on 4/27/2016.
 */
public interface OnCustomerSelectedListener {
    void onSelectCustomer(Customer customer);
    void onLongClickCustomer(Customer customer);
}
