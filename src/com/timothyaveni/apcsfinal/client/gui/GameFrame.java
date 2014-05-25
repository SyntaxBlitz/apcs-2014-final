package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;


public class GameFrame {
	
	public final int WIDTH = 1024;
	public final int HEIGHT = 768;
	
	private String name;
	private JFrame frame;
	
	
	//******When you construct a GameFrame object you must call changeFrame()
	public GameFrame(String name){
		this.name = name;
	}
	public void changeFrame(Component c){

		frame = new JFrame(name);
		//frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c.setBounds(0, 0, WIDTH, HEIGHT);
		frame.add(c);
		frame.setResizable(false);
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
