package com.strawberry.engine.component;

import com.strawberry.engine.core.Vector3f;
import com.strawberry.engine.rendering.ForwardSpot;

/**
 * ����� ������ ������ ����� ������ ���������� ��� ��� ������ ����
 * @author LapKazes
 *
 */
public class SpotLight extends PointLight
{
	private float _cutoff; //����� ���� ������ ��� �����
	
	/**
	 * ����� ����
	 * @param color ��� ������
	 * @param intensity ���� ������
	 * @param attenuation ����� ������ ��� �����
	 * @param cutoff ����� ���� ������ ��� �����
	 */
	public SpotLight(Vector3f color, float intensity, Vector3f attenuation, float cutoff) 
	{
		super(color, intensity, attenuation);
		this._cutoff = cutoff;
		this.setShader(ForwardSpot.getInstance());
	}

	/**
	 * ����� ������
	 * @return
	 */
	public Vector3f getDirection() {
		return getTransform().getTransformedRotation().getForward();
	}

	public float getCutoff() { return _cutoff; }

	public void setCutoff(float cutoff) { this._cutoff = cutoff; }

}
