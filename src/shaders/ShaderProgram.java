package shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public abstract class ShaderProgram {
	private int programId;
	private int vertexShaderID, fragmentShaderID;
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
		protected ShaderProgram(String vertexFIle, String fragmentFile) {
			vertexShaderID = loadShader(vertexFIle, GL20.GL_VERTEX_SHADER);
			fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
			programId = GL20.glCreateProgram();
			GL20.glAttachShader(programId, vertexShaderID);
			GL20.glAttachShader(programId, fragmentShaderID);
			bindAttributes();
			GL20.glLinkProgram(programId);
			GL20.glValidateProgram(programId);
			getAllUniformLocation();
		}
	public void start() {
		GL20.glUseProgram(programId);
	}
	public void stop() {
		GL20.glUseProgram(0);
		
	}
	public void cleanUp() {
		stop();
		GL20.glDetachShader(programId, vertexShaderID);
		GL20.glDetachShader(programId, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programId);
	}
	protected abstract void bindAttributes();
	
	protected void bindAttribute(int attribute,String variableName) {
		GL20.glBindAttribLocation(programId, attribute, variableName);
	}
	protected void loadInt(int location,int value) {
		GL20.glUniform1i(location, value);
	}
	protected void loadFloat(int location,float value) {
		GL20.glUniform1f(location, value);
	}
	protected void loadVector(int location,Vector3f vector) {
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}
	protected void load2DVector(int location,Vector2f vector) {
		GL20.glUniform2f(location, vector.x, vector.y);
	}
	protected void loadBoolean(int location,boolean value) {
		float toLoad = 0;
		if(value) {
			toLoad = 1;
		}
		GL20.glUniform1f(location, toLoad);
		
	}
	
	protected void loadMatrix(int location , Matrix4f matrix) {
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		GL20.glUniformMatrix4(location, false, matrixBuffer);
	}
	
	protected abstract void getAllUniformLocation();
	protected int getUniformLocation(String uniformName) {
		return GL20.glGetUniformLocation(programId, uniformName);
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
