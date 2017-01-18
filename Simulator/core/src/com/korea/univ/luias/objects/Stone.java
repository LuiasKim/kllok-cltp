package com.korea.univ.luias.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;

public class Stone extends Phys_Object{

	
	private Sprite sprite;
	
	//team 0 : red 1 : yellow
	private int team;
	
	
	public Stone(Texture texture, World world){
		
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
	
}
