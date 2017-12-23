package test.dmisb.apps65.data.storage;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.requery.Persistable;
import io.requery.reactivex.ReactiveEntityStore;
import test.dmisb.apps65.data.dto.SpecialityDto;
import test.dmisb.apps65.data.dto.UserDto;
import test.dmisb.apps65.data.network.res.SpecialityRes;
import test.dmisb.apps65.data.network.res.UserRes;
import test.dmisb.apps65.data.storage.model.SpecialityEntity;
import test.dmisb.apps65.data.storage.model.UserEntity;
import test.dmisb.apps65.data.storage.model.UserEntity_SpecialityEntity;

public class StorageManager {
    private final ReactiveEntityStore<Persistable> storage;

    public StorageManager(ReactiveEntityStore<Persistable> storage) {
        this.storage = storage;
    }

    public void saveUsers(UserRes user) {
        UserEntity userEntity = new UserEntity();

        String firstName = user.getFirstName().toLowerCase();
        firstName = Character.toUpperCase(firstName.charAt(0)) + firstName.substring(1);
        userEntity.setFirstName(firstName);

        String lastName = user.getLastName().toLowerCase();
        lastName = Character.toUpperCase(lastName.charAt(0)) + lastName.substring(1);
        userEntity.setLastName(lastName);

        userEntity.setFullName(firstName + " " + lastName);
        userEntity.setAvatarUrl(user.getAvatrUrl());
        userEntity.setBirthday(user.getBirthday());

        storage.upsert(userEntity).subscribe();

        saveSpeciality(user.getSpecialty(), userEntity);
    }

    private void saveSpeciality(List<SpecialityRes> specialities, UserEntity userEntity) {
        List<UserEntity_SpecialityEntity> joinEntities = new ArrayList<>();
        SpecialityEntity speciality;
        UserEntity_SpecialityEntity join;

        for (SpecialityRes specialityRes : specialities) {
            speciality = new SpecialityEntity();
            speciality.setId(specialityRes.getId());
            speciality.setSpecialityName(specialityRes.getName());
            storage.upsert(speciality).subscribe();

            join = new UserEntity_SpecialityEntity();
            join.setUserId(userEntity.getFullName());
            join.setSpecialityId(speciality.getId());
            joinEntities.add(join);
        }

        storage.upsert(joinEntities).subscribe();
    }

    public Observable<SpecialityDto> getSpeciality() {
        return storage.select(SpecialityEntity.class)
                .get()
                .observable()
                .flatMap(specialityEntity -> Observable.just(new SpecialityDto(specialityEntity)));
    }

    public SpecialityEntity getSpecialityById(int specialityId) {
        return storage.select(SpecialityEntity.class)
                .where(SpecialityEntity.ID.eq(specialityId))
                .get()
                .firstOrNull();
    }

    public Observable<SpecialityEntity> getSpecialityByUser(String fullName) {
        return storage.select(SpecialityEntity.class)
                .join(UserEntity_SpecialityEntity.class)
                .on(UserEntity_SpecialityEntity.SPECIALITY_ID.eq(SpecialityEntity.ID))
                .where(UserEntity_SpecialityEntity.USER_ID.eq(fullName))
                .get()
                .observable();
    }

    public Observable<UserEntity> getUserBySpeciality(int specialityId) {
        return storage.select(UserEntity.class)
                .join(UserEntity_SpecialityEntity.class)
                .on(UserEntity.FULL_NAME.eq(UserEntity_SpecialityEntity.USER_ID))
                .where(UserEntity_SpecialityEntity.SPECIALITY_ID.eq(specialityId))
                .get()
                .observable();

    }

    private UserEntity getUser(String fullName) {
        return storage.select(UserEntity.class)
                .where(UserEntity.FULL_NAME.eq(fullName))
                .get()
                .firstOrNull();
    }

    public UserDto getUserByName(String fullName) {
        UserEntity user = getUser(fullName);
        return user != null ? new UserDto(user) : null;
    }
}
