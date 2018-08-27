package com.strawberry.engine.menu;

import com.strawberry.engine.rendering.RenderingEngine;


public class Loading extends Menu
{
	int i=0;
	public Loading()
	{
		super();
		this.disableInput();
		
	}
	protected void addEntries()
	{
		super.addEntry("Loading...");	
	}
	public void update()
	{
		super.update();
		//if(super.state == EntryState.Done)
		//{
			if(++this.i > 500)
			{
				startGame();
				this.i = 0;
			}
			if(this._state == EntryState.Done)
			{
				MenuManager.menuState = MenuState.StartGame;
				MenuManager.gameState = GameState.Playing;
				
			}
		//}
	}
	private void startGame()
	{
		//LevelManager.init();
		this._state = EntryState.Finishing;
	}
}
