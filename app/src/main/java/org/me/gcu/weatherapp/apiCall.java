package org.me.gcu.weatherapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class apiCall {
    public InputStream callWeatherAPI(String apiUrl) throws IOException {
        // Make the HTTP request to the API
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Check the HTTP response code
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // If the response is successful, return the input stream
            return connection.getInputStream();
        } else {
            // If the response is not successful, throw an exception or handle it accordingly
            throw new IOException("API call failed with response code: " + responseCode);
        }
    }
}

