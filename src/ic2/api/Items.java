/*    */ package ic2.api;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.lang.reflect.Field;
/*    */ import net.minecraft.server.ItemStack;
/*    */ 
/*    */ public final class Items
/*    */ {
/*    */   public static ItemStack getItem(String var0)
/*    */   {
/*    */     try
/*    */     {
/* 11 */       Object var1 = Class.forName(getPackage() + ".common.Ic2Items").getField(var0).get(null);
/* 12 */       return (var1 instanceof ItemStack) ? (ItemStack)var1 : null;
/*    */     }
/*    */     catch (Exception var2)
/*    */     {
/* 16 */       System.out.println("IC2 API: Call getItem failed for " + var0);
/* 17 */     }return null;
/*    */   }
/*    */ 
/*    */   private static String getPackage()
/*    */   {
/* 23 */     Package var0 = Items.class.getPackage();
/* 24 */     return var0 != null ? var0.getName().substring(0, var0.getName().lastIndexOf('.')) : "ic2";
/*    */   }
/*    */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ic2.api.Items
 * JD-Core Version:    0.6.2
 */