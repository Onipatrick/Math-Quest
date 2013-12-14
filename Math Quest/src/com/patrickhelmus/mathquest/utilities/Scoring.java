package com.patrickhelmus.mathquest.utilities;

public class Scoring {
	
	private static int numberCorrect;	
	private static int numberAttempted;
	
	public static int getnumberAttempted(){
		return numberAttempted;
	}
	
	public static int getnumberCorrect(){
		return numberCorrect;
	}
	
	public static void putScore(int numberAttemptedUpdate, int numberCorrectUpdate){
		
		numberCorrect = numberCorrectUpdate;
		numberAttempted = numberAttemptedUpdate;
		
	}

}
