package com.strawberry.engine.core;


//import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
//import org.lwjgl.opengl.Display;

import org.lwjgl.opengl.GL11;

import com.strawberry.engine.menu.GameState;
import com.strawberry.engine.menu.MenuManager;
import com.strawberry.engine.menu.MenuState;
import com.strawberry.engine.rendering.RenderingEngine;

/**
 * מחלקה אבסטרקטית למשחק שבה יש את הגרף הראשי שייצויר (ותחתיו הבנים שלו)
 * @author LapKazes
 *
 */
public abstract class Game
{
	private GameObject _root = new GameObject(); //הגרף הראשי
	
	/**
	 * מטודה אבסטרקטית המיוצגת כדי לאתחל את המשחק
	 */
	public abstract void init();
	
	/**
	 * קלט מהמשתמש
	 */
	public void input(float delta)
	{
		if(MenuManager.gameState == GameState.Playing && MenuManager.menuState == MenuState.StartGame)
		{
			getRootObject().input(delta);
			
			if(Input.getKeyDown(Keyboard.KEY_P | Keyboard.KEY_ESCAPE))
			{
				MenuManager.gameState = GameState.Paused;
				MenuManager.menuState = MenuState.StartGame;
				
			}
		}
			
	}
	
	/**
	 * מטודת העדכון של המשחק
	 */
	public void update(float delta)
	{
		if(MenuManager.gameState == GameState.Playing && MenuManager.menuState == MenuState.StartGame)
		{
			if(!_root.isImportant())
				_root.setImportant(true);
			getRootObject().update(delta);
		}
		MenuManager.update();
	}
	
	/**
	 * מטודת ציור הגרף הראשי על המסך
	 * @param engine מנוע הציור שיושם במטודה
	 */
	public void render(RenderingEngine engine)
	{
		RenderingEngine.clrScreen();
		
		engine.initializeGL3D();
		if(MenuManager.gameState == GameState.Playing && MenuManager.menuState == MenuState.StartGame)
			engine.render(getRootObject());
		
		engine.initializeGL2D();
		MenuManager.render();
		GL11.glColor3f(1.0f, 0.0f, 0.0f);     // set color to red
        drawQuad(5.0f, 5.0f, 100.0f, 80.0f);  // draw quad
        GL11.glColor3f(1.0f, 1.0f, 1.0f);     // reset color
		
	}
	public void drawQuad(float x, float y, float width, float height) {
	    // Draws a quad like this:
	    //
	    //    height
	    //       |
	    //       v________
	    //       |        |
	    //       |        |
	    //       |        |
	    //       |        |
	    //       |________|<--width
	    //     xy
	    //
	   
	    GL11.glBegin(GL11.GL_QUADS);

	    GL11.glTexCoord2f(0, 0); GL11.glVertex2f(x, y);
	    GL11.glTexCoord2f(1, 0); GL11.glVertex2f(x + width, y);
	    GL11.glTexCoord2f(1, 1); GL11.glVertex2f(x + width, y + height);
	    GL11.glTexCoord2f(0, 1); GL11.glVertex2f(x, y + height);

	    GL11.glEnd();
	}
	
	/**
	 * מטודה זו מוחקת את הגרף שנמצא בגרף הראשי, כדי להקל על סגירת המשחק
	 */
	public void cleanUp()
	{
		_root = null;
	}
	
	/**
	 * מטודה זו מוסיפה לגרף הראשי אובייקט (ילד)
	 * @param object הילד
	 */
	public void addToRoot(GameObject object)
	{
		getRootObject().addChild(object);
	}
	
	/**
	 * מטודה זו מחזירה את הגרף הראשי
	 * @return
	 */
	private GameObject getRootObject() { return this._root; }
}