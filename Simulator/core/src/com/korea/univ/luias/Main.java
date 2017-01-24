package com.korea.univ.luias;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.korea.univ.luias.components.Control_view;
import com.korea.univ.luias.components.Full_view;
import com.korea.univ.luias.components.Half_view;
import com.korea.univ.luias.components.Info_view;
import com.korea.univ.luias.components.TeamDialog;
import com.korea.univ.luias.components.system.GameController;
import com.korea.univ.luias.objects.Stone;
import com.korea.univ.luias.objects.Wall;

public class Main extends ApplicationAdapter {

	public static int rthrowCount = 0, ythrowCount = 0, total = 0;
	public static int userTeam = -1, end = 1;
	public static int current = 1;
	public static boolean isStarted = false;

	public static ArrayList<Wall> walls = new ArrayList<Wall>();
	public static ArrayList<Stone> stones = new ArrayList<Stone>();
	
	public static int[][] scoreBoard = new int[2][10];

	public Half_view h_view;
	public Full_view f_view;

	public Info_view i_view;
	public Control_view c_view;
	
	public GameController controller;

	public TeamDialog t_dialog;

	BitmapFont font_black;
	BitmapFont font_black_s;
	BitmapFont font_red;
	BitmapFont font_yellow;

	private Texture redStone;
	private Texture yellowStone;

	World world;

	@Override
	public void create() {
		try {
					
			FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font1.ttf"));
			FreeTypeFontParameter parameter = new FreeTypeFontParameter();

			parameter.size = 20;
			parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890.,/!@#$%^&*()-+=<>;:[]{}`~_";
			parameter.color = Color.BLACK;

			font_black_s = fontGenerator.generateFont(parameter);

			parameter.size = 26;

			font_black = fontGenerator.generateFont(parameter);

			parameter.color = Color.RED;

			font_red = fontGenerator.generateFont(parameter);

			parameter.color = Color.GOLD;

			font_yellow = fontGenerator.generateFont(parameter);

			fontGenerator.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}

		redStone = new Texture(Gdx.files.internal("images/redstone.png"));
		yellowStone = new Texture(Gdx.files.internal("images/yellowstone.png"));

		world = new World(new Vector2(0, 0), true);

		f_view = new Full_view(world);
		h_view = new Half_view(world, redStone, yellowStone);

		i_view = new Info_view(redStone, yellowStone, font_black, font_red, font_yellow);
		c_view = new Control_view(h_view, font_black, font_black_s);

		h_view.setC_view(c_view);
		
		t_dialog = new TeamDialog(this, font_black, font_red, font_yellow);
		
		controller = new GameController();

		Gdx.input.setInputProcessor(t_dialog.getStage());
		
		world.setContactFilter(new ContactFilter() {

			@Override
			public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
				// TODO Auto-generated method stub

				for (Wall w : walls) {

					for (Stone s : stones) {

						if (fixtureA == w.getFixture() || fixtureB == w.getFixture()) {
							Vector2 position;
							switch (w.getType()) {
							case TYPE_TOP:
								position = s.getBody().getPosition();
								if (position.y > w.getBody().getPosition().y + 0.3f)
									return true;
								else
									return false;

							case TYPE_LEFT:
								position = s.getBody().getPosition();
								if (position.x < w.getBody().getPosition().x - 0.3f)
									return true;
								else
									return false;

							case TYPE_RIGHT:
								position = s.getBody().getPosition();
								if (position.x > w.getBody().getPosition().x + 0.3f)
									return true;
								else
									return false;

							case TYPE_BOTTOM:
								position = s.getBody().getPosition();
								if (position.y < w.getBody().getPosition().y + 0.3f)
									return true;
								else
									return false;
							}

						}

					}

				}

				return true;
			}
		});
		
		controller.resetScore();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.step(1 / 60f, 6, 2);
		
		f_view.render();
		h_view.render();
		i_view.render();
		c_view.render();
		t_dialog.render();

		f_view.update();
		h_view.update();
		i_view.update();
		c_view.update();
		t_dialog.update();
		
		controller.checkGameStatus(world,i_view.getStones(),h_view.getStones());
	}

	@Override
	public void dispose() {
		font_black.dispose();
		font_red.dispose();
		font_yellow.dispose();
	}
}
