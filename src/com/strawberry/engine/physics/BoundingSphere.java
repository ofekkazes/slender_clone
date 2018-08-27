package com.strawberry.engine.physics;

import com.strawberry.engine.core.Vector3f;

public class BoundingSphere extends Collider
{
	private Vector3f _center;
	private float _radius;
	
	public BoundingSphere(Vector3f center, float radius)
	{
		super(ColliderType.BoundingShpere);
		this._center = center;
		this._radius = radius;
	}
	
	public IntersectData IntersectBoundingSphere(BoundingSphere other)
	{
		float radiusDistance = this.getRadius() + other.getRadius();
		Vector3f direction = this.getCenter().sub(other.getCenter());
		float centerDistance = direction.length();
		direction = direction.div(centerDistance);
		
		float distance = centerDistance - radiusDistance;
		
		return new IntersectData(distance < 0, direction.mul(distance));
	}
	
	@Override
	public void transform(Vector3f translation) 
	{
		this._center = this._center.add(translation);
	}
	
	@Override
	public Vector3f getCenter() { return _center; }

	public void setCenter(Vector3f center) { this._center = center; }

	public float getRadius() { return _radius; }

	public void setRadius(float radius) { this._radius = radius;	}

	
}
