package org.aditya.catapult;

import java.util.ArrayList;
import java.util.Iterator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
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

	public EntityTrajectoryBlock(World p_i1706_1_) {
		super(p_i1706_1_);
		this.field_145813_c = true;
		this.field_145815_h = 40;
		this.field_145816_i = 2.0F;
	}

	public EntityTrajectoryBlock(World p_i45318_1_, double p_i45318_2_, double p_i45318_4_, double p_i45318_6_,
			Block p_i45318_8_) {
		this(p_i45318_1_, p_i45318_2_, p_i45318_4_, p_i45318_6_, p_i45318_8_, 0);
	}

	public EntityTrajectoryBlock(World p_i45319_1_, double p_i45319_2_, double p_i45319_4_, double p_i45319_6_,
			Block p_i45319_8_, int p_i45319_9_) {
		super(p_i45319_1_);
		this.field_145813_c = true;
		this.field_145815_h = 40;
		this.field_145816_i = 2.0F;
		this.field_145811_e = p_i45319_8_;
		this.field_145814_a = p_i45319_9_;
		this.preventEntitySpawning = true;
		this.setSize(0.98F, 0.98F);
		this.yOffset = this.height / 2.0F;
		this.setPosition(p_i45319_2_, p_i45319_4_, p_i45319_6_);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = p_i45319_2_;
		this.prevPosY = p_i45319_4_;
		this.prevPosZ = p_i45319_6_;

		this.initialX = p_i45319_2_;
		this.initialY = p_i45319_4_;
		this.initialZ = p_i45319_6_;
	}
	
	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        ++this.field_145812_b;
        this.motionY -= 0.03999999910593033D;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;
        
        this.worldObj.spawnParticle("fireworksSpark", this.posX, this.posY, this.posZ, 0, 0, 0);

		if (this.onGround) {
			this.setDead();
		}
	}
}