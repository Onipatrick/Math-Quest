package com.patrickhelmus.mathquest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ComicPageTwo extends Activity {
	  private View.OnClickListener mGoToPageThree = new View.OnClickListener()
	  {
	     	@Override
	     	public void onClick(View arg0)
	     	{
	         Intent i = new Intent(ComicPageTwo.this, ComicPageThree.class);
	         startActivity(i);
	     	}
	  };
	  
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.activity_comic_page_two);
	      findViewById(R.id.advancePageThree).setOnClickListener(mGoToPageThree);
	  }

}
