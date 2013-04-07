package net.undead.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.undead.UndeadMinecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemEnergyBar extends ItemFood {

	public ItemEnergyBar(int i, int j, float f, boolean flag) {
		super(i, j, f, flag);
		setUnlocalizedName("energyBar");
		setCreativeTab(UndeadMinecraft.tabUndead);
	}

	@Override
	public void onFoodEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
			entityplayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 100, 3));
			entityplayer.addPotionEffect(new PotionEffect(Potion.blindness.id, 10, 1));
			entityplayer.addPotionEffect(new PotionEffect(Potion.weakness.id, 95, 2));
			entityplayer.addPotionEffect(new PotionEffect(Potion.hunger.id, 30, 5));
		super.onFoodEaten(itemstack, world, entityplayer);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		return itemstack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateIcons(IconRegister iconRegister) {
		iconIndex = iconRegister.registerIcon("UndeadMinecraft:energyBar");
	}
}
