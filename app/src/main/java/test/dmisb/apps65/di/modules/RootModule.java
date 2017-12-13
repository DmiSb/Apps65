package test.dmisb.apps65.di.modules;

import dagger.Module;
import dagger.Provides;
import test.dmisb.apps65.di.DaggerScope;
import test.dmisb.apps65.root.RootActivity;
import test.dmisb.apps65.root.RootPresenter;

@Module
public class RootModule {
    @Provides
    @DaggerScope(RootActivity.class)
    RootPresenter provideRootPresenter() {
        return new RootPresenter();
    }
}
