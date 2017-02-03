package com.korea.univ.luias.components;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.korea.univ.luias.Main;
import com.korea.univ.luias.objects.Stone;

public class Info_view extends View {
	public final String version = "Curling Simulator V 0.5";
	
	
	BitmapFont black, red, yellow;
	Texture redStone, yellowStone;
	SpriteBatch batch;

	float width = 600, height = 600;

	private ArrayList<Stone> stones = new ArrayList<Stone>();

	Stage stage;

	public Info_view(BitmapFont... font) {
		super();
		this.black = font[0];
		this.red = font[1];
		this.yellow = font[2];

		batch = new SpriteBatch();

		camera = new OrthographicCamera();
		viewport = new FitViewport(width, height, camera);
		viewport.apply();
		
		shaperenderer = new ShapeRenderer();
		stage = new Stage(viewport);

		redStone = new Texture(Gdx.files.internal("images/Stones/RedStone.png"));
		yellowStone = new Texture(Gdx.files.internal("images/Stones/YellowStone.png"));
		//float x = 8 + 10;
		float y = 150;
		for (int i = 0; i < 8; i++, y+= 30) {
			Stone r = new Stone(redStone);
			r.isInfo();
			r.setPosition(80, y);
			r.setSize(28, 28);
			stage.addActor(r);
			stones.add(r);
		}
		
		y = 150;
		//x = 8 + 10;
		
		for(int i = 0; i < 8; i++, y+= 30){
			Stone ye = new Stone(yellowStone);
			ye.isInfo();
			ye.setPosition(363, y);
			ye.setSize(28, 28);
			stage.addActor(ye);
			stones.add(ye);
		}

	}

	@Override
	public void update() {

		for(int i = 0; i < Main.rthrowCount; i++){
			stones.get(8-Main.rthrowCount).setVisible(false);
		}
		
		for(int i = 0; i < Main.ythrowCount; i++){
			stones.get(16-Main.ythrowCount).setVisible(false);
		}

	}

	@Override
	public void render() {
		Gdx.gl.glViewport(15, 55, 850, 850);
		camera.update();

		batch.setProjectionMatrix(camera.combined);

		batch.begin();

		//black.draw(batch,version, width/2, height-10);
		//black.draw(batch, "Team - ", width/2, height - 50);
		//red.draw(batch, "RED", 7 + (width/2)+80, height - 50);

		//black.draw(batch, "Team - ", width/2, height - 110);
		//yellow.draw(batch, "YELLOW", (width/2)+80, height - 110);

		//black.draw(batch, "- Current Thrower -", (width/2)+50, height - 165);
		//if (Main.current == 0)
		//	red.draw(batch, "RED", (width/2)+135, height - 195);
		//else
		//	yellow.draw(batch, "YELLOW", (width/2)+110, height - 195);

		/*
		black.draw(batch, version, 7, height - 10);
		black.draw(batch, "Team - ", 7, height - 90);
		red.draw(batch, "RED", 7 + 80, height - 90);

		black.draw(batch, "Team - ", 7, height - 170);
		yellow.draw(batch, "YELLOW", 7 + 80, height - 170);

		black.draw(batch, "- Current Thrower -", 7 + 15, height - 245);
		if (Main.current == 0)
			red.draw(batch, "RED", 7 + 100, height - 275);
		else
			yellow.draw(batch, "YELLOW", 7 + 78, height - 275);

		black.draw(batch, "Score", 7, height - 315);
		*/
		batch.end();
		
		/* version1 score board
		shaperenderer.setProjectionMatrix(camera.combined);

		shaperenderer.begin(ShapeType.Filled);
		shaperenderer.setColor(Color.BLACK);

		// outter , up,left,right,down order -----------------------------------
		shaperenderer.rectLine(7, height - 335, 230, height - 335, 2);
		shaperenderer.rectLine(7, height - 335, 7, height - 515, 2);
		shaperenderer.rectLine(230, height - 335, 230, height - 515, 2);
		shaperenderer.rectLine(7, height - 515, 230, height - 515, 2);
		// ---------------------------------------------------------------------

		// inner----------------------------------------------------------------

		// centerline
		shaperenderer.rectLine(7, height - 360, 230, height - 360, 2);
		shaperenderer.rectLine(7, height - 393, 230, height - 393, 2);
		shaperenderer.rectLine(7, height - 425, 230, height - 425, 2);
		shaperenderer.rectLine(7, height - 450, 230, height - 450, 2);
		shaperenderer.rectLine(7, height - 483, 230, height - 483, 2);

		// each line
		float x = 7 + 30;
		for (int i = 0; i < 5; i++, x += 38)
			shaperenderer.rectLine(x, height - 335, x, height - 515, 2);

		// ---------------------------------------------------------------------

		shaperenderer.end();

		batch.begin();
		x = 13 + 38;
		for (int i = 1; i < 6; i++, x += 38)
			black.draw(batch, String.valueOf(i), x, height - 340);

		x = 10 + 38;
		for (int i = 6; i < 11; i++, x += 38)
			black.draw(batch, String.valueOf(i), x, height - 430);

		// 1~8 round
		red.draw(batch, "R", 13, height - 368);
		yellow.draw(batch, "Y", 13, height - 402);
		x = 13 + 38;
		for (int i = 0; i < 5; i++, x += 38){
			if(Main.scoreBoard[0][i] == -1)
				break;
			
			black.draw(batch, String.valueOf(Main.scoreBoard[0][i]), x, height - 368);
			black.draw(batch, String.valueOf(Main.scoreBoard[1][i]), x, height - 402);

		}

		// 9~16 round
		red.draw(batch, "R", 13, height - 458);
		yellow.draw(batch, "Y", 13, height - 492);
		x = 13+38;
		for (int i = 5; i < 10; i++, x += 38){
			if(Main.scoreBoard[0][i] == -1)
				break;
			
			black.draw(batch, String.valueOf(Main.scoreBoard[0][i]), x, height - 458);
			black.draw(batch, String.valueOf(Main.scoreBoard[1][i]), x, height - 492);
		}

		batch.end();
		*/
		stage.draw();
		

	}

	public Stage getStage() {
		return stage;
	}
	
	public ArrayList <Stone> getStones(){
		return stones;
	}

	public void dispose(){
		redStone.dispose();
		yellowStone.dispose();
	}
}
