package net.tntchina.clientbase.event.events;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

/**
 * set the block axisAlignedBB.(设置方块的位置信息)
 * @author TNTChina
 */
public class BlockBBEvent extends CancellableEvent {
	
	private BlockPos pos;
	private Block block;
	private AxisAlignedBB axisAlignedBB;
	private List<AxisAlignedBB> axisAlignedBBList = new ArrayList<AxisAlignedBB>();
	
	public BlockBBEvent(BlockPos pos, Block block, AxisAlignedBB axisalignedbb) {
		this.pos = pos;
		this.block = block;
		this.axisAlignedBB = axisalignedbb;
	}

	public BlockPos getPos() {
		return this.pos;
	}

	public void setPos(BlockPos pos) {
		this.pos = pos;
	}

	public Block getBlock() {
		return this.block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public AxisAlignedBB getAxisAlignedBB() {
		return this.axisAlignedBB;
	}
	
	public List<AxisAlignedBB> getAxisAlignedBBList() {
		return this.axisAlignedBBList;
	}

	public void setAxisAlignedBB(AxisAlignedBB axisAlignedBB) {
		this.axisAlignedBB = axisAlignedBB;
	}
	
	public void setAxisAlignedBBList(List<AxisAlignedBB> axisAlignedBBList) {
		this.axisAlignedBBList = axisAlignedBBList;
	}
}