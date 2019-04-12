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
	public int motionZ;// 这个字段只能在这个类中用  并非其他类
	
	public void setX(int motionX) {// 在Mixin应用后会调用
		this.motionX = motionX;
	}

	public void setY(int motionY) {
		this.motionY = motionY;
	}

	public void setZ(int motionZ) {
		this.motionZ = motionZ;
	}
}