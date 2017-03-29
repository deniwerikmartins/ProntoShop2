package okason.com.prontoshop.ui.transaction;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okason.com.prontoshop.common.ShoppingCart;
import okason.com.prontoshop.core.ProntoShopApplication;
import okason.com.prontoshop.core.listeners.OnDatabaseOperationCompleteListener;
import okason.com.prontoshop.data.DatabaseHelper;
import okason.com.prontoshop.model.LineItem;
import okason.com.prontoshop.model.SalesTransaction;
import okason.com.prontoshop.util.Constants;

/**
 * Created by deni on 29/03/2017.
 */

public class TransactionSQLiteManager implements TransactionContract.Repository {
    private DatabaseHelper dbHelper;
    private final Context mContext;
    private SQLiteDatabase database;
    private boolean DEBUG = false;
    private final static String LOG_TAG = TransactionSQLiteManager.class.getSimpleName();

    @Inject
    ShoppingCart mCart;

    public TransactionSQLiteManager(Context context) {
        mContext = context;
        dbHelper = DatabaseHelper.newInstance(context);
        database = dbHelper.getWritableDatabase();
        ProntoShopApplication.getInstance().getAppComponent().inject(this);
    }

    @Override
    public List<SalesTransaction> getAllTransactions() {
        //initializa an empty list of transactions
        List<SalesTransaction> salesTransactions = new ArrayList<>();

        //sql command to selecet all transactions
        String selectQuery = "SELECT * FROM " + Constants.TRANSACTION_TABLE;

        //make sure the database is not null
        if (database != null) {
            //get cursor from all the customer in the database
            Cursor cursor = database.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    //get each customer in the cursor;
                    salesTransactions.add(SalesTransaction.getTransactionFromCursor(cursor));
                    cursor.moveToNext();
                }
            }
        }
        return salesTransactions;
    }

    @Override
    public SalesTransaction getTransactionById(long id) {
        //get the cursor representanting the Transaction
        Cursor cursor = database.rawQuery("SELECT * FROM " + Constants.TRANSACTION_TABLE + " WHERE " + Constants.COLUMN_ID + " = '" + id + "'", null);

        SalesTransaction salesTransaction;
        if (cursor.moveToFirst()) {
            salesTransaction = SalesTransaction.getTransactionFromCursor(cursor);
        } else {
            salesTransaction = null;
        }
        return salesTransaction;
    }

    @Override
    public void deleteTransaction(long id, OnDatabaseOperationCompleteListener listener) {
        if (database != null) {
            int result = database.delete(Constants.TRANSACTION_TABLE, Constants.COLUMN_ID + " = " + id, null);
            if (result > 0) {
                listener.onDatabaseOperationSucceded("Transaction deleted");
            } else {
                listener.onDatabaseOperationFailed("Unable to delete Transaction");
            }
        }
    }

    @Override
    public List<LineItem> getAllLineItems() {
        return mCart.getShoppingCart();
    }

    @Override
    public void saveTransaction(SalesTransaction transaction, OnDatabaseOperationCompleteListener listener) {
        if (database != null) {
            ContentValues values = new ContentValues();
            values.put(Constants.COLUMN_CUSTOMER_ID, transaction.getCustomerId());
            values.put(Constants.COLUMN_DATE_CREATED, System.currentTimeMillis());
            values.put(Constants.COLUMN_SUB_TOTAL_AMOUNT, transaction.getSubTotalAmount());
            values.put(Constants.COLUMN_LINE_ITEMS, transaction.getJsonLineItems());
            values.put(Constants.COLUMN_TAX_AMOUNT, transaction.getTaxAmount());
            values.put(Constants.COLUMN_PAYMENT_STATUS, convertBooleanToInt(transaction.isPaid()));
            values.put(Constants.COLUMN_PAYMENT_TYPE, transaction.getPaymentType());
            values.put(Constants.COLUMN_SUB_TOTAL_AMOUNT, transaction.getTotalAmount());
            values.put(Constants.COLUMN_LAST_UPDATED, System.currentTimeMillis());

            try {
                database.insertOrThrow(Constants.TRANSACTION_TABLE, null, values);
                listener.onDatabaseOperationSucceded("Transaction saved");
                if (DEBUG){
                    Log.d(LOG_TAG, "Transaction saved");
                }
            } catch (SQLException e) {
                listener.onDatabaseOperationFailed(e.getMessage());
            }
        }
    }

    @Override
    public void updateTransaction(SalesTransaction transaction, OnDatabaseOperationCompleteListener listener) {
        if (database != null) {
            ContentValues values = new ContentValues();
            values.put(Constants.COLUMN_CUSTOMER_ID, transaction.getCustomerId());
            values.put(Constants.COLUMN_SUB_TOTAL_AMOUNT, transaction.getSubTotalAmount());
            values.put(Constants.COLUMN_LINE_ITEMS, transaction.getJsonLineItems());
            values.put(Constants.COLUMN_TAX_AMOUNT, transaction.getTaxAmount());
            values.put(Constants.COLUMN_PAYMENT_STATUS, convertBooleanToInt(transaction.isPaid()));
            values.put(Constants.COLUMN_PAYMENT_TYPE, transaction.getPaymentType());
            values.put(Constants.COLUMN_SUB_TOTAL_AMOUNT, transaction.getTotalAmount());
            values.put(Constants.COLUMN_LAST_UPDATED, System.currentTimeMillis());

            int result = database.update(Constants.TRANSACTION_TABLE, values, Constants.COLUMN_ID + " = " + transaction.getId(), null);
            if (result == 1){
                listener.onDatabaseOperationSucceded("Transaction Updated");
            } else {
                listener.onDatabaseOperationFailed("Transaction update failed");
            }
        }
    }

    public int convertBooleanToInt(Boolean boolValue) {
        if (boolValue){
            return 1;
        } else {
            return 0;
        }
    }
}
