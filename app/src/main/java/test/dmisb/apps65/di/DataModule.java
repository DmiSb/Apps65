package test.dmisb.apps65.di;

import android.content.Context;

import com.squareup.moshi.Moshi;

import java.util.concurrent.TimeUnit;

import io.requery.Persistable;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.meta.EntityModel;
import io.requery.reactivex.ReactiveEntityStore;
import io.requery.reactivex.ReactiveSupport;
import io.requery.sql.Configuration;
import io.requery.sql.EntityDataStore;
import io.requery.sql.TableCreationMode;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import test.dmisb.apps65.data.network.RestService;
import test.dmisb.apps65.data.network.adapter.DateAdapter;
import test.dmisb.apps65.data.repo.Repository;
import test.dmisb.apps65.data.storage.StorageManager;
import test.dmisb.apps65.data.storage.model.Models;
import test.dmisb.apps65.utils.AppConfig;
import toothpick.config.Module;

public class DataModule extends Module {

    public DataModule(Context context) {
        // Network
        RestService service = createService(RestService.class);
        // StorageManager
        StorageManager storage = createStorageManager(createStorage(context));
        // Repository
        bind(Repository.class).toInstance(new Repository(service, storage));
    }

    //region ================= Network =================

    private OkHttpClient createClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(AppConfig.HTTP_LOG_LEVEL))
                .connectTimeout(AppConfig.MAX_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(AppConfig.MAX_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(AppConfig.MAX_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();
    }

    private static Converter.Factory createConvertFactory() {
        return MoshiConverterFactory.create(new Moshi.Builder()
                .add(new DateAdapter())
                .build());
    }

    private static Retrofit createRetrofit(OkHttpClient okHttp) {
        return new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
                .addConverterFactory(createConvertFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttp)
                .build();
    }

    private <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = createRetrofit(createClient());
        return retrofit.create(serviceClass);
    }

    //endregion

    //region ================= Storage =================

    private static final int DB_VERSION = 1;

    private StorageManager createStorageManager(ReactiveEntityStore<Persistable> provideStorage) {
        return new StorageManager(provideStorage);
    }

    private ReactiveEntityStore<Persistable> createStorage(Context context) {
        EntityModel entityModel = Models.DEFAULT;
        DatabaseSource source = new DatabaseSource(context, entityModel, DB_VERSION);
        source.setTableCreationMode(TableCreationMode.DROP_CREATE);
        source.setLoggingEnabled(AppConfig.DB_LOGGING_ENABLED);
        Configuration configuration = source. getConfiguration();
        return ReactiveSupport.toReactiveStore(
                new EntityDataStore<Persistable>(configuration));
    }

    //endregion
}
