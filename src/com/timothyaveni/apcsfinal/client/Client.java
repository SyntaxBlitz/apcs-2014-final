package com.timothyaveni.apcsfinal.client;

import java.awt.Container;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import com.timothyaveni.apcsfinal.client.gui.GameFrame;
import com.timothyaveni.apcsfinal.client.gui.MenuPanel;

public class Client {
	private static final double FPS = 30.0;
	private boolean[] keyboard = new boolean[4];
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private long frame = 0; // current frame number. Increments on each frame
	private KeyListener keyListener;
	
	private boolean inGame = false;
	private Player player;

	public Client() {
		keyListener = new ClientKeyListener(this);
		GameFrame newGame = new GameFrame("Saviors of Gundthor", this);
		MenuPanel menu = new MenuPanel(newGame);
		newGame.changeFrame(menu);
	}

	/**
	 * render environment
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// display GUI
		Client client = new Client();
		client.gameLoop();
	}

	public void gameLoop() {
		long lastLoopTime;
		boolean isRunning = true;

		while (isRunning) {
			lastLoopTime = System.nanoTime();

			// get player input
			// move sprites
			// render environment

			try {
				Thread.sleep((long) (1000000000 / FPS - (System.nanoTime() - lastLoopTime)));

			} catch (InterruptedException e) {
			}

			frame++;
		}
	}

	public long getFrame() {
		return this.frame;
	}
	
	public void setKey(int index, boolean value) {
		keyboard[index] = value;
	}
	
	public boolean isInGame() {
		return inGame;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<Entity> getEntityList() {
		return entities;
	}

}
