package com.strawberry.engine.OLD;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Texture2d 
{
	private Texture texture;
	private Rectangle rectangle;
	
	/**
	 * ���������� ��������
	 * @param texture
	 * @param rectangle
	 */
	public Texture2d(Texture texture, Rectangle rectangle) 
	{
		this.texture = texture;
		this.rectangle = rectangle;
	}
	
	/**
	 * ���������� ����� �� �� ����� �������� ����� ����
	 * @param location
	 * @param rectangle
	 */
	public Texture2d(String location, Rectangle rectangle) 
	{
		this.texture = Texture2d.load2DTexture(location);
		this.rectangle = rectangle;
	}
	public Texture getTexture() {
		return texture;
	}
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	public Rectangle getRectangle() {
		return rectangle;
	}
	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}
	/**
	 * ����� ������ ������� �� ������
	 * @param filename �� �����
	 * @return
	 */
	public static Texture load2DTexture(String filename)
	{
		try {
			
			return TextureLoader.getTexture("PNG", new FileInputStream(new File("res/"+filename+"."+"png")));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
