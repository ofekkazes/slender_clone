package com.strawberry.engine.core;

public class Quaternion 
{
	private float _x;
	private float _y;
	private float _z;
	private float _w;
	
	public Quaternion(float x, float y, float z, float w)
	{
		this._x = x;
		this._y = y;
		this._z = z;
		this._w = w;
	}
	
	public Quaternion(Vector3f axis, float angle)
	{
		float sinHalfAngle = (float) Math.sin(angle / 2);
		float cosHalfAngle = (float) Math.cos(angle / 2);

		this._x = axis.getX() * sinHalfAngle;
		this._y = axis.getY() * sinHalfAngle;
		this._z = axis.getZ() * sinHalfAngle;
		this._w = cosHalfAngle;
	}
	
	public float length()
	{
		return (float)Math.sqrt(this._x * this._x + this._y * this._y + this._z * this._z + this._w * this._w);
	}
	
	public Quaternion normalize()
	{
		float length = length();
		return new Quaternion(this._x / length, this._y / length, this._z / length, this._w / length);
	}
	public Quaternion conjugate()
	{
		return new Quaternion(-this._x, -this._y, -this._z, this._w);
	}
	
	public Quaternion mul(float r)
	{
		return new Quaternion(this._x * r, this._y * r, this._z * r, this._w * r);
	}
	public Quaternion mul(Quaternion r)
	{
		float w_ = this._w * r.getW() - this._x * r.getX() - this._y * r.getY() - this._z * r.getZ();
		float x_ = this._x * r.getW() + this._w * r.getX() + this._y * r.getZ() - this._z * r.getY();
		float y_ = this._y * r.getW() + this._w * r.getY() + this._z * r.getX() - this._x * r.getZ();
		float z_ = this._z * r.getW() + this._w * r.getZ() + this._x * r.getY() - this._y * r.getX();
		return new Quaternion(x_, y_, z_, w_);
	}
	
	public Quaternion mul(Vector3f r)
	{
		float w_ = -this._x * r.getX() - this._y * r.getY() - this._z * r.getZ();
		float x_ = this._w * r.getX() + this._y * r.getZ() - this._z * r.getY();
		float y_ = this._w * r.getY() + this._z * r.getX() - this._x * r.getZ();
		float z_ = this._w * r.getZ() + this._x * r.getY() - this._y * r.getX();
		return new Quaternion(x_, y_, z_, w_);
	}
	/*
	public Quaternion lookAt(Vector3f lookAtPos, Vector3f up)
	{
		Vector3f forward = lookAtPos.getCopy().normalize();
		Vector3f right = up.cross(forward);
		
		float w_ = (float) (Math.sqrt(1.0f + right.getX() + up.getY() + forward.getZ()) * 0.5f);
		float recip = 1.0f / (4.0f * w_);
		float x_ = (forward.getY() - up.getZ()) * recip;
		float y_ = (right.getZ() - forward.getX()) * recip;
		float z_ = (up.getX() - right.getY()) * recip;
		
		return new Quaternion(x_, y_, z_, w_);
	}*/
	
	public Matrix4f toRotationMatrix()
	{
		Vector3f forward = new Vector3f(2.0f * (this._x * this._z - this._w * this._y), 
										2.0f * (this._y * this._z + this._w * this._x), 
										1.0f - 2.0f * (this._x * this._x + this._y * this._y));
		Vector3f up = new Vector3f(2.0f * (this._x * this._y + this._w * this._z), 
				 				   1.0f - 2.0f * (this._x * this._x + this._z * this._z),
								   2.0f * (this._y * this._z - this._w * this._x));
		Vector3f right = new Vector3f(1.0f - 2.0f * (this._y * this._y + this._z * this._z),
									  2.0f * (this._x * this._y - this._w * this._z), 
									  2.0f * (this._x * this._z + this._w * this._y));
		
		return new Matrix4f().initRotation(forward, up, right);
		//return new Matrix4f().initRotation(getForward(), getUp(), getRight());
	}
	
	public Vector3f getForward() { return new Vector3f(0,0,1).rotate(this); }
	
	public Vector3f getBack() { return new Vector3f(0,0,-1).rotate(this); }
	
	public Vector3f getUp() { return new Vector3f(0,1,0).rotate(this); }
	
	public Vector3f getDown() { return new Vector3f(0,-1,0).rotate(this); }
	
	public Vector3f getRight() { return new Vector3f(1,0,0).rotate(this); }
	
	public Vector3f getLeft() { return new Vector3f(-1,0,0).rotate(this); }

	public float getX() { return _x; }

	public float getY() { return _y; }

	public float getZ() { return _z;	}

	public float getW() { return _w;	}

	public void setX(float x) {	this._x = x;	}

	public void setY(float y) { this._y = y;	}

	public void setZ(float z) { this._z = z;	}

	public void setW(float w) { this._w = w;	}	
	
	public Quaternion set(Quaternion r) { this._x = r._x; this._y = r._y; this._z = r._z; this._w = r._w; return this; }
	
	public boolean equals(Quaternion other)
	{
		return _x == other._x && _y == other._y && _z == other._z && _w == other._w;
	}
}
