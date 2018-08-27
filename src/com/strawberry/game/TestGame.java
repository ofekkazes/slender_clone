package com.strawberry.game;

import com.strawberry.engine.component.Camera;
import com.strawberry.engine.component.DirectionalLight;
import com.strawberry.engine.component.MeshRenderer;
import com.strawberry.engine.component.PointLight;
import com.strawberry.engine.component.SpotLight;
import com.strawberry.engine.core.Game;
import com.strawberry.engine.core.GameObject;
import com.strawberry.engine.core.Quaternion;
import com.strawberry.engine.core.Vector2f;
import com.strawberry.engine.core.Vector3f;
import com.strawberry.engine.rendering.Material;
import com.strawberry.engine.rendering.Mesh;
import com.strawberry.engine.rendering.Texture;
import com.strawberry.engine.rendering.Vertex;
import com.strawberry.engine.rendering.Window;

public class TestGame extends Game
{
	private GameObject planeObject;
	
	MeshRenderer meshRenderer;
	
	GameObject flashLight;
	SpotLight flashlightComponent;
	/**
	 * הקונסטרקטור של המשחק
	 */
	public TestGame()
	{
		this.flashLight = new GameObject();
		//init();
	}
	
	public void init()
	{
		this.flashlightComponent = new SpotLight(Vector3f.ZERO, 1.4f,
				new Vector3f(0, 0, 0.1f), //Attenuation
				0.9f);
		this.flashLight.addComponent(flashlightComponent);
		//this.flashLight.getTransform().getPosition().setY(this.flashLight.getTransform().getPosition().getY() - 6);
		this.flashLight.getTransform().getPosition().setZ(this.flashLight.getTransform().getPosition().getZ() + 14);

		
		Material material = new Material(); //new Material(ResourceLoader.loadTextureID("java.jpg"), new Vector3f(1,  1, 1));
		material.addTexture("diffuse", Texture.loadTextureID("java.jpg"));
		material.addFloat("specularIntensity", 1f); //the power of the reflection of the light
		material.addFloat("specularPower", 8f); //the amount of reflection of light
		addToRoot(new GameObject().addComponent(new Camera((float)Math.toRadians(70.0f), Window.getWidth() / Window.getHeight(), 0.01f, 1000f)).addChild(flashLight));

		float fieldDepth = 100.0f;
		float fieldWidth = 100.0f;
		Vertex[] vertices = new Vertex[] {new Vertex(new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0,0)),
				  						  new Vertex(new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
				  						  new Vertex(new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0)),
				  						  new Vertex(new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f,1.0f))};
		int[] indices = new int[] {0, 1, 2,
								   2, 1, 3};
		Mesh mesh = new Mesh();
		mesh.addVertices(vertices, indices, true);
		meshRenderer = new MeshRenderer(mesh, material);
		
		//mesh = Mesh.loadMesh("Slenderman Model.obj");
	
		//meshRenderer = new MeshRenderer(mesh, material);
		//root.addComponent(meshRenderer);
		
		Mesh test = new Mesh("Slenderman Model.obj");
		GameObject testMesh = new GameObject().addComponent(new MeshRenderer(test, material));
		testMesh.getTransform().setPosition(new Vector3f(10, 2, 0));
		testMesh.getTransform().setRotation(new Quaternion(new Vector3f(0, 1, 0), (float)Math.toRadians(-90)));
		
		planeObject = new GameObject();
		planeObject.addComponent(meshRenderer);
		planeObject.getTransform().getPosition().set(0, -1, 5);
		
		GameObject directionalLight = new GameObject();
		DirectionalLight light = new DirectionalLight(new Vector3f(0f, 0f, 1f), 0.4f);
		directionalLight.addComponent(light);
		directionalLight.getTransform().setRotation(new Quaternion(new Vector3f(1, 0, 0), (float)Math.toRadians(-45)));

		GameObject pointLight = new GameObject();
		PointLight light1 = new PointLight(new Vector3f(0f, 1f, 0f), 0.4f,
				new Vector3f(0, 0, 1) //Attenuation 
				);
		pointLight.addComponent(light1);
		
		pointLight.getTransform().getPosition().set(-25, 1, -5);
		
		GameObject spotLight = new GameObject();
		SpotLight light2 =new SpotLight(new Vector3f(0.5f, 1f, 0.75f), 0.4f,
				new Vector3f(0, 0, 0.1f), //Attenuation
				0.7f);
		spotLight.addComponent(light2);
		spotLight.getTransform().setRotation(new Quaternion(new Vector3f(0, 1, 0), (float)Math.toRadians(90.0f)));
		
	
		addToRoot(planeObject);
		addToRoot(directionalLight);
		addToRoot(pointLight);
		addToRoot(spotLight);
		addToRoot(testMesh);
		//transform.set_scale(new Vector3f(0.5f, 0.5f, 0.5f));
		
			}

	/**
	 * קלט מהמשתמש
	 */
	public void input()
	{
		
	}
	
	float temp = 0;
	/**
	 * מטודת העדכון של המשחק
	 */
	public void update(float delta)
	{
			temp+=delta;
		
			//meshRenderer.sLight1.getPointLight().setPosition(camera.get_position());
			//meshRenderer.sLight1.setDirection(camera.get_forward());
			//transform.set_translation(new Vector3f((float)Math.sin(temp), 0.00f, 10.0f));
			//transform.set_rotation(new Vector3f(0, (float)Math.sin(temp), 0));
			//transform.set_scale(new Vector3f(1, 1, 0.5f));
			//transform.set_scale(new Vector3f((float)Math.sin(temp), (float)Math.sin(temp), (float)Math.sin(temp)));			
	}

	
}
