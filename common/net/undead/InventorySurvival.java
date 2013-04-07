package net.undead;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.undead.entity.EntityWithInventory;

public class InventorySurvival implements IInventory {
	public ItemStack inventory[];
	public EntityWithInventory ent;
	public boolean inventoryChanged;
	public int slotCount;

	public InventorySurvival(EntityWithInventory entityWithInventory,
			int i) {
		slotCount = i;
		inventory = new ItemStack[i]; // how many items it can hold
		inventoryChanged = false;
		ent = entityWithInventory; // pass the entity class
	}

	private int getInventorySlotContainItem(int i) {
		for (int j = 0; j < inventory.length; j++) {
			if (inventory[j] != null && inventory[j].itemID == i) {
				return j;
			}
		}

		return -1;
	}

	public boolean isFull() {
		for (int i = 0; i < inventory.length; i++) {
			if (getStackInSlot(i) == null)
				return false;
		}
		return true;
	}

	public ItemStack getFirstSwordStack() {
		for (int i = 0; i < getSizeInventory(); i++) {
			Item item1 = getStackInSlot(i).getItem();
			if (item1 instanceof ItemSword) {
				return getStackInSlot(i);
			}
		}
		return null;
	}

	public ItemStack getFirstBowStack() {
		for (int i = 0; i < getSizeInventory(); i++) {
			Item item1 = getStackInSlot(i).getItem();
			if (item1 instanceof ItemBow) {
				return getStackInSlot(i);
			}
		}
		return null;
	}

	public int getItemStackSlot(ItemStack item) {
		for (int i = 0; i < getSizeInventory(); i++) {
			if (getStackInSlot(i).itemID == item.itemID) {
				return i;
			}
		}
		return -1;
	}

	public int storeItemStack(ItemStack itemstack) {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] != null
					&& inventory[i].itemID == itemstack.itemID
					&& inventory[i].isStackable()
					&& inventory[i].stackSize < inventory[i].getMaxStackSize()
					&& inventory[i].stackSize < getInventoryStackLimit()
					&& (!inventory[i].getHasSubtypes() || inventory[i]
							.getItemDamage() == itemstack.getItemDamage())) {
				return i;
			}
		}

		return -1;
	}

	public int getFirstEmptyStack() {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] == null) {
				return i;
			}
		}

		return -1;
	}

	public boolean contains(int id) {
		for (int i = 0; i < 36; i++) {
			ItemStack itemstack2 = getStackInSlot(i);
			if (itemstack2 != null) {
				if (itemstack2.itemID == id) {
					return true;
				}
			}
		}
		return false;
	}

	private int storePartialItemStack(ItemStack itemstack) {
		int i = itemstack.itemID;
		int j = itemstack.stackSize;
		int k = storeItemStack(itemstack);
		if (k < 0) {
			k = getFirstEmptyStack();
		}
		if (k < 0) {
			return j;
		}
		if (inventory[k] == null) {
			inventory[k] = new ItemStack(i, 0, itemstack.getItemDamage());
		}
		int l = j;
		if (l > inventory[k].getMaxStackSize() - inventory[k].stackSize) {
			l = inventory[k].getMaxStackSize() - inventory[k].stackSize;
		}
		if (l > getInventoryStackLimit() - inventory[k].stackSize) {
			l = getInventoryStackLimit() - inventory[k].stackSize;
		}
		if (l == 0) {
			return j;
		} else {
			j -= l;
			inventory[k].stackSize += l;
			inventory[k].animationsToGo = 5;
			return j;
		}
	}

	public boolean consumeInventoryItem(int i) {
		int j = getInventorySlotContainItem(i);
		if (j < 0) {
			return false;
		}
		if (--inventory[j].stackSize <= 0) {
			inventory[j] = null;
		}
		return true;
	}

	public boolean addItemStackToInventory(ItemStack itemstack) {
		if (!itemstack.isItemDamaged()) {
			int i;
			do {
				i = itemstack.stackSize;
				itemstack.stackSize = storePartialItemStack(itemstack);
			} while (itemstack.stackSize > 0 && itemstack.stackSize < i);
			return itemstack.stackSize < i;
		}
		int j = getFirstEmptyStack();
		if (j >= 0) {
			inventory[j] = ItemStack.copyItemStack(itemstack);
			itemstack.stackSize = 0;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		ItemStack aitemstack[] = inventory;
		if (aitemstack[i] != null) {
			if (aitemstack[i].stackSize <= j) {
				ItemStack itemstack = aitemstack[i];
				aitemstack[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = aitemstack[i].splitStack(j);
			if (aitemstack[i].stackSize == 0) {
				aitemstack[i] = null;
			}
			return itemstack1;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		ItemStack aitemstack[] = inventory;
		aitemstack[i] = itemstack;
	}

	public NBTTagList writeToNBT(NBTTagList nbttaglist) {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte) i);
				inventory[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}
		return nbttaglist;
	}

	public void readFromNBT(NBTTagList nbttaglist) {
		inventory = new ItemStack[36];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound = (NBTTagCompound) nbttaglist
					.tagAt(i);
			int j = nbttagcompound.getByte("Slot") & 0xff;
			ItemStack itemstack = ItemStack
					.loadItemStackFromNBT(nbttagcompound);
			if (itemstack.getItem() == null) {
				continue;
			}
			if (j >= 0 && j < inventory.length) {
				inventory[j] = itemstack;
			}
		}

	}

	@Override
	public int getSizeInventory() {
		return inventory.length + 1;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		ItemStack aitemstack[] = inventory;
		return aitemstack[i];
	}

	@Override
	public String getInvName() {
		return "Inventory";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void onInventoryChanged() {
		inventoryChanged = true;
	}

	public boolean canInteractWith(EntityPlayer entityplayer) {
		if (ent.isDead) {
			return false;
		} else {
			return entityplayer.getDistanceSqToEntity(ent) <= 64D;
		}
	}

	public boolean func_28018_c(ItemStack itemstack) {
		for (int j = 0; j < inventory.length; j++) {
			if (inventory[j] != null && inventory[j].isItemEqual(itemstack)) {
				return true;
			}
		}

		return false;
	}

	public void dropAllItems() {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] != null) {
				ent.entityDropItem(inventory[i], 0.0F);
				inventory[i] = null;
			}
		}

	}

	public void func_35142_x_() {

	}

	public void func_35141_y_() {

	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return false;
	}

	@Override
	public void openChest() {

	}

	@Override
	public void closeChest() {

	}

	public ArrayList<ItemStack> getAllSwords() {
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		for (int i = 0; i < 32; i++) {
			if (getStackInSlot(i) == null)
				continue;
			if (getStackInSlot(i).getItem() instanceof ItemSword) {
				list.add(getStackInSlot(i));
			}
		}
		return list;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		return false;
	}
}
