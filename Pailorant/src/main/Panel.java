package main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
public class Panel extends JPanel implements ActionListener{
	private final int width = 800;
	private final int height = 800;
	private JButton start = new JButton("Start");
	public Panel(){
		this.setPreferredSize(new Dimension(width, height));
		this.add(start);
		start.addActionListener(this);
		
	}
	public void startGame() {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o == start) {
			new Main().start();
		}
	}
	
	
}
