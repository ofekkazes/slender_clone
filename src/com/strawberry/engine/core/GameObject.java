package com.strawberry.engine.core;

import java.util.ArrayList;

import com.strawberry.engine.component.GameComponent;
import com.strawberry.engine.rendering.RenderingEngine;
import com.strawberry.engine.rendering.Shader;

/**
 * 
 * ����� �� ������ ������� ������ ���� ����� ��, ����� ������ ���� ����
 * 
 */
public class GameObject 
{
	private ArrayList<GameObject> _children;//��� ������� �� ����
	private ArrayList<GameComponent> _components;//��� ������� ���� �����  �� ������
	private Transform _transform;//��� ������� ���� ������� ���������� ��� (�����, �����..)
	private boolean _important; //�� ���� ����� ���� ��� �����
	
	public GameObject()
	{
		this._children = new ArrayList<GameObject>();
		this._components = new ArrayList<GameComponent>();
		this._transform = new Transform();
		_transform.setScale(new Vector3f(0.5f, 0.5f, 0.5f));
		setImportant(false);
	}
	
	/**
	 * ����� �� ���� �����
	 * @param o ���
	 */
	public GameObject addChild(GameObject o)
	{
		this._children.add(o);
		o.getTransform().setParent(_transform);
		
		return this;
	}
	
	/**
	 * ����� ����� ������ �������
	 * @param c �����
	 */
	public GameObject addComponent(GameComponent c)
	{
		this._components.add(c);
		c.setParent(this);
		
		return this;
	}
	
	/**
	 * ����� �������
	 */
	public void input(float delta)
	{
		_transform.update();
		
		for(GameComponent component : this._components)
			component.input(delta);
		
		for(GameObject child : this._children)
			child.input(delta);
	}
	
	/**
	 * ����� ������� �� �� ����� ��������
	 */
	public void update(float delta)
	{
		for(GameComponent component : this._components)
			component.update(delta);
		
		for(GameObject child : this._children)
			child.update(delta);
	}
	
	/**
	 * ����� ������ ���� �� �� ����� ��������
	 */
	public void render3D(Shader shader, RenderingEngine engine, Vector3f position)
	{
		int range = 300;
		
		if(isImportant() || (
		  (position.getX() + range > getTransform().getPosition().getX() &&
		   position.getX() - range < getTransform().getPosition().getX()) ||
		  (position.getZ() + range > getTransform().getPosition().getZ() &&
		   position.getZ() - range < getTransform().getPosition().getZ())))
		{
			for(GameComponent component : this._components)
				component.render3D(shader, engine);
			
			for(GameObject child : this._children)
				child.render3D(shader, engine, position);
		}
		
	}
	
	
	
	
	/**
	 * ����� ������� ����� ����� �� �������� ������
	 * @param engine
	 */
	public void addToRenderingEngine(RenderingEngine engine)
	{
		for(GameComponent component : this._components)
			component.addToRenderingEngine(engine);
		
		for(GameObject child : this._children)
			child.addToRenderingEngine(engine);
	}
	
	/**
	 * ����� ������� �� ������� ���������� �� ��������
	 * @return
	 */
	public Transform getTransform()	{ return this._transform; }

	/**
	 * ����� ������� ��� �������� ���� �����
	 * @return
	 */
	public boolean isImportant() { return _important; }

	/**
	 * ������ ������ �� ������ �������� ������ ����� �� ���� ��� ���� �� ����� ������ ����
	 * @param important
	 */
	public void setImportant(boolean important) { this._important = important; }
	
	public void cleanUp()
	{
		for(GameComponent component : this._components)
			component.cleanUp();
		
		for(GameObject child : this._children)
			child.cleanUp();
		
		this._important = false;
		this._transform.cleanUp();
	}
}
