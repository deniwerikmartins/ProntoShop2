package okason.com.prontoshop.ui.customerlist;

import android.util.Log;

import okason.com.prontoshop.core.listeners.OnDatabaseOperationCompleteListener;
import okason.com.prontoshop.data.SampleCustomerData;
import okason.com.prontoshop.model.Customer;

import java.util.List;

/**
 * Created by Valentine on 5/3/2016.
 */
public class CustomerListInMemoryRepository implements CustomerListContract.Repository {
    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = SampleCustomerData.getCustomers();
        Log.d("Returned Customers :", "Customers " + customers.toString());
        return customers;

    }

    @Override
    public Customer getCustomerById(long id) {
        return null;
    }

    @Override
    public void onDeleteCustomer(Customer customer, OnDatabaseOperationCompleteListener listener) {

    }

    @Override
    public void addCustomer(Customer customer, OnDatabaseOperationCompleteListener listener) {

    }

    @Override
    public void updatedCustomer(Customer customer, OnDatabaseOperationCompleteListener listener) {

    }
}
