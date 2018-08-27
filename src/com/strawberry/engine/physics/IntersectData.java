package com.strawberry.engine.physics;

import com.strawberry.engine.core.Vector3f;

public class IntersectData 
{
	private boolean _isIntersect;
	//private float _distance;
	private Vector3f _direction;
	
	public IntersectData(boolean isIntersect, Vector3f direction)
	{
		this.setIntersect(isIntersect);
		this.setDirection(direction);
	}

	public boolean isIntersect() {	return _isIntersect; }

	public void setIntersect(boolean isIntersect) {	this._isIntersect = isIntersect;}

	public float getDistance() { return _direction.length(); }

	public void setDirection(Vector3f direction) { this._direction = direction; }

	public Vector3f getDirection() { return _direction; }
}
