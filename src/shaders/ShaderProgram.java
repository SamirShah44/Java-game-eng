package shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class ShaderProgram {
	private int programId;
	private int vertexShaderID, fragmentShaderID;
	
		protected ShaderProgram(String vertexFIle, String fragmentFile) {
			vertexShaderID = loadShader(vertexFIle, GL20.GL_VERTEX_SHADER);
			fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
			programId = GL20.glCreateProgram();
			GL20.glAttachShader(programId, fragmentShaderID);
			GL20.glAttachShader(programId, vertexShaderID);
			GL20.glLinkProgram(programId);
			GL20.glValidateProgram(programId);
			bindAttributes();
		}
	public void start() {
		GL20.glUseProgram(programId);
	}
	public void stop() {
		GL20.glUseProgram(0);
		
	}
	public void cleanUp() {
		stop();
		GL20.glDetachShader(programId, fragmentShaderID);
		GL20.glDetachShader(programId, vertexShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programId);
	}
	protected abstract void bindAttributes();
	
	protected void bindAttribute(int attribute,String variableName) {
		GL20.glBindAttribLocation(programId, attribute, variableName);
	}
	
	private static int loadShader(String file, int type) {
		StringBuilder shaderSource = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("//\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader!");
			System.exit(-1);
		}
		return shaderID;

	}
}
