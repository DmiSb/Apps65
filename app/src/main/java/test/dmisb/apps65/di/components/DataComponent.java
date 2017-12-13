package test.dmisb.apps65.di.components;

import javax.inject.Singleton;

import dagger.Subcomponent;
import test.dmisb.apps65.data.repo.Repository;
import test.dmisb.apps65.di.modules.DataModule;
import test.dmisb.apps65.di.modules.NetworkModule;
import test.dmisb.apps65.di.modules.RootModule;
import test.dmisb.apps65.di.modules.StorageModule;

@Subcomponent(modules = {NetworkModule.class, StorageModule.class, DataModule.class})
@Singleton
public interface DataComponent {
    void inject(Repository repository);

    RootComponent plus(RootModule module);
}
