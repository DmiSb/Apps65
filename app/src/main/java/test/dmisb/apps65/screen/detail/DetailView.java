package test.dmisb.apps65.screen.detail;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import test.dmisb.apps65.core.IBaseView;
import test.dmisb.apps65.data.dto.UserDto;

@StateStrategyType(value = AddToEndSingleStrategy.class)
interface DetailView extends IBaseView {
    void setUserData(UserDto user);
    void setSpeciality(String text);
}
