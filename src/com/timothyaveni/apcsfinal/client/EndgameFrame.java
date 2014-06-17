package com.timothyaveni.apcsfinal.client;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.timothyaveni.apcsfinal.client.gui.UsesClient;

public class EndgameFrame extends JPanel implements UsesClient {

	private static final long serialVersionUID = 6739658143466738909L;
	private Client client;
	
	private BufferedImage image;
	
	public EndgameFrame() {
		try {
			this.image = ImageIO.read(FileReader.getFileFromResourceString("EndGame.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.setPreferredSize(new Dimension(Client.WIDTH, Client.HEIGHT));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}
	
	@Override
	public void setClient(Client client) {
		this.client = client;
	}

}
