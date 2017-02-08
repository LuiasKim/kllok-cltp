package com.korea.univ.luias.components.system.strategy;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.korea.univ.luias.components.system.strategy.variables.Point;
import com.korea.univ.luias.components.system.strategy.variables.Strategy_parameter;
import com.korea.univ.luias.components.system.strategy.variables.Strategy_support;
import com.korea.univ.luias.objects.Stone;

public class Strategy_util {

	private static ArrayList<Stone> classA = new ArrayList<Stone>(), classB = new ArrayList<Stone>(),
			classC = new ArrayList<Stone>(), classD = new ArrayList<Stone>();

	private static final float x = 2.140f;
	private static final float y = 38.53f;

	public static int analyze(int team, ArrayList<Stone> stones) {
		/*
		 * Situation. No. 1 -> Any stones doesn't exist No. 2 -> Only other
		 * team's No. 21 -> in ClassA No. 22 -> in ClassB No. 23 -> in ClassC
		 * No. 24 -> in ClassD No. 3 -> Only my team's No. 31 -> in ClassA No.
		 * 32 -> in ClassB No. 33 -> in ClassC No. 34 -> in ClassD No. 4 -> Both
		 * No. 41 -> My team's in ClassA No. 412 -> Other team's in ClassA No.
		 * 42 -> My team's in ClassB No. 422 -> Other team's in ClassB No. 43 ->
		 * My team's in ClassC No. 432 -> Other team's in ClassC No. 44 -> My
		 * team's in ClassD No. 442 -> Other team's in ClassD
		 */

		boolean otherExist = false;
		boolean mineExist = false;
		boolean anyExist = false;

		classA.clear();
		classB.clear();
		classC.clear();
		classD.clear();

		// current situation analyze

		// 1. in house, other team's stone exist?
		for (int i = 0; i < stones.size(); i++) {
			Stone stone = stones.get(i);

			// Distance From Center
			float DFC = getDistance(stone.getBody().getPosition());

			if (DFC > 1.83f)
				continue;

			if (DFC < 0.15f)
				classA.add(stone);

			if (DFC > 0.15f && DFC < 0.61f)
				classB.add(stone);

			if (DFC > 0.61f && DFC < 1.22f)
				classC.add(stone);

			if (DFC > 1.22f && DFC < 1.83f)
				classD.add(stone);

			if (stone.getTeam() == team) {
				anyExist = true;
				mineExist = true;
				continue;
			}

			otherExist = true;
			anyExist = true;
		}
		// 2. in house, don't exist any stones?
		if (!anyExist && !otherExist && !mineExist) {
			return 1;
		}
		/*
		 * // 3. in house, only other team's stone exist? if(anyExist &&
		 * otherExist && !mineExist){ return 2; } // 4. in house, only my stone
		 * exist? if(anyExist && !otherExist && mineExist){ return 3; } // 5. in
		 * house stoneExist (Both) if(anyExist){ for(int i = 0; i <
		 * classA.size(); i++){ if(classA.get(i).getTeam() != this.team) return
		 * 412; else return 41; } return 4; }
		 */
		// order
		// 3 (true)
		// 3 (false) -> 1 (true) -> 5
		// 3 (falsE) -> 1 (false) -> 2 (true) -> 4
		return 1;
	}

	public static void calc_shotScore(int situation, Stone shoted, Strategy_parameter param) {
		/*
		 * ArrayList <Stone> classAt = new ArrayList<Stone>(), classBt = new
		 * ArrayList<Stone>(), classCt = new ArrayList<Stone>(), classDt = new
		 * ArrayList<Stone>();
		 * 
		 * for(int i = 0; i < stones.size(); i++){ Stone stone = stones.get(i);
		 * // Distance From Center float DFC =
		 * getDistance(stone.getBody().getPosition());
		 * 
		 * if( DFC > 1.83f) continue;
		 * 
		 * if( DFC < 0.15f) classAt.add(stone);
		 * 
		 * if( DFC > 0.15f && DFC < 0.61f) classBt.add(stone);
		 * 
		 * if( DFC > 0.61f && DFC < 1.22f) classCt.add(stone);
		 * 
		 * if( DFC > 1.22f && DFC < 1.83f) classDt.add(stone); }
		 */
		float dist, angle;

		switch (situation) {

		case 1: // in house, don't exist any stones?

			dist = getDistance(shoted.getBody().getPosition());
			System.out.println(shoted.getNum() + "st distance : " + dist);
			angle = getAngle(shoted.getBody().getPosition());
			System.out.println(shoted.getNum() + "st angle : " + angle);

			Strategy_support support = new Strategy_support();
			if (dist > 0.15f) {
				if (shoted.getBody().getPosition().y < 38.53f)
					support.power = 1;
				else
					support.power = -1;
			}

			if (angle > 90f) {
				support.angle = -1;
			} else {
				support.angle = 1;
			}
			
			support.x = shoted.getX();
			
			param.setScore(dist);
			// parameter.setScore((25f-(float)(Math.abs(0-dist))));
			// parameter.setScore((25f-dist)+(25f-(float)(Math.abs(angle-90f))));
			param.setSupport(support);
			System.out.println(shoted.getNum() + "st parameter's score : " + param.getScore());
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

	public static void calc_shotScore(Point p, Stone shoted, Strategy_parameter param) {

		float dist, angle;

		dist = getDistance(p.getPosition(), shoted.getBody().getPosition());
		System.out.println("distance : " + dist);
		angle = getAngle(p.getPosition(), shoted.getBody().getPosition());
		System.out.println("angle : " + angle);

		Strategy_support support = new Strategy_support();
		if (dist > 1f) {
			
			if (shoted.getBody().getPosition().y < p.getY())
				support.power = 2;
			else
				support.power = -2;
			
		}else if (dist < 1f && dist > 0.15f) {
			
			if (shoted.getBody().getPosition().y < p.getY())
				support.power = 1;
			else
				support.power = -1;
			
		}else if(dist < 0.15f){
			support.power = 0;
		}

		if (angle > 90f) {
			support.angle = -1;
		} else {
			support.angle = 1;
		}
		param.setScore(dist);
		// parameter.setScore((25f-(float)(Math.abs(0-dist))));
		// parameter.setScore((25f-dist)+(25f-(float)(Math.abs(angle-90f))));
		param.setSupport(support);

		System.out.println("parameter's score : " + param.getScore());

	}

	public static float getDistance(Vector2 std, Vector2 position) {
		return (float) (Math.sqrt((Math.pow(position.x - std.x, 2)) + (Math.pow(position.y - std.y, 2))));
	}

	public static float getDistance(Vector2 position) {

		return (float) (Math.sqrt((Math.pow(position.x - x, 2)) + (Math.pow(position.y - y, 2))));

	}

	public static float getAngle(Vector2 position) {

		float x2 = position.x - x;
		float y2 = position.y - y;

		return (Math.abs((float) (Math.toDegrees(Math.atan2(y2, x2)))));

	}

	public static float getAngle(Vector2 std, Vector2 position) {

		float x2 = position.x - std.x;
		float y2 = position.y - std.y;

		return (Math.abs((float) (Math.toDegrees(Math.atan2(y2, x2)))));

	}

}
