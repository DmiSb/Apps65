package test.dmisb.apps65.screen.users;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import test.dmisb.apps65.core.IBaseView;
import test.dmisb.apps65.data.dto.UserDto;

@StateStrategyType(value = AddToEndSingleStrategy.class)
interface UsersView extends IBaseView {
    void setTitle(String specialityName);
    void setItems(List<UserDto> items);
}
