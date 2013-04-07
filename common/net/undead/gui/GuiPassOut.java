package net.undead.gui;

import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.src.ModLoader;

import org.lwjgl.input.Keyboard;

public class GuiPassOut extends GuiScreen {

	public String reason;

	public GuiPassOut(String reason) {
		this.reason = reason;
		allowUserInput = true;
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		if (par2 == Keyboard.KEY_ESCAPE) {
			ModLoader.openGUI(ModLoader.getMinecraftInstance().thePlayer, new GuiIngameMenu());
		}
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		drawGradientRect(0, 0, width, height, 0xa0404040, 0xa0202020);
		drawCenteredString(fontRenderer, "You passed out due to " + reason
				+ ".", width / 2, 100, 0xffffff);
		super.drawScreen(par1, par2, par3);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

}
