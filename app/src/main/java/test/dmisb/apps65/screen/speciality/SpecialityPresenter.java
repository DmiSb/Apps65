package test.dmisb.apps65.screen.speciality;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import test.dmisb.apps65.core.BasePresenter;
import test.dmisb.apps65.di.Scopes;
import test.dmisb.apps65.root.RootPresenter;
import toothpick.Scope;
import toothpick.Toothpick;

@InjectViewState
public class SpecialityPresenter extends BasePresenter<SpecialityView> {

    @Inject
    RootPresenter rootPresenter;

    @Override
    protected void initComponent() {
        Scope scope = Toothpick.openScope(Scopes.DATA_SCOPE);
        Toothpick.inject(this, scope);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        repository.loadUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        specialities -> getViewState().setItems(specialities),
                        throwable -> router.showSystemMessage(throwable.getMessage())
                );
    }

    void onItemClick(int specialityId){
        router.navigateTo(Scopes.USERS_SCOPE, specialityId);
    }
}
