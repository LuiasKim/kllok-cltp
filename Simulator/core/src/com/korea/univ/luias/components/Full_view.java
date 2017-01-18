package com.korea.univ.luias.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Full_view extends View{
	
	private float width =  50f;
	private float height = 50f;
	
	private float w_offset = 2.745f;
	private float h_offset = 22.865f;
	
	public Full_view(){
		camera = new OrthographicCamera();
		
		viewport = new FitViewport(width, height,camera);
		viewport.apply();
		
		camera.position.set(width/2,45f,0);
		//camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		
		shaperenderer = new ShapeRenderer();
		
		camera.zoom = 0.9f;
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
			shaperenderer.rect((width-46.51f)/2, (height-5.27f)/2, 46.51f, 5.27f);
		
			shaperenderer.setColor(new Color(1,1,1,0.9f));
			shaperenderer.rect((width-44.51f)/2, (height-4.27f)/2, 44.51f, 4.27f);
		shaperenderer.end();
		
		
		shaperenderer.begin(ShapeType.Line);
			shaperenderer.setColor(new Color(0,0,0,1f));
			//hack under
			shaperenderer.line(w_offset+1.22f, (h_offset+2.135f-0.1f), w_offset+1.22f, (h_offset+2.135f-0.5f));
			shaperenderer.line(w_offset+1.22f, (h_offset+2.135f+0.1f), w_offset+1.22f, (h_offset+2.135f+0.5f));
			
			//hack upper
			shaperenderer.line(width-(w_offset+1.22f), (h_offset+2.135f-0.1f), width-(w_offset+1.22f), (h_offset+2.135f-0.5f));
			shaperenderer.line(width-(w_offset+1.22f), (h_offset+2.135f+0.1f), width-(w_offset+1.22f), (h_offset+2.135f+0.5f));
		shaperenderer.end();
		
		//circle under
		shaperenderer.begin(ShapeType.Filled);
			shaperenderer.setColor(new Color(0,0,1,0.65f));
			shaperenderer.circle(w_offset+4.88f, h_offset+2.135f, 1.83f,100);
			shaperenderer.setColor(new Color(1,1,1,0.9f));
			shaperenderer.circle(w_offset+4.88f, h_offset+2.135f, 1.22f,100);
			shaperenderer.setColor(new Color(1,0,0,0.65f));
			shaperenderer.circle(w_offset+4.88f, h_offset+2.135f, 0.61f,100);
			shaperenderer.setColor(new Color(1,1,1,0.9f));
			shaperenderer.circle(w_offset+4.88f, h_offset+2.135f, 0.15f,100);
		shaperenderer.end();
		
		//circle upper
		shaperenderer.begin(ShapeType.Filled);
			shaperenderer.setColor(new Color(0,0,1,0.65f));
			shaperenderer.circle(width-(w_offset+4.88f), h_offset+2.135f, 1.83f,100);
			shaperenderer.setColor(new Color(1,1,1,0.9f));
			shaperenderer.circle(width-(w_offset+4.88f), h_offset+2.135f, 1.22f,100);
			shaperenderer.setColor(new Color(1,0,0,0.65f));
			shaperenderer.circle(width-(w_offset+4.88f), h_offset+2.135f, 0.61f,100);
			shaperenderer.setColor(new Color(1,1,1,0.9f));
			shaperenderer.circle(width-(w_offset+4.88f), h_offset+2.135f, 0.15f,100);
		shaperenderer.end();
		
	}
	
}
