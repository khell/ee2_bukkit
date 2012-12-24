/*     */ package ee;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.server.AxisAlignedBB;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityExperienceOrb;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityItem;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.World;
/*     */ import net.minecraft.server.WorldData;
/*     */ 
/*     */ public class ItemWatchOfTime extends ItemEECharged
/*     */ {
/*     */   public boolean itemCharging;
/*     */ 
/*     */   public ItemWatchOfTime(int var1)
/*     */   {
/*  20 */     super(var1, 4);
/*     */   }
/*     */ 
/*     */   public int getIconFromDamage(int var1)
/*     */   {
/*  25 */     return isActivated(var1) ? this.textureId + 1 : this.textureId;
/*     */   }
/*     */ 
/*     */   public void slowEntities(Entity var1, int var2)
/*     */   {
/*  30 */     if ((!(var1 instanceof EntityItem)) && (!(var1 instanceof EntityExperienceOrb)) && (!(var1 instanceof EntityGrimArrow)) && (!(var1 instanceof EntityPhilosopherEssence)) && (!(var1 instanceof EntityLavaEssence)) && (!(var1 instanceof EntityWaterEssence)) && (!(var1 instanceof EntityWindEssence)) && (!(var1 instanceof EntityHyperkinesis)) && (!(var1 instanceof EntityPyrokinesis)))
/*     */     {
/*  32 */       if (var2 < 4)
/*     */       {
/*  34 */         var1.motX /= var2 * (var2 + 1);
/*  35 */         var1.motZ /= var2 * (var2 + 1);
/*     */       }
/*     */       else
/*     */       {
/*  39 */         var1.motX /= (var2 * var2 * var2 * var2 + 1);
/*  40 */         var1.motZ /= (var2 * var2 * var2 * var2 + 1);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void ConsumeReagent(ItemStack var1, EntityHuman var2, boolean var3)
/*     */   {
/*  47 */     EEBase.ConsumeReagentForDuration(var1, var2, var3);
/*     */   }
/*     */ 
/*     */   public void doPassive(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*  52 */     if (EEBase.getWatchCycle(var3) == 1)
/*     */     {
/*  54 */       EEProxy.getWorldInfo(var2).a(EEProxy.getWorldInfo(var2).getTime() + chargeLevel(var1) * 2);
/*     */     }
/*     */ 
/*  57 */     if (EEBase.getWatchCycle(var3) == 2)
/*     */     {
/*  59 */       EEProxy.getWorldInfo(var2).a(EEProxy.getWorldInfo(var2).getTime() - chargeLevel(var1) * 2);
/*     */     }
/*     */ 
/*  62 */     List var4 = var2.getEntities(var3, AxisAlignedBB.b(var3.locX - 10.0D, var3.locY - 10.0D, var3.locZ - 10.0D, var3.locX + 10.0D, var3.locY + 10.0D, var3.locZ + 10.0D));
/*  63 */     Iterator var6 = var4.iterator();
/*     */ 
/*  65 */     while (var6.hasNext())
/*     */     {
/*  67 */       Entity var5 = (Entity)var6.next();
/*  68 */       slowEntities(var5, chargeLevel(var1) + 1);
/*     */     }
/*     */ 
/*  71 */     ItemStack[] var9 = new ItemStack[9];
/*  72 */     var9 = EEBase.quickBar(var3);
/*     */ 
/*  74 */     for (int var7 = 0; var7 < var9.length; var7++)
/*     */     {
/*  76 */       if ((var9[var7] != null) && ((var9[var7].getItem() instanceof ItemWatchOfTime)) && (((ItemWatchOfTime)var9[var7].getItem()).isActivated(var9[var7])))
/*     */       {
/*  78 */         for (int var8 = 0; var8 < var9.length; var8++)
/*     */         {
/*  80 */           if ((var7 != var8) && (var9[var8] != null) && ((var9[var8].getItem() instanceof ItemWatchOfTime)) && (((ItemWatchOfTime)var9[var8].getItem()).isActivated(var9[var8])))
/*     */           {
/*  82 */             ((ItemWatchOfTime)var9[var8].getItem()).doToggle(var1, var2, var3);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doActive(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doHeld(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doRelease(ItemStack var1, World var2, EntityHuman var3) {  }
/*     */ 
/*     */ 
/*  97 */   public void doAlternate(ItemStack var1, World var2, EntityHuman var3) { EEBase.updateWatchCycle(var3); }
/*     */ 
/*     */   public void doLeftClick(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean canActivate() {
/* 104 */     return true;
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemWatchOfTime
 * JD-Core Version:    0.6.2
 */