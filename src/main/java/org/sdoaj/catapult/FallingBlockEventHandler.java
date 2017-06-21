package org.sdoaj.catapult;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.passive.EntityCow;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class FallingBlockEventHandler {
	
	@SubscribeEvent
	public void immobilizeFallingBlock(LivingUpdateEvent event) {
		if (!(event.entity instanceof Entity)) {
			return;
		}
		
		final Entity entity = (Entity) event.entity;
		
		if (!entity.isRiding()) {
			return;
		}
		
		if (!(entity.ridingEntity instanceof EntityFallingBlock)) {
			return;
		}
		
		final EntityFallingBlock block = (EntityFallingBlock) entity.ridingEntity;
		
		if (!block.onGround) {
			return;
		}
		
		block.setVelocity(0, 0, 0);
	}

}
