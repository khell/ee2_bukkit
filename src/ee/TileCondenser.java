/*      */ package ee;
/*      */ 
/*      */ import buildcraft.api.ISpecialInventory;
/*      */ import buildcraft.api.Orientations;
/*      */ import ee.core.GuiIds;
/*      */ import ee.item.ItemLootBall;
/*      */ import forge.ISidedInventory;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import net.minecraft.server.AxisAlignedBB;
/*      */ import net.minecraft.server.Entity;
/*      */ import net.minecraft.server.EntityHuman;
/*      */ import net.minecraft.server.EntityItem;
/*      */ import net.minecraft.server.Item;
/*      */ import net.minecraft.server.ItemStack;
/*      */ import net.minecraft.server.NBTTagCompound;
/*      */ import net.minecraft.server.NBTTagList;
/*      */ import net.minecraft.server.TileEntity;
/*      */ import net.minecraft.server.TileEntityChest;
/*      */ import net.minecraft.server.World;
/*      */ import net.minecraft.server.mod_EE;
/*      */ 
/*      */ public class TileCondenser extends TileEE
/*      */   implements ISpecialInventory, ISidedInventory, IEEPowerNet
/*      */ {
/*   27 */   private ItemStack[] items = new ItemStack[92];
/*   28 */   public int scaledEnergy = 0;
/*      */   public float lidAngle;
/*      */   public float prevLidAngle;
/*      */   public int numUsingPlayers;
/*      */   private int ticksSinceSync;
/*      */   private int eternalDensity;
/*      */   private boolean condenseOn;
/*      */   private boolean initialized;
/*   36 */   public int displayEnergy = 0;
/*   37 */   public int currentItemProgress = 0;
/*      */   private boolean attractionOn;
/*      */ 
/*      */   private boolean isChest(TileEntity var1)
/*      */   {
/*   42 */     return ((var1 instanceof TileEntityChest)) || ((var1 instanceof TileAlchChest));
/*      */   }
/*      */ 
/*      */   public void onBlockRemoval()
/*      */   {
/*   47 */     for (int var1 = 0; var1 < getSize(); var1++)
/*      */     {
/*   49 */       ItemStack var2 = getItem(var1);
/*      */ 
/*   51 */       if (var2 != null)
/*      */       {
/*   53 */         float var3 = this.world.random.nextFloat() * 0.8F + 0.1F;
/*   54 */         float var4 = this.world.random.nextFloat() * 0.8F + 0.1F;
/*   55 */         float var5 = this.world.random.nextFloat() * 0.8F + 0.1F;
/*      */ 
/*   57 */         while (var2.count > 0)
/*      */         {
/*   59 */           int var6 = this.world.random.nextInt(21) + 10;
/*      */ 
/*   61 */           if (var6 > var2.count)
/*      */           {
/*   63 */             var6 = var2.count;
/*      */           }
/*      */ 
/*   66 */           var2.count -= var6;
/*   67 */           EntityItem var7 = new EntityItem(this.world, this.x + var3, this.y + var4, this.z + var5, new ItemStack(var2.id, var6, var2.getData()));
/*      */ 
/*   69 */           if (var7 != null)
/*      */           {
/*   71 */             float var8 = 0.05F;
/*   72 */             var7.motX = ((float)this.world.random.nextGaussian() * var8);
/*   73 */             var7.motY = ((float)this.world.random.nextGaussian() * var8 + 0.2F);
/*   74 */             var7.motZ = ((float)this.world.random.nextGaussian() * var8);
/*      */ 
/*   76 */             if ((var7.itemStack.getItem() instanceof ItemKleinStar))
/*      */             {
/*   78 */               ((ItemKleinStar)var7.itemStack.getItem()).setKleinPoints(var7.itemStack, ((ItemKleinStar)var2.getItem()).getKleinPoints(var2));
/*      */             }
/*      */ 
/*   81 */             this.world.addEntity(var7);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static boolean putInChest(TileEntity var0, ItemStack var1)
/*      */   {
/*   90 */     if ((var1 != null) && (var1.id != 0))
/*      */     {
/*   92 */       if (var0 == null)
/*      */       {
/*   94 */         return false;
/*      */       }
/*      */ 
/*  101 */       if ((var0 instanceof TileEntityChest))
/*      */       {
/*  103 */         for (int var2 = 0; var2 < ((TileEntityChest)var0).getSize(); var2++)
/*      */         {
/*  105 */           ItemStack var3 = ((TileEntityChest)var0).getItem(var2);
/*      */ 
/*  107 */           if ((var3 != null) && (var3.doMaterialsMatch(var1)) && (var3.count + var1.count <= var3.getMaxStackSize()))
/*      */           {
/*  109 */             var3.count += var1.count;
/*  110 */             return true;
/*      */           }
/*      */         }
/*      */ 
/*  114 */         for (var2 = 0; var2 < ((TileEntityChest)var0).getSize(); var2++)
/*      */         {
/*  116 */           if (((TileEntityChest)var0).getItem(var2) == null)
/*      */           {
/*  118 */             ((TileEntityChest)var0).setItem(var2, var1);
/*  119 */             return true;
/*      */           }
/*      */         }
/*      */       }
/*  123 */       else if ((var0 instanceof TileAlchChest))
/*      */       {
/*  125 */         for (int var2 = 0; var2 < ((TileAlchChest)var0).getSize(); var2++)
/*      */         {
/*  127 */           ItemStack var3 = ((TileAlchChest)var0).getItem(var2);
/*      */ 
/*  129 */           if ((var3 != null) && (var3.doMaterialsMatch(var1)) && (var3.count + var1.count <= var3.getMaxStackSize()) && (var3.getData() == var1.getData()))
/*      */           {
/*  131 */             var3.count += var1.count;
/*  132 */             return true;
/*      */           }
/*      */         }
/*      */ 
/*  136 */         for (var2 = 0; var2 < ((TileAlchChest)var0).getSize(); var2++)
/*      */         {
/*  138 */           if (((TileAlchChest)var0).getItem(var2) == null)
/*      */           {
/*  140 */             ((TileAlchChest)var0).setItem(var2, var1);
/*  141 */             return true;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  146 */       return false;
/*      */     }
/*      */ 
/*  151 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean tryDropInChest(ItemStack var1)
/*      */   {
/*  157 */     TileEntity var2 = null;
/*      */ 
/*  159 */     if (isChest(this.world.getTileEntity(this.x, this.y + 1, this.z)))
/*      */     {
/*  161 */       var2 = this.world.getTileEntity(this.x, this.y + 1, this.z);
/*  162 */       return putInChest(var2, var1);
/*      */     }
/*  164 */     if (isChest(this.world.getTileEntity(this.x, this.y - 1, this.z)))
/*      */     {
/*  166 */       var2 = this.world.getTileEntity(this.x, this.y - 1, this.z);
/*  167 */       return putInChest(var2, var1);
/*      */     }
/*  169 */     if (isChest(this.world.getTileEntity(this.x + 1, this.y, this.z)))
/*      */     {
/*  171 */       var2 = this.world.getTileEntity(this.x + 1, this.y, this.z);
/*  172 */       return putInChest(var2, var1);
/*      */     }
/*  174 */     if (isChest(this.world.getTileEntity(this.x - 1, this.y, this.z)))
/*      */     {
/*  176 */       var2 = this.world.getTileEntity(this.x - 1, this.y, this.z);
/*  177 */       return putInChest(var2, var1);
/*      */     }
/*  179 */     if (isChest(this.world.getTileEntity(this.x, this.y, this.z + 1)))
/*      */     {
/*  181 */       var2 = this.world.getTileEntity(this.x, this.y, this.z + 1);
/*  182 */       return putInChest(var2, var1);
/*      */     }
/*  184 */     if (isChest(this.world.getTileEntity(this.x, this.y, this.z - 1)))
/*      */     {
/*  186 */       var2 = this.world.getTileEntity(this.x, this.y, this.z - 1);
/*  187 */       return putInChest(var2, var1);
/*      */     }
/*      */ 
/*  191 */     return false;
/*      */   }
/*      */ 
/*      */   public void doCondense(ItemStack var1)
/*      */   {
/*  197 */     if (this.eternalDensity != -1)
/*      */     {
/*  199 */       int var2 = 0;
/*      */ 
/*  202 */       for (int var3 = 1; var3 < this.items.length; var3++)
/*      */       {
/*  204 */         if ((this.items[var3] != null) && (isValidMaterial(this.items[var3])) && (EEMaps.getEMC(this.items[var3]) > var2))
/*      */         {
/*  206 */           var2 = EEMaps.getEMC(this.items[var3]);
/*      */         }
/*      */       }
/*      */ 
/*  210 */       for (var3 = 1; var3 < this.items.length; var3++)
/*      */       {
/*  212 */         if ((this.items[var3] != null) && (isValidMaterial(this.items[var3])) && (EEMaps.getEMC(this.items[var3]) < var2))
/*      */         {
/*  214 */           var2 = EEMaps.getEMC(this.items[var3]);
/*      */         }
/*      */       }
/*      */ 
/*  218 */       if ((var2 >= EEMaps.getEMC(EEItem.redMatter.id)) || (AnalyzeTier(this.items[this.eternalDensity], EEMaps.getEMC(EEItem.redMatter.id))) || (var2 >= EEMaps.getEMC(EEItem.darkMatter.id)) || (AnalyzeTier(this.items[this.eternalDensity], EEMaps.getEMC(EEItem.darkMatter.id))) || (var2 >= EEMaps.getEMC(Item.DIAMOND.id)) || (AnalyzeTier(this.items[this.eternalDensity], EEMaps.getEMC(Item.DIAMOND.id))) || (var2 >= EEMaps.getEMC(Item.GOLD_INGOT.id)) || (AnalyzeTier(this.items[this.eternalDensity], EEMaps.getEMC(Item.GOLD_INGOT.id))) || (var2 >= EEMaps.getEMC(Item.IRON_INGOT.id)) || (!AnalyzeTier(this.items[this.eternalDensity], EEMaps.getEMC(Item.IRON_INGOT.id))));
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean AnalyzeTier(ItemStack var1, int var2)
/*      */   {
/*  227 */     if (var1 == null)
/*      */     {
/*  229 */       return false;
/*      */     }
/*      */ 
/*  233 */     int var3 = 0;
/*      */ 
/*  236 */     for (int var4 = 0; var4 < this.items.length; var4++)
/*      */     {
/*  238 */       if ((this.items[var4] != null) && (isValidMaterial(this.items[var4])) && (EEMaps.getEMC(this.items[var4]) < var2))
/*      */       {
/*  240 */         var3 += EEMaps.getEMC(this.items[var4]) * this.items[var4].count;
/*      */       }
/*      */     }
/*      */ 
/*  244 */     if (var3 + emc(var1) < var2)
/*      */     {
/*  246 */       return false;
/*      */     }
/*      */ 
/*  250 */     var4 = 0;
/*      */ 
/*  252 */     while ((var3 + emc(var1) >= var2) && (var4 < 10))
/*      */     {
/*  254 */       var4++;
/*  255 */       ConsumeMaterialBelowTier(var1, var2);
/*      */     }
/*      */ 
/*  258 */     if ((emc(var1) >= var2) && (roomFor(getProduct(var2))))
/*      */     {
/*  260 */       PushStack(getProduct(var2));
/*  261 */       takeEMC(var1, var2);
/*      */     }
/*      */ 
/*  264 */     return true;
/*      */   }
/*      */ 
/*      */   private boolean roomFor(ItemStack var1)
/*      */   {
/*  271 */     if (var1 == null)
/*      */     {
/*  273 */       return false;
/*      */     }
/*      */ 
/*  277 */     for (int var2 = 1; var2 < this.items.length; var2++)
/*      */     {
/*  279 */       if (this.items[var2] == null)
/*      */       {
/*  281 */         return true;
/*      */       }
/*      */ 
/*  284 */       if ((this.items[var2].doMaterialsMatch(var1)) && (this.items[var2].count <= var1.getMaxStackSize() - var1.count))
/*      */       {
/*  286 */         return true;
/*      */       }
/*      */     }
/*      */ 
/*  290 */     return false;
/*      */   }
/*      */ 
/*      */   private ItemStack getProduct(int var1)
/*      */   {
/*  296 */     return var1 == EEMaps.getEMC(EEItem.redMatter.id) ? new ItemStack(EEItem.redMatter, 1) : var1 == EEMaps.getEMC(EEItem.darkMatter.id) ? new ItemStack(EEItem.darkMatter, 1) : var1 == EEMaps.getEMC(Item.DIAMOND.id) ? new ItemStack(Item.DIAMOND, 1) : var1 == EEMaps.getEMC(Item.GOLD_INGOT.id) ? new ItemStack(Item.GOLD_INGOT, 1) : var1 == EEMaps.getEMC(Item.IRON_INGOT.id) ? new ItemStack(Item.IRON_INGOT, 1) : null;
/*      */   }
/*      */ 
/*      */   private void ConsumeMaterialBelowTier(ItemStack var1, int var2)
/*      */   {
/*  301 */     for (int var3 = 1; var3 < this.items.length; var3++)
/*      */     {
/*  303 */       if ((this.items[var3] != null) && (isValidMaterial(this.items[var3])) && (EEMaps.getEMC(this.items[var3]) < var2))
/*      */       {
/*  305 */         addEMC(var1, EEMaps.getEMC(this.items[var3]));
/*  306 */         this.items[var3].count -= 1;
/*      */ 
/*  308 */         if (this.items[var3].count == 0)
/*      */         {
/*  310 */           this.items[var3] = null;
/*      */         }
/*      */ 
/*  313 */         return;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean isValidMaterial(ItemStack var1)
/*      */   {
/*  320 */     if (var1 == null)
/*      */     {
/*  322 */       return false;
/*      */     }
/*  324 */     if (EEMaps.getEMC(var1) == 0)
/*      */     {
/*  326 */       return false;
/*      */     }
/*  328 */     if ((var1.getItem() instanceof ItemKleinStar))
/*      */     {
/*  330 */       return false;
/*      */     }
/*      */ 
/*  334 */     int var2 = var1.id;
/*  335 */     return var2 != EEItem.redMatter.id;
/*      */   }
/*      */ 
/*      */   private int emc(ItemStack var1)
/*      */   {
/*  341 */     return (var1.getItem() instanceof ItemEternalDensity) ? ((ItemEternalDensity)var1.getItem()).getInteger(var1, "emc") : (!(var1.getItem() instanceof ItemEternalDensity)) && (!(var1.getItem() instanceof ItemVoidRing)) ? 0 : ((ItemVoidRing)var1.getItem()).getInteger(var1, "emc");
/*      */   }
/*      */ 
/*      */   private void takeEMC(ItemStack var1, int var2)
/*      */   {
/*  346 */     if (((var1.getItem() instanceof ItemEternalDensity)) || ((var1.getItem() instanceof ItemVoidRing)))
/*      */     {
/*  348 */       if ((var1.getItem() instanceof ItemEternalDensity))
/*      */       {
/*  350 */         ((ItemEternalDensity)var1.getItem()).setInteger(var1, "emc", emc(var1) - var2);
/*      */       }
/*      */       else
/*      */       {
/*  354 */         ((ItemVoidRing)var1.getItem()).setInteger(var1, "emc", emc(var1) - var2);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addEMC(ItemStack var1, int var2)
/*      */   {
/*  361 */     if (((var1.getItem() instanceof ItemEternalDensity)) || ((var1.getItem() instanceof ItemVoidRing)))
/*      */     {
/*  363 */       if ((var1.getItem() instanceof ItemEternalDensity))
/*      */       {
/*  365 */         ((ItemEternalDensity)var1.getItem()).setInteger(var1, "emc", emc(var1) + var2);
/*      */       }
/*      */       else
/*      */       {
/*  369 */         ((ItemVoidRing)var1.getItem()).setInteger(var1, "emc", emc(var1) + var2);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public ItemStack target()
/*      */   {
/*  376 */     return this.items[0];
/*      */   }
/*      */ 
/*      */   public int getTargetValue(ItemStack var1)
/*      */   {
/*  381 */     return EEMaps.getEMC(var1.id, var1.getData()) == 0 ? EEMaps.getEMC(var1.id) : var1.d() ? EEMaps.getEMC(var1.id) * (int)((var1.i() - var1.getData()) / var1.i()) : var1 == null ? 0 : EEMaps.getEMC(var1.id, var1.getData());
/*      */   }
/*      */ 
/*      */   public boolean canCondense()
/*      */   {
/*  386 */     return target() != null;
/*      */   }
/*      */ 
/*      */   public boolean isInventoryFull()
/*      */   {
/*  391 */     for (int var1 = 0; var1 < this.items.length; var1++)
/*      */     {
/*  393 */       if (this.items[var1] == null)
/*      */       {
/*  395 */         return false;
/*      */       }
/*      */     }
/*      */ 
/*  399 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean receiveEnergy(int var1, byte var2, boolean var3)
/*      */   {
/*  404 */     if ((canCondense()) && (this.scaledEnergy + var1 <= 800000000))
/*      */     {
/*  406 */       if (var3)
/*      */       {
/*  408 */         this.scaledEnergy += var1;
/*      */       }
/*      */ 
/*  411 */       return true;
/*      */     }
/*      */ 
/*  415 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean sendEnergy(int var1, byte var2, boolean var3)
/*      */   {
/*  421 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean passEnergy(int var1, byte var2, boolean var3)
/*      */   {
/*  426 */     return false;
/*      */   }
/*      */ 
/*      */   public void sendAllPackets(int var1) {
/*      */   }
/*      */ 
/*      */   public int relayBonus() {
/*  433 */     return 0;
/*      */   }
/*      */ 
/*      */   public int getSize()
/*      */   {
/*  441 */     return this.items.length;
/*      */   }
/*      */ 
/*      */   public int getMaxStackSize()
/*      */   {
/*  450 */     return 64;
/*      */   }
/*      */ 
/*      */   public boolean addItem(ItemStack var1, boolean var2, Orientations var3)
/*      */   {
/*  455 */     switch (var3)
/*      */     {
/*      */     case Unknown:
/*      */     case XNeg:
/*      */     case XPos:
/*      */     case YNeg:
/*      */     case YPos:
/*      */     case ZNeg:
/*      */     case ZPos:
/*  465 */       if (var1 != null)
/*      */       {
/*  467 */         for (int var4 = 1; var4 < this.items.length; var4++)
/*      */         {
/*  469 */           if (this.items[var4] == null)
/*      */           {
/*  471 */             if (var2)
/*      */             {
/*  473 */               for (this.items[var4] = var1.cloneItemStack(); var1.count > 0; var1.count -= 1);
/*      */             }
/*      */ 
/*  479 */             return true;
/*      */           }
/*      */ 
/*  482 */           if ((this.items[var4].doMaterialsMatch(var1)) && (this.items[var4].count < this.items[var4].getMaxStackSize()))
/*      */           {
/*  484 */             if (var2)
/*      */             {
/*  486 */               while ((this.items[var4].count < this.items[var4].getMaxStackSize()) && (var1.count > 0))
/*      */               {
/*  488 */                 this.items[var4].count += 1;
/*  489 */                 var1.count -= 1;
/*      */               }
/*      */ 
/*  492 */               if (var1.count != 0);
/*      */             }
/*      */             else
/*      */             {
/*  498 */               return true;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       break;
/*      */     }
/*  504 */     return false;
/*      */   }
/*      */ 
/*      */   public ItemStack extractItem(boolean var1, Orientations var2)
/*      */   {
/*  510 */     switch (var2)
/*      */     {
/*      */     case Unknown:
/*      */     case XNeg:
/*      */     case XPos:
/*      */     case YNeg:
/*      */     case YPos:
/*      */     case ZNeg:
/*      */     case ZPos:
/*  520 */       for (int var3 = 1; var3 < this.items.length; var3++)
/*      */       {
/*  522 */         if ((this.items[var3] != null) && ((target() == null) || (this.items[var3].doMaterialsMatch(target()))))
/*      */         {
/*  524 */           ItemStack var4 = this.items[var3].cloneItemStack();
/*  525 */           var4.count = 1;
/*      */ 
/*  527 */           if (var1)
/*      */           {
/*  529 */             this.items[var3].count -= 1;
/*      */ 
/*  531 */             if (this.items[var3].count < 1)
/*      */             {
/*  533 */               this.items[var3] = null;
/*      */             }
/*      */           }
/*      */ 
/*  537 */           return var4;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  542 */     return null;
/*      */   }
/*      */ 
/*      */   public String getName()
/*      */   {
/*  551 */     return "Condenser";
/*      */   }
/*      */ 
/*      */   public void a(NBTTagCompound var1)
/*      */   {
/*  559 */     super.a(var1);
/*  560 */     NBTTagList var2 = var1.getList("Items");
/*  561 */     this.items = new ItemStack[getSize()];
/*      */ 
/*  563 */     for (int var3 = 0; var3 < var2.size(); var3++)
/*      */     {
/*  565 */       NBTTagCompound var4 = (NBTTagCompound)var2.get(var3);
/*  566 */       byte var5 = var4.getByte("Slot");
/*      */ 
/*  568 */       if ((var5 >= 0) && (var5 < this.items.length))
/*      */       {
/*  570 */         this.items[var5] = ItemStack.a(var4);
/*      */       }
/*      */     }
/*      */ 
/*  574 */     this.scaledEnergy = var1.getInt("scaledEnergy");
/*  575 */     this.eternalDensity = var1.getShort("eternalDensity");
/*      */   }
/*      */ 
/*      */   public void b(NBTTagCompound var1)
/*      */   {
/*  583 */     super.b(var1);
/*  584 */     var1.setInt("scaledEnergy", this.scaledEnergy);
/*  585 */     var1.setShort("eternalDensity", (short)this.eternalDensity);
/*  586 */     NBTTagList var2 = new NBTTagList();
/*      */ 
/*  588 */     for (int var3 = 0; var3 < this.items.length; var3++)
/*      */     {
/*  590 */       if (this.items[var3] != null)
/*      */       {
/*  592 */         NBTTagCompound var4 = new NBTTagCompound();
/*  593 */         var4.setByte("Slot", (byte)var3);
/*  594 */         this.items[var3].save(var4);
/*  595 */         var2.add(var4);
/*      */       }
/*      */     }
/*      */ 
/*  599 */     var1.set("Items", var2);
/*      */   }
/*      */ 
/*      */   public ItemStack getItem(int var1)
/*      */   {
/*  607 */     return this.items[var1];
/*      */   }
/*      */ 
/*      */   public ItemStack splitStack(int var1, int var2)
/*      */   {
/*  616 */     if (this.items[var1] != null)
/*      */     {
/*  620 */       if (this.items[var1].count <= var2)
/*      */       {
/*  622 */         ItemStack var3 = this.items[var1];
/*  623 */         this.items[var1] = null;
/*  624 */         return var3;
/*      */       }
/*      */ 
/*  628 */       ItemStack var3 = this.items[var1].a(var2);
/*      */ 
/*  630 */       if (this.items[var1].count == 0)
/*      */       {
/*  632 */         this.items[var1] = null;
/*      */       }
/*      */ 
/*  635 */       return var3;
/*      */     }
/*      */ 
/*  640 */     return null;
/*      */   }
/*      */ 
/*      */   public void setItem(int var1, ItemStack var2)
/*      */   {
/*  649 */     this.items[var1] = var2;
/*      */ 
/*  651 */     if ((var2 != null) && (var2.count > getMaxStackSize()))
/*      */     {
/*  653 */       var2.count = getMaxStackSize();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void update()
/*      */   {
/*  662 */     super.update();
/*  663 */     boolean var1 = false;
/*  664 */     boolean var2 = false;
/*      */ 
/*  666 */     for (int var3 = 0; var3 < getSize(); var3++)
/*      */     {
/*  668 */       if (this.items[var3] != null)
/*      */       {
/*  670 */         if ((this.items[var3].getItem() instanceof ItemVoidRing))
/*      */         {
/*  672 */           this.eternalDensity = var3;
/*      */ 
/*  674 */           if ((this.items[var3].getData() & 0x1) == 0)
/*      */           {
/*  676 */             this.items[var3].setData(this.items[var3].getData() + 1);
/*  677 */             ((ItemEECharged)this.items[var3].getItem()).setBoolean(this.items[var3], "active", true);
/*      */           }
/*      */ 
/*  680 */           var1 = true;
/*  681 */           var2 = true;
/*      */         }
/*      */ 
/*  684 */         if (this.items[var3].getItem().id == EEItem.eternalDensity.id)
/*      */         {
/*  686 */           this.eternalDensity = var3;
/*      */ 
/*  688 */           if ((this.items[var3].getData() & 0x1) == 0)
/*      */           {
/*  690 */             this.items[var3].setData(this.items[var3].getData() + 1);
/*  691 */             ((ItemEECharged)this.items[var3].getItem()).setBoolean(this.items[var3], "active", true);
/*      */           }
/*      */ 
/*  694 */           var1 = true;
/*      */         }
/*      */ 
/*  697 */         if ((this.items[var3].getItem() instanceof ItemAttractionRing))
/*      */         {
/*  699 */           if ((this.items[var3].getData() & 0x1) == 0)
/*      */           {
/*  701 */             this.items[var3].setData(this.items[var3].getData() + 1);
/*  702 */             ((ItemEECharged)this.items[var3].getItem()).setBoolean(this.items[var3], "active", true);
/*      */           }
/*      */ 
/*  705 */           var2 = true;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  710 */     if (var1 != this.condenseOn)
/*      */     {
/*  712 */       this.condenseOn = var1;
/*      */     }
/*      */ 
/*  715 */     if (var2 != this.attractionOn)
/*      */     {
/*  717 */       this.attractionOn = var2;
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getCondenserProgressScaled(int var1)
/*      */   {
/*  723 */     return this.scaledEnergy / 80 > getTargetValue(target()) ? var1 : getTargetValue(target()) == 0 ? 0 : this.scaledEnergy / 80 * var1 / getTargetValue(target());
/*      */   }
/*      */ 
/*      */   public boolean isValidTarget()
/*      */   {
/*  728 */     return EEMaps.getEMC(this.items[0].id, this.items[0].getData()) != 0;
/*      */   }
/*      */ 
/*      */   public void q_()
/*      */   {
/*  737 */     this.currentItemProgress = getCondenserProgressScaled(102);
/*  738 */     this.displayEnergy = latentEnergy();
/*      */ 
/*  740 */     if (!this.initialized)
/*      */     {
/*  742 */       this.initialized = true;
/*  743 */       update();
/*      */     }
/*      */ 
/*  746 */     if (++this.ticksSinceSync % 20 * 4 == 0)
/*      */     {
/*  748 */       this.world.playNote(this.x, this.y, this.z, 1, this.numUsingPlayers);
/*      */     }
/*      */ 
/*  751 */     this.prevLidAngle = this.lidAngle;
/*  752 */     float var1 = 0.1F;
/*      */ 
/*  755 */     if ((this.numUsingPlayers > 0) && (this.lidAngle == 0.0F))
/*      */     {
/*  757 */       double var4 = this.x + 0.5D;
/*  758 */       double var2 = this.z + 0.5D;
/*  759 */       this.world.makeSound(var4, this.y + 0.5D, var2, "random.chestopen", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
/*      */     }
/*      */ 
/*  762 */     if (((this.numUsingPlayers == 0) && (this.lidAngle > 0.0F)) || ((this.numUsingPlayers > 0) && (this.lidAngle < 1.0F)))
/*      */     {
/*  764 */       float var8 = this.lidAngle;
/*      */ 
/*  766 */       if (this.numUsingPlayers > 0)
/*      */       {
/*  768 */         this.lidAngle += var1;
/*      */       }
/*      */       else
/*      */       {
/*  772 */         this.lidAngle -= var1;
/*      */       }
/*      */ 
/*  775 */       if (this.lidAngle > 1.0F)
/*      */       {
/*  777 */         this.lidAngle = 1.0F;
/*      */       }
/*      */ 
/*  780 */       float var5 = 0.5F;
/*      */ 
/*  782 */       if ((this.lidAngle < var5) && (var8 >= var5))
/*      */       {
/*  784 */         double var2 = this.x + 0.5D;
/*  785 */         double var6 = this.z + 0.5D;
/*  786 */         this.world.makeSound(var2, this.y + 0.5D, var6, "random.chestclosed", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
/*      */       }
/*      */ 
/*  789 */       if (this.lidAngle < 0.0F)
/*      */       {
/*  791 */         this.lidAngle = 0.0F;
/*      */       }
/*      */     }
/*      */ 
/*  795 */     if (canCondense())
/*      */     {
/*  797 */       while ((this.scaledEnergy >= getTargetValue(target()) * 80) && (roomFor(new ItemStack(target().id, 1, target().getData()))))
/*      */       {
/*  799 */         this.scaledEnergy -= getTargetValue(target()) * 80;
/*  800 */         PushStack(new ItemStack(target().id, 1, target().getData()));
/*      */       }
/*      */ 
/*  803 */       for (int var9 = 1; var9 < this.items.length; var9++)
/*      */       {
/*  805 */         if ((this.items[var9] != null) && (EEMaps.getEMC(this.items[var9]) != 0) && (!this.items[var9].doMaterialsMatch(target())) && (!(this.items[var9].getItem() instanceof ItemKleinStar)) && (this.scaledEnergy + EEMaps.getEMC(this.items[var9]) * 80 <= 800000000))
/*      */         {
/*  807 */           this.scaledEnergy += EEMaps.getEMC(this.items[var9]) * 80;
/*  808 */           this.items[var9].count -= 1;
/*      */ 
/*  810 */           if (this.items[var9].count != 0)
/*      */             break;
/*  812 */           this.items[var9] = null;
/*      */ 
/*  815 */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  820 */     if ((this.condenseOn) && (this.eternalDensity >= 0))
/*      */     {
/*  822 */       doCondense(this.items[this.eternalDensity]);
/*      */     }
/*      */ 
/*  825 */     if (this.attractionOn)
/*      */     {
/*  827 */       doAttraction();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void doAttraction()
/*      */   {
/*  833 */     List var1 = this.world.a(EntityLootBall.class, AxisAlignedBB.b(this.x - 10, this.y - 10, this.z - 10, this.x + 10, this.y + 10, this.z + 10));
/*  834 */     Iterator var3 = var1.iterator();
/*      */ 
/*  836 */     while (var3.hasNext())
/*      */     {
/*  838 */       Entity var2 = (Entity)var3.next();
/*  839 */       PullItems(var2);
/*      */     }
/*      */ 
/*  842 */     List var12 = this.world.a(EntityLootBall.class, AxisAlignedBB.b(this.x - 10, this.y - 10, this.z - 10, this.x + 10, this.y + 10, this.z + 10));
/*  843 */     Iterator var5 = var12.iterator();
/*      */ 
/*  845 */     while (var5.hasNext())
/*      */     {
/*  847 */       Entity var4 = (Entity)var5.next();
/*  848 */       PullItems(var4);
/*      */     }
/*      */ 
/*  851 */     List var13 = this.world.a(EntityItem.class, AxisAlignedBB.b(this.x - 10, this.y - 10, this.z - 10, this.x + 10, this.y + 10, this.z + 10));
/*  852 */     Iterator var7 = var13.iterator();
/*      */ 
/*  854 */     while (var7.hasNext())
/*      */     {
/*  856 */       Entity var6 = (Entity)var7.next();
/*  857 */       PullItems(var6);
/*      */     }
/*      */ 
/*  860 */     List var14 = this.world.a(EntityLootBall.class, AxisAlignedBB.b(this.x - 0.5D, this.y - 0.5D, this.z - 0.5D, this.x + 1.25D, this.y + 1.25D, this.z + 1.25D));
/*  861 */     Iterator var9 = var14.iterator();
/*      */ 
/*  863 */     while (var9.hasNext())
/*      */     {
/*  865 */       Entity var8 = (Entity)var9.next();
/*  866 */       GrabItems(var8);
/*      */     }
/*      */ 
/*  869 */     List var15 = this.world.a(EntityItem.class, AxisAlignedBB.b(this.x - 0.5D, this.y - 0.5D, this.z - 0.5D, this.x + 1.25D, this.y + 1.25D, this.z + 1.25D));
/*  870 */     Iterator var11 = var15.iterator();
/*      */ 
/*  872 */     while (var11.hasNext())
/*      */     {
/*  874 */       Entity var10 = (Entity)var11.next();
/*  875 */       GrabItems(var10);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean PushStack(EntityItem var1)
/*      */   {
/*  881 */     if (var1 == null)
/*      */     {
/*  883 */       return false;
/*      */     }
/*  885 */     if (var1.itemStack == null)
/*      */     {
/*  887 */       var1.die();
/*  888 */       return false;
/*      */     }
/*  890 */     if (var1.itemStack.count < 1)
/*      */     {
/*  892 */       var1.die();
/*  893 */       return false;
/*      */     }
/*      */ 
/*  897 */     for (int var2 = 1; var2 < this.items.length; var2++)
/*      */     {
/*  899 */       if (this.items[var2] == null)
/*      */       {
/*  901 */         this.items[var2] = var1.itemStack.cloneItemStack();
/*      */ 
/*  903 */         for (this.items[var2].count = 0; (var1.itemStack.count > 0) && (this.items[var2].count < this.items[var2].getMaxStackSize()); var1.itemStack.count -= 1)
/*      */         {
/*  905 */           this.items[var2].count += 1;
/*      */         }
/*      */ 
/*  908 */         var1.die();
/*  909 */         return true;
/*      */       }
/*      */ 
/*  912 */       if ((this.items[var2].doMaterialsMatch(var1.itemStack)) && (this.items[var2].count <= var1.itemStack.getMaxStackSize() - var1.itemStack.count))
/*      */       {
/*  914 */         while ((var1.itemStack.count > 0) && (this.items[var2].count < this.items[var2].getMaxStackSize()))
/*      */         {
/*  916 */           this.items[var2].count += 1;
/*  917 */           var1.itemStack.count -= 1;
/*      */         }
/*      */ 
/*  920 */         var1.die();
/*  921 */         return true;
/*      */       }
/*      */     }
/*      */ 
/*  925 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean PushStack(ItemStack var1)
/*      */   {
/*  931 */     if (var1 == null)
/*      */     {
/*  933 */       return false;
/*      */     }
/*      */ 
/*  937 */     for (int var2 = 1; var2 < this.items.length; var2++)
/*      */     {
/*  939 */       if (this.items[var2] == null)
/*      */       {
/*  941 */         this.items[var2] = var1.cloneItemStack();
/*  942 */         var1 = null;
/*  943 */         return true;
/*      */       }
/*      */ 
/*  946 */       if ((this.items[var2].doMaterialsMatch(var1)) && (this.items[var2].count <= var1.getMaxStackSize() - var1.count))
/*      */       {
/*  948 */         this.items[var2].count += var1.count;
/*  949 */         var1 = null;
/*  950 */         return true;
/*      */       }
/*      */     }
/*      */ 
/*  954 */     return false;
/*      */   }
/*      */ 
/*      */   private void PushDenseStacks(EntityLootBall var1)
/*      */   {
/*  960 */     for (int var2 = 1; var2 < var1.items.length; var2++)
/*      */     {
/*  962 */       if ((var1.items[var2] != null) && (PushStack(var1.items[var2])))
/*      */       {
/*  964 */         var1.items[var2] = null;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void GrabItems(Entity var1)
/*      */   {
/*  971 */     if ((var1 != null) && ((var1 instanceof EntityItem)))
/*      */     {
/*  973 */       ItemStack var9 = ((EntityItem)var1).itemStack;
/*      */ 
/*  975 */       if (var9 == null)
/*      */       {
/*  977 */         var1.die();
/*  978 */         return;
/*      */       }
/*      */ 
/*  981 */       if ((var9.getItem() instanceof ItemLootBall))
/*      */       {
/*  983 */         ItemLootBall var3 = (ItemLootBall)var9.getItem();
/*  984 */         ItemStack[] var4 = var3.getDroplist(var9);
/*  985 */         ItemStack[] var5 = var4;
/*  986 */         int var6 = var4.length;
/*      */ 
/*  988 */         for (int var7 = 0; var7 < var6; var7++)
/*      */         {
/*  990 */           ItemStack var8 = var5[var7];
/*  991 */           PushStack(var8);
/*      */         }
/*      */ 
/*  994 */         var1.die();
/*      */       }
/*      */       else
/*      */       {
/*  998 */         PushStack(var9);
/*  999 */         var1.die();
/*      */       }
/*      */     }
/* 1002 */     else if ((var1 != null) && ((var1 instanceof EntityLootBall)))
/*      */     {
/* 1004 */       if (((EntityLootBall)var1).items == null)
/*      */       {
/* 1006 */         var1.die();
/*      */       }
/*      */ 
/* 1009 */       ItemStack[] var2 = ((EntityLootBall)var1).items;
/* 1010 */       PushDenseStacks((EntityLootBall)var1);
/*      */ 
/* 1012 */       if (((EntityLootBall)var1).isEmpty())
/*      */       {
/* 1014 */         var1.die();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void PullItems(Entity var1)
/*      */   {
/* 1021 */     if (((var1 instanceof EntityItem)) || ((var1 instanceof EntityLootBall)))
/*      */     {
/* 1023 */       if ((var1 instanceof EntityLootBall))
/*      */       {
/* 1025 */         ((EntityLootBall)var1).setBeingPulled(true);
/*      */       }
/*      */ 
/* 1028 */       double var3 = this.x + 0.5D - var1.locX;
/* 1029 */       double var5 = this.y + 0.5D - var1.locY;
/* 1030 */       double var7 = this.z + 0.5D - var1.locZ;
/* 1031 */       double var9 = var3 * var3 + var5 * var5 + var7 * var7;
/* 1032 */       var9 *= var9;
/*      */ 
/* 1034 */       if (var9 <= Math.pow(6.0D, 4.0D))
/*      */       {
/* 1036 */         double var11 = var3 * 0.01999999955296516D / var9 * Math.pow(6.0D, 3.0D);
/* 1037 */         double var13 = var5 * 0.01999999955296516D / var9 * Math.pow(6.0D, 3.0D);
/* 1038 */         double var15 = var7 * 0.01999999955296516D / var9 * Math.pow(6.0D, 3.0D);
/*      */ 
/* 1040 */         if (var11 > 0.1D)
/*      */         {
/* 1042 */           var11 = 0.1D;
/*      */         }
/* 1044 */         else if (var11 < -0.1D)
/*      */         {
/* 1046 */           var11 = -0.1D;
/*      */         }
/*      */ 
/* 1049 */         if (var13 > 0.1D)
/*      */         {
/* 1051 */           var13 = 0.1D;
/*      */         }
/* 1053 */         else if (var13 < -0.1D)
/*      */         {
/* 1055 */           var13 = -0.1D;
/*      */         }
/*      */ 
/* 1058 */         if (var15 > 0.1D)
/*      */         {
/* 1060 */           var15 = 0.1D;
/*      */         }
/* 1062 */         else if (var15 < -0.1D)
/*      */         {
/* 1064 */           var15 = -0.1D;
/*      */         }
/*      */ 
/* 1067 */         var1.motX += var11 * 1.2D;
/* 1068 */         var1.motY += var13 * 1.2D;
/* 1069 */         var1.motZ += var15 * 1.2D;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public int latentEnergy()
/*      */   {
/* 1076 */     return this.scaledEnergy / 80;
/*      */   }
/*      */ 
/*      */   public void b(int var1, int var2)
/*      */   {
/* 1081 */     if (var1 == 1)
/*      */     {
/* 1083 */       this.numUsingPlayers = var2;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void f()
/*      */   {
/* 1089 */     this.numUsingPlayers += 1;
/* 1090 */     this.world.playNote(this.x, this.y, this.z, 1, this.numUsingPlayers);
/*      */   }
/*      */ 
/*      */   public void g()
/*      */   {
/* 1095 */     this.numUsingPlayers -= 1;
/* 1096 */     this.world.playNote(this.x, this.y, this.z, 1, this.numUsingPlayers);
/*      */   }
/*      */ 
/*      */   public boolean a(EntityHuman var1)
/*      */   {
/* 1104 */     return this.world.getTileEntity(this.x, this.y, this.z) == this;
/*      */   }
/*      */ 
/*      */   public int getStartInventorySide(int var1)
/*      */   {
/* 1109 */     return 1;
/*      */   }
/*      */ 
/*      */   public int getSizeInventorySide(int var1)
/*      */   {
/* 1114 */     return this.items.length - 1;
/*      */   }
/*      */ 
/*      */   public boolean onBlockActivated(EntityHuman var1)
/*      */   {
/* 1119 */     if (!this.world.isStatic)
/*      */     {
/* 1121 */       var1.openGui(mod_EE.getInstance(), GuiIds.CONDENSER, this.world, this.x, this.y, this.z);
/*      */     }
/*      */ 
/* 1124 */     return true;
/*      */   }
/*      */ 
/*      */   public int getTextureForSide(int var1)
/*      */   {
/* 1129 */     if ((var1 != 1) && (var1 != 0))
/*      */     {
/* 1131 */       byte var2 = this.direction;
/* 1132 */       return var1 != var2 ? EEBase.condenserSide : EEBase.condenserFront;
/*      */     }
/*      */ 
/* 1136 */     return EEBase.condenserTop;
/*      */   }
/*      */ 
/*      */   public int getInventoryTexture(int var1)
/*      */   {
/* 1142 */     return (var1 != 1) && (var1 != 0) ? EEBase.condenserSide : var1 == 3 ? EEBase.condenserFront : EEBase.condenserTop;
/*      */   }
/*      */ 
/*      */   public int getLightValue()
/*      */   {
/* 1147 */     return 10;
/*      */   }
/*      */ 
/*      */   public void onNeighborBlockChange(int var1)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void randomDisplayTick(Random var1)
/*      */   {
/*      */   }
/*      */ 
/*      */   public ItemStack splitWithoutUpdate(int var1)
/*      */   {
/* 1160 */     return null;
/*      */   }
/*      */ 
/*      */   public ItemStack[] getContents()
/*      */   {
/* 1165 */     return this.items;
/*      */   }
/*      */ 
/*      */   public void setMaxStackSize(int size)
/*      */   {
/*      */   }
/*      */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.TileCondenser
 * JD-Core Version:    0.6.2
 */