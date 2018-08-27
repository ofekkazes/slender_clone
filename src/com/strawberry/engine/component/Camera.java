package com.strawberry.engine.component;

import org.lwjgl.input.Keyboard;

import com.strawberry.engine.core.Input;
import com.strawberry.engine.core.Matrix4f;
import com.strawberry.engine.core.Vector2f;
import com.strawberry.engine.core.Vector3f;
import com.strawberry.engine.physics.BoundingSphere;
import com.strawberry.engine.rendering.RenderingEngine;
import com.strawberry.engine.rendering.Shader;
import com.strawberry.engine.rendering.Window;

/**
 * מחלקה שיורשת את מחלקת הפונקציונליות הבסיסית ומגדירה מצלמה שגם שולטת בשחקן (ממבט FPS)
 * @author LapKazes
 *
 */
public class Camera extends GameComponent
{
	public static final Vector3f yAxis = new Vector3f(0,1,0); //אובייקט שפשוט מגדיר את ציר ה- Y לחישובים מסוימים

	private boolean _mouse = false; //מגדיר שימוש בעכבר
	
	public Matrix4f _projection; //האזור שעליו המודלים יצויירו (נקודת המבט של המצלמה)
	
	public Vector3f movementVector;
	private Vector3f collisionVector;
	private Vector3f oldPos;
	private Vector3f newPos;
	
	private BoundingSphere camSphere;
	
	/**
	 * פעולה בונה
	 * @param fov שדה ראייה
	 * @param aspectRatio יחס המסך (גובה/רוחב)
	 * @param zNear התחלת האזור שעליו יצויירו המודלים
	 * @param zFar סוף האזור שממנו יצויירו המודלים
	 */
	public Camera(float fov, float aspectRatio, float zNear, float zFar)
	{
		this._projection = new Matrix4f().initPerspective(fov, aspectRatio, zFar, zNear);
		this.movementVector = new Vector3f();
		this.setCollisionVector(new Vector3f());
		this.setNewPos(Vector3f.ZERO);
		this.setOldPos(Vector3f.ZERO);
		this.camSphere = new BoundingSphere(Vector3f.ZERO, 7.5f);
	}
	
	/**
	 * מחזיר את מיקום המצלמה בחלל התלת מימדי
	 * @return
	 */
	public Matrix4f getViewProjection()
	{
		Matrix4f cameraRotation = getTransform().getTransformedRotation().conjugate().toRotationMatrix();
		Vector3f cameraPosition = getTransform().getTransformedPosition().mul(-1);
		
		Matrix4f cameraTranslation = new Matrix4f().initTranslation(cameraPosition.getX(), 
																	cameraPosition.getY(), 
																	cameraPosition.getZ());
		return _projection.mul(cameraRotation.mul(cameraTranslation));
	}
	
	Vector2f centerPosition = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);
	/**
	 * מטודה המקבלת קלט מהמשתמש
	 */
	@Override
	public void input(float delta)
	{
		this.movementVector = new Vector3f();
		if(Input.getKey(Keyboard.KEY_D))
			this.movementVector = this.movementVector.add(this.getTransform().getRotation().getRight());//move(this.getTransform().getRotation().getRight(), movAmt);
		if(Input.getKey(Keyboard.KEY_A))
			this.movementVector = this.movementVector.add(this.getTransform().getRotation().getLeft());//move(this.getTransform().getRotation().getLeft(), movAmt);
		if(Input.getKey(Keyboard.KEY_W))
			this.movementVector = this.movementVector.add(this.getTransform().getRotation().getForward());//move(this.getTransform().getRotation().getForward(), movAmt);
		if(Input.getKey(Keyboard.KEY_S))
			this.movementVector = this.movementVector.sub(this.getTransform().getRotation().getForward());//move(this.getTransform().getRotation().getForward(), -movAmt);
		if(Input.getMouseDown(1))
		{
			_mouse = !_mouse;
			Input.setMousePos(centerPosition);
			Input.setCursor(!_mouse);
		}
		if(_mouse)
		{
			Vector2f deltaPos = Input.getMousePos().sub(centerPosition);
		
			boolean rotY = deltaPos.getX() != 0;
			boolean rotX = deltaPos.getY() != 0;
			
			if(rotY) 
				this.getTransform().rotate(yAxis, deltaPos.getX() * 0.005f);
			if(rotX)
				this.getTransform().rotate(this.getTransform().getRotation().getRight(), -deltaPos.getY() * 0.005f);
		
			if(rotX || rotY)
				Input.setMousePos(centerPosition);
			}
		
	}
	

	/**
	 * המטודה מעדכנת את מיקום המצלמה
	 */
	@Override
	public void update(float delta) 
	{
		float movAmt;
		
		if(Input.getKey(Keyboard.KEY_LSHIFT) || Input.getKey(Keyboard.KEY_RSHIFT))
			movAmt =(float)(40 * delta) * 1.5f;
		else movAmt = (float)(40 * delta);
		
		this.movementVector.setY(0);
		
		oldPos = this.getTransform().getPosition();
		newPos = oldPos.add(movementVector.mul(movAmt));
		
		movementVector = movementVector.mul(collisionVector);
		if(movementVector.length() > 0)
			this.move(movementVector, movAmt);
		
		this.camSphere.setCenter(getNewPos());
	}

	/**
	 * 	מטודה המזיזה את המצלמה
	 * @param direction כיוון
	 * @param amount כמות הזזה
	 */
	public void move(Vector3f direction, float amount)
	{
		this.getTransform().setPosition(this.getTransform().getPosition().add(direction.mul(amount)));
	}
	
	/**
	 * הוספת המצלמה למנוע הציור
	 */
	@Override
	public void addToRenderingEngine(RenderingEngine engine) 
	{
		engine.addCamera(this);
	}


	@Override
	public void render3D(Shader shader, RenderingEngine engine) {
		// TODO Auto-generated method stub
		
	}

	public Vector3f getCollisionVector() {
		return collisionVector;
	}

	public void setCollisionVector(Vector3f collisionVector) {
		this.collisionVector = collisionVector;
	}

	public Vector3f getNewPos() {
		return newPos;
	}

	public void setNewPos(Vector3f newPos) {
		this.newPos = newPos;
	}

	public Vector3f getOldPos() {
		return oldPos;
	}

	public void setOldPos(Vector3f oldPos) {
		this.oldPos = oldPos;
	}

	public Vector3f getMovementVector() {
		return movementVector;
	}

	public void setMovementVector(Vector3f movementVector) {
		this.movementVector = movementVector;
	}
	
	public BoundingSphere getBoundingSphere() {return this.camSphere; }

	@Override
	public void cleanUp() {
		this._projection.initIdentity();
	}

}
