package engine.graphic;

import engine.utils.ShaderUtils;
import static org.lwjgl.opengl.GL20.*;

import engine.math.Matrix4f;
import engine.math.Vector3f;
import java.util.HashMap;
import java.util.Map;


public class Shaders {
	public static final int VERTEX_ATTRIB = 0;
	public static final int TCOORD = 1; //texture coordinate
	private final int ID;
	public static Shaders BG;
	public static Shaders BIRD;
	public static Shaders PIPE;
	public static Shaders FADE;
	private boolean enabled = false;
	private Map<String, Integer> locationCache = new HashMap<String, Integer>();
	
	public Shaders(String vertex, String fragment) {
		ID = ShaderUtils.load(vertex, fragment);
	}
	
	public static void loadAll(){
		BG = new Shaders("shaders/bg.vert", "shaders/bg.frag");
		BIRD = new Shaders("shaders/bird.vert", "shaders/bird.frag");
		PIPE = new Shaders("shaders/pipe.vert", "shaders/pipe.frag");
		FADE = new Shaders("shaders/fade.vert", "shaders/fade.frag");
	}
	
	public int getUniform(String name) {
		if(locationCache.containsKey(name)) 
			return locationCache.get(name);
		int result = glGetUniformLocation(ID, name);
		if(result == -1) {
			System.err.println("Could not find uniform variable '" + name + "'!");
		}
		else 
			locationCache.put(name, result);
		return result;
	}
	
	public void setUniform1i(String name, int value) {
		if(!enabled) enable();
		glUniform1i(getUniform(name),value);
	}
	
	public void setUniform1f(String name, float value) { //1 float
		if(!enabled) enable();
		glUniform1f(getUniform(name),value);
	}
	
	public void setUniform2f(String name, float x , float y) { //2 float
		if(!enabled) enable();
		glUniform2f(getUniform(name),x,y);
	}
	
	public void setUniform3f(String name, Vector3f vector ) {
		if(!enabled) enable();
		glUniform3f(getUniform(name),vector.getX(), vector.getY(), vector.getZ());
	}
	
	public void setUniformMat4f(String name, Matrix4f matrix) {
		if(!enabled) enable();
		glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
	}
	
	public void enable() {
		glUseProgram(ID);
		enabled = true;
	}
	
	public void disable() {
		glUseProgram(0);
		enabled =false;
	}
}
