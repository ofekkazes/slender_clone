package com.strawberry.engine.rendering;

import com.strawberry.engine.component.BaseLight;
import com.strawberry.engine.component.PointLight;
import com.strawberry.engine.component.SpotLight;
import com.strawberry.engine.core.Matrix4f;
import com.strawberry.engine.core.Transform;

public class ForwardSpot extends Shader
{
	private static final ForwardSpot instance = new ForwardSpot();
	
	public static ForwardSpot getInstance()
	{
		return instance;
	}
	
	public ForwardSpot()
	{
		super();
		
		addVertexShader(Shader.loadShader("forward-spot.vs"));
		addFragmentShader(Shader.loadShader("forward-spot.fs"));
		
		setAttribLocation("position", 0);
		setAttribLocation("textCoord", 1);
		setAttribLocation("normal", 2);
		
		compileShader();
		
		addUniform("model");
		addUniform("MVP");
		
		addUniform("specularIntensity");
		addUniform("specularPower");
		addUniform("eyePos");
		
		addUniform("spotLight.pointLight.base.color");
		addUniform("spotLight.pointLight.base.intensity");
		addUniform("spotLight.pointLight.atten.constant");
		addUniform("spotLight.pointLight.atten.linear");
		addUniform("spotLight.pointLight.atten.exponent");
		addUniform("spotLight.pointLight.position");
		addUniform("spotLight.pointLight.range");
		
		addUniform("spotLight.direction");
		addUniform("spotLight.cutoff");
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
		setUniform("spotLight", (SpotLight)engine.getActiveLight());
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
	private void setUniform(String uniformName, SpotLight spotLight) 
	{
		setUniform(uniformName + ".pointLight", (PointLight)spotLight);
		setUniformV(uniformName + ".direction", spotLight.getDirection());
		setUniformf(uniformName + ".cutoff", spotLight.getCutoff());
	}
}
