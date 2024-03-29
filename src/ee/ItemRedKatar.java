/*     */ package ee;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.AxisAlignedBB;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.BlockDeadBush;
/*     */ import net.minecraft.server.BlockFlower;
/*     */ import net.minecraft.server.BlockGrass;
/*     */ import net.minecraft.server.BlockLeaves;
/*     */ import net.minecraft.server.BlockLongGrass;
/*     */ import net.minecraft.server.DamageSource;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityCow;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityItem;
/*     */ import net.minecraft.server.EntityLiving;
/*     */ import net.minecraft.server.EntityMonster;
/*     */ import net.minecraft.server.EntityMushroomCow;
/*     */ import net.minecraft.server.EntitySheep;
/*     */ import net.minecraft.server.EnumAnimation;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.Material;
/*     */ import net.minecraft.server.StepSound;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class ItemRedKatar extends ItemRedTool
/*     */ {
/*     */   public boolean itemCharging;
/*  29 */   private static Block[] blocksEffectiveAgainst = { Block.WOOD, Block.BOOKSHELF, Block.LOG, Block.CHEST, Block.DIRT, Block.GRASS, Block.LEAVES, Block.WEB, Block.WOOL };
/*     */ 
/*     */   protected ItemRedKatar(int var1)
/*     */   {
/*  33 */     super(var1, 4, 18, blocksEffectiveAgainst);
/*     */   }
/*     */ 
/*     */   public boolean a(ItemStack var1, int var2, int var3, int var4, int var5, EntityLiving var6)
/*     */   {
/*  38 */     boolean var7 = false;
/*     */ 
/*  40 */     if ((!EEMaps.isLeaf(var2)) && (var2 != Block.WEB.id) && (var2 != Block.VINE.id) && (var2 != BlockFlower.LONG_GRASS.id) && (var2 != BlockFlower.DEAD_BUSH.id))
/*     */     {
/*  42 */       var7 = true;
/*     */     }
/*     */ 
/*  45 */     if (!var7)
/*     */     {
/*  47 */       EEProxy.dropBlockAsItemStack(Block.byId[var2], var6, var3, var4, var5, new ItemStack(var2, 1, var2 == Block.LEAVES.id ? ((ItemEECharged)var1.getItem()).getShort(var1, "lastMeta") & 0x3 : ((ItemEECharged)var1.getItem()).getShort(var1, "lastMeta")));
/*     */     }
/*     */ 
/*  50 */     return super.a(var1, var2, var3, var4, var5, var6);
/*     */   }
/*     */ 
/*     */   public boolean canDestroySpecialBlock(Block var1)
/*     */   {
/*  58 */     return var1.id == Block.WEB.id;
/*     */   }
/*     */ 
/*     */   public boolean ConsumeReagent(int var1, ItemStack var2, EntityHuman var3, boolean var4)
/*     */   {
/*  63 */     if (getFuelRemaining(var2) >= 16)
/*     */     {
/*  65 */       setFuelRemaining(var2, getFuelRemaining(var2) - 16);
/*  66 */       return true;
/*     */     }
/*     */ 
/*  70 */     int var5 = getFuelRemaining(var2);
/*     */ 
/*  72 */     while (getFuelRemaining(var2) < 16)
/*     */     {
/*  74 */       ConsumeReagent(var2, var3, var4);
/*     */ 
/*  76 */       if (var5 == getFuelRemaining(var2))
/*     */       {
/*     */         break;
/*     */       }
/*     */ 
/*  81 */       var5 = getFuelRemaining(var2);
/*     */ 
/*  83 */       if (getFuelRemaining(var2) >= 16)
/*     */       {
/*  85 */         setFuelRemaining(var2, getFuelRemaining(var2) - 16);
/*  86 */         return true;
/*     */       }
/*     */     }
/*     */ 
/*  90 */     return false;
/*     */   }
/*     */ 
/*     */   public float getStrVsBlock(ItemStack var1, Block var2, int var3)
/*     */   {
/*  96 */     if (getShort(var1, "lastMeta") != var3)
/*     */     {
/*  98 */       setShort(var1, "lastMeta", var3);
/*     */     }
/*     */ 
/* 101 */     if ((var2.id != Block.VINE.id) && (var2.id != Block.LEAVES.id) && (var2.id != Block.WEB.id))
/*     */     {
/* 103 */       if (var2.id == Block.WOOL.id)
/*     */       {
/* 105 */         return 5.0F;
/*     */       }
/* 107 */       if ((var2.material != Material.EARTH) && (var2.material != Material.GRASS))
/*     */       {
/* 109 */         return var2.material == Material.WOOD ? 18.0F + chargeLevel(var1) * 2 : super.getDestroySpeed(var1, var2);
/*     */       }
/*     */ 
/* 113 */       float var4 = 18.0F + chargeLevel(var1) * 4;
/* 114 */       return var4;
/*     */     }
/*     */ 
/* 119 */     return 15.0F;
/*     */   }
/*     */ 
/*     */   public void doSwordBreak(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 125 */     if (chargeLevel(var1) > 0)
/*     */     {
/* 127 */       boolean var4 = false;
/*     */ 		int var5;
/* 130 */       for (var5 = 1; var5 <= chargeLevel(var1); var5++)
/*     */       {
/* 132 */         if (var5 == chargeLevel(var1))
/*     */         {
/* 134 */           var4 = true;
/*     */         }
/*     */ 
/* 137 */         if (!ConsumeReagent(1, var1, var3, var4))
/*     */         {
/* 139 */           var5--;
/* 140 */           break;
/*     */         }
/*     */       }
/*     */ 
/* 144 */       if (var5 < 1)
/*     */       {
/* 146 */         return;
/*     */       }
/*     */ 
/* 149 */       var3.C_();
/* 150 */       var2.makeSound(var3, "flash", 0.8F, 1.5F);
/* 151 */       List var6 = var2.getEntities(var3, AxisAlignedBB.b((float)var3.locX - (var5 / 1.5D + 2.0D), var3.locY - (var5 / 1.5D + 2.0D), (float)var3.locZ - (var5 / 1.5D + 2.0D), (float)var3.locX + var5 / 1.5D + 2.0D, var3.locY + var5 / 1.5D + 2.0D, (float)var3.locZ + var5 / 1.5D + 2.0D));
/*     */ 
/* 153 */       for (int var7 = 0; var7 < var6.size(); var7++)
/*     */       {
/* 155 */         if (((var6.get(var7) instanceof EntityLiving)) && ((EEBase.getSwordMode(var3)) || ((var6.get(var7) instanceof EntityMonster))))
/*     */         {
/* 157 */           Entity var8 = (Entity)var6.get(var7);
/* 158 */           var8.damageEntity(DamageSource.playerAttack(var3), this.weaponDamage + chargeLevel(var1) * 2);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 170 */     if (EEProxy.isClient(var3))
/*     */     {
/* 172 */       return false;
/*     */     }
/*     */ 
/* 176 */     int var8 = var3.getTypeId(var4, var5, var6);
/* 177 */     chargeLevel(var1);
/*     */ 
/* 179 */     if ((EEMaps.isLeaf(var8)) || (var8 == Block.WEB.id) || (var8 == Block.VINE.id) || (var8 == BlockFlower.LONG_GRASS.id) || (var8 == BlockFlower.DEAD_BUSH.id))
/*     */     {
/* 181 */       onItemUseShears(var1, var2, var3, var4, var5, var6, var7);
/*     */     }
/*     */ 
/* 184 */     if (((var8 == Block.DIRT.id) || (var8 == Block.GRASS.id)) && (var3.getTypeId(var4, var5 + 1, var6) == 0))
/*     */     {
/* 186 */       onItemUseHoe(var1, var2, var3, var4, var5, var6, var7);
/*     */     }
/*     */ 
/* 189 */     if (EEMaps.isWood(var8))
/*     */     {
/* 191 */       onItemUseAxe(var1, var2, var3, var4, var5, var6, var7);
/*     */     }
/*     */ 
/* 194 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean onItemUseShears(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 200 */     if (chargeLevel(var1) > 0)
/*     */     {
/* 202 */       boolean var8 = false;
/* 203 */       cleanDroplist(var1);
/* 204 */       var2.C_();
/* 205 */       var3.makeSound(var2, "flash", 0.8F, 1.5F);
/*     */ 
/* 207 */       for (int var9 = -(chargeLevel(var1) + 2); var9 <= chargeLevel(var1) + 2; var9++)
/*     */       {
/* 209 */         for (int var10 = -(chargeLevel(var1) + 2); var10 <= chargeLevel(var1) + 2; var10++)
/*     */         {
/* 211 */           for (int var11 = -(chargeLevel(var1) + 2); var11 <= chargeLevel(var1) + 2; var11++)
/*     */           {
/* 213 */             int var12 = var3.getTypeId(var4 + var9, var5 + var10, var6 + var11);
/*     */ 
/* 215 */             if ((EEMaps.isLeaf(var12)) || (var12 == Block.VINE.id) || (var12 == Block.WEB.id) || (var12 == BlockFlower.LONG_GRASS.id) || (var12 == BlockFlower.DEAD_BUSH.id))
/*     */             {
/* 217 */               if (getFuelRemaining(var1) < 1)
/*     */               {
/* 219 */                 ConsumeReagent(var1, var2, false);
/*     */               }
/*     */ 
/* 222 */               if (getFuelRemaining(var1) > 0)
/*     */               {
/* 224 */                 int var13 = var3.getData(var4 + var9, var5 + var10, var6 + var11);
/*     */ 
/* 226 */                 if ((!EEMaps.isLeaf(var12)) && (var12 != Block.VINE.id) && (var12 != Block.WEB.id) && (var12 != Block.LONG_GRASS.id) && (var12 != Block.DEAD_BUSH.id))
/*     */                 {
/* 228 */                   ArrayList var14 = Block.byId[var12].getBlockDropped(var3, var4 + var9, var5 + var10, var6 + var11, var13, 0);
/* 229 */                   Iterator var15 = var14.iterator();
/*     */ 
/* 231 */                   while (var15.hasNext())
/*     */                   {
/* 233 */                     ItemStack var16 = (ItemStack)var15.next();
/* 234 */                     addToDroplist(var1, var16);
/*     */                   }
/*     */                 }
/* 237 */                 else if (var12 == Block.LEAVES.id)
/*     */                 {
/* 239 */                   addToDroplist(var1, new ItemStack(Block.LEAVES.id, 1, var13 & 0x3));
/*     */                 }
/*     */                 else
/*     */                 {
/* 243 */                   addToDroplist(var1, new ItemStack(Block.byId[var12], 1, var13));
/*     */                 }
/*     */ 
/* 246 */                 setShort(var1, "fuelRemaining", getFuelRemaining(var1) - 1);
/* 247 */                 var3.setTypeId(var4 + var9, var5 + var10, var6 + var11, 0);
/*     */ 
/* 249 */                 if (var3.random.nextInt(8) == 0)
/*     */                 {
/* 251 */                   var3.a("largesmoke", var4 + var9, var5 + var10, var6 + var11, 0.0D, 0.0D, 0.0D);
/*     */                 }
/*     */ 
/* 254 */                 if (var3.random.nextInt(8) == 0)
/*     */                 {
/* 256 */                   var3.a("explode", var4 + var9, var5 + var10, var6 + var11, 0.0D, 0.0D, 0.0D);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 264 */       ejectDropList(var3, var1, var4, var5, var6);
/*     */     }
/*     */ 
/* 267 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean onItemUseHoe(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 275 */     if (chargeLevel(var1) > 0)
/*     */     {
/* 277 */       var2.C_();
/* 278 */       var3.makeSound(var2, "flash", 0.8F, 1.5F);
/*     */ 
/* 280 */       if ((var3.getTypeId(var4, var5, var6) == BlockFlower.YELLOW_FLOWER.id) || (var3.getTypeId(var4, var5, var6) == BlockFlower.RED_ROSE.id) || (var3.getTypeId(var4, var5, var6) == BlockFlower.BROWN_MUSHROOM.id) || (var3.getTypeId(var4, var5, var6) == BlockFlower.RED_MUSHROOM.id) || (var3.getTypeId(var4, var5, var6) == BlockLongGrass.LONG_GRASS.id) || (var3.getTypeId(var4, var5, var6) == BlockDeadBush.DEAD_BUSH.id))
/*     */       {
/* 282 */         var5--;
/*     */       }
/*     */ 
/* 285 */       for (int var8 = -(chargeLevel(var1) * chargeLevel(var1)) - 1; var8 <= chargeLevel(var1) * chargeLevel(var1) + 1; var8++)
/*     */       {
/* 287 */         for (int var9 = -(chargeLevel(var1) * chargeLevel(var1)) - 1; var9 <= chargeLevel(var1) * chargeLevel(var1) + 1; var9++)
/*     */         {
/* 289 */           int var15 = var4 + var8;
/* 290 */           int var12 = var6 + var9;
/* 291 */           int var13 = var3.getTypeId(var15, var5, var12);
/* 292 */           int var14 = var3.getTypeId(var15, var5 + 1, var12);
/*     */ 
/* 294 */           if ((var14 == BlockFlower.YELLOW_FLOWER.id) || (var14 == BlockFlower.RED_ROSE.id) || (var14 == BlockFlower.BROWN_MUSHROOM.id) || (var14 == BlockFlower.RED_MUSHROOM.id) || (var14 == BlockLongGrass.LONG_GRASS.id) || (var14 == BlockDeadBush.DEAD_BUSH.id))
/*     */           {
/* 296 */             Block.byId[var14].dropNaturally(var3, var15, var5 + 1, var12, var3.getData(var15, var5 + 1, var12), 1.0F, 1);
/* 297 */             var3.setTypeId(var15, var5 + 1, var12, 0);
/* 298 */             var14 = 0;
/*     */           }
/*     */ 
/* 301 */           if ((var14 == 0) && ((var13 == Block.DIRT.id) || (var13 == Block.GRASS.id)))
/*     */           {
/* 303 */             if (getFuelRemaining(var1) < 1)
/*     */             {
/* 305 */               ConsumeReagent(var1, var2, false);
/*     */             }
/*     */ 
/* 308 */             if (getFuelRemaining(var1) > 0)
/*     */             {
/* 310 */               var3.setTypeId(var15, var5, var12, 60);
/* 311 */               setShort(var1, "fuelRemaining", getFuelRemaining(var1) - 1);
/*     */ 
/* 313 */               if (var3.random.nextInt(8) == 0)
/*     */               {
/* 315 */                 var3.a("largesmoke", var15, var5, var12, 0.0D, 0.0D, 0.0D);
/*     */               }
/*     */ 
/* 318 */               if (var3.random.nextInt(8) == 0)
/*     */               {
/* 320 */                 var3.a("explode", var15, var5, var12, 0.0D, 0.0D, 0.0D);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 327 */       return false;
/*     */     }
/* 329 */     if ((var2 != null) && (!var2.d(var4, var5, var6)))
/*     */     {
/* 331 */       return false;
/*     */     }
/*     */ 
/* 335 */     int var8 = var3.getTypeId(var4, var5, var6);
/* 336 */     int var9 = var3.getTypeId(var4, var5 + 1, var6);
/*     */ 
/* 338 */     if (((var7 == 0) || (var9 != 0) || (var8 != Block.GRASS.id)) && (var8 != Block.DIRT.id))
/*     */     {
/* 340 */       return false;
/*     */     }
/*     */ 
/* 344 */     Block var10 = Block.SOIL;
/* 345 */     var3.makeSound(var4 + 0.5F, var5 + 0.5F, var6 + 0.5F, var10.stepSound.getName(), (var10.stepSound.getVolume1() + 1.0F) / 2.0F, var10.stepSound.getVolume2() * 0.8F);
/*     */ 
/* 347 */     if (var3.isStatic)
/*     */     {
/* 349 */       return true;
/*     */     }
/*     */ 
/* 353 */     var3.setTypeId(var4, var5, var6, var10.id);
/* 354 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean onItemUseAxe(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 362 */     if (chargeLevel(var1) > 0)
/*     */     {
/* 364 */       double var8 = var4;
/* 365 */       double var10 = var5;
/* 366 */       double var12 = var6;
/* 367 */       boolean var14 = false;
/* 368 */       cleanDroplist(var1);
/*     */ 
/* 370 */       if (chargeLevel(var1) < 1)
/*     */       {
/* 372 */         return false;
/*     */       }
/*     */ 
/* 375 */       var2.C_();
/* 376 */       var3.makeSound(var2, "flash", 0.8F, 1.5F);
/*     */ 
/* 378 */       for (int var15 = -(chargeLevel(var1) * 2) + 1; var15 <= chargeLevel(var1) * 2 - 1; var15++)
/*     */       {
/* 380 */         for (int var16 = chargeLevel(var1) * 2 + 1; var16 >= -2; var16--)
/*     */         {
/* 382 */           for (int var17 = -(chargeLevel(var1) * 2) + 1; var17 <= chargeLevel(var1) * 2 - 1; var17++)
/*     */           {
/* 384 */             int var18 = (int)(var8 + var15);
/* 385 */             int var19 = (int)(var10 + var16);
/* 386 */             int var20 = (int)(var12 + var17);
/* 387 */             int var21 = var3.getTypeId(var18, var19, var20);
/*     */ 
/* 389 */             if ((EEMaps.isWood(var21)) || (EEMaps.isLeaf(var21)))
/*     */             {
/* 391 */               if (getFuelRemaining(var1) < 1)
/*     */               {
/* 393 */                 if ((var15 == chargeLevel(var1)) && (var17 == chargeLevel(var1)))
/*     */                 {
/* 395 */                   ConsumeReagent(var1, var2, var14);
/* 396 */                   var14 = false;
/*     */                 }
/*     */                 else
/*     */                 {
/* 400 */                   ConsumeReagent(var1, var2, false);
/*     */                 }
/*     */               }
/*     */ 
/* 404 */               if (getFuelRemaining(var1) > 0)
/*     */               {
/* 406 */                 int var22 = var3.getData(var18, var19, var20);
/* 407 */                 ArrayList var23 = Block.byId[var21].getBlockDropped(var3, var18, var19, var20, var22, 0);
/* 408 */                 Iterator var24 = var23.iterator();
/*     */ 
/* 410 */                 while (var24.hasNext())
/*     */                 {
/* 412 */                   ItemStack var25 = (ItemStack)var24.next();
/* 413 */                   addToDroplist(var1, var25);
/*     */                 }
/*     */ 
/* 416 */                 var3.setTypeId(var18, var19, var20, 0);
/*     */ 
/* 418 */                 if (!EEMaps.isLeaf(var21))
/*     */                 {
/* 420 */                   setShort(var1, "fuelRemaining", getFuelRemaining(var1) - 1);
/*     */                 }
/*     */ 
/* 423 */                 if (var3.random.nextInt(8) == 0)
/*     */                 {
/* 425 */                   var3.a("largesmoke", var18, var19, var20, 0.0D, 0.0D, 0.0D);
/*     */                 }
/*     */ 
/* 428 */                 if (var3.random.nextInt(8) == 0)
/*     */                 {
/* 430 */                   var3.a("explode", var18, var19, var20, 0.0D, 0.0D, 0.0D);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 438 */       ejectDropList(var3, var1, var8, var10, var12);
/*     */     }
/*     */ 
/* 441 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isFull3D()
/*     */   {
/* 446 */     return true;
/*     */   }
/*     */ 
/*     */   public EnumAnimation d(ItemStack var1)
/*     */   {
/* 454 */     return EnumAnimation.d;
/*     */   }
/*     */ 
/*     */   public int c(ItemStack var1)
/*     */   {
/* 462 */     return 72000;
/*     */   }
/*     */ 
/*     */   public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 470 */     if (EEProxy.isClient(var2))
/*     */     {
/* 472 */       return var1;
/*     */     }
/*     */ 
/* 476 */     var3.a(var1, c(var1));
/* 477 */     return var1;
/*     */   }
/*     */ 
/*     */   public void doShear(ItemStack var1, World var2, EntityHuman var3, Entity var4)
/*     */   {
/* 486 */     if (chargeLevel(var1) > 0)
/*     */     {
/* 488 */       boolean var5 = false;
/* 489 */       int var6 = 0;
/*     */ 
/* 491 */       if (getFuelRemaining(var1) < 10)
/*     */       {
/* 493 */         ConsumeReagent(var1, var3, false);
/*     */       }
/*     */ 
/* 496 */       if (getFuelRemaining(var1) < 10)
/*     */       {
/* 498 */         ConsumeReagent(var1, var3, false);
/*     */       }
/*     */ 
/* 501 */       if (getFuelRemaining(var1) < 10)
/*     */       {
/* 503 */         ConsumeReagent(var1, var3, false);
/*     */       }
/*     */ 
/* 506 */       while ((getFuelRemaining(var1) >= 10) && (var6 < chargeLevel(var1)))
/*     */       {
/* 508 */         setShort(var1, "fuelRemaining", getFuelRemaining(var1) - 10);
/* 509 */         var6++;
/*     */ 
/* 511 */         if (getFuelRemaining(var1) < 10)
/*     */         {
/* 513 */           ConsumeReagent(var1, var3, false);
/*     */         }
/*     */ 
/* 516 */         if (getFuelRemaining(var1) < 10)
/*     */         {
/* 518 */           ConsumeReagent(var1, var3, false);
/*     */         }
/*     */ 
/* 521 */         if (getFuelRemaining(var1) < 10)
/*     */         {
/* 523 */           ConsumeReagent(var1, var3, false);
/*     */         }
/*     */       }
/*     */ 
/* 527 */       if (var6 > 0)
/*     */       {
/* 529 */         var3.C_();
/* 530 */         var2.makeSound(var3, "flash", 0.8F, 1.5F);
/* 531 */         int var7 = 3 * var6;
/*     */ 
/* 537 */         if ((var4 instanceof EntitySheep))
/*     */         {
/* 539 */           if (var2.random.nextInt(100) < var7)
/*     */           {
/* 541 */             EntitySheep var8 = new EntitySheep(var2);
/* 542 */             double var9 = var4.locX - var3.locX;
/* 543 */             double var11 = var4.locZ - var3.locZ;
/*     */ 
/* 545 */             if (var9 < 0.0D)
/*     */             {
/* 547 */               var9 *= -1.0D;
/*     */             }
/*     */ 
/* 550 */             if (var11 < 0.0D)
/*     */             {
/* 552 */               var11 *= -1.0D;
/*     */             }
/*     */ 
/* 555 */             var9 += var4.locX;
/* 556 */             var11 += var4.locZ;
/* 557 */             double var13 = var4.locY;
/*     */ 
/* 559 */             for (int var15 = -5; var15 <= 5; var15++)
/*     */             {
/* 561 */               if ((var2.getTypeId((int)var9, (int)var13 + var15, (int)var11) != 0) && (var2.getTypeId((int)var9, (int)var13 + var15 + 1, (int)var11) == 0))
/*     */               {
/* 563 */                 var8.setPosition(var9, var13 + var15 + 1.0D, var11);
/* 564 */                 var8.setColor(((EntitySheep)var4).getColor());
/* 565 */                 var2.addEntity(var8);
/* 566 */                 break;
/*     */               }
/*     */             }
/*     */           }
/*     */ 
/* 571 */           ((EntitySheep)var4).setSheared(true);
/* 572 */           int var19 = 3 + var2.random.nextInt(2) + chargeLevel(var1) / 2;
/* 573 */           EntityItem var21 = null;
/*     */ 
/* 575 */           for (int var10 = 0; var10 < var19; var10++)
/*     */           {
/* 577 */             var21 = new EntityItem(var2, var3.locX, var3.locY, var3.locZ, new ItemStack(Block.WOOL.id, var19, ((EntitySheep)var4).getColor()));
/*     */           }
/*     */ 
/* 580 */           var2.addEntity(var21);
/*     */         }
/* 582 */         else if ((var4 instanceof EntityMushroomCow))
/*     */         {
/* 584 */           if (var2.random.nextInt(100) < var7)
/*     */           {
/* 586 */             EntityMushroomCow var18 = new EntityMushroomCow(var2);
/* 587 */             double var9 = var4.locX - var3.locX;
/* 588 */             double var11 = var4.locZ - var3.locZ;
/*     */ 
/* 590 */             if (var9 < 0.0D)
/*     */             {
/* 592 */               var9 *= -1.0D;
/*     */             }
/*     */ 
/* 595 */             if (var11 < 0.0D)
/*     */             {
/* 597 */               var11 *= -1.0D;
/*     */             }
/*     */ 
/* 600 */             var9 += var4.locX;
/* 601 */             var11 += var4.locZ;
/* 602 */             double var13 = var4.locY;
/*     */ 
/* 604 */             for (int var15 = -5; var15 <= 5; var15++)
/*     */             {
/* 606 */               if ((var2.getTypeId((int)var9, (int)var13 + var15, (int)var11) != 0) && (var2.getTypeId((int)var9, (int)var13 + var15 + 1, (int)var11) == 0))
/*     */               {
/* 608 */                 var18.setPosition(var9, var13 + var15 + 1.0D, var11);
/* 609 */                 var2.addEntity(var18);
/* 610 */                 break;
/*     */               }
/*     */             }
/*     */           }
/*     */ 
/* 615 */           ((EntityMushroomCow)var4).die();
/* 616 */           EntityCow var20 = new EntityCow(var2);
/* 617 */           var20.setPositionRotation(var4.locX, var4.locY, var4.locZ, var4.yaw, var4.pitch);
/* 618 */           var20.setHealth(((EntityMushroomCow)var4).getHealth());
/* 619 */           var20.V = ((EntityMushroomCow)var4).V;
/* 620 */           var2.addEntity(var20);
/* 621 */           var2.a("largeexplode", var4.locX, var4.locY + var4.length / 2.0F, var4.locZ, 0.0D, 0.0D, 0.0D);
/* 622 */           int var23 = 5 + var2.random.nextInt(2) + chargeLevel(var1) / 2;
/* 623 */           Object var22 = null;
/*     */ 
/* 625 */           for (int var24 = 0; var24 < var23; var24++)
/*     */           {
/* 627 */             new EntityItem(var2, var3.locX, var3.locY, var3.locZ, new ItemStack(Block.RED_MUSHROOM, var23));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 632 */     else if ((var4 instanceof EntitySheep))
/*     */     {
/* 634 */       new EntitySheep(var2);
/* 635 */       ((EntitySheep)var4).setSheared(true);
/* 636 */       int var6 = 3 + var2.random.nextInt(2);
/* 637 */       EntityItem var17 = null;
/*     */ 
/* 639 */       for (int var19 = 0; var19 < var6; var19++)
/*     */       {
/* 641 */         var17 = new EntityItem(var2, var3.locX, var3.locY, var3.locZ, new ItemStack(Block.WOOL.id, var6, ((EntitySheep)var4).getColor()));
/*     */       }
/*     */ 
/* 644 */       var2.addEntity(var17);
/*     */     }
/* 646 */     else if ((var4 instanceof EntityMushroomCow))
/*     */     {
/* 648 */       ((EntityMushroomCow)var4).die();
/* 649 */       EntityCow var16 = new EntityCow(((EntityMushroomCow)var4).world);
/* 650 */       var16.setPositionRotation(((EntityMushroomCow)var4).locX, ((EntityMushroomCow)var4).locY, ((EntityMushroomCow)var4).locZ, ((EntityMushroomCow)var4).yaw, ((EntityMushroomCow)var4).pitch);
/* 651 */       var16.setHealth(((EntityMushroomCow)var4).getHealth());
/* 652 */       var16.V = ((EntityMushroomCow)var4).V;
/* 653 */       ((EntityMushroomCow)var4).world.addEntity(var16);
/* 654 */       ((EntityMushroomCow)var4).world.a("largeexplode", ((EntityMushroomCow)var4).locX, ((EntityMushroomCow)var4).locY + ((EntityMushroomCow)var4).length / 2.0F, ((EntityMushroomCow)var4).locZ, 0.0D, 0.0D, 0.0D);
/*     */ 
/* 656 */       for (int var6 = 0; var6 < 5; var6++)
/*     */       {
/* 658 */         ((EntityMushroomCow)var4).world.addEntity(new EntityItem(((EntityMushroomCow)var4).world, ((EntityMushroomCow)var4).locX, ((EntityMushroomCow)var4).locY + ((EntityMushroomCow)var4).length, ((EntityMushroomCow)var4).locZ, new ItemStack(Block.RED_MUSHROOM)));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int a(Entity var1)
/*     */   {
/* 668 */     return (!(var1 instanceof EntitySheep)) && (!(var1 instanceof EntityMushroomCow)) ? this.weaponDamage : 1;
/*     */   }
/*     */ 
/*     */   public boolean a(ItemStack var1, EntityLiving var2, EntityLiving var3)
/*     */   {
/* 677 */     if ((var3 instanceof EntityHuman))
/*     */     {
/* 679 */       EntityHuman var4 = (EntityHuman)var3;
/*     */ 
/* 681 */       if ((var2 instanceof EntitySheep))
/*     */       {
/* 683 */         if (!((EntitySheep)var2).isSheared())
/*     */         {
/* 685 */           doShear(var1, var4.world, var4, var2);
/*     */         }
/*     */ 
/* 688 */         var2.heal(1);
/*     */       }
/* 690 */       else if ((var2 instanceof EntityMushroomCow))
/*     */       {
/* 692 */         doShear(var1, var4.world, var4, var2);
/* 693 */         var2.heal(1);
/*     */       }
/*     */     }
/*     */ 
/* 697 */     return true;
/*     */   }
/*     */ 
/*     */   public void doAlternate(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 702 */     EEBase.updateSwordMode(var3);
/*     */   }
/*     */ 
/*     */   public void doRelease(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 707 */     doSwordBreak(var1, var2, var3);
/*     */   }
/*     */ 
/*     */   public void doToggle(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemRedKatar
 * JD-Core Version:    0.6.2
 */