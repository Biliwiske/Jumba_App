package com.kiselev.jumba.middle.store;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kiselev.jumba.R;

import java.util.ArrayList;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {
    private final RecyclerViewClickListener recyclerViewClickListener;
    private Context context;
    private ArrayList<Store> store;

    public StoreAdapter(Context context, ArrayList<Store> store, RecyclerViewClickListener recyclerViewClickListener) {
        this.context = context;
        this.store = store;
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item, parent, false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        Store currentStore = store.get(position);

        holder.name.setText(currentStore.getName());
        holder.cost.setText(String.valueOf(currentStore.getCost()));
        holder.description.setText(currentStore.getDescription());
        holder.image.setImageResource(currentStore.getImage());
    }

    @Override
    public int getItemCount() {
        return store.size();
    }

    public class StoreViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView name, cost, description;

        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.photo);
            name = itemView.findViewById(R.id.name_card);
            cost = itemView.findViewById(R.id.cost_card);
            description = itemView.findViewById(R.id.description);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                recyclerViewClickListener.onClick(position);
            }
        }
    }

    public interface RecyclerViewClickListener {
        void onClick(int position);
    }
}
