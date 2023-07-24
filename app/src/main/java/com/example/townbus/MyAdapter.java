package com.example.townbus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<Transport> list;

    public MyAdapter(Context context, ArrayList<Transport> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.transport_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Transport transport = list.get(position);

        holder.transportOperator.setText(transport.getTransportOperator());
        holder.transportFrom.setText(transport.getTransportFrom());
        holder.transportTo.setText(transport.getTransportTo());
        holder.dateOfJourney.setText(transport.getTransportDepartureDate().toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView transportOperator, transportFrom, transportTo, dateOfJourney;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            transportOperator = itemView.findViewById(R.id.operator_textview);
            transportFrom = itemView.findViewById(R.id.from_textview);
            transportTo = itemView.findViewById(R.id.to_textview);
            dateOfJourney = itemView.findViewById(R.id.date_textview);
        }
    }
}
