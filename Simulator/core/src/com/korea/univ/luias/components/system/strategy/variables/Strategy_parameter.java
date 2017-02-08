package com.korea.univ.luias.components.system.strategy.variables;

public class Strategy_parameter {
	private float score = 9999;
	
	float angle;
	float power;
	int curl;
	int situation;
	Strategy_support support;
	
	float x, y;
	
	public Strategy_parameter(){
		
	}
	
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
	
	public Strategy_parameter(float angle, float power, int curl, int situation, Strategy_support support, float x, float y){
		this(angle,power,curl,situation,support);
		
		this.x = x;
		this.y = y;
		
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
	
	public void setSupport(Strategy_support support){
		this.support = support;
	}
	
	public Strategy_support getSupport(){
		return support;
	}
	
	public float getX(){
		return x;
	}
}
