package edu.matc.chat;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.TimerTask;
import java.util.Timer;

public class Splash extends AppCompatActivity
{
    AnimationDrawable splashAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        // How to show icon in action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        //sets up animation
        ImageView imgFrame = (ImageView)findViewById(R.id.imageView);
        imgFrame.setBackgroundResource(R.drawable.animation);
        splashAnimation=(AnimationDrawable)imgFrame.getBackground();

        splashAnimation.start();


        TimerTask task= new TimerTask()
        {
            @Override
            public void run()
            {



                finish();
                startActivity(new Intent(Splash.this, MainActivity.class));
            }


        };

        Timer opening = new Timer();
        opening.schedule(task, 3000);






    }
}
