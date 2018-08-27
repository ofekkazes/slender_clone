package com.strawberry.engine.component;

import com.strawberry.engine.core.Vector3f;
import com.strawberry.engine.rendering.RenderingEngine;
import com.strawberry.engine.rendering.Shader;

/**
 * מטודת בסיס לכל סוגי התאורה, שיורשת ממחלקת הפונקציונליות
 * @author LapKazes
 *
 */
public class BaseLight extends GameComponent
{
	private Vector3f _color; //צבע התאורה
	private float _intensity; // חוזק התאורה
	private Shader _shader; //סוג התאורה
	
	/**
	 * פעולה בונה
	 * @param color צבע התאורה
	 * @param intensity חוזק התאורה
	 */
	public BaseLight(Vector3f color, float intensity)
	{
		this._color = color;
		this._intensity = intensity;
	}
	
	@Override
	public void addToRenderingEngine(RenderingEngine engine) 
	{
		engine.addLight(this);
	}
	
	protected void setShader(Shader shader) { this._shader = shader; }
	
	public Shader getShader() { return this._shader; }

	public Vector3f getColor() { return _color; }

	public void setColor(Vector3f _color) {	this._color = _color; }

	public float getIntensity() { return _intensity; }

	public void setIntensity(float _intensity) { this._intensity = _intensity;}

	@Override
	public void input(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render3D(Shader shader, RenderingEngine engine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanUp() {
		this._shader = null;
		
	}
}
