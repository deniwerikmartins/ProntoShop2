package okason.com.prontoshop.ui.productlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import okason.com.prontoshop.core.listeners.OnDatabaseOperationCompleteListener;
import okason.com.prontoshop.data.DatabaseHelper;
import okason.com.prontoshop.model.Category;
import okason.com.prontoshop.model.Product;
import okason.com.prontoshop.util.Constants;

/**
 * Created by deni on 28/03/2017.
 */

public class ProductListSQLiteManager implements ProductListContract.Repository{

    private DatabaseHelper dbHelper;
    private final Context mContext;
    private SQLiteDatabase database;


    public ProductListSQLiteManager(Context context) {
        mContext = context;
        dbHelper = DatabaseHelper.newInstance(context);
        database = dbHelper.getWritableDatabase();
    }

    @Override
    public List<Product> getAllProducts() {
        //initialize an empty list of products
        List<Product> products = new ArrayList<>();

        //SQL command to select all Products
        String selectQuery = "SELECT * FROM " + Constants.PRODUCT_TABLE;

        //make sure the database is not null
        if (database != null){
            //get cursor fom all the produts in the database
            Cursor cursor = database.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()){
                while(!cursor.isAfterLast()){
                    //get each product in the Cursor
                    products.add(Product.getProductFromCursor(cursor)) ;
                    cursor.moveToNext();
                }
            }
        }
        return products;
    }

    @Override
    public Product getProductById(long id) {
        //get cursor representing the Product
        Cursor cursor = database.rawQuery("SELECT * FROM " + Constants.PRODUCT_TABLE + " WHERE " + Constants.COLUMN_ID + " = '" + id + "'", null);

        Product product;

        if (cursor.moveToFirst()){
            product = Product.getProductFromCursor(cursor);
        }else {
            product = null;
        }
        return product;
    }

    @Override
    public void deleteProduct(Product product, OnDatabaseOperationCompleteListener listener) {
        if (database != null){
            int result = database.delete(Constants.PRODUCT_TABLE, Constants.COLUMN_ID + " = " + product.getId(), null);
            if (result > 0){
                listener.onDatabaseOperationSucceded("Product Deleted");
            } else {
                listener.onDatabaseOperationFailed("Unable to delete Product");
            }

        }
    }

    @Override
    public void addProduct(Product product, OnDatabaseOperationCompleteListener listener) {
        //prepare the information tht will be saved to the database
        ContentValues values = new ContentValues();
        values.put(Constants.COLUMN_NAME, product.getProductName());
        values.put(Constants.COLUMN_DESCRIPTION, product.getDescription());
        values.put(Constants.COLUMN_PRICE, product.getSalePrice());
        values.put(Constants.COLUMN_PURCHASE_PRICE, product.getPurchasePrice());
        values.put(Constants.COLUMN_IMAGE_PATH, product.getImagePath());
        values.put(Constants.COLUMN_CATEGORY_NAME, product.getCategoryName());
        values.put(Constants.COLUMN_DATE_CREATED, System.currentTimeMillis());
        values.put(Constants.COLUMN_LAST_UPDATED, System.currentTimeMillis());
        values.put(Constants.COLUMN_CATEGORY_ID, createOrGetCategoryId(product.getCategoryName(), listener));

        try {
            database.insertOrThrow(Constants.PRODUCT_TABLE, null, values);
            listener.onDatabaseOperationSucceded("Product Added");
        } catch (SQLException e) {
            listener.onDatabaseOperationFailed(e.getMessage());
        }
    }

    public long createOrGetCategoryId(String categoryName, OnDatabaseOperationCompleteListener listener) {
        Category foundCategory = getCategory(categoryName);
        if (foundCategory == null){
            foundCategory = addCategory(categoryName, listener);
        }
        return foundCategory.getId();
    }

    public Category addCategory(final String categoryName, OnDatabaseOperationCompleteListener listener){
        Category category = new Category();
        category.setCategoryName(categoryName);
        saveCategory(category, listener);
        return category;
    }

    public void saveCategory(Category category, OnDatabaseOperationCompleteListener listener) {
        ContentValues values = new ContentValues();
        values.put(Constants.COLUMN_NAME, category.getCategoryName());
        try {
            database.insertOrThrow(Constants.CATEGORY_TABLE, null, values);
        } catch (SQLException e){
            listener.onDatabaseOperationFailed("Unable to save Category");
        }
    }

    public Category getCategory(String categoryName) {
        Category category = null;
        Cursor cursor = database.rawQuery("SELECT * FROM " + Constants.CATEGORY_TABLE + " " + "WHERE " + Constants.COLUMN_NAME + " = '" + categoryName + "'", null);
        if (cursor.moveToFirst()){
            category = Category.fromCursor(cursor);
        }
        return category;
    }

    @Override
    public void updateProduct(Product product, OnDatabaseOperationCompleteListener listener) {
        //prepare the information tht will be saved to the database
        ContentValues values = new ContentValues();
        values.put(Constants.COLUMN_NAME, product.getProductName());
        values.put(Constants.COLUMN_DESCRIPTION, product.getDescription());
        values.put(Constants.COLUMN_PRICE, product.getSalePrice());
        values.put(Constants.COLUMN_PURCHASE_PRICE, product.getPurchasePrice());
        values.put(Constants.COLUMN_IMAGE_PATH, product.getImagePath());
        values.put(Constants.COLUMN_CATEGORY_NAME, product.getCategoryName());
        values.put(Constants.COLUMN_LAST_UPDATED, System.currentTimeMillis());

        int result = database.update(Constants.PRODUCT_TABLE, values, Constants.COLUMN_ID + " = " + product.getId(), null);
        if (result == 1){
            listener.onDatabaseOperationSucceded("Product Updated");
        } else {
            listener.onDatabaseOperationFailed("Product update failed");
        }
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();

        //Command to select all Categories
        String selectQuery = "SELECT * FROM " + Constants.CATEGORY_TABLE;

        //get a cursor for all categories in the database
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor != null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                categories.add(Category.fromCursor(cursor));
                cursor.moveToNext();
            }
            cursor.close();

        }
        return categories;
    }
}
