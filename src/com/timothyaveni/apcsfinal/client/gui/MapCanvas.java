package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.List;

import com.timothyaveni.apcsfinal.client.Client;
import com.timothyaveni.apcsfinal.client.Entity;
import com.timothyaveni.apcsfinal.client.Location;
import com.timothyaveni.apcsfinal.client.Tank;

public class MapCanvas extends Canvas implements UsesClient {
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1024;
	public static final int HEIGHT = WIDTH / 4 * 3;
	private Client client;

	private Map map;
	private BufferStrategy bs;

	private Tank t;

	// Constructor :D
	public MapCanvas() {
		super();
		map = new Map("Map1", ".png");
		// render();
	}

	public void init() {
		this.createBufferStrategy(2);
		bs = this.getBufferStrategy();
	}

	public void render() {
		Graphics g;
		do {
			do {
				g = bs.getDrawGraphics();

				Location playerLocation = client.getPlayer().getLocation();
				// Location playerLocation = t.getLocation();
				g.drawImage(map.getPic(playerLocation), 0, 0, 1024, 768, null);

				List<Entity> entities = client.getEntityList();

				for (int i = 0; i < entities.size(); i++) {
					Entity thisEntity = entities.get(i);
					g.drawImage(thisEntity.getImage(client.getFrame()), thisEntity.getLocation().getX()
							- playerLocation.getX() + WIDTH / 2 - thisEntity.getWidth() / 2, thisEntity.getLocation()
							.getY() - playerLocation.getY() + HEIGHT / 2 - thisEntity.getHeight() / 2,
							thisEntity.getWidth(), thisEntity.getHeight(), null);
				}

				// pull arraylist down off client
				// misc comment so I'm able to commit
				// g.dispose(); // releases system resources the graphics
				// object
				// is
			} while (bs.contentsRestored());
		} while (bs.contentsLost());
		bs.show();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}

	@Override
	public void setClient(Client client) {
		this.client = client;
	}
}