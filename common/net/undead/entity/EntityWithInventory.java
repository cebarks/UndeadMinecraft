package net.undead.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.undead.InventorySurvival;

public class EntityWithInventory extends EntityCreature {

	public EntityWithInventory(World world) {
		super(world);
	}
	public int slotCount = 9; // default the number of slots in the inventory to
								// 3x3 or 9
	public InventorySurvival inventory = new InventorySurvival(this,
			slotCount);

	public void setInvSize(int i) {
		// should be set in multiples of 9, no greater than 27
		slotCount = i;
		inventory = new InventorySurvival(this, slotCount); // re-initialize
																// the inventory
																// obj
	}

	public void openInventory(EntityPlayer theplayer) {
		/*
		 * GUI call here.Should be created/overridden in the entity sub class
		 */
	}
	
	@Override
	public void onDeath(DamageSource damagesource) // when mob dies
	{
		inventory.dropAllItems(); // when dead drop all inventory! **
		super.onDeath(damagesource);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
	}

	// END:Inventory
	// code-----------------------------------------------------------

	// START:Read/Write
	// code--------------------------------------------------------
	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setTag("Inventory",
				inventory.writeToNBT(new NBTTagList())); // write the inventory
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
		NBTTagList nbttaglist = nbttagcompound.getTagList("Inventory");
		inventory.readFromNBT(nbttaglist);
	}

	// END:Read/Write
	// code--------------------------------------------------------

	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	public int getMaxHealth() {
		return 0;
	}
}