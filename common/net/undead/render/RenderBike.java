package net.undead.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.undead.entity.EntityBike;
import net.undead.model.ModelBike;

import org.lwjgl.opengl.GL11;

public class RenderBike extends Render {
	/** instance of ModelBoat for rendering */
	protected ModelBike modelBike;

	public RenderBike() {
		shadowSize = 0.5F;
		modelBike = new ModelBike();
	}

	public void renderBike(EntityBike par1EntityBoat, double par2, double par4,
			double par6, float par8, float par9) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) par2, (float) par4, (float) par6);
		GL11.glRotatef(180F - par8, 0.0F, 1.0F, 0.0F);

		loadTexture("/terrain.png");
		float f2 = 0.75F;
		GL11.glScalef(f2, f2, f2);
		GL11.glScalef(1.0F / f2, 1.0F / f2, 1.0F / f2);
		loadTexture("/textures/ZombieMod/Bike.png");
		GL11.glScalef(-1F, -1F, 1.0F);
		modelBike
				.render(par1EntityBoat, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}

	@Override
	public void doRender(Entity par1Entity, double par2, double par4,
			double par6, float par8, float par9) {
		renderBike((EntityBike) par1Entity, par2, par4, par6, par8, par9);
	}
}
