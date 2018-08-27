package com.strawberry.engine.rendering;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import com.strawberry.engine.core.Matrix4f;
import com.strawberry.engine.core.Transform;
import com.strawberry.engine.core.Vector3f;

//import org.lwjgl.util.vector.Vector3f;

/**
 * מחלקת השיידר
 * (אפקט למודל)
 * @author LapKazes
 *
 */
public abstract class Shader 
{
	private int _program;
	private HashMap<String, Integer> _uniforms;
	
	public Shader()
	{
		_program = glCreateProgram();
		if(_program == 0)
		{
			System.err.println("Shader creation has failed: Could not find memory location in constructor");
			System.exit(1);
		}
		
		this._uniforms = new HashMap<String, Integer>();
	}
	
	/**
	 * מוסיף שיידר המתמחה בקוי המודל
	 * @param text
	 */
	public void addVertexShader(String text)
	{
		addProgram(text, GL_VERTEX_SHADER);
	}
	
	public void addUniform(String uniform)
	{
		int uniformLocation = glGetUniformLocation(_program, uniform);
		//glBindAttribLocation(_program, 0, uniform);
		if(uniformLocation == 0xFFFFFFFF)
		{
			System.err.println("Could not find uniform: "+ uniform);
			System.exit(1);
		}
		//glBindAttribLocation(_program, 0, uniform);
		this._uniforms.put(uniform, uniformLocation);
		
	}
	
	/**
	 * מוסיף שיידר המתמחה בפיזיקה של המודל
	 * @param text
	 */
	public void addGeometryShader(String text)
	{
		addProgram(text, GL_GEOMETRY_SHADER);
	}
	
	/**
	 * מוסיף שיידר המתמחה באפקטים סביבתיים
	 * @param text
	 */
	public void addFragmentShader(String text)
	{
		addProgram(text, GL_FRAGMENT_SHADER);
	}
	
	/**
	 * מטודה הטוענת לbuffer
	 * של opengl
	 * את האפקט
	 */
	public void bind()
	{
		glUseProgram(_program);
		glBindAttribLocation(_program, 0, "location");
	}
	
	/**
	 * מעדכן את הפרמטרים
	 * @param transform מיקום
	 * @param material מאפיינים
	 * @param engine מנוע הרינדור
	 */
	public abstract void updateUniforms(Transform transform, Material material, RenderingEngine engine);
	
	public void setAttribLocation(String attributeName, int location)
	{
		glBindAttribLocation(_program, location, attributeName);
	}
	
	/**
	 * המטודה אומרת לOpenGL
	 * להשתמש באפקט
	 */
	public void compileShader()
	{
		glLinkProgram(_program);
		if(glGetProgrami(_program, GL_LINK_STATUS) == 0)
		{
			System.err.println(glGetProgramInfoLog(_program, 1024));
			System.exit(1);
		}
		
		glValidateProgram(_program);
		if(glGetProgrami(_program, GL_VALIDATE_STATUS) == 0)
		{
			System.err.println(glGetProgramInfoLog(_program, 1024));
			System.exit(1);
		}
	}
	
	/**
	 * המטודה מוסיפה את האפקט מהזיכרון לopengl
	 * @param text
	 * @param type
	 */
	private void addProgram(String text, int type)
	{
		int shader =  glCreateShader(type);
		if(shader == 0)
		{
			System.err.println("Shader creation has failed: Could not find memory location when adding shader");
			System.exit(1);
		}
		glShaderSource(shader, text);
		glCompileShader(shader);
		
		if(glGetShaderi(shader, GL_COMPILE_STATUS) == 0)
		{
			System.err.println(glGetShaderInfoLog(shader, 1024));
			System.exit(1);
		}
		
		glAttachShader(_program, shader);	
	}
	
	public void setUniformi(String uniformName, int value)
	{
		glUniform1i(this._uniforms.get(uniformName), value);
	}
	
	public void setUniformf(String uniformName, float value)
	{
		glUniform1f(this._uniforms.get(uniformName), value);
	}
	
	public void setUniformV(String uniformName, Vector3f value)
	{
		glUniform3f(this._uniforms.get(uniformName), value.getX(), value.getY(), value.getZ());
	}
	
	public void setUniformM(String uniformName, Matrix4f value)
	{
		glUniformMatrix4(this._uniforms.get(uniformName), true, Mesh.createFlippedBuffer(value));
		
	}
	
	/**
	 * מטודה הטוענת שיידר ( אוסף הנחיות שנועדו לצייר אפקטים למודלים)
	 * @param filename שם הקובץ
	 * @return
	 */
	public static String loadShader(String filename)
	{
		StringBuilder shaderSource = new StringBuilder();
		
		BufferedReader shaderReader = null;
		try
		{
			shaderReader = new BufferedReader(new FileReader("./res/shaders/"+filename));
			String line;
			while ((line  = shaderReader.readLine()) != null)
			{
				shaderSource.append(line).append("\n");
			}
			shaderReader.close();
		}catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		
		return shaderSource.toString();
	}
}
