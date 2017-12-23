package test.dmisb.apps65.data.dto;

import test.dmisb.apps65.data.storage.model.SpecialityEntity;

public class SpecialityDto {
    private int id;
    private String name;

    public SpecialityDto(SpecialityEntity speciality) {
        id = speciality.getId();
        name = speciality.getSpecialityName();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
