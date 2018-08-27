package com.strawberry.engine.component;

import com.strawberry.engine.rendering.ForwardTerrain;
import com.strawberry.engine.rendering.Material;
import com.strawberry.engine.rendering.Mesh;
import com.strawberry.engine.rendering.RenderingEngine;
import com.strawberry.engine.rendering.Shader;

/**
 *  מחלקה שיורשת ממחלקת הפונקציונליות הבסיסית ומציירת את המודל על המסך עם החומר שלו (טקסטורה ועוד)
 * @author LapKazes
 */
public class TerrainRenderer extends MeshRenderer
{
	/**
	 * פעולה בונה של מצייר המודלים
	 * @param mesh מודל לציור
	 * @param material חומר המודל
	 */
	public TerrainRenderer(Mesh mesh, Material material)
	{
		super(mesh, material);
	}
	
	/**
	 * ציור המודל על המסך, בתוספת האפקט ומנוע הציור
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
