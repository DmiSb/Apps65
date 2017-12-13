package test.dmisb.apps65.di.modules;

import android.content.Context;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.requery.Persistable;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.meta.EntityModel;
import io.requery.reactivex.ReactiveEntityStore;
import io.requery.reactivex.ReactiveSupport;
import io.requery.sql.Configuration;
import io.requery.sql.EntityDataStore;
import io.requery.sql.TableCreationMode;
import test.dmisb.apps65.data.storage.model.Models;
import test.dmisb.apps65.data.storage.StorageManager;
import test.dmisb.apps65.utils.AppConfig;

@Module
public class StorageModule {
    private static final int DB_VERSION = 1;

    @Provides
    @Singleton
    ReactiveEntityStore<Persistable> provideStorage(Context context) {
        return createStorage(context);
    }

    @Provides
    @Singleton
    StorageManager provideStorageManager(ReactiveEntityStore<Persistable> provideStorage) {
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
}
