package com.korea.univ.luias.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Control_view extends View{

	private Stage stage;
	private SpriteBatch batch;
	
	private float width = 600;
	private float height = 600;
	
	private BitmapFont font;
	private BitmapFont font_s;
	private Half_view h_view;
	
	private TextButton[] buttons;
	private TextureRegionDrawable textures[];
	
	private int curl;
	
	private String btText[] = {
			"Left","Non","Right"
	};
	
	public Control_view(Half_view h_view,BitmapFont ... fonts){
		super();
		
		camera = new OrthographicCamera();
		viewport = new FitViewport(width,height,camera);
		
		
		stage = new Stage(viewport);
		camera.position.set(-120,300,0);
		
		batch = new SpriteBatch();
		
		this.font = fonts[0];
		this.font_s = fonts[1];
		this.h_view = h_view;
		
		shaperenderer = new ShapeRenderer();
		
		textures = new TextureRegionDrawable[2];
		textures[0] = new TextureRegionDrawable(new TextureRegion( new Texture(Gdx.files.internal("images/button.png"))));
		textures[1] = new TextureRegionDrawable(new TextureRegion( new Texture(Gdx.files.internal("images/button_push.png"))));
		
		buttons = new TextButton[3];
		TextButtonStyle btStyle = new TextButtonStyle(textures[0], textures[1], null, font_s);
		
		
		float x = 0;
		for(int i = 0; i < 3; i++, x+= 55){
			buttons[i] = new TextButton(btText[i], btStyle);
			buttons[i].setSize(60, 30);
			buttons[i].setPosition(x, height - 435);
			stage.addActor(buttons[i]);
		}
		
		buttons[0].addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				curl = 1;
				return true;
			}
		});
		buttons[1].addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				curl = 0;
				return true;
			}
		});
		buttons[2].addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
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
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		shaperenderer.setProjectionMatrix(camera.combined);
		
		batch.begin();
		
			font.draw(batch, "Computer_", 7, height-10);
			font_s.draw(batch, "Strategy : ", 7, height - 60);
			font.draw(batch, "Unknown", 7 + 83, height-58);
			
			font_s.draw(batch, "Angle : ", 7 , height - 100);
			font.draw(batch, "40", 7+63, height - 98);
			
			font_s.draw(batch, "Curl : ", 7 , height - 140);
			font.draw(batch, "Inner", 7+53,height - 138);
			
			font_s.draw(batch, "Power", 7 , height - 180);
			
			font_s.draw(batch, "0" , 7, height - 220);
			font_s.draw(batch, "100" , 95, height - 220);
			font_s.draw(batch, "M" , 145, height - 220);
			
			
		batch.end();
		
		
		shaperenderer.begin(ShapeType.Filled);
		
			shaperenderer.setColor(Color.CHARTREUSE);
			shaperenderer.rect(7.1f,height-217.5f,100,19.8f);
		
		shaperenderer.end();
		
		shaperenderer.begin(ShapeType.Line);
		
			shaperenderer.setColor(Color.BLACK);
			shaperenderer.rect(7, height-218, 150,20);
			shaperenderer.rect(7, height-218, 100,20);
		
		shaperenderer.end();
		
		
		
		batch.begin();
		
			font.draw(batch, "User_", 7 , height - 290);
			
			font_s.draw(batch, "Angle : ", 7, height - 340);
			font.draw(batch, String.valueOf((int)(h_view.getAngle())), 7 + 63, height - 338);
			
			font_s.draw(batch, "Curl", 7, height - 380);
			
			font_s.draw(batch, "Power", 7, height - 450);
			
			font_s.draw(batch, "0" , 7, height - 491f);
			font_s.draw(batch, "100" , 95, height - 491f);
			font_s.draw(batch, "M" , 145, height - 491f);
		
		batch.end();
		
		shaperenderer.begin(ShapeType.Filled);
		
		shaperenderer.setColor(Color.CHARTREUSE);
		
		float power = (h_view.getPower()/40)*150;
		
		
		shaperenderer.rect(7.1f,height-489.5f,power,19.8f);
	
	shaperenderer.end();
		shaperenderer.begin(ShapeType.Line);
		
			shaperenderer.setColor(Color.BLACK);
			shaperenderer.rect(7, height-490, 150,20);
			shaperenderer.rect(7, height-490, 100,20);
	
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
