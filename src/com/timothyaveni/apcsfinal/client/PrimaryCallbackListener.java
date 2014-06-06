package com.timothyaveni.apcsfinal.client;

import java.util.HashMap;

import com.timothyaveni.apcsfinal.networking.EntityTypeID;
import com.timothyaveni.apcsfinal.networking.packet.AcknowledgePacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityLocationPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewClientAcknowledgementPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewEntityPacket;

public class PrimaryCallbackListener extends ClientCallbackListener {

	private Client client;

	// key is entity id, value is the id of the last Location packet we've
	// gotten about that entity.
	private HashMap<Integer, Integer> lastEntityLocationId = new HashMap<Integer, Integer>();

	public PrimaryCallbackListener(Client client) {
		this.client = client;
	}

	@Override
	public void receiveFailure() {
		System.out.println("There was a failure in receiving packets");
	}

	@Override
	public void entityMoved(EntityLocationPacket packet) {
		int entityId = packet.getEntityId();

		Integer lastLocationId = lastEntityLocationId.get(entityId);

		if ((lastLocationId != null && lastLocationId > packet.getRemoteId())
				|| client.getPlayer().getId() == packet.getEntityId())
			return;

		lastEntityLocationId.put(entityId, packet.getRemoteId());
		if (client.getEntityList().containsKey(entityId)) {
			client.getEntityList().get(entityId).setLocation(packet.getLocation());
		}
	}

	@Override
	public void entityDamaged(EntityDamagePacket packet) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clientConnectionAcknowldged(NewClientAcknowledgementPacket packet) {
		System.out.println("My name is George and my id is " + packet.getPlayerEntityId());
		Player player = new Tank(packet.getPlayerEntityId(), new Location(600, 600, 1));
		client.setPlayer(player);
		client.getEntityList().put(packet.getPlayerEntityId(), player);
	}

	@Override
	public void newEntity(NewEntityPacket packet) {
		System.out.println("new entity: " + packet.getEntityId());
		if (client.getEntityList().containsKey(packet.getEntityId()))
			return;
		client.getEntityList().put(packet.getEntityId(),
				EntityTypeID.constructEntity(packet.getEntityType(), packet.getEntityId(), packet.getEntityLocation()));
	}

}
