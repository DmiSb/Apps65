package test.dmisb.apps65.screen.users;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import test.dmisb.apps65.R;
import test.dmisb.apps65.core.BaseFragment;
import test.dmisb.apps65.data.dto.UserDto;

public class UsersFragment extends BaseFragment implements UsersView {

    public static final String SPECIALITY_KEY = "SPECIALITY_KEY";
    private UsersAdapter adapter;

    @InjectPresenter
    UsersPresenter presenter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_users;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        Bundle arguments = getArguments();
        int specialityId = 0;
        if (arguments != null) {
            specialityId = getArguments().getInt(SPECIALITY_KEY);
        }
        presenter.setSpecialityId(specialityId);

        adapter = new UsersAdapter(presenter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView list = $(R.id.users_list);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);

        $(R.id.users_back).setOnClickListener(v -> presenter.onBackClick());
    }

    @Override
    public void setTitle(String specialityName) {
        String text = getString(R.string.user_list);
        text = String.format(text, specialityName);
        ((TextView) $(R.id.users_title)).setText(text);
    }

    @Override
    public void setItems(List<UserDto> items) {
        adapter.setItems(items);
    }
}
