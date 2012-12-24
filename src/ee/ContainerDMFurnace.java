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
/*     */ public class ContainerDMFurnace extends Container
/*     */ {
/*     */   private TileDMFurnace furnace;
/*  14 */   private int cookTime = 0;
/*  15 */   private int burnTime = 0;
/*  16 */   private int itemBurnTime = 0;
/*     */   private boolean initialized;
/*     */ 
/*     */   public ContainerDMFurnace(IInventory var1, TileDMFurnace var2)
/*     */   {
/*  21 */     this.furnace = var2;
/*  22 */     setPlayer(((PlayerInventory)var1).player);
/*  23 */     a(new Slot(var2, 0, 49, 53));
/*  24 */     a(new Slot(var2, 1, 49, 17));
/*     */ 
/*  28 */     for (int var3 = 0; var3 <= 1; var3++)
/*     */     {
/*  30 */       for (int var4 = 0; var4 <= 3; var4++)
/*     */       {
/*  32 */         a(new Slot(var2, var3 * 4 + var4 + 2, 13 + var3 * 18, 8 + var4 * 18));
/*     */       }
/*     */     }
/*     */ 
/*  36 */     a(new Slot(var2, 10, 109, 35));
/*     */ 
/*  38 */     for (int var3 = 0; var3 <= 1; var3++)
/*     */     {
/*  40 */       for (int var4 = 0; var4 <= 3; var4++)
/*     */       {
/*  42 */         a(new Slot(var2, var3 * 4 + var4 + 11, 131 + var3 * 18, 8 + var4 * 18));
/*     */       }
/*     */     }
/*     */ 
/*  46 */     for (int var3 = 0; var3 < 3; var3++)
/*     */     {
/*  48 */       for (int var4 = 0; var4 < 9; var4++)
/*     */       {
/*  50 */         a(new Slot(var1, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
/*     */       }
/*     */     }
/*     */ 
/*  54 */     for (int var3 = 0; var3 < 9; var3++)
/*     */     {
/*  56 */       a(new Slot(var1, var3, 8 + var3 * 18, 142));
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
/*  70 */     super.a();
/*     */ 
/*  72 */     for (int var1 = 0; var1 < this.listeners.size(); var1++)
/*     */     {
/*  74 */       ICrafting var2 = (ICrafting)this.listeners.get(var1);
/*     */ 
/*  76 */       if ((this.cookTime != this.furnace.furnaceCookTime) || (!this.initialized))
/*     */       {
/*  78 */         var2.setContainerData(this, 0, this.furnace.furnaceCookTime);
/*     */       }
/*     */ 
/*  81 */       if ((this.burnTime != this.furnace.furnaceBurnTime) || (!this.initialized))
/*     */       {
/*  83 */         var2.setContainerData(this, 1, this.furnace.furnaceBurnTime);
/*     */       }
/*     */ 
/*  86 */       if ((this.itemBurnTime != this.furnace.currentItemBurnTime) || (!this.initialized))
/*     */       {
/*  88 */         var2.setContainerData(this, 2, this.furnace.currentItemBurnTime);
/*     */       }
/*     */     }
/*     */ 
/*  92 */     this.cookTime = this.furnace.furnaceCookTime;
/*  93 */     this.burnTime = this.furnace.furnaceBurnTime;
/*  94 */     this.itemBurnTime = this.furnace.currentItemBurnTime;
/*  95 */     this.initialized = true;
/*     */   }
/*     */ 
/*     */   public void updateProgressBar(int var1, int var2)
/*     */   {
/* 100 */     if (var1 == 0)
/*     */     {
/* 102 */       this.furnace.furnaceCookTime = var2;
/*     */     }
/*     */ 
/* 105 */     if (var1 == 1)
/*     */     {
/* 107 */       this.furnace.furnaceBurnTime = var2;
/*     */     }
/*     */ 
/* 110 */     if (var1 == 2)
/*     */     {
/* 112 */       this.furnace.currentItemBurnTime = var2;
/*     */     }
/*     */   }
/*     */ 
/*     */   public ItemStack a(int var1)
/*     */   {
/* 121 */     ItemStack var2 = null;
/* 122 */     Slot var3 = (Slot)this.e.get(var1);
/*     */ 
/* 124 */     if ((var3 != null) && (var3.c()))
/*     */     {
/* 126 */       ItemStack var4 = var3.getItem();
/* 127 */       var2 = var4.cloneItemStack();
/*     */ 
/* 129 */       if ((var1 >= 10) && (var1 <= 18))
/*     */       {
/* 131 */         if (!a(var4, 19, 54, true))
/*     */         {
/* 133 */           if (var4.count == 0)
/*     */           {
/* 135 */             var3.set(null);
/*     */           }
/*     */ 
/* 138 */           return null;
/*     */         }
/*     */       }
/* 141 */       else if ((var1 >= 19) && (var1 < 45))
/*     */       {
/* 143 */         if (this.furnace.getItemBurnTime(var4) > 0)
/*     */         {
/* 145 */           if (!a(var4, 0, 0, true))
/*     */           {
/* 147 */             if (var4.count == 0)
/*     */             {
/* 149 */               var3.set(null);
/*     */             }
/*     */ 
/* 152 */             return null;
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 157 */           if (!a(var4, 1, 9, false))
/*     */           {
/* 159 */             if (var4.count == 0)
/*     */             {
/* 161 */               var3.set(null);
/*     */             }
/*     */ 
/* 164 */             return null;
/*     */           }
/*     */ 
/* 167 */           if (!a(var4, 45, 54, false))
/*     */           {
/* 169 */             if (var4.count == 0)
/*     */             {
/* 171 */               var3.set(null);
/*     */             }
/*     */ 
/* 174 */             return null;
/*     */           }
/*     */         }
/*     */       }
/* 178 */       else if ((var1 >= 45) && (var1 < 54))
/*     */       {
/* 180 */         if (this.furnace.getItemBurnTime(var4) > 0)
/*     */         {
/* 182 */           if (!a(var4, 0, 0, true))
/*     */           {
/* 184 */             if (var4.count == 0)
/*     */             {
/* 186 */               var3.set(null);
/*     */             }
/*     */ 
/* 189 */             return null;
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 194 */           if (!a(var4, 1, 9, false))
/*     */           {
/* 196 */             if (var4.count == 0)
/*     */             {
/* 198 */               var3.set(null);
/*     */             }
/*     */ 
/* 201 */             return null;
/*     */           }
/*     */ 
/* 204 */           if (!a(var4, 19, 45, false))
/*     */           {
/* 206 */             if (var4.count == 0)
/*     */             {
/* 208 */               var3.set(null);
/*     */             }
/*     */ 
/* 211 */             return null;
/*     */           }
/*     */         }
/*     */       }
/* 215 */       else if (!a(var4, 19, 54, false))
/*     */       {
/* 217 */         if (var4.count == 0)
/*     */         {
/* 219 */           var3.set(null);
/*     */         }
/*     */ 
/* 222 */         return null;
/*     */       }
/*     */ 
/* 225 */       if (var4.count == 0)
/*     */       {
/* 227 */         var3.set(null);
/*     */       }
/*     */       else
/*     */       {
/* 231 */         var3.d();
/*     */       }
/*     */ 
/* 234 */       if (var4.count == var2.count)
/*     */       {
/* 236 */         return null;
/*     */       }
/*     */ 
/* 239 */       var3.c(var4);
/*     */     }
/*     */ 
/* 242 */     return var2;
/*     */   }
/*     */ 
/*     */   public boolean b(EntityHuman var1)
/*     */   {
/* 247 */     return this.furnace.a(var1);
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ContainerDMFurnace
 * JD-Core Version:    0.6.2
 */