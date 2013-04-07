package net.undead.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.undead.ContainerSurviralInv;
import net.undead.entity.EntitySurvivor;

import org.lwjgl.opengl.GL11;

public class GuiSurvivalInventory extends GuiContainer {

	private EntityPlayer thePlayer;
	private EntitySurvivor theMob;
	public GuiSurvivalInventory(EntityPlayer p, EntitySurvivor m) {
		super(new ContainerSurviralInv(p.inventory, m.inventory));
		thePlayer = p;
		theMob = m;
	}

	protected void drawGuiContainerForegroundLayer() {
		fontRenderer.drawString(theMob.name + "'s Inventory", 46, -17, 0x404040);
		fontRenderer.drawString(thePlayer.username + "'s Inventory", 8,
				(ySize - 96) + 2, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		//TODO FIX
		//int back = mc.renderEngine.getTexture("/textures/ZombieMod/Inv1.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		//TODO FIX
		//mc.renderEngine.bindTexture(back);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y - 20, 0, 0, xSize, ySize + 24);
		
		// draws in
																	// the
																	// background
																	// image
		// int mobslots = mc.renderEngine.getTexture("/ILN/3x9InvBoxes.png");
		// //display Mob's slots at the top
		// mc.renderEngine.bindTexture(mobslots);
		// j = (width - xSize) / 2;
		// k = (height - ySize) / 2;
		// drawTexturedModalRect(j, k, 0, 0, xSize, (inventoryRows) * 18 + 17);
		// //crop overlay to match number of rows (1-3)
	}

	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}

}
