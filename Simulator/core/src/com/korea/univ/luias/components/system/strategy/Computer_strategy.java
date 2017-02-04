package com.korea.univ.luias.components.system.strategy;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.korea.univ.luias.Main;
import com.korea.univ.luias.components.Half_view;
import com.korea.univ.luias.components.system.strategy.variables.Strategy_parameter;
import com.korea.univ.luias.objects.Stone;

public class Computer_strategy{

	/* conditions... 
	 * 
	 * 1. if in house other team's stone exist?
	 * 
	 * 	-- check the closest stone other team's from house center
	 *  -- shot my stone after check my stone is close from house center?
	 *  -- try again again again again...
	 *  -- calculate score
	 *  -- apply that shot
	 *  
	 * 2. if not exist stones in house?
	 * 
	 *  -- shot my stone after check my stone is close from house center?
	 *  -- try again again again again....
	 *  -- calculate score
	 *  -- apply that shot
	 *  
	 * 3. if exist stone in house only my team's?
	 * 
	 *  -- shot my stone after check my stone is close from house center?
	 *  -- don't touched my other stone?
	 *  -- try again again again again....
	 *  -- calculate score
	 *  -- apply that shot
	 *  
	 */
	
	private World mWorld;
	
	private boolean nowSimulating = false;
	private int team;
	
	private Half_view h_view;
	private Stage stage;
	
	public Computer_strategy(World world, Half_view h_view){
		

		stage = new Stage();
		this.mWorld = world;
		this.h_view = h_view;
	
	}
	/*
	public void startSimulation(){
		nowSimulating = true;
		// 1. set the world
		shots = new ArrayList <Strategy_parameter>();
		
		// 2. analysis current situation
		setWorld();
		stones_shoted.clear();
		final int situation = analyze();
		System.out.println("current Situation : "+situation);
		// 3. seek the best shot
		new Thread(new Runnable(){
			@Override
			public void run(){
				
				for(try_count = 1; try_count <= 5; try_count++){
					// 4. shot the random
					if(shots.size() < 1){
						Strategy_parameter parameter = generateParameter(situation,previous_shot);
						shots.add(parameter);
						shotStone(parameter);
						System.out.println("Parameter attr : "+parameter.toString());
						previous_shot = parameter;
					}else{
						Strategy_parameter parameter = generateParameter(situation,previous_shot);
						shots.add(parameter);
						shotStone(parameter);
						System.out.println("Parameter attr : "+parameter.toString());
						previous_shot = parameter;
					}
					// 5. calculate score each shot
					
					while(true){
						try{
							Thread.sleep(100);
						}catch(Exception e){}
						if(!stones_shoted.get(stones_shoted.size()-1).waitThis())
							break;
						
						//if(!stones_shoted.get(try_count-1).waitThis())
						//	break;
						//stones_shoted.get(try_count-1).update(0.0001f);
					}
					
					calc_shotScore(try_count,situation);
					// 6. resetWorld
					resetWorld();	
					// 7. go to step 3
				}	
				int best = selectShot(shots);
				System.out.println("best : "+best+", params : "+shots.get(best).toString());
				Strategy_parameter parameter = shots.get(best);
				
				Main.stones.add(new Stone(mWorld, 2.140f, 3.78f, Main.current,
						(Main.current == 0) ? h_view.redStone : h_view.yellowStone, parameter.getPower(),
						(int) parameter.getAngle(), parameter.getCurl(), 0.3f, 0.3f, Main.total + 1));
				if (Main.current == 0)
					Main.rthrowCount++;
				else
					Main.ythrowCount++;
				
				Main.total ++;

				Main.current = Main.current == 0 ? 1 : 0;
				destroyWorld();
				
				nowSimulating = false;
			}
		}).start();
		
		// 8. select best score shot
		
		// 8. destroy world
		//destroyWorld();
		// 9. return parameter
	}
	*/
	
	
	
	public int selectShot(ArrayList<Strategy_parameter> parameters){
		
		int temp = 0;
		System.out.println("parameter sizes : "+parameters.size());
		for(int i = 1; i < parameters.size(); i++){
			System.out.println(temp+"st score : "+parameters.get(temp).getScore()+"  " +i+"st score : "+parameters.get(i).getScore());
			if(parameters.get(temp).getScore() > parameters.get(i).getScore()){
				temp = i;
			}
		}
		
		return temp;
	}

	
	public void setTeam(int team){
		this.team = team;
	}
	
	public void update(){
		if(nowSimulating)
			//world.step(1.0f/20.0f, 12, 4);
		
		stage.draw();
		
		if(Main.current != Main.userTeam){
			if(Main.stones.size() > 0){
				if(!Main.stones.get(Main.total-1).waitThis()){
					//if(!isSimulating()){
						//startSimulation();
					//}
				}
			}
		}

	}
	
	public boolean isSimulating(){
		return nowSimulating;
	}


	
}
