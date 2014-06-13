package com.timothyaveni.apcsfinal.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.timothyaveni.apcsfinal.client.Entity;
import com.timothyaveni.apcsfinal.client.EntityInfo;
import com.timothyaveni.apcsfinal.client.MapMetadata;
import com.timothyaveni.apcsfinal.client.Player;
import com.timothyaveni.apcsfinal.client.Projectile;
import com.timothyaveni.apcsfinal.networking.EntityTypeID;
import com.timothyaveni.apcsfinal.networking.WorldSectionID;
import com.timothyaveni.apcsfinal.networking.packet.NewEntityPacket;
import com.timothyaveni.apcsfinal.networking.packet.Packet;
import com.timothyaveni.apcsfinal.networking.server.ServerThread;

public class Server {

	private int port;
	private final double TPS = 30.0;
	private final int WIDTH = 1024;
	private final int HEIGHT = 1024 / 4 * 3;
	int visibilityThreshold = (int) Math.sqrt((WIDTH / 2) * (WIDTH / 2) + (HEIGHT / 2) * (HEIGHT / 2)) + 100;

	private List<ConnectedClient> clientList = Collections.synchronizedList(new ArrayList<ConnectedClient>());
	private static List<Packet> packetQueue = Collections.synchronizedList(new ArrayList<Packet>());

	private Map<Integer, Entity> entities = Collections.synchronizedMap(new HashMap<Integer, Entity>());
	// entities that have been within the distance threshold of some player at
	// some point
	private Map<Integer, Entity> visibleEntities = Collections.synchronizedMap(new HashMap<Integer, Entity>());
	// entities that have not
	private List<Entity> invisibleEntities = Collections.synchronizedList(new ArrayList<Entity>());

	private List<Player> players = Collections.synchronizedList(new ArrayList<Player>());

	private List<Projectile> myProjectiles = Collections.synchronizedList(new ArrayList<Projectile>());

	private Map<Integer, MapMetadata> loadedMaps = Collections.synchronizedMap(new HashMap<Integer, MapMetadata>());
	private static int lastEntityId = 0;

	private ServerThread thread;

	private DatagramSocket socket;

	private long currentTick = 0;

	private static int lastLocalPacketId = 0;

	public Server(int port) {
		PrimaryCallbackListener listener = new PrimaryCallbackListener(this);

		try {
			socket = new DatagramSocket(port);
			listener.bindSuccess();
		} catch (SocketException e) {
			listener.bindFail();
		}

		thread = new ServerThread(socket, listener, this);

		Thread runThread = new Thread(thread);
		runThread.start();

		mainLoop();
	}

	public List<ConnectedClient> getClientList() {
		return clientList;
	}

	public ServerThread getThread() { // triangular dependency? you bet your
										// ass.
		return this.thread;
	}

	private void mainLoop() {
		long tickStart;

		// easiest to just load in all the maps at once
		loadMap(1);
		loadMap(2);
		loadMap(3);

		while (true) {
			tickStart = System.nanoTime();

			sendOutPackets();
			thread.checkUnacknowledgedPackets();

			Player[] playerArray = players.toArray(new Player[0]);
			for (Player player : playerArray) {
				Entity[] invisibleEntityArray = invisibleEntities.toArray(new Entity[0]); 
				for (Entity entity: invisibleEntityArray) {
					if (player.getLocation().getWorldSectionId() == entity.getLocation().getWorldSectionId()) {
						if (player.getLocation().getDistanceTo(entity.getLocation()) < visibilityThreshold) {
							addPacketToQueue(new NewEntityPacket(getNextPacketId(), entity));
							invisibleEntities.remove(entity);
							visibleEntities.put(entity.getId(), entity);
						}
					}
				}
			}

			Entity[] visibleEntityArray = visibleEntities.values().toArray(new Entity[0]);
			for (int i = 0; i < visibleEntityArray.length; i++) {
				if (visibleEntityArray[i] instanceof EnemyAI) {
					EnemyAI thisEnemy = (EnemyAI) visibleEntityArray[i];
					thisEnemy.act(this);
				}
			}

			updateMyProjectiles();

			try {
				long tryDelay = ((long) (1000000000 / TPS) - (System.nanoTime() - tickStart)) / 1000000;
				if (tryDelay > 0)
					Thread.sleep(tryDelay);
			} catch (InterruptedException e) {
			}
			currentTick++;
		}
	}

	public void sendOutPackets() {
		Packet[] sendPackets = packetQueue.toArray(new Packet[0]);
		packetQueue.clear();
		for (int i = 0; i < sendPackets.length; i++) {
			Packet packet = sendPackets[i];
			byte[] data = packet.getByteArray();
			for (ConnectedClient client : clientList) {
				DatagramPacket outPacket = new DatagramPacket(data, data.length, client.getAddress(), client.getPort());
				try {
					socket.send(outPacket);
				} catch (IOException e) {
					// cry
				}
			}
		}
	}

	private void updateMyProjectiles() {
		Projectile[] projectiles = myProjectiles.toArray(new Projectile[0]);
		for (int i = 0; i < projectiles.length; i++) {
			Projectile projectile = projectiles[i];
			projectile.update(this);
		}
	}

	public static void main(String[] args) {
		int port = 21102;
		if (args.length > 0) {
			port = Integer.valueOf(args[0]);
		}
		Server server = new Server(port);
	}

	public void loadMap(int worldSectionId) {
		System.out.println(worldSectionId);
		MapMetadata metadata = null;
		try {
			metadata = new MapMetadata(WorldSectionID.getMapNameFromID(worldSectionId), worldSectionId);
		} catch (IOException e) {
			e.printStackTrace();
		}
		loadedMaps.put(worldSectionId, metadata);
		List<EntityInfo> entityInfo = metadata.getEntityInfo();
		for (EntityInfo thisEntity : entityInfo) {
			Entity entity = EntityTypeID.constructEntity(thisEntity.getType(), Server.getNextEntityId(),
					thisEntity.getLocation());
			this.entities.put(entity.getId(), entity);
			this.invisibleEntities.add(entity);
		}
	}

	// really I have no clue why these two methods are synchronized
	public synchronized static int getNextPacketId() {
		return lastLocalPacketId++; // starts at 0, so we can just increment
									// afterwards
	}

	public synchronized static int getNextEntityId() {
		return lastEntityId++;
	}

	public static void addPacketToQueue(Packet packet) {
		packetQueue.add(packet);
	}

	public long getCurrentTick() {
		return currentTick;
	}

	public Map<Integer, Entity> getEntityList() {
		return this.entities;
	}

	public Map<Integer, Entity> getVisibleEntityList() {
		return this.visibleEntities;
	}

	public List<Entity> getInvisibleEntityList() {
		return this.invisibleEntities;
	}

	public Map<Integer, MapMetadata> getLoadedMaps() {
		return this.loadedMaps;
	}

	public List<Player> getPlayerList() {
		return this.players;
	}

	public List<Projectile> getMyProjectiles() {
		return myProjectiles;
	}
}
