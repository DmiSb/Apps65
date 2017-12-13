package test.dmisb.apps65.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import test.dmisb.apps65.data.repo.Repository;

@Module
public class DataModule {
    @Provides
    @Singleton
    Repository provideRepository() {
        return new Repository();
    }
}
