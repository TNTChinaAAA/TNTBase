package net.tntchina.clientbase.module;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.lwjgl.input.Keyboard;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Session;
import net.tntchina.clientbase.event.EventListener;
import net.tntchina.clientbase.event.EventManager;
import net.tntchina.clientbase.event.events.MotionUpdateEvent;
import net.tntchina.clientbase.module.modules.render.HUD;
import net.tntchina.clientbase.module.modules.render.HUD.Information;
import net.tntchina.clientbase.value.Value;
import net.tntchina.util.*;

/**
 * the module's abstract super.
 * @author TNTChina
 */
public abstract class Module implements EventListener, Runnable {
	
	public volatile Minecraft mc = Minecraft.getMinecraft();
	public volatile MinecraftServer server = MinecraftServer.getServer();
	public volatile String name = "";
	public volatile String description = "";
	public volatile int KeyBind = 0;
	public volatile ModuleCategory category = ModuleCategory.COMBAT;
	public volatile boolean state = false;
	public volatile Logger logger;
	public volatile Random random = new Random();
	public volatile Thread moduleThread;
	public volatile TimeUtil timeUtil;
	
	/**
	 * @return this module's description
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * set the desription
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @param name ����
	 * @param key ����
	 * @param Category �ĸ����� 
	 * @param description ע��
	 */
	public Module(String name, int key, ModuleCategory Categorys, String description) {
		this(name, key, Categorys);
		this.description = description;
	}
	
	/**
	 * @param name ����
	 * @param key ����
	 * @param Category �ĸ����� 
	 */
	public Module(String name, int key, ModuleCategory Categorys) {
		this.name = name;
		this.KeyBind = key;
		this.category = Categorys;
		this.state = false;
		this.logger = LogManager.getLogger(name);
		this.moduleThread = new Thread(this);
		this.timeUtil = new TimeUtil();
		EventManager.registerListener(this);
	}
	
	/**
	 * @param name ����
	 * @param key ����
	 *    ������캯�� ������û�а���
	 */
	public Module(String name, ModuleCategory categorys) {
		this(name, 0, categorys);
	}
	
	/**
	 * ������캯�������������������û�����û�а���
	 * @param name
	 */
	public Module(String name) {
		this(name, ModuleCategory.COMBAT);
	}
	
	/**
	 * @return Minecraft thePlayer (�ҵ�����ͻ��˵����)
	 */
	public EntityPlayerSP getPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}
	
	/**
	 * @return The keyboard enter key(������Ŀǰ�İ���)
	 */
	protected int getCurrentKey() {
		return Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();
	}
	
	/**
	 * ��� ������ 
	 */
	public void info(Object obj) {
		if (this.logger == null) {
			return;
		}
		
		this.logger.info(obj);
	}
	
	public void run() {
		if (this.getState()) {
			;
		} else {
			return;
		}
	}
	
	public void start() {
		this.moduleThread.start();
	}
	
	/**
	 * �����ٶ� 
	 */
	public void setSpeed(double speed) {
		double direction = this.getDirection();
		double currentMotion = Math.sqrt(this.mc.thePlayer.motionX * this.mc.thePlayer.motionX + this.mc.thePlayer.motionZ * this.mc.thePlayer.motionZ);
		this.mc.thePlayer.motionX = -Math.sin(direction) * speed * currentMotion;
		this.mc.thePlayer.motionZ = Math.cos(direction) * speed * currentMotion;
	}
	
	/**
	 * @return The player has forward(����Ƿ��ƶ�)
	 */
	public boolean isForward() {
		return this.mc.thePlayer.moveForward > 0.0F | this.mc.gameSettings.keyBindForward.isKeyDown();
	}
	
    public float getDirection() {
        float var1 = this.mc.thePlayer.rotationYaw;
        float forward = 1.0F;
        
        if (this.mc.thePlayer.moveForward < 0.0F) { // If the player walks backward
            var1 += 180.0F;
        }

        if (this.mc.thePlayer.moveForward < 0.0F) {
            forward = -0.5F;
        } else if (this.mc.thePlayer.moveForward > 0.0F) {
            forward = 0.5F;
        }

        if (this.mc.thePlayer.moveStrafing > 0.0F) {
            var1 -= 90.0F * forward;
        }

        if (this.mc.thePlayer.moveStrafing < 0.0F) {
            var1 += 90.0F * forward;
        }
        
        var1 *= 0.017453292F; // Faster version of Math.toRadians (x * 1 / 180 * PI)
        return var1;
    }
    
    /**
     *  ���ÿ����رյ�ģʽ 
     */
	public void setState(boolean state) {
		this.state = state;
    	HUD.informations.add(new Information(this.getState(), this.getName()));
		
		if (this.getState()) {
			this.onEnable();
		} else {
			this.onDisable();
		}
	}

	/**
	 * @return the module has enable(������ܿ�����û��)
	 */
	public boolean getState() {
		return this.state;
	}

	/**
	 * @return The module's name(���ܵ�����)
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return The module's keybind(������ܵİ���)
	 */
	public int getKeyBind() {
		return this.KeyBind;
	}

	/**
	 * @return The module's category(��ù��ܵ����)
	 */
	public ModuleCategory getCategory() {
		return this.category;
	}

	/**
	 * @return The modules its category its name��������ܵ���������)
	 */
	public String getCategoryName() {
		return this.category.getName();
	}

	/**
	 *  set the module name(��������) 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 *  set the module its key (���ð���) 
	 */
	public void setKeyBind(Integer key) {
		this.KeyBind = key;
	}

	public void setCategory(ModuleCategory c) {
		this.category = c;
	}
	
	/**
	 * @return the world on loaed entity list(����������ǰ��ʵ�� ���������� �����)
	 */
	public List<Entity> getLoaedEntityList() {
		return Minecraft.getMinecraft().theWorld.loadedEntityList;
	}
	
	/**
	 * @return the world entity list.(�ҵ�����������е�ʵ�弯��)
	 */
	public Set<Entity> getEntityList() {
		return Minecraft.getMinecraft().theWorld.entityList;
	}
	
	/**
	 * attack entity
	 * @param Living
	 */
	public void attackEntity(EntityLivingBase Living) {
		Minecraft.getMinecraft().thePlayer.swingItem();
		Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C02PacketUseEntity(Living, C02PacketUseEntity.Action.ATTACK));
		Minecraft.getMinecraft().thePlayer.attackTargetEntityWithCurrentItem(Living);
	}

	/**
	 * @param valueName Value's name
	 * @return you want to find value
	 */
	@SuppressWarnings("rawtypes")
	public Value getValue(String valueName) {
		for (Field field : this.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			Object o = new Object(); 
			
			try {
				o = field.get(this);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			
			if (o instanceof Value) {
				Value value = (Value) o;

				if (value.getValueName().equalsIgnoreCase(valueName)) {
					return value;
				}
			}
		}

		return null;
	}

	/**
	 * @return the module values.
	 */
	@SuppressWarnings("rawtypes")
	public List<Value> getValues() {
		List<Value> values = new ArrayList<Value>();

		for (Field field : this.getClass().getDeclaredFields()) {
			try {
				field.setAccessible(true);
				Object o = field.get(this);

				if (o instanceof Value) {
					values.add((Value) o);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return values;
	}
	
	/**
	 * @return the player has on the liquid(����ڲ��ڷ���Liquid����)
	 */
	public boolean isOnLiquid() {
		boolean onLiquid = false;
		final int y = (int) (this.mc.thePlayer.boundingBox.minY - 0.01);
		int double1 = MathHelper.floor_double(this.mc.thePlayer.boundingBox.maxX) + 1;
		int double2 = MathHelper.floor_double(this.mc.thePlayer.boundingBox.maxZ) + 1;
		
		for (int x = MathHelper.floor_double(this.mc.thePlayer.boundingBox.minX); x < double1; ++x) {
			for (int z = MathHelper.floor_double(this.mc.thePlayer.boundingBox.minZ); z < double2; ++z) {
				Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
				
				if (block != null && !(block instanceof BlockAir)) {
					if (!(block instanceof BlockLiquid)) { 
						return false;
					}
					
					onLiquid = true;
				}
			}
		}

		return onLiquid;
	}

	/**
	 * Render on EntityRenderer invoke (��ʾʵ�� ��EntityRenderer����)
	 * You need override(����Ҫ���������������ʵ����ʾʵ���һЩ���� ��ҪopenGL��ȾһЩ����)
	 */
	public void onRender() {}

	/**
	 * on the setState toggled module invoke(�����ÿ���״̬���л������е���)
	 */
	public void onToggled() {}

	/**
	 * on enable the module invoke(�ڿ���������ܵ�ʱ�����)
	 */
	public void onEnable() {}

	/**
	 * on disable the module invoke(�ڹر�������ܵ�ʱ�����)
	 */
	public void onDisable() {}

	/**
	 * on the player update invoke(�ڸ��������Ϣ��ʱ�����)
	 */
	public void onUpdate() {}

	/**
	 * @return the module has mode(���������û��ģʽ)
	 */
	public boolean hasMode() {
		return this.getMode() != null && !this.getMode().equals("");
	}
	
	/**
	 * @return the module current mode(�������Ŀǰ��ģʽ)
	 */
	public String getMode() {
		return null;
	}

	/**
	 * toggled the module(�л��������)
	 */
	public void Toggled() {
		this.setState(!this.getState());
	}
	
	/**
	 * @return a entityotherplayermp instance.(һ��EntityOtherPlayerMP�Ķ���)
	 */
	public EntityOtherPlayerMP getEntityOtherPlayerMP() {
		return new EntityOtherPlayerMP(this.mc.theWorld, this.mc.session.getProfile());
	}
	
	/**
	 * not thing mean(û��ʵ������) 
	 */
	public void addEntityOtherPlayerMP(EntityOtherPlayerMP entityOtherPlayerMP, int id) {
		entityOtherPlayerMP.cameraYaw = this.mc.thePlayer.cameraYaw;
		entityOtherPlayerMP.cameraPitch = this.mc.thePlayer.cameraPitch;
		entityOtherPlayerMP.posX = this.mc.thePlayer.posX;
		entityOtherPlayerMP.posY = this.mc.thePlayer.posY;
		entityOtherPlayerMP.posZ = this.mc.thePlayer.posZ;
		entityOtherPlayerMP.rotationYawHead = this.mc.thePlayer.rotationYawHead;
		entityOtherPlayerMP.rotationYaw = this.mc.thePlayer.rotationYaw;
		entityOtherPlayerMP.rotationPitch = this.mc.thePlayer.rotationPitch;
		entityOtherPlayerMP.renderYawOffset = this.mc.thePlayer.renderYawOffset;
		this.mc.theWorld.addEntityToWorld(id, entityOtherPlayerMP);
	}

	/**
	 * @return the minecraft world(�ҵ����������)
	 */
	public WorldClient getWorld() {
		return Minecraft.getMinecraft().theWorld;
	}
	
	/**
	 * @return the minecraft player on server instance(�ҵ�������������������������ʵ�����)
	 */
	public EntityPlayerMP getEntityPlayerMP() {
		Session sesstion = Minecraft.getMinecraft().getSession();
		MinecraftServer server = MinecraftServer.getServer();
		
		if (server == null) {
			return null;
		}
		
		ServerConfigurationManager config = server.getConfigurationManager();
		
		if (config == null) {
			return null;
		}
		
		return config.getPlayerByUsername(sesstion.getUsername());
	}
	
	/**
	 * print message to guiscreen(�����Ϣ��GuiChat(��Ϣ��Ļ)��)
	 * @param message(��Ϣ)
	 */
	public void printMessageToGuiScreen(String message) {
		Minecraft.getMinecraft().thePlayer.addChatMessage(IChatComponent.Serializer.jsonToComponent("{text:\"" + message + "\"}"));
	}
	
	/**
	 * set player sprinting
	 */
	public void setSprinting() {
		this.setSprinting(true);
	}
	
	/**
	 * set player sneaking
	 */
	public void setSneaking() {
		this.setSneaking(true);
	}
	
	/**
	 * set player sprinting
	 */
	public void setSprinting(boolean flag) {
		if (flag) {
			this.mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(Minecraft.getMinecraft().thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
		} else {
			this.mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(Minecraft.getMinecraft().thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
		}
	}
	
	/**
	 * set player sneaking
	 */
	public void setSneaking(boolean flag) {
		if (flag) {
			this.mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(Minecraft.getMinecraft().thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
		} else {
			this.mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(Minecraft.getMinecraft().thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
		}
	}
	
	/**
	 * @return the module has value
	 */
	public boolean hasValue() {
		return this.getValues().size() > 0;
	}
	
	/**
	 * on the player update walking player invoke the void(����Ҹ���UpdateWalkingPlayer��ʱ������������)
	 * @param event(�¼�)
	 */
	public void onMotion(MotionUpdateEvent event) {
		
	}
}