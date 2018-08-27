package com.strawberry.game;

import com.strawberry.engine.core.CoreEngine;

public class Main 
{
	/**
	 * מטודה ראשית
	 * @param args
	 */
	public static void main(String[]args)
	{
		CoreEngine engine = new CoreEngine(800, 600, 60, new FinalGame());
		engine.createWindow();
		engine.start();
	}
}
