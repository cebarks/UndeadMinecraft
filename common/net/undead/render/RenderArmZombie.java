package net.undead.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.undead.entity.EntityArmZombie;

public class RenderArmZombie extends RenderLiving {

	public RenderArmZombie(ModelBase modelbase, float f) {
		super(modelbase, f);
	}

	public void renderArmZombie(EntityArmZombie entityarmzombie, double d,
			double d1, double d2, float f, float f1) {
		super.doRenderLiving(entityarmzombie, d, d1, d2, f, f1);
	}

	@Override
	public void doRenderLiving(EntityLiving entityliving, double d, double d1,
			double d2, float f, float f1) {
		renderArmZombie((EntityArmZombie) entityliving, d, d1, d2, f, f1);
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2,
			float f, float f1) {
		renderArmZombie((EntityArmZombie) entity, d, d1, d2, f, f1);
	}
}
