package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Canvas;
import java.awt.Graphics;

import javax.swing.JFrame;

public class MapCanvas extends Canvas {

	private static final long serialVersionUID = 1L;
	public final int WIDTH = 1024;
	public final int HEIGHT = WIDTH / 4 * 3;
	public final String NAME = "Saviors of Gundthor";

	private JFrame frame;
	//private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	// Constructor :D
	public MapCanvas() {

		// constructs the frame and sets the layout.
		frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // makes it so the
																// frame closes
																// when the 'X'
																// is clicked.
		frame.add(this);
		this.setBounds(0, 0, WIDTH, HEIGHT);

		frame.setResizable(false);
		frame.pack();

		frame.setLocationRelativeTo(null); // centers the window in the screen
		frame.setVisible(true);
	}

	public void render(Graphics g) {
		// This is what chooses what to display (in this instance, a green
		// field)
		// [[[[[ WILL BE CHANGED LATER ]]]]]

		//g.setColor(Color.GREEN);
		//g.fillRect(0, 0, WIDTH, HEIGHT); // this fills the JFrame with the color
		
		Map m = new Map("E:\\Map.png"); //would normally add JPanel returned by map to Canvas
		frame.add(m);
		frame.pack();
		// g.drawImage

		//g.dispose(); // releases system resources the graphics object is taking
						// up
	}

	@Override
	public void paint(Graphics g) {
		render(g);
	}

	public static void main(String[] args) {
		MapCanvas m = new MapCanvas();
	}
}