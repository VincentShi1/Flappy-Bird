package main;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL15;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.util.ArrayList;
import java.util.Collections;

import engine.io.Input;
import engine.math.Matrix4f;
import level.Level;
import engine.graphic.*;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import javax.swing.*;

public class Main implements Runnable{
	public Thread game;
	public long window;
	public final int width = 1380, height = 860;
	private Level level;
	private boolean running = false;
	
	public void start() {
		running = true;
		game = new Thread(this,"game");
		game.start();
	}
	
	public void init() {
		if (glfwInit() != true) {
			System.err.println("Could not initialize GLFW!");
			return;
		}
		
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		window = glfwCreateWindow(width, height, "Flappy", NULL, NULL);
		if (window == NULL) {
			System.err.println("Could not create GLFW window!");
			return;
		}
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
		
		glfwSetKeyCallback(window, new Input());
		
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		GL.createCapabilities();
		
 		glEnable(GL_DEPTH_TEST);
		glActiveTexture(GL_TEXTURE1);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		System.out.println("OpenGL: " + glGetString(GL_VERSION));
		Shaders.loadAll();
		
		Matrix4f pr_matrix = Matrix4f.orthographic(-10.0f, 10.0f, -10.0f * 9.0f / 16.0f, 10.0f * 9.0f / 16.0f, -1.0f, 1.0f);
		Shaders.BG.setUniformMat4f("pr_matrix", pr_matrix);
		Shaders.BG.setUniform1i("tex", 1);
		
		Shaders.BIRD.setUniformMat4f("pr_matrix", pr_matrix);
		Shaders.BIRD.setUniform1i("tex", 1);
		
		Shaders.PIPE.setUniformMat4f("pr_matrix", pr_matrix);
		Shaders.PIPE.setUniform1i("tex", 1);
		level = new Level();
	}
	public void run() {
		init();
		
		long lastTime = System.nanoTime();
		double delta = 0.0;
		double ns = 1000000000.0 / 60.0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while (running && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)){
			long now = System.nanoTime();
			delta +=(now - lastTime)/ns;
			lastTime = now;
			if(delta >= 1.0) {
				update();
				updates++;
				delta--;
			}
			
			render();
			frames++;
			if(System.currentTimeMillis()-timer > 1000) {
				timer += 1000;
				System.out.println(updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
			if (glfwWindowShouldClose(window) == true)
				running = false;
		}
		glfwDestroyWindow(window); //close the window
		GLFW.glfwTerminate();
	}
	public void update(){
		GLFW.glfwPollEvents();
		level.update();
		
		if(level.isGameOver()) {
			level = new Level();
		}
	}
	public void render(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		level.render();
		int error = GL15.glGetError(); //tells error
		if(error != GL15.GL_NO_ERROR) {
			System.out.println(error);
		}
		glfwSwapBuffers(window);
		
	}
	public static void main(String[] a) {
		int int1[] = {1,2,3,4};
		int int2[] = {9,6,8,3,2,6};
		ArrayList<Integer> arr1 = new ArrayList<Integer>();
		Collections.addAll(arr1, 1,7,3,1,8);
		ArrayList<Integer> arr2 = new ArrayList<Integer>();
		Collections.addAll(arr2, 1,2,3,4,5);
		System.out.println(requirement.binarySearch(int1, 1));
		System.out.println(requirement.binarySearch(arr2, 2));
		requirement.mergeSort(int2, 5);
		new Frame();
		//new Main().start();
	}
}
