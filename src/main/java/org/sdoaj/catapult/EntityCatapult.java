package org.sdoaj.catapult;

import javax.vecmath.Vector3d;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class EntityCatapult extends EntityCow {
	public EntityCatapult(World world) {
		super(world);
		this.setSize(2F, 1.5F);
	}

	public boolean isAIEnabled() {
		return false;
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0D);
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

	protected Item getDropItem() {
		return Item.getItemFromBlock(Blocks.log);
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {

		int amountToDrop = this.getRNG().nextInt(3);

		for (int i = 0; i < amountToDrop; i++) {
			this.dropItem(Item.getItemFromBlock(Blocks.log), 1);
		}
	}
	
	public boolean interact(EntityPlayer player) {
		World world = player.getEntityWorld();
		
		double angle = Main.angle;
		double power = Main.power;
		
		if (!Main.parametersSet) {
			player.addChatComponentMessage(
					new ChatComponentText(EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + "Use \"/catapult <angle> <power>\" first!"));
			return false;
		}
		
		if (!world.isRemote) {
			return false;
		}
		
		player.addChatComponentMessage(
				new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "Launching cow..."));
		
		EntityCow cow = new EntityCow(world);
		cow.setLocationAndAngles(this.posX, this.posY, this.posZ, 0, 0);
		
		Vector3d initialVector = new Vector3d(Math.cos(Math.toRadians(angle)) * power, Math.sin(Math.toRadians(angle)) * power, Math.cos(Math.toRadians(angle)) * power);
//		Vector3d multiplyVector = new Vector3d();
		Vector3d multiplyVector = new Vector3d(0, 1, -1);
		Vector3d velocity = multiplyVectors(initialVector, multiplyVector);
		cow.setVelocity(velocity.x, velocity.y, velocity.z);
		
		world.spawnEntityInWorld(cow);
		
		return true;
	}
	
	private Vector3d multiplyVectors(Vector3d vector1, Vector3d vector2) {
		return new Vector3d(vector1.x * vector2.x, vector1.y * vector2.y, vector1.z * vector2.z);
	}
}