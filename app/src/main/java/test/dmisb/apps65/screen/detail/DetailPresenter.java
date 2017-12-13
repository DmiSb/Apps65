package test.dmisb.apps65.screen.detail;

import android.text.TextUtils;

import test.dmisb.apps65.core.BasePresenter;
import test.dmisb.apps65.data.storage.model.UserEntity;
import test.dmisb.apps65.di.DaggerService;
import test.dmisb.apps65.di.Scopes;

public class DetailPresenter extends BasePresenter<DetailFragment> {

    private final String fullName;

    public DetailPresenter(String fullName) {
        this.fullName = fullName;
    }

    @Override
    protected void initComponent() {
        DetailFragment.Component component = DaggerService.getComponent(Scopes.DETAIL_SCOPE);
        if (component != null)
            component.inject(this);
    }

    @Override
    protected void onAttachView() {
        if (getView() != null) {
            UserEntity user = repository.getUserByName(fullName);
            if (user != null) {
                getView().setUserData(user);
            }

            StringBuilder speciality = new StringBuilder("");
            StringBuilder divider = new StringBuilder("");
            repository.getSpecialityByUser(fullName)
                    .subscribe(
                            specialityEntity -> {
                                speciality.append(divider).append(specialityEntity.getSpecialityName());
                                if (TextUtils.isEmpty(divider))
                                    divider.append(",\n");
                            },
                            throwable -> {},
                            () -> getView().setSpeciality(speciality.toString())
                    );
        }
    }

    void onBackClick() {
        DaggerService.unregisterComponent(Scopes.DETAIL_SCOPE);
        router.exit();
    }
}
