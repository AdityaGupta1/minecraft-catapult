package org.sdoaj.catapult;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.passive.EntityCow;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class FallingBlockEventHandler {
	
	@SubscribeEvent
	public void immobilizeFallingBlock(LivingUpdateEvent event) {
		if (!(event.entity instanceof EntityCow)) {
			return;
		}
		
		EntityCow cow = (EntityCow) event.entity;
		
		if (!cow.isRiding()) {
			return;
		}
		
		if (!(cow.ridingEntity instanceof EntityFallingBlock)) {
			return;
		}
		
		EntityFallingBlock block = (EntityFallingBlock) cow.ridingEntity;
		
		if (!block.onGround) {
			return;
		}
		
		block.setVelocity(0, 0, 0);
	}

}
