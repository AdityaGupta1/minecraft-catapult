package org.sdoaj.catapult;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class EntityCatapult extends EntityCow {
	public EntityCatapult(World world) {
		super(world);
		this.setSize(0.9F, 1.3F);
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
			if (this.isBurning()) {
				// charcoal
				this.dropItem(new ItemStack(Items.coal, 1, 1).getItem(), 1);
			} else {
				this.dropItem(Item.getItemFromBlock(Blocks.log), 1);
			}
		}
	}

	public boolean interact(EntityPlayer player)
    {
		player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "catapult"));
		return true;
    }
}