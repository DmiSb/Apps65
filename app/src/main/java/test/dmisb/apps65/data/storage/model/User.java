package test.dmisb.apps65.data.storage.model;

import java.util.Date;

import io.requery.Entity;
import io.requery.JunctionTable;
import io.requery.Key;
import io.requery.ManyToMany;
import io.requery.Persistable;
import io.requery.query.Result;

@Entity
interface User extends Persistable {
    @Key
    String getFullName();
    String getFirstName();
    String getLastName();
    Date getBirthday();
    String getAvatarUrl();
    @ManyToMany
    @JunctionTable
    Result<Speciality> getSpeciality();
}
