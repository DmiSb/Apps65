package test.dmisb.apps65.root;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.NavigatorHolder;
import test.dmisb.apps65.R;
import test.dmisb.apps65.core.IBaseView;
import test.dmisb.apps65.di.DaggerService;
import test.dmisb.apps65.di.Scopes;
import test.dmisb.apps65.di.components.RootComponent;

public class RootActivity extends AppCompatActivity implements IBaseView {

    @Inject
    RootPresenter presenter;
    @Inject
    NavigatorHolder navigatorHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);

        initComponent();
    }

    private void initComponent() {
        RootComponent component = DaggerService.getComponent(Scopes.ROOT_SCOPE);
        if (component != null)
            component.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigatorHolder.setNavigator(new RootNavigator(this, R.id.root_frame));
        presenter.takeView(this);
    }

    @Override
    protected void onPause() {
        navigatorHolder.removeNavigator();
        presenter.dropView();
        super.onPause();
    }

    @Override
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
    }
}
