/*    */ package ee;
/*    */ 
/*    */ import net.minecraft.server.EEProxy;
/*    */ import net.minecraft.server.EntityHuman;
/*    */ import net.minecraft.server.ItemStack;
/*    */ import net.minecraft.server.World;
/*    */ 
/*    */ public class ItemHyperkineticLens extends ItemEECharged
/*    */ {
/*    */   public boolean itemCharging;
/*    */ 
/*    */   public ItemHyperkineticLens(int var1)
/*    */   {
/* 14 */     super(var1, 3);
/*    */   }
/*    */ 
/*    */   public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*    */   {
/* 23 */     return false;
/*    */   }
/*    */ 
/*    */   public void doBreak(ItemStack var1, World var2, EntityHuman var3)
/*    */   {
/* 28 */     int var4 = 1;
/*    */ 
/* 30 */     if (chargeLevel(var1) > 0)
/*    */     {
/* 32 */       var4++;
/*    */     }
/*    */ 
/* 35 */     if (chargeLevel(var1) > 1)
/*    */     {
/* 37 */       var4++;
/* 38 */       var4++;
/*    */     }
/*    */ 
/* 41 */     if (chargeLevel(var1) > 2)
/*    */     {
/* 43 */       var4++;
/* 44 */       var4++;
/*    */     }
/*    */ 
/* 47 */     var2.makeSound(var3, "wall", 1.0F, 1.0F);
/* 48 */     var2.addEntity(new EntityHyperkinesis(var2, var3, chargeLevel(var1), var4));
/*    */   }
/*    */ 
/*    */   public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
/*    */   {
/* 56 */     if (EEProxy.isClient(var2))
/*    */     {
/* 58 */       return var1;
/*    */     }
/*    */ 
/* 62 */     doBreak(var1, var2, var3);
/* 63 */     return var1;
/*    */   }
/*    */ 
/*    */   public void doRelease(ItemStack var1, World var2, EntityHuman var3)
/*    */   {
/* 69 */     if (!EEProxy.isClient(var2))
/*    */     {
/* 71 */       doBreak(var1, var2, var3);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void doLeftClick(ItemStack var1, World var2, EntityHuman var3)
/*    */   {
/* 77 */     if (!EEProxy.isClient(var2))
/*    */     {
/* 79 */       doBreak(var1, var2, var3);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void doToggle(ItemStack var1, World var2, EntityHuman var3)
/*    */   {
/*    */   }
/*    */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemHyperkineticLens
 * JD-Core Version:    0.6.2
 */