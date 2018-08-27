package com.strawberry.engine.OLD;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;
import static org.lwjgl.opengl.GL11.glVertex3f;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.opengl.Texture;


public class Statics 
{
	
	/**
	 * מטודה המציירת קובייה דו מימדית בחלל תלת מימדי
	 *//*
	public static void drawCube(int x, int y, int z, int width, int height, float r, float g, float b)
	{
		glColor3f(r, g, b);
		glBegin(GL_QUADS);
		{
			glVertex3f(x, y, z);
			glVertex3f(x, y+height, z);
			glVertex3f(x+width, y+height, z);
			glVertex3f(x+width, y, z);
			
		}
		glEnd();
	}
	*/
	/**
	 * מטודת ציור הטקסטורה
	 * @param t
	 * @param position
	 *//*
	public static void renderTexture(Texture t, Rectangle position)
	{
		glEnable(GL_TEXTURE_2D);
		glColor3f(1, 1, 1);
		t.bind();
		glBegin(GL_QUADS);
		{
			glTexCoord2f(0, 0);
			glVertex2i((int)position.getMinX(), (int)position.getMinY()); // Upper-Left
			glTexCoord2f(1, 0);
			glVertex2i((int)position.getWidth(), (int)position.getMinY()); // Upper-Right
			glTexCoord2f(1, 1);
			glVertex2i((int)position.getWidth(), (int)position.getHeight()); // Bottom-Right
			glTexCoord2f(0, 1);
			glVertex2i((int)position.getMinX(), (int)position.getHeight()); // Bottom-Left
		}
		glEnd();
	}
	*/
	/**
	 * מטודה המציירת טקסט על המסך
	 * @param font
	 * @param text
	 * @param position
	 * @param color
	 */
	public static void renderText(TrueTypeFont font, String text, Vector2f position, Color color)
	{
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		color.bind();
        font.drawString(position.getX(), position.getY(), text, color);
        glDisable(GL_BLEND);
	}
	
	/**
	 * מטודה הבודקת אם יש פגיעה בין שתי נקודות תלת מימדיות
	 * @param radius
	 * @param main
	 * @param source
	 * @return
	 */
	public static boolean isInRectangularRadius(float radius, Vector3f main, Vector3f source)
	{
		if(main.getX() + radius > source.getX() && main.getX() - radius < source.getX())//Check radius in x axis
		{
			if(main.getZ() + radius > source.getZ() && main.getZ() - radius < source.getZ())//Check radius in z axis
			{
				//Height radius required
				
				//if y is gathering from top to bottom
				if(main.getY() - radius < source.getY() && main.getY() + radius > source.getY())
					return true;
			}
		}
		return false;
	}
	
	/**
	 * מטודה הבודקת אם יש פגיעה בין עיגולים
	 * @param radius
	 * @param main
	 * @param source
	 * @return
	 */
	public static boolean isInCircularRadius(float radius, Vector3f main, Vector3f source)
	{
		float distance = (float) Math.sqrt(Math.pow(source.getX() - main.getX(),  2) + Math.pow(source.getY() - main.getY(),  2) + Math.pow(source.getZ() - main.getZ(),  2));
		return radius > distance;
	}
}
