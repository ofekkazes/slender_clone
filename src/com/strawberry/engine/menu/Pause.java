package com.strawberry.engine.menu;

//import com.strawberry.engine.rendering.RenderingEngine;

public class Pause extends Menu
{
	public Pause()
	{
		super();
		
	}
	protected void addEntries()
	{
		super.addEntry("You paused the game, to return hit me");
		super.addEntry("Exit to main menu");
	}
	public void update()
	{
		super.update();
		if(super._state == EntryState.Done)
		{
			switch(super._currentEntry)
			{
				case 0: returnToGame(); break;
				case 1: returnToMenu(); break;
			}
		}
	}
	private void returnToGame()
	{
		MenuManager.gameState = GameState.Playing;
		MenuManager.menuState = MenuState.StartGame;
	}
	private void returnToMenu()
	{
		MenuManager.gameState = GameState.MainMenu;
		MenuManager.menuState = MenuState.MainMenu;
	}
}
