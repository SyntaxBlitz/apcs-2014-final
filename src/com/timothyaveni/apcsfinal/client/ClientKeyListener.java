package com.timothyaveni.apcsfinal.client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.timothyaveni.apcsfinal.networking.packet.EntityLocationPacket;

public class ClientKeyListener implements KeyListener {
	private Client client;

	public ClientKeyListener(Client client) {
		this.client = client;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (client.isInGame())
			switch (e.getKeyCode()) {
				case KeyEvent.VK_D:
					client.setKey(0, true);
					client.getPlayer().setMoving(true);
					break;
				case KeyEvent.VK_S:
					client.setKey(1, true);
					client.getPlayer().setMoving(true);
					break;
				case KeyEvent.VK_A:
					client.setKey(2, true);
					client.getPlayer().setMoving(true);
					break;
				case KeyEvent.VK_W:
					client.setKey(3, true);
					client.getPlayer().setMoving(true);
					break;
				case KeyEvent.VK_R:
					client.getPlayer().setLocation(client.getCurrentMap().getMetadata().getSpawnPoint());
					client.getPlayer().setHP(client.getPlayer().getMaxHP());
					client.getNetworkThread().sendPacket(
							new EntityLocationPacket(Client.getNextPacketId(), client.getPlayer().getId(), client
									.getPlayer().getLocation()));
					break;
			}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (client.isInGame())
			switch (e.getKeyCode()) {
				case KeyEvent.VK_D:
					client.setKey(0, false);
					client.getPlayer().setMoving(false);
					break;
				case KeyEvent.VK_S:
					client.setKey(1, false);
					client.getPlayer().setMoving(false);
					break;
				case KeyEvent.VK_A:
					client.setKey(2, false);
					client.getPlayer().setMoving(false);
					break;
				case KeyEvent.VK_W:
					client.setKey(3, false);
					client.getPlayer().setMoving(false);
					break;
			}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
