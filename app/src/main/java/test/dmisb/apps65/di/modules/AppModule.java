package test.dmisb.apps65.di.modules;

import android.content.Context;

import dagger.Provides;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

@dagger.Module
public class AppModule {
    private final Context context;
    private final Cicerone cicerone;

    public AppModule(Context context, Cicerone cicerone) {
        this.context = context;
        this.cicerone = cicerone;
    }

    @Provides
    public Context provideContext() {
        return context;
    }

    @Provides
    public Router provideRouter() {
        return (Router) cicerone.getRouter();
    }

    @Provides
    public NavigatorHolder provideNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }
}
