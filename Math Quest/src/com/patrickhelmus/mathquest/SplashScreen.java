package com.patrickhelmus.mathquest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class SplashScreen extends Activity {
	 // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    //Set the on click listener
    private View.OnClickListener mStartGame = new View.OnClickListener()
 {
    	@Override
    	public void onClick(View arg0)
    	{
        Intent i = new Intent(SplashScreen.this, ComicPageOne.class);
        startActivity(i);
    	}
 };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_splash_screen);
        findViewById(R.id.startButton).setVisibility(View.INVISIBLE);
 
        new Handler().postDelayed(new Runnable() {
 
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
 
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Show start Button
            	findViewById(R.id.startButton).setVisibility(View.VISIBLE);
            	findViewById(R.id.startButton).setOnClickListener(mStartGame);
            	
            }
        }, SPLASH_TIME_OUT);
    }
}
