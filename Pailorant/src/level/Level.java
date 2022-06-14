package level;

import java.util.Random;

import engine.graphic.Shaders;
import engine.graphic.Texture;
import engine.graphic.VertexArray;
import engine.io.Input;
import engine.math.Matrix4f;
import engine.math.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Level {
	private VertexArray background, fade;
	private Texture bgTexture;
	
	private int map = 0;
	private int xScroll = 0;
	
	private Bird bird;
	private Pipe[] pipes = new Pipe[10];
	private int index = 0;
	private Random random = new Random();
	private float OFFSET = 5.0f;
	boolean control = true, reset = false;
	private float time = 0.0f;
	
	public Level()
	{
		float[] vertices = new float[]{
				-10.0f, -10.0f * 9.0f / 16.0f, 0.0f,
				-10.0f,  10.0f * 9.0f / 16.0f, 0.0f,
				  0.0f,  10.0f * 9.0f / 16.0f, 0.0f,
				  0.0f, -10.0f * 9.0f / 16.0f, 0.0f
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
		
		fade = new VertexArray(6);
		background = new VertexArray(vertices, indices, tcs);
		bgTexture = new Texture("res/bg.jpg");
		bird = new Bird();
		createPipes();
	}
	
	private void createPipes() {
		Pipe.create();
		for(int i = 0; i<10; i+=2) {
			pipes[i] = new Pipe(OFFSET + index * 3.0f,random.nextFloat() *4.0f);
			pipes[i+1] = new Pipe(pipes[i].getX(), pipes[i].getY()-11.5f);
			index +=2;
		}
	}
	private void updatePipes() {
		pipes[index % 10] = new Pipe(OFFSET + index * 3.0f, random.nextFloat() * 4.0f);
		pipes[(index + 1) % 10] = new Pipe(pipes[index % 10].getX(), pipes[index % 10].getY() - 11.5f);
		index += 2;
	}
	
	public void update(){
		if(control) {
			xScroll--;
		if(-xScroll % 335 == 0) map++;
		if(-xScroll > 250 && -xScroll % 120 == 0) updatePipes();
		
		}
		bird.update();
		
		if(control && collision()) {
			bird.fall();
			control = false;
		}
		if(!control && Input.isKeyDown(GLFW_KEY_SPACE)) reset = true; 
		time += 0.01f;
	}
	
	private void renderPipes() {
		Shaders.PIPE.enable();
		Shaders.PIPE.setUniform2f("bird", 0, bird.getY());
		Shaders.PIPE.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(xScroll* 0.05f, 0.0f, 0.0f)));
		Pipe.getTexture().bind();;
		Pipe.getMesh().bind();
		
		for(int i = 0; i < 5 * 2; i++) {
			Shaders.PIPE.setUniformMat4f("ml_matrix", pipes[i].getModelMatrix());
			Shaders.PIPE.setUniform1f("top", i % 2 == 0 ? 1 : 0);
			Pipe.getMesh().draw();
		}
		Pipe.getMesh().unbind();
		Pipe.getTexture().unbind();
	}
	
	public boolean isGameOver() {
		return reset;
	}
	
	private boolean collision() {
		for(int i =0; i<10 ; i++) {
			float bx = -xScroll * .05f;
			float by = bird.getY();
			float px = pipes[i].getX();
			float py = pipes[i].getY();
			
			float bx0 = bx - bird.getSize()/2.0f;
			float bx1 = bx + bird.getSize()/2.0f;
			float by0 = by - bird.getSize()/2.0f;
			float by1 = by + bird.getSize()/2.0f;
			
			float px0 = px;
			float px1 = px + Pipe.getWidth();
			float py0 = py;
			float py1 = py + Pipe.getHeight();
			
			if(bx1 > px0 && bx0 < px1) {
				if(by1 > py0 && by0 < py1) {
					return true;
				}
			}
		}
		return false;
		
	}
	
	public void render() {
		bgTexture.bind();
		Shaders.BG.enable();
		Shaders.BG.setUniform2f("bird", 0, bird.getY());
		background.bind();
		for(int i = map; i< map + 4; i++){
			Shaders.BG.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(i*10+xScroll * 0.03f ,0.0f, 0.0f)));
			background.draw();
		}
		Shaders.BG.disable();
		bgTexture.unbind();
		renderPipes();
		bird.render();
		Shaders.FADE.enable();
		Shaders.FADE.setUniform1f("time", time);
		fade.render();
		Shaders.FADE.disable();
	}
	

}
