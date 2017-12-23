package test.dmisb.apps65.screen.users;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import test.dmisb.apps65.R;
import test.dmisb.apps65.core.BaseAdapter;
import test.dmisb.apps65.core.BaseHolder;
import test.dmisb.apps65.data.dto.UserDto;
import test.dmisb.apps65.utils.CalcUtils;

class UsersAdapter extends BaseAdapter<UserDto, UsersAdapter.Holder> {

    private UsersPresenter presenter;

    UsersAdapter(UsersPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(createView(parent, R.layout.item_users));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        UserDto user = getItems().get(position);
        holder.userName.setText(user.getFullName());
        if (user.getBirthday() != null) {
            int years = CalcUtils.calcAge(user.getBirthday());
            String text = holder.userAge.getResources().getQuantityString(R.plurals.user_age, years, years);
            holder.userAge.setText(text);
        } else {
            holder.userAge.setText("-");
        }
    }

    class Holder extends BaseHolder implements View.OnClickListener {
        final TextView userName;
        final TextView userAge;

        Holder(View itemView) {
            super(itemView);
            userName = $(R.id.user_full_name);
            userAge = $(R.id.user_age);

            $(R.id.user_wrp).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            UserDto user = getItems().get(getAdapterPosition());
            presenter.onItemClick(user.getFullName());
        }
    }
}