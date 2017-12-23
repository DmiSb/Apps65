package test.dmisb.apps65.screen.speciality;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import test.dmisb.apps65.R;
import test.dmisb.apps65.core.BaseFragment;
import test.dmisb.apps65.data.dto.SpecialityDto;

public class SpecialityFragment extends BaseFragment implements SpecialityView {

    private SpecialityAdapter adapter;

    @InjectPresenter
    SpecialityPresenter presenter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_speciality;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        adapter = new SpecialityAdapter(presenter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView list = $(R.id.speciality_list);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
    }

    @Override
    public void setItems(List<SpecialityDto> items) {
        adapter.setItems(items);
    }
}
