package com.timothyaveni.apcsfinal.client;

import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityLocationPacket;
import com.timothyaveni.apcsfinal.networking.packet.EnvironmentAnimationPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewClientAcknowledgementPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewEntityPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewProjectileAcknowledgePacket;
import com.timothyaveni.apcsfinal.networking.packet.SimpleAttackPacket;

public abstract class ClientCallbackListener {

	public abstract void receiveFailure();

	public abstract void entityMoved(EntityLocationPacket packet);

	public abstract void entityDamaged(EntityDamagePacket packet);

	public abstract void clientConnectionAcknowldged(NewClientAcknowledgementPacket packet);

	public abstract void newEntity(NewEntityPacket packet);

	public abstract void simpleAttackAnimationUpdated(SimpleAttackPacket packet);

	public abstract void newProjectileAcknowledged(NewProjectileAcknowledgePacket packet);

	public abstract void environmentAnimationStarted(EnvironmentAnimationPacket packet);
	
}
