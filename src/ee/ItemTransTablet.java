/*    */ package ee;
/*    */ 
/*    */ import ee.core.GuiIds;
/*    */ import net.minecraft.server.EEProxy;
/*    */ import net.minecraft.server.EntityHuman;
/*    */ import net.minecraft.server.ItemStack;
/*    */ import net.minecraft.server.World;
/*    */ import net.minecraft.server.mod_EE;
/*    */ 
/*    */ public class ItemTransTablet extends ItemEECharged
/*    */ {
/*    */   public ItemTransTablet(int var1)
/*    */   {
/* 14 */     super(var1, 0);
/*    */   }
/*    */ 
/*    */   public void doExtra(World var1, ItemStack var2, EntityHuman var3)
/*    */   {
/* 19 */     if (!EEProxy.isClient(var1))
/*    */     {
/* 21 */       var3.openGui(mod_EE.getInstance(), GuiIds.PORT_TRANS_TABLE, var1, (int)var3.locX, (int)var3.locY, (int)var3.locZ);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void doRelease(ItemStack var1, World var2, EntityHuman var3)
/*    */   {
/* 27 */     var3.C_();
/* 28 */     var2.makeSound(var3, "transmute", 0.6F, 1.0F);
/* 29 */     var2.addEntity(new EntityPhilosopherEssence(var2, var3, chargeLevel(var1)));
/*    */   }
/*    */ 
/*    */   public void doAlternate(ItemStack var1, World var2, EntityHuman var3)
/*    */   {
/* 34 */     doExtra(var2, var1, var3);
/*    */   }
/*    */ 
/*    */   public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
/*    */   {
/* 42 */     if (EEProxy.isClient(var2))
/*    */     {
/* 44 */       return var1;
/*    */     }
/*    */ 
/* 48 */     var3.openGui(mod_EE.getInstance(), GuiIds.PORT_TRANS_TABLE, var2, (int)var3.locX, (int)var3.locY, (int)var3.locZ);
/* 49 */     return var1;
/*    */   }
/*    */ 
/*    */   public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*    */   {
/* 59 */     if (EEProxy.isClient(var3))
/*    */     {
/* 61 */       return true;
/*    */     }
/*    */ 
/* 65 */     var2.openGui(mod_EE.getInstance(), GuiIds.PORT_TRANS_TABLE, var3, var4, var5, var6);
/* 66 */     return true;
/*    */   }
/*    */ 
/*    */   public void doLeftClick(ItemStack var1, World var2, EntityHuman var3)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void doToggle(ItemStack var1, World var2, EntityHuman var3)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void doChargeTick(ItemStack var1, World var2, EntityHuman var3)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void doUncharge(ItemStack var1, World var2, EntityHuman var3)
/*    */   {
/*    */   }
/*    */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemTransTablet
 * JD-Core Version:    0.6.2
 */