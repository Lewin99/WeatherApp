package org.me.gcu.weatherapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

public class MauritiusFragment extends Fragment {
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    private TextView textView_2nd_Day_name;
    private TextView textView_3rd_day_name;
    private TextView Day;
    private TextView Max_temperature;
    private TextView Min_temperature;
    private ImageView Icon;
    private TextView Weather_condition;
    private TextView Humidity;
    private TextView Pressure;
    private TextView Wind;
    private TextView Visibility;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mauritius, container, false);

        new FetchMauritiusWeatherTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        Day = view.findViewById(R.id.Textview_day);
        Max_temperature = view.findViewById(R.id.Textview_maxTemp);
        Min_temperature = view.findViewById(R.id.Textview_minTemp);
        Icon = view.findViewById(R.id.ImageView_icon);
        Weather_condition = view.findViewById(R.id.Textview_weatherCondition);
        Humidity = view.findViewById(R.id.Textview_humidity);
        Pressure = view.findViewById(R.id.Textview_pressure);
        Wind = view.findViewById(R.id.Textview_Wind);
        Visibility = view.findViewById(R.id.Textview_visibility);

        viewPager = view.findViewById(R.id.viewPager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        textView_2nd_Day_name = view.findViewById(R.id.textView_2nd_Day_name);
        textView_3rd_day_name = view.findViewById(R.id.textView_3rd_day_name);

        textView_2nd_Day_name.setOnClickListener(v -> viewPager.setCurrentItem(0));
        textView_3rd_day_name.setOnClickListener(v -> viewPager.setCurrentItem(1));

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                removeBottomBorder(textView_2nd_Day_name);
                removeBottomBorder(textView_3rd_day_name);

                if (position == 0) {
                    setBottomBorder(textView_2nd_Day_name);
                } else if (position == 1) {
                    setBottomBorder(textView_3rd_day_name);
                }
            }
        });

        return view;
    }

    private void setBottomBorder(TextView textView) {
        textView.setBackgroundResource(R.drawable.bottom_border);
    }

    private void removeBottomBorder(TextView textView) {
        textView.setBackground(null);
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(MauritiusFragment fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return new Mauritius_2nd_DayFragment();
            } else if (position == 1) {
                return new Mauritius_3rd_DayFragment();
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }

    private class FetchMauritiusWeatherTask extends AsyncTask<Void, Void, List<MauritiusWeatherData>> {
        @SuppressWarnings("deprecation")
        @SuppressLint("StaticFieldLeak")
        @Override
        protected List<MauritiusWeatherData> doInBackground(Void... voids) {
            apiCall apiCallInstance = new apiCall();
            try {
                InputStream inputStream = apiCallInstance.callWeatherAPI("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/934154");
                MauritiusWeatherParser parser = new MauritiusWeatherParser();
                return parser.parse(inputStream);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<MauritiusWeatherData> weatherDataList) {
            if (weatherDataList != null && !weatherDataList.isEmpty()) {
                textView_2nd_Day_name.setText(weatherDataList.get(1).getDay());
                textView_3rd_day_name.setText(weatherDataList.get(2).getDay());
                MauritiusWeatherData firstDayData = weatherDataList.get(0);

                Day.setText(firstDayData.getDay());
                Max_temperature.setText(extractCelsiusTemperature(firstDayData.getMaxTemperature()));
                Min_temperature.setText(extractCelsiusTemperature(firstDayData.getMinTemperature()));
                String[] weatherParts = firstDayData.getWeatherCondition().split(", ");
                String weatherConditionText = weatherParts[0]; // This should be the weather condition without the minimum temperature
                Weather_condition.setText(weatherConditionText);
                Humidity.setText(firstDayData.getHumidity());
                Pressure.setText(firstDayData.getPressure());
                Wind.setText(firstDayData.getWindSpeed());
                Visibility.setText(firstDayData.getVisibility());

                String imageUrl = firstDayData.getImageUrl();
                new LoadImageTask(Icon).execute(imageUrl);

                // Set up the click listener for the mapIcon ImageView
                ImageView mapIcon = getView().findViewById(R.id.imageView_mapicon);
                mapIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (weatherDataList != null && !weatherDataList.isEmpty()) {
                            // Log the latitude and longitude values before passing them
                            double latitude = Double.parseDouble(weatherDataList.get(1).getLatitude());
                            double longitude = Double.parseDouble(weatherDataList.get(1).getLongitude());
                            Log.d("MauritiusFragment", "Latitude: " + latitude + ", Longitude: " + longitude);

                            Intent intent = new Intent(getActivity(), MauritiusMap.class);
                            intent.putExtra("latitude", latitude);
                            intent.putExtra("longitude", longitude);
                            startActivity(intent);
                        } else {
                            Log.e("MauritiusFragment", "No weather data available to display on map.");
                        }
                    }
                });
            } else {
                Log.e("FetchWeatherTask", "Failed to fetch Mauritius weather data.");
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

    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;

        public LoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}
