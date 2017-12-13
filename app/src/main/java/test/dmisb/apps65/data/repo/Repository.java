package test.dmisb.apps65.data.repo;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import test.dmisb.apps65.data.network.core.RestCallTransformer;
import test.dmisb.apps65.data.network.RestService;
import test.dmisb.apps65.data.network.res.BaseRes;
import test.dmisb.apps65.data.network.res.UserRes;
import test.dmisb.apps65.data.storage.model.SpecialityEntity;
import test.dmisb.apps65.data.storage.StorageManager;
import test.dmisb.apps65.data.storage.model.UserEntity;
import test.dmisb.apps65.di.DaggerService;
import test.dmisb.apps65.di.Scopes;
import test.dmisb.apps65.di.components.DataComponent;

public class Repository {

    @Inject
    RestService restService;
    @Inject
    StorageManager storage;

    public Repository() {
        DataComponent component = DaggerService.getComponent(Scopes.DATA_SCOPE);
        if (component != null)
            component.inject(this);
    }

    public Observable<SpecialityEntity> loadUsers() {
        Observable<SpecialityEntity> fromStorage = storage.getSpeciality();

        Observable<SpecialityEntity> fromNetwork = restService.getUsers()
                .compose(new RestCallTransformer<>())
                .flatMap(this::updateRes)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        return Observable.mergeDelayError(fromStorage, fromNetwork)
                .distinct(SpecialityEntity::getId);
    }

    private Observable<SpecialityEntity> updateRes(BaseRes baseRes) {
        for (UserRes userRes : baseRes.getResponse()) {
            storage.saveUsers(userRes);
        }
        return storage.getSpeciality();
    }

    public SpecialityEntity getSpecialityById(int specialityId) {
        return storage.getSpecialityById(specialityId);
    }

    public Observable<SpecialityEntity> getSpecialityByUser(String fullName) {
        return storage.getSpecialityByUser(fullName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<UserEntity> getUsersBySpeciality(int specialityId) {
        return storage.getUserBySpeciality(specialityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public UserEntity getUserByName(String fullName) {
        return storage.getUserByName(fullName);
    }
}
