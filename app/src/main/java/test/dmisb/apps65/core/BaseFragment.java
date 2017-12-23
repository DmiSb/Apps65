package test.dmisb.apps65.core;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;

public abstract class BaseFragment extends MvpAppCompatFragment {

    private View frameView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        frameView = inflater.inflate(getLayoutRes(), container, false);
        initView(savedInstanceState);
        return frameView;
    }

    protected abstract @LayoutRes
    int getLayoutRes();

    protected abstract void initView(@Nullable Bundle bundle);

    protected <T extends View> T $(@IdRes int id) {
        //noinspection unchecked
        return (T) frameView.findViewById(id);
    }
}
