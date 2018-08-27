package com.strawberry.engine.component;

import com.strawberry.engine.core.Vector3f;
import com.strawberry.engine.rendering.ForwardPoint;

public class PointLight extends BaseLight
{
	private static final int COLOR_DEPTH = 256; //תמיכה רק ב256 צבעים שנצטרך לתאורה
	
	private float _range; //מקסימום טווח
	private Vector3f _attenuation; // ההדללה של התאורה בכל ציר
	
	/**
	 * פעולה בונה
	 * @param color צבע התאורה
	 * @param intensity חוזק התאורה
	 * @param attenuation הדללת התאורה בכל פיקסל
	 */
	public PointLight(Vector3f color, float intensity, Vector3f attenuation) 
	{
		super(color, intensity);
		this._attenuation = attenuation;
		
		float a = this._attenuation.getZ();
		float b = this._attenuation.getY();
		float c = this._attenuation.getX() - PointLight.COLOR_DEPTH * getIntensity() * getColor().max();
		
		this.setRange((float)(-b + Math.sqrt(b * b - 4 * a - c)) / (2 * a));
		this.setShader(ForwardPoint.getInstance());
	}

	public float getRange() { return _range; }

	public void setRange(float range) {	this._range = range; }

	public float getConstant() { return this._attenuation.getX(); }

	public float getLinear() { return this._attenuation.getY(); }

	public float getExponent() { return this._attenuation.getZ(); }

	public void setConstant(float constant) { this._attenuation.setX(constant); }

	public void setLinear(float linear) { this._attenuation.setY(linear);	}

	public void setExponent(float exponent) { this._attenuation.setZ(exponent);	}
}
