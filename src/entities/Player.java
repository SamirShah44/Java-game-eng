package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TextureModel;
import renderEngine.DisplayManager;
import terrains.Terrain;

public class Player extends Entity{
	
	public static final float RUN_SPEED = 20;
	public static final float TURN_SPEED = 160;
	
	public static final float HEIGHT=0;
	
	private static final float GRAVITY = -50;
	private static final float JUMP_POWER = 30;
	
	private boolean isInAir = false;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardSPeed = 0;
	
	public Player(TextureModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
		// TODO Auto-generated constructor stub
	}
	
	public void Move(Terrain  terrain) {
		checkInput();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSecond(), 0);
		
		float distance = currentSpeed * DisplayManager.getFrameTimeSecond();
		
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		upwardSPeed += GRAVITY * DisplayManager.getFrameTimeSecond();
		super.increasePosition(0, upwardSPeed* DisplayManager.getFrameTimeSecond() , 0);
		
		
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		if(super.getPosition().y <= terrainHeight) {
			upwardSPeed = 0; 
			super.getPosition().y = terrainHeight;
			isInAir = false;
		}
	}
	private void jump() {
		if(!isInAir) {
			this.upwardSPeed = JUMP_POWER;
			isInAir = true;
		}
	}
	private void checkInput() {
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.currentSpeed = RUN_SPEED;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.currentSpeed = -RUN_SPEED;
		}
		else {
			this.currentSpeed = 0;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.currentTurnSpeed = -TURN_SPEED;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.currentTurnSpeed = TURN_SPEED;
		}
		else {
			this.currentTurnSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			jump();
		}
		
		
		
	}

}
