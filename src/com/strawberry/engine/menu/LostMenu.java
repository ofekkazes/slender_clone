package com.strawberry.engine.menu;

public class LostMenu extends Menu
{

	public LostMenu()
	{
		super();
		
	}
	protected void addEntries()
	{
		super.addEntry("You lost..");
		super.addEntry("Try again");
		super.addEntry("Go to Main Menu");
	}
	public void update()
	{
		super.update();
		if(super._state == EntryState.Done)
		{
			switch(super._currentEntry)
			{
				case 1: PlaySelected(); break;
				case 2: QuitSelected(); break;
			}
			//this.init();
		}
	}
	private void PlaySelected()
	{
		MenuManager.menuState = MenuState.StartGame;
		MenuManager.gameState = GameState.Loading;
	}
	private void QuitSelected()
	{
		MenuManager.menuState = MenuState.MainMenu;
		MenuManager.gameState = GameState.MainMenu;
	}
}
