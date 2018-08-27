package com.strawberry.engine.component;

import com.strawberry.engine.core.Vector3f;
import com.strawberry.engine.rendering.ForwardSpot;

/**
 * מחלקה שיורשת מתאורה מקיפה ומשמשת לאובייקטים כמו פנס ותאורת רחוב
 * @author LapKazes
 *
 */
public class SpotLight extends PointLight
{
	private float _cutoff; //הורדת כמות התאורה לכל פיקסל
	
	/**
	 * פעולה בונה
	 * @param color צבע התאורה
	 * @param intensity חוזק התאורה
	 * @param attenuation הדללת התאורה בכל פיקסל
	 * @param cutoff הורדת כמות התאורה לכל פיקסל
	 */
	public SpotLight(Vector3f color, float intensity, Vector3f attenuation, float cutoff) 
	{
		super(color, intensity, attenuation);
		this._cutoff = cutoff;
		this.setShader(ForwardSpot.getInstance());
	}

	/**
	 * כיוון התאורה
	 * @return
	 */
	public Vector3f getDirection() {
		return getTransform().getTransformedRotation().getForward();
	}

	public float getCutoff() { return _cutoff; }

	public void setCutoff(float cutoff) { this._cutoff = cutoff; }

}
