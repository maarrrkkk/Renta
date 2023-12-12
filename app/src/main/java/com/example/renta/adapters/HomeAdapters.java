package com.example.renta.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renta.R;
import com.example.renta.listeners.ItemListener;
import com.example.renta.model.Item;

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
        holder.shortDescription.setText(currentItem.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView price;
        private final TextView location;
        private final TextView shortDescription;
        private final RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            price = itemView.findViewById(R.id.price);
            location = itemView.findViewById(R.id.location);
            shortDescription = itemView.findViewById(R.id.short_description);
            relativeLayout = itemView.findViewById(R.id.relative_layout);

            itemView.setOnClickListener(v -> itemListener.OnItemPosition(getAdapterPosition()));
        }
    }
}
