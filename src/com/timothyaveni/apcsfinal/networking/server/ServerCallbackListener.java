package com.timothyaveni.apcsfinal.networking.server;

import java.net.InetAddress;

import com.timothyaveni.apcsfinal.networking.packet.AcknowledgePacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityDamagePacket;
import com.timothyaveni.apcsfinal.networking.packet.NewClientPacket;
import com.timothyaveni.apcsfinal.networking.packet.EntityLocationPacket;

public abstract class ServerCallbackListener {

	public abstract void bindSuccess();

	public abstract void bindFail();

	public abstract void receiveFailure();

	public abstract void entityMoved(EntityLocationPacket packet);

	public abstract void entityDamaged(EntityDamagePacket packet);

	public abstract void packetAcknowledged(AcknowledgePacket packet);

	public abstract void newClientConnected(NewClientPacket packet, InetAddress address, int port);

}
