package net.tntchina.util;

import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.tntchina.clientbase.main.TNTBase;

public class XRayUtil {
	
	private static final ArrayList<Block> block = new ArrayList<Block>();
	
	public static void initXRay() {
		XRayUtil.block.add(Blocks.flower_pot);
		XRayUtil.block.add(Block.getBlockFromName("coal_ore"));
		XRayUtil.block.add(Block.getBlockFromName("iron_ore"));
		XRayUtil.block.add(Block.getBlockFromName("gold_ore"));
		XRayUtil.block.add(Block.getBlockFromName("redstone_ore"));
		XRayUtil.block.add(Block.getBlockById(74));
		XRayUtil.block.add(Block.getBlockFromName("lapis_ore"));
		XRayUtil.block.add(Block.getBlockFromName("diamond_ore"));
		XRayUtil.block.add(Block.getBlockFromName("emerald_ore"));
		XRayUtil.block.add(Block.getBlockFromName("quartz_ore"));
		XRayUtil.block.add(Block.getBlockFromName("clay"));
		XRayUtil.block.add(Block.getBlockFromName("glowstone"));
		XRayUtil.block.add(Block.getBlockById(8));
		XRayUtil.block.add(Block.getBlockById(9));
		XRayUtil.block.add(Block.getBlockById(10));
		XRayUtil.block.add(Block.getBlockById(11));
		XRayUtil.block.add(Block.getBlockFromName("crafting_table"));
		XRayUtil.block.add(Block.getBlockById(61));
		XRayUtil.block.add(Block.getBlockById(62));
		XRayUtil.block.add(Block.getBlockFromName("torch"));
		XRayUtil.block.add(Block.getBlockFromName("ladder"));
		XRayUtil.block.add(Block.getBlockFromName("tnt"));
		XRayUtil.block.add(Block.getBlockFromName("coal_block"));
		XRayUtil.block.add(Block.getBlockFromName("iron_block"));
		XRayUtil.block.add(Block.getBlockFromName("gold_block"));
		XRayUtil.block.add(Block.getBlockFromName("diamond_block"));
		XRayUtil.block.add(Block.getBlockFromName("emerald_block"));
		XRayUtil.block.add(Block.getBlockFromName("redstone_block"));
		XRayUtil.block.add(Block.getBlockFromName("lapis_block"));
		XRayUtil.block.add(Block.getBlockFromName("fire"));
		XRayUtil.block.add(Block.getBlockFromName("mossy_cobblestone"));
		XRayUtil.block.add(Block.getBlockFromName("mob_spawner"));
		XRayUtil.block.add(Block.getBlockFromName("end_portal_frame"));
		XRayUtil.block.add(Block.getBlockFromName("enchanting_table"));
		XRayUtil.block.add(Block.getBlockFromName("bookshelf"));
		XRayUtil.block.add(Block.getBlockFromName("command_block"));
		TNTBase.getLogger().info("Register XRayBlocks end.");
	}
	
	public static boolean isXRayBlock(Block block) {
		if (XRayUtil.block.contains(block)) {
			return true;
		} else {
			return false;
		}
	}
}
