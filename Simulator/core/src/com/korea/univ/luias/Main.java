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
import com.badlogic.gdx.physics.box2d.World;
import com.korea.univ.luias.components.Control_view;
import com.korea.univ.luias.components.Full_view;
import com.korea.univ.luias.components.Half_view;
import com.korea.univ.luias.components.Info_view;
import com.korea.univ.luias.components.Score_view;
import com.korea.univ.luias.components.TeamDialog;
import com.korea.univ.luias.components.system.GameController;
import com.korea.univ.luias.components.system.strategy.Computer_strategy;
import com.korea.univ.luias.objects.Stone;
import com.korea.univ.luias.objects.Stone.Stone_Type;

public class Main extends ApplicationAdapter {

	public static int rthrowCount = 0, ythrowCount = 0, total = 0;
	public static int userTeam = -1, end = 1;
	public static int current = 1;
	public static boolean isStarted = false;

	public volatile static ArrayList<Stone> stones = new ArrayList<Stone>();
	public static Stone_Type types[] = {Stone_Type.Type_Lead, Stone_Type.Type_Second, Stone_Type.Type_Third, Stone_Type.Type_Fourth};
	 
	public static int[][] scoreBoard = new int[2][10];

	public Half_view h_view;
	public Full_view f_view;

	public Info_view i_view;
	public Control_view c_view;
	
	public Score_view s_view;
	public Computer_strategy cs;
	
	public GameController controller;

	public TeamDialog t_dialog;

	BitmapFont font_black;
	BitmapFont font_black_s;
	
	
	BitmapFont font_red;
	BitmapFont font_yellow;

	private Texture redStone;
	private Texture yellowStone;

	public World world;

	@Override
	public void create() {
		try {
					
			FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font2.ttf"));
			FreeTypeFontParameter parameter = new FreeTypeFontParameter();

			parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890.,/!@#$%^&*()-+=<>;:[]{}`~_";
			parameter.size = 16;
			
			parameter.color = Color.BLACK;
			font_black_s = fontGenerator.generateFont(parameter);
			
			parameter.size = 20;
			
			parameter.color = Color.BLACK;
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

		i_view = new Info_view(font_black, font_red, font_yellow);
		c_view = new Control_view(h_view);
		s_view = new Score_view(font_red,font_yellow,font_black);

		h_view.setC_view(c_view);
		
		t_dialog = new TeamDialog(this, font_black, font_red, font_yellow);
		
		controller = new GameController();
		cs = new Computer_strategy(world,h_view);
		
		Gdx.input.setInputProcessor(t_dialog.getStage());

		controller.resetScore();
		
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		for(int i = 0; i < stones.size() ;i++){
			if(stones.get(i).isRemoved()){
				stones.remove(i);
				i = 0;
			}
		}
		
		if(isStarted)
			world.step(1/ 30f, 12, 4);
		
		f_view.render();
		h_view.render();
		s_view.render();
		i_view.render();
		c_view.render();
		t_dialog.render();
		
		f_view.update();
		h_view.update();
		s_view.update();
		i_view.update();
		c_view.update();
		t_dialog.update();
		//cs.update();
		
		
		controller.checkGameStatus(world,i_view.getStones(),h_view.getStones());
	}

	@Override
	public void dispose() {
		font_black.dispose();
		font_black_s.dispose();
		font_red.dispose();
		font_yellow.dispose();
		
		redStone.dispose();
		yellowStone.dispose();
		
		i_view.dispose();
	}
}
