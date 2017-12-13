package test.dmisb.apps65.di.components;

import dagger.Subcomponent;
import test.dmisb.apps65.di.DaggerScope;
import test.dmisb.apps65.di.modules.RootModule;
import test.dmisb.apps65.root.RootActivity;
import test.dmisb.apps65.root.RootPresenter;
import test.dmisb.apps65.screen.detail.DetailFragment;
import test.dmisb.apps65.screen.speciality.SpecialityFragment;
import test.dmisb.apps65.screen.users.UsersFragment;

@Subcomponent(modules = RootModule.class)
@DaggerScope(RootActivity.class)
public interface RootComponent {
    void inject(RootActivity activity);
    void inject(RootPresenter presenter);

    SpecialityFragment.Component plusSpeciality(SpecialityFragment.Module module);
    UsersFragment.Component plusUsers(UsersFragment.Module module);
    DetailFragment.Component plusDetail(DetailFragment.Module module);
}
