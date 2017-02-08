package com.korea.univ.luias.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.korea.univ.luias.Main;

public class Full_view extends View{
	
	//version 2 height 44.51 - 2
	private float width =  50f;
	private float height = 50f;
	
	
	private Stage stage;
	
	public Full_view(World world){
		camera = new OrthographicCamera();
		
		viewport = new FitViewport(width, height,camera);
		viewport.apply();
		
		stage = new Stage(viewport);
		camera.position.set(20.8f,21.24f, 0);
		camera.zoom = 0.85f;
		shaperenderer = new ShapeRenderer();	
	}
	
	@Override
	public void update(){
		
		if(Main.stones.size() > 0){
			if(!Main.stones.get(Main.stones.size()-1).isRemoved())
				stage.addActor(Main.stones.get(Main.stones.size()-1));
		}
		
	}
	
	@Override
	public void render(){
		camera.update();
		shaperenderer.setProjectionMatrix(camera.combined);
		
	
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glViewport(0, 0, 900,900);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		// ============================================ Version 2
		// background
		shaperenderer.begin(ShapeType.Filled);
		shaperenderer.setColor(Color.BLACK);
		shaperenderer.rectLine(0, 0.05f, 4.32f, 0.05f, 0.05f);
		shaperenderer.rectLine(0, 42.46f, 4.32f, 42.46f, 0.05f);
		shaperenderer.rectLine(0.05f, 0.05f, 0.05f, 42.46f, 0.05f);
		shaperenderer.rectLine(4.33f, 0.05f, 4.33f, 42.46f, 0.05f);
		
		shaperenderer.setColor(new Color(0,0,0,0.3f));
		shaperenderer.rect(0.05f, 0.05f, 4.27f, 42.41f);
		
		shaperenderer.setColor(new Color(1,1,1,0.9f));
		shaperenderer.rect(0.05f, 0.05f, 4.27f, 42.41f);
		
		// ============================================= house
		shaperenderer.setColor(new Color(0,0,1,0.7f));
		shaperenderer.circle(0.04f+2.135f,3.78f, 1.83f,50);
		shaperenderer.setColor(new Color(1,1,1,1f));
		shaperenderer.circle(0.04f+2.135f,3.78f, 1.22f,50);
		shaperenderer.setColor(new Color(1,0,0,0.6f));
		shaperenderer.circle(0.04f+2.135f,3.78f, 0.61f,50);
		shaperenderer.setColor(new Color(1,1,1,1f));
		shaperenderer.circle(0.04f+2.135f,3.78f, 0.15f,50);
		
		shaperenderer.setColor(new Color(0,0,1,0.7f));
		shaperenderer.circle(0.04f+2.135f,38.53f, 1.83f,50);
		shaperenderer.setColor(new Color(1,1,1,1f));
		shaperenderer.circle(0.04f+2.135f,38.53f, 1.22f,50);
		shaperenderer.setColor(new Color(1,0,0,0.6f));
		shaperenderer.circle(0.04f+2.135f,38.53f, 0.61f,50);
		shaperenderer.setColor(new Color(1,1,1,1f));
		shaperenderer.circle(0.04f+2.135f,38.53f, 0.15f,50);
		// ===================================================
		shaperenderer.end();
		
		shaperenderer.begin(ShapeType.Line);
		shaperenderer.setColor(Color.BLACK);
		shaperenderer.line((0.05f+2.135f-0.6f), 0.12f, 0.05f+2.135f-0.1f, 0.12f);
		shaperenderer.line((0.05f+2.135f+0.6f), 0.12f, 0.05f+2.135f+0.1f, 0.12f);
		shaperenderer.line((0.05f+2.135f-0.6f), 42.27f, 0.05f+2.135f-0.1f, 42.27f);
		shaperenderer.line((0.05f+2.135f+0.6f), 42.27f, 0.05f+2.135f+0.1f, 42.27f);
		
		shaperenderer.line(0.05f+2.135f, 0.12f,0.05f+2.135f,42.27f);
		shaperenderer.line(0.05f, 3.78f,4.32f,3.78f);
		shaperenderer.line(0.05f, 38.53f,4.32f,38.53f);
		shaperenderer.line(0.05f, 1.95f,4.32f,1.95f);
		shaperenderer.line(0.05f, 40.33f,4.32f,40.33f);
		shaperenderer.line(0.05f, 10.18f,4.32f,10.18f);
		shaperenderer.line(0.05f, 32.13f,4.32f,32.13f);
		shaperenderer.line(0.05f, 21.155f,4.32f,21.155f);
		shaperenderer.end();
		
		stage.draw();
	}
	
}
