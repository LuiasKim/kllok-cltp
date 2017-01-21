package com.korea.univ.luias.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Stone extends Phys_Object {

	private Sprite sprite;

	// team 0 : red 1 : yellow
	private int team;
	private Vector2 curl;
	private float vx, vy;
	private boolean isPhys = false;
	private boolean isStop = true;
	private boolean waitOther = true;
	private float curlV = 0.0f;
	private Texture texture;
	private int num = 0;
	public Stone(Texture texture) {
		super();
		this.sprite = new Sprite(texture);
		this.texture = texture;
	}
	
	public Stone(Texture texture, float x, float y, float width, float height, int num){
		this(texture);
		
		this.setX(x);
		this.setY(y);
		sprite.setX(x);
		sprite.setY(y);
		
		this.setWidth(width);
		this.setHeight(height);
		
		this.num = num;
	}

	public Stone(World world, float x, float y, int team, Texture texture,int num) {
		super();

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

		body.setLinearDamping(1f);

		this.team = team;

		sprite = new Sprite(texture);
		this.texture = texture;
		this.num = num;
	}

	public Stone(World world, float x, float y, int team, Texture texture, float power, float angle, int curl,int num) {

		this(world, x, y, team, texture,num);

		angle -= 90;

		vx = Math.abs(((float) (Math.cos(Math.toRadians(angle))) * power));
		vy = (float) ((float) (Math.sin(Math.toRadians(angle))) * Math.sqrt(power));

		switch (curl) {
		case 1:
			body.setAngularVelocity(10f);
			curlV = 0.15f;
			break;
		case 0:
			curlV = 0.0f;
			break;
		case -1:
			body.setAngularVelocity(-10f);
			curlV = -0.15f;
			break;
		}

		body.setAngularDamping(0.2f);
		body.setLinearVelocity(vx, vy);
		isPhys = true;
	}

	public Stone(World world, float x, float y, int team, Texture texture, float power, float angle, int curl,
			float width, float height, int num) {
		this(world, x, y, team, texture, power, angle, curl,num);

		this.setWidth(width);
		this.setHeight(height);

		sprite.setSize(width, height);
		sprite.setOriginCenter();
		
		if(curl != 0)
			isStop = false;
	}

	@Override
	public void update(float delta) {

		
		
		if (!isPhys) {
			sprite.setPosition(this.getX(), this.getY());
			sprite.setSize(this.getWidth(), this.getHeight());
			sprite.setOriginCenter();
			sprite.setRotation(this.getRotation());
			this.setRotation(this.getRotation() + 5f);
		}

		if (isPhys) {
			this.setPosition(body.getPosition().x, body.getPosition().y);
			sprite.setPosition(this.getX() - (this.getWidth() / 2), this.getY() - (this.getHeight() / 2));
			this.setRotation(this.getRotation()+body.getAngularVelocity());
			sprite.setRotation(this.getRotation());
			
			if(!isStop){
				curl = body.getLinearVelocity().nor();
				curl = curl.rotate(90);
				curl = new Vector2(0, curl.y * curlV);
				body.applyForceToCenter(curl, false);
			}
			
			if(body.getLinearVelocity().len() < 1f){
				isStop = true;
			}
			
			if(body.getLinearVelocity().len() <= 0.1f)
				waitOther = false;

		}
		
		

	}

	@Override
	public void draw(Batch batch, float delta) {

		sprite.draw(batch);
		update(delta);
	}

	public int getTeam() {
		return team;
	}

	public Fixture getFixture() {
		return fixture;
	}

	public Body getBody() {
		return body;
	}

	public Texture getTexture(){
		return texture;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public int getNum(){
		return num;
	}
	
	public boolean waitThis(){
		return waitOther;
	}
}
