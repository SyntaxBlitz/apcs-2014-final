package com.timothyaveni.apcsfinal.server;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.timothyaveni.apcsfinal.client.Entity;
import com.timothyaveni.apcsfinal.client.Location;
import com.timothyaveni.apcsfinal.client.MapMetadata;
import com.timothyaveni.apcsfinal.client.Player;
import com.timothyaveni.apcsfinal.client.Projectile;
import com.timothyaveni.apcsfinal.networking.EntityTypeID;
import com.timothyaveni.apcsfinal.networking.WorldSectionID;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityLocationPacket;
import com.timothyaveni.apcsfinal.networking.packet.EnvironmentAnimationPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewClientAcknowledgementPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewClientPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewEntityPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewProjectileAcknowledgePacket;
import com.timothyaveni.apcsfinal.networking.packet.NewProjectilePacket;
import com.timothyaveni.apcsfinal.networking.packet.SimpleAttackPacket;
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
		if (server.getEntityList().containsKey(entityId)) {
			server.getEntityList().get(entityId).setLocation(packet.getLocation());
		}

		if (packet.getLocation().getWorldSectionId() != 0
				&& !server.getLoadedMaps().containsKey(packet.getLocation().getWorldSectionId())) { // will probably never happen.
			try {
				server.getLoadedMaps().put(
						packet.getLocation().getWorldSectionId(),
						new MapMetadata(WorldSectionID.getMapNameFromID(packet.getLocation().getWorldSectionId()),
								packet.getLocation().getWorldSectionId()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Server.addPacketToQueue(new EntityLocationPacket(Server.getNextPacketId(), entityId, packet.getLocation()));
	}

	@Override
	public void entityDamaged(EntityDamagePacket packet) {
		Entity entity = server.getEntityList().get(packet.getEntityId());
		if (entity != null) {
			int entityHP = entity.getHP();
			int damageAmount = packet.getDamageAmount();
			if (entityHP - damageAmount > entity.getMaxHP())
				entity.setHP(entity.getMaxHP());
			else
				entity.setHP(entityHP - damageAmount);

			if (entity instanceof Player) {
				// just send it to everyone
				Server.addPacketToQueue(new EntityDamagePacket(Server.getNextPacketId(), packet.getEntityId(),
						damageAmount));
			} else if (entity.getHP() < 0) {
				EntityLocationPacket toSend = new EntityLocationPacket(Server.getNextPacketId(), packet.getEntityId(),
						new Location(0, 0, 0, 0));
				toSend.setMustAcknowledge(true); // this is a particularly important location packet because this is how the client knows to remove the entity
				Server.addPacketToQueue(toSend);

				server.getEntityList().remove(packet.getEntityId());
				server.getVisibleEntityList().remove(packet.getEntityId());
			}
		}
	}

	@Override
	public synchronized void newClientConnected(NewClientPacket packet, InetAddress address, int port) {
		int newClientId = server.getClientList().size();
		if (newClientId > 255) { // uh we can't fit you bro
			System.out.println("New client connected, but couldn't fit in client list");
			return;
		}
		System.out.println("New client connected. Assigning id " + newClientId);

		HashMap<Integer, Entity> entities = server.getEntityList();
		int newEntityId = Server.getNextEntityId();
		Entity newEntity = EntityTypeID.constructEntity(packet.getEntityType(), newEntityId, server.getLoadedMaps()
				.get(1).getSpawnPoint());
		entities.put(newEntityId, newEntity);
		server.getVisibleEntityList().put(newEntityId, newEntity);
		server.getPlayerList().add((Player) newEntity);

		server.getClientList().add(new ConnectedClient(newClientId, address, port));
		server.getThread().sendIndividualPacket(
				new NewClientAcknowledgementPacket(Server.getNextPacketId(), newClientId, newEntityId), address, port);

		Server.addPacketToQueue(new NewEntityPacket(Server.getNextPacketId(), newEntity));

		Entity[] visibleEntities = server.getVisibleEntityList().values().toArray(new Entity[0]);
		for (Entity entity: visibleEntities) {
			server.getThread().sendIndividualPacket(new NewEntityPacket(Server.getNextPacketId(), entity),
					address, port);
		}
	}

	@Override
	public void simpleAttackAnimationUpdated(SimpleAttackPacket packet) {
		Entity entity = server.getEntityList().get(packet.getEntityId());
		if (entity != null) {
			entity.setStartedAttack(packet.isAttacking());
		}
		Server.addPacketToQueue(new SimpleAttackPacket(Server.getNextPacketId(), packet.getEntityId(), packet
				.isAttacking()));
	}

	@Override
	public void projectileIdRequested(NewProjectilePacket packet, InetAddress address, int port) {
		int entityId = Server.getNextEntityId();
		Projectile projectile = (Projectile) EntityTypeID.constructEntity(packet.getType(), entityId,
				packet.getLocation());
		server.getVisibleEntityList().put(entityId, projectile);
		server.getEntityList().put(entityId, projectile);

		server.getThread().sendIndividualPacket(
				new NewProjectileAcknowledgePacket(Server.getNextPacketId(), packet.getRemoteId(), entityId), address,
				port);
		Server.addPacketToQueue(new NewEntityPacket(Server.getNextPacketId(), projectile));
	}

	@Override
	public void environmentAnimationStarted(EnvironmentAnimationPacket packet) {
		// just let everyone know. it's an animation, so we don't care
		Server.addPacketToQueue(new EnvironmentAnimationPacket(Server.getNextPacketId(), packet.getAnimationType(),
				packet.getLocation(), packet.getData()));
	}

}
