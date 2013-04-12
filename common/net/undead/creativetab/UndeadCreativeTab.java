package net.undead.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.undead.UndeadMinecraft;

public class UndeadCreativeTab extends CreativeTabs {

	public UndeadCreativeTab(String label) {
		super(label);
	}

	public ItemStack getIconItemStack() {
        return new ItemStack(UndeadMinecraft.zombieLimb, 1, 0);
}
}