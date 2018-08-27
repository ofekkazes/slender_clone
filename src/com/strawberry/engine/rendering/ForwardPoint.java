package com.strawberry.engine.rendering;

import com.strawberry.engine.component.BaseLight;
import com.strawberry.engine.component.PointLight;
import com.strawberry.engine.core.Matrix4f;
import com.strawberry.engine.core.Transform;

public class ForwardPoint extends Shader
{
	private static final ForwardPoint instance = new ForwardPoint();
	
	public static ForwardPoint getInstance()
	{
		return instance;
	}
	
	public ForwardPoint()
	{
		super();
		
		addVertexShader(Shader.loadShader("forward-point.vs"));
		addFragmentShader(Shader.loadShader("forward-point.fs"));
		
		setAttribLocation("position", 0);
		setAttribLocation("textCoord", 1);
		setAttribLocation("normal", 2);
		
		compileShader();
		
		addUniform("model");
		addUniform("MVP");
		
		addUniform("specularIntensity");
		addUniform("specularPower");
		addUniform("eyePos");
		
		addUniform("pointLight.base.color");
		addUniform("pointLight.base.intensity");
		addUniform("pointLight.atten.constant");
		addUniform("pointLight.atten.linear");
		addUniform("pointLight.atten.exponent");
		addUniform("pointLight.position");
		addUniform("pointLight.range");
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
		setUniform("pointLight", (PointLight)engine.getActiveLight());
	}
	
	public void setUniform(String uniformName, BaseLight baseLight)
	{
		setUniformV(uniformName + ".color", baseLight.getColor());
		setUniformf(uniformName + ".intensity", baseLight.getIntensity());
	}
	
	private void setUniform(String uniformName, PointLight pointLight) 
	{
		setUniform(uniformName + ".base", (BaseLight)pointLight);
		setUniformf(uniformName + ".atten.constant", pointLight.getConstant());
		setUniformf(uniformName + ".atten.linear", pointLight.getLinear());
		setUniformf(uniformName + ".atten.exponent", pointLight.getExponent());
		setUniformV(uniformName + ".position", pointLight.getTransform().getTransformedPosition());
		setUniformf(uniformName + ".range", pointLight.getRange());
	}
	
}
