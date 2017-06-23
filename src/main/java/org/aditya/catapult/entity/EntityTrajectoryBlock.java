package org.aditya.catapult.entity;

import java.util.HashMap;
import java.util.Map;

import org.aditya.catapult.Main;
import org.aditya.catapult.util.ColorBlock;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityTrajectoryBlock extends EntityFallingBlock {
	private Block field_145811_e;
	public int field_145814_a;
	public int field_145812_b;
	public boolean field_145813_c;
	private boolean field_145808_f;
	private boolean field_145809_g;
	private int field_145815_h;
	private float field_145816_i;
	public NBTTagCompound field_145810_d;

	private double initialX = 0;
	private double initialY = 0;
	private double initialZ = 0;
	
	private int color;

	public EntityTrajectoryBlock(World world, double x, double y, double z,
			Block block, int color) {
		super(world);
		this.field_145813_c = true;
		this.field_145815_h = 40;
		this.field_145816_i = 2.0F;
		this.field_145811_e = block;
		this.field_145814_a = 0;
		this.preventEntitySpawning = true;
		this.setSize(0.98F, 0.98F);
		this.yOffset = this.height / 2.0F;
		this.setPosition(x, y, z);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;

		this.initialX = x;
		this.initialY = y;
		this.initialZ = z;
		
		this.color = color;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		// motion same as EntityFallingBlock
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		++this.field_145812_b;
		this.motionY -= 0.03999999910593033D;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9800000190734863D;
		this.motionY *= 0.9800000190734863D;
		this.motionZ *= 0.9800000190734863D;
		
		World world = this.worldObj;

		EntityTrajectoryParticle particle = new EntityTrajectoryParticle(world, this.posX, this.posY, this.posZ,
				0, 0, 0, Minecraft.getMinecraft().effectRenderer);
		particle.setColour(color);
		Minecraft.getMinecraft().effectRenderer.addEffect(particle);

		if (this.onGround) {
			this.setDead();
		}
	}
}