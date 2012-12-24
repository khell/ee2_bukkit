/*    */ package ee;
/*    */ 
/*    */ import forge.ArmorProperties;
/*    */ import net.minecraft.server.DamageSource;
/*    */ import net.minecraft.server.EntityHuman;
/*    */ import net.minecraft.server.EntityLiving;
/*    */ import net.minecraft.server.ItemStack;
/*    */ 
/*    */ public class ItemRedArmor extends ItemDarkArmor
/*    */ {
/*    */   public ItemRedArmor(int var1, int var2, int var3)
/*    */   {
/* 13 */     super(var1, var2, var3);
/* 14 */     setMaxDurability(0);
/*    */   }
/*    */ 
/*    */   public String getTextureFile()
/*    */   {
/* 19 */     return "/eqex/eqexsheet.png";
/*    */   }
/*    */ 
/*    */   public void damageArmor(EntityLiving var1, ItemStack var2, DamageSource var3, int var4, int var5) {
/*    */   }
/*    */ 
/*    */   public ArmorProperties getProperties(EntityLiving var1, ItemStack var2, DamageSource var3, double var4, int var6) {
/* 26 */     return (var6 != 0) && (var6 != 3) ? new ArmorProperties(0, 0.3D, 225) : (var6 == 0) && (var3 == DamageSource.FALL) ? new ArmorProperties(1, 1.0D, 10) : new ArmorProperties(0, 0.2D, 150);
/*    */   }
/*    */ 
/*    */   public int getArmorDisplay(EntityHuman var1, ItemStack var2, int var3)
/*    */   {
/* 31 */     return (var3 != 0) && (var3 != 3) ? 6 : 4;
/*    */   }
/*    */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemRedArmor
 * JD-Core Version:    0.6.2
 */