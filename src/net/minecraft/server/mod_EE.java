/*     */ package net.minecraft.server;
/*     */ 
/*     */ import ee.EEAddonBC;
/*     */ import ee.EEAddonForestry;
/*     */ import ee.EEAddonIC2;
/*     */ import ee.EEAddonRP2;
/*     */ import ee.EEBase;
/*     */ import ee.EEBlock;
/*     */ import ee.EEGuiHandler;
/*     */ import ee.EEItem;
/*     */ import ee.EEMaps;
/*     */ import ee.EntityGrimArrow;
/*     */ import ee.EntityHyperkinesis;
/*     */ import ee.EntityLavaEssence;
/*     */ import ee.EntityLootBall;
/*     */ import ee.EntityNovaPrimed;
/*     */ import ee.EntityPhilosopherEssence;
/*     */ import ee.EntityPyrokinesis;
/*     */ import ee.EntityWaterEssence;
/*     */ import ee.EntityWindEssence;
/*     */ import ee.ItemEECharged;
/*     */ import ee.ItemRedArmorPlus;
/*     */ import ee.ItemSwiftWolfRing;
/*     */ import ee.ItemWatchOfTime;
/*     */ import ee.TransTabletData;
/*     */ import ee.core.PickupHandler;
/*     */ import ee.network.PacketHandler;
/*     */ import forge.DimensionManager;
/*     */ import forge.MinecraftForge;
/*     */ import forge.NetworkMod;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class mod_EE extends NetworkMod
/*     */ {
/*     */   public static final String MOD_NAME = "Equivalent Exchange 2";
/*     */   public static final String CHANNEL_NAME = "EE2";
/*     */   public static final String SOUND_RESOURCE_LOCATION = "/ee/sound/";
/*     */   public static final String SOUND_PREFIX = "ee.sound.";
/*  41 */   private static int tickCounter = 0;
/*     */   private static mod_EE instance;
/*     */   private int blackListTimer;
/*     */   private int rmArmorClearSlot;
/*     */   public static int pedestalModelID;
/*     */   public static int chestModelID;
/*  47 */   public static HashMap playerShockwave = new HashMap();
/*     */ 
/*     */   public void load() {
/*     */   }
/*     */ 
/*     */   public mod_EE() {
/*  53 */     instance = this;
/*  54 */     MinecraftForge.versionDetect("Equivalent Exchange 2", 3, 3, 7);
/*  55 */     ModLoader.setInGameHook(this, true, true);
/*  56 */     MinecraftForge.registerConnectionHandler(new PacketHandler());
/*  57 */     MinecraftForge.setGuiHandler(this, new EEGuiHandler());
/*  58 */     EEBase.init(this);
/*  59 */     MinecraftForge.registerEntity(EntityPhilosopherEssence.class, this, 143, 300, 2, true);
/*  60 */     MinecraftForge.registerEntity(EntityWaterEssence.class, this, 144, 300, 2, true);
/*  61 */     MinecraftForge.registerEntity(EntityLavaEssence.class, this, 145, 300, 2, true);
/*  62 */     MinecraftForge.registerEntity(EntityWindEssence.class, this, 146, 300, 2, true);
/*  63 */     MinecraftForge.registerEntity(EntityPyrokinesis.class, this, 147, 300, 2, true);
/*  64 */     MinecraftForge.registerEntity(EntityGrimArrow.class, this, 148, 300, 2, true);
/*  65 */     MinecraftForge.registerEntity(EntityLootBall.class, this, 149, 300, 2, true);
/*  66 */     MinecraftForge.registerEntity(EntityNovaPrimed.class, this, 150, 300, 2, true);
/*  67 */     MinecraftForge.registerEntity(EntityHyperkinesis.class, this, 151, 300, 2, true);
/*  68 */     EEItem.init();
/*  69 */     EEBlock.init();
/*  70 */     EEMaps.InitAlchemicalValues();
/*  71 */     EEMaps.InitFlyingItems();
/*  72 */     EEMaps.InitFuelItems();
/*  73 */     EEMaps.InitFireImmunities();
/*  74 */     EEMaps.InitDurationEffectItems();
/*  75 */     EEMaps.InitEERecipes();
/*  76 */     EEMaps.InitRepairRecipes();
/*  77 */     EEMaps.InitChestItems();
/*  78 */     EEMaps.InitChargeditems();
/*  79 */     EEMaps.InitWoodAndLeafBlocks();
/*  80 */     EEMaps.InitPedestalItems();
/*  81 */     EEMaps.InitModItems();
/*  82 */     EEMaps.InitOreBlocks();
/*  83 */     EEMaps.InitBlacklist();
/*  84 */     EEMaps.InitMetaData();
/*  85 */     pedestalModelID = ModLoader.getUniqueBlockModelID(this, true);
/*  86 */     chestModelID = ModLoader.getUniqueBlockModelID(this, true);
/*  87 */     this.blackListTimer = 100;
/*  88 */     MinecraftForge.registerPickupHandler(new PickupHandler());
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/*  93 */     return String.format("%d.%d.%d.%d", new Object[] { Integer.valueOf(1), Integer.valueOf(4), Integer.valueOf(6), Integer.valueOf(5) });
/*     */   }
/*     */ 
/*     */   public boolean onTickInGame(MinecraftServer var1)
/*     */   {
/*  98 */     EEProxy.Init(var1, this);
/*  99 */     World[] var2 = DimensionManager.getWorlds();
/* 100 */     int var3 = var2.length;
/*     */ 
/* 102 */     for (int var4 = 0; var4 < var3; var4++)
/*     */     {
/* 104 */       World var5 = var2[var4];
/* 105 */       onTickInGame(var1, var5.players, var5);
/*     */     }
/*     */ 
/* 108 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean onTickInGame(MinecraftServer var1, List var2, World var3)
/*     */   {
/* 113 */     if (!EEBase.externalModsInitialized)
/*     */     {
/* 115 */       for (int var4 = 0; var4 < ModLoader.getLoadedMods().size(); var4++)
/*     */       {
/* 117 */         if (((BaseMod)ModLoader.getLoadedMods().get(var4)).toString().contains("mod_IC2"))
/*     */         {
/* 119 */           EEAddonIC2.initialize();
/*     */         }
/* 121 */         else if (((BaseMod)ModLoader.getLoadedMods().get(var4)).toString().contains("mod_RedPowerCore"))
/*     */         {
/* 123 */           EEAddonRP2.initBase();
/*     */         }
/* 125 */         else if (((BaseMod)ModLoader.getLoadedMods().get(var4)).toString().contains("mod_RedPowerWorld"))
/*     */         {
/* 127 */           EEAddonRP2.initBase();
/* 128 */           EEAddonRP2.initWorld();
/*     */         }
/* 130 */         else if (((BaseMod)ModLoader.getLoadedMods().get(var4)).toString().contains("mod_BuildCraftEnergy"))
/*     */         {
/* 132 */           EEAddonBC.initialize();
/*     */         }
/* 134 */         else if (((BaseMod)ModLoader.getLoadedMods().get(var4)).toString().contains("mod_Forestry"))
/*     */         {
/* 136 */           EEAddonForestry.initialize();
/*     */         }
/*     */       }
/*     */ 
/* 140 */       EEBase.externalModsInitialized = true;
/*     */     }
/*     */ 
/* 143 */     EEBase.validatePedestalCoords(var3);
/*     */ 
/* 145 */     if (tickCounter % 10 == 0)
/*     */     {
/* 147 */       doTransGridUpdates(var2);
/* 148 */       tickCounter = 0;
/*     */     }
/*     */ 
/* 151 */     doWatchCheck(var2, var3);
/* 152 */     doFlightCheck(var2, var3);
/* 153 */     Iterator var6 = var2.iterator();
/*     */ 
/* 155 */     while (var6.hasNext())
/*     */     {
/* 157 */       EntityHuman var5 = (EntityHuman)var6.next();
/*     */ 
/* 159 */       if (this.blackListTimer <= 0)
/*     */       {
/* 161 */         this.blackListTimer = 100;
/*     */ 
/* 163 */         if (EEMaps.isBlacklisted(var5.name))
/*     */         {
/* 165 */           var5.world.strikeLightning(new EntityWeatherLighting(var5.world, var5.locX, var5.locY, var5.locZ));
/*     */         }
/*     */       }
/*     */ 
/* 169 */       doGemPowers(var5);
/* 170 */       doEquipCheck(var5, var3);
/* 171 */       doFireImmuneCheck(var5);
/*     */     }
/*     */ 
/* 174 */     if (this.blackListTimer > 0)
/*     */     {
/* 176 */       this.blackListTimer -= 1;
/*     */     }
/*     */ 
/* 179 */     tickCounter += 1;
/* 180 */     return true;
/*     */   }
/*     */ 
/*     */   private void doTransGridUpdates(List var1)
/*     */   {
/* 185 */     Iterator var2 = var1.iterator();
/*     */ 
/* 187 */     while (var2.hasNext())
/*     */     {
/* 189 */       EntityHuman var3 = (EntityHuman)var2.next();
/*     */ 
/* 191 */       if (EEBase.getTransGridOpen(var3).booleanValue())
/*     */       {
/* 193 */         EEProxy.getTransData(var3).onUpdate(var3.world, var3);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void doGemPowers(EntityHuman var1)
/*     */   {
/* 200 */     EEBase.updatePlayerToggleCooldown(var1);
/*     */ 
/* 202 */     for (int var2 = 0; var2 <= 3; var2++)
/*     */     {
/* 204 */       if (var1.inventory.armor[var2] != null)
/*     */       {
/* 206 */         ItemStack var3 = var1.inventory.armor[var2];
/*     */ 
/* 208 */         if ((var2 == 2) && ((var3.getItem() instanceof ItemRedArmorPlus)) && (var1.hasEffect(MobEffectList.POISON)))
/*     */         {
/* 210 */           var1.d(var1.getEffect(MobEffectList.POISON));
/*     */         }
/*     */ 
/* 213 */         if ((var2 == 1) && ((var3.getItem() instanceof ItemRedArmorPlus)))
/*     */         {
/* 215 */           List var4 = var1.world.a(EntityLiving.class, AxisAlignedBB.b(var1.locX - 5.0D, var1.locY - 5.0D, var1.locZ - 5.0D, var1.locX + 5.0D, var1.locY + 5.0D, var1.locZ + 5.0D));
/*     */ 
/* 217 */           for (int var5 = 0; var5 < var4.size(); var5++)
/*     */           {
/* 219 */             Entity var6 = (Entity)var4.get(var5);
/*     */ 
/* 221 */             if ((!(var6 instanceof EntityHuman)) && ((var6.motX > 0.0D) || (var6.motZ > 0.0D)))
/*     */             {
/* 223 */               var6.motX *= 0.1000000014901161D;
/* 224 */               var6.motZ *= 0.1000000014901161D;
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 229 */         if ((var2 == 0) && ((var3.getItem() instanceof ItemRedArmorPlus)))
/*     */         {
/* 231 */           if ((!var1.isSprinting()) && (EEBase.getPlayerArmorMovement(var1)))
/*     */           {
/* 233 */             var1.setSprinting(true);
/*     */           }
/*     */ 
/* 236 */           if (EEBase.getPlayerArmorMovement(var1))
/*     */           {
/* 238 */             var1.am = ((float)(var1.am + 0.04000000000000001D));
/*     */ 
/* 240 */             if (var1.am > 0.3F)
/*     */             {
/* 242 */               var1.am = 0.3F;
/*     */             }
/*     */ 
/* 245 */             if (var1.motY > 1.0D)
/*     */             {
/* 247 */               var1.motY = 1.0D;
/*     */             }
/*     */ 
/* 250 */             if ((var1.motY < 0.0D) && (!var1.isSneaking()))
/*     */             {
/* 252 */               var1.motY *= 0.88D;
/*     */             }
/*     */           }
/*     */ 
/* 256 */           if (var1.fallDistance > 0.0F)
/*     */           {
/* 258 */             var1.fallDistance = 0.0F;
/*     */           }
/*     */         }
/*     */ 
/* 262 */         if ((var2 == 3) && ((var3.getItem() instanceof ItemRedArmorPlus)) && (var1.getAirTicks() < 20))
/*     */         {
/* 264 */           var1.setAirTicks(20);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void doShockwave(EntityHuman var1)
/*     */   {
/* 272 */     List var2 = var1.world.a(EntityLiving.class, AxisAlignedBB.b(var1.locX - 7.0D, var1.locY - 7.0D, var1.locZ - 7.0D, var1.locX + 7.0D, var1.locY + 7.0D, var1.locZ + 7.0D));
/*     */ 
/* 274 */     for (int var3 = 0; var3 < var2.size(); var3++)
/*     */     {
/* 276 */       Entity var4 = (Entity)var2.get(var3);
/*     */ 
/* 278 */       if (!(var4 instanceof EntityHuman))
/*     */       {
/* 280 */         var4.motX += 0.2D / (var4.locX - var1.locX);
/* 281 */         var4.motY += 0.05999999865889549D;
/* 282 */         var4.motZ += 0.2D / (var4.locZ - var1.locZ);
/*     */       }
/*     */     }
/*     */ 
/* 286 */     List var7 = var1.world.a(EntityArrow.class, AxisAlignedBB.b((float)var1.locX - 5.0F, var1.locY - 5.0D, (float)var1.locZ - 5.0F, (float)var1.locX + 5.0F, var1.locY + 5.0D, (float)var1.locZ + 5.0F));
/*     */ 
/* 288 */     for (int var8 = 0; var8 < var7.size(); var8++)
/*     */     {
/* 290 */       Entity var5 = (Entity)var7.get(var8);
/* 291 */       var5.motX += 0.2D / (var5.locX - var1.locX);
/* 292 */       var5.motY += 0.05999999865889549D;
/* 293 */       var5.motZ += 0.2D / (var5.locZ - var1.locZ);
/*     */     }
/*     */ 
/* 296 */     List var10 = var1.world.a(EntityFireball.class, AxisAlignedBB.b((float)var1.locX - 5.0F, var1.locY - 5.0D, (float)var1.locZ - 5.0F, (float)var1.locX + 5.0F, var1.locY + 5.0D, (float)var1.locZ + 5.0F));
/*     */ 
/* 298 */     for (int var9 = 0; var9 < var10.size(); var9++)
/*     */     {
/* 300 */       Entity var6 = (Entity)var10.get(var9);
/* 301 */       var6.motX += 0.2D / (var6.locX - var1.locX);
/* 302 */       var6.motY += 0.05999999865889549D;
/* 303 */       var6.motZ += 0.2D / (var6.locZ - var1.locZ);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void doWatchCheck(List var1, World var2)
/*     */   {
/* 309 */     int var3 = 0;
/* 310 */     Iterator var4 = var1.iterator();
/*     */     int var7;
/*     */     int var8;
/* 312 */     for (; var4.hasNext(); 
/* 318 */       var8 < var7)
/*     */     {
/* 314 */       EntityHuman var5 = (EntityHuman)var4.next();
/* 315 */       ItemStack[] var6 = EEBase.quickBar(var5);
/* 316 */       var7 = var6.length;
/*     */ 
/* 318 */       var8 = 0; continue;
/*     */ 
/* 320 */       ItemStack var9 = var6[var8];
/*     */ 
/* 322 */       if ((var9 != null) && ((var9.getItem() instanceof ItemEECharged)) && ((var9.getItem() instanceof ItemWatchOfTime)) && ((var9.getData() & 0x1) == 1) && (EEBase.getPlayerEffect(var9.getItem(), var5) > 0))
/*     */       {
/* 324 */         var3 += ((ItemEECharged)var9.getItem()).chargeLevel(var9) + 1;
/* 325 */         EEBase.playerWatchMagnitude.put(var5, Integer.valueOf(((ItemEECharged)var9.getItem()).chargeLevel(var9) + 1));
/*     */       }
/* 318 */       var8++;
/*     */     }
/*     */ 
/* 330 */     EEBase.playerWoftFactor = var3;
/*     */   }
/*     */ 
/*     */   private void doFlightCheck(List var1, World var2)
/*     */   {
/* 335 */     Iterator var3 = var1.iterator();
/*     */ 
/* 337 */     while (var3.hasNext())
/*     */     {
/* 339 */       EntityHuman var4 = (EntityHuman)var3.next();
/*     */ 
/* 341 */       if (((EntityPlayer)var4).itemInWorldManager.isCreative())
/*     */       {
/* 343 */         return;
/*     */       }
/*     */ 
/* 346 */       boolean var5 = false;
/* 347 */       boolean var6 = false;
/* 348 */       boolean var7 = false;
/* 349 */       boolean var8 = false;
/* 350 */       ItemStack[] var9 = EEBase.quickBar(var4);
/* 351 */       int var10 = var9.length;
/*     */ 
/* 353 */       for (int var11 = 0; var11 < var10; var11++)
/*     */       {
/* 355 */         ItemStack var12 = var9[var11];
/*     */ 
/* 357 */         if ((var12 != null) && (EEMaps.isFlyingItem(var12.id)))
/*     */         {
/* 359 */           if (var12.getItem() == EEItem.arcaneRing)
/*     */           {
/* 361 */             var5 = true;
/* 362 */             var4.abilities.canFly = true;
/*     */           }
/* 364 */           else if ((var12.getItem() == EEItem.evertide) && (EEBase.isPlayerInWater(var4)))
/*     */           {
/* 366 */             var7 = true;
/* 367 */             var4.abilities.isFlying = true;
/*     */           }
/* 369 */           else if ((var12.getItem() == EEItem.volcanite) && (EEBase.isPlayerInLava(var4)))
/*     */           {
/* 371 */             var6 = true;
/* 372 */             var4.abilities.isFlying = true;
/*     */           }
/* 374 */           else if (var12.getItem() == EEItem.swiftWolfRing)
/*     */           {
/* 376 */             var8 = true;
/* 377 */             var4.abilities.canFly = true;
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 382 */       if ((var8) && ((var7) || (var6) || (var5)))
/*     */       {
/* 384 */         var8 = false;
/* 385 */         disableSWRG(var4);
/*     */       }
/* 387 */       else if ((var8) && (!var6) && (!var7) && (!var5))
/*     */       {
/* 389 */         if (var4.abilities.isFlying)
/*     */         {
/* 391 */           forceEnableSWRG(var4);
/*     */         }
/*     */ 
/* 394 */         if (!var4.abilities.isFlying)
/*     */         {
/* 396 */           disableSWRG(var4);
/*     */         }
/*     */       }
/*     */ 
/* 400 */       if ((!var8) && (!var6) && (!var7) && (!var5))
/*     */       {
/* 402 */         var4.abilities.canFly = false;
/* 403 */         var4.abilities.isFlying = false;
/* 404 */         var4.updateAbilities();
/*     */       }
/*     */       else
/*     */       {
/* 408 */         var4.abilities.canFly = true;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void forceEnableSWRG(EntityHuman var1)
/*     */   {
/* 415 */     ItemStack[] var2 = EEBase.quickBar(var1);
/* 416 */     int var3 = var2.length;
/*     */ 
/* 418 */     for (int var4 = 0; var4 < var3; var4++)
/*     */     {
/* 420 */       ItemStack var5 = var2[var4];
/*     */ 
/* 422 */       if ((var5 != null) && (var5.getItem() == EEItem.swiftWolfRing))
/*     */       {
/* 424 */         if (!((ItemEECharged)var5.getItem()).isActivated(var5))
/*     */         {
/* 426 */           ((ItemEECharged)var5.getItem()).doToggle(var5, var1.world, var1);
/* 427 */           return;
/*     */         }
/*     */ 
/* 430 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void disableSWRG(EntityHuman var1)
/*     */   {
/* 437 */     ItemStack[] var2 = EEBase.quickBar(var1);
/* 438 */     int var3 = var2.length;
/*     */ 
/* 440 */     for (int var4 = 0; var4 < var3; var4++)
/*     */     {
/* 442 */       ItemStack var5 = var2[var4];
/*     */ 
/* 444 */       if ((var5 != null) && (var5.getItem() == EEItem.swiftWolfRing))
/*     */       {
/* 446 */         if (var1.abilities.isFlying)
/*     */         {
/* 448 */           if (((ItemEECharged)var5.getItem()).isActivated(var5))
/*     */           {
/* 450 */             if (EEBase.getPlayerEffect(var5.getItem(), var1) <= 0)
/*     */             {
/* 452 */               ((ItemEECharged)var5.getItem()).ConsumeReagent(var5, var1, false);
/*     */             }
/*     */ 
/* 455 */             if (EEBase.getPlayerEffect(var5.getItem(), var1) <= 0)
/*     */             {
/* 457 */               ((ItemEECharged)var5.getItem()).doToggle(var5, var1.world, var1);
/*     */             }
/*     */           }
/*     */         }
/* 461 */         else if (((ItemEECharged)var5.getItem()).isActivated(var5))
/*     */         {
/* 463 */           ((ItemEECharged)var5.getItem()).doToggle(var5, var1.world, var1);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void doEquipCheck(EntityHuman var1, World var2)
/*     */   {
/* 471 */     ItemStack[] var3 = EEBase.quickBar(var1);
/* 472 */     int var4 = var3.length;
/*     */ 
/* 474 */     for (int var5 = 0; var5 < var4; var5++)
/*     */     {
/* 476 */       ItemStack var6 = var3[var5];
/*     */ 
/* 478 */       if ((var6 != null) && ((var6.getItem() instanceof ItemEECharged)))
/*     */       {
/* 480 */         if (var6 == var1.U())
/*     */         {
/* 482 */           ((ItemEECharged)var6.getItem()).doHeld(var6, var2, var1);
/*     */         }
/*     */ 
/* 485 */         ((ItemEECharged)var6.getItem()).doPassive(var6, var2, var1);
/*     */ 
/* 487 */         if (((var6.getData() & 0x1) == 1) && (EEMaps.hasDurationEffect(var6.getItem())))
/*     */         {
/* 489 */           if ((var6.getItem() instanceof ItemWatchOfTime))
/*     */           {
/* 491 */             if (EEBase.getPlayerEffect(var6.getItem(), var1) > 0)
/*     */             {
/* 493 */               EEBase.updatePlayerEffect(var6.getItem(), EEBase.getPlayerEffect(var6.getItem(), var1) - (((ItemEECharged)var6.getItem()).chargeLevel(var6) + 1) * (((ItemEECharged)var6.getItem()).chargeLevel(var6) + 1), var1);
/*     */             }
/*     */           }
/* 496 */           else if (EEBase.getPlayerEffect(var6.getItem(), var1) > 0)
/*     */           {
/* 498 */             EEBase.updatePlayerEffect(var6.getItem(), EEBase.getPlayerEffect(var6.getItem(), var1) - 1, var1);
/*     */           }
/*     */ 
/* 501 */           if (EEBase.getPlayerEffect(var6.getItem(), var1) <= 0)
/*     */           {
/* 503 */             ((ItemEECharged)var6.getItem()).ConsumeReagent(var6, var1, false);
/*     */           }
/*     */ 
/* 506 */           if (EEBase.getPlayerEffect(var6.getItem(), var1) <= 0)
/*     */           {
/* 508 */             ((ItemEECharged)var6.getItem()).doToggle(var6, var2, var1);
/*     */           }
/*     */           else
/*     */           {
/* 512 */             ((ItemEECharged)var6.getItem()).doActive(var6, var2, var1);
/*     */           }
/*     */         }
/*     */ 
/* 516 */         if (((var6.getData() & 0x2) == 2) && ((var6.getItem() instanceof ItemSwiftWolfRing)))
/*     */         {
/* 518 */           if (EEBase.getPlayerEffect(var6.getItem(), var1) > 0)
/*     */           {
/* 520 */             EEBase.updatePlayerEffect(var6.getItem(), EEBase.getPlayerEffect(var6.getItem(), var1) - 2, var1);
/*     */           }
/*     */ 
/* 523 */           if (EEBase.getPlayerEffect(var6.getItem(), var1) <= 0)
/*     */           {
/* 525 */             ((ItemEECharged)var6.getItem()).ConsumeReagent(var6, var1, false);
/*     */           }
/*     */ 
/* 528 */           if (EEBase.getPlayerEffect(var6.getItem(), var1) <= 0)
/*     */           {
/* 530 */             ((ItemEECharged)var6.getItem()).doToggle(var6, var2, var1);
/*     */           }
/*     */           else
/*     */           {
/* 534 */             ((ItemEECharged)var6.getItem()).doActive(var6, var2, var1);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void doFireImmuneCheck(EntityHuman var1)
/*     */   {
/* 543 */     boolean var2 = false;
/*     */ 
/* 546 */     for (int var3 = 0; var3 < 9; var3++)
/*     */     {
/* 548 */       if ((var1.inventory.items[var3] != null) && (EEMaps.isFireImmuneItem(var1.inventory.items[var3].id)))
/*     */       {
/* 550 */         var2 = true;
/*     */       }
/*     */     }
/*     */ 
/* 554 */     for (var3 = 0; var3 < 4; var3++)
/*     */     {
/* 556 */       if ((var1.inventory.armor[var3] != null) && (EEMaps.isFireImmuneArmor(var1.inventory.armor[var3].id)))
/*     */       {
/* 558 */         var2 = true;
/*     */       }
/*     */     }
/*     */ 
/* 562 */     var1.fireProof = var2;
/*     */   }
/*     */ 
/*     */   public boolean clientSideRequired()
/*     */   {
/* 567 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean serverSideRequired()
/*     */   {
/* 572 */     return false;
/*     */   }
/*     */ 
/*     */   public static BaseMod getInstance()
/*     */   {
/* 577 */     return instance;
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     net.minecraft.server.mod_EE
 * JD-Core Version:    0.6.2
 */