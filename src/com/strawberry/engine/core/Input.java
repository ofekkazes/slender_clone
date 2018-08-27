package com.strawberry.engine.core;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 * מחלקה ששולטת ומטפלת בקלט של השחקן
 * @author LapKazes
 *
 */
public class Input 
{
	public static final int NUM_KEYCODES = 256; //מקשי המקלדת
	public static final int NUM_MOUSEBUTTONS = 5; //מקשי העכבר
	
	private static boolean[] _lastKeys = new boolean[NUM_KEYCODES]; //המקשים מהפריים הקודם
	private static boolean[] _lastMouse = new boolean[NUM_MOUSEBUTTONS]; //מקשי העכבר מהפריים הקודם
	
	/**
	 * המטודה מעדכנת את רשימות העכבר והמקלדת עם המקשים שנלחצים בכל פריים
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
	 * המטודה מקבלת מספר של מקש במקלדת ומחזירה האם הוא נלחץ
	 * @param keyCode
	 * @return
	 */
	public static boolean getKey(int keyCode)
	{
		return Keyboard.isKeyDown(keyCode);
	}
	
	/**
	 * מטודה המקבלת מספר של מקש במקלדת ומחזירה האם הוא נלחץ ושוחרר כמו בלחיצה אמיתית
	 * @param keyCode
	 * @return
	 */
	public static boolean getKeyDown(int keyCode)
	{
		return getKey(keyCode) && !_lastKeys[keyCode];
	}
	
	/**
	 * מטודה המקבלת מספר מקש במקלדת ומחזירה האם אותו מקש שוחרר מלחיצה
	 * @param keyCode
	 * @return
	 */
	public static boolean getKeyUp(int keyCode)
	{
		return !getKey(keyCode) && _lastKeys[keyCode];
	}
	
	/**
	 * המטודה מקבלת מספר מקש בעכבר ומחזירה האם הוא נלחץ
	 * @param mouseButton
	 * @return
	 */
	public static boolean getMouse(int mouseButton)
	{
		return Mouse.isButtonDown(mouseButton);
	}
	
	/**
	 * מטודה המקבלת מקש של עכבר ומחזירה האם הוא נלחץ ושוחרר
	 * @param mouseButton
	 * @return
	 */
	public static boolean getMouseDown(int mouseButton)
	{
		return getMouse(mouseButton) && !_lastMouse[mouseButton];
	}
	
	/**
	 * המטודה מקבלת מספר מקש בעכבר ומחזירה האם הוא שוחרר
	 * @param mouseButton
	 * @return
	 */
	public static boolean getMouseUp(int mouseButton)
	{
		return !getMouse(mouseButton) && _lastMouse[mouseButton];
	}
	
	/**
	 * המטודה מחזירה את מיקום העכבר במסך
	 * @return
	 */
	public static Vector2f getMousePos()
	{
		return new Vector2f(Mouse.getX(), Mouse.getY());
	}
	
	/**
	 * המטודה מקבלת מיקום ומזיזה את העכבר למיקום במסך
	 * @param position
	 */
	public static void setMousePos(Vector2f position)
	{
		Mouse.setCursorPosition((int)position.getX(), (int)position.getY());
	}
	
	/**
	 * המטודה קובעת האם יראו את העכבר על המסך
	 * @param enabled
	 */
	public static void setCursor(boolean enabled)
	{
		Mouse.setGrabbed(!enabled);
	}
}
