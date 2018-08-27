package com.strawberry.engine.core;

import java.util.ArrayList;

public class Statics 
{
	/**
	 * ������ ����� ���� ������� ������ �� ������� ���� ��� ����
	 * @param data ���� �������
	 * @return
	 */
	public static String[] removeEmptyStrings(String[] data)
	{
		ArrayList<String> result = new ArrayList<String>();
		for(int i=0; i<data.length; i++)
		{
			if(!data[i].equals(""))
			{
				result.add(data[i]);
			}
		}
		String[] res = new String[result.size()];
		result.toArray(res);
		
		return res;
	}
	
	/**
	 * ������ ����� ���� ���� Integer ������� ���� ������ �����
	 * @param integers ����
	 * @return
	 */
	public static int[] convertToIntArray(Integer[] integers)
	{
	    int[] ret = new int[integers.length];
	    for (int i = 0; i < integers.length; i++)
	    {
	        ret[i] = integers[i].intValue();
	    }
	    return ret;
	}
	
	/**
	 * ����� ������� ���� ������� �������� ���� �����
	 * @param value ���� ����� ����� �����
	 * @param min ���� �������
	 * @param max ���� �������
	 * @return
	 */
	public static float clamp(float value, float min, float max) 
	{
	    return Math.max(min, Math.min(max, value));
	}
}
