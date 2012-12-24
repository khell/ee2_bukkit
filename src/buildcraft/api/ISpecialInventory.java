package buildcraft.api;

import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;

public abstract interface ISpecialInventory extends IInventory
{
  public abstract boolean addItem(ItemStack paramItemStack, boolean paramBoolean, Orientations paramOrientations);

  public abstract ItemStack extractItem(boolean paramBoolean, Orientations paramOrientations);
}

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     buildcraft.api.ISpecialInventory
 * JD-Core Version:    0.6.2
 */