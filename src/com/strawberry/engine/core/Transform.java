package com.strawberry.engine.core;

/**
 * מחלקת השינוי
 * @author LapKazes
 *
 */
public class Transform 
{
	private Transform _parent; //האבא
	private Matrix4f _parentMatrix; //מטריקס האבא
	
	private Vector3f _position; //מיקום בחלל התלת מימדי
	private Quaternion _rotation; //סיבוב האובייקט
	private Vector3f _scale; //גודל לכל ציר
	
	private Vector3f _oldPosition; //המיקום מהפריים הקודם
	private Quaternion _oldRotation; //הסיבוב מהפריים הקודם
	private Vector3f _oldScale; //הגודל מהפריים הקודם
	
	/**
	 * פעולה בונה
	 */
	public Transform()
	{
		this.setPosition(new Vector3f(0, 0, 0));
		this._rotation = new Quaternion(0,0,0, 1);
		this._scale = new Vector3f(1, 1, 1);
		
		this._parentMatrix = new Matrix4f().initIdentity();
		
		this._oldPosition = Vector3f.ZERO;
		this._oldRotation = new Quaternion(0,0,0,0);
		this._oldScale = new Vector3f(1,1,1);
	}
	
	/**
	 * המטודה מעדכנת את האובייקט הנוכחי כל פריים
	 */
	public void update()
	{
		if(this._oldPosition != null)
		{
			_oldPosition = _position;
			_oldScale = _scale;
			_oldRotation = _rotation;
		}
		else
		{
			this._oldPosition = Vector3f.ZERO.set(this._position).add(1.0f);
			this._oldRotation = new Quaternion(0,0,0,0).set(this._rotation).mul(0.5f);
			this._oldScale = Vector3f.ZERO.set(this._scale).add(1.0f);
		}
	}
	
	/**
	 * המטודה מסובבת את האובייקט
	 * @param axis ציר הסיבוב
	 * @param angle מעלות הסיבוב
	 */
	public void rotate(Vector3f axis, float angle)
	{
		this._rotation = new Quaternion(axis, angle).mul(this._rotation).normalize();
	}
	
	/**
	 * מטודה שבודקת ומחזירה האם האבא השתנה או שהתכונות האחרות השתנו
	 * @return
	 */
	public boolean hasChanged()
	{
		if(this._parent != null && this._parent.hasChanged()) 
			return true;
		
		if (!this._position.equals(this._oldPosition)) return true;
		if (!this._scale.equals(this._oldScale)) return true;
		if (!this._rotation.equals(this._oldRotation)) return true;
		
		return false;
	}

	/**
	 * המטודה מחזירה מטריקס שבו התכונות שבמחלקה
	 * @return
	 */
	public Matrix4f getTransformation()
	{
		Matrix4f translationMatrix = new Matrix4f().initTranslation(this._position.getX(),
				this._position.getY(), this._position.getZ());
		
		Matrix4f rotationMatrix = this._rotation.toRotationMatrix();//new Matrix4f().initRotation(this._rotation.getX(), this._rotation.getY(), this._rotation.getZ());
		Matrix4f scaleMatrix = new Matrix4f().initScale(this._scale.getX(), this._scale.getY(), this._scale.getZ());
		
		
		
		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}
	
	/**
	 * המטודה מחזירה את המטריקס של האב
	 * @return
	 */
	private Matrix4f getParentMatrix()
	{
		if(_parent != null && _parent.hasChanged()) 
			_parentMatrix = _parent.getTransformation();
		return this._parentMatrix;
	}
	
	/**
	 * המטודה מקבלת את המיקום שמשוכלל עם כל האבות
	 * @return
	 */
	public Vector3f getTransformedPosition()
	{
		return getParentMatrix().transform(this._position);
	}
	
	/**
	 * המטודה מקבלת סיבוב שמשוכלל עם האב
	 * @return
	 */
	public Quaternion getTransformedRotation()
	{
		Quaternion parentRotation = new Quaternion(0, 0, 0, 1);
		
		if(_parent != null) 
			parentRotation = this._parent.getTransformedRotation();
		
		return parentRotation.mul(_rotation);
	}
	
	public void setParent(Transform parent) { this._parent = parent; }

	public Quaternion getRotation() { return this._rotation; }

	public void setRotation(Quaternion rotation)	{ this._rotation = rotation; }

	public Vector3f getScale() { return this._scale; }

	public void setScale(Vector3f scale) {	this._scale = scale; }

	public Vector3f getPosition() {	return _position; }

	public void setPosition(Vector3f _position) { this._position = _position; }

	public void cleanUp() {
		this._position = null;
		this._rotation = null;
		this._scale = null;
		
	}
}
