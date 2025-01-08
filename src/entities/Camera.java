package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	private Vector3f positioVector3f =  new Vector3f();
	private float pitch;
	private float yaw;
	private float roll;
	
	public Camera() {
		
	}
	public void move() {
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			positioVector3f.z -= 0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			positioVector3f.x+= 0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			positioVector3f.x-= 0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			positioVector3f.z += 0.02f;
		}
	}
	public Vector3f getPositioVector3f() {
		return positioVector3f;
	}
	public float getPitch() {
		return pitch;
	}
	public float getYaw() {
		return yaw;
	}
	public float getRoll() {
		return roll;
	}
}
