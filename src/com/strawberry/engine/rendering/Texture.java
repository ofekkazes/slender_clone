package com.strawberry.engine.rendering;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

import java.io.File;
import java.io.FileInputStream;

import org.newdawn.slick.opengl.TextureLoader;

public class Texture 
{
	private int _id;
	
	public Texture(int id)
	{
		this._id = id;
	}
	
	public void bind()
	{
		glBindTexture(GL_TEXTURE_2D, this._id);
	}

	public int getId() {
		return _id;
	}

	public void setId(int id) {
		this._id = id;
	}
	

	
	/**
	 * מטודה הטוענת טקסטורה ומחזירה את המיקום שלה, לשימוש בכרטיס מסך
	 * @param filename שם הקובץ
	 * @return
	 */
	public static com.strawberry.engine.rendering.Texture loadTextureID(String filename)
	{
		String[] splitArray = filename.split("\\.");
		String ext = splitArray[splitArray.length - 1];
		
		try
		{
			int id = TextureLoader.getTexture(ext, new FileInputStream(new File("./res/textures/" + filename))).getTextureID();
			return new com.strawberry.engine.rendering.Texture(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
}
