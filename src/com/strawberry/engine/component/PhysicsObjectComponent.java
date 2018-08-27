package com.strawberry.engine.component;

import com.strawberry.engine.physics.PhysicsObject;
import com.strawberry.engine.rendering.RenderingEngine;
import com.strawberry.engine.rendering.Shader;

public class PhysicsObjectComponent extends GameComponent
{
	private PhysicsObject _physicsObject;
	
	public PhysicsObjectComponent(PhysicsObject physicsObject) 
	{
		this.setPhysicsObject(physicsObject);
	}
	@Override
	public void input(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float delta) 
	{
		this.getTransform().setPosition(_physicsObject.getPosition());
	}

	@Override
	public void render3D(Shader shader, RenderingEngine engine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addToRenderingEngine(RenderingEngine engine) {
		// TODO Auto-generated method stub
		
	}
	public PhysicsObject getPhysicsObject() {
		return _physicsObject;
	}
	public void setPhysicsObject(PhysicsObject physicsObject) {
		this._physicsObject = physicsObject;
	}
	@Override
	public void cleanUp() {
		this._physicsObject = null;
		
	}

}
