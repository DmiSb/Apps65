package test.dmisb.apps65.data.dto;

import java.util.Date;

import test.dmisb.apps65.data.storage.model.UserEntity;

public class UserDto {
    private String fullName;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String avatarUrl;

    public UserDto(UserEntity user) {
        this.fullName = user.getFullName();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.birthday = user.getBirthday();
        this.avatarUrl = user.getAvatarUrl();
    }

    public String getFullName() {
        return fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
