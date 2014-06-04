package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.List;

import com.timothyaveni.apcsfinal.client.Client;
import com.timothyaveni.apcsfinal.client.Entity;
import com.timothyaveni.apcsfinal.client.Location;

public class MapCanvas extends Canvas implements UsesClient {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1024;
	public static final int HEIGHT = WIDTH / 4 * 3;
	private Client client;

	private Map map;
	private BufferStrategy strategy;

	// Constructor :D
	public MapCanvas() {
		super();
		map = new Map("Map1.png");
		this.createBufferStrategy(2);
		strategy = this.getBufferStrategy();
	}

	public void render() {
		do {
			do {
				Graphics g = strategy.getDrawGraphics();
				BufferedImage buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
				Graphics2D bufferGraphics = buffer.createGraphics();

				Location playerLocation = client.getPlayer().getLocation();
				bufferGraphics.drawImage(map.getPic(playerLocation), 0, 0, 1024, 768, null);

				List<Entity> entities = client.getEntityList();

				for (int i = 0; i < entities.size(); i++) {
					Entity thisEntity = entities.get(i);
					bufferGraphics.drawImage(thisEntity.getImage(client.getFrame()), thisEntity.getLocation().getX()
							- playerLocation.getX() + WIDTH / 2 - thisEntity.getWidth() / 2, thisEntity.getLocation()
							.getY() - playerLocation.getY() + HEIGHT / 2 - thisEntity.getHeight() / 2,
							thisEntity.getWidth(), thisEntity.getHeight(), null);
				}

				g.drawImage(buffer, 0, 0, null);

				// pull arraylist down off client
				// misc comment so I'm able to commit
				g.dispose(); // releases system resources the graphics object is
			} while (strategy.contentsRestored());
			strategy.show();
		} while (strategy.contentsLost());
	}

	@Override
	public void setClient(Client client) {
		this.client = client;
	}
}