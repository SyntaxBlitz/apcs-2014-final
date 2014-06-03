package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Component;

import javax.swing.JFrame;

import com.timothyaveni.apcsfinal.client.Client;

public class GameFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final int WIDTH = 1024;
	public final int HEIGHT = 768;

	private String name;
	private JFrame frame;
	private Client c;

	// ******When you construct a GameFrame object you must call changeFrame()
	public GameFrame(String name, Client c) {
		this.name = name;
		this.c = c;
	}

	public void changeFrame(Component c) {

		// creates new frame and sets all attributes associated with it
		//frame.getContentPane().removeAll();
		frame = new JFrame(name);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		c.setBounds(0, 0, WIDTH, HEIGHT);
		frame.add(c);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.update(frame.getGraphics());

	}

	// closes previous frame
	public void close() {
		frame.dispose();
	}
	@Override
	public void setDefaultCloseOperation(int operation){
		super.setDefaultCloseOperation(operation);
	}

	public Client getClient() {
		return c;
	}
}
