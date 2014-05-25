package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;


public class GameFrame {
	public final int WIDTH = 1024;
	public final int HEIGHT = 768;
	
	private String name;
	private MenuPanel menu;
	private MapCanvas map;
	private JFrame frame;
	
	public GameFrame(String name){
		this.name = name;
		this.map = new MapCanvas();
		this.menu = new MenuPanel();
		frame = new JFrame(name);
		

		// constructs the frame and sets the layout.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // makes it so the
																// frame closes
																// when the 'X'
																// is clicked.
		frame.add(menu);
		menu.setBounds(0, 0, WIDTH, HEIGHT);

		frame.setResizable(false);
		frame.pack();

		frame.setLocationRelativeTo(null); // centers the window in the screen
		frame.setVisible(true);
	}
	public void changeFrame(Component c){
		frame = new JFrame(name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(c);
		c.setBounds(0, 0, WIDTH, HEIGHT);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	public void close(){
		//WindowEvent wev = new WindowEvent(frame, WindowEvent.WINDOW_CLOSED);
		//Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
		frame.dispose();
	}
}
