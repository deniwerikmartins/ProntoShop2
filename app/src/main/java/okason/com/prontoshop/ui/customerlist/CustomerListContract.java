package okason.com.prontoshop.ui.customerlist;

import okason.com.prontoshop.core.listeners.OnDatabaseOperationCompleteListener;
import okason.com.prontoshop.model.Customer;

import java.util.List;

/**
 * Created by Valentine on 5/2/2016.
 */
public interface CustomerListContract {
    public interface View{
        void showCustomers(List<Customer> customers);
        void showAddCustomerForm();
        void showDeleteCustomerPrompt(Customer customer);
        void showEditCustomerForm(Customer customer);
        void showEmptyText();
        void hideEmptyText();
        void showMessage(String message);

    }

    public interface Actions{
        void loadCustomer();
        Customer getCustomer(long id);
        void onCustomerSelected(Customer customer);
        void onAddCustomerButtonClicked();
        void addCustomer(Customer customer);
        void onDeleteCustomerButtonClicked(Customer customer);
        void deleteCustomer(Customer customer);
        void onEditCustomerButtonClicked(Customer customer);
        void updateCustomer(Customer customer);

    }

    public interface Repository{
        List<Customer> getAllCustomers();
        Customer getCustomerById (long id);
        void onDeleteCustomer(Customer customer, OnDatabaseOperationCompleteListener listener);
        void addCustomer(Customer customer, OnDatabaseOperationCompleteListener listener);
        void updatedCustomer(Customer customer, OnDatabaseOperationCompleteListener listener);
    }
}
