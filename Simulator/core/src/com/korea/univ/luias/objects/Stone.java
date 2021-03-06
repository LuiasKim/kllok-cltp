package com.korea.univ.luias.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.korea.univ.luias.Main;
import com.korea.univ.luias.components.system.strategy.Strategy_util;
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
	private boolean isTemp = false;
	private boolean isStop = true;
	private boolean waitOther = true;
	private boolean isInfo = false;
	private boolean isRemoved = false;
	private float curlV = 0.0f;
	private Texture texture;
	private int num = 0;
	private World world;

	private Stone_Type type;

	private Vector2 position_origin;
	private boolean freeGuard = false;

	public enum Stone_Type {
		Type_Lead, Type_Second, Type_Third, Type_Fourth
	}

	public Stone(Texture texture) {
		super();
		this.sprite = new Sprite(texture);
		this.texture = texture;
	}

	public Stone(Texture texture, float x, float y, float width, float height, int num) {
		this(texture);

		this.setX(x);
		this.setY(y);
		sprite.setX(x);
		sprite.setY(y);

		this.setWidth(width);
		this.setHeight(height);

		this.num = num;
	}

	public Stone(World world, float x, float y, int team, Texture texture, int num) {
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
		circle.setRadius(0.15f);

		def.shape = circle;

		fixture = body.createFixture(def);

		circle.dispose();

		body.setLinearDamping(1f);

		this.team = team;

		if (texture != null) {
			sprite = new Sprite(texture);
			this.texture = texture;
		}
		this.num = num;
		this.world = world;
	}

	public Stone(World world, float x, float y, int team, Texture texture, float power, float angle, int curl,
			int num) {

		this(world, x, y, team, texture, num);

		// angle -= 90;

		vy = Math.abs(((float) (Math.sin(Math.toRadians(angle))) * power));
		vx = (float) ((float) (Math.cos(Math.toRadians(angle))) * Math.sqrt(power));

		switch (curl) {
		case 1:
			body.setAngularVelocity(10f);
			curlV = 0.03f;
			break;
		case 0:
			curlV = 0.0f;
			break;
		case -1:
			body.setAngularVelocity(-10f);
			curlV = -0.03f;
			break;
		}

		body.setAngularDamping(0.2f);
		body.setLinearVelocity(vx, vy);
		isPhys = true;
	}

	public Stone(World world, float x, float y, int team, Texture texture, float power, float angle, int curl,
			float width, float height, int num) {
		this(world, x, y, team, texture, power, angle, curl, num);

		this.setWidth(width);
		this.setHeight(height);

		sprite.setSize(width, height);
		sprite.setOriginCenter();

		if (curl != 0)
			isStop = false;
	}

	public Stone(World world, float x, float y, int team, Texture texture, float power, float angle, int curl,
			float width, float height, int num, Stone_Type type) {
		this(world, x, y, team, texture, power, angle, curl, width, height, num);

		this.setType(type);
	}

	public Stone(World world, float x, float y, int team, int num) {
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
		circle.setRadius(0.15f);

		def.shape = circle;

		fixture = body.createFixture(def);

		circle.dispose();

		body.setLinearDamping(1f);

		this.team = team;
		this.num = num;
		this.world = world;
	}

	public Stone(World world, float x, float y, int team, int num, Stone_Type type) {
		this(world, x, y, team, num);

		this.setType(type);
	}

	public Stone(World world, float x, float y, int team, int num, float power, float angle, int curl) {
		// super();

		b_def = new BodyDef();
		b_def.type = BodyType.DynamicBody;
		b_def.position.set(x, y);

		body = world.createBody(b_def);

		def = new FixtureDef();
		def.density = 1.0f;
		def.friction = 0.5f;
		def.restitution = 1.0f;

		CircleShape circle = new CircleShape();
		circle.setRadius(0.15f);

		def.shape = circle;

		fixture = body.createFixture(def);

		circle.dispose();

		body.setLinearDamping(1f);

		this.team = team;
		this.num = num;

		vy = Math.abs(((float) (Math.sin(Math.toRadians(angle))) * power));
		vx = (float) ((float) (Math.cos(Math.toRadians(angle))) * Math.sqrt(power));

		switch (curl) {
		case 1:
			body.setAngularVelocity(10f);
			curlV = Float.MIN_VALUE;
			break;
		case 0:
			curlV = 0.0f;
			break;
		case -1:
			body.setAngularVelocity(-10f);
			curlV = -Float.MIN_VALUE;
			break;
		}

		if (curl != 0)
			isStop = false;

		body.setAngularDamping(0.2f);
		body.setLinearVelocity(vx, vy);
		isPhys = true;
		isTemp = true;
		this.world = world;
	}

	public Stone(World world, float x, float y, int team, int num, float power, float angle, int curl,
			Stone_Type type) {
		this(world, x, y, team, num, power, angle, curl);

		this.setType(type);
	}

	@Override
	public void update(float delta) {

		if (!isPhys) {
			sprite.setPosition(this.getX(), this.getY());
			sprite.setSize(this.getWidth(), this.getHeight());
			sprite.setOriginCenter();

			if (!isInfo) {
				// sprite.setRotation(this.getRotation());
				// this.setRotation(this.getRotation() + 5f);
			}
		}

		if (isPhys) {
			this.setPosition(body.getPosition().x, body.getPosition().y);
			this.setRotation(this.getRotation() + body.getAngularVelocity());

			if (!isTemp) {
				sprite.setPosition(this.getX() - (this.getWidth() / 2), this.getY() - (this.getHeight() / 2));
				sprite.setRotation(this.getRotation());
			}

			if (!isStop) {
				curl = body.getLinearVelocity().nor();
				curl = curl.rotate(90);
				curl = new Vector2((curl.x*curlV)*((0.5f+curl.y)/(1+curl.y)), 0);
				body.applyForceToCenter(curl, false);
			}

			if (body.getLinearVelocity().len() < 0.7f) {
				isStop = true;
			}

			if (body.getLinearVelocity().len() <= 0.05f && waitOther) {
				waitOther = false;
				if (Strategy_util.getDistance(getBody().getPosition()) > 1.83f && getBody().getPosition().y < 36.68f && !freeGuard) {
					position_origin = new Vector2(body.getPosition().x,body.getPosition().y);
					freeGuard = true;
				}
			}

			if (outOfRule() && !isRemoved) {
				if (freeGuard) {
					if (Main.total < 5) {
						this.body.getPosition().set(position_origin);
						this.setPosition(position_origin.x, position_origin.y);
					}
				} else {
					synchronized (world) {
						world.destroyBody(body);
					}
					isStop = true;
					waitOther = false;
					this.remove();
					isRemoved = true;
				}
			}
		}

	}

	@Override
	public void draw(Batch batch, float delta) {

		if (!isTemp)
			sprite.draw(batch);

		update(delta);
	}

	public boolean outOfRule() {
		// Shoot and HogLine
		if (body.getLinearVelocity().len() < 0.1f) { // it is stop?
			if (body.getPosition().y < 32.11f) { // it's under than Hog line?
				return true; // Out of Rules
			}
		}

		// it's near from Side bumper?
		if (body.getPosition().x < 0.1f || body.getPosition().x > 4.22f) {
			return true;
		}
		// it's far from back line?
		if (body.getPosition().y > 40.55f) {
			return true;
		}

		// Free Guard Zone
		
		
		return false;
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

	public Texture getTexture() {
		return texture;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public int getNum() {
		return num;
	}

	public void setIsRemoved(boolean isRemoved) {
		this.isRemoved = isRemoved;
	}

	public boolean waitThis() {
		return waitOther;
	}

	public void isInfo() {
		isInfo = true;
	}

	public boolean isRemoved() {
		return isRemoved;
	}

	public void setAngle(float angle) {
		sprite.setRotation(angle);
		this.setRotation(angle);
	}

	public void setType(Stone_Type type) {
		this.type = type;
	}

	public Stone_Type getType() {
		return type;
	}
	
	public void setFreeGuard(boolean freeGuard){
		this.freeGuard = freeGuard;
	}
	
	public boolean getFreeGuard(){
		return freeGuard;
	}

}
