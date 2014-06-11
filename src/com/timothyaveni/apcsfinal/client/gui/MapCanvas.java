package com.timothyaveni.apcsfinal.client.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import com.timothyaveni.apcsfinal.client.Client;
import com.timothyaveni.apcsfinal.client.Entity;
import com.timothyaveni.apcsfinal.client.Location;
import com.timothyaveni.apcsfinal.client.Tank;
import com.timothyaveni.apcsfinal.client.environmentanimation.EnvironmentAnimation;

public class MapCanvas extends Canvas implements UsesClient {
	private static final long serialVersionUID = 1L;
	private Client client;

	private BufferStrategy bs;
	private Font font = new Font("Arial", Font.PLAIN, 12);

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
				for (int i = 0; i < entityArray.length; i++) {
					Entity thisEntity = entityArray[i];
					if (thisEntity.getLocation().getWorldSectionId() != client.getCurrentMap().getWorldSectionId())
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

				EnvironmentAnimation[] environmentAnimations = client.getEnvironmentAnimations().toArray(
						new EnvironmentAnimation[0]);
				for (int i = 0; i < environmentAnimations.length; i++) {
					EnvironmentAnimation thisAnimation = environmentAnimations[i];
					if (!thisAnimation.stillRelevant(client.getFrame())) {
						System.out.println(client.getEnvironmentAnimations().remove(thisAnimation));
						continue;
					}
					BufferedImage renderImage = thisAnimation.getImage(client.getFrame());
					g.drawImage(thisAnimation.getImage(client.getFrame()), thisAnimation.getLocation().getX()
							- playerLocation.getX() + Client.WIDTH / 2 - renderImage.getWidth() / 2, thisAnimation
							.getLocation().getY()
							- playerLocation.getY()
							+ Client.HEIGHT
							/ 2
							- renderImage.getHeight()
							/ 2, renderImage.getWidth(), renderImage.getHeight(), null);
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
		g.fillRoundRect(30, Client.HEIGHT - 60,
				(int) ((Client.WIDTH / 3) * ((double) client.getPlayer().getHP() / client.getPlayer().getMaxHP())), 16,
				10, 10);

		String renderString = client.getPlayer().getHP() + "/" + client.getPlayer().getMaxHP();
		g.setColor(Color.WHITE);
		if ((client.getPlayer() instanceof Tank) && ((Tank) client.getPlayer()).isAbilityActive())
			g.setColor(new Color(128, 255, 255));
		g.setFont(font);

		g.drawString(renderString, Client.WIDTH / 3 - 40, Client.HEIGHT - 47);

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