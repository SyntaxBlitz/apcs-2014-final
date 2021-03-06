package com.timothyaveni.apcsfinal.client;

import java.util.HashMap;

import com.timothyaveni.apcsfinal.networking.AnimationTypeID;
import com.timothyaveni.apcsfinal.networking.EntityType;
import com.timothyaveni.apcsfinal.networking.EntityTypeID;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityLocationPacket;
import com.timothyaveni.apcsfinal.networking.packet.EnvironmentAnimationPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewClientAcknowledgementPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewEntityPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewProjectileAcknowledgePacket;
import com.timothyaveni.apcsfinal.networking.packet.SimpleAttackPacket;
import com.timothyaveni.apcsfinal.server.ArcanusEnemy;

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
			if (packet.getLocation().getWorldSectionId() == 0) {
				if (client.getEntityList().get(entityId) instanceof ArcanusEnemy) {
					client.setInGame(false);
					client.getGameFrame().close();
					client.getGameFrame().changeFrame(new EndgameFrame());
				}
				client.getEntityList().remove(entityId);
			} else {
				Entity entity = client.getEntityList().get(entityId);
				if (packet.getLocation().equals(entity.getLocation())) {
					entity.setMoving(false);
				} else {
					entity.setLocation(packet.getLocation());
					entity.setMoving(true);
				}
			}
		}
	}

	@Override
	public void entityDamaged(EntityDamagePacket packet) {
		Entity entity = client.getEntityList().get(packet.getEntityId());
		if (entity == client.getPlayer()) { // otherwise we don't care.
			int playerHP = client.getPlayer().getHP();
			int damageAmount = packet.getDamageAmount();
			if (playerHP - damageAmount > client.getPlayer().getMaxHP())
				client.getPlayer().setHP(client.getPlayer().getMaxHP());
			else
				client.getPlayer().setHP(playerHP - damageAmount);

			if (client.getPlayer().getHP() <= 0) {
				client.getPlayer().setLocation(client.getCurrentMap().getMetadata().getSpawnPoint());
				client.getPlayer().setHP(client.getPlayer().getMaxHP());
				client.getNetworkThread().sendPacket(
						new EntityLocationPacket(Client.getNextPacketId(), client.getPlayer().getId(), client
								.getPlayer().getLocation()));
			}
		}
	}

	@Override
	public void clientConnectionAcknowledged(NewClientAcknowledgementPacket packet) {
		// can't load in until the map is loaded.
		System.out.println("client connection acknowledged");
		while (client.getGameFrame().getMapCanvas() == null || !client.getGameFrame().getMapCanvas().isReadyToRender())
			try {
				Thread.sleep(10); // busy wait; this is ugly but I don't have
									// time to rework the entire project
			} catch (InterruptedException e) {
			}
		System.out.println("finished loading, about to load in map");

		Player player = (Player) EntityTypeID.constructEntity(client.getPlayerType(), packet.getPlayerEntityId(),
				client.getCurrentMap().getMetadata().getSpawnPoint());

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

	@Override
	public void simpleAttackAnimationUpdated(SimpleAttackPacket packet) {
		Entity entity = client.getEntityList().get(packet.getEntityId());
		if (entity != null) {
			entity.setStartedAttack(packet.isAttacking());
		}
	}

	@Override
	public void newProjectileAcknowledged(NewProjectileAcknowledgePacket packet) {
		Projectile projectile = client.getUnacknowledgedProjectiles().get(packet.getAcknowledgePacketId());
		if (projectile != null) {
			projectile.setId(packet.getNewProjectileId());
			client.getUnacknowledgedProjectiles().remove(packet.getAcknowledgePacketId());
			client.getMyProjectiles().add(projectile);
		}
	}

	@Override
	public void environmentAnimationStarted(EnvironmentAnimationPacket packet) {
		client.getEnvironmentAnimations().add(
				AnimationTypeID.constructAnimation(packet.getAnimationType(), client.getFrame(), packet.getLocation(),
						packet.getData()));
	}

}
