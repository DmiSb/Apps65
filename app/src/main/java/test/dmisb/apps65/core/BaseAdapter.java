package test.dmisb.apps65.core;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, H extends BaseHolder>
        extends RecyclerView.Adapter<H> {

    private List<T> items = new ArrayList<>();

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    protected List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    protected View createView(ViewGroup parent, @LayoutRes int layoutRes) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
    }
}
