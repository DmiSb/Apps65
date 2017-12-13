package test.dmisb.apps65.root;

import android.content.Intent;
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
            case Scopes.SPECIALITY_SCOPE:   return SpecialityFragment.newInstance();
            case Scopes.USERS_SCOPE:        return UsersFragment.newInstance((int) data);
            case Scopes.DETAIL_SCOPE:       return DetailFragment.newInstance((String) data);
        }
        return null;
    }
}
