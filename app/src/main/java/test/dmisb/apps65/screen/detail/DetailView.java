package test.dmisb.apps65.screen.detail;

import test.dmisb.apps65.core.IBaseView;
import test.dmisb.apps65.data.dto.UserDto;

interface DetailView extends IBaseView {
    void setUserData(UserDto user);
    void setSpeciality(String text);
}
