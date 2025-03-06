package entities;

import org.lwjgl.util.vector.Vector3f;

public class Light {
	private Vector3f positionVector3f;
	private Vector3f colorVector3f;
	
	public Light(Vector3f positionVector3f, Vector3f colorVector3f) {
		super();
		this.positionVector3f = positionVector3f;
		this.colorVector3f = colorVector3f;
	}

	public Vector3f getPositionVector3f() {
		return positionVector3f;
	}

	public void setPositionVector3f(Vector3f positionVector3f) {
		this.positionVector3f = positionVector3f;
	}

	public Vector3f getColorVector3f() {
		return colorVector3f;
	}

	public void setColorVector3f(Vector3f colorVector3f) {
		this.colorVector3f = colorVector3f;
	}
	
	
}
