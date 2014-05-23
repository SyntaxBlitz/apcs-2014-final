package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Map extends JPanel{

	private static final long serialVersionUID = 1L;
	String imageFileName; 					//name of the file location
	private BufferedImage image = null;     //image to be used in render method
	
	public Map(String fileLocation){   		//file location does nothing as of yet for testing purposes
		
		imageFileName = fileLocation;
		
		//currently is not working, throws IllegalArgumentException
		//Normally would assign image to the image at the file location
		try {
			image = ImageIO.read(new File(imageFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JLabel pic = new JLabel(new ImageIcon(image));
		add(pic);
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(image, 0, 0, 1024, 768, null);
	}
	
	//returns a JPanel so you can add it to the Map Canvas
	public Dimension getPreferredSize() {
		if (image == null) {
			return new Dimension(100, 100);
		}	else	{
		return new Dimension(image.getWidth(), image.getHeight());
		}
	}
	
	public BufferedImage getPic(){
		return image;
	}
}