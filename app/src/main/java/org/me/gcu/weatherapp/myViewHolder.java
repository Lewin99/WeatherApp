package org.me.gcu.weatherapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class myViewHolder extends RecyclerView.ViewHolder{

    TextView locationName;
    TextView weatherCondition;
    TextView Temperature;
    TextView Humidity;
    TextView WindSpeed;

    public myViewHolder(@NonNull View itemView) {
        super(itemView);

        locationName=itemView.findViewById(R.id.location_name);
        weatherCondition=itemView.findViewById(R.id.weather_condition);
        Temperature=itemView.findViewById(R.id.temp);
        Humidity=itemView.findViewById(R.id.humidity);
        WindSpeed=itemView.findViewById(R.id.wind_speed);


    }
}
