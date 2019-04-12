package Mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import Mixin.impl.IS12PacketEntityVelocity;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

@Mixin(S12PacketEntityVelocity.class)
public abstract class MixinS12PacketEntityVelocity implements IS12PacketEntityVelocity {
		
	@Shadow
    public int motionX;
	
	@Shadow
    public int motionY;
    
	@Shadow
	public int motionZ;// ����ֶ�ֻ�������������  ����������
	
	public void setX(int motionX) {// ��MixinӦ�ú�����
		this.motionX = motionX;
	}

	public void setY(int motionY) {
		this.motionY = motionY;
	}

	public void setZ(int motionZ) {
		this.motionZ = motionZ;
	}
}