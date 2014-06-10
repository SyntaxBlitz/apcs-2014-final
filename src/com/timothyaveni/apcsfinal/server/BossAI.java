package com.timothyaveni.apcsfinal.server;

public interface BossAI extends EnemyAI {

	public void projectileAttack(Server server);

	public void summonMinions(Server server);

}
