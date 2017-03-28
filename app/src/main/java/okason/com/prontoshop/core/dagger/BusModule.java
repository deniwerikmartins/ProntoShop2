package okason.com.prontoshop.core.dagger;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Valentine on 5/2/2016.
 */
@Module
public class BusModule {

    @Provides @Singleton
    public Bus provideBus(){
        return new Bus();
    }
}
