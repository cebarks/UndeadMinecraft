package net.undead.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.undead.entity.EntityCaveDweller;

public class RenderCaveDweller extends RenderLiving {

	public RenderCaveDweller(ModelBase modelbase, float f) {
		super(modelbase, f);
	}

	public void renderCaveDweller(EntityCaveDweller entitycavedweller,
			double d, double d1, double d2, float f, float f1) {
		super.doRenderLiving(entitycavedweller, d, d1, d2, f, f1);
	}

	@Override
	public void doRenderLiving(EntityLiving entityliving, double d, double d1,
			double d2, float f, float f1) {
		renderCaveDweller((EntityCaveDweller) entityliving, d, d1, d2, f, f1);
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2,
			float f, float f1) {
		renderCaveDweller((EntityCaveDweller) entity, d, d1, d2, f, f1);
	}
}
