/*    */ package ee;
/*    */ 
/*    */ import net.minecraft.server.IInventory;
/*    */ import net.minecraft.server.ItemStack;
/*    */ import net.minecraft.server.Slot;
/*    */ 
/*    */ public class SlotMercurialTarget extends Slot
/*    */ {
/*    */   private final int slotIndex;
/*    */   public int c;
/*    */   public int d;
/*    */   public int e;
/*    */ 
/*    */   public SlotMercurialTarget(IInventory var1, int var2, int var3, int var4)
/*    */   {
/* 22 */     super(var1, var2, var3, var4);
/* 23 */     this.slotIndex = var2;
/* 24 */     this.d = var3;
/* 25 */     this.e = var4;
/*    */   }
/*    */ 
/*    */   public boolean isAllowed(ItemStack var1)
/*    */   {
/* 33 */     return var1 == null;
/*    */   }
/*    */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.SlotMercurialTarget
 * JD-Core Version:    0.6.2
 */