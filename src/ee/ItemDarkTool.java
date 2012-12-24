/*    */ package ee;
/*    */ 
/*    */ import net.minecraft.server.Block;
/*    */ import net.minecraft.server.Entity;
/*    */ import net.minecraft.server.EntityHuman;
/*    */ import net.minecraft.server.EntityLiving;
/*    */ import net.minecraft.server.ItemStack;
/*    */ import net.minecraft.server.World;
/*    */ 
/*    */ public abstract class ItemDarkTool extends ItemEECharged
/*    */ {
/*    */   private Block[] blocksEffectiveAgainst;
/* 13 */   private float efficiencyOnProperMaterial = 14.0F;
/*    */ 
/*    */   protected ItemDarkTool(int var1, int var2, int var3, Block[] var4)
/*    */   {
/* 17 */     super(var1, var2);
/* 18 */     this.blocksEffectiveAgainst = var4;
/* 19 */     this.weaponDamage = (var3 + 3);
/*    */   }
/*    */ 
/*    */   public float getDestroySpeed(ItemStack var1, Block var2)
/*    */   {
/* 28 */     for (int var3 = 0; var3 < this.blocksEffectiveAgainst.length; var3++)
/*    */     {
/* 30 */       if (this.blocksEffectiveAgainst[var3] == var2)
/*    */       {
/* 32 */         return this.efficiencyOnProperMaterial;
/*    */       }
/*    */     }
/*    */ 
/* 36 */     return 1.0F;
/*    */   }
/*    */ 
/*    */   public int a(Entity var1)
/*    */   {
/* 44 */     return this.weaponDamage;
/*    */   }
/*    */ 
/*    */   public boolean a(ItemStack var1, EntityLiving var2, EntityLiving var3)
/*    */   {
/* 53 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean a(ItemStack var1, int var2, int var3, int var4, int var5, EntityLiving var6)
/*    */   {
/* 58 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean isFull3D()
/*    */   {
/* 63 */     return true;
/*    */   }
/*    */ 
/*    */   public void doBreak(ItemStack var1, World var2, EntityHuman var3)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void doPassive(ItemStack var1, World var2, EntityHuman var3)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void doActive(ItemStack var1, World var2, EntityHuman var3)
/*    */   {
/*    */   }
/*    */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemDarkTool
 * JD-Core Version:    0.6.2
 */