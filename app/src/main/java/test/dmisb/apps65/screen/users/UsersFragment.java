package test.dmisb.apps65.screen.users;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import dagger.Provides;
import test.dmisb.apps65.R;
import test.dmisb.apps65.core.BaseFragment;
import test.dmisb.apps65.data.storage.model.UserEntity;
import test.dmisb.apps65.di.DaggerScope;
import test.dmisb.apps65.di.DaggerService;
import test.dmisb.apps65.di.Scopes;
import test.dmisb.apps65.di.components.RootComponent;

public class UsersFragment extends BaseFragment<UsersPresenter>{

    private static int specialityId;
    private UsersAdapter adapter;

    public static UsersFragment newInstance(int specialityId) {
        registerComponent();
        UsersFragment instance = new UsersFragment();
        instance.specialityId = specialityId;
        return instance;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_users;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        $(R.id.users_back).setOnClickListener(v -> presenter.onBackClick());

        adapter = new UsersAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView list = $(R.id.users_list);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
    }

    @Override
    public boolean onSystemBackPressed() {
        presenter.onBackClick();
        return true;
    }

    void setTitle(String specialityName) {
        String text = getString(R.string.user_list);
        text = String.format(text, specialityName);
        ((TextView) $(R.id.users_title)).setText(text);
    }

    void addUser(UserEntity user) {
        adapter.addItem(user);
    }

    //region ================= DI =================

    private static void registerComponent() {
        if (DaggerService.getComponent(Scopes.USERS_SCOPE) == null) {
            RootComponent rootComponent = DaggerService.getComponent(Scopes.ROOT_SCOPE);
            if (rootComponent != null) {
                UsersFragment.Component component = rootComponent.plusUsers(new UsersFragment.Module());
                if (component != null) {
                    DaggerService.registerComponent(Scopes.USERS_SCOPE, component);
                }
            }
        }
    }

    @Override
    protected void initComponent() {
        UsersFragment.Component component = DaggerService.getComponent(Scopes.USERS_SCOPE);
        if (component != null)
            component.inject(this);
    }

    @dagger.Module
    public static class Module {
        @Provides
        @DaggerScope(UsersFragment.class)
        UsersPresenter provideUsersPresenter() {
            return new UsersPresenter(specialityId);
        }
    }

    @dagger.Subcomponent(modules = Module.class)
    @DaggerScope(UsersFragment.class)
    public interface Component {
        void inject(UsersFragment fragment);
        void inject(UsersPresenter presenter);
        void inject(UsersAdapter adapter);
    }

    //endregion
}
