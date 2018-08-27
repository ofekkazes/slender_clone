package com.strawberry.engine.menu;

import java.awt.Font;
import java.util.List;
import java.util.Vector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import com.strawberry.engine.OLD.Statics;
import com.strawberry.engine.core.Input;
import com.strawberry.engine.rendering.Window;

enum EntryState
{
	Begin,
	Operational,
	Finishing,
	Done
}

/**
 * לכל דף שונה יש תפריט שונה ולכן לכל תפריט יש מחלקה שונה
 * @author LapKazes
 *
 */
public abstract class Menu 
{
	private List<Entry> _entries;
	protected int _currentEntry;
	protected EntryState _state = EntryState.Begin;
	private float _fade = 0;
	private boolean _allowInput;
	public Menu()
	{
		init();
	}
	
	/**
	 * אתחול המחלקה
	 */
	protected void init()
	{
		this._entries = new Vector<Entry>();
		Entry.setFont(Menu.loadFont("Calibri", Font.ITALIC, 24));
		this._currentEntry = 0;
		this._state = EntryState.Begin;
		addEntries();
		this._allowInput = true;
	}
	
	/**
	 * למקרה שקלט לא נדרש
	 */
	protected void disableInput()
	{
		this._allowInput = false;
	}
	
	/**
	 * המטודה מוסיפה תוית חדשה
	 * @param label
	 */
	protected void addEntry(String label)
	{
		if(this._entries.size() == 0)
			Entry.setID(0);
		this._entries.add(new Entry(label));
	}
	
	/**
	 * המטודה קוראת לכל מי שיורש אותה להוסיף תויות
	 */
	protected abstract void addEntries();
	
	/**
	 * המטודה מטפלת בקלט התפריט
	 */
	private void getInput()
	{
		while(Keyboard.next())
		{
			if(Keyboard.getEventKeyState())
			{
				if(Keyboard.getEventKey() == Keyboard.KEY_UP)
				{
					this._currentEntry--;
					if(0>this._currentEntry) this._currentEntry = this._entries.size()-1;
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_DOWN)
				{
					this._currentEntry++;
					if(this._currentEntry > this._entries.size() - 1) this._currentEntry = 0;
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_RETURN)
				{
					this._state = EntryState.Finishing;
				}
			}
		}
		int x = Mouse.getX();
		int y = Window.getHeight() - Mouse.getY();
		Rectangle mouseRec = new Rectangle(x, y, 1, 1);
		for(Entry e : this._entries)
		{
			if(e.getRectangle().contains(mouseRec))
				this._currentEntry = e.getId();
			//System.out.println(mouseRec);
		}
		if(Input.getMouseDown(1))
		{
			this._state = EntryState.Finishing;
			
		}
	}
	
	/**
	 * מטודת העדכון של התפריט
	 */
	public void update()
	{
		if(this._entries.size() != 0)
		{
			for(int i = 0; i<this._entries.size(); i++)
			{
				if(this._entries.get(i).id == this._currentEntry)
				{
					this._entries.get(i).setChosen(true);
					this._entries.get(i).setColor(new Color(.65f,  .124f,  .132f,  1f));
				}
				else
				{
					this._entries.get(i).setChosen(false);
					this._entries.get(i).setColor(Color.gray);
				}
			}
			switch(this._state)
			{
			case Begin: 
				if(90 > this._fade) this._fade += 1f;
				else this._state = EntryState.Operational;
				break;
			case Operational:
				if(this._allowInput) this.getInput();
				
				break;
			case Finishing:
				if(this._fade > 0) this._fade -= 1f;
				else this._state = EntryState.Done;
				break;
			case Done:this.init();
				break;
			}
		}
	}
	
	/**
	 * מטודת הציור של התפריט על המסך
	 */
	public void render()
	{
		for(Entry e : this._entries)
		{
			e.setColor(new Color(e.getColor().r, e.getColor().g, e.getColor().b, (float) Math.sin(Math.toRadians(_fade))));
			Statics.renderText(Entry.getFont(), e.getName(), e.getPosition(), e.getColor());
		}
	}
	
	/**
	 * מטודה הטוענת פונט בדרך שונה
	 * @param name שם הפונט
	 * @param attribute סטייל
	 * @param size גודל הפונט
	 * @return
	 */
	public static TrueTypeFont loadFont(String name, int attribute,  int size)
	{
		Font awtFont = new Font(name, attribute, size);
		return new TrueTypeFont(awtFont, true);
	}
}