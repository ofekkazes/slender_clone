package com.strawberry.engine.core;

/**
 * מחלקת זמן
 * @author LapKazes
 *
 */
public class Time 
{
	private static final long SECOND = 1000000000l;

	/**
	 * המטודה מחזירה זמן שעובר מאז שהמשחק התחיל
	 * @return
	 */
	public static double getTime()
	{
		return (double)System.nanoTime() / (double)SECOND;
	}
}
