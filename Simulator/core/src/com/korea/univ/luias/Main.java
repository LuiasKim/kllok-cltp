package com.korea.univ.luias;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.korea.univ.luias.components.Control_view;
import com.korea.univ.luias.components.Full_view;
import com.korea.univ.luias.components.Half_view;
import com.korea.univ.luias.components.Info_view;

public class Main extends ApplicationAdapter {
	
	public static int rthrowCount = 0,ythrowCount = 0;
	public static int current = -1;
	public static boolean isStarted = false;
	
	Half_view h_view;
	Full_view f_view;
	
	Info_view i_view;
	Control_view c_view;
	
	BitmapFont font_black;
	BitmapFont font_red;
	BitmapFont font_yellow;
	
	@Override
	public void create () {
		try{
		FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font1.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		
		parameter.size = 26;
		parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890.,/!@#$%^&*()-+=<>;:[]{}`~";
		parameter.color = Color.BLACK;
		
		font_black = fontGenerator.generateFont(parameter);
		
		parameter.color = Color.RED;
		
		font_red = fontGenerator.generateFont(parameter);
		
		parameter.color = Color.GOLD;
		
		font_yellow = fontGenerator.generateFont(parameter);
		
		fontGenerator.dispose();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		f_view = new Full_view();
		h_view = new Half_view();
		
		i_view = new Info_view(font_black, font_red, font_yellow);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		f_view.render();
		h_view.render();
		i_view.render();
		
		f_view.update();
		h_view.update();
		i_view.update();
	}
	
	@Override
	public void dispose () {
		font_black.dispose();
		font_red.dispose();
		font_yellow.dispose();
	}
}
