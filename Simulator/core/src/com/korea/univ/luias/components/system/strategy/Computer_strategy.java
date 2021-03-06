package com.korea.univ.luias.components.system.strategy;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.korea.univ.luias.Main;
import com.korea.univ.luias.components.Half_view;
import com.korea.univ.luias.components.system.strategy.variables.Coordinate;
import com.korea.univ.luias.components.system.strategy.variables.Strategy_parameter;

/* ||*********************************************************************||
 * ||                                                                     ||
 * ||  ##    ##     ##         #######    ##########       ###            ||       
 * ||  ##   ##    ##  ##     ##      ##   ##             ##   ##          ||     
 * ||  ##  ##    ##    ##    ##      ##   ##            ##     ##         ||     
 * ||  ## ##    ##      ##   ##    ###    ##           ##       ##        ||     
 * ||  ####     ##      ##   ##   ##      ##########   ###########        ||
 * ||  ## ##    ##      ##   ## ##        ##           ##       ##        ||
 * ||  ##  ##    ##    ##    ##   ##      ##           ##       ##        ||
 * ||  ##   ##    ##  ##     ##     ##    ##           ##       ##        ||
 * ||  ##    ##     ##       ##      ##   ##########   ##       ##        ||
 * ||                                                                     ||
 * ||            ##       ##  ###     ##  ##########  ##         ##       ||
 * ||            ##       ##  ## #    ##      ##      ##         ##       ||
 * ||            ##       ##  ##  #   ##      ##       ##       ##        ||
 * ||            ##       ##  ##   #  ##      ##        ##     ##         ||
 * ||             ##     ##   ##    # ##      ##         ##   ##          ||
 * ||              ##   ##    ##     ###      ##          ## ##           ||
 * ||                ###      ##      ##  ##########       ###        #   ||
 * ||                                                                     ||
 * ||*********************************************************************||
 */

public class Computer_strategy {

	/*
	 * conditions...
	 * 
	 * 1. if in house other team's stone exist?
	 * 
	 * -- check the closest stone other team's from house center -- shot my
	 * stone after check my stone is close from house center? -- try again again
	 * again again... -- calculate score -- apply that shot
	 * 
	 * 2. if not exist stones in house?
	 * 
	 * -- shot my stone after check my stone is close from house center? -- try
	 * again again again again.... -- calculate score -- apply that shot
	 * 
	 * 3. if exist stone in house only my team's?
	 * 
	 * -- shot my stone after check my stone is close from house center? --
	 * don't touched my other stone? -- try again again again again.... --
	 * calculate score -- apply that shot
	 * 
	 */

	private World mWorld;

	private boolean nowSimulating = false;
	private Stage stage;
	private Half_view h_view;
	private Coordinate coord;

	private ArrayList<Strategy_parameter> params;
	private TempThread tThread;

	private Simulation_ShotMain sMain;

	public Computer_strategy(World world, Half_view h_view) {

		this.stage = new Stage();
		this.mWorld = world;
		this.h_view = h_view;
		params = new ArrayList<Strategy_parameter>();
		coord = new Coordinate();
		
		

	}

	public void startSimulation() {
		if(nowSimulating)
			return;
		
		nowSimulating = true;
		
		tThread = new TempThread(stage,coord,h_view);
		tThread.start();
		
		/*
		nowSimulating = true;

		sMain = new Simulation_ShotMain(mWorld, h_view, stage, params);
		sMain.start();
		*/
	}

	public void update() {
		/*
		if (nowSimulating)
			sMain.update();

		if (Main.isStarted) {
			if (Main.current != Main.userTeam) {
				if (Main.stones.size() > 0) {
					if (!Main.stones.get(Main.stones.size()-1).waitThis()) {
						if (!isSimulating()) {
							startSimulation();
						}
					}
				} else {
					if (!isSimulating()) {
						startSimulation();
					}
				}
			}

			if (isSimulating()) {
				if (sMain.isEnd()) {
					nowSimulating = false;
				}
			}
		}
		*/
		
		if(nowSimulating)
			tThread.update();
		
		stage.draw();

	}

	public boolean isSimulating() {
		return nowSimulating;
	}

}
