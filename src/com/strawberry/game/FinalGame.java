package com.strawberry.game;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.strawberry.engine.component.Camera;
import com.strawberry.engine.component.MeshRenderer;
//import com.strawberry.engine.component.PhysicsEngineComponent;
//import com.strawberry.engine.component.PhysicsObjectComponent;
import com.strawberry.engine.component.SpotLight;
//import com.strawberry.engine.component.TerrainRenderer;
import com.strawberry.engine.core.Game;
import com.strawberry.engine.core.GameObject;
import com.strawberry.engine.core.Input;
import com.strawberry.engine.core.Statics;
import com.strawberry.engine.core.Vector2f;
import com.strawberry.engine.core.Vector3f;
import com.strawberry.engine.menu.MenuManager;
import com.strawberry.engine.physics.BoundingSphere;
import com.strawberry.engine.physics.IntersectData;
import com.strawberry.engine.physics.PhysicsEngine;
//import com.strawberry.engine.physics.PhysicsObject;
import com.strawberry.engine.rendering.Material;
import com.strawberry.engine.rendering.Mesh;
import com.strawberry.engine.rendering.Texture;
import com.strawberry.engine.rendering.Vertex;
import com.strawberry.engine.rendering.Window;

public class FinalGame extends Game
{
	PhysicsEngine physicsEngine;
	
	private GameObject cameraObject;
	private Camera camera;

	private SpotLight flashlightComponent;
	private GameObject flashLight;
	private boolean isFlashOn;
	private float flashIntensity;
	
	private GameObject terrain;
	private Material forestGrass;
	private float terrainDepth = 1000.0f;
	private float terrainWidth = 1000.0f;

	private ArrayList<GameObject> trees;
	
	private int difficulty;
	
	float temp = 0;
	

	/**
	 * הקונסטרקטור של המשחק
	 */
	public FinalGame()
	{
		this.physicsEngine = new PhysicsEngine();
		
		this.camera = new Camera((float)Math.toRadians(40f), Window.getWidth() / Window.getHeight(), 0.01f, 1000f);
		this.cameraObject = new GameObject().addComponent(camera);
		
		this.isFlashOn = true;
		this.flashLight = new GameObject();
		
		this.terrain = new GameObject();
		this.forestGrass = new Material();

		this.trees = new ArrayList<GameObject>();
	}
	
	public void init()
	{
		MenuManager.init();
		this.difficulty = 1;
		
		this.cameraObject.getTransform().setPosition(new Vector3f(0, 4, 0));
			
		this.flashlightComponent = new SpotLight(new Vector3f(0.2f, 0.2f, 0.2f), 1.4f,
				new Vector3f(0, 0, 0.1f), //Attenuation
				0.9f);
		this.flashIntensity = this.flashlightComponent.getIntensity();
		this.flashLight.addComponent(flashlightComponent);
		this.flashLight.getTransform().getPosition().setY(this.flashLight.getTransform().getPosition().getY() - 6);
		this.flashLight.getTransform().getPosition().setZ(this.flashLight.getTransform().getPosition().getZ() + 14);
		
		this.forestGrass.addTexture("diffuse", Texture.loadTextureID("forestgrass1.jpg"));
		this.forestGrass.addFloat("specularIntensity", 0.5f);
		this.forestGrass.addFloat("specularPower", 6f);
		
		this.terrainDepth = 1000.0f;
		this.terrainWidth = 1000.0f;
		Vertex[] terrainVertices = new Vertex[] {new Vertex(new Vector3f(-terrainWidth, 0.0f, -terrainDepth), new Vector2f(0,0)),
				  						  new Vertex(new Vector3f(-terrainWidth, 0.0f, terrainDepth * 3), new Vector2f(0.0f, 1.0f)),
				  						  new Vertex(new Vector3f(terrainWidth * 3, 0.0f, -terrainDepth), new Vector2f(1.0f, 0)),
				  						  new Vertex(new Vector3f(terrainWidth * 3, 0.0f, terrainDepth * 3), new Vector2f(1.0f,1.0f))};
		int[] terrainIndices = new int[] {0, 1, 2,
								   		  2, 1, 3};
		
		this.terrain.addComponent(new MeshRenderer(new Mesh(terrainVertices, terrainIndices), forestGrass));
		this.terrain.setImportant(true);
		this.addToRoot(this.terrain);
		
		this.trees = Arrange.arrangeTrees(this.terrain, (int)terrainWidth, (int)terrainDepth);
		System.out.println(this.trees.size());
		Page.init(this.trees);
		
		Slender.init();
		this.terrain.addChild(Slender.getObject());
		
		this.terrain.addChild(cameraObject);
		this.cameraObject.addChild(flashLight);
		
		/*TODO: 5. Add pages model and textures
		 *TODO: 6. Page collection & Difficulty
		 *TODO: 9. Win/Lose state + appropriate screen
		 *
		 *TODO: Finish SkyBox 
		 *TODO: Finish physics engine
		 *
		 *TODO: When finish designing make ambient light Vector3f.ZERO so the scene will be pitch black 
		 */
	}

	
	public void input(float delta)
	{
		super.input(delta);
		
		if(Input.getKeyDown(Keyboard.KEY_F))
		{
			this.isFlashOn = !this.isFlashOn;
			if(this.isFlashOn) this.flashlightComponent.setIntensity(flashIntensity);
			else this.flashlightComponent.setIntensity(-4f);
		}
		
	}

	
	public void update(float delta)
	{
		super.update(delta);
		
		cameraObject.getTransform().getPosition().setY(8);
		//camShpere.setVelocity(camera.getMovementVector());
		//camShpere.setPosition(cameraObject.getTransform().getPosition());
		
		Slender.update(this.difficulty, cameraObject.getTransform().getPosition(), cameraObject.getTransform().getRotation());
		if(this.isFlashOn)
		{
			if(this.flashlightComponent.getIntensity() > -1)
			{
				this.flashlightComponent.setIntensity(this.flashlightComponent.getIntensity() - delta/100);
				this.flashIntensity = this.flashlightComponent.getIntensity();
			}
			else this.flashlightComponent.setIntensity(-4);
		}
		Vector3f collisionVector = new Vector3f(1f, 1f, 1f);
		
				
		//if(Page.getLength() == 0) //GOTO: winState
			doNothing();
		if(true)
			//Waited too much || Slender is near you for too long (depending on difficulty)
			//GOTO: StaticScreen and from there loseState
			doNothing();
		
		for(GameObject tree: this.trees)
		{
			Vector3f center = new Vector3f(tree.getTransform().getPosition().getX() + 9, 0, tree.getTransform().getPosition().getZ() + 6);
			BoundingSphere bs = new BoundingSphere(center, 9f);
			IntersectData id = this.camera.getBoundingSphere().IntersectBoundingSphere(bs);
			if(id.isIntersect())
				collisionVector= id.getDirection();
			//System.out.println(id.isIntersect());	
		}
		//System.out.println(camera.getTransform().getPosition());
		camera.setCollisionVector(collisionVector);
		//Borders
		this.cameraObject.getTransform().getPosition().setX(Statics.clamp(
				this.cameraObject.getTransform().getPosition().getX(), 
				-this.terrainWidth / 2, this.terrainWidth / 2));
		this.cameraObject.getTransform().getPosition().setZ(Statics.clamp(
				this.cameraObject.getTransform().getPosition().getZ(), 
				-this.terrainDepth / 2, this.terrainDepth / 2));
	}
	
	/**
	 * Does Nothing
	 */
	public void doNothing()
	{
		
	}
	
	@Override
	public void cleanUp()
	{
		
		super.cleanUp();
	}
}
