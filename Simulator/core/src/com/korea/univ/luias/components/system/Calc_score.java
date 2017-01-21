package com.korea.univ.luias.components.system;

public class Calc_score {

	private static final Calc_score INSTANCE = new Calc_score();
	
	public static Calc_score getInstance(){
		return INSTANCE;
	}
	
	public int[] calc(){
		/**
		 * index 0 : winner team
		 * index 1 : score
		*/
		
		/** 
		 * - how to calculate -
		 * 1. get All stones
		 * 2. check the closest stone from house center
		 * 3. check the closest stone from house center other team's
		 * 4. count the stones of closest team what get from 2nd round
		 * 5. set the end's score
		*/
		
		return null;
	}
	
}
