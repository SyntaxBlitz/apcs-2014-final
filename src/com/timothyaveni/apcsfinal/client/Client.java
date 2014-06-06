package com.timothyaveni.apcsfinal.client;

import java.awt.event.KeyListener;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

import com.timothyaveni.apcsfinal.client.gui.GameFrame;
import com.timothyaveni.apcsfinal.client.gui.MenuPanel;

public class Client {
	private static final double FPS = 30.0;
	private boolean[] keyboard = new boolean[4];
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private long frame = 0; // current frame number. Increments on each frame

	private boolean inGame = false;
	private Player player;
	private ClientMouseListener mouseListener;
	
	private GameFrame gameFrame;
	
	private DatagramSocket socket;
	private ClientCallbackListener callbackListener;
	private InetAddress address;
	
	private ClientNetworkThread networkThread;

	public Client() {		
		KeyListener keyListener = new ClientKeyListener(this);
		mouseListener = new ClientMouseListener(this);
		gameFrame = new GameFrame("Saviors of Gundthor", this);
		gameFrame.addKeyListener(keyListener);
		gameFrame.addMouseListener(mouseListener);
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

			if (keyboard[0] || keyboard[1] || keyboard[2] || keyboard[3]) {
				player.setMoving(true);
			} else
				player.setMoving(false);

			if (frame - mouseListener.getFrameClicked() >= 20)
				player.setInCombat(false);

			player.characterMove(keyboard);

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
	
	public void setSocket(DatagramSocket socket) {
		this.socket = socket;
	}
	
	public void setRemoteInetAddress(InetAddress address) {
		this.address = address;
	}
	
	public void setNetworkThread(ClientNetworkThread thread) {
		this.networkThread = thread;
	}
	
	public void setCallbackListener(ClientCallbackListener listener) {
		this.callbackListener = listener;
	}
}
// testing something