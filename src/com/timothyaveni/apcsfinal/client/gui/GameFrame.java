package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Component;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import com.timothyaveni.apcsfinal.client.Client;

public class GameFrame {

	/**
	 * 
	 */

	private String name;
	private JFrame frame;
	private Client client;

	private MapCanvas mapCanvas = null;
	private KeyListener keyListener;
	private MouseListener mouseListener;

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
		frame.add(component);
		component.setBounds(0, 0, Client.WIDTH, Client.HEIGHT);
		frame.setSize(Client.WIDTH, Client.HEIGHT);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.update(frame.getGraphics());

		component.requestFocus();
		component.addKeyListener(keyListener);
		component.addMouseListener(mouseListener);
		component.setClient(client);

		if (component instanceof MapCanvas) {
			System.out.println("setting local map canvas");
			this.mapCanvas = (MapCanvas) component;
			mapCanvas.init();
		}
	}

	// closes previous frame
	public void close() {
		frame.dispose();
	}

	public void setKeyListener(KeyListener keyListener) {
		this.keyListener = keyListener;
	}

	public void setMouseListener(MouseListener mouseListener) {
		this.mouseListener = mouseListener;
	}

	public Client getClient() {
		return client;
	}

	public MapCanvas getMapCanvas() {
		return this.mapCanvas;
	}
}
