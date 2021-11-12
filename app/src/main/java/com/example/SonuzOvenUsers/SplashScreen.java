package com.example.SonuzOvenUsers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.SonuzOvenUsers.R;

public class SplashScreen extends AppCompatActivity {

    private int TIMER =3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        setSplashscreen startsplashscreen = new setSplashscreen();
        startsplashscreen.start();

    }
    private class setSplashscreen extends Thread{
        public void run() {
            try{
            sleep(1000 * TIMER);
            }catch (InterruptedException e){
            e.printStackTrace();
            }

            Intent intent = new Intent(SplashScreen.this,MainActivity.class);
            startActivity(intent);
            SplashScreen.this.finish();
        }
    }



}