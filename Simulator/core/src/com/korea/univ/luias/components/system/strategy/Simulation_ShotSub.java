package com.korea.univ.luias.components.system.strategy;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.korea.univ.luias.Main;
import com.korea.univ.luias.components.system.strategy.variables.Strategy_parameter;
import com.korea.univ.luias.objects.Stone;

public class Simulation_ShotSub extends Thread {

	private int Layer, num;

	private ArrayList<Stone> stones;
	private ArrayList<Strategy_parameter> params;
	private ArrayList<Simulation_ShotSub> subs;
	private Stage stage;
	private World world;

	private boolean end = false;
	private boolean ready = false;
	private boolean allReady = false;
	private Simulation_functions funcs;
	private Strategy_parameter parameter;

	public Simulation_ShotSub(ArrayList<Simulation_ShotSub> subs, Stage stage, ArrayList<Strategy_parameter> params,
			Strategy_parameter mainParameter, int Layer, int num) {

		this.stage = stage;
		this.params = params;
		this.subs = subs;
		this.parameter = mainParameter;
		this.Layer = Layer;
		this.num = num;

		this.world = new World(new Vector2(0, 0), true);
		this.stones = new ArrayList<Stone>();
		this.funcs = new Simulation_functions(stones, stage);

	}

	@Override
	public void run() {
		System.out.println("simulate" + toString() + " started");
		ready = false;
		end = false;
		// 1. set the world
		funcs.setWorld(world);

		// 2. shot Stone with each number's parameter
		funcs.shotStone(world, Main.current, num, parameter);

		// 2-1. wait for stone stop
		while (true) {

			try {
				Thread.sleep(100);
			} catch (Exception e) {
			}

			if (!funcs.getShotedStone().waitThis())
				break;

		}

		// 3. calculate shot Score
		Strategy_util.calc_shotScore(parameter.getSituation(), funcs.getShotedStone(), parameter);

		// 4. destroy world
		funcs.destroyWorld(world);
		ready = true;

		// (5.) calculate shot score whit other thread shots
		Strategy_parameter best = new Strategy_parameter();
		if (Layer != 0) {
			allReady = false;
			// 5-1 wait other thread
			while (true) {
				try {
					Thread.sleep(500);
				} catch (Exception e) {
				}
				for (int i = 0; i < subs.size(); i++) {
					Simulation_ShotSub sub = subs.get(i);

					if(sub == null)
						continue;
					
					if (sub.getThisLayer() != Layer)
						continue;

					if (!sub.ready)
						break;
					
					allReady = true;
				}
				
				if(allReady)
					break;
				
			}
			for (int i = 0; i < subs.size(); i++) {
				Simulation_ShotSub sub = subs.get(i);

				if (sub.getParameter().getScore() < best.getScore())
					best = sub.getParameter();

			}

		} else {
			best = parameter;
		}

		if (Layer == 4) {
			System.out.println("simulate" + toString() + "ended");
			end = true;
			return;
		}

		// 6. generate new Parameters
		parameter = funcs.generateParameter(parameter.getSituation(), parameter);
		synchronized (params) {
			params.add(parameter);
		}
		

		// 7. Start two new thread
		Simulation_ShotSub sub1 = new Simulation_ShotSub(subs, stage, params, parameter, Layer + 1, num+1);
		Simulation_ShotSub sub2 = new Simulation_ShotSub(subs, stage, params, best, Layer + 1, num+2);	
		
		synchronized (subs) {
			subs.add(sub1);
			subs.add(sub2);
		}

		sub1.start();
		sub2.start();
		// 8. wait subs

		while (!sub1.isEnd() || !sub2.isEnd()) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}
		}
		System.out.println("simulate" + toString() + "ended");
		end = true;
	}

	public Strategy_parameter getParameter() {
		return parameter;
	}

	public void update() {
		world.step(1.0f / 20.0f, 12, 4);

	}

	public int getThisLayer() {
		return Layer;
	}

	public int getNum() {
		return num;
	}

	public String toString() {
		return "Layer " + Layer + " - " + num + "th Thread";
	}

	public boolean isEnd() {
		return end;
	}

	public boolean isReady() {
		return ready;
	}
}
