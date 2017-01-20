package com.korea.univ.luias.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.korea.univ.luias.Main;
import com.korea.univ.luias.objects.Stone;

public class Half_view extends View{
	
	//32.01f height, 4.27f width
	
	private float width =  40f;
	private float height = 40f;
	
	private float ground_height = 13.58f;
	//2.3f
	
	//private float h_offset = 13.21f;
	private Stage stage;
	float mouseX, mouseY, angle;
	float dX[] = new float [3],dY[] = new float [3];
	
	private Texture redStone;
	private Texture yellowStone;
	
	private Stone r_temp;
	private Stone y_temp;
	
	private boolean isPressed = false;
	private SpriteBatch batch;
	
	private float power = 0.0f;
	private Control_view c_view;
	public Half_view(final World world, Texture redStone, Texture yellowStone){
		
		camera = new OrthographicCamera();
		
		viewport = new FitViewport(width,height,camera);
		viewport.apply();
		
		stage = new Stage(viewport);
		camera.position.set(width/25, 6.125f ,0);
		
		shaperenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		
		camera.zoom = 0.4f;
	
		this.redStone = redStone;
		this.yellowStone = yellowStone;
		
		r_temp = new Stone(redStone);
		y_temp = new Stone(yellowStone);
		
		r_temp.setPosition(1.885f, 1.57f);
		r_temp.setSize(1, 1);
		y_temp.setPosition(1.885f, 1.57f);
		y_temp.setSize(1, 1);
		
		stage.addActor(r_temp);
		stage.addActor(y_temp);
		
		stage.addListener(new InputListener(){
			@Override
			public boolean mouseMoved(InputEvent event, float x, float y){
				mouseX = x;
				mouseY = y;
				
				return true;
			}
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y,int pointer, int button){
				
				if(x >=0 && x <= 5){
					if(power != 0.0f)
						power = 0.0f;
				
					isPressed = true;
				}else{
					return false;
				}
				
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				
				if(isPressed){
					isPressed = false;
					
					Main.stones.add(new Stone(
							world,14.025f,25f,
							Main.current,(Main.current == 0) ? Half_view.this.redStone : Half_view.this.yellowStone,power,(int)angle,c_view.getCurl(),0.6f,0.6f));
					
					if(Main.current == 0)
						Main.rthrowCount ++;
					else
						Main.ythrowCount ++;
					
					
					
					Main.current = Main.current == 0 ? 1 : 0;
					
					
					
				}
				
			}
		});
		
	}
	
	public void setC_view(Control_view c_view){
		this.c_view = c_view;
	}
	
	@Override
	public void update(){
		
		if(Main.current == 0){
			y_temp.setVisible(false);
			r_temp.setVisible(true);
		}else{
			r_temp.setVisible(false);
			y_temp.setVisible(true);
		}
		
		//x2 - x1
		float tempX = mouseX - 2.385f;
		
		//y2 - y1
		float tempY = mouseY - 1.47f;
		
		//atan value
		angle = (float)Math.atan2(tempY, tempX);
		
		//atan value to Degrees
		angle = (float)Math.toDegrees(angle);
		
		//angle value to Absolute value
		angle = Math.abs(angle);
		
		dX[0] = (float)(2.385f + Math.cos(Math.toRadians(angle))*2f);
		dY[0] = (float)(2.05f + Math.sin(Math.toRadians(angle))*2f);
		
		dX[1] = (float)(2.385f + Math.cos(Math.toRadians(angle+10))*1.5f);
		dY[1] = (float)(2.05f + Math.sin(Math.toRadians(angle+10))*1.5f);
		
		dX[2] = (float)(2.385f + Math.cos(Math.toRadians(angle-10))*1.5f);
		dY[2] = (float)(2.05f + Math.sin(Math.toRadians(angle-10))*1.5f);
		
		if(isPressed){
			if(power >= 39.8f){
				power = 39.8f;
				return;
			}
			power += 0.1f;
		}
		/*
		if(Main.stones.size() > 0){
			for(Stone s : Main.stones){
				if(s.getX() > 29.27f){
					stage.addActor(new Stone(s.getTexture(),s.getY(),s.getX(),0.8f,0.8f));
				}
				for(int i = 0; i < stage.getActors().size; i++){
					Stone st = (Stone)stage.getActors().get(i);
					
				}
			}
		}
		*/
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
			shaperenderer.circle(2.385f, ground_height-(+1.58f), 1.83f,100);
			shaperenderer.setColor(new Color(1,1,1,0.9f));
			shaperenderer.circle(2.385f, ground_height-(+1.58f), 1.22f,100);
			shaperenderer.setColor(new Color(1,0,0,0.65f));
			shaperenderer.circle(2.385f, ground_height-(+1.58f), 0.61f,100);
			shaperenderer.setColor(new Color(1,1,1,0.9f));
			shaperenderer.circle(2.385f, ground_height-(+1.58f), 0.15f,100);
		shaperenderer.end();
		
		//shoot rendering
		
		shaperenderer.begin(ShapeType.Line);
			shaperenderer.setColor(Color.BLACK);
			shaperenderer.line(0.25f, ground_height-1.58f, 4.52f, ground_height-1.58f);
			shaperenderer.line(0.25f, ground_height+0.25f, 4.52f, ground_height+0.25f);
			shaperenderer.line(2.385f, ground_height-1.58f, 2.385f, 0.25f);
			
		
		shaperenderer.end();
		
		stage.draw();
		
		shaperenderer.begin(ShapeType.Filled);
		
			shaperenderer.setColor(Color.CORAL);
			shaperenderer.rectLine(2.385f, 2.05f, dX[0], dY[0], 0.1f);
			shaperenderer.rectLine(dX[0], dY[0], dX[1], dY[1], 0.1f);
			shaperenderer.rectLine(dX[0], dY[0], dX[2], dY[2], 0.1f);
			
		shaperenderer.end();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for(Stone s : Main.stones){
			if(s.getX() >44.51f-ground_height)
				batch.draw(s.getTexture(), (s.getY()-25.25f)+2.385f, ground_height-(44.26f-s.getX()), 0.6f,0.6f);
			
		}
		batch.end();
		
		
	}
	public Stage getStage(){
		return stage;
	}
	
	public float getAngle(){
		return angle;
	}
	
	public float getPower(){
		return power;
	}
}
