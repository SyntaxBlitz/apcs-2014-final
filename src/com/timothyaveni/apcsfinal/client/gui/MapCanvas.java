package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Canvas;
import java.awt.Graphics;
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

	// Constructor :D
	public MapCanvas() {
		super();
	}

	public void render(Graphics g) {

		// draws map background on Canvas in frame
		Map m = new Map("Map.png");
		g.drawImage(m.getPic(), 0, 0, 1024, 768, null);

		ArrayList<Entity> e = new ArrayList<Entity>();
		// it is never okay to construct entities with id=-1 but this is just a
		// test and normally you get the id from the server
		e.add(new Tank(-1, new Location(512, 382, 1)));
		// c.getArrayList();

		for (int i = 0; i < e.size(); i++) {
			g.drawImage(e.get(i).getImage(client.getFrame()), 0, 0, e.get(i).getWidth(), e.get(i).getHeight(), null);
		}

		// pull arraylist down off client
		// misc comment so I'm able to commit
		g.dispose(); // releases system resources the graphics object is taking
						// up
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