package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.timothyaveni.apcsfinal.client.Client;
import com.timothyaveni.apcsfinal.client.Entity;
import com.timothyaveni.apcsfinal.client.Location;
import com.timothyaveni.apcsfinal.client.Tank;

public class MapCanvas extends Canvas implements UsesClient {

	private static final long serialVersionUID = 1L;
	public final int WIDTH = 1024;
	public final int HEIGHT = WIDTH / 4 * 3;
	private Client client;

	private Map map;
	
	// Constructor :D
	public MapCanvas() {
		super();
		map = new Map("Map.png");
	}

	public void render(Graphics g) {
		BufferedImage buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D bufferGraphics = buffer.createGraphics();
		
		// draws map background on Canvas in frame
		bufferGraphics.drawImage(map.getPic(), 0, 0, 1024, 768, null);

		ArrayList<Entity> e = new ArrayList<Entity>();
		// it is never okay to construct entities with id=-1 but this is just a
		// test and normally you get the id from the server
		e.add(new Tank(-1, new Location(512, 382, 1)));
		// c.getArrayList();

		for (int i = 0; i < e.size(); i++) {
			bufferGraphics.drawImage(e.get(i).getImage(client.getFrame()), e.get(i).getLocation().getX(), e.get(i).getLocation().getY(), e.get(i).getWidth(), e.get(i).getHeight(), null);
		}

		// pull arraylist down off client
		// misc comment so I'm able to commit
		//g.dispose(); // releases system resources the graphics object is taking
						// up
		g.drawImage(buffer, 0, 0, null);
	}

	@Override
	public void paint(Graphics g) {
		render(g);
	}

	@Override
	public void setClient(Client client) {
		this.client = client;
	}
}