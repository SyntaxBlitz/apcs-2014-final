package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.HashMap;
import java.util.List;

import com.timothyaveni.apcsfinal.client.Client;
import com.timothyaveni.apcsfinal.client.Entity;
import com.timothyaveni.apcsfinal.client.Location;

public class MapCanvas extends Canvas implements UsesClient {
	private static final long serialVersionUID = 1L;
	private Client client;

	private BufferStrategy bs;

	private boolean readyToRender = false;

	// Constructor :D
	public MapCanvas() {
		super();
	}

	public void init() {
		this.createBufferStrategy(2);
		bs = this.getBufferStrategy();

		this.setReadyToRender(true);
	}

	public void render() {
		Graphics g;
		do {
			do {
				g = bs.getDrawGraphics();

				Location playerLocation = client.getPlayer().getLocation();
				// Location playerLocation = t.getLocation();
				g.drawImage(client.getCurrentMap().getPic(playerLocation), 0, 0, 1024, 768, null);

				HashMap<Integer, Entity> entities = client.getEntityList();

				for (Integer key : entities.keySet()) {
					Entity thisEntity = entities.get(key);
					g.drawImage(thisEntity.getImage(client.getFrame()), thisEntity.getLocation().getX()
							- playerLocation.getX() + Client.WIDTH / 2 - thisEntity.getWidth() / 2, thisEntity
							.getLocation().getY()
							- playerLocation.getY()
							+ Client.HEIGHT
							/ 2
							- thisEntity.getHeight()
							/ 2, thisEntity.getWidth(), thisEntity.getHeight(), null);
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

	public boolean isReadyToRender() {
		return readyToRender;
	}

	public void setReadyToRender(boolean readyToRender) {
		this.readyToRender = readyToRender;
	}
}