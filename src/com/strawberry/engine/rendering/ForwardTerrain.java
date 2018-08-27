package com.strawberry.engine.rendering;

import com.strawberry.engine.core.Matrix4f;
import com.strawberry.engine.core.Transform;

public class ForwardTerrain extends Shader
{
	private static final ForwardTerrain instance = new ForwardTerrain();
	
	public static ForwardTerrain getInstance()
	{
		return instance;
	}
	
	public ForwardTerrain()
	{
		super();
		
		addVertexShader(Shader.loadShader("forward-Terrain.vs"));
		addFragmentShader(Shader.loadShader("forward-Terrain.fs"));
		
		setAttribLocation("position", 0);
		setAttribLocation("textCoord", 1);
		
		compileShader();
		
		addUniform("model");
		addUniform("MVP");
	}
	
	public void updateUniforms(Transform transform, Material material, RenderingEngine engine)
	{
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f projectedMatrix = engine.getMainCamera().getViewProjection().mul(worldMatrix);

		material.getTexture("diffuse").bind();
		
		setUniformM("MVP", projectedMatrix);
		setUniformM("model", worldMatrix);
	}
	
}
