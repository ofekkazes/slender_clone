package com.strawberry.engine.core;

import java.nio.ByteBuffer;

/**
 * מחלקת הטקסטורה המשנית
 * @author LapKazes
 *
 */
public class TextureData 
{
	private int _width;
	private int _height;
	private ByteBuffer _buffer;
	
	public TextureData(ByteBuffer buffer, int width, int height)
	{
		this._buffer = buffer;
		this._height = height;
		this._width = width;
	}

	public int getWidth() {	return _width; }

	public int getHeight() { return _height; }

	public ByteBuffer getBuffer() {	return _buffer; }

	public void setWidth(int width) { this._width = width; }

	public void setHeight(int height) {	this._height = height; }

	public void setBuffer(ByteBuffer buffer) { this._buffer = buffer; }
}
