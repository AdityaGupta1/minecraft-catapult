package org.aditya.catapult;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderCatapult extends Render {
	private static final ResourceLocation catapultTextures = new ResourceLocation(
			"catapult:textures/catapult.png");
	ModelCatapult model = new ModelCatapult();

	public RenderCatapult() {
		super();
	}

	protected ResourceLocation func_180572_a(EntityCatapult p_180572_1_) {
		return catapultTextures;
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.func_180572_a((EntityCatapult) entity);
	}
	
	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTick) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);	
//		GL11.glScalef(0.5F, 0.5F, 0.5F);
		GL11.glRotated(180, 0, 1, 0);
		GL11.glScalef(2F, 2F, 2F);
		bindTexture(getEntityTexture(entity));
		model.render(entity, 0, 0, 0, 0, 0, 0.0625F);
		GL11.glPopMatrix();
	}
}