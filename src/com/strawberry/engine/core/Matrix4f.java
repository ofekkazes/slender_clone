package com.strawberry.engine.core;

public class Matrix4f 
{
	private float[][] _m;
	public Matrix4f()
	{
		this._m = new float[4][4];
	}
	
	public Matrix4f initIdentity()
	{
		this._m[0][0] = 1;	this._m[0][1] = 0;	this._m[0][2] = 0;	this._m[0][3] = 0;
		this._m[1][0] = 0;	this._m[1][1] = 1;	this._m[1][2] = 0;	this._m[1][3] = 0;
		this._m[2][0] = 0;	this._m[2][1] = 0;	this._m[2][2] = 1;	this._m[2][3] = 0;
		this._m[3][0] = 0;	this._m[3][1] = 0;	this._m[3][2] = 0;	this._m[3][3] = 1;
		return this;
	}
	
	/**
	 * מעדכן את מיקום המטריקס בחלל לפי הפרמטרים
	 * @param x מיקום בציר ה X
	 * @param y מיקום בציר ה Y
	 * @param z מיקום בציר ה Z
	 * @return מטריקס המיקום
	 */
	public Matrix4f initTranslation(float x, float y, float z)
	{
		this._m[0][0] = 1;	this._m[0][1] = 0;	this._m[0][2] = 0;	this._m[0][3] = x;
		this._m[1][0] = 0;	this._m[1][1] = 1;	this._m[1][2] = 0;	this._m[1][3] = y;
		this._m[2][0] = 0;	this._m[2][1] = 0;	this._m[2][2] = 1;	this._m[2][3] = z;
		this._m[3][0] = 0;	this._m[3][1] = 0;	this._m[3][2] = 0;	this._m[3][3] = 1;
		return this;
	}
	
	public Matrix4f initPerspective(float fov, float aspect, float zFar, float zNear)
	{
		float tanHalfOV = (float)Math.tan(fov / 2);
		float zRange = zNear - zFar;
		
		this._m[0][0] = 1.0f / (aspect * tanHalfOV);this._m[0][1] = 0;				this._m[0][2] = 0;							this._m[0][3] = 0;
		this._m[1][0] = 0;							this._m[1][1] = 1.0f / tanHalfOV;this._m[1][2] = 0;							this._m[1][3] = 0;
		this._m[2][0] = 0;							this._m[2][1] = 0;				this._m[2][2] = (-zNear - zFar) / zRange;	this._m[2][3] = 2 * zFar * zNear / zRange;
		this._m[3][0] = 0;							this._m[3][1] = 0;				this._m[3][2] = 1;							this._m[3][3] = 0;
		return this;
	}
	
	public Matrix4f initOrthographic(float left, float right, float bottom, float top, float near, float far)
	{
		float width = right - left;
		float height = top - bottom;
		float depth = far - near;
		
		this._m[0][0] = 2 / width;	this._m[0][1] = 0;			this._m[0][2] = 0;			this._m[0][3] = -(right + left) / width;
		this._m[1][0] = 0;			this._m[1][1] = 2 / height;	this._m[1][2] = 0;			this._m[1][3] = -(top + bottom) / height;
		this._m[2][0] = 0;			this._m[2][1] = 0;			this._m[2][2] = -2 / depth;	this._m[2][3] = -(far + near) / depth;
		this._m[3][0] = 0;			this._m[3][1] = 0;			this._m[3][2] = 0;			this._m[3][3] = 1;
		return this;
	}
	
	public Matrix4f initRotation(float x, float y, float z)
	{
		Matrix4f rx = new Matrix4f();
		Matrix4f ry = new Matrix4f();
		Matrix4f rz = new Matrix4f();
		
		x = (float)Math.toRadians(x);
		y = (float)Math.toRadians(y);
		z = (float)Math.toRadians(z);
		
		rx._m[0][0] = 1;				 rx._m[0][1] = 0;				  rx._m[0][2] = 0;				    rx._m[0][3] = 0;
		rx._m[1][0] = 0;				 rx._m[1][1] = (float)Math.cos(x);rx._m[1][2] = -(float)Math.sin(x);rx._m[1][3] = 0;
		rx._m[2][0] = 0;				 rx._m[2][1] = (float)Math.sin(x);rx._m[2][2] = (float)Math.cos(x); rx._m[2][3] = 0;
		rx._m[3][0] = 0;				 rx._m[3][1] = 0;				  rx._m[3][2] = 0;				    rx._m[3][3] = 1;
		
		ry._m[0][0] = (float)Math.cos(y);ry._m[0][1] = 0;				  ry._m[0][2] = -(float)Math.sin(y);ry._m[0][3] = 0;
		ry._m[1][0] = 0;				 ry._m[1][1] = 1;				  ry._m[1][2] = 0;					ry._m[1][3] = 0;
		ry._m[2][0] = (float)Math.sin(y);ry._m[2][1] = 0;				  ry._m[2][2] = (float)Math.cos(y);	ry._m[2][3] = 0;
		ry._m[3][0] = 0;				 ry._m[3][1] = 0;				  ry._m[3][2] = 0;					ry._m[3][3] = 1;
		
		
		//Same as Vector2
		rz._m[0][0] = (float)Math.cos(z);rz._m[0][1] = -(float)Math.sin(z);rz._m[0][2] = 0;					rz._m[0][3] = 0;
		rz._m[1][0] = (float)Math.sin(z);rz._m[1][1] = (float)Math.cos(z);rz._m[1][2] = 0;					rz._m[1][3] = 0;
		rz._m[2][0] = 0;				 rz._m[2][1] = 0;				  rz._m[2][2] = 1;					rz._m[2][3] = 0;
		rz._m[3][0] = 0;				 rz._m[3][1] = 0;				  rz._m[3][2] = 0;					rz._m[3][3] = 1;
		
		this._m = rz.mul(ry.mul(rx)).getMatrixCopy();
		return this;
	}
	
	public Matrix4f initScale(float x, float y, float z)
	{
		this._m[0][0] = x;	this._m[0][1] = 0;	this._m[0][2] = 0;	this._m[0][3] = 0;
		this._m[1][0] = 0;	this._m[1][1] = y;	this._m[1][2] = 0;	this._m[1][3] = 0;
		this._m[2][0] = 0;	this._m[2][1] = 0;	this._m[2][2] = z;	this._m[2][3] = 0;
		this._m[3][0] = 0;	this._m[3][1] = 0;	this._m[3][2] = 0;	this._m[3][3] = 1;
		return this;
	}
	
	public Matrix4f initRotation(Vector3f forward, Vector3f up)
	{
		Vector3f f = forward.normalize();
		Vector3f r = up.normalize();
		r = r.cross(f);
		Vector3f u = f.cross(r);
		
		return initRotation(f, u, r);
	}
	
	public Matrix4f initRotation(Vector3f forward, Vector3f up, Vector3f right)
	{
		Vector3f f = forward;
		Vector3f r = right;
		Vector3f u = up;
		
		this._m[0][0] = r.getX();	this._m[0][1] = r.getY();	this._m[0][2] = r.getZ();	this._m[0][3] = 0;
		this._m[1][0] = u.getX();	this._m[1][1] = u.getY();	this._m[1][2] = u.getZ();	this._m[1][3] = 0;
		this._m[2][0] = f.getX();	this._m[2][1] = f.getY();	this._m[2][2] = f.getZ();	this._m[2][3] = 0;
		this._m[3][0] = 0;			this._m[3][1] = 0;			this._m[3][2] = 0;			this._m[3][3] = 1;
		return this;
	}
	
	public Vector3f transform(Vector3f r)
	{
		return new Vector3f(this._m[0][0] * r.getX() + this._m[0][1] * r.getY() + this._m[0][2] * r.getZ() + this._m[0][3],
							this._m[1][0] * r.getX() + this._m[1][1] * r.getY() + this._m[1][2] * r.getZ() + this._m[1][3],
							this._m[2][0] * r.getX() + this._m[2][1] * r.getY() + this._m[2][2] * r.getZ() + this._m[2][3]);
	}
	
	public Matrix4f mul(Matrix4f r)
	{
		Matrix4f res = new Matrix4f();
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j<4; j++)
			{
				res.set(i, j, this._m[i][0] * r.get(0, j) +
							  this._m[i][1] * r.get(1, j) +
							  this._m[i][2] * r.get(2, j) +
							  this._m[i][3] * r.get(3, j));
			}
		}
		return res;
	}
	
	public float[][] getMatrixCopy() 
	{
		float[][] copy = new float[4][4];
		for (int i = 0; i < copy.length; i++) {
			for (int j = 0; j < copy.length; j++) {
				copy[i][j] = this._m[i][j];
			}
		}
		return copy;
	}
	
	public float get(int x, int y) { return this._m[x][y];	}
	
	public void setMatrix(float[][] _m) { this._m = _m;	}
	
	public void set(int x, int y, float value) { this._m[x][y] = value; }
}
