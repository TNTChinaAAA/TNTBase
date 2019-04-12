package net.tntchina.clientbase.event.events;

import net.minecraft.block.*;
import net.minecraft.util.*;
import net.tntchina.clientbase.event.*;

/**
 * on the player break block invoke event.(在玩家破坏方块调用的事件)
 * @author TNTChina
 */
public class BreakBlockEvent extends Event {
	
	private Block block;
	private BlockPos pos;
	private EnumFacing facing;
	
	public BreakBlockEvent(Block block, BlockPos pos, EnumFacing facing) {
		this.block = block;
		this.pos = pos;
		this.facing = facing;
	}

	public BlockPos getPos() {
		return pos;
	}

	public EnumFacing getFacing() {
		return facing;
	}

	public Block getBlock() {
		return block;
	}
}