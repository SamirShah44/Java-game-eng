package engineTester;

import org.lwjgl.opengl.Display;

import models.RawModel;
import models.TextureModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import shaders.StaticShader;
import textures.ModelTexture;

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
		renderEngine.Renderer renderer = new renderEngine.Renderer();
		
		StaticShader shader = new StaticShader();
		
		float[] vertices = { -0.5f, 0.5f, 0f, -0.5f, -0.5f, 0f, 0.5f, -0.5f, 0f, 0.5f, 0.5f, 0f };
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		
		RawModel model = loader.loadToVao(vertices,indices);
		ModelTexture texture = new ModelTexture(loader.loadTexture("pika"));
		TextureModel textureModel = new TextureModel(model,texture);
		
		while (!Display.isCloseRequested()) {
			renderer.prepare();
			shader.start();
			// game logic
			renderer.render(textureModel);
			// render geometry
			shader.stop();
			DisplayManager.updateDisplay();
		}
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}