package org.me.gcu.weatherapp;

public class LondonWeatherData {
    private String day;
    private String weatherCondition;
    private String minTemperature;
    private String maxTemperature;
    private String windDirection;
    private String windSpeed;
    private String visibility;
    private String pressure;
    private String humidity;
    private String uvRisk;
    private String pollution;
    private String sunrise;
    private String sunset;
    private String latitude;
    private String longitude;

    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LondonWeatherData(String day, String weatherCondition, String minTemperature, String maxTemperature,
                             String windDirection, String windSpeed, String visibility, String pressure,
                             String humidity, String uvRisk, String pollution, String sunrise, String sunset,
                             String latitude, String longitude,String imageUrl) {
        this.day = day;
        this.weatherCondition = weatherCondition;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
        this.visibility = visibility;
        this.pressure = pressure;
        this.humidity = humidity;
        this.uvRisk = uvRisk;
        this.pollution = pollution;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageUrl=imageUrl;
    }

    // Getters and setters for all instance variables

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public String getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(String minTemperature) {
        this.minTemperature = minTemperature;
    }

    public String getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(String maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getUvRisk() {
        return uvRisk;
    }

    public void setUvRisk(String uvRisk) {
        this.uvRisk = uvRisk;
    }

    public String getPollution() {
        return pollution;
    }

    public void setPollution(String pollution) {
        this.pollution = pollution;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    // Omitted for brevity, but they should be implemented

    @Override
    public String toString() {
        return "WeatherData{" +
                "day='" + day + '\'' +
                ", weatherCondition='" + weatherCondition + '\'' +
                ", minTemperature='" + minTemperature + '\'' +
                ", maxTemperature='" + maxTemperature + '\'' +
                ", windDirection='" + windDirection + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                ", visibility='" + visibility + '\'' +
                ", pressure='" + pressure + '\'' +
                ", humidity='" + humidity + '\'' +
                ", uvRisk='" + uvRisk + '\'' +
                ", pollution='" + pollution + '\'' +
                ", sunrise='" + sunrise + '\'' +
                ", sunset='" + sunset + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", imageurl='" + imageUrl + '\'' +'}';
    }
}
