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
/*     */ public class ContainerRelay extends Container
/*     */ {
/*  13 */   private int kleinDrain = 0;
/*  14 */   private int kleinCharge = 0;
/*  15 */   private int relayEnergy = 0;
/*  16 */   private int kleinDrainPoints = 0;
/*  17 */   private int kleinChargePoints = 0;
/*  18 */   private int relayEnergyPoints = 0;
/*     */   private TileRelay array;
/*  20 */   private int cookTime = 0;
/*  21 */   private int burnTime = 0;
/*     */   private int itemBurnTime;
/*     */ 
/*     */   public ContainerRelay(PlayerInventory var1, TileRelay var2)
/*     */   {
/*  26 */     this.array = var2;
/*  27 */     setPlayer(var1.player);
/*  28 */     a(new Slot(var2, 0, 67, 43));
/*     */ 
/*  32 */     for (int var3 = 0; var3 <= 1; var3++)
/*     */     {
/*  34 */       for (int var4 = 0; var4 <= 2; var4++)
/*     */       {
/*  36 */         a(new Slot(var2, var3 * 3 + var4 + 1, 27 + var3 * 18, 17 + var4 * 18));
/*     */       }
/*     */     }
/*     */ 
/*  40 */     a(new Slot(var2, 7, 127, 43));
/*     */ 
/*  42 */     for (var3 = 0; var3 < 3; var3++)
/*     */     {
/*  44 */       for (int var4 = 0; var4 < 9; var4++)
/*     */       {
/*  46 */         a(new Slot(var1, var4 + var3 * 9 + 9, 8 + var4 * 18, 95 + var3 * 18));
/*     */       }
/*     */     }
/*     */ 
/*  50 */     for (var3 = 0; var3 < 9; var3++)
/*     */     {
/*  52 */       a(new Slot(var1, var3, 8 + var3 * 18, 153));
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
/*  65 */     super.a();
/*     */ 
/*  67 */     for (int var1 = 0; var1 < this.listeners.size(); var1++)
/*     */     {
/*  69 */       ICrafting var2 = (ICrafting)this.listeners.get(var1);
/*     */ 
/*  71 */       if (this.cookTime != this.array.cookProgressScaled)
/*     */       {
/*  73 */         var2.setContainerData(this, 0, this.array.cookProgressScaled);
/*     */       }
/*     */ 
/*  76 */       if (this.burnTime != this.array.burnTimeRemainingScaled)
/*     */       {
/*  78 */         var2.setContainerData(this, 1, this.array.burnTimeRemainingScaled);
/*     */       }
/*     */ 
/*  81 */       if (this.kleinDrain != this.array.kleinDrainingScaled)
/*     */       {
/*  83 */         var2.setContainerData(this, 2, this.array.kleinDrainingScaled);
/*     */       }
/*     */ 
/*  86 */       if (this.kleinCharge != this.array.kleinChargingScaled)
/*     */       {
/*  88 */         var2.setContainerData(this, 3, this.array.kleinChargingScaled);
/*     */       }
/*     */ 
/*  91 */       if (this.relayEnergy != this.array.relayEnergyScaled)
/*     */       {
/*  93 */         var2.setContainerData(this, 4, this.array.relayEnergyScaled);
/*     */       }
/*     */ 
/*  96 */       if (this.kleinChargePoints != this.array.kleinChargePoints)
/*     */       {
/*  98 */         var2.setContainerData(this, 5, this.array.kleinChargePoints & 0xFFFF);
/*     */       }
/*     */ 
/* 101 */       if (this.kleinChargePoints != this.array.kleinChargePoints)
/*     */       {
/* 103 */         var2.setContainerData(this, 6, this.array.kleinChargePoints >>> 16);
/*     */       }
/*     */ 
/* 106 */       if (this.kleinDrainPoints != this.array.kleinDrainPoints)
/*     */       {
/* 108 */         var2.setContainerData(this, 7, this.array.kleinDrainPoints & 0xFFFF);
/*     */       }
/*     */ 
/* 111 */       if (this.kleinDrainPoints != this.array.kleinDrainPoints)
/*     */       {
/* 113 */         var2.setContainerData(this, 8, this.array.kleinDrainPoints >>> 16);
/*     */       }
/*     */ 
/* 116 */       if (this.relayEnergyPoints != this.array.scaledEnergy)
/*     */       {
/* 118 */         var2.setContainerData(this, 9, this.array.scaledEnergy & 0xFFFF);
/*     */       }
/*     */ 
/* 121 */       if (this.relayEnergyPoints != this.array.scaledEnergy)
/*     */       {
/* 123 */         var2.setContainerData(this, 10, this.array.scaledEnergy >>> 16);
/*     */       }
/*     */     }
/*     */ 
/* 127 */     this.cookTime = this.array.cookProgressScaled;
/* 128 */     this.burnTime = this.array.burnTimeRemainingScaled;
/* 129 */     this.kleinDrain = this.array.kleinDrainingScaled;
/* 130 */     this.kleinCharge = this.array.kleinChargingScaled;
/* 131 */     this.relayEnergy = this.array.relayEnergyScaled;
/* 132 */     this.kleinChargePoints = this.array.kleinChargePoints;
/* 133 */     this.kleinDrainPoints = this.array.kleinDrainPoints;
/* 134 */     this.relayEnergyPoints = this.array.scaledEnergy;
/*     */   }
/*     */ 
/*     */   public void updateProgressBar(int var1, int var2)
/*     */   {
/* 139 */     if (var1 == 0)
/*     */     {
/* 141 */       this.array.arrayCounter = var2;
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean b(EntityHuman var1)
/*     */   {
/* 147 */     return this.array.a(var1);
/*     */   }
/*     */ 
/*     */   public ItemStack a(int var1)
/*     */   {
/* 155 */     ItemStack var2 = null;
/* 156 */     Slot var3 = (Slot)this.e.get(var1);
/*     */ 
/* 158 */     if ((var3 != null) && (var3.c()))
/*     */     {
/* 160 */       ItemStack var4 = var3.getItem();
/* 161 */       var2 = var4.cloneItemStack();
/*     */ 
/* 163 */       if (var1 == 7)
/*     */       {
/* 165 */         if (!a(var4, 8, 43, true))
/*     */         {
/* 167 */           if (var4.count == 0)
/*     */           {
/* 169 */             var3.set(null);
/*     */           }
/*     */ 
/* 172 */           return null;
/*     */         }
/*     */       }
/* 175 */       else if ((var1 >= 8) && (var1 <= 34))
/*     */       {
/* 177 */         if (EEMaps.getEMC(var4.id) > 0)
/*     */         {
/* 179 */           if (!a(var4, 0, 7, true))
/*     */           {
/* 181 */             if (var4.count == 0)
/*     */             {
/* 183 */               var3.set(null);
/*     */             }
/*     */ 
/* 186 */             return null;
/*     */           }
/*     */         }
/* 189 */         else if (!a(var4, 35, 43, false))
/*     */         {
/* 191 */           if (var4.count == 0)
/*     */           {
/* 193 */             var3.set(null);
/*     */           }
/*     */ 
/* 196 */           return null;
/*     */         }
/*     */       }
/* 199 */       else if ((var1 >= 35) && (var1 <= 43))
/*     */       {
/* 201 */         if (EEMaps.getEMC(var4.id) > 0)
/*     */         {
/* 203 */           if (!a(var4, 0, 7, true))
/*     */           {
/* 205 */             if (var4.count == 0)
/*     */             {
/* 207 */               var3.set(null);
/*     */             }
/*     */ 
/* 210 */             return null;
/*     */           }
/*     */         }
/* 213 */         else if (!a(var4, 8, 34, false))
/*     */         {
/* 215 */           if (var4.count == 0)
/*     */           {
/* 217 */             var3.set(null);
/*     */           }
/*     */ 
/* 220 */           return null;
/*     */         }
/*     */       }
/* 223 */       else if (!a(var4, 8, 43, false))
/*     */       {
/* 225 */         if (var4.count == 0)
/*     */         {
/* 227 */           var3.set(null);
/*     */         }
/*     */ 
/* 230 */         return null;
/*     */       }
/*     */ 
/* 233 */       if (var4.count == 0)
/*     */       {
/* 235 */         var3.set(null);
/*     */       }
/*     */       else
/*     */       {
/* 239 */         var3.d();
/*     */       }
/*     */ 
/* 242 */       if (var4.count == var2.count)
/*     */       {
/* 244 */         return null;
/*     */       }
/*     */ 
/* 247 */       var3.c(var4);
/*     */     }
/*     */ 
/* 250 */     return var2;
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ContainerRelay
 * JD-Core Version:    0.6.2
 */