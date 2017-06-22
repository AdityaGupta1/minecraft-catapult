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
		
		if (!(entity.ridingEntity instanceof EntityFallingBlock)) {
			return;
		}
		
		EntityFallingBlock block = (EntityFallingBlock) entity.ridingEntity;
		
		if (!block.onGround) {
			return;
		}
		
		block.setVelocity(0, 0, 0);
	}

}
