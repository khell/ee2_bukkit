/*    */ package forestry.api.core;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.logging.Logger;
/*    */ import net.minecraft.server.Item;
/*    */ import net.minecraft.server.ItemStack;
/*    */ import net.minecraft.server.ModLoader;
/*    */ 
/*    */ public class ItemInterface
/*    */ {
/*    */   public static ItemStack getItem(String var0)
/*    */   {
/* 11 */     ItemStack var1 = null;
/*    */     try
/*    */     {
/* 15 */       String var2 = ItemInterface.class.getPackage().getName();
/* 16 */       var2 = var2.substring(0, var2.lastIndexOf('.'));
/* 17 */       String var3 = var2.substring(0, var2.lastIndexOf('.')) + ".core.config.ForestryItem";
/* 18 */       Object var4 = Class.forName(var3).getField(var0).get(null);
/*    */ 
/* 20 */       if ((var4 instanceof Item))
/*    */       {
/* 22 */         var1 = new ItemStack((Item)var4);
/*    */       }
/* 24 */       else if ((var4 instanceof ItemStack))
/*    */       {
/* 26 */         var1 = (ItemStack)var4;
/*    */       }
/*    */     }
/*    */     catch (Exception var5)
/*    */     {
/* 31 */       ModLoader.getLogger().warning("Could not retrieve Forestry item identified by: " + var0);
/*    */     }
/*    */ 
/* 34 */     return var1;
/*    */   }
/*    */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     forestry.api.core.ItemInterface
 * JD-Core Version:    0.6.2
 */