package test.dmisb.apps65.screen.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import dagger.Provides;
import test.dmisb.apps65.R;
import test.dmisb.apps65.core.BaseFragment;
import test.dmisb.apps65.data.storage.model.UserEntity;
import test.dmisb.apps65.di.DaggerScope;
import test.dmisb.apps65.di.DaggerService;
import test.dmisb.apps65.di.Scopes;
import test.dmisb.apps65.di.components.RootComponent;
import test.dmisb.apps65.utils.CalcUtils;
import test.dmisb.apps65.utils.FormatUtils;

public class DetailFragment extends BaseFragment<DetailPresenter> {

    private static String fullName;

    private TextView firstName;
    private TextView lastName;
    private TextView userBirthDay;
    private TextView userAge;
    private TextView speciality;
    private ImageView avatarImg;

    public static DetailFragment newInstance(String fullName) {
        registerComponent();
        DetailFragment instance = new DetailFragment();
        instance.fullName = fullName;
        return instance;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_detail;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        firstName = $(R.id.detail_first_name);
        lastName = $(R.id.detail_last_name);
        avatarImg = $(R.id.detail_avatar);
        userBirthDay = $(R.id.detail_birthday);
        userAge = $(R.id.detail_age);
        speciality = $(R.id.detail_speciality);

        $(R.id.detail_back).setOnClickListener(v -> presenter.onBackClick());
    }

    @Override
    public boolean onSystemBackPressed() {
        presenter.onBackClick();
        return true;
    }

    void setUserData(UserEntity user) {
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());

        RequestOptions options = new RequestOptions()
                .fitCenter()
                .circleCrop()
                .placeholder(R.drawable.ic_avatar)
                .error(R.drawable.ic_avatar);

        Glide.with(this)
                .load(user.getAvatrUrl())
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

    void setSpeciality(String text) {
        speciality.setText(text);
    }

    //region ================= DI =================

    private static void registerComponent() {
        if (DaggerService.getComponent(Scopes.DETAIL_SCOPE) == null) {
            RootComponent rootComponent = DaggerService.getComponent(Scopes.ROOT_SCOPE);
            if (rootComponent != null) {
                DetailFragment.Component component = rootComponent.plusDetail(new DetailFragment.Module());
                if (component != null) {
                    DaggerService.registerComponent(Scopes.DETAIL_SCOPE, component);
                }
            }
        }
    }

    @Override
    protected void initComponent() {
        DetailFragment.Component component = DaggerService.getComponent(Scopes.DETAIL_SCOPE);
        if (component != null)
            component.inject(this);
    }

    @dagger.Module
    public static class Module {
        @Provides
        @DaggerScope(DetailFragment.class)
        DetailPresenter provideDetailPresenter() {
            return new DetailPresenter(fullName);
        }
    }

    @dagger.Subcomponent(modules = Module.class)
    @DaggerScope(DetailFragment.class)
    public interface Component {
        void inject(DetailFragment fragment);
        void inject(DetailPresenter presenter);
    }

    //endregion
}
