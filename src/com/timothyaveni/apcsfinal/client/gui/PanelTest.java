package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Component;

import javax.swing.JFrame;

public class PanelTest {

	private String name;
	private JFrame frame;

	// ******When you construct a GameFrame object you must call changeFrame()
	public PanelTest(String name) {
		this.name = name;
	}

	public void changeFrame(Component component) {

		// creates new frame and sets all attributes associated with it
		// frame.getContentPane().removeAll();

		frame = new JFrame(name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(component);
		component.setBounds(0, 0, 1024, 768);
		frame.setSize(1024, 768);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.update(frame.getGraphics());

		component.requestFocus();
	}

	// closes previous frame
	public void close() {
		frame.dispose();
	}
}