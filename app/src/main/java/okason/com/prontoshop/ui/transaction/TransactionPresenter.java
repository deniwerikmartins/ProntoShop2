package okason.com.prontoshop.ui.transaction;

import okason.com.prontoshop.core.ProntoShopApplication;
import okason.com.prontoshop.core.listeners.OnDatabaseOperationCompleteListener;
import okason.com.prontoshop.model.Customer;
import okason.com.prontoshop.model.SalesTransaction;
import okason.com.prontoshop.ui.customerlist.CustomerListContract;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Valentine on 5/3/2016.
 */
public class TransactionPresenter implements TransactionContract.Actions, OnDatabaseOperationCompleteListener {
    private final TransactionContract.View mView;
    @Inject TransactionContract.Repository mRepository;
    @Inject CustomerListContract.Repository mCustomerRepository;

    public TransactionPresenter(TransactionContract.View view) {
        mView = view;
        ProntoShopApplication.getInstance().getAppComponent().inject(this);
    }

    @Override
    public void loadTransactions() {
        List<SalesTransaction> transactions = mRepository.getAllTransactions();
        if (transactions != null && transactions.size() > 0){
            mView.hideEmptyText();
            mView.showTransaction(transactions);
        } else {
            mView.showEmptyText();
        }
    }

    @Override
    public void onDeleteItemButtonClicked(SalesTransaction transaction) {
        mView.showConfirmDeletePrompty(transaction);
    }

    @Override
    public void editTransaction(SalesTransaction transaction) {
        mRepository.updateTransaction(transaction, this);
    }

    @Override
    public void deleteTransaction(SalesTransaction transaction) {
        mRepository.deleteTransaction(transaction.getId(), this);
    }

    @Override
    public Customer getCustomerById(long id) {
        return mCustomerRepository.getCustomerById(id);
    }

    @Override
    public void onDatabaseOperationFailed(String error) {
        mView.showMessage("Error: " + error);
    }

    @Override
    public void onDatabaseOperationSucceded(String message) {
        mView.showMessage(message);
    }
}
