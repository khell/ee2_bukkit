/*     */ package ee;
/*     */ 
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class ItemKleinStar extends ItemEECharged
/*     */ {
/*     */   public ItemKleinStar(int var1, int var2)
/*     */   {
/*  13 */     super(var1, 0);
/*  14 */     this.maxStackSize = 1;
/*  15 */     setMaxDurability(1001);
/*     */   }
/*     */ 
/*     */   public void a(ItemStack var1, World var2, Entity var3, int var4, boolean var5)
/*     */   {
/*  24 */     if (!EEProxy.isClient(var2))
/*     */     {
/*  26 */       onUpdate(var1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onUpdate(ItemStack var1)
/*     */   {
/*  32 */     int var2 = EEBase.getKleinLevel(var1.id);
/*     */ 
/*  34 */     if (getKleinPoints(var1) == 0)
/*     */     {
/*  36 */       var1.setData(0);
/*     */     }
/*  38 */     else if (var2 == 1)
/*     */     {
/*  40 */       if (getKleinPoints(var1) <= 50000)
/*     */       {
/*  42 */         var1.setData(var1.i() - getKleinPoints(var1) / 50);
/*     */       }
/*     */       else
/*     */       {
/*  46 */         var1.setData(1);
/*     */       }
/*     */     }
/*  49 */     else if (var2 == 2)
/*     */     {
/*  51 */       if (getKleinPoints(var1) <= 200000)
/*     */       {
/*  53 */         var1.setData(var1.i() - getKleinPoints(var1) / 200);
/*     */       }
/*     */       else
/*     */       {
/*  57 */         var1.setData(1);
/*     */       }
/*     */     }
/*  60 */     else if (var2 == 3)
/*     */     {
/*  62 */       if (getKleinPoints(var1) <= 800000)
/*     */       {
/*  64 */         var1.setData(var1.i() - getKleinPoints(var1) / 800);
/*     */       }
/*     */       else
/*     */       {
/*  68 */         var1.setData(1);
/*     */       }
/*     */     }
/*  71 */     else if (var2 == 4)
/*     */     {
/*  73 */       if (getKleinPoints(var1) <= 3200000)
/*     */       {
/*  75 */         var1.setData(var1.i() - getKleinPoints(var1) / 3200);
/*     */       }
/*     */       else
/*     */       {
/*  79 */         var1.setData(1);
/*     */       }
/*     */     }
/*  82 */     else if (var2 == 5)
/*     */     {
/*  84 */       if (getKleinPoints(var1) <= 12800000)
/*     */       {
/*  86 */         var1.setData(var1.i() - getKleinPoints(var1) / 12800);
/*     */       }
/*     */       else
/*     */       {
/*  90 */         var1.setData(1);
/*     */       }
/*     */     }
/*  93 */     else if (var2 == 6)
/*     */     {
/*  95 */       if (getKleinPoints(var1) <= 51200000)
/*     */       {
/*  97 */         var1.setData(var1.i() - getKleinPoints(var1) / 51200);
/*     */       }
/*     */       else
/*     */       {
/* 101 */         var1.setData(1);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getMaxPoints(ItemStack var1)
/*     */   {
/* 108 */     return EEBase.getKleinLevel(var1.id) == 6 ? 51200000 : EEBase.getKleinLevel(var1.id) == 5 ? 12800000 : EEBase.getKleinLevel(var1.id) == 4 ? 3200000 : EEBase.getKleinLevel(var1.id) == 3 ? 800000 : EEBase.getKleinLevel(var1.id) == 2 ? 200000 : EEBase.getKleinLevel(var1.id) == 1 ? 50000 : 0;
/*     */   }
/*     */ 
/*     */   public int getKleinPoints(ItemStack var1)
/*     */   {
/* 113 */     return getInteger(var1, "points");
/*     */   }
/*     */ 
/*     */   public void setKleinPoints(ItemStack var1, int var2)
/*     */   {
/* 118 */     setInteger(var1, "points", var2);
/*     */   }
/*     */   public void doChargeTick(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */   public void doUncharge(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doToggle(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doAlternate(ItemStack var1, World var2, EntityHuman var3) {
/* 129 */     var3.a("This Klein Star currently holds " + getKleinPoints(var1) + " EMC.");
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemKleinStar
 * JD-Core Version:    0.6.2
 */