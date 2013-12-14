package com.patrickhelmus.mathquest;


import org.json.JSONException;
import org.json.JSONObject;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.graphics.drawable.AnimationDrawable;

import com.patrickhelmus.mathquest.utilities.RandomGenerator;
import com.patrickhelmus.mathquest.utilities.Scoring;

public class MainActivity extends Activity {
	
	
	String[] problems;
	String[] solutions;
	static String operatorPressed = "placeholder";	
	String displayProblem;
	String displaySolution;
	
	int numberAttempted = 0;
	int numberCorrect = 0;
	
	//Chronometer classes
	TextView textGoesHere;
    long startTime;
    long countUp;
	
    NfcAdapter mNfcAdapter;
    PendingIntent mNfcPendingIntent;
    IntentFilter[] mReadTagFilters;
    IntentFilter[] mWriteTagFilters;

    AnimationDrawable walkingAnimation;
	AnimatorSet characterFadeIn;
	
	AnimatorSet[] moveAnimation;
	
	int animationState = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Fragment version of launching an intent
		//getFragmentManager().beginTransaction()
		//.add(R.id.main_container, new MainActivity())
		//.commit();
        // get an instance of the context's cached NfcAdapter
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        // if null is returned this demo cannot run. Use this check if the
        // "required" parameter of <uses-feature> in the manifest is not set
        if (mNfcAdapter == null)
        {
            Toast.makeText(this,
                    "Your device does not support NFC. Sorry.",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        // check if NFC is enabled
        checkNfcEnabled();

        // Handle foreground NFC scanning in this activity by creating a
        // PendingIntent with FLAG_ACTIVITY_SINGLE_TOP flag so each new scan
        // is not added to the Back Stack
        mNfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        // Create intent filter to handle NDEF NFC tags detected from inside our
        // application when in "read mode":
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try
        {
            ndefDetected.addDataType("application/com.patrickhelmus.mathquest");
        }
        catch (MalformedMimeTypeException e)
        {
            throw new RuntimeException("Could not add MIME type.", e);
        }

        // Create intent filter to detect any NFC tag when attempting to write
        // to a tag in "write mode"
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        
        // create IntentFilter arrays:
        mWriteTagFilters = new IntentFilter[] { tagDetected };
        mReadTagFilters = new IntentFilter[] { ndefDetected, tagDetected };
        
        //Prevents null pointer exception when fragment is regenerated
		if (savedInstanceState != null)
			return;
		
		//Gets scores from Scoring class to load into text view
		numberAttempted = Scoring.getnumberAttempted();
		numberCorrect = Scoring.getnumberCorrect();
		
		TextView score = (TextView) this.findViewById(R.id.txtScoreTotal);
		score.setText(numberAttempted + " / " + numberCorrect);
		
		//Grabs problems from strings xml and loads them into problems array object
		problems = getResources().getStringArray(R.array.problems);		
		//Grabs solutions from strings xml and loads them into solutions array object
		solutions = getResources().getStringArray(R.array.solutions);

		//Patrick's sweet sweet random number generator class		
		int random = RandomGenerator.generateRandom(solutions.length, 0);
				
		//Pulls a random problem and the associated solution based on the array index
		displayProblem = problems[random];
		displaySolution = solutions[random];
		
		//Loads the problem into the text view
		TextView problem = (TextView) this.findViewById(R.id.textProblem);
		problem.setText(displayProblem);
		
		
		Chronometer stopWatch = (Chronometer) findViewById(R.id.chrono);
        startTime = SystemClock.elapsedRealtime();

        textGoesHere = (TextView) findViewById(R.id.textgoeshere);
        stopWatch.setOnChronometerTickListener(new OnChronometerTickListener(){
            @Override
            public void onChronometerTick(Chronometer arg0) {
                countUp = (SystemClock.elapsedRealtime() - arg0.getBase()) / 1000;
                String asText = "countUp:" + (countUp); 
                textGoesHere.setText(asText);
            }
        });
        stopWatch.start();
		
		
		/*
		 * Buttons
		 */
		
		ImageButton add = (ImageButton) findViewById(R.id.btnAdd);
		add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View a) {
                // Perform action on click
            	//Sets an operatorPressed String value to check against the solution.
            	operatorPressed = "add";
            	checkAnswer(operatorPressed, displaySolution);
            }
        });
        
        ImageButton sub = (ImageButton) findViewById(R.id.btnSub);
        sub.setOnClickListener(new View.OnClickListener() {
            public void onClick(View s) {
                // Perform action on click
            	//Sets an operatorPressed String value to check against the solution.
            	operatorPressed = "sub";
            	checkAnswer(operatorPressed, displaySolution);
            }
        });
        
        ImageButton mul = (ImageButton) findViewById(R.id.btnMul);
        mul.setOnClickListener(new View.OnClickListener() {
            public void onClick(View m) {
                // Perform action on click
            	//Sets an operatorPressed String value to check against the solution.
            	operatorPressed = "mul";
            	checkAnswer(operatorPressed, displaySolution);
            }
        });
        
        ImageButton div = (ImageButton) findViewById(R.id.btnDiv);
        div.setOnClickListener(new View.OnClickListener() {
            public void onClick(View d) {
                // Perform action on click
            	//Sets an operatorPressed String value to check against the solution.
            	operatorPressed = "div";
            	checkAnswer(operatorPressed, displaySolution);
            }
        });
        
		//Animation drawable variable will be used to animate the character via XML animation list
		final AnimationDrawable walkingAnimation;
		//ImageView Variables for each drawable on the screen
		final ImageView walkingImage;
		//final ImageView cloudImage1;
		moveAnimation = new AnimatorSet[10];
		moveAnimation[0] = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.move_right_one);
		moveAnimation[1] = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.move_right_two);
		moveAnimation[2] = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.move_right_three);
		moveAnimation[3] = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.move_right_four);
		moveAnimation[4] = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.move_right_five);
		moveAnimation[5] = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.move_right_six);
		moveAnimation[6] = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.move_right_seven);
		moveAnimation[7] = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.move_right_eight);
		moveAnimation[8] = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.move_right_nine);
		moveAnimation[9] = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.move_right_ten);
		
		//draw the imageView, set the background to be the XML animation list
		//set the background of the animation Drawable to that of the imageView
		walkingImage = (ImageView) findViewById(R.id.mainCharacter); 
		walkingImage.setBackgroundResource(R.drawable.wallking); 

		moveAnimation[0].setTarget(walkingImage);
		moveAnimation[1].setTarget(walkingImage);
		moveAnimation[2].setTarget(walkingImage);
		moveAnimation[3].setTarget(walkingImage);
		moveAnimation[4].setTarget(walkingImage);
		moveAnimation[5].setTarget(walkingImage);
		moveAnimation[6].setTarget(walkingImage);
		moveAnimation[7].setTarget(walkingImage);
		moveAnimation[8].setTarget(walkingImage);
		moveAnimation[9].setTarget(walkingImage);

	    walkingAnimation = (AnimationDrawable) walkingImage.getBackground();
        walkingAnimation.start();
		// This causes a crash for some reason
        //characterFadeIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.fade_in);
		//characterFadeIn.start();
	}

    /* Called when the activity will start interacting with the user. */
    @Override
    protected void onResume()
    {
        super.onResume();

        // Double check if NFC is enabled
        checkNfcEnabled();

        // Enable priority for current activity to detect scanned tags
        // enableForegroundDispatch( activity, pendingIntent,
        // intentsFiltersArray, techListsArray );
        mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent,
                mReadTagFilters, null);

    }

	
	 private void checkNfcEnabled()
	 {
	     Boolean nfcEnabled = mNfcAdapter.isEnabled();
	     if (!nfcEnabled)
	     {
	         Log.i("MainActivity", "CheckNfcEnabled() - NFC is not enabled!!");
	     }
	 }

	 @Override
	 protected void onNewIntent(Intent intent)
	 {
	     Log.d("MainActivity", "onNewIntent called:" + intent);

         // Currently in tag READING mode
         if (intent.getAction().equals(NfcAdapter.ACTION_NDEF_DISCOVERED))
         {
             NdefMessage[] msg = getNdefMessagesFromIntent(intent);
             //confirmDisplayedContentOverwrite(msgs[0]);
             checkNfcChoice(msg[0]);
         }
	 }

	private void checkNfcChoice(final NdefMessage msg)
	{
	    // use the current values in the NDEF payload
	    // to update the text fields
	    String payload = new String(msg.getRecords()[0].getPayload());
	    String choice = parseNfcPayloadChoice(payload);
        
	    checkAnswer(choice, displaySolution);
	}
	
	String parseNfcPayloadChoice(String payload)
	{
        JSONObject inventory = null;
        String name = "";
        String ram = "";
        String processor = "";
        try
        {
            inventory = new JSONObject(payload);
            name = inventory.getString("name");
            ram = inventory.getString("ram");
            processor = inventory.getString("processor");
        } catch (JSONException e)
        {
            Log.e("parseNfcPayload", "Couldn't parse JSON: ", e);
        }

        return name;		
	}
	 
	 
	 NdefMessage[] getNdefMessagesFromIntent(Intent intent)
	 {
		// Parse the intent
		NdefMessage[] msgs = null;
		String action = intent.getAction();
		if (action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED))
		{
			Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			if (rawMsgs != null)
			{
				msgs = new NdefMessage[rawMsgs.length];
				for (int i = 0; i < rawMsgs.length; i++)
				{
					msgs[i] = (NdefMessage) rawMsgs[i];
				}
			}
			else
			{
				// Unknown tag type
				byte[] empty = new byte[] {};
				NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN,
						empty, empty, empty);
				NdefMessage msg = new NdefMessage(new NdefRecord[] { record });
				msgs = new NdefMessage[] { msg };
			}
		}
		else
		{
			Log.e("MainActivity", "Unknown intent.");
			finish();
		}
		return msgs;
	}
	
    private void generateNewProblem()
    {
		//Grabs problems from strings xml and loads them into problems array object
		problems = getResources().getStringArray(R.array.problems);		
		//Grabs solutions from strings xml and loads them into solutions array object
		solutions = getResources().getStringArray(R.array.solutions);

		//Patrick's sweet sweet random number generator class		
		int random = RandomGenerator.generateRandom(solutions.length, 0);
				
		//Pulls a random problem and the associated solution based on the array index
		displayProblem = problems[random];
		displaySolution = solutions[random];
		
		//Loads the problem into the text view
		TextView problem = (TextView) this.findViewById(R.id.textProblem);
		problem.setText(displayProblem);
    }
	 
	 
	public void checkAnswer(String operatorPressed, String displaySolution){
		
		//Adds one to number attempted
		numberAttempted++;
		
		//Loads score into text view
		TextView score = (TextView) this.findViewById(R.id.txtScoreTotal);
		score.setText(numberAttempted + " / " + numberCorrect);
		
		//Displays the values held in memory for operatorPressed and displaySolution
		toastMaster("Press:  " + operatorPressed + " Ans: " + displaySolution + 
				" Rand:" + solutions.length);
		/*
		 * Conditionals that test for correct/incorrect answer.
		 */
		
		//compareTo() returns 0 if the values are equal. 
        if (displaySolution.compareTo(operatorPressed) == 0){
        	
        	
        	//Adds one to numberCorrect
        	numberCorrect++;
        	
        	Scoring.putScore(numberAttempted, numberCorrect);
        	
        	//Restarts activity and generates new number
        	//Intent intent = getIntent();        	
            //finish();
            //startActivity(intent);
        	generateNewProblem();

            moveAnimation[animationState].start();
            animationState++;
            
            if(animationState == 6)
            {
                // This means they won! Launch the final screen
                Intent i = new Intent(MainActivity.this, ComicPageFour.class);
                startActivity(i);
            }
        }
        
        //compareTo() returns negative value if the values are different. 
        else if (displaySolution.compareTo(operatorPressed) != 0){
        	toastMaster(operatorPressed + " is incorrect.");
        }
        
        //If this comes up there's a problem with the conditional, think of it like a catch. 
        else{
        	toastMaster("User pressed" + operatorPressed + " something went wrong in the conditional.");
        }
	}
	
	public void toastMaster(String text){
		
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		
	}
	
	
	/* Comments out options menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	*/

}
