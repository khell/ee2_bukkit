/*    */ package ee;
/*    */ 
/*    */ import net.minecraft.server.Block;
/*    */ import net.minecraft.server.Entity;
/*    */ import net.minecraft.server.EntityHuman;
/*    */ import net.minecraft.server.EntityLiving;
/*    */ import net.minecraft.server.ItemStack;
/*    */ import net.minecraft.server.World;
/*    */ 
/*    */ public abstract class ItemRedTool extends ItemEECharged
/*    */ {
/*    */   private Block[] blocksEffectiveAgainst;
/* 13 */   private float efficiencyOnProperMaterial = 18.0F;
/*    */   private int damageVsEntity;
/*    */ 
/*    */   protected ItemRedTool(int var1, int var2, int var3, Block[] var4)
/*    */   {
/* 18 */     super(var1, var2);
/* 19 */     this.blocksEffectiveAgainst = var4;
/* 20 */     this.weaponDamage = (var3 + 5);
/*    */   }
/*    */ 
/*    */   public float getDestroySpeed(ItemStack var1, Block var2)
/*    */   {
/* 29 */     for (int var3 = 0; var3 < this.blocksEffectiveAgainst.length; var3++)
/*    */     {
/* 31 */       if (this.blocksEffectiveAgainst[var3] == var2)
/*    */       {
/* 33 */         return this.efficiencyOnProperMaterial;
/*    */       }
/*    */     }
/*    */ 
/* 37 */     return 2.0F;
/*    */   }
/*    */ 
/*    */   public boolean a(ItemStack var1, int var2, int var3, int var4, int var5, EntityLiving var6)
/*    */   {
/* 42 */     return true;
/*    */   }
/*    */ 
/*    */   public int a(Entity var1)
/*    */   {
/* 50 */     return this.weaponDamage;
/*    */   }
/*    */ 
/*    */   public boolean a(ItemStack var1, EntityLiving var2, EntityLiving var3)
/*    */   {
/* 59 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean isFull3D()
/*    */   {
/* 64 */     return true;
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
 * Qualified Name:     ee.ItemRedTool
 * JD-Core Version:    0.6.2
 */