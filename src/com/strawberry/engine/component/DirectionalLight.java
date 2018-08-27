package com.strawberry.engine.component;

import com.strawberry.engine.core.Vector3f;
import com.strawberry.engine.rendering.ForwardDirectional;

/**
 * ����� ������� ����� �����
 * @author LapKazes
 *
 */
public class DirectionalLight extends BaseLight
{

	public DirectionalLight(Vector3f color, float intensity) 
	{
		super(color, intensity);

		setShader(ForwardDirectional.getInstance());
	}
	
	 /**
	  * ����� ������� �� ����� ������
	  * @return
	  */
	public Vector3f getDirection() { return getTransform().getTransformedRotation().getForward(); }
	
	
}
