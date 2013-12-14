package com.patrickhelmus.mathquest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ComicPageOne extends Activity {
	
    private View.OnClickListener pageTwo = new View.OnClickListener()
 {
    	@Override
    	public void onClick(View arg0)
    	{
        Intent i = new Intent(ComicPageOne.this, ComicPageTwo.class);
        startActivity(i);
    	}
 };
 
 @Override
 protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_comic_page_one);
     findViewById(R.id.advandeToPageTwo).setOnClickListener(pageTwo);
 }

}
