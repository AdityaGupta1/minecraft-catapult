package org.aditya.catapult.util;

import net.minecraft.block.Block;

/*
 * utility class to map colors to blocks
 */
public class ColorBlock {
	int color;
	Block block;
	
	public ColorBlock(int color, Block block) {
		this.color = color;
		this.block = block;
	}
	
	public int getColor() {
		return color;
	}
	
	public Block getBlock() {
		return block;
	}
}
