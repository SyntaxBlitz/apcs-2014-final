package com.timothyaveni.apcsfinal.client;

import com.timothyaveni.apcsfinal.networking.packet.AcknowledgePacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityLocationPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewClientAcknowledgementPacket;
import com.timothyaveni.apcsfinal.networking.packet.NewEntityPacket;

public abstract class ClientCallbackListener {

	public abstract void receiveFailure();

	public abstract void entityMoved(EntityLocationPacket packet);

	public abstract void entityDamaged(EntityDamagePacket packet);

	public abstract void clientConnectionAcknowldged(NewClientAcknowledgementPacket packet);

	public abstract void newEntity(NewEntityPacket packet);

}
