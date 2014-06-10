package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.timothyaveni.apcsfinal.client.Client;
import com.timothyaveni.apcsfinal.client.Entity;
import com.timothyaveni.apcsfinal.client.Location;
import com.timothyaveni.apcsfinal.client.Projectile;

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

				Entity[] entityArray = entities.values().toArray(new Entity[0]);
				for(int i = 0; i < entityArray.length; i++) {
					Entity thisEntity = entityArray[i];
					if (thisEntity instanceof Projectile)
						continue;
					g.drawImage(thisEntity.getImage(client.getFrame()), thisEntity.getLocation().getX()
							- playerLocation.getX() + Client.WIDTH / 2 - thisEntity.getWidth() / 2, thisEntity
							.getLocation().getY()
							- playerLocation.getY()
							+ Client.HEIGHT
							/ 2
							- thisEntity.getHeight()
							/ 2, thisEntity.getWidth(), thisEntity.getHeight(), null);
				}

				for(int i = 0; i < entityArray.length; i++) {
					Entity thisEntity = entityArray[i];
					if (!(thisEntity instanceof Projectile))
						continue;
					g.drawImage(thisEntity.getImage(client.getFrame()), thisEntity.getLocation().getX()
							- playerLocation.getX() + Client.WIDTH / 2 - thisEntity.getWidth() / 2, thisEntity
							.getLocation().getY()
							- playerLocation.getY()
							+ Client.HEIGHT
							/ 2
							- thisEntity.getHeight()
							/ 2, thisEntity.getWidth(), thisEntity.getHeight(), null);
				}

				renderHPBar(g);

				g.dispose();
			} while (bs.contentsRestored());
		} while (bs.contentsLost());
		bs.show();
	}

	private void renderHPBar(Graphics g) {
		g.setColor(new Color(96, 96, 96));
		g.fillRoundRect(30, Client.HEIGHT - 60, Client.WIDTH / 3, 16, 10, 10);
		g.setColor(new Color(128, 0, 0));
		g.fillRoundRect(30, Client.HEIGHT - 60, (Client.WIDTH / 3)
				* (client.getPlayer().getHP() / client.getPlayer().getMaxHP()), 16, 10, 10);
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