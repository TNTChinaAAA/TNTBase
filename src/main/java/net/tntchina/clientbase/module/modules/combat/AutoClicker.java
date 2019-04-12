package net.tntchina.clientbase.module.modules.combat;

import org.lwjgl.input.Mouse;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;
import net.tntchina.clientbase.value.values.NumberValue;
import net.tntchina.util.MathHelper;
import net.tntchina.util.TimeHelper;

public class AutoClicker extends Module {

	private NumberValue CPS = new NumberValue(10.0D, "CPS", 20.0D, 1.0D);
	private TimeHelper timer = new TimeHelper();
	
	public AutoClicker(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
	public void onUpdate() {
		if (!this.getState()) {
			return;
		}
		
		if (this.timer.hasTimeReached(MathHelper.div(1000, this.CPS.getObject().doubleValue()))) {
			this.mc.gameSettings.keyBindAttack.pressed = true;
			
			if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit != null && this.mc.objectMouseOver.typeOfHit == MovingObjectType.ENTITY) {
				this.mc.thePlayer.swingItem();
				this.mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(this.mc.objectMouseOver.entityHit, Action.ATTACK));
			}
			
			this.timer.setLastMS();
		} else {
			if (Mouse.getEventButtonState()) {
				this.mc.gameSettings.keyBindAttack.pressed = Mouse.isButtonDown(1);
			}
		}
	}
}