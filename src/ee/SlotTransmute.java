/*     */ package ee;
/*     */ 
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.Slot;
/*     */ 
/*     */ public class SlotTransmute extends Slot
/*     */ {
/*     */   private final int slotIndex;
/*     */   public final TransTabletData transGrid;
/*     */   public int c;
/*     */   public int d;
/*     */   public int e;
/*     */ 
/*     */   public SlotTransmute(TransTabletData var1, int var2, int var3, int var4)
/*     */   {
/*  22 */     super(var1, var2, var3, var4);
/*  23 */     this.transGrid = var1;
/*  24 */     this.slotIndex = var2;
/*  25 */     this.d = var3;
/*  26 */     this.e = var4;
/*     */   }
/*     */ 
/*     */   public void c(ItemStack var1)
/*     */   {
/*  34 */     this.transGrid.latentEnergy += this.transGrid.currentEnergy - this.transGrid.kleinEMCTotal();
/*  35 */     this.transGrid.latentEnergy -= EEMaps.getEMC(var1);
/*     */ 
/*  37 */     for (int var2 = 0; var2 < 8; var2++)
/*     */     {
/*  39 */       if (this.transGrid.items[var2] != null)
/*     */       {
/*  41 */         if ((this.transGrid.items[var2].getItem() instanceof ItemKleinStar))
/*     */         {
/*  43 */           if (this.transGrid.latentEnergy < 0)
/*     */           {
/*  45 */             if (((ItemKleinStar)this.transGrid.items[var2].getItem()).getKleinPoints(this.transGrid.items[var2]) > 0)
/*     */             {
/*  47 */               int var3 = -this.transGrid.latentEnergy;
/*     */ 
/*  49 */               if (((ItemKleinStar)this.transGrid.items[var2].getItem()).getKleinPoints(this.transGrid.items[var2]) < var3)
/*     */               {
/*  51 */                 var3 = ((ItemKleinStar)this.transGrid.items[var2].getItem()).getKleinPoints(this.transGrid.items[var2]);
/*     */               }
/*     */ 
/*  54 */               this.transGrid.latentEnergy += var3;
/*  55 */               ((ItemKleinStar)this.transGrid.items[var2].getItem()).setKleinPoints(this.transGrid.items[var2], ((ItemKleinStar)this.transGrid.items[var2].getItem()).getKleinPoints(this.transGrid.items[var2]) - var3);
/*     */             }
/*     */ 
/*  58 */             ((ItemKleinStar)this.transGrid.items[var2].getItem()).onUpdate(this.transGrid.items[var2]);
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/*  63 */           this.transGrid.items[var2].count -= 1;
/*     */ 
/*  65 */           if (this.transGrid.items[var2].count < 1)
/*     */           {
/*  67 */             this.transGrid.items[var2] = null;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  73 */     this.transGrid.calculateEMC();
/*  74 */     d();
/*     */   }
/*     */ 
/*     */   public boolean isAllowed(ItemStack var1)
/*     */   {
/*  82 */     return false;
/*     */   }
/*     */ 
/*     */   public ItemStack getItem()
/*     */   {
/*  90 */     return this.transGrid.getItem(this.slotIndex);
/*     */   }
/*     */ 
/*     */   public boolean c()
/*     */   {
/*  98 */     return getItem() != null;
/*     */   }
/*     */ 
/*     */   public void set(ItemStack var1)
/*     */   {
/* 106 */     d();
/*     */   }
/*     */ 
/*     */   public void d()
/*     */   {
/* 114 */     this.transGrid.update();
/*     */   }
/*     */ 
/*     */   public int a()
/*     */   {
/* 123 */     return this.transGrid.getMaxStackSize();
/*     */   }
/*     */ 
/*     */   public int getBackgroundIconIndex()
/*     */   {
/* 128 */     return -1;
/*     */   }
/*     */ 
/*     */   public ItemStack a(int var1)
/*     */   {
/* 137 */     return this.transGrid.splitStack(this.slotIndex, var1);
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.SlotTransmute
 * JD-Core Version:    0.6.2
 */