package org.aditya.catapult.util;

import java.util.TimerTask;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class FallingBlockEventHandler {

	@SubscribeEvent
	public void immobilizeFallingBlock(LivingUpdateEvent event) {
		Entity entity = event.entity;

		if (!entity.isRiding()) {
			return;
		}

		Entity ridingEntity = entity.ridingEntity;

		if (!(ridingEntity instanceof EntityFallingBlock)) {
			return;
		}

		if (!ridingEntity.onGround) {
			return;
		}

		ridingEntity.setVelocity(0, 0, 0);
		
		World world = ridingEntity.worldObj;
		double x = ridingEntity.posX;
		double y = ridingEntity.posY;
		double z = ridingEntity.posZ;
		
		
	}
}
