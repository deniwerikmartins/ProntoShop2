package okason.com.prontoshop.core.dagger;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import okason.com.prontoshop.common.ShoppingCart;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Valentine on 5/2/2016.
 */

@Module
public class ShoppingCartModule {

    @Provides @Singleton
    SharedPreferences providesSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides @Singleton
    ShoppingCart providesShoppingCart(SharedPreferences preferences){
        return new ShoppingCart(preferences);
    }
}
