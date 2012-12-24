/*     */ package ee;
/*     */ 
/*     */ import buildcraft.api.ISpecialInventory;
/*     */ import buildcraft.api.Orientations;
/*     */ import ee.core.GuiIds;
/*     */ import forge.ISidedInventory;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.EEProxy;
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
/*     */ public class TileRMFurnace extends TileEE
/*     */   implements ISpecialInventory, ISidedInventory, IEEPowerNet
/*     */ {
/*  25 */   private ItemStack[] items = new ItemStack[27];
/*  26 */   public int furnaceBurnTime = 0;
/*  27 */   public int currentItemBurnTime = 0;
/*  28 */   public int furnaceCookTime = 0;
/*     */   public int nextinstack;
/*     */   public int nextoutstack;
/*  31 */   private float woftFactor = 1.0F;
/*     */ 
/*     */   private boolean isChest(TileEntity var1)
/*     */   {
/*  35 */     return ((var1 instanceof TileEntityChest)) || ((var1 instanceof TileAlchChest));
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
/*     */   public int getSize()
/*     */   {
/*  86 */     return this.items.length;
/*     */   }
/*     */ 
/*     */   public int getMaxStackSize()
/*     */   {
/*  95 */     return 64;
/*     */   }
/*     */ 
/*     */   public ItemStack getItem(int var1)
/*     */   {
/* 103 */     return this.items[var1];
/*     */   }
/*     */ 
/*     */   public ItemStack splitStack(int var1, int var2)
/*     */   {
/* 112 */     if (this.items[var1] != null)
/*     */     {
/* 116 */       if (this.items[var1].count <= var2)
/*     */       {
/* 118 */         ItemStack var3 = this.items[var1];
/* 119 */         this.items[var1] = null;
/* 120 */         return var3;
/*     */       }
/*     */ 
/* 124 */       ItemStack var3 = this.items[var1].a(var2);
/*     */ 
/* 126 */       if (this.items[var1].count == 0)
/*     */       {
/* 128 */         this.items[var1] = null;
/*     */       }
/*     */ 
/* 131 */       return var3;
/*     */     }
/*     */ 
/* 136 */     return null;
/*     */   }
/*     */ 
/*     */   public void setItem(int var1, ItemStack var2)
/*     */   {
/* 145 */     this.items[var1] = var2;
/*     */ 
/* 147 */     if ((var2 != null) && (var2.count > getMaxStackSize()))
/*     */     {
/* 149 */       var2.count = getMaxStackSize();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean addItem(ItemStack var1, boolean var2, Orientations var3)
/*     */   {
/* 155 */     switch (var3)
/*     */     {
/*     */     case Unknown:
/*     */     case XNeg:
/*     */     case XPos:
/*     */     case YNeg:
/*     */     case YPos:
/*     */     case ZNeg:
/*     */     case ZPos:
/* 165 */       if (var1 != null)
/*     */       {
/* 167 */         if ((getItemBurnTime(var1, true) > 0) && (var1.id != Block.LOG.id))
/*     */         {
/* 169 */           if (this.items[0] == null)
/*     */           {
/* 171 */             if (var2)
/*     */             {
/* 173 */               for (this.items[0] = var1.cloneItemStack(); var1.count > 0; var1.count -= 1);
/*     */             }
/*     */ 
/* 179 */             return true;
/*     */           }
/* 181 */           if ((this.items[0].doMaterialsMatch(var1)) && (this.items[0].count < this.items[0].getMaxStackSize()))
/*     */           {
/* 183 */             if (var2)
/*     */             {
/* 185 */               while ((this.items[0].count < this.items[0].getMaxStackSize()) && (var1.count > 0))
/*     */               {
/* 187 */                 this.items[0].count += 1;
/* 188 */                 var1.count -= 1;
/*     */               }
/*     */             }
/*     */ 
/* 192 */             return true;
/*     */           }
/*     */         }
/* 195 */         else if (FurnaceRecipes.getInstance().getSmeltingResult(var1) != null)
/*     */         {
/* 197 */           for (int var4 = 1; var4 <= 13; var4++)
/*     */           {
/* 199 */             if (this.items[var4] == null)
/*     */             {
/* 201 */               if (var2)
/*     */               {
/* 203 */                 for (this.items[var4] = var1.cloneItemStack(); var1.count > 0; var1.count -= 1);
/*     */               }
/*     */ 
/* 209 */               return true;
/*     */             }
/*     */ 
/* 212 */             if ((this.items[var4].doMaterialsMatch(var1)) && (this.items[var4].count < this.items[var4].getMaxStackSize()))
/*     */             {
/* 214 */               if (var2)
/*     */               {
/* 216 */                 while ((this.items[var4].count < this.items[var4].getMaxStackSize()) && (var1.count > 0))
/*     */                 {
/* 218 */                   this.items[var4].count += 1;
/* 219 */                   var1.count -= 1;
/*     */                 }
/*     */ 
/* 222 */                 if (var1.count != 0);
/*     */               }
/*     */               else
/*     */               {
/* 228 */                 return true;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       break;
/*     */     }
/* 235 */     return false;
/*     */   }
/*     */ 
/*     */   public ItemStack extractItem(boolean var1, Orientations var2)
/*     */   {
/* 241 */     switch (var2)
/*     */     {
/*     */     case XNeg:
/* 245 */       if (this.items[0] == null)
/*     */       {
/* 247 */         return null;
/*     */       }
/* 249 */       if ((this.items[0].getItem() instanceof ItemKleinStar))
/*     */       {
/* 251 */         ItemStack var5 = this.items[0].cloneItemStack();
/*     */ 
/* 253 */         if (var1)
/*     */         {
/* 255 */           this.items[0] = null;
/*     */         }
/*     */ 
/* 258 */         return var5;
/*     */       }
/*     */ 
/*     */     case Unknown:
/*     */     case XPos:
/*     */     case YNeg:
/*     */     case YPos:
/*     */     case ZNeg:
/*     */     case ZPos:
/* 268 */       for (int var3 = 10; var3 < this.items.length; var3++)
/*     */       {
/* 270 */         if (this.items[var3] != null)
/*     */         {
/* 272 */           ItemStack var4 = this.items[var3].cloneItemStack();
/* 273 */           var4.count = 1;
/*     */ 
/* 275 */           if (var1)
/*     */           {
/* 277 */             this.items[var3].count -= 1;
/*     */ 
/* 279 */             if (this.items[var3].count < 1)
/*     */             {
/* 281 */               this.items[var3] = null;
/*     */             }
/*     */           }
/*     */ 
/* 285 */           return var4;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 290 */     return null;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 299 */     return "RM Furnace";
/*     */   }
/*     */ 
/*     */   public void a(NBTTagCompound var1)
/*     */   {
/* 307 */     super.a(var1);
/* 308 */     NBTTagList var2 = var1.getList("Items");
/* 309 */     this.items = new ItemStack[getSize()];
/*     */ 
/* 311 */     for (int var3 = 0; var3 < var2.size(); var3++)
/*     */     {
/* 313 */       NBTTagCompound var4 = (NBTTagCompound)var2.get(var3);
/* 314 */       byte var5 = var4.getByte("Slot");
/*     */ 
/* 316 */       if ((var5 >= 0) && (var5 < this.items.length))
/*     */       {
/* 318 */         this.items[var5] = ItemStack.a(var4);
/*     */       }
/*     */     }
/*     */ 
/* 322 */     this.woftFactor = var1.getFloat("TimeFactor");
/* 323 */     this.furnaceBurnTime = var1.getInt("BurnTime");
/* 324 */     this.furnaceCookTime = var1.getShort("CookTime");
/* 325 */     this.currentItemBurnTime = getItemBurnTime(this.items[1], false);
/*     */   }
/*     */ 
/*     */   public void b(NBTTagCompound var1)
/*     */   {
/* 333 */     super.b(var1);
/* 334 */     var1.setInt("BurnTime", this.furnaceBurnTime);
/* 335 */     var1.setShort("CookTime", (short)this.furnaceCookTime);
/* 336 */     var1.setFloat("TimeFactor", this.woftFactor);
/* 337 */     NBTTagList var2 = new NBTTagList();
/*     */ 
/* 339 */     for (int var3 = 0; var3 < this.items.length; var3++)
/*     */     {
/* 341 */       if (this.items[var3] != null)
/*     */       {
/* 343 */         NBTTagCompound var4 = new NBTTagCompound();
/* 344 */         var4.setByte("Slot", (byte)var3);
/* 345 */         this.items[var3].save(var4);
/* 346 */         var2.add(var4);
/*     */       }
/*     */     }
/*     */ 
/* 350 */     var1.set("Items", var2);
/*     */   }
/*     */ 
/*     */   public int getCookProgressScaled(int var1)
/*     */   {
/* 355 */     return (this.world != null) && (!EEProxy.isClient(this.world)) ? (this.furnaceCookTime + ((isBurning()) && (canSmelt()) ? 1 : 0)) * var1 / 3 : 0;
/*     */   }
/*     */ 
/*     */   public int getBurnTimeRemainingScaled(int var1)
/*     */   {
/* 360 */     if (this.currentItemBurnTime == 0)
/*     */     {
/* 362 */       this.currentItemBurnTime = 10;
/*     */     }
/*     */ 
/* 365 */     return this.furnaceBurnTime * var1 / this.currentItemBurnTime;
/*     */   }
/*     */ 
/*     */   public boolean isBurning()
/*     */   {
/* 370 */     return this.furnaceBurnTime > 0;
/*     */   }
/*     */ 
/*     */   public void q_()
/*     */   {
/* 379 */     if (!clientFail())
/*     */     {
/* 381 */       this.woftFactor = (EEBase.getPedestalFactor(this.world) * EEBase.getPlayerWatchFactor());
/* 382 */       boolean var1 = this.furnaceBurnTime > 0;
/* 383 */       boolean var2 = false;
/* 384 */       boolean var3 = false;
/*     */ 
/* 386 */       if (this.furnaceBurnTime > 0)
/*     */       {
/* 388 */         this.furnaceBurnTime = ((int)(this.furnaceBurnTime - (getWOFTReciprocal(this.woftFactor) >= 1.0F ? getWOFTReciprocal(this.woftFactor) : 1.0F)));
/*     */ 
/* 390 */         if (this.furnaceBurnTime <= 0)
/*     */         {
/* 392 */           this.furnaceBurnTime = 0;
/* 393 */           var3 = true;
/*     */         }
/*     */       }
/*     */ 
/* 397 */       if (!this.world.isStatic)
/*     */       {
/* 399 */         if ((this.furnaceBurnTime <= 0) && (canSmelt()))
/*     */         {
/* 401 */           this.currentItemBurnTime = (this.furnaceBurnTime = getItemBurnTime(this.items[0], false) / 48);
/*     */ 
/* 403 */           if (this.furnaceBurnTime > 0)
/*     */           {
/* 405 */             var2 = true;
/* 406 */             var3 = true;
/*     */ 
/* 408 */             if ((this.items[0] != null) && (!EEBase.isKleinStar(this.items[0].id)))
/*     */             {
/* 410 */               if (this.items[0].getItem().k())
/*     */               {
/* 412 */                 this.items[0] = new ItemStack(this.items[0].getItem().j());
/*     */               }
/*     */               else
/*     */               {
/* 416 */                 this.items[0].count -= 1;
/*     */               }
/*     */ 
/* 419 */               if (this.items[0].count == 0)
/*     */               {
/* 421 */                 this.items[0] = null;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 427 */         if ((isBurning()) && (canSmelt()))
/*     */         {
/* 429 */           this.furnaceCookTime = ((int)(this.furnaceCookTime + (getWOFTReciprocal(this.woftFactor) >= 1.0F ? getWOFTReciprocal(this.woftFactor) : 1.0F)));
/*     */           do {
/* 431 */             this.furnaceCookTime -= 3;
/* 432 */             smeltItem();
/* 433 */             var2 = true;
/*     */ 
/* 429 */             var3 = true; if (this.furnaceCookTime < 3) break;  } while (canSmelt());
/*     */         }
/*     */         else
/*     */         {
/* 438 */           for (int var4 = 15; var4 < 27; var4++)
/*     */           {
/* 440 */             if ((this.items[var4] != null) && (this.items[var4].count >= this.items[var4].getMaxStackSize()) && (tryDropInChest(new ItemStack(this.items[var4].getItem(), this.items[var4].count))))
/*     */             {
/* 442 */               this.items[var4] = null;
/*     */             }
/*     */           }
/*     */ 
/* 446 */           this.furnaceCookTime = 0;
/* 447 */           this.furnaceBurnTime = 0;
/*     */         }
/*     */       }
/*     */ 
/* 451 */       if (var2)
/*     */       {
/* 453 */         update();
/*     */       }
/*     */ 
/* 456 */       if (var3)
/*     */       {
/* 458 */         this.world.notify(this.x, this.y, this.z);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean canSmelt()
/*     */   {
/* 465 */     if (this.items[1] == null)
/*     */     {
/* 467 */       for (int var1 = 2; var1 <= 13; var1++)
/*     */       {
/* 469 */         if (this.items[var1] != null)
/*     */         {
/* 471 */           this.items[1] = this.items[var1].cloneItemStack();
/* 472 */           this.items[var1] = null;
/* 473 */           break;
/*     */         }
/*     */       }
/*     */ 
/* 477 */       if (this.items[1] == null)
/*     */       {
/* 479 */         return false;
/*     */       }
/*     */     }
/*     */ 
/* 483 */     ItemStack var3 = FurnaceRecipes.getInstance().getSmeltingResult(this.items[1]);
/*     */ 
/* 485 */     if (var3 == null)
/*     */     {
/* 487 */       return false;
/*     */     }
/* 489 */     if (this.items[14] == null)
/*     */     {
/* 491 */       return true;
/*     */     }
/*     */ 
/* 497 */     if (!this.items[14].doMaterialsMatch(var3))
/*     */     {
/* 499 */       if (tryDropInChest(this.items[14].cloneItemStack()))
/*     */       {
/* 501 */         this.items[14] = null;
/* 502 */         return true;
/*     */       }
/*     */ 
/* 505 */       for (int var2 = 15; var2 <= 26; var2++)
/*     */       {
/* 507 */         if (this.items[var2] == null)
/*     */         {
/* 509 */           this.items[var2] = this.items[14].cloneItemStack();
/* 510 */           this.items[14] = null;
/* 511 */           return true;
/*     */         }
/*     */ 
/* 514 */         if (this.items[var2].doMaterialsMatch(this.items[14]))
/*     */         {
/* 516 */           while ((this.items[14] != null) && (this.items[var2].count < 64))
/*     */           {
/* 518 */             this.items[14].count -= 1;
/* 519 */             this.items[var2].count += 1;
/*     */ 
/* 521 */             if (this.items[14].count == 0)
/*     */             {
/* 523 */               this.items[14] = null;
/* 524 */               return true;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 531 */     if ((this.items[14].count < getMaxStackSize()) && (this.items[14].count < this.items[14].getMaxStackSize()))
/*     */     {
/* 533 */       return true;
/*     */     }
/*     */ 
/* 537 */     for (int var2 = 15; var2 < 27; var2++)
/*     */     {
/* 539 */       if ((this.items[var2] != null) && (this.items[var2].count >= this.items[var2].getMaxStackSize()) && (tryDropInChest(this.items[var2].cloneItemStack())))
/*     */       {
/* 541 */         this.items[var2] = null;
/*     */       }
/*     */     }
/*     */ 
/* 545 */     if (this.items[14] == null)
/*     */     {
/* 547 */       return true;
/*     */     }
/*     */ 
/* 551 */     for (var2 = 15; var2 <= 26; var2++)
/*     */     {
/* 553 */       if (this.items[var2] == null)
/*     */       {
/* 555 */         this.items[var2] = this.items[14].cloneItemStack();
/* 556 */         this.items[14] = null;
/* 557 */         return true;
/*     */       }
/*     */ 
/* 560 */       if (this.items[var2].doMaterialsMatch(this.items[14]))
/*     */       {
/* 562 */         while ((this.items[14] != null) && (this.items[var2].count < 64))
/*     */         {
/* 564 */           this.items[14].count -= 1;
/* 565 */           this.items[var2].count += 1;
/*     */ 
/* 567 */           if (this.items[14].count == 0)
/*     */           {
/* 569 */             this.items[14] = null;
/* 570 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 576 */     return this.items[14].count < var3.getMaxStackSize();
/*     */   }
/*     */ 
/*     */   public void smeltItem()
/*     */   {
/* 584 */     if (canSmelt())
/*     */     {
/* 586 */       ItemStack var1 = FurnaceRecipes.getInstance().getSmeltingResult(this.items[1]);
/* 587 */       boolean var2 = false;
/*     */ 
/* 589 */       if (this.items[14] == null)
/*     */       {
/* 591 */         this.items[14] = var1.cloneItemStack();
/*     */ 
/* 593 */         if (EEMaps.isOreBlock(this.items[1].id))
/*     */         {
/* 595 */           this.items[14].count += 1;
/*     */         }
/*     */       }
/* 598 */       else if (this.items[14].id == var1.id)
/*     */       {
/* 600 */         this.items[14].count += var1.count;
/*     */ 
/* 602 */         if (EEMaps.isOreBlock(this.items[1].id))
/*     */         {
/* 604 */           if (this.items[14].count < var1.getMaxStackSize())
/*     */           {
/* 606 */             this.items[14].count += 1;
/*     */           }
/*     */           else
/*     */           {
/* 610 */             var2 = true;
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 615 */       if (this.items[14].count == var1.getMaxStackSize())
/*     */       {
/* 617 */         if (tryDropInChest(this.items[14]))
/*     */         {
/* 619 */           this.items[14] = null;
/*     */ 
/* 621 */           if (var2)
/*     */           {
/* 623 */             this.items[14] = var1.cloneItemStack();
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 628 */           for (int var3 = 15; var3 <= 26; var3++)
/*     */           {
/* 630 */             if (this.items[var3] == null)
/*     */             {
/* 632 */               this.items[var3] = this.items[14].cloneItemStack();
/* 633 */               this.items[14] = null;
/*     */ 
/* 635 */               if (!var2)
/*     */                 break;
/* 637 */               this.items[14] = var1.cloneItemStack();
/*     */ 
/* 640 */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 646 */       if (this.items[1].getItem().k())
/*     */       {
/* 648 */         this.items[1] = new ItemStack(this.items[1].getItem().j());
/*     */       }
/*     */       else
/*     */       {
/* 652 */         this.items[1].count -= 1;
/*     */       }
/*     */ 
/* 655 */       if (this.items[1].count < 1)
/*     */       {
/* 657 */         this.items[1] = null;
/*     */       }
/*     */ 
/* 660 */       this.world.notify(this.x, this.y, this.z);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getItemBurnTime(ItemStack var1, boolean var2)
/*     */   {
/* 666 */     if (var1 == null)
/*     */     {
/* 668 */       return 0;
/*     */     }
/*     */ 
/* 672 */     int var3 = var1.getItem().id;
/*     */ 
/* 674 */     if (EEBase.isKleinStar(var3)) if (EEBase.takeKleinStarPoints(var1, var2 ? 0 : 32, this.world))
/*     */       {
/* 676 */         return 1600;
/*     */       }
/*     */ 
/*     */ 
/* 680 */     int var4 = var1.getData();
/* 681 */     return var3 == EEItem.aeternalisFuel.id ? 409600 : var3 == EEItem.mobiusFuel.id ? 102400 : var3 == EEItem.alchemicalCoal.id ? 25600 : var3 == Block.SAPLING.id ? 100 : var3 == Item.LAVA_BUCKET.id ? 3200 : var3 == Item.COAL.id ? 6400 : var3 == Item.STICK.id ? 100 : (var3 < 256) && (Block.byId[var3].material == Material.WOOD) ? 300 : ModLoader.addAllFuel(var3, var4);
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
/* 695 */     return this.world.getTileEntity(this.x, this.y, this.z) == this;
/*     */   }
/*     */ 
/*     */   public int getStartInventorySide(int var1)
/*     */   {
/* 700 */     return var1 == 1 ? 0 : 1;
/*     */   }
/*     */ 
/*     */   public int getSizeInventorySide(int var1)
/*     */   {
/* 705 */     return var1 == 1 ? 1 : 26;
/*     */   }
/*     */ 
/*     */   public boolean onBlockActivated(EntityHuman var1)
/*     */   {
/* 710 */     if (!this.world.isStatic)
/*     */     {
/* 712 */       var1.openGui(mod_EE.getInstance(), GuiIds.RM_FURNACE, this.world, this.x, this.y, this.z);
/*     */     }
/*     */ 
/* 715 */     return true;
/*     */   }
/*     */ 
/*     */   public int getTextureForSide(int var1)
/*     */   {
/* 720 */     byte var2 = this.direction;
/* 721 */     return var1 == var2 ? EEBase.rmFurnaceFront : EEBase.rmBlockSide;
/*     */   }
/*     */ 
/*     */   public int getInventoryTexture(int var1)
/*     */   {
/* 726 */     return var1 == 3 ? EEBase.rmFurnaceFront : EEBase.rmBlockSide;
/*     */   }
/*     */ 
/*     */   public int getLightValue()
/*     */   {
/* 731 */     return isBurning() ? 15 : 0;
/*     */   }
/*     */ 
/*     */   public void randomDisplayTick(Random var1)
/*     */   {
/* 736 */     if (isBurning())
/*     */     {
/* 738 */       byte var2 = this.direction;
/* 739 */       float var3 = this.x + 0.5F;
/* 740 */       float var4 = this.y + 0.0F + var1.nextFloat() * 6.0F / 16.0F;
/* 741 */       float var5 = this.z + 0.5F;
/* 742 */       float var6 = 0.52F;
/* 743 */       float var7 = var1.nextFloat() * 0.6F - 0.3F;
/*     */ 
/* 745 */       if (var2 == 4)
/*     */       {
/* 747 */         this.world.a("smoke", var3 - var6, var4, var5 + var7, 0.0D, 0.0D, 0.0D);
/* 748 */         this.world.a("flame", var3 - var6, var4, var5 + var7, 0.0D, 0.0D, 0.0D);
/*     */       }
/* 750 */       else if (var2 == 5)
/*     */       {
/* 752 */         this.world.a("smoke", var3 + var6, var4, var5 + var7, 0.0D, 0.0D, 0.0D);
/* 753 */         this.world.a("flame", var3 + var6, var4, var5 + var7, 0.0D, 0.0D, 0.0D);
/*     */       }
/* 755 */       else if (var2 == 2)
/*     */       {
/* 757 */         this.world.a("smoke", var3 + var7, var4, var5 - var6, 0.0D, 0.0D, 0.0D);
/* 758 */         this.world.a("flame", var3 + var7, var4, var5 - var6, 0.0D, 0.0D, 0.0D);
/*     */       }
/* 760 */       else if (var2 == 3)
/*     */       {
/* 762 */         this.world.a("smoke", var3 + var7, var4, var5 + var6, 0.0D, 0.0D, 0.0D);
/* 763 */         this.world.a("flame", var3 + var7, var4, var5 + var6, 0.0D, 0.0D, 0.0D);
/*     */       }
/*     */ 
/* 766 */       for (int var8 = 0; var8 < 4; var8++)
/*     */       {
/* 768 */         double var9 = this.x + var1.nextFloat();
/* 769 */         double var11 = this.y + var1.nextFloat();
/* 770 */         double var13 = this.z + var1.nextFloat();
/* 771 */         double var15 = 0.0D;
/* 772 */         double var17 = 0.0D;
/* 773 */         double var19 = 0.0D;
/* 774 */         int var21 = var1.nextInt(2) * 2 - 1;
/* 775 */         var15 = (var1.nextFloat() - 0.5D) * 0.5D;
/* 776 */         var17 = (var1.nextFloat() - 0.5D) * 0.5D;
/* 777 */         var19 = (var1.nextFloat() - 0.5D) * 0.5D;
/*     */ 
/* 779 */         if (((this.world.getTypeId(this.x - 1, this.y, this.z) != EEBlock.eeStone.id) || (this.world.getData(this.x - 1, this.y, this.z) != 3)) && ((this.world.getTypeId(this.x + 1, this.y, this.z) != EEBlock.eeStone.id) || (this.world.getData(this.x + 1, this.y, this.z) != 3)))
/*     */         {
/* 781 */           var9 = this.x + 0.5D + 0.25D * var21;
/* 782 */           var15 = var1.nextFloat() * 2.0F * var21;
/*     */         }
/*     */         else
/*     */         {
/* 786 */           var13 = this.z + 0.5D + 0.25D * var21;
/* 787 */           var19 = var1.nextFloat() * 2.0F * var21;
/*     */         }
/*     */ 
/* 790 */         this.world.a("portal", var9, var11, var13, var15, var17, var19);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean receiveEnergy(int var1, byte var2, boolean var3)
/*     */   {
/* 797 */     if (canSmelt())
/*     */     {
/* 799 */       if (var3)
/*     */       {
/* 801 */         this.furnaceBurnTime += var1;
/*     */       }
/*     */ 
/* 804 */       return true;
/*     */     }
/*     */ 
/* 808 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean sendEnergy(int var1, byte var2, boolean var3)
/*     */   {
/* 814 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean passEnergy(int var1, byte var2, boolean var3)
/*     */   {
/* 819 */     return false;
/*     */   }
/*     */ 
/*     */   public void sendAllPackets(int var1) {
/*     */   }
/*     */ 
/*     */   public int relayBonus() {
/* 826 */     return 0;
/*     */   }
/*     */ 
/*     */   public ItemStack splitWithoutUpdate(int var1)
/*     */   {
/* 835 */     return null;
/*     */   }
/*     */ 
/*     */   public ItemStack[] getContents()
/*     */   {
/* 840 */     return this.items;
/*     */   }
/*     */ 
/*     */   public void setMaxStackSize(int size)
/*     */   {
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.TileRMFurnace
 * JD-Core Version:    0.6.2
 */