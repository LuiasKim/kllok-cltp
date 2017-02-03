package com.korea.univ.luias.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Control_view extends View{

	private Stage stage;
	private SpriteBatch batch;
	
	private float width = 600;
	private float height = 600;
	
	private Half_view h_view;
	
	private Button[] buttons;
	private TextureRegionDrawable textures[];
	
	private int curl;
	
	private String filePath[] = {
			"images/arrow/outturn.png",
			"images/arrow/outturn_press.png",
			"images/arrow/inturn.png",
			"images/arrow/inturn_press.png",
	};
	
	public Control_view(Half_view h_view){
		super();
		
		camera = new OrthographicCamera();
		
		viewport = new FitViewport(width,height,camera);
		stage = new Stage(viewport);
		camera.position.set(100,170,0);
		
		batch = new SpriteBatch();
		
		this.h_view = h_view;
		
		shaperenderer = new ShapeRenderer();
		
		textures = new TextureRegionDrawable[4];
		for(int i =0; i < 4; i++)
			textures[i] = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(filePath[i]))));
		
		buttons = new Button[2];
		
		
		float x = 10;
		buttons[0] = new Button(textures[0],textures[1],textures[1]);
		buttons[1] = new Button(textures[2],textures[3],textures[3]);
		
		for(int i = 0; i < 2; i ++,x+=45){
			buttons[i].setSize(40, 50);
			buttons[i].setPosition(x, 0);
			buttons[i].localToStageCoordinates(new Vector2(20,-20));
			buttons[i].setChecked(false);
			stage.addActor(buttons[i]);
		}
		
		buttons[0].addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				buttons[1].setChecked(false);
				
				curl = 1;
				return true;
			}
		});
		buttons[1].addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				buttons[0].setChecked(false);
				
				curl = -1;
				return true;
			}
		});
		
	}
	
	@Override
	public void update(){
		stage.act();
	}
	
	@Override
	public void render(){
		Gdx.gl.glViewport(0, 150, 600, 600);
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		shaperenderer.setProjectionMatrix(camera.combined);
		
		float power = (h_view.getPower()/50)*100;
		
		shaperenderer.begin(ShapeType.Filled);
		
			shaperenderer.setColor(Color.RED);
			shaperenderer.rect(255f,height-600f,30,power);
	
		shaperenderer.end();
		
		shaperenderer.begin(ShapeType.Line);
		
			shaperenderer.setColor(Color.BLACK);
			shaperenderer.rect(255, height-600, 30,100);
			float y = 600;
			for(int i = 0; i < 10; i++,y-=10)
				shaperenderer.line(255, height-y, 285,height-y);
	
		shaperenderer.end();
		
		stage.draw();
	}
	
	public Stage getStage(){
		return stage;
	}
	
	public int getCurl(){
		return curl;
	}
	
}
