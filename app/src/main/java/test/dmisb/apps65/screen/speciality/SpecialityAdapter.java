package test.dmisb.apps65.screen.speciality;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import test.dmisb.apps65.R;
import test.dmisb.apps65.core.BaseAdapter;
import test.dmisb.apps65.core.BaseHolder;
import test.dmisb.apps65.data.storage.model.SpecialityEntity;
import test.dmisb.apps65.di.DaggerService;
import test.dmisb.apps65.di.Scopes;

public class SpecialityAdapter extends BaseAdapter<SpecialityEntity, SpecialityAdapter.Holder> {

    @Inject
    SpecialityPresenter presenter;

    SpecialityAdapter() {
        SpecialityFragment.Component component = DaggerService.getComponent(Scopes.SPECIALITY_SCOPE);
        if (component != null) {
            component.inject(this);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(createView(parent, R.layout.item_speciality));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        SpecialityEntity speciality = getItems().get(position);
        holder.name.setText(speciality.getSpecialityName());
        holder.name.setOnClickListener(v -> presenter.onItemClick(speciality.getId()));
    }

    void addItem(SpecialityEntity speciality) {
        getItems().add(speciality);
        notifyDataSetChanged();
    }

    class Holder extends BaseHolder {
        final TextView name;

        Holder(View itemView) {
            super(itemView);
            name = $(R.id.speciality_name);
        }
    }
}
