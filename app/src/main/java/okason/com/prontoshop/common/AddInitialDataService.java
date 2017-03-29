package okason.com.prontoshop.common;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import okason.com.prontoshop.core.listeners.OnDatabaseOperationCompleteListener;
import okason.com.prontoshop.data.SampleCustomerData;
import okason.com.prontoshop.data.SampleProductData;
import okason.com.prontoshop.model.Customer;
import okason.com.prontoshop.model.Product;
import okason.com.prontoshop.ui.customerlist.CustomerListSQLiteManager;
import okason.com.prontoshop.ui.productlist.ProductListSQLiteManager;

/**
 * Created by deni on 29/03/2017.
 */

public class AddInitialDataService extends IntentService{

    public AddInitialDataService(){
        super("AddInitialDataService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        //add sample Customers to database
        List<Customer> customers = SampleCustomerData.getCustomers();
        CustomerListSQLiteManager customerListSQLiteManager = new CustomerListSQLiteManager(getApplicationContext());
        for (Customer customer :
                customers) {
            customerListSQLiteManager.addCustomer(customer, new OnDatabaseOperationCompleteListener() {
                @Override
                public void onDatabaseOperationFailed(String error) {
                    Log.d("Customer", "Error: " + error);
                }

                @Override
                public void onDatabaseOperationSucceded(String message) {
                    Log.d("Customer", "Customer Inserted" + message);
                }
            });
        }

        //Add initial Products
        List<Product> products = SampleProductData.getSampleProducts();
        ProductListSQLiteManager productListSQLiteManager = new ProductListSQLiteManager(getApplicationContext());
        for (Product product :
                products) {
            productListSQLiteManager.addProduct(product, new OnDatabaseOperationCompleteListener() {
                @Override
                public void onDatabaseOperationFailed(String error) {
                    Log.d("First Run", "Error:" + error);
                }

                @Override
                public void onDatabaseOperationSucceded(String message) {
                    Log.d("First Run", "Sucess" + message);
                }
            });
        }

        //add sample Categories
        List<String> categories = new ArrayList<>();
        categories.add("Eletronics");
        categories.add("Computers");
        categories.add("Toys");
        categories.add("Garden");
        categories.add("Kitchen");
        categories.add("Clothing");
        categories.add("Health");

        for (String category :
                categories) {
            productListSQLiteManager.createOrGetCategoryId(category, new OnDatabaseOperationCompleteListener() {
                @Override
                public void onDatabaseOperationFailed(String error) {

                }

                @Override
                public void onDatabaseOperationSucceded(String message) {

                }
            });
        }

        Intent restartIntent = new Intent(this,MainActivity.class);
        restartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(restartIntent);
        

    }
}
