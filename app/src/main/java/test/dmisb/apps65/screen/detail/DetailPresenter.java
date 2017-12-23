package test.dmisb.apps65.screen.detail;

import android.text.TextUtils;

import com.arellomobile.mvp.InjectViewState;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import test.dmisb.apps65.core.BasePresenter;
import test.dmisb.apps65.data.dto.UserDto;
import test.dmisb.apps65.di.Scopes;
import toothpick.Scope;
import toothpick.Toothpick;

@InjectViewState
public class DetailPresenter extends BasePresenter<DetailView> {

    private String fullName;

    @Override
    protected void initComponent() {
        Scope scope = Toothpick.openScope(Scopes.DATA_SCOPE);
        Toothpick.inject(this, scope);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        UserDto user = repository.getUserByName(fullName);
        if (user != null) {
            getViewState().setUserData(user);
        }

        StringBuilder specialities = new StringBuilder("");
        StringBuilder divider = new StringBuilder("");
        repository.getSpecialityByUser(fullName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        speciality -> {
                            specialities.append(divider).append(speciality.getName());
                            if (TextUtils.isEmpty(divider))
                                divider.append(",\n");
                        },
                        throwable -> router.showSystemMessage(throwable.getMessage()),
                        () -> getViewState().setSpeciality(specialities.toString())
                );
    }

    void setFullName(String fullName) {
        this.fullName = fullName;
    }

    void onBackClick() {
        router.exit();
    }
}