/*    */ package ee;
/*    */ 
/*    */ import net.minecraft.server.IInventory;
/*    */ import net.minecraft.server.ItemStack;
/*    */ import net.minecraft.server.Slot;
/*    */ 
/*    */ public class SlotTransmuteInput extends Slot
/*    */ {
/*    */   private final int slotIndex;
/*    */   public final TransTabletData transGrid;
/*    */   public int c;
/*    */   public int d;
/*    */   public int e;
/*    */ 
/*    */   public SlotTransmuteInput(IInventory var1, int var2, int var3, int var4)
/*    */   {
/* 23 */     super(var1, var2, var3, var4);
/* 24 */     this.transGrid = ((TransTabletData)var1);
/* 25 */     this.slotIndex = var2;
/* 26 */     this.d = var3;
/* 27 */     this.e = var4;
/*    */   }
/*    */ 
/*    */   public boolean isAllowed(ItemStack var1)
/*    */   {
/* 35 */     return var1 == null;
/*    */   }
/*    */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.SlotTransmuteInput
 * JD-Core Version:    0.6.2
 */