package okason.com.prontoshop.core;

import android.app.Application;

import com.squareup.otto.Bus;

import okason.com.prontoshop.core.dagger.AppComponent;
import okason.com.prontoshop.core.dagger.AppModule;
import okason.com.prontoshop.core.dagger.DaggerAppComponent;

/**
 * Created by deni on 28/03/2017.
 */

public class ProntoShopApplication extends Application {

    private static AppComponent appComponent;
    private static ProntoShopApplication instance = new ProntoShopApplication();

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
        getAppComponent();
        instance.bus = new Bus();
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
