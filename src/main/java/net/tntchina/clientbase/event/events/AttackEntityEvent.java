package net.tntchina.clientbase.event.events;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

/**
 * on Player attacking entity event.(����ʵ�幥��ʵ���ʱ����õ��¼�)
 */
public class AttackEntityEvent extends CancellableEvent {
	
	private Minecraft mc;
	private EntityPlayer entity;
	private Entity attackTarget;
	private State state;
	
	public AttackEntityEvent(Minecraft mc, Entity attackTarget, EntityPlayer entity, State state) {
		this.mc = mc;
		this.attackTarget = attackTarget;
		this.entity = entity;
		this.state = state;
	}
	
	public Minecraft getMinecraft() {
		return this.mc;
	}
	
	public EntityPlayer getEntity() {
		return this.entity;
	}
	
	public Entity getAttackTarget() {
		return this.attackTarget;
	}
	
	public State getState() {
		return this.state;
	}
	
	public static enum State {
		PRE, POST;
	}
}
