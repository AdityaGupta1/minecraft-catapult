package org.aditya.catapult.model;

import org.aditya.catapult.entity.EntityCatapult;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class ModelCatapult extends ModelBase {

	ModelRenderer base1;
	ModelRenderer base2;
	ModelRenderer base3;
	ModelRenderer base4;
	ModelRenderer verticalBar1;
	ModelRenderer verticalBar2;
	ModelRenderer topBar;
	ModelRenderer supportBar1;
	ModelRenderer supportBar2;
	ModelRenderer launcherBase;
	ModelRenderer launcherHandle;

	public ModelCatapult() {
		textureWidth = 128;
		textureHeight = 128;

		base1 = new ModelRenderer(this, 0, 0);
		base1.mirror = false;
		base1.addBox(-10F, 0F, 12F, 20, 4, 4);
		base1.setTextureSize(128, 128);

		base2 = new ModelRenderer(this, 0, 8);
		base2.mirror = false;
		base2.addBox(-10F, 0F, -16F, 4, 4, 32);
		base2.setTextureSize(128, 128);

		base3 = new ModelRenderer(this, 0, 0);
		base3.mirror = false;
		base3.addBox(-10F, 0F, -16F, 20, 4, 4);
		base3.setTextureSize(128, 128);

		base4 = new ModelRenderer(this, 0, 8);
		base4.mirror = false;
		base4.addBox(6F, 0F, -16F, 4, 4, 32);
		base4.setTextureSize(128, 128);

		verticalBar1 = new ModelRenderer(this, 48, 0);
		verticalBar1.mirror = false;
		verticalBar1.addBox(6F, 0F, 2F, 4, 14, 4);
		verticalBar1.setTextureSize(128, 128);

		verticalBar2 = new ModelRenderer(this, 48, 0);
		verticalBar2.mirror = false;
		verticalBar2.addBox(-10F, 0F, 2F, 4, 14, 4);
		verticalBar2.setTextureSize(128, 128);

		topBar = new ModelRenderer(this, 0, 44);
		topBar.mirror = false;
		topBar.addBox(-10F, 10F, 2F, 20, 4, 4);
		topBar.setTextureSize(128, 128);

		supportBar1 = new ModelRenderer(this, 64, 0);
		supportBar1.mirror = false;
		supportBar1.addBox(0F, 0F, 0F, 4, 14, 4);
		supportBar1.setTextureSize(128, 128);
		supportBar1.setRotationPoint(6F, 1F, 13F);
		supportBar1.rotateAngleX = (float) (-Math.PI / 4);

		supportBar2 = new ModelRenderer(this, 64, 0);
		supportBar2.mirror = false;
		supportBar2.addBox(0F, 0F, 0F, 4, 14, 4);
		supportBar2.setTextureSize(128, 128);
		supportBar2.setRotationPoint(-10F, 1F, 13F);
		supportBar2.rotateAngleX = (float) (-Math.PI / 4);

		launcherBase = new ModelRenderer(this, 0, 68);
		launcherBase.mirror = false;
		launcherBase.addBox(-6F, 0F, -3F, 12, 4, 4);
		launcherBase.setTextureSize(128, 128);

		launcherHandle = new ModelRenderer(this, 0, 52);
		launcherHandle.mirror = true;
		launcherHandle.addBox(-1F, 1F, -2F, 2, 14, 2);
		launcherHandle.setTextureSize(128, 128);
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		EntityCatapult entity = (EntityCatapult) par1Entity;

		base1.render(par7);
		base2.render(par7);
		base3.render(par7);
		base4.render(par7);
		verticalBar1.render(par7);
		verticalBar2.render(par7);
		topBar.render(par7);
		supportBar1.render(par7);
		supportBar2.render(par7);
		launcherBase.render(par7);
		launcherHandle.render(par7);
	}
}