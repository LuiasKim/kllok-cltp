package com.korea.univ.luias.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.korea.univ.luias.Main;

public class Score_view extends View{

	private float width =  50f;
	private float height = 50f;
	
	BitmapFont red,yellow,black;
	
	SpriteBatch batch;
	
	OrthographicCamera camera2;
	FitViewport viewport2;
	
	public Score_view(BitmapFont ... fonts){
		
		camera = new OrthographicCamera();
		camera2 = new OrthographicCamera();
		
		viewport = new FitViewport(width, height,camera);
		viewport.apply();
		
		viewport2 = new FitViewport(650f,650f,camera2);
		viewport2.apply();
		
		camera2.position.set(235f,295f,0);
		camera2.zoom = 1f;
		
		camera.position.set(9.4f,12.4f, 0);
		camera.zoom = 0.5f;
		shaperenderer = new ShapeRenderer();
		
		red = fonts[0];
		yellow = fonts[1];
		black = fonts[2];
		
		batch = new SpriteBatch();
		
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		Gdx.gl.glViewport(30, 5, 800, 800);
		camera.update();
		camera2.update();
		shaperenderer.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera2.combined);
	
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		
		
		shaperenderer.begin(ShapeType.Filled);
		shaperenderer.setColor(Color.BLACK);
		
		float x = 1.3f;
	    for(int i = 1; i < 13; i+=1,x+=1.055f)
	    	shaperenderer.rectLine(x, 3.65f,x,0.05f, 0.03f);
		
		    shaperenderer.rectLine(0.05f, 0.05f,x,0.05f, 0.03f);
		    shaperenderer.rectLine(0.05f, 3.66f,x,3.66f, 0.03f);
		    shaperenderer.rectLine(0.05f, 2.45f,x,2.45f, 0.03f);
		    shaperenderer.rectLine(0.05f, 1.25f,x,1.25f, 0.03f);
		    
		    shaperenderer.rectLine(0.05f, 3.65f,0.05f,0.05f, 0.03f);
		    shaperenderer.rectLine(x, 3.65f,x,0.05f, 0.03f);
		    
		    
		shaperenderer.end();
		
		batch.begin();
		
			x = 4f;
			for(int i = 1; i < 11; i++){
				if(i <= 9)
					x += 27.5f;
				else
					x += 22f;
				
				black.draw(batch, String.valueOf(i), x, 58f);
				
			}
			
			red.draw(batch, "R", 0f, 29f);
			yellow.draw(batch, "Y", 0, -3f);
			
			black.draw(batch,"S",333.5f,58f);
			
			x = 32f;
			for (int i = 0; i < 5; i++, x += 28f, x -= 0.5f){
				if(Main.scoreBoard[0][i] == -1)
					break;
				
				black.draw(batch, String.valueOf(Main.scoreBoard[0][i]), x, 30f);
				black.draw(batch, String.valueOf(Main.scoreBoard[1][i]), x, 0f);

			}
			
			
		
		batch.end();
		
	}
	

}
