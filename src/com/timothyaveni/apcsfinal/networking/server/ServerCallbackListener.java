package com.timothyaveni.apcsfinal.networking.server;

import com.timothyaveni.apcsfinal.networking.packet.AcknowledgePacket;
import com.timothyaveni.apcsfinal.networking.packet.EnemyDamagePacket;
import com.timothyaveni.apcsfinal.networking.packet.PlayerLocationPacket;

public abstract class ServerCallbackListener {

	public abstract void bindSuccess();

	public abstract void bindFail();

	public abstract void receiveFailure();

	public abstract void playerMoved(PlayerLocationPacket p);

	public abstract void entityDamaged(EnemyDamagePacket packet);

	public abstract void packetAcknowledged(AcknowledgePacket packet);

}
