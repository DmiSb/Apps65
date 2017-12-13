package test.dmisb.apps65.screen.speciality;

import javax.inject.Inject;

import test.dmisb.apps65.core.BasePresenter;
import test.dmisb.apps65.di.DaggerService;
import test.dmisb.apps65.di.Scopes;
import test.dmisb.apps65.root.RootPresenter;

public class SpecialityPresenter extends BasePresenter<SpecialityFragment> {

    @Inject
    RootPresenter rootPresenter;

    @Override
    protected void initComponent() {
        SpecialityFragment.Component component = DaggerService.getComponent(Scopes.SPECIALITY_SCOPE);
        if (component != null) {
            component.inject(this);
        }
    }

    @Override
    protected void onAttachView() {
        repository.loadUsers()
                .subscribe(
                        speciality -> {
                            if (getView() != null) {
                                getView().addSpeciality(speciality);
                            }
                        },
                        throwable -> {} //getView().showMessage(throwable.toString())
                );
    }

    void onItemClick(int specialityId){
        router.navigateTo(Scopes.USERS_SCOPE, specialityId);
    }
}
