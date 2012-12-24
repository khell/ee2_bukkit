/*     */ package ee;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.server.Container;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.ICrafting;
/*     */ import net.minecraft.server.IInventory;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.PlayerInventory;
/*     */ import net.minecraft.server.Slot;
/*     */ 
/*     */ public class ContainerCollector3 extends Container
/*     */ {
/*     */   private TileCollector3 collector;
/*  14 */   private int sunStatus = 0;
/*  15 */   private int sunTimeScaled = 0;
/*  16 */   private int currentFuelProgress = 0;
/*     */   private boolean isUsingPower;
/*  18 */   private int kleinProgressScaled = 0;
/*  19 */   private int kleinPoints = 0;
/*  20 */   private int sunTime = 0;
/*     */ 
/*     */   public ContainerCollector3(PlayerInventory var1, TileCollector3 var2)
/*     */   {
/*  24 */     this.collector = var2;
/*  25 */     setPlayer(var1.player);
/*  26 */     a(new Slot(var2, 0, 158, 58));
/*     */ 
/*  30 */     for (int var3 = 0; var3 <= 3; var3++)
/*     */     {
/*  32 */       for (int var4 = 0; var4 <= 3; var4++)
/*     */       {
/*  34 */         a(new Slot(var2, var3 * 4 + var4 + 1, 18 + var3 * 18, 8 + var4 * 18));
/*     */       }
/*     */     }
/*     */ 
/*  38 */     a(new Slot(var2, 17, 158, 13));
/*  39 */     a(new Slot(var2, 18, 187, 36));
/*     */ 
/*  41 */     for (var3 = 0; var3 < 3; var3++)
/*     */     {
/*  43 */       for (int var4 = 0; var4 < 9; var4++)
/*     */       {
/*  45 */         a(new Slot(var1, var4 + var3 * 9 + 9, 30 + var4 * 18, 84 + var3 * 18));
/*     */       }
/*     */     }
/*     */ 
/*  49 */     for (var3 = 0; var3 < 9; var3++)
/*     */     {
/*  51 */       a(new Slot(var1, var3, 30 + var3 * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */   public IInventory getInventory()
/*     */   {
/*  57 */     return this.collector;
/*     */   }
/*     */ 
/*     */   public boolean b(EntityHuman var1)
/*     */   {
/*  62 */     return this.collector.a(var1);
/*     */   }
/*     */ 
/*     */   public void a()
/*     */   {
/*  70 */     super.a();
/*     */ 
/*  72 */     for (int var1 = 0; var1 < this.listeners.size(); var1++)
/*     */     {
/*  74 */       ICrafting var2 = (ICrafting)this.listeners.get(var1);
/*     */ 
/*  76 */       if (this.sunTimeScaled != this.collector.sunTimeScaled)
/*     */       {
/*  78 */         var2.setContainerData(this, 0, this.collector.sunTimeScaled);
/*     */       }
/*     */ 
/*  81 */       if (this.sunStatus != this.collector.currentSunStatus)
/*     */       {
/*  83 */         var2.setContainerData(this, 1, this.collector.currentSunStatus);
/*     */       }
/*     */ 
/*  86 */       if (this.currentFuelProgress != this.collector.currentFuelProgress)
/*     */       {
/*  88 */         var2.setContainerData(this, 2, this.collector.currentFuelProgress);
/*     */       }
/*     */ 
/*  91 */       if (this.isUsingPower != this.collector.isUsingPower)
/*     */       {
/*  93 */         var2.setContainerData(this, 3, this.collector.isUsingPower ? 1 : 0);
/*     */       }
/*     */ 
/*  96 */       if (this.isUsingPower != this.collector.isUsingPower)
/*     */       {
/*  98 */         var2.setContainerData(this, 4, this.collector.isUsingPower ? 1 : 0);
/*     */       }
/*     */ 
/* 101 */       if (this.kleinProgressScaled != this.collector.kleinProgressScaled)
/*     */       {
/* 103 */         var2.setContainerData(this, 5, this.collector.kleinProgressScaled);
/*     */       }
/*     */ 
/* 106 */       if (this.kleinPoints != this.collector.kleinPoints)
/*     */       {
/* 108 */         var2.setContainerData(this, 6, this.collector.kleinPoints & 0xFFFF);
/*     */       }
/*     */ 
/* 111 */       if (this.kleinPoints != this.collector.kleinPoints)
/*     */       {
/* 113 */         var2.setContainerData(this, 7, this.collector.kleinPoints >>> 16);
/*     */       }
/*     */ 
/* 116 */       if (this.sunTime != this.collector.collectorSunTime)
/*     */       {
/* 118 */         var2.setContainerData(this, 8, this.collector.collectorSunTime & 0xFFFF);
/*     */       }
/*     */ 
/* 121 */       if (this.sunTime != this.collector.collectorSunTime)
/*     */       {
/* 123 */         var2.setContainerData(this, 9, this.collector.collectorSunTime >>> 16);
/*     */       }
/*     */     }
/*     */ 
/* 127 */     this.sunTime = this.collector.collectorSunTime;
/* 128 */     this.kleinPoints = this.collector.kleinPoints;
/* 129 */     this.isUsingPower = this.collector.isUsingPower;
/* 130 */     this.sunTimeScaled = this.collector.sunTimeScaled;
/* 131 */     this.sunStatus = this.collector.currentSunStatus;
/* 132 */     this.currentFuelProgress = this.collector.currentFuelProgress;
/* 133 */     this.kleinProgressScaled = this.collector.kleinProgressScaled;
/*     */   }
/*     */ 
/*     */   public ItemStack a(int var1)
/*     */   {
/* 141 */     ItemStack var2 = null;
/* 142 */     Slot var3 = (Slot)this.e.get(var1);
/*     */ 
/* 144 */     if ((var3 != null) && (var3.c()))
/*     */     {
/* 146 */       ItemStack var4 = var3.getItem();
/* 147 */       var2 = var4.cloneItemStack();
/*     */ 
/* 149 */       if (var1 <= 18)
/*     */       {
/* 151 */         if (!a(var4, 19, 54, true))
/*     */         {
/* 153 */           if (var4.count == 0)
/*     */           {
/* 155 */             var3.set(null);
/*     */           }
/*     */ 
/* 158 */           return null;
/*     */         }
/*     */       }
/* 161 */       else if ((var1 >= 19) && (var1 < 46))
/*     */       {
/* 163 */         if (EEMaps.isFuel(var4))
/*     */         {
/* 165 */           if (!a(var4, 0, 16, true))
/*     */           {
/* 167 */             if (var4.count == 0)
/*     */             {
/* 169 */               var3.set(null);
/*     */             }
/*     */ 
/* 172 */             return null;
/*     */           }
/*     */         }
/* 175 */         else if (!a(var4, 46, 54, false))
/*     */         {
/* 177 */           if (var4.count == 0)
/*     */           {
/* 179 */             var3.set(null);
/*     */           }
/*     */ 
/* 182 */           return null;
/*     */         }
/*     */       }
/* 185 */       else if ((var1 >= 46) && (var1 <= 54))
/*     */       {
/* 187 */         if ((EEMaps.isFuel(var4)) && (!a(var4, 0, 16, true)))
/*     */         {
/* 189 */           if (var4.count == 0)
/*     */           {
/* 191 */             var3.set(null);
/*     */           }
/*     */ 
/* 194 */           return null;
/*     */         }
/*     */       }
/* 197 */       else if (!a(var4, 19, 54, false))
/*     */       {
/* 199 */         if (var4.count == 0)
/*     */         {
/* 201 */           var3.set(null);
/*     */         }
/*     */ 
/* 204 */         return null;
/*     */       }
/*     */ 
/* 207 */       if (var4.count == 0)
/*     */       {
/* 209 */         var3.set(null);
/*     */       }
/*     */       else
/*     */       {
/* 213 */         var3.d();
/*     */       }
/*     */ 
/* 216 */       if (var4.count == var2.count)
/*     */       {
/* 218 */         return null;
/*     */       }
/*     */ 
/* 221 */       var3.c(var4);
/*     */     }
/*     */ 
/* 224 */     return var2;
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ContainerCollector3
 * JD-Core Version:    0.6.2
 */