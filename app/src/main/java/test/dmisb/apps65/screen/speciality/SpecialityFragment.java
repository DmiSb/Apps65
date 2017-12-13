package test.dmisb.apps65.screen.speciality;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import dagger.Provides;
import test.dmisb.apps65.R;
import test.dmisb.apps65.core.BaseFragment;
import test.dmisb.apps65.data.storage.model.SpecialityEntity;
import test.dmisb.apps65.di.DaggerScope;
import test.dmisb.apps65.di.DaggerService;
import test.dmisb.apps65.di.Scopes;
import test.dmisb.apps65.di.components.RootComponent;

public class SpecialityFragment extends BaseFragment<SpecialityPresenter> {

    private SpecialityAdapter adapter;

    public static SpecialityFragment newInstance() {
        registerComponent();
        return new SpecialityFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_speciality;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        adapter = new SpecialityAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView list = $(R.id.speciality_list);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
    }

    @Override
    public boolean onSystemBackPressed() {
        return false;
    }

    void addSpeciality(SpecialityEntity speciality) {
        adapter.addItem(speciality);
    }

    //region ================= DI =================

    private static void registerComponent() {
        if (DaggerService.getComponent(Scopes.SPECIALITY_SCOPE) == null) {
            RootComponent rootComponent = DaggerService.getComponent(Scopes.ROOT_SCOPE);
            if (rootComponent != null) {
                SpecialityFragment.Component component = rootComponent.plusSpeciality(new SpecialityFragment.Module());
                if (component != null) {
                    DaggerService.registerComponent(Scopes.SPECIALITY_SCOPE, component);
                }
            }
        }
    }

    @Override
    protected void initComponent() {
        SpecialityFragment.Component component = DaggerService.getComponent(Scopes.SPECIALITY_SCOPE);
        if (component != null) {
            component.inject(this);
        }
    }

    @dagger.Module
    public static class Module {
        @Provides
        @DaggerScope(SpecialityFragment.class)
        SpecialityPresenter provideProfessionsPresenter() {
            return new SpecialityPresenter();
        }
    }

    @dagger.Subcomponent(modules = Module.class)
    @DaggerScope(SpecialityFragment.class)
    public interface Component {
        void inject(SpecialityFragment fragment);
        void inject(SpecialityPresenter presenter);
        void inject(SpecialityAdapter adapter);
    }

    //endregion
}
