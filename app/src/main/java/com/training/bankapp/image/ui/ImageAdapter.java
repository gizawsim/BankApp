package com.training.bankapp.image.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.training.bankapp.R;
import com.training.bankapp.data.remote.model.ImageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter responsible for displaying the information
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context context;
    private LayoutInflater inflater;

    private List<ImageItem> images = new ArrayList<>();

    public ImageAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setImages(List<ImageItem> data) {
        images.clear();
        images.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.rv_image, parent, false);
        return new ImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ImageItem image = images.get(position);
        Glide.with(context.getApplicationContext()).load(image.getUrl())
                .placeholder(R.drawable.ic_baseline_person_24)
                .into(holder.imgUrl);
        holder.txtTitle.setText(image.getTitle());
        holder.txtLike.setText(String.valueOf(image.getLikes()));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imgUrl;
        TextView txtTitle;
        TextView txtLike;

        public ImageViewHolder(View itemView) {
            super(itemView);

            imgUrl = itemView.findViewById(R.id.img_rv);
            txtTitle = itemView.findViewById(R.id.txt_title_rv);
            txtLike = itemView.findViewById(R.id.txt_likes_rv);
        }

    }
}
