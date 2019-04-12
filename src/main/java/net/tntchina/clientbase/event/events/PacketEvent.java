package net.tntchina.clientbase.event.events;

import net.minecraft.network.Packet;

/**
 * on the server send packet to client invoke event(�ڷ������������ݰ����ͻ���ʱ����Ӧ���¼�)
 * @author TNTChina
 */
public class PacketEvent extends CancellableEvent {
	
	private Packet<?> packet;// the packet(���ݰ�)
	
	public PacketEvent(Packet<?> packet) {
		this.packet = packet;
	}
	
	/**
	 * @return the server send packet(���������͵����ݰ�)
	 */
	public Packet<?> getPacket() {
		return this.packet;
	}
}
