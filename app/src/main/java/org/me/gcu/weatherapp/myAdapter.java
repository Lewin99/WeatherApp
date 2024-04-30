package org.me.gcu.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myViewHolder> {

    Context context;
    List<Item> items;
    OnItemClickListener listener;

    public myAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context).inflate(R.layout.links_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Item currentItem = items.get(position);
        holder.locationName.setText(currentItem.getLocationName());
        holder.weatherCondition.setText(currentItem.getWeatherCondition());
        holder.Temperature.setText(currentItem.getTemperature() + "°C");
        holder.Humidity.setText(currentItem.getHumidity() + "%");
        holder.WindSpeed.setText(currentItem.getWindSpeed() + "mph");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(currentItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateItems(List<Item> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }
}
