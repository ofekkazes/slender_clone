package com.strawberry.engine.core;

import java.util.ArrayList;

public class Statics 
{
	/**
	 * המטודה מקבלת מערך מחרוזות ומוחקת את המקומות שאין בהם כלום
	 * @param data מערך מחרוזות
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
	 * המטודה מקבלת מערך מסוג Integer ומחזירה אותו לשימוש בסיסי
	 * @param integers מערך
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
	 * מטודה שמגדירה גבול מקסימום ומינימום לערך כלשהו
	 * @param value הערך שאותו רוצים לבדוק
	 * @param min גבול מינימלי
	 * @param max גבול מקסימלי
	 * @return
	 */
	public static float clamp(float value, float min, float max) 
	{
	    return Math.max(min, Math.min(max, value));
	}
}
