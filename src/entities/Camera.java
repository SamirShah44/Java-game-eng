package entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	private Vector3f positionVector3f = new Vector3f(0,10,10);
	private float pitch ;
	private float yaw ;
	private float roll;
	
	
	
	private float distranceFromPlayer = 70;
	private float angleAroundPlayer = 0;
	
	
	private Player player;
	
 
	public Camera(Player player) {
		this.player = player;
	}
	public void move() {
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		
		float horizontalDis = calculateHorizontalDistance();
		float vertDis = calculateVerticalDistance();
		
		calculateCameraPosition(horizontalDis, vertDis);
		
		
		this.yaw = 180 - (angleAroundPlayer + player.getRotY());
	}
	
	private float calculateHorizontalDistance(){
		float hD = (float) (distranceFromPlayer * Math.cos(Math.toRadians(pitch)));
		if(hD < 0)
			hD = 0;
		return hD;
	}
	
	private float calculateVerticalDistance(){
		float vD = (float) (distranceFromPlayer * Math.sin(Math.toRadians(pitch)));
		if(vD < 0)
			vD = 0;
		return vD;
	}
	
	public Vector3f getPositioVector3f() {
		return positionVector3f;
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
	
	private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
		float theta = player.getRotY() + angleAroundPlayer;
	    float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
	    float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
	    
	    positionVector3f.x = player.getPosition().x - offsetX;
	    positionVector3f.z = player.getPosition().z - offsetZ;
	    positionVector3f.y = player.getPosition().y + verticalDistance;
	}
	
	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel()*0.1f;
		
		distranceFromPlayer -= zoomLevel;
		
		if (distranceFromPlayer < 10) distranceFromPlayer = 10;  // Minimum distance
	    if (distranceFromPlayer > 100) distranceFromPlayer = 100; // Maximum distance
	}
	
	private void calculatePitch() {
		if(Mouse.isButtonDown(1)) {
			float pitchCHange = Mouse.getDY()*0.1f;
			pitch -= pitchCHange;
			if (pitch < 0) pitch = 0;
	        if (pitch > 90) pitch = 90;
		}
	}
	
	private void calculateAngleAroundPlayer() {
		if(Mouse.isButtonDown(0)) {
			float angleCHange = Mouse.getDX()*0.3f;
			
			angleAroundPlayer -= angleCHange;
		}
	}
}
