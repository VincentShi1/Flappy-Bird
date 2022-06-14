package main;
import javax.swing.JFrame;
public class Frame extends JFrame{
	public Frame(){
		this.add(new Panel());
		this.setResizable(true);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
