package com.strawberry.game;

import java.util.ArrayList;
import java.util.Random;

import com.strawberry.engine.component.MeshRenderer;
import com.strawberry.engine.core.GameObject;
import com.strawberry.engine.core.Statics;
import com.strawberry.engine.core.Vector3f;
import com.strawberry.engine.rendering.Material;
import com.strawberry.engine.rendering.Mesh;
import com.strawberry.engine.rendering.Texture;

/**
 * מחלקה סטטית שמארגנת אובייקטים במשחק
 * @author LapKazes
 *
 */
public class Arrange 
{
	/**
	 * המטודה יוצרת ומארגנת עצים במשחק בצורה רנדומלית
	 * @param parent האב שעליו ישבו כל העצים
	 * @param terrainWidth רוחב המקום שעליו ניתן ליצור עצים
	 * @param terrainDepth העומק של המקום שעליו ניתן ליצור עצים
	 * @return רשימת עצים מסודרת
	 */
	public static ArrayList<GameObject> arrangeTrees(GameObject parent, int terrainWidth, int terrainDepth)
	{
		GameObject tree[][] = new GameObject[10][5];
		ArrayList<GameObject>trees = new ArrayList<GameObject>();
		Mesh treeModel = new Mesh("low poly tree.obj");
		
		Material treeMaterial = new Material();
		treeMaterial.addTexture("diffuse", Texture.loadTextureID("tree.jpg"));
		treeMaterial.addFloat("specularIntensity", 1f);
		treeMaterial.addFloat("specularPower", 8f);

		Random rnd = new Random();
		for (int i = 0; i < tree.length; i++) {
			for (int j = 0; j < tree[i].length; j++) 
			{
				tree[i][j] = new GameObject();
				tree[i][j].addComponent(new MeshRenderer(treeModel, treeMaterial));
				tree[i][j].getTransform().setScale(new Vector3f(1, 1, 1));
				tree[i][j].getTransform().setPosition(new Vector3f(rnd.nextInt(terrainWidth * 3) - terrainWidth, tree[i][j].getTransform().getScale().getY(), rnd.nextInt(terrainDepth * 3) - terrainDepth));
				if(tree[i][j].getTransform().getPosition().getX() >= -terrainWidth / 3 &&
				   tree[i][j].getTransform().getPosition().getX() <= terrainWidth / 3 &&
				   tree[i][j].getTransform().getPosition().getZ() >= -terrainDepth / 3 &&
				   tree[i][j].getTransform().getPosition().getZ() <= terrainDepth / 3)
				{
					Statics.clamp(tree[i][j].getTransform().getPosition().getX(), -terrainWidth / 2, terrainWidth / 2);
					Statics.clamp(tree[i][j].getTransform().getPosition().getZ(), -terrainDepth / 2, terrainDepth / 2);
					
					trees.add(tree[i][j]);
					parent.addChild(tree[i][j]);
				}
				else j--;
			}
		}
	
		return trees;
	}
}
