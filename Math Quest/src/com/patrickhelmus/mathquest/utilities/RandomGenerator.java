package com.patrickhelmus.mathquest.utilities;

import java.util.Random;

public class RandomGenerator {
	
public static int generateRandom(int highestNumber, int lowestNumber){
		
	Random r = new Random();
	
	
	int High = highestNumber;
	int Low = lowestNumber;
	
	int randomGeneratedNumber = r.nextInt(High-Low) + Low;
	
	return randomGeneratedNumber;
		
	}

}
