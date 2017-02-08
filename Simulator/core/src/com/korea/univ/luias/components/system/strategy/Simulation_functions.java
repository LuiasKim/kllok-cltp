package com.korea.univ.luias.components.system.strategy;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.korea.univ.luias.Main;
import com.korea.univ.luias.components.Half_view;
import com.korea.univ.luias.components.system.strategy.variables.Point;
import com.korea.univ.luias.components.system.strategy.variables.Strategy_parameter;
import com.korea.univ.luias.components.system.strategy.variables.Strategy_support;
import com.korea.univ.luias.objects.Stone;

public class Simulation_functions {

	private Random random;
	private ArrayList<Stone> stones;
	private Stone shoted;
	private Stage stage;

	public Simulation_functions() {
		random = new Random();
	}

	public Simulation_functions(ArrayList<Stone> stones, Stage stage) {
		this();
		this.stones = stones;
		this.stage = stage;
	}

	public void shotStone(World world, int team, int try_count, Strategy_parameter parameter) {

		shoted = new Stone(world, 2.140f, 3.78f, team, try_count, parameter.getPower(), (int) parameter.getAngle(),
				(int) parameter.getCurl(), Main.types[(Main.total + 1) / 5]);
		/*
		 * Stone stone = new Stone(world, 2.140f, 3.78f, team,(Main.current ==
		 * 0) ? h_view.redStone : h_view.yellowStone, parameter.getPower(),(int)
		 * parameter.getAngle(), parameter.getCurl(),0.3f,0.3f,try_count);
		 */
		stones.add(shoted);

		synchronized (stage) {
			stage.addActor(shoted);
		}

	}

	public void shotStone(World world, int team, int try_count, Strategy_parameter parameter, Half_view h_view) {

		if (!world.isLocked()) {
			shoted = new Stone(world, 2.140f, 3.78f, Main.current,
					(Main.current == 0) ? h_view.redStone : h_view.yellowStone, parameter.getPower(),
					(int) parameter.getAngle(), parameter.getCurl(), 0.3f, 0.3f, Main.total + 1,
					Main.types[(Main.total + 1) / 5]);
		}
		/*
		 * Stone stone = new Stone(world, 2.140f, 3.78f, team,(Main.current ==
		 * 0) ? h_view.redStone : h_view.yellowStone, parameter.getPower(),(int)
		 * parameter.getAngle(), parameter.getCurl(),0.3f,0.3f,try_count);
		 */
		stones.add(shoted);
		Main.stones.add(shoted);
		// synchronized (stage) {
		// stage.addActor(shoted);
		// }

	}

	public Strategy_parameter generateParameter(int situation, Strategy_parameter previous) {

		float angle = 0f;
		int curl = 0;
		float power = 0f;

		if (previous == null) {
			return shotRandom(situation);
		} else {
			if (previous.getSupport() == null)
				return shotRandom(situation);

			Strategy_support support = previous.getSupport();
			curl = previous.getCurl();

			if (support.angle < 0) {
				angle = previous.getAngle() - 1f;
				// angle = previous.getAngle() - (random.nextInt(5) + 1);
			} else {
				angle = previous.getAngle() + 1f;
				// angle = previous.getAngle() + (random.nextInt(5) + 1);
			}
			switch(support.power){
			case -1:
				power = previous.getPower() - 0.1f;
				// power = previous.getPower() - (random.nextInt(3) + 1);
				break;
			case 1:
				power = previous.getPower() + 0.1f;
				// power = previous.getPower() + (random.nextInt(3) + 1);
				break;
			case -2:
				power = previous.getPower() - 1f;
				// power = previous.getPower() - (random.nextInt(3) + 1);
				break;
			case 2:
				power = previous.getPower() + 1f;
				// power = previous.getPower() + (random.nextInt(3) + 1);
				break;
				
			}
		}

		return new Strategy_parameter(angle, power, curl, situation);
	}
	
	public Strategy_parameter generateParameter(Point p, Strategy_parameter previous) {

		float angle = 0f;
		int curl = 0;
		float power = 0f;

		if (previous == null) {
			return shotRandom(p);
		} else {
			if (previous.getSupport() == null)
				return shotRandom(p);

			Strategy_support support = previous.getSupport();
			curl = previous.getCurl();

			if (support.angle < 0) {
				angle = previous.getAngle() - Math.abs((p.getX()-support.x));
				// angle = previous.getAngle() - (random.nextInt(5) + 1);
			} else {
				angle = previous.getAngle() + Math.abs((p.getX()-support.x));
				// angle = previous.getAngle() + (random.nextInt(5) + 1);
			}
			switch(support.power){
			case -1:
				power = previous.getPower() - (previous.getScore()/10.0f);
				// power = previous.getPower() - (random.nextInt(3) + 1);
				break;
			case 1:
				power = previous.getPower() + (previous.getScore()/10.0f);
				// power = previous.getPower() + (random.nextInt(3) + 1);
				break;
			case -2:
				power = previous.getPower() - (previous.getScore()/10.0f);
				// power = previous.getPower() - (random.nextInt(3) + 1);
				break;
			case 2:
				power = previous.getPower() + (previous.getScore()/10.0f);
				// power = previous.getPower() + (random.nextInt(3) + 1);
				break;
				
			}
		}

		return new Strategy_parameter(angle, power, curl, 1);
	}

	public Strategy_parameter shotRandom(Point p) {
		float angle = 0f;
		int curl = 0;
		float power = 0f;
		
		
		if(Math.abs(p.getX()-2.140f) < 1.22f){
			int rand = random.nextInt(2);
			curl = rand == 0 ? -1 : rand;
		}else if(p.getX() < 2.140f){
			curl = 1;
		}else{
			curl = -1;
		}
		

		angle = Strategy_util.getAngle(p.getPosition(), new Vector2(2.140f,3.78f));

		power = Strategy_util.getDistance(p.getPosition(), new Vector2(2.140f,3.78f));

		return new Strategy_parameter(angle, power, curl, 1);
	}

	public Strategy_parameter shotRandom(int situation) {
		float angle = 0f;
		int curl = 0;
		float power = 0f;

		int rand = random.nextInt(2);
		curl = rand == 0 ? -1 : rand;

		if (curl == -1)// right
			angle = random.nextInt(21) + 90f;

		if (curl == 1)// left
			angle = (random.nextInt(21) * -1) + 90f;

		power = random.nextInt(21) + 25f;

		return new Strategy_parameter(angle, power, curl, situation);
	}

	public void setWorld(World world) {
		for (int i = 0; i < Main.stones.size(); i++) {
			Stone t = Main.stones.get(i);
			Vector2 position = t.getBody().getPosition();
			stones.add(new Stone(world, position.x, position.y, t.getTeam(), t.getNum()));
		}
	}

	public void resetWorld(World world) {

		synchronized (world) {
			destroyWorld(world);
			setWorld(world);
		}
	}

	public Stone getShotedStone() {
		return shoted;
	}

	public void destroyWorld(World world) {
		Main.WaitWorld = true;
		// if (world.isLocked()) {
		// for (Stone stone : stones)
		// world.destroyBody(stone.getBody());
		// }
		synchronized (world) {
			Array<Body> bodies = new Array<Body>();
			world.getBodies(bodies);
			for (int i = 0; i < bodies.size; i++) {
				//if (!world.isLocked())
					world.destroyBody(bodies.get(i));
			}
		}

		synchronized (stage) {
			shoted.remove();
		}

		stones.clear();
		Main.stones.clear();

		Main.WaitWorld = false;
	}

}
