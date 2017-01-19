package com.korea.univ.luias.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Stone extends Phys_Object{

	
	private Sprite sprite;
	
	//team 0 : red 1 : yellow
	private int team;
	
	public Stone(Texture texture){
		this.sprite = new Sprite(texture);
	}
	
	public Stone(World world, float x, float y,int team, Texture texture){
		
		b_def = new BodyDef();
		b_def.type = BodyType.DynamicBody;
		b_def.position.set(x, y);
		
		body = world.createBody(b_def);
		
		def = new FixtureDef();
		def.density = 1.0f;
		def.friction = 0.5f;
		def.restitution = 1.0f;
		
		CircleShape circle = new CircleShape();
		circle.setRadius(0.3f);
		
		def.shape = circle;
		
		fixture = body.createFixture(def);
		
		circle.dispose();

		
		this.team = team;
		
		sprite = new Sprite(texture);
		
	}
	
	public int getTeam(){
		return team;
	}
	
	@Override
	public void update(float delta){
		sprite.setPosition(this.getX(), this.getY());
		sprite.setSize(this.getWidth(), this.getHeight());
		sprite.setOriginCenter();
		sprite.setRotation(this.getRotation());
		this.setRotation(this.getRotation()+5f);
	}
	
	@Override
	public void draw(Batch batch, float delta){
		
		sprite.draw(batch);
		update(delta);
	}
	
	public Fixture getFixture(){
		return fixture;
	}
	
	public Body getBody(){
		return body;
	}
	
}
