package com.strawberry.engine.component;

import com.strawberry.engine.rendering.ForwardTerrain;
import com.strawberry.engine.rendering.Material;
import com.strawberry.engine.rendering.Mesh;
import com.strawberry.engine.rendering.RenderingEngine;
import com.strawberry.engine.rendering.Shader;

/**
 *  ����� ������ ������ ������������� ������� ������� �� ����� �� ���� �� ����� ��� (������� ����)
 * @author LapKazes
 */
public class TerrainRenderer extends MeshRenderer
{
	/**
	 * ����� ���� �� ����� �������
	 * @param mesh ���� �����
	 * @param material ���� �����
	 */
	public TerrainRenderer(Mesh mesh, Material material)
	{
		super(mesh, material);
	}
	
	/**
	 * ���� ����� �� ����, ������ ����� ����� �����
	 */
	public void render3D(Shader shader, RenderingEngine engine) 
	{
		ForwardTerrain t = ForwardTerrain.getInstance();
		t.bind();
		t.updateUniforms(getTransform(), _material, engine);

		_mesh.render();
		
		super.render3D(shader, engine);
	}


}
