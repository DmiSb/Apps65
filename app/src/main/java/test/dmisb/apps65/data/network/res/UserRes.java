package test.dmisb.apps65.data.network.res;

import java.util.Date;
import java.util.List;

public class UserRes {
    private String f_name;
    private String l_name;
    private Date birthday;
    private String avatr_url;
    private List<SpecialityRes> specialty;

    public String getFirstName() {
        return f_name;
    }

    public String getLastName() {
        return l_name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getAvatrUrl() {
        return avatr_url;
    }

    public List<SpecialityRes> getSpecialty() {
        return specialty;
    }
}
