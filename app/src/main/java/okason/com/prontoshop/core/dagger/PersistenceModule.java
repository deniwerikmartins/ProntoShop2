package okason.com.prontoshop.core.dagger;

import android.content.Context;

import okason.com.prontoshop.ui.customerlist.CustomerListContract;
import okason.com.prontoshop.ui.customerlist.CustomerListInMemoryRepository;
import okason.com.prontoshop.ui.productlist.ProductInMemoryRepository;
import okason.com.prontoshop.ui.productlist.ProductListContract;
import okason.com.prontoshop.ui.transaction.TempRespository;
import okason.com.prontoshop.ui.transaction.TransactionContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Valentine on 5/3/2016.
 */
@Module
public class PersistenceModule {

    @Provides @Singleton
    public ProductListContract.Repository providesProductRepository(Context context){
        return new ProductInMemoryRepository();
    }

    @Provides @Singleton
    public CustomerListContract.Repository providesCustomerRepository(Context context){
        return new CustomerListInMemoryRepository();
    }

    @Provides
    public TransactionContract.Repository providesTransactionRespository(Context context){
        return new TempRespository();
    }


}
