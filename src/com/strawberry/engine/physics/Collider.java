package com.strawberry.engine.physics;

import com.strawberry.engine.core.Vector3f;

public abstract class Collider 
{
	private ColliderType _type;
	
	public Collider(ColliderType type)
	{
		this.setType(type);
	}
	
	public abstract void transform(Vector3f translation);
	
	public IntersectData intersect(Collider other)
	{
		if(this.getType() == ColliderType.BoundingShpere && other.getType() == ColliderType.BoundingShpere)
		{
			BoundingSphere child = (BoundingSphere)this;
			return child.IntersectBoundingSphere((BoundingSphere)other);
		}
		return null;
	}

	public ColliderType getType() { return _type; }

	public void setType(ColliderType type) {	this._type = type; }

	public Vector3f getCenter() { return Vector3f.ZERO;	}

}
