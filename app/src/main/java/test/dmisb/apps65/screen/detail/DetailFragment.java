package test.dmisb.apps65.screen.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import test.dmisb.apps65.R;
import test.dmisb.apps65.core.BaseFragment;
import test.dmisb.apps65.data.dto.UserDto;
import test.dmisb.apps65.utils.CalcUtils;
import test.dmisb.apps65.utils.FormatUtils;

public class DetailFragment extends BaseFragment implements DetailView {

    public static final String DETAIL_KEY = "DETAIL_KEY";

    private TextView firstName;
    private TextView lastName;
    private TextView userBirthDay;
    private TextView userAge;
    private TextView speciality;
    private ImageView avatarImg;

    @InjectPresenter
    DetailPresenter presenter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_detail;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        Bundle arguments = getArguments();
        String fullName = "";
        if (arguments != null) {
            fullName = arguments.getString(DETAIL_KEY);
        }
        presenter.setFullName(fullName);

        firstName = $(R.id.detail_first_name);
        lastName = $(R.id.detail_last_name);
        avatarImg = $(R.id.detail_avatar);
        userBirthDay = $(R.id.detail_birthday);
        userAge = $(R.id.detail_age);
        speciality = $(R.id.detail_speciality);

        $(R.id.detail_back).setOnClickListener(v -> presenter.onBackClick());
    }

    @Override
    public void setUserData(UserDto user) {
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());

        RequestOptions options = new RequestOptions()
                .fitCenter()
                .circleCrop()
                .placeholder(R.drawable.ic_avatar)
                .error(R.drawable.ic_avatar);

        Glide.with(this)
                .load(user.getAvatarUrl())
                .apply(options)
                .into(avatarImg);

        if (user.getBirthday() != null) {
            int years = CalcUtils.calcAge(user.getBirthday());
            String text = getResources().getQuantityString(R.plurals.user_age, years, years);
            userAge.setText(text);

            userBirthDay.setText(FormatUtils.dateFormat(user.getBirthday()));
        } else {
            userAge.setText("-");
            userBirthDay.setText("-");
        }
    }

    @Override
    public void setSpeciality(String text) {
        speciality.setText(text);
    }
}