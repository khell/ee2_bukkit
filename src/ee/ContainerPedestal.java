/*    */ package ee;
/*    */ 
/*    */ import net.minecraft.server.Container;
/*    */ import net.minecraft.server.EntityHuman;
/*    */ import net.minecraft.server.IInventory;
/*    */ import net.minecraft.server.ItemStack;
/*    */ import net.minecraft.server.PlayerInventory;
/*    */ import net.minecraft.server.Slot;
/*    */ 
/*    */ public class ContainerPedestal extends Container
/*    */ {
/*    */   private TilePedestal entity;
/*    */ 
/*    */   public ContainerPedestal(IInventory var1, TilePedestal var2)
/*    */   {
/* 16 */     this.entity = var2;
/* 17 */     setPlayer(((PlayerInventory)var1).player);
/* 18 */     a(new Slot(var2, 0, 80, 20));
/*    */ 
/* 21 */     for (int var3 = 0; var3 < 3; var3++)
/*    */     {
/* 23 */       for (int var4 = 0; var4 < 9; var4++)
/*    */       {
/* 25 */         a(new Slot(var1, var4 + var3 * 9 + 9, 8 + var4 * 18, 54 + var3 * 18));
/*    */       }
/*    */     }
/*    */ 
/* 29 */     for (int var3 = 0; var3 < 9; var3++)
/*    */     {
/* 31 */       a(new Slot(var1, var3, 8 + var3 * 18, 112));
/*    */     }
/*    */   }
/*    */ 
/*    */   public IInventory getInventory()
/*    */   {
/* 37 */     return this.entity;
/*    */   }
/*    */ 
/*    */   public boolean b(EntityHuman var1)
/*    */   {
/* 42 */     return this.entity.a(var1);
/*    */   }
/*    */ 
/*    */   public ItemStack a(int var1)
/*    */   {
/* 50 */     return null;
/*    */   }
/*    */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ContainerPedestal
 * JD-Core Version:    0.6.2
 */