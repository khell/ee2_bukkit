/*     */ package ee;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.BlockFlower;
/*     */ import net.minecraft.server.BlockGrass;
/*     */ import net.minecraft.server.BlockLongGrass;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.Material;
/*     */ import net.minecraft.server.World;
/*     */ import net.minecraft.server.WorldGenBigTree;
/*     */ import net.minecraft.server.WorldGenForest;
/*     */ import net.minecraft.server.WorldGenTaiga2;
/*     */ import net.minecraft.server.WorldGenTrees;
/*     */ import net.minecraft.server.WorldGenerator;
/*     */ 
/*     */ public class ItemHarvestRing extends ItemEECharged
/*     */ {
/*     */   private int ticksLastSpent;
/*  20 */   public int tempMeta = 0;
/*  21 */   public int costCounter = 0;
/*     */ 
/*     */   public ItemHarvestRing(int var1)
/*     */   {
/*  25 */     super(var1, 0);
/*     */   }
/*     */ 
/*     */   public int getIconFromDamage(int var1)
/*     */   {
/*  30 */     return !isActivated(var1) ? this.textureId : this.textureId + 1;
/*     */   }
/*     */ 
/*     */   public void doFertilize(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*  35 */     boolean var4 = false;
/*  36 */     boolean var5 = true;
/*  37 */     boolean var6 = true;
/*  38 */     int var7 = (int)EEBase.playerX(var3);
/*  39 */     int var8 = (int)EEBase.playerY(var3);
/*  40 */     int var9 = (int)EEBase.playerZ(var3);
/*     */ 
/*  42 */     for (int var10 = -15; var10 <= 15; var10++)
/*     */     {
/*  44 */       for (int var11 = -15; var11 <= 15; var11++)
/*     */       {
/*  46 */         for (int var12 = -15; var12 <= 15; var12++)
/*     */         {
/*  48 */           int var13 = var2.getTypeId(var7 + var10, var8 + var11, var9 + var12);
/*     */ 
/*  51 */           if ((3 >= var10) && (var10 >= -3) && (3 >= var11) && (var11 >= -3) && (3 >= var12) && (var12 >= -3))
/*     */           {
/*  53 */             if (var13 == Block.CROPS.id)
/*     */             {
/*  55 */               int var14 = var2.getData(var7 + var10, var8 + var11, var9 + var12);
/*     */ 
/*  57 */               if (var14 < 7)
/*     */               {
/*  59 */                 if (!var4)
/*     */                 {
/*  61 */                   if (ConsumeReagentBonemeal(var3, var5))
/*     */                   {
/*  63 */                     if (var6)
/*     */                     {
/*  65 */                       var2.makeSound(var3, "flash", 0.7F, 1.0F);
/*  66 */                       var6 = false;
/*     */                     }
/*     */ 
/*  69 */                     var4 = true;
/*  70 */                     var14++;
/*     */ 
/*  72 */                     if (var2.random.nextInt(8) == 0)
/*     */                     {
/*  74 */                       var2.a("largesmoke", var7 + var10, var8 + var11, var9 + var12, 0.0D, 0.05D, 0.0D);
/*     */                     }
/*     */ 
/*  77 */                     var2.setData(var7 + var10, var8 + var11, var9 + var12, var14);
/*     */                   }
/*     */ 
/*  80 */                   var5 = false;
/*     */                 }
/*     */                 else
/*     */                 {
/*  84 */                   if (var6)
/*     */                   {
/*  86 */                     var2.makeSound(var3, "flash", 0.7F, 1.0F);
/*  87 */                     var6 = false;
/*     */                   }
/*     */ 
/*  90 */                   var14++;
/*     */ 
/*  92 */                   if (var2.random.nextInt(8) == 0)
/*     */                   {
/*  94 */                     var2.a("largesmoke", var7 + var10, var8 + var11, var9 + var12, 0.0D, 0.05D, 0.0D);
/*     */                   }
/*     */ 
/*  97 */                   var2.setData(var7 + var10, var8 + var11, var9 + var12, var14);
/*     */                 }
/*     */               }
/*     */             }
/* 101 */             else if ((var13 == Block.SUGAR_CANE_BLOCK.id) && (var2.getTypeId(var7 + var10, var8 + var11 - 4, var9 + var12) != Block.SUGAR_CANE_BLOCK.id) && (var2.getTypeId(var7 + var10, var8 + var11 + 1, var9 + var12) == 0))
/*     */             {
/* 103 */               if (!var4)
/*     */               {
/* 105 */                 if (ConsumeReagentBonemeal(var3, var5))
/*     */                 {
/* 107 */                   if (var6)
/*     */                   {
/* 109 */                     var2.makeSound(var3, "flash", 0.7F, 1.0F);
/* 110 */                     var6 = false;
/*     */                   }
/*     */ 
/* 113 */                   var4 = true;
/* 114 */                   var2.setTypeId(var7 + var10, var8 + var11 + 1, var9 + var12, Block.SUGAR_CANE_BLOCK.id);
/*     */                 }
/*     */ 
/* 117 */                 var5 = false;
/*     */               }
/*     */               else
/*     */               {
/* 121 */                 if (var6)
/*     */                 {
/* 123 */                   var2.makeSound(var3, "flash", 0.7F, 1.0F);
/* 124 */                   var6 = false;
/*     */                 }
/*     */ 
/* 127 */                 var2.setTypeId(var7 + var10, var8 + var11 + 1, var9 + var12, Block.SUGAR_CANE_BLOCK.id);
/*     */               }
/*     */             }
/* 130 */             else if ((var13 == Block.CACTUS.id) && (var2.getTypeId(var7 + var10, var8 + var11 - 4, var9 + var12) != Block.CACTUS.id) && (var2.getTypeId(var7 + var10, var8 + var11 + 1, var9 + var12) == 0))
/*     */             {
/* 132 */               if (!var4)
/*     */               {
/* 134 */                 if (ConsumeReagentBonemeal(var3, var5))
/*     */                 {
/* 136 */                   if (var6)
/*     */                   {
/* 138 */                     var2.makeSound(var3, "flash", 0.7F, 1.0F);
/* 139 */                     var6 = false;
/*     */                   }
/*     */ 
/* 142 */                   var4 = true;
/* 143 */                   var2.setTypeId(var7 + var10, var8 + var11 + 1, var9 + var12, Block.CACTUS.id);
/*     */                 }
/*     */ 
/* 146 */                 var5 = false;
/*     */               }
/*     */               else
/*     */               {
/* 150 */                 if (var6)
/*     */                 {
/* 152 */                   var2.makeSound(var3, "flash", 0.7F, 1.0F);
/* 153 */                   var6 = false;
/*     */                 }
/*     */ 
/* 156 */                 var2.setTypeId(var7 + var10, var8 + var11 + 1, var9 + var12, Block.CACTUS.id);
/*     */               }
/*     */             }
/*     */ 
/* 160 */             if ((var13 == BlockFlower.RED_ROSE.id) || (var13 == BlockFlower.YELLOW_FLOWER.id) || (var13 == BlockFlower.BROWN_MUSHROOM.id) || (var13 == BlockFlower.RED_MUSHROOM.id))
/*     */             {
/* 162 */               for (int var14 = -1; var14 <= 0; var14++)
/*     */               {
/* 164 */                 if (var2.getTypeId(var7 + var10 + var14, var8 + var11, var9 + var12) == 0)
/*     */                 {
/* 166 */                   if (!var4)
/*     */                   {
/* 168 */                     if (ConsumeReagentBonemeal(var3, var5))
/*     */                     {
/* 170 */                       if (var6)
/*     */                       {
/* 172 */                         var2.makeSound(var3, "flash", 0.7F, 1.0F);
/* 173 */                         var6 = false;
/*     */                       }
/*     */ 
/* 176 */                       var4 = true;
/* 177 */                       var2.setTypeId(var7 + var10 + var14, var8 + var11, var9 + var12, var13);
/*     */                     }
/*     */ 
/* 180 */                     var5 = false;
/*     */                   }
/*     */                   else
/*     */                   {
/* 184 */                     if (var6)
/*     */                     {
/* 186 */                       var2.makeSound(var3, "flash", 0.7F, 1.0F);
/* 187 */                       var6 = false;
/*     */                     }
/*     */ 
/* 190 */                     var2.setTypeId(var7 + var10 + var14, var8 + var11, var9 + var12, var13);
/*     */                   }
/*     */                 }
/* 193 */                 else if (var2.getTypeId(var7 + var10, var8 + var11, var9 + var12 + var14) == 0)
/*     */                 {
/* 195 */                   if (var4)
/*     */                   {
/* 197 */                     if (var6)
/*     */                     {
/* 199 */                       var2.makeSound(var3, "flash", 0.7F, 1.0F);
/* 200 */                       var6 = false;
/*     */                     }
/*     */ 
/* 203 */                     var2.setTypeId(var7 + var10, var8 + var11, var9 + var12 + var14, var13);
/* 204 */                     break;
/*     */                   }
/*     */ 
/* 207 */                   if (ConsumeReagentBonemeal(var3, var5))
/*     */                   {
/* 209 */                     if (var6)
/*     */                     {
/* 211 */                       var2.makeSound(var3, "flash", 0.7F, 1.0F);
/* 212 */                       var6 = false;
/*     */                     }
/*     */ 
/* 215 */                     var4 = true;
/* 216 */                     var2.setTypeId(var7 + var10, var8 + var11, var9 + var12 + var14, var13);
/*     */                   }
/*     */ 
/* 219 */                   var5 = false;
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */ 
/* 225 */           if (var13 == Block.SAPLING.id)
/*     */           {
/* 229 */             if (!var4)
/*     */             {
/* 231 */               if (ConsumeReagentBonemeal(var3, var5))
/*     */               {
/* 233 */                 if (var6)
/*     */                 {
/* 235 */                   var2.makeSound(var3, "flash", 0.7F, 1.0F);
/* 236 */                   var6 = false;
/*     */                 }
/*     */ 
/* 239 */                 var4 = true;
/*     */ 
/* 241 */                 if (var2.random.nextInt(100) < 25)
/*     */                 {
/* 243 */                   int var14 = var2.getData(var7 + var10, var8 + var11, var9 + var12) & 0x3;
/* 244 */                   var2.setRawTypeId(var7 + var10, var8 + var11, var9 + var12, 0);
/* 245 */                   Object var15 = null;
/*     */ 
/* 247 */                   if (var14 == 1)
/*     */                   {
/* 249 */                     var15 = new WorldGenTaiga2(true);
/*     */                   }
/* 251 */                   else if (var14 == 2)
/*     */                   {
/* 253 */                     var15 = new WorldGenForest(true);
/*     */                   }
/*     */                   else
/*     */                   {
/* 257 */                     var15 = new WorldGenTrees(true);
/*     */ 
/* 259 */                     if (var2.random.nextInt(10) == 0)
/*     */                     {
/* 261 */                       var15 = new WorldGenBigTree(true);
/*     */                     }
/*     */                   }
/*     */ 
/* 265 */                   if (var2.random.nextInt(8) == 0)
/*     */                   {
/* 267 */                     var2.a("largesmoke", var7 + var10, var8 + var11, var9 + var12, 0.0D, 0.05D, 0.0D);
/*     */                   }
/*     */ 
/* 270 */                   if (!((WorldGenerator)var15).a(var2, var2.random, var7 + var10, var8 + var11, var9 + var12))
/*     */                   {
/* 272 */                     var2.setRawTypeIdAndData(var7 + var10, var8 + var11, var9 + var12, Block.SAPLING.id, var14);
/*     */                   }
/*     */                 }
/*     */               }
/*     */ 
/* 277 */               var5 = false;
/*     */             }
/*     */             else
/*     */             {
/* 281 */               if (var6)
/*     */               {
/* 283 */                 var2.makeSound(var3, "flash", 0.7F, 1.0F);
/* 284 */                 var6 = false;
/*     */               }
/*     */ 
/* 287 */               if (var2.random.nextInt(100) < 25)
/*     */               {
/* 289 */                 int var14 = var2.getData(var7 + var10, var8 + var11, var9 + var12) & 0x3;
/* 290 */                 var2.setRawTypeId(var7 + var10, var8 + var11, var9 + var12, 0);
/* 291 */                 Object var15 = null;
/*     */ 
/* 293 */                 if (var14 == 1)
/*     */                 {
/* 295 */                   var15 = new WorldGenTaiga2(true);
/*     */                 }
/* 297 */                 else if (var14 == 2)
/*     */                 {
/* 299 */                   var15 = new WorldGenForest(true);
/*     */                 }
/*     */                 else
/*     */                 {
/* 303 */                   var15 = new WorldGenTrees(true);
/*     */ 
/* 305 */                   if (var2.random.nextInt(10) == 0)
/*     */                   {
/* 307 */                     var15 = new WorldGenBigTree(true);
/*     */                   }
/*     */                 }
/*     */ 
/* 311 */                 if (var2.random.nextInt(8) == 0)
/*     */                 {
/* 313 */                   var2.a("largesmoke", var7 + var10, var8 + var11, var9 + var12, 0.0D, 0.05D, 0.0D);
/*     */                 }
/*     */ 
/* 316 */                 if (!((WorldGenerator)var15).a(var2, var2.random, var7 + var10, var8 + var11, var9 + var12))
/*     */                 {
/* 318 */                   var2.setRawTypeIdAndData(var7 + var10, var8 + var11, var9 + var12, Block.SAPLING.id, var14);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean ConsumeReagentBonemeal(EntityHuman var1, boolean var2)
/*     */   {
/* 330 */     return EEBase.Consume(new ItemStack(Item.INK_SACK, 4, 15), var1, var2);
/*     */   }
/*     */ 
/*     */   public boolean ConsumeReagentSapling(EntityHuman var1, boolean var2)
/*     */   {
/* 335 */     if (EEBase.Consume(new ItemStack(Block.SAPLING, 1, 0), var1, var2))
/*     */     {
/* 337 */       this.tempMeta = 0;
/* 338 */       return true;
/*     */     }
/* 340 */     if (EEBase.Consume(new ItemStack(Block.SAPLING, 1, 1), var1, var2))
/*     */     {
/* 342 */       this.tempMeta = 1;
/* 343 */       return true;
/*     */     }
/* 345 */     if (EEBase.Consume(new ItemStack(Block.SAPLING, 1, 2), var1, var2))
/*     */     {
/* 347 */       this.tempMeta = 2;
/* 348 */       return true;
/*     */     }
/*     */ 
/* 352 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean ConsumeReagentCactus(EntityHuman var1, boolean var2)
/*     */   {
/* 358 */     return EEBase.Consume(new ItemStack(Block.CACTUS, 1), var1, var2);
/*     */   }
/*     */ 
/*     */   public boolean ConsumeReagentSeeds(EntityHuman var1, boolean var2)
/*     */   {
/* 363 */     return EEBase.Consume(new ItemStack(Item.SEEDS, 1), var1, var2);
/*     */   }
/*     */ 
/*     */   public boolean ConsumeReagentReed(EntityHuman var1, boolean var2)
/*     */   {
/* 368 */     return EEBase.Consume(new ItemStack(Item.SUGAR_CANE, 1), var1, var2);
/*     */   }
/*     */ 
/*     */   public float getDestroySpeed(ItemStack var1, Block var2)
/*     */   {
/* 377 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   public void doPassive(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 382 */     int var4 = (int)EEBase.playerX(var3);
/* 383 */     int var5 = (int)EEBase.playerY(var3);
/* 384 */     int var6 = (int)EEBase.playerZ(var3);
/*     */ 
/* 386 */     for (int var7 = -5; var7 <= 5; var7++)
/*     */     {
/* 388 */       for (int var8 = -5; var8 <= 5; var8++)
/*     */       {
/* 390 */         for (int var9 = -5; var9 <= 5; var9++)
/*     */         {
/* 392 */           int var10 = var2.getTypeId(var4 + var7, var5 + var8, var6 + var9);
/*     */ 
/* 395 */           if (var10 == Block.CROPS.id)
/*     */           {
/* 397 */             int var11 = var2.getData(var4 + var7, var5 + var8, var6 + var9);
/*     */ 
/* 399 */             if ((var11 < 7) && (var2.random.nextInt(600) == 0))
/*     */             {
/* 401 */               var11++;
/* 402 */               var2.setData(var4 + var7, var5 + var8, var6 + var9, var11);
/*     */             }
/*     */           }
/* 405 */           else if ((var10 != BlockFlower.YELLOW_FLOWER.id) && (var10 != BlockFlower.RED_ROSE.id) && (var10 != BlockFlower.BROWN_MUSHROOM.id) && (var10 != BlockFlower.RED_MUSHROOM.id))
/*     */           {
/* 407 */             if ((var10 == Block.GRASS.id) && (var2.getTypeId(var4 + var7, var5 + var8 + 1, var6 + var9) == 0) && (var2.random.nextInt(4000) == 0))
/*     */             {
/* 409 */               var2.setTypeId(var4 + var7, var5 + var8 + 1, var6 + var9, BlockFlower.LONG_GRASS.id);
/* 410 */               var2.setData(var4 + var7, var5 + var8 + 1, var6 + var9, 1);
/*     */             }
/*     */ 
/* 413 */             if ((var10 == Block.DIRT.id) && (var2.getTypeId(var4 + var7, var5 + var8 + 1, var6 + var9) == 0) && (var2.random.nextInt(800) == 0))
/*     */             {
/* 415 */               var2.setTypeId(var4 + var7, var5 + var8, var6 + var9, Block.GRASS.id);
/*     */             }
/* 417 */             else if (((var10 == Block.SUGAR_CANE_BLOCK.id) || (var10 == Block.CACTUS.id)) && (var2.getTypeId(var4 + var7, var5 + var8 + 1, var6 + var9) == 0) && (var2.getTypeId(var4 + var7, var5 + var8 - 4, var6 + var9) != Block.SUGAR_CANE_BLOCK.id) && (var2.getTypeId(var4 + var7, var5 + var8 - 4, var6 + var9) != Block.CACTUS.id) && (var2.random.nextInt(600) == 0))
/*     */             {
/* 419 */               var2.setTypeId(var4 + var7, var5 + var8 + 1, var6 + var9, var10);
/*     */ 
/* 421 */               if (var2.random.nextInt(8) == 0)
/*     */               {
/* 423 */                 var2.a("largesmoke", var4 + var7, var5 + var8, var6 + var9, 0.0D, 0.05D, 0.0D);
/*     */               }
/*     */             }
/*     */           }
/* 427 */           else if (var2.random.nextInt(2) == 0)
/*     */           {
/* 429 */             for (int var11 = -1; var11 < 0; var11++)
/*     */             {
/* 431 */               if ((var2.getTypeId(var4 + var7 + var11, var5 + var8, var6 + var9) == 0) && (var2.getTypeId(var4 + var7 + var11, var5 + var8 - 1, var6 + var9) == Block.GRASS.id))
/*     */               {
/* 433 */                 if (var2.random.nextInt(800) == 0)
/*     */                 {
/* 435 */                   var2.setTypeId(var4 + var7 + var11, var5 + var8, var6 + var9, var10);
/*     */ 
/* 437 */                   if (var2.random.nextInt(8) == 0)
/*     */                   {
/* 439 */                     var2.a("largesmoke", var4 + var7 + var11, var5 + var8, var6 + var9, 0.0D, 0.05D, 0.0D);
/*     */                   }
/*     */                 }
/*     */               }
/* 443 */               else if ((var2.getTypeId(var4 + var7, var5 + var8, var6 + var9 + var11) == 0) && (var2.getTypeId(var4 + var7, var5 + var8 - 1, var6 + var9 + var11) == Block.GRASS.id) && (var2.random.nextInt(1800) == 0))
/*     */               {
/* 445 */                 var2.setTypeId(var4 + var7, var5 + var8, var6 + var9 + var11, var10);
/*     */ 
/* 447 */                 if (var2.random.nextInt(8) == 0)
/*     */                 {
/* 449 */                   var2.a("largesmoke", var4 + var7, var5 + var8, var6 + var9 + var11, 0.0D, 0.05D, 0.0D);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doActive(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 461 */     int var4 = (int)EEBase.playerX(var3);
/* 462 */     int var5 = (int)EEBase.playerY(var3);
/* 463 */     int var6 = (int)EEBase.playerZ(var3);
/*     */ 
/* 465 */     for (int var7 = -5; var7 <= 5; var7++)
/*     */     {
/* 467 */       for (int var8 = -5; var8 <= 5; var8++)
/*     */       {
/* 469 */         for (int var9 = -5; var9 <= 5; var9++)
/*     */         {
/* 471 */           int var10 = var2.getTypeId(var4 + var7, var5 + var8, var6 + var9);
/*     */ 
/* 473 */           if (var10 == Block.CROPS.id)
/*     */           {
/* 475 */             int var11 = var2.getData(var4 + var7, var5 + var8, var6 + var9);
/*     */ 
/* 477 */             if (var11 >= 7)
/*     */             {
/* 479 */               Block.byId[var10].dropNaturally(var2, var4 + var7, var5 + var8, var6 + var9, var2.getData(var4 + var7, var5 + var8, var6 + var9), 0.05F, 1);
/* 480 */               Block.byId[var10].dropNaturally(var2, var4 + var7, var5 + var8, var6 + var9, var2.getData(var4 + var7, var5 + var8, var6 + var9), 1.0F, 1);
/* 481 */               var2.setTypeId(var4 + var7, var5 + var8, var6 + var9, 0);
/*     */ 
/* 483 */               if (var2.random.nextInt(8) == 0)
/*     */               {
/* 485 */                 var2.a("largesmoke", var4 + var7, var5 + var8, var6 + var9, 0.0D, 0.05D, 0.0D);
/*     */               }
/*     */             }
/* 488 */             else if (var2.random.nextInt(400) == 0)
/*     */             {
/* 490 */               var11++;
/* 491 */               var2.setData(var4 + var7, var5 + var8, var6 + var9, var11);
/*     */             }
/*     */           }
/* 494 */           else if ((var10 != BlockFlower.YELLOW_FLOWER.id) && (var10 != BlockFlower.RED_ROSE.id) && (var10 != BlockFlower.BROWN_MUSHROOM.id) && (var10 != BlockFlower.RED_MUSHROOM.id) && (var10 != BlockFlower.LONG_GRASS.id))
/*     */           {
/* 496 */             if (((var10 == Block.SUGAR_CANE_BLOCK.id) && (var2.getTypeId(var4 + var7, var5 + var8 - 4, var6 + var9) == Block.SUGAR_CANE_BLOCK.id)) || ((var10 == Block.CACTUS.id) && (var2.getTypeId(var4 + var7, var5 + var8 - 4, var6 + var9) == Block.CACTUS.id)))
/*     */             {
/* 498 */               if (var10 == Block.SUGAR_CANE_BLOCK.id)
/*     */               {
/* 500 */                 Block.byId[var10].dropNaturally(var2, var4 + var7, var5 + var8 - 3, var6 + var9, var2.getData(var4 + var7, var5 + var8, var6 + var9), 0.25F, 1);
/* 501 */                 Block.byId[var10].dropNaturally(var2, var4 + var7, var5 + var8 - 3, var6 + var9, var2.getData(var4 + var7, var5 + var8, var6 + var9), 1.0F, 1);
/* 502 */                 var2.setTypeId(var4 + var7, var5 + var8 - 3, var6 + var9, 0);
/*     */               }
/*     */               else
/*     */               {
/* 506 */                 Block.byId[var10].dropNaturally(var2, var4 + var7, var5 + var8 - 4, var6 + var9, var2.getData(var4 + var7, var5 + var8, var6 + var9), 0.25F, 1);
/* 507 */                 Block.byId[var10].dropNaturally(var2, var4 + var7, var5 + var8 - 4, var6 + var9, var2.getData(var4 + var7, var5 + var8, var6 + var9), 1.0F, 1);
/* 508 */                 var2.setTypeId(var4 + var7, var5 + var8 - 4, var6 + var9, 0);
/*     */               }
/*     */ 
/* 511 */               if (var2.random.nextInt(8) == 0)
/*     */               {
/* 513 */                 var2.a("largesmoke", var4 + var7, var5 + var8 - 3, var6 + var9, 0.0D, 0.05D, 0.0D);
/*     */               }
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 519 */             Block.byId[var10].dropNaturally(var2, var4 + var7, var5 + var8, var6 + var9, var2.getData(var4 + var7, var5 + var8, var6 + var9), 0.05F, 1);
/* 520 */             Block.byId[var10].dropNaturally(var2, var4 + var7, var5 + var8, var6 + var9, var2.getData(var4 + var7, var5 + var8, var6 + var9), 1.0F, 1);
/* 521 */             var2.setTypeId(var4 + var7, var5 + var8, var6 + var9, 0);
/*     */ 
/* 523 */             if (var2.random.nextInt(8) == 0)
/*     */             {
/* 525 */               var2.a("largesmoke", var4 + var7, var5 + var8, var6 + var9, 0.0D, 0.05D, 0.0D);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 539 */     if (EEProxy.isClient(var3))
/*     */     {
/* 541 */       return false;
/*     */     }
/*     */ 
/* 549 */     if (var3.getTypeId(var4, var5, var6) == 60)
/*     */     {
/* 551 */       for (int var14 = -5; var14 <= 5; var14++)
/*     */       {
/* 553 */         for (int var9 = -5; var9 <= 5; var9++)
/*     */         {
/* 555 */           int var10 = var3.getTypeId(var14 + var4, var5, var9 + var6);
/* 556 */           int var11 = var3.getTypeId(var14 + var4, var5 + 1, var9 + var6);
/*     */ 
/* 558 */           if ((var10 == 60) && (var11 == 0) && (ConsumeReagentSeeds(var2, false)))
/*     */           {
/* 560 */             var3.setTypeId(var14 + var4, var5 + 1, var9 + var6, Block.CROPS.id);
/*     */ 
/* 562 */             if (var3.random.nextInt(8) == 0)
/*     */             {
/* 564 */               var3.a("largesmoke", var14 + var4, var5, var9 + var6, 0.0D, 0.05D, 0.0D);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 570 */       return true;
/*     */     }
/*     */ 
/* 576 */     if (var3.getTypeId(var4, var5, var6) == 12)
/*     */     {
/* 578 */       double var15 = EEBase.direction(var2);
/*     */ 
/* 580 */       if (var15 == 5.0D)
/*     */       {
/* 582 */         var4 += 5;
/*     */       }
/* 584 */       else if (var15 == 4.0D)
/*     */       {
/* 586 */         var6 -= 5;
/*     */       }
/* 588 */       else if (var15 == 3.0D)
/*     */       {
/* 590 */         var4 -= 5;
/*     */       }
/* 592 */       else if (var15 == 2.0D)
/*     */       {
/* 594 */         var6 += 5;
/*     */       }
/*     */ 
/* 597 */       for (int var10 = -5; var10 <= 5; var10++)
/*     */       {
/* 599 */         for (int var11 = -5; var11 <= 5; var11++)
/*     */         {
/* 601 */           int var12 = var3.getTypeId(var10 + var4, var5, var11 + var6);
/* 602 */           int var13 = var3.getTypeId(var10 + var4, var5 + 1, var11 + var6);
/*     */ 
/* 604 */           if ((var12 == 12) && (var13 == 0) && (var10 % 5 == 0) && (var11 % 5 == 0) && (ConsumeReagentCactus(var2, false)))
/*     */           {
/* 606 */             var3.setTypeId(var10 + var4, var5 + 1, var11 + var6, Block.CACTUS.id);
/*     */ 
/* 608 */             if (var3.random.nextInt(8) == 0)
/*     */             {
/* 610 */               var3.a("largesmoke", var10 + var4, var5, var11 + var6, 0.0D, 0.05D, 0.0D);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 616 */       return true;
/*     */     }
/*     */ 
/* 620 */     boolean var8 = false;
/*     */ 
/* 622 */     if ((var3.getTypeId(var4, var5, var6) != Block.DIRT.id) && (var3.getTypeId(var4, var5, var6) != Block.GRASS.id) && (var3.getTypeId(var4, var5, var6) != BlockFlower.LONG_GRASS.id))
/*     */     {
/* 624 */       return false;
/*     */     }
/*     */ 
/* 628 */     if (var3.getTypeId(var4, var5, var6) == BlockFlower.LONG_GRASS.id)
/*     */     {
/* 630 */       Block.byId[BlockFlower.LONG_GRASS.id].dropNaturally(var3, var4, var5, var6, 1, 1.0F, 1);
/* 631 */       var3.setTypeId(var4, var5, var6, 0);
/* 632 */       var5--;
/*     */     }
/*     */ 
/* 635 */     if ((var3.getMaterial(var4 + 1, var5, var6) == Material.WATER) || (var3.getMaterial(var4 - 1, var5, var6) == Material.WATER) || (var3.getMaterial(var4, var5, var6 + 1) == Material.WATER) || (var3.getMaterial(var4, var5, var6 - 1) == Material.WATER))
/*     */     {
/* 637 */       var8 = true;
/*     */     }
/*     */ 
/* 640 */     for (int var9 = -8; var9 <= 8; var9++)
/*     */     {
/* 642 */       for (int var10 = -8; var10 <= 8; var10++)
/*     */       {
/* 644 */         int var11 = var3.getTypeId(var9 + var4, var5, var10 + var6);
/* 645 */         int var12 = var3.getTypeId(var9 + var4, var5 + 1, var10 + var6);
/*     */ 
/* 647 */         if (var8)
/*     */         {
/* 649 */           if (((var3.getMaterial(var9 + var4 + 1, var5, var10 + var6) == Material.WATER) || (var3.getMaterial(var9 + var4 - 1, var5, var10 + var6) == Material.WATER) || (var3.getMaterial(var9 + var4, var5, var10 + var6 + 1) == Material.WATER) || (var3.getMaterial(var9 + var4, var5, var10 + var6 - 1) == Material.WATER)) && ((var11 == Block.DIRT.id) || (var11 == Block.GRASS.id)) && (var12 == 0) && (ConsumeReagentReed(var2, false)))
/*     */           {
/* 651 */             var3.setTypeId(var9 + var4, var5 + 1, var10 + var6, Block.SUGAR_CANE_BLOCK.id);
/*     */           }
/*     */         }
/* 654 */         else if (((var11 == Block.DIRT.id) || (var11 == Block.GRASS.id)) && ((var12 == 0) || (var12 == BlockFlower.LONG_GRASS.id)) && (var9 % 4 == 0) && (var10 % 4 == 0) && (ConsumeReagentSapling(var2, false)))
/*     */         {
/* 656 */           if (var12 == BlockFlower.LONG_GRASS.id)
/*     */           {
/* 658 */             Block.byId[var12].dropNaturally(var3, var9 + var4, var5 + 1, var10 + var6, 1, 1.0F, 1);
/* 659 */             var3.setTypeId(var9 + var4, var5 + 1, var10 + var6, 0);
/*     */           }
/*     */ 
/* 662 */           var3.setTypeIdAndData(var9 + var4, var5 + 1, var10 + var6, Block.SAPLING.id, this.tempMeta);
/*     */ 
/* 664 */           if (var3.random.nextInt(8) == 0)
/*     */           {
/* 666 */             var3.a("largesmoke", var9 + var4, var5, var10 + var6, 0.0D, 0.05D, 0.0D);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 672 */     return true;
/*     */   }
/*     */ 
/*     */   public void ConsumeReagent(ItemStack var1, EntityHuman var2, boolean var3)
/*     */   {
/* 681 */     EEBase.ConsumeReagentForDuration(var1, var2, var3);
/*     */   }
/*     */ 
/*     */   public void doLeftClick(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 686 */     doFertilize(var1, var2, var3);
/*     */   }
/*     */ 
/*     */   public boolean canActivate()
/*     */   {
/* 691 */     return true;
/*     */   }
/*     */ 
/*     */   public void doChargeTick(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void doUncharge(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemHarvestRing
 * JD-Core Version:    0.6.2
 */