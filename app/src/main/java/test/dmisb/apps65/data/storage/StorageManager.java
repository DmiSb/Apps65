package test.dmisb.apps65.data.storage;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.requery.Persistable;
import io.requery.reactivex.ReactiveEntityStore;
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
        userEntity.setAvatrUrl(user.getAvatrUrl());
        userEntity.setBirthday(user.getBirthday());

        storage.upsert(userEntity).subscribe();

        saveSpeciality(user.getSpecialty(), userEntity);
    }

    private void saveSpeciality(List<SpecialityRes> specialities, UserEntity userEntity) {
        List<UserEntity_SpecialityEntity> specialityEntities = new ArrayList<>();
        SpecialityEntity entity;
        UserEntity_SpecialityEntity join;

        for (SpecialityRes specialityRes : specialities) {
            entity = new SpecialityEntity();
            entity.setId(specialityRes.getId());
            entity.setSpecialityName(specialityRes.getName());
            storage.upsert(specialityEntities).subscribe();

            join = new UserEntity_SpecialityEntity();
            join.setUserId(userEntity.getFullName());
            join.setSpecialityId(entity.getId());
            specialityEntities.add(join);
        }

        storage.upsert(specialityEntities).subscribe();
    }

    public Observable<SpecialityEntity> getSpeciality() {
        return storage.select(SpecialityEntity.class)
                .get()
                .observable();
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

    public UserEntity getUserByName(String fullName) {
        return storage.select(UserEntity.class)
                .where(UserEntity.FULL_NAME.eq(fullName))
                .get()
                .firstOrNull();
    }
}
