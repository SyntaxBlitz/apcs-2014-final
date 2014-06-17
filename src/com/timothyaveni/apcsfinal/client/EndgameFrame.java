package com.timothyaveni.apcsfinal.client;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.timothyaveni.apcsfinal.client.gui.Colors;
import com.timothyaveni.apcsfinal.client.gui.ImageModifier;
import com.timothyaveni.apcsfinal.client.gui.UsesClient;

public class EndgameFrame extends JPanel implements UsesClient, ActionListener {

	private static final long serialVersionUID = 6739658143466738909L;
	private Client client;
	private int counter;
	private Timer timer;
	
	private BufferedImage image;
	
	public EndgameFrame() {
		counter = 100;
		
		try {
			this.image = ImageIO.read(FileReader.getFileFromResourceString("EndGame.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.setPreferredSize(new Dimension(Client.WIDTH, Client.HEIGHT));
		
		this.sceneLoop();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//g.drawImage(image, 0, 0, null);
		g.drawImage(ImageModifier.fadeIn(image, 0, 0, 1024, 768, Colors.ALPHA_RGB, 0.009F), 0, 0, this);
	}
	
	@Override
	public void setClient(Client client) {
		this.client = client;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		counter --;
		if (counter >= 0) {
			this.repaint();
		} else {
			timer.stop();
			this.repaint();
			System.out.println("Done");
		}
	}

	public void sceneLoop() {
		timer = new Timer(50, this);
		timer.start();
	}
}
