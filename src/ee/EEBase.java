/*      */ package ee;
/*      */ 
/*      */ import forge.ICraftingHandler;
/*      */ import forge.MinecraftForge;
/*      */ import java.io.File;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import net.minecraft.server.AxisAlignedBB;
/*      */ import net.minecraft.server.BaseMod;
/*      */ import net.minecraft.server.Block;
/*      */ import net.minecraft.server.EEProxy;
/*      */ import net.minecraft.server.Entity;
/*      */ import net.minecraft.server.EntityArrow;
/*      */ import net.minecraft.server.EntityBlaze;
/*      */ import net.minecraft.server.EntityCaveSpider;
/*      */ import net.minecraft.server.EntityChicken;
/*      */ import net.minecraft.server.EntityCow;
/*      */ import net.minecraft.server.EntityCreeper;
/*      */ import net.minecraft.server.EntityEnderman;
/*      */ import net.minecraft.server.EntityFireball;
/*      */ import net.minecraft.server.EntityGhast;
/*      */ import net.minecraft.server.EntityHuman;
/*      */ import net.minecraft.server.EntityIronGolem;
/*      */ import net.minecraft.server.EntityLiving;
/*      */ import net.minecraft.server.EntityMagmaCube;
/*      */ import net.minecraft.server.EntityMushroomCow;
/*      */ import net.minecraft.server.EntityOcelot;
/*      */ import net.minecraft.server.EntityPig;
/*      */ import net.minecraft.server.EntityPigZombie;
/*      */ import net.minecraft.server.EntityPlayer;
/*      */ import net.minecraft.server.EntitySheep;
/*      */ import net.minecraft.server.EntitySilverfish;
/*      */ import net.minecraft.server.EntitySkeleton;
/*      */ import net.minecraft.server.EntitySlime;
/*      */ import net.minecraft.server.EntitySnowman;
/*      */ import net.minecraft.server.EntitySpider;
/*      */ import net.minecraft.server.EntityVillager;
/*      */ import net.minecraft.server.EntityWeatherLighting;
/*      */ import net.minecraft.server.EntityWolf;
/*      */ import net.minecraft.server.EntityZombie;
/*      */ import net.minecraft.server.EnumMovingObjectType;
/*      */ import net.minecraft.server.IInventory;
/*      */ import net.minecraft.server.Item;
/*      */ import net.minecraft.server.ItemStack;
/*      */ import net.minecraft.server.MathHelper;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.server.MovingObjectPosition;
/*      */ import net.minecraft.server.PlayerInventory;
/*      */ import net.minecraft.server.Vec3D;
/*      */ import net.minecraft.server.World;
/*      */ 
/*      */ public class EEBase
/*      */ {
/*   54 */   public static HashMap playerSwordMode = new HashMap();
/*   55 */   public static HashMap playerWatchCycle = new HashMap();
/*   56 */   public static HashMap playerBuildMode = new HashMap();
/*   57 */   public static HashMap playerInWater = new HashMap();
/*   58 */   public static HashMap playerInLava = new HashMap();
/*   59 */   public static HashMap playerHammerMode = new HashMap();
/*   60 */   public static HashMap playerArmorOffensiveToggle = new HashMap();
/*   61 */   public static HashMap playerArmorMovementToggle = new HashMap();
/*   62 */   public static HashMap playerToggleCooldown = new HashMap();
/*   63 */   public static HashMap playerToolMode = new HashMap();
/*   64 */   public static HashMap playerWatchMagnitude = new HashMap();
/*   65 */   public static HashMap playerLeftClick = new HashMap();
/*   66 */   public static HashMap playerTransGridOpen = new HashMap();
/*   67 */   public static HashMap playerItemCharging = new HashMap();
/*   68 */   public static HashMap playerEffectDurations = new HashMap();
/*   69 */   public static HashMap playerKnowledge = new HashMap();
/*      */   private static BaseMod instance;
/*      */   private static EEBase eeBaseInstance;
/*   72 */   public static boolean initialized = false;
/*      */   public static EEProps props;
/*   74 */   public static int playerWoftFactor = 1;
/*      */   private static boolean leftClickWasDown;
/*      */   private static boolean extraKeyWasDown;
/*      */   private static boolean releaseKeyWasDown;
/*      */   private static boolean chargeKeyWasDown;
/*      */   private static boolean toggleKeyWasDown;
/*      */   public static boolean externalModsInitialized;
/*   81 */   public static int alchChestFront = 0;
/*   82 */   public static int alchChestSide = 1;
/*   83 */   public static int alchChestTop = 2;
/*   84 */   public static int condenserFront = 3;
/*   85 */   public static int condenserSide = 4;
/*   86 */   public static int condenserTop = 5;
/*   87 */   public static int relayFront = 6;
/*   88 */   public static int relaySide = 7;
/*   89 */   public static int relayTop = 8;
/*   90 */   public static int collectorFront = 9;
/*   91 */   public static int collectorSide = 10;
/*   92 */   public static int collectorTop = 11;
/*   93 */   public static int dmFurnaceFront = 12;
/*   94 */   public static int dmBlockSide = 13;
/*   95 */   public static int rmFurnaceFront = 14;
/*   96 */   public static int rmBlockSide = 15;
/*   97 */   public static int iTorchSide = 16;
/*   98 */   public static int novaCatalystSide = 17;
/*   99 */   public static int novaCataclysmSide = 18;
/*  100 */   public static int novaCatalystTop = 19;
/*  101 */   public static int novaCatalystBottom = 20;
/*  102 */   public static int collector2Top = 21;
/*  103 */   public static int collector3Top = 22;
/*  104 */   public static int relay2Top = 23;
/*  105 */   public static int relay3Top = 24;
/*  106 */   public static int transTabletSide = 25;
/*  107 */   public static int transTabletBottom = 26;
/*  108 */   public static int transTabletTop = 27;
/*  109 */   public static int portalDeviceSide = 28;
/*  110 */   public static int portalDeviceBottom = 29;
/*  111 */   public static int portalDeviceTop = 30;
/*  112 */   public static HashMap pedestalCoords = new HashMap();
/*      */   private static int machineFactor;
/*      */ 
/*      */   public static void init(BaseMod var0)
/*      */   {
/*  117 */     if (!initialized)
/*      */     {
/*  119 */       initialized = true;
/*  120 */       instance = var0;
/*  121 */       props = new EEProps(new File("mod_EE.props").getPath());
/*  122 */       props = EEMaps.InitProps(props);
/*  123 */       props.func_26596_save();
/*  124 */       machineFactor = props.getInt("machineFactor");
/*  125 */       setupCraftHook();
/*      */     }
/*      */   }
/*      */ 
/*      */   public int AddFuel(int var1, int var2)
/*      */   {
/*  131 */     if (var1 == EEItem.alchemicalCoal.id)
/*      */     {
/*  133 */       if (var2 == 0)
/*      */       {
/*  135 */         return 6400;
/*      */       }
/*      */     }
/*  138 */     else if (var1 == EEItem.mobiusFuel.id)
/*      */     {
/*  140 */       return 25600;
/*      */     }
/*      */ 
/*  143 */     return 0;
/*      */   }
/*      */ 
/*      */   public static boolean isCurrentItem(Item var0, EntityHuman var1)
/*      */   {
/*  148 */     return var1.inventory.getItemInHand() != null;
/*      */   }
/*      */ 
/*      */   public static boolean isOnQuickBar(Item var0, EntityHuman var1)
/*      */   {
/*  153 */     for (int var2 = 0; var2 < 9; var2++)
/*      */     {
/*  155 */       if ((var1.inventory.getItem(var2) != null) && (var1.inventory.getItem(var2).getItem() == var0))
/*      */       {
/*  157 */         return true;
/*      */       }
/*      */     }
/*      */ 
/*  161 */     return false;
/*      */   }
/*      */ 
/*      */   public static ItemStack[] quickBar(EntityHuman var0)
/*      */   {
/*  166 */     ItemStack[] var1 = new ItemStack[9];
/*      */ 
/*  168 */     for (int var2 = 0; var2 < 9; var2++)
/*      */     {
/*  170 */       var1[var2] = var0.inventory.items[var2];
/*      */     }
/*      */ 
/*  173 */     return var1;
/*      */   }
/*      */ 
/*      */   public static boolean EntityHasItemStack(ItemStack var0, IInventory var1)
/*      */   {
/*  178 */     boolean var2 = var0.getData() == -1;
/*  179 */     ItemStack[] var3 = new ItemStack[40];
/*      */ 
/*  182 */     for (int var4 = 0; var4 < var1.getSize(); var4++)
/*      */     {
/*  184 */       if ((var1.getItem(var4) != null) && ((var1.getItem(var4).doMaterialsMatch(var0)) || ((var1.getItem(var4).getItem() == var0.getItem()) && (var2))))
/*      */       {
/*  186 */         if (var1.getItem(var4).count >= var0.count)
/*      */         {
/*  188 */           return true;
/*      */         }
/*      */ 
/*  191 */         var3[var4] = var1.getItem(var4);
/*      */       }
/*      */     }
/*      */ 
/*  195 */     var4 = 0;
/*      */ 
/*  197 */     for (int var5 = 0; var5 < var1.getSize(); var5++)
/*      */     {
/*  199 */       if ((var3[var5] != null) && ((var3[var5].doMaterialsMatch(var0)) || ((var3[var5].getItem() == var0.getItem()) && (var2))))
/*      */       {
/*  201 */         var4 += var3[var5].count;
/*      */ 
/*  203 */         if (var4 >= var0.count)
/*      */         {
/*  205 */           return true;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  210 */     return false;
/*      */   }
/*      */ 
/*      */   public static boolean HasItemStack(ItemStack var0, EntityHuman var1)
/*      */   {
/*  215 */     boolean var2 = var0.getData() == -1;
/*  216 */     ItemStack[] var3 = new ItemStack[40];
/*  217 */     PlayerInventory var4 = var1.inventory;
/*      */ 
/*  220 */     for (int var5 = 0; var5 < var4.items.length + var4.armor.length; var5++)
/*      */     {
/*  222 */       if ((var4.getItem(var5) != null) && ((var4.getItem(var5).doMaterialsMatch(var0)) || ((var4.getItem(var5).getItem() == var0.getItem()) && (var2))))
/*      */       {
/*  224 */         if (var4.getItem(var5).count >= var0.count)
/*      */         {
/*  226 */           return true;
/*      */         }
/*      */ 
/*  229 */         var3[var5] = var4.getItem(var5);
/*      */       }
/*      */     }
/*      */ 
/*  233 */     var5 = 0;
/*      */ 
/*  235 */     for (int var6 = 0; var6 < var4.items.length + var4.armor.length; var6++)
/*      */     {
/*  237 */       if ((var3[var6] != null) && ((var3[var6].doMaterialsMatch(var0)) || ((var3[var6].getItem() == var0.getItem()) && (var2))))
/*      */       {
/*  239 */         var5 += var3[var6].count;
/*      */ 
/*  241 */         if (var5 >= var0.count)
/*      */         {
/*  243 */           return true;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  248 */     return false;
/*      */   }
/*      */ 
/*      */   public static int getKleinEnergyForDisplay(ItemStack var0)
/*      */   {
/*  253 */     return (var0.getItem() instanceof ItemKleinStar) ? ((ItemKleinStar)var0.getItem()).getKleinPoints(var0) : var0 == null ? 0 : 0;
/*      */   }
/*      */ 
/*      */   public static int getDisplayEnergy(ItemStack var0)
/*      */   {
/*  258 */     if (var0 == null)
/*      */     {
/*  260 */       return 0;
/*      */     }
/*  262 */     if (((var0.getItem() instanceof ItemEECharged)) && ((var0.getItem() instanceof ItemTransTablet)))
/*      */     {
/*  264 */       ItemEECharged var1 = (ItemEECharged)var0.getItem();
/*  265 */       return var1.getInteger(var0, "displayEnergy");
/*      */     }
/*      */ 
/*  269 */     return 0;
/*      */   }
/*      */ 
/*      */   public static void setDisplayEnergy(ItemStack var0, int var1)
/*      */   {
/*  275 */     if (var0 != null)
/*      */     {
/*  277 */       if (((var0.getItem() instanceof ItemEECharged)) && ((var0.getItem() instanceof ItemTransTablet)))
/*      */       {
/*  279 */         ItemEECharged var2 = (ItemEECharged)var0.getItem();
/*  280 */         var2.setInteger(var0, "displayEnergy", var1);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static int getLatentEnergy(ItemStack var0)
/*      */   {
/*  287 */     if (var0 == null)
/*      */     {
/*  289 */       return 0;
/*      */     }
/*  291 */     if (((var0.getItem() instanceof ItemEECharged)) && ((var0.getItem() instanceof ItemTransTablet)))
/*      */     {
/*  293 */       ItemEECharged var1 = (ItemEECharged)var0.getItem();
/*  294 */       return var1.getInteger(var0, "latentEnergy");
/*      */     }
/*      */ 
/*  298 */     return 0;
/*      */   }
/*      */ 
/*      */   public static void setLatentEnergy(ItemStack var0, int var1)
/*      */   {
/*  304 */     if (var0 != null)
/*      */     {
/*  306 */       if (((var0.getItem() instanceof ItemEECharged)) && ((var0.getItem() instanceof ItemTransTablet)))
/*      */       {
/*  308 */         ItemEECharged var2 = (ItemEECharged)var0.getItem();
/*  309 */         var2.setInteger(var0, "latentEnergy", var1);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static boolean canIncreaseKleinStarPoints(ItemStack var0, World var1)
/*      */   {
/*  316 */     if (EEProxy.isClient(var1))
/*      */     {
/*  318 */       return false;
/*      */     }
/*      */ 
/*  322 */     byte var2 = 1;
/*  323 */     return var0 != null;
/*      */   }
/*      */ 
/*      */   public static boolean isKleinStar(int var0)
/*      */   {
/*  329 */     return (var0 == EEItem.kleinStar1.id) || (var0 == EEItem.kleinStar2.id) || (var0 == EEItem.kleinStar3.id) || (var0 == EEItem.kleinStar4.id) || (var0 == EEItem.kleinStar5.id) || (var0 == EEItem.kleinStar6.id);
/*      */   }
/*      */ 
/*      */   public static int getKleinLevel(int var0)
/*      */   {
/*  334 */     return var0 == EEItem.kleinStar6.id ? 6 : var0 == EEItem.kleinStar5.id ? 5 : var0 == EEItem.kleinStar4.id ? 4 : var0 == EEItem.kleinStar3.id ? 3 : var0 == EEItem.kleinStar2.id ? 2 : var0 == EEItem.kleinStar1.id ? 1 : 0;
/*      */   }
/*      */ 
/*      */   public static boolean addKleinStarPoints(ItemStack var0, int var1, World var2)
/*      */   {
/*  339 */     if (EEProxy.isClient(var2))
/*      */     {
/*  341 */       return false;
/*      */     }
/*  343 */     if (var0 == null)
/*      */     {
/*  345 */       return false;
/*      */     }
/*  347 */     if (!isKleinStar(var0.id))
/*      */     {
/*  349 */       return false;
/*      */     }
/*      */ 
/*  353 */     ItemKleinStar var3 = (ItemKleinStar)var0.getItem();
/*      */ 
/*  355 */     if (var3.getKleinPoints(var0) <= var3.getMaxPoints(var0) - var1)
/*      */     {
/*  357 */       var3.setKleinPoints(var0, var3.getKleinPoints(var0) + var1);
/*  358 */       var3.onUpdate(var0);
/*  359 */       return true;
/*      */     }
/*      */ 
/*  363 */     return false;
/*      */   }
/*      */ 
/*      */   public static boolean addKleinStarPoints(ItemStack var0, int var1)
/*      */   {
/*  370 */     if (var0 == null)
/*      */     {
/*  372 */       return false;
/*      */     }
/*  374 */     if (!isKleinStar(var0.id))
/*      */     {
/*  376 */       return false;
/*      */     }
/*      */ 
/*  380 */     ItemKleinStar var2 = (ItemKleinStar)var0.getItem();
/*      */ 
/*  382 */     if (var2.getKleinPoints(var0) <= var2.getMaxPoints(var0) - var1)
/*      */     {
/*  384 */       var2.setKleinPoints(var0, var2.getKleinPoints(var0) + var1);
/*  385 */       var2.onUpdate(var0);
/*  386 */       return true;
/*      */     }
/*      */ 
/*  390 */     return false;
/*      */   }
/*      */ 
/*      */   public static boolean takeKleinStarPoints(ItemStack var0, int var1, World var2)
/*      */   {
/*  397 */     if (EEProxy.isClient(var2))
/*      */     {
/*  399 */       return false;
/*      */     }
/*  401 */     if (var0 == null)
/*      */     {
/*  403 */       return false;
/*      */     }
/*  405 */     if (!isKleinStar(var0.id))
/*      */     {
/*  407 */       return false;
/*      */     }
/*      */ 
/*  411 */     ItemKleinStar var3 = (ItemKleinStar)var0.getItem();
/*      */ 
/*  413 */     if (var3.getKleinPoints(var0) >= var1)
/*      */     {
/*  415 */       var3.setKleinPoints(var0, var3.getKleinPoints(var0) - var1);
/*  416 */       var3.onUpdate(var0);
/*  417 */       return true;
/*      */     }
/*      */ 
/*  421 */     return false;
/*      */   }
/*      */ 
/*      */   public static boolean consumeKleinStarPoint(EntityHuman var0, int var1)
/*      */   {
/*  428 */     if (var0 == null)
/*      */     {
/*  430 */       return false;
/*      */     }
/*  432 */     if (EEProxy.isClient(var0.world))
/*      */     {
/*  434 */       return false;
/*      */     }
/*      */ 
/*  438 */     PlayerInventory var2 = var0.inventory;
/*      */ 
/*  440 */     for (int var3 = 0; var3 < var2.items.length; var3++)
/*      */     {
/*  442 */       if (var2.getItem(var3) != null)
/*      */       {
/*  444 */         ItemStack var4 = var2.getItem(var3);
/*      */ 
/*  446 */         if ((isKleinStar(var4.id)) && (takeKleinStarPoints(var4, var1, var0.world)))
/*      */         {
/*  448 */           return true;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  453 */     return false;
/*      */   }
/*      */ 
/*      */   public static boolean Consume(ItemStack var0, EntityHuman var1, boolean var2)
/*      */   {
/*  459 */     if (var1 == null)
/*      */     {
/*  461 */       return false;
/*      */     }
/*  463 */     if (EEProxy.isClient(var1.world))
/*      */     {
/*  465 */       return false;
/*      */     }
/*      */ 
/*  469 */     int var3 = var0.count;
/*  470 */     int var4 = 0;
/*  471 */     boolean var5 = false;
/*      */ 
/*  473 */     if (var0.getData() == -1)
/*      */     {
/*  475 */       var5 = true;
/*      */     }
/*      */ 
/*  478 */     ItemStack[] var6 = var1.inventory.items;
/*      */ 
/*  481 */     for (int var7 = 0; var7 < var6.length; var7++)
/*      */     {
/*  483 */       if (var6[var7] != null)
/*      */       {
/*  485 */         if (var3 <= var4)
/*      */         {
/*      */           break;
/*      */         }
/*      */ 
/*  490 */         if ((var6[var7].doMaterialsMatch(var0)) || ((var5) && (var6[var7].id == var0.id)))
/*      */         {
/*  492 */           var4 += var6[var7].count;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  497 */     if (var4 < var3)
/*      */     {
/*  499 */       return false;
/*      */     }
/*      */ 
/*  503 */     var4 = 0;
/*      */ 
/*  505 */     for (var7 = 0; var7 < var6.length; var7++)
/*      */     {
/*  507 */       if ((var6[var7] != null) && ((var6[var7].doMaterialsMatch(var0)) || ((var5) && (var6[var7].id == var0.id))))
/*      */       {
/*  509 */         for (int var8 = var6[var7].count; var8 > 0; var8--)
/*      */         {
/*  511 */           var6[var7].count -= 1;
/*      */ 
/*  513 */           if (var6[var7].count == 0)
/*      */           {
/*  515 */             var6[var7] = null;
/*      */           }
/*      */ 
/*  518 */           var4++;
/*      */ 
/*  520 */           if (var4 >= var3)
/*      */           {
/*  522 */             return true;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  528 */     if (var2)
/*      */     {
/*  530 */       var1.a("You don't have enough fuel/klein power to do that.");
/*      */     }
/*      */ 
/*  533 */     return false;
/*      */   }
/*      */ 
/*      */   public static double direction(EntityHuman var0)
/*      */   {
/*  540 */     return var0.pitch <= -55.0F ? 1.0D : (var0.pitch > -55.0F) && (var0.pitch < 55.0F) ? (MathHelper.floor(var0.yaw * 4.0F / 360.0F + 0.5D) & 0x3) + 2 : 0.0D;
/*      */   }
/*      */ 
/*      */   public static double heading(EntityHuman var0)
/*      */   {
/*  545 */     return (MathHelper.floor(var0.yaw * 4.0F / 360.0F + 0.5D) & 0x3) + 2;
/*      */   }
/*      */ 
/*      */   public static double playerX(EntityHuman var0)
/*      */   {
/*  550 */     return MathHelper.floor(var0.locX);
/*      */   }
/*      */ 
/*      */   public static double playerY(EntityHuman var0)
/*      */   {
/*  555 */     return MathHelper.floor(var0.locY);
/*      */   }
/*      */ 
/*      */   public static double playerZ(EntityHuman var0)
/*      */   {
/*  560 */     return MathHelper.floor(var0.locZ);
/*      */   }
/*      */ 
/*      */   public static void doLeftClick(World var0, EntityHuman var1)
/*      */   {
/*  565 */     if (var1.U() != null)
/*      */     {
/*  567 */       if ((var1.U().getItem() instanceof ItemEECharged))
/*      */       {
/*  569 */         ((ItemEECharged)var1.U().getItem()).doLeftClick(var1.U(), var0, var1);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void doAlternate(World var0, EntityHuman var1)
/*      */   {
/*  576 */     ItemStack var2 = var1.U();
/*      */ 
/*  578 */     if (var2 == null)
/*      */     {
/*  580 */       armorCheck(var1);
/*      */     }
/*  582 */     else if ((var1.U().getItem() instanceof ItemEECharged))
/*      */     {
/*  584 */       ((ItemEECharged)var1.U().getItem()).doAlternate(var1.U(), var0, var1);
/*      */     }
/*      */     else
/*      */     {
/*  588 */       armorCheck(var1);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static void armorCheck(EntityHuman var0)
/*      */   {
/*  594 */     if ((hasRedArmor(var0)) && (getPlayerArmorOffensive(var0)))
/*      */     {
/*  596 */       Combustion var1 = new Combustion(var0.world, var0, var0.locX, var0.locY, var0.locZ, 4.0F);
/*  597 */       var1.doExplosionA();
/*  598 */       var1.doExplosionB(true);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static boolean hasRedArmor(EntityHuman var0)
/*      */   {
/*  604 */     return (var0.inventory.armor[2] != null) && ((var0.inventory.armor[2].getItem() instanceof ItemRedArmorPlus));
/*      */   }
/*      */ 
/*      */   public static void doToggle(World var0, EntityHuman var1)
/*      */   {
/*  609 */     ItemStack var2 = var1.U();
/*      */ 
/*  611 */     if (var2 == null)
/*      */     {
/*  613 */       if ((hasMovementArmor(var1)) && (getPlayerToggleCooldown(var1) <= 0))
/*      */       {
/*  615 */         updatePlayerArmorMovement(var1, true);
/*  616 */         setPlayerToggleCooldown(var1, 20);
/*      */       }
/*      */     }
/*  619 */     else if ((var1.U().getItem() instanceof ItemEECharged))
/*      */     {
/*  621 */       ((ItemEECharged)var1.U().getItem()).doToggle(var1.U(), var0, var1);
/*      */     }
/*  623 */     else if ((hasMovementArmor(var1)) && (getPlayerToggleCooldown(var1) <= 0))
/*      */     {
/*  625 */       updatePlayerArmorMovement(var1, true);
/*  626 */       setPlayerToggleCooldown(var1, 20);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void doJumpTick(World var0, EntityPlayer var1)
/*      */   {
/*  632 */     bootsCheck(var1);
/*      */   }
/*      */ 
/*      */   private static void bootsCheck(EntityHuman var0)
/*      */   {
/*  637 */     if ((hasRedBoots(var0)) && (getPlayerArmorMovement(var0)))
/*      */     {
/*  639 */       var0.motY += 0.1D;
/*      */     }
/*      */   }
/*      */ 
/*      */   private static boolean hasRedBoots(EntityHuman var0)
/*      */   {
/*  645 */     return (var0.inventory.armor[0] != null) && ((var0.inventory.armor[0].getItem() instanceof ItemRedArmorPlus));
/*      */   }
/*      */ 
/*      */   public static void doSneakTick(World var0, EntityPlayer var1)
/*      */   {
/*  650 */     greavesCheck(var1);
/*      */   }
/*      */ 
/*      */   private static void greavesCheck(EntityHuman var0)
/*      */   {
/*  655 */     if ((hasRedGreaves(var0)) && (getPlayerArmorOffensive(var0)))
/*      */     {
/*  657 */       var0.motY -= 0.97D;
/*  658 */       doShockwave(var0);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static void doShockwave(EntityHuman var0)
/*      */   {
/*  664 */     List var1 = var0.world.a(EntityLiving.class, AxisAlignedBB.b(var0.locX - 7.0D, var0.locY - 7.0D, var0.locZ - 7.0D, var0.locX + 7.0D, var0.locY + 7.0D, var0.locZ + 7.0D));
/*      */ 
/*  666 */     for (int var2 = 0; var2 < var1.size(); var2++)
/*      */     {
/*  668 */       Entity var3 = (Entity)var1.get(var2);
/*      */ 
/*  670 */       if (!(var3 instanceof EntityHuman))
/*      */       {
/*  672 */         var3.motX += 0.2D / (var3.locX - var0.locX);
/*  673 */         var3.motY += 0.05999999865889549D;
/*  674 */         var3.motZ += 0.2D / (var3.locZ - var0.locZ);
/*      */       }
/*      */     }
/*      */ 
/*  678 */     List var6 = var0.world.a(EntityArrow.class, AxisAlignedBB.b((float)var0.locX - 5.0F, var0.locY - 5.0D, (float)var0.locZ - 5.0F, (float)var0.locX + 5.0F, var0.locY + 5.0D, (float)var0.locZ + 5.0F));
/*      */ 
/*  680 */     for (int var7 = 0; var7 < var6.size(); var7++)
/*      */     {
/*  682 */       Entity var4 = (Entity)var6.get(var7);
/*  683 */       var4.motX += 0.2D / (var4.locX - var0.locX);
/*  684 */       var4.motY += 0.05999999865889549D;
/*  685 */       var4.motZ += 0.2D / (var4.locZ - var0.locZ);
/*      */     }
/*      */ 
/*  688 */     List var8 = var0.world.a(EntityFireball.class, AxisAlignedBB.b((float)var0.locX - 5.0F, var0.locY - 5.0D, (float)var0.locZ - 5.0F, (float)var0.locX + 5.0F, var0.locY + 5.0D, (float)var0.locZ + 5.0F));
/*      */ 
/*  690 */     for (int var9 = 0; var9 < var8.size(); var9++)
/*      */     {
/*  692 */       Entity var5 = (Entity)var8.get(var9);
/*  693 */       var5.motX += 0.2D / (var5.locX - var0.locX);
/*  694 */       var5.motY += 0.05999999865889549D;
/*  695 */       var5.motZ += 0.2D / (var5.locZ - var0.locZ);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static boolean hasRedGreaves(EntityHuman var0)
/*      */   {
/*  701 */     return (var0.inventory.armor[1] != null) && ((var0.inventory.armor[1].getItem() instanceof ItemRedArmorPlus));
/*      */   }
/*      */ 
/*      */   public static void doRelease(World var0, EntityHuman var1)
/*      */   {
/*  706 */     ItemStack var2 = var1.U();
/*      */ 
/*  708 */     if (var2 == null)
/*      */     {
/*  710 */       helmetCheck(var1);
/*      */     }
/*  712 */     else if ((var2.getItem() instanceof ItemEECharged))
/*      */     {
/*  714 */       ((ItemEECharged)var1.U().getItem()).doRelease(var1.U(), var0, var1);
/*      */     }
/*      */     else
/*      */     {
/*  718 */       helmetCheck(var1);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static void helmetCheck(EntityHuman var0)
/*      */   {
/*  724 */     if ((hasRedHelmet(var0)) && (getPlayerArmorOffensive(var0)))
/*      */     {
/*  726 */       float var1 = 1.0F;
/*  727 */       float var2 = var0.lastPitch + (var0.pitch - var0.lastPitch) * var1;
/*  728 */       float var3 = var0.lastYaw + (var0.yaw - var0.lastYaw) * var1;
/*  729 */       double var4 = var0.lastX + (var0.locX - var0.lastX) * var1;
/*  730 */       double var6 = var0.lastY + (var0.locY - var0.lastY) * var1 + 1.62D - var0.height;
/*  731 */       double var8 = var0.lastZ + (var0.locZ - var0.lastZ) * var1;
/*  732 */       Vec3D var10 = Vec3D.create(var4, var6, var8);
/*  733 */       float var11 = MathHelper.cos(-var3 * 0.01745329F - 3.141593F);
/*  734 */       float var12 = MathHelper.sin(-var3 * 0.01745329F - 3.141593F);
/*  735 */       float var13 = -MathHelper.cos(-var2 * 0.01745329F);
/*  736 */       float var14 = MathHelper.sin(-var2 * 0.01745329F);
/*  737 */       float var15 = var12 * var13;
/*  738 */       float var17 = var11 * var13;
/*  739 */       double var18 = 150.0D;
/*  740 */       Vec3D var20 = var10.add(var15 * var18, var14 * var18, var17 * var18);
/*  741 */       MovingObjectPosition var21 = var0.world.rayTrace(var10, var20, true);
/*      */ 
/*  743 */       if (var21 == null)
/*      */       {
/*  745 */         return;
/*      */       }
/*      */ 
/*  748 */       if (var21.type == EnumMovingObjectType.TILE)
/*      */       {
/*  750 */         int var22 = var21.b;
/*  751 */         int var23 = var21.c;
/*  752 */         int var24 = var21.d;
/*  753 */         var0.world.strikeLightning(new EntityWeatherLighting(var0.world, var22, var23, var24));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static boolean hasRedHelmet(EntityHuman var0)
/*      */   {
/*  760 */     return (var0.inventory.armor[3] != null) && ((var0.inventory.armor[3].getItem() instanceof ItemRedArmorPlus));
/*      */   }
/*      */ 
/*      */   public static void doCharge(World var0, EntityHuman var1)
/*      */   {
/*  765 */     ItemStack var2 = var1.U();
/*      */ 
/*  767 */     if (var2 == null)
/*      */     {
/*  769 */       if ((hasOffensiveArmor(var1)) && (getPlayerToggleCooldown(var1) <= 0))
/*      */       {
/*  771 */         updatePlayerArmorOffensive(var1, true);
/*  772 */         setPlayerToggleCooldown(var1, 20);
/*      */       }
/*      */     }
/*  775 */     else if ((var2.getItem() instanceof ItemEECharged))
/*      */     {
/*  777 */       ItemEECharged var3 = (ItemEECharged)var2.getItem();
/*      */ 
/*  779 */       if (!var1.isSneaking())
/*      */       {
/*  781 */         if ((var3.getMaxCharge() > 0) && (var3.chargeLevel(var2) < var3.getMaxCharge()) && (var3.chargeGoal(var2) < var3.getMaxCharge()))
/*      */         {
/*  783 */           var3.setShort(var2, "chargeGoal", var3.chargeGoal(var2) + 1);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  788 */         var3.doUncharge(var2, var0, var1);
/*      */       }
/*      */     }
/*  791 */     else if ((hasOffensiveArmor(var1)) && (getPlayerToggleCooldown(var1) <= 0))
/*      */     {
/*  793 */       updatePlayerArmorOffensive(var1, true);
/*  794 */       setPlayerToggleCooldown(var1, 20);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static boolean hasOffensiveArmor(EntityHuman var0)
/*      */   {
/*  800 */     return ((var0.inventory.armor[2] != null) && ((var0.inventory.armor[2].getItem() instanceof ItemRedArmorPlus))) || ((var0.inventory.armor[1] != null) && ((var0.inventory.armor[1].getItem() instanceof ItemRedArmorPlus))) || ((var0.inventory.armor[3] != null) && ((var0.inventory.armor[3].getItem() instanceof ItemRedArmorPlus)));
/*      */   }
/*      */ 
/*      */   private static boolean hasMovementArmor(EntityHuman var0)
/*      */   {
/*  805 */     return (var0.inventory.armor[0] != null) && ((var0.inventory.armor[0].getItem() instanceof ItemRedArmorPlus));
/*      */   }
/*      */ 
/*      */   static boolean isPlayerCharging(EntityHuman var0, Item var1)
/*      */   {
/*  810 */     return ((HashMap)playerItemCharging.get(var0)).get(var1) == null ? false : playerItemCharging.get(var0) == null ? false : ((Boolean)((HashMap)playerItemCharging.get(var0)).get(var1)).booleanValue();
/*      */   }
/*      */ 
/*      */   public static void updatePlayerEffect(Item var0, int var1, EntityHuman var2)
/*      */   {
/*  815 */     HashMap var3 = new HashMap();
/*      */ 
/*  817 */     if (playerEffectDurations.get(var2) != null)
/*      */     {
/*  819 */       var3 = (HashMap)playerEffectDurations.get(var2);
/*      */     }
/*      */ 
/*  822 */     var3.put(var0, Integer.valueOf(var1));
/*  823 */     playerEffectDurations.put(var2, var3);
/*      */   }
/*      */ 
/*      */   public static int getPlayerEffect(Item var0, EntityHuman var1)
/*      */   {
/*  828 */     return ((HashMap)playerEffectDurations.get(var1)).get(var0) == null ? 0 : playerEffectDurations.get(var1) == null ? 0 : ((Integer)((HashMap)playerEffectDurations.get(var1)).get(var0)).intValue();
/*      */   }
/*      */ 
/*      */   public static int getPlayerToggleCooldown(EntityHuman var0)
/*      */   {
/*  833 */     if (playerToggleCooldown.get(var0) == null)
/*      */     {
/*  835 */       playerToggleCooldown.put(var0, Integer.valueOf(0));
/*      */     }
/*      */ 
/*  838 */     return ((Integer)playerToggleCooldown.get(var0)).intValue();
/*      */   }
/*      */ 
/*      */   public static void setPlayerToggleCooldown(EntityHuman var0, int var1)
/*      */   {
/*  843 */     if (playerToggleCooldown.get(var0) == null)
/*      */     {
/*  845 */       playerToggleCooldown.put(var0, Integer.valueOf(0));
/*      */     }
/*      */     else
/*      */     {
/*  849 */       playerToggleCooldown.put(var0, Integer.valueOf(var1));
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void updatePlayerToggleCooldown(EntityHuman var0)
/*      */   {
/*  855 */     if (playerToggleCooldown.get(var0) == null)
/*      */     {
/*  857 */       playerToggleCooldown.put(var0, Integer.valueOf(0));
/*      */     }
/*      */     else
/*      */     {
/*  861 */       playerToggleCooldown.put(var0, Integer.valueOf(((Integer)playerToggleCooldown.get(var0)).intValue() - 1));
/*      */     }
/*      */   }
/*      */ 
/*      */   public static int getBuildMode(EntityHuman var0)
/*      */   {
/*  867 */     if (playerBuildMode.get(var0) == null)
/*      */     {
/*  869 */       playerBuildMode.put(var0, Integer.valueOf(0));
/*      */     }
/*      */ 
/*  872 */     return ((Integer)playerBuildMode.get(var0)).intValue();
/*      */   }
/*      */ 
/*      */   public static void updateBuildMode(EntityHuman var0)
/*      */   {
/*  877 */     if (playerBuildMode.get(var0) == null)
/*      */     {
/*  879 */       playerBuildMode.put(var0, Integer.valueOf(0));
/*      */     }
/*  881 */     else if (((Integer)playerBuildMode.get(var0)).intValue() == 3)
/*      */     {
/*  883 */       playerBuildMode.put(var0, Integer.valueOf(0));
/*      */     }
/*      */     else
/*      */     {
/*  887 */       playerBuildMode.put(var0, Integer.valueOf(((Integer)playerBuildMode.get(var0)).intValue() + 1));
/*      */     }
/*      */ 
/*  890 */     if (((Integer)playerBuildMode.get(var0)).intValue() == 0)
/*      */     {
/*  892 */       var0.a("Mercurial Extension mode.");
/*      */     }
/*  894 */     else if (((Integer)playerBuildMode.get(var0)).intValue() == 1)
/*      */     {
/*  896 */       var0.a("Mercurial Creation mode.");
/*      */     }
/*  898 */     else if (((Integer)playerBuildMode.get(var0)).intValue() == 2)
/*      */     {
/*  900 */       var0.a("Mercurial Transmute mode.");
/*      */     }
/*  902 */     else if (((Integer)playerBuildMode.get(var0)).intValue() == 3)
/*      */     {
/*  904 */       var0.a("Mercurial Pillar mode. [Careful!]");
/*      */     }
/*      */   }
/*      */ 
/*      */   public static boolean getPlayerArmorOffensive(EntityHuman var0)
/*      */   {
/*  910 */     if (playerArmorOffensiveToggle.get(var0) == null)
/*      */     {
/*  912 */       playerArmorOffensiveToggle.put(var0, Boolean.valueOf(false));
/*      */     }
/*      */ 
/*  915 */     return ((Boolean)playerArmorOffensiveToggle.get(var0)).booleanValue();
/*      */   }
/*      */ 
/*      */   public static void updatePlayerArmorOffensive(EntityHuman var0, boolean var1)
/*      */   {
/*  920 */     if (playerArmorOffensiveToggle.get(var0) == null)
/*      */     {
/*  922 */       playerArmorOffensiveToggle.put(var0, Boolean.valueOf(false));
/*      */     }
/*      */     else
/*      */     {
/*  926 */       playerArmorOffensiveToggle.put(var0, Boolean.valueOf(!((Boolean)playerArmorOffensiveToggle.get(var0)).booleanValue()));
/*      */     }
/*      */ 
/*  929 */     if (((Boolean)playerArmorOffensiveToggle.get(var0)).booleanValue())
/*      */     {
/*  931 */       if (var1)
/*      */       {
/*  933 */         var0.a("Armor offensive powers on.");
/*      */       }
/*      */     }
/*  936 */     else if (var1)
/*      */     {
/*  938 */       var0.a("Armor offensive powers off.");
/*      */     }
/*      */   }
/*      */ 
/*      */   public static boolean getPlayerArmorMovement(EntityHuman var0)
/*      */   {
/*  944 */     if (playerArmorMovementToggle.get(var0) == null)
/*      */     {
/*  946 */       playerArmorMovementToggle.put(var0, Boolean.valueOf(false));
/*      */     }
/*      */ 
/*  949 */     return ((Boolean)playerArmorMovementToggle.get(var0)).booleanValue();
/*      */   }
/*      */ 
/*      */   public static void updatePlayerArmorMovement(EntityHuman var0, boolean var1)
/*      */   {
/*  954 */     if (playerArmorMovementToggle.get(var0) == null)
/*      */     {
/*  956 */       playerArmorMovementToggle.put(var0, Boolean.valueOf(false));
/*      */     }
/*      */     else
/*      */     {
/*  960 */       playerArmorMovementToggle.put(var0, Boolean.valueOf(!((Boolean)playerArmorMovementToggle.get(var0)).booleanValue()));
/*      */     }
/*      */ 
/*  963 */     if (((Boolean)playerArmorMovementToggle.get(var0)).booleanValue())
/*      */     {
/*  965 */       if (var1)
/*      */       {
/*  967 */         var0.a("Armor movement powers on.");
/*      */       }
/*      */     }
/*  970 */     else if (var1)
/*      */     {
/*  972 */       var0.a("Armor movement powers off.");
/*      */     }
/*      */   }
/*      */ 
/*      */   public static boolean getHammerMode(EntityHuman var0)
/*      */   {
/*  978 */     if (playerHammerMode.get(var0) == null)
/*      */     {
/*  980 */       playerHammerMode.put(var0, Boolean.valueOf(false));
/*  981 */       return false;
/*      */     }
/*      */ 
/*  985 */     return ((Boolean)playerHammerMode.get(var0)).booleanValue();
/*      */   }
/*      */ 
/*      */   public static void updateHammerMode(EntityHuman var0, boolean var1)
/*      */   {
/*  991 */     if (playerHammerMode.get(var0) == null)
/*      */     {
/*  993 */       playerHammerMode.put(var0, Boolean.valueOf(false));
/*      */     }
/*      */     else
/*      */     {
/*  997 */       playerHammerMode.put(var0, Boolean.valueOf(!((Boolean)playerHammerMode.get(var0)).booleanValue()));
/*      */     }
/*      */ 
/* 1000 */     if (((Boolean)playerHammerMode.get(var0)).booleanValue())
/*      */     {
/* 1002 */       if (var1)
/*      */       {
/* 1004 */         var0.a("Hammer mega-impact mode.");
/*      */       }
/*      */     }
/* 1007 */     else if (var1)
/*      */     {
/* 1009 */       var0.a("Hammer normal-impact mode.");
/*      */     }
/*      */   }
/*      */ 
/*      */   public static boolean getSwordMode(EntityHuman var0)
/*      */   {
/* 1015 */     if (playerSwordMode.get(var0) == null)
/*      */     {
/* 1017 */       playerSwordMode.put(var0, Boolean.valueOf(false));
/* 1018 */       return false;
/*      */     }
/*      */ 
/* 1022 */     return ((Boolean)playerSwordMode.get(var0)).booleanValue();
/*      */   }
/*      */ 
/*      */   public static void updateSwordMode(EntityHuman var0)
/*      */   {
/* 1028 */     if (playerSwordMode.get(var0) == null)
/*      */     {
/* 1030 */       playerSwordMode.put(var0, Boolean.valueOf(false));
/*      */     }
/*      */     else
/*      */     {
/* 1034 */       playerSwordMode.put(var0, Boolean.valueOf(!((Boolean)playerSwordMode.get(var0)).booleanValue()));
/*      */     }
/*      */ 
/* 1037 */     if (((Boolean)playerSwordMode.get(var0)).booleanValue())
/*      */     {
/* 1039 */       var0.a("Sword AoE will harm peaceful/aggressive.");
/*      */     }
/*      */     else
/*      */     {
/* 1043 */       var0.a("Sword AoE will harm aggressive only.");
/*      */     }
/*      */   }
/*      */ 
/*      */   public static int getWatchCycle(EntityHuman var0)
/*      */   {
/* 1049 */     if (playerWatchCycle.get(var0) == null)
/*      */     {
/* 1051 */       playerWatchCycle.put(var0, Integer.valueOf(0));
/* 1052 */       return 0;
/*      */     }
/*      */ 
/* 1056 */     return ((Integer)playerWatchCycle.get(var0)).intValue();
/*      */   }
/*      */ 
/*      */   public static void updateWatchCycle(EntityHuman var0)
/*      */   {
/* 1062 */     if (playerWatchCycle.get(var0) == null)
/*      */     {
/* 1064 */       playerWatchCycle.put(var0, Integer.valueOf(0));
/*      */     }
/* 1066 */     else if (((Integer)playerWatchCycle.get(var0)).intValue() == 2)
/*      */     {
/* 1068 */       playerWatchCycle.put(var0, Integer.valueOf(0));
/*      */     }
/*      */     else
/*      */     {
/* 1072 */       playerWatchCycle.put(var0, Integer.valueOf(((Integer)playerWatchCycle.get(var0)).intValue() + 1));
/*      */     }
/*      */ 
/* 1075 */     if (((Integer)playerWatchCycle.get(var0)).intValue() == 0)
/*      */     {
/* 1077 */       var0.a("Sun-scroll is off.");
/*      */     }
/*      */ 
/* 1080 */     if (((Integer)playerWatchCycle.get(var0)).intValue() == 1)
/*      */     {
/* 1082 */       var0.a("Sun-scrolling forward.");
/*      */     }
/*      */ 
/* 1085 */     if (((Integer)playerWatchCycle.get(var0)).intValue() == 2)
/*      */     {
/* 1087 */       var0.a("Sun-scrolling backwards.");
/*      */     }
/*      */   }
/*      */ 
/*      */   public static int getToolMode(EntityHuman var0)
/*      */   {
/* 1093 */     if (playerToolMode.get(var0) == null)
/*      */     {
/* 1095 */       playerToolMode.put(var0, Integer.valueOf(0));
/* 1096 */       return 0;
/*      */     }
/*      */ 
/* 1100 */     return ((Integer)playerToolMode.get(var0)).intValue();
/*      */   }
/*      */ 
/*      */   public static void updateToolMode(EntityHuman var0)
/*      */   {
/* 1106 */     if (playerToolMode.get(var0) == null)
/*      */     {
/* 1108 */       playerToolMode.put(var0, Integer.valueOf(0));
/*      */     }
/* 1110 */     else if (((Integer)playerToolMode.get(var0)).intValue() == 3)
/*      */     {
/* 1112 */       playerToolMode.put(var0, Integer.valueOf(0));
/*      */     }
/*      */     else
/*      */     {
/* 1116 */       playerToolMode.put(var0, Integer.valueOf(((Integer)playerToolMode.get(var0)).intValue() + 1));
/*      */     }
/*      */ 
/* 1119 */     if (((Integer)playerToolMode.get(var0)).intValue() == 0)
/*      */     {
/* 1121 */       var0.a("Tool set to normal.");
/*      */     }
/*      */ 
/* 1124 */     if (((Integer)playerToolMode.get(var0)).intValue() == 1)
/*      */     {
/* 1126 */       var0.a("Tool set to tall-shot.");
/*      */     }
/*      */ 
/* 1129 */     if (((Integer)playerToolMode.get(var0)).intValue() == 2)
/*      */     {
/* 1131 */       var0.a("Tool set to wide-shot.");
/*      */     }
/*      */ 
/* 1134 */     if (((Integer)playerToolMode.get(var0)).intValue() == 3)
/*      */     {
/* 1136 */       var0.a("Tool set to long-shot.");
/*      */     }
/*      */   }
/*      */ 
/*      */   public static boolean isPlayerInLava(EntityHuman var0)
/*      */   {
/* 1142 */     if (playerInLava.get(var0) == null)
/*      */     {
/* 1144 */       playerInLava.put(var0, Boolean.valueOf(false));
/* 1145 */       return false;
/*      */     }
/*      */ 
/* 1149 */     return ((Boolean)playerInLava.get(var0)).booleanValue();
/*      */   }
/*      */ 
/*      */   public static void updatePlayerInLava(EntityHuman var0, boolean var1)
/*      */   {
/* 1155 */     playerInLava.put(var0, Boolean.valueOf(var1));
/*      */   }
/*      */ 
/*      */   public static boolean isPlayerInWater(EntityHuman var0)
/*      */   {
/* 1160 */     if (playerInWater.get(var0) == null)
/*      */     {
/* 1162 */       playerInWater.put(var0, Boolean.valueOf(false));
/* 1163 */       return false;
/*      */     }
/*      */ 
/* 1167 */     return ((Boolean)playerInWater.get(var0)).booleanValue();
/*      */   }
/*      */ 
/*      */   public static void updatePlayerInWater(EntityHuman var0, boolean var1)
/*      */   {
/* 1173 */     playerInWater.put(var0, Boolean.valueOf(var1));
/*      */   }
/*      */ 
/*      */   private static void setupCraftHook()
/*      */   {
/* 1178 */     ICraftingHandler var0 = new ICraftingHandler()
/*      */     {
/*      */       public void onTakenFromCrafting(EntityHuman var1, ItemStack var2, IInventory var3)
/*      */       {
/* 1182 */         int var4 = 0;
/*      */ 
/* 1185 */         if ((var2 != null) && (EEMergeLib.mergeOnCraft.contains(Integer.valueOf(var2.id))))
/*      */         {
/* 1187 */           for (int var5 = 0; var5 < var3.getSize(); var5++)
/*      */           {
/* 1189 */             ItemStack var6 = var3.getItem(var5);
/*      */ 
/* 1191 */             if ((var6 != null) && ((var6.getItem() instanceof ItemKleinStar)) && (((ItemKleinStar)var6.getItem()).getKleinPoints(var6) > 0))
/*      */             {
/* 1193 */               var4 += ((ItemKleinStar)var6.getItem()).getKleinPoints(var6);
/*      */             }
/*      */           }
/*      */ 
/* 1197 */           ((ItemKleinStar)var2.getItem()).setKleinPoints(var2, var4);
/*      */         }
/* 1199 */         else if ((var2 != null) && (EEMergeLib.destroyOnCraft.contains(Integer.valueOf(var2.id))) && (var2.id == EEItem.arcaneRing.id))
/*      */         {
/* 1201 */           for (int var5 = 0; var5 < var3.getSize(); var5++)
/*      */           {
/* 1203 */             var3.setItem(var5, null);
/*      */           }
/*      */         }
/*      */       }
/*      */     };
/* 1208 */     MinecraftForge.registerCraftingHandler(var0);
/*      */   }
/*      */ 
/*      */   public static EEBase getInstance()
/*      */   {
/* 1213 */     return eeBaseInstance;
/*      */   }
/*      */ 
/*      */   public static float getPedestalFactor(World var0)
/*      */   {
/* 1218 */     float var1 = 1.0F;
/* 1219 */     validatePedestalCoords(var0);
/*      */ 
/* 1221 */     for (int var2 = 0; var2 < pedestalCoords.size(); var2++)
/*      */     {
/* 1223 */       if (pedestalCoords.get(Integer.valueOf(var2)) != null)
/*      */       {
/* 1225 */         var1 = (float)(var1 * 0.9D);
/*      */       }
/*      */     }
/*      */ 
/* 1229 */     return var1 < 0.1F ? 0.1F : var1;
/*      */   }
/*      */ 
/*      */   public static void addPedestalCoords(TilePedestal var0)
/*      */   {
/* 1234 */     int var1 = 0;
/*      */ 
/* 1237 */     for (Integer[] var2 = { Integer.valueOf(var0.x), Integer.valueOf(var0.y), Integer.valueOf(var0.z) }; pedestalCoords.get(Integer.valueOf(var1)) != null; var1++);
/* 1242 */     pedestalCoords.put(Integer.valueOf(var1), var2);
/* 1243 */     validatePedestalCoords(var0.world);
/*      */   }
/*      */ 
/*      */   public static void validatePedestalCoords(World var0)
/*      */   {
/* 1248 */     for (int var1 = 0; var1 < pedestalCoords.size(); var1++)
/*      */     {
/* 1250 */       if (pedestalCoords.get(Integer.valueOf(var1)) != null)
/*      */       {
/* 1252 */         Integer[] var2 = (Integer[])pedestalCoords.get(Integer.valueOf(var1));
/*      */ 
/* 1254 */         if (EEProxy.getTileEntity(var0, var2[0].intValue(), var2[1].intValue(), var2[2].intValue(), TilePedestal.class) == null)
/*      */         {
/* 1256 */           removePedestalCoord(var1);
/*      */         }
/* 1258 */         else if (!((TilePedestal)EEProxy.getTileEntity(var0, var2[0].intValue(), var2[1].intValue(), var2[2].intValue(), TilePedestal.class)).isActivated())
/*      */         {
/* 1260 */           removePedestalCoord(var1);
/*      */         }
/*      */         else
/*      */         {
/* 1264 */           for (int var3 = 0; var3 < pedestalCoords.size(); var3++)
/*      */           {
/* 1266 */             if ((var1 != var3) && (pedestalCoords.get(Integer.valueOf(var3)) != null))
/*      */             {
/* 1268 */               Integer[] var4 = (Integer[])pedestalCoords.get(Integer.valueOf(var3));
/*      */ 
/* 1270 */               if (coordsEqual(var2, var4))
/*      */               {
/* 1272 */                 removePedestalCoord(var3);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static boolean coordsEqual(Integer[] var0, Integer[] var1)
/*      */   {
/* 1283 */     return (var0[0].equals(var1[0])) && (var0[1].equals(var1[1])) && (var0[2].equals(var1[2]));
/*      */   }
/*      */ 
/*      */   private static void removePedestalCoord(int var0)
/*      */   {
/* 1288 */     pedestalCoords.remove(Integer.valueOf(var0));
/*      */   }
/*      */ 
/*      */   public static float getPlayerWatchFactor()
/*      */   {
/* 1293 */     float var0 = 1.0F;
/*      */ 
/* 1295 */     for (int var1 = 0; var1 < playerWoftFactor; var1++)
/*      */     {
/* 1297 */       var0 = (float)(var0 * 0.9D);
/*      */     }
/*      */ 
/* 1300 */     return var0;
/*      */   }
/*      */ 
/*      */   public static void ConsumeReagentForDuration(ItemStack var0, EntityHuman var1, boolean var2)
/*      */   {
/* 1305 */     if (!EEProxy.isClient(var1.world))
/*      */     {
/* 1307 */       if (consumeKleinStarPoint(var1, 32))
/*      */       {
/* 1309 */         updatePlayerEffect(var0.getItem(), 64, var1);
/*      */       }
/* 1311 */       else if (Consume(new ItemStack(EEItem.aeternalisFuel), var1, var2))
/*      */       {
/* 1313 */         updatePlayerEffect(var0.getItem(), 16384, var1);
/*      */       }
/* 1315 */       else if (Consume(new ItemStack(EEItem.mobiusFuel), var1, var2))
/*      */       {
/* 1317 */         updatePlayerEffect(var0.getItem(), 4096, var1);
/*      */       }
/* 1319 */       else if (Consume(new ItemStack(Block.GLOWSTONE), var1, false))
/*      */       {
/* 1321 */         updatePlayerEffect(var0.getItem(), 3072, var1);
/*      */       }
/* 1323 */       else if (Consume(new ItemStack(EEItem.alchemicalCoal), var1, false))
/*      */       {
/* 1325 */         updatePlayerEffect(var0.getItem(), 1024, var1);
/*      */       }
/* 1327 */       else if (Consume(new ItemStack(Item.GLOWSTONE_DUST), var1, false))
/*      */       {
/* 1329 */         updatePlayerEffect(var0.getItem(), 768, var1);
/*      */       }
/* 1331 */       else if (Consume(new ItemStack(Item.SULPHUR), var1, false))
/*      */       {
/* 1333 */         updatePlayerEffect(var0.getItem(), 384, var1);
/*      */       }
/* 1335 */       else if (Consume(new ItemStack(Item.COAL, 1, 0), var1, false))
/*      */       {
/* 1337 */         updatePlayerEffect(var0.getItem(), 256, var1);
/*      */       }
/* 1339 */       else if (Consume(new ItemStack(Item.REDSTONE), var1, false))
/*      */       {
/* 1341 */         updatePlayerEffect(var0.getItem(), 128, var1);
/*      */       }
/* 1343 */       else if (Consume(new ItemStack(Item.COAL, 1, 1), var1, false))
/*      */       {
/* 1345 */         updatePlayerEffect(var0.getItem(), 64, var1);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void ConsumeReagent(ItemStack var0, EntityHuman var1, boolean var2)
/*      */   {
/* 1352 */     if (consumeKleinStarPoint(var1, 32))
/*      */     {
/* 1354 */       ((ItemEECharged)var0.getItem()).setShort(var0, "fuelRemaining", ((ItemEECharged)var0.getItem()).getShort(var0, "fuelRemaining") + 4);
/*      */     }
/* 1356 */     else if (Consume(new ItemStack(EEItem.aeternalisFuel, 1), var1, false))
/*      */     {
/* 1358 */       ((ItemEECharged)var0.getItem()).setShort(var0, "fuelRemaining", ((ItemEECharged)var0.getItem()).getShort(var0, "fuelRemaining") + 1024);
/*      */     }
/* 1360 */     else if (Consume(new ItemStack(EEItem.mobiusFuel, 1), var1, false))
/*      */     {
/* 1362 */       ((ItemEECharged)var0.getItem()).setShort(var0, "fuelRemaining", ((ItemEECharged)var0.getItem()).getShort(var0, "fuelRemaining") + 256);
/*      */     }
/* 1364 */     else if (Consume(new ItemStack(Block.GLOWSTONE, 1), var1, false))
/*      */     {
/* 1366 */       ((ItemEECharged)var0.getItem()).setShort(var0, "fuelRemaining", ((ItemEECharged)var0.getItem()).getShort(var0, "fuelRemaining") + 192);
/*      */     }
/* 1368 */     else if (Consume(new ItemStack(EEItem.alchemicalCoal, 1), var1, false))
/*      */     {
/* 1370 */       ((ItemEECharged)var0.getItem()).setShort(var0, "fuelRemaining", ((ItemEECharged)var0.getItem()).getShort(var0, "fuelRemaining") + 64);
/*      */     }
/* 1372 */     else if (Consume(new ItemStack(Item.GLOWSTONE_DUST, 1), var1, false))
/*      */     {
/* 1374 */       ((ItemEECharged)var0.getItem()).setShort(var0, "fuelRemaining", ((ItemEECharged)var0.getItem()).getShort(var0, "fuelRemaining") + 48);
/*      */     }
/* 1376 */     else if (Consume(new ItemStack(Item.SULPHUR, 1), var1, false))
/*      */     {
/* 1378 */       ((ItemEECharged)var0.getItem()).setShort(var0, "fuelRemaining", ((ItemEECharged)var0.getItem()).getShort(var0, "fuelRemaining") + 24);
/*      */     }
/* 1380 */     else if (Consume(new ItemStack(Item.COAL, 1, 0), var1, var2))
/*      */     {
/* 1382 */       ((ItemEECharged)var0.getItem()).setShort(var0, "fuelRemaining", ((ItemEECharged)var0.getItem()).getShort(var0, "fuelRemaining") + 16);
/*      */     }
/* 1384 */     else if (Consume(new ItemStack(Item.REDSTONE, 1), var1, var2))
/*      */     {
/* 1386 */       ((ItemEECharged)var0.getItem()).setShort(var0, "fuelRemaining", ((ItemEECharged)var0.getItem()).getShort(var0, "fuelRemaining") + 8);
/*      */     }
/* 1388 */     else if (Consume(new ItemStack(Item.COAL, 1, 1), var1, var2))
/*      */     {
/* 1390 */       ((ItemEECharged)var0.getItem()).setShort(var0, "fuelRemaining", ((ItemEECharged)var0.getItem()).getShort(var0, "fuelRemaining") + 4);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static boolean isLeftClickDown(EntityHuman var0, MinecraftServer var1)
/*      */   {
/* 1396 */     if (playerLeftClick.get(var0) == null)
/*      */     {
/* 1398 */       resetLeftClick(var0);
/*      */     }
/*      */ 
/* 1401 */     return ((Boolean)playerLeftClick.get(var0)).booleanValue();
/*      */   }
/*      */ 
/*      */   public static void resetLeftClick(EntityHuman var0)
/*      */   {
/* 1406 */     playerLeftClick.put(var0, Boolean.valueOf(false));
/*      */   }
/*      */ 
/*      */   public static void watchTransGrid(EntityHuman var0)
/*      */   {
/* 1411 */     playerTransGridOpen.put(var0, Boolean.valueOf(true));
/*      */   }
/*      */ 
/*      */   public static void closeTransGrid(EntityHuman var0)
/*      */   {
/* 1416 */     playerTransGridOpen.put(var0, Boolean.valueOf(false));
/*      */   }
/*      */ 
/*      */   public static Boolean getTransGridOpen(EntityHuman var0)
/*      */   {
/* 1421 */     if (playerTransGridOpen.get(var0) == null)
/*      */     {
/* 1423 */       playerTransGridOpen.put(var0, Boolean.valueOf(false));
/*      */     }
/*      */ 
/* 1426 */     return (Boolean)playerTransGridOpen.get(var0);
/*      */   }
/*      */ 
/*      */   public static int getMachineFactor()
/*      */   {
/* 1431 */     return machineFactor > 16 ? 16 : machineFactor < 1 ? 1 : machineFactor;
/*      */   }
/*      */ 
/*      */   public static boolean isNeutralEntity(Entity var0)
/*      */   {
/* 1436 */     return ((var0 instanceof EntitySheep)) || ((var0 instanceof EntityCow)) || ((var0 instanceof EntityPig)) || ((var0 instanceof EntityChicken)) || ((var0 instanceof EntityMushroomCow)) || ((var0 instanceof EntityVillager)) || ((var0 instanceof EntityOcelot)) || ((var0 instanceof EntityWolf)) || ((var0 instanceof EntitySnowman)) || ((var0 instanceof EntityIronGolem));
/*      */   }
/*      */ 
/*      */   public static boolean isHostileEntity(Entity var0)
/*      */   {
/* 1441 */     return ((var0 instanceof EntityCreeper)) || ((var0 instanceof EntityZombie)) || ((var0 instanceof EntitySkeleton)) || ((var0 instanceof EntitySpider)) || ((var0 instanceof EntityCaveSpider)) || ((var0 instanceof EntityEnderman)) || ((var0 instanceof EntitySilverfish)) || ((var0 instanceof EntitySlime)) || ((var0 instanceof EntityGhast)) || ((var0 instanceof EntityMagmaCube)) || ((var0 instanceof EntityPigZombie)) || ((var0 instanceof EntityBlaze));
/*      */   }
/*      */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.EEBase
 * JD-Core Version:    0.6.2
 */