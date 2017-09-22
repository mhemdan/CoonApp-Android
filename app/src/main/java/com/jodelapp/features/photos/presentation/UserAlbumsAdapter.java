package com.jodelapp.features.photos.presentation;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;
import com.jodelapp.R;
import com.jodelapp.features.photos.models.AlbumPresentationModel;
import com.jodelapp.features.photos.models.PhotoPresentationModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by m.hemdan on 9/18/17.
 */

public class UserAlbumsAdapter extends RecyclerView.Adapter<UserAlbumsAdapter.ViewHolder> {

    private final List<AlbumPresentationModel> items;
    private Context context;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    private  UserPhotoListContract.Presenter presenter;

    public UserAlbumsAdapter(final List<AlbumPresentationModel> data) {
        this.items = data;
        for (int i = 0; i < data.size(); i++) {
            expandState.append(i, false);
        }
    }

    public void setPresenter(UserPhotoListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void setAlbumPhotos(int albumIndex,List<PhotoPresentationModel> photos){
        items.get(albumIndex).setPhotoPresentationModelList(photos);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_list_album, parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final AlbumPresentationModel item = items.get(position);
        holder.setIsRecyclable(false);
        holder.txtAlbumName.setText(item.getTitle());
        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        holder.expandableLayout.setInRecyclerView(true);
        holder.expandableLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
        holder.expandableLayout.setInterpolator(Utils.createInterpolator(Utils.LINEAR_OUT_SLOW_IN_INTERPOLATOR));
        holder.expandableLayout.setExpanded(expandState.get(position));
        if(item.getPhotoPresentationModelList()!=null) {
            UserPhotoAdapter adapter = new UserPhotoAdapter(item.getPhotoPresentationModelList());
            adapter.setAlbumName(item.getTitle());
            holder.lsUserPhotos.setAdapter(adapter);
        }
        holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                createRotateAnimator(holder.buttonLayout, 0f, 180f).start();
                expandState.put(position, true);
                presenter.getPhotosByAlbumID(String.valueOf(item.getId()),position);
            }

            @Override
            public void onPreClose() {
                createRotateAnimator(holder.buttonLayout, 180f, 0f).start();
                expandState.put(position, false);
            }
        });

        holder.buttonLayout.setRotation(expandState.get(position) ? 180f : 0f);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickButton(holder.expandableLayout);
            }
        });

    }

    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_album_name)
        public TextView txtAlbumName;
        @BindView(R.id.button)
        public RelativeLayout buttonLayout;
        /**
         * You must use the ExpandableLinearLayout in the recycler view.
         * The ExpandableRelativeLayout doesn't work.
         */
        @BindView(R.id.expandableLayout)
        public ExpandableLinearLayout expandableLayout;

        @BindView(R.id.ls_user_photos)
        RecyclerView lsUserPhotos;
        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            lsUserPhotos.setLayoutManager(linearLayoutManager);
        }
    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
}