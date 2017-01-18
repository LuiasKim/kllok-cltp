package com.korea.univ.luias.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Phys_Object extends Actor{
	Body body;
	BodyDef b_def;
	FixtureDef def;
	Fixture fixture;
	
	public abstract void update(float delta);
}
