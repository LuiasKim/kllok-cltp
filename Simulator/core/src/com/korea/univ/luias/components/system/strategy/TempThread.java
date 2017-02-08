package com.korea.univ.luias.components.system.strategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.korea.univ.luias.Main;
import com.korea.univ.luias.components.Half_view;
import com.korea.univ.luias.components.system.strategy.variables.Coordinate;
import com.korea.univ.luias.components.system.strategy.variables.Point;
import com.korea.univ.luias.components.system.strategy.variables.Strategy_parameter;
import com.korea.univ.luias.objects.Stone;

public class TempThread extends Thread {

	private ArrayList<Stone> stones;
	private World world;
	private Coordinate coord;

	private Simulation_functions funcs;
	private Half_view h_view;
	public static Point p;
	
	private FileHandle data;

	public TempThread(Stage stage, Coordinate coord, Half_view h_view) {

		this.coord = coord;
		//this.world = new World(new Vector2(0, 0), true);
		this.stones = new ArrayList<Stone>();
		this.funcs = new Simulation_functions(stones, stage);
		this.h_view = h_view;
		
		
		data = Gdx.files.local("data/CoordTable.dat");
	}

	@Override
	public void run() {

		Strategy_parameter previous = null;
		Collection<Point> points = coord.getPoints().values();
		Iterator<Point> iter = points.iterator();
		int j = 1;
		while (iter.hasNext()) {
			previous = null;
			p = iter.next();
			int i = 1;
			while (true) {
				System.out.print(j+"th point try"+i+"\n");
				if( (i+1)/10 == 0 )
					System.out.println();
				
				Strategy_parameter parameter = funcs.generateParameter(p, previous);
				// 1. set the world
				funcs.setWorld(Main.world);
				// 2. shot Stone with each number's parameter
				funcs.shotStone(Main.world, Main.current, 1, parameter,h_view);

				// 2-1. wait for stone stop
				while (true) {

					try {
						Thread.sleep(500);
					} catch (Exception e) {
					}

					if (!funcs.getShotedStone().waitThis())
						break;

				}
				// 3. calculate shot Score
				Strategy_util.calc_shotScore(p, funcs.getShotedStone(), parameter);
				
				// 4. destroy world
				funcs.destroyWorld(Main.world);

				if (parameter.getScore() < 0.2f) {
					p.setParameter(parameter);
					System.out.println(p.toString()+" parameter :  "+parameter.toString());
					data.writeString(p.getParamInfo()+"\n", true,"UTF-8");
					break;
				}

				previous = parameter;
				i++;
			}
			j++;
		}
	}
	
	public void update() {
		//world.step(1.0f / 20.0f, 12, 4);
	}

}
