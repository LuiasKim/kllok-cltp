package com.korea.univ.luias.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.korea.univ.luias.Main;

public class Half_view extends View{
	
	//32.01f height, 4.27f width
	
	private float width =  40f;
	private float height = 40f;
	
	private float ground_height = 13.58f;
	//2.3f
	
	//private float h_offset = 13.21f;
	
	public Half_view(){
		
		camera = new OrthographicCamera();
		
		viewport = new FitViewport(width,height,camera);
		viewport.apply();
		
		camera.position.set(width/25, 6.125f ,0);
		
		shaperenderer = new ShapeRenderer();
		
		
		camera.zoom = 0.4f;
		
		 
	}
	
	@Override
	public void update(){
		
	}
	
	@Override
	public void render(){
		camera.update();
		shaperenderer.setProjectionMatrix(camera.combined);
	
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		//background
 
		shaperenderer.begin(ShapeType.Filled);
			shaperenderer.setColor(new Color(0,0,0,0.9f));
			shaperenderer.rect(0, 0, 4.77f, ground_height+0.5f);
			
			shaperenderer.setColor(new Color(1,1,1,0.9f));
			shaperenderer.rect(0.25f, 0.25f, 4.27f, ground_height);
		shaperenderer.end();
			
		shaperenderer.begin(ShapeType.Line);
			shaperenderer.setColor(new Color(0,0,0,1f));
			//hack under
			shaperenderer.line(2.385f+0.1f, 1.47f, 2.385f+0.3f, 1.47f);
			shaperenderer.line(2.385f-0.1f, 1.47f, 2.385f-0.3f, 1.47f);
			
		shaperenderer.end();
			
			//circle upper
		shaperenderer.begin(ShapeType.Filled);
			shaperenderer.setColor(new Color(0,0,1,0.65f));
			shaperenderer.circle(2.385f, ground_height-(+1.83f), 1.83f,100);
			shaperenderer.setColor(new Color(1,1,1,0.9f));
			shaperenderer.circle(2.385f, ground_height-(+1.83f), 1.22f,100);
			shaperenderer.setColor(new Color(1,0,0,0.65f));
			shaperenderer.circle(2.385f, ground_height-(+1.83f), 0.61f,100);
			shaperenderer.setColor(new Color(1,1,1,0.9f));
			shaperenderer.circle(2.385f, ground_height-(+1.83f), 0.15f,100);
		shaperenderer.end();
		
		//shoot rendering
		
		shaperenderer.begin(ShapeType.Filled);
			if(Main.current == 0)
				shaperenderer.setColor(Color.RED);
			else
				shaperenderer.setColor(Color.GOLD);
			
			shaperenderer.circle(2.385f, 1.62f, 0.15f,100);
		shaperenderer.end();
		
		//direction view
	}
	
}
