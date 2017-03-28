package okason.com.prontoshop.core.dagger;

import android.content.Context;

import okason.com.prontoshop.core.ProntoShopApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Valentine on 4/22/2016.
 */
@Module
public class AppModule {

    private final ProntoShopApplication app;

    public AppModule(ProntoShopApplication app) {
        this.app = app;
    }

    @Provides @Singleton
    public ProntoShopApplication provideApp(){
        return app;
    }

    @Provides @Singleton
    public Context provideContext(){
        return app;
    }
}
