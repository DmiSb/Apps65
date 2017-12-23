package test.dmisb.apps65.screen.speciality;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import test.dmisb.apps65.R;
import test.dmisb.apps65.core.BaseAdapter;
import test.dmisb.apps65.core.BaseHolder;
import test.dmisb.apps65.data.dto.SpecialityDto;

class SpecialityAdapter extends BaseAdapter<SpecialityDto, SpecialityAdapter.Holder> {

    private SpecialityPresenter presenter;

    SpecialityAdapter(SpecialityPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(createView(parent, R.layout.item_speciality));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        SpecialityDto speciality = getItems().get(position);
        holder.name.setText(speciality.getName());
    }

    class Holder extends BaseHolder implements View.OnClickListener{
        final TextView name;

        Holder(View itemView) {
            super(itemView);
            name = $(R.id.speciality_name);
            name.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            SpecialityDto speciality = getItems().get(getAdapterPosition());
            presenter.onItemClick(speciality.getId());
        }
    }
}
