package com.strawberry.engine.OLD;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

public class Settings 
{
	public static boolean isFullScreen = false;
	
	/**
	 * ����� ������� ��� ��� ��� ����� ���
	 */
	public static void switchFullScreen()
	{
		isFullScreen = !isFullScreen;
		try 
		{
			Display.setFullscreen(isFullScreen);
		}
		catch (LWJGLException e) 
		{
			e.printStackTrace();
		}
	}
}
