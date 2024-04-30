package org.me.gcu.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class FragmentsView extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TextView glasgowLink, londonLink, newyorkLink, omanLink, mauritiusLink, bangladeshLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments_view);

        // Initialize ViewPager2 and set its adapter
        viewPager = findViewById(R.id.viewPager);
        FragmentsAdapter adapter = new FragmentsAdapter(this);
        viewPager.setAdapter(adapter);

        // Get the location name from the intent and find its position
        String locationName = getIntent().getStringExtra("locationName");
        int position = getPositionForLocationName(locationName);
        // Set the current item without smooth scrolling animation
        viewPager.setCurrentItem(position, false);

        // Setup TextViews for navigation
        setupTextViews();
        // Set the initial border for the TextView corresponding to the location passed via the intent
        setInitialBorder(locationName);

        // Register a callback to update the bottom border when the page changes
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Remove bottom border from all TextViews
                removeBottomBorder(glasgowLink);
                removeBottomBorder(londonLink);
                removeBottomBorder(newyorkLink);
                removeBottomBorder(omanLink);
                removeBottomBorder(mauritiusLink);
                removeBottomBorder(bangladeshLink);

                // Set bottom border to the TextView corresponding to the new page position
                switch (position) {
                    case 0:
                        setBottomBorder(glasgowLink);
                        break;
                    case 1:
                        setBottomBorder(londonLink);
                        break;
                    case 2:
                        setBottomBorder(newyorkLink);
                        break;
                    case 3:
                        setBottomBorder(omanLink);
                        break;
                    case 4:
                        setBottomBorder(mauritiusLink);
                        break;
                    case 5:
                        setBottomBorder(bangladeshLink);
                        break;
                }
            }
        });

        // Set up BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.bottombaritem_home) {
                    // Start the HomeActivity when the home menu item is clicked
                    Intent homeIntent = new Intent(FragmentsView.this, Dashboard.class);
                    startActivity(homeIntent);
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });
    }



    private void setupTextViews() {
        // Find TextViews by their IDs
        glasgowLink = findViewById(R.id.link_Glasgow);
        londonLink = findViewById(R.id.link_London);
        newyorkLink = findViewById(R.id.link_Newyork);
        omanLink = findViewById(R.id.link_Oman);
        mauritiusLink = findViewById(R.id.link_Mauritius);
        bangladeshLink = findViewById(R.id.link_Bangladesh);

        // Set onClickListeners for each TextView to navigate to the corresponding fragment
        glasgowLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
                setBottomBorder(glasgowLink);
            }
        });
        londonLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
                setBottomBorder(londonLink);
            }
        });
        newyorkLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
                setBottomBorder(newyorkLink);
            }
        });
        omanLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(3);
                setBottomBorder(omanLink);
            }
        });
        mauritiusLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(4);
                setBottomBorder(mauritiusLink);
            }
        });
        bangladeshLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(5);
                setBottomBorder(bangladeshLink);
            }
        });
    }

    private int getPositionForLocationName(String locationName) {
        // Map location names to their corresponding positions
        switch (locationName) {
            case "Glasgow":
                return 0;
            case "London":
                return 1;
            case "New York":
                return 2;
            case "Oman":
                return 3;
            case "Mauritius":
                return 4;
            case "Bangladesh":
                return 5;
            default:
                return 0;
        }
    }

    private void setBottomBorder(TextView textView) {
        // Set the bottom border for the given TextView
        textView.setBackgroundResource(R.drawable.bottom_border);
    }

    private void removeBottomBorder(TextView textView) {
        // Remove the bottom border from the given TextView
        textView.setBackground(null);
    }

    private void setInitialBorder(String locationName) {
        // Remove bottom border from all TextViews
        removeBottomBorder(glasgowLink);
        removeBottomBorder(londonLink);
        removeBottomBorder(newyorkLink);
        removeBottomBorder(omanLink);
        removeBottomBorder(mauritiusLink);
        removeBottomBorder(bangladeshLink);

        // Set bottom border to the TextView corresponding to the location name
        switch (locationName) {
            case "Glasgow":
                setBottomBorder(glasgowLink);
                break;
            case "London":
                setBottomBorder(londonLink);
                break;
            case "New York":
                setBottomBorder(newyorkLink);
                break;
            case "Oman":
                setBottomBorder(omanLink);
                break;
            case "Mauritius":
                setBottomBorder(mauritiusLink);
                break;
            case "Bangladesh":
                setBottomBorder(bangladeshLink);
                break;
        }
    }
}
