package test.dmisb.apps65.screen.speciality;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import test.dmisb.apps65.core.IBaseView;
import test.dmisb.apps65.data.dto.SpecialityDto;

@StateStrategyType(value = AddToEndSingleStrategy.class)
interface SpecialityView extends IBaseView {
    void setItems(List<SpecialityDto> items);
}
