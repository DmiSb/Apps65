package test.dmisb.apps65.root;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import ru.terrakok.cicerone.android.SupportAppNavigator;
import test.dmisb.apps65.di.Scopes;
import test.dmisb.apps65.screen.detail.DetailFragment;
import test.dmisb.apps65.screen.speciality.SpecialityFragment;
import test.dmisb.apps65.screen.users.UsersFragment;

class RootNavigator extends SupportAppNavigator {

    RootNavigator(FragmentActivity activity, int containerId) {
        super(activity, containerId);
    }

    @Override
    protected Intent createActivityIntent(String screenKey, Object data) {
        return null;
    }

    @Override
    protected Fragment createFragment(String screenKey, Object data) {
        switch (screenKey) {
            case Scopes.SPECIALITY_SCOPE:
                return new SpecialityFragment();

            case Scopes.USERS_SCOPE:
                UsersFragment usersFragment = new UsersFragment();
                Bundle userArguments = new Bundle();
                userArguments.putInt(UsersFragment.SPECIALITY_KEY, (int) data);
                usersFragment.setArguments(userArguments);
                return usersFragment;

            case Scopes.DETAIL_SCOPE:
                DetailFragment detailFragment = new DetailFragment();
                Bundle detailArguments = new Bundle();
                detailArguments.putString(DetailFragment.DETAIL_KEY, (String) data);
                detailFragment.setArguments(detailArguments);
                return detailFragment;
        }
        return null;
    }
}
