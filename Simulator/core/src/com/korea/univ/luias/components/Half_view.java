package com.korea.univ.luias.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.korea.univ.luias.Main;
import com.korea.univ.luias.components.system.strategy.TempThread;
import com.korea.univ.luias.objects.Stone;

public class Half_view extends View {

	// 32.01f height, 4.27f width

	private float width = 40f;
	private float height = 40f;

	private float ground_height = 10.06f;
	// 2.3f

	// private float h_offset = 13.21f;
	private Stage stage;
	float mouseX, mouseY, angle;
	float dX[] = new float[3], dY[] = new float[3];

	public Texture redStone;
	public Texture yellowStone;

	private Stone r_temp;
	private Stone y_temp;

	private Array<Stone> stones;

	private boolean isPressed = false;
	// private SpriteBatch batch;

	private float power = 0.0f;
	private Control_view c_view;

	public Half_view(final World world, Texture redStone, Texture yellowStone) {
		camera = new OrthographicCamera();

		viewport = new FitViewport(width, height, camera);

		stage = new Stage(viewport);

		camera.position.set(3.2f, 3.01f, 0);
		camera.zoom = 0.3f;

		shaperenderer = new ShapeRenderer();
		this.redStone = redStone;
		this.yellowStone = yellowStone;

		r_temp = new Stone(redStone);
		y_temp = new Stone(yellowStone);

		r_temp.setPosition(1.735f, 0.12f);
		r_temp.setSize(0.8f, 0.8f);
		y_temp.setPosition(1.735f, 0.12f);
		y_temp.setSize(0.8f, 0.8f);

		stones = new Array<Stone>(20);

		stage.addActor(r_temp);
		stage.addActor(y_temp);

		stage.addListener(new InputListener() {
			@Override
			public boolean mouseMoved(InputEvent event, float x, float y) {
				if (x >= 1.3f && x <= 7.5f) {
					mouseX = x - 2f;
					mouseY = y + 0.65f;
				}

				if (mouseY < 2f)
					return false;

				return true;
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				if (!Main.isStarted)
					return false;

				if (mouseY < 2f)
					return false;

				if (Main.stones.size() > 0) {
					if (Main.stones.get(Main.stones.size() - 1).waitThis())
						return false;
				}
				if (x >= 1.3f && x <= 7.5f) {
					if (power != 0.0f)
						power = 0.0f;

					isPressed = true;
				} else {
					return false;
				}

				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

				if (mouseY < 2f)
					return;

				if (isPressed) {
					isPressed = false;

					if (power < 1f)
						return;

					Main.stones.add(new Stone(world, 2.140f, 3.78f, Main.current,
							(Main.current == 0) ? Half_view.this.redStone : Half_view.this.yellowStone, power,
							(int) angle, c_view.getCurl(), 0.3f, 0.3f, Main.total + 1,
							Main.types[(Main.total + 1) / 5]));

					if (Main.current == 0)
						Main.rthrowCount++;
					else
						Main.ythrowCount++;

					Main.total++;

					Main.current = Main.current == 0 ? 1 : 0;

				}

			}
		});

	}

	public void setC_view(Control_view c_view) {
		this.c_view = c_view;
	}

	@Override
	public void update() {

		if (Main.isStarted) {
			if (Main.stones.size() > 0) {
				if (Main.stones.get(Main.stones.size() - 1).waitThis()) {
					y_temp.setVisible(false);
					r_temp.setVisible(false);
				} else {
					if (Main.current == 0) {
						y_temp.setVisible(false);
						r_temp.setVisible(true);
					} else {
						r_temp.setVisible(false);
						y_temp.setVisible(true);
					}
				}
			} else {
				if (Main.current == 0) {
					y_temp.setVisible(false);
					r_temp.setVisible(true);
				} else {
					r_temp.setVisible(false);
					y_temp.setVisible(true);
				}
			}
		}
		// x2 - x1
		float tempX = mouseX - 2.135f;

		// y2 - y1
		float tempY = mouseY - 0.52f;

		// atan value
		angle = (float) Math.atan2(tempY, tempX);

		// atan value to Degrees
		angle = (float) Math.toDegrees(angle);

		// angle value to Absolute value
		angle = Math.abs(angle);

		r_temp.setAngle(angle - 90);
		y_temp.setAngle(angle - 90);

		if (angle >= 130)
			angle = 130;
		else if (angle <= 60)
			angle = 50;

		dX[0] = (float) (2.135f + Math.cos(Math.toRadians(angle)) * 2f);
		dY[0] = (float) (0.52f + Math.sin(Math.toRadians(angle)) * 2f);

		dX[1] = (float) (2.135f + Math.cos(Math.toRadians(angle + 10)) * 1.5f);
		dY[1] = (float) (0.52f + Math.sin(Math.toRadians(angle + 10)) * 1.5f);

		dX[2] = (float) (2.135f + Math.cos(Math.toRadians(angle - 10)) * 1.5f);
		dY[2] = (float) (0.52f + Math.sin(Math.toRadians(angle - 10)) * 1.5f);

		if (isPressed) {
			if (power >= 49.8f) {
				power = 49.8f;
				return;
			}
			power += 0.2f;
		}

		for (int i = 0; i < Main.stones.size(); i++) {
			Stone s = Main.stones.get(i);

			if (s.getY() < 42.34f - ground_height)
				continue;

			if (s.isRemoved())
				continue;

			if (stones.size < 1)
				stones.add(new Stone(s.getTexture(), s.getX() - 0.15f, ground_height - (42.34f - s.getY()), 0.3f, 0.3f,
						s.getNum()));

			for (int j = 0; j < this.stones.size; j++) {
				Stone s2 = this.stones.get(j);
				if (s2.getNum() == s.getNum()) {
					s2.setFreeGuard(s.getFreeGuard());
					s2.setPosition(s.getX() - 0.15f, ground_height - (42.34f - s.getY()));
					s2.getSprite().setRotation(s.getSprite().getRotation());
				} else if (s.getNum() > stones.size) {
					stones.add(new Stone(s.getTexture(), s.getX() - 0.15f, ground_height - (42.34f - s.getY()), 0.3f,
							0.3f, s.getNum()));
				}

				if (s2.getX() <= 0.05f || s2.getX() >= 4.05f || s2.getY() >= ground_height - (42.34f - 40.5f)) {
					if (!s2.getFreeGuard()) {
						s2.setIsRemoved(true);
						s2.remove();
						stones.removeIndex(j);
						j--;
					}
				}

			}

		}

		for (Stone s : stones) {
			stage.addActor(s);
		}

		if (!Main.isStarted) {
			if (Main.stones.size() < 1) {
				for (int i = 0; i < stones.size; i++)
					stones.get(i).remove();

				stones.clear();
			}
		}

	}

	@Override
	public void render() {
		camera.update();
		shaperenderer.setProjectionMatrix(camera.combined);

		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glViewport(0, 55, 850, 850);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		// =================================== Version 2

		shaperenderer.begin(ShapeType.Filled);
		shaperenderer.setColor(Color.BLACK);
		shaperenderer.rectLine(0.01f, 0.01f, 4.28f, 0.01f, 0.02f);
		shaperenderer.rectLine(0.01f, 8.88f, 4.3f, 8.88f, 0.02f);
		shaperenderer.rectLine(0.01f, 0.01f, 0.01f, 8.87f, 0.02f);
		shaperenderer.rectLine(4.3f, 0.01f, 4.3f, 8.87f, 0.02f);

		shaperenderer.setColor(new Color(0, 0, 0, 0.3f));
		shaperenderer.rect(0.03f, 0.05f, 4.23f, 8.80f);

		shaperenderer.setColor(new Color(1, 1, 1, 0.9f));
		shaperenderer.rect(0.03f, 0.05f, 4.23f, 8.80f);

		shaperenderer.setColor(new Color(0, 0, 1, 0.7f));
		shaperenderer.circle(0.01f + 2.135f, 0.01f + 6.40f, 1.83f, 50);

		shaperenderer.setColor(new Color(1, 1, 1, 1f));
		shaperenderer.circle(0.01f + 2.135f, 0.01f + 6.40f, 1.22f, 50);
		shaperenderer.setColor(new Color(1, 0, 0, 0.6f));
		shaperenderer.circle(0.01f + 2.135f, 0.01f + 6.40f, 0.61f, 50);
		shaperenderer.setColor(new Color(1, 1, 1, 1f));
		shaperenderer.circle(0.01f + 2.135f, 0.01f + 6.40f, 0.15f, 50);
		shaperenderer.end();

		shaperenderer.begin(ShapeType.Line);
		shaperenderer.setColor(Color.BLACK);
		shaperenderer.line(0.01f, 6.41f, 4.28f, 6.41f);
		shaperenderer.line(0.01f, 8.24f, 4.28f, 8.24f);
		shaperenderer.line(2.145f, 8.87f, 2.145f, 0.01f);
		shaperenderer.end();

		stage.draw();

		shaperenderer.begin(ShapeType.Filled);
		/*
		if (Main.isStarted) {
			if (Main.stones.size() > 0) {
				if (!Main.stones.get(Main.stones.size() - 1).waitThis()) {
					shaperenderer.setColor(Color.CORAL);
					shaperenderer.rectLine(2.135f, 0.52f, dX[0], dY[0], 0.05f);
					shaperenderer.rectLine(dX[0], dY[0], dX[1], dY[1], 0.05f);
					shaperenderer.rectLine(dX[0], dY[0], dX[2], dY[2], 0.05f);
				}
			} else {
				shaperenderer.setColor(Color.CORAL);
				shaperenderer.rectLine(2.135f, 0.52f, dX[0], dY[0], 0.05f);
				shaperenderer.rectLine(dX[0], dY[0], dX[1], dY[1], 0.05f);
				shaperenderer.rectLine(dX[0], dY[0], dX[2], dY[2], 0.05f);
			}
		}
		*/
		shaperenderer.setColor(Color.RED);
		shaperenderer.circle(TempThread.p.getX(), ground_height - (42.34f - TempThread.p.getY()), 0.2f);
		
		shaperenderer.end();

	}

	public Stage getStage() {
		return stage;
	}

	public float getAngle() {
		return angle;
	}

	public float getPower() {
		return power;
	}

	public Array<Stone> getStones() {
		return stones;
	}
}
