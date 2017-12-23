package test.dmisb.apps65.screen.users;

import java.util.List;

import test.dmisb.apps65.core.IBaseView;
import test.dmisb.apps65.data.dto.UserDto;

interface UsersView extends IBaseView {
    void setTitle(String specialityName);
    void setItems(List<UserDto> items);
}
