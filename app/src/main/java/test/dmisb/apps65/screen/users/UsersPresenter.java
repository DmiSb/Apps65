package test.dmisb.apps65.screen.users;

import test.dmisb.apps65.core.BasePresenter;
import test.dmisb.apps65.data.storage.model.SpecialityEntity;
import test.dmisb.apps65.di.DaggerService;
import test.dmisb.apps65.di.Scopes;

public class UsersPresenter extends BasePresenter<UsersFragment> {

    private final int specialityId;

    UsersPresenter(int specialityId) {
        this.specialityId = specialityId;
    }

    @Override
    protected void initComponent() {
        UsersFragment.Component component = DaggerService.getComponent(Scopes.USERS_SCOPE);
        if (component != null)
            component.inject(this);
    }

    @Override
    protected void onAttachView() {
        if (getView() != null) {
            SpecialityEntity speciality = repository.getSpecialityById(specialityId);
            if (speciality != null)
                getView().setTitle(speciality.getSpecialityName());

            repository.getUsersBySpeciality(specialityId)
                    .subscribe(
                            userEntity -> getView().addUser(userEntity),
                            throwable -> router.showSystemMessage(throwable.getMessage())
                    );
        }
    }

    void onBackClick() {
        DaggerService.unregisterComponent(Scopes.USERS_SCOPE);
        router.exit();
    }

    void onItemClick(String fullName) {
        router.navigateTo(Scopes.DETAIL_SCOPE, fullName);
    }
}
