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
			Entity entity = client.getEntityList().get(entityId);
			if (packet.getLocation().equals(entity.getLocation())) {
				entity.setMoving(false);
			} else {
				entity.setLocation(packet.getLocation());
				entity.setMoving(true);
			}

		}
	}

	@Override
	public void entityDamaged(EntityDamagePacket packet) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clientConnectionAcknowldged(NewClientAcknowledgementPacket packet) {
		Player player = (Player) EntityTypeID.constructEntity(client.getPlayerType(), packet.getPlayerEntityId(),
				new Location(600, 600, 1));
		client.setPlayer(player);
		client.getEntityList().put(packet.getPlayerEntityId(), player);

		client.setInGame(true);
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
