package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Light;
import toolbox.Maths;

public class TerrainShader extends ShaderProgram {
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	
	private int location_lightPosition , location_lightColor;
	
	private int location_shineDamper , location_reflectivity;
	private int location_skyColor;
	
	private int location_backgroundTexture , location_blendMap;
	private int location_rTexture,location_gTexture,location_bTexture;
	
	
	
	
	private static final String VERTEX_FILE = "src/shaders/terrainVertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/terrainFragmentShader.txt";
	
	public TerrainShader() {
		super(VERTEX_FILE,FRAGMENT_FILE);
	}
	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}
	@Override
	protected void getAllUniformLocation() {
	location_transformationMatrix =	super.getUniformLocation("transformationMatrix");
	location_projectionMatrix = super.getUniformLocation("projectionMatrix");
	location_viewMatrix = super.getUniformLocation("viewMatrix");
	
	location_lightPosition = super.getUniformLocation("lightPosition");
	location_lightColor = super.getUniformLocation("lightColor");
	
	location_reflectivity = super.getUniformLocation("reflectivity");
	location_shineDamper = super.getUniformLocation("shineDamper");
	location_skyColor = super.getUniformLocation("skyColor");
	
	
	location_backgroundTexture = super.getUniformLocation("backgroundTexture");
	location_rTexture = super.getUniformLocation("rTexture");
	location_gTexture = super.getUniformLocation("gTexture");
	location_bTexture = super.getUniformLocation("bTexture");
	location_blendMap = super.getUniformLocation("blendMap");
	
	}
	
	public void connectTextureUnits() {
		super.loadInt(location_backgroundTexture, 0);
		super.loadInt(location_rTexture, 1);
		super.loadInt(location_gTexture, 2);
		super.loadInt(location_bTexture, 3);
		super.loadInt(location_blendMap, 4);
	}
	
	public void loadSkyColor(float r, float g, float b) {
		super.loadVector(location_skyColor, new Vector3f(r,g,b));
	}
	public void loadShineVariables(float damper,float reflectivity) {
		super.loadFloat(location_reflectivity, reflectivity);
		super.loadFloat(location_shineDamper, damper);
	}
	
	public void loadLight(Light light) {
		super.loadVector(location_lightPosition, light.getPositionVector3f());
		super.loadVector(location_lightColor, light.getColorVector3f());
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix( location_projectionMatrix, matrix);
	}
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix4f = Maths.createViewMatrix(camera);
		super.loadMatrix( location_viewMatrix, viewMatrix4f);
	}
}
