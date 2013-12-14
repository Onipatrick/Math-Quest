package com.patrickhelmus.mathquest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ComicPageThree extends Activity {
	
	  private View.OnClickListener mStartGame = new View.OnClickListener()
	  {
	     	@Override
	     	public void onClick(View arg0)
	     	{
	         Intent i = new Intent(ComicPageThree.this, MainActivity.class);
	         startActivity(i);
	     	}
	  };
	  
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.activity_comic_page_three);
	      findViewById(R.id.startGame).setOnClickListener(mStartGame);
	  }

}
