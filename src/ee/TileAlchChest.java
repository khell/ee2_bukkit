/*      */ package ee;
/*      */ 
/*      */ import buildcraft.api.ISpecialInventory;
/*      */ import buildcraft.api.Orientations;
/*      */ import ee.core.GuiIds;
/*      */ import ee.item.ItemLootBall;
/*      */ import forge.ISidedInventory;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import net.minecraft.server.AxisAlignedBB;
/*      */ import net.minecraft.server.EEProxy;
/*      */ import net.minecraft.server.Entity;
/*      */ import net.minecraft.server.EntityHuman;
/*      */ import net.minecraft.server.EntityItem;
/*      */ import net.minecraft.server.Item;
/*      */ import net.minecraft.server.ItemStack;
/*      */ import net.minecraft.server.NBTTagCompound;
/*      */ import net.minecraft.server.NBTTagList;
/*      */ import net.minecraft.server.World;
/*      */ import net.minecraft.server.mod_EE;
/*      */ 
/*      */ public class TileAlchChest extends TileEE
/*      */   implements ISpecialInventory, ISidedInventory
/*      */ {
/*   25 */   private ItemStack[] items = new ItemStack[113];
/*   26 */   private int repairTimer = 0;
/*      */   private int eternalDensity;
/*      */   private boolean repairOn;
/*      */   private boolean condenseOn;
/*      */   private boolean interdictionOn;
/*      */   public boolean timeWarp;
/*      */   public float lidAngle;
/*      */   public float prevLidAngle;
/*      */   public int numUsingPlayers;
/*      */   private int ticksSinceSync;
/*      */   private boolean initialized;
/*      */   private boolean attractionOn;
/*      */ 
/*      */   public boolean addItem(ItemStack var1, boolean var2, Orientations var3)
/*      */   {
/*   41 */     switch (var3)
/*      */     {
/*      */     case Unknown:
/*      */     case XNeg:
/*      */     case XPos:
/*      */     case YNeg:
/*      */     case YPos:
/*      */     case ZNeg:
/*      */     case ZPos:
/*   51 */       if (var1 != null)
/*      */       {
/*   53 */         for (int var4 = 0; var4 < this.items.length; var4++)
/*      */         {
/*   55 */           if (this.items[var4] == null)
/*      */           {
/*   57 */             if (var2)
/*      */             {
/*   59 */               for (this.items[var4] = var1.cloneItemStack(); var1.count > 0; var1.count -= 1);
/*      */             }
/*      */ 
/*   65 */             return true;
/*      */           }
/*      */ 
/*   68 */           if ((this.items[var4].doMaterialsMatch(var1)) && (this.items[var4].count < this.items[var4].getMaxStackSize()))
/*      */           {
/*   70 */             if (var2)
/*      */             {
/*   72 */               while ((this.items[var4].count < this.items[var4].getMaxStackSize()) && (var1.count > 0))
/*      */               {
/*   74 */                 this.items[var4].count += 1;
/*   75 */                 var1.count -= 1;
/*      */               }
/*      */ 
/*   78 */               if (var1.count != 0);
/*      */             }
/*      */             else
/*      */             {
/*   84 */               return true;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       break;
/*      */     }
/*   90 */     return false;
/*      */   }
/*      */ 
/*      */   public ItemStack extractItem(boolean var1, Orientations var2)
/*      */   {
/*   96 */     switch (var2)
/*      */     {
/*      */     case Unknown:
/*      */     case XNeg:
/*      */     case XPos:
/*      */     case YNeg:
/*      */     case YPos:
/*      */     case ZNeg:
/*      */     case ZPos:
/*  106 */       for (int var3 = 0; var3 < this.items.length; var3++)
/*      */       {
/*  108 */         if (this.items[var3] != null)
/*      */         {
/*  110 */           ItemStack var4 = this.items[var3].cloneItemStack();
/*  111 */           var4.count = 1;
/*      */ 
/*  113 */           if (var1)
/*      */           {
/*  115 */             this.items[var3].count -= 1;
/*      */ 
/*  117 */             if (this.items[var3].count < 1)
/*      */             {
/*  119 */               this.items[var3] = null;
/*      */             }
/*      */           }
/*      */ 
/*  123 */           return var4;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  128 */     return null;
/*      */   }
/*      */ 
/*      */   public int getSize()
/*      */   {
/*  137 */     return 104;
/*      */   }
/*      */ 
/*      */   public ItemStack getItem(int var1)
/*      */   {
/*  145 */     return this.items[var1];
/*      */   }
/*      */ 
/*      */   public ItemStack splitStack(int var1, int var2)
/*      */   {
/*  154 */     if (this.items[var1] != null)
/*      */     {
/*  158 */       if (this.items[var1].count <= var2)
/*      */       {
/*  160 */         ItemStack var3 = this.items[var1];
/*  161 */         this.items[var1] = null;
/*  162 */         update();
/*  163 */         return var3;
/*      */       }
/*      */ 
/*  167 */       ItemStack var3 = this.items[var1].a(var2);
/*      */ 
/*  169 */       if (this.items[var1].count == 0)
/*      */       {
/*  171 */         this.items[var1] = null;
/*      */       }
/*      */ 
/*  174 */       update();
/*  175 */       return var3;
/*      */     }
/*      */ 
/*  180 */     return null;
/*      */   }
/*      */ 
/*      */   public void setItem(int var1, ItemStack var2)
/*      */   {
/*  189 */     this.items[var1] = var2;
/*      */ 
/*  191 */     if ((var2 != null) && (var2.count > getMaxStackSize()))
/*      */     {
/*  193 */       var2.count = getMaxStackSize();
/*      */     }
/*      */ 
/*  196 */     update();
/*      */   }
/*      */ 
/*      */   public String getName()
/*      */   {
/*  204 */     return "Chest";
/*      */   }
/*      */ 
/*      */   public void a(NBTTagCompound var1)
/*      */   {
/*  212 */     super.a(var1);
/*  213 */     NBTTagList var2 = var1.getList("Items");
/*  214 */     this.items = new ItemStack[getSize()];
/*      */ 
/*  216 */     for (int var3 = 0; var3 < var2.size(); var3++)
/*      */     {
/*  218 */       NBTTagCompound var4 = (NBTTagCompound)var2.get(var3);
/*  219 */       int var5 = var4.getByte("Slot") & 0xFF;
/*      */ 
/*  221 */       if ((var5 >= 0) && (var5 < this.items.length))
/*      */       {
/*  223 */         this.items[var5] = ItemStack.a(var4);
/*      */       }
/*      */     }
/*      */ 
/*  227 */     this.condenseOn = var1.getBoolean("condenseOn");
/*  228 */     this.repairOn = var1.getBoolean("repairOn");
/*  229 */     this.eternalDensity = var1.getShort("eternalDensity");
/*  230 */     this.timeWarp = var1.getBoolean("timeWarp");
/*  231 */     this.interdictionOn = var1.getBoolean("interdictionOn");
/*      */   }
/*      */ 
/*      */   public void b(NBTTagCompound var1)
/*      */   {
/*  239 */     super.b(var1);
/*  240 */     var1.setBoolean("timeWarp", this.timeWarp);
/*  241 */     var1.setBoolean("condenseOn", this.condenseOn);
/*  242 */     var1.setBoolean("repairOn", this.repairOn);
/*  243 */     var1.setShort("eternalDensity", (short)this.eternalDensity);
/*  244 */     var1.setBoolean("interdictionOn", this.interdictionOn);
/*  245 */     NBTTagList var2 = new NBTTagList();
/*      */ 
/*  247 */     for (int var3 = 0; var3 < this.items.length; var3++)
/*      */     {
/*  249 */       if (this.items[var3] != null)
/*      */       {
/*  251 */         NBTTagCompound var4 = new NBTTagCompound();
/*  252 */         var4.setByte("Slot", (byte)var3);
/*  253 */         this.items[var3].save(var4);
/*  254 */         var2.add(var4);
/*      */       }
/*      */     }
/*      */ 
/*  258 */     var1.set("Items", var2);
/*      */   }
/*      */ 
/*      */   public int getMaxStackSize()
/*      */   {
/*  267 */     return 64;
/*      */   }
/*      */ 
/*      */   public void update()
/*      */   {
/*  275 */     super.update();
/*      */ 
/*  277 */     if ((this.world != null) && (!EEProxy.isClient(this.world)))
/*      */     {
/*  279 */       boolean var1 = false;
/*  280 */       boolean var2 = false;
/*  281 */       boolean var3 = false;
/*  282 */       boolean var4 = false;
/*  283 */       boolean var5 = false;
/*      */ 
/*  285 */       for (int var6 = 0; var6 < getSize(); var6++)
/*      */       {
/*  287 */         if (this.items[var6] != null)
/*      */         {
/*  289 */           if (this.items[var6].getItem().id == EEItem.watchOfTime.id)
/*      */           {
/*  291 */             var4 = true;
/*      */           }
/*      */ 
/*  294 */           if (this.items[var6].getItem().id == EEItem.repairCharm.id)
/*      */           {
/*  296 */             var1 = true;
/*      */           }
/*      */ 
/*  299 */           if ((this.items[var6].getItem() instanceof ItemVoidRing))
/*      */           {
/*  301 */             this.eternalDensity = var6;
/*      */ 
/*  303 */             if ((this.items[var6].getData() & 0x1) == 0)
/*      */             {
/*  305 */               this.items[var6].setData(this.items[var6].getData() + 1);
/*  306 */               ((ItemEECharged)this.items[var6].getItem()).setBoolean(this.items[var6], "active", true);
/*      */             }
/*      */ 
/*  309 */             var2 = true;
/*  310 */             var5 = true;
/*      */           }
/*      */ 
/*  313 */           if (this.items[var6].getItem().id == EEItem.eternalDensity.id)
/*      */           {
/*  315 */             this.eternalDensity = var6;
/*      */ 
/*  317 */             if ((this.items[var6].getData() & 0x1) == 0)
/*      */             {
/*  319 */               this.items[var6].setData(this.items[var6].getData() + 1);
/*  320 */               ((ItemEECharged)this.items[var6].getItem()).setBoolean(this.items[var6], "active", true);
/*      */             }
/*      */ 
/*  323 */             var2 = true;
/*      */           }
/*      */ 
/*  326 */           if ((this.items[var6].getItem() instanceof ItemAttractionRing))
/*      */           {
/*  328 */             if ((this.items[var6].getData() & 0x1) == 0)
/*      */             {
/*  330 */               this.items[var6].setData(this.items[var6].getData() + 1);
/*  331 */               ((ItemEECharged)this.items[var6].getItem()).setBoolean(this.items[var6], "active", true);
/*      */             }
/*      */ 
/*  334 */             var5 = true;
/*      */           }
/*      */ 
/*  337 */           if ((this.items[var6].getItem().id == EEBlock.eeTorch.id) && (this.items[var6].getData() == 0))
/*      */           {
/*  339 */             var3 = true;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  344 */       if (var4 != this.timeWarp)
/*      */       {
/*  346 */         this.timeWarp = var4;
/*      */       }
/*      */ 
/*  349 */       if (var1 != this.repairOn)
/*      */       {
/*  351 */         this.repairOn = var1;
/*      */       }
/*      */ 
/*  354 */       if (var5 != this.attractionOn)
/*      */       {
/*  356 */         this.attractionOn = var5;
/*      */       }
/*      */ 
/*  359 */       if (var2 != this.condenseOn)
/*      */       {
/*  361 */         this.condenseOn = var2;
/*      */       }
/*  363 */       else if (!var2)
/*      */       {
/*  365 */         this.eternalDensity = -1;
/*      */       }
/*      */ 
/*  368 */       if (var3 != this.interdictionOn)
/*      */       {
/*  370 */         this.world.notify(this.x, this.y, this.z);
/*  371 */         this.interdictionOn = var3;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void doRepair()
/*      */   {
/*  378 */     if (this.repairTimer >= 20)
/*      */     {
/*  380 */       ItemStack var1 = null;
/*  381 */       boolean var2 = false;
/*      */ 
/*  383 */       for (int var3 = 0; var3 < getSize(); var3++)
/*      */       {
/*  385 */         var2 = false;
/*  386 */         var1 = this.items[var3];
/*      */ 
/*  388 */         if (var1 != null)
/*      */         {
/*  390 */           for (int var4 = 0; var4 < EEMaps.chargedItems.size(); var4++)
/*      */           {
/*  392 */             if (((Integer)EEMaps.chargedItems.get(Integer.valueOf(var4))).intValue() == var1.id)
/*      */             {
/*  394 */               var2 = true;
/*  395 */               break;
/*      */             }
/*      */           }
/*      */ 
/*  399 */           if ((!var2) && (var1.getData() >= 1) && (var1.d()))
/*      */           {
/*  401 */             var1.setData(var1.getData() - 1);
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  406 */       this.repairTimer = 0;
/*      */     }
/*      */ 
/*  409 */     this.repairTimer += 1;
/*      */   }
/*      */ 
/*      */   public void doCondense(ItemStack var1)
/*      */   {
/*  414 */     if (this.eternalDensity != -1)
/*      */     {
/*  416 */       int var2 = 0;
/*      */ 
/*  419 */       for (int var3 = 0; var3 < this.items.length; var3++)
/*      */       {
/*  421 */         if ((this.items[var3] != null) && (isValidMaterial(this.items[var3])) && (EEMaps.getEMC(this.items[var3]) > var2))
/*      */         {
/*  423 */           var2 = EEMaps.getEMC(this.items[var3]);
/*      */         }
/*      */       }
/*      */ 
/*  427 */       for (int var3 = 0; var3 < this.items.length; var3++)
/*      */       {
/*  429 */         if ((this.items[var3] != null) && (isValidMaterial(this.items[var3])) && (EEMaps.getEMC(this.items[var3]) < var2))
/*      */         {
/*  431 */           var2 = EEMaps.getEMC(this.items[var3]);
/*      */         }
/*      */       }
/*      */ 
/*  435 */       if ((var2 >= EEMaps.getEMC(EEItem.redMatter.id)) || (AnalyzeTier(this.items[this.eternalDensity], EEMaps.getEMC(EEItem.redMatter.id))) || (var2 >= EEMaps.getEMC(EEItem.darkMatter.id)) || (AnalyzeTier(this.items[this.eternalDensity], EEMaps.getEMC(EEItem.darkMatter.id))) || (var2 >= EEMaps.getEMC(Item.DIAMOND.id)) || (AnalyzeTier(this.items[this.eternalDensity], EEMaps.getEMC(Item.DIAMOND.id))) || (var2 >= EEMaps.getEMC(Item.GOLD_INGOT.id)) || (AnalyzeTier(this.items[this.eternalDensity], EEMaps.getEMC(Item.GOLD_INGOT.id))) || (var2 >= EEMaps.getEMC(Item.IRON_INGOT.id)) || (!AnalyzeTier(this.items[this.eternalDensity], EEMaps.getEMC(Item.IRON_INGOT.id))));
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean AnalyzeTier(ItemStack var1, int var2)
/*      */   {
/*  444 */     if (var1 == null)
/*      */     {
/*  446 */       return false;
/*      */     }
/*      */ 
/*  450 */     int var3 = 0;
/*      */ 
/*  453 */     for (int var4 = 0; var4 < this.items.length; var4++)
/*      */     {
/*  455 */       if ((this.items[var4] != null) && (isValidMaterial(this.items[var4])) && (EEMaps.getEMC(this.items[var4]) < var2))
/*      */       {
/*  457 */         var3 += EEMaps.getEMC(this.items[var4]) * this.items[var4].count;
/*      */       }
/*      */     }
/*      */ 
/*  461 */     if (var3 + emc(var1) < var2)
/*      */     {
/*  463 */       return false;
/*      */     }
/*      */ 
/*  467 */     int var4 = 0;
/*      */ 
/*  469 */     while ((var3 + emc(var1) >= var2) && (var4 < 10))
/*      */     {
/*  471 */       var4++;
/*  472 */       ConsumeMaterialBelowTier(var1, var2);
/*      */     }
/*      */ 
/*  475 */     if ((emc(var1) >= var2) && (roomFor(getProduct(var2))))
/*      */     {
/*  477 */       PushStack(getProduct(var2));
/*  478 */       takeEMC(var1, var2);
/*      */     }
/*      */ 
/*  481 */     return true;
/*      */   }
/*      */ 
/*      */   private boolean roomFor(ItemStack var1)
/*      */   {
/*  488 */     if (var1 == null)
/*      */     {
/*  490 */       return false;
/*      */     }
/*      */ 
/*  494 */     for (int var2 = 0; var2 < this.items.length; var2++)
/*      */     {
/*  496 */       if (this.items[var2] == null)
/*      */       {
/*  498 */         return true;
/*      */       }
/*      */ 
/*  501 */       if ((this.items[var2].doMaterialsMatch(var1)) && (this.items[var2].count <= var1.getMaxStackSize() - var1.count))
/*      */       {
/*  503 */         return true;
/*      */       }
/*      */     }
/*      */ 
/*  507 */     return false;
/*      */   }
/*      */ 
/*      */   private ItemStack getProduct(int var1)
/*      */   {
/*  513 */     return var1 == EEMaps.getEMC(EEItem.redMatter.id) ? new ItemStack(EEItem.redMatter, 1) : var1 == EEMaps.getEMC(EEItem.darkMatter.id) ? new ItemStack(EEItem.darkMatter, 1) : var1 == EEMaps.getEMC(Item.DIAMOND.id) ? new ItemStack(Item.DIAMOND, 1) : var1 == EEMaps.getEMC(Item.GOLD_INGOT.id) ? new ItemStack(Item.GOLD_INGOT, 1) : var1 == EEMaps.getEMC(Item.IRON_INGOT.id) ? new ItemStack(Item.IRON_INGOT, 1) : null;
/*      */   }
/*      */ 
/*      */   private void ConsumeMaterialBelowTier(ItemStack var1, int var2)
/*      */   {
/*  518 */     for (int var3 = 0; var3 < this.items.length; var3++)
/*      */     {
/*  520 */       if ((this.items[var3] != null) && (isValidMaterial(this.items[var3])) && (EEMaps.getEMC(this.items[var3]) < var2))
/*      */       {
/*  522 */         addEMC(var1, EEMaps.getEMC(this.items[var3]));
/*  523 */         this.items[var3].count -= 1;
/*      */ 
/*  525 */         if (this.items[var3].count == 0)
/*      */         {
/*  527 */           this.items[var3] = null;
/*      */         }
/*      */ 
/*  530 */         return;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean isValidMaterial(ItemStack var1)
/*      */   {
/*  537 */     if (var1 == null)
/*      */     {
/*  539 */       return false;
/*      */     }
/*  541 */     if (EEMaps.getEMC(var1) == 0)
/*      */     {
/*  543 */       return false;
/*      */     }
/*  545 */     if ((var1.getItem() instanceof ItemKleinStar))
/*      */     {
/*  547 */       return false;
/*      */     }
/*      */ 
/*  551 */     int var2 = var1.id;
/*  552 */     return var2 != EEItem.redMatter.id;
/*      */   }
/*      */ 
/*      */   private int emc(ItemStack var1)
/*      */   {
/*  558 */     return (var1.getItem() instanceof ItemEternalDensity) ? ((ItemEternalDensity)var1.getItem()).getInteger(var1, "emc") : (!(var1.getItem() instanceof ItemEternalDensity)) && (!(var1.getItem() instanceof ItemVoidRing)) ? 0 : ((ItemVoidRing)var1.getItem()).getInteger(var1, "emc");
/*      */   }
/*      */ 
/*      */   private void takeEMC(ItemStack var1, int var2)
/*      */   {
/*  563 */     if (((var1.getItem() instanceof ItemEternalDensity)) || ((var1.getItem() instanceof ItemVoidRing)))
/*      */     {
/*  565 */       if ((var1.getItem() instanceof ItemEternalDensity))
/*      */       {
/*  567 */         ((ItemEternalDensity)var1.getItem()).setInteger(var1, "emc", emc(var1) - var2);
/*      */       }
/*      */       else
/*      */       {
/*  571 */         ((ItemVoidRing)var1.getItem()).setInteger(var1, "emc", emc(var1) - var2);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addEMC(ItemStack var1, int var2)
/*      */   {
/*  578 */     if (((var1.getItem() instanceof ItemEternalDensity)) || ((var1.getItem() instanceof ItemVoidRing)))
/*      */     {
/*  580 */       if ((var1.getItem() instanceof ItemEternalDensity))
/*      */       {
/*  582 */         ((ItemEternalDensity)var1.getItem()).setInteger(var1, "emc", emc(var1) + var2);
/*      */       }
/*      */       else
/*      */       {
/*  586 */         ((ItemVoidRing)var1.getItem()).setInteger(var1, "emc", emc(var1) + var2);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void q_()
/*      */   {
/*  597 */     if (++this.ticksSinceSync % 20 * 4 == 0)
/*      */     {
/*  599 */       this.world.playNote(this.x, this.y, this.z, 1, this.numUsingPlayers);
/*      */     }
/*      */ 
/*  602 */     this.prevLidAngle = this.lidAngle;
/*  603 */     float var1 = 0.1F;
/*      */ 
/*  606 */     if ((this.numUsingPlayers > 0) && (this.lidAngle == 0.0F))
/*      */     {
/*  608 */       double var4 = this.x + 0.5D;
/*  609 */       double var2 = this.z + 0.5D;
/*  610 */       this.world.makeSound(var4, this.y + 0.5D, var2, "random.chestopen", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
/*      */     }
/*      */ 
/*  613 */     if (((this.numUsingPlayers == 0) && (this.lidAngle > 0.0F)) || ((this.numUsingPlayers > 0) && (this.lidAngle < 1.0F)))
/*      */     {
/*  615 */       float var8 = this.lidAngle;
/*      */ 
/*  617 */       if (this.numUsingPlayers > 0)
/*      */       {
/*  619 */         this.lidAngle += var1;
/*      */       }
/*      */       else
/*      */       {
/*  623 */         this.lidAngle -= var1;
/*      */       }
/*      */ 
/*  626 */       if (this.lidAngle > 1.0F)
/*      */       {
/*  628 */         this.lidAngle = 1.0F;
/*      */       }
/*      */ 
/*  631 */       float var5 = 0.5F;
/*      */ 
/*  633 */       if ((this.lidAngle < var5) && (var8 >= var5))
/*      */       {
/*  635 */         double var2 = this.x + 0.5D;
/*  636 */         double var6 = this.z + 0.5D;
/*  637 */         this.world.makeSound(var2, this.y + 0.5D, var6, "random.chestclosed", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
/*      */       }
/*      */ 
/*  640 */       if (this.lidAngle < 0.0F)
/*      */       {
/*  642 */         this.lidAngle = 0.0F;
/*      */       }
/*      */     }
/*      */ 
/*  646 */     if (this.repairOn)
/*      */     {
/*  648 */       doRepair();
/*      */     }
/*      */ 
/*  651 */     if (this.attractionOn)
/*      */     {
/*  653 */       doAttraction();
/*      */     }
/*      */ 
/*  656 */     if ((this.condenseOn) && (this.eternalDensity >= 0))
/*      */     {
/*  658 */       doCondense(this.items[this.eternalDensity]);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void doAttraction()
/*      */   {
/*  664 */     List var1 = this.world.a(EntityLootBall.class, AxisAlignedBB.b(this.x - 10, this.y - 10, this.z - 10, this.x + 10, this.y + 10, this.z + 10));
/*  665 */     Iterator var3 = var1.iterator();
/*      */ 
/*  667 */     while (var3.hasNext())
/*      */     {
/*  669 */       Entity var2 = (Entity)var3.next();
/*  670 */       PullItems(var2);
/*      */     }
/*      */ 
/*  673 */     List var12 = this.world.a(EntityLootBall.class, AxisAlignedBB.b(this.x - 10, this.y - 10, this.z - 10, this.x + 10, this.y + 10, this.z + 10));
/*  674 */     Iterator var5 = var12.iterator();
/*      */ 
/*  676 */     while (var5.hasNext())
/*      */     {
/*  678 */       Entity var4 = (Entity)var5.next();
/*  679 */       PullItems(var4);
/*      */     }
/*      */ 
/*  682 */     List var13 = this.world.a(EntityItem.class, AxisAlignedBB.b(this.x - 10, this.y - 10, this.z - 10, this.x + 10, this.y + 10, this.z + 10));
/*  683 */     Iterator var7 = var13.iterator();
/*      */ 
/*  685 */     while (var7.hasNext())
/*      */     {
/*  687 */       Entity var6 = (Entity)var7.next();
/*  688 */       PullItems(var6);
/*      */     }
/*      */ 
/*  691 */     List var14 = this.world.a(EntityLootBall.class, AxisAlignedBB.b(this.x - 0.5D, this.y - 0.5D, this.z - 0.5D, this.x + 1.25D, this.y + 1.25D, this.z + 1.25D));
/*  692 */     Iterator var9 = var14.iterator();
/*      */ 
/*  694 */     while (var9.hasNext())
/*      */     {
/*  696 */       Entity var8 = (Entity)var9.next();
/*  697 */       GrabItems(var8);
/*      */     }
/*      */ 
/*  700 */     List var15 = this.world.a(EntityItem.class, AxisAlignedBB.b(this.x - 0.5D, this.y - 0.5D, this.z - 0.5D, this.x + 1.25D, this.y + 1.25D, this.z + 1.25D));
/*  701 */     Iterator var11 = var15.iterator();
/*      */ 
/*  703 */     while (var11.hasNext())
/*      */     {
/*  705 */       Entity var10 = (Entity)var11.next();
/*  706 */       GrabItems(var10);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean PushStack(EntityItem var1)
/*      */   {
/*  712 */     if (var1 == null)
/*      */     {
/*  714 */       return false;
/*      */     }
/*  716 */     if (var1.itemStack == null)
/*      */     {
/*  718 */       var1.die();
/*  719 */       return false;
/*      */     }
/*  721 */     if (var1.itemStack.count < 1)
/*      */     {
/*  723 */       var1.die();
/*  724 */       return false;
/*      */     }
/*      */ 
/*  728 */     for (int var2 = 0; var2 < this.items.length; var2++)
/*      */     {
/*  730 */       if (this.items[var2] == null)
/*      */       {
/*  732 */         this.items[var2] = var1.itemStack.cloneItemStack();
/*      */ 
/*  734 */         for (this.items[var2].count = 0; (var1.itemStack.count > 0) && (this.items[var2].count < this.items[var2].getMaxStackSize()); var1.itemStack.count -= 1)
/*      */         {
/*  736 */           this.items[var2].count += 1;
/*      */         }
/*      */ 
/*  739 */         var1.die();
/*  740 */         return true;
/*      */       }
/*      */ 
/*  743 */       if ((this.items[var2].doMaterialsMatch(var1.itemStack)) && (this.items[var2].count <= var1.itemStack.getMaxStackSize() - var1.itemStack.count))
/*      */       {
/*  745 */         while ((var1.itemStack.count > 0) && (this.items[var2].count < this.items[var2].getMaxStackSize()))
/*      */         {
/*  747 */           this.items[var2].count += 1;
/*  748 */           var1.itemStack.count -= 1;
/*      */         }
/*      */ 
/*  751 */         var1.die();
/*  752 */         return true;
/*      */       }
/*      */     }
/*      */ 
/*  756 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean PushStack(ItemStack var1)
/*      */   {
/*  762 */     if (var1 == null)
/*      */     {
/*  764 */       return false;
/*      */     }
/*      */ 
/*  768 */     for (int var2 = 0; var2 < this.items.length; var2++)
/*      */     {
/*  770 */       if (this.items[var2] == null)
/*      */       {
/*  772 */         this.items[var2] = var1.cloneItemStack();
/*  773 */         var1 = null;
/*  774 */         return true;
/*      */       }
/*      */ 
/*  777 */       if ((this.items[var2].doMaterialsMatch(var1)) && (this.items[var2].count <= var1.getMaxStackSize() - var1.count))
/*      */       {
/*  779 */         this.items[var2].count += var1.count;
/*  780 */         var1 = null;
/*  781 */         return true;
/*      */       }
/*      */     }
/*      */ 
/*  785 */     return false;
/*      */   }
/*      */ 
/*      */   private void PushDenseStacks(EntityLootBall var1)
/*      */   {
/*  791 */     for (int var2 = 0; var2 < var1.items.length; var2++)
/*      */     {
/*  793 */       if ((var1.items[var2] != null) && (PushStack(var1.items[var2])))
/*      */       {
/*  795 */         var1.items[var2] = null;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void GrabItems(Entity var1)
/*      */   {
/*  802 */     if ((var1 != null) && ((var1 instanceof EntityItem)))
/*      */     {
/*  804 */       ItemStack var9 = ((EntityItem)var1).itemStack;
/*      */ 
/*  806 */       if (var9 == null)
/*      */       {
/*  808 */         var1.die();
/*  809 */         return;
/*      */       }
/*      */ 
/*  812 */       if ((var9.getItem() instanceof ItemLootBall))
/*      */       {
/*  814 */         ItemLootBall var3 = (ItemLootBall)var9.getItem();
/*  815 */         ItemStack[] var4 = var3.getDroplist(var9);
/*  816 */         ItemStack[] var5 = var4;
/*  817 */         int var6 = var4.length;
/*      */ 
/*  819 */         for (int var7 = 0; var7 < var6; var7++)
/*      */         {
/*  821 */           ItemStack var8 = var5[var7];
/*  822 */           PushStack(var8);
/*      */         }
/*      */ 
/*  825 */         var1.die();
/*      */       }
/*      */       else
/*      */       {
/*  829 */         PushStack(var9);
/*  830 */         var1.die();
/*      */       }
/*      */     }
/*  833 */     else if ((var1 != null) && ((var1 instanceof EntityLootBall)))
/*      */     {
/*  835 */       if (((EntityLootBall)var1).items == null)
/*      */       {
/*  837 */         var1.die();
/*      */       }
/*      */ 
/*  840 */       ItemStack[] var2 = ((EntityLootBall)var1).items;
/*  841 */       PushDenseStacks((EntityLootBall)var1);
/*      */ 
/*  843 */       if (((EntityLootBall)var1).isEmpty())
/*      */       {
/*  845 */         var1.die();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void PullItems(Entity var1)
/*      */   {
/*  852 */     if (((var1 instanceof EntityItem)) || ((var1 instanceof EntityLootBall)))
/*      */     {
/*  854 */       if ((var1 instanceof EntityLootBall))
/*      */       {
/*  856 */         ((EntityLootBall)var1).setBeingPulled(true);
/*      */       }
/*      */ 
/*  859 */       double var3 = this.x + 0.5D - var1.locX;
/*  860 */       double var5 = this.y + 0.5D - var1.locY;
/*  861 */       double var7 = this.z + 0.5D - var1.locZ;
/*  862 */       double var9 = var3 * var3 + var5 * var5 + var7 * var7;
/*  863 */       var9 *= var9;
/*      */ 
/*  865 */       if (var9 <= Math.pow(6.0D, 4.0D))
/*      */       {
/*  867 */         double var11 = var3 * 0.01999999955296516D / var9 * Math.pow(6.0D, 3.0D);
/*  868 */         double var13 = var5 * 0.01999999955296516D / var9 * Math.pow(6.0D, 3.0D);
/*  869 */         double var15 = var7 * 0.01999999955296516D / var9 * Math.pow(6.0D, 3.0D);
/*      */ 
/*  871 */         if (var11 > 0.1D)
/*      */         {
/*  873 */           var11 = 0.1D;
/*      */         }
/*  875 */         else if (var11 < -0.1D)
/*      */         {
/*  877 */           var11 = -0.1D;
/*      */         }
/*      */ 
/*  880 */         if (var13 > 0.1D)
/*      */         {
/*  882 */           var13 = 0.1D;
/*      */         }
/*  884 */         else if (var13 < -0.1D)
/*      */         {
/*  886 */           var13 = -0.1D;
/*      */         }
/*      */ 
/*  889 */         if (var15 > 0.1D)
/*      */         {
/*  891 */           var15 = 0.1D;
/*      */         }
/*  893 */         else if (var15 < -0.1D)
/*      */         {
/*  895 */           var15 = -0.1D;
/*      */         }
/*      */ 
/*  898 */         var1.motX += var11 * 1.2D;
/*  899 */         var1.motY += var13 * 1.2D;
/*  900 */         var1.motZ += var15 * 1.2D;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isInterdicting()
/*      */   {
/*  907 */     return this.interdictionOn;
/*      */   }
/*      */ 
/*      */   private void PushEntities(Entity var1, int var2, int var3, int var4)
/*      */   {
/*  912 */     if ((!(var1 instanceof EntityHuman)) && (!(var1 instanceof EntityItem)))
/*      */     {
/*  914 */       double var6 = var2 - var1.locX;
/*  915 */       double var8 = var3 - var1.locY;
/*  916 */       double var10 = var4 - var1.locZ;
/*  917 */       double var12 = var6 * var6 + var8 * var8 + var10 * var10;
/*  918 */       var12 *= var12;
/*      */ 
/*  920 */       if (var12 <= Math.pow(6.0D, 4.0D))
/*      */       {
/*  922 */         double var14 = -(var6 * 0.01999999955296516D / var12) * Math.pow(6.0D, 3.0D);
/*  923 */         double var16 = -(var8 * 0.01999999955296516D / var12) * Math.pow(6.0D, 3.0D);
/*  924 */         double var18 = -(var10 * 0.01999999955296516D / var12) * Math.pow(6.0D, 3.0D);
/*      */ 
/*  926 */         if (var14 > 0.0D)
/*      */         {
/*  928 */           var14 = 0.22D;
/*      */         }
/*  930 */         else if (var14 < 0.0D)
/*      */         {
/*  932 */           var14 = -0.22D;
/*      */         }
/*      */ 
/*  935 */         if (var16 > 0.2D)
/*      */         {
/*  937 */           var16 = 0.12D;
/*      */         }
/*  939 */         else if (var16 < -0.1D)
/*      */         {
/*  941 */           var16 = 0.12D;
/*      */         }
/*      */ 
/*  944 */         if (var18 > 0.0D)
/*      */         {
/*  946 */           var18 = 0.22D;
/*      */         }
/*  948 */         else if (var18 < 0.0D)
/*      */         {
/*  950 */           var18 = -0.22D;
/*      */         }
/*      */ 
/*  953 */         var1.motX += var14;
/*  954 */         var1.motY += var16;
/*  955 */         var1.motZ += var18;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void b(int var1, int var2)
/*      */   {
/*  962 */     if (var1 == 1)
/*      */     {
/*  964 */       this.numUsingPlayers = var2;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void f()
/*      */   {
/*  970 */     this.numUsingPlayers += 1;
/*  971 */     this.world.playNote(this.x, this.y, this.z, 1, this.numUsingPlayers);
/*      */   }
/*      */ 
/*      */   public void g()
/*      */   {
/*  976 */     this.numUsingPlayers -= 1;
/*  977 */     this.world.playNote(this.x, this.y, this.z, 1, this.numUsingPlayers);
/*      */   }
/*      */ 
/*      */   public boolean a(EntityHuman var1)
/*      */   {
/*  985 */     return this.world.getTileEntity(this.x, this.y, this.z) == this;
/*      */   }
/*      */ 
/*      */   public int getStartInventorySide(int var1)
/*      */   {
/*  990 */     return 0;
/*      */   }
/*      */ 
/*      */   public int getSizeInventorySide(int var1)
/*      */   {
/*  995 */     return getSize();
/*      */   }
/*      */ 
/*      */   public boolean onBlockActivated(EntityHuman var1)
/*      */   {
/* 1000 */     if (!this.world.isStatic)
/*      */     {
/* 1002 */       var1.openGui(mod_EE.getInstance(), GuiIds.ALCH_CHEST, this.world, this.x, this.y, this.z);
/*      */     }
/*      */ 
/* 1005 */     return true;
/*      */   }
/*      */ 
/*      */   public void onBlockRemoval()
/*      */   {
/* 1010 */     for (int var1 = 0; var1 < getSize(); var1++)
/*      */     {
/* 1012 */       ItemStack var2 = getItem(var1);
/*      */ 
/* 1014 */       if (var2 != null)
/*      */       {
/* 1016 */         float var3 = this.world.random.nextFloat() * 0.8F + 0.1F;
/* 1017 */         float var4 = this.world.random.nextFloat() * 0.8F + 0.1F;
/* 1018 */         float var5 = this.world.random.nextFloat() * 0.8F + 0.1F;
/*      */ 
/* 1020 */         while (var2.count > 0)
/*      */         {
/* 1022 */           int var6 = this.world.random.nextInt(21) + 10;
/*      */ 
/* 1024 */           if (var6 > var2.count)
/*      */           {
/* 1026 */             var6 = var2.count;
/*      */           }
/*      */ 
/* 1029 */           var2.count -= var6;
/* 1030 */           EntityItem var7 = new EntityItem(this.world, this.x + var3, this.y + var4, this.z + var5, new ItemStack(var2.id, var6, var2.getData()));
/*      */ 
/* 1032 */           if (var7 != null)
/*      */           {
/* 1034 */             float var8 = 0.05F;
/* 1035 */             var7.motX = ((float)this.world.random.nextGaussian() * var8);
/* 1036 */             var7.motY = ((float)this.world.random.nextGaussian() * var8 + 0.2F);
/* 1037 */             var7.motZ = ((float)this.world.random.nextGaussian() * var8);
/*      */ 
/* 1039 */             if ((var7.itemStack.getItem() instanceof ItemKleinStar))
/*      */             {
/* 1041 */               ((ItemKleinStar)var7.itemStack.getItem()).setKleinPoints(var7.itemStack, ((ItemKleinStar)var2.getItem()).getKleinPoints(var2));
/*      */             }
/*      */ 
/* 1044 */             this.world.addEntity(var7);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getTextureForSide(int var1)
/*      */   {
/* 1053 */     if ((var1 != 1) && (var1 != 0))
/*      */     {
/* 1055 */       byte var2 = this.direction;
/* 1056 */       return var1 != var2 ? EEBase.alchChestSide : EEBase.alchChestFront;
/*      */     }
/*      */ 
/* 1060 */     return EEBase.alchChestTop;
/*      */   }
/*      */ 
/*      */   public int getInventoryTexture(int var1)
/*      */   {
/* 1066 */     return var1 == 3 ? EEBase.alchChestFront : var1 == 1 ? EEBase.alchChestTop : EEBase.alchChestSide;
/*      */   }
/*      */ 
/*      */   public int getLightValue()
/*      */   {
/* 1071 */     return isInterdicting() ? 15 : 0;
/*      */   }
/*      */ 
/*      */   public void onNeighborBlockChange(int var1)
/*      */   {
/*      */   }
/*      */ 
/*      */   public ItemStack splitWithoutUpdate(int var1)
/*      */   {
/* 1082 */     return null;
/*      */   }
/*      */ 
/*      */   public ItemStack[] getContents()
/*      */   {
/* 1087 */     return this.items;
/*      */   }
/*      */ 
/*      */   public void setMaxStackSize(int size)
/*      */   {
/*      */   }
/*      */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.TileAlchChest
 * JD-Core Version:    0.6.2
 */