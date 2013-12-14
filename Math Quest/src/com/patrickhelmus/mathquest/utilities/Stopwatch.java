package com.patrickhelmus.mathquest.utilities;

public class Stopwatch 
{
	// Public constants
	public static final int MILLIS  = 0;
	public static final int SECONDS = 1;
	public static final int MINUTES = 2;
	
	// Private constants
	private static final long FROM_MILLIS_TO_SECONDS = 1000;
	private static final long FROM_MILLIS_TO_MINUTES = 60000;
	
    // Fields
    private long startTime  = 0;
    private long stopTime   = 0;
    private boolean running = false;

    /** Start the stopwatch. */
    public void start() 
    {
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }

    /** Stop the stopwatch. */
    public void stop() 
    {
        this.stopTime = System.currentTimeMillis();
        this.running = false;
    }
    
    /** Returns the elapsed time in milliseconds. */
    public long getElapsedTime(int type) 
    {
    	// To store elapsed time
        long elapsedTime;
        
        // Amount of conversion
        long conversionAmount;
        
        // Convert to seconds
        if (type == SECONDS)
        	conversionAmount = FROM_MILLIS_TO_SECONDS;
        
        // Convert to minutes
        else if (type == MINUTES)
        	conversionAmount = FROM_MILLIS_TO_MINUTES;
        
        // Leave it at milliseconds
        else
        	conversionAmount = 1;
        
        // If the stopwatch is still running
        if (running) 
             elapsedTime = (System.currentTimeMillis() - startTime) / conversionAmount;
        
        // If the stopwatch has been stopped
        else 
            elapsedTime = (stopTime - startTime) / conversionAmount;
        
        // Return the elapsed time
        return elapsedTime;
    } 
}