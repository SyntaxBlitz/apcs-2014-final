package com.timothyaveni.apcsfinal.client.gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;



public class MapCanvas extends Canvas {
	
	private static final long serialVersionUID = 1L;
	public final int WIDTH = 128;
	public final int HEIGHT = WIDTH/12 * 9;
	public final String NAME = "Saviors of Gundthor";
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int scale;
	private JFrame frame;
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	//Constructor :D
	public MapCanvas(int scale){
		
		//sets scale of the frame
		this.scale = scale;
		
		//This block sets it so you cannot change the size of the screen once it is drawn
		setMinimumSize(new Dimension(WIDTH * scale, HEIGHT * scale));
		setMaximumSize(new Dimension(WIDTH * scale, HEIGHT * scale));
		setPreferredSize(new Dimension(WIDTH * scale, HEIGHT * scale));
		
		//constructs the frame and sets the layout.
		frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //makes it so the frame closes when the 'X' is clicked.
		frame.setLayout(new BorderLayout()); //sets layout of image
		
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void render(){
		BufferStrategy bs = getBufferStrategy(); //Object that allows us to organize things on the canvas
		if(bs == null){
			createBufferStrategy(3); //If bs is null, it will create a bufferStrategy of the value 3 which reduces tearing and cross pixelation.
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		//This is what chooses what to display (in this instance, a green field)
		// [[[[[ WILL BE CHANGED LATER ]]]]]
		// Also not working currently
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, getWidth(), getHeight()); //this fills the JFrame with the color
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

		g.dispose(); //releases system resources the graphics object is taking up
		bs.show(); //shows the frame
	}
	
	public static void main(String[] args){
		MapCanvas m = new MapCanvas(7);
		m.render();
	}
}