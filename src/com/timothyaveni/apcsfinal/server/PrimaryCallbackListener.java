package com.timothyaveni.apcsfinal.server;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

import com.timothyaveni.apcsfinal.client.Entity;
import com.timothyaveni.apcsfinal.client.Location;
import com.timothyaveni.apcsfinal.networking.EntityTypeID;
import com.timothyaveni.apcsfinal.networking.packet.AcknowledgePacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityLocationPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewClientAcknowledgementPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewClientPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewEntityPacket;
import com.timothyaveni.apcsfinal.networking.server.ServerCallbackListener;

public class PrimaryCallbackListener extends ServerCallbackListener {

	private Server server;

	// key is player entity id, val is last location packet from player
	private HashMap<Integer, Integer> lastPlayerLocationId;

	public PrimaryCallbackListener(Server server) {
		this.server = server;
		this.lastPlayerLocationId = new HashMap<Integer, Integer>();
	}

	@Override
	public void bindSuccess() {
		System.out.println("Successfully bound to port");
	}

	@Override
	public void bindFail() {
		System.out.println("Did NOT successfully bind to port");
	}

	@Override
	public void receiveFailure() {
		System.out.println("Something went wrong while trying to receive packets");
	}

	@Override
	public void entityMoved(EntityLocationPacket packet) {
		int entityId = packet.getEntityId();

		Integer lastLocationID = lastPlayerLocationId.get(entityId);

		if (lastLocationID != null && lastLocationID > packet.getRemoteId())
			return;

		lastPlayerLocationId.put(entityId, packet.getRemoteId());
		if (server.getEntityList().contains(entityId)) {
			server.getEntityList().get(entityId).setLocation(packet.getLocation());
		}

		Server.addPacketToQueue(new EntityLocationPacket(Server.getNextPacketId(), entityId,
				packet.getWorldSectionId(), packet.getLocation()));
	}

	@Override
	public void entityDamaged(EntityDamagePacket packet) {
		// TODO Auto-generated method stub

	}

	@Override
	public void packetAcknowledged(AcknowledgePacket packet) {
		// TODO Auto-generated method stub

	}

	@Override
	public synchronized void newClientConnected(NewClientPacket packet, InetAddress address, int port) {
		int newClientId = server.getClientList().size();
		if (newClientId > 255) { // uh we can't fit you bro
			System.out.println("New client connected, but couldn't fit in client list");
			return;
		}
		System.out.println("New client connected. Assigning id " + newClientId);

		ArrayList<Entity> entities = server.getEntityList();
		int newEntityId = entities.size();
		Entity newEntity = EntityTypeID.constructEntity(packet.getEntityType(), newEntityId, new Location(600, 600, 1));
		// entities.add(EntityTypeID.constructEntity(packet.getEntityType(),
		// entities.size(), server.getMap(0).getMetadata().getSpawnPoint()));
		entities.add(newEntity);

		server.getClientList().add(new ConnectedClient(newClientId, address, port));
		server.getThread().sendIndividualPacket(
				new NewClientAcknowledgementPacket(Server.getNextPacketId(), newClientId, newEntityId), address, port);

		Server.addPacketToQueue(new NewEntityPacket(Server.getNextPacketId(), newEntity));

		Entity[] entityArray = entities.toArray(new Entity[entities.size()]);
		for (int i = 0; i < entityArray.length; i++) { // have to send the new client all the entities we already have in memory
			server.getThread().sendIndividualPacket(new NewEntityPacket(Server.getNextPacketId(), entityArray[i]), address,
					port);
		}
	}

}
