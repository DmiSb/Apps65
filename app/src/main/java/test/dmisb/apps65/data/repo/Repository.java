package test.dmisb.apps65.data.repo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import test.dmisb.apps65.data.dto.SpecialityDto;
import test.dmisb.apps65.data.dto.UserDto;
import test.dmisb.apps65.data.network.core.RestCallTransformer;
import test.dmisb.apps65.data.network.RestService;
import test.dmisb.apps65.data.network.res.BaseRes;
import test.dmisb.apps65.data.network.res.UserRes;
import test.dmisb.apps65.data.storage.StorageManager;

public class Repository {

    private RestService restService;
    private StorageManager storage;

    public Repository(RestService restService, StorageManager storage) {
        this.restService = restService;
        this.storage = storage;
    }

    public Single<List<SpecialityDto>> loadUsers() {
        Observable<SpecialityDto> fromStorage = storage.getSpeciality();

        Observable<SpecialityDto> fromNetwork = restService.getUsers()
                .compose(new RestCallTransformer<>())
                .flatMap(this::updateRes);

        return Observable.mergeDelayError(fromStorage, fromNetwork)
                .distinct(SpecialityDto::getId)
                .toList()
                .flatMap(Single::just);
    }

    private Observable<SpecialityDto> updateRes(BaseRes baseRes) {
        for (UserRes userRes : baseRes.getResponse()) {
            storage.saveUsers(userRes);
        }
        return storage.getSpeciality();
    }

    public Single<SpecialityDto> getSpecialityById(int specialityId) {
        return Single.just(storage.getSpecialityById(specialityId))
                    .flatMap(specialityEntity -> Single.just(new SpecialityDto(specialityEntity)));
    }

    public Observable<SpecialityDto> getSpecialityByUser(String fullName) {
        return storage.getSpecialityByUser(fullName)
                .flatMap(specialityEntity -> Observable.just(new SpecialityDto(specialityEntity)));
    }

    public Single<List<UserDto>> getUsersBySpeciality(int specialityId) {
        return storage.getUserBySpeciality(specialityId)
                .flatMap(userEntities -> Observable.just(new UserDto(userEntities)))
                .toList()
                .flatMap(Single::just);
    }

    public UserDto getUserByName(String fullName) {
        return storage.getUserByName(fullName);
    }
}
