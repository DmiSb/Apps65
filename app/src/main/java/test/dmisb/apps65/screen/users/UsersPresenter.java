package test.dmisb.apps65.screen.users;

import com.arellomobile.mvp.InjectViewState;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import test.dmisb.apps65.core.BasePresenter;
import test.dmisb.apps65.di.Scopes;
import toothpick.Scope;
import toothpick.Toothpick;

@InjectViewState
public class UsersPresenter extends BasePresenter<UsersView> {

    private int specialityId;

    @Override
    protected void initComponent() {
        Scope scope = Toothpick.openScope(Scopes.DATA_SCOPE);
        Toothpick.inject(this, scope);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        repository.getSpecialityById(specialityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    speciality -> getViewState().setTitle(speciality.getName())
                );

        repository.getUsersBySpeciality(specialityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        users -> getViewState().setItems(users),
                        throwable -> router.showSystemMessage(throwable.getMessage())
                );
    }

    void setSpecialityId(int specialityId) {
        this.specialityId = specialityId;
    }

    void onBackClick() {
        router.exit();
    }

    void onItemClick(String fullName) {
        router.navigateTo(Scopes.DETAIL_SCOPE, fullName);
    }
}
