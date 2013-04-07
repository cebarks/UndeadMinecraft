package net.undead.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.undead.UndeadMinecraft;
import net.undead.entity.EntityBullet;
import net.undead.entity.EntityNewZombie;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemGun extends Item {

	private long lastFire;
	
	public ItemGun(int id) {
		super(id);
		setCreativeTab(UndeadMinecraft.tabUndead);
		lastFire = System.currentTimeMillis();
	}

	public void fire(World world, EntityPlayer entityPlayer) {
		if(System.currentTimeMillis()>=lastFire+500) {
			double locX = entityPlayer.serverPosX;
			double locY = entityPlayer.serverPosY;
			double locZ = entityPlayer.serverPosZ;
			for (int x = (int) locX; x < 2; x++) {
				for (int y = (int) locY; y < 2; y++) {
					for (int z = (int) locZ; z < 2; z++) {
						world.spawnParticle("smoke", x, y, z, 1, 1, 1);
					}
				}
			}
			world.playSoundAtEntity(entityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			EntityBullet eb = new EntityBullet(world, entityPlayer, 10.0F);
			eb.setDamage(.1);
			world.spawnEntityInWorld(eb);
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world,
			EntityPlayer ep) {
		if (ep.inventory.hasItem(UndeadMinecraft.pistolAmmo.itemID)) {
			fire(world, ep);
			if(!ep.capabilities.isCreativeMode)
				ep.inventory.consumeInventoryItem(UndeadMinecraft.pistolAmmo.itemID);
			
			for (Object o : world.loadedEntityList) {
				if (o instanceof EntityNewZombie) {
					EntityNewZombie enz = (EntityNewZombie) o;

					double posX = enz.serverPosX;
					double posY = enz.serverPosY;
					double posZ = enz.serverPosZ;

					double locX = ep.serverPosX;
					double locY = ep.serverPosY;
					double locZ = ep.serverPosZ;
					double distance = 500;
					if (posX <= locX - distance && posX >= locX + distance
							&& posY <= locY - distance
							&& posY >= locY + distance
							&& posZ <= locZ - distance
							&& posZ >= locZ - distance) {
						enz.setAttackTarget(ep);
					}
				}
			}
		}
		return super.onItemRightClick(itemStack, world, ep);
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void updateIcons(IconRegister iconRegister) {
		iconIndex = iconRegister.registerIcon("UndeadMinecraft:blood");
	}
}