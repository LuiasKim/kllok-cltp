package com.korea.univ.luias.components.system.strategy;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.korea.univ.luias.Main;
import com.korea.univ.luias.components.Half_view;
import com.korea.univ.luias.components.system.strategy.variables.Strategy_parameter;
import com.korea.univ.luias.objects.Stone;

public class Simulation_ShotMain extends Thread {

	private ArrayList<Simulation_ShotSub> subs;
	private ArrayList<Strategy_parameter> params;
	private Stage stage;
	private Simulation_functions funcs;
	private boolean end = false;

	private World mWorld;
	private Half_view h_view;

	public Simulation_ShotMain(World world, Half_view h_view , Stage stage, ArrayList<Strategy_parameter> params) {

		this.setName("Simul_Main");
		this.params = params;
		this.stage = stage;
		this.mWorld = world;
		this.h_view = h_view;

		subs = new ArrayList<Simulation_ShotSub>();
		funcs = new Simulation_functions();
	}

	@Override
	public void run() {
		end = false;
		System.out.println("simulate Main started");
		int situation = Strategy_util.analyze(Main.current, Main.stones);
		Strategy_parameter parameter = funcs.shotRandom(situation);
		params.add(parameter);

		Simulation_ShotSub sub1 = new Simulation_ShotSub(subs,stage, params, parameter, 0, 1);
		subs.add(sub1);

		sub1.start();

		while (!sub1.isEnd()) {
			try {
				Thread.sleep(300);
			} catch (Exception e) {
			}
		}

		int best = selectShot(params);
		parameter = params.get(best);

		Main.stones.add(new Stone(mWorld, 2.140f, 3.78f, Main.current,
				(Main.current == 0) ? h_view.redStone : h_view.yellowStone, parameter.getPower(),
				(int) parameter.getAngle(), parameter.getCurl(), 0.3f, 0.3f, Main.total));

		if (Main.current == 0)
			Main.rthrowCount++;
		else
			Main.ythrowCount++;

		Main.total++;

		Main.current = Main.current == 0 ? 1 : 0;
		System.out.println("simulate Main ended");

		subs.clear();
		params.clear();
		stage.clear();
		
		end = true;
	}

	public int selectShot(ArrayList<Strategy_parameter> parameters) {

		int temp = 0;
		System.out.println("parameter sizes : " + parameters.size());
		for (int i = 1; i < parameters.size(); i++) {
			System.out.println(temp + "st score : " + parameters.get(temp).getScore() + "  " + i + "st score : "
					+ parameters.get(i).getScore());
			if (parameters.get(temp).getScore() > parameters.get(i).getScore()) {
				temp = i;
			}
		}

		return temp;
	}

	public void update() {

		synchronized (subs) {
			for (int i = 0; i < subs.size(); i++) {
				subs.get(i).update();
			}
		}
		
	}

	public boolean isEnd() {
		return end;
	}

}
