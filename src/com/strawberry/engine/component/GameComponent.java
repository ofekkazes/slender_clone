package com.strawberry.engine.component;

import com.strawberry.engine.core.GameObject;
import com.strawberry.engine.core.Transform;
import com.strawberry.engine.rendering.RenderingEngine;
import com.strawberry.engine.rendering.Shader;

/**
 * GameComponent ��� ����� ��������� ������� ������ ������������ �GameObject
 * @author LapKazes
 *
 */
public abstract class GameComponent 
{
	private GameObject _parent; //�������� ����� ������
	
	/**
	 * ����� ������ ��� �������
	 * @param delta ��� �����
	 */
	public abstract void input(float delta);
	
	/**
	 * ����� ������� �� ������ �� �����
	 * @param delta ��� �����
	 */
	public abstract void update(float delta);
	
	/**
	 * ����� ������� �� ������ �� ����
	 * @param shader ����
	 * @param engine ���� ������
	 */
	public abstract void render3D(Shader shader, RenderingEngine engine);
	
	/**
	 * ����� ������� �� ������ ����� ����� (�� ����)
	 * @param engine
	 */
	public abstract void addToRenderingEngine(RenderingEngine engine);
	
	public abstract void cleanUp();
	
	/**
	 * ����� ������ �� �������� ����� ������ ����
	 * @param parent
	 */
	public void setParent(GameObject parent) { this._parent = parent; }
	
	/**
	 * ����� ������� �� ������, ����� ����� ��������
	 * @return
	 */
	public Transform getTransform() { return this._parent.getTransform(); }
}
