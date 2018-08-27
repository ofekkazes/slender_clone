package com.strawberry.engine.core;

import java.util.ArrayList;

import com.strawberry.engine.component.GameComponent;
import com.strawberry.engine.rendering.RenderingEngine;
import com.strawberry.engine.rendering.Shader;

/**
 * 
 * מחלקה זו מגדירה אובייקט שהמנוע יכול לשלוט בו, להציג ולפלוט אותו למסך
 * 
 */
public class GameObject 
{
	private ArrayList<GameObject> _children;//לכל אובייקט יש בנים
	private ArrayList<GameComponent> _components;//לכל אובייקט תהיה רשימה  של הגדרות
	private Transform _transform;//לכל אובייקט יהיו ההגדרות הפיזיקליות שלו (סיבוב, מיקום..)
	private boolean _important; //אם חשוב לצייר אותו בכל פריים
	
	public GameObject()
	{
		this._children = new ArrayList<GameObject>();
		this._components = new ArrayList<GameComponent>();
		this._transform = new Transform();
		_transform.setScale(new Vector3f(0.5f, 0.5f, 0.5f));
		setImportant(false);
	}
	
	/**
	 * הוספת בן לגרף הבנים
	 * @param o הבן
	 */
	public GameObject addChild(GameObject o)
	{
		this._children.add(o);
		o.getTransform().setParent(_transform);
		
		return this;
	}
	
	/**
	 * הוספת הגדרה לרשימת ההגדרות
	 * @param c הגדרה
	 */
	public GameObject addComponent(GameComponent c)
	{
		this._components.add(c);
		c.setParent(this);
		
		return this;
	}
	
	/**
	 * מטודת האינפוט
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
	 * מטודה המעדכנת את כל הבנים וההגדרות
	 */
	public void update(float delta)
	{
		for(GameComponent component : this._components)
			component.update(delta);
		
		for(GameObject child : this._children)
			child.update(delta);
	}
	
	/**
	 * מטודה שפולטת למסך את כל הבנים וההגדרות
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
	 * מטודה המוסיפה למנוע הציור את האובייקט הנוכחי
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
	 * מטודה המחזירה את ההגדרות הפיזיקליות של האובייקט
	 * @return
	 */
	public Transform getTransform()	{ return this._transform; }

	/**
	 * מטודה המחזירה האם האובייקט חשוב לציור
	 * @return
	 */
	public boolean isImportant() { return _important; }

	/**
	 * המטודה מגדירה את חשיבות האובייקט מבחינת ציורו על המסך כדי להקל על המעבד וכרטיס המסך
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
