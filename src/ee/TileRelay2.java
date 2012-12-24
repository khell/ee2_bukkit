/*     */ package ee;
/*     */ 
/*     */ import buildcraft.api.ISpecialInventory;
/*     */ import buildcraft.api.Orientations;
/*     */ import ee.core.GuiIds;
/*     */ import forge.ISidedInventory;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityItem;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.NBTTagCompound;
/*     */ import net.minecraft.server.NBTTagList;
/*     */ import net.minecraft.server.TileEntity;
/*     */ import net.minecraft.server.TileEntityChest;
/*     */ import net.minecraft.server.World;
/*     */ import net.minecraft.server.mod_EE;
/*     */ 
/*     */ public class TileRelay2 extends TileEE
/*     */   implements ISpecialInventory, ISidedInventory, IEEPowerNet
/*     */ {
/*  19 */   private ItemStack[] items = new ItemStack[14];
/*     */   public int scaledEnergy;
/*     */   public int accumulate;
/*     */   public int arrayCounter;
/*     */   private float woftFactor;
/*  24 */   private int in = 0;
/*     */   private int klein;
/*     */   private boolean isSending;
/*     */   public int burnTimeRemainingScaled;
/*     */   public int cookProgressScaled;
/*     */   public int kleinDrainingScaled;
/*     */   public int kleinChargingScaled;
/*     */   public int relayEnergyScaled;
/*     */   public int kleinDrainPoints;
/*     */   public int kleinChargePoints;
/*     */ 
/*     */   public TileRelay2()
/*     */   {
/*  37 */     this.klein = (this.items.length - 1);
/*  38 */     this.arrayCounter = 0;
/*  39 */     this.accumulate = 0;
/*  40 */     this.woftFactor = 1.0F;
/*  41 */     this.kleinDrainPoints = 0;
/*  42 */     this.kleinChargePoints = 0;
/*  43 */     this.kleinDrainingScaled = 0;
/*  44 */     this.kleinChargingScaled = 0;
/*  45 */     this.relayEnergyScaled = 0;
/*     */   }
/*     */ 
/*     */   public void onBlockRemoval()
/*     */   {
/*  50 */     for (int var1 = 0; var1 < getSize(); var1++)
/*     */     {
/*  52 */       ItemStack var2 = getItem(var1);
/*     */ 
/*  54 */       if (var2 != null)
/*     */       {
/*  56 */         float var3 = this.world.random.nextFloat() * 0.8F + 0.1F;
/*  57 */         float var4 = this.world.random.nextFloat() * 0.8F + 0.1F;
/*  58 */         float var5 = this.world.random.nextFloat() * 0.8F + 0.1F;
/*     */ 
/*  60 */         while (var2.count > 0)
/*     */         {
/*  62 */           int var6 = this.world.random.nextInt(21) + 10;
/*     */ 
/*  64 */           if (var6 > var2.count)
/*     */           {
/*  66 */             var6 = var2.count;
/*     */           }
/*     */ 
/*  69 */           var2.count -= var6;
/*  70 */           EntityItem var7 = new EntityItem(this.world, this.x + var3, this.y + var4, this.z + var5, new ItemStack(var2.id, var6, var2.getData()));
/*     */ 
/*  72 */           if (var7 != null)
/*     */           {
/*  74 */             float var8 = 0.05F;
/*  75 */             var7.motX = ((float)this.world.random.nextGaussian() * var8);
/*  76 */             var7.motY = ((float)this.world.random.nextGaussian() * var8 + 0.2F);
/*  77 */             var7.motZ = ((float)this.world.random.nextGaussian() * var8);
/*     */ 
/*  79 */             if ((var7.itemStack.getItem() instanceof ItemKleinStar))
/*     */             {
/*  81 */               ((ItemKleinStar)var7.itemStack.getItem()).setKleinPoints(var7.itemStack, ((ItemKleinStar)var2.getItem()).getKleinPoints(var2));
/*     */             }
/*     */ 
/*  84 */             this.world.addEntity(var7);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean isChest(TileEntity var1)
/*     */   {
/*  93 */     return ((var1 instanceof TileEntityChest)) || ((var1 instanceof TileAlchChest));
/*     */   }
/*     */ 
/*     */   public static boolean putInChest(TileEntity var0, ItemStack var1)
/*     */   {
/*  98 */     if ((var1 != null) && (var1.id != 0))
/*     */     {
/* 100 */       if (var0 == null)
/*     */       {
/* 102 */         return false;
/*     */       }
/*     */ 
/* 109 */       if ((var0 instanceof TileEntityChest))
/*     */       {
/* 111 */         for (int var2 = 0; var2 < ((TileEntityChest)var0).getSize(); var2++)
/*     */         {
/* 113 */           ItemStack var3 = ((TileEntityChest)var0).getItem(var2);
/*     */ 
/* 115 */           if ((var3 != null) && (var3.doMaterialsMatch(var1)) && (var3.count + var1.count <= var3.getMaxStackSize()))
/*     */           {
/* 117 */             var3.count += var1.count;
/* 118 */             return true;
/*     */           }
/*     */         }
/*     */ 
/* 122 */         for (var2 = 0; var2 < ((TileEntityChest)var0).getSize(); var2++)
/*     */         {
/* 124 */           if (((TileEntityChest)var0).getItem(var2) == null)
/*     */           {
/* 126 */             ((TileEntityChest)var0).setItem(var2, var1);
/* 127 */             return true;
/*     */           }
/*     */         }
/*     */       }
/* 131 */       else if ((var0 instanceof TileAlchChest))
/*     */       {
/* 133 */         for (int var2 = 0; var2 < ((TileAlchChest)var0).getSize(); var2++)
/*     */         {
/* 135 */           ItemStack var3 = ((TileAlchChest)var0).getItem(var2);
/*     */ 
/* 137 */           if ((var3 != null) && (var3.doMaterialsMatch(var1)) && (var3.count + var1.count <= var3.getMaxStackSize()) && (var3.getData() == var1.getData()))
/*     */           {
/* 139 */             var3.count += var1.count;
/* 140 */             return true;
/*     */           }
/*     */         }
/*     */ 
/* 144 */         for (var2 = 0; var2 < ((TileAlchChest)var0).getSize(); var2++)
/*     */         {
/* 146 */           if (((TileAlchChest)var0).getItem(var2) == null)
/*     */           {
/* 148 */             ((TileAlchChest)var0).setItem(var2, var1);
/* 149 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 154 */       return false;
/*     */     }
/*     */ 
/* 159 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean tryDropInChest(ItemStack var1)
/*     */   {
/* 165 */     TileEntity var2 = null;
/*     */ 
/* 167 */     if (isChest(this.world.getTileEntity(this.x, this.y + 1, this.z)))
/*     */     {
/* 169 */       var2 = this.world.getTileEntity(this.x, this.y + 1, this.z);
/* 170 */       return putInChest(var2, var1);
/*     */     }
/* 172 */     if (isChest(this.world.getTileEntity(this.x, this.y - 1, this.z)))
/*     */     {
/* 174 */       var2 = this.world.getTileEntity(this.x, this.y - 1, this.z);
/* 175 */       return putInChest(var2, var1);
/*     */     }
/* 177 */     if (isChest(this.world.getTileEntity(this.x + 1, this.y, this.z)))
/*     */     {
/* 179 */       var2 = this.world.getTileEntity(this.x + 1, this.y, this.z);
/* 180 */       return putInChest(var2, var1);
/*     */     }
/* 182 */     if (isChest(this.world.getTileEntity(this.x - 1, this.y, this.z)))
/*     */     {
/* 184 */       var2 = this.world.getTileEntity(this.x - 1, this.y, this.z);
/* 185 */       return putInChest(var2, var1);
/*     */     }
/* 187 */     if (isChest(this.world.getTileEntity(this.x, this.y, this.z + 1)))
/*     */     {
/* 189 */       var2 = this.world.getTileEntity(this.x, this.y, this.z + 1);
/* 190 */       return putInChest(var2, var1);
/*     */     }
/* 192 */     if (isChest(this.world.getTileEntity(this.x, this.y, this.z - 1)))
/*     */     {
/* 194 */       var2 = this.world.getTileEntity(this.x, this.y, this.z - 1);
/* 195 */       return putInChest(var2, var1);
/*     */     }
/*     */ 
/* 199 */     return false;
/*     */   }
/*     */ 
/*     */   public int getSize()
/*     */   {
/* 208 */     return this.items.length;
/*     */   }
/*     */ 
/*     */   public int getMaxStackSize()
/*     */   {
/* 217 */     return 64;
/*     */   }
/*     */ 
/*     */   public ItemStack getItem(int var1)
/*     */   {
/* 225 */     return this.items[var1];
/*     */   }
/*     */ 
/*     */   public ItemStack splitStack(int var1, int var2)
/*     */   {
/* 234 */     if (this.items[var1] != null)
/*     */     {
/* 238 */       if (this.items[var1].count <= var2)
/*     */       {
/* 240 */         ItemStack var3 = this.items[var1];
/* 241 */         this.items[var1] = null;
/* 242 */         return var3;
/*     */       }
/*     */ 
/* 246 */       ItemStack var3 = this.items[var1].a(var2);
/*     */ 
/* 248 */       if (this.items[var1].count == 0)
/*     */       {
/* 250 */         this.items[var1] = null;
/*     */       }
/*     */ 
/* 253 */       return var3;
/*     */     }
/*     */ 
/* 258 */     return null;
/*     */   }
/*     */ 
/*     */   public void setItem(int var1, ItemStack var2)
/*     */   {
/* 267 */     this.items[var1] = var2;
/*     */ 
/* 269 */     if ((var2 != null) && (var2.count > getMaxStackSize()))
/*     */     {
/* 271 */       var2.count = getMaxStackSize();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean addItem(ItemStack var1, boolean var2, Orientations var3)
/*     */   {
/* 277 */     switch (var3)
/*     */     {
/*     */     case Unknown:
/*     */     case XNeg:
/*     */     case XPos:
/*     */     case YNeg:
/*     */     case YPos:
/*     */     case ZNeg:
/*     */     case ZPos:
/* 287 */       if (var1 != null)
/*     */       {
/* 289 */         for (int var4 = 0; var4 <= this.items.length - 2; var4++)
/*     */         {
/* 291 */           if (this.items[var4] != null)
/*     */           {
/* 293 */             if ((this.items[var4].doMaterialsMatch(var1)) && (this.items[var4].count < this.items[var4].getMaxStackSize()))
/*     */             {
/* 295 */               if (var2)
/*     */               {
/* 297 */                 while ((this.items[var4].count < this.items[var4].getMaxStackSize()) && (var1.count > 0))
/*     */                 {
/* 299 */                   this.items[var4].count += 1;
/* 300 */                   var1.count -= 1;
/*     */                 }
/*     */ 
/* 303 */                 if (var1.count != 0);
/*     */               }
/*     */               else
/*     */               {
/* 309 */                 return true;
/*     */               }
/*     */             }
/*     */           }
/*     */           else {
/* 314 */             if ((var1.getItem() instanceof ItemKleinStar))
/*     */             {
/* 318 */               if (!var3.equals(Orientations.YPos))
/*     */               {
/* 320 */                 ItemKleinStar var5 = (ItemKleinStar)var1.getItem();
/*     */ 
/* 322 */                 if (var5.getKleinPoints(var1) == 0) continue; if (var4 != 0)
/*     */                 {
/* 324 */                   continue;
/*     */                 }
/*     */               }
/*     */               else
/*     */               {
/* 329 */                 ItemKleinStar var5 = (ItemKleinStar)var1.getItem();
/*     */ 
/* 331 */                 if ((var5.getKleinPoints(var1) == var5.getMaxPoints(var1)) || (var4 != this.items.length - 1))
/*     */                 {
/*     */                   continue;
/*     */                 }
/*     */               }
/*     */             }
/*     */ 
/* 338 */             if (var2)
/*     */             {
/* 340 */               for (this.items[var4] = var1.cloneItemStack(); var1.count > 0; var1.count -= 1);
/*     */             }
/*     */ 
/* 346 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */       break;
/*     */     }
/* 352 */     return false;
/*     */   }
/*     */ 
/*     */   public ItemStack extractItem(boolean var1, Orientations var2)
/*     */   {
/* 360 */     switch (var2)
/*     */     {
/*     */     case XNeg:
/* 364 */       if (this.items[(this.items.length - 1)] == null)
/*     */       {
/* 366 */         return null;
/*     */       }
/* 368 */       if ((this.items[(this.items.length - 1)].getItem() instanceof ItemKleinStar))
/*     */       {
/* 370 */         ItemStack var3 = this.items[(this.items.length - 1)].cloneItemStack();
/*     */ 
/* 372 */         if (var1)
/*     */         {
/* 374 */           this.items[(this.items.length - 1)] = null;
/*     */         }
/*     */ 
/* 377 */         return var3;
/*     */       }
/*     */ 
/*     */     case Unknown:
/*     */     case XPos:
/*     */     case YNeg:
/*     */     case YPos:
/*     */     case ZNeg:
/*     */     case ZPos:
/* 387 */       if (this.items[0] == null)
/*     */       {
/* 389 */         return null;
/*     */       }
/* 391 */       if ((this.items[0].getItem() instanceof ItemKleinStar))
/*     */       {
/* 393 */         ItemStack var3 = this.items[0].cloneItemStack();
/*     */ 
/* 395 */         if (var1)
/*     */         {
/* 397 */           this.items[0] = null;
/*     */         }
/*     */ 
/* 400 */         return var3;
/*     */       }
/*     */       break;
/*     */     }
/* 404 */     return null;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 413 */     return "AM Array";
/*     */   }
/*     */ 
/*     */   public void a(NBTTagCompound var1)
/*     */   {
/* 421 */     super.a(var1);
/* 422 */     NBTTagList var2 = var1.getList("Items");
/* 423 */     this.items = new ItemStack[getSize()];
/*     */ 
/* 425 */     for (int var3 = 0; var3 < var2.size(); var3++)
/*     */     {
/* 427 */       NBTTagCompound var4 = (NBTTagCompound)var2.get(var3);
/* 428 */       byte var5 = var4.getByte("Slot");
/*     */ 
/* 430 */       if ((var5 >= 0) && (var5 < this.items.length))
/*     */       {
/* 432 */         this.items[var5] = ItemStack.a(var4);
/*     */       }
/*     */     }
/*     */ 
/* 436 */     this.scaledEnergy = var1.getInt("scaledEnergy");
/* 437 */     this.woftFactor = var1.getFloat("timeFactor");
/* 438 */     this.arrayCounter = var1.getShort("arrayCounter");
/*     */   }
/*     */ 
/*     */   public void b(NBTTagCompound var1)
/*     */   {
/* 446 */     super.b(var1);
/* 447 */     var1.setInt("scaledEnergy", this.scaledEnergy);
/* 448 */     var1.setShort("arrayCounter", (short)this.arrayCounter);
/* 449 */     var1.setFloat("timeFactor", this.woftFactor);
/* 450 */     NBTTagList var2 = new NBTTagList();
/*     */ 
/* 452 */     for (int var3 = 0; var3 < this.items.length; var3++)
/*     */     {
/* 454 */       if (this.items[var3] != null)
/*     */       {
/* 456 */         NBTTagCompound var4 = new NBTTagCompound();
/* 457 */         var4.setByte("Slot", (byte)var3);
/* 458 */         this.items[var3].save(var4);
/* 459 */         var2.add(var4);
/*     */       }
/*     */     }
/*     */ 
/* 463 */     var1.set("Items", var2);
/*     */   }
/*     */ 
/*     */   public int getCookProgressScaled(int var1)
/*     */   {
/* 468 */     return (!EEBase.isKleinStar(this.items[this.klein].id)) && (!this.isSending) ? 0 : this.items[this.klein] == null ? 0 : var1;
/*     */   }
/*     */ 
/*     */   public int getBurnTimeRemainingScaled(int var1)
/*     */   {
/* 473 */     return EEMaps.getEMC(this.items[0].id, this.items[this.in].getData()) > 0 ? var1 : this.items[0] == null ? 0 : 0;
/*     */   }
/*     */ 
/*     */   public int latentEnergy()
/*     */   {
/* 478 */     return this.scaledEnergy / 80;
/*     */   }
/*     */ 
/*     */   public boolean receiveEnergy(int var1, byte var2, boolean var3)
/*     */   {
/* 483 */     if (passAllPackets(var1, var3))
/*     */     {
/* 485 */       return true;
/*     */     }
/* 487 */     if (this.scaledEnergy <= scaledMaximum() - var1)
/*     */     {
/* 489 */       if (var3)
/*     */       {
/* 491 */         this.accumulate += var1;
/*     */       }
/*     */ 
/* 494 */       return true;
/*     */     }
/*     */ 
/* 498 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean passAllPackets(int var1, boolean var2)
/*     */   {
/* 504 */     int var3 = 0;
/*     */ 
/* 506 */     for (byte var4 = 0; var4 < 6; var4 = (byte)(var4 + 1))
/*     */     {
/* 508 */       if (passEnergy(var1, var4, false))
/*     */       {
/* 510 */         var3++;
/*     */       }
/*     */     }
/*     */ 
/* 514 */     if (var3 == 0)
/*     */     {
/* 516 */       return false;
/*     */     }
/* 518 */     if (!var2)
/*     */     {
/* 520 */       return true;
/*     */     }
/*     */ 
/* 524 */     int var6 = var1 / var3;
/*     */ 
/* 526 */     if (var6 < 1)
/*     */     {
/* 528 */       return false;
/*     */     }
/*     */ 
/* 532 */     for (byte var5 = 0; var5 < 6; var5 = (byte)(var5 + 1))
/*     */     {
/* 534 */       passEnergy(var6, var5, true);
/*     */     }
/*     */ 
/* 537 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean passEnergy(int var1, byte var2, boolean var3)
/*     */   {
/* 544 */     TileEntity var4 = this.world.getTileEntity(this.x + (var2 == 4 ? 1 : var2 == 5 ? -1 : 0), this.y + (var2 == 0 ? 1 : var2 == 1 ? -1 : 0), this.z + (var2 == 2 ? 1 : var2 == 3 ? -1 : 0));
/*     */ 
/* 546 */     if (var4 == null)
/*     */     {
/* 548 */       return false;
/*     */     }
/* 550 */     if ((!(var4 instanceof TileRelay)) && (!(var4 instanceof TileRelay2)) && (!(var4 instanceof TileRelay3)))
/*     */     {
/* 552 */       return ((var4 instanceof IEEPowerNet)) && (((IEEPowerNet)var4).receiveEnergy(var1, var2, var3));
/*     */     }
/*     */ 
/* 556 */     IEEPowerNet var10000 = (IEEPowerNet)var4;
/* 557 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean sendEnergy(int var1, byte var2, boolean var3)
/*     */   {
/* 563 */     TileEntity var4 = this.world.getTileEntity(this.x + (var2 == 4 ? 1 : var2 == 5 ? -1 : 0), this.y + (var2 == 0 ? 1 : var2 == 1 ? -1 : 0), this.z + (var2 == 2 ? 1 : var2 == 3 ? -1 : 0));
/* 564 */     return var4 != null;
/*     */   }
/*     */ 
/*     */   public void sendAllPackets(int var1)
/*     */   {
/* 569 */     int var2 = 0;
/*     */ 
/* 571 */     for (byte var3 = 0; var3 < 6; var3 = (byte)(var3 + 1))
/*     */     {
/* 573 */       if (sendEnergy(var1, var3, false))
/*     */       {
/* 575 */         var2++;
/*     */       }
/*     */     }
/*     */ 
/* 579 */     if (var2 != 0)
/*     */     {
/* 581 */       int var5 = var1 / var2;
/*     */ 
/* 583 */       if (var5 >= 1)
/*     */       {
/* 585 */         for (byte var4 = 0; var4 < 6; var4 = (byte)(var4 + 1))
/*     */         {
/* 587 */           if (this.scaledEnergy - var5 <= 0)
/*     */           {
/* 589 */             return;
/*     */           }
/*     */ 
/* 592 */           if (sendEnergy(var5, var4, true))
/*     */           {
/* 594 */             this.scaledEnergy -= var5;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int relayBonus()
/*     */   {
/* 603 */     return 12;
/*     */   }
/*     */ 
/*     */   private float getRelayOutput()
/*     */   {
/* 608 */     return 192.0F;
/*     */   }
/*     */ 
/*     */   private int relayMaximum()
/*     */   {
/* 613 */     return 1000000;
/*     */   }
/*     */ 
/*     */   private int scaledMaximum()
/*     */   {
/* 618 */     return relayMaximum() * 80;
/*     */   }
/*     */ 
/*     */   public int getRelayProductivity()
/*     */   {
/* 623 */     return (int)(getRelayOutput() * getWOFTReciprocal(this.woftFactor));
/*     */   }
/*     */ 
/*     */   public void q_()
/*     */   {
/* 632 */     if (!clientFail())
/*     */     {
/* 634 */       boolean var1 = false;
/* 635 */       this.woftFactor = (EEBase.getPedestalFactor(this.world) * EEBase.getPlayerWatchFactor());
/*     */ 
/* 637 */       if (!this.world.isStatic)
/*     */       {
/* 639 */         this.burnTimeRemainingScaled = getBurnTimeRemainingScaled(12);
/* 640 */         this.cookProgressScaled = getCookProgressScaled(24);
/* 641 */         this.kleinDrainingScaled = getKleinDrainingScaled(30);
/* 642 */         this.kleinChargingScaled = getKleinChargingScaled(30);
/* 643 */         this.relayEnergyScaled = getRelayEnergyScaled(102);
/*     */ 
/* 645 */         if (this.accumulate > 0)
/*     */         {
/* 647 */           this.scaledEnergy += this.accumulate;
/* 648 */           this.accumulate = 0;
/*     */         }
/*     */ 
/* 653 */         if ((this.items[0] != null) && (EEBase.isKleinStar(this.items[0].id)))
/*     */         {
/* 655 */           for (int var2 = getRelayProductivity() * EEBase.getKleinLevel(this.items[0].id); var2 > 0; var2--)
/*     */           {
/* 657 */             if ((latentEnergy() < relayMaximum()) && (EEBase.takeKleinStarPoints(this.items[0], 1, this.world)))
/*     */             {
/* 659 */               this.scaledEnergy += 80;
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 664 */         if ((this.arrayCounter <= 0) && (canDestroy()))
/*     */         {
/* 666 */           this.arrayCounter = 5;
/* 667 */           var1 = true;
/* 668 */           destroyItem();
/*     */         }
/*     */ 
/* 671 */         if (this.scaledEnergy >= getRelayProductivity())
/*     */         {
/* 673 */           sendAllPackets(getRelayProductivity());
/*     */ 
/* 675 */           if ((this.items[(this.items.length - 1)] != null) && (EEBase.isKleinStar(this.items[(this.items.length - 1)].id)) && (this.scaledEnergy > 80))
/*     */           {
/* 677 */             for (int var2 = getRelayProductivity() * EEBase.getKleinLevel(this.items[(this.items.length - 1)].id); var2 > 0; var2--)
/*     */             {
/* 679 */               if ((this.scaledEnergy >= 80) && (EEBase.addKleinStarPoints(this.items[(this.items.length - 1)], 1, this.world)))
/*     */               {
/* 681 */                 this.scaledEnergy -= 80;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 687 */         if (this.arrayCounter > 0)
/*     */         {
/* 689 */           this.arrayCounter -= 1;
/*     */         }
/*     */       }
/*     */ 
/* 693 */       if (var1)
/*     */       {
/* 695 */         this.world.notify(this.x, this.y, this.z);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private int getRelayEnergyScaled(int var1)
/*     */   {
/* 702 */     return latentEnergy() * var1 / relayMaximum();
/*     */   }
/*     */ 
/*     */   private int getKleinChargingScaled(int var1)
/*     */   {
/* 707 */     if ((this.items[(this.items.length - 1)] != null) && ((this.items[(this.items.length - 1)].getItem() instanceof ItemKleinStar)))
/*     */     {
/* 709 */       this.kleinChargePoints = ((ItemKleinStar)this.items[(this.items.length - 1)].getItem()).getKleinPoints(this.items[(this.items.length - 1)]);
/* 710 */       return ((ItemKleinStar)this.items[(this.items.length - 1)].getItem()).getKleinPoints(this.items[(this.items.length - 1)]) * var1 / ((ItemKleinStar)this.items[(this.items.length - 1)].getItem()).getMaxPoints(this.items[(this.items.length - 1)]);
/*     */     }
/*     */ 
/* 714 */     this.kleinChargePoints = 0;
/* 715 */     return 0;
/*     */   }
/*     */ 
/*     */   private int getKleinDrainingScaled(int var1)
/*     */   {
/* 721 */     if ((this.items[0] != null) && ((this.items[0].getItem() instanceof ItemKleinStar)))
/*     */     {
/* 723 */       this.kleinDrainPoints = ((ItemKleinStar)this.items[0].getItem()).getKleinPoints(this.items[0]);
/* 724 */       return ((ItemKleinStar)this.items[0].getItem()).getKleinPoints(this.items[0]) * var1 / ((ItemKleinStar)this.items[0].getItem()).getMaxPoints(this.items[0]);
/*     */     }
/*     */ 
/* 728 */     this.kleinDrainPoints = 0;
/* 729 */     return 0;
/*     */   }
/*     */ 
/*     */   private boolean canDestroy()
/*     */   {
/* 735 */     if (this.items[0] == null)
/*     */     {
/* 737 */       for (int var1 = this.items.length - 2; var1 >= 1; var1--)
/*     */       {
/* 739 */         if ((this.items[var1] != null) && (EEMaps.getEMC(this.items[var1]) > 0))
/*     */         {
/* 741 */           this.items[0] = this.items[var1].cloneItemStack();
/* 742 */           this.items[var1] = null;
/* 743 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 748 */     return this.items[0] != null;
/*     */   }
/*     */ 
/*     */   public void destroyItem()
/*     */   {
/* 753 */     if (canDestroy())
/*     */     {
/* 755 */       if (!EEBase.isKleinStar(this.items[0].id))
/*     */       {
/* 757 */         this.scaledEnergy += getCorrectValue(this.items[0]) * 80;
/* 758 */         this.items[0].count -= 1;
/*     */ 
/* 760 */         if (this.items[0].count <= 0)
/*     */         {
/* 762 */           this.items[0] = null;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private int getCorrectValue(ItemStack var1)
/*     */   {
/* 770 */     return EEMaps.getEMC(var1);
/*     */   }
/*     */ 
/*     */   private int getItemBurnTime(ItemStack var1)
/*     */   {
/* 775 */     if (var1 == null)
/*     */     {
/* 777 */       return 0;
/*     */     }
/* 779 */     if (EEBase.isKleinStar(var1.id))
/*     */     {
/* 781 */       return 0;
/*     */     }
/* 783 */     if (EEMaps.getEMC(var1) == 0)
/*     */     {
/* 785 */       EntityItem var2 = new EntityItem(this.world, this.x, this.y, this.z, var1.cloneItemStack());
/* 786 */       var2.pickupDelay = 10;
/* 787 */       this.world.addEntity(var2);
/* 788 */       var1 = null;
/* 789 */       return 0;
/*     */     }
/*     */ 
/* 793 */     return var1.d() ? (int)(EEMaps.getEMC(var1.id) * (var1.i() - var1.getData()) / var1.i()) : EEMaps.getEMC(var1);
/*     */   }
/*     */ 
/*     */   public void f()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void g()
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean a(EntityHuman var1)
/*     */   {
/* 806 */     return this.world.getTileEntity(this.x, this.y, this.z) == this;
/*     */   }
/*     */ 
/*     */   public int getStartInventorySide(int var1)
/*     */   {
/* 811 */     return var1 == 1 ? this.items.length - 1 : 0;
/*     */   }
/*     */ 
/*     */   public int getSizeInventorySide(int var1)
/*     */   {
/* 816 */     return var1 == 1 ? 1 : this.items.length - 1;
/*     */   }
/*     */ 
/*     */   public boolean onBlockActivated(EntityHuman var1)
/*     */   {
/* 821 */     if (!this.world.isStatic)
/*     */     {
/* 823 */       var1.openGui(mod_EE.getInstance(), GuiIds.RELAY_2, this.world, this.x, this.y, this.z);
/*     */     }
/*     */ 
/* 826 */     return true;
/*     */   }
/*     */ 
/*     */   public int getTextureForSide(int var1)
/*     */   {
/* 831 */     if (var1 == 1)
/*     */     {
/* 833 */       return EEBase.relay2Top;
/*     */     }
/*     */ 
/* 837 */     byte var2 = this.direction;
/* 838 */     return var1 == var2 ? EEBase.relayFront : EEBase.relaySide;
/*     */   }
/*     */ 
/*     */   public int getInventoryTexture(int var1)
/*     */   {
/* 844 */     return var1 == 3 ? EEBase.relayFront : var1 == 1 ? EEBase.relay2Top : EEBase.relaySide;
/*     */   }
/*     */ 
/*     */   public int getLightValue()
/*     */   {
/* 849 */     return 11;
/*     */   }
/*     */ 
/*     */   public void onNeighborBlockChange(int var1)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void randomDisplayTick(Random var1)
/*     */   {
/*     */   }
/*     */ 
/*     */   public ItemStack splitWithoutUpdate(int var1)
/*     */   {
/* 862 */     return null;
/*     */   }
/*     */ 
/*     */   public ItemStack[] getContents()
/*     */   {
/* 867 */     return this.items;
/*     */   }
/*     */ 
/*     */   public void setMaxStackSize(int size)
/*     */   {
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.TileRelay2
 * JD-Core Version:    0.6.2
 */