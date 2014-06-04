package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Component;

import javax.swing.JFrame;

import com.timothyaveni.apcsfinal.client.Client;

public class GameFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final int WIDTH = 1024;
	public final int HEIGHT = 768;

	private String name;
	private JFrame frame;
	private Client client;

	// ******When you construct a GameFrame object you must call changeFrame()
	public GameFrame(String name, Client c) {
		this.name = name;
		this.client = c;
	}

	public <T extends Component & UsesClient> void changeFrame(T component) {

		// creates new frame and sets all attributes associated with it
		// frame.getContentPane().removeAll();
		frame = new JFrame(name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		component.setBounds(0, 0, WIDTH, HEIGHT);
		frame.add(component);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.update(frame.getGraphics());
		
		component.setClient(client);

	}

	// closes previous frame
	public void close() {
		frame.dispose();
	}

	@Override
	public void setDefaultCloseOperation(int operation) {
		super.setDefaultCloseOperation(operation);
	}

	public Client getClient() {
		return client;
	}
}
