package com.strawberry.engine.core;

/**
 * ����� ���
 * @author LapKazes
 *
 */
public class Time 
{
	private static final long SECOND = 1000000000l;

	/**
	 * ������ ������ ��� ����� ��� ������ �����
	 * @return
	 */
	public static double getTime()
	{
		return (double)System.nanoTime() / (double)SECOND;
	}
}
