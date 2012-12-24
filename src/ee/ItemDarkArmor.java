/*    */ package ee;
/*    */ 
/*    */ import forge.ArmorProperties;
/*    */ import forge.ISpecialArmor;
/*    */ import forge.ITextureProvider;
/*    */ import net.minecraft.server.DamageSource;
/*    */ import net.minecraft.server.EntityHuman;
/*    */ import net.minecraft.server.EntityLiving;
/*    */ import net.minecraft.server.EnumArmorMaterial;
/*    */ import net.minecraft.server.ItemArmor;
/*    */ import net.minecraft.server.ItemStack;
/*    */ 
/*    */ public class ItemDarkArmor extends ItemArmor
/*    */   implements ISpecialArmor, ITextureProvider
/*    */ {
/*    */   public ItemDarkArmor(int var1, int var2, int var3)
/*    */   {
/* 17 */     super(var1, EnumArmorMaterial.DIAMOND, var2, var3);
/* 18 */     setMaxDurability(0);
/*    */   }
/*    */ 
/*    */   public String getTextureFile()
/*    */   {
/* 23 */     return "/eqex/eqexsheet.png";
/*    */   }
/*    */ 
/*    */   public void damageArmor(EntityLiving var1, ItemStack var2, DamageSource var3, int var4, int var5) {
/*    */   }
/*    */ 
/*    */   public ArmorProperties getProperties(EntityLiving var1, ItemStack var2, DamageSource var3, double var4, int var6) {
/* 30 */     return (var6 != 0) && (var6 != 3) ? new ArmorProperties(0, 0.3D, 150) : (var6 == 0) && (var3 == DamageSource.FALL) ? new ArmorProperties(1, 1.0D, 5) : new ArmorProperties(0, 0.2D, 100);
/*    */   }
/*    */ 
/*    */   public int getArmorDisplay(EntityHuman var1, ItemStack var2, int var3)
/*    */   {
/* 35 */     return (var3 != 0) && (var3 != 3) ? 6 : 4;
/*    */   }
/*    */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemDarkArmor
 * JD-Core Version:    0.6.2
 */