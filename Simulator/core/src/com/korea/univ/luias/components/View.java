package com.korea.univ.luias.components;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class View {
	
	public OrthographicCamera camera;
	public ShapeRenderer shaperenderer;
	public Viewport viewport;
	
	
	public abstract void update();
	public abstract void render();
	
}
