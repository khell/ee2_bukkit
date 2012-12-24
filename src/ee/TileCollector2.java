/*      */ package ee;
/*      */ 
/*      */ import buildcraft.api.ISpecialInventory;
/*      */ import buildcraft.api.Orientations;
/*      */ import ee.core.GuiIds;
/*      */ import forge.ISidedInventory;
/*      */ import java.io.PrintStream;
/*      */ import java.util.Random;
/*      */ import net.minecraft.server.Block;
/*      */ import net.minecraft.server.EntityHuman;
/*      */ import net.minecraft.server.EntityItem;
/*      */ import net.minecraft.server.Item;
/*      */ import net.minecraft.server.ItemStack;
/*      */ import net.minecraft.server.NBTTagCompound;
/*      */ import net.minecraft.server.NBTTagList;
/*      */ import net.minecraft.server.TileEntity;
/*      */ import net.minecraft.server.World;
/*      */ import net.minecraft.server.WorldProvider;
/*      */ import net.minecraft.server.mod_EE;
/*      */ 
/*      */ public class TileCollector2 extends TileEE
/*      */   implements ISpecialInventory, ISidedInventory, IEEPowerNet
/*      */ {
/*      */   private ItemStack[] items;
/*      */   public int currentSunStatus;
/*      */   public int collectorSunTime;
/*      */   private int accumulate;
/*      */   private float woftFactor;
/*      */   public int currentFuelProgress;
/*      */   public boolean isUsingPower;
/*      */   public int sunTimeScaled;
/*      */   public int kleinProgressScaled;
/*      */   public int kleinPoints;
/*      */ 
/*      */   public TileCollector2()
/*      */   {
/*   26 */     this.items = new ItemStack[15];
/*   27 */     this.kleinPoints = 0;
/*   28 */     this.currentSunStatus = 1;
/*   29 */     this.collectorSunTime = 0;
/*   30 */     this.woftFactor = 1.0F;
/*   31 */     this.accumulate = 0;
/*   32 */     this.currentFuelProgress = 0;
/*   33 */     this.kleinProgressScaled = 0;
/*   34 */     this.sunTimeScaled = 0;
/*      */   }
/*      */ 
/*      */   public int getSize()
/*      */   {
/*   42 */     return this.items.length;
/*      */   }
/*      */ 
/*      */   public void onBlockRemoval()
/*      */   {
/*   49 */     for (int i = 0; i < getSize(); i++)
/*      */     {
/*   51 */       ItemStack itemstack = getItem(i);
/*      */ 
/*   53 */       if (itemstack != null)
/*      */       {
/*   58 */         float f = this.world.random.nextFloat() * 0.8F + 0.1F;
/*   59 */         float f1 = this.world.random.nextFloat() * 0.8F + 0.1F;
/*   60 */         float f2 = this.world.random.nextFloat() * 0.8F + 0.1F;
/*      */ 
/*   68 */         while (itemstack.count > 0)
/*      */         {
/*   73 */           int j = this.world.random.nextInt(21) + 10;
/*      */ 
/*   75 */           if (j > itemstack.count)
/*      */           {
/*   77 */             j = itemstack.count;
/*      */           }
/*      */ 
/*   80 */           itemstack.count -= j;
/*   81 */           EntityItem entityitem = new EntityItem(this.world, this.x + f, this.y + f1, this.z + f2, new ItemStack(itemstack.id, j, itemstack.getData()));
/*      */ 
/*   83 */           if (entityitem != null)
/*      */           {
/*   85 */             float f3 = 0.05F;
/*   86 */             entityitem.motX = ((float)this.world.random.nextGaussian() * f3);
/*   87 */             entityitem.motY = ((float)this.world.random.nextGaussian() * f3 + 0.2F);
/*   88 */             entityitem.motZ = ((float)this.world.random.nextGaussian() * f3);
/*      */ 
/*   90 */             if ((entityitem.itemStack.getItem() instanceof ItemKleinStar))
/*      */             {
/*   92 */               ((ItemKleinStar)entityitem.itemStack.getItem()).setKleinPoints(entityitem.itemStack, ((ItemKleinStar)itemstack.getItem()).getKleinPoints(itemstack));
/*      */             }
/*      */ 
/*   95 */             this.world.addEntity(entityitem);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getMaxStackSize()
/*      */   {
/*  107 */     return 64;
/*      */   }
/*      */ 
/*      */   public ItemStack getItem(int i)
/*      */   {
/*  115 */     return this.items[i];
/*      */   }
/*      */ 
/*      */   public ItemStack splitStack(int i, int j)
/*      */   {
/*  124 */     if (this.items[i] != null)
/*      */     {
/*  126 */       if (this.items[i].count <= j)
/*      */       {
/*  128 */         ItemStack itemstack = this.items[i];
/*  129 */         this.items[i] = null;
/*  130 */         return itemstack;
/*      */       }
/*      */ 
/*  133 */       ItemStack itemstack1 = this.items[i].a(j);
/*      */ 
/*  135 */       if (this.items[i].count == 0)
/*      */       {
/*  137 */         this.items[i] = null;
/*      */       }
/*      */ 
/*  140 */       return itemstack1;
/*      */     }
/*      */ 
/*  144 */     return null;
/*      */   }
/*      */ 
/*      */   public void setItem(int i, ItemStack itemstack)
/*      */   {
/*  153 */     this.items[i] = itemstack;
/*      */ 
/*  155 */     if ((itemstack != null) && (itemstack.count > getMaxStackSize()))
/*      */     {
/*  157 */       itemstack.count = getMaxStackSize();
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean addItem(ItemStack var1, boolean var2, Orientations var3)
/*      */   {
/*  163 */     switch (var3)
/*      */     {
/*      */     case Unknown:
/*      */     case XNeg:
/*      */     case XPos:
/*      */     case YNeg:
/*      */     case YPos:
/*      */     case ZNeg:
/*      */     case ZPos:
/*  173 */       if (var1 != null)
/*      */       {
/*  175 */         if (EEMaps.isFuel(var1))
/*      */         {
/*  177 */           for (int var4 = 0; var4 <= this.items.length - 3; var4++)
/*      */           {
/*  179 */             if (this.items[var4] == null)
/*      */             {
/*  181 */               if (var2)
/*      */               {
/*  183 */                 for (this.items[var4] = var1.cloneItemStack(); var1.count > 0; var1.count -= 1);
/*      */               }
/*      */ 
/*  189 */               return true;
/*      */             }
/*      */ 
/*  192 */             if ((this.items[var4].doMaterialsMatch(var1)) && (this.items[var4].count < this.items[var4].getMaxStackSize()))
/*      */             {
/*  194 */               if (var2)
/*      */               {
/*  196 */                 while ((this.items[var4].count < this.items[var4].getMaxStackSize()) && (var1.count > 0))
/*      */                 {
/*  198 */                   this.items[var4].count += 1;
/*  199 */                   var1.count -= 1;
/*      */                 }
/*      */ 
/*  202 */                 if (var1.count != 0);
/*      */               }
/*      */               else
/*      */               {
/*  208 */                 return true;
/*      */               }
/*      */             }
/*      */           }
/*  212 */         } else if ((EEBase.isKleinStar(var1.id)) && (this.items[0] == null))
/*      */         {
/*  214 */           if (var2)
/*      */           {
/*  216 */             for (this.items[0] = var1.cloneItemStack(); var1.count > 0; var1.count -= 1);
/*      */           }
/*      */ 
/*  222 */           return true;
/*      */         }
/*      */       }
/*      */       break;
/*      */     }
/*  227 */     return false;
/*      */   }
/*      */ 
/*      */   public ItemStack extractItem(boolean flag, Orientations orientations)
/*      */   {
/*  233 */     switch (orientations)
/*      */     {
/*      */     case Unknown:
/*      */     case XNeg:
/*      */     case XPos:
/*      */     case YNeg:
/*      */     case YPos:
/*      */     case ZNeg:
/*      */     case ZPos:
/*  243 */       for (int i = 0; i < this.items.length; i++)
/*      */       {
/*  245 */         if ((this.items[i] != null) && (i != this.items.length - 1))
/*      */         {
/*  250 */           if (i == 0)
/*      */           {
/*  252 */             if (EEBase.isKleinStar(this.items[i].id))
/*      */             {
/*  257 */               ItemStack itemstack = this.items[i].cloneItemStack();
/*      */ 
/*  259 */               if (flag)
/*      */               {
/*  261 */                 this.items[i] = null;
/*      */               }
/*      */ 
/*  264 */               return itemstack;
/*      */             }
/*      */           }
/*  267 */           else if ((this.items[i].id == EEItem.aeternalisFuel.id) || ((this.items[(this.items.length - 1)] != null) && (this.items[i].doMaterialsMatch(this.items[(this.items.length - 1)]))))
/*      */           {
/*  272 */             ItemStack itemstack1 = this.items[i].cloneItemStack();
/*  273 */             itemstack1.count = 1;
/*      */ 
/*  275 */             if (flag)
/*      */             {
/*  277 */               this.items[i].count -= 1;
/*      */ 
/*  279 */               if (this.items[i].count < 1)
/*      */               {
/*  281 */                 this.items[i] = null;
/*      */               }
/*      */             }
/*      */ 
/*  285 */             return itemstack1;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  291 */     return null;
/*      */   }
/*      */ 
/*      */   public String getName()
/*      */   {
/*  299 */     return "Energy Collector";
/*      */   }
/*      */ 
/*      */   public void a(NBTTagCompound nbttagcompound)
/*      */   {
/*  307 */     super.a(nbttagcompound);
/*  308 */     NBTTagList nbttaglist = nbttagcompound.getList("Items");
/*  309 */     this.items = new ItemStack[getSize()];
/*      */ 
/*  311 */     for (int i = 0; i < nbttaglist.size(); i++)
/*      */     {
/*  313 */       NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.get(i);
/*  314 */       byte byte0 = nbttagcompound1.getByte("Slot");
/*      */ 
/*  316 */       if ((byte0 >= 0) && (byte0 < this.items.length))
/*      */       {
/*  318 */         this.items[byte0] = ItemStack.a(nbttagcompound1);
/*      */       }
/*      */     }
/*      */ 
/*  322 */     this.currentSunStatus = nbttagcompound.getShort("sunStatus");
/*  323 */     this.woftFactor = nbttagcompound.getFloat("timeFactor");
/*  324 */     this.accumulate = nbttagcompound.getInt("accumulate");
/*  325 */     this.collectorSunTime = nbttagcompound.getInt("sunTime");
/*      */   }
/*      */ 
/*      */   public void b(NBTTagCompound nbttagcompound)
/*      */   {
/*  333 */     super.b(nbttagcompound);
/*  334 */     nbttagcompound.setInt("sunTime", this.collectorSunTime);
/*  335 */     nbttagcompound.setFloat("timeFactor", this.woftFactor);
/*  336 */     nbttagcompound.setInt("accumulate", this.accumulate);
/*  337 */     nbttagcompound.setShort("sunStatus", (short)this.currentSunStatus);
/*  338 */     NBTTagList nbttaglist = new NBTTagList();
/*      */ 
/*  340 */     for (int i = 0; i < this.items.length; i++)
/*      */     {
/*  342 */       if (this.items[i] != null)
/*      */       {
/*  344 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  345 */         nbttagcompound1.setByte("Slot", (byte)i);
/*  346 */         this.items[i].save(nbttagcompound1);
/*  347 */         nbttaglist.add(nbttagcompound1);
/*      */       }
/*      */     }
/*      */ 
/*  351 */     nbttagcompound.set("Items", nbttaglist);
/*      */   }
/*      */ 
/*      */   public int getSunProgressScaled(int i)
/*      */   {
/*  356 */     if (canUpgrade())
/*      */     {
/*  358 */       if (getFuelDifference() <= 0)
/*      */       {
/*  360 */         return (this.items[0] == null) || (!EEBase.isKleinStar(this.items[0].id)) ? 0 : 24;
/*      */       }
/*      */ 
/*  363 */       if (this.collectorSunTime * i / (getFuelDifference() * 80) > 24)
/*      */       {
/*  365 */         return 24;
/*      */       }
/*      */ 
/*  369 */       return this.collectorSunTime * i / (getFuelDifference() * 80);
/*      */     }
/*      */ 
/*  374 */     return 0;
/*      */   }
/*      */ 
/*      */   public boolean canUpgrade()
/*      */   {
/*  380 */     if (this.items[0] == null)
/*      */     {
/*  382 */       int i = this.items.length - 3;
/*      */ 
/*  386 */       while (i >= 1)
/*      */       {
/*  391 */         if ((this.items[i] != null) && ((this.items[(this.items.length - 1)] == null) || (!this.items[i].doMaterialsMatch(this.items[(this.items.length - 1)]))) && (EEMaps.isFuel(this.items[i])) && (this.items[i].getItem().id != EEItem.aeternalisFuel.id))
/*      */         {
/*  393 */           this.items[0] = this.items[i].cloneItemStack();
/*  394 */           this.items[i] = null;
/*  395 */           break;
/*      */         }
/*      */ 
/*  398 */         i--;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  403 */     if (this.items[0] == null)
/*      */     {
/*  405 */       if (this.items[(this.items.length - 2)] != null)
/*      */       {
/*  407 */         if ((EEMaps.isFuel(this.items[(this.items.length - 2)])) && (this.items[(this.items.length - 2)].getItem().id != EEItem.aeternalisFuel.id))
/*      */         {
/*  409 */           this.items[0] = this.items[(this.items.length - 2)].cloneItemStack();
/*  410 */           this.items[(this.items.length - 2)] = null;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  415 */         return false;
/*      */       }
/*      */     }
/*      */ 
/*  419 */     if (this.items[0] == null)
/*      */     {
/*  421 */       return false;
/*      */     }
/*      */ 
/*  424 */     if (EEBase.isKleinStar(this.items[0].id))
/*      */     {
/*  426 */       if (EEBase.canIncreaseKleinStarPoints(this.items[0], this.world))
/*      */       {
/*  428 */         return true;
/*      */       }
/*      */ 
/*  431 */       if (this.items[(this.items.length - 2)] == null)
/*      */       {
/*  433 */         this.items[(this.items.length - 2)] = this.items[0].cloneItemStack();
/*  434 */         this.items[0] = null;
/*  435 */         return false;
/*      */       }
/*      */ 
/*  438 */       for (int j = 1; j <= this.items.length - 3; j++)
/*      */       {
/*  440 */         if (this.items[j] == null)
/*      */         {
/*  442 */           this.items[j] = this.items[(this.items.length - 2)].cloneItemStack();
/*  443 */           this.items[(this.items.length - 2)] = this.items[0].cloneItemStack();
/*  444 */           this.items[0] = null;
/*  445 */           return false;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  450 */     if ((this.items[0].getItem().id != EEItem.aeternalisFuel.id) && (EEMaps.isFuel(this.items[0])))
/*      */     {
/*  452 */       return true;
/*      */     }
/*      */ 
/*  455 */     return this.items[0].getItem().id == EEItem.darkMatter.id;
/*      */   }
/*      */ 
/*      */   public boolean receiveEnergy(int i, byte byte0, boolean flag)
/*      */   {
/*  460 */     if (!isUsingPower())
/*      */     {
/*  462 */       return false;
/*      */     }
/*      */ 
/*  465 */     if (flag)
/*      */     {
/*  467 */       this.accumulate += i;
/*  468 */       return true;
/*      */     }
/*      */ 
/*  472 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean sendEnergy(int i, byte byte0, boolean flag)
/*      */   {
/*  478 */     TileEntity tileentity = this.world.getTileEntity(this.x + (byte0 != 5 ? 1 : byte0 != 4 ? 0 : -1), this.y + (byte0 != 1 ? 1 : byte0 != 0 ? 0 : -1), this.z + (byte0 != 3 ? 1 : byte0 != 2 ? 0 : -1));
/*      */ 
/*  480 */     if (tileentity == null)
/*      */     {
/*  482 */       return false;
/*      */     }
/*      */ 
/*  485 */     return ((tileentity instanceof IEEPowerNet)) && (((IEEPowerNet)tileentity).receiveEnergy(i + ((IEEPowerNet)tileentity).relayBonus(), byte0, flag));
/*      */   }
/*      */ 
/*      */   public void sendAllPackets(int i)
/*      */   {
/*  490 */     int j = 0;
/*      */ 
/*  492 */     for (byte byte0 = 0; byte0 < 6; byte0 = (byte)(byte0 + 1))
/*      */     {
/*  494 */       if (sendEnergy(i, byte0, false))
/*      */       {
/*  496 */         j++;
/*      */       }
/*      */     }
/*      */ 
/*  500 */     if (j == 0)
/*      */     {
/*  502 */       if (this.collectorSunTime <= 2400000 - i)
/*      */       {
/*  504 */         this.collectorSunTime += i;
/*      */       }
/*      */ 
/*  507 */       return;
/*      */     }
/*      */ 
/*  510 */     int k = i / j;
/*      */ 
/*  512 */     if (k < 1)
/*      */     {
/*  514 */       return;
/*      */     }
/*      */ 
/*  517 */     for (byte byte1 = 0; byte1 < 6; byte1 = (byte)(byte1 + 1))
/*      */     {
/*  519 */       sendEnergy(k, byte1, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean passEnergy(int i, byte byte0, boolean flag)
/*      */   {
/*  525 */     return false;
/*      */   }
/*      */ 
/*      */   public int relayBonus()
/*      */   {
/*  530 */     return 0;
/*      */   }
/*      */ 
/*      */   public int getRealSunStatus()
/*      */   {
/*  535 */     if (this.world == null)
/*      */     {
/*  537 */       System.out.println("World object is turning a null for collectors..");
/*  538 */       return 0;
/*      */     }
/*      */ 
/*  541 */     if (this.world.worldProvider.d)
/*      */     {
/*  543 */       this.currentSunStatus = 16;
/*      */     }
/*      */     else
/*      */     {
/*  547 */       this.currentSunStatus = (this.world.getLightLevel(this.x, this.y + 1, this.z) + 1);
/*      */     }
/*      */ 
/*  550 */     return this.currentSunStatus;
/*      */   }
/*      */ 
/*      */   public int getSunStatus(int i)
/*      */   {
/*  555 */     return getRealSunStatus() * i / 16;
/*      */   }
/*      */ 
/*      */   public void q_()
/*      */   {
/*  564 */     if (clientFail())
/*      */     {
/*  566 */       return;
/*      */     }
/*      */ 
/*  569 */     if (!this.world.isStatic)
/*      */     {
/*  571 */       if (this.collectorSunTime < 0)
/*      */       {
/*  573 */         this.collectorSunTime = 0;
/*      */       }
/*      */ 
/*  576 */       if ((this.items[0] != null) && ((this.items[0].getItem() instanceof ItemKleinStar)))
/*      */       {
/*  578 */         this.kleinProgressScaled = getKleinProgressScaled(48);
/*  579 */         this.kleinPoints = getKleinPoints(this.items[0]);
/*      */       }
/*      */ 
/*  582 */       this.sunTimeScaled = getSunTimeScaled(48);
/*  583 */       this.currentFuelProgress = getSunProgressScaled(24);
/*  584 */       this.currentSunStatus = getSunStatus(12);
/*  585 */       this.isUsingPower = isUsingPower();
/*      */ 
/*  587 */       for (int i = this.items.length - 3; i >= 2; i--)
/*      */       {
/*  589 */         if ((this.items[i] == null) && (this.items[(i - 1)] != null))
/*      */         {
/*  591 */           this.items[i] = this.items[(i - 1)].cloneItemStack();
/*  592 */           this.items[(i - 1)] = null;
/*      */         }
/*      */       }
/*      */ 
/*  596 */       this.woftFactor = (EEBase.getPedestalFactor(this.world) * EEBase.getPlayerWatchFactor());
/*      */ 
/*  598 */       if (isUsingPower())
/*      */       {
/*  600 */         this.collectorSunTime += getFactoredProduction();
/*      */ 
/*  602 */         if (this.accumulate > 0)
/*      */         {
/*  604 */           this.collectorSunTime += this.accumulate;
/*  605 */           this.accumulate = 0;
/*      */         }
/*      */ 
/*  608 */         if (EEBase.isKleinStar(this.items[0].id))
/*      */         {
/*  610 */           int j = getFactoredProduction() * EEBase.getKleinLevel(this.items[0].id);
/*      */           do {
/*  612 */             this.collectorSunTime -= 80;
/*      */ 
/*  610 */             j--; if ((j <= 0) || (this.collectorSunTime < 80)) break;  } while (EEBase.addKleinStarPoints(this.items[0], 1, this.world));
/*      */         }
/*      */         else
/*      */         {
/*      */           do
/*      */           {
/*  619 */             this.collectorSunTime -= getFuelDifference() * 80;
/*      */ 
/*  617 */             uptierFuel(); if (getFuelDifference() <= 0) break;  } while (this.collectorSunTime >= getFuelDifference() * 80);
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  625 */         if (this.accumulate > 0)
/*      */         {
/*  627 */           this.collectorSunTime += this.accumulate;
/*  628 */           this.accumulate = 0;
/*      */         }
/*      */ 
/*  631 */         sendAllPackets(getFactoredProduction());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private int getKleinPoints(ItemStack itemstack)
/*      */   {
/*  638 */     if (itemstack == null)
/*      */     {
/*  640 */       return 0;
/*      */     }
/*      */ 
/*  643 */     if ((itemstack.getItem() instanceof ItemKleinStar))
/*      */     {
/*  645 */       return ((ItemKleinStar)itemstack.getItem()).getKleinPoints(itemstack);
/*      */     }
/*      */ 
/*  649 */     return 0;
/*      */   }
/*      */ 
/*      */   private int getSunTimeScaled(int i)
/*      */   {
/*  655 */     return this.collectorSunTime * i / 2400000;
/*      */   }
/*      */ 
/*      */   private int getKleinProgressScaled(int i)
/*      */   {
/*  660 */     if ((this.items[0] != null) && ((this.items[0].getItem() instanceof ItemKleinStar)))
/*      */     {
/*  662 */       return ((ItemKleinStar)this.items[0].getItem()).getKleinPoints(this.items[0]) * i / ((ItemKleinStar)this.items[0].getItem()).getMaxPoints(this.items[0]);
/*      */     }
/*      */ 
/*  666 */     return 0;
/*      */   }
/*      */ 
/*      */   public int getFactoredProduction()
/*      */   {
/*  672 */     return (int)(getProduction() * getWOFTReciprocal(this.woftFactor));
/*      */   }
/*      */ 
/*      */   public int getProduction()
/*      */   {
/*  677 */     return getRealSunStatus() * 3;
/*      */   }
/*      */ 
/*      */   public boolean isUsingPower()
/*      */   {
/*  682 */     return (canUpgrade()) && (canCollect());
/*      */   }
/*      */ 
/*      */   private int getFuelDifference()
/*      */   {
/*  687 */     if (this.items[0] == null)
/*      */     {
/*  689 */       return 0;
/*      */     }
/*      */ 
/*  693 */     return getFuelLevel(getNextFuel(this.items[0])) - getFuelLevel(this.items[0]);
/*      */   }
/*      */ 
/*      */   private int getFuelLevel(ItemStack itemstack)
/*      */   {
/*  699 */     return EEMaps.getEMC(itemstack);
/*      */   }
/*      */ 
/*      */   private ItemStack getNextFuel(ItemStack itemstack)
/*      */   {
/*  704 */     int i = itemstack.id;
/*  705 */     int j = itemstack.getData();
/*      */ 
/*  707 */     if (this.items[(this.items.length - 1)] == null)
/*      */     {
/*  709 */       if (EEMaps.isFuel(itemstack))
/*      */       {
/*  711 */         if ((i == Item.COAL.id) && (j == 1))
/*      */         {
/*  713 */           return new ItemStack(Item.REDSTONE.id, 1, 0);
/*      */         }
/*      */ 
/*  716 */         if (i == Item.REDSTONE.id)
/*      */         {
/*  718 */           return new ItemStack(Item.COAL.id, 1, 0);
/*      */         }
/*      */ 
/*  721 */         if (i == Item.COAL.id)
/*      */         {
/*  723 */           return new ItemStack(Item.SULPHUR.id, 1, 0);
/*      */         }
/*      */ 
/*  726 */         if (i == Item.SULPHUR.id)
/*      */         {
/*  728 */           return new ItemStack(Item.GLOWSTONE_DUST.id, 1, 0);
/*      */         }
/*      */ 
/*  731 */         if (i == Item.GLOWSTONE_DUST.id)
/*      */         {
/*  733 */           return new ItemStack(EEItem.alchemicalCoal.id, 1, 0);
/*      */         }
/*      */ 
/*  736 */         if (i == EEItem.alchemicalCoal.id)
/*      */         {
/*  738 */           return new ItemStack(Item.BLAZE_POWDER.id, 1, 0);
/*      */         }
/*      */ 
/*  741 */         if (i == Item.BLAZE_POWDER.id)
/*      */         {
/*  743 */           return new ItemStack(Block.GLOWSTONE.id, 1, 0);
/*      */         }
/*      */ 
/*  746 */         if (i == Block.GLOWSTONE.id)
/*      */         {
/*  748 */           return new ItemStack(EEItem.mobiusFuel.id, 1, 0);
/*      */         }
/*      */ 
/*  751 */         if (i == EEItem.mobiusFuel.id)
/*      */         {
/*  753 */           return new ItemStack(EEItem.aeternalisFuel.id, 1, 0);
/*      */         }
/*      */       }
/*      */     } else {
/*  757 */       if (EEMaps.isFuel(this.items[(this.items.length - 1)]))
/*      */       {
/*  759 */         if (EEMaps.getEMC(i, j) < EEMaps.getEMC(this.items[(this.items.length - 1)].id, this.items[(this.items.length - 1)].getData()))
/*      */         {
/*  761 */           return this.items[(this.items.length - 1)];
/*      */         }
/*      */ 
/*  765 */         return null;
/*      */       }
/*      */ 
/*  770 */       EntityItem entityitem = new EntityItem(this.world, this.x, this.y, this.z, this.items[(this.items.length - 1)].cloneItemStack());
/*  771 */       this.items[(this.items.length - 1)] = null;
/*  772 */       entityitem.pickupDelay = 10;
/*  773 */       this.world.addEntity(entityitem);
/*  774 */       return null;
/*      */     }
/*      */ 
/*  777 */     return null;
/*      */   }
/*      */ 
/*      */   private boolean canCollect()
/*      */   {
/*  782 */     if (this.items[0] == null)
/*      */     {
/*  784 */       int i = 1;
/*      */ 
/*  788 */       while (i <= this.items.length - 3)
/*      */       {
/*  793 */         if ((this.items[i] != null) && ((this.items[(this.items.length - 1)] == null) || ((this.items[(this.items.length - 1)] != null) && (this.items[(this.items.length - 1)].doMaterialsMatch(this.items[i])))))
/*      */         {
/*  795 */           this.items[0] = this.items[i].cloneItemStack();
/*  796 */           this.items[i] = null;
/*  797 */           break;
/*      */         }
/*      */ 
/*  800 */         i++;
/*      */       }
/*      */ 
/*  804 */       if (this.items[0] == null)
/*      */       {
/*  806 */         return false;
/*      */       }
/*      */     }
/*      */ 
/*  810 */     if (EEBase.isKleinStar(this.items[0].id))
/*      */     {
/*  812 */       return true;
/*      */     }
/*      */ 
/*  815 */     if (getNextFuel(this.items[0]) == null)
/*      */     {
/*  817 */       return false;
/*      */     }
/*      */ 
/*  820 */     ItemStack itemstack = getNextFuel(this.items[0]).cloneItemStack();
/*      */ 
/*  822 */     if (this.items[(this.items.length - 2)] == null)
/*      */     {
/*  824 */       return true;
/*      */     }
/*      */ 
/*  827 */     if (!this.items[(this.items.length - 2)].doMaterialsMatch(itemstack))
/*      */     {
/*  831 */       for (int j = 1; j <= this.items.length - 3; j++)
/*      */       {
/*  833 */         if (this.items[j] != null)
/*      */         {
/*  835 */           if (this.items[j].doMaterialsMatch(this.items[(this.items.length - 2)]))
/*      */           {
/*      */             do
/*      */             {
/*  842 */               if ((this.items[(this.items.length - 2)] == null) || (this.items[j].count >= 64))
/*      */               {
/*      */                 break;
/*      */               }
/*      */ 
/*  847 */               this.items[(this.items.length - 2)].count -= 1;
/*  848 */               this.items[j].count += 1;
/*      */             }
/*  850 */             while (this.items[(this.items.length - 2)].count != 0);
/*      */ 
/*  852 */             this.items[(this.items.length - 2)] = null;
/*  853 */             return true;
/*      */           }
/*      */         }
/*      */         else {
/*  857 */           this.items[j] = this.items[(this.items.length - 2)].cloneItemStack();
/*  858 */           this.items[(this.items.length - 2)] = null;
/*  859 */           return true;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  864 */     if ((this.items[(this.items.length - 2)] != null) && (!this.items[(this.items.length - 2)].doMaterialsMatch(itemstack)))
/*      */     {
/*  866 */       return false;
/*      */     }
/*      */ 
/*  869 */     if ((this.items[(this.items.length - 2)].count < getMaxStackSize()) && (this.items[(this.items.length - 2)].count < this.items[(this.items.length - 2)].getMaxStackSize()))
/*      */     {
/*  871 */       return true;
/*      */     }
/*      */ 
/*  874 */     for (int k = 1; k <= this.items.length - 2; k++)
/*      */     {
/*  876 */       if ((this.items[k] != null) && ((this.items[k].getItem().id == EEItem.mobiusFuel.id) || ((this.items[(this.items.length - 1)] != null) && (this.items[k].doMaterialsMatch(this.items[(this.items.length - 1)])))) && (this.items[k].count >= this.items[k].getMaxStackSize()) && (tryDropInChest(new ItemStack(this.items[k].getItem(), this.items[k].count))))
/*      */       {
/*  878 */         this.items[k] = null;
/*      */       }
/*      */     }
/*      */ 
/*  882 */     if (this.items[(this.items.length - 2)] == null)
/*      */     {
/*  884 */       return true;
/*      */     }
/*      */ 
/*  889 */     for (int l = 1; l <= this.items.length - 3; l++)
/*      */     {
/*  891 */       if (this.items[l] != null)
/*      */       {
/*  893 */         if (this.items[l].doMaterialsMatch(this.items[(this.items.length - 2)]))
/*      */         {
/*      */           do
/*      */           {
/*  900 */             if ((this.items[(this.items.length - 2)] == null) || (this.items[l].count >= 64))
/*      */             {
/*      */               break;
/*      */             }
/*      */ 
/*  905 */             this.items[(this.items.length - 2)].count -= 1;
/*  906 */             this.items[l].count += 1;
/*      */           }
/*  908 */           while (this.items[(this.items.length - 2)].count != 0);
/*      */ 
/*  910 */           this.items[(this.items.length - 2)] = null;
/*  911 */           return true;
/*      */         }
/*      */       }
/*      */       else {
/*  915 */         this.items[l] = this.items[(this.items.length - 2)].cloneItemStack();
/*  916 */         this.items[(this.items.length - 2)] = null;
/*  917 */         return true;
/*      */       }
/*      */     }
/*      */ 
/*  921 */     return this.items[(this.items.length - 2)].count < itemstack.getMaxStackSize();
/*      */   }
/*      */ 
/*      */   public void uptierFuel()
/*      */   {
/*  926 */     if (!canCollect())
/*      */     {
/*  928 */       return;
/*      */     }
/*      */ 
/*  931 */     if (getNextFuel(this.items[0]) == null)
/*      */     {
/*  933 */       return;
/*      */     }
/*      */ 
/*  936 */     ItemStack itemstack = getNextFuel(this.items[0]).cloneItemStack();
/*  937 */     itemstack.count = 1;
/*      */ 
/*  939 */     if (this.items[(this.items.length - 2)] == null)
/*      */     {
/*  941 */       if (((this.items[(this.items.length - 1)] != null) && (itemstack.doMaterialsMatch(this.items[(this.items.length - 1)]))) || (itemstack.getItem() == EEItem.aeternalisFuel))
/*      */       {
/*  943 */         if (!tryDropInChest(itemstack))
/*      */         {
/*  945 */           this.items[(this.items.length - 2)] = itemstack.cloneItemStack();
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  950 */         this.items[(this.items.length - 2)] = itemstack.cloneItemStack();
/*      */       }
/*      */     }
/*  953 */     else if (this.items[(this.items.length - 2)].id == itemstack.id)
/*      */     {
/*  955 */       if (this.items[(this.items.length - 2)].count == itemstack.getMaxStackSize())
/*      */       {
/*  957 */         if ((this.items[(this.items.length - 2)].getItem().id == EEItem.aeternalisFuel.id) || ((this.items[(this.items.length - 1)] != null) && (this.items[(this.items.length - 2)].doMaterialsMatch(this.items[(this.items.length - 1)]))))
/*      */         {
/*  959 */           if (tryDropInChest(this.items[(this.items.length - 2)].cloneItemStack()))
/*      */           {
/*  961 */             this.items[(this.items.length - 2)] = null;
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  966 */           int i = 1;
/*      */ 
/*  970 */           while (i <= this.items.length - 3)
/*      */           {
/*  975 */             if (this.items[i] == null)
/*      */             {
/*  977 */               this.items[i] = this.items[(this.items.length - 2)].cloneItemStack();
/*  978 */               this.items[(this.items.length - 2)] = null;
/*  979 */               break;
/*      */             }
/*      */ 
/*  982 */             if (this.items[i].doMaterialsMatch(this.items[(this.items.length - 2)]))
/*      */             {
/*  986 */               while ((this.items[i].count < this.items[i].getMaxStackSize()) && (this.items[(this.items.length - 2)] != null))
/*      */               {
/*  991 */                 this.items[(this.items.length - 2)].count -= 1;
/*  992 */                 this.items[i].count += 1;
/*      */ 
/*  994 */                 if (this.items[(this.items.length - 2)].count == 0)
/*      */                 {
/*  996 */                   this.items[(this.items.length - 2)] = null;
/*      */                 }
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/* 1002 */             i++;
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/* 1007 */       else if (((this.items[(this.items.length - 1)] != null) && (itemstack.doMaterialsMatch(this.items[(this.items.length - 1)]))) || (itemstack.getItem() == EEItem.aeternalisFuel))
/*      */       {
/* 1009 */         if (!tryDropInChest(itemstack))
/*      */         {
/* 1011 */           this.items[(this.items.length - 2)].count += itemstack.count;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1016 */         this.items[(this.items.length - 2)].count += itemstack.count;
/*      */       }
/*      */     }
/* 1019 */     else if (((this.items[(this.items.length - 1)] != null) && (itemstack.doMaterialsMatch(this.items[(this.items.length - 1)]))) || ((itemstack.getItem() == EEItem.aeternalisFuel) && (tryDropInChest(this.items[(this.items.length - 2)].cloneItemStack()))))
/*      */     {
/* 1021 */       this.items[(this.items.length - 2)] = null;
/*      */     }
/*      */ 
/* 1024 */     if (this.items[0].getItem().k())
/*      */     {
/* 1026 */       this.items[0] = new ItemStack(this.items[0].getItem().j());
/*      */     }
/*      */     else
/*      */     {
/* 1030 */       this.items[0].count -= 1;
/*      */     }
/*      */ 
/* 1033 */     if (this.items[0].count <= 0)
/*      */     {
/* 1035 */       this.items[0] = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void f()
/*      */   {
/*      */   }
/*      */ 
/*      */   public void g()
/*      */   {
/*      */   }
/*      */ 
/*      */   public boolean a(EntityHuman entityhuman)
/*      */   {
/* 1052 */     if (this.world.getTileEntity(this.x, this.y, this.z) != this)
/*      */     {
/* 1054 */       return false;
/*      */     }
/*      */ 
/* 1058 */     return entityhuman.e(this.x + 0.5D, this.y + 0.5D, this.z + 0.5D) <= 64.0D;
/*      */   }
/*      */ 
/*      */   public int getStartInventorySide(int i)
/*      */   {
/* 1064 */     return i != 0 ? 1 : 0;
/*      */   }
/*      */ 
/*      */   public int getSizeInventorySide(int i)
/*      */   {
/* 1069 */     if (i == 0)
/*      */     {
/* 1071 */       return 1;
/*      */     }
/*      */ 
/* 1075 */     return this.items.length - 2;
/*      */   }
/*      */ 
/*      */   public boolean onBlockActivated(EntityHuman entityhuman)
/*      */   {
/* 1081 */     entityhuman.openGui(mod_EE.getInstance(), GuiIds.COLLECTOR_2, this.world, this.x, this.y, this.z);
/* 1082 */     return true;
/*      */   }
/*      */ 
/*      */   public int getTextureForSide(int i)
/*      */   {
/* 1087 */     if (i == 1)
/*      */     {
/* 1089 */       return EEBase.collector2Top;
/*      */     }
/*      */ 
/* 1092 */     byte byte0 = this.direction;
/*      */ 
/* 1094 */     if (i != byte0)
/*      */     {
/* 1096 */       return EEBase.collectorSide;
/*      */     }
/*      */ 
/* 1100 */     return EEBase.collectorFront;
/*      */   }
/*      */ 
/*      */   public int getInventoryTexture(int i)
/*      */   {
/* 1106 */     if (i == 1)
/*      */     {
/* 1108 */       return EEBase.collector2Top;
/*      */     }
/*      */ 
/* 1111 */     if (i == 3)
/*      */     {
/* 1113 */       return EEBase.collectorFront;
/*      */     }
/*      */ 
/* 1117 */     return EEBase.collectorSide;
/*      */   }
/*      */ 
/*      */   public int getLightValue()
/*      */   {
/* 1123 */     return 11;
/*      */   }
/*      */ 
/*      */   public void onNeighborBlockChange(int i)
/*      */   {
/*      */   }
/*      */ 
/*      */   public ItemStack splitWithoutUpdate(int i)
/*      */   {
/* 1136 */     return null;
/*      */   }
/*      */ 
/*      */   public ItemStack[] getContents()
/*      */   {
/* 1141 */     return this.items;
/*      */   }
/*      */ 
/*      */   public void setMaxStackSize(int size)
/*      */   {
/*      */   }
/*      */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.TileCollector2
 * JD-Core Version:    0.6.2
 */