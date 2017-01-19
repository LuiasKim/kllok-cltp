package com.korea.univ.luias.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Wall extends Phys_Object{

	public enum Wall_Type {
			TYPE_TOP,
			TYPE_LEFT,
			TYPE_RIGHT,
			TYPE_BOTTOM
	};
	
	private Wall_Type type;
	
	public Wall(World world, float Width, float Height, float x, float y, Wall_Type type){
		b_def = new BodyDef();
		b_def.type = BodyType.StaticBody;
		
		b_def.position.set(x, y);
		body = world.createBody(b_def);
		
		
		PolygonShape box = new PolygonShape();
		
		box.setAsBox(Width, Height);
		fixture = body.createFixture(box, 0.0f);
		
		box.dispose();
		
		this.type = type;
	}
	
	public Fixture getFixture(){
		return fixture;
	}
	
	public Body getBody(){
		return body;
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}
	
	public Wall_Type getType(){
		return type;
	}
	
}
