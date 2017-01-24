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
import com.korea.univ.luias.objects.Wall;
import com.korea.univ.luias.objects.Wall.Wall_Type;

public class Full_view extends View{
	
	private float width =  50f;
	private float height = 50f;
	
	private float w_offset = 2.745f;
	private float h_offset = 22.865f;
	
	
	private Stage stage;
	
	public Full_view(World world){
		camera = new OrthographicCamera();
		
		viewport = new FitViewport(width, height,camera);
		viewport.apply();
		
		stage = new Stage(viewport);
		
		camera.position.set(width/2,45f,0);
		
		camera.zoom = 0.9f;
		
		shaperenderer = new ShapeRenderer();	
		
		//create wall ---------------------------------------
		
		Main.walls.add(new Wall(world,width,0.1f,width/2,(height-4.57f)/2 ,Wall_Type.TYPE_BOTTOM));
		Main.walls.add(new Wall(world,width,0.1f,width/2,((height-4.57f)/2)+4.57f  ,Wall_Type.TYPE_TOP));
		Main.walls.add(new Wall(world,0.1f,2.135f,5.53f,height/2  ,Wall_Type.TYPE_LEFT));
		Main.walls.add(new Wall(world,0.1f,2.135f,width-5.53f,height/2  ,Wall_Type.TYPE_RIGHT));
		
		//---------------------------------------------------
	}
	
	@Override
	public void update(){
		
		if(Main.stones.size() > 0)
			stage.addActor(Main.stones.get(Main.total-1));
		
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
			//shaperenderer.rect((width-46.51f)/2, (height-5.27f)/2, 46.51f, 5.27f);
			shaperenderer.rectLine(((width-46.51f)/2), (height-h_offset-4.57f),47.51f,(height-h_offset-4.57f), 0.25f);
			shaperenderer.rectLine(((width-46.51f)/2), (height-h_offset+0.5f),47.51f,(height-h_offset+0.5f), 0.25f);
			shaperenderer.rectLine(w_offset-0.15f, (height-h_offset-4.57f),w_offset-0.15f,(height-h_offset+0.5f), 0.25f);
			shaperenderer.rectLine(47.46f, (height-h_offset-4.57f),47.46f,(height-h_offset+0.5f), 0.25f);
		shaperenderer.end();
			
		shaperenderer.begin(ShapeType.Filled);
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
		
		shaperenderer.begin(ShapeType.Line);
			shaperenderer.setColor(Color.BLACK);
			shaperenderer.line(w_offset+3.05f, h_offset,w_offset+3.05f,h_offset+4.27f);
			shaperenderer.line(w_offset+4.88f, h_offset,w_offset+4.88f,h_offset+4.28f);
			shaperenderer.line(w_offset+11.28f, h_offset,w_offset+11.28f,h_offset+4.28f);
			shaperenderer.line(w_offset+33.23f, h_offset,w_offset+33.23f,h_offset+4.28f);
			shaperenderer.line(w_offset+39.63f, h_offset,w_offset+39.63f,h_offset+4.28f);//----------
			shaperenderer.line(w_offset+41.46f, h_offset,w_offset+41.46f,h_offset+4.28f);
			shaperenderer.line(w_offset+4.88f, h_offset+2.135f,w_offset+41.46f,h_offset+2.135f); //----------
			
		shaperenderer.end();
		stage.draw();
		
	}
	
}
