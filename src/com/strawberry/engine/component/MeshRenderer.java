package com.strawberry.engine.component;

import com.strawberry.engine.rendering.Material;
import com.strawberry.engine.rendering.Mesh;
import com.strawberry.engine.rendering.RenderingEngine;
import com.strawberry.engine.rendering.Shader;

/**
 *  ����� ������ ������ ������������� ������� ������� �� ����� �� ���� �� ����� ��� (������� ����)
 * @author LapKazes
 */
public class MeshRenderer extends GameComponent
{
	protected Mesh _mesh; // ����
	protected Material _material; //���� �����
	
	/**
	 * ����� ���� �� ����� �������
	 * @param mesh ���� �����
	 * @param material ���� �����
	 */
	public MeshRenderer(Mesh mesh, Material material)
	{
		this._mesh = mesh;
		this._material = material;
	}
	
	/**
	 * ���� ����� �� ����, ������ ����� ����� �����
	 */
	public void render3D(Shader shader, RenderingEngine engine) 
	{
		shader.bind();
		shader.updateUniforms(getTransform(), _material, engine);
		
		_mesh.render();
	}
	
	@Override
	public void input(float delta) {}
	
	@Override
	public void update(float delta) {}
	
	@Override
	public void addToRenderingEngine(RenderingEngine engine) {}

	@Override
	public void cleanUp() {
		this._material.cleanUp();
		this._mesh.cleanUp();
		
	}

}
