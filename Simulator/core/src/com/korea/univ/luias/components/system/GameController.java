package com.korea.univ.luias.components.system;

import com.korea.univ.luias.Main;

public class GameController {

	private static final GameController INSTANCE = new GameController();
	
	public static GameController getInstance(){
		return INSTANCE;
	}
	
	public void checkGameStatus(){
		/**
		 * check every time.
		 * 1. check throw count
		 * 2. if end of throwing, calculate score ( Calc_score )
		 * 3. set the waiting throw for other's stone stop
		 */
			
		if(Main.total >= 16){
			if(Main.stones.get(Main.total-1).waitThis())
				return;
			
			Main.isStarted = false;
			int[] result = Calc_score.getInstance().calc();
			
			setScore(result);
			
		}
		
	}
	
	public void restart(){
		resetScore();
		Main.total = 0;
		Main.rthrowCount = 0;
		Main.ythrowCount = 0;
		Main.end = 1;
	}
	
	public void setScore(int[] result){
		
		Main.scoreBoard[result[0]][Main.end-1] = result[1];
		
		Main.end++;
		
		Main.isStarted = true;
	}
	
	public void resetScore(){
			
		for(int i = 0 ; i < 2; i ++)
			for(int j = 0; j < 10; j++)
				Main.scoreBoard[i][j] = -1;
		
	}
	
	
	
}
