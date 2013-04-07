package net.undead.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.undead.UndeadMinecraft;

public class ItemCamera extends Item {

	//private Minecraft mc = ModLoader.getMinecraftInstance();
	private boolean hold = false;

	public ItemCamera(int i) {
		super(i);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName("camera");
		setCreativeTab(UndeadMinecraft.tabUndead);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
//		long generate = UndeadMinecraft.undeadUtil.helpTool.GenerateImage(world);
//		if (generate != 0) {
//			mc.thePlayer.addChatMessage("Success! Took: " + generate / 1000
//					+ " second(s).");
//		} else {
//			mc.thePlayer.addChatMessage("Failed.");
//		}
		return itemstack;
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity,
			int i, boolean flag) {
		if (flag && !hold) {
//			mc.thePlayer
//					.addChatMessage("Warning: This might freeze Minecraft for a couple of seconds.");
			hold = true;
		}
		if (!flag) {
			hold = false;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateIcons(IconRegister iconRegister) {
		iconIndex = iconRegister.registerIcon("UndeadMinecraft:camera");
	}

}
