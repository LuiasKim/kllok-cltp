package com.korea.univ.luias.components.system;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.korea.univ.luias.Main;
import com.korea.univ.luias.objects.Stone;

public class GameController {
	
	public void checkGameStatus(World world, ArrayList <Stone> array, Array <Stone> stones) {
		/**
		 * check every time. 1. check throw count 2. if end of throwing,
		 * calculate score ( Calc_score ) 3. set the waiting throw for other's
		 * stone stop
		 */	
		
		if (Main.total >= 16) {
			if (Main.stones.get(Main.stones.size()-1).waitThis())
				return;

			Main.isStarted = false;
			int[] result = Calc_score.getInstance().calc();
			
			for(int i = 0; i < stones.size; i++)
				stones.get(i).remove();
			
			stones.clear();
			
			for(int i = 0; i < array.size(); i++)
				array.get(i).setVisible(true);
			
			setScore(world, result);
		}

	}

	public void nextEnd(World world) {
		
		for(int i = 0; i < Main.stones.size() ;i++)
			world.destroyBody(Main.stones.get(i).getBody());
		
		for(int i = 0; i < Main.stones.size(); i++)
			Main.stones.get(i).remove();
		
		Main.stones.clear();
		Main.end++;
		Main.rthrowCount = 0;
		Main.ythrowCount = 0;
		Main.total = 0;
	}

	public void restart() {
		resetScore();
		Main.total = 0;
		Main.rthrowCount = 0;
		Main.ythrowCount = 0;
		Main.end = 1;
	}

	public void setScore(World world, int[] result) {
		System.out.println(result[0]+","+result[1]);
		
		if (result[0] == -1) {
			Main.scoreBoard[0][Main.end - 1] = result[1];
			Main.scoreBoard[1][Main.end - 1] = result[1];
			Main.current = Main.userTeam;
		}else if (result[0] == 0) {
			Main.scoreBoard[0][Main.end - 1] = result[1];
			Main.scoreBoard[1][Main.end - 1] = 0;
			Main.current = result[0];
		}else{
			Main.scoreBoard[0][Main.end - 1] = 0;
			Main.scoreBoard[1][Main.end - 1] = result[1];
			Main.current = result[0];
			
		}

		nextEnd(world);

		Main.isStarted = true;
	}

	public void resetScore() {

		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 10; j++)
				Main.scoreBoard[i][j] = -1;

	}
	

}
