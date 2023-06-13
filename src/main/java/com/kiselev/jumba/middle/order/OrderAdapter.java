package com.kiselev.jumba.middle.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kiselev.jumba.R;
import com.kiselev.jumba.middle.store.Store;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private Context context;
    private ArrayList<Order> order;

    public OrderAdapter(Context context, ArrayList<Order> order) {
        this.context = context;
        this.order = order;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order currentOrder = order.get(position);
        holder.date.setText(currentOrder.getDate());
        holder.name.setText(currentOrder.getProduct());
        holder.status.setText(currentOrder.getStatus());
        holder.image.setImageResource(currentOrder.getImage());
    }

    @Override
    public int getItemCount() {
        return order.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name, date, status;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.photo);
            name = itemView.findViewById(R.id.name_card);
            date = itemView.findViewById(R.id.date_card);
            status = itemView.findViewById(R.id.status_card);
        }
    }
}
