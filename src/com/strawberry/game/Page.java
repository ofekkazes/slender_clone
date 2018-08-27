package com.strawberry.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.strawberry.engine.component.MeshRenderer;
import com.strawberry.engine.core.GameObject;
import com.strawberry.engine.physics.BoundingSphere;
import com.strawberry.engine.rendering.Material;
import com.strawberry.engine.rendering.Mesh;
import com.strawberry.engine.rendering.Texture;

/**
 * במשחק סלנדר נדרש למצוא 8 דפים כדי לנצח במשחק
 * @author LapKazes
 *
 */
public class Page 
{
	private static List<GameObject> _pages;
	private static Material _material;
	private static Mesh _model;
	
	/**
	 * מחלקה זאת סטטית, זאת מטודת האתחול של המחלקה
	 */
	public static void init(ArrayList<GameObject> trees)
	{
		_pages = new ArrayList<GameObject>();
		_material = new Material();
		_material.addFloat("specularIntensity", 1f);
		_material.addFloat("specularPower", 8f);
		_material.addTexture("diffuse", Texture.loadTextureID("forestgrass1.jpg"));
		_model = new Mesh("box.obj");
		
		initLocations(trees);
	}
	
	/**
	 * מאתחל את המקומות האפשריים להנחת דף
	 */
	private static void initLocations(ArrayList<GameObject> trees)
	{
		Random rnd = new Random();
		for (int i = 0; i < 4; i++) 
		{
			/*int index = rnd.nextInt(trees.size());
			GameObject tree = trees.get(index);
			_pages.add(new GameObject());
			_pages.get(i).addComponent(new MeshRenderer(_model, _material));
			_pages.get(i).getTransform().getPosition().setY(8);
			_pages.get(i).getTransform().getPosition().setX(tree.getTransform().getTransformedPosition().getCopy().getX());
			_pages.get(i).getTransform().getPosition().setZ(tree.getTransform().getTransformedPosition().getCopy().getZ());				
			_pages.get(i).getTransform().setScale(_pages.get(i).getTransform().getScale().mul(10f));
			
			trees.get(index).addChild(_pages.get(i));
			*/
			
			int index = rnd.nextInt(trees.size());
			GameObject tree = trees.get(index);
			
			GameObject page = new GameObject();
			page.addComponent(new MeshRenderer(_model, _material));
			page.getTransform().getPosition().set(tree.getTransform().getPosition().getX(), 8f, tree.getTransform().getPosition().getZ());
			page.getTransform().setScale(page.getTransform().getScale().mul(5f));
			_pages.add(page);
			trees.get(index).addChild(page);
			System.out.println("page " + i + " is added at " + _pages.get(i).getTransform().getPosition());
		}
	}
	
	public static void checkCollisions(BoundingSphere playerSphere)
	{
		for (int i = 0; i < _pages.size(); i++) 
		{
			BoundingSphere bs = new BoundingSphere(_pages.get(i).getTransform().getPosition(), 5f);
			
			if(bs.IntersectBoundingSphere(playerSphere).isIntersect())
			{
				removePage(_pages.get(i));
			}
		}
	}
	
	/**
	 * מחיקת דף חדש
	 * @param m Page Model
	 */
	public static boolean removePage(GameObject m)
	{
		return _pages.remove(m);
	}
	
	public static int getLength() { return _pages.size(); }
	
}
