package com.timothyaveni.apcsfinal.client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ClientKeyListener implements KeyListener {
	private Client client;
	private boolean[] keyboard = new boolean[4];
	@Override
	public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case 39:
				keyboard[0] = true;
				break;
			case 38:
				keyboard[1] = true;
				break;
			case 37:
				keyboard[2] = true;
				break;
			case 40:
				keyboard[3] = true;
				break;
			}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
			switch (e.getKeyCode()) {
			case 39:
				keyboard[0] = false;
				break;
			case 38:
				keyboard[1] = false;
				break;
			case 37:
				keyboard[2] = false;
				break;
			case 40:
				keyboard[3] = false;
				break;
			}
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	public boolean[] getKeyboard(){
		return keyboard;
	}			

}