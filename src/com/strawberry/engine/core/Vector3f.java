package com.strawberry.engine.core;


public class Vector3f 
{
	public static final Vector3f ZERO = new Vector3f();
	private float _x;
	private float _y;
	private float _z;
	
	public Vector3f()
	{
		this(0, 0, 0);
	}
	
	public Vector3f(float x, float y, float z)
	{
		this._x = x;
		this._y = y;
		this._z = z;
	}
	
	public Vector3f(Vector3f r)
	{
		this._x = r._x;
		this._y = r._y;
		this._z = r._z;
	}
	
	public float length()
	{
		return (float)Math.sqrt(this._x * this._x + this._z * this._z + this._y * this._y);
	}
	
	public float dot(Vector3f r)
	{
		return this._x * r._x + this._y * r._y + this._z * r._z;
	}
	
	public Vector3f reflect(Vector3f direction) 
	{
		return this.sub(direction.mul(this.dot(direction) * 2));
	}
	
	public Vector3f cross(Vector3f r)
	{
		float tmpX = this._y * r._z - this._z * r._y;
		float tmpY = this._z * r._x - this._x * r._z;
		float tmpZ = this._x * r._y - this._y * r._x;
		
		return new Vector3f(tmpX, tmpY, tmpZ);
	}
	
	public Vector3f normalize()
	{
		float length = this.length();
		
		this._x /= length;
		this._y /= length;
		this._z /= length;
		
		return this;
	}
	
	public Vector3f rotate(float angle, Vector3f axis)
	{
		float sinAngle = (float)Math.sin(-angle);
		float cosAngle = (float)Math.cos(-angle);
		
		return this.cross(axis.mul(sinAngle)).add( //x
						 (this.mul(cosAngle)).add( //z
						 axis.mul(this.dot(axis.mul(1 - cosAngle))))); //y
	}
	
	public Vector3f rotate(Quaternion rotation)
	{
		Quaternion conjugate = rotation.conjugate();
		Quaternion w = rotation.mul(this).mul(conjugate);
		
		return new Vector3f(w.getX(), w.getY(), w.getZ());
	}
	
	public Vector3f lerp(Vector3f dest, float lerpFactor)
	{
		return dest.sub(this).mul(lerpFactor).add(this);
	}
	
	public Vector3f add(Vector3f r)
	{
		return new Vector3f(this._x + r._x, this._y + r._y, this._z + r._z);
	}
	
	public Vector3f add(float r)
	{
		return new Vector3f(this._x + r, this._y + r, this._z + r);
	}
	
	public Vector3f sub(Vector3f r)
	{
		return new Vector3f(this._x - r._x, this._y - r._y, this._z - r._z);
	}
	
	public Vector3f sub(float r)
	{
		return new Vector3f(this._x - r, this._y - r, this._z - r);
	}
	
	public Vector3f mul(Vector3f r)
	{
		return new Vector3f(this._x * r._x, this._y * r._y, this._z * r._z);
	}
	
	public Vector3f mul(float r)
	{
		return new Vector3f(this._x * r, this._y * r, this._z * r);
	}
	
	public Vector3f div(Vector3f r)
	{
		return new Vector3f(this._x / r._x, this._y / r._y, this._z / r._z);
	}
	
	public Vector3f div(float r)
	{
		return new Vector3f(this._x / r, this._y / r, this._z / r);
	}
	
	public float max()
	{
		return Math.max(_x, Math.max(_y, _z));
	}
	
	public Vector3f max(Vector3f r)
	{
		Vector3f result = new Vector3f();
		
		if(this.getX() > r.getX()) result._x = this.getX();
		else result._x = r.getX();
		
		if(this.getY() > r.getY()) result._y = this.getY();
		else result._y = r.getY();
		
		if(this.getZ() > r.getZ()) result._z = this.getZ();
		else result._z = r.getZ();
		
		return result;
	}
	
	public Vector2f getXY() { return new Vector2f(_x, _y); }
	public Vector2f getYZ() { return new Vector2f(_y, _z); }
	public Vector2f getZX() { return new Vector2f(_z, _x); }
	
	public Vector2f getYX() { return new Vector2f(_y, _x); }
	public Vector2f getZY() { return new Vector2f(_z, _y); }
	public Vector2f getXZ() { return new Vector2f(_x, _z); }
	
	public void set(float x, float y, float z) { this._x = x; this._y = y; this._z = z; }
	
	public Vector3f set(Vector3f r) { this._x = r._x; this._y = r._y; this._z = r._z; return this; }

	public float getX() { return _x; }

	public float getY() { return _y; }

	public float getZ() { return _z; }

	public void setX(float x) { this._x = x; }

	public void setY(float y) {	this._y = y; }

	public void setZ(float z) {	this._z = z; }
	
	public Vector3f getCopy()
	{
		return new Vector3f(_x, _y, _z);
	}
	
	public boolean equals(Vector3f other)
	{
		return _x == other._x && _y == other._y && _z == other._z;
	}
	
	public String toString()
	{
		return "{"+this._x+", "+this.getY()+", "+this.getZ()+"}";
	}

	
}
