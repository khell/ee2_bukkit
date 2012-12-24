/*    */ package ee.core;
/*    */ 
/*    */ import ee.item.ItemLootBall;
/*    */ import forge.ForgeHooks;
/*    */ import forge.IPickupHandler;
/*    */ import net.minecraft.server.EntityHuman;
/*    */ import net.minecraft.server.EntityItem;
/*    */ import net.minecraft.server.ItemStack;
/*    */ import net.minecraft.server.PlayerInventory;
/*    */ 
/*    */ public class PickupHandler
/*    */   implements IPickupHandler
/*    */ {
/*    */   public boolean onItemPickup(EntityHuman var1, EntityItem var2)
/*    */   {
/* 14 */     ItemStack var3 = var2.itemStack;
/*    */ 
/* 16 */     if ((var3 != null) && (var3.count > 0))
/*    */     {
/* 18 */       if (!(var2.itemStack.getItem() instanceof ItemLootBall))
/*    */       {
/* 20 */         return true;
/*    */       }
/*    */ 
/* 24 */       ItemLootBall var4 = (ItemLootBall)var3.getItem();
/* 25 */       ItemStack[] var5 = var4.getDroplist(var3);
/*    */ 
/* 27 */       if (var5 != null)
/*    */       {
/* 30 */         EntityItem var6 = new EntityItem(var1.world);
/* 31 */         var6.setLocation(var1.locX, var1.locY, var1.locZ, 0.0F, 0.0F);
/*    */ 
/* 33 */         if (var6 != null)
/*    */         {
/* 35 */           ItemStack[] var7 = var5;
/* 36 */           int var8 = var5.length;
/*    */ 
/* 38 */           for (int var9 = 0; var9 < var8; var9++)
/*    */           {
/* 40 */             ItemStack var10 = var7[var9];
/*    */ 
/* 42 */             if (var10 != null)
/*    */             {
/* 44 */               var6.itemStack = var10;
/*    */ 
/* 46 */               if ((ForgeHooks.onItemPickup(var1, var6)) && (!var1.inventory.pickup(var6.itemStack)))
/*    */               {
/* 48 */                 var1.drop(var6.itemStack);
/*    */               }
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */ 
/* 55 */       var3.count = 0;
/* 56 */       return false;
/*    */     }
/*    */ 
/* 61 */     return false;
/*    */   }
/*    */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.core.PickupHandler
 * JD-Core Version:    0.6.2
 */