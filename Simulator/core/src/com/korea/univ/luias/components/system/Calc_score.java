package com.korea.univ.luias.components.system;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.korea.univ.luias.Main;
import com.korea.univ.luias.objects.Stone;

public class Calc_score {

	private static final Calc_score INSTANCE = new Calc_score();
	
	public static Calc_score getInstance(){
		return INSTANCE;
	}
	
	/**house center is
	 * xOffset = 2.745
	 * yOffset = 22.865
	 * 
	 * x = 39.63
	 * y = 2.135
	*/
	
	private float x = 2.140f;
	private float y = 38.53f;
	
	@SuppressWarnings("unused")
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
		
		// 1. get All stones
		ArrayList <Stone> stones = Main.stones;
		
		//temp var for store team num
		int team = -1;
		
		// 2. check the closest stone from house center
		int closest = 0;
		float c_dist = getDistance(stones.get(0).getBody().getPosition());
		System.out.println("1st c_dist : "+ c_dist);
		for(int i = 0; i < stones.size(); i++){
			
			float t_dist = getDistance(stones.get(i).getBody().getPosition());
			if(t_dist > 1.83)
				continue;
			
			if(c_dist > t_dist){
				closest = i;
				c_dist = t_dist;
				System.out.println(i+"st c_dist and t_dist in 1st loop :  "+ c_dist+", "+t_dist);
				team = stones.get(i).getTeam();
			}
			
		}
		
		//if stones not exist in house return no winner and score
		if(team == -1){
			if(c_dist > 1.83)
				return new int[]{ -1 , 0 };
		}
		
		// 3. check the closest stone from house center other team's
		int second = 0;
		c_dist = getDistance(stones.get(0).getBody().getPosition());
		System.out.println("2st c_dist : "+ c_dist);
		for(int i = 0; i < stones.size(); i++){
			
			float t_dist = getDistance(stones.get(i).getBody().getPosition());
			if(t_dist > 1.83 || stones.get(i).getTeam() == team)
				continue;
			
			if(c_dist > t_dist && stones.get(i).getTeam() != team){
				closest = i;
				c_dist = t_dist;
				System.out.println(i+"st c_dist and t_dist in 2st loop :  "+ c_dist+", "+t_dist);
			}
			
		}
		
		// 4. count the stones of closest team what get from 2nd round 
		int score = 0;
		for(int i = 0; i < stones.size(); i++){
			
			float t_dist = getDistance(stones.get(i).getBody().getPosition());
			if(t_dist > 1.83 || stones.get(i).getTeam() != team)
				continue;
			
			if(c_dist > t_dist && stones.get(i).getTeam() == team){
				score++;
			}
			
		}
		
		// 5. set the Score
		return new int[]{team,score};
	}
	
	public float getDistance(Vector2 position){
		return (float)(Math.sqrt( (Math.pow(position.x-x, 2)) + (Math.pow(position.y-y, 2)) ));
	}
	
}
