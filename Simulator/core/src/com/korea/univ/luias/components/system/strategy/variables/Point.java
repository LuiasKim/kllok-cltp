package com.korea.univ.luias.components.system.strategy.variables;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.math.Vector2;
import com.korea.univ.luias.Main;
import com.korea.univ.luias.components.system.strategy.Strategy_util;
import com.korea.univ.luias.objects.Stone;


public class Point {

	private float x, y;
	private float ScoreBased_eval, Rank_point, 
				  Multi_xyPoint, Value_xPoint,
				  Value_yPoint, Double_takeOut, 
				  Front_Guard, Back_Gaurd,
				  Final_value;
	public static ArrayList<Stone> stones = new ArrayList<Stone>();
	private final float xx = 2.140f;
	private final float yy = 38.53f;
	
	private float distFromCenter;
	
	private Strategy_parameter parameter;

	
	public Point(float x, float y) {
		
		this.x = x;
		this.y = y;
		
		Value_xPoint = 1.0f-(Math.abs(x-2.135f))/(float)Math.pow((1.83f+0.15f), 2);
		if(y > 43.41f-4.88f){
			Value_yPoint = (float)Math.pow((Math.abs(y-38.53f))-(1.83f+0.15f),4)/(float)Math.pow(1.83f+0.15f,4);
		}else if(43.41f-5.17f <= y && y <= 43.41f-4.88f){
			Value_yPoint = 1;
		}else{
			Value_yPoint = (float)Math.exp(0.3f-Math.abs(y-38.53f));
		}
		
		Multi_xyPoint = Value_xPoint * Value_yPoint;	

		distFromCenter = Strategy_util.getDistance(new Vector2(x,y));
		
	}
	
	public void calculateDouble_takeOut(int Total, ArrayList<Stone> stones){
		if(Total < 2)
			return;
		
		int j=0;
		Double_takeOut=0;
		float beta = 0.55f;
		float theta = 0.0f;
		float DFS = 0.0f;
		// Double_takeOut
		for(int i=1; i<=Total;i++){
			Stone stone = stones.get(i-1);
			// Distance From Center
			// + if at computer turn
			if((stone.getTeam() == Main.userTeam)){
				float DFC = getDistance(stone.getBody().getPosition());
				if(DFC < 1.83f){
					// Distance From Stone
					DFS = getDistance_coordinate(x, y, stone.getBody().getPosition());
				    j++;
					
					//theta = (float)(Math.toDegrees(theta-90));
					float tempX = x-stone.getX();
					float tempY = y-stone.getY();
					
					theta = (float)(Math.atan2(tempY, tempX));
					theta = (float)(Math.toDegrees(theta))-90.0f;
					
					float Direction_function = (12f*0.15f)*((0.5f*Math.abs((float)Math.cos(Math.toRadians(theta))))+0.5f);
					Double_takeOut += beta*((0.8f*(((DFS*(2*Direction_function-DFS))/(float)Math.pow(Direction_function, 2))))+0.2f);
				}
			}
			
		}
		Double_takeOut /=j;
		//System.out.println(Double_takeOut+", cos : "+(float)Math.cos(Math.toRadians(theta))+" DFS : "+DFS);
	}
	
	public void setRank(ArrayList <Stone> stones){
		if(distFromCenter > 1.83f)
			return;
		
		float dist[] = new float[stones.size()+1];
		Arrays.fill(dist,99999);
		
		for(int i = 0, j = 0; i < stones.size(); i++){
			Stone s = stones.get(i);
			
			if(s.getTeam() != Main.userTeam)
				continue;
			
			float Sdist = Strategy_util.getDistance(s.getBody().getPosition());
			if(Sdist > 1.83f){
				continue;
			}
			
			dist[j] = Sdist;
			j++;
		}
		
		Arrays.sort(dist);
		for(int i = dist.length; i >= 1; i--){
			if(distFromCenter < dist[i-1]){
				Rank_point = 1.0f/(float)Math.pow(i, 3);
				//System.out.println(i+", "+dist[i-1]);
			}
		}
		
	}
	
	public void calculateFrontGuard(ArrayList <Stone> stones){
		
		Front_Guard = 0.0f;
		
		for(int i = 0; i < stones.size(); i++){
			
			Stone s = stones.get(i);
			if(s.getTeam() != Main.userTeam)
				continue;
			
			if(Strategy_util.getDistance(s.getBody().getPosition()) > 1.83f)
				continue;
			
			if(y > s.getY())
				return;
			
			float beta = 1.0f;
			Front_Guard = beta * (float)Math.exp(-0.1f*(Math.pow( y-s.getY()+1f, 2)))-(float)Math.exp(-3f*(Math.pow( y-s.getY(), 2)));
			Front_Guard *= (float)Math.exp(-8*(Math.pow(x - s.getX(), 2)));
		}
		
	}
	
	public void calulateBackGuard(ArrayList <Stone> stones){
		
		Back_Gaurd = 0.0f;
		
		for(int i = 0; i < stones.size(); i++){
			
			Stone s = stones.get(i);
			if(s.getTeam() != Main.userTeam)
				continue;
			
			if(Strategy_util.getDistance(s.getBody().getPosition()) > 1.83f)
				continue;
			
			if(y < s.getY())
				return;
			
			float beta = 1.0f;
			Back_Gaurd = beta * (float)Math.exp(-1f*(Math.pow( y-s.getY()-0.2f, 2)))-(float)Math.exp(-8f*(Math.pow( y-s.getY(), 2)));
			Back_Gaurd *= (float)Math.exp(-6*(Math.pow(x - s.getX(), 2)));
		}
		
	}
	
	public void calculateTotal(){
		
		Final_value = (Multi_xyPoint + Rank_point)*Double_takeOut+(Multi_xyPoint+Rank_point)*(Front_Guard+Back_Gaurd);
		
	}
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
	public Vector2 getPosition(){
		return new Vector2(x,y);
	}
	
	public float getScoreBased_eval() {
		return ScoreBased_eval;
	}
	public void setScoreBased_eval(float scoreBased_eval) {
		ScoreBased_eval = scoreBased_eval;
	}
	public float getRank_point() {
		return Rank_point;
	}
	public void setRank_point(float rank_point) {
		Rank_point = rank_point;
	}
	
	public float getMulti_xyPoint() {
		return Multi_xyPoint;
	}
	public float getValue_xPoint() {
		return Value_xPoint;
	}
	public float getValue_yPoint() {
		return Value_yPoint;
	}

	public float getDouble_takeOut() {
		return Double_takeOut;
	}
	public void setDouble_takeOut(float double_takeOut) {
		Double_takeOut = double_takeOut;
	}
	public float getFront_Guard() {
		return Front_Guard;
	}
	public void setFront_Guard(float front_Guard) {
		Front_Guard = front_Guard;
	}
	public float getBack_Gaurd() {
		return Back_Gaurd;
	}
	public void setBack_Gaurd(float back_Gaurd) {
		Back_Gaurd = back_Gaurd;
	}
	
	@Override
	public String toString(){
		return new String("X :"+x+" Y : "+y);
	}
	
	public String getParamInfo(){
		return new String(x+", "+y+" "+parameter.toString());
	}
	
	public float getDistance(Vector2 position){
		
		return (float)(Math.sqrt( (Math.pow(position.x-xx, 2)) + (Math.pow(position.y-yy, 2)) ));
		
	}
	public float getDistance_coordinate(float x,float y, Vector2 checked_stone_position){
		// x,y : coordinate
		// checked_stone_position : stone in field
		return (float)(Math.sqrt( (Math.pow(checked_stone_position.x-x, 2)) + (Math.pow(checked_stone_position.y-y, 2)) ));
		
	}
	
	public float getTotal(){
		return Final_value;
	}
	
	
	public void setParameter(Strategy_parameter parameter){
		this.parameter = parameter;
	}

	public Strategy_parameter getParameter(){
		return parameter;
	}
	
}

