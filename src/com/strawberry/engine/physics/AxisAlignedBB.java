package com.strawberry.engine.physics;

import com.strawberry.engine.core.Vector3f;

public class AxisAlignedBB extends Collider
{
	private Vector3f _min;
	private Vector3f _max;
	
	public AxisAlignedBB(Vector3f min, Vector3f max)
	{
		super(ColliderType.AxisAlignedBoundingBox);
		this.setMin(min);
		this.setMax(max);
	}
	
	public IntersectData IntersectBoundingBox(AxisAlignedBB other)
	{
		Vector3f distance1 = other.getMin().sub(this.getMax());
		Vector3f distance2 = this.getMin().sub(other.getMax());
		Vector3f distance = distance1.max(distance2);
		
		float maxDistance = distance.max();
		
		return new IntersectData(maxDistance < 0, distance);
	}

	public Vector3f getMin() { return _min; }

	public void setMin(Vector3f min) { this._min = min; }

	public Vector3f getMax() { return _max; }

	public void setMax(Vector3f max) { this._max = max;	}

	@Override
	public void transform(Vector3f translation) {
		// TODO Auto-generated method stub
		
	}
}
