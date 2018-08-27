package com.strawberry.engine.menu;

public class Options extends Menu
{
	
	public Options()
	{
		super();
	}
	protected void addEntries()
	{
		super.addEntry("Blah");
		super.addEntry("Blah");
		super.addEntry("Blah");
		super.addEntry("Back");
	}
	public void update()
	{
		super.update();
		if(super._state == EntryState.Done)
		{
			switch(super._currentEntry)
			{
				case 3: BackSelected(); break;
			}
			//this.init();
		}
	}
	private void BackSelected()
	{
		MenuManager.menuState = MenuState.MainMenu;
	}
	
}
