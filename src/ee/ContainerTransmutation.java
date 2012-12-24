/*     */ package ee;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.server.Container;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.ICrafting;
/*     */ import net.minecraft.server.IInventory;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.PlayerInventory;
/*     */ import net.minecraft.server.Slot;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class ContainerTransmutation extends Container
/*     */ {
/*     */   private EntityHuman player;
/*     */   private TransTabletData transGrid;
/*     */   private int latentEnergy;
/*     */   private int currentEnergy;
/*     */   private int learned;
/*     */   private int lock;
/*     */   private boolean initialized;
/*     */ 
/*     */   public ContainerTransmutation(PlayerInventory var1, EntityHuman var2, TransTabletData var3)
/*     */   {
/*  24 */     this.player = var2;
/*  25 */     setPlayer(var2);
/*  26 */     this.transGrid = var3;
/*  27 */     this.learned = var3.learned;
/*  28 */     this.lock = (var3.isMatterLocked() ? 1 : var3.isFuelLocked() ? 2 : 0);
/*  29 */     this.latentEnergy = var3.getLatentEnergy();
/*  30 */     this.currentEnergy = var3.getCurrentEnergy();
/*  31 */     a(new SlotTransmuteInput(var3, 0, 43, 29));
/*  32 */     a(new SlotTransmuteInput(var3, 1, 34, 47));
/*  33 */     a(new SlotTransmuteInput(var3, 2, 52, 47));
/*  34 */     a(new SlotTransmuteInput(var3, 3, 16, 56));
/*  35 */     a(new SlotTransmuteInput(var3, 4, 70, 56));
/*  36 */     a(new SlotTransmuteInput(var3, 5, 34, 65));
/*  37 */     a(new SlotTransmuteInput(var3, 6, 52, 65));
/*  38 */     a(new SlotTransmuteInput(var3, 7, 43, 83));
/*  39 */     a(new SlotTransmuteInput(var3, 8, 158, 56));
/*  40 */     a(new SlotConsume(var3, 9, 107, 103));
/*  41 */     a(new SlotTransmute(var3, 10, 158, 15));
/*  42 */     a(new SlotTransmute(var3, 11, 140, 19));
/*  43 */     a(new SlotTransmute(var3, 12, 176, 19));
/*  44 */     a(new SlotTransmute(var3, 13, 123, 36));
/*  45 */     a(new SlotTransmute(var3, 14, 158, 37));
/*  46 */     a(new SlotTransmute(var3, 15, 193, 36));
/*  47 */     a(new SlotTransmute(var3, 16, 116, 56));
/*  48 */     a(new SlotTransmute(var3, 17, 139, 56));
/*  49 */     a(new SlotTransmute(var3, 18, 177, 56));
/*  50 */     a(new SlotTransmute(var3, 19, 199, 56));
/*  51 */     a(new SlotTransmute(var3, 20, 123, 76));
/*  52 */     a(new SlotTransmute(var3, 21, 158, 75));
/*  53 */     a(new SlotTransmute(var3, 22, 193, 76));
/*  54 */     a(new SlotTransmute(var3, 23, 140, 93));
/*  55 */     a(new SlotTransmute(var3, 24, 176, 93));
/*  56 */     a(new SlotTransmute(var3, 25, 158, 97));
/*     */ 
/*  59 */     for (int var4 = 0; var4 < 3; var4++)
/*     */     {
/*  61 */       for (int var5 = 0; var5 < 9; var5++)
/*     */       {
/*  63 */         a(new Slot(this.player.inventory, var5 + var4 * 9 + 9, 35 + var5 * 18, 123 + var4 * 18));
/*     */       }
/*     */     }
/*     */ 
/*  67 */     for (int var4 = 0; var4 < 9; var4++)
/*     */     {
/*  69 */       a(new Slot(this.player.inventory, var4, 35 + var4 * 18, 181));
/*     */     }
/*     */ 
/*  72 */     a(this.transGrid);
/*  73 */     EEBase.watchTransGrid(this.player);
/*     */   }
/*     */ 
/*     */   public IInventory getInventory()
/*     */   {
/*  78 */     return this.transGrid;
/*     */   }
/*     */ 
/*     */   public void setItem(int var1, ItemStack var2)
/*     */   {
/*  85 */     super.setItem(var1, var2);
/*     */ 
/*  87 */     if (var1 < 26)
/*     */     {
/*  89 */       if (var2 == null)
/*     */       {
/*  91 */         this.transGrid.items[var1] = null;
/*     */       }
/*     */       else
/*     */       {
/*  95 */         this.transGrid.items[var1] = var2.cloneItemStack();
/*     */       }
/*     */     }
/*     */ 
/*  99 */     a(this.transGrid);
/*     */   }
/*     */ 
/*     */   public void a(IInventory var1)
/*     */   {
/* 107 */     a();
/*     */ 
/* 109 */     if (!EEProxy.isClient(EEProxy.theWorld))
/*     */     {
/* 111 */       this.transGrid.update();
/* 112 */       this.transGrid.displayResults(this.transGrid.latentEnergy + this.transGrid.currentEnergy);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void a()
/*     */   {
/* 121 */     super.a();
/*     */ 
/* 123 */     for (int var1 = 0; var1 < this.listeners.size(); var1++)
/*     */     {
/* 125 */       ICrafting var2 = (ICrafting)this.listeners.get(var1);
/*     */ 
/* 127 */       if ((this.latentEnergy != this.transGrid.latentEnergy) || (!this.initialized))
/*     */       {
/* 129 */         var2.setContainerData(this, 0, this.transGrid.latentEnergy & 0xFFFF);
/*     */       }
/*     */ 
/* 132 */       if ((this.latentEnergy != this.transGrid.latentEnergy) || (!this.initialized))
/*     */       {
/* 134 */         var2.setContainerData(this, 1, this.transGrid.latentEnergy >>> 16);
/*     */       }
/*     */ 
/* 137 */       if ((this.currentEnergy != this.transGrid.currentEnergy) || (!this.initialized))
/*     */       {
/* 139 */         var2.setContainerData(this, 2, this.transGrid.currentEnergy & 0xFFFF);
/*     */       }
/*     */ 
/* 142 */       if ((this.currentEnergy != this.transGrid.currentEnergy) || (!this.initialized))
/*     */       {
/* 144 */         var2.setContainerData(this, 3, this.transGrid.currentEnergy >>> 16);
/*     */       }
/*     */ 
/* 147 */       if ((this.learned != this.transGrid.learned) || (!this.initialized))
/*     */       {
/* 149 */         var2.setContainerData(this, 4, this.transGrid.learned);
/*     */       }
/*     */ 
/* 152 */       if (this.lock == (this.transGrid.isMatterLocked() ? 1 : this.transGrid.isFuelLocked() ? 2 : 0)) { if (this.initialized);
/*     */       } else {
/* 154 */         var2.setContainerData(this, 5, this.transGrid.isMatterLocked() ? 1 : this.transGrid.isFuelLocked() ? 2 : 0);
/*     */       }
/*     */     }
/*     */ 
/* 158 */     this.learned = this.transGrid.learned;
/* 159 */     this.lock = (this.transGrid.isMatterLocked() ? 1 : this.transGrid.isFuelLocked() ? 2 : 0);
/* 160 */     this.latentEnergy = this.transGrid.latentEnergy;
/* 161 */     this.currentEnergy = this.transGrid.currentEnergy;
/* 162 */     this.initialized = true;
/*     */   }
/*     */ 
/*     */   public void updateProgressBar(int var1, int var2)
/*     */   {
/* 167 */     if (var1 == 0)
/*     */     {
/* 169 */       this.transGrid.latentEnergy = (this.transGrid.latentEnergy & 0xFFFF0000 | var2);
/*     */     }
/*     */ 
/* 172 */     if (var1 == 1)
/*     */     {
/* 174 */       this.transGrid.latentEnergy = (this.transGrid.latentEnergy & 0xFFFF | var2 << 16);
/*     */     }
/*     */ 
/* 177 */     if (var1 == 2)
/*     */     {
/* 179 */       this.transGrid.currentEnergy = (this.transGrid.currentEnergy & 0xFFFF0000 | var2);
/*     */     }
/*     */ 
/* 182 */     if (var1 == 3)
/*     */     {
/* 184 */       this.transGrid.currentEnergy = (this.transGrid.currentEnergy & 0xFFFF | var2 << 16);
/*     */     }
/*     */ 
/* 187 */     if (var1 == 4)
/*     */     {
/* 189 */       this.transGrid.learned = var2;
/*     */     }
/*     */ 
/* 192 */     if (var1 == 5)
/*     */     {
/* 194 */       if (var2 == 0)
/*     */       {
/* 196 */         this.transGrid.unlock();
/*     */       }
/*     */ 
/* 199 */       if (var2 == 1)
/*     */       {
/* 201 */         this.transGrid.fuelUnlock();
/* 202 */         this.transGrid.matterLock();
/*     */       }
/*     */ 
/* 205 */       if (var2 == 2)
/*     */       {
/* 207 */         this.transGrid.matterUnlock();
/* 208 */         this.transGrid.fuelLock();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean b(EntityHuman var1)
/*     */   {
/* 215 */     return true;
/*     */   }
/*     */ 
/*     */   public void a(EntityHuman var1)
/*     */   {
/* 223 */     super.a(var1);
/* 224 */     EEBase.closeTransGrid(this.player);
/*     */ 
/* 226 */     if (!this.player.world.isStatic)
/*     */     {
/* 228 */       for (int var2 = 0; var2 < 25; var2++)
/*     */       {
/* 230 */         ItemStack var3 = this.transGrid.splitWithoutUpdate(var2);
/*     */ 
/* 232 */         if (var3 != null)
/*     */         {
/* 234 */           this.player.drop(var3);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public ItemStack a(int var1)
/*     */   {
/* 245 */     ItemStack var2 = null;
/* 246 */     Slot var3 = (Slot)this.e.get(var1);
/* 247 */     ItemStack var4 = null;
/*     */ 
/* 249 */     if ((var1 > 9) && (var1 < 26) && (var3 != null) && (var3.c()))
/*     */     {
/* 251 */       var4 = var3.getItem().cloneItemStack();
/*     */     }
/*     */ 
/* 254 */     if ((var3 != null) && (var3.c()))
/*     */     {
/* 256 */       ItemStack var5 = var3.getItem();
/* 257 */       var2 = var5.cloneItemStack();
/*     */ 
/* 259 */       if (var1 <= 8)
/*     */       {
/* 261 */         if (!a(var5, 26, 62, true))
/*     */         {
/* 263 */           var3.set(null);
/*     */         }
/*     */       }
/* 266 */       else if ((var1 > 9) && (var1 < 26))
/*     */       {
/* 268 */         if (!grabResult(var5, (Slot)this.e.get(var1), 26, 62, false))
/*     */         {
/* 270 */           var3.set(null);
/*     */         }
/*     */       }
/* 273 */       else if ((var1 >= 26) && (var1 < 62))
/*     */       {
/* 275 */         if (((EEMaps.getEMC(var5) > 0) || (EEBase.isKleinStar(var5.id))) && (!a(var5, 0, 8, false)))
/*     */         {
/* 277 */           if (var5.count == 0)
/*     */           {
/* 279 */             var3.set(null);
/*     */           }
/*     */ 
/* 282 */           return null;
/*     */         }
/*     */       }
/* 285 */       else if (!a(var5, 26, 62, false))
/*     */       {
/* 287 */         if (var5.count == 0)
/*     */         {
/* 289 */           var3.set(null);
/*     */         }
/*     */ 
/* 292 */         return null;
/*     */       }
/*     */ 
/* 295 */       if (var5.count == 0)
/*     */       {
/* 297 */         if ((var1 > 9) && (var1 < 26))
/*     */         {
/* 299 */           var5.count = 1;
/*     */         }
/*     */         else
/*     */         {
/* 303 */           var3.set(null);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 308 */         var3.d();
/*     */       }
/*     */ 
/* 311 */       if (var5.count == var2.count)
/*     */       {
/* 313 */         if ((var1 > 9) && (var1 < 26) && (var4 != null))
/*     */         {
/* 315 */           return var4;
/*     */         }
/*     */ 
/* 318 */         return null;
/*     */       }
/*     */ 
/* 321 */       if ((var1 > 9) && (var1 < 26) && (this.transGrid.latentEnergy + this.transGrid.currentEnergy < EEMaps.getEMC(var5)))
/*     */       {
/* 323 */         return null;
/*     */       }
/*     */ 
/* 326 */       var3.c(var5);
/*     */     }
/*     */ 
/* 329 */     if ((var4 != null) && (var1 > 9) && (var1 < 26))
/*     */     {
/* 331 */       var3.set(var4);
/*     */     }
/*     */ 
/* 334 */     return var2;
/*     */   }
/*     */ 
/*     */   protected boolean grabResult(ItemStack var1, Slot var2, int var3, int var4, boolean var5)
/*     */   {
/* 339 */     if (this.transGrid.latentEnergy + this.transGrid.currentEnergy < EEMaps.getEMC(var1))
/*     */     {
/* 341 */       return false;
/*     */     }
/*     */ 
/* 345 */     var2.c(var1);
/* 346 */     boolean var6 = false;
/* 347 */     int var7 = var3;
/*     */ 
/* 349 */     if (var5)
/*     */     {
/* 351 */       var7 = var4 - 1;
/*     */     }
/*     */ 
/* 354 */     if (var1.isStackable())
/*     */     {
/* 356 */       while ((var1.count > 0) && (((!var5) && (var7 < var4)) || ((var5) && (var7 >= var3))))
/*     */       {
/* 358 */         Slot var8 = (Slot)this.e.get(var7);
/* 359 */         ItemStack var9 = var8.getItem();
/*     */ 
/* 361 */         if ((var9 != null) && (var9.id == var1.id) && ((!var1.usesData()) || (var1.getData() == var9.getData())) && (ItemStack.equals(var1, var9)))
/*     */         {
/* 363 */           int var10 = var9.count + var1.count;
/*     */ 
/* 365 */           if (var10 <= var1.getMaxStackSize())
/*     */           {
/* 367 */             var1.count = 0;
/* 368 */             var9.count = var10;
/* 369 */             var8.d();
/* 370 */             var6 = true;
/*     */           }
/* 372 */           else if (var9.count < var1.getMaxStackSize())
/*     */           {
/* 374 */             var1.count -= var1.getMaxStackSize() - var9.count;
/* 375 */             var9.count = var1.getMaxStackSize();
/* 376 */             var8.d();
/* 377 */             var6 = true;
/*     */           }
/*     */         }
/*     */ 
/* 381 */         if (var5)
/*     */         {
/* 383 */           var7--;
/*     */         }
/*     */         else
/*     */         {
/* 387 */           var7++;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 392 */     if (var1.count > 0)
/*     */     {
/*     */       int var11;
/* 396 */       if (var5)
/*     */       {
/* 398 */         var11 = var4 - 1;
/*     */       }
/*     */       else
/*     */       {
/* 402 */         var11 = var3;
/*     */       }
/*     */ 
/* 405 */       while (((!var5) && (var11 < var4)) || ((var5) && (var11 >= var3)))
/*     */       {
/* 407 */         Slot var12 = (Slot)this.e.get(var11);
/* 408 */         ItemStack var13 = var12.getItem();
/*     */ 
/* 410 */         if (var13 == null)
/*     */         {
/* 412 */           var12.set(var1.cloneItemStack());
/* 413 */           var12.d();
/* 414 */           var1.count = 0;
/* 415 */           var6 = true;
/* 416 */           break;
/*     */         }
/*     */ 
/* 419 */         if (var5)
/*     */         {
/* 421 */           var11--;
/*     */         }
/*     */         else
/*     */         {
/* 425 */           var11++;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 430 */     var1.count = 1;
/* 431 */     return var6;
/*     */   }
/*     */ 
/*     */   public ItemStack clickItem(int var1, int var2, boolean var3, EntityHuman var4)
/*     */   {
/* 437 */     ItemStack var5 = null;
/*     */ 
/* 439 */     if (var2 > 1)
/*     */     {
/* 441 */       return null;
/*     */     }
/*     */ 
/* 445 */     if ((var2 == 0) || (var2 == 1))
/*     */     {
/* 447 */       PlayerInventory var6 = var4.inventory;
/*     */ 
/* 449 */       if (var1 == -999)
/*     */       {
/* 451 */         if ((var6.getCarried() != null) && (var1 == -999))
/*     */         {
/* 453 */           if (var2 == 0)
/*     */           {
/* 455 */             var4.drop(var6.getCarried());
/* 456 */             var6.setCarried(null);
/*     */           }
/*     */ 
/* 459 */           if (var2 == 1)
/*     */           {
/* 461 */             var4.drop(var6.getCarried().a(1));
/*     */ 
/* 463 */             if (var6.getCarried().count == 0)
/*     */             {
/* 465 */               var6.setCarried(null);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 470 */       else if (var3)
/*     */       {
/* 472 */         ItemStack var7 = a(var1);
/*     */ 
/* 474 */         if (var7 != null)
/*     */         {
/* 476 */           int var8 = var7.id;
/* 477 */           var5 = var7.cloneItemStack();
/* 478 */           Slot var9 = (Slot)this.e.get(var1);
/*     */ 
/* 480 */           if ((var9 != null) && (var9.getItem() != null) && (var9.getItem().id == var8) && (var9.getItem().isStackable()))
/*     */           {
/* 482 */             retrySlotClick(var1, var2, 1, var9.getItem().getMaxStackSize(), var3, var4);
/*     */           }
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 488 */         if (var1 < 0)
/*     */         {
/* 490 */           return null;
/*     */         }
/*     */ 
/* 493 */         Slot var12 = (Slot)this.e.get(var1);
/*     */ 
/* 495 */         if (var12 != null)
/*     */         {
/* 497 */           var12.d();
/* 498 */           ItemStack var13 = var12.getItem();
/* 499 */           ItemStack var14 = var6.getCarried();
/*     */ 
/* 501 */           if (var13 != null)
/*     */           {
/* 503 */             var5 = var13.cloneItemStack();
/*     */           }
/*     */ 
/* 508 */           if (var13 == null)
/*     */           {
/* 510 */             if ((var14 != null) && (var12.isAllowed(var14)))
/*     */             {
/* 512 */               int var10 = var2 != 0 ? 1 : var14.count;
/*     */ 
/* 514 */               if (var10 > var12.a())
/*     */               {
/* 516 */                 var10 = var12.a();
/*     */               }
/*     */ 
/* 519 */               var12.set(var14.a(var10));
/*     */ 
/* 521 */               if (var14.count == 0)
/*     */               {
/* 523 */                 var6.setCarried(null);
/*     */               }
/*     */             }
/*     */           }
/* 527 */           else if (var14 == null)
/*     */           {
/* 529 */             int var10 = var2 != 0 ? (var13.count + 1) / 2 : var13.count;
/* 530 */             ItemStack var11 = var12.a(var10);
/* 531 */             var6.setCarried(var11);
/*     */ 
/* 533 */             if ((var1 >= 10) && (var1 <= 25))
/*     */             {
/* 535 */               var12.set(new ItemStack(var11.id, 1, var11.getData()));
/*     */             }
/* 537 */             else if (var13.count == 0)
/*     */             {
/* 539 */               var12.set(null);
/*     */             }
/*     */ 
/* 542 */             var12.c(var6.getCarried());
/*     */           }
/* 544 */           else if (var12.isAllowed(var14))
/*     */           {
/* 546 */             if ((var13.id == var14.id) && ((!var13.usesData()) || (var13.getData() == var14.getData())) && (ItemStack.equals(var13, var14)))
/*     */             {
/* 548 */               int var10 = var2 != 0 ? 1 : var14.count;
/*     */ 
/* 550 */               if (var10 > var12.a() - var13.count)
/*     */               {
/* 552 */                 var10 = var12.a() - var13.count;
/*     */               }
/*     */ 
/* 555 */               if (var10 > var14.getMaxStackSize() - var13.count)
/*     */               {
/* 557 */                 var10 = var14.getMaxStackSize() - var13.count;
/*     */               }
/*     */ 
/* 560 */               var14.a(var10);
/*     */ 
/* 562 */               if (var14.count == 0)
/*     */               {
/* 564 */                 var6.setCarried(null);
/*     */               }
/*     */ 
/* 567 */               var13.count += var10;
/*     */             }
/* 569 */             else if (var14.count <= var12.a())
/*     */             {
/* 571 */               var12.set(var14);
/* 572 */               var6.setCarried(var13);
/*     */             }
/*     */           }
/* 575 */           else if ((var13.id == var14.id) && (var14.getMaxStackSize() > 1) && ((!var13.usesData()) || (var13.getData() == var14.getData())) && (ItemStack.equals(var13, var14)))
/*     */           {
/* 577 */             int var10 = var13.count;
/*     */ 
/* 579 */             if ((var10 > 0) && (var10 + var14.count <= var14.getMaxStackSize()))
/*     */             {
/* 581 */               var14.count += var10;
/*     */ 
/* 583 */               if ((var1 < 10) || (var1 > 25))
/*     */               {
/* 585 */                 var13.a(var10);
/*     */ 
/* 587 */                 if (var13.count == 0)
/*     */                 {
/* 589 */                   var12.set(null);
/*     */                 }
/*     */               }
/*     */ 
/* 593 */               var12.c(var6.getCarried());
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 600 */     return var5;
/*     */   }
/*     */ 
/*     */   protected void retrySlotClick(int var1, int var2, int var3, int var4, boolean var5, EntityHuman var6)
/*     */   {
/* 606 */     if (var3 < var4)
/*     */     {
/* 608 */       var3++;
/* 609 */       slotClick(var1, var2, var3, var4, var5, var6);
/*     */     }
/*     */   }
/*     */ 
/*     */   public ItemStack slotClick(int var1, int var2, int var3, int var4, boolean var5, EntityHuman var6)
/*     */   {
/* 615 */     ItemStack var7 = null;
/*     */ 
/* 617 */     if (var2 > 1)
/*     */     {
/* 619 */       return null;
/*     */     }
/*     */ 
/* 623 */     if ((var2 == 0) || (var2 == 1))
/*     */     {
/* 625 */       PlayerInventory var8 = var6.inventory;
/*     */ 
/* 627 */       if (var1 == -999)
/*     */       {
/* 629 */         if ((var8.getCarried() != null) && (var1 == -999))
/*     */         {
/* 631 */           if (var2 == 0)
/*     */           {
/* 633 */             var6.drop(var8.getCarried());
/* 634 */             var8.setCarried(null);
/*     */           }
/*     */ 
/* 637 */           if (var2 == 1)
/*     */           {
/* 639 */             var6.drop(var8.getCarried().a(1));
/*     */ 
/* 641 */             if (var8.getCarried().count == 0)
/*     */             {
/* 643 */               var8.setCarried(null);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 648 */       else if (var5)
/*     */       {
/* 650 */         ItemStack var9 = a(var1);
/*     */ 
/* 652 */         if (var9 != null)
/*     */         {
/* 654 */           int var10 = var9.id;
/* 655 */           var7 = var9.cloneItemStack();
/* 656 */           Slot var11 = (Slot)this.e.get(var1);
/*     */ 
/* 658 */           if ((var11 != null) && (var11.getItem() != null) && (var11.getItem().id == var10))
/*     */           {
/* 660 */             retrySlotClick(var1, var2, var3, var4, var5, var6);
/*     */           }
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 666 */         if (var1 < 0)
/*     */         {
/* 668 */           return null;
/*     */         }
/*     */ 
/* 671 */         Slot var14 = (Slot)this.e.get(var1);
/*     */ 
/* 673 */         if (var14 != null)
/*     */         {
/* 675 */           var14.d();
/* 676 */           ItemStack var16 = var14.getItem();
/* 677 */           ItemStack var15 = var8.getCarried();
/*     */ 
/* 679 */           if (var16 != null)
/*     */           {
/* 681 */             var7 = var16.cloneItemStack();
/*     */           }
/*     */ 
/* 686 */           if (var16 == null)
/*     */           {
/* 688 */             if ((var15 != null) && (var14.isAllowed(var15)))
/*     */             {
/* 690 */               int var12 = var2 != 0 ? 1 : var15.count;
/*     */ 
/* 692 */               if (var12 > var14.a())
/*     */               {
/* 694 */                 var12 = var14.a();
/*     */               }
/*     */ 
/* 697 */               var14.set(var15.a(var12));
/*     */ 
/* 699 */               if (var15.count == 0)
/*     */               {
/* 701 */                 var8.setCarried(null);
/*     */               }
/*     */             }
/*     */           }
/* 705 */           else if (var15 == null)
/*     */           {
/* 707 */             int var12 = var2 != 0 ? (var16.count + 1) / 2 : var16.count;
/* 708 */             ItemStack var13 = var14.a(var12);
/* 709 */             var8.setCarried(var13);
/*     */ 
/* 711 */             if ((var1 >= 10) && (var1 <= 25))
/*     */             {
/* 713 */               var14.set(new ItemStack(var13.id, 1, var13.getData()));
/*     */             }
/* 715 */             else if (var16.count == 0)
/*     */             {
/* 717 */               var14.set(null);
/*     */             }
/*     */ 
/* 720 */             var14.c(var8.getCarried());
/*     */           }
/* 722 */           else if (var14.isAllowed(var15))
/*     */           {
/* 724 */             if ((var16.id == var15.id) && ((!var16.usesData()) || (var16.getData() == var15.getData())) && (ItemStack.equals(var16, var15)))
/*     */             {
/* 726 */               int var12 = var2 != 0 ? 1 : var15.count;
/*     */ 
/* 728 */               if (var12 > var14.a() - var16.count)
/*     */               {
/* 730 */                 var12 = var14.a() - var16.count;
/*     */               }
/*     */ 
/* 733 */               if (var12 > var15.getMaxStackSize() - var16.count)
/*     */               {
/* 735 */                 var12 = var15.getMaxStackSize() - var16.count;
/*     */               }
/*     */ 
/* 738 */               var15.a(var12);
/*     */ 
/* 740 */               if (var15.count == 0)
/*     */               {
/* 742 */                 var8.setCarried(null);
/*     */               }
/*     */ 
/* 745 */               var16.count += var12;
/*     */             }
/* 747 */             else if (var15.count <= var14.a())
/*     */             {
/* 749 */               var14.set(var15);
/* 750 */               var8.setCarried(var16);
/*     */             }
/*     */           }
/* 753 */           else if ((var16.id == var15.id) && (var15.getMaxStackSize() > 1) && ((!var16.usesData()) || (var16.getData() == var15.getData())) && (ItemStack.equals(var16, var15)))
/*     */           {
/* 755 */             int var12 = var16.count;
/*     */ 
/* 757 */             if ((var12 > 0) && (var12 + var15.count <= var15.getMaxStackSize()))
/*     */             {
/* 759 */               var15.count += var12;
/*     */ 
/* 761 */               if ((var1 < 10) || (var1 > 25))
/*     */               {
/* 763 */                 var16.a(var12);
/*     */ 
/* 765 */                 if (var16.count == 0)
/*     */                 {
/* 767 */                   var14.set(null);
/*     */                 }
/*     */               }
/*     */ 
/* 771 */               var14.c(var8.getCarried());
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 778 */     return var7;
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ContainerTransmutation
 * JD-Core Version:    0.6.2
 */