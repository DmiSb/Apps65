package test.dmisb.apps65.core;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;
import test.dmisb.apps65.data.repo.Repository;

public abstract class BasePresenter<V extends IBaseView> {

    private V view;

    @Inject
    protected Repository repository;
    @Inject
    protected Router router;

    protected BasePresenter() {
        initComponent();
    }

    protected abstract void initComponent();

    public void takeView(V view) {
        this.view = view;
        onAttachView();
    }

    protected abstract void onAttachView();

    public void dropView() {
        this.view = null;
    }

    @Nullable
    protected V getView() {
        return view;
    }
}
