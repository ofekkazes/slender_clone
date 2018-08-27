package com.strawberry.engine.rendering.mesh;

import java.util.ArrayList;

import com.strawberry.engine.core.Vector2f;
import com.strawberry.engine.core.Vector3f;

public class IndexedModel 
{
	private ArrayList<Vector3f> _positions;
	private ArrayList<Vector2f> _textCoordinates;
	private ArrayList<Vector3f> _normals;
	private ArrayList<Integer> _indices;
	
	public IndexedModel() 
	{
		this._positions = new ArrayList<Vector3f>();
		this._textCoordinates = new ArrayList<Vector2f>();
		this._normals = new ArrayList<Vector3f>();
		this._indices = new ArrayList<Integer>();
	}
	
	public void calcNormals()
	{
		for(int i=0; i< _indices.size(); i+= 3)
		{
			int i0 = _indices.get(i);
			int i1 = _indices.get(i+1);
			int i2 = _indices.get(i+2);
			
			Vector3f v1 = _positions.get(i1).sub(_positions.get(i0));
			Vector3f v2 = _positions.get(i2).sub(_positions.get(i0));
			
			Vector3f normal = v1.cross(v2).normalize();
			
			_normals.get(i0).set(_normals.get(i0).add(normal));
			_normals.get(i1).set(_normals.get(i1).add(normal));
			_normals.get(i2).set(_normals.get(i2).add(normal));
		}
		
		for(int i = 0; i< _normals.size(); i++)
			_normals.get(i).set(_normals.get(i).normalize());
	}

	public ArrayList<Vector3f> getPositions() {	return _positions; }

	public ArrayList<Vector2f> getTextureCoordinates() { return _textCoordinates; }

	public ArrayList<Vector3f> getNormals() { return _normals; }

	public ArrayList<Integer> getIndices() { return _indices; }
	
}
