package test.dmisb.apps65.data.storage.model;

import io.requery.Entity;
import io.requery.Index;
import io.requery.Key;
import io.requery.Persistable;

@Entity
interface Speciality extends Persistable {
    @Key
    int getId();
    @Index("NAME")
    String getSpecialityName();
}
