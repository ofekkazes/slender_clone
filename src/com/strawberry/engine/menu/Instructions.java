package com.strawberry.engine.menu;

public class Instructions extends Menu
{
	public Instructions()
	{
		super();
	}
	protected void addEntries()
	{
		super.addEntry("You are alone in the forest..");
		super.addEntry("       the slender is chasing you");
		super.addEntry("");
		super.addEntry("Use the arrow keys and WASD to collect");
		super.addEntry("all 8 pages and escape the slender.");
		super.addEntry("");
		super.addEntry("Back");
	}
	public void update()
	{
		super.update();
		if(super._state == EntryState.Done)
		{
			switch(super._currentEntry)
			{
				case 6: BackSelected(); break;
			}
			//this.init();
		}
	}
	private void BackSelected()
	{
		MenuManager.menuState = MenuState.MainMenu;
	}
}
