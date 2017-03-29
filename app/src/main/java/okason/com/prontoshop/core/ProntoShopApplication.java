package okason.com.prontoshop.core;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.squareup.otto.Bus;

import okason.com.prontoshop.common.AddInitialDataService;
import okason.com.prontoshop.core.dagger.AppComponent;
import okason.com.prontoshop.core.dagger.AppModule;
import okason.com.prontoshop.core.dagger.DaggerAppComponent;
import okason.com.prontoshop.util.Constants;

/**
 * Created by deni on 28/03/2017.
 */

public class ProntoShopApplication extends Application {

    private static AppComponent appComponent;
    private static ProntoShopApplication instance = new ProntoShopApplication();

    private SharedPreferences sharedPreferences;

    private Bus bus;
    public Bus getBus()
    {
        return bus;
    }


    public static ProntoShopApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance.bus = new Bus();
        initDefaultProducts();
        getAppComponent();

    }

    private void initDefaultProducts() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean(Constants.FIRST_RUN, true)){
            startService(new Intent(this, AddInitialDataService.class));
            editor.putBoolean(Constants.FIRST_RUN, false).commit();
        }

    }

    public AppComponent getAppComponent() {
        if (appComponent == null){
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return appComponent;
    }
}
