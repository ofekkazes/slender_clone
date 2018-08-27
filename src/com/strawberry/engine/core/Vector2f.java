package com.strawberry.engine.core;

public class Vector2f 
{

	public static Vector2f ZERO = new Vector2f();
	
	private float _x;
	private float _y;

	public Vector2f() {
		this(0, 0);
	}

	public Vector2f(Vector2f src) {
		set(src);
	}

	public Vector2f(float x, float y) {
		set(x, y);
	}

	public void set(float x, float y) {
		this._x = x;
		this._y = y;
	}

	public Vector2f set(Vector2f src) {
		this._x = src.getX();
		this._y = src.getY();
		return this;
	}

	public float length() 
	{
		return (float)Math.sqrt(this._x * this._x + this._y * this._y);
	}
	
	public float max()
	{
		return Math.max(this._x, this._y);
	}

	public Vector2f negate() 
	{
		this._x = -this._x;
		this._y = -this._y;
		return this;
	}

	public Vector2f normalized() 
	{
		float l = length();

		return new Vector2f(this._x / l, this._y / l);
	}

	public float dot(Vector2f r) 
	{
		return r._x * this._x + r._y * this._y;
	}
	
	public float cross(Vector2f r)
	{
		return this._x * r._y - this._y * r._x;
	}
	
	public Vector2f lerp(Vector2f dest, float lerpFactor)
	{
		return dest.sub(this).mul(lerpFactor).add(this);
	}
	
	public Vector2f rotate(float angle)
	{
		double radians = Math.toRadians(angle);
		
		double cos = Math.cos(radians);
		double sin = Math.sin(radians);
		
		return new Vector2f((float)(this._x * cos - this._y * sin), (float)(this._x * sin + this._y * cos));
	}

	public Vector2f add(Vector2f r) 
	{
		return new Vector2f(r._x + this._x, r._y + this._y);
	}
	
	public Vector2f add(float r) 
	{
		return new Vector2f(r + this._x, r + this._y);
	}

	public Vector2f sub(Vector2f r) 
	{
		return new Vector2f(this._x - r._x, this._y - r._y);
	}
	
	public Vector2f sub(float r) 
	{
		return new Vector2f(this._x - r, this._y - r);
	}
	
	public Vector2f mul(Vector2f r) 
	{
		return new Vector2f(this._x * r._x, this._y * r._y);
	}
	
	public Vector2f mul(float r) 
	{
		return new Vector2f(this._x * r, this._y * r);
	}
	
	public Vector2f div(Vector2f r) 
	{
		return new Vector2f(this._x / r._x, this._y / r._y);
	}
	
	public Vector2f div(float r) 
	{
		return new Vector2f(this._x / r, this._y / r);
	}
	
	public Vector2f abs() 
	{
		return new Vector2f(Math.abs(this._x), Math.abs(this._y));
	}

	public String toString() 
	{
		StringBuilder sb = new StringBuilder(64);

		sb.append("Vector2f[");
		sb.append(_x);
		sb.append(", ");
		sb.append(_y);
		sb.append(']');
		return sb.toString();
	}

	/**
	 * @return x
	 */
	public final float getX() {	return _x; }

	/**
	 * @return y
	 */
	public final float getY() {	return _y; }

	/**
	 * Set X
	 * @param x
	 */
	public final void setX(float x) { this._x = x; }

	/**
	 * Set Y
	 * @param y
	 */
	public final void setY(float y) { this._y = y; }	
	
	public boolean equals(Vector2f other) 
	{
		return (_x == other._x && _y == other._y);
	}
	
}
