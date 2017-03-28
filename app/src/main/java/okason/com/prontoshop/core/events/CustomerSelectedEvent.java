package okason.com.prontoshop.core.events;

import okason.com.prontoshop.model.Customer;

/**
 * Created by Valentine on 5/2/2016.
 */
public class CustomerSelectedEvent {
    private final Customer selectedCustomer;
    private final boolean clearCustomer;

    public CustomerSelectedEvent(Customer selectedCustomer, boolean clearCustomer) {
        this.selectedCustomer = selectedCustomer;
        this.clearCustomer = clearCustomer;
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public boolean isClearCustomer() {
        return clearCustomer;
    }
}
