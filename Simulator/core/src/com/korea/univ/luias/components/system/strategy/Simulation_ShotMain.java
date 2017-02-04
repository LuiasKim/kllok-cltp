package com.korea.univ.luias.components.system.strategy;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.korea.univ.luias.Main;
import com.korea.univ.luias.components.system.strategy.variables.Strategy_parameter;
import com.korea.univ.luias.components.system.strategy.variables.Strategy_support;
import com.korea.univ.luias.objects.Stone;

public class Simulation_ShotMain {

	private ArrayList <Stone> stones;
	private ArrayList <Stone> stones_shoted;
	private ArrayList <Strategy_parameter> shots;
	private ArrayList <World> worlds;
	
	private Random random;
	private Stage stage;
	
	public Simulation_ShotMain(Stage stage){
		
		this.stage = stage;
		stones = new ArrayList<Stone>();
		stones_shoted = new ArrayList<Stone>();
		shots = new ArrayList<Strategy_parameter>();
		worlds = new ArrayList<World>();
		
	}
	
	public void shotStone(World world,int team, int try_count, Strategy_parameter parameter){
		
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
			if(previous.getSupport() == null)
				return shotRandom(situation);
			
			Strategy_support support = previous.getSupport();
			curl = previous.getCurl();
			
			if(support.angle < 0){
				angle = previous.getAngle() - (random.nextInt(5)+1);
			}else{
				angle = previous.getAngle() + (random.nextInt(5)+1);
			}
			if(support.power < 0){
				power = previous.getPower() - (random.nextInt(2)+1);
			}else{
				power = previous.getPower() + (random.nextInt(2)+1);
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
	
	public void setWorld(World world){
		for(int i = 0; i < Main.stones.size(); i++){
			Stone t = Main.stones.get(i);
			Vector2 position = t.getBody().getPosition();
			stones.add(new Stone(world,position.x,position.y,t.getTeam(),t.getNum()));
		}
		
	}
	
	public void resetWorld(World world){
		destroyWorld(world);
		setWorld(world);
	}
	
	public void destroyWorld(World world){
		
		for(Stone stone : stones)
			world.destroyBody(stone.getBody());
		
		for(Stone stone : stones_shoted)
			stone.remove();
			
		stones.clear();
	}
	
	
	public void update(){
		for(int i = 0; i < worlds.size(); i++)
			worlds.get(i).step(1.0f/20.0f, 12, 4);
	}
	
}
