package com.strawberry.engine.menu;

import com.strawberry.engine.core.CoreEngine;

public class MainMenu extends Menu
{

	public MainMenu()
	{
		super();
		
	}
	protected void addEntries()
	{
		super.addEntry("Play Story");
		super.addEntry("Instructions");
		super.addEntry("Options");
		super.addEntry("Quit");
	}
	public void update()
	{
		super.update();
		if(super._state == EntryState.Done)
		{
			switch(super._currentEntry)
			{
				case 0: PlaySelected(); break;
				case 1: InstructionsSelected(); break;
				case 2: OptionsSelected(); break;
				case 3: QuitSelected(); break;
			}
			//this.init();
		}
	}
	private void PlaySelected()
	{
		MenuManager.menuState = MenuState.StartGame;
		MenuManager.gameState = GameState.Paused;
	}
	private void InstructionsSelected()
	{
		MenuManager.menuState = MenuState.Instructions;
	}
	private void OptionsSelected()
	{
		MenuManager.menuState = MenuState.Options;
	}
	private void QuitSelected()
	{
		CoreEngine.cleanUp();
	}
}
