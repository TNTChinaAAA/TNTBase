package net.tntchina.clientbase.event.events;

import net.minecraft.network.Packet;

/**
 * on the server send packet to client invoke event(在服务器发送数据包到客户端时候响应的事件)
 * @author TNTChina
 */
public class PacketEvent extends CancellableEvent {
	
	private Packet<?> packet;// the packet(数据包)
	
	public PacketEvent(Packet<?> packet) {
		this.packet = packet;
	}
	
	/**
	 * @return the server send packet(服务器发送的数据包)
	 */
	public Packet<?> getPacket() {
		return this.packet;
	}
}
