package com.strawberry.engine.rendering;

import com.strawberry.engine.core.Matrix4f;
import com.strawberry.engine.core.Transform;

public class ForwardAmbient extends Shader
{
	private static final ForwardAmbient instance = new ForwardAmbient();
	
	public static ForwardAmbient getInstance()
	{
		return instance;
	}
	
	public ForwardAmbient()
	{
		super();
		
		addVertexShader(Shader.loadShader("forward-ambient.vs"));
		addFragmentShader(Shader.loadShader("forward-ambient.fs"));
		
		setAttribLocation("position", 0);
		setAttribLocation("textCoord", 1);
		
		
		compileShader();
		
		addUniform("MVP");
		addUniform("ambientIntensity");
	}
	
	public void updateUniforms(Transform transform, Material material, RenderingEngine engine)
	{
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f projectedMatrix = engine.getMainCamera().getViewProjection().mul(worldMatrix);

		material.getTexture("diffuse").bind();
		
		setUniformM("MVP", projectedMatrix);
		setUniformV("ambientIntensity", engine.getAmbientLight());
	}
}
