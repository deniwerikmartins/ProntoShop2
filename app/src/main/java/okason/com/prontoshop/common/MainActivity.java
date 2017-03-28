package okason.com.prontoshop.common;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okason.com.prontoshop.R;
import okason.com.prontoshop.core.ProntoShopApplication;
import okason.com.prontoshop.core.events.CustomerSelectedEvent;
import okason.com.prontoshop.core.events.UpdateToolbarEvent;
import okason.com.prontoshop.data.DatabaseHelper;
import okason.com.prontoshop.model.LineItem;
import okason.com.prontoshop.util.Formatter;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewPager) ViewPager mViewPager;
    @BindView(R.id.tabs) TabLayout mTabLayout;
    @BindView(R.id.text_view_number_of_items) TextView mQtyTextView;
    @BindView(R.id.text_view_total_amount) TextView mTotalTextView;
    @BindView(R.id.text_view_customer_name) TextView mNameTextView;

    private Bus mBus;
    @Inject
    ShoppingCart mCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        setupViewPager();
        mBus = ProntoShopApplication.getInstance().getBus();

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();



    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }


   /* @Override
    protected void onPause() {
        super.onPause();
        ProntoShopApplication.getInstance().getAppComponent().inject(this);
        mCart.saveCartToPreference();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            ProntoShopApplication.getInstance().getAppComponent().inject(this);
            mCart.saveCartToPreference();
            mBus.unregister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    @Subscribe
    public void onUpdateToolbar(UpdateToolbarEvent event){
        populateToolbar(event.getLineItems());
    }

    @Subscribe
    public void onCustomerSelected(CustomerSelectedEvent event){
        if (event.isClearCustomer()) {
            mNameTextView.setText(getString(R.string.hint_customer_name));
        } else {
            mNameTextView.setText(event.getSelectedCustomer().getCustomerName());
        }
    }


    private void populateToolbar(List<LineItem> listOfItemsInShoppingCart) {
        double totalAmount = 0;
        int numberOfItems = 0;
        if (listOfItemsInShoppingCart != null && listOfItemsInShoppingCart.size() > 0) {
            for (LineItem item: listOfItemsInShoppingCart){
                totalAmount += item.getSumPrice();
                numberOfItems += item.getQauntity();
            }
            mTotalTextView.setText(Formatter.formatCurrency(totalAmount));

            if (numberOfItems > 1){
                mQtyTextView.setText(numberOfItems + " items" );
            }else {
                mQtyTextView.setText(numberOfItems + " item" );
            }
        } else {
            mTotalTextView.setText(Formatter.formatCurrency(0.00));
            mQtyTextView.setText(0 + " item" );

        }

    }

}
