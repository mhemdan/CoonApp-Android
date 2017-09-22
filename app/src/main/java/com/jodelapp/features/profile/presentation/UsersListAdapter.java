package com.jodelapp.features.profile.presentation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jodelapp.R;
import com.jodelapp.features.profile.model.UserPresentationModel;
import com.jodelapp.views.activities.MainActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by m.hemdan on 9/19/17.
 */

public class UsersListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<UserPresentationModel> usersDataList = new ArrayList<>();

    public UsersListAdapter(List<UserPresentationModel> todoDataList) {
        this.usersDataList.clear();
        this.usersDataList.addAll(todoDataList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_user, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserPresentationModel userPresentationModel = usersDataList.get(position);
        ((UserItemViewHolder) holder).render(userPresentationModel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(userPresentationModel);
            }
        });
    }


    @Override
    public int getItemCount() {
        return usersDataList.size();
    }


    class UserItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_email)
        TextView txtEmail;
        @BindView(R.id.txt_phone)
        TextView txtPhone;
        @BindView(R.id.txt_website)
        TextView txtWebsite;

        UserItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void render(UserPresentationModel userPresentationModel) {
            txtName.setText(userPresentationModel.getName());
            txtEmail.setText(userPresentationModel.getEmail());
            txtPhone.setText(userPresentationModel.getPhone());
            txtWebsite.setText(userPresentationModel.getWebsite());
        }
    }
}
