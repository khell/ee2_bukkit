/*     */ package ee;
/*     */ 
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.Slot;
/*     */ 
/*     */ public class SlotConsume extends Slot
/*     */ {
/*     */   private final int slotIndex;
/*     */   public final TransTabletData transGrid;
/*     */   public int c;
/*     */   public int d;
/*     */   public int e;
/*     */ 
/*     */   public SlotConsume(TransTabletData var1, int var2, int var3, int var4)
/*     */   {
/*  23 */     super(var1, var2, var3, var4);
/*  24 */     this.transGrid = var1;
/*  25 */     this.slotIndex = var2;
/*  26 */     this.d = var3;
/*  27 */     this.e = var4;
/*     */   }
/*     */ 
/*     */   public void c(ItemStack var1)
/*     */   {
/*  35 */     d();
/*     */   }
/*     */ 
/*     */   public boolean isAllowed(ItemStack var1)
/*     */   {
/*  43 */     return var1 == null;
/*     */   }
/*     */ 
/*     */   public ItemStack getItem()
/*     */   {
/*  51 */     return this.transGrid.getItem(this.slotIndex);
/*     */   }
/*     */ 
/*     */   public boolean c()
/*     */   {
/*  59 */     return getItem() != null;
/*     */   }
/*     */ 
/*     */   public void set(ItemStack var1)
/*     */   {
/*  67 */     d();
/*     */ 
/*  69 */     if (!EEProxy.isClient(EEProxy.theWorld))
/*     */     {
/*  71 */       if (var1 != null)
/*     */       {
/*  73 */         if (!EEBase.isKleinStar(var1.id))
/*     */         {
/*  75 */           if ((EEMaps.getEMC(var1) != 0) || (var1.getItem() == EEItem.alchemyTome))
/*     */           {
/*  77 */             if (this.transGrid.matchesLock(var1))
/*     */             {
/*  79 */               if ((!this.transGrid.isFuelLocked()) && (!this.transGrid.isMatterLocked()))
/*     */               {
/*  81 */                 if (EEMaps.isFuel(var1))
/*     */                 {
/*  83 */                   this.transGrid.fuelLock();
/*     */                 }
/*     */                 else
/*     */                 {
/*  87 */                   this.transGrid.matterLock();
/*     */                 }
/*     */               }
/*     */ 
/*  91 */               if (!this.transGrid.playerKnows(var1.id, var1.getData()))
/*     */               {
/*  93 */                 if (var1.getItem() == EEItem.alchemyTome)
/*     */                 {
/*  95 */                   this.transGrid.pushTome();
/*     */                 }
/*     */ 
/*  98 */                 this.transGrid.pushKnowledge(var1.id, var1.getData());
/*  99 */                 this.transGrid.learned = 60;
/*     */               }
/*     */ 
/* 102 */               while (var1.count > 0)
/*     */               {
/* 104 */                 var1.count -= 1;
/* 105 */                 this.transGrid.latentEnergy += EEMaps.getEMC(var1);
/*     */               }
/*     */             }
/*     */ 
/* 109 */             if (var1.count == 0)
/*     */             {
/* 111 */               set(null);
/*     */             }
/*     */ 
/* 114 */             d();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void d()
/*     */   {
/* 126 */     this.transGrid.update();
/*     */   }
/*     */ 
/*     */   public int a()
/*     */   {
/* 135 */     return this.transGrid.getMaxStackSize();
/*     */   }
/*     */ 
/*     */   public int getBackgroundIconIndex()
/*     */   {
/* 140 */     return -1;
/*     */   }
/*     */ 
/*     */   public ItemStack a(int var1)
/*     */   {
/* 149 */     return this.transGrid.splitStack(this.slotIndex, var1);
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.SlotConsume
 * JD-Core Version:    0.6.2
 */