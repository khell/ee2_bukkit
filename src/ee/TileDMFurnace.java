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
/*     */ import net.minecraft.server.FurnaceRecipes;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.Material;
/*     */ import net.minecraft.server.ModLoader;
/*     */ import net.minecraft.server.NBTTagCompound;
/*     */ import net.minecraft.server.NBTTagList;
/*     */ import net.minecraft.server.TileEntity;
/*     */ import net.minecraft.server.TileEntityChest;
/*     */ import net.minecraft.server.World;
/*     */ import net.minecraft.server.mod_EE;
/*     */ 
/*     */ public class TileDMFurnace extends TileEE
/*     */   implements ISpecialInventory, ISidedInventory, IEEPowerNet
/*     */ {
/*  24 */   private ItemStack[] items = new ItemStack[19];
/*  25 */   public int furnaceBurnTime = 0;
/*  26 */   public int currentItemBurnTime = 0;
/*  27 */   public int furnaceCookTime = 0;
/*     */   public int nextinstack;
/*     */   public int nextoutstack;
/*  30 */   private float woftFactor = 1.0F;
/*     */ 
/*     */   public void onBlockRemoval()
/*     */   {
/*  34 */     for (int var1 = 0; var1 < getSize(); var1++)
/*     */     {
/*  36 */       ItemStack var2 = getItem(var1);
/*     */ 
/*  38 */       if (var2 != null)
/*     */       {
/*  40 */         float var3 = this.world.random.nextFloat() * 0.8F + 0.1F;
/*  41 */         float var4 = this.world.random.nextFloat() * 0.8F + 0.1F;
/*  42 */         float var5 = this.world.random.nextFloat() * 0.8F + 0.1F;
/*     */ 
/*  44 */         while (var2.count > 0)
/*     */         {
/*  46 */           int var6 = this.world.random.nextInt(21) + 10;
/*     */ 
/*  48 */           if (var6 > var2.count)
/*     */           {
/*  50 */             var6 = var2.count;
/*     */           }
/*     */ 
/*  53 */           var2.count -= var6;
/*  54 */           EntityItem var7 = new EntityItem(this.world, this.x + var3, this.y + var4, this.z + var5, new ItemStack(var2.id, var6, var2.getData()));
/*     */ 
/*  56 */           if (var7 != null)
/*     */           {
/*  58 */             float var8 = 0.05F;
/*  59 */             var7.motX = ((float)this.world.random.nextGaussian() * var8);
/*  60 */             var7.motY = ((float)this.world.random.nextGaussian() * var8 + 0.2F);
/*  61 */             var7.motZ = ((float)this.world.random.nextGaussian() * var8);
/*     */ 
/*  63 */             if ((var7.itemStack.getItem() instanceof ItemKleinStar))
/*     */             {
/*  65 */               ((ItemKleinStar)var7.itemStack.getItem()).setKleinPoints(var7.itemStack, ((ItemKleinStar)var2.getItem()).getKleinPoints(var2));
/*     */             }
/*     */ 
/*  68 */             this.world.addEntity(var7);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean isChest(TileEntity var1)
/*     */   {
/*  77 */     return ((var1 instanceof TileEntityChest)) || ((var1 instanceof TileAlchChest));
/*     */   }
/*     */ 
/*     */   public int getSize()
/*     */   {
/*  85 */     return this.items.length;
/*     */   }
/*     */ 
/*     */   public int getMaxStackSize()
/*     */   {
/*  94 */     return 64;
/*     */   }
/*     */ 
/*     */   public ItemStack getItem(int var1)
/*     */   {
/* 102 */     return this.items[var1];
/*     */   }
/*     */ 
/*     */   public ItemStack splitStack(int var1, int var2)
/*     */   {
/* 111 */     if (this.items[var1] != null)
/*     */     {
/* 115 */       if (this.items[var1].count <= var2)
/*     */       {
/* 117 */         ItemStack var3 = this.items[var1];
/* 118 */         this.items[var1] = null;
/* 119 */         return var3;
/*     */       }
/*     */ 
/* 123 */       ItemStack var3 = this.items[var1].a(var2);
/*     */ 
/* 125 */       if (this.items[var1].count == 0)
/*     */       {
/* 127 */         this.items[var1] = null;
/*     */       }
/*     */ 
/* 130 */       return var3;
/*     */     }
/*     */ 
/* 135 */     return null;
/*     */   }
/*     */ 
/*     */   public void setItem(int var1, ItemStack var2)
/*     */   {
/* 144 */     this.items[var1] = var2;
/*     */ 
/* 146 */     if ((var2 != null) && (var2.count > getMaxStackSize()))
/*     */     {
/* 148 */       var2.count = getMaxStackSize();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean addItem(ItemStack var1, boolean var2, Orientations var3)
/*     */   {
/* 154 */     switch (var3)
/*     */     {
/*     */     case Unknown:
/*     */     case XNeg:
/*     */     case XPos:
/*     */     case YNeg:
/*     */     case YPos:
/*     */     case ZNeg:
/*     */     case ZPos:
/* 164 */       if (var1 != null)
/*     */       {
/* 166 */         if ((getItemBurnTime(var1) > 0) && (var1.id != Block.LOG.id))
/*     */         {
/* 168 */           if (this.items[0] == null)
/*     */           {
/* 170 */             if (var2)
/*     */             {
/* 172 */               for (this.items[0] = var1.cloneItemStack(); var1.count > 0; var1.count -= 1);
/*     */             }
/*     */ 
/* 178 */             return true;
/*     */           }
/* 180 */           if ((this.items[0].doMaterialsMatch(var1)) && (this.items[0].count < this.items[0].getMaxStackSize()))
/*     */           {
/* 182 */             if (var2)
/*     */             {
/* 184 */               while ((this.items[0].count < this.items[0].getMaxStackSize()) && (var1.count > 0))
/*     */               {
/* 186 */                 this.items[0].count += 1;
/* 187 */                 var1.count -= 1;
/*     */               }
/*     */             }
/*     */ 
/* 191 */             return true;
/*     */           }
/*     */         }
/* 194 */         else if (FurnaceRecipes.getInstance().getSmeltingResult(var1) != null)
/*     */         {
/* 196 */           for (int var4 = 1; var4 <= 9; var4++)
/*     */           {
/* 198 */             if (this.items[var4] == null)
/*     */             {
/* 200 */               if (var2)
/*     */               {
/* 202 */                 for (this.items[var4] = var1.cloneItemStack(); var1.count > 0; var1.count -= 1);
/*     */               }
/*     */ 
/* 208 */               return true;
/*     */             }
/*     */ 
/* 211 */             if ((this.items[var4].doMaterialsMatch(var1)) && (this.items[var4].count < this.items[var4].getMaxStackSize()))
/*     */             {
/* 213 */               if (var2)
/*     */               {
/* 215 */                 while ((this.items[var4].count < this.items[var4].getMaxStackSize()) && (var1.count > 0))
/*     */                 {
/* 217 */                   this.items[var4].count += 1;
/* 218 */                   var1.count -= 1;
/*     */                 }
/*     */ 
/* 221 */                 if (var1.count != 0);
/*     */               }
/*     */               else
/*     */               {
/* 227 */                 return true;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       break;
/*     */     }
/* 234 */     return false;
/*     */   }
/*     */ 
/*     */   public ItemStack extractItem(boolean var1, Orientations var2)
/*     */   {
/* 240 */     switch (var2)
/*     */     {
/*     */     case Unknown:
/*     */     case XNeg:
/*     */     case XPos:
/*     */     case YNeg:
/*     */     case YPos:
/*     */     case ZNeg:
/*     */     case ZPos:
/* 250 */       for (int var3 = 10; var3 < this.items.length; var3++)
/*     */       {
/* 252 */         if (this.items[var3] != null)
/*     */         {
/* 254 */           ItemStack var4 = this.items[var3].cloneItemStack();
/* 255 */           var4.count = 1;
/*     */ 
/* 257 */           if (var1)
/*     */           {
/* 259 */             this.items[var3].count -= 1;
/*     */ 
/* 261 */             if (this.items[var3].count < 1)
/*     */             {
/* 263 */               this.items[var3] = null;
/*     */             }
/*     */           }
/*     */ 
/* 267 */           return var4;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 272 */     return null;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 281 */     return "DM Furnace";
/*     */   }
/*     */ 
/*     */   public void a(NBTTagCompound var1)
/*     */   {
/* 289 */     super.a(var1);
/* 290 */     NBTTagList var2 = var1.getList("Items");
/* 291 */     this.items = new ItemStack[getSize()];
/*     */ 
/* 293 */     for (int var3 = 0; var3 < var2.size(); var3++)
/*     */     {
/* 295 */       NBTTagCompound var4 = (NBTTagCompound)var2.get(var3);
/* 296 */       byte var5 = var4.getByte("Slot");
/*     */ 
/* 298 */       if ((var5 >= 0) && (var5 < this.items.length))
/*     */       {
/* 300 */         this.items[var5] = ItemStack.a(var4);
/*     */       }
/*     */     }
/*     */ 
/* 304 */     this.woftFactor = var1.getFloat("TimeFactor");
/* 305 */     this.furnaceBurnTime = var1.getInt("BurnTime");
/* 306 */     this.furnaceCookTime = var1.getShort("CookTime");
/* 307 */     this.currentItemBurnTime = getItemBurnTime(this.items[1]);
/*     */   }
/*     */ 
/*     */   public void b(NBTTagCompound var1)
/*     */   {
/* 315 */     super.b(var1);
/* 316 */     var1.setInt("BurnTime", this.furnaceBurnTime);
/* 317 */     var1.setShort("CookTime", (short)this.furnaceCookTime);
/* 318 */     var1.setFloat("TimeFactor", this.woftFactor);
/* 319 */     NBTTagList var2 = new NBTTagList();
/*     */ 
/* 321 */     for (int var3 = 0; var3 < this.items.length; var3++)
/*     */     {
/* 323 */       if (this.items[var3] != null)
/*     */       {
/* 325 */         NBTTagCompound var4 = new NBTTagCompound();
/* 326 */         var4.setByte("Slot", (byte)var3);
/* 327 */         this.items[var3].save(var4);
/* 328 */         var2.add(var4);
/*     */       }
/*     */     }
/*     */ 
/* 332 */     var1.set("Items", var2);
/*     */   }
/*     */ 
/*     */   public int getCookProgressScaled(int var1)
/*     */   {
/* 337 */     return this.furnaceCookTime * var1 / 10;
/*     */   }
/*     */ 
/*     */   public int getBurnTimeRemainingScaled(int var1)
/*     */   {
/* 342 */     if (this.currentItemBurnTime == 0)
/*     */     {
/* 344 */       this.currentItemBurnTime = 10;
/*     */     }
/*     */ 
/* 347 */     return this.furnaceBurnTime * var1 / this.currentItemBurnTime;
/*     */   }
/*     */ 
/*     */   public boolean isBurning()
/*     */   {
/* 352 */     return this.furnaceBurnTime > 0;
/*     */   }
/*     */ 
/*     */   public void q_()
/*     */   {
/* 361 */     if (!clientFail())
/*     */     {
/* 363 */       this.woftFactor = (EEBase.getPedestalFactor(this.world) * EEBase.getPlayerWatchFactor());
/* 364 */       boolean var1 = this.furnaceBurnTime > 0;
/* 365 */       boolean var2 = false;
/* 366 */       boolean var3 = false;
/*     */ 
/* 368 */       if (this.furnaceBurnTime > 0)
/*     */       {
/* 370 */         this.furnaceBurnTime = ((int)(this.furnaceBurnTime - (getWOFTReciprocal(this.woftFactor) >= 1.0F ? getWOFTReciprocal(this.woftFactor) : 1.0F)));
/*     */ 
/* 372 */         if (this.furnaceBurnTime <= 0)
/*     */         {
/* 374 */           this.furnaceBurnTime = 0;
/* 375 */           var3 = true;
/*     */         }
/*     */       }
/*     */ 
/* 379 */       if (!this.world.isStatic)
/*     */       {
/* 381 */         if ((this.furnaceBurnTime <= 0) && (canSmelt()))
/*     */         {
/* 383 */           this.currentItemBurnTime = (this.furnaceBurnTime = getItemBurnTime(this.items[0]) / 16);
/*     */ 
/* 385 */           if (this.furnaceBurnTime > 0)
/*     */           {
/* 387 */             var2 = true;
/* 388 */             var3 = true;
/*     */ 
/* 390 */             if ((this.items[0] != null) && (!EEBase.isKleinStar(this.items[0].id)))
/*     */             {
/* 392 */               if (this.items[0].getItem().k())
/*     */               {
/* 394 */                 this.items[0] = new ItemStack(this.items[0].getItem().j());
/*     */               }
/*     */               else
/*     */               {
/* 398 */                 this.items[0].count -= 1;
/*     */               }
/*     */ 
/* 401 */               if (this.items[0].count == 0)
/*     */               {
/* 403 */                 this.items[0] = null;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 409 */         if ((isBurning()) && (canSmelt()))
/*     */         {
/* 411 */           this.furnaceCookTime = ((int)(this.furnaceCookTime + (getWOFTReciprocal(this.woftFactor) >= 1.0F ? getWOFTReciprocal(this.woftFactor) : 1.0F)));
/*     */ 
/* 413 */           if (this.furnaceCookTime >= 9)
/*     */           {
/* 415 */             this.furnaceCookTime = 0;
/* 416 */             smeltItem();
/* 417 */             var2 = true;
/* 418 */             var3 = true;
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 423 */           for (int var4 = 11; var4 < 19; var4++)
/*     */           {
/* 425 */             if ((this.items[var4] != null) && (this.items[var4].count >= this.items[var4].getMaxStackSize()) && (tryDropInChest(new ItemStack(this.items[var4].getItem(), this.items[var4].count))))
/*     */             {
/* 427 */               this.items[var4] = null;
/*     */             }
/*     */           }
/*     */ 
/* 431 */           this.furnaceCookTime = 0;
/* 432 */           this.furnaceBurnTime = 0;
/*     */         }
/*     */       }
/*     */ 
/* 436 */       if (var2)
/*     */       {
/* 438 */         update();
/*     */       }
/*     */ 
/* 441 */       if (var3)
/*     */       {
/* 443 */         this.world.notify(this.x, this.y, this.z);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean canSmelt()
/*     */   {
/* 450 */     if (this.items[1] == null)
/*     */     {
/* 452 */       for (int var1 = 2; var1 <= 9; var1++)
/*     */       {
/* 454 */         if (this.items[var1] != null)
/*     */         {
/* 456 */           this.items[1] = this.items[var1].cloneItemStack();
/* 457 */           this.items[var1] = null;
/* 458 */           var1 = 10;
/*     */         }
/*     */       }
/*     */ 
/* 462 */       if (this.items[1] == null)
/*     */       {
/* 464 */         return false;
/*     */       }
/*     */     }
/*     */ 
/* 468 */     ItemStack var3 = FurnaceRecipes.getInstance().getSmeltingResult(this.items[1]);
/*     */ 
/* 470 */     if (var3 == null)
/*     */     {
/* 472 */       return false;
/*     */     }
/* 474 */     if (this.items[10] == null)
/*     */     {
/* 476 */       return true;
/*     */     }
/*     */ 
/* 483 */     if (!this.items[10].doMaterialsMatch(var3))
/*     */     {
/* 485 */       if (tryDropInChest(this.items[10].cloneItemStack()))
/*     */       {
/* 487 */         this.items[10] = null;
/* 488 */         return true;
/*     */       }
/*     */ 
/* 491 */       for (int var2 = 11; var2 <= 18; var2++)
/*     */       {
/* 493 */         if (this.items[var2] == null)
/*     */         {
/* 495 */           this.items[var2] = this.items[10].cloneItemStack();
/* 496 */           this.items[10] = null;
/* 497 */           boolean var4 = true;
/* 498 */           return true;
/*     */         }
/*     */ 
/* 501 */         if (this.items[var2].doMaterialsMatch(this.items[10]))
/*     */         {
/* 503 */           while ((this.items[10] != null) && (this.items[var2].count < 64))
/*     */           {
/* 505 */             this.items[10].count -= 1;
/* 506 */             this.items[var2].count += 1;
/*     */ 
/* 508 */             if (this.items[10].count == 0)
/*     */             {
/* 510 */               this.items[10] = null;
/* 511 */               boolean var4 = true;
/* 512 */               return true;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 519 */     if ((this.items[10].count < getMaxStackSize()) && (this.items[10].count < this.items[10].getMaxStackSize()))
/*     */     {
/* 521 */       return true;
/*     */     }
/*     */ 
/* 525 */     for (int var2 = 11; var2 < 19; var2++)
/*     */     {
/* 527 */       if ((this.items[var2] != null) && (this.items[var2].count >= this.items[var2].getMaxStackSize()) && (tryDropInChest(this.items[var2].cloneItemStack())))
/*     */       {
/* 529 */         this.items[var2] = null;
/*     */       }
/*     */     }
/*     */ 
/* 533 */     if (this.items[10] == null)
/*     */     {
/* 535 */       return true;
/*     */     }
/*     */ 
/* 539 */     for (var2 = 11; var2 <= 18; var2++)
/*     */     {
/* 541 */       if (this.items[var2] == null)
/*     */       {
/* 543 */         this.items[var2] = this.items[10].cloneItemStack();
/* 544 */         this.items[10] = null;
/* 545 */         boolean var4 = true;
/* 546 */         return true;
/*     */       }
/*     */ 
/* 549 */       if (this.items[var2].doMaterialsMatch(this.items[10]))
/*     */       {
/* 551 */         while ((this.items[10] != null) && (this.items[var2].count < 64))
/*     */         {
/* 553 */           this.items[10].count -= 1;
/* 554 */           this.items[var2].count += 1;
/*     */ 
/* 556 */           if (this.items[10].count == 0)
/*     */           {
/* 558 */             this.items[10] = null;
/* 559 */             boolean var4 = true;
/* 560 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 566 */     return this.items[10].count < var3.getMaxStackSize();
/*     */   }
/*     */ 
/*     */   public void smeltItem()
/*     */   {
/* 574 */     if (canSmelt())
/*     */     {
/* 576 */       ItemStack var1 = FurnaceRecipes.getInstance().getSmeltingResult(this.items[1]);
/* 577 */       boolean var2 = false;
/*     */ 
/* 579 */       if (this.items[10] == null)
/*     */       {
/* 581 */         this.items[10] = var1.cloneItemStack();
/*     */ 
/* 583 */         if ((this.world.random.nextInt(2) == 0) && (EEMaps.isOreBlock(this.items[1].id)))
/*     */         {
/* 585 */           this.items[10].count += 1;
/*     */         }
/*     */       }
/* 588 */       else if (this.items[10].id == var1.id)
/*     */       {
/* 590 */         this.items[10].count += var1.count;
/*     */ 
/* 592 */         if ((this.world.random.nextInt(2) == 0) && (EEMaps.isOreBlock(this.items[1].id)))
/*     */         {
/* 594 */           if (this.items[10].count < var1.getMaxStackSize())
/*     */           {
/* 596 */             this.items[10].count += 1;
/*     */           }
/*     */           else
/*     */           {
/* 600 */             var2 = true;
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 605 */       if (this.items[10].count == var1.getMaxStackSize())
/*     */       {
/* 607 */         if (tryDropInChest(this.items[10]))
/*     */         {
/* 609 */           this.items[10] = null;
/*     */ 
/* 611 */           if (var2)
/*     */           {
/* 613 */             this.items[10] = var1.cloneItemStack();
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 618 */           for (int var3 = 11; var3 <= 18; var3++)
/*     */           {
/* 620 */             if (this.items[var3] == null)
/*     */             {
/* 622 */               this.items[var3] = this.items[10].cloneItemStack();
/* 623 */               this.items[10] = null;
/*     */ 
/* 625 */               if (var2)
/*     */               {
/* 627 */                 this.items[10] = var1.cloneItemStack();
/*     */               }
/*     */ 
/* 630 */               var3 = 19;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 636 */       if (this.items[1].getItem().k())
/*     */       {
/* 638 */         this.items[1] = new ItemStack(this.items[1].getItem().j());
/*     */       }
/*     */       else
/*     */       {
/* 642 */         this.items[1].count -= 1;
/*     */       }
/*     */ 
/* 645 */       if (this.items[1].count < 1)
/*     */       {
/* 647 */         this.items[1] = null;
/*     */       }
/*     */ 
/* 650 */       this.world.notify(this.x, this.y, this.z);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getItemBurnTime(ItemStack var1)
/*     */   {
/* 656 */     if (var1 == null)
/*     */     {
/* 658 */       return 0;
/*     */     }
/*     */ 
/* 662 */     int var2 = var1.getItem().id;
/*     */ 
/* 664 */     if ((EEBase.isKleinStar(var2)) && (EEBase.takeKleinStarPoints(var1, 32, this.world)))
/*     */     {
/* 666 */       return 1600;
/*     */     }
/*     */ 
/* 670 */     int var3 = var1.getData();
/* 671 */     return var2 == EEItem.aeternalisFuel.id ? 409600 : var2 == EEItem.mobiusFuel.id ? 102400 : var2 == EEItem.alchemicalCoal.id ? 25600 : var2 == Block.SAPLING.id ? 100 : var2 == Item.LAVA_BUCKET.id ? 3200 : var2 == Item.COAL.id ? 6400 : var2 == Item.STICK.id ? 100 : (var2 < 256) && (Block.byId[var2].material == Material.WOOD) ? 300 : ModLoader.addAllFuel(var2, var3);
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
/* 685 */     return this.world.getTileEntity(this.x, this.y, this.z) == this;
/*     */   }
/*     */ 
/*     */   public int getStartInventorySide(int var1)
/*     */   {
/* 690 */     return var1 == 1 ? 1 : var1 == 0 ? 0 : 10;
/*     */   }
/*     */ 
/*     */   public int getSizeInventorySide(int var1)
/*     */   {
/* 695 */     return var1 == 1 ? 9 : var1 == 0 ? 1 : 9;
/*     */   }
/*     */ 
/*     */   public boolean onBlockActivated(EntityHuman var1)
/*     */   {
/* 700 */     if (!this.world.isStatic)
/*     */     {
/* 702 */       var1.openGui(mod_EE.getInstance(), GuiIds.DM_FURNACE, this.world, this.x, this.y, this.z);
/*     */     }
/*     */ 
/* 705 */     return true;
/*     */   }
/*     */ 
/*     */   public int getTextureForSide(int var1)
/*     */   {
/* 710 */     byte var2 = this.direction;
/* 711 */     return var1 == var2 ? EEBase.dmFurnaceFront : EEBase.dmBlockSide;
/*     */   }
/*     */ 
/*     */   public int getInventoryTexture(int var1)
/*     */   {
/* 716 */     return var1 == 3 ? EEBase.dmFurnaceFront : EEBase.dmBlockSide;
/*     */   }
/*     */ 
/*     */   public int getLightValue()
/*     */   {
/* 721 */     return isBurning() ? 15 : 0;
/*     */   }
/*     */ 
/*     */   public void randomDisplayTick(Random var1)
/*     */   {
/* 726 */     if (isBurning())
/*     */     {
/* 728 */       byte var2 = this.direction;
/* 729 */       float var3 = this.x + 0.5F;
/* 730 */       float var4 = this.y + 0.0F + var1.nextFloat() * 6.0F / 16.0F;
/* 731 */       float var5 = this.z + 0.5F;
/* 732 */       float var6 = 0.52F;
/* 733 */       float var7 = var1.nextFloat() * 0.6F - 0.3F;
/*     */ 
/* 735 */       if (var2 == 4)
/*     */       {
/* 737 */         this.world.a("smoke", var3 - var6, var4, var5 + var7, 0.0D, 0.0D, 0.0D);
/* 738 */         this.world.a("flame", var3 - var6, var4, var5 + var7, 0.0D, 0.0D, 0.0D);
/*     */       }
/* 740 */       else if (var2 == 5)
/*     */       {
/* 742 */         this.world.a("smoke", var3 + var6, var4, var5 + var7, 0.0D, 0.0D, 0.0D);
/* 743 */         this.world.a("flame", var3 + var6, var4, var5 + var7, 0.0D, 0.0D, 0.0D);
/*     */       }
/* 745 */       else if (var2 == 2)
/*     */       {
/* 747 */         this.world.a("smoke", var3 + var7, var4, var5 - var6, 0.0D, 0.0D, 0.0D);
/* 748 */         this.world.a("flame", var3 + var7, var4, var5 - var6, 0.0D, 0.0D, 0.0D);
/*     */       }
/* 750 */       else if (var2 == 3)
/*     */       {
/* 752 */         this.world.a("smoke", var3 + var7, var4, var5 + var6, 0.0D, 0.0D, 0.0D);
/* 753 */         this.world.a("flame", var3 + var7, var4, var5 + var6, 0.0D, 0.0D, 0.0D);
/*     */       }
/*     */ 
/* 756 */       for (int var8 = 0; var8 < 4; var8++)
/*     */       {
/* 758 */         double var9 = this.x + var1.nextFloat();
/* 759 */         double var11 = this.y + var1.nextFloat();
/* 760 */         double var13 = this.z + var1.nextFloat();
/* 761 */         double var15 = 0.0D;
/* 762 */         double var17 = 0.0D;
/* 763 */         double var19 = 0.0D;
/* 764 */         int var21 = var1.nextInt(2) * 2 - 1;
/* 765 */         var15 = (var1.nextFloat() - 0.5D) * 0.5D;
/* 766 */         var17 = (var1.nextFloat() - 0.5D) * 0.5D;
/* 767 */         var19 = (var1.nextFloat() - 0.5D) * 0.5D;
/*     */ 
/* 769 */         if (((this.world.getTypeId(this.x - 1, this.y, this.z) != EEBlock.eeStone.id) || (this.world.getData(this.x - 1, this.y, this.z) != 3)) && ((this.world.getTypeId(this.x + 1, this.y, this.z) != EEBlock.eeStone.id) || (this.world.getData(this.x + 1, this.y, this.z) != 3)))
/*     */         {
/* 771 */           var9 = this.x + 0.5D + 0.25D * var21;
/* 772 */           var15 = var1.nextFloat() * 2.0F * var21;
/*     */         }
/*     */         else
/*     */         {
/* 776 */           var13 = this.z + 0.5D + 0.25D * var21;
/* 777 */           var19 = var1.nextFloat() * 2.0F * var21;
/*     */         }
/*     */ 
/* 780 */         this.world.a("portal", var9, var11, var13, var15, var17, var19);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean receiveEnergy(int var1, byte var2, boolean var3)
/*     */   {
/* 787 */     if (canSmelt())
/*     */     {
/* 789 */       if (var3)
/*     */       {
/* 791 */         this.furnaceBurnTime += var1;
/*     */       }
/*     */ 
/* 794 */       return true;
/*     */     }
/*     */ 
/* 798 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean sendEnergy(int var1, byte var2, boolean var3)
/*     */   {
/* 804 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean passEnergy(int var1, byte var2, boolean var3)
/*     */   {
/* 809 */     return false;
/*     */   }
/*     */ 
/*     */   public void sendAllPackets(int var1) {
/*     */   }
/*     */ 
/*     */   public int relayBonus() {
/* 816 */     return 0;
/*     */   }
/*     */ 
/*     */   public ItemStack splitWithoutUpdate(int var1)
/*     */   {
/* 825 */     return null;
/*     */   }
/*     */ 
/*     */   public ItemStack[] getContents()
/*     */   {
/* 830 */     return this.items;
/*     */   }
/*     */ 
/*     */   public void setMaxStackSize(int size)
/*     */   {
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.TileDMFurnace
 * JD-Core Version:    0.6.2
 */