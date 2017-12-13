package test.dmisb.apps65.root;

import test.dmisb.apps65.core.BasePresenter;
import test.dmisb.apps65.di.DaggerService;
import test.dmisb.apps65.di.Scopes;
import test.dmisb.apps65.di.components.RootComponent;
import test.dmisb.apps65.screen.speciality.SpecialityFragment;

public class RootPresenter extends BasePresenter<RootActivity> {

    private boolean started = false;

    @Override
    protected void initComponent() {
        RootComponent component = DaggerService.getComponent(Scopes.ROOT_SCOPE);
        if (component != null)
            component.inject(this);
    }

    @Override
    protected void onAttachView() {
        if (!started) {
            router.newRootScreen(Scopes.SPECIALITY_SCOPE, new SpecialityFragment());
            started = true;
        }
    }
}
