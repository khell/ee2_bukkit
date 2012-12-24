/*     */ package ee;
/*     */ 
/*     */ import buildcraft.api.ISpecialInventory;
/*     */ import buildcraft.api.Orientations;
/*     */ import ee.core.GuiIds;
/*     */ import forge.ISidedInventory;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityItem;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.NBTTagCompound;
/*     */ import net.minecraft.server.NBTTagList;
/*     */ import net.minecraft.server.TileEntity;
/*     */ import net.minecraft.server.World;
/*     */ import net.minecraft.server.WorldProvider;
/*     */ import net.minecraft.server.mod_EE;
/*     */ 
/*     */ public class TileCollector extends TileEE
/*     */   implements ISpecialInventory, ISidedInventory, IEEPowerNet
/*     */ {
/*  19 */   private ItemStack[] items = new ItemStack[11];
/*  20 */   public int currentSunStatus = 1;
/*  21 */   public int collectorSunTime = 0;
/*  22 */   private int accumulate = 0;
/*  23 */   private float woftFactor = 1.0F;
/*  24 */   public int currentFuelProgress = 0;
/*     */   public boolean isUsingPower;
/*  26 */   public int kleinProgressScaled = 0;
/*  27 */   public int sunTimeScaled = 0;
/*  28 */   public int kleinPoints = 0;
/*     */ 
/*     */   public int getSize()
/*     */   {
/*  35 */     return this.items.length;
/*     */   }
/*     */ 
/*     */   public void onBlockRemoval()
/*     */   {
/*  40 */     for (int var1 = 0; var1 < getSize(); var1++)
/*     */     {
/*  42 */       ItemStack var2 = getItem(var1);
/*     */ 
/*  44 */       if (var2 != null)
/*     */       {
/*  46 */         float var3 = this.world.random.nextFloat() * 0.8F + 0.1F;
/*  47 */         float var4 = this.world.random.nextFloat() * 0.8F + 0.1F;
/*  48 */         float var5 = this.world.random.nextFloat() * 0.8F + 0.1F;
/*     */ 
/*  50 */         while (var2.count > 0)
/*     */         {
/*  52 */           int var6 = this.world.random.nextInt(21) + 10;
/*     */ 
/*  54 */           if (var6 > var2.count)
/*     */           {
/*  56 */             var6 = var2.count;
/*     */           }
/*     */ 
/*  59 */           var2.count -= var6;
/*  60 */           EntityItem var7 = new EntityItem(this.world, this.x + var3, this.y + var4, this.z + var5, new ItemStack(var2.id, var6, var2.getData()));
/*     */ 
/*  62 */           if (var7 != null)
/*     */           {
/*  64 */             float var8 = 0.05F;
/*  65 */             var7.motX = ((float)this.world.random.nextGaussian() * var8);
/*  66 */             var7.motY = ((float)this.world.random.nextGaussian() * var8 + 0.2F);
/*  67 */             var7.motZ = ((float)this.world.random.nextGaussian() * var8);
/*     */ 
/*  69 */             if ((var7.itemStack.getItem() instanceof ItemKleinStar))
/*     */             {
/*  71 */               ((ItemKleinStar)var7.itemStack.getItem()).setKleinPoints(var7.itemStack, ((ItemKleinStar)var2.getItem()).getKleinPoints(var2));
/*     */             }
/*     */ 
/*  74 */             this.world.addEntity(var7);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getMaxStackSize()
/*     */   {
/*  87 */     return 64;
/*     */   }
/*     */ 
/*     */   public ItemStack getItem(int var1)
/*     */   {
/*  95 */     return this.items[var1];
/*     */   }
/*     */ 
/*     */   public ItemStack splitStack(int var1, int var2)
/*     */   {
/* 104 */     if (this.items[var1] != null)
/*     */     {
/* 108 */       if (this.items[var1].count <= var2)
/*     */       {
/* 110 */         ItemStack var3 = this.items[var1];
/* 111 */         this.items[var1] = null;
/* 112 */         return var3;
/*     */       }
/*     */ 
/* 116 */       ItemStack var3 = this.items[var1].a(var2);
/*     */ 
/* 118 */       if (this.items[var1].count == 0)
/*     */       {
/* 120 */         this.items[var1] = null;
/*     */       }
/*     */ 
/* 123 */       return var3;
/*     */     }
/*     */ 
/* 128 */     return null;
/*     */   }
/*     */ 
/*     */   public void setItem(int var1, ItemStack var2)
/*     */   {
/* 137 */     this.items[var1] = var2;
/*     */ 
/* 139 */     if ((var2 != null) && (var2.count > getMaxStackSize()))
/*     */     {
/* 141 */       var2.count = getMaxStackSize();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean addItem(ItemStack var1, boolean var2, Orientations var3)
/*     */   {
/* 147 */     switch (var3)
/*     */     {
/*     */     case Unknown:
/*     */     case XNeg:
/*     */     case XPos:
/*     */     case YNeg:
/*     */     case YPos:
/*     */     case ZNeg:
/*     */     case ZPos:
/* 157 */       if (var1 != null)
/*     */       {
/* 159 */         if (EEMaps.isFuel(var1))
/*     */         {
/* 161 */           for (int var4 = 0; var4 <= this.items.length - 3; var4++)
/*     */           {
/* 163 */             if (this.items[var4] == null)
/*     */             {
/* 165 */               if (var2)
/*     */               {
/* 167 */                 for (this.items[var4] = var1.cloneItemStack(); var1.count > 0; var1.count -= 1);
/*     */               }
/*     */ 
/* 173 */               return true;
/*     */             }
/*     */ 
/* 176 */             if ((this.items[var4].doMaterialsMatch(var1)) && (this.items[var4].count < this.items[var4].getMaxStackSize()))
/*     */             {
/* 178 */               if (var2)
/*     */               {
/* 180 */                 while ((this.items[var4].count < this.items[var4].getMaxStackSize()) && (var1.count > 0))
/*     */                 {
/* 182 */                   this.items[var4].count += 1;
/* 183 */                   var1.count -= 1;
/*     */                 }
/*     */ 
/* 186 */                 if (var1.count != 0);
/*     */               }
/*     */               else
/*     */               {
/* 192 */                 return true;
/*     */               }
/*     */             }
/*     */           }
/* 196 */         } else if ((EEBase.isKleinStar(var1.id)) && (this.items[0] == null))
/*     */         {
/* 198 */           if (var2)
/*     */           {
/* 200 */             for (this.items[0] = var1.cloneItemStack(); var1.count > 0; var1.count -= 1);
/*     */           }
/*     */ 
/* 206 */           return true;
/*     */         }
/*     */       }
/*     */       break;
/*     */     }
/* 211 */     return false;
/*     */   }
/*     */ 
/*     */   public ItemStack extractItem(boolean var1, Orientations var2)
/*     */   {
/* 217 */     switch (var2)
/*     */     {
/*     */     case Unknown:
/*     */     case XNeg:
/*     */     case XPos:
/*     */     case YNeg:
/*     */     case YPos:
/*     */     case ZNeg:
/*     */     case ZPos:
/* 227 */       for (int var3 = 0; var3 < this.items.length; var3++)
/*     */       {
/* 229 */         if ((this.items[var3] != null) && (var3 != this.items.length - 1))
/*     */         {
/* 233 */           if (var3 == 0)
/*     */           {
/* 235 */             if (EEBase.isKleinStar(this.items[var3].id))
/*     */             {
/* 237 */               ItemStack var4 = this.items[var3].cloneItemStack();
/*     */ 
/* 239 */               if (var1)
/*     */               {
/* 241 */                 this.items[var3] = null;
/*     */               }
/*     */ 
/* 244 */               return var4;
/*     */             }
/*     */           }
/* 247 */           else if ((this.items[var3].id == EEItem.aeternalisFuel.id) || ((this.items[(this.items.length - 1)] != null) && (this.items[var3].doMaterialsMatch(this.items[(this.items.length - 1)]))))
/*     */           {
/* 249 */             ItemStack var4 = this.items[var3].cloneItemStack();
/* 250 */             var4.count = 1;
/*     */ 
/* 252 */             if (var1)
/*     */             {
/* 254 */               this.items[var3].count -= 1;
/*     */ 
/* 256 */               if (this.items[var3].count < 1)
/*     */               {
/* 258 */                 this.items[var3] = null;
/*     */               }
/*     */             }
/*     */ 
/* 262 */             return var4;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 268 */     return null;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 277 */     return "Energy Collector";
/*     */   }
/*     */ 
/*     */   public void a(NBTTagCompound var1)
/*     */   {
/* 285 */     super.a(var1);
/* 286 */     NBTTagList var2 = var1.getList("Items");
/* 287 */     this.items = new ItemStack[getSize()];
/*     */ 
/* 289 */     for (int var3 = 0; var3 < var2.size(); var3++)
/*     */     {
/* 291 */       NBTTagCompound var4 = (NBTTagCompound)var2.get(var3);
/* 292 */       byte var5 = var4.getByte("Slot");
/*     */ 
/* 294 */       if ((var5 >= 0) && (var5 < this.items.length))
/*     */       {
/* 296 */         this.items[var5] = ItemStack.a(var4);
/*     */       }
/*     */     }
/*     */ 
/* 300 */     this.currentSunStatus = var1.getShort("sunStatus");
/* 301 */     this.woftFactor = var1.getFloat("timeFactor");
/* 302 */     this.accumulate = var1.getInt("accumulate");
/* 303 */     this.collectorSunTime = var1.getInt("sunTime");
/*     */   }
/*     */ 
/*     */   public void b(NBTTagCompound var1)
/*     */   {
/* 311 */     super.b(var1);
/* 312 */     var1.setInt("sunTime", this.collectorSunTime);
/* 313 */     var1.setFloat("timeFactor", this.woftFactor);
/* 314 */     var1.setInt("accumulate", this.accumulate);
/* 315 */     var1.setShort("sunStatus", (short)this.currentSunStatus);
/* 316 */     NBTTagList var2 = new NBTTagList();
/*     */ 
/* 318 */     for (int var3 = 0; var3 < this.items.length; var3++)
/*     */     {
/* 320 */       if (this.items[var3] != null)
/*     */       {
/* 322 */         NBTTagCompound var4 = new NBTTagCompound();
/* 323 */         var4.setByte("Slot", (byte)var3);
/* 324 */         this.items[var3].save(var4);
/* 325 */         var2.add(var4);
/*     */       }
/*     */     }
/*     */ 
/* 329 */     var1.set("Items", var2);
/*     */   }
/*     */ 
/*     */   public int getSunProgressScaled(int var1)
/*     */   {
/* 334 */     return canUpgrade() ? this.collectorSunTime * var1 / (getFuelDifference() * 80) : this.collectorSunTime * var1 / (getFuelDifference() * 80) > 24 ? 24 : getFuelDifference() <= 0 ? 0 : (this.items[0] != null) && (EEBase.isKleinStar(this.items[0].id)) ? 24 : 0;
/*     */   }
/*     */ 
/*     */   public boolean canUpgrade()
/*     */   {
/* 341 */     if (this.items[0] == null)
/*     */     {
/* 343 */       for (int var1 = this.items.length - 3; var1 >= 1; var1--)
/*     */       {
/* 345 */         if ((this.items[var1] != null) && ((this.items[(this.items.length - 1)] == null) || (!this.items[var1].doMaterialsMatch(this.items[(this.items.length - 1)]))) && (EEMaps.isFuel(this.items[var1])) && (this.items[var1].getItem().id != EEItem.aeternalisFuel.id))
/*     */         {
/* 347 */           this.items[0] = this.items[var1].cloneItemStack();
/* 348 */           this.items[var1] = null;
/* 349 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 354 */     if (this.items[0] == null)
/*     */     {
/* 356 */       if (this.items[(this.items.length - 2)] == null)
/*     */       {
/* 358 */         return false;
/*     */       }
/*     */ 
/* 361 */       if ((EEMaps.isFuel(this.items[(this.items.length - 2)])) && (this.items[(this.items.length - 2)].getItem().id != EEItem.aeternalisFuel.id))
/*     */       {
/* 363 */         this.items[0] = this.items[(this.items.length - 2)].cloneItemStack();
/* 364 */         this.items[(this.items.length - 2)] = null;
/*     */       }
/*     */     }
/*     */ 
/* 368 */     if (this.items[0] == null)
/*     */     {
/* 370 */       return false;
/*     */     }
/*     */ 
/* 374 */     if (EEBase.isKleinStar(this.items[0].id))
/*     */     {
/* 376 */       if (EEBase.canIncreaseKleinStarPoints(this.items[0], this.world))
/*     */       {
/* 378 */         return true;
/*     */       }
/*     */ 
/* 381 */       if (this.items[(this.items.length - 2)] == null)
/*     */       {
/* 383 */         this.items[(this.items.length - 2)] = this.items[0].cloneItemStack();
/* 384 */         this.items[0] = null;
/* 385 */         return false;
/*     */       }
/*     */ 
/* 388 */       for (int var1 = 1; var1 <= this.items.length - 3; var1++)
/*     */       {
/* 390 */         if (this.items[var1] == null)
/*     */         {
/* 392 */           this.items[var1] = this.items[(this.items.length - 2)].cloneItemStack();
/* 393 */           this.items[(this.items.length - 2)] = this.items[0].cloneItemStack();
/* 394 */           this.items[0] = null;
/* 395 */           return false;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 400 */     return (this.items[0].getItem().id != EEItem.aeternalisFuel.id) && (EEMaps.isFuel(this.items[0]));
/*     */   }
/*     */ 
/*     */   public boolean receiveEnergy(int var1, byte var2, boolean var3)
/*     */   {
/* 406 */     if (!isUsingPower())
/*     */     {
/* 408 */       return false;
/*     */     }
/* 410 */     if (var3)
/*     */     {
/* 412 */       this.accumulate += var1;
/* 413 */       return true;
/*     */     }
/*     */ 
/* 417 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean sendEnergy(int var1, byte var2, boolean var3)
/*     */   {
/* 423 */     TileEntity var4 = this.world.getTileEntity(this.x + (var2 == 4 ? 1 : var2 == 5 ? -1 : 0), this.y + (var2 == 0 ? 1 : var2 == 1 ? -1 : 0), this.z + (var2 == 2 ? 1 : var2 == 3 ? -1 : 0));
/* 424 */     return var4 != null;
/*     */   }
/*     */ 
/*     */   public void sendAllPackets(int var1)
/*     */   {
/* 429 */     int var2 = 0;
/*     */ 
/* 431 */     for (byte var3 = 0; var3 < 6; var3 = (byte)(var3 + 1))
/*     */     {
/* 433 */       if (sendEnergy(var1, var3, false))
/*     */       {
/* 435 */         var2++;
/*     */       }
/*     */     }
/*     */ 
/* 439 */     if (var2 == 0)
/*     */     {
/* 441 */       if (this.collectorSunTime <= 800000 - var1)
/*     */       {
/* 443 */         this.collectorSunTime += var1;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 448 */       int var5 = var1 / var2;
/*     */ 
/* 450 */       if (var5 >= 1)
/*     */       {
/* 452 */         for (byte var4 = 0; var4 < 6; var4 = (byte)(var4 + 1))
/*     */         {
/* 454 */           sendEnergy(var5, var4, true);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean passEnergy(int var1, byte var2, boolean var3)
/*     */   {
/* 462 */     return false;
/*     */   }
/*     */ 
/*     */   public int relayBonus()
/*     */   {
/* 467 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getRealSunStatus()
/*     */   {
/* 472 */     if (this.world.worldProvider.d)
/*     */     {
/* 474 */       this.currentSunStatus = 16;
/*     */     }
/*     */     else
/*     */     {
/* 478 */       this.currentSunStatus = (this.world.getLightLevel(this.x, this.y + 1, this.z) + 1);
/*     */     }
/*     */ 
/* 481 */     return this.currentSunStatus;
/*     */   }
/*     */ 
/*     */   public int getSunStatus(int var1)
/*     */   {
/* 486 */     return getRealSunStatus() * var1 / 16;
/*     */   }
/*     */ 
/*     */   public void q_()
/*     */   {
/* 495 */     if (!clientFail())
/*     */     {
/* 497 */       if (!this.world.isStatic)
/*     */       {
/* 499 */         if (this.collectorSunTime < 0)
/*     */         {
/* 501 */           this.collectorSunTime = 0;
/*     */         }
/*     */ 
/* 504 */         if ((this.items[0] != null) && ((this.items[0].getItem() instanceof ItemKleinStar)))
/*     */         {
/* 506 */           this.kleinProgressScaled = getKleinProgressScaled(48);
/* 507 */           this.kleinPoints = getKleinPoints(this.items[0]);
/*     */         }
/*     */ 
/* 510 */         this.sunTimeScaled = getSunTimeScaled(48);
/* 511 */         this.currentFuelProgress = getSunProgressScaled(24);
/* 512 */         this.currentSunStatus = getSunStatus(12);
/* 513 */         this.isUsingPower = isUsingPower();
/*     */ 
/* 516 */         for (int var1 = this.items.length - 3; var1 >= 2; var1--)
/*     */         {
/* 518 */           if ((this.items[var1] == null) && (this.items[(var1 - 1)] != null))
/*     */           {
/* 520 */             this.items[var1] = this.items[(var1 - 1)].cloneItemStack();
/* 521 */             this.items[(var1 - 1)] = null;
/*     */           }
/*     */         }
/*     */ 
/* 525 */         this.woftFactor = (EEBase.getPedestalFactor(this.world) * EEBase.getPlayerWatchFactor());
/*     */ 
/* 527 */         if (isUsingPower())
/*     */         {
/* 529 */           this.collectorSunTime += getFactoredProduction();
/*     */ 
/* 531 */           if (this.accumulate > 0)
/*     */           {
/* 533 */             this.collectorSunTime += this.accumulate;
/* 534 */             this.accumulate = 0;
/*     */           }
/*     */ 
/* 537 */           if (EEBase.isKleinStar(this.items[0].id))
/*     */           {
/* 539 */             var1 = getFactoredProduction() * EEBase.getKleinLevel(this.items[0].id);
/*     */             do {
/* 541 */               this.collectorSunTime -= 80;
/*     */ 
/* 539 */               var1--; if ((var1 <= 0) || (this.collectorSunTime < 80)) break;  } while (EEBase.addKleinStarPoints(this.items[0], 1, this.world));
/*     */           }
/*     */           else
/*     */           {
/*     */             do
/*     */             {
/* 548 */               this.collectorSunTime -= getFuelDifference() * 80;
/* 549 */               uptierFuel();
/*     */ 
/* 546 */               if (getFuelDifference() <= 0) break;  } while (this.collectorSunTime >= getFuelDifference() * 80);
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 555 */           if (this.accumulate > 0)
/*     */           {
/* 557 */             this.collectorSunTime += this.accumulate;
/* 558 */             this.accumulate = 0;
/*     */           }
/*     */ 
/* 561 */           sendAllPackets(getFactoredProduction());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private int getKleinPoints(ItemStack var1)
/*     */   {
/* 569 */     return (var1.getItem() instanceof ItemKleinStar) ? ((ItemKleinStar)var1.getItem()).getKleinPoints(var1) : var1 == null ? 0 : 0;
/*     */   }
/*     */ 
/*     */   private int getSunTimeScaled(int var1)
/*     */   {
/* 574 */     return this.collectorSunTime * var1 / 800000;
/*     */   }
/*     */ 
/*     */   private int getKleinProgressScaled(int var1)
/*     */   {
/* 579 */     return (this.items[0] != null) && ((this.items[0].getItem() instanceof ItemKleinStar)) ? ((ItemKleinStar)this.items[0].getItem()).getKleinPoints(this.items[0]) * var1 / ((ItemKleinStar)this.items[0].getItem()).getMaxPoints(this.items[0]) : 0;
/*     */   }
/*     */ 
/*     */   public int getFactoredProduction()
/*     */   {
/* 584 */     return (int)(getProduction() * getWOFTReciprocal(this.woftFactor));
/*     */   }
/*     */ 
/*     */   public int getProduction()
/*     */   {
/* 589 */     return getRealSunStatus();
/*     */   }
/*     */ 
/*     */   public boolean isUsingPower()
/*     */   {
/* 594 */     return (canUpgrade()) && (canCollect());
/*     */   }
/*     */ 
/*     */   private int getFuelDifference()
/*     */   {
/* 599 */     return this.items[0] == null ? 0 : getFuelLevel(getNextFuel(this.items[0])) - getFuelLevel(this.items[0]);
/*     */   }
/*     */ 
/*     */   private int getFuelLevel(ItemStack var1)
/*     */   {
/* 604 */     return EEMaps.getEMC(var1);
/*     */   }
/*     */ 
/*     */   private ItemStack getNextFuel(ItemStack var1)
/*     */   {
/* 609 */     int var2 = var1.id;
/* 610 */     int var3 = var1.getData();
/*     */ 
/* 612 */     if (this.items[(this.items.length - 1)] == null)
/*     */     {
/* 614 */       if (EEMaps.isFuel(var1))
/*     */       {
/* 616 */         if ((var2 == Item.COAL.id) && (var3 == 1))
/*     */         {
/* 618 */           return new ItemStack(Item.REDSTONE.id, 1, 0);
/*     */         }
/*     */ 
/* 621 */         if (var2 == Item.REDSTONE.id)
/*     */         {
/* 623 */           return new ItemStack(Item.COAL.id, 1, 0);
/*     */         }
/*     */ 
/* 626 */         if (var2 == Item.COAL.id)
/*     */         {
/* 628 */           return new ItemStack(Item.SULPHUR.id, 1, 0);
/*     */         }
/*     */ 
/* 631 */         if (var2 == Item.SULPHUR.id)
/*     */         {
/* 633 */           return new ItemStack(Item.GLOWSTONE_DUST.id, 1, 0);
/*     */         }
/*     */ 
/* 636 */         if (var2 == Item.GLOWSTONE_DUST.id)
/*     */         {
/* 638 */           return new ItemStack(EEItem.alchemicalCoal.id, 1, 0);
/*     */         }
/*     */ 
/* 641 */         if (var2 == EEItem.alchemicalCoal.id)
/*     */         {
/* 643 */           return new ItemStack(Item.BLAZE_POWDER.id, 1, 0);
/*     */         }
/*     */ 
/* 646 */         if (var2 == Item.BLAZE_POWDER.id)
/*     */         {
/* 648 */           return new ItemStack(Block.GLOWSTONE.id, 1, 0);
/*     */         }
/*     */ 
/* 651 */         if (var2 == Block.GLOWSTONE.id)
/*     */         {
/* 653 */           return new ItemStack(EEItem.mobiusFuel.id, 1, 0);
/*     */         }
/*     */ 
/* 656 */         if (var2 == EEItem.mobiusFuel.id)
/*     */         {
/* 658 */           return new ItemStack(EEItem.aeternalisFuel.id, 1, 0);
/*     */         }
/*     */       }
/*     */ 
/* 662 */       return null;
/*     */     }
/* 664 */     if (EEMaps.isFuel(this.items[(this.items.length - 1)]))
/*     */     {
/* 666 */       return EEMaps.getEMC(var2, var3) < EEMaps.getEMC(this.items[(this.items.length - 1)].id, this.items[(this.items.length - 1)].getData()) ? this.items[(this.items.length - 1)] : null;
/*     */     }
/*     */ 
/* 670 */     EntityItem var4 = new EntityItem(this.world, this.x, this.y, this.z, this.items[(this.items.length - 1)].cloneItemStack());
/* 671 */     this.items[(this.items.length - 1)] = null;
/* 672 */     var4.pickupDelay = 10;
/* 673 */     this.world.addEntity(var4);
/* 674 */     return null;
/*     */   }
/*     */ 
/*     */   private boolean canCollect()
/*     */   {
/* 680 */     if (this.items[0] == null)
/*     */     {
/* 682 */       for (int var1 = 1; var1 <= this.items.length - 3; var1++)
/*     */       {
/* 684 */         if ((this.items[var1] != null) && ((this.items[(this.items.length - 1)] == null) || ((this.items[(this.items.length - 1)] != null) && (this.items[(this.items.length - 1)].doMaterialsMatch(this.items[var1])))))
/*     */         {
/* 686 */           this.items[0] = this.items[var1].cloneItemStack();
/* 687 */           this.items[var1] = null;
/* 688 */           break;
/*     */         }
/*     */       }
/*     */ 
/* 692 */       if (this.items[0] == null)
/*     */       {
/* 694 */         return false;
/*     */       }
/*     */     }
/*     */ 
/* 698 */     if (EEBase.isKleinStar(this.items[0].id))
/*     */     {
/* 700 */       return true;
/*     */     }
/* 702 */     if (getNextFuel(this.items[0]) == null)
/*     */     {
/* 704 */       return false;
/*     */     }
/*     */ 
/* 708 */     ItemStack var3 = getNextFuel(this.items[0]).cloneItemStack();
/*     */ 
/* 710 */     if (this.items[(this.items.length - 2)] == null)
/*     */     {
/* 712 */       return true;
/*     */     }
/*     */ 
/* 718 */     if (!this.items[(this.items.length - 2)].doMaterialsMatch(var3))
/*     */     {
/* 720 */       for (int var2 = 1; var2 <= this.items.length - 3; var2++)
/*     */       {
/* 722 */         if (this.items[var2] == null)
/*     */         {
/* 724 */           this.items[var2] = this.items[(this.items.length - 2)].cloneItemStack();
/* 725 */           this.items[(this.items.length - 2)] = null;
/* 726 */           return true;
/*     */         }
/*     */ 
/* 729 */         if (this.items[var2].doMaterialsMatch(this.items[(this.items.length - 2)]))
/*     */         {
/* 731 */           while ((this.items[(this.items.length - 2)] != null) && (this.items[var2].count < 64))
/*     */           {
/* 733 */             this.items[(this.items.length - 2)].count -= 1;
/* 734 */             this.items[var2].count += 1;
/*     */ 
/* 736 */             if (this.items[(this.items.length - 2)].count == 0)
/*     */             {
/* 738 */               this.items[(this.items.length - 2)] = null;
/* 739 */               return true;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 746 */     if ((this.items[(this.items.length - 2)] != null) && (!this.items[(this.items.length - 2)].doMaterialsMatch(var3)))
/*     */     {
/* 748 */       return false;
/*     */     }
/* 750 */     if ((this.items[(this.items.length - 2)].count < getMaxStackSize()) && (this.items[(this.items.length - 2)].count < this.items[(this.items.length - 2)].getMaxStackSize()))
/*     */     {
/* 752 */       return true;
/*     */     }
/*     */ 
/* 756 */     for (int var2 = 1; var2 <= this.items.length - 2; var2++)
/*     */     {
/* 758 */       if ((this.items[var2] != null) && ((this.items[var2].getItem().id == EEItem.mobiusFuel.id) || ((this.items[(this.items.length - 1)] != null) && (this.items[var2].doMaterialsMatch(this.items[(this.items.length - 1)])))) && (this.items[var2].count >= this.items[var2].getMaxStackSize()) && (tryDropInChest(new ItemStack(this.items[var2].getItem(), this.items[var2].count))))
/*     */       {
/* 760 */         this.items[var2] = null;
/*     */       }
/*     */     }
/*     */ 
/* 764 */     if (this.items[(this.items.length - 2)] == null)
/*     */     {
/* 766 */       return true;
/*     */     }
/*     */ 
/* 770 */     for (var2 = 1; var2 <= this.items.length - 3; var2++)
/*     */     {
/* 772 */       if (this.items[var2] == null)
/*     */       {
/* 774 */         this.items[var2] = this.items[(this.items.length - 2)].cloneItemStack();
/* 775 */         this.items[(this.items.length - 2)] = null;
/* 776 */         return true;
/*     */       }
/*     */ 
/* 779 */       if (this.items[var2].doMaterialsMatch(this.items[(this.items.length - 2)]))
/*     */       {
/* 781 */         while ((this.items[(this.items.length - 2)] != null) && (this.items[var2].count < 64))
/*     */         {
/* 783 */           this.items[(this.items.length - 2)].count -= 1;
/* 784 */           this.items[var2].count += 1;
/*     */ 
/* 786 */           if (this.items[(this.items.length - 2)].count == 0)
/*     */           {
/* 788 */             this.items[(this.items.length - 2)] = null;
/* 789 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 795 */     return this.items[(this.items.length - 2)].count < var3.getMaxStackSize();
/*     */   }
/*     */ 
/*     */   public void uptierFuel()
/*     */   {
/* 804 */     if (canCollect())
/*     */     {
/* 806 */       if (getNextFuel(this.items[0]) != null)
/*     */       {
/* 808 */         ItemStack var1 = getNextFuel(this.items[0]).cloneItemStack();
/* 809 */         var1.count = 1;
/*     */ 
/* 811 */         if (this.items[(this.items.length - 2)] == null)
/*     */         {
/* 813 */           if (((this.items[(this.items.length - 1)] == null) || (!var1.doMaterialsMatch(this.items[(this.items.length - 1)]))) && (var1.getItem() != EEItem.aeternalisFuel))
/*     */           {
/* 815 */             this.items[(this.items.length - 2)] = var1.cloneItemStack();
/*     */           }
/* 817 */           else if (!tryDropInChest(var1))
/*     */           {
/* 819 */             this.items[(this.items.length - 2)] = var1.cloneItemStack();
/*     */           }
/*     */         }
/* 822 */         else if (this.items[(this.items.length - 2)].id == var1.id)
/*     */         {
/* 824 */           if (this.items[(this.items.length - 2)].count == var1.getMaxStackSize())
/*     */           {
/* 826 */             if ((this.items[(this.items.length - 2)].getItem().id != EEItem.aeternalisFuel.id) && ((this.items[(this.items.length - 1)] == null) || (!this.items[(this.items.length - 2)].doMaterialsMatch(this.items[(this.items.length - 1)]))))
/*     */             {
/* 828 */               for (int var2 = 1; var2 <= this.items.length - 3; var2++)
/*     */               {
/* 830 */                 if (this.items[var2] == null)
/*     */                 {
/* 832 */                   this.items[var2] = this.items[(this.items.length - 2)].cloneItemStack();
/* 833 */                   this.items[(this.items.length - 2)] = null;
/* 834 */                   break;
/*     */                 }
/*     */ 
/* 837 */                 if (this.items[var2].doMaterialsMatch(this.items[(this.items.length - 2)]))
/*     */                 {
/* 839 */                   while ((this.items[var2].count < this.items[var2].getMaxStackSize()) && (this.items[(this.items.length - 2)] != null))
/*     */                   {
/* 841 */                     this.items[(this.items.length - 2)].count -= 1;
/* 842 */                     this.items[var2].count += 1;
/*     */ 
/* 844 */                     if (this.items[(this.items.length - 2)].count == 0)
/*     */                     {
/* 846 */                       this.items[(this.items.length - 2)] = null;
/*     */                     }
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/* 852 */             else if (tryDropInChest(this.items[(this.items.length - 2)].cloneItemStack()))
/*     */             {
/* 854 */               this.items[(this.items.length - 2)] = null;
/*     */             }
/*     */           }
/* 857 */           else if (((this.items[(this.items.length - 1)] == null) || (!var1.doMaterialsMatch(this.items[(this.items.length - 1)]))) && (var1.getItem() != EEItem.aeternalisFuel))
/*     */           {
/* 859 */             this.items[(this.items.length - 2)].count += var1.count;
/*     */           }
/* 861 */           else if (!tryDropInChest(var1))
/*     */           {
/* 863 */             this.items[(this.items.length - 2)].count += var1.count;
/*     */           }
/*     */         }
/* 866 */         else if (((this.items[(this.items.length - 1)] != null) && (var1.doMaterialsMatch(this.items[(this.items.length - 1)]))) || ((var1.getItem() == EEItem.aeternalisFuel) && (tryDropInChest(this.items[(this.items.length - 2)].cloneItemStack()))))
/*     */         {
/* 868 */           this.items[(this.items.length - 2)] = null;
/*     */         }
/*     */ 
/* 871 */         if (this.items[0].getItem().k())
/*     */         {
/* 873 */           this.items[0] = new ItemStack(this.items[0].getItem().j());
/*     */         }
/*     */         else
/*     */         {
/* 877 */           this.items[0].count -= 1;
/*     */         }
/*     */ 
/* 880 */         if (this.items[0].count <= 0)
/*     */         {
/* 882 */           this.items[0] = null;
/*     */         }
/*     */       }
/*     */     }
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
/*     */   public boolean a(EntityHuman var1) {
/* 897 */     return this.world.getTileEntity(this.x, this.y, this.z) == this;
/*     */   }
/*     */ 
/*     */   public int getStartInventorySide(int var1)
/*     */   {
/* 902 */     return var1 == 0 ? 0 : 1;
/*     */   }
/*     */ 
/*     */   public int getSizeInventorySide(int var1)
/*     */   {
/* 907 */     return var1 == 0 ? 1 : this.items.length - 2;
/*     */   }
/*     */ 
/*     */   public boolean onBlockActivated(EntityHuman var1)
/*     */   {
/* 912 */     if (!this.world.isStatic)
/*     */     {
/* 914 */       var1.openGui(mod_EE.getInstance(), GuiIds.COLLECTOR_1, this.world, this.x, this.y, this.z);
/*     */     }
/*     */ 
/* 917 */     return true;
/*     */   }
/*     */ 
/*     */   public int getTextureForSide(int var1)
/*     */   {
/* 922 */     if (var1 == 1)
/*     */     {
/* 924 */       return EEBase.collectorTop;
/*     */     }
/*     */ 
/* 928 */     byte var2 = this.direction;
/* 929 */     return var1 != var2 ? EEBase.collectorSide : EEBase.collectorFront;
/*     */   }
/*     */ 
/*     */   public int getInventoryTexture(int var1)
/*     */   {
/* 935 */     return var1 == 3 ? EEBase.collectorFront : var1 == 1 ? EEBase.collectorTop : EEBase.collectorSide;
/*     */   }
/*     */ 
/*     */   public int getLightValue()
/*     */   {
/* 940 */     return 7;
/*     */   }
/*     */ 
/*     */   public void onNeighborBlockChange(int var1)
/*     */   {
/*     */   }
/*     */ 
/*     */   public ItemStack splitWithoutUpdate(int var1)
/*     */   {
/* 951 */     return null;
/*     */   }
/*     */ 
/*     */   public ItemStack[] getContents()
/*     */   {
/* 956 */     return this.items;
/*     */   }
/*     */ 
/*     */   public void setMaxStackSize(int size)
/*     */   {
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.TileCollector
 * JD-Core Version:    0.6.2
 */