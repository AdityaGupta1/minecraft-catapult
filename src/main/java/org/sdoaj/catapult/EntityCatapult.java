package org.sdoaj.catapult;

import java.util.Arrays;
import java.util.List;

import javax.vecmath.Vector3d;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class EntityCatapult extends EntityCreature {
	List<Block> blocks = Arrays
			.asList(new Block[] { Blocks.diamond_block, Blocks.gold_block, Blocks.iron_block, Blocks.emerald_block });

	public EntityCatapult(World world) {
		super(world);
		this.setSize(2F, 1.5F);
	}
	
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0D);
    }

	public boolean isAIEnabled() {
		return false;
	}

	protected String getLivingSound() {
		return null;
	}

	protected String getHurtSound() {
		return null;
	}

	protected String getDeathSound() {
		return null;
	}

	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {
		return;
	}

	protected float getSoundVolume() {
		return 0F;
	}
	
	private Vector3d multiplyVectors(Vector3d vector1, Vector3d vector2) {
		return new Vector3d(vector1.x * vector2.x, vector1.y * vector2.y, vector1.z * vector2.z);
	}

	@Override
	public ItemStack getHeldItem() {
		return null;
	}

	@Override
	public ItemStack getEquipmentInSlot(int p_71124_1_) {
		return null;
	}

	@Override
	public void setCurrentItemOrArmor(int p_70062_1_, ItemStack p_70062_2_) {
		// do nothing
	}

	@Override
	public ItemStack[] getLastActiveItems() {
		return new ItemStack[]{};
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {

		int amountToDrop = this.getRNG().nextInt(3);

		for (int i = 0; i < amountToDrop; i++) {
			this.dropItem(Item.getItemFromBlock(Blocks.log), 1);
		}
	}
	
	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.setPosition(this.lastTickPosX, this.lastTickPosY, this.lastTickPosZ);
	}

	public boolean interact(EntityPlayer player) {
		World world = player.getEntityWorld();

		double angle = Main.angle;
		double power = Main.power;

		if (!Main.parametersSet) {
			player.addChatComponentMessage(new ChatComponentText(Main.redText + "Use \"/catapult <angle> <power>\" first!"));
			return false;
		}

		if (!world.isRemote) {
			return false;
		}

		player.addChatComponentMessage(
				new ChatComponentText(Main.aquaText + "Launching cow..."));

		EntityCow cow = new EntityCow(world);
		cow.setLocationAndAngles(this.posX, this.posY, this.posZ, 0, 0);

		EntityFallingBlock block = new EntityFallingBlock(world, this.posX, this.posY, this.posZ,
				blocks.get((int) Math.floor(Math.random() * blocks.size())));
		Vector3d initialVector = new Vector3d(Math.cos(Math.toRadians(angle)) * power,
				Math.sin(Math.toRadians(angle)) * power, Math.cos(Math.toRadians(angle)) * power);
		Vector3d multiplyVector = new Vector3d(0, 1, -1);
		Vector3d velocity = multiplyVectors(initialVector, multiplyVector);
		block.setVelocity(velocity.x, velocity.y, velocity.z);

		cow.mountEntity(block);

		world.spawnEntityInWorld(block);
		world.spawnEntityInWorld(cow);

		return true;
	}
}