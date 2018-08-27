package com.strawberry.engine.core;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 * ����� ������ ������ ���� �� �����
 * @author LapKazes
 *
 */
public class Input 
{
	public static final int NUM_KEYCODES = 256; //���� ������
	public static final int NUM_MOUSEBUTTONS = 5; //���� �����
	
	private static boolean[] _lastKeys = new boolean[NUM_KEYCODES]; //������ ������� �����
	private static boolean[] _lastMouse = new boolean[NUM_MOUSEBUTTONS]; //���� ����� ������� �����
	
	/**
	 * ������ ������ �� ������ ����� ������� �� ������ ������� ��� �����
	 */
	public static void update()
	{
		for (int i = 0; i < NUM_KEYCODES; i++) 
		{
			_lastKeys[i] = getKey(i);
		}
		for (int i = 0; i < NUM_MOUSEBUTTONS; i++) 
		{
			_lastMouse[i] = getMouse(i);
		}
	}
	
	/**
	 * ������ ����� ���� �� ��� ������ ������� ��� ��� ����
	 * @param keyCode
	 * @return
	 */
	public static boolean getKey(int keyCode)
	{
		return Keyboard.isKeyDown(keyCode);
	}
	
	/**
	 * ����� ������ ���� �� ��� ������ ������� ��� ��� ���� ������ ��� ������ ������
	 * @param keyCode
	 * @return
	 */
	public static boolean getKeyDown(int keyCode)
	{
		return getKey(keyCode) && !_lastKeys[keyCode];
	}
	
	/**
	 * ����� ������ ���� ��� ������ ������� ��� ���� ��� ����� ������
	 * @param keyCode
	 * @return
	 */
	public static boolean getKeyUp(int keyCode)
	{
		return !getKey(keyCode) && _lastKeys[keyCode];
	}
	
	/**
	 * ������ ����� ���� ��� ����� ������� ��� ��� ����
	 * @param mouseButton
	 * @return
	 */
	public static boolean getMouse(int mouseButton)
	{
		return Mouse.isButtonDown(mouseButton);
	}
	
	/**
	 * ����� ������ ��� �� ���� ������� ��� ��� ���� ������
	 * @param mouseButton
	 * @return
	 */
	public static boolean getMouseDown(int mouseButton)
	{
		return getMouse(mouseButton) && !_lastMouse[mouseButton];
	}
	
	/**
	 * ������ ����� ���� ��� ����� ������� ��� ��� �����
	 * @param mouseButton
	 * @return
	 */
	public static boolean getMouseUp(int mouseButton)
	{
		return !getMouse(mouseButton) && _lastMouse[mouseButton];
	}
	
	/**
	 * ������ ������ �� ����� ����� ����
	 * @return
	 */
	public static Vector2f getMousePos()
	{
		return new Vector2f(Mouse.getX(), Mouse.getY());
	}
	
	/**
	 * ������ ����� ����� ������ �� ����� ������ ����
	 * @param position
	 */
	public static void setMousePos(Vector2f position)
	{
		Mouse.setCursorPosition((int)position.getX(), (int)position.getY());
	}
	
	/**
	 * ������ ����� ��� ���� �� ����� �� ����
	 * @param enabled
	 */
	public static void setCursor(boolean enabled)
	{
		Mouse.setGrabbed(!enabled);
	}
}
