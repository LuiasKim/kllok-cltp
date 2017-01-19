package com.korea.univ.luias.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.korea.univ.luias.Main;

public class TeamDialog extends View{

	private Dialog dialog;
	
	private NinePatchDrawable background;
	private TextButton[] buttons;
	private Label title;
	
	private Stage stage;
	

	public TeamDialog(final Main main, BitmapFont ... fonts){
		super();
		
		camera = new OrthographicCamera();
		viewport = new FitViewport(600, 600, camera);
		
		stage = new Stage(viewport);
		
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images/ninePatches/dialog.pack"));
		background = new NinePatchDrawable(atlas.createPatch("glossy_rectangle_t"));
		
		WindowStyle winStyle = new WindowStyle(fonts[0], Color.BLACK, background);
		dialog = new Dialog("", winStyle);
		dialog.setSize(400, 400);
		
		LabelStyle lbStyle = new LabelStyle(fonts[0],Color.BLACK);
		
		title = new Label("Select your Team",lbStyle);
		title.setAlignment(Align.center, Align.center);
		
		TextureRegionDrawable up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("images/button.png"))));
		TextureRegionDrawable down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("images/button_push.png"))));
		
		buttons = new TextButton [3];
		TextButtonStyle tbStyle = new TextButtonStyle(up,down,down,null);
		
		tbStyle.font = fonts[1];
		buttons[0] = new TextButton("RED", tbStyle);
		buttons[0].addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				buttons[1].setChecked(false);
				Main.userTeam = 0;
				return true;
			}
		});
		
		tbStyle.font = fonts[2];
		buttons[1] = new TextButton("YELLOW", tbStyle);
		buttons[1].addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				buttons[0].setChecked(false);
				Main.userTeam = 1;
				return true;
			}
		});
		
		
		tbStyle.font = fonts[0];
		buttons[2] = new TextButton("START", tbStyle);
		buttons[2].addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				Main.isStarted = true;
				dialog.hide();
				
				InputMultiplexer mux = new InputMultiplexer(main.h_view.getStage(),main.c_view.getStage());
				Gdx.input.setInputProcessor(mux);
				
				return true;
			}
		});
		
		
		Table table = dialog.getContentTable();
		
		table.clear();
		table.columnDefaults(2);
		table.add(title).colspan(2).padBottom(20f);
		table.row();

		table.add(buttons[0],buttons[1]);
		table.row();
		
		table.add(buttons[2]).colspan(2).padTop(10).width(100).height(30);
		
		
		table.layout();
		
		dialog.addAction(Actions.alpha(0));
		dialog.act(1);
		
		dialog.show(stage);
	}
	
	
	
	@Override
	public void update(){
		stage.act();
	}
	
	@Override
	public void render(){
		stage.draw();
	}
	
	public Stage getStage(){
		return stage;
	}
	
}
