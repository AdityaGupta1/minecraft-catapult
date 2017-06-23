package org.aditya.catapult.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFireworkSparkFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class EntityTrajectoryParticle extends EntityFireworkSparkFX
{
    private int baseTextureIndex = 160;
    private boolean field_92054_ax;
    private boolean field_92048_ay;
    private final EffectRenderer field_92047_az;
    private float fadeColourRed;
    private float fadeColourGreen;
    private float fadeColourBlue;
    private boolean hasFadeColour;

    public EntityTrajectoryParticle(World world, double p_i1207_2_, double p_i1207_4_, double p_i1207_6_, double p_i1207_8_, double p_i1207_10_, double p_i1207_12_, EffectRenderer p_i1207_14_)
    {
        super(world, p_i1207_2_, p_i1207_4_, p_i1207_6_, p_i1207_8_, p_i1207_10_, p_i1207_12_, p_i1207_14_);
        this.motionX = p_i1207_8_;
        this.motionY = p_i1207_10_;
        this.motionZ = p_i1207_12_;
        this.field_92047_az = p_i1207_14_;
        this.particleScale *= 0.75F;
        this.particleMaxAge = 48 + this.rand.nextInt(12);
        this.noClip = false;
    }

    public void setTrail(boolean p_92045_1_)
    {
        this.field_92054_ax = p_92045_1_;
    }

    public void setTwinkle(boolean p_92043_1_)
    {
        this.field_92048_ay = p_92043_1_;
    }

    public void setColour(int p_92044_1_)
    {
        float f = (float)((p_92044_1_ & 16711680) >> 16) / 255.0F;
        float f1 = (float)((p_92044_1_ & 65280) >> 8) / 255.0F;
        float f2 = (float)((p_92044_1_ & 255) >> 0) / 255.0F;
        float f3 = 1.0F;
        this.setRBGColorF(f * f3, f1 * f3, f2 * f3);
    }

    public void setFadeColour(int p_92046_1_)
    {
        this.fadeColourRed = (float)((p_92046_1_ & 16711680) >> 16) / 255.0F;
        this.fadeColourGreen = (float)((p_92046_1_ & 65280) >> 8) / 255.0F;
        this.fadeColourBlue = (float)((p_92046_1_ & 255) >> 0) / 255.0F;
        this.hasFadeColour = true;
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }

        if (this.particleAge > this.particleMaxAge / 2)
        {
            this.setAlphaF(1.0F - ((float)this.particleAge - (float)(this.particleMaxAge / 2)) / (float)this.particleMaxAge);

            if (this.hasFadeColour)
            {
                this.particleRed += (this.fadeColourRed - this.particleRed) * 0.2F;
                this.particleGreen += (this.fadeColourGreen - this.particleGreen) * 0.2F;
                this.particleBlue += (this.fadeColourBlue - this.particleBlue) * 0.2F;
            }
        }

        this.setParticleTextureIndex(this.baseTextureIndex + (7 - this.particleAge * 8 / this.particleMaxAge));
        this.moveEntity(this.motionX, this.motionY, this.motionZ);

        if (this.onGround)
        {
            this.setDead();
        }

        if (this.field_92054_ax && this.particleAge < this.particleMaxAge / 2 && (this.particleAge + this.particleMaxAge) % 2 == 0)
        {
            EntityTrajectoryParticle entityfireworksparkfx = new EntityTrajectoryParticle(this.worldObj, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, this.field_92047_az);
            entityfireworksparkfx.setRBGColorF(this.particleRed, this.particleGreen, this.particleBlue);
            entityfireworksparkfx.particleAge = entityfireworksparkfx.particleMaxAge / 2;

            if (this.hasFadeColour)
            {
                entityfireworksparkfx.hasFadeColour = true;
                entityfireworksparkfx.fadeColourRed = this.fadeColourRed;
                entityfireworksparkfx.fadeColourGreen = this.fadeColourGreen;
                entityfireworksparkfx.fadeColourBlue = this.fadeColourBlue;
            }

            entityfireworksparkfx.field_92048_ay = this.field_92048_ay;
            this.field_92047_az.addEffect(entityfireworksparkfx);
        }
    }
}