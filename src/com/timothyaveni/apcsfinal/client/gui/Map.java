package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class Map {
	
	String imageFileName = "Map.png"; //name of the file location
	private BufferedImage image; //image to be used in render method
	
	public Map(String fileLocation){ //file location does nothing as of yet for testing purposes
		
		//currently is not working, throws IllegalArgumentException
		//Normally would assign image to the image at the file location
		try {
			image = ImageIO.read(getClass().getResource(imageFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//returns a JPanel so you can add it to the Map Canvas
	public JPanel render(){
		JPanel j = new JPanel(){
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(image, 0, 0, 1024, 768, null); //draws image to JPanel
			}
		};
		
		return j;

	}
}
