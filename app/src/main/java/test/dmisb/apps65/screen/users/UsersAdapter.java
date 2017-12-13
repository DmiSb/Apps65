package test.dmisb.apps65.screen.users;

import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import test.dmisb.apps65.R;
import test.dmisb.apps65.core.BaseAdapter;
import test.dmisb.apps65.core.BaseHolder;
import test.dmisb.apps65.data.storage.model.UserEntity;
import test.dmisb.apps65.di.DaggerService;
import test.dmisb.apps65.di.Scopes;
import test.dmisb.apps65.utils.CalcUtils;

public class UsersAdapter extends BaseAdapter<UserEntity, UsersAdapter.Holder> {

    @Inject
    UsersPresenter presenter;

    public UsersAdapter() {
        UsersFragment.Component component = DaggerService.getComponent(Scopes.USERS_SCOPE);
        if (component != null)
            component.inject(this);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(createView(parent, R.layout.item_users));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        UserEntity user = getItems().get(position);
        holder.userName.setText(user.getFullName());
        if (user.getBirthday() != null) {
            int years = CalcUtils.calcAge(user.getBirthday());
            String text = holder.userAge.getResources().getQuantityString(R.plurals.user_age, years, years);
            holder.userAge.setText(text);
        } else {
            holder.userAge.setText("-");
        }
        holder.userWrp.setOnClickListener(v -> presenter.onItemClick(user.getFullName()));
    }

    void addItem(UserEntity user) {
        getItems().add(user);
        notifyDataSetChanged();
    }

    class Holder extends BaseHolder {
        final TextView userName;
        final TextView userAge;
        final CardView userWrp;

        Holder(View itemView) {
            super(itemView);
            userName = $(R.id.user_full_name);
            userAge = $(R.id.user_age);
            userWrp = $(R.id.user_wrp);
        }
    }
}
