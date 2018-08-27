package com.strawberry.engine.physics;

import java.util.ArrayList;
import java.util.List;

import com.strawberry.engine.core.Vector3f;

public class PhysicsEngine 
{
	private List<PhysicsObject> _objects;

	public PhysicsEngine()
	{
		this._objects = new ArrayList<PhysicsObject>();
	}
	
	public void addObject(PhysicsObject object)
	{
		this._objects.add(object);
	}
	
	public void simulate(float delta)
	{
		for (PhysicsObject object : _objects) 
			object.integrate(delta);
	}
	
	public void handleCollosions()
	{
		for(int i = 0; i < this._objects.size(); i++)
		{
			for(int j = i + 1; j < this._objects.size(); j++)
			{
				IntersectData intersectData = 
						this._objects.get(i).getCollider().intersect(this._objects.get(j).getCollider());
				if(intersectData.isIntersect())
				{
					Vector3f direction = intersectData.getDirection().normalize();
					Vector3f otherDirection = direction.reflect(_objects.get(i).getVelocity().normalize());
					
					this._objects.get(i).setVelocity(this._objects.get(i).getVelocity().reflect(otherDirection));
					this._objects.get(j).setVelocity(this._objects.get(j).getVelocity().reflect(direction));
				}
			}
		}
	}
	
	public List<PhysicsObject> getObjects() { return _objects; }
	
	public int getNumObjects() { return _objects.size(); }

	public void setObjects(List<PhysicsObject> objects) { this._objects = objects;}
	
	public PhysicsObject getObject(int index) { return this._objects.get(index); }
}
