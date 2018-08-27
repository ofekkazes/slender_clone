package com.strawberry.engine.physics;

import com.strawberry.engine.core.Vector3f;

public class Plane 
{
	private Vector3f _normal;
	private float _distance;
	
	public Plane(Vector3f normal, float distance)
	{
		this.setNormal(normal);
		this.setDistance(distance);
	}
	
	public Plane normalize()
	{
		float length = getNormal().length();
		return new Plane(getNormal().normalize(), getDistance() / length);
	}
	
	public IntersectData IntersectSphere(BoundingSphere other)
	{
		float distanceFromSphereCenter = Math.abs(this.getNormal().dot(other.getCenter()) + this.getDistance());
		float distanceFromSphere = distanceFromSphereCenter - other.getRadius();
		
		return new IntersectData(distanceFromSphere < 0, this._normal.mul(distanceFromSphere));
	}

	public Vector3f getNormal() { return _normal; }

	public void setNormal(Vector3f normal) { this._normal = normal; }

	public float getDistance() { return _distance; }

	public void setDistance(float distance) { this._distance = distance; }
}
