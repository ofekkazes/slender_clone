package com.strawberry.game;

import java.util.Random;

import com.strawberry.engine.component.MeshRenderer;
import com.strawberry.engine.core.GameObject;
import com.strawberry.engine.core.Quaternion;
import com.strawberry.engine.core.Time;
import com.strawberry.engine.core.Vector3f;
import com.strawberry.engine.menu.GameState;
import com.strawberry.engine.menu.MenuManager;
import com.strawberry.engine.rendering.Material;
import com.strawberry.engine.rendering.Mesh;
import com.strawberry.engine.rendering.Texture;

/**
 * מחלקה סטטית כי יש רק אויב אחד
 * @author LapKazes
 *
 */
public class Slender 
{
	private static GameObject object; //האובייקט שעליו יישבו כל האפשרויות
	private static Material material; //סוג החומר והטקסטורה של הסלנדר
	
	private static int timer;//כל כמה שניות סלנדר ישתגר למיקום של השחקן
	private static boolean isVisible;//סלנדר ישתגר רק כשלא רואים אותו
	private static boolean isInRnage;//אם הסלנדר לא יותר מידי קרוב הוא ישתגר
	
	private static Random rnd; //(ראנדום)
	
	/**
	 * מטודת אתחול לסלנדר
	 */
	public static void init()
	{
		setObject(new GameObject());
		setMaterial(new Material());
		
		material.addTexture("diffuse", Texture.loadTextureID("slender.PNG"));
		material.addFloat("specularIntensity", 1f);
		material.addFloat("specularPower", 8f);
		
		object.addComponent(new MeshRenderer(new Mesh("Slenderman Model.obj"), material));
		object.getTransform().setPosition(new Vector3f(250, 10, 150));
		object.getTransform().setRotation(new Quaternion(new Vector3f(0, 1, 0), (float)Math.toRadians(-90)));
		object.getTransform().setScale(new Vector3f(0.03f, 0.03f, 0.03f));
		
		timer = 0;
		isVisible = false;
		isInRnage = false;
		
		rnd = new Random();
	}
	
	/**
	 * מטודת העדכון של הסלנדר
	 * @param difficulty דרגת קושי
	 * @param playerPosition מיקום השחקן
	 * @param playerRotation סיבוב השחקן
	 */
	public static void update(float difficulty, Vector3f playerPosition, Quaternion playerRotation)
	{
		float distance = Math.abs((-1) * (float) Math.pow(Slender.getObject().getTransform().getPosition().getX() - playerPosition.getCopy().getX(), 2) +
				 (float) Math.pow(Slender.getObject().getTransform().getPosition().getZ() - playerPosition.getCopy().getZ(), 2));

		timer = (int) Time.getTime() % 100;
		
		if((timer %  ((difficulty / 4 ) * 60)) == 0)
		{
			checkIfVisible();
			
			if(!isVisible())
			{
				//float distance = Slender.object.getTransform().getPosition().sub(playerPosition).max();
				
				if(distance > 7500)
				{
					System.out.println(distance);
					Slender.object.getTransform().setPosition(playerPosition.getCopy().sub(new Vector3f(rnd.nextInt(300) - 150,0,rnd.nextInt(300) - 150)));
					Slender.object.getTransform().getPosition().setY(10f);
					Vector3f positionToCheck = new Vector3f(playerPosition.getX(), Slender.object.getTransform().getPosition().getY(), playerPosition.getZ());
					//Slender.object.getTransform().setRotation(Slender.object.getTransform().getRotation().lookAt(positionToCheck, new Vector3f(0,1,0)));
				}
			}
		}
		
		//Write on screen 'Slender is near'
		if(distance < 4000) isInRnage = true;
		else isInRnage = false;
		
		//if(30 > distance /*|| (isInRange && STAYED TOO LONG)*/) MenuManager.gameState = GameState.Lost;
	}
	
	private static void checkIfVisible()
	{
		
	}

	public static GameObject getObject() { return object; }

	public static Material getMaterial() { return material;	}

	public static int getTimer() { return timer; }

	public static boolean isVisible() { return Slender.isVisible; }

	public static boolean isInRnage() { return Slender.isInRnage; }

	public static void setObject(GameObject object) { Slender.object = object; }

	public static void setMaterial(Material material) {	Slender.material = material; }

	public static void setTimer(int timer) { Slender.timer = timer; }

	public static void setVisible(boolean isVisible) {	Slender.isVisible = isVisible;	}

	public static void setInRnage(boolean isInRnage) {	Slender.isInRnage = isInRnage;	}
}
