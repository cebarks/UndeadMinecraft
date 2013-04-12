package net.undead;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSurviralInv extends Container {

	private InventorySurvival mobInv;
	public int numRows;

	public ContainerSurviralInv(IInventory iinventory,
			InventorySurvival inventorySurvival) {
		mobInv = inventorySurvival;
		// creating the inventory slots for the mob. Should look to make this
		// more modular. Right now it's 3x3
		int rows = (int) Math.floor(mobInv.slotCount / 9); // should return a
															// value from 1 to 3
															// as long as
															// inventory no
															// larger than 27
		numRows = rows;
		for (int j = 0; j < rows; j++) // row
		{ // by
			for (int i1 = 0; i1 < 9; i1++) // column
			{
				if (j < 3) {
					addSlotToContainer(new Slot(mobInv, i1 + j * 9, 8 + i1 * 18,
							17 + j * 18 - 24));
				} else {
					addSlotToContainer(new Slot(mobInv, i1 + j * 9, 8 + i1 * 18,
							17 + j * 18 - 20));
				}
			} // , x-dislplay, y-display
		}

		// making the slots for the interacting player's inventory
		for (int k = 0; k < 3; k++) {
			for (int j1 = 0; j1 < 8; j1++) {
				addSlotToContainer(new Slot(iinventory, j1 + k * 9 + 9, 8 + j1 * 18,
						84 + k * 18));
			}

		}
		// Hot slots for player's inventory
		for (int l = 0; l < 8; l++) {
			addSlotToContainer(new Slot(iinventory, l, 8 + l * 18, 142));
		}

	}

	public boolean isUsableByPlayer(EntityPlayer entityplayer) {
		return mobInv.canInteractWith(entityplayer);
	}

	public ItemStack getStackInSlot(int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i >= 10 && i < 46) {
				mergeItemStack(itemstack1, 1, 10, false);
			} else {
				mergeItemStack(itemstack1, 10, 46, false);
			}
			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
			if (itemstack1.stackSize != itemstack.stackSize) {
				slot.onPickupFromSlot(null, itemstack1);
			} else {
				return null;
			}
		}
		return itemstack;
	}

	public ItemStack transferStackInSlot(int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i < numRows * 9) {
				if (!mergeItemStack(itemstack1, numRows * 9,
						inventorySlots.size(), true)) {
					return null;
				}
			} else if (!mergeItemStack(itemstack1, 0, numRows * 9, false)) {
				return null;
			}
			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
		}
		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}
}
