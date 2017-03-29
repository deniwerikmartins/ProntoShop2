package okason.com.prontoshop.core.dagger;

import okason.com.prontoshop.common.MainActivity;
import okason.com.prontoshop.common.ShoppingCart;
import okason.com.prontoshop.ui.customerlist.CustomerPresenter;
import okason.com.prontoshop.ui.productlist.ProductPresenter;
import okason.com.prontoshop.ui.transaction.TransactionPresenter;

import javax.inject.Singleton;

import dagger.Component;
import okason.com.prontoshop.ui.transaction.TransactionSQLiteManager;

/**
 * Created by Valentine on 4/22/2016.
 */

@Singleton
@Component(
        modules = {
                AppModule.class,
                ShoppingCartModule.class,
                BusModule.class,
                PersistenceModule.class
        }
)
public interface AppComponent {
    void inject(MainActivity activity);
    void inject(ShoppingCart cart);
    void inject(ProductPresenter presenter);
    void inject(CustomerPresenter presenter);
    void inject(TransactionPresenter presenter);
    void inject(TransactionSQLiteManager manager);
}
