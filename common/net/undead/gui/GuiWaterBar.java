package net.undead.gui;

import net.minecraft.client.gui.Gui;

public class GuiWaterBar extends Gui {

	int WaterLevel;
	int MaxWaterLevel;

	public GuiWaterBar() {

		MaxWaterLevel = 182;
		WaterLevel = MaxWaterLevel;

	}
}