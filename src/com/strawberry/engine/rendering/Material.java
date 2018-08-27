package com.strawberry.engine.rendering;

import java.util.HashMap;

import com.strawberry.engine.core.Vector3f;

public class Material 
{
	private HashMap<String, Texture> _textureHash;
	private HashMap<String, Vector3f> _vectorHash;
	private HashMap<String, Float> _floatHash;
	
	
	public Material() 
	{
		this._textureHash = new HashMap<String, Texture>();
		this._vectorHash = new HashMap<String, Vector3f>();
		this._floatHash = new HashMap<String, Float>();
	}
	
	public void addTexture(String name, Texture texture) { this._textureHash.put(name, texture); }
	
	public Texture getTexture(String name) 
	{ 
		Texture t = this._textureHash.get(name);
		if(t != null) return t;
		
		return Texture.loadTextureID("java.jpg");
	}
	
	public void addVector(String name, Vector3f vector) { this._vectorHash.put(name, vector); }
	
	public Vector3f getVector(String name) 
	{ 
		Vector3f v = this._vectorHash.get(name);
		if(v != null) return v;
		
		return Vector3f.ZERO;
	}
	
	public void addFloat(String name, Float f) { this._floatHash.put(name, f); }
	
	public float getFloat(String name) 
	{ 
		Float f = this._floatHash.get(name);
		if(f != null) return f;
		
		return 0;
	}

	public void cleanUp() {
		this._floatHash.clear();
		this._textureHash.clear();
		this._vectorHash.clear();
	}
	
	
}
