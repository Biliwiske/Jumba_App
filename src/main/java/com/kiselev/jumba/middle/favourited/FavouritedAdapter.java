package com.kiselev.jumba.middle.favourited;

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

public class FavouritedAdapter extends RecyclerView.Adapter<FavouritedAdapter.FavouritedViewHolder> {
    private final FavouritedAdapter.RecyclerViewClickListener recyclerViewClickListener;
    private Context context;
    private ArrayList<Favourited> favourited;

    public FavouritedAdapter(Context context, ArrayList<Favourited> favourited, FavouritedAdapter.RecyclerViewClickListener recyclerViewClickListener) {
        this.context = context;
        this.favourited = favourited;
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    @NonNull
    @Override
    public FavouritedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_fav, parent, false);
        return new FavouritedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritedViewHolder holder, int position) {
        Favourited currentFavourited = favourited.get(position);

        holder.name.setText(currentFavourited.getName());
        holder.description.setText(currentFavourited.getDescription());
        holder.cost.setText(String.valueOf(currentFavourited.getCost()));
        holder.image.setImageResource(currentFavourited.getImage());
    }

    @Override
    public int getItemCount() {
        return favourited.size();
    }

    public class FavouritedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView name, description, cost;
        public FavouritedViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.photo);
            name = itemView.findViewById(R.id.name_card_);
            description = itemView.findViewById(R.id.description_card_);
            cost = itemView.findViewById(R.id.cost_card_);

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
