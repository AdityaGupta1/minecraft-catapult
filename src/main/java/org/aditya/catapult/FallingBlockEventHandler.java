package org.aditya.catapult;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class FallingBlockEventHandler {

	@SubscribeEvent
	public void immobilizeFallingBlock(LivingUpdateEvent event) {
		Entity entity = event.entity;

		if (!entity.isRiding()) {
			return;
		}

		Entity ridingEntity = entity.ridingEntity;

		if (!ridingEntity.onGround) {
			return;
		}

		if (ridingEntity instanceof EntityFallingBlock) {
			ridingEntity.setVelocity(0, 0, 0);
		}
	}
}
