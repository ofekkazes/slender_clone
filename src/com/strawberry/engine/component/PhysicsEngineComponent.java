package com.strawberry.engine.component;

import com.strawberry.engine.physics.PhysicsEngine;
import com.strawberry.engine.rendering.RenderingEngine;
import com.strawberry.engine.rendering.Shader;

public class PhysicsEngineComponent extends GameComponent
{
	private PhysicsEngine _physicsEngine;
	
	public PhysicsEngineComponent(PhysicsEngine engine) 
	{
		_physicsEngine = engine;
	}
	@Override
	public void input(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float delta) 
	{
		_physicsEngine.simulate(delta);
		_physicsEngine.handleCollosions();
	}
	
	public PhysicsEngine getEngine() { return _physicsEngine; }

	@Override
	public void render3D(Shader shader, RenderingEngine engine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addToRenderingEngine(RenderingEngine engine) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void cleanUp() {
		this._physicsEngine = null;
		
	}

}
