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
/*     */ public class ContainerRelay3 extends Container
/*     */ {
/*  13 */   private int kleinDrain = 0;
/*  14 */   private int kleinCharge = 0;
/*  15 */   private int relayEnergy = 0;
/*  16 */   private int kleinDrainPoints = 0;
/*  17 */   private int kleinChargePoints = 0;
/*  18 */   private int relayEnergyPoints = 0;
/*     */   private TileRelay3 array;
/*  20 */   private int cookTime = 0;
/*  21 */   private int burnTime = 0;
/*     */   private int itemBurnTime;
/*     */ 
/*     */   public ContainerRelay3(PlayerInventory var1, TileRelay3 var2)
/*     */   {
/*  26 */     this.array = var2;
/*  27 */     setPlayer(var1.player);
/*  28 */     a(new Slot(var2, 0, 104, 58));
/*     */ 
/*  32 */     for (int var3 = 0; var3 <= 3; var3++)
/*     */     {
/*  34 */       for (int var4 = 0; var4 <= 4; var4++)
/*     */       {
/*  36 */         a(new Slot(var2, var3 * 5 + var4 + 1, 28 + var3 * 18, 18 + var4 * 18));
/*     */       }
/*     */     }
/*     */ 
/*  40 */     a(new Slot(var2, 21, 164, 58));
/*     */ 
/*  42 */     for (var3 = 0; var3 < 3; var3++)
/*     */     {
/*  44 */       for (int var4 = 0; var4 < 9; var4++)
/*     */       {
/*  46 */         a(new Slot(var1, var4 + var3 * 9 + 9, 26 + var4 * 18, 113 + var3 * 18));
/*     */       }
/*     */     }
/*     */ 
/*  50 */     for (var3 = 0; var3 < 9; var3++)
/*     */     {
/*  52 */       a(new Slot(var1, var3, 26 + var3 * 18, 171));
/*     */     }
/*     */   }
/*     */ 
/*     */   public IInventory getInventory()
/*     */   {
/*  58 */     return this.array;
/*     */   }
/*     */ 
/*     */   public void a()
/*     */   {
/*  66 */     super.a();
/*     */ 
/*  68 */     for (int var1 = 0; var1 < this.listeners.size(); var1++)
/*     */     {
/*  70 */       ICrafting var2 = (ICrafting)this.listeners.get(var1);
/*     */ 
/*  72 */       if (this.cookTime != this.array.cookProgressScaled)
/*     */       {
/*  74 */         var2.setContainerData(this, 0, this.array.cookProgressScaled);
/*     */       }
/*     */ 
/*  77 */       if (this.burnTime != this.array.burnTimeRemainingScaled)
/*     */       {
/*  79 */         var2.setContainerData(this, 1, this.array.burnTimeRemainingScaled);
/*     */       }
/*     */ 
/*  82 */       if (this.kleinDrain != this.array.kleinDrainingScaled)
/*     */       {
/*  84 */         var2.setContainerData(this, 2, this.array.kleinDrainingScaled);
/*     */       }
/*     */ 
/*  87 */       if (this.kleinCharge != this.array.kleinChargingScaled)
/*     */       {
/*  89 */         var2.setContainerData(this, 3, this.array.kleinChargingScaled);
/*     */       }
/*     */ 
/*  92 */       if (this.relayEnergy != this.array.relayEnergyScaled)
/*     */       {
/*  94 */         var2.setContainerData(this, 4, this.array.relayEnergyScaled);
/*     */       }
/*     */ 
/*  97 */       if (this.kleinChargePoints != this.array.kleinChargePoints)
/*     */       {
/*  99 */         var2.setContainerData(this, 5, this.array.kleinChargePoints & 0xFFFF);
/*     */       }
/*     */ 
/* 102 */       if (this.kleinChargePoints != this.array.kleinChargePoints)
/*     */       {
/* 104 */         var2.setContainerData(this, 6, this.array.kleinChargePoints >>> 16);
/*     */       }
/*     */ 
/* 107 */       if (this.kleinDrainPoints != this.array.kleinDrainPoints)
/*     */       {
/* 109 */         var2.setContainerData(this, 7, this.array.kleinDrainPoints & 0xFFFF);
/*     */       }
/*     */ 
/* 112 */       if (this.kleinDrainPoints != this.array.kleinDrainPoints)
/*     */       {
/* 114 */         var2.setContainerData(this, 8, this.array.kleinDrainPoints >>> 16);
/*     */       }
/*     */ 
/* 117 */       if (this.relayEnergyPoints != this.array.scaledEnergy)
/*     */       {
/* 119 */         var2.setContainerData(this, 9, this.array.scaledEnergy & 0xFFFF);
/*     */       }
/*     */ 
/* 122 */       if (this.relayEnergyPoints != this.array.scaledEnergy)
/*     */       {
/* 124 */         var2.setContainerData(this, 10, this.array.scaledEnergy >>> 16);
/*     */       }
/*     */     }
/*     */ 
/* 128 */     this.cookTime = this.array.cookProgressScaled;
/* 129 */     this.burnTime = this.array.burnTimeRemainingScaled;
/* 130 */     this.kleinDrain = this.array.kleinDrainingScaled;
/* 131 */     this.kleinCharge = this.array.kleinChargingScaled;
/* 132 */     this.relayEnergy = this.array.relayEnergyScaled;
/* 133 */     this.kleinChargePoints = this.array.kleinChargePoints;
/* 134 */     this.kleinDrainPoints = this.array.kleinDrainPoints;
/* 135 */     this.relayEnergyPoints = this.array.scaledEnergy;
/*     */   }
/*     */ 
/*     */   public void updateProgressBar(int var1, int var2)
/*     */   {
/* 140 */     if (var1 == 0)
/*     */     {
/* 142 */       this.array.arrayCounter = var2;
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean b(EntityHuman var1)
/*     */   {
/* 148 */     return this.array.a(var1);
/*     */   }
/*     */ 
/*     */   public ItemStack a(int var1)
/*     */   {
/* 156 */     ItemStack var2 = null;
/* 157 */     Slot var3 = (Slot)this.e.get(var1);
/*     */ 
/* 159 */     if ((var3 != null) && (var3.c()))
/*     */     {
/* 161 */       ItemStack var4 = var3.getItem();
/* 162 */       var2 = var4.cloneItemStack();
/*     */ 
/* 164 */       if (var1 == 21)
/*     */       {
/* 166 */         if (!a(var4, 22, 57, true))
/*     */         {
/* 168 */           if (var4.count == 0)
/*     */           {
/* 170 */             var3.set(null);
/*     */           }
/*     */ 
/* 173 */           return null;
/*     */         }
/*     */       }
/* 176 */       else if ((var1 >= 22) && (var1 <= 48))
/*     */       {
/* 178 */         if (EEMaps.getEMC(var4.id) > 0)
/*     */         {
/* 180 */           if (!a(var4, 0, 21, true))
/*     */           {
/* 182 */             if (var4.count == 0)
/*     */             {
/* 184 */               var3.set(null);
/*     */             }
/*     */ 
/* 187 */             return null;
/*     */           }
/*     */         }
/* 190 */         else if (!a(var4, 49, 57, false))
/*     */         {
/* 192 */           if (var4.count == 0)
/*     */           {
/* 194 */             var3.set(null);
/*     */           }
/*     */ 
/* 197 */           return null;
/*     */         }
/*     */       }
/* 200 */       else if ((var1 >= 49) && (var1 <= 57))
/*     */       {
/* 202 */         if (EEMaps.getEMC(var4.id) > 0)
/*     */         {
/* 204 */           if (!a(var4, 0, 21, true))
/*     */           {
/* 206 */             if (var4.count == 0)
/*     */             {
/* 208 */               var3.set(null);
/*     */             }
/*     */ 
/* 211 */             return null;
/*     */           }
/*     */         }
/* 214 */         else if (!a(var4, 22, 48, false))
/*     */         {
/* 216 */           if (var4.count == 0)
/*     */           {
/* 218 */             var3.set(null);
/*     */           }
/*     */ 
/* 221 */           return null;
/*     */         }
/*     */       }
/* 224 */       else if (!a(var4, 22, 57, false))
/*     */       {
/* 226 */         if (var4.count == 0)
/*     */         {
/* 228 */           var3.set(null);
/*     */         }
/*     */ 
/* 231 */         return null;
/*     */       }
/*     */ 
/* 234 */       if (var4.count == 0)
/*     */       {
/* 236 */         var3.set(null);
/*     */       }
/*     */       else
/*     */       {
/* 240 */         var3.d();
/*     */       }
/*     */ 
/* 243 */       if (var4.count == var2.count)
/*     */       {
/* 245 */         return null;
/*     */       }
/*     */ 
/* 248 */       var3.c(var4);
/*     */     }
/*     */ 
/* 251 */     return var2;
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ContainerRelay3
 * JD-Core Version:    0.6.2
 */