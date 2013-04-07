package net.undead.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.undead.UndeadMinecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemLeatherCanteenWithWater extends Item {

	public ItemLeatherCanteenWithWater(int par1) {
		super(par1);
		setUnlocalizedName("leatherCanteenWithWater");
		setCreativeTab(UndeadMinecraft.tabUndead);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 48;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		par3EntityPlayer.setItemInUse(par1ItemStack,
				getMaxItemUseDuration(par1ItemStack));
		return super.onItemRightClick(par1ItemStack, par2World,
				par3EntityPlayer);
	}

	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		par1ItemStack.stackSize--;
		par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(
				UndeadMinecraft.leatherCanteen));
		//TODO fix
		//par3EntityPlayer.addThirst(15);
		return super.onEaten(par1ItemStack, par2World, par3EntityPlayer);
	}

	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.drink;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateIcons(IconRegister iconRegister) {
		iconIndex = iconRegister.registerIcon("UndeadMinecraft:waterCanteen2");
	}

}