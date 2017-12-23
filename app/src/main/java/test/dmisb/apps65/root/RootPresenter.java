package test.dmisb.apps65.root;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;
import test.dmisb.apps65.di.Scopes;
import toothpick.Scope;
import toothpick.Toothpick;

@InjectViewState
public class RootPresenter extends MvpPresenter<RootView> {

    @Inject
    Router router;

    RootPresenter() {
        Scope scope = Toothpick.openScope(Scopes.DATA_SCOPE);
        Toothpick.inject(this, scope);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        router.newRootScreen(Scopes.SPECIALITY_SCOPE);
    }
}
