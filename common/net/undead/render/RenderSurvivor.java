package net.undead.render;

import java.awt.Color;
import java.awt.Graphics;

import javax.imageio.ImageIO;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.src.ModLoader;
import net.undead.entity.EntitySurvivor;

public class RenderSurvivor extends RenderBiped {

	public RenderSurvivor(ModelBiped par1ModelBiped, float par2) {
		this(par1ModelBiped, par2, 1.0F);
	}

	public RenderSurvivor(ModelBiped par1ModelBiped, float par2, float par3) {
		super(par1ModelBiped, par2);
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		renderName((EntitySurvivor) entity, d, d1, d2, f, f1);
		super.doRender(entity, d, d1, d2, f, f1);
	}

	public RenderManager GetRenderManager() {
		return renderManager;
	}

	@Override
	protected void renderModel(EntityLiving entityliving, float f, float f1, float f2, float f3, float f4, float f5) {
		EntitySurvivor survivor = (EntitySurvivor) entityliving;
		RenderEngine re = renderManager.renderEngine;

		if (survivor.textureInt == -1) {
			setTexture(entityliving);
		} else {
			//re.bindTexture(survivor.textureInt);
			re.setupTexture(survivor.textureBuffer, survivor.textureInt);
		}

		mainModel.render(entityliving, f, f1, f2, f3, f4, f5);
		super.renderModel(entityliving, f, f1, f2, f3, f4, f5);
	}

	public void renderName(EntitySurvivor entity, double d, double d1, double d2, float f, float f1) {
		super.doRenderLiving(entity, d, d1, d2, f, f1);

		if (entity.name.length() > 0 && entity.name != "" && !entity.isFriends) {
			renderLivingLabel(entity, entity.name, d, d1, d2, 64);
		}
		if (entity.name.length() > 0 && entity.name != "" && entity.isFriends) {
			renderLivingLabel(entity, "" + entity.name, d, d1, d2, 64);
		}
		if (entity.isDebug) {
			renderLivingLabel(entity, "isLeader: " + entity.leader, d, d1 + 0.5D, d2, 64);
			renderLivingLabel(entity, "isFollower:" + entity.follower, d, d1 + 0.75D, d2, 64);
			renderLivingLabel(entity, "Attack Strength:" + entity.attackStrength, d, d1 + 1D, d2, 64);
			renderLivingLabel(entity, "heldItem: " + entity.getHeldItem(), d, d1 + 1.25D, d2, 64);
			renderLivingLabel(entity, "entityToAttack: " + entity.getEntityToAttack(), d, d1 + 1.5D, d2, 64);
			renderLivingLabel(entity, "My Entity: " + entity, d, d1 + 1.75D, d2, 64);
			renderLivingLabel(entity, "shouldFollow: " + entity.shouldFollowPlayer, d, d1 + 2D, d2, 64);
			renderLivingLabel(entity, "health: " + entity.getHealth(), d, d1 + 2.25D, d2, 64);

		}
	}

	public void setTexture(EntityLiving entityliving) {
		EntitySurvivor survivor = (EntitySurvivor) entityliving;
		Graphics bfg = survivor.textureBuffer.getGraphics();
		RenderEngine re = renderManager.renderEngine;

		try {
			// Stuff to render *In order*
			loadDownloadableImageTexture(entityliving.skinUrl, entityliving.getTexture());
			bfg.drawImage(ImageIO.read(re.texturePack.getSelectedTexturePack().getResourceAsStream(survivor.skinTexture)), 0, 0, null);
			bfg.drawImage(ImageIO.read(re.texturePack.getSelectedTexturePack().getResourceAsStream(survivor.skinTexture)), 0, 0, null);
			bfg.drawImage(ImageIO.read(re.texturePack.getSelectedTexturePack().getResourceAsStream(survivor.eyeTexture)), 0, 0, null);
			bfg.drawImage(ImageIO.read(re.texturePack.getSelectedTexturePack().getResourceAsStream(survivor.hairTexture)), 0, 0, null);
			bfg.drawImage(ImageIO.read(re.texturePack.getSelectedTexturePack().getResourceAsStream(survivor.shirtTexture)), 0, 0, null);
			bfg.drawImage(ImageIO.read(re.texturePack.getSelectedTexturePack().getResourceAsStream(survivor.pantsTexture)), 0, 0, null);
			bfg.drawImage(ImageIO.read(re.texturePack.getSelectedTexturePack().getResourceAsStream(survivor.shoesTexture)), 0, 0, null);

		} catch (Exception e) {
		}
		
		bfg.dispose();
		
		survivor.textureInt = re.allocateAndSetupTexture(survivor.textureBuffer);
	}

}