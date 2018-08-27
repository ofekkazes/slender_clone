package com.strawberry.engine.rendering;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
//import static org.lwjgl.opengl.GL11.GL_ENABLE_BIT;
import static org.lwjgl.opengl.GL11.GL_EQUAL;
//import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_LESS;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
//import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_SRGB;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
//import static org.lwjgl.opengl.GL11.glPopAttrib;
//import static org.lwjgl.opengl.GL11.glPushAttrib;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import com.strawberry.engine.component.BaseLight;
import com.strawberry.engine.component.Camera;
import com.strawberry.engine.core.GameObject;
//import com.strawberry.engine.core.Matrix4f;
import com.strawberry.engine.core.Vector3f;
//import com.strawberry.engine.menu.GameState;
//import com.strawberry.engine.menu.MenuManager;

/**
 * מנוע הציור/רנדור
 * @author LapKazes
 *
 */
public class RenderingEngine 
{
	private Camera _mainCamera; //המצלמה הראשית
	private Vector3f _ambientLight; //הצבע שיאפיל על כולם

	private ArrayList<BaseLight> _lights; //רשימת התאורות לציור
	private BaseLight _activeLight; //התאורה שבדיוק מצוירת

	/**
	 * קונסטרקטור
	 */
	public RenderingEngine()
	{
		_lights = new ArrayList<BaseLight>();
		
		_ambientLight = new Vector3f(0.15f, 0.15f, 0.15f);

		
	}

	/**
	 * המטודה מציירת את האובייקט שהתקבל בתוספת האפקטים השונים
	 * @param object האובייקט לציור
	 */
	public void render(GameObject object)
	{
		//clrScreen();
		
		this._lights.clear();
		object.addToRenderingEngine(this);
		
		Shader forwardAmbient = ForwardAmbient.getInstance();

		object.render3D(forwardAmbient, this, _mainCamera.getTransform().getPosition());
		
		glEnable(GL_BLEND);
		//glBlendFunc(GL_ONE, GL_ONE);
		//glDepthMask(false);
		//glDepthFunc(GL_EQUAL);
		{
			for(BaseLight light : _lights)
			{
				_activeLight = light;
				
				object.render3D(light.getShader(), this, _mainCamera.getTransform().getPosition());
			}
		}
		//glDepthFunc(GL_LESS);
		//glDepthMask(true);
		//glDisable(GL_BLEND);

	}

	/**
	 * המטודה מנקה את הבאפר של הצבעים
	 */
	public static void clrScreen()
	{
		//glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		//if(!Slender.isInRnage())glClear(GL_DEPTH_BUFFER_BIT);
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
        GL11.glLoadIdentity();
	}

	/**
	 * המטודה מוסיפה תאורה לרשימה
	 * @param light
	 */
	public void addLight(BaseLight light)
	{
		this._lights.add(light);
	}
	
	/**
	 * המטודה מגדירה מצלמה חדשה
	 * @param camera
	 */
	public void addCamera(Camera camera)
	{
		setMainCamera(camera);
		
	}
	
	public BaseLight getActiveLight()
	{
		return _activeLight;
	}
	/*
	private static void unbindTextures()
	{
		glBindTexture(GL_TEXTURE_2D, 0);
	}
*/
	/**
	 * המטודה מאתחלת לגרפיקה דו מימדית
	 */
	public void initializeGL2D()
	{
		GL11.glViewport(0, 0, Window.getWidth(), Window.getHeight());
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0f, Window.getWidth(), Window.getHeight(), 0.0f, 0.0f, 1.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
	}
	
	/**
	 * המטודה מאתחלת את ההגדרות לגרפיקה תלת מימדית
	 */
	public void initializeGL3D()
	{
		GL11.glViewport(0, 0, Window.getWidth(), Window.getHeight());
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        glDisable(GL_CULL_FACE);
        GLU.gluPerspective(360.0f, (float)Window.getWidth() / (float)Window.getHeight(), 0.001f, Float.MAX_VALUE);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
	}
	
	public void initializeGL()
	{
		GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glClearDepth(1.0f);
        GL11.glClearStencil(0);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        GL11.glDisable(GL11.GL_DITHER);

        GL11.glCullFace(GL11.GL_BACK);
        GL11.glEnable(GL11.GL_CULL_FACE);
       
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

        GL11.glColor3f(1.0f, 1.0f, 1.0f);
	}
	
	public Vector3f getAmbientLight() { return _ambientLight; }
	
	public static String getGlVersion() { return glGetString(GL_VERSION); }

	public Camera getMainCamera() { return _mainCamera; }

	public void setMainCamera(Camera mainCamera) { this._mainCamera = mainCamera; }
}
