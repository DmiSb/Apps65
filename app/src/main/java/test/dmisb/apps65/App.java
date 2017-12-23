package test.dmisb.apps65;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import ru.terrakok.cicerone.Cicerone;
import test.dmisb.apps65.di.Scopes;
import test.dmisb.apps65.di.AppModule;
import test.dmisb.apps65.di.DataModule;
import test.dmisb.apps65.utils.FormatUtils;
import toothpick.Toothpick;
import toothpick.configuration.Configuration;


public class App extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();

        initToothpick();
        initScopes();

        FormatUtils.initFormats();
        initStetho();
    }

    public static Context getAppContext() {
        return appContext;
    }

    private void initToothpick() {
        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes());
        } else {
            Toothpick.setConfiguration(Configuration.forProduction().disableReflection());
        }
    }

    private void initScopes() {
        Toothpick.openScope(Scopes.APP_SCOPE)
                .installModules(new AppModule(appContext));
        Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.DATA_SCOPE)
                .installModules(new DataModule(appContext));
    }

    private void initStetho() {
        Stetho.InitializerBuilder builder = Stetho.newInitializerBuilder(this);
        builder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this));
        builder.enableDumpapp(Stetho.defaultDumperPluginsProvider(this));
        Stetho.Initializer initializer = builder.build();
        Stetho.initialize(initializer);
    }
}
