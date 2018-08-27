package com.strawberry.engine.component;

import com.strawberry.engine.core.GameObject;
import com.strawberry.engine.core.Transform;
import com.strawberry.engine.rendering.RenderingEngine;
import com.strawberry.engine.rendering.Shader;

/**
 * GameComponent הוא מחלקה אבסטרקטית שתפקידה להוסיף פונקציונליות לGameObject
 * @author LapKazes
 *
 */
public abstract class GameComponent 
{
	private GameObject _parent; //האובייקט ששולט במחלקה
	
	/**
	 * מטודה הבודקת קלט מהמשתמש
	 * @param delta זמן משתנה
	 */
	public abstract void input(float delta);
	
	/**
	 * מטודה המעדכנת את המחלקה כל פריים
	 * @param delta זמן משתנה
	 */
	public abstract void update(float delta);
	
	/**
	 * מטודה המציירת את המחלקה על המסך
	 * @param shader אפקט
	 * @param engine מנוע רינדור
	 */
	public abstract void render3D(Shader shader, RenderingEngine engine);
	
	/**
	 * מטודה שמוסיפה את המחלקה למנוע הציור (אם צריך)
	 * @param engine
	 */
	public abstract void addToRenderingEngine(RenderingEngine engine);
	
	public abstract void cleanUp();
	
	/**
	 * מטודה המקבלת את האובייקט ששולט במחלקה הזאת
	 * @param parent
	 */
	public void setParent(GameObject parent) { this._parent = parent; }
	
	/**
	 * מטודה המחזירה את המיקום, סיבוב וגודל האובייקט
	 * @return
	 */
	public Transform getTransform() { return this._parent.getTransform(); }
}
