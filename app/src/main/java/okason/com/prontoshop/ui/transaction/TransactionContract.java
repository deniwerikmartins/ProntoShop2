package okason.com.prontoshop.ui.transaction;

import okason.com.prontoshop.core.listeners.OnDatabaseOperationCompleteListener;
import okason.com.prontoshop.model.Customer;
import okason.com.prontoshop.model.SalesTransaction;

import java.util.List;

/**
 * Created by Valentine on 5/3/2016.
 */
public interface TransactionContract {
    public interface View{
        void showTransaction(List<SalesTransaction> transactions);
        void showEmptyText();
        void hideEmptyText();
        void showConfirmDeletePrompty(SalesTransaction transaction);
        void showMessage(String message);

    }

    public interface  Actions{
        void loadTransactions();
        void onDeleteItemButtonClicked(SalesTransaction transaction);
        void editTransaction(SalesTransaction transaction);
        void deleteTransaction(SalesTransaction transaction);
        Customer getCustomerById(long id);
    }


    public interface  Repository{
        List<SalesTransaction> getAllTransactions();
        void updateTransaction(SalesTransaction transaction, OnDatabaseOperationCompleteListener listener);
        SalesTransaction getTransactionById(long id);
        void deleteTransaction(long id, OnDatabaseOperationCompleteListener listener);
    }
}
