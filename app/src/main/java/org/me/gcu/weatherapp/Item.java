package org.me.gcu.weatherapp;

import android.util.Log;

public class Item {
    private String locationName;
    private String weatherCondition;
    private int temperature;
    private int humidity;
    private int windSpeed;


    public Item(String locationName,String weatherCondition, String temperature, String humidity, String windSpeed) {
        this.locationName = locationName;
        this.weatherCondition = weatherCondition;
        this.temperature = parseInt(temperature);
        this.humidity = parseInt(humidity);
        this.windSpeed = parseInt(windSpeed);
    }

    private int parseInt(String value) {
        if ("-- ".equals(value.trim())) {
            // Return a default value if the string is "-- "
            return 0;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            // Log the error or handle it as appropriate for your application
            Log.e("ParsingError", "Failed to parse string into integer: " + value);
            return 0; // Return a default value or handle the error in another way
        }
    }


    // Add getter and setter for locationName
    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = Integer.parseInt(temperature);
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = Integer.parseInt(humidity);
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = Integer.parseInt(windSpeed);


    }
    @Override
    public String toString() {
        return "Item{" +
                "locationName='" + locationName + '\'' +
                ", weatherCondition='" + weatherCondition + '\'' +
                ", temperature=" + temperature +
                ", windSpeed=" + windSpeed +
                ", humidity=" + humidity +
                '}';
    }


}
