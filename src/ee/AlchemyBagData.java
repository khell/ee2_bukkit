/*     */ package ee;
/*     */ 
/*     */ import ee.item.ItemLootBall;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.minecraft.server.AxisAlignedBB;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityExperienceOrb;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityItem;
/*     */ import net.minecraft.server.IInventory;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.NBTTagCompound;
/*     */ import net.minecraft.server.NBTTagList;
/*     */ import net.minecraft.server.PlayerInventory;
/*     */ import net.minecraft.server.World;
/*     */ import net.minecraft.server.WorldMapBase;
/*     */ import org.bukkit.craftbukkit.entity.CraftHumanEntity;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ 
/*     */ public class AlchemyBagData extends WorldMapBase
/*     */   implements IInventory
/*     */ {
/*     */   public boolean voidOn;
/*     */   public boolean repairOn;
/*     */   public boolean markForUpdate;
/*     */   public boolean condenseOn;
/*  35 */   public int repairTimer = 0;
/*  36 */   public int condenseCheckTimer = 0;
/*     */   public static final String prefix = "bag";
/*     */   public static final String prefix_ = "bag_";
/*  39 */   public ItemStack[] items = new ItemStack[113];
/*     */   private int eternalDensity;
/*     */   private boolean initialized;
/*  42 */   public static List datas = new LinkedList();
/*     */ 
/*     */   public AlchemyBagData(String var1)
/*     */   {
/*  46 */     super(var1);
/*  47 */     datas.add(this);
/*     */   }
/*     */ 
/*     */   public void onUpdate(World var1, EntityHuman var2)
/*     */   {
/*  52 */     if (!this.initialized)
/*     */     {
/*  54 */       this.initialized = true;
/*  55 */       update();
/*     */     }
/*     */ 
/*  58 */     if (this.repairOn)
/*     */     {
/*  60 */       doRepair();
/*     */     }
/*     */ 
/*  63 */     if (this.condenseOn)
/*     */     {
/*  65 */       doCondense(this.items[this.eternalDensity]);
/*     */     }
/*     */ 
/*  68 */     if (this.voidOn)
/*     */     {
/*  70 */       boolean var3 = false;
/*     */ 
/*  72 */       for (int var4 = 0; var4 <= 15; var4++)
/*     */       {
/*  74 */         boolean var5 = true;
/*  75 */         ItemStack[] var6 = var2.inventory.items;
/*  76 */         int var7 = var6.length;
/*     */ 
/*  78 */         for (int var8 = 0; var8 < var7; var8++)
/*     */         {
/*  80 */           ItemStack var9 = var6[var8];
/*     */ 
/*  82 */           if ((var9 != null) && (var9.doMaterialsMatch(new ItemStack(EEItem.alchemyBag, 1, var4))))
/*     */           {
/*  84 */             var5 = false;
/*     */           }
/*     */         }
/*     */ 
/*  88 */         if (!var5)
/*     */         {
/*  90 */           String var10 = "bag_" + var2.name + var4;
/*  91 */           AlchemyBagData var11 = (AlchemyBagData)var1.a(AlchemyBagData.class, var10);
/*     */ 
/*  93 */           if (var11 != null)
/*     */           {
/*  95 */             if (var3)
/*     */             {
/*     */               break;
/*     */             }
/*     */ 
/* 100 */             if (var11.voidOn)
/*     */             {
/* 102 */               var3 = true;
/*     */             }
/*     */ 
/* 105 */             if ((var11 == this) && (var3))
/*     */             {
/* 107 */               doAttraction(var2);
/* 108 */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 115 */     if (this.markForUpdate)
/*     */     {
/* 117 */       a();
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getSize()
/*     */   {
/* 126 */     return 104;
/*     */   }
/*     */ 
/*     */   public ItemStack getItem(int var1)
/*     */   {
/* 134 */     return this.items[var1];
/*     */   }
/*     */ 
/*     */   public ItemStack splitStack(int var1, int var2)
/*     */   {
/* 143 */     if (this.items[var1] != null)
/*     */     {
/* 147 */       if (this.items[var1].count <= var2)
/*     */       {
/* 149 */         ItemStack var3 = this.items[var1];
/* 150 */         this.items[var1] = null;
/* 151 */         update();
/* 152 */         return var3;
/*     */       }
/*     */ 
/* 156 */       ItemStack var3 = this.items[var1].a(var2);
/*     */ 
/* 158 */       if (this.items[var1].count == 0)
/*     */       {
/* 160 */         this.items[var1] = null;
/*     */       }
/*     */ 
/* 163 */       update();
/* 164 */       return var3;
/*     */     }
/*     */ 
/* 169 */     return null;
/*     */   }
/*     */ 
/*     */   public void setItem(int var1, ItemStack var2)
/*     */   {
/* 178 */     this.items[var1] = var2;
/*     */ 
/* 180 */     if ((var2 != null) && (var2.count > getMaxStackSize()))
/*     */     {
/* 182 */       var2.count = getMaxStackSize();
/*     */     }
/*     */ 
/* 185 */     update();
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 193 */     return "Bag";
/*     */   }
/*     */ 
/*     */   public int getMaxStackSize()
/*     */   {
/* 202 */     return 64;
/*     */   }
/*     */ 
/*     */   public void update()
/*     */   {
/* 210 */     this.markForUpdate = true;
/* 211 */     boolean var1 = false;
/* 212 */     boolean var2 = false;
/* 213 */     boolean var3 = false;
/*     */ 
/* 215 */     for (int var4 = 0; var4 < this.items.length; var4++)
/*     */     {
/* 217 */       if (this.items[var4] != null)
/*     */       {
/* 219 */         if (this.items[var4].getItem() == EEItem.repairCharm)
/*     */         {
/* 221 */           var1 = true;
/*     */         }
/*     */ 
/* 224 */         if (this.items[var4].getItem() == EEItem.voidRing)
/*     */         {
/* 226 */           this.eternalDensity = var4;
/*     */ 
/* 228 */           if ((this.items[var4].getData() & 0x1) == 0)
/*     */           {
/* 230 */             this.items[var4].setData(this.items[var4].getData() + 1);
/* 231 */             ((ItemEECharged)this.items[var4].getItem()).setBoolean(this.items[var4], "active", true);
/*     */           }
/*     */ 
/* 234 */           var3 = true;
/* 235 */           var2 = true;
/*     */         }
/*     */ 
/* 238 */         if (this.items[var4].getItem() == EEItem.eternalDensity)
/*     */         {
/* 240 */           this.eternalDensity = var4;
/*     */ 
/* 242 */           if ((this.items[var4].getData() & 0x1) == 0)
/*     */           {
/* 244 */             this.items[var4].setData(this.items[var4].getData() + 1);
/* 245 */             ((ItemEECharged)this.items[var4].getItem()).setBoolean(this.items[var4], "active", true);
/*     */           }
/*     */ 
/* 248 */           var2 = true;
/*     */         }
/*     */ 
/* 251 */         if (this.items[var4].getItem() == EEItem.attractionRing)
/*     */         {
/* 253 */           var3 = true;
/*     */ 
/* 255 */           if ((this.items[var4].getData() & 0x1) == 0)
/*     */           {
/* 257 */             this.items[var4].setData(this.items[var4].getData() + 1);
/* 258 */             ((ItemEECharged)this.items[var4].getItem()).setBoolean(this.items[var4], "active", true);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 264 */     if (var1 != this.repairOn)
/*     */     {
/* 266 */       this.repairOn = var1;
/*     */     }
/*     */ 
/* 269 */     if (var2 != this.condenseOn)
/*     */     {
/* 271 */       this.condenseOn = var2;
/*     */     }
/*     */ 
/* 274 */     if (var3 != this.voidOn)
/*     */     {
/* 276 */       this.voidOn = var3;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doRepair()
/*     */   {
/* 282 */     if (this.repairTimer >= 20)
/*     */     {
/* 284 */       ItemStack var1 = null;
/* 285 */       boolean var2 = false;
/*     */ 
/* 287 */       for (int var3 = 0; var3 < getSize(); var3++)
/*     */       {
/* 289 */         var2 = false;
/* 290 */         var1 = this.items[var3];
/*     */ 
/* 292 */         if (var1 != null)
/*     */         {
/* 294 */           for (int var4 = 0; var4 < EEMaps.chargedItems.size(); var4++)
/*     */           {
/* 296 */             if (((Integer)EEMaps.chargedItems.get(Integer.valueOf(var4))).intValue() == var1.id)
/*     */             {
/* 298 */               var2 = true;
/* 299 */               break;
/*     */             }
/*     */           }
/*     */ 
/* 303 */           if ((!var2) && (var1.getData() >= 1) && (var1.d()))
/*     */           {
/* 305 */             var1.setData(var1.getData() - 1);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 310 */       this.repairTimer = 0;
/*     */     }
/*     */ 
/* 313 */     this.repairTimer += 1;
/* 314 */     this.markForUpdate = true;
/*     */   }
/*     */ 
/*     */   public void doCondense(ItemStack var1)
/*     */   {
/* 319 */     if (this.eternalDensity != -1)
/*     */     {
/* 321 */       int var2 = 0;
/*     */ 
/* 324 */       for (int var3 = 0; var3 < this.items.length; var3++)
/*     */       {
/* 326 */         if ((this.items[var3] != null) && (isValidMaterial(this.items[var3])) && (EEMaps.getEMC(this.items[var3]) > var2))
/*     */         {
/* 328 */           var2 = EEMaps.getEMC(this.items[var3]);
/*     */         }
/*     */       }
/*     */ 
/* 332 */       for (var3 = 0; var3 < this.items.length; var3++)
/*     */       {
/* 334 */         if ((this.items[var3] != null) && (isValidMaterial(this.items[var3])) && (EEMaps.getEMC(this.items[var3]) < var2))
/*     */         {
/* 336 */           var2 = EEMaps.getEMC(this.items[var3]);
/*     */         }
/*     */       }
/*     */ 
/* 340 */       if ((var2 >= EEMaps.getEMC(EEItem.redMatter.id)) || (AnalyzeTier(this.items[this.eternalDensity], EEMaps.getEMC(EEItem.redMatter.id))) || (var2 >= EEMaps.getEMC(EEItem.darkMatter.id)) || (AnalyzeTier(this.items[this.eternalDensity], EEMaps.getEMC(EEItem.darkMatter.id))) || (var2 >= EEMaps.getEMC(Item.DIAMOND.id)) || (AnalyzeTier(this.items[this.eternalDensity], EEMaps.getEMC(Item.DIAMOND.id))) || (var2 >= EEMaps.getEMC(Item.GOLD_INGOT.id)) || (AnalyzeTier(this.items[this.eternalDensity], EEMaps.getEMC(Item.GOLD_INGOT.id))) || (var2 >= EEMaps.getEMC(Item.IRON_INGOT.id)) || (!AnalyzeTier(this.items[this.eternalDensity], EEMaps.getEMC(Item.IRON_INGOT.id))));
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean AnalyzeTier(ItemStack var1, int var2)
/*     */   {
/* 349 */     if (var1 == null)
/*     */     {
/* 351 */       return false;
/*     */     }
/*     */ 
/* 355 */     int var3 = 0;
/*     */ 
/* 358 */     for (int var4 = 0; var4 < this.items.length; var4++)
/*     */     {
/* 360 */       if ((this.items[var4] != null) && (isValidMaterial(this.items[var4])) && (EEMaps.getEMC(this.items[var4]) < var2))
/*     */       {
/* 362 */         var3 += EEMaps.getEMC(this.items[var4]) * this.items[var4].count;
/*     */       }
/*     */     }
/*     */ 
/* 366 */     if (var3 + emc(var1) < var2)
/*     */     {
/* 368 */       return false;
/*     */     }
/*     */ 
/* 372 */     var4 = 0;
/*     */ 
/* 374 */     while ((var3 + emc(var1) >= var2) && (var4 < 10))
/*     */     {
/* 376 */       var4++;
/* 377 */       ConsumeMaterialBelowTier(var1, var2);
/*     */     }
/*     */ 
/* 380 */     if ((emc(var1) >= var2) && (roomFor(getProduct(var2))))
/*     */     {
/* 382 */       PushStack(getProduct(var2));
/* 383 */       takeEMC(var1, var2);
/*     */     }
/*     */ 
/* 386 */     return true;
/*     */   }
/*     */ 
/*     */   private boolean roomFor(ItemStack var1)
/*     */   {
/* 393 */     if (var1 == null)
/*     */     {
/* 395 */       return false;
/*     */     }
/*     */ 
/* 399 */     for (int var2 = 0; var2 < this.items.length; var2++)
/*     */     {
/* 401 */       if (this.items[var2] == null)
/*     */       {
/* 403 */         return true;
/*     */       }
/*     */ 
/* 406 */       if ((this.items[var2].doMaterialsMatch(var1)) && (this.items[var2].count <= var1.getMaxStackSize() - var1.count))
/*     */       {
/* 408 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 412 */     return false;
/*     */   }
/*     */ 
/*     */   private ItemStack getProduct(int var1)
/*     */   {
/* 418 */     return var1 == EEMaps.getEMC(EEItem.redMatter.id) ? new ItemStack(EEItem.redMatter, 1) : var1 == EEMaps.getEMC(EEItem.darkMatter.id) ? new ItemStack(EEItem.darkMatter, 1) : var1 == EEMaps.getEMC(Item.DIAMOND.id) ? new ItemStack(Item.DIAMOND, 1) : var1 == EEMaps.getEMC(Item.GOLD_INGOT.id) ? new ItemStack(Item.GOLD_INGOT, 1) : var1 == EEMaps.getEMC(Item.IRON_INGOT.id) ? new ItemStack(Item.IRON_INGOT, 1) : null;
/*     */   }
/*     */ 
/*     */   public boolean PushStack(ItemStack var1)
/*     */   {
/* 423 */     if (var1 == null)
/*     */     {
/* 425 */       return true;
/*     */     }
/*     */ 
/* 429 */     for (int var2 = 0; var2 < this.items.length; var2++)
/*     */     {
/* 431 */       if (this.items[var2] == null)
/*     */       {
/* 433 */         this.items[var2] = var1.cloneItemStack();
/* 434 */         var1 = null;
/* 435 */         return true;
/*     */       }
/*     */ 
/* 438 */       if ((this.items[var2].doMaterialsMatch(var1)) && (this.items[var2].count <= var1.getMaxStackSize() - var1.count))
/*     */       {
/* 440 */         this.items[var2].count += var1.count;
/* 441 */         var1 = null;
/* 442 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 446 */     return false;
/*     */   }
/*     */ 
/*     */   private void ConsumeMaterialBelowTier(ItemStack var1, int var2)
/*     */   {
/* 452 */     for (int var3 = 0; var3 < this.items.length; var3++)
/*     */     {
/* 454 */       if ((this.items[var3] != null) && (isValidMaterial(this.items[var3])) && (EEMaps.getEMC(this.items[var3]) < var2))
/*     */       {
/* 456 */         addEMC(var1, EEMaps.getEMC(this.items[var3]));
/* 457 */         this.items[var3].count -= 1;
/*     */ 
/* 459 */         if (this.items[var3].count == 0)
/*     */         {
/* 461 */           this.items[var3] = null;
/*     */         }
/*     */ 
/* 464 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean isValidMaterial(ItemStack var1)
/*     */   {
/* 471 */     if (var1 == null)
/*     */     {
/* 473 */       return false;
/*     */     }
/* 475 */     if (EEMaps.getEMC(var1) == 0)
/*     */     {
/* 477 */       return false;
/*     */     }
/* 479 */     if ((var1.getItem() instanceof ItemKleinStar))
/*     */     {
/* 481 */       return false;
/*     */     }
/*     */ 
/* 485 */     int var2 = var1.id;
/* 486 */     return var2 != EEItem.redMatter.id;
/*     */   }
/*     */ 
/*     */   private int emc(ItemStack var1)
/*     */   {
/* 492 */     return (var1.getItem() instanceof ItemEternalDensity) ? ((ItemEternalDensity)var1.getItem()).getInteger(var1, "emc") : (!(var1.getItem() instanceof ItemEternalDensity)) && (!(var1.getItem() instanceof ItemVoidRing)) ? 0 : ((ItemVoidRing)var1.getItem()).getInteger(var1, "emc");
/*     */   }
/*     */ 
/*     */   private void takeEMC(ItemStack var1, int var2)
/*     */   {
/* 497 */     if (((var1.getItem() instanceof ItemEternalDensity)) || ((var1.getItem() instanceof ItemVoidRing)))
/*     */     {
/* 499 */       if ((var1.getItem() instanceof ItemEternalDensity))
/*     */       {
/* 501 */         ((ItemEternalDensity)var1.getItem()).setInteger(var1, "emc", emc(var1) - var2);
/*     */       }
/*     */       else
/*     */       {
/* 505 */         ((ItemVoidRing)var1.getItem()).setInteger(var1, "emc", emc(var1) - var2);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addEMC(ItemStack var1, int var2)
/*     */   {
/* 512 */     if (((var1.getItem() instanceof ItemEternalDensity)) || ((var1.getItem() instanceof ItemVoidRing)))
/*     */     {
/* 514 */       if ((var1.getItem() instanceof ItemEternalDensity))
/*     */       {
/* 516 */         ((ItemEternalDensity)var1.getItem()).setInteger(var1, "emc", emc(var1) + var2);
/*     */       }
/*     */       else
/*     */       {
/* 520 */         ((ItemVoidRing)var1.getItem()).setInteger(var1, "emc", emc(var1) + var2);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doAttraction(EntityHuman var1)
/*     */   {
/* 527 */     List var2 = var1.world.a(EntityItem.class, AxisAlignedBB.b(EEBase.playerX(var1) - 10.0D, EEBase.playerY(var1) - 10.0D, EEBase.playerZ(var1) - 10.0D, EEBase.playerX(var1) + 10.0D, EEBase.playerY(var1) + 10.0D, EEBase.playerZ(var1) + 10.0D));
/* 528 */     Iterator var4 = var2.iterator();
/*     */ 
/* 530 */     while (var4.hasNext())
/*     */     {
/* 532 */       Entity var3 = (Entity)var4.next();
/* 533 */       PullItems(var3, var1);
/*     */     }
/*     */ 
/* 536 */     List var14 = var1.world.a(EntityItem.class, AxisAlignedBB.b(EEBase.playerX(var1) - 0.55D, EEBase.playerY(var1) - 0.55D, EEBase.playerZ(var1) - 0.55D, EEBase.playerX(var1) + 0.55D, EEBase.playerY(var1) + 0.55D, EEBase.playerZ(var1) + 0.55D));
/* 537 */     Iterator var6 = var14.iterator();
/*     */ 
/* 539 */     while (var6.hasNext())
/*     */     {
/* 541 */       Entity var5 = (Entity)var6.next();
/* 542 */       GrabItems(var5);
/*     */     }
/*     */ 
/* 545 */     List var15 = var1.world.a(EntityLootBall.class, AxisAlignedBB.b(EEBase.playerX(var1) - 10.0D, EEBase.playerY(var1) - 10.0D, EEBase.playerZ(var1) - 10.0D, EEBase.playerX(var1) + 10.0D, EEBase.playerY(var1) + 10.0D, EEBase.playerZ(var1) + 10.0D));
/* 546 */     Iterator var8 = var15.iterator();
/*     */ 
/* 548 */     while (var8.hasNext())
/*     */     {
/* 550 */       Entity var7 = (Entity)var8.next();
/* 551 */       PullItems(var7, var1);
/*     */     }
/*     */ 
/* 554 */     List var16 = var1.world.a(EntityLootBall.class, AxisAlignedBB.b(EEBase.playerX(var1) - 0.55D, EEBase.playerY(var1) - 0.55D, EEBase.playerZ(var1) - 0.55D, EEBase.playerX(var1) + 0.55D, EEBase.playerY(var1) + 0.55D, EEBase.playerZ(var1) + 0.55D));
/* 555 */     Iterator var10 = var16.iterator();
/*     */ 
/* 557 */     while (var10.hasNext())
/*     */     {
/* 559 */       Entity var9 = (Entity)var10.next();
/* 560 */       GrabItems(var9);
/*     */     }
/*     */ 
/* 563 */     List var13 = var1.world.a(EntityExperienceOrb.class, AxisAlignedBB.b(EEBase.playerX(var1) - 10.0D, EEBase.playerY(var1) - 10.0D, EEBase.playerZ(var1) - 10.0D, EEBase.playerX(var1) + 10.0D, EEBase.playerY(var1) + 10.0D, EEBase.playerZ(var1) + 10.0D));
/* 564 */     Iterator var12 = var13.iterator();
/*     */ 
/* 566 */     while (var12.hasNext())
/*     */     {
/* 568 */       Entity var11 = (Entity)var12.next();
/* 569 */       PullItems(var11, var1);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void PullItems(Entity var1, EntityHuman var2)
/*     */   {
/* 575 */     if (((var1 instanceof EntityItem)) || ((var1 instanceof EntityLootBall)))
/*     */     {
/* 577 */       if ((var1 instanceof EntityLootBall))
/*     */       {
/* 579 */         ((EntityLootBall)var1).setBeingPulled(true);
/*     */       }
/*     */ 
/* 582 */       double var4 = EEBase.playerX(var2) + 0.5D - var1.locX;
/* 583 */       double var6 = EEBase.playerY(var2) + 0.5D - var1.locY;
/* 584 */       double var8 = EEBase.playerZ(var2) + 0.5D - var1.locZ;
/* 585 */       double var10 = var4 * var4 + var6 * var6 + var8 * var8;
/* 586 */       var10 *= var10;
/*     */ 
/* 588 */       if (var10 <= Math.pow(6.0D, 4.0D))
/*     */       {
/* 590 */         double var12 = var4 * 0.01999999955296516D / var10 * Math.pow(6.0D, 3.0D);
/* 591 */         double var14 = var6 * 0.01999999955296516D / var10 * Math.pow(6.0D, 3.0D);
/* 592 */         double var16 = var8 * 0.01999999955296516D / var10 * Math.pow(6.0D, 3.0D);
/*     */ 
/* 594 */         if (var12 > 0.1D)
/*     */         {
/* 596 */           var12 = 0.1D;
/*     */         }
/* 598 */         else if (var12 < -0.1D)
/*     */         {
/* 600 */           var12 = -0.1D;
/*     */         }
/*     */ 
/* 603 */         if (var14 > 0.1D)
/*     */         {
/* 605 */           var14 = 0.1D;
/*     */         }
/* 607 */         else if (var14 < -0.1D)
/*     */         {
/* 609 */           var14 = -0.1D;
/*     */         }
/*     */ 
/* 612 */         if (var16 > 0.1D)
/*     */         {
/* 614 */           var16 = 0.1D;
/*     */         }
/* 616 */         else if (var16 < -0.1D)
/*     */         {
/* 618 */           var16 = -0.1D;
/*     */         }
/*     */ 
/* 621 */         var1.motX += var12 * 1.2D;
/* 622 */         var1.motY += var14 * 1.2D;
/* 623 */         var1.motZ += var16 * 1.2D;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void GrabItems(Entity var1)
/*     */   {
/* 630 */     if ((var1 != null) && ((var1 instanceof EntityItem)))
/*     */     {
/* 632 */       ItemStack var9 = ((EntityItem)var1).itemStack;
/*     */ 
/* 634 */       if (var9 == null)
/*     */       {
/* 636 */         var1.die();
/* 637 */         return;
/*     */       }
/*     */ 
/* 640 */       if ((var9.getItem() instanceof ItemLootBall))
/*     */       {
/* 642 */         ItemLootBall var3 = (ItemLootBall)var9.getItem();
/* 643 */         ItemStack[] var4 = var3.getDroplist(var9);
/* 644 */         ItemStack[] var5 = var4;
/* 645 */         int var6 = var4.length;
/*     */ 
/* 647 */         for (int var7 = 0; var7 < var6; var7++)
/*     */         {
/* 649 */           ItemStack var8 = var5[var7];
/* 650 */           PushStack(var8);
/*     */         }
/*     */ 
/* 653 */         var1.die();
/*     */       }
/*     */       else
/*     */       {
/* 657 */         PushStack(var9);
/* 658 */         var1.die();
/*     */       }
/*     */     }
/* 661 */     else if ((var1 != null) && ((var1 instanceof EntityLootBall)))
/*     */     {
/* 663 */       if (((EntityLootBall)var1).items == null)
/*     */       {
/* 665 */         var1.die();
/*     */       }
/*     */ 
/* 668 */       ItemStack[] var2 = ((EntityLootBall)var1).items;
/* 669 */       PushDenseStacks((EntityLootBall)var1);
/*     */ 
/* 671 */       if (((EntityLootBall)var1).isEmpty())
/*     */       {
/* 673 */         var1.die();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void PushDenseStacks(EntityLootBall var1)
/*     */   {
/* 680 */     for (int var2 = 0; var2 < var1.items.length; var2++)
/*     */     {
/* 682 */       if ((var1.items[var2] != null) && (PushStack(var1.items[var2])))
/*     */       {
/* 684 */         var1.items[var2] = null;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean PushStack(EntityItem var1)
/*     */   {
/* 691 */     if (var1 == null)
/*     */     {
/* 693 */       return false;
/*     */     }
/* 695 */     if (var1.itemStack == null)
/*     */     {
/* 697 */       var1.die();
/* 698 */       return false;
/*     */     }
/* 700 */     if (var1.itemStack.count < 1)
/*     */     {
/* 702 */       var1.die();
/* 703 */       return false;
/*     */     }
/*     */ 
/* 707 */     for (int var2 = 0; var2 < this.items.length; var2++)
/*     */     {
/* 709 */       if (this.items[var2] == null)
/*     */       {
/* 711 */         this.items[var2] = var1.itemStack.cloneItemStack();
/*     */ 
/* 713 */         for (this.items[var2].count = 0; (var1.itemStack.count > 0) && (this.items[var2].count < this.items[var2].getMaxStackSize()); var1.itemStack.count -= 1)
/*     */         {
/* 715 */           this.items[var2].count += 1;
/*     */         }
/*     */ 
/* 718 */         var1.die();
/* 719 */         return true;
/*     */       }
/*     */ 
/* 722 */       if ((this.items[var2].doMaterialsMatch(var1.itemStack)) && (this.items[var2].count <= var1.itemStack.getMaxStackSize() - var1.itemStack.count))
/*     */       {
/* 724 */         while ((var1.itemStack.count > 0) && (this.items[var2].count < this.items[var2].getMaxStackSize()))
/*     */         {
/* 726 */           this.items[var2].count += 1;
/* 727 */           var1.itemStack.count -= 1;
/*     */         }
/*     */ 
/* 730 */         var1.die();
/* 731 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 735 */     return false;
/*     */   }
/*     */ 
/*     */   private void PushDenseStacks(EntityLootBall var1, EntityHuman var2)
/*     */   {
/* 741 */     for (int var3 = 0; var3 < var1.items.length; var3++)
/*     */     {
/* 743 */       if (var1.items[var3] != null)
/*     */       {
/* 745 */         PushStack(var1.items[var3], var2);
/* 746 */         var1.items[var3] = null;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void PushStack(ItemStack var1, EntityHuman var2)
/*     */   {
/* 755 */     for (int var3 = 0; var3 < getSize(); var3++)
/*     */     {
/* 757 */       if (var1 != null)
/*     */       {
/* 759 */         if (this.items[var3] == null)
/*     */         {
/* 761 */           this.items[var3] = var1.cloneItemStack();
/* 762 */           var1 = null;
/* 763 */           this.markForUpdate = true;
/* 764 */           return;
/*     */         }
/*     */ 
/* 767 */         if (this.items[var3].doMaterialsMatch(var1))
/*     */         {
/*     */           do
/*     */           {
/* 771 */             this.items[var3].count += 1;
/* 772 */             var1.count -= 1;
/*     */ 
/* 774 */             if (var1.count == 0)
/*     */             {
/* 776 */               var1 = null;
/* 777 */               this.markForUpdate = true;
/*     */               return;
/*     */             }
/* 769 */             if (this.items[var3].count >= this.items[var3].getMaxStackSize()) break;  } while (var1 != null);
/*     */         }
/* 782 */         else if (var3 == this.items.length - 1)
/*     */         {
/* 784 */           EntityItem var4 = new EntityItem(var2.world, EEBase.playerX(var2), EEBase.playerY(var2), EEBase.playerZ(var2), var1);
/* 785 */           var4.pickupDelay = 1;
/* 786 */           var2.world.addEntity(var4);
/* 787 */           this.markForUpdate = true;
/* 788 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 793 */     if (var1 != null)
/*     */     {
/* 795 */       for (var3 = 0; var3 < this.items.length; var3++)
/*     */       {
/* 797 */         if (this.items[var3] == null)
/*     */         {
/* 799 */           this.items[var3] = var1.cloneItemStack();
/* 800 */           var1 = null;
/* 801 */           this.markForUpdate = true;
/* 802 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean a(EntityHuman var1)
/*     */   {
/* 813 */     return true;
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
/*     */   public void a(NBTTagCompound var1) {
/* 825 */     this.voidOn = var1.getBoolean("voidOn");
/* 826 */     this.repairOn = var1.getBoolean("repairOn");
/* 827 */     this.condenseOn = var1.getBoolean("condenseOn");
/* 828 */     this.eternalDensity = var1.getShort("eternalDensity");
/* 829 */     NBTTagList var2 = var1.getList("Items");
/* 830 */     this.items = new ItemStack[113];
/*     */ 
/* 832 */     for (int var3 = 0; var3 < var2.size(); var3++)
/*     */     {
/* 834 */       NBTTagCompound var4 = (NBTTagCompound)var2.get(var3);
/* 835 */       int var5 = var4.getByte("Slot") & 0xFF;
/*     */ 
/* 837 */       if ((var5 >= 0) && (var5 < this.items.length))
/*     */       {
/* 839 */         this.items[var5] = ItemStack.a(var4);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void b(NBTTagCompound var1)
/*     */   {
/* 849 */     var1.setBoolean("voidOn", this.voidOn);
/* 850 */     var1.setBoolean("repairOn", this.repairOn);
/* 851 */     var1.setBoolean("condenseOn", this.condenseOn);
/* 852 */     var1.setShort("eternalDensity", (short)this.eternalDensity);
/* 853 */     NBTTagList var2 = new NBTTagList();
/*     */ 
/* 855 */     for (int var3 = 0; var3 < this.items.length; var3++)
/*     */     {
/* 857 */       if (this.items[var3] != null)
/*     */       {
/* 859 */         NBTTagCompound var4 = new NBTTagCompound();
/* 860 */         var4.setByte("Slot", (byte)var3);
/* 861 */         this.items[var3].save(var4);
/* 862 */         var2.add(var4);
/*     */       }
/*     */     }
/*     */ 
/* 866 */     var1.set("Items", var2);
/*     */   }
/*     */ 
/*     */   public ItemStack splitWithoutUpdate(int var1)
/*     */   {
/* 875 */     return null;
/*     */   }
/*     */ 
/*     */   public ItemStack[] getContents()
/*     */   {
/* 880 */     return this.items;
/*     */   }
/*     */ 
/*     */   public void onOpen(CraftHumanEntity who)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void onClose(CraftHumanEntity who)
/*     */   {
/*     */   }
/*     */ 
/*     */   public List<HumanEntity> getViewers()
/*     */   {
/* 895 */     return new ArrayList(0);
/*     */   }
/*     */ 
/*     */   public InventoryHolder getOwner()
/*     */   {
/* 900 */     return null;
/*     */   }
/*     */ 
/*     */   public void setMaxStackSize(int size)
/*     */   {
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.AlchemyBagData
 * JD-Core Version:    0.6.2
 */