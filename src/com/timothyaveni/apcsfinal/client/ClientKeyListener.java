package com.timothyaveni.apcsfinal.client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ClientKeyListener implements KeyListener {
	private Client client;

	public ClientKeyListener(Client client) {
		this.client = client;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_RIGHT:
				client.setKey(0, true);
				client.getPlayer().setMoving(true);
				break;
			case KeyEvent.VK_DOWN:
				client.setKey(1, true);
				client.getPlayer().setMoving(true);
				break;
			case KeyEvent.VK_LEFT:
				client.setKey(2, true);
				client.getPlayer().setMoving(true);
				break;
			case KeyEvent.VK_UP:
				client.setKey(3, true);
				client.getPlayer().setMoving(true);
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		switch (e.getKeyCode()) {
			case KeyEvent.VK_RIGHT:
				client.setKey(0, false);
				client.getPlayer().setMoving(false);
				break;
			case KeyEvent.VK_DOWN:
				client.setKey(1, false);
				client.getPlayer().setMoving(false);
				break;
			case KeyEvent.VK_LEFT:
				client.setKey(2, false);
				client.getPlayer().setMoving(false);
				break;
			case KeyEvent.VK_UP:
				client.setKey(3, false);
				client.getPlayer().setMoving(false);
				break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
