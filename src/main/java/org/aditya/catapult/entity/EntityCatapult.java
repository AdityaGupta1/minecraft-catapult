package org.aditya.catapult.entity;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;

import org.aditya.catapult.CommandCatapult;
import org.aditya.catapult.Main;
import org.aditya.catapult.util.Trajectory;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class EntityCatapult extends EntityCreature {

	public EntityCatapult(World world) {
		super(world);
		this.setSize(2F, 1.5F);
	}

	protected void applyEntityAttributes() {
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
		return new ItemStack[] {};
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {

		int amountToDrop = this.getRNG().nextInt(3);

		for (int i = 0; i < amountToDrop; i++) {
			this.dropItem(Item.getItemFromBlock(Blocks.log), 1);
		}
	}

	private List<Trajectory> trajectories = new ArrayList<>();

	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.setPosition(this.lastTickPosX, this.lastTickPosY, this.lastTickPosZ);

		for (Trajectory trajectory : trajectories) {
			this.worldObj.spawnEntityInWorld(createBlock(trajectory.getAngle(), trajectory.getPower(), true,
					trajectory.getColor(), trajectory.getRotationAngle()));
		}
	}

	public boolean interact(EntityPlayer player) {
		World world = player.getEntityWorld();

		if (!world.isRemote) {
			return false;
		}

		double angle = Main.angle;
		double power = Main.power;

		if (!Main.parametersSet) {
			player.addChatComponentMessage(Main.createChatMessage(
					"Use " + new CommandCatapult().getCommandUsage(null) + " first!", EnumChatFormatting.RED));
			return false;
		}

		if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemSword) {
			clearTrajectories();
			player.addChatComponentMessage(Main.createChatMessage("Cleared all trajectories", EnumChatFormatting.AQUA));
			return true;
		}

		if (player.isSneaking()) {
			if (trajectories
					.contains(new Trajectory(angle, power, Main.getColorBlock().getColor(), Main.rotationAngle))) {
				player.addChatComponentMessage(
						Main.createChatMessage("This trajectory is already being shown!", EnumChatFormatting.RED));
				return false;
			}

			trajectories.add(new Trajectory(angle, power, Main.getColorBlock().getColor(), Main.rotationAngle));
			player.addChatComponentMessage(Main.createChatMessage("Added a trajectory with Angle: " + angle
					+ " degrees, Power: " + Main.shownPower + ", Color: " + Main.color, EnumChatFormatting.AQUA));

			return true;
		}

		player.addChatComponentMessage(Main.createChatMessage("Launching cow...", EnumChatFormatting.AQUA));

		EntityCow cow = new EntityCow(world);
		cow.setLocationAndAngles(this.posX, this.posY, this.posZ, 0, 0);

		EntityFallingBlock block = createBlock(false);

		cow.mountEntity(block);

		world.spawnEntityInWorld(block);
		world.spawnEntityInWorld(cow);

		return true;
	}

	private EntityFallingBlock createBlock(boolean trajectoryBlock) {
		return createBlock(Main.angle, Main.power, trajectoryBlock, Main.getColorBlock().getColor(),
				Main.rotationAngle);
	}

	private EntityFallingBlock createBlock(double angle, double power, boolean trajectoryBlock, int color,
			double rotationAngle) {
		World world = this.worldObj;

		EntityFallingBlock block = new EntityFallingBlock(world);

		if (trajectoryBlock) {
			block = new EntityTrajectoryBlock(world, this.posX, this.posY + 0.1, this.posZ,
					Main.getColorBlock().getBlock(), color);
		} else {
			block = new EntityFallingBlock(world, this.posX, this.posY + 0., this.posZ,
					Main.getColorBlock().getBlock());
		}

	
		final double angleRadiant = Math.toRadians(angle);
		final double rotRadiant = Math.toRadians(rotationAngle);

		final Vector3d initialVector = new Vector3d(0, Math.sin(angleRadiant), Math.cos(angleRadiant));
		
		final Vector3d multiplyVector = new Vector3d(0, power, -power);
		final Vector3d velocity = multiplyVectors(initialVector, multiplyVector);

		//we use a rotation  matrix to change the angle around the Y axis
		final Matrix3d rotationMatrix = new Matrix3d();
		rotationMatrix.rotY(rotRadiant);
		rotationMatrix.transform(velocity);

		System.out.println("Velocity is: " + velocity);
		block.setVelocity(velocity.x, velocity.y, velocity.z);
		return block;
	}

	/*
	 * spawns an invisible block that leaves particles to form a trail
	 */
	private void highlightTrajectory(World world, double angle, double power) {
		world.spawnEntityInWorld(createBlock(true));
	}

	private void clearTrajectories() {
		this.trajectories.clear();
	}
}