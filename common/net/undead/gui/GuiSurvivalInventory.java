package net.undead.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.undead.ContainerSurviralInv;
import net.undead.entity.EntitySurvivor;

import org.lwjgl.opengl.GL11;

public class GuiSurvivalInventory extends GuiContainer {

    private EntityPlayer   thePlayer;
    private EntitySurvivor theMob;

    public GuiSurvivalInventory(EntityPlayer p, EntitySurvivor m) {
        super(new ContainerSurviralInv(p.inventory, m.inventory));
        thePlayer = p;
        theMob = m;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        fontRenderer.drawString(theMob.name + "'s Inventory", 8, -17, 0x404040);
        fontRenderer.drawString(thePlayer.username + "'s Inventory", 8, (ySize - 96) + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture("/mods/UndeadMinecraft/textures/other/ZombieMod/Inv1.png");
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y - 20, 0, 0, xSize, ySize + 24);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

}
