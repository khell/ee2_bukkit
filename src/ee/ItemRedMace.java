/*     */ package ee;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityLiving;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.Material;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class ItemRedMace extends ItemRedTool
/*     */ {
/*  15 */   private static Block[] blocksEffectiveAgainst = { Block.COBBLESTONE, Block.DOUBLE_STEP, Block.STEP, Block.STONE, Block.SANDSTONE, Block.MOSSY_COBBLESTONE, Block.IRON_ORE, Block.IRON_BLOCK, Block.COAL_ORE, Block.GOLD_BLOCK, Block.GOLD_ORE, Block.DIAMOND_ORE, Block.DIAMOND_BLOCK, Block.REDSTONE_ORE, Block.GLOWING_REDSTONE_ORE, Block.ICE, Block.NETHERRACK, Block.LAPIS_ORE, Block.LAPIS_BLOCK, Block.OBSIDIAN, Block.GRASS, Block.DIRT, Block.SOUL_SAND, Block.SAND, Block.GRAVEL, Block.SNOW, Block.SNOW_BLOCK, Block.CLAY, Block.SOIL, EEBlock.eeStone, EEBlock.eePedestal, EEBlock.eeDevice, EEBlock.eeChest };
/*     */ 
/*     */   protected ItemRedMace(int var1)
/*     */   {
/*  19 */     super(var1, 4, 16, blocksEffectiveAgainst);
/*     */   }
/*     */ 
/*     */   public float getStrVsBlock(ItemStack var1, Block var2, int var3)
/*     */   {
/*  24 */     float var4 = 1.0F;
/*  25 */     return ((var2.id != EEBlock.eePedestal.id) || (var3 != 0)) && ((var2.id != EEBlock.eeStone.id) || ((var3 != 8) && (var3 != 9))) ? 16.0F / var4 : (var2.material != Material.STONE) && (var2.material != Material.ORE) ? (super.getDestroySpeed(var1, var2) + 4.0F * chargeLevel(var1)) / var4 : ((var2.material == Material.STONE) || (var2.material == Material.ORE)) && (chargeLevel(var1) > 0) ? 16.0F + 16.0F * chargeLevel(var1) / var4 : 1200000.0F / var4;
/*     */   }
/*     */ 
/*     */   public boolean a(ItemStack var1, int var2, int var3, int var4, int var5, EntityLiving var6)
/*     */   {
/*  30 */     EntityHuman var7 = null;
/*     */ 
/*  32 */     if ((var6 instanceof EntityHuman))
/*     */     {
/*  34 */       var7 = (EntityHuman)var6;
/*     */ 
/*  36 */       if (EEBase.getToolMode(var7) != 0)
/*     */       {
/*  38 */         if (EEBase.getToolMode(var7) == 1)
/*     */         {
/*  40 */           doTallImpact(var7.world, var1, var7, var3, var4, var5, EEBase.direction(var7));
/*     */         }
/*  42 */         else if (EEBase.getToolMode(var7) == 2)
/*     */         {
/*  44 */           doWideImpact(var7.world, var1, var3, var4, var5, EEBase.heading(var7));
/*     */         }
/*  46 */         else if (EEBase.getToolMode(var7) == 3)
/*     */         {
/*  48 */           doLongImpact(var7.world, var1, var3, var4, var5, EEBase.direction(var7));
/*     */         }
/*     */       }
/*  51 */       else if (EEBase.getHammerMode(var7))
/*     */       {
/*  53 */         doMegaImpact(var7.world, var1, var3, var4, var5, EEBase.direction(var7));
/*     */       }
/*     */ 
/*  56 */       return true;
/*     */     }
/*     */ 
/*  60 */     return true;
/*     */   }
/*     */ 
/*     */   public void doLongImpact(World var1, ItemStack var2, int var3, int var4, int var5, double var6)
/*     */   {
/*  66 */     cleanDroplist(var2);
/*     */ 
/*  68 */     for (int var8 = 1; var8 <= 2; var8++)
/*     */     {
/*  70 */       int var9 = var3;
/*  71 */       int var10 = var4;
/*  72 */       int var11 = var5;
/*     */ 
/*  74 */       if (var6 == 0.0D)
/*     */       {
/*  76 */         var10 = var4 - var8;
/*     */       }
/*     */ 
/*  79 */       if (var6 == 1.0D)
/*     */       {
/*  81 */         var10 += var8;
/*     */       }
/*     */ 
/*  84 */       if (var6 == 2.0D)
/*     */       {
/*  86 */         var11 = var5 + var8;
/*     */       }
/*     */ 
/*  89 */       if (var6 == 3.0D)
/*     */       {
/*  91 */         var9 = var3 - var8;
/*     */       }
/*     */ 
/*  94 */       if (var6 == 4.0D)
/*     */       {
/*  96 */         var11 -= var8;
/*     */       }
/*     */ 
/*  99 */       if (var6 == 5.0D)
/*     */       {
/* 101 */         var9 += var8;
/*     */       }
/*     */ 
/* 104 */       int var12 = var1.getTypeId(var9, var10, var11);
/* 105 */       int var13 = var1.getData(var9, var10, var11);
/*     */ 
/* 107 */       if (canBreak(var12, var13))
/*     */       {
/* 109 */         scanBlockAndBreak(var1, var2, var9, var10, var11);
/*     */       }
/*     */     }
/*     */ 
/* 113 */     ejectDropList(var1, var2, var3, var4 + 0.5D, var5);
/*     */   }
/*     */ 
/*     */   public void doWideImpact(World var1, ItemStack var2, int var3, int var4, int var5, double var6)
/*     */   {
/* 118 */     cleanDroplist(var2);
/*     */ 
/* 120 */     for (int var8 = -1; var8 <= 1; var8++)
/*     */     {
/* 122 */       int var9 = var3;
/* 123 */       int var11 = var5;
/*     */ 
/* 125 */       if (var8 != 0)
/*     */       {
/* 127 */         if ((var6 != 2.0D) && (var6 != 4.0D))
/*     */         {
/* 129 */           var11 = var5 + var8;
/*     */         }
/*     */         else
/*     */         {
/* 133 */           var9 = var3 + var8;
/*     */         }
/*     */ 
/* 136 */         int var12 = var1.getTypeId(var9, var4, var11);
/* 137 */         int var13 = var1.getData(var9, var4, var11);
/*     */ 
/* 139 */         if (canBreak(var12, var13))
/*     */         {
/* 141 */           scanBlockAndBreak(var1, var2, var9, var4, var11);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 146 */     ejectDropList(var1, var2, var3, var4 + 0.5D, var5);
/*     */   }
/*     */ 
/*     */   public void doTallImpact(World var1, ItemStack var2, EntityHuman var3, int var4, int var5, int var6, double var7)
/*     */   {
/* 151 */     cleanDroplist(var2);
/*     */ 
/* 153 */     for (int var9 = -1; var9 <= 1; var9++)
/*     */     {
/* 155 */       int var10 = var4;
/* 156 */       int var11 = var5;
/* 157 */       int var12 = var6;
/*     */ 
/* 159 */       if (var9 != 0)
/*     */       {
/* 161 */         if ((var7 != 0.0D) && (var7 != 1.0D))
/*     */         {
/* 163 */           var11 = var5 + var9;
/*     */         }
/* 165 */         else if ((EEBase.heading(var3) != 2.0D) && (EEBase.heading(var3) != 4.0D))
/*     */         {
/* 167 */           var10 = var4 + var9;
/*     */         }
/*     */         else
/*     */         {
/* 171 */           var12 = var6 + var9;
/*     */         }
/*     */ 
/* 174 */         int var13 = var1.getTypeId(var10, var11, var12);
/* 175 */         int var14 = var1.getData(var10, var11, var12);
/*     */ 
/* 177 */         if (canBreak(var13, var14))
/*     */         {
/* 179 */           scanBlockAndBreak(var1, var2, var10, var11, var12);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 184 */     ejectDropList(var1, var2, var4, var5 + 0.5D, var6);
/*     */   }
/*     */ 
/*     */   public void doMegaImpact(World var1, ItemStack var2, int var3, int var4, int var5, double var6)
/*     */   {
/* 189 */     cleanDroplist(var2);
/*     */ 
/* 191 */     for (int var8 = -1; var8 <= 1; var8++)
/*     */     {
/* 193 */       for (int var9 = -1; var9 <= 1; var9++)
/*     */       {
/* 195 */         int var10 = var3;
/* 196 */         int var11 = var4;
/* 197 */         int var12 = var5;
/*     */ 
/* 199 */         if ((var8 != 0) || (var9 != 0))
/*     */         {
/* 201 */           if ((var6 != 0.0D) && (var6 != 1.0D))
/*     */           {
/* 203 */             if ((var6 != 2.0D) && (var6 != 4.0D))
/*     */             {
/* 205 */               if ((var6 == 3.0D) || (var6 == 5.0D))
/*     */               {
/* 207 */                 var11 = var4 + var8;
/* 208 */                 var12 = var5 + var9;
/*     */               }
/*     */             }
/*     */             else
/*     */             {
/* 213 */               var10 = var3 + var8;
/* 214 */               var11 = var4 + var9;
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 219 */             var10 = var3 + var8;
/* 220 */             var12 = var5 + var9;
/*     */           }
/*     */ 
/* 223 */           int var13 = var1.getTypeId(var10, var11, var12);
/* 224 */           int var14 = var1.getData(var10, var11, var12);
/*     */ 
/* 226 */           if (canBreak(var13, var14))
/*     */           {
/* 228 */             scanBlockAndBreak(var1, var2, var10, var11, var12);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 234 */     ejectDropList(var1, var2, var3, var4 + 0.5D, var5);
/*     */   }
/*     */ 
/*     */   public void scanBlockAndBreak(World var1, ItemStack var2, int var3, int var4, int var5)
/*     */   {
/* 239 */     int var6 = var1.getTypeId(var3, var4, var5);
/* 240 */     int var7 = var1.getData(var3, var4, var5);
/* 241 */     ArrayList var8 = Block.byId[var6].getBlockDropped(var1, var3, var4, var5, var7, 0);
/* 242 */     Iterator var9 = var8.iterator();
/*     */ 
/* 244 */     while (var9.hasNext())
/*     */     {
/* 246 */       ItemStack var10 = (ItemStack)var9.next();
/* 247 */       addToDroplist(var2, var10);
/*     */     }
/*     */ 
/* 250 */     var1.setTypeId(var3, var4, var5, 0);
/*     */ 
/* 252 */     if (var1.random.nextInt(8) == 0)
/*     */     {
/* 254 */       var1.a("largesmoke", var3, var4, var5, 0.0D, 0.0D, 0.0D);
/*     */     }
/*     */ 
/* 257 */     if (var1.random.nextInt(8) == 0)
/*     */     {
/* 259 */       var1.a("explode", var3, var4, var5, 0.0D, 0.0D, 0.0D);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean canDestroySpecialBlock(Block var1)
/*     */   {
/* 268 */     return var1 == Block.OBSIDIAN;
/*     */   }
/*     */ 
/*     */   public boolean canBreak(int var1, int var2)
/*     */   {
/* 273 */     if (Block.byId[var1] == null)
/*     */     {
/* 275 */       return false;
/*     */     }
/* 277 */     if ((!Block.byId[var1].hasTileEntity(var2)) && (var1 != Block.BEDROCK.id))
/*     */     {
/* 279 */       if (Block.byId[var1].material == null)
/*     */       {
/* 281 */         return false;
/*     */       }
/*     */ 
/* 285 */       for (int var3 = 0; var3 < blocksEffectiveAgainst.length; var3++)
/*     */       {
/* 287 */         if ((blocksEffectiveAgainst[var3] != null) && (var1 == blocksEffectiveAgainst[var3].id))
/*     */         {
/* 289 */           return true;
/*     */         }
/*     */       }
/*     */ 
/* 293 */       if (Block.byId[var1].material == Material.STONE)
/*     */       {
/* 295 */         return true;
/*     */       }
/* 297 */       if ((Block.byId[var1].material != Material.GRASS) && (Block.byId[var1].material != Material.EARTH) && (Block.byId[var1].material != Material.SAND) && (Block.byId[var1].material != Material.SNOW_LAYER) && (Block.byId[var1].material != Material.CLAY))
/*     */       {
/* 299 */         if (canDestroySpecialBlock(Block.byId[var1]))
/*     */         {
/* 301 */           return true;
/*     */         }
/*     */ 
/* 305 */         return false;
/*     */       }
/*     */ 
/* 310 */       return true;
/*     */     }
/*     */ 
/* 316 */     return false;
/*     */   }
/*     */ 
/*     */   public void doPickaxeBreak(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 322 */     if (chargeLevel(var1) > 0)
/*     */     {
/* 324 */       int var4 = (int)EEBase.playerX(var3);
/* 325 */       int var5 = (int)EEBase.playerY(var3);
/* 326 */       int var6 = (int)EEBase.playerZ(var3);
/*     */ 
/* 328 */       for (int var7 = -2; var7 <= 2; var7++)
/*     */       {
/* 330 */         for (int var8 = -2; var8 <= 2; var8++)
/*     */         {
/* 332 */           for (int var9 = -2; var9 <= 2; var9++)
/*     */           {
/* 334 */             int var10 = var2.getTypeId(var4 + var7, var5 + var8, var6 + var9);
/*     */ 
/* 336 */             if (isOre(var10))
/*     */             {
/* 338 */               startSearchPick(var2, var1, var3, var10, var4 + var7, var5 + var8, var6 + var9, true);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean isOre(int var1)
/*     */   {
/* 348 */     return EEMaps.isOreBlock(var1);
/*     */   }
/*     */ 
/*     */   public void doAlternate(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 353 */     if ((EEBase.getToolMode(var3) == 0) && (EEBase.getHammerMode(var3)))
/*     */     {
/* 355 */       EEBase.updateToolMode(var3);
/* 356 */       EEBase.updateHammerMode(var3, false);
/*     */     }
/* 358 */     else if ((EEBase.getToolMode(var3) == 0) && (!EEBase.getHammerMode(var3)))
/*     */     {
/* 360 */       EEBase.updateHammerMode(var3, true);
/*     */     }
/*     */     else
/*     */     {
/* 364 */       EEBase.updateToolMode(var3);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void startSearchPick(World var1, ItemStack var2, EntityHuman var3, int var4, int var5, int var6, int var7, boolean var8)
/*     */   {
/* 370 */     if (var4 == Block.BEDROCK.id)
/*     */     {
/* 372 */       var3.a("Nice try. You can't break bedrock.");
/*     */     }
/*     */     else
/*     */     {
/* 376 */       var1.makeSound(var3, "flash", 0.8F, 1.5F);
/*     */ 
/* 378 */       if (var8)
/*     */       {
/* 380 */         var3.C_();
/*     */       }
/*     */ 
/* 383 */       doBreakPick(var1, var2, var3, var4, var5, var6, var7);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void startSearchShovel(World var1, EntityHuman var2, ItemStack var3, int var4, int var5, int var6, int var7, boolean var8)
/*     */   {
/* 389 */     var1.makeSound(var2, "flash", 0.8F, 1.5F);
/*     */ 
/* 391 */     if (var8)
/*     */     {
/* 393 */       var2.C_();
/*     */     }
/*     */ 
/* 396 */     doBreakShovel(var1, var2, var3, var4, var5, var6, var7);
/*     */   }
/*     */ 
/*     */   public void doBreakPick(World var1, ItemStack var2, EntityHuman var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 401 */     scanBlockAndBreak(var1, var2, var5, var6, var7);
/*     */ 
/* 403 */     for (int var8 = -1; var8 <= 1; var8++)
/*     */     {
/* 405 */       for (int var9 = -1; var9 <= 1; var9++)
/*     */       {
/* 407 */         for (int var10 = -1; var10 <= 1; var10++)
/*     */         {
/* 409 */           if ((var4 != Block.REDSTONE_ORE.id) && (var4 != Block.GLOWING_REDSTONE_ORE.id))
/*     */           {
/* 411 */             if (var1.getTypeId(var5 + var8, var6 + var9, var7 + var10) == var4)
/*     */             {
/* 413 */               doBreakPick(var1, var2, var3, var4, var5 + var8, var6 + var9, var7 + var10);
/*     */             }
/*     */           }
/* 416 */           else if ((var1.getTypeId(var5 + var8, var6 + var9, var7 + var10) == Block.GLOWING_REDSTONE_ORE.id) || (var1.getTypeId(var5 + var8, var6 + var9, var7 + var10) == Block.REDSTONE_ORE.id))
/*     */           {
/* 418 */             doBreakPick(var1, var2, var3, var4, var5 + var8, var6 + var9, var7 + var10);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 424 */     ejectDropList(var1, var2, EEBase.playerX(var3), EEBase.playerY(var3), EEBase.playerZ(var3));
/*     */   }
/*     */ 
/*     */   public void doBreakShovel(World var1, EntityHuman var2, ItemStack var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 429 */     if (getFuelRemaining(var3) < 1)
/*     */     {
/* 431 */       ConsumeReagent(var3, var2, false);
/*     */     }
/*     */ 
/* 434 */     if (getFuelRemaining(var3) > 0)
/*     */     {
/* 436 */       int var8 = var1.getData(var5, var6, var7);
/* 437 */       ArrayList var9 = Block.byId[var4].getBlockDropped(var1, var5, var6, var7, var8, 0);
/* 438 */       Iterator var10 = var9.iterator();
/*     */ 
/* 440 */       while (var10.hasNext())
/*     */       {
/* 442 */         ItemStack var11 = (ItemStack)var10.next();
/* 443 */         addToDroplist(var3, var11);
/*     */       }
/*     */ 
/* 446 */       var1.setTypeId(var5, var6, var7, 0);
/* 447 */       setShort(var3, "fuelRemaining", getFuelRemaining(var3) - 1);
/*     */ 
/* 449 */       for (int var14 = -1; var14 <= 1; var14++)
/*     */       {
/* 451 */         for (int var13 = -1; var13 <= 1; var13++)
/*     */         {
/* 453 */           for (int var12 = -1; var12 <= 1; var12++)
/*     */           {
/* 455 */             if (var1.getTypeId(var5 + var14, var6 + var13, var7 + var12) == var4)
/*     */             {
/* 457 */               doBreakShovelAdd(var1, var2, var3, var4, var5 + var14, var6 + var13, var7 + var12);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 463 */       if (var1.random.nextInt(8) == 0)
/*     */       {
/* 465 */         var1.a("largesmoke", var5, var6 + 1, var7, 0.0D, 0.0D, 0.0D);
/*     */       }
/*     */ 
/* 468 */       if (var1.random.nextInt(8) == 0)
/*     */       {
/* 470 */         var1.a("explode", var5, var6 + 1, var7, 0.0D, 0.0D, 0.0D);
/*     */       }
/*     */ 
/* 473 */       ejectDropList(var1, var3, var5, var6, var7);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doBreakShovelAdd(World var1, EntityHuman var2, ItemStack var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 479 */     if (getFuelRemaining(var3) < 1)
/*     */     {
/* 481 */       ConsumeReagent(var3, var2, false);
/*     */     }
/*     */ 
/* 484 */     if (getFuelRemaining(var3) > 0)
/*     */     {
/* 486 */       int var8 = var1.getData(var5, var6, var7);
/* 487 */       ArrayList var9 = Block.byId[var4].getBlockDropped(var1, var5, var6, var7, var8, 0);
/* 488 */       Iterator var10 = var9.iterator();
/*     */ 
/* 490 */       while (var10.hasNext())
/*     */       {
/* 492 */         ItemStack var11 = (ItemStack)var10.next();
/* 493 */         addToDroplist(var3, var11);
/*     */       }
/*     */ 
/* 496 */       var1.setTypeId(var5, var6, var7, 0);
/* 497 */       setShort(var3, "fuelRemaining", getFuelRemaining(var3) - 1);
/*     */ 
/* 499 */       for (int var14 = -1; var14 <= 1; var14++)
/*     */       {
/* 501 */         for (int var13 = -1; var13 <= 1; var13++)
/*     */         {
/* 503 */           for (int var12 = -1; var12 <= 1; var12++)
/*     */           {
/* 505 */             if (var1.getTypeId(var5 + var14, var6 + var13, var7 + var12) == var4)
/*     */             {
/* 507 */               doBreakShovelAdd(var1, var2, var3, var4, var5 + var14, var6 + var13, var7 + var12);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 513 */       if (var1.random.nextInt(8) == 0)
/*     */       {
/* 515 */         var1.a("largesmoke", var5, var6 + 1, var7, 0.0D, 0.0D, 0.0D);
/*     */       }
/*     */ 
/* 518 */       if (var1.random.nextInt(8) == 0)
/*     */       {
/* 520 */         var1.a("explode", var5, var6 + 1, var7, 0.0D, 0.0D, 0.0D);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doRelease(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 527 */     doBreak(var1, var2, var3);
/*     */   }
/*     */ 
/*     */   public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 536 */     if (EEProxy.isClient(var3))
/*     */     {
/* 538 */       return false;
/*     */     }
/* 540 */     if (chargeLevel(var1) < 1)
/*     */     {
/* 542 */       return false;
/*     */     }
/*     */ 
/* 546 */     cleanDroplist(var1);
/* 547 */     int var8 = var3.getTypeId(var4, var5, var6);
/*     */ 
/* 549 */     if (isOre(var8))
/*     */     {
/* 551 */       startSearchPick(var3, var1, var2, var8, var4, var5, var6, false);
/*     */     }
/* 553 */     else if (var3.getMaterial(var4, var5, var6) == Material.STONE)
/*     */     {
/* 555 */       onItemUseHammer(var1, var2, var3, var4, var5, var6, var7);
/*     */     }
/* 557 */     else if ((var3.getMaterial(var4, var5, var6) == Material.EARTH) || (var3.getMaterial(var4, var5, var6) == Material.GRASS) || (var3.getMaterial(var4, var5, var6) == Material.CLAY) || (var3.getMaterial(var4, var5, var6) == Material.SAND) || (var3.getMaterial(var4, var5, var6) == Material.SNOW_LAYER))
/*     */     {
/* 559 */       onItemUseShovel(var1, var2, var3, var4, var5, var6, var7);
/*     */     }
/*     */ 
/* 562 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean onItemUseHammer(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 568 */     boolean var8 = true;
/*     */ 
/* 570 */     if (chargeLevel(var1) > 0)
/*     */     {
/* 572 */       cleanDroplist(var1);
/* 573 */       var2.C_();
/* 574 */       var3.makeSound(var2, "flash", 0.8F, 1.5F);
/*     */ 
/* 576 */       for (int var9 = -(chargeLevel(var1) * (var7 == 4 ? 0 : var7 == 5 ? 2 : 1)); var9 <= chargeLevel(var1) * (var7 == 4 ? 2 : var7 == 5 ? 0 : 1); var9++)
/*     */       {
/* 578 */         for (int var10 = -(chargeLevel(var1) * (var7 == 0 ? 0 : var7 == 1 ? 2 : 1)); var10 <= chargeLevel(var1) * (var7 == 0 ? 2 : var7 == 1 ? 0 : 1); var10++)
/*     */         {
/* 580 */           for (int var11 = -(chargeLevel(var1) * (var7 == 2 ? 0 : var7 == 3 ? 2 : 1)); var11 <= chargeLevel(var1) * (var7 == 2 ? 2 : var7 == 3 ? 0 : 1); var11++)
/*     */           {
/* 582 */             int var12 = var4 + var9;
/* 583 */             int var13 = var5 + var10;
/* 584 */             int var14 = var6 + var11;
/* 585 */             int var15 = var3.getTypeId(var12, var13, var14);
/* 586 */             int var16 = var3.getData(var12, var13, var14);
/*     */ 
/* 588 */             if (canBreak(var15, var16))
/*     */             {
/* 590 */               if (getFuelRemaining(var1) < 1)
/*     */               {
/* 592 */                 ConsumeReagent(var1, var2, var8);
/* 593 */                 var8 = false;
/*     */               }
/*     */ 
/* 596 */               if (getFuelRemaining(var1) > 0)
/*     */               {
/* 598 */                 ArrayList var17 = Block.byId[var15].getBlockDropped(var3, var12, var13, var14, var16, 0);
/* 599 */                 Iterator var18 = var17.iterator();
/*     */ 
/* 601 */                 while (var18.hasNext())
/*     */                 {
/* 603 */                   ItemStack var19 = (ItemStack)var18.next();
/* 604 */                   addToDroplist(var1, var19);
/*     */                 }
/*     */ 
/* 607 */                 var3.setTypeId(var12, var13, var14, 0);
/*     */ 
/* 609 */                 if (var3.random.nextInt(8) == 0)
/*     */                 {
/* 611 */                   var3.a("largesmoke", var12, var13, var14, 0.0D, 0.0D, 0.0D);
/*     */                 }
/*     */ 
/* 614 */                 if (var3.random.nextInt(8) == 0)
/*     */                 {
/* 616 */                   var3.a("explode", var12, var13, var14, 0.0D, 0.0D, 0.0D);
/*     */                 }
/*     */ 
/* 619 */                 setShort(var1, "fuelRemaining", getFuelRemaining(var1) - 1);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 626 */       ejectDropList(var3, var1, var4, var5, var6);
/*     */     }
/*     */ 
/* 629 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean onItemUseShovel(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 634 */     if (chargeLevel(var1) >= 1)
/*     */     {
/* 636 */       cleanDroplist(var1);
/* 637 */       int var8 = var3.getTypeId(var4, var5, var6);
/*     */ 
/* 639 */       if (var8 == Block.GRAVEL.id)
/*     */       {
/* 641 */         startSearchShovel(var3, var2, var1, var8, var4, var5, var6, false);
/* 642 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 646 */     if (chargeLevel(var1) <= 0)
/*     */     {
/* 648 */       return false;
/*     */     }
/*     */ 
/* 652 */     boolean var19 = true;
/* 653 */     cleanDroplist(var1);
/* 654 */     var2.C_();
/* 655 */     var3.makeSound(var2, "flash", 0.8F, 1.5F);
/*     */ 
/* 657 */     for (int var9 = -chargeLevel(var1); var9 <= chargeLevel(var1); var9++)
/*     */     {
/* 659 */       for (int var10 = -chargeLevel(var1); var10 <= chargeLevel(var1); var10++)
/*     */       {
/* 661 */         int var11 = var4 + var9;
/* 662 */         int var13 = var6 + var10;
/*     */ 
/* 664 */         if (var7 == 2)
/*     */         {
/* 666 */           var13 += chargeLevel(var1);
/*     */         }
/* 668 */         else if (var7 == 3)
/*     */         {
/* 670 */           var13 -= chargeLevel(var1);
/*     */         }
/* 672 */         else if (var7 == 4)
/*     */         {
/* 674 */           var11 += chargeLevel(var1);
/*     */         }
/* 676 */         else if (var7 == 5)
/*     */         {
/* 678 */           var11 -= chargeLevel(var1);
/*     */         }
/*     */ 
/* 681 */         int var14 = var3.getTypeId(var11, var5, var13);
/* 682 */         int var15 = var3.getData(var11, var5, var13);
/*     */ 
/* 684 */         if (canBreak(var14, var15))
/*     */         {
/* 686 */           if (getFuelRemaining(var1) < 1)
/*     */           {
/* 688 */             if ((var9 == chargeLevel(var1)) && (var10 == chargeLevel(var1)))
/*     */             {
/* 690 */               ConsumeReagent(var1, var2, var19);
/* 691 */               var19 = false;
/*     */             }
/*     */             else
/*     */             {
/* 695 */               ConsumeReagent(var1, var2, false);
/*     */             }
/*     */           }
/*     */ 
/* 699 */           if (getFuelRemaining(var1) > 0)
/*     */           {
/* 701 */             ArrayList var16 = Block.byId[var14].getBlockDropped(var3, var11, var5, var13, var15, 0);
/* 702 */             Iterator var17 = var16.iterator();
/*     */ 
/* 704 */             while (var17.hasNext())
/*     */             {
/* 706 */               ItemStack var18 = (ItemStack)var17.next();
/* 707 */               addToDroplist(var1, var18);
/*     */             }
/*     */ 
/* 710 */             var3.setTypeId(var11, var5, var13, 0);
/*     */ 
/* 712 */             if (var3.random.nextInt(8) == 0)
/*     */             {
/* 714 */               var3.a("largesmoke", var11, var5, var13, 0.0D, 0.0D, 0.0D);
/*     */             }
/*     */ 
/* 717 */             if (var3.random.nextInt(8) == 0)
/*     */             {
/* 719 */               var3.a("explode", var11, var5, var13, 0.0D, 0.0D, 0.0D);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 726 */     ejectDropList(var3, var1, var4, var5, var6);
/* 727 */     return true;
/*     */   }
/*     */ 
/*     */   public void doToggle(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemRedMace
 * JD-Core Version:    0.6.2
 */