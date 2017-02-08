package com.korea.univ.luias.components.system.strategy.variables;

import java.util.HashMap;

public class Coordinate {

	private HashMap< Integer, Point> points;

	
	public Coordinate() {


		
		points = new HashMap<Integer, Point>();
		for (float y = 33.30f; y <= 41.25f; y += 0.05f) {
			for (float x = 0.05f; x <= 4.30f; x += 0.05f) {
				
				String key = String.valueOf(y)+String.valueOf(x);
				
						
				Point p = new Point(x,y);
				points.put(key.hashCode(), p);
				
			}

		}
		
//		System.out.println("total point count : "+points.size());

	}
	
	public HashMap<Integer, Point> getPoints(){
		return points;
	}

}
