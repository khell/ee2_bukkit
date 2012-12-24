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
/*     */ public class ContainerRMFurnace extends Container
/*     */ {
/*     */   private TileRMFurnace furnace;
/*  14 */   private int cookTime = 0;
/*  15 */   private int burnTime = 0;
/*  16 */   private int itemBurnTime = 0;
/*     */   private boolean initialized;
/*     */ 
/*     */   public ContainerRMFurnace(IInventory var1, TileRMFurnace var2)
/*     */   {
/*  21 */     this.furnace = var2;
/*  22 */     setPlayer(((PlayerInventory)var1).player);
/*  23 */     a(new Slot(var2, 0, 65, 53));
/*  24 */     a(new Slot(var2, 1, 65, 17));
/*     */ 
/*  28 */     for (int var3 = 0; var3 <= 2; var3++)
/*     */     {
/*  30 */       for (int var4 = 0; var4 <= 3; var4++)
/*     */       {
/*  32 */         a(new Slot(var2, var3 * 4 + var4 + 2, 11 + var3 * 18, 8 + var4 * 18));
/*     */       }
/*     */     }
/*     */ 
/*  36 */     a(new Slot(var2, 14, 125, 35));
/*     */ 
/*  38 */     for (var3 = 0; var3 <= 2; var3++)
/*     */     {
/*  40 */       for (int var4 = 0; var4 <= 3; var4++)
/*     */       {
/*  42 */         a(new Slot(var2, var3 * 4 + var4 + 15, 147 + var3 * 18, 8 + var4 * 18));
/*     */       }
/*     */     }
/*     */ 
/*  46 */     for (var3 = 0; var3 < 3; var3++)
/*     */     {
/*  48 */       for (int var4 = 0; var4 < 9; var4++)
/*     */       {
/*  50 */         a(new Slot(var1, var4 + var3 * 9 + 9, 24 + var4 * 18, 84 + var3 * 18));
/*     */       }
/*     */     }
/*     */ 
/*  54 */     for (var3 = 0; var3 < 9; var3++)
/*     */     {
/*  56 */       a(new Slot(var1, var3, 24 + var3 * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */   public IInventory getInventory()
/*     */   {
/*  62 */     return this.furnace;
/*     */   }
/*     */ 
/*     */   public void a()
/*     */   {
/*  69 */     super.a();
/*     */ 
/*  71 */     for (int var1 = 0; var1 < this.listeners.size(); var1++)
/*     */     {
/*  73 */       ICrafting var2 = (ICrafting)this.listeners.get(var1);
/*     */ 
/*  75 */       if ((this.cookTime != this.furnace.furnaceCookTime) || (!this.initialized))
/*     */       {
/*  77 */         var2.setContainerData(this, 0, this.furnace.furnaceCookTime);
/*     */       }
/*     */ 
/*  80 */       if ((this.burnTime != this.furnace.furnaceBurnTime) || (!this.initialized))
/*     */       {
/*  82 */         var2.setContainerData(this, 1, this.furnace.furnaceBurnTime);
/*     */       }
/*     */ 
/*  85 */       if ((this.itemBurnTime != this.furnace.currentItemBurnTime) || (!this.initialized))
/*     */       {
/*  87 */         var2.setContainerData(this, 2, this.furnace.currentItemBurnTime);
/*     */       }
/*     */     }
/*     */ 
/*  91 */     this.cookTime = this.furnace.furnaceCookTime;
/*  92 */     this.burnTime = this.furnace.furnaceBurnTime;
/*  93 */     this.itemBurnTime = this.furnace.currentItemBurnTime;
/*  94 */     this.initialized = true;
/*     */   }
/*     */ 
/*     */   public void updateProgressBar(int var1, int var2)
/*     */   {
/*  99 */     if (var1 == 0)
/*     */     {
/* 101 */       this.furnace.furnaceCookTime = var2;
/*     */     }
/*     */ 
/* 104 */     if (var1 == 1)
/*     */     {
/* 106 */       this.furnace.furnaceBurnTime = var2;
/*     */     }
/*     */ 
/* 109 */     if (var1 == 2)
/*     */     {
/* 111 */       this.furnace.currentItemBurnTime = var2;
/*     */     }
/*     */   }
/*     */ 
/*     */   public ItemStack a(int var1)
/*     */   {
/* 120 */     ItemStack var2 = null;
/* 121 */     Slot var3 = (Slot)this.e.get(var1);
/*     */ 
/* 123 */     if ((var3 != null) && (var3.c()))
/*     */     {
/* 125 */       ItemStack var4 = var3.getItem();
/* 126 */       var2 = var4.cloneItemStack();
/*     */ 
/* 128 */       if ((var1 >= 14) && (var1 <= 26))
/*     */       {
/* 130 */         if (!a(var4, 27, 62, true))
/*     */         {
/* 132 */           if (var4.count == 0)
/*     */           {
/* 134 */             var3.set(null);
/*     */           }
/*     */ 
/* 137 */           return null;
/*     */         }
/*     */       }
/* 140 */       else if ((var1 >= 27) && (var1 < 53))
/*     */       {
/* 142 */         if (this.furnace.getItemBurnTime(var4, true) > 0)
/*     */         {
/* 144 */           if (!a(var4, 0, 0, true))
/*     */           {
/* 146 */             if (var4.count == 0)
/*     */             {
/* 148 */               var3.set(null);
/*     */             }
/*     */ 
/* 151 */             return null;
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 156 */           if (!a(var4, 1, 13, false))
/*     */           {
/* 158 */             if (var4.count == 0)
/*     */             {
/* 160 */               var3.set(null);
/*     */             }
/*     */ 
/* 163 */             return null;
/*     */           }
/*     */ 
/* 166 */           if (!a(var4, 53, 62, false))
/*     */           {
/* 168 */             if (var4.count == 0)
/*     */             {
/* 170 */               var3.set(null);
/*     */             }
/*     */ 
/* 173 */             return null;
/*     */           }
/*     */         }
/*     */       }
/* 177 */       else if ((var1 >= 54) && (var1 < 63))
/*     */       {
/* 179 */         if (this.furnace.getItemBurnTime(var4, true) > 0)
/*     */         {
/* 181 */           if (!a(var4, 0, 0, true))
/*     */           {
/* 183 */             if (var4.count == 0)
/*     */             {
/* 185 */               var3.set(null);
/*     */             }
/*     */ 
/* 188 */             return null;
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 193 */           if (!a(var4, 1, 13, false))
/*     */           {
/* 195 */             if (var4.count == 0)
/*     */             {
/* 197 */               var3.set(null);
/*     */             }
/*     */ 
/* 200 */             return null;
/*     */           }
/*     */ 
/* 203 */           if (!a(var4, 27, 53, false))
/*     */           {
/* 205 */             if (var4.count == 0)
/*     */             {
/* 207 */               var3.set(null);
/*     */             }
/*     */ 
/* 210 */             return null;
/*     */           }
/*     */         }
/*     */       }
/* 214 */       else if (!a(var4, 27, 62, false))
/*     */       {
/* 216 */         if (var4.count == 0)
/*     */         {
/* 218 */           var3.set(null);
/*     */         }
/*     */ 
/* 221 */         return null;
/*     */       }
/*     */ 
/* 224 */       if (var4.count == 0)
/*     */       {
/* 226 */         var3.set(null);
/*     */       }
/*     */       else
/*     */       {
/* 230 */         var3.d();
/*     */       }
/*     */ 
/* 233 */       if (var4.count == var2.count)
/*     */       {
/* 235 */         return null;
/*     */       }
/*     */ 
/* 238 */       var3.c(var4);
/*     */     }
/*     */ 
/* 241 */     return var2;
/*     */   }
/*     */ 
/*     */   public void addSlotListener(ICrafting icrafting) {
/* 245 */     super.addSlotListener(icrafting);
/* 246 */     icrafting.setContainerData(this, 0, this.furnace.furnaceCookTime);
/* 247 */     icrafting.setContainerData(this, 1, this.furnace.furnaceBurnTime);
/* 248 */     icrafting.setContainerData(this, 2, this.furnace.currentItemBurnTime);
/*     */   }
/*     */ 
/*     */   public boolean b(EntityHuman var1)
/*     */   {
/* 253 */     return this.furnace.a(var1);
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ContainerRMFurnace
 * JD-Core Version:    0.6.2
 */