package com.timothyaveni.apcsfinal.client;

import java.awt.event.KeyListener;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.timothyaveni.apcsfinal.client.environmentanimation.EnvironmentAnimation;
import com.timothyaveni.apcsfinal.client.gui.GameFrame;
import com.timothyaveni.apcsfinal.client.gui.MenuPanel;
import com.timothyaveni.apcsfinal.networking.EntityType;
import com.timothyaveni.apcsfinal.networking.packet.EntityLocationPacket;

public class Client {
	private static final double FPS = 30.0;
	public static final int WIDTH = 1024;
	public static final int HEIGHT = WIDTH / 4 * 3;

	private static int lastLocalPacketId = 0;
	private boolean[] keyboard = new boolean[4];
	private java.util.Map<Integer, Entity> entities = Collections.synchronizedMap(new HashMap<Integer, Entity>());
	private List<Projectile> myProjectiles = Collections.synchronizedList(new ArrayList<Projectile>());
	private java.util.Map<Integer, Projectile> unacknowledgedProjectiles = Collections.synchronizedMap(new HashMap<Integer, Projectile>());
	private List<EnvironmentAnimation> environmentAnimations = Collections.synchronizedList(new ArrayList<EnvironmentAnimation>());
	private long frame = 0; // current frame number. Increments on each frame

	private boolean inGame = false;
	private Player player;
	private EntityType playerType;

	private ClientMouseListener mouseListener;

	private GameFrame gameFrame;

	private DatagramSocket socket;
	private ClientCallbackListener callbackListener;
	private InetAddress address;

	private ClientNetworkThread networkThread;

	private Map currentMap;

	public Client() {
		KeyListener keyListener = new ClientKeyListener(this);
		mouseListener = new ClientMouseListener(this);
		gameFrame = new GameFrame("Saviors of Gundthor", this);
		gameFrame.setKeyListener(keyListener);
		gameFrame.setMouseListener(mouseListener);
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
		long lastLoopTime = System.nanoTime();
		boolean isRunning = true;

		while (isRunning) {
			try {
				long tryDelay = ((long) (1000000000 / FPS) - (System.nanoTime() - lastLoopTime)) / 1000000;
				if (tryDelay > 0)
					Thread.sleep(tryDelay);
			} catch (InterruptedException e) {
			}

			if (!inGame)
				continue;
				
			lastLoopTime = System.nanoTime();

			if (keyboard[0] || keyboard[1] || keyboard[2] || keyboard[3]) {
				player.setMoving(true);
			} else
				player.setMoving(false);

			player.characterMove(keyboard, currentMap, entities);
			networkThread.sendPacket(new EntityLocationPacket(Client.getNextPacketId(), player.getId(), player
					.getLocation()));

			int nextMap = currentMap.getMetadata().getNextMap(player.getLocation().getX(), player.getLocation().getY());
			
			if (nextMap != -1) {
				this.currentMap = new Map(nextMap);
				player.setLocation(currentMap.getMetadata().getSpawnPoint()); // I think this is awkward if the player has active projectiles but whatever
			}
			
			if (frame % 20 == 0 && player.getHP() < player.getMaxHP()) {
				player.setHP(player.getHP() + 1);
			}

			updateMyProjectiles();

			getPlayer().updateAbility(frame);

			networkThread.checkUnacknowledgedPackets(); // if the server has taken too long to acknowledge any packets, this is where we resend them

			// get player input
			// move sprites
			gameFrame.getMapCanvas().render();

			frame++;
		}
	}

	private void updateMyProjectiles() {
		Projectile[] projectiles = myProjectiles.toArray(new Projectile[0]);
		for (int i = 0; i < projectiles.length; i++) {
			Projectile projectile = projectiles[i];
			projectile.update(this);
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

	public java.util.Map<Integer, Entity> getEntityList() {
		return entities;
	}

	public void setSocket(DatagramSocket socket) {
		this.socket = socket;
	}

	public void setRemoteInetAddress(InetAddress address) {
		this.address = address;
	}

	public InetAddress getRemoteInetAddress() {
		return address;
	}

	public void setNetworkThread(ClientNetworkThread thread) {
		this.networkThread = thread;
		Thread t = new Thread(networkThread);
		t.start();
	}

	public ClientNetworkThread getNetworkThread() {
		return networkThread;
	}

	public void setCallbackListener(ClientCallbackListener listener) {
		this.callbackListener = listener;
	}

	public synchronized static int getNextPacketId() {
		return lastLocalPacketId++; // starts at 0, so we can just
									// increment afterwards
	}

	public Map getCurrentMap() {
		return currentMap;
	}

	public void setCurrentMap(Map currentMap) {
		this.currentMap = currentMap;
	}

	public void setPlayerType(EntityType playerType) {
		this.playerType = playerType;
	}

	public EntityType getPlayerType() {
		return this.playerType;
	}

	public GameFrame getGameFrame() {
		return this.gameFrame;
	}

	public List<Projectile> getMyProjectiles() {
		return this.myProjectiles;
	}

	public java.util.Map<Integer, Projectile> getUnacknowledgedProjectiles() {
		return this.unacknowledgedProjectiles;
	}

	public List<EnvironmentAnimation> getEnvironmentAnimations() {
		return environmentAnimations;
	}
	
}