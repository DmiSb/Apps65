package test.dmisb.apps65;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import ru.terrakok.cicerone.Cicerone;
import test.dmisb.apps65.di.DaggerService;
import test.dmisb.apps65.di.Scopes;
import test.dmisb.apps65.di.components.AppComponent;
import test.dmisb.apps65.di.components.DaggerAppComponent;
import test.dmisb.apps65.di.components.DataComponent;
import test.dmisb.apps65.di.components.RootComponent;
import test.dmisb.apps65.di.modules.AppModule;
import test.dmisb.apps65.di.modules.DataModule;
import test.dmisb.apps65.di.modules.RootModule;
import test.dmisb.apps65.utils.FormatUtils;


public class App extends Application {

    private static Context appContext;
    private Cicerone cicerone;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        cicerone = Cicerone.create();

        initDaggerComponents();
        FormatUtils.initFormats();
        initStetho();
    }

    public static Context getAppContext() {
        return appContext;
    }

    private void initDaggerComponents() {
        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(appContext, cicerone))
                .build();
        DataComponent dataComponent = appComponent.plus(new DataModule());
        DaggerService.registerComponent(Scopes.DATA_SCOPE, dataComponent);

        RootComponent rootComponent = dataComponent.plus(new RootModule());
        DaggerService.registerComponent(Scopes.ROOT_SCOPE, rootComponent);
    }

    private void initStetho() {
        Stetho.InitializerBuilder builder = Stetho.newInitializerBuilder(this);
        builder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this));
        builder.enableDumpapp(Stetho.defaultDumperPluginsProvider(this));
        Stetho.Initializer initializer = builder.build();
        Stetho.initialize(initializer);
    }
}
