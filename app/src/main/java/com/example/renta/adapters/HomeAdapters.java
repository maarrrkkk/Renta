package com.example.renta.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.renta.R;
import com.example.renta.listeners.ItemListener;
import com.example.renta.model.Item;

import java.text.BreakIterator;
import java.util.List;


public class HomeAdapters extends RecyclerView.Adapter<HomeAdapters.ViewHolder> {

    private final Context context;
    private final List<Item> itemList;
    private final ItemListener itemListener;

    public HomeAdapters(Context context, List<Item> itemList, ItemListener itemListener) {
        this.context = context;
        this.itemList = itemList;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.top_deals, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item currentItem = itemList.get(position);

        holder.price.setText(currentItem.getPrice());
        holder.location.setText(currentItem.getLocation());
        holder.category.setText(currentItem.getCategory());
        holder.name.setText(currentItem.getName());
        Glide.with(context)
                .load(itemList.get(position).getImage())
                .placeholder(R.drawable.room)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.backgroundImageView.setImageDrawable(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // Handle placeholder cleanup if needed
                    }
                });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView price;
        private final TextView location;
        private final TextView category;
        private final TextView name;

        private final ImageView backgroundImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            price = itemView.findViewById(R.id.price);
            location = itemView.findViewById(R.id.location);
            category = itemView.findViewById(R.id.category);
            name = itemView.findViewById(R.id.name);

            backgroundImageView = itemView.findViewById(R.id.backgroundImageView);

            itemView.setOnClickListener(v -> itemListener.OnItemPosition(getAdapterPosition()));
        }
    }
}
