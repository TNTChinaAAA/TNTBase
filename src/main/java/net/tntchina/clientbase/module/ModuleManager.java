package net.tntchina.clientbase.module;

import java.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.tntchina.clientbase.module.modules.combat.*;
import net.tntchina.clientbase.module.modules.exploit.*;
import net.tntchina.clientbase.module.modules.misc.*;
import net.tntchina.clientbase.module.modules.movement.*;
import net.tntchina.clientbase.module.modules.player.*;
import net.tntchina.clientbase.module.modules.render.*;
import net.tntchina.clientbase.module.modules.world.*;
import net.tntchina.clientbase.module.modules.world.Timer;

/**
 * the manager register modules.(这个处理者注册功能)
 * 
 * @author TNTChina
 */
public class ModuleManager {

	private static final List<Module> modules = new ArrayList<Module>();
	private static ModuleManager theModuleMabager;
	public final Minecraft mc = Minecraft.getMinecraft();

	public ModuleManager() {
		ModuleManager.theModuleMabager = this;
		this.registerModule(new ChestStealer("ChestStealer", ModuleCategory.PLAYER));
		this.registerModule(new NameProtect("NameProtect", ModuleCategory.MISC));
		this.registerModule(new Spammer("Spammer", ModuleCategory.MISC));
		this.registerModule(new NoRotate("NoRotate", ModuleCategory.RENDER));
		this.registerModule(new NameTags("NameTags", ModuleCategory.RENDER));
		this.registerModule(new NoHurtCamera("NoHurtCamera", ModuleCategory.RENDER));
		this.registerModule(new NotViewBob("NotViewBob", ModuleCategory.RENDER));
		this.registerModule(new FastLadder("FastLadder", ModuleCategory.MOVEMENT));
		this.registerModule(new Glide("Glide", ModuleCategory.MOVEMENT));
		this.registerModule(new GodMode("GodMode", ModuleCategory.PLAYER));
		this.registerModule(new Blink("Blink", ModuleCategory.MOVEMENT));
		this.registerModule(new HighJump("HighJump", ModuleCategory.MOVEMENT));
		this.registerModule(new NoHungry("NoHungry", ModuleCategory.PLAYER));
		this.registerModule(new AutoSoup("AutoSoup", ModuleCategory.PLAYER));
		this.registerModule(new ESP("ESP", ModuleCategory.RENDER));
		this.registerModule(new XRay("XRay", 45, ModuleCategory.RENDER));
		this.registerModule(new HUD("HUD", 0, ModuleCategory.RENDER));
		this.registerModule(new ChestESP("ChestESP", ModuleCategory.RENDER));
		this.registerModule(new NoFall("NoFall", ModuleCategory.PLAYER));
		this.registerModule(new Speed("Speed", 35, ModuleCategory.MOVEMENT));
		this.registerModule(new KillAura("KillAura", 19, ModuleCategory.COMBAT));
		this.registerModule(new Fullbright("Fullbright", ModuleCategory.RENDER));
		this.registerModule(new CanPVP("CanPVP", ModuleCategory.EXPLOIT));
		this.registerModule(new Scaffold("Scaffold", ModuleCategory.WORLD));
		this.registerModule(new Reach("Reach", ModuleCategory.COMBAT));
		this.registerModule(new Velocity("Velocity", 0, ModuleCategory.COMBAT));
		this.registerModule(new Fly("Fly", 33, ModuleCategory.MOVEMENT));
		this.registerModule(new AutoClicker("AutoClicker", ModuleCategory.COMBAT));
		this.registerModule(new KeepSprint("KeepSprint", 50, ModuleCategory.MOVEMENT));
		this.registerModule(new FastPlace("FastPlace", 21, ModuleCategory.WORLD));
		this.registerModule(new HitBox("HitBox", ModuleCategory.PLAYER));
		this.registerModule(new Climb("Climb", ModuleCategory.MOVEMENT));
		this.registerModule(new AntiFire("AntiFire", ModuleCategory.PLAYER));
		this.registerModule(new FastBreak("FastBreak", 0, ModuleCategory.WORLD));
		this.registerModule(new InvMove("InvMove", 0, ModuleCategory.MOVEMENT));
		this.registerModule(new Teams("Teams", ModuleCategory.PLAYER));
		this.registerModule(new Step("Step", ModuleCategory.MOVEMENT));
		this.registerModule(new Critical("Critical", 0, ModuleCategory.COMBAT));
		this.registerModule(new Timer("Timer", 0, ModuleCategory.WORLD));
		this.registerModule(new LongJump("LongJump", ModuleCategory.MOVEMENT));
		this.registerModule(new NoWeb("NoWeb", ModuleCategory.MOVEMENT));
		this.registerModule(new NoSlow("NoSlow", ModuleCategory.MOVEMENT));
		this.registerModule(new AutoTool("AutoTool", ModuleCategory.PLAYER));
		this.registerModule(new AutoArmor("AutoArmor", ModuleCategory.PLAYER));
		this.registerModule(new FastBow("FastBow", ModuleCategory.PLAYER));
		this.registerModule(new AutoBow("AutoBow", ModuleCategory.PLAYER));
		this.sortName();// sort modules.
	}

	public ModuleCategory getCategory(String name) {
		for (ModuleCategory c : ModuleCategory.values()) {
			if (c.getName().equalsIgnoreCase(name)) {
				return c;
			}
		}

		return null;
	}

	/**
	 * name sort(名字排序)
	 */
	public void sortName() {
		Collections.sort(ModuleManager.modules, new Comparator<Module>() {

			public int compare(Module o1, Module o2) {
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
	}

	/**
	 * HUD调用的专属方法
	 */
	public void sort(List<Module> modules) {
		Collections.sort(modules, new Comparator<Module>() {

			public int compare(Module o1, Module o2) {
				if (ModuleManager.this.mc.fontRendererObj != null) {
					final FontRenderer fontRendererObj = ModuleManager.this.mc.fontRendererObj;
					String c = o1.getName();
					String d = o2.getName();

					if (o1.hasMode()) {
						c = c + " ";
						c = c + o1.getMode();
					}

					if (o2.hasMode()) {
						d = d + " ";
						d = d + o2.getMode();
					}

					int a = fontRendererObj.getStringWidth(c);
					int b = fontRendererObj.getStringWidth(d);

					if (a == b) {
						return c.compareToIgnoreCase(d);
					}

					return a > b ? -1 : 1;
				} else {
					String c = o1.getName();
					String d = o2.getName();

					if (o1.hasMode()) {
						c = c + " ";
						c = c + o1.getMode();
					}

					if (o2.hasMode()) {
						d = d + " ";
						d = d + o2.getMode();
					}

					int a = c.length();
					int b = d.length();

					if (a == b) {
						return c.compareToIgnoreCase(d);
					}

					return a > b ? -1 : 1;
				}
			}
		});
	}

	/**
	 * @return the module manager(获得ModuleManager的实体对象)
	 */
	public static final ModuleManager getModuleManager() {
		return ModuleManager.theModuleMabager;
	}

	public static <T extends Module> boolean getModuleState(Class<T> clazz) {
		final Module m = ModuleManager.getModule(clazz);
		
		if (m == null) {
			return false;
		} else {
			if (m.getState()) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * register your module(On ModuleManager <init> void invoke, register.) (注册你的功能
	 * 在功能处理者的构造函数中调用这个办法注册你的功能)
	 */
	public void registerModule(Module module) {
		ModuleManager.modules.add(module);
	}

	/**
	 * @return the modules (返回功能集合)
	 */
	public static final List<Module> getModules() {
		return ModuleManager.modules;
	}

	public static ArrayList<Module> getToggled() {
		ArrayList<Module> fuckyou = new ArrayList<Module>();
		for (Module m : modules) {
			if (m.getState()) {
				fuckyou.add(m);
			}
		}
		return fuckyou;
	}

	/**
	 * @param clazz(类的Class对象)
	 * @return get module list module to you (从功能集合获得功能对象)
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Module> T getModule(Class<T> clazz) {
		for (Module m : ModuleManager.getModules()) {
			if (m.getClass().equals(clazz)) {
				return (T) m;
			}
		}

		return null;
	}

	/**
	 * @param name(名字)
	 * @return get module list module to you (从功能集合获得功能对象)
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Module> T getModule(String name) {
		for (Module m : ModuleManager.getModules()) {
			if (m.getName().equalsIgnoreCase(name)) {
				return (T) m;
			}
		}

		return null;
	}
}