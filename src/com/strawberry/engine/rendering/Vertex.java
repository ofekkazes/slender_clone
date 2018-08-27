package com.strawberry.engine.rendering;

import com.strawberry.engine.core.Vector2f;
import com.strawberry.engine.core.Vector3f;

/**
 * מודל מורכב מאוסף של פנים ופנים מורכבות מאוסף של קווים, וקווים מאוסף נקודות, זו מחלקת הנקודה
 * @author LapKazes
 *
 */
public class Vertex 
{
	public static final int SIZE = 8;
	private Vector3f _position;
	private Vector2f _textCoordinates;
	private Vector3f _normal;
	
	public Vertex(Vector3f pos)
	{
		this(pos, new Vector2f(0,0));
	}
	
	public Vertex(Vector3f pos, Vector2f text)
	{
		this(pos,text, Vector3f.ZERO);
	}

	
	public Vertex(Vector3f pos, Vector2f text, Vector3f normal)
	{
		this._position = pos;
		this._textCoordinates = text;
		this._normal = normal;
	}


	public Vector3f getPosition() {
		return _position;
	}

	public void setPosition(Vector3f position) {
		this._position = position;
	}

	public Vector2f getTextCoordinates() {
		return _textCoordinates;
	}

	public void setTextCoordinates(Vector2f textCoordinates) {
		this._textCoordinates = textCoordinates;
	}

	public Vector3f getNormal() {
		return _normal;
	}

	public void setNormal(Vector3f normal) {
		this._normal = normal;
	}

}
