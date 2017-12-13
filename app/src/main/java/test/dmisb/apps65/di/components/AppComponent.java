package test.dmisb.apps65.di.components;

import android.content.Context;

import test.dmisb.apps65.di.modules.AppModule;
import test.dmisb.apps65.di.modules.DataModule;

@dagger.Component(modules = {AppModule.class})
public interface AppComponent {
    Context getContext();

    DataComponent plus(DataModule module);
}
