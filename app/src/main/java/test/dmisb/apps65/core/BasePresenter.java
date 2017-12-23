package test.dmisb.apps65.core;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;
import test.dmisb.apps65.data.repo.Repository;

public abstract class BasePresenter<V extends MvpView> extends MvpPresenter<V> {

    @Inject
    protected Repository repository;
    @Inject
    protected Router router;

    protected BasePresenter() {
        initComponent();
    }

    protected abstract void initComponent();
}
