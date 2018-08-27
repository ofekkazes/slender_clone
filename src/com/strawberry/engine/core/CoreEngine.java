package com.strawberry.engine.core;

import com.strawberry.engine.rendering.RenderingEngine;
import com.strawberry.engine.rendering.Window;

public class CoreEngine 
{
	private boolean _isRunning; //האם המנוע רץ
	private Game _game; //המשחק
	
	private int _width; //רוחב המסך ברזולוצייה
	private int _height; //גובה המסך ברזולוציה
	private double _frameTime; //הזמן שלוקח לכל פריים להתעדכן
	
	private RenderingEngine renderingEngine; //מנוע הציור

	/**
	 * פעולה בונה
	 * @param width רוחב
	 * @param height גובה
	 * @param frameRate כמות הפריימים הרצויה בשניה
	 * @param game משחק להפעלה
	 */
	public CoreEngine(int width, int height, double frameRate, Game game)
	{
		this._isRunning = false;
		this._width = width;
		this._height = height;
		this._game = game;
		this._frameTime = 1.0/frameRate;
		
	}
	
	/**
	 * מטודה היוצרת מסך, ומנוע ציור חדשים
	 */
	public void createWindow()
	{
		Window.createWindow(this._width, this._height, "Strawberry");
		System.out.println("OpenGL: "+RenderingEngine.getGlVersion());
		this.renderingEngine = new RenderingEngine();
		this.renderingEngine.initializeGL();
	}
	
	/**
	 * מטודה מתחילה להריץ את המנוע
	 */
	public void start()
	{
		if(this._isRunning)
			return;
		run();
	}
	
	/**
	 * מטודה המפסיקה את ריצת המנוע
	 */
	public void stop()
	{
		if(!this._isRunning)
			return;
		
		this._isRunning = false;
	}
	
	/**
	 * המטודה ה"ראשית" של המנוע, שמאתחלת את המשחק, מריצה את הלולאה הראשית, מעדכנת ומציירת את המשחק
	 */
	private void run()
	{
		this._game.init();
		
		this._isRunning = true;
		
		int frames = 0;
		long frameCounter = 0;

		double lastTime = Time.getTime();
		double unprocessedTime = 0;
		
		while(this._isRunning)
		{
			boolean render = false;
			
			double startTime = Time.getTime();
			double passedTime = startTime-lastTime;
			lastTime = startTime;
			
			unprocessedTime += passedTime;
			frameCounter += passedTime;
			
			while(unprocessedTime > _frameTime)
			{
				render = true;
				unprocessedTime -= _frameTime;
				
				if(Window.isCloseRequested())
					stop();
				
				this._game.input((float)_frameTime);
				Input.update();
				this._game.update((float)_frameTime);
				
				if(frameCounter >= 1.0)
				{
					System.out.println(frames);
					frames = 0;
					frameCounter = 0;
				}
			}
			if(render)
			{
				_game.render(renderingEngine);
				Window.render();
				frames++;
			}
			else
			{
				try 
				{
					Thread.sleep(1);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		}
		this._game.cleanUp();
		cleanUp();
	}
	
	/**
	 * המטודה מנקה ומוחקת את כל המידע
	 */
	public static void cleanUp()
	{
		Window.dispose();
		
		System.exit(0);
	}
}
