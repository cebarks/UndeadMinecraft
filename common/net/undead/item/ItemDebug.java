package net.undead.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.undead.UndeadMinecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDebug extends Item {

	public ItemDebug(int id) {
		super(id);
		setMaxStackSize(64);
		setUnlocalizedName("debug");
		setCreativeTab(UndeadMinecraft.tabUndead);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateIcons(IconRegister iconRegister) {
		iconIndex = iconRegister.registerIcon("UndeadMinecraft:blood");
	}
}