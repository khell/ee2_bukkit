/*     */ package ee;
/*     */ 
/*     */ import ee.core.GuiIds;
/*     */ import java.util.HashMap;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.BlockFlower;
/*     */ import net.minecraft.server.BlockLongGrass;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.World;
/*     */ import net.minecraft.server.mod_EE;
/*     */ 
/*     */ public class ItemMercurialEye extends ItemEECharged
/*     */ {
/*  14 */   private static String prefix_ = "eye_";
/*     */ 
/*     */   public ItemMercurialEye(int var1)
/*     */   {
/*  18 */     super(var1, 4);
/*     */   }
/*     */ 
/*     */   public static boolean ConsumeReagent(EntityHuman var0, int var1, int var2, MercurialEyeData var3)
/*     */   {
/*  23 */     if (var1 == 0)
/*     */     {
/*  25 */       return false;
/*     */     }
/*     */ 
/*  29 */     ItemStack var4 = new ItemStack(var1, 1, var2);
/*     */ 
/*  31 */     if (EEMaps.getEMC(var4) == 0)
/*     */     {
/*  33 */       return false;
/*     */     }
/*  35 */     if (getKleinStarPoints(var3.getItem(0)) >= EEMaps.getEMC(var1, var2))
/*     */     {
/*  37 */       decKleinStarPoints(var3.getItem(0), EEMaps.getEMC(var1, var2));
/*  38 */       return true;
/*     */     }
/*     */ 
/*  42 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean ConsumeTransmuteReagent(EntityHuman var1, int var2, int var3, int var4, int var5, MercurialEyeData var6)
/*     */   {
/*  49 */     if (var2 == 0)
/*     */     {
/*  51 */       return false;
/*     */     }
/*     */ 
/*  55 */     ItemStack var7 = new ItemStack(var2, 1, var3);
/*     */ 
/*  57 */     if (EEMaps.getEMC(var7) == 0)
/*     */     {
/*  59 */       return false;
/*     */     }
/*  61 */     if (var4 == 0)
/*     */     {
/*  63 */       return false;
/*     */     }
/*  65 */     if (EEMaps.getEMC(var4, var5) == 0)
/*     */     {
/*  67 */       return false;
/*     */     }
/*  69 */     if (getKleinStarPoints(var6.getItem(0)) >= EEMaps.getEMC(var2, var3) - EEMaps.getEMC(var4, var5))
/*     */     {
/*  71 */       if ((EEMaps.getEMC(var2, var3) - EEMaps.getEMC(var4, var5) < 0) && (getKleinStarPoints(var6.getItem(0)) - (EEMaps.getEMC(var2, var3) - EEMaps.getEMC(var4, var5)) > ((ItemKleinStar)var6.getItem(0).getItem()).getMaxPoints(var6.getItem(0))))
/*     */       {
/*  73 */         return false;
/*     */       }
/*     */ 
/*  77 */       decKleinStarPoints(var6.getItem(0), EEMaps.getEMC(var2, var3) - EEMaps.getEMC(var4, var5));
/*  78 */       return true;
/*     */     }
/*     */ 
/*  83 */     return false;
/*     */   }
/*     */ 
/*     */   public static void decKleinStarPoints(ItemStack var0, int var1)
/*     */   {
/*  90 */     if (var0 != null)
/*     */     {
/*  92 */       if ((var0.getItem() instanceof ItemKleinStar))
/*     */       {
/*  94 */         ItemKleinStar var2 = (ItemKleinStar)var0.getItem();
/*  95 */         var2.setKleinPoints(var0, var2.getKleinPoints(var0) - var1 >= 0 ? var2.getKleinPoints(var0) - var1 : 0);
/*  96 */         var2.onUpdate(var0);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static int getKleinStarPoints(ItemStack var0)
/*     */   {
/* 103 */     return (var0.getItem() instanceof ItemKleinStar) ? ((ItemKleinStar)var0.getItem()).getKleinPoints(var0) : var0 == null ? 0 : 0;
/*     */   }
/*     */ 
/*     */   public static MercurialEyeData getEyeData(EntityHuman var0, World var1)
/*     */   {
/* 108 */     String var2 = var0.name;
/* 109 */     String var3 = prefix_ + var2;
/* 110 */     MercurialEyeData var4 = (MercurialEyeData)var1.a(MercurialEyeData.class, var3);
/*     */ 
/* 112 */     if (var4 == null)
/*     */     {
/* 114 */       var4 = new MercurialEyeData(var3);
/* 115 */       var4.a();
/* 116 */       var1.a(var3, var4);
/*     */     }
/*     */ 
/* 119 */     return var4;
/*     */   }
/*     */ 
/*     */   public static MercurialEyeData getEyeData(ItemStack var0, EntityHuman var1, World var2)
/*     */   {
/* 124 */     String var3 = var1.name;
/* 125 */     String var4 = prefix_ + var3;
/* 126 */     MercurialEyeData var5 = (MercurialEyeData)var2.a(MercurialEyeData.class, var4);
/*     */ 
/* 128 */     if (var5 == null)
/*     */     {
/* 130 */       var5 = new MercurialEyeData(var4);
/* 131 */       var5.a();
/* 132 */       var2.a(var4, var5);
/*     */     }
/*     */ 
/* 135 */     return var5;
/*     */   }
/*     */ 
/*     */   public void doAlternate(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 140 */     doExtra(var2, var1, var3);
/*     */   }
/*     */ 
/*     */   public void doExtra(World var1, ItemStack var2, EntityHuman var3)
/*     */   {
/* 145 */     if (EEProxy.isServer())
/*     */     {
/* 147 */       var3.openGui(mod_EE.getInstance(), GuiIds.MERCURIAL_EYE, var1, (int)var3.locX, (int)var3.locY, (int)var3.locZ);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doExtra(World var1, ItemStack var2, EntityHuman var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 153 */     if (EEProxy.isServer())
/*     */     {
/* 155 */       var3.openGui(mod_EE.getInstance(), GuiIds.MERCURIAL_EYE, var1, var5, var6, var7);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void d(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 164 */     if (!EEProxy.isClient(var2))
/*     */     {
/* 166 */       String var4 = var3.name;
/* 167 */       String var5 = prefix_ + var4;
/* 168 */       MercurialEyeData var6 = (MercurialEyeData)var2.a(MercurialEyeData.class, var5);
/*     */ 
/* 170 */       if (var6 == null)
/*     */       {
/* 172 */         var6 = new MercurialEyeData(var5);
/* 173 */         var2.a(var5, var6);
/* 174 */         var6.a();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 185 */     if (EEProxy.isClient(var3))
/*     */     {
/* 187 */       return false;
/*     */     }
/*     */ 
/* 191 */     MercurialEyeData var8 = getEyeData(var1, var2, var3);
/*     */ 
/* 193 */     if ((var8.getItem(0) != null) && (var8.getItem(1) != null))
/*     */     {
/* 195 */       if (EEMaps.getEMC(var8.getItem(1)) == 0)
/*     */       {
/* 197 */         return false;
/*     */       }
/* 199 */       if (!EEBase.isKleinStar(var8.getItem(0).id))
/*     */       {
/* 201 */         return false;
/*     */       }
/* 203 */       if (var8.getItem(1).id >= Block.byId.length)
/*     */       {
/* 205 */         return false;
/*     */       }
/*     */ 
/* 209 */       if (Block.byId[var3.getTypeId(var4, var5, var6)].hasTileEntity(var3.getData(var4, var5, var6)))
/*     */       {
/* 211 */         if ((var3.getTypeId(var4, var5, var6) == EEBlock.eeStone.id) && (var3.getData(var4, var5, var6) <= 7))
/*     */         {
/* 213 */           return false;
/*     */         }
/*     */ 
/* 216 */         if (var3.getTypeId(var4, var5, var6) != EEBlock.eeStone.id)
/*     */         {
/* 218 */           return false;
/*     */         }
/*     */       }
/*     */ 
/* 222 */       if (Block.byId[var8.getItem(1).id].hasTileEntity(var8.getItem(1).getData()))
/*     */       {
/* 224 */         if ((var8.getItem(1).id == EEBlock.eeStone.id) && (var8.getItem(1).getData() <= 7))
/*     */         {
/* 226 */           return false;
/*     */         }
/*     */ 
/* 229 */         if (var8.getItem(1).id != EEBlock.eeStone.id)
/*     */         {
/* 231 */           return false;
/*     */         }
/*     */       }
/*     */ 
/* 235 */       int var9 = var8.getItem(1).id;
/* 236 */       int var10 = var8.getItem(1).getData();
/* 237 */       double var11 = EEBase.direction(var2);
/*     */ 
/* 239 */       if (var3.getTypeId(var4, var5, var6) == Block.SNOW.id)
/*     */       {
/* 241 */         if (var7 == 1)
/*     */         {
/* 243 */           var5--;
/*     */         }
/*     */ 
/* 246 */         if (var7 == 2)
/*     */         {
/* 248 */           var6++;
/* 249 */           var5++;
/*     */         }
/*     */ 
/* 252 */         if (var7 == 3)
/*     */         {
/* 254 */           var6--;
/* 255 */           var5++;
/*     */         }
/*     */ 
/* 258 */         if (var7 == 4)
/*     */         {
/* 260 */           var4++;
/* 261 */           var5++;
/*     */         }
/*     */ 
/* 264 */         if (var7 == 5)
/*     */         {
/* 266 */           var4--;
/* 267 */           var5++;
/*     */         }
/*     */       }
/*     */ 
/* 271 */       if (EEBase.getBuildMode(var2) == 1)
/*     */       {
/* 273 */         if (var7 == 0)
/*     */         {
/* 275 */           var5--;
/*     */         }
/*     */ 
/* 278 */         if (var7 == 1)
/*     */         {
/* 280 */           var5++;
/*     */         }
/*     */ 
/* 283 */         if (var7 == 2)
/*     */         {
/* 285 */           var6--;
/*     */         }
/*     */ 
/* 288 */         if (var7 == 3)
/*     */         {
/* 290 */           var6++;
/*     */         }
/*     */ 
/* 293 */         if (var7 == 4)
/*     */         {
/* 295 */           var4--;
/*     */         }
/*     */ 
/* 298 */         if (var7 == 5)
/*     */         {
/* 300 */           var4++;
/*     */         }
/*     */       }
/*     */ 
/* 304 */       byte var13 = 0;
/* 305 */       byte var14 = 0;
/* 306 */       byte var15 = 0;
/* 307 */       byte var16 = 0;
/* 308 */       byte var17 = 0;
/* 309 */       byte var18 = 0;
/*     */ 
/* 311 */       if (EEBase.getBuildMode(var2) != 3)
/*     */       {
/* 313 */         if ((var11 != 0.0D) && (var11 != 1.0D))
/*     */         {
/* 315 */           if ((var11 != 2.0D) && (var11 != 4.0D))
/*     */           {
/* 317 */             if ((var11 == 3.0D) || (var11 == 5.0D))
/*     */             {
/* 319 */               if (var7 == 0)
/*     */               {
/* 321 */                 var17 = -1;
/* 322 */                 var18 = 1;
/* 323 */                 var15 = -2;
/*     */               }
/* 325 */               else if (var7 == 1)
/*     */               {
/* 327 */                 var17 = -1;
/* 328 */                 var18 = 1;
/* 329 */                 var16 = 2;
/*     */               }
/* 331 */               else if (var7 == 2)
/*     */               {
/* 333 */                 var17 = -2;
/* 334 */                 var15 = -1;
/* 335 */                 var16 = 1;
/*     */               }
/* 337 */               else if (var7 == 3)
/*     */               {
/* 339 */                 var18 = 2;
/* 340 */                 var15 = -1;
/* 341 */                 var16 = 1;
/*     */               }
/* 343 */               else if ((var7 == 4) || (var7 == 5))
/*     */               {
/* 345 */                 var17 = -1;
/* 346 */                 var18 = 1;
/* 347 */                 var15 = -1;
/* 348 */                 var16 = 1;
/*     */               }
/*     */             }
/*     */           }
/* 352 */           else if (var7 == 0)
/*     */           {
/* 354 */             var13 = -1;
/* 355 */             var14 = 1;
/* 356 */             var15 = -2;
/*     */           }
/* 358 */           else if (var7 == 1)
/*     */           {
/* 360 */             var13 = -1;
/* 361 */             var14 = 1;
/* 362 */             var16 = 2;
/*     */           }
/* 364 */           else if ((var7 != 2) && (var7 != 3))
/*     */           {
/* 366 */             if (var7 == 4)
/*     */             {
/* 368 */               var13 = -2;
/* 369 */               var15 = -1;
/* 370 */               var16 = 1;
/*     */             }
/* 372 */             else if (var7 == 5)
/*     */             {
/* 374 */               var14 = 2;
/* 375 */               var15 = -1;
/* 376 */               var16 = 1;
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 381 */             var13 = -1;
/* 382 */             var14 = 1;
/* 383 */             var15 = -1;
/* 384 */             var16 = 1;
/*     */           }
/*     */         }
/* 387 */         else if ((var7 != 0) && (var7 != 1))
/*     */         {
/* 389 */           if (var7 == 2)
/*     */           {
/* 391 */             var13 = -1;
/* 392 */             var14 = 1;
/* 393 */             var17 = -2;
/*     */           }
/* 395 */           else if (var7 == 3)
/*     */           {
/* 397 */             var13 = -1;
/* 398 */             var14 = 1;
/* 399 */             var18 = 2;
/*     */           }
/* 401 */           else if (var7 == 4)
/*     */           {
/* 403 */             var13 = -2;
/* 404 */             var17 = -1;
/* 405 */             var18 = 1;
/*     */           }
/* 407 */           else if (var7 == 5)
/*     */           {
/* 409 */             var14 = 2;
/* 410 */             var17 = -1;
/* 411 */             var18 = 1;
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 416 */           var13 = -1;
/* 417 */           var14 = 1;
/* 418 */           var17 = -1;
/* 419 */           var18 = 1;
/*     */         }
/*     */       }
/*     */ 
/* 423 */       if (EEBase.getBuildMode(var2) != 3)
/*     */       {
/* 425 */         doWall(var3, var1, var2, var4, var5, var6, var13, var15, var17, var14, var16, var18, var9, var10);
/*     */       }
/*     */       else
/*     */       {
/* 429 */         doPillar(var3, var1, var2, var4, var5, var6, var7, var9, var10);
/*     */       }
/*     */ 
/* 432 */       return true;
/*     */     }
/*     */ 
/* 437 */     return false;
/*     */   }
/*     */ 
/*     */   private void doPillar(World var1, ItemStack var2, EntityHuman var3, int var4, int var5, int var6, int var7, int var8, int var9)
/*     */   {
/* 444 */     MercurialEyeData var10 = getEyeData(var2, var3, var3.world);
/* 445 */     boolean var11 = false;
/* 446 */     byte var12 = 1;
/* 447 */     byte var13 = 1;
/* 448 */     byte var14 = 1;
/* 449 */     byte var15 = -1;
/* 450 */     byte var16 = -1;
/* 451 */     byte var17 = -1;
/* 452 */     int var18 = 3 + 3 * chargeLevel(var2) - 1;
/*     */ 
/* 457 */     if (var7 == 0)
/*     */     {
/* 459 */       byte var27 = 0;
/* 460 */       int var23 = var18;
/*     */ 
/* 462 */       for (int var19 = var15; var19 <= var12; var19++)
/*     */       {
/* 464 */         for (int var20 = var17; var20 <= var14; var20++)
/*     */         {
/* 466 */           for (int var21 = var27; (var21 <= var23) && (doPillarBlock(var1, var4 + var19, var5 + var21, var6 + var20, var8, var9, var11, var3, var10, var21 - var27)); var21++);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/* 473 */     else if (var7 == 1)
/*     */     {
/* 475 */       int var26 = -var18;
/* 476 */       var13 = 0;
/*     */ 
/* 478 */       for (int var19 = var15; var19 <= var12; var19++)
/*     */       {
/* 480 */         for (int var20 = var17; var20 <= var14; var20++)
/*     */         {
/* 482 */           for (int var21 = var13; (var21 >= var26) && (doPillarBlock(var1, var4 + var19, var5 + var21, var6 + var20, var8, var9, var11, var3, var10, -var21)); var21--);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/* 489 */     else if (var7 == 2)
/*     */     {
/* 491 */       byte var30 = 0;
/* 492 */       int var25 = var18;
/*     */ 
/* 494 */       for (int var19 = var15; var19 <= var12; var19++)
/*     */       {
/* 496 */         for (int var20 = var16; var20 <= var13; var20++)
/*     */         {
/* 498 */           for (int var21 = var30; (var21 <= var25) && (doPillarBlock(var1, var4 + var19, var5 + var20, var6 + var21, var8, var9, var11, var3, var10, var21 - var30)); var21++);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/* 505 */     else if (var7 == 3)
/*     */     {
/* 507 */       int var29 = -var18;
/* 508 */       var14 = 0;
/*     */ 
/* 510 */       for (int var19 = var15; var19 <= var12; var19++)
/*     */       {
/* 512 */         for (int var20 = var16; var20 <= var13; var20++)
/*     */         {
/* 514 */           for (int var21 = var14; (var21 >= var29) && (doPillarBlock(var1, var4 + var19, var5 + var20, var6 + var21, var8, var9, var11, var3, var10, -var21)); var21--);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/* 521 */     else if (var7 == 4)
/*     */     {
/* 523 */       byte var24 = 0;
/* 524 */       int var22 = var18;
/*     */ 
/* 526 */       for (int var19 = var16; var19 <= var13; var19++)
/*     */       {
/* 528 */         for (int var20 = var17; var20 <= var14; var20++)
/*     */         {
/* 530 */           for (int var21 = var24; (var21 <= var22) && (doPillarBlock(var1, var4 + var21, var5 + var19, var6 + var20, var8, var9, var11, var3, var10, var21 - var24)); var21++);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/* 537 */     else if (var7 == 5)
/*     */     {
/* 539 */       int var28 = -var18;
/* 540 */       var12 = 0;
/*     */ 
/* 542 */       for (int var19 = var16; var19 <= var13; var19++)
/*     */       {
/* 544 */         for (int var20 = var17; var20 <= var14; var20++)
/*     */         {
/* 546 */           for (int var21 = var12; (var21 >= var28) && (doPillarBlock(var1, var4 + var21, var5 + var19, var6 + var20, var8, var9, var11, var3, var10, -var21)); var21--);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean doPillarBlock(World var1, int var2, int var3, int var4, int var5, int var6, boolean var7, EntityHuman var8, MercurialEyeData var9, int var10)
/*     */   {
/* 557 */     int var11 = var1.getTypeId(var2, var3, var4);
/*     */ 
/* 559 */     if ((var11 != 0) && (var11 != 8) && (var11 != 9) && (var11 != 10) && (var11 != 11) && (var11 != BlockFlower.LONG_GRASS.id) && (var11 != 78))
/*     */     {
/* 561 */       return var10 <= 4;
/*     */     }
/*     */ 
/* 565 */     if (var11 == BlockFlower.LONG_GRASS.id)
/*     */     {
/* 567 */       Block.byId[var11].dropNaturally(var1, var2, var3 + 1, var4, 1, 1.0F, 1);
/*     */     }
/*     */ 
/* 570 */     if (ConsumeReagent(var8, var5, var6, var9))
/*     */     {
/* 572 */       if (!var7)
/*     */       {
/* 574 */         var1.makeSound(var8, "wall", 0.8F, 0.8F / (c.nextFloat() * 0.4F + 0.8F));
/* 575 */         var7 = true;
/*     */       }
/*     */ 
/* 578 */       var1.setTypeIdAndData(var2, var3, var4, var5, var6);
/*     */ 
/* 580 */       if (var1.random.nextInt(8) == 0)
/*     */       {
/* 582 */         var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
/*     */       }
/*     */     }
/*     */ 
/* 586 */     return true;
/*     */   }
/*     */ 
/*     */   public void doWall(World var1, ItemStack var2, EntityHuman var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14)
/*     */   {
/* 592 */     MercurialEyeData var15 = getEyeData(var2, var3, var3.world);
/* 593 */     boolean var16 = false;
/*     */ 
/* 595 */     for (int var17 = chargeLevel(var2) * var7; var17 <= chargeLevel(var2) * var10; var17++)
/*     */     {
/* 597 */       for (int var18 = chargeLevel(var2) * var8; var18 <= chargeLevel(var2) * var11; var18++)
/*     */       {
/* 599 */         for (int var19 = chargeLevel(var2) * var9; var19 <= chargeLevel(var2) * var12; var19++)
/*     */         {
/* 601 */           int var20 = var1.getTypeId(var17 + var4, var18 + var5, var19 + var6);
/*     */ 
/* 603 */           if ((var20 != 0) && (var20 != 8) && (var20 != 9) && (var20 != 10) && (var20 != 11) && (var20 != BlockFlower.LONG_GRASS.id) && (var20 != 78))
/*     */           {
/* 605 */             if (((Integer)EEBase.playerBuildMode.get(var3)).intValue() == 2)
/*     */             {
/* 607 */               int var21 = var1.getTypeId(var17 + var4, var18 + var5, var19 + var6);
/* 608 */               int var22 = var1.getData(var17 + var4, var18 + var5, var19 + var6);
/*     */ 
/* 610 */               if ((EEMaps.getEMC(var21, var22) != 0) && (ConsumeTransmuteReagent(var3, var13, var14, var21, var22, var15)))
/*     */               {
/* 612 */                 if (!var16)
/*     */                 {
/* 614 */                   var1.makeSound(var3, "wall", 0.8F, 0.8F / (c.nextFloat() * 0.4F + 0.8F));
/* 615 */                   var16 = true;
/*     */                 }
/*     */ 
/* 618 */                 var1.setTypeIdAndData(var17 + var4, var18 + var5, var19 + var6, var13, var14);
/*     */ 
/* 620 */                 if (var1.random.nextInt(8) == 0)
/*     */                 {
/* 622 */                   var1.a("largesmoke", var4 + var17, var5 + var18 + 1, var6 + var19, 0.0D, 0.0D, 0.0D);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/* 627 */           else if (((Integer)EEBase.playerBuildMode.get(var3)).intValue() != 2)
/*     */           {
/* 629 */             if (var20 == BlockFlower.LONG_GRASS.id)
/*     */             {
/* 631 */               Block.byId[var20].dropNaturally(var1, var17 + var4, var18 + var5 + 1, var19 + var6, 1, 1.0F, 1);
/*     */             }
/*     */ 
/* 634 */             if (ConsumeReagent(var3, var13, var14, var15))
/*     */             {
/* 636 */               if (!var16)
/*     */               {
/* 638 */                 var1.makeSound(var3, "wall", 0.8F, 0.8F / (c.nextFloat() * 0.4F + 0.8F));
/* 639 */                 var16 = true;
/*     */               }
/*     */ 
/* 642 */               var1.setTypeIdAndData(var17 + var4, var18 + var5, var19 + var6, var13, var14);
/*     */ 
/* 644 */               if (var1.random.nextInt(8) == 0)
/*     */               {
/* 646 */                 var1.a("largesmoke", var4 + var17, var5 + var18 + 1, var6 + var19, 0.0D, 0.0D, 0.0D);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void ConsumeReagent(ItemStack var1, EntityHuman var2, boolean var3) {
/*     */   }
/*     */ 
/*     */   public void doPassive(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doActive(ItemStack var1, World var2, EntityHuman var3) {  }
/*     */ 
/*     */ 
/*     */   public void doHeld(ItemStack var1, World var2, EntityHuman var3) {  }
/*     */ 
/*     */ 
/*     */   public void doLeftClick(ItemStack var1, World var2, EntityHuman var3) {  }
/*     */ 
/*     */ 
/* 667 */   public void doToggle(ItemStack var1, World var2, EntityHuman var3) { EEBase.updateBuildMode(var3); }
/*     */ 
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemMercurialEye
 * JD-Core Version:    0.6.2
 */