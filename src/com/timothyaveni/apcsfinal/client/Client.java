package com.timothyaveni.apcsfinal.client;

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

	private GameFrame gameFrame;

	public Client() {
		keyListener = new ClientKeyListener(this);
		gameFrame = new GameFrame("Saviors of Gundthor", this);
		gameFrame.addKeyListener(keyListener);
		MenuPanel menu = new MenuPanel(gameFrame);
		gameFrame.changeFrame(menu);
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
			if (!inGame)
				continue;
			lastLoopTime = System.nanoTime();

			if (keyboard[0]) {
				player.setLocation(new Location(player.getLocation().getX() + player.getVelocity(), player
						.getLocation().getY(), Location.EAST));
			} else if (keyboard[1]) {
				player.setLocation(new Location(player.getLocation().getX(), player.getLocation().getY()
						- player.getVelocity(), Location.NORTH));
			} else if (keyboard[2]) {
				player.setLocation(new Location(player.getLocation().getX() - player.getVelocity(), player
						.getLocation().getY(), Location.WEST));
			} else if (keyboard[3]) {
				player.setLocation(new Location(player.getLocation().getX(), player.getLocation().getY()
						+ player.getVelocity(), Location.SOUTH));
			}

			// get player input
			// move sprites
			// render environment
			gameFrame.getMapCanvas().render();

			try {
				// Thread.sleep((long) (1000000000 / FPS - (System.nanoTime() -
				// lastLoopTime)));
				Thread.sleep(33);

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
