package net.undead;

import net.minecraft.client.model.ModelBiped;
import net.undead.render.RenderNewZombie;
import net.undead.render.RenderSurvivor;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(net.undead.entity.EntitySurvivor.class, new RenderSurvivor(new ModelBiped(), 0));
		RenderingRegistry.registerEntityRenderingHandler(net.undead.entity.EntityNewZombie.class, new RenderNewZombie(new ModelBiped(), 0));
	}
}