package test.dmisb.apps65.root;

import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import javax.inject.Inject;

import ru.terrakok.cicerone.NavigatorHolder;
import test.dmisb.apps65.R;
import test.dmisb.apps65.di.Scopes;
import toothpick.Scope;
import toothpick.Toothpick;

public class RootActivity extends MvpAppCompatActivity implements RootView {

    @InjectPresenter
    RootPresenter presenter;

    @Inject
    NavigatorHolder navigatorHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);

        initToothPick();
    }

    private void initToothPick() {
        Scope scope = Toothpick.openScope(Scopes.APP_SCOPE);
        Toothpick.inject(this, scope);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        navigatorHolder.setNavigator(new RootNavigator(this, R.id.root_frame));
    }

    @Override
    protected void onPause() {
        navigatorHolder.removeNavigator();
        super.onPause();
    }

    /*@Override
    public void onBackPressed() {
        Fragment fragment = getVisibleFragment();
        if (fragment == null || !((IBaseView) fragment).onSystemBackPressed()) {
            super.onBackPressed();
        }
    }

    private Fragment getVisibleFragment(){
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    @Override
    public boolean onSystemBackPressed() {
        return false;
    }*/
}
