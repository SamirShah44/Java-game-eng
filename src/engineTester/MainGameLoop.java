package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.RawModel;
import models.TextureModel;
import objConverter.ModelData;
import objConverter.OBJLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

/**
 * This class contains the main method and is used to test the engine.
 * 
 * @author Karl
 *
 */
public class MainGameLoop {

	/**
	 * Creates a display and then continuously updates the display until the user
	 * tries to close it.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();		
		
		
		//////////////////////////Yerrain stuffs
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("flower"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack texturePaxkPack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("myMap"));
		
		
		////////////////////////////////////
			ModelData modelData = OBJLoader.loadOBJ("tree");
			RawModel treeModel = loader.loadToVao(modelData.getVertices(), 
			modelData.getTextureCoords(), modelData.getNormals(), modelData.getIndices());

		TextureModel textureModel = new TextureModel(treeModel,new ModelTexture(loader.loadTexture("tree")));
		
		
		ModelData lowPolyTreeData = OBJLoader.loadOBJ("lowPolyTree");
		RawModel lowPolyTreeRawModel = loader.loadToVao(lowPolyTreeData.getVertices(), 
				lowPolyTreeData.getTextureCoords(), lowPolyTreeData.getNormals(), lowPolyTreeData.getIndices());

	TextureModel lowPolyTreeModel = new TextureModel(lowPolyTreeRawModel,new ModelTexture(loader.loadTexture("lowPolyTree")));
	
		
	
		
		ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
		fernTextureAtlas.setNumberofRows(2);
		
		ModelData fernData = OBJLoader.loadOBJ("fern");
		RawModel rawFern = loader.loadToVao(fernData.getVertices(), 
				fernData.getTextureCoords(), fernData.getNormals(), fernData.getIndices());
		TextureModel fern = new TextureModel(rawFern, fernTextureAtlas);		
		
		
///////////////////////Player
		ModelData bunnModelData = OBJLoader.loadOBJ("person");
RawModel bunnyModel = loader.loadToVao(bunnModelData.getVertices(), 
		bunnModelData.getTextureCoords(), bunnModelData.getNormals(), bunnModelData.getIndices());
TextureModel playerModel = new TextureModel(bunnyModel, new ModelTexture(loader.loadTexture("playerTexture")));
Player player = new Player(playerModel, new Vector3f(100,0,-50), 0, 0, 0, 0.2f);

//////////////////
		
		
		
		ModelTexture treeTexture  = textureModel.getTexture();
		treeTexture.setShineDamper(10);
		treeTexture.setReflectivity(0.0f);
		
		Light light = new Light(new Vector3f(0,100,0), new Vector3f(1f,1f,1f));
		Camera camera = new Camera(player);
		
		Terrain terrain1 = new Terrain(0, -1, loader, texturePaxkPack, blendMap, "heightmap");
//		Terrain terrain2 = new Terrain(-1, -1, loader, texturePaxkPack, blendMap,"heightmap");
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random(676452);
		for(int i = 0; i<1000;i++) {
			if(i % 5 == 0) {
				float X = random.nextFloat() * 800 -40;
				float Z = random.nextFloat() * -600;
				float Y = terrain1.getHeightOfTerrain(X, Z);
				
				entities.add(new Entity(fern, random.nextInt(4),new Vector3f(X,Y,Z), 0f, 
						0f, 0f,0.7f));
			}
			if(i % 3 == 0) {
				float X = random.nextFloat() * 800 -40;
				float Z = random.nextFloat() * -600;
				float Y = terrain1.getHeightOfTerrain(X, Z);
				
				entities.add(new Entity(lowPolyTreeModel, new Vector3f(X,Y,Z), 0f, 
						0f, 0f,0.2f));
			}
			if(i%2 == 0) {
				float X = random.nextFloat() * 800 -40;
				float Z = random.nextFloat() * -600;
				float Y = terrain1.getHeightOfTerrain(X, Z);
				
				entities.add(new Entity(textureModel, new Vector3f(X,Y,Z), 0f, 
						0f, 0f,1f));
				
			}
//			allGrass.add(new Entity(	grassModel, new Vector3f(X,Y,Z), 0f, 
//					0f, 0f,1f));
		}

		MasterRenderer renderer = new MasterRenderer();
		while (!Display.isCloseRequested()) {
//			entity.increasePosition(0.0f, 0,-0.1f);
//			entity.increaseRotation(0f, 1f, 0f);
			camera.move();
			player.Move(terrain1);
			
			renderer.processEntity(player);
			
			 
			renderer.processTerrain(terrain1);
//			renderer.processTerrain(terrain2);
			renderer.render(light, camera);
//			renderer.processEntity(entity);
			
			for(Entity drag: entities) {
				
				renderer.processEntity(drag);
			}
			DisplayManager.updateDisplay();
		}
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}