package com.strawberry.engine.menu;

//import com.strawberry.engine.rendering.RenderingEngine;

public class MenuManager 
{
	public static GameState gameState;
	public static MenuState menuState;
	
	private static MainMenu mainMenu;
	private static Options options;
	private static Instructions instructions;
	private static Loading loading;
	private static Pause pause;

	/**
	 * צריך רק מנהל תפריטים אחד ולכן הוא סטטי וזאת מטודת האתחול
	 */
	public static void init()
	{
		gameState = GameState.MainMenu;
		menuState = MenuState.MainMenu;
		
		mainMenu = new MainMenu();
		options = new Options();
		instructions = new Instructions();
		loading = new Loading();
		pause = new Pause();
	}
	
	/**
	 * מטודת העדכון של המנהל התפריטים
	 */
	public static void update()
	{
		//RenderingEngine.clrScreen();
		
		if (MenuManager.gameState == GameState.MainMenu && MenuManager.menuState == MenuState.MainMenu)
            mainMenu.update();
		if (MenuManager.menuState == MenuState.Options && MenuManager.gameState == GameState.MainMenu)
            options.update();
		if(MenuManager.menuState == MenuState.Instructions && gameState == GameState.MainMenu)
			instructions.update();
		if(MenuManager.menuState == MenuState.StartGame && gameState == GameState.Loading)
			loading.update();
		if(MenuManager.gameState == GameState.Paused && MenuManager.menuState == MenuState.StartGame)
			pause.update();
	}
	
	/**
	 * מטודת הציור של כל התפריטים
	 */
	public static void render()
	{
		if (MenuManager.gameState == GameState.MainMenu && MenuManager.menuState == MenuState.MainMenu)
            mainMenu.render();
		if (MenuManager.menuState == MenuState.Options && MenuManager.gameState == GameState.MainMenu)
            options.render();
		if(MenuManager.menuState == MenuState.Instructions && gameState == GameState.MainMenu)
			instructions.render();
		if(MenuManager.menuState == MenuState.StartGame && gameState == GameState.Loading)
			loading.render();
		if(MenuManager.gameState == GameState.Paused && MenuManager.menuState == MenuState.StartGame)
			pause.render();
	}
	
}

