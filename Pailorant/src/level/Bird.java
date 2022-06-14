package level;

import org.lwjgl.glfw.GLFW;

import engine.graphic.Shaders;
import engine.graphic.Texture;
import engine.graphic.VertexArray;
import engine.io.Input;
import engine.math.Matrix4f;
import engine.math.Vector3f;

public class Bird {
	
	private float SIZE = 1.0f;
	private VertexArray mesh;
	private Texture texture;
	
	private Vector3f position = new Vector3f();
	private float rotation;
	private float delta = 0.0f;
	
	public Bird() {
		float[] vertices = new float[]{
				-SIZE / 2.0f, -SIZE / 2.0f, 0.2f,
				-SIZE / 2.0f,  SIZE / 2.0f, 0.2f,
				 SIZE / 2.0f,  SIZE / 2.0f, 0.2f,
				 SIZE / 2.0f, -SIZE / 2.0f, 0.2f
		}; 
		
		byte[] indices = new byte[] {
				0,1,2, //first triangle consists of 
				2,3,0  // second triangle consists of
		};
		
		float[] tcs = new float[] {
				0,1,
				0,0,
				1,0,
				1,1
		};
		
		mesh = new VertexArray(vertices, indices, tcs);
		texture = new Texture("res/holmer_handsome.jpg");
	}
	
	public void update() {
		position.setY(position.getY() - delta);
		if(Input.isKeyDown(GLFW.GLFW_KEY_SPACE) ) delta = -0.15f;
		else delta += 0.01f;
		
		rotation = -delta * 90.f;
	}
	
	public void fall() {
		delta = -0.15f;
	}
	public void render() {
		Shaders.BIRD.enable();
		Shaders.BIRD.setUniformMat4f("ml_matrix", Matrix4f.translate(position).multiply(Matrix4f.rotate(rotation)));
		texture.bind();
		mesh.render();
		Shaders.BIRD.disable();
	}

	public float getY() {
		// TODO Auto-generated method stub
		return position.getY();
	}

	public float getSize() {
		// TODO Auto-generated method stub
		return SIZE;
	}
}
