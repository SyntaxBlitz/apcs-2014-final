package com.timothyaveni.apcsfinal.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.timothyaveni.apcsfinal.client.Entity;
import com.timothyaveni.apcsfinal.client.EntityInfo;
import com.timothyaveni.apcsfinal.client.FileReader;
import com.timothyaveni.apcsfinal.client.MapMetadata;
import com.timothyaveni.apcsfinal.client.Player;
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

	private ArrayList<ConnectedClient> clientList = new ArrayList<ConnectedClient>();
	private static ArrayList<Packet> packetQueue = new ArrayList<Packet>();

	private HashMap<Integer, Entity> entities = new HashMap<Integer, Entity>();
	// entities that have been within the distance threshold of some player at
	// some point
	private HashMap<Integer, Entity> visibleEntities = new HashMap<Integer, Entity>();
	// entities that have not
	private ArrayList<Entity> invisibleEntities = new ArrayList<Entity>();

	private ArrayList<Player> players = new ArrayList<Player>();

	private HashMap<Integer, MapMetadata> loadedMaps = new HashMap<Integer, MapMetadata>();
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

	public ArrayList<ConnectedClient> getClientList() {
		return clientList;
	}

	public ServerThread getThread() { // triangular dependency? you bet your
										// ass.
		return this.thread;
	}

	private void mainLoop() {
		long tickStart;

		// load in first map
		loadMap(1);

		while (true) {
			tickStart = System.nanoTime();

			sendOutPackets();
			thread.checkUnacknowledgedPackets();

			for (Player player : players) {
				Iterator<Entity> i = invisibleEntities.iterator();
				while (i.hasNext()) {
					Entity entity = i.next();
					if (player.getLocation().getWorldSectionId() == entity.getLocation().getWorldSectionId()) {
						if (player.getLocation().getDistanceTo(entity.getLocation()) < visibilityThreshold) {
							addPacketToQueue(new NewEntityPacket(getNextPacketId(), entity));
							i.remove();
							visibleEntities.put(entity.getId(), entity);
						}
					}
				}
			}

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

	public static void main(String[] args) {
		int port = 21102;
		if (args.length > 0) {
			port = Integer.valueOf(args[0]);
		}
		Server server = new Server(port);
	}

	public void loadMap(int worldSectionId) {
		MapMetadata metadata = new MapMetadata(FileReader.getFileFromResourceString(WorldSectionID
				.getMapNameFromID(worldSectionId) + "_metadata.json"), worldSectionId);
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

	public HashMap<Integer, Entity> getEntityList() {
		return this.entities;
	}

	public HashMap<Integer, Entity> getVisibleEntityList() {
		return this.visibleEntities;
	}

	public ArrayList<Entity> getInvisibleEntityList() {
		return this.invisibleEntities;
	}

	public HashMap<Integer, MapMetadata> getLoadedMaps() {
		return this.loadedMaps;
	}

	public ArrayList<Player> getPlayerList() {
		return this.players;
	}

}
