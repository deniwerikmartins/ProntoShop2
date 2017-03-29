package okason.com.prontoshop.ui.transaction;

import okason.com.prontoshop.core.listeners.OnDatabaseOperationCompleteListener;
import okason.com.prontoshop.model.LineItem;
import okason.com.prontoshop.model.SalesTransaction;

import java.util.List;

/**
 * Created by Valentine on 5/3/2016.
 */
public class TempRespository implements TransactionContract.Repository {
    @Override
    public List<SalesTransaction> getAllTransactions() {
        return null;
    }

    @Override
    public void updateTransaction(SalesTransaction transaction, OnDatabaseOperationCompleteListener listener) {

    }

    @Override
    public SalesTransaction getTransactionById(long id) {
        return null;
    }

    @Override
    public void deleteTransaction(long id, OnDatabaseOperationCompleteListener listener) {

    }

    @Override
    public List<LineItem> getAllLineItems() {
        return null;
    }

    @Override
    public void saveTransaction(SalesTransaction transaction, OnDatabaseOperationCompleteListener listener) {

    }
}
