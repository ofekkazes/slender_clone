package com.strawberry.engine.core;

/**
 * ����� ������
 * @author LapKazes
 *
 */
public class Transform 
{
	private Transform _parent; //����
	private Matrix4f _parentMatrix; //������ ����
	
	private Vector3f _position; //����� ���� ���� �����
	private Quaternion _rotation; //����� ��������
	private Vector3f _scale; //���� ��� ���
	
	private Vector3f _oldPosition; //������ ������� �����
	private Quaternion _oldRotation; //������ ������� �����
	private Vector3f _oldScale; //����� ������� �����
	
	/**
	 * ����� ����
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
	 * ������ ������ �� �������� ������ �� �����
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
	 * ������ ������ �� ��������
	 * @param axis ��� ������
	 * @param angle ����� ������
	 */
	public void rotate(Vector3f axis, float angle)
	{
		this._rotation = new Quaternion(axis, angle).mul(this._rotation).normalize();
	}
	
	/**
	 * ����� ������ ������� ��� ���� ����� �� �������� ������ �����
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
	 * ������ ������ ������ ��� ������� �������
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
	 * ������ ������ �� ������� �� ���
	 * @return
	 */
	private Matrix4f getParentMatrix()
	{
		if(_parent != null && _parent.hasChanged()) 
			_parentMatrix = _parent.getTransformation();
		return this._parentMatrix;
	}
	
	/**
	 * ������ ����� �� ������ ������� �� �� �����
	 * @return
	 */
	public Vector3f getTransformedPosition()
	{
		return getParentMatrix().transform(this._position);
	}
	
	/**
	 * ������ ����� ����� ������� �� ���
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
