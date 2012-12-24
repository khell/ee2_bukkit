/*      */ package ee;
/*      */ 
/*      */ import ee.core.GuiIds;
/*      */ import ee.network.EEPacket;
/*      */ import ee.network.PacketHandler;
/*      */ import ee.network.PacketTypeHandler;
/*      */ import java.io.PrintStream;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import net.minecraft.server.AxisAlignedBB;
/*      */ import net.minecraft.server.Block;
/*      */ import net.minecraft.server.BlockFlower;
/*      */ import net.minecraft.server.BlockGrass;
/*      */ import net.minecraft.server.BlockLongGrass;
/*      */ import net.minecraft.server.EEProxy;
/*      */ import net.minecraft.server.Entity;
/*      */ import net.minecraft.server.EntityArrow;
/*      */ import net.minecraft.server.EntityExperienceOrb;
/*      */ import net.minecraft.server.EntityFireball;
/*      */ import net.minecraft.server.EntityHuman;
/*      */ import net.minecraft.server.EntityItem;
/*      */ import net.minecraft.server.EntityLiving;
/*      */ import net.minecraft.server.EntityMonster;
/*      */ import net.minecraft.server.EntityWeatherLighting;
/*      */ import net.minecraft.server.IInventory;
/*      */ import net.minecraft.server.Item;
/*      */ import net.minecraft.server.ItemStack;
/*      */ import net.minecraft.server.Material;
/*      */ import net.minecraft.server.NBTTagCompound;
/*      */ import net.minecraft.server.NBTTagList;
/*      */ import net.minecraft.server.Packet;
/*      */ import net.minecraft.server.PlayerInventory;
/*      */ import net.minecraft.server.World;
/*      */ import net.minecraft.server.WorldData;
/*      */ import net.minecraft.server.mod_EE;
/*      */ 
/*      */ public class TilePedestal extends TileEE
/*      */   implements IInventory
/*      */ {
/*   32 */   private ItemStack[] items = new ItemStack[1];
/*   33 */   private int activeItem = 0;
/*      */   private boolean interdictionActive;
/*      */   private boolean evertideActive;
/*      */   private boolean grimarchActive;
/*      */   private boolean harvestActive;
/*      */   private boolean ignitionActive;
/*      */   private boolean zeroActive;
/*      */   private boolean repairActive;
/*      */   private boolean soulstoneActive;
/*      */   private boolean swiftwolfActive;
/*      */   private boolean volcaniteActive;
/*      */   private boolean watchActive;
/*      */   private boolean updateBlock;
/*      */   public int activationCooldown;
/*      */   private boolean activated;
/*      */   private EntityHuman activationPlayer;
/*      */   private boolean initActivation;
/*      */   private boolean initDeactivation;
/*   51 */   private int grimarchCounter = 0;
/*   52 */   private int repairTimer = 0;
/*      */   private int healingTimer;
/*      */   private boolean attractionActive;
/*      */ 
/*      */   public String getName()
/*      */   {
/*   61 */     return "Pedestal";
/*      */   }
/*      */ 
/*      */   public void a(NBTTagCompound var1)
/*      */   {
/*   69 */     super.a(var1);
/*   70 */     NBTTagList var2 = var1.getList("Items");
/*   71 */     this.items = new ItemStack[getSize()];
/*      */ 
/*   73 */     for (int var3 = 0; var3 < var2.size(); var3++)
/*      */     {
/*   75 */       NBTTagCompound var4 = (NBTTagCompound)var2.get(var3);
/*   76 */       int var5 = var4.getByte("Slot") & 0xFF;
/*      */ 
/*   78 */       if ((var5 >= 0) && (var5 < this.items.length))
/*      */       {
/*   80 */         this.items[var5] = ItemStack.a(var4);
/*      */       }
/*      */     }
/*      */ 
/*   84 */     this.interdictionActive = var1.getBoolean("interdictionActive");
/*   85 */     this.attractionActive = var1.getBoolean("attractionActive");
/*   86 */     this.evertideActive = var1.getBoolean("evertideActive");
/*   87 */     this.grimarchActive = var1.getBoolean("grimarchActive");
/*   88 */     this.harvestActive = var1.getBoolean("harvestActive");
/*   89 */     this.ignitionActive = var1.getBoolean("ignitionActive");
/*   90 */     this.zeroActive = var1.getBoolean("zeroActive");
/*   91 */     this.repairActive = var1.getBoolean("repairActive");
/*   92 */     this.soulstoneActive = var1.getBoolean("soulstoneActive");
/*   93 */     this.swiftwolfActive = var1.getBoolean("swiftwolfActive");
/*   94 */     this.volcaniteActive = var1.getBoolean("volcaniteActive");
/*   95 */     this.watchActive = var1.getBoolean("watchActive");
/*   96 */     this.activated = var1.getBoolean("activated");
/*      */ 
/*   98 */     if ((!this.volcaniteActive) && (!this.evertideActive) && (this.activated))
/*      */     {
/*  100 */       this.initActivation = true;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void b(NBTTagCompound var1)
/*      */   {
/*  109 */     super.b(var1);
/*  110 */     var1.setBoolean("watchActive", this.watchActive);
/*  111 */     var1.setBoolean("volcaniteActive", this.volcaniteActive);
/*  112 */     var1.setBoolean("swiftwolfActive", this.swiftwolfActive);
/*  113 */     var1.setBoolean("soulstoneActive", this.soulstoneActive);
/*  114 */     var1.setBoolean("repairActive", this.repairActive);
/*  115 */     var1.setBoolean("ignitionActive", this.ignitionActive);
/*  116 */     var1.setBoolean("zeroActive", this.zeroActive);
/*  117 */     var1.setBoolean("harvestActive", this.harvestActive);
/*  118 */     var1.setBoolean("grimarchActive", this.grimarchActive);
/*  119 */     var1.setBoolean("evertideActive", this.evertideActive);
/*  120 */     var1.setBoolean("interdictionActive", this.interdictionActive);
/*  121 */     var1.setBoolean("attractionActive", this.attractionActive);
/*  122 */     var1.setBoolean("activated", this.activated);
/*  123 */     NBTTagList var2 = new NBTTagList();
/*      */ 
/*  125 */     for (byte var3 = 0; var3 < this.items.length; var3 = (byte)(var3 + 1))
/*      */     {
/*  127 */       if (this.items[var3] != null)
/*      */       {
/*  129 */         NBTTagCompound var4 = new NBTTagCompound();
/*  130 */         var4.setByte("Slot", var3);
/*  131 */         this.items[var3].save(var4);
/*  132 */         var2.add(var4);
/*      */       }
/*      */     }
/*      */ 
/*  136 */     var1.set("Items", var2);
/*      */   }
/*      */ 
/*      */   public void update()
/*      */   {
/*  144 */     super.update();
/*      */ 
/*  146 */     if (this.items[0] == null)
/*      */     {
/*  148 */       resetAll();
/*      */     }
/*      */     else
/*      */     {
/*  152 */       int var1 = this.items[0].id;
/*  153 */       int var2 = this.items[0].getData();
/*      */ 
/*  155 */       if (EEMaps.isPedestalItem(var1))
/*      */       {
/*  157 */         if ((var1 == EEBlock.eeTorch.id) && (var2 == 0))
/*      */         {
/*  159 */           resetAll();
/*  160 */           this.updateBlock = true;
/*  161 */           this.interdictionActive = true;
/*      */         }
/*  163 */         else if (var1 == EEItem.evertide.id)
/*      */         {
/*  165 */           resetAll();
/*  166 */           this.evertideActive = true;
/*      */         }
/*  168 */         else if (var1 == EEItem.grimarchRing.id)
/*      */         {
/*  170 */           resetAll();
/*  171 */           this.grimarchActive = true;
/*      */         }
/*  173 */         else if (var1 == EEItem.harvestRing.id)
/*      */         {
/*  175 */           resetAll();
/*  176 */           this.harvestActive = true;
/*      */         }
/*  178 */         else if (var1 == EEItem.zeroRing.id)
/*      */         {
/*  180 */           resetAll();
/*  181 */           this.zeroActive = true;
/*      */         }
/*  183 */         else if (var1 == EEItem.ignitionRing.id)
/*      */         {
/*  185 */           resetAll();
/*  186 */           this.ignitionActive = true;
/*      */         }
/*  188 */         else if (var1 == EEItem.repairCharm.id)
/*      */         {
/*  190 */           resetAll();
/*  191 */           this.repairActive = true;
/*      */         }
/*  193 */         else if (var1 == EEItem.soulStone.id)
/*      */         {
/*  195 */           resetAll();
/*  196 */           this.soulstoneActive = true;
/*      */         }
/*  198 */         else if (var1 == EEItem.swiftWolfRing.id)
/*      */         {
/*  200 */           resetAll();
/*  201 */           this.swiftwolfActive = true;
/*      */         }
/*  203 */         else if (var1 == EEItem.volcanite.id)
/*      */         {
/*  205 */           resetAll();
/*  206 */           this.volcaniteActive = true;
/*      */         }
/*  208 */         else if (var1 == EEItem.watchOfTime.id)
/*      */         {
/*  210 */           resetAll();
/*  211 */           this.watchActive = true;
/*      */         }
/*  213 */         else if (var1 == EEItem.attractionRing.id)
/*      */         {
/*  215 */           resetAll();
/*  216 */           this.attractionActive = true;
/*      */         }
/*      */         else
/*      */         {
/*  220 */           this.updateBlock = true;
/*  221 */           resetAll();
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  226 */         EntityItem var3 = new EntityItem(this.world, this.x, this.y, this.z, this.items[0].cloneItemStack());
/*  227 */         var3.pickupDelay = 10;
/*  228 */         this.world.addEntity(var3);
/*  229 */         this.items[0] = null;
/*  230 */         this.updateBlock = true;
/*  231 */         resetAll();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isActivated()
/*      */   {
/*  238 */     return this.activated;
/*      */   }
/*      */ 
/*      */   public void setActivated(boolean var1)
/*      */   {
/*  243 */     this.activated = var1;
/*      */   }
/*      */ 
/*      */   private void resetAll()
/*      */   {
/*  248 */     this.activated = false;
/*  249 */     this.initActivation = false;
/*  250 */     this.initDeactivation = false;
/*  251 */     this.attractionActive = false;
/*  252 */     this.interdictionActive = false;
/*  253 */     this.evertideActive = false;
/*  254 */     this.grimarchActive = false;
/*  255 */     this.harvestActive = false;
/*  256 */     this.ignitionActive = false;
/*  257 */     this.repairActive = false;
/*  258 */     this.soulstoneActive = false;
/*  259 */     this.swiftwolfActive = false;
/*  260 */     this.volcaniteActive = false;
/*  261 */     this.watchActive = false;
/*  262 */     resetTimeFactor();
/*      */   }
/*      */ 
/*      */   public void q_()
/*      */   {
/*  271 */     if (!clientFail())
/*      */     {
/*  273 */       if (this.activated)
/*      */       {
/*  277 */         if (this.world.random.nextInt(5) == 0)
/*      */         {
/*  279 */           for (int var1 = 0; var1 < 1; var1++)
/*      */           {
/*  281 */             double var10000 = this.x + this.world.random.nextFloat();
/*  282 */             double var4 = this.y + this.world.random.nextFloat();
/*  283 */             var10000 = this.z + this.world.random.nextFloat();
/*  284 */             double var8 = 0.0D;
/*  285 */             double var10 = 0.0D;
/*  286 */             double var12 = 0.0D;
/*  287 */             int var14 = this.world.random.nextInt(2) * 2 - 1;
/*  288 */             var8 = (this.world.random.nextFloat() - 0.5D) * 0.5D;
/*  289 */             var10 = (this.world.random.nextFloat() - 0.5D) * 0.5D;
/*  290 */             var12 = (this.world.random.nextFloat() - 0.5D) * 0.5D;
/*      */             double var6;
/*  293 */             if (this.world.random.nextInt(2) == 0)
/*      */             {
/*  295 */               var6 = this.z + 0.5D + 0.25D * var14;
/*  296 */               var12 = this.world.random.nextFloat() * 2.0F * var14;
/*      */             }
/*      */             else
/*      */             {
/*  300 */               var6 = this.z + 0.5D + 0.25D * var14 * -1.0D;
/*  301 */               var12 = this.world.random.nextFloat() * 2.0F * var14 * -1.0F;
/*      */             }
/*      */             double var2;
/*  306 */             if (this.world.random.nextInt(2) == 0)
/*      */             {
/*  308 */               var2 = this.x + 0.5D + 0.25D * var14;
/*  309 */               var8 = this.world.random.nextFloat() * 2.0F * var14;
/*      */             }
/*      */             else
/*      */             {
/*  313 */               var2 = this.x + 0.5D + 0.25D * var14 * -1.0D;
/*  314 */               var8 = this.world.random.nextFloat() * 2.0F * var14 * -1.0F;
/*      */             }
/*      */ 
/*  317 */             this.world.a("portal", var2, var4, var6, var8, var10, var12);
/*      */           }
/*      */         }
/*      */ 
/*  321 */         for (int var1 = 0; var1 < 1; var1++)
/*      */         {
/*  323 */           float var15 = this.x + 0.45F + this.world.random.nextFloat() / 16.0F;
/*  324 */           float var3 = this.y + 0.3F + this.world.random.nextFloat() / 16.0F;
/*  325 */           float var16 = this.z + 0.45F + this.world.random.nextFloat() / 16.0F;
/*  326 */           float var5 = 0.2F;
/*      */ 
/*  328 */           if (this.world.random.nextInt(8) == 0)
/*      */           {
/*  330 */             this.world.a("flame", var15 - var5, var3, var16 - var5, 0.0D, 0.0D, 0.0D);
/*      */           }
/*      */ 
/*  333 */           if (this.world.random.nextInt(8) == 0)
/*      */           {
/*  335 */             this.world.a("flame", var15 - var5, var3, var16, 0.0D, 0.0D, 0.0D);
/*      */           }
/*      */ 
/*  338 */           if (this.world.random.nextInt(8) == 0)
/*      */           {
/*  340 */             this.world.a("flame", var15 - var5, var3, var16 + var5, 0.0D, 0.0D, 0.0D);
/*      */           }
/*      */ 
/*  343 */           if (this.world.random.nextInt(8) == 0)
/*      */           {
/*  345 */             this.world.a("flame", var15, var3, var16 - var5, 0.0D, 0.0D, 0.0D);
/*      */           }
/*      */ 
/*  348 */           if (this.world.random.nextInt(8) == 0)
/*      */           {
/*  350 */             this.world.a("flame", var15 + var5, var3, var16 - var5, 0.0D, 0.0D, 0.0D);
/*      */           }
/*      */ 
/*  353 */           if (this.world.random.nextInt(8) == 0)
/*      */           {
/*  355 */             this.world.a("flame", var15 + var5, var3, var16, 0.0D, 0.0D, 0.0D);
/*      */           }
/*      */ 
/*  358 */           if (this.world.random.nextInt(8) == 0)
/*      */           {
/*  360 */             this.world.a("flame", var15 + var5, var3, var16 + var5, 0.0D, 0.0D, 0.0D);
/*      */           }
/*      */ 
/*  363 */           if (this.world.random.nextInt(8) == 0)
/*      */           {
/*  365 */             this.world.a("flame", var15, var3, var16 + var5, 0.0D, 0.0D, 0.0D);
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  370 */       if (this.attractionActive)
/*      */       {
/*  372 */         doAttraction(this.x, this.y, this.z);
/*      */       }
/*      */ 
/*  375 */       if (this.interdictionActive)
/*      */       {
/*  377 */         doInterdiction(this.x, this.y, this.z);
/*      */       }
/*      */ 
/*  380 */       if (this.evertideActive)
/*      */       {
/*  382 */         doEvertide();
/*      */       }
/*      */ 
/*  385 */       if (this.grimarchActive)
/*      */       {
/*  387 */         doGrimarch();
/*      */       }
/*      */ 
/*  390 */       if (this.harvestActive)
/*      */       {
/*  392 */         doHarvest();
/*      */       }
/*      */ 
/*  395 */       if (this.repairActive)
/*      */       {
/*  397 */         doRepair();
/*      */       }
/*      */ 
/*  400 */       if (this.ignitionActive)
/*      */       {
/*  402 */         doIgnition();
/*      */       }
/*      */ 
/*  405 */       if (this.soulstoneActive)
/*      */       {
/*  407 */         doSoulstone();
/*      */       }
/*      */ 
/*  410 */       if (this.swiftwolfActive)
/*      */       {
/*  412 */         doSwiftwolf();
/*      */       }
/*      */ 
/*  415 */       if (this.volcaniteActive)
/*      */       {
/*  417 */         doVolcanite();
/*      */       }
/*      */ 
/*  420 */       if (this.watchActive)
/*      */       {
/*  422 */         doWatch();
/*      */       }
/*      */ 
/*  425 */       if (this.updateBlock)
/*      */       {
/*  427 */         this.world.notify(this.x, this.y, this.z);
/*      */       }
/*      */ 
/*  430 */       if (this.activationCooldown > 0)
/*      */       {
/*  432 */         this.activationCooldown -= 1;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void doAttraction(int var1, int var2, int var3)
/*      */   {
/*  439 */     if (this.initActivation)
/*      */     {
/*  441 */       this.initActivation = false;
/*  442 */       this.activationCooldown = 20;
/*      */     }
/*      */ 
/*  445 */     if (this.initDeactivation)
/*      */     {
/*  447 */       this.initDeactivation = false;
/*  448 */       this.activationCooldown = 20;
/*      */     }
/*      */ 
/*  451 */     if (this.activated)
/*      */     {
/*  453 */       List var4 = this.world.getEntities(this.world.a(this.player), AxisAlignedBB.b(var1 - 10, var2 - 10, var3 - 10, var1 + 10, var2 + 10, var3 + 10));
/*  454 */       Iterator var6 = var4.iterator();
/*      */ 
/*  456 */       while (var6.hasNext())
/*      */       {
/*  458 */         Entity var5 = (Entity)var6.next();
/*  459 */         pullEntities(var5, var1, var2, var3);
/*      */       }
/*      */ 
/*  462 */       List var17 = this.world.a(EntityLootBall.class, AxisAlignedBB.b(var1 - 10, var2 - 10, var3 - 10, var1 + 10, var2 + 10, var3 + 10));
/*  463 */       Iterator var8 = var17.iterator();
/*      */ 
/*  465 */       while (var8.hasNext())
/*      */       {
/*  467 */         Entity var7 = (Entity)var8.next();
/*  468 */         PullItems(var7);
/*      */       }
/*      */ 
/*  471 */       List var18 = this.world.a(EntityLootBall.class, AxisAlignedBB.b(var1 - 10, var2 - 10, var3 - 10, var1 + 10, var2 + 10, var3 + 10));
/*  472 */       Iterator var10 = var18.iterator();
/*      */ 
/*  474 */       while (var10.hasNext())
/*      */       {
/*  476 */         Entity var9 = (Entity)var10.next();
/*  477 */         PullItems(var9);
/*      */       }
/*      */ 
/*  480 */       List var19 = this.world.a(EntityItem.class, AxisAlignedBB.b(var1 - 10, var2 - 10, var3 - 10, var1 + 10, var2 + 10, var3 + 10));
/*  481 */       Iterator var12 = var19.iterator();
/*      */ 
/*  483 */       while (var12.hasNext())
/*      */       {
/*  485 */         Entity var11 = (Entity)var12.next();
/*  486 */         PullItems(var11);
/*      */       }
/*      */ 
/*  489 */       List var20 = this.world.a(EntityLootBall.class, AxisAlignedBB.b(var1 - 0.5D, var2 - 0.5D, var3 - 0.5D, var1 + 1.25D, var2 + 1.25D, var3 + 1.25D));
/*  490 */       Iterator var14 = var20.iterator();
/*      */ 
/*  492 */       while (var14.hasNext())
/*      */       {
/*  494 */         Entity var13 = (Entity)var14.next();
/*  495 */         GrabItems(var13);
/*      */       }
/*      */ 
/*  498 */       List var21 = this.world.a(EntityItem.class, AxisAlignedBB.b(var1 - 0.5D, var2 - 0.5D, var3 - 0.5D, var1 + 1.25D, var2 + 1.25D, var3 + 1.25D));
/*  499 */       Iterator var16 = var21.iterator();
/*      */ 
/*  501 */       while (var16.hasNext())
/*      */       {
/*  503 */         Entity var15 = (Entity)var16.next();
/*  504 */         GrabItems(var15);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean PushStack(ItemStack var1)
/*      */   {
/*  511 */     return var1 == null ? false : tryDropInChest(var1);
/*      */   }
/*      */ 
/*      */   public boolean PushStack(EntityItem var1)
/*      */   {
/*  516 */     if (var1 == null)
/*      */     {
/*  518 */       return false;
/*      */     }
/*  520 */     if (var1.itemStack == null)
/*      */     {
/*  522 */       var1.die();
/*  523 */       return false;
/*      */     }
/*  525 */     if (var1.itemStack.count < 1)
/*      */     {
/*  527 */       var1.die();
/*  528 */       return false;
/*      */     }
/*      */ 
/*  532 */     ItemStack var2 = var1.itemStack.cloneItemStack();
/*      */ 
/*  534 */     for (var2.count = 1; (var1.itemStack.count > 0) && (tryDropInChest(var2.cloneItemStack())); var1.itemStack.count -= 1);
/*  539 */     return var1.itemStack.count <= 0;
/*      */   }
/*      */ 
/*      */   private void PushDenseStacks(EntityLootBall var1)
/*      */   {
/*  545 */     for (int var2 = 0; var2 < var1.items.length; var2++)
/*      */     {
/*  547 */       if ((var1.items[var2] != null) && (tryDropInChest(var1.items[var2])))
/*      */       {
/*  549 */         var1.items[var2] = null;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void GrabItems(Entity var1)
/*      */   {
/*  556 */     if ((var1 != null) && ((var1 instanceof EntityItem)))
/*      */     {
/*  558 */       if (((EntityItem)var1).itemStack == null)
/*      */       {
/*  560 */         var1.die();
/*      */       }
/*      */ 
/*  563 */       if (PushStack((EntityItem)var1))
/*      */       {
/*  565 */         var1.die();
/*      */       }
/*      */     }
/*  568 */     else if ((var1 != null) && ((var1 instanceof EntityLootBall)))
/*      */     {
/*  570 */       if (((EntityLootBall)var1).items == null)
/*      */       {
/*  572 */         var1.die();
/*      */       }
/*      */ 
/*  575 */       ItemStack[] var2 = ((EntityLootBall)var1).items;
/*  576 */       PushDenseStacks((EntityLootBall)var1);
/*      */ 
/*  578 */       if (((EntityLootBall)var1).isEmpty())
/*      */       {
/*  580 */         var1.die();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void PullItems(Entity var1)
/*      */   {
/*  587 */     if (((var1 instanceof EntityItem)) || ((var1 instanceof EntityLootBall)))
/*      */     {
/*  589 */       if ((var1 instanceof EntityLootBall))
/*      */       {
/*  591 */         ((EntityLootBall)var1).setBeingPulled(true);
/*      */       }
/*      */ 
/*  594 */       double var3 = this.x + 0.5D - var1.locX;
/*  595 */       double var5 = this.y + 0.5D - var1.locY;
/*  596 */       double var7 = this.z + 0.5D - var1.locZ;
/*  597 */       double var9 = var3 * var3 + var5 * var5 + var7 * var7;
/*  598 */       var9 *= var9;
/*      */ 
/*  600 */       if (var9 <= Math.pow(6.0D, 4.0D))
/*      */       {
/*  602 */         double var11 = var3 * 0.01999999955296516D / var9 * Math.pow(6.0D, 3.0D);
/*  603 */         double var13 = var5 * 0.01999999955296516D / var9 * Math.pow(6.0D, 3.0D);
/*  604 */         double var15 = var7 * 0.01999999955296516D / var9 * Math.pow(6.0D, 3.0D);
/*      */ 
/*  606 */         if (var11 > 0.1D)
/*      */         {
/*  608 */           var11 = 0.1D;
/*      */         }
/*  610 */         else if (var11 < -0.1D)
/*      */         {
/*  612 */           var11 = -0.1D;
/*      */         }
/*      */ 
/*  615 */         if (var13 > 0.1D)
/*      */         {
/*  617 */           var13 = 0.1D;
/*      */         }
/*  619 */         else if (var13 < -0.1D)
/*      */         {
/*  621 */           var13 = -0.1D;
/*      */         }
/*      */ 
/*  624 */         if (var15 > 0.1D)
/*      */         {
/*  626 */           var15 = 0.1D;
/*      */         }
/*  628 */         else if (var15 < -0.1D)
/*      */         {
/*  630 */           var15 = -0.1D;
/*      */         }
/*      */ 
/*  633 */         var1.motX += var11 * 1.2D;
/*  634 */         var1.motY += var13 * 1.2D;
/*  635 */         var1.motZ += var15 * 1.2D;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void pullEntities(Entity var1, int var2, int var3, int var4)
/*      */   {
/*  642 */     if (!(var1 instanceof EntityHuman))
/*      */     {
/*  644 */       if ((var1 instanceof EntityLiving))
/*      */       {
/*  646 */         double var5 = var2 + 0.5F - var1.locX;
/*  647 */         double var7 = var3 + 0.5F - var1.locY;
/*  648 */         double var9 = var4 + 0.5F - var1.locZ;
/*  649 */         double var11 = var5 * var5 + var7 * var7 + var9 * var9;
/*  650 */         var11 *= var11;
/*      */ 
/*  652 */         if (var11 <= Math.pow(6.0D, 4.0D))
/*      */         {
/*  654 */           double var13 = var5 * 0.01999999955296516D / var11 * Math.pow(6.0D, 3.0D);
/*  655 */           double var15 = var7 * 0.01999999955296516D / var11 * Math.pow(6.0D, 3.0D);
/*  656 */           double var17 = var9 * 0.01999999955296516D / var11 * Math.pow(6.0D, 3.0D);
/*      */ 
/*  658 */           if (var13 > 0.1D)
/*      */           {
/*  660 */             var13 = 0.1D;
/*      */           }
/*  662 */           else if (var13 < -0.1D)
/*      */           {
/*  664 */             var13 = -0.1D;
/*      */           }
/*      */ 
/*  667 */           if (var15 > 0.1D)
/*      */           {
/*  669 */             var15 = 0.1D;
/*      */           }
/*  671 */           else if (var15 < -0.1D)
/*      */           {
/*  673 */             var15 = -0.1D;
/*      */           }
/*      */ 
/*  676 */           if (var17 > 0.1D)
/*      */           {
/*  678 */             var17 = 0.1D;
/*      */           }
/*  680 */           else if (var17 < -0.1D)
/*      */           {
/*  682 */             var17 = -0.1D;
/*      */           }
/*      */ 
/*  685 */           if ((var1 instanceof EntityItem))
/*      */           {
/*  687 */             var1.motX += var13 * 1.8D;
/*  688 */             var1.motY += var15 * 2.8D;
/*  689 */             var1.motZ += var17 * 1.8D;
/*      */           }
/*      */           else
/*      */           {
/*  693 */             var1.motX += var13 * 1.4D;
/*  694 */             var1.motY += var15 * 1.2D;
/*  695 */             var1.motZ += var17 * 1.4D;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void doWatch()
/*      */   {
/*  704 */     if (this.initActivation)
/*      */     {
/*  706 */       setTimeFactor();
/*  707 */       this.initActivation = false;
/*  708 */       this.activationCooldown = 20;
/*      */     }
/*      */ 
/*  711 */     if (this.initDeactivation)
/*      */     {
/*  713 */       resetTimeFactor();
/*  714 */       this.initDeactivation = false;
/*  715 */       this.activationCooldown = 20;
/*      */     }
/*      */ 
/*  718 */     if (this.activated)
/*      */     {
/*  720 */       List var1 = this.world.getEntities(this.world.a(this.player), AxisAlignedBB.b(this.x - 10, this.y - 10, this.z - 10, this.x + 10, this.y + 10, this.z + 10));
/*  721 */       Iterator var3 = var1.iterator();
/*      */ 
/*  723 */       while (var3.hasNext())
/*      */       {
/*  725 */         Entity var2 = (Entity)var3.next();
/*  726 */         slowEntities(var2);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void doVolcanite()
/*      */   {
/*  733 */     if ((this.activated) && (this.initActivation))
/*      */     {
/*  735 */       this.initActivation = false;
/*  736 */       this.activationCooldown = 60;
/*      */ 
/*  738 */       if (EEProxy.getWorldInfo(this.world).isThundering())
/*      */       {
/*  740 */         EEProxy.getWorldInfo(this.world).setThundering(false);
/*  741 */         EEProxy.getWorldInfo(this.world).setThunderDuration(0);
/*      */       }
/*      */ 
/*  744 */       if (EEProxy.getWorldInfo(this.world).hasStorm())
/*      */       {
/*  746 */         EEProxy.getWorldInfo(this.world).setStorm(false);
/*  747 */         EEProxy.getWorldInfo(this.world).setWeatherDuration(0);
/*      */       }
/*      */     }
/*      */ 
/*  751 */     if ((this.activationCooldown == 0) || (this.initDeactivation))
/*      */     {
/*  753 */       this.activated = false;
/*  754 */       this.initDeactivation = false;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void doSwiftwolf()
/*      */   {
/*  760 */     if (this.initActivation)
/*      */     {
/*  762 */       this.activationCooldown = 20;
/*  763 */       this.initActivation = false;
/*      */     }
/*      */ 
/*  766 */     if (this.initDeactivation)
/*      */     {
/*  768 */       this.initDeactivation = false;
/*      */     }
/*      */ 
/*  771 */     if (this.activated)
/*      */     {
/*  776 */       if (this.world.random.nextInt(1000 / ((this.world.x() ? 2 : 1) * (this.world.w() ? 2 : 1))) == 0)
/*      */       {
/*  778 */         int var1 = 0;
/*  779 */         int var2 = 0;
/*  780 */         int var3 = 0;
/*  781 */         int var4 = 0;
/*      */ 
/*  783 */         for (int var5 = -5; var5 <= 5; var5++)
/*      */         {
/*  785 */           for (int var6 = -5; var6 <= 5; var6++)
/*      */           {
/*  787 */             for (int var7 = 127; var7 >= 0; var7--)
/*      */             {
/*  789 */               var1 = this.world.getTypeId(this.x + var5, var7, this.z + var6);
/*      */ 
/*  791 */               if (var1 != 0)
/*      */               {
/*  793 */                 var3 = var7;
/*  794 */                 break;
/*      */               }
/*      */             }
/*      */ 
/*  798 */             if (var1 != 0)
/*      */             {
/*  800 */               var4 = this.z + var6;
/*  801 */               break;
/*      */             }
/*      */           }
/*      */ 
/*  805 */           if (var1 != 0)
/*      */           {
/*  807 */             var2 = this.z + var5;
/*  808 */             break;
/*      */           }
/*      */         }
/*      */ 
/*  812 */         this.world.strikeLightning(new EntityWeatherLighting(this.world, var2, var3, var4));
/*      */       }
/*      */ 
/*  815 */       List var8 = this.world.a(EntityMonster.class, AxisAlignedBB.b(this.x - 10, this.y - 10, this.z - 10, this.x + 10, this.y + 10, this.z + 10));
/*      */ 
/*  817 */       for (int var2 = 0; var2 < var8.size(); var2++)
/*      */       {
/*  819 */         if (this.world.random.nextInt(60) == 0)
/*      */         {
/*  821 */           Entity var9 = (Entity)var8.get(var2);
/*      */ 
/*  823 */           if (var9 != null)
/*      */           {
/*  825 */             if (this.world.isChunkLoaded((int)var9.locX, (int)var9.locY, (int)var9.locZ))
/*      */             {
/*  827 */               if (EEProxy.getWorldInfo(this.world).isThundering())
/*      */               {
/*  829 */                 this.world.strikeLightning(new EntityWeatherLighting(this.world, var9.locX, var9.locY, var9.locZ));
/*      */ 
/*  831 */                 for (int var4 = 0; var4 <= this.world.random.nextInt(3); var4++)
/*      */                 {
/*  833 */                   this.world.strikeLightning(new EntityWeatherLighting(this.world, var9.locX, var9.locY, var9.locZ));
/*      */                 }
/*      */               }
/*  836 */               else if (EEProxy.getWorldInfo(this.world).hasStorm())
/*      */               {
/*  838 */                 this.world.strikeLightning(new EntityWeatherLighting(this.world, var9.locX, var9.locY, var9.locZ));
/*      */ 
/*  840 */                 for (int var4 = 0; var4 <= this.world.random.nextInt(2); var4++)
/*      */                 {
/*  842 */                   this.world.strikeLightning(new EntityWeatherLighting(this.world, var9.locX, var9.locY, var9.locZ));
/*      */                 }
/*      */               }
/*      */               else
/*      */               {
/*  847 */                 this.world.strikeLightning(new EntityWeatherLighting(this.world, var9.locX, var9.locY, var9.locZ));
/*      */               }
/*      */             }
/*      */             else
/*      */             {
/*  852 */               this.world.strikeLightning(new EntityWeatherLighting(this.world, var9.locX, var9.locY, var9.locZ));
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void doSoulstone()
/*      */   {
/*  862 */     if (this.initActivation)
/*      */     {
/*  864 */       this.activationCooldown = 20;
/*  865 */       this.healingTimer = 20;
/*  866 */       this.initActivation = false;
/*      */     }
/*      */ 
/*  869 */     if (this.initDeactivation)
/*      */     {
/*  871 */       this.initDeactivation = false;
/*      */     }
/*      */ 
/*  874 */     if (this.activated)
/*      */     {
/*  876 */       if (this.healingTimer <= 0)
/*      */       {
/*  878 */         this.healingTimer = 20;
/*  879 */         List var1 = this.world.a(EntityHuman.class, AxisAlignedBB.b(this.x - 10, this.y - 10, this.z - 10, this.x + 10, this.y + 10, this.z + 10));
/*  880 */         Iterator var3 = var1.iterator();
/*      */ 
/*  882 */         while (var3.hasNext())
/*      */         {
/*  884 */           Entity var2 = (Entity)var3.next();
/*  885 */           HealForPlayer(var2);
/*      */         }
/*      */       }
/*      */ 
/*  889 */       if (this.healingTimer > 0)
/*      */       {
/*  891 */         this.healingTimer -= 1;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private Object HealForPlayer(Entity var1)
/*      */   {
/*  898 */     if ((var1 instanceof EntityHuman))
/*      */     {
/*  900 */       EntityHuman var2 = (EntityHuman)var1;
/*      */ 
/*  902 */       if (EEProxy.getEntityHealth(var2) < 20)
/*      */       {
/*  904 */         this.world.makeSound(var1, "heal", 0.8F, 1.5F);
/*  905 */         ((EntityHuman)var1).heal(1);
/*      */       }
/*      */     }
/*      */ 
/*  909 */     return null;
/*      */   }
/*      */ 
/*      */   private void doIgnition()
/*      */   {
/*  914 */     if (this.initActivation)
/*      */     {
/*  916 */       this.activationCooldown = 20;
/*  917 */       this.initActivation = false;
/*      */     }
/*      */ 
/*  920 */     if (this.initDeactivation)
/*      */     {
/*  922 */       this.initDeactivation = false;
/*      */     }
/*      */ 
/*  925 */     if (this.activated)
/*      */     {
/*  927 */       List var1 = this.world.a(EntityMonster.class, AxisAlignedBB.b(this.x - 10, this.y - 10, this.z - 10, this.x + 10, this.y + 10, this.z + 10));
/*      */ 
/*  929 */       for (int var2 = 0; var2 < var1.size(); var2++)
/*      */       {
/*  931 */         if (this.world.random.nextInt(5) == 0)
/*      */         {
/*  933 */           Entity var3 = (Entity)var1.get(var2);
/*  934 */           EEProxy.dealFireDamage(var3, 3);
/*  935 */           var3.setOnFire(40);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void doZero()
/*      */   {
/*  943 */     if (this.initActivation)
/*      */     {
/*  945 */       this.activationCooldown = 20;
/*  946 */       this.initActivation = false;
/*      */     }
/*      */ 
/*  949 */     if (this.initDeactivation)
/*      */     {
/*  951 */       this.initDeactivation = false;
/*      */     }
/*      */ 
/*  954 */     if (this.activated)
/*      */     {
/*  956 */       doFreezeOverTime();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void doFreezeOverTime()
/*      */   {
/*  962 */     List var1 = this.world.a(EntityMonster.class, AxisAlignedBB.b(this.x - 5, this.y - 5, this.z - 5, this.x + 5, this.y + 5, this.z + 5));
/*      */ 
/*  965 */     for (int var2 = 0; var2 < var1.size(); var2++)
/*      */     {
/*  967 */       Entity var3 = (Entity)var1.get(var2);
/*      */ 
/*  969 */       if ((var3.motX > 0.0D) || (var3.motZ > 0.0D))
/*      */       {
/*  971 */         var3.motX *= 0.2D;
/*  972 */         var3.motZ *= 0.2D;
/*      */       }
/*      */     }
/*      */ 
/*  976 */     for (int var2 = -4; var2 <= 4; var2++)
/*      */     {
/*  978 */       for (int var6 = -4; var6 <= 4; var6++)
/*      */       {
/*  980 */         for (int var4 = -4; var4 <= 4; var4++)
/*      */         {
/*  982 */           if (((var2 <= -2) && (var2 >= 2)) || ((var6 != 0) && (((var4 <= -2) && (var4 >= 2)) || (var6 != 0))))
/*      */           {
/*  984 */             if (this.world.random.nextInt(20) == 0)
/*      */             {
/*  986 */               int var5 = this.world.getTypeId(this.x + var2, this.y + var6 - 1, this.z + var4);
/*      */ 
/*  988 */               if ((var5 != 0) && (Block.byId[var5].a()) && (this.world.getMaterial(this.x + var2, this.y + var6 - 1, this.z + var4).isBuildable()) && (this.world.getTypeId(this.x + var2, this.y + var6, this.z + var4) == 0))
/*      */               {
/*  990 */                 this.world.setTypeId(this.x + var2, this.y + var6, this.z + var4, Block.SNOW.id);
/*      */               }
/*      */             }
/*      */ 
/*  994 */             if ((this.world.random.nextInt(3) == 0) && (this.world.getMaterial(this.x + var2, this.y + var6, this.z + var4) == Material.WATER) && (this.world.getTypeId(this.x + var2, this.y + var6 + 1, this.z + var4) == 0))
/*      */             {
/*  996 */               this.world.setTypeId(this.x + var2, this.y + var6, this.z + var4, Block.ICE.id);
/*      */             }
/*      */ 
/*  999 */             if ((this.world.random.nextInt(3) == 0) && (this.world.getMaterial(this.x + var2, this.y + var6, this.z + var4) == Material.LAVA) && (this.world.getTypeId(this.x + var2, this.y + var6 + 1, this.z + var4) == 0) && (this.world.getData(this.x + var2, this.y + var6, this.z + var4) == 0))
/*      */             {
/* 1001 */               this.world.setTypeId(this.x + var2, this.y + var6, this.z + var4, Block.OBSIDIAN.id);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void doRepair()
/*      */   {
/* 1011 */     if (this.initActivation)
/*      */     {
/* 1013 */       this.activationCooldown = 20;
/* 1014 */       this.initActivation = false;
/*      */     }
/*      */ 
/* 1017 */     if (this.initDeactivation)
/*      */     {
/* 1019 */       this.initDeactivation = false;
/*      */     }
/*      */ 
/* 1022 */     if (this.activated)
/*      */     {
/* 1024 */       System.out.println("Repair is active..");
/* 1025 */       List var1 = this.world.a(EntityHuman.class, AxisAlignedBB.b(this.x - 10, this.y - 10, this.z - 10, this.x + 10, this.y + 10, this.z + 10));
/* 1026 */       Iterator var3 = var1.iterator();
/*      */ 
/* 1028 */       while (var3.hasNext())
/*      */       {
/* 1030 */         Entity var2 = (Entity)var3.next();
/* 1031 */         RepairForPlayer(var2);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void RepairForPlayer(Entity var1)
/*      */   {
/* 1038 */     if ((var1 instanceof EntityHuman))
/*      */     {
/* 1040 */       System.out.println("Player found..");
/*      */ 
/* 1042 */       if (this.repairTimer >= 2)
/*      */       {
/* 1044 */         this.repairTimer = 0;
/* 1045 */         ItemStack[] var2 = new ItemStack[((EntityHuman)var1).inventory.items.length];
/* 1046 */         ItemStack[] var3 = new ItemStack[((EntityHuman)var1).inventory.armor.length];
/* 1047 */         var2 = ((EntityHuman)var1).inventory.items;
/* 1048 */         var3 = ((EntityHuman)var1).inventory.armor;
/* 1049 */         ItemStack var4 = null;
/* 1050 */         boolean var5 = false;
/*      */ 
/* 1052 */         for (int var6 = 0; var6 < var2.length; var6++)
/*      */         {
/* 1054 */           var5 = false;
/* 1055 */           var4 = var2[var6];
/*      */ 
/* 1057 */           if (var4 != null)
/*      */           {
/* 1059 */             for (int var7 = 0; var7 < EEMaps.chargedItems.size(); var7++)
/*      */             {
/* 1061 */               if (((Integer)EEMaps.chargedItems.get(Integer.valueOf(var7))).intValue() == var4.id)
/*      */               {
/* 1063 */                 var5 = true;
/* 1064 */                 break;
/*      */               }
/*      */             }
/*      */ 
/* 1068 */             if ((!var5) && (var4.getData() >= 1) && (var4.d()))
/*      */             {
/* 1070 */               var4.setData(var4.getData() - 1);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 1076 */       this.repairTimer += 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void doHarvest()
/*      */   {
/* 1082 */     if (this.initActivation)
/*      */     {
/* 1084 */       this.initActivation = false;
/* 1085 */       this.activationCooldown = 20;
/*      */     }
/*      */ 
/* 1088 */     if (this.initDeactivation)
/*      */     {
/* 1090 */       this.initDeactivation = false;
/*      */     }
/*      */ 
/* 1093 */     if (this.activated)
/*      */     {
/* 1095 */       doPassiveHarvest();
/* 1096 */       doActiveHarvest();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void doPassiveHarvest()
/*      */   {
/* 1102 */     int var1 = this.x;
/* 1103 */     int var2 = this.y;
/* 1104 */     int var3 = this.z;
/*      */ 
/* 1106 */     for (int var4 = -5; var4 <= 5; var4++)
/*      */     {
/* 1108 */       for (int var5 = -5; var5 <= 5; var5++)
/*      */       {
/* 1110 */         for (int var6 = -5; var6 <= 5; var6++)
/*      */         {
/* 1112 */           int var7 = this.world.getTypeId(var1 + var4, var2 + var5, var3 + var6);
/*      */ 
/* 1115 */           if (var7 == Block.CROPS.id)
/*      */           {
/* 1117 */             int var8 = this.world.getData(var1 + var4, var2 + var5, var3 + var6);
/*      */ 
/* 1119 */             if ((var8 < 7) && (this.world.random.nextInt(600) == 0))
/*      */             {
/* 1121 */               var8++;
/* 1122 */               this.world.setData(var1 + var4, var2 + var5, var3 + var6, var8);
/*      */             }
/*      */           }
/* 1125 */           else if ((var7 != BlockFlower.YELLOW_FLOWER.id) && (var7 != BlockFlower.RED_ROSE.id) && (var7 != BlockFlower.BROWN_MUSHROOM.id) && (var7 != BlockFlower.RED_MUSHROOM.id))
/*      */           {
/* 1127 */             if ((var7 == Block.GRASS.id) && (this.world.getTypeId(var1 + var4, var2 + var5 + 1, var3 + var6) == 0) && (this.world.random.nextInt(4000) == 0))
/*      */             {
/* 1129 */               this.world.setTypeId(var1 + var4, var2 + var5 + 1, var3 + var6, BlockFlower.LONG_GRASS.id);
/* 1130 */               this.world.setData(var1 + var4, var2 + var5 + 1, var3 + var6, 1);
/*      */             }
/*      */ 
/* 1133 */             if ((var7 == Block.DIRT.id) && (this.world.getTypeId(var1 + var4, var2 + var5 + 1, var3 + var6) == 0) && (this.world.random.nextInt(800) == 0))
/*      */             {
/* 1135 */               this.world.setTypeId(var1 + var4, var2 + var5, var3 + var6, Block.GRASS.id);
/*      */             }
/* 1137 */             else if (((var7 == Block.SUGAR_CANE_BLOCK.id) || (var7 == Block.CACTUS.id)) && (this.world.getTypeId(var1 + var4, var2 + var5 + 1, var3 + var6) == 0) && (this.world.getTypeId(var1 + var4, var2 + var5 - 4, var3 + var6) != Block.SUGAR_CANE_BLOCK.id) && (this.world.getTypeId(var1 + var4, var2 + var5 - 4, var3 + var6) != Block.CACTUS.id) && (this.world.random.nextInt(600) == 0))
/*      */             {
/* 1139 */               this.world.setTypeId(var1 + var4, var2 + var5 + 1, var3 + var6, var7);
/* 1140 */               this.world.a("largesmoke", var1 + var4, var2 + var5, var3 + var6, 0.0D, 0.05D, 0.0D);
/*      */             }
/*      */           }
/* 1143 */           else if (this.world.random.nextInt(2) == 0)
/*      */           {
/* 1145 */             for (int var8 = -1; var8 < 0; var8++)
/*      */             {
/* 1147 */               if ((this.world.getTypeId(var1 + var4 + var8, var2 + var5, var3 + var6) == 0) && (this.world.getTypeId(var1 + var4 + var8, var2 + var5 - 1, var3 + var6) == Block.GRASS.id))
/*      */               {
/* 1149 */                 if (this.world.random.nextInt(800) == 0)
/*      */                 {
/* 1151 */                   this.world.setTypeId(var1 + var4 + var8, var2 + var5, var3 + var6, var7);
/* 1152 */                   this.world.a("largesmoke", var1 + var4 + var8, var2 + var5, var3 + var6, 0.0D, 0.05D, 0.0D);
/*      */                 }
/*      */               }
/* 1155 */               else if ((this.world.getTypeId(var1 + var4, var2 + var5, var3 + var6 + var8) == 0) && (this.world.getTypeId(var1 + var4, var2 + var5 - 1, var3 + var6 + var8) == Block.GRASS.id) && (this.world.random.nextInt(1800) == 0))
/*      */               {
/* 1157 */                 this.world.setTypeId(var1 + var4, var2 + var5, var3 + var6 + var8, var7);
/* 1158 */                 this.world.a("largesmoke", var1 + var4, var2 + var5, var3 + var6 + var8, 0.0D, 0.05D, 0.0D);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void doActiveHarvest()
/*      */   {
/* 1169 */     int var1 = this.x;
/* 1170 */     int var2 = this.y;
/* 1171 */     int var3 = this.z;
/*      */ 
/* 1173 */     for (int var4 = -5; var4 <= 5; var4++)
/*      */     {
/* 1175 */       for (int var5 = -5; var5 <= 5; var5++)
/*      */       {
/* 1177 */         for (int var6 = -5; var6 <= 5; var6++)
/*      */         {
/* 1179 */           int var7 = this.world.getTypeId(var1 + var4, var2 + var5, var3 + var6);
/*      */ 
/* 1181 */           if (var7 == Block.CROPS.id)
/*      */           {
/* 1183 */             int var8 = this.world.getData(var1 + var4, var2 + var5, var3 + var6);
/*      */ 
/* 1185 */             if (var8 >= 7)
/*      */             {
/* 1187 */               Block.byId[var7].dropNaturally(this.world, var1 + var4, var2 + var5, var3 + var6, this.world.getData(var1 + var4, var2 + var5, var3 + var6), 0.05F, 1);
/* 1188 */               Block.byId[var7].dropNaturally(this.world, var1 + var4, var2 + var5, var3 + var6, this.world.getData(var1 + var4, var2 + var5, var3 + var6), 1.0F, 1);
/* 1189 */               this.world.setTypeId(var1 + var4, var2 + var5, var3 + var6, 0);
/* 1190 */               this.world.a("largesmoke", var1 + var4, var2 + var5, var3 + var6, 0.0D, 0.05D, 0.0D);
/*      */             }
/* 1192 */             else if (this.world.random.nextInt(400) == 0)
/*      */             {
/* 1194 */               var8++;
/* 1195 */               this.world.setData(var1 + var4, var2 + var5, var3 + var6, var8);
/*      */             }
/*      */           }
/* 1198 */           else if ((var7 != BlockFlower.YELLOW_FLOWER.id) && (var7 != BlockFlower.RED_ROSE.id) && (var7 != BlockFlower.BROWN_MUSHROOM.id) && (var7 != BlockFlower.RED_MUSHROOM.id) && (var7 != BlockFlower.LONG_GRASS.id))
/*      */           {
/* 1200 */             if (((var7 == Block.SUGAR_CANE_BLOCK.id) && (this.world.getTypeId(var1 + var4, var2 + var5 - 4, var3 + var6) == Block.SUGAR_CANE_BLOCK.id)) || ((var7 == Block.CACTUS.id) && (this.world.getTypeId(var1 + var4, var2 + var5 - 4, var3 + var6) == Block.CACTUS.id)))
/*      */             {
/* 1202 */               if (var7 == Block.SUGAR_CANE_BLOCK.id)
/*      */               {
/* 1204 */                 Block.byId[var7].dropNaturally(this.world, var1 + var4, var2 + var5 - 3, var3 + var6, this.world.getData(var1 + var4, var2 + var5, var3 + var6), 0.25F, 1);
/* 1205 */                 Block.byId[var7].dropNaturally(this.world, var1 + var4, var2 + var5 - 3, var3 + var6, this.world.getData(var1 + var4, var2 + var5, var3 + var6), 1.0F, 1);
/* 1206 */                 this.world.setTypeId(var1 + var4, var2 + var5 - 3, var3 + var6, 0);
/*      */               }
/*      */               else
/*      */               {
/* 1210 */                 Block.byId[var7].dropNaturally(this.world, var1 + var4, var2 + var5 - 4, var3 + var6, this.world.getData(var1 + var4, var2 + var5, var3 + var6), 0.25F, 1);
/* 1211 */                 Block.byId[var7].dropNaturally(this.world, var1 + var4, var2 + var5 - 4, var3 + var6, this.world.getData(var1 + var4, var2 + var5, var3 + var6), 1.0F, 1);
/* 1212 */                 this.world.setTypeId(var1 + var4, var2 + var5 - 4, var3 + var6, 0);
/*      */               }
/*      */ 
/* 1215 */               this.world.a("largesmoke", var1 + var4, var2 + var5 - 3, var3 + var6, 0.0D, 0.05D, 0.0D);
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 1220 */             Block.byId[var7].dropNaturally(this.world, var1 + var4, var2 + var5, var3 + var6, this.world.getData(var1 + var4, var2 + var5, var3 + var6), 0.05F, 1);
/* 1221 */             Block.byId[var7].dropNaturally(this.world, var1 + var4, var2 + var5, var3 + var6, this.world.getData(var1 + var4, var2 + var5, var3 + var6), 1.0F, 1);
/* 1222 */             this.world.setTypeId(var1 + var4, var2 + var5, var3 + var6, 0);
/* 1223 */             this.world.a("largesmoke", var1 + var4, var2 + var5, var3 + var6, 0.0D, 0.05D, 0.0D);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void doGrimarch()
/*      */   {
/* 1232 */     if (this.activationPlayer != null)
/*      */     {
/* 1234 */       if (this.initActivation)
/*      */       {
/* 1236 */         this.grimarchCounter = 40;
/* 1237 */         this.activationCooldown = 20;
/* 1238 */         this.initActivation = false;
/*      */       }
/*      */ 
/* 1241 */       if (this.initDeactivation)
/*      */       {
/* 1243 */         this.initDeactivation = false;
/*      */       }
/*      */ 
/* 1246 */       if (this.activated)
/*      */       {
/* 1248 */         int var1 = this.x;
/* 1249 */         int var2 = this.y;
/* 1250 */         int var3 = this.z;
/* 1251 */         byte var4 = 10;
/*      */ 
/* 1253 */         if ((this.grimarchCounter >= 0) && (this.grimarchCounter < 5))
/*      */         {
/* 1255 */           List var5 = this.world.a(EntityLiving.class, AxisAlignedBB.b(var1 - var4, var2 - var4, var3 - var4, var1 + var4, var2 + var4, var3 + var4));
/* 1256 */           Iterator var7 = var5.iterator();
/*      */ 
/* 1258 */           while (var7.hasNext())
/*      */           {
/* 1260 */             Entity var6 = (Entity)var7.next();
/* 1261 */             ShootArrowAt(var6, var1, var2, var3);
/*      */           }
/*      */ 
/* 1264 */           this.grimarchCounter -= 1;
/*      */ 
/* 1266 */           if (this.grimarchCounter == 0)
/*      */           {
/* 1268 */             this.grimarchCounter = 40;
/*      */           }
/*      */         }
/*      */ 
/* 1272 */         if (this.grimarchCounter >= 5)
/*      */         {
/* 1274 */           this.grimarchCounter -= 1;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void ShootArrowAt(Entity var1, int var2, int var3, int var4)
/*      */   {
/* 1282 */     if (!(var1 instanceof EntityHuman))
/*      */     {
/* 1284 */       double var5 = var1.locX - this.x;
/* 1285 */       double var7 = var1.boundingBox.b + var1.length / 2.0F - (this.y + 1.0D);
/* 1286 */       double var9 = var1.locZ - this.z;
/* 1287 */       EntityGrimArrow var11 = new EntityGrimArrow(this.world, var5, var7, var9);
/* 1288 */       double var12 = 4.0D;
/* 1289 */       var11.locX = this.x;
/* 1290 */       var11.locY = (this.y + 2.0D + 0.5D);
/* 1291 */       var11.locZ = this.z;
/* 1292 */       this.world.addEntity(var11);
/* 1293 */       this.world.makeSound(var11, "random.bow", 0.8F, 0.8F / (this.world.random.nextFloat() * 0.4F + 0.8F));
/*      */     }
/*      */   }
/*      */ 
/*      */   private void doEvertide()
/*      */   {
/* 1299 */     if ((this.activated) && (this.initActivation))
/*      */     {
/* 1301 */       this.initActivation = false;
/* 1302 */       this.activationCooldown = 60;
/*      */ 
/* 1304 */       if (!EEProxy.getWorldInfo(this.world).hasStorm())
/*      */       {
/* 1306 */         EEProxy.getWorldInfo(this.world).setStorm(true);
/* 1307 */         EEProxy.getWorldInfo(this.world).setWeatherDuration(6000);
/*      */       }
/*      */       else
/*      */       {
/* 1311 */         EEProxy.getWorldInfo(this.world).setWeatherDuration(EEProxy.getWorldInfo(this.world).getWeatherDuration() + 6000);
/*      */       }
/*      */     }
/*      */ 
/* 1315 */     if ((this.activationCooldown == 0) || (this.initDeactivation))
/*      */     {
/* 1317 */       this.activated = false;
/* 1318 */       this.initDeactivation = false;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void doInterdiction(int var1, int var2, int var3)
/*      */   {
/* 1324 */     if (this.initActivation)
/*      */     {
/* 1326 */       this.activationCooldown = 20;
/* 1327 */       this.initActivation = false;
/*      */     }
/*      */ 
/* 1330 */     if (this.initDeactivation)
/*      */     {
/* 1332 */       this.initDeactivation = false;
/*      */     }
/*      */ 
/* 1335 */     if (this.activated)
/*      */     {
/* 1337 */       float var4 = 9.0F;
/* 1338 */       List var5 = this.world.a(EntityMonster.class, AxisAlignedBB.b(var1 - var4, var2 - var4, var3 - var4, var1 + var4, var2 + var4, var3 + var4));
/* 1339 */       Iterator var7 = var5.iterator();
/*      */ 
/* 1341 */       while (var7.hasNext())
/*      */       {
/* 1343 */         Entity var6 = (Entity)var7.next();
/* 1344 */         PushEntities(var6, var1, var2, var3);
/*      */       }
/*      */ 
/* 1347 */       List var12 = this.world.a(EntityArrow.class, AxisAlignedBB.b(var1 - var4, var2 - var4, var3 - var4, var1 + var4, var2 + var4, var3 + var4));
/* 1348 */       Iterator var9 = var12.iterator();
/*      */ 
/* 1350 */       while (var9.hasNext())
/*      */       {
/* 1352 */         Entity var8 = (Entity)var9.next();
/* 1353 */         PushEntities(var8, var1, var2, var3);
/*      */       }
/*      */ 
/* 1356 */       List var13 = this.world.a(EntityFireball.class, AxisAlignedBB.b(var1 - var4, var2 - var4, var3 - var4, var1 + var4, var2 + var4, var3 + var4));
/* 1357 */       Iterator var11 = var13.iterator();
/*      */ 
/* 1359 */       while (var11.hasNext())
/*      */       {
/* 1361 */         Entity var10 = (Entity)var11.next();
/* 1362 */         PushEntities(var10, var1, var2, var3);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void PushEntities(Entity var1, int var2, int var3, int var4)
/*      */   {
/* 1369 */     if (!(var1 instanceof EntityHuman))
/*      */     {
/* 1371 */       double var6 = var2 - var1.locX;
/* 1372 */       double var8 = var3 - var1.locY;
/* 1373 */       double var10 = var4 - var1.locZ;
/* 1374 */       double var12 = var6 * var6 + var8 * var8 + var10 * var10;
/* 1375 */       var12 *= var12;
/*      */ 
/* 1377 */       if (var12 <= Math.pow(6.0D, 4.0D))
/*      */       {
/* 1379 */         double var14 = -(var6 * 0.01999999955296516D / var12) * Math.pow(6.0D, 3.0D);
/* 1380 */         double var16 = -(var8 * 0.01999999955296516D / var12) * Math.pow(6.0D, 3.0D);
/* 1381 */         double var18 = -(var10 * 0.01999999955296516D / var12) * Math.pow(6.0D, 3.0D);
/*      */ 
/* 1383 */         if (var14 > 0.0D)
/*      */         {
/* 1385 */           var14 = 0.22D;
/*      */         }
/* 1387 */         else if (var14 < 0.0D)
/*      */         {
/* 1389 */           var14 = -0.22D;
/*      */         }
/*      */ 
/* 1392 */         if (var16 > 0.2D)
/*      */         {
/* 1394 */           var16 = 0.12D;
/*      */         }
/* 1396 */         else if (var16 < -0.1D)
/*      */         {
/* 1398 */           var16 = 0.12D;
/*      */         }
/*      */ 
/* 1401 */         if (var18 > 0.0D)
/*      */         {
/* 1403 */           var18 = 0.22D;
/*      */         }
/* 1405 */         else if (var18 < 0.0D)
/*      */         {
/* 1407 */           var18 = -0.22D;
/*      */         }
/*      */ 
/* 1410 */         var1.motX += var14;
/* 1411 */         var1.motY += var16;
/* 1412 */         var1.motZ += var18;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isInterdicting()
/*      */   {
/* 1419 */     return this.interdictionActive;
/*      */   }
/*      */ 
/*      */   public void setTimeFactor()
/*      */   {
/* 1424 */     EEBase.addPedestalCoords(this);
/*      */   }
/*      */ 
/*      */   public void resetTimeFactor()
/*      */   {
/* 1429 */     EEBase.validatePedestalCoords(this.world);
/*      */   }
/*      */ 
/*      */   public void slowEntities(Entity var1)
/*      */   {
/* 1434 */     if ((!(var1 instanceof EntityItem)) && (!(var1 instanceof EntityExperienceOrb)) && (!(var1 instanceof EntityGrimArrow)) && (!(var1 instanceof EntityPhilosopherEssence)) && (!(var1 instanceof EntityLavaEssence)) && (!(var1 instanceof EntityWaterEssence)) && (!(var1 instanceof EntityWindEssence)) && (!(var1 instanceof EntityHyperkinesis)) && (!(var1 instanceof EntityPyrokinesis)))
/*      */     {
/* 1436 */       byte var2 = 4;
/* 1437 */       var1.motX /= (var2 * var2 * var2 * var2 + 1);
/* 1438 */       var1.motZ /= (var2 * var2 * var2 * var2 + 1);
/*      */ 
/* 1440 */       if (var1.motY < 0.0D)
/*      */       {
/* 1442 */         var1.motY /= (1.0D + 0.002D * (var2 * var2 + 1));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void activate(EntityHuman var1)
/*      */   {
/* 1449 */     this.world.makeSound(var1, "transmute", 0.6F, 1.0F);
/*      */ 
/* 1451 */     if ((this.items[0] != null) && (EEMaps.isPedestalItem(this.items[0].id)) && (this.activationCooldown <= 0))
/*      */     {
/* 1453 */       this.activated = (!this.activated);
/*      */ 
/* 1459 */       if (this.activated)
/*      */       {
/* 1461 */         this.initActivation = true;
/*      */ 
/* 1463 */         for (int var2 = 0; var2 < 4; var2++)
/*      */         {
/* 1465 */           float var3 = this.x + 0.5F + this.world.random.nextFloat() / 16.0F;
/* 1466 */           float var4 = this.y + 1.0F + this.world.random.nextFloat() / 16.0F;
/* 1467 */           float var5 = this.z + 0.5F + this.world.random.nextFloat() / 16.0F;
/* 1468 */           this.world.a("flame", var3, var4, var5, 0.0D, 0.0D, 0.0D);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1473 */         this.initDeactivation = true;
/*      */ 
/* 1475 */         for (int var2 = 0; var2 < 4; var2++)
/*      */         {
/* 1477 */           float var3 = this.x + 0.5F + this.world.random.nextFloat() / 16.0F;
/* 1478 */           float var4 = this.y + 1.0F + this.world.random.nextFloat() / 16.0F;
/* 1479 */           float var5 = this.z + 0.5F + this.world.random.nextFloat() / 16.0F;
/* 1480 */           this.world.a("smoke", var3, var4, var5, 0.0D, 0.02D, 0.0D);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1485 */     this.activationPlayer = var1;
/*      */   }
/*      */ 
/*      */   public void activate()
/*      */   {
/* 1490 */     if (this.activationCooldown <= 0)
/*      */     {
/* 1492 */       this.world.makeSound(this.x, this.y, this.z, "transmute", 0.6F, 1.0F);
/*      */ 
/* 1494 */       if ((this.items[0] != null) && (EEMaps.isPedestalItem(this.items[0].id)))
/*      */       {
/* 1496 */         if ((this.items[0].getItem() instanceof ItemGrimarchRing))
/*      */         {
/* 1498 */           return;
/*      */         }
/*      */ 
/* 1501 */         this.activated = (!this.activated);
/*      */ 
/* 1507 */         if (this.activated)
/*      */         {
/* 1509 */           this.initActivation = true;
/*      */ 
/* 1511 */           for (int var1 = 0; var1 < 4; var1++)
/*      */           {
/* 1513 */             float var2 = this.x + 0.5F + this.world.random.nextFloat() / 16.0F;
/* 1514 */             float var3 = this.y + 1.0F + this.world.random.nextFloat() / 16.0F;
/* 1515 */             float var4 = this.z + 0.5F + this.world.random.nextFloat() / 16.0F;
/* 1516 */             this.world.a("flame", var2, var3, var4, 0.0D, 0.0D, 0.0D);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 1521 */           this.initDeactivation = true;
/*      */ 
/* 1523 */           for (int var1 = 0; var1 < 4; var1++)
/*      */           {
/* 1525 */             float var2 = this.x + 0.5F + this.world.random.nextFloat() / 16.0F;
/* 1526 */             float var3 = this.y + 1.0F + this.world.random.nextFloat() / 16.0F;
/* 1527 */             float var4 = this.z + 0.5F + this.world.random.nextFloat() / 16.0F;
/* 1528 */             this.world.a("smoke", var2, var3, var4, 0.0D, 0.02D, 0.0D);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1534 */     this.activationPlayer = null;
/*      */   }
/*      */ 
/*      */   public ItemStack splitStack(int var1, int var2)
/*      */   {
/* 1543 */     if (this.items[var1] != null)
/*      */     {
/* 1547 */       if (this.items[var1].count <= var2)
/*      */       {
/* 1549 */         ItemStack var3 = this.items[var1];
/* 1550 */         this.items[var1] = null;
/* 1551 */         return var3;
/*      */       }
/*      */ 
/* 1555 */       ItemStack var3 = this.items[var1].a(var2);
/*      */ 
/* 1557 */       if (this.items[var1].count == 0)
/*      */       {
/* 1559 */         this.items[var1] = null;
/*      */       }
/*      */ 
/* 1562 */       return var3;
/*      */     }
/*      */ 
/* 1567 */     return null;
/*      */   }
/*      */ 
/*      */   public void setItem(int var1, ItemStack var2)
/*      */   {
/* 1576 */     this.items[var1] = var2;
/*      */ 
/* 1578 */     if ((var2 != null) && (var2.count > getMaxStackSize()))
/*      */     {
/* 1580 */       var2.count = getMaxStackSize();
/*      */     }
/*      */   }
/*      */ 
/*      */   public ItemStack getItem(int var1)
/*      */   {
/* 1589 */     return this.items[var1];
/*      */   }
/*      */ 
/*      */   public int getSize()
/*      */   {
/* 1597 */     return this.items.length;
/*      */   }
/*      */ 
/*      */   public int getMaxStackSize()
/*      */   {
/* 1606 */     return 64;
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
/*      */   public boolean a(EntityHuman var1) {
/* 1618 */     return this.world.getTileEntity(this.x, this.y, this.z) == this;
/*      */   }
/*      */ 
/*      */   public boolean onBlockActivated(EntityHuman var1)
/*      */   {
/* 1623 */     var1.openGui(mod_EE.getInstance(), GuiIds.PEDESTAL, var1.world, (int)var1.locX, (int)var1.locY, (int)var1.locZ);
/* 1624 */     return true;
/*      */   }
/*      */ 
/*      */   public void setPlayer(EntityHuman var1)
/*      */   {
/* 1629 */     this.player = var1.name;
/*      */   }
/*      */ 
/*      */   public ItemStack splitWithoutUpdate(int var1)
/*      */   {
/* 1638 */     return null;
/*      */   }
/*      */ 
/*      */   public Packet d()
/*      */   {
/* 1646 */     EEPacket var1 = PacketHandler.getPacket(PacketTypeHandler.PEDESTAL);
/* 1647 */     var1.setCoords(this.x, this.y, this.z);
/*      */ 
/* 1649 */     if (getItem(0) != null)
/*      */     {
/* 1651 */       var1.setItem(getItem(0).getItem().id);
/*      */     }
/*      */     else
/*      */     {
/* 1655 */       var1.setItem(-1);
/*      */     }
/*      */ 
/* 1658 */     var1.setState(this.activated);
/* 1659 */     return PacketHandler.getPacketForSending(var1);
/*      */   }
/*      */ 
/*      */   public ItemStack[] getContents()
/*      */   {
/* 1664 */     return this.items;
/*      */   }
/*      */ 
/*      */   public void setMaxStackSize(int size)
/*      */   {
/*      */   }
/*      */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.TilePedestal
 * JD-Core Version:    0.6.2
 */