package test.dmisb.apps65.core;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.inject.Inject;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IBaseView {

    private View frameView;
    @Inject
    protected P presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initComponent();
        frameView = inflater.inflate(getLayoutRes(), container, false);
        initView(savedInstanceState);
        return frameView;
    }

    protected abstract void initComponent();

    protected abstract @LayoutRes
    int getLayoutRes();

    protected abstract void initView(@Nullable Bundle bundle);

    protected <T extends View> T $(@IdRes int id) {
        //noinspection unchecked
        return (T) frameView.findViewById(id);
    }

    @Override
    public void onStart() {
        super.onStart();
        //noinspection unchecked
        presenter.takeView( this);
    }

    @Override
    public void onStop() {
        //noinspection unchecked
        presenter.dropView();
        super.onStop();
    }

    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
