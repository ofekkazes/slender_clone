package com.strawberry.engine.menu;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.TrueTypeFont;
/**
 * 
 * @author LapKazes
 *
 */
public class Entry
{
	private static int ID=0;
	public int id = 0;
	private static Vector2f startPos = new Vector2f(Display.getWidth() / 2, Display.getHeight() / 2);
	private static TrueTypeFont font;
	
	private Vector2f position;
	private String name;
	private boolean chosen;
	private org.newdawn.slick.Color color;
	private Rectangle rectangle;
	public static float fade;
	
	/**
	 * לכל תוית יש קונסטרקטור
	 * @param name
	 */
	public Entry(String name)
	{
		this.name = name;
		this.chosen = false;
		this.color = org.newdawn.slick.Color.gray;
		fade = 0;
		
		this.id = ID;
		ID++;
		
		this.position = new Vector2f(startPos.x, startPos.y + (this.id * 40));
		this.rectangle = new Rectangle((int)this.position.x, (int)this.position.y, font.getWidth(this.name), font.getHeight(this.name));
	}

	public static int getID() {
		return ID;
	}

	public static void setID(int iD) {
		ID = iD;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static Vector2f getStartPos() {
		return startPos;
	}

	public static void setStartPos(Vector2f startPos) {
		Entry.startPos = startPos;
	}

	public static TrueTypeFont getFont() {
		return font;
	}

	public static void setFont(TrueTypeFont font) {
		Entry.font = font;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isChosen() {
		return chosen;
	}

	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}

	public org.newdawn.slick.Color getColor() {
		return color;
	}

	public void setColor(org.newdawn.slick.Color white) {
		this.color = white;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

}
