package com.timothyaveni.apcsfinal.server;

public interface BossAI extends EnemyAI {

	public void projectileAttack();

	public void summonMinions(Server server);

}
