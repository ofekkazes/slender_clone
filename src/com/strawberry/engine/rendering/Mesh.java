package com.strawberry.engine.rendering;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import com.strawberry.engine.core.Matrix4f;
import com.strawberry.engine.core.Statics;
import com.strawberry.engine.core.Vector3f;
import com.strawberry.engine.rendering.mesh.IndexedModel;
import com.strawberry.engine.rendering.mesh.OBJModel;

/**
 * מודל מורכב מאוסף של פנים
 * פנים מורכבים מאוסף של קווים
 * קווים מורכבים מאוסף של נקודות
 * 
 * זה מחלקת מודל
 * @author LapKazes
 *
 */
public class Mesh 
{
	private int _size;
	private int _ibo;
	private int _vbo;
	
	/**
	 * קונסטרקטור המחלקה
	 */
	public Mesh(Vertex[] vertices, int[] indices)
	{
		this._vbo = glGenBuffers();
		this._ibo = glGenBuffers();
		this._size = 0;
		
		addVertices(vertices, indices, true);
	}
	public Mesh()
	{
		this._vbo = glGenBuffers();
		this._ibo = glGenBuffers();
		this._size = 0;
	}
	
	public Mesh(String filename)
	{
		this._vbo = glGenBuffers();
		this._ibo = glGenBuffers();
		this._size = 0;
		
		loadMesh(filename);
	}
	
	
	/**
	 * מטודת הוספת קווים מנחים (Vertex)
	 * @param vertices
	 */
	public void addVertices(Vertex[] vertices, int[] indices)
	{
		addVertices(vertices, indices, false);
		
	}
	
	public void addVertices(Vertex[] vertices, int[] indices, boolean calcNormals)
	{
		if(calcNormals)
			calcNormals(vertices, indices);
		
		this._size = indices.length;
		
		glBindBuffer(GL_ARRAY_BUFFER, this._vbo);
		glBufferData(GL_ARRAY_BUFFER, createFlippedBuffer(vertices), GL_STATIC_DRAW);
	
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this._ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, createFlippedBuffer(indices), GL_STATIC_DRAW);
		
	}
	
	/**
	 * המטודה מציירת את הפנים של המודל
	 */
	public void render()
	{
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		
		glBindBuffer(GL_ARRAY_BUFFER, this._vbo);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this._ibo);
		glDrawElements(GL_TRIANGLES, this._size, GL_UNSIGNED_INT, 0);
		//glDrawArrays(GL_TRIANGLES, 0, this._size);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
	}
	
	private void calcNormals(Vertex[] vertices, int[] indices)
	{
		for(int i=0; i<indices.length; i+= 3)
		{
			int i0 = indices[i];
			int i1 = indices[i+1];
			int i2 = indices[i+2];
			
			Vector3f v1 = vertices[i1].getPosition().sub(vertices[i0].getPosition());
			Vector3f v2 = vertices[i2].getPosition().sub(vertices[i0].getPosition());
			
			Vector3f normal = v1.cross(v2).normalize();
			
			vertices[i0].setNormal(vertices[i0].getNormal().add(normal));
			vertices[i1].setNormal(vertices[i1].getNormal().add(normal));
			vertices[i2].setNormal(vertices[i2].getNormal().add(normal));
		}
		
		for(int i = 0; i< vertices.length; i++)
			vertices[i].setNormal(vertices[i].getNormal().normalize());
	}
	
	private static FloatBuffer createFloatBuffer(int size)
	{
		return BufferUtils.createFloatBuffer(size);
	}
	
	private static IntBuffer createIntBuffer(int size)
	{
		return BufferUtils.createIntBuffer(size);
	}
	
	private static IntBuffer createFlippedBuffer(int... values)
	{
		IntBuffer buffer = createIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		return buffer;
	}
	
	private static FloatBuffer createFlippedBuffer(Vertex[] vertices)
	{
		FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.SIZE );

		for(int i=0;i<vertices.length; i++)
		{
			buffer.put(vertices[i].getPosition().getX());
			buffer.put(vertices[i].getPosition().getY());
			buffer.put(vertices[i].getPosition().getZ());
			buffer.put(vertices[i].getTextCoordinates().getX());
			buffer.put(vertices[i].getTextCoordinates().getY());
			buffer.put(vertices[i].getNormal().getX());
			buffer.put(vertices[i].getNormal().getY());
			buffer.put(vertices[i].getNormal().getZ());
		}
		
		buffer.flip();
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Matrix4f value)
	{
		FloatBuffer buffer = createFloatBuffer(4*4);

		for(int i=0;i<4; i++)
		{
			for(int j=0;j<4; j++)
			{
				buffer.put(value.get(i, j));
			}
		}
		
		buffer.flip();
		return buffer;
	}
	
	public Mesh loadMesh(String filename)
	{
		String[] splitArr = filename.split("\\.");
		String ext = splitArr[splitArr.length - 1];
		
		
		if(!ext.equals("obj"))
		{
			System.err.println("At this point only .obj is supported");
			System.exit(1);
		}
		OBJModel test = new OBJModel("./res/models/"+filename);
		IndexedModel model = test.toIndexedModel();
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		
		for (int i = 0; i < model.getPositions().size(); i++) 
		{
			vertices.add(new Vertex(model.getPositions().get(i),
									model.getTextureCoordinates().get(i),
									model.getNormals().get(i)));
		}
		
		Vertex[] vertexData = new Vertex[vertices.size()];
		vertices.toArray(vertexData);
		
		Integer[] indexData = new Integer[model.getIndices().size()];
		model.getIndices().toArray(indexData);
		model.calcNormals();
		
		this.addVertices(vertexData, Statics.convertToIntArray(indexData), false);
		
		//ArrayList<Integer> indices = new ArrayList<Integer>();
		/*
		BufferedReader reader = null;
		
		try
		{
			reader = new BufferedReader(new FileReader("./res/models/"+filename));
			String line;
			while ((line  = reader.readLine()) != null)
			{
				String[] tokens = line.split(" ");
				tokens = Statics.removeEmptyStrings(tokens);
				
				if(tokens.length == 0 || tokens[0].equals("#"))
					continue;
				else if(tokens[0].equals("v"))
				{
					vertices.add(new Vertex(new Vector3f(Float.valueOf(tokens[1]), 
									  		  			 Float.valueOf(tokens[2]),
									  		  			 Float.valueOf(tokens[3]))));
				}
				else if(tokens[0].equals("f"))
				{
					indices.add(Integer.parseInt(tokens[1].split("/")[0]) - 1);
					indices.add(Integer.parseInt(tokens[2].split("/")[0]) - 1);
					indices.add(Integer.parseInt(tokens[3].split("/")[0]) - 1);
					
					if(tokens.length > 4)
					{
						indices.add(Integer.parseInt(tokens[1].split("/")[0]) - 1);
						indices.add(Integer.parseInt(tokens[3].split("/")[0]) - 1);
						indices.add(Integer.parseInt(tokens[4].split("/")[0]) - 1);
					}
				}
			}
			reader.close();
			
			Mesh resultMesh = new Mesh();
			Vertex[] vertexData = new Vertex[vertices.size()];
			vertices.toArray(vertexData);
			
			Integer[] indexData = new Integer[indices.size()];
			indices.toArray(indexData);
			
			resultMesh.addVertices(vertexData, Statics.convertToIntArray(indexData));
			
			return resultMesh;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}*/
		return null;
	}
	public void cleanUp() {
		this._ibo = 0;
		this._size = 0;
		this._vbo = 0;
		
	}
}
