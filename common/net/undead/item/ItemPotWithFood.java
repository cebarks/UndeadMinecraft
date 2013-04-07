package net.undead.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.undead.UndeadMinecraft;

public class ItemPotWithFood extends Item {
	public ItemPotWithFood(int i) {
		super(i);
		setMaxStackSize(1);
		setUnlocalizedName("potWithFood");
		setCreativeTab(UndeadMinecraft.tabUndead);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateIcons(IconRegister iconRegister) {
		iconIndex = iconRegister.registerIcon("UndeadMinecraft:potWithFood");
	}
}