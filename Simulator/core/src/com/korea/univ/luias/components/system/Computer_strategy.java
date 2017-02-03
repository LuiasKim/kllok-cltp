package com.korea.univ.luias.components.system;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.korea.univ.luias.Main;
import com.korea.univ.luias.components.Half_view;
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
	
	private ArrayList <Stone> stones;
	private ArrayList <Stone> stones_shoted;
	private ArrayList <Strategy_parameter> shots;
	private World mWorld;
	private World world;
	private int try_count;
	
	private float x = 2.140f;
	private float y = 38.53f;
	
	private boolean otherExist = false;
	private boolean mineExist = false;
	private boolean anyExist = false;
	private boolean nowSimulating = false;
	private int team;
	
	private Strategy_parameter previous_shot;
	
	private Random random;
	private Half_view h_view;


	private Stage stage;
	
	
	private ArrayList <Stone> classA = new ArrayList<Stone>(), 
			  classB = new ArrayList<Stone>(), 
			  classC = new ArrayList<Stone>(),
			  classD = new ArrayList<Stone>();
	
	public Computer_strategy(World world, Half_view h_view){
		

		stage = new Stage();

		
		this.mWorld = world;
		stones = new ArrayList<Stone>();
		stones_shoted = new ArrayList<Stone>();
		random = new Random();
		this.h_view = h_view;
		this.world = new World(new Vector2(0,0),true);
	
	}
	
	public void startSimulation(){
		nowSimulating = true;
		// 1. set the world
		shots = new ArrayList <Strategy_parameter>();
		
		// 2. analysis current situation
		setWorld();
		final int situation = analyze();
		System.out.println("current Situation : "+situation);
		// 3. seek the best shot
		new Thread(new Runnable(){
			@Override
			public void run(){
				stones_shoted.clear();
				for(try_count = 1; try_count <= 10; try_count++){
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
	
	public void shotStone(Strategy_parameter parameter){
		
		Stone stone = new Stone(world, 2.140f, 3.78f, team, try_count, parameter.getPower(),(int)parameter.getAngle(),(int)parameter.getCurl());
		/*Stone stone = new Stone(world, 2.140f, 3.78f, team,(Main.current == 0) ? h_view.redStone : h_view.yellowStone,
				parameter.getPower(),(int) parameter.getAngle(), parameter.getCurl(),0.3f,0.3f,try_count);
				*/
		stones.add(stone);
		stones_shoted.add(stone);
		stage.addActor(stone);
		
	}
	
	public Strategy_parameter generateParameter(int situation,Strategy_parameter previous){
		
		float angle = 0f;
		int curl = 0;
		float power = 0f;
		
		if(previous == null){
			return shotRandom(situation);
		}else{
			if(previous.support == null)
				return shotRandom(situation);
			
			Strategy_support support = previous.support;
			curl = previous.curl;
			
			if(support.angle < 0){
				angle = previous.angle - (random.nextInt(5)+1);
			}else{
				angle = previous.angle + (random.nextInt(5)+1);
			}
			if(support.power < 0){
				power = previous.power - (random.nextInt(2)+1);
			}else{
				power = previous.power + (random.nextInt(2)+1);
			}
		}
		
		return new Strategy_parameter(angle,power,curl,situation);
	}
	
	public Strategy_parameter shotRandom(int situation){
		float angle = 0f;
		int curl = 0;
		float power = 0f;
		
		int rand = random.nextInt(2);
		curl = rand == 0 ? -1 : rand;
			
		if(curl == -1)//right
			angle = random.nextInt(21)+90f;
		
		if(curl == 1)//left
			angle = (random.nextInt(21)*-1)+90f;
			
		power = random.nextInt(21)+25f;
		
		return new Strategy_parameter(angle,power,curl,situation);
	}
	
	@SuppressWarnings("unused")
	public int analyze(){
		/*
		 * Situation.
		 * No. 1 -> Any stones doesn't exist
		 * No. 2 -> Only other team's
		 * 	No. 21 -> in ClassA
		 *  No. 22 -> in ClassB
		 *  No. 23 -> in ClassC
		 *  No. 24 -> in ClassD
		 * No. 3 -> Only my team's
		 * 	No. 31 -> in ClassA
		 *  No. 32 -> in ClassB
		 *  No. 33 -> in ClassC
		 *  No. 34 -> in ClassD
		 * No. 4 -> Both
		 * 	No. 41 -> My team's in ClassA
		 *  No. 412 -> Other team's in ClassA
		 *  No. 42 -> My team's in ClassB
		 *  No. 422 -> Other team's in ClassB
		 *  No. 43 -> My team's in ClassC
		 *  No. 432 -> Other team's in ClassC
		 *  No. 44 -> My team's in ClassD
		 *  No. 442 -> Other team's in ClassD
		 */
		
		classA.clear();
		classB.clear();
		classC.clear();
		classD.clear();
		
		// current situation analyze
		
		// 1. in house, other team's stone exist?
		for(int i = 0; i < stones.size(); i++){
			Stone stone = stones.get(i);
			
			// Distance From Center
			float DFC = getDistance(stone.getBody().getPosition());
			
			if( DFC > 1.83f)
				continue;
		
			if( DFC < 0.15f)
				classA.add(stone);
			
			if( DFC > 0.15f && DFC < 0.61f)
				classB.add(stone);
			
			if( DFC > 0.61f && DFC < 1.22f)
				classC.add(stone);
			
			if( DFC > 1.22f && DFC < 1.83f)
				classD.add(stone);
			
			if(stone.getTeam() == this.team){
				anyExist = true;
				mineExist = true;
				continue;
			}
			
			otherExist = true;
			anyExist = true;
		}
		// 2. in house, don't exist any stones?
		if(!anyExist && !otherExist && !mineExist){
			return 1;
		}
		// 3. in house, only other team's stone exist?
		if(anyExist && otherExist && !mineExist){
			return 2;
		}
		// 4. in house, only my stone exist?
		if(anyExist && !otherExist && mineExist){
			return 3;
		}
		// 5. in house stoneExist (Both)
		if(anyExist){
			for(int i = 0; i < classA.size(); i++){
				if(classA.get(i).getTeam() != this.team)
					return 412;
				else
					return 41;
			}
			return 4;
		}
		// order
		// 3 (true)
		// 3 (false) -> 1 (true) -> 5
		// 3 (falsE) -> 1 (false) -> 2 (true) -> 4
		return 0;
	}
	
	public void calc_shotScore(int try_count, int situation){
		
		ArrayList <Stone> classAt = new ArrayList<Stone>(), 
				  classBt = new ArrayList<Stone>(), 
				  classCt = new ArrayList<Stone>(),
				  classDt = new ArrayList<Stone>();
		
		for(int i = 0; i < stones.size(); i++){
			Stone stone = stones.get(i);
			// Distance From Center
			float DFC = getDistance(stone.getBody().getPosition());
			
			if( DFC > 1.83f)
				continue;
		
			if( DFC < 0.15f)
				classAt.add(stone);
			
			if( DFC > 0.15f && DFC < 0.61f)
				classBt.add(stone);
			
			if( DFC > 0.61f && DFC < 1.22f)
				classCt.add(stone);
			
			if( DFC > 1.22f && DFC < 1.83f)
				classDt.add(stone);
		}
		
		float dist,angle;
		Stone stone;
		Strategy_parameter parameter;
		
		switch(situation){
		
		case 1: // in house, don't exist any stones?
			stone = stones_shoted.get(try_count-1);
			parameter = shots.get(try_count-1);
			
			dist = getDistance(stone.getBody().getPosition());
			System.out.println(try_count+"st distance : "+dist);
			angle = getAngle(stone.getBody().getPosition());
			System.out.println(try_count+"st angle : "+angle);
			
			Strategy_support support = new Strategy_support();
			if(dist > 0.15f){
				if(stone.getBody().getPosition().y < 38.53f)
					support.power = 1;
				else
					support.power = -1;
			}
			
			if(angle > 90f){
				support.angle = -1;
			}else{
				support.angle = 1;
			}
			parameter.setScore(dist);
			//parameter.setScore((25f-(float)(Math.abs(0-dist))));
			//parameter.setScore((25f-dist)+(25f-(float)(Math.abs(angle-90f))));
			parameter.support = support;
			
			System.out.println(try_count+"st parameter's score : "+parameter.getScore());
			break;
			
		case 2: // in house, only other team's stone exist?
			break;
		case 3: // in house, only my stone exist?
			break;
		case 4: // in house stoneExist (Both)
			break;
		case 41: // My team's in ClassA
			break;
		case 412: // Other team's in ClassA
			break;
		}
		
	}
	
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
	
	public void setWorld(){
		for(int i = 0; i < Main.stones.size(); i++){
			Stone t = Main.stones.get(i);
			Vector2 position = t.getBody().getPosition();
			stones.add(new Stone(world,position.x,position.y,t.getTeam(),t.getNum()));
		}
		
	}
	
	public void resetWorld(){
		destroyWorld();
		setWorld();
	}
	
	public void destroyWorld(){
		for(int i = 0; i < stones.size(); i++){
			world.destroyBody(stones.get(i).getBody());
		}
		
		for(int i = 0; i < stones_shoted.size(); i++)
			stones_shoted.get(i).remove();
		
		stones.clear();
	}
	
	public float getDistance(Vector2 position){
		
		return (float)(Math.sqrt( (Math.pow(position.x-x, 2)) + (Math.pow(position.y-y, 2)) ));
		
	}
	
	public float getAngle(Vector2 position){
		
		float x2 = position.x-x;
		float y2 = position.y-y;
		
		return (Math.abs((float)(Math.toDegrees(Math.atan2(y2, x2)))));
		
	}
	
	public void setTeam(int team){
		this.team = team;
	}
	
	public class Strategy_parameter {
		
		private float score = 0;
		
		float angle;
		float power;
		int curl;
		int situation;
		Strategy_support support;
		
		public Strategy_parameter(float angle, float power, int curl, int situation){
			
			this.angle = angle;
			this.power = power;
			this.curl = curl;
			this.situation = situation;
			
		}
		
		public Strategy_parameter(float angle, float power, int curl, int situation, Strategy_support support){
			this(angle,power,curl,situation);
			this.support = support;
		}
		
		public float getAngle(){
			return angle;
		}
		
		public float getPower(){
			return power;
		}
		
		public int getCurl(){
			return curl;
		}
		
		public int getSituation(){
			return situation;
		}
		
		public void setScore(float score){
			this.score = score;
		}
		
		public float getScore(){
			return score;
		}
		
		public String toString(){
			return new String("Power : "+power+" Angle : "+angle+" Curl : "+curl);
		}
	}
	
	protected class Strategy_support{
		// -1 is less 
		//  1 is bigger
		public int power;
		public int angle;
	}
	
	public void update(){
		world.step(1.0f/20.0f, 12, 4);
		stage.draw();
		
		if(Main.current != Main.userTeam){
			if(Main.stones.size() > 0){
				if(!Main.stones.get(Main.total-1).waitThis()){
					if(!isSimulating())
						startSimulation();
				}
			}
		}

	}
	
	
	public boolean isSimulating(){
		return nowSimulating;
	}


	
}
