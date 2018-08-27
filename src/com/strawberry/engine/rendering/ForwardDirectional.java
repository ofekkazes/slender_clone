package com.strawberry.engine.rendering;

import com.strawberry.engine.component.BaseLight;
import com.strawberry.engine.component.DirectionalLight;
import com.strawberry.engine.core.Matrix4f;
import com.strawberry.engine.core.Transform;

public class ForwardDirectional extends Shader
{
	private static final ForwardDirectional instance = new ForwardDirectional();
	
	public static ForwardDirectional getInstance()
	{
		return instance;
	}
	
	public ForwardDirectional()
	{
		super();
		
		addVertexShader(Shader.loadShader("forward-directional.vs"));
		addFragmentShader(Shader.loadShader("forward-directional.fs"));
		
		setAttribLocation("position", 0);
		setAttribLocation("textCoord", 1);
		setAttribLocation("normal", 2);
		
		compileShader();
		
		addUniform("model");
		addUniform("MVP");
		
		addUniform("specularIntensity");
		addUniform("specularPower");
		addUniform("eyePos");
		
		addUniform("directionalLight.base.color");
		addUniform("directionalLight.base.intensity");
		addUniform("directionalLight.direction");
	}
	
	public void updateUniforms(Transform transform, Material material, RenderingEngine engine)
	{
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f projectedMatrix = engine.getMainCamera().getViewProjection().mul(worldMatrix);

		material.getTexture("diffuse").bind();
		
		setUniformM("MVP", projectedMatrix);
		setUniformM("model", worldMatrix);
		
		setUniformf("specularIntensity", material.getFloat("specularIntensity"));
		setUniformf("specularPower", material.getFloat("specularPower"));
		
		setUniformV("eyePos", engine.getMainCamera().getTransform().getTransformedPosition());
		
		setUniform("directionalLight", (DirectionalLight)engine.getActiveLight());
	}
	
	public void setUniform(String uniformName, BaseLight baseLight)
	{
		setUniformV(uniformName + ".color", baseLight.getColor());
		setUniformf(uniformName + ".intensity", baseLight.getIntensity());
	}
	
	public void setUniform(String uniformName, DirectionalLight directionalLight)
	{
		setUniform(uniformName + ".base", (BaseLight)directionalLight);
		setUniformV(uniformName + ".direction", directionalLight.getDirection());
	}
}
