package com.patrickhelmus.mathquest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ComicPageFour extends Activity {
	 private View.OnClickListener mStartGame = new View.OnClickListener()
	  {
	     	@Override
	     	public void onClick(View arg0)
	     	{
	         Intent i = new Intent(ComicPageFour.this, MainActivity.class);
	         startActivity(i);
	     	}
	  };
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.activity_comic_page_four);
	     findViewById(R.id.end).setOnClickListener(mStartGame);
	 }

}
