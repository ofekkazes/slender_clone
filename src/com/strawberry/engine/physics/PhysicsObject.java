package com.strawberry.engine.physics;

import com.strawberry.engine.core.Vector3f;

public class PhysicsObject 
{
	private Vector3f _position;
	private Vector3f oldPosition;
	private Vector3f _velocity;
	private float _radius;
	
	private Collider _collider;
	
	public PhysicsObject(Collider collider, Vector3f velocity)
	{
		this.setPosition(collider.getCenter());
		this.oldPosition = collider.getCenter();
		this.setVelocity(velocity);
		this._collider = collider;
	}
	
	public void integrate(float delta)
	{
		this._position = this._position.add(this._velocity.mul(delta));
	}
	
	public Collider getCollider()
	{
		Vector3f translation = this._position.sub(oldPosition);
		this.oldPosition = this._position;
		this._collider.transform(translation);
		
		return this._collider;
	}

	public Vector3f getPosition() {	return _position; }

	public void setPosition(Vector3f position) { this._position = position; }

	public Vector3f getVelocity() {	return _velocity; }

	public void setVelocity(Vector3f velocity) { this._velocity = velocity; }

	public float getRadius() { return _radius; }

	public void setRadius(float radius) { this._radius = radius; }
}
