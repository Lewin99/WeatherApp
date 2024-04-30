package org.me.gcu.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int FIRST_SCREEN=5000;
    //variables
    Animation anim;
    ImageView logo;
    TextView logoTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //animation
        anim= AnimationUtils.loadAnimation(this,R.anim.logonimation);

        //Hooks
        logo=findViewById(R.id.LogoImg);
        logoTitle=findViewById(R.id.logoTxtView);

        logo.setAnimation(anim);
        logoTitle.setAnimation(anim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              Intent intent=new Intent(MainActivity.this,Dashboard.class);
              startActivity(intent);
              finish();
            }
        },FIRST_SCREEN);


    }
}