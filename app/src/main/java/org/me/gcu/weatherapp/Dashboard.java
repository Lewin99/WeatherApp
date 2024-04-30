package org.me.gcu.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import org.xmlpull.v1.XmlPullParserException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.AlertDialog;

public class Dashboard extends AppCompatActivity implements myAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private myAdapter adapter;
    private List<Item> originalList = new ArrayList<>(); // Assuming Item is a class you have defined elsewhere
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerView = findViewById(R.id.recycle_review);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new myAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        searchView = findViewById(R.id.searchView);
        // Customize SearchView
        SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(getResources().getColor(android.R.color.white));
        searchAutoComplete.setTextColor(getResources().getColor(android.R.color.white));

        ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_button);
        searchIcon.setColorFilter(getResources().getColor(android.R.color.white));

        ImageView closeIcon = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        closeIcon.setColorFilter(getResources().getColor(android.R.color.white));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        String[] locations = {
                "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/2648579", // Glasgow
                "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/2643743", // London
                "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/5128581", // New York
                "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/287286", // Oman
                "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/934154", // Mauritius
                "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/1185241" // Bangladesh
        };

        if (isConnected()) {
            new FetchDataTask().execute(locations);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No Internet connection.");
            builder.setMessage("You have no internet connection");

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.show();
        }
    }

    @Override
    public void onItemClick(Item item) {
        Intent intent = new Intent(this, FragmentsView.class);
        intent.putExtra("locationName", item.getLocationName());
        startActivity(intent);
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            return (mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting());
        } else {
            return false;
        }
    }

    private class FetchDataTask extends AsyncTask<String[], Void, List<Item>> {
        @Override
        protected List<Item> doInBackground(String[]... urls) {
            List<Item> allItems = new ArrayList<>();
            for (String url : urls[0]) {
                try {
                    InputStream inputStream = new apiCall().callWeatherAPI(url);
                    String fetchedData = convertStreamToString(inputStream);
                    Log.d("FetchedData", fetchedData);

                    InputStream parsedInputStream = new ByteArrayInputStream(fetchedData.getBytes(StandardCharsets.UTF_8));
                    String locationName = extractLocationNameFromId(url);
                    XmlParser parser = new XmlParser(locationName);
                    List<Item> items = parser.parse(parsedInputStream);

                    allItems.addAll(items);
                } catch (IOException | XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
            return allItems;
        }

        private String extractLocationNameFromId(String url) {
            Map<String, String> locationMap = new HashMap<>();
            locationMap.put("2648579", "Glasgow");
            locationMap.put("2643743", "London");
            locationMap.put("5128581", "New York");
            locationMap.put("287286", "Oman");
            locationMap.put("934154", "Mauritius");
            locationMap.put("1185241", "Bangladesh");

            String[] parts = url.split("/");
            String locationId = parts[parts.length - 1];

            return locationMap.get(locationId);
        }

        @Override
        protected void onPostExecute(List<Item> items) {
            originalList.addAll(items); // Store the original list
            adapter.updateItems(items);
            adapter.notifyDataSetChanged();
        }

        private String convertStreamToString(InputStream is) {
            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }
    }

    // Method to filter items based on search query
    private void filter(String text) {
        List<Item> filteredItems = new ArrayList<>();
        for (Item item : originalList) {
            if (item.getLocationName().toLowerCase().contains(text.toLowerCase())) {
                filteredItems.add(item);
            }
        }
        adapter.updateItems(filteredItems);
    }
}
