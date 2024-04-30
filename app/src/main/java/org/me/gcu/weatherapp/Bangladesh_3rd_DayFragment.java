package org.me.gcu.weatherapp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Bangladesh_3rd_DayFragment extends Fragment {

    private TextView Day;
    private TextView Max_temperature;
    private TextView Min_temperature;
    private TextView Weather_condition;
    private TextView Humidity;
    private TextView Pressure;
    private TextView Wind;
    private TextView Visibility;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_glasgow_2nd__day, container, false);
        new Bangladesh_3rd_DayFragment.FetchBangladeshWeatherTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        Day = view.findViewById(R.id.Textview_day);
        Max_temperature = view.findViewById(R.id.Textview_maxTemp);
        Min_temperature = view.findViewById(R.id.Textview_minTemp);
        Weather_condition = view.findViewById(R.id.Textview_weatherCondition);
        Humidity = view.findViewById(R.id.Textview_humidity);
        Pressure = view.findViewById(R.id.Textview_pressure);
        Wind = view.findViewById(R.id.Textview_Wind);
        Visibility = view.findViewById(R.id.Textview_visibility);

        return view;
    }

    private class FetchBangladeshWeatherTask extends AsyncTask<Void, Void, List<BangladeshWeatherData>> {
        @SuppressWarnings("deprecation")
        @SuppressLint("StaticFieldLeak")
        @Override
        protected List<BangladeshWeatherData> doInBackground(Void... voids) {
            apiCall apiCallInstance = new apiCall();
            try {
                InputStream inputStream = apiCallInstance.callWeatherAPI("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/934154"); // Replace with the correct URL for Bangladesh
                BangladeshWeatherParser parser = new BangladeshWeatherParser();
                return parser.parse(inputStream);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<BangladeshWeatherData> weatherDataList) {
            if (weatherDataList != null && !weatherDataList.isEmpty()) {
                BangladeshWeatherData firstDayData = weatherDataList.get(2);

                Day.setText(firstDayData.getDay());
                Max_temperature.setText(extractCelsiusTemperature(firstDayData.getMaxTemperature()));
                Min_temperature.setText(extractCelsiusTemperature(firstDayData.getMinTemperature()));
                String[] weatherParts = firstDayData.getWeatherCondition().split(", ");
                String weatherConditionText = weatherParts[0];
                Weather_condition.setText(weatherConditionText);
                Humidity.setText(firstDayData.getHumidity());
                Pressure.setText(firstDayData.getPressure());
                Wind.setText(firstDayData.getWindSpeed());
                Visibility.setText(firstDayData.getVisibility());

            } else {
                Log.e("FetchWeatherTask", "Failed to fetch Bangladesh weather data.");
            }
        }
    }

    private String extractCelsiusTemperature(String temperature) {
        if (temperature != null && !temperature.isEmpty()) {
            // Split the temperature string by space and use the first part as the Celsius temperature
            String[] tempParts = temperature.split(" ");
            return tempParts[0]; // This should be the Celsius temperature
        }
        return "0"; // Return "0" if no temperature is available
    }

}
