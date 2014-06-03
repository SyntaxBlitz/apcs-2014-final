package com.timothyaveni.apcsfinal.client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ClientKeyListener implements KeyListener {
	private Client client;
	private boolean[] keyboard = new boolean[4];

	public ClientKeyListener(Client client) {
		this.client = client;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_RIGHT:
				client.setKey(0, true);
				break;
			case KeyEvent.VK_UP:
 				client.setKey(1, true);
				break;
			case KeyEvent.VK_LEFT:
				client.setKey(2, true);
				break;
			case KeyEvent.VK_DOWN:
				client.setKey(3, true);
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		switch (e.getKeyCode()) {
			case KeyEvent.VK_RIGHT:
				client.setKey(0, false);
				break;
			case KeyEvent.VK_UP:
 				client.setKey(1, false);
				break;
			case KeyEvent.VK_LEFT:
				client.setKey(2, false);
				break;
			case KeyEvent.VK_DOWN:
				client.setKey(3, false);
				break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
