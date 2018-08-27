package com.strawberry.engine.rendering.mesh;

public class OBJIndex 
{
	public int _vertexIndex;
	public int _textureCoordIndex;
	public int _normalIndex;
	
	public boolean equlas(Object obj)
	{
		OBJIndex index = (OBJIndex)obj;
		
		return _vertexIndex == index._vertexIndex &&
			   _textureCoordIndex == index._textureCoordIndex &&
			   _normalIndex == index._normalIndex;
	}
	
	@Override
	public int hashCode()
	{
		final int BASE = 17;
		final int MULTIPLIER = 31;
		
		int res = BASE;
		res = MULTIPLIER * res + _vertexIndex;
		res = MULTIPLIER * res + _textureCoordIndex;
		res = MULTIPLIER * res + _normalIndex;
		
		return res;
	}
}
