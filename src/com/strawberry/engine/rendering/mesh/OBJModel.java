package com.strawberry.engine.rendering.mesh;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.strawberry.engine.core.Statics;
import com.strawberry.engine.core.Vector2f;
import com.strawberry.engine.core.Vector3f;

public class OBJModel 
{
	private ArrayList<Vector3f> _positions;
	private ArrayList<Vector2f> _textCoordinates;
	private ArrayList<Vector3f> _normals;
	private ArrayList<OBJIndex> _indices;
	private boolean _hasTextureCoordinates;
	private boolean _hasNormals;
	
	public OBJModel(String filename) 
	{
		this._positions = new ArrayList<Vector3f>();
		this._textCoordinates = new ArrayList<Vector2f>();
		this._normals = new ArrayList<Vector3f>();
		this._indices = new ArrayList<OBJIndex>();
		this._hasTextureCoordinates = false;
		this._hasNormals = false;
		
		load(filename);
	}
	
	private void load(String filename)
	{
		BufferedReader reader = null;
		
		try
		{
			reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line  = reader.readLine()) != null)
			{
				String[] tokens = line.split(" ");
				tokens = Statics.removeEmptyStrings(tokens);
				
				if(tokens.length == 0 || tokens[0].equals("#"))
					continue;
				else if(tokens[0].equals("v"))
				{
					_positions.add(new Vector3f(Float.valueOf(tokens[1]), 
									  		    Float.valueOf(tokens[2]),
									  		    Float.valueOf(tokens[3])));
				}
				else if(tokens[0].equals("vt"))
				{
					_textCoordinates.add(new Vector2f(Float.valueOf(tokens[1]), 
													  Float.valueOf(tokens[2])));
				}
				else if(tokens[0].equals("vn"))
				{
					_normals.add(new Vector3f(Float.valueOf(tokens[1]), 
									  		    Float.valueOf(tokens[2]),
									  		    Float.valueOf(tokens[3])));
				}
				else if(tokens[0].equals("f"))
				{
					for (int i = 0; i < tokens.length - 3; i++) 
					{
						this._indices.add(parseOBJIndex(tokens[1]));
						this._indices.add(parseOBJIndex(tokens[2 + i]));
						this._indices.add(parseOBJIndex(tokens[3 + i]));
					}
				}
			}
			reader.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public IndexedModel toIndexedModel()
	{
		IndexedModel res = new IndexedModel();
		IndexedModel normalModel = new IndexedModel();
		HashMap<OBJIndex, Integer> resultIndexMap = new HashMap<OBJIndex, Integer>();
		HashMap<Integer, Integer> normalIndexMap = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> indexMap = new HashMap<Integer, Integer>();

		for (int i = 0; i < this._indices.size(); i++) 
		{
			OBJIndex currentIndex = this._indices.get(i);
			
			Vector3f currentPosition = this._positions.get(currentIndex._vertexIndex);
			Vector2f currentTexture;
			Vector3f currentNormal;
			
			if(this._hasTextureCoordinates) 
				currentTexture = this._textCoordinates.get(currentIndex._textureCoordIndex);
			else currentTexture = Vector2f.ZERO;
			
			if(this._hasNormals) 
				currentNormal = this._normals.get(currentIndex._normalIndex);
			else currentNormal = Vector3f.ZERO;
			
			Integer modelVertexIndex = resultIndexMap.get(currentIndex);
			
			if(modelVertexIndex == null)
			{
				modelVertexIndex = res.getPositions().size();
				resultIndexMap.put(currentIndex, modelVertexIndex);
							
				res.getPositions().add(currentPosition);
				res.getTextureCoordinates().add(currentTexture);
				if(_hasNormals)
					res.getNormals().add(currentNormal);
			}
			Integer normalModelIndex = normalIndexMap.get(currentIndex._vertexIndex);
			if(normalModelIndex == null)
			{
				normalIndexMap.put(currentIndex._vertexIndex, normalModel.getPositions().size());
				normalModelIndex = normalModel.getPositions().size();
				
				normalModel.getPositions().add(currentPosition);
				normalModel.getTextureCoordinates().add(currentTexture);
				normalModel.getNormals().add(currentNormal);
			}
			
			res.getIndices().add(modelVertexIndex);
			normalModel.getIndices().add(normalModelIndex);
			indexMap.put(modelVertexIndex, normalModelIndex);
		}
		
		if(!_hasNormals)
		{
			normalModel.calcNormals();
			
			for (int i = 0; i < res.getPositions().size(); i++) 
				res.getNormals().add(normalModel.getNormals().get(indexMap.get(i)));
		}
		
		return res;
	}
	
	private OBJIndex parseOBJIndex(String token)
	{
		String[] values = token.split("/");
		OBJIndex res = new OBJIndex();
		res._vertexIndex = Integer.parseInt(values[0]) - 1;
		if(values.length > 1)
		{
			this._hasTextureCoordinates = true;
			res._textureCoordIndex = Integer.parseInt(values[1]) - 1;
			
			if(values.length > 2)
			{
				this._hasNormals = true;
				res._normalIndex = Integer.parseInt(values[2]) - 1;
			}
		}
		return res;
	}

	public ArrayList<Vector3f> getPositions() {	return _positions; }

	public ArrayList<Vector2f> getTextureCoordinates() { return _textCoordinates; }

	public ArrayList<Vector3f> getNormals() { return _normals; }

	public ArrayList<OBJIndex> getIndices() { return _indices; }
	
}
