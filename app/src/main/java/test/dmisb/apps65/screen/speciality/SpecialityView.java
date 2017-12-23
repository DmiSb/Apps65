package test.dmisb.apps65.screen.speciality;

import java.util.List;

import test.dmisb.apps65.core.IBaseView;
import test.dmisb.apps65.data.dto.SpecialityDto;

interface SpecialityView extends IBaseView {
    void setItems(List<SpecialityDto> items);
}
