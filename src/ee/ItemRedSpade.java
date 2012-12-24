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
/*     */ public class ItemRedSpade extends ItemRedTool
/*     */ {
/*  15 */   private static Block[] blocksEffectiveAgainst = { Block.GRASS, Block.DIRT, Block.SOUL_SAND, Block.SAND, Block.GRAVEL, Block.SNOW, Block.SNOW_BLOCK, Block.CLAY, Block.SOIL };
/*     */ 
/*     */   public ItemRedSpade(int var1)
/*     */   {
/*  19 */     super(var1, 3, 5, blocksEffectiveAgainst);
/*     */   }
/*     */ 
/*     */   public boolean canDestroySpecialBlock(Block var1)
/*     */   {
/*  27 */     return var1 == Block.SNOW;
/*     */   }
/*     */ 
/*     */   public float getDestroySpeed(ItemStack var1, Block var2)
/*     */   {
/*  36 */     float var3 = 1.0F;
/*  37 */     return super.getDestroySpeed(var1, var2) / var3;
/*     */   }
/*     */ 
/*     */   public boolean a(ItemStack var1, int var2, int var3, int var4, int var5, EntityLiving var6)
/*     */   {
/*  42 */     EntityHuman var7 = null;
/*     */ 
/*  44 */     if ((var6 instanceof EntityHuman))
/*     */     {
/*  46 */       var7 = (EntityHuman)var6;
/*     */ 
/*  48 */       if (EEBase.getToolMode(var7) != 0)
/*     */       {
/*  50 */         if (EEBase.getToolMode(var7) == 1)
/*     */         {
/*  52 */           doTallImpact(var7.world, var1, var7, var3, var4, var5, EEBase.direction(var7));
/*     */         }
/*  54 */         else if (EEBase.getToolMode(var7) == 2)
/*     */         {
/*  56 */           doWideImpact(var7.world, var1, var3, var4, var5, EEBase.heading(var7));
/*     */         }
/*  58 */         else if (EEBase.getToolMode(var7) == 3)
/*     */         {
/*  60 */           doLongImpact(var7.world, var1, var3, var4, var5, EEBase.direction(var7));
/*     */         }
/*     */       }
/*     */ 
/*  64 */       return true;
/*     */     }
/*     */ 
/*  68 */     return true;
/*     */   }
/*     */ 
/*     */   public void scanBlockAndBreak(World var1, ItemStack var2, int var3, int var4, int var5)
/*     */   {
/*  74 */     int var6 = var1.getTypeId(var3, var4, var5);
/*  75 */     int var7 = var1.getData(var3, var4, var5);
/*  76 */     ArrayList var8 = Block.byId[var6].getBlockDropped(var1, var3, var4, var5, var7, 0);
/*  77 */     Iterator var9 = var8.iterator();
/*     */ 
/*  79 */     while (var9.hasNext())
/*     */     {
/*  81 */       ItemStack var10 = (ItemStack)var9.next();
/*  82 */       addToDroplist(var2, var10);
/*     */     }
/*     */ 
/*  85 */     var1.setTypeId(var3, var4, var5, 0);
/*     */ 
/*  87 */     if (var1.random.nextInt(8) == 0)
/*     */     {
/*  89 */       var1.a("largesmoke", var3, var4, var5, 0.0D, 0.0D, 0.0D);
/*     */     }
/*     */ 
/*  92 */     if (var1.random.nextInt(8) == 0)
/*     */     {
/*  94 */       var1.a("explode", var3, var4, var5, 0.0D, 0.0D, 0.0D);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean canBreak(int var1, int var2)
/*     */   {
/* 100 */     if (Block.byId[var1] == null)
/*     */     {
/* 102 */       return false;
/*     */     }
/* 104 */     if ((!Block.byId[var1].hasTileEntity(var2)) && (var1 != Block.BEDROCK.id))
/*     */     {
/* 106 */       if (Block.byId[var1].material == null)
/*     */       {
/* 108 */         return false;
/*     */       }
/*     */ 
/* 112 */       for (int var3 = 0; var3 < blocksEffectiveAgainst.length; var3++)
/*     */       {
/* 114 */         if (var1 == blocksEffectiveAgainst[var3].id)
/*     */         {
/* 116 */           return true;
/*     */         }
/*     */       }
/*     */ 
/* 120 */       if ((Block.byId[var1].material != Material.GRASS) && (Block.byId[var1].material != Material.EARTH) && (Block.byId[var1].material != Material.SAND) && (Block.byId[var1].material != Material.SNOW_LAYER) && (Block.byId[var1].material != Material.CLAY))
/*     */       {
/* 122 */         return false;
/*     */       }
/*     */ 
/* 126 */       return true;
/*     */     }
/*     */ 
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */   public void doLongImpact(World var1, ItemStack var2, int var3, int var4, int var5, double var6)
/*     */   {
/* 138 */     cleanDroplist(var2);
/*     */ 
/* 140 */     for (int var8 = 1; var8 <= 2; var8++)
/*     */     {
/* 142 */       int var9 = var3;
/* 143 */       int var10 = var4;
/* 144 */       int var11 = var5;
/*     */ 
/* 146 */       if (var6 == 0.0D)
/*     */       {
/* 148 */         var10 = var4 - var8;
/*     */       }
/*     */ 
/* 151 */       if (var6 == 1.0D)
/*     */       {
/* 153 */         var10 = var4 + var8;
/*     */       }
/*     */ 
/* 156 */       if (var6 == 2.0D)
/*     */       {
/* 158 */         var11 = var5 + var8;
/*     */       }
/*     */ 
/* 161 */       if (var6 == 3.0D)
/*     */       {
/* 163 */         var9 = var3 - var8;
/*     */       }
/*     */ 
/* 166 */       if (var6 == 4.0D)
/*     */       {
/* 168 */         var11 = var5 - var8;
/*     */       }
/*     */ 
/* 171 */       if (var6 == 5.0D)
/*     */       {
/* 173 */         var9 = var3 + var8;
/*     */       }
/*     */ 
/* 176 */       int var12 = var1.getTypeId(var9, var10, var11);
/* 177 */       int var13 = var1.getData(var9, var10, var11);
/*     */ 
/* 179 */       if (canBreak(var12, var13))
/*     */       {
/* 181 */         scanBlockAndBreak(var1, var2, var9, var10, var11);
/*     */       }
/*     */     }
/*     */ 
/* 185 */     ejectDropList(var1, var2, var3, var4 + 0.5D, var5);
/*     */   }
/*     */ 
/*     */   public void doWideImpact(World var1, ItemStack var2, int var3, int var4, int var5, double var6)
/*     */   {
/* 190 */     cleanDroplist(var2);
/*     */ 
/* 192 */     for (int var8 = -1; var8 <= 1; var8++)
/*     */     {
/* 194 */       int var9 = var3;
/* 195 */       int var11 = var5;
/*     */ 
/* 197 */       if (var8 != 0)
/*     */       {
/* 199 */         if ((var6 != 2.0D) && (var6 != 4.0D))
/*     */         {
/* 201 */           var11 = var5 + var8;
/*     */         }
/*     */         else
/*     */         {
/* 205 */           var9 = var3 + var8;
/*     */         }
/*     */ 
/* 208 */         int var12 = var1.getTypeId(var9, var4, var11);
/* 209 */         int var13 = var1.getData(var9, var4, var11);
/*     */ 
/* 211 */         if (canBreak(var12, var13))
/*     */         {
/* 213 */           scanBlockAndBreak(var1, var2, var9, var4, var11);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 218 */     ejectDropList(var1, var2, var3, var4 + 0.5D, var5);
/*     */   }
/*     */ 
/*     */   public void doTallImpact(World var1, ItemStack var2, EntityHuman var3, int var4, int var5, int var6, double var7)
/*     */   {
/* 223 */     cleanDroplist(var2);
/*     */ 
/* 225 */     for (int var9 = -1; var9 <= 1; var9++)
/*     */     {
/* 227 */       int var10 = var4;
/* 228 */       int var11 = var5;
/* 229 */       int var12 = var6;
/*     */ 
/* 231 */       if (var9 != 0)
/*     */       {
/* 233 */         if ((var7 != 0.0D) && (var7 != 1.0D))
/*     */         {
/* 235 */           var11 = var5 + var9;
/*     */         }
/* 237 */         else if ((EEBase.heading(var3) != 2.0D) && (EEBase.heading(var3) != 4.0D))
/*     */         {
/* 239 */           var10 = var4 + var9;
/*     */         }
/*     */         else
/*     */         {
/* 243 */           var12 = var6 + var9;
/*     */         }
/*     */ 
/* 246 */         int var13 = var1.getTypeId(var10, var11, var12);
/* 247 */         int var14 = var1.getData(var10, var11, var12);
/*     */ 
/* 249 */         if (canBreak(var13, var14))
/*     */         {
/* 251 */           scanBlockAndBreak(var1, var2, var10, var11, var12);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 256 */     ejectDropList(var1, var2, var4, var5 + 0.5D, var6);
/*     */   }
/*     */ 
/*     */   public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 265 */     if (EEProxy.isClient(var3))
/*     */     {
/* 267 */       return false;
/*     */     }
/*     */ 
/* 271 */     if (chargeLevel(var1) >= 1)
/*     */     {
/* 273 */       cleanDroplist(var1);
/* 274 */       int var8 = var3.getTypeId(var4, var5, var6);
/*     */ 
/* 276 */       if (var8 == Block.GRAVEL.id)
/*     */       {
/* 278 */         startSearch(var3, var2, var1, var8, var4, var5, var6, false);
/* 279 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 283 */     if (chargeLevel(var1) <= 0)
/*     */     {
/* 285 */       return false;
/*     */     }
/*     */ 
/* 289 */     boolean var19 = true;
/* 290 */     cleanDroplist(var1);
/* 291 */     var2.C_();
/* 292 */     var3.makeSound(var2, "flash", 0.8F, 1.5F);
/*     */ 
/* 294 */     for (int var9 = -chargeLevel(var1); var9 <= chargeLevel(var1); var9++)
/*     */     {
/* 296 */       for (int var10 = -chargeLevel(var1); var10 <= chargeLevel(var1); var10++)
/*     */       {
/* 298 */         int var11 = var4 + var9;
/* 299 */         int var13 = var6 + var10;
/*     */ 
/* 301 */         if (var7 == 2)
/*     */         {
/* 303 */           var13 += chargeLevel(var1);
/*     */         }
/* 305 */         else if (var7 == 3)
/*     */         {
/* 307 */           var13 -= chargeLevel(var1);
/*     */         }
/* 309 */         else if (var7 == 4)
/*     */         {
/* 311 */           var11 += chargeLevel(var1);
/*     */         }
/* 313 */         else if (var7 == 5)
/*     */         {
/* 315 */           var11 -= chargeLevel(var1);
/*     */         }
/*     */ 
/* 318 */         int var14 = var3.getTypeId(var11, var5, var13);
/* 319 */         int var15 = var3.getData(var11, var5, var13);
/*     */ 
/* 321 */         if (canBreak(var14, var15))
/*     */         {
/* 323 */           if (getFuelRemaining(var1) < 1)
/*     */           {
/* 325 */             if ((var9 == chargeLevel(var1)) && (var10 == chargeLevel(var1)))
/*     */             {
/* 327 */               ConsumeReagent(var1, var2, var19);
/* 328 */               var19 = false;
/*     */             }
/*     */             else
/*     */             {
/* 332 */               ConsumeReagent(var1, var2, false);
/*     */             }
/*     */           }
/*     */ 
/* 336 */           if (getFuelRemaining(var1) > 0)
/*     */           {
/* 338 */             ArrayList var16 = Block.byId[var14].getBlockDropped(var3, var11, var5, var13, var15, 0);
/* 339 */             Iterator var17 = var16.iterator();
/*     */ 
/* 341 */             while (var17.hasNext())
/*     */             {
/* 343 */               ItemStack var18 = (ItemStack)var17.next();
/* 344 */               addToDroplist(var1, var18);
/*     */             }
/*     */ 
/* 347 */             var3.setTypeId(var11, var5, var13, 0);
/*     */ 
/* 349 */             if (var3.random.nextInt(8) == 0)
/*     */             {
/* 351 */               var3.a("largesmoke", var11, var5, var13, 0.0D, 0.0D, 0.0D);
/*     */             }
/*     */ 
/* 354 */             if (var3.random.nextInt(8) == 0)
/*     */             {
/* 356 */               var3.a("explode", var11, var5, var13, 0.0D, 0.0D, 0.0D);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 363 */     ejectDropList(var3, var1, var4, var5, var6);
/* 364 */     return true;
/*     */   }
/*     */ 
/*     */   public void startSearch(World var1, EntityHuman var2, ItemStack var3, int var4, int var5, int var6, int var7, boolean var8)
/*     */   {
/* 371 */     var1.makeSound(var2, "flash", 0.8F, 1.5F);
/*     */ 
/* 373 */     if (var8)
/*     */     {
/* 375 */       var2.C_();
/*     */     }
/*     */ 
/* 378 */     doBreakShovel(var1, var2, var3, var4, var5, var6, var7);
/*     */   }
/*     */ 
/*     */   public void doBreakShovel(World var1, EntityHuman var2, ItemStack var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 383 */     if (getFuelRemaining(var3) < 1)
/*     */     {
/* 385 */       ConsumeReagent(var3, var2, false);
/*     */     }
/*     */ 
/* 388 */     if (getFuelRemaining(var3) > 0)
/*     */     {
/* 390 */       int var8 = var1.getData(var5, var6, var7);
/* 391 */       ArrayList var9 = Block.byId[var4].getBlockDropped(var1, var5, var6, var7, var8, 0);
/* 392 */       Iterator var10 = var9.iterator();
/*     */ 
/* 394 */       while (var10.hasNext())
/*     */       {
/* 396 */         ItemStack var11 = (ItemStack)var10.next();
/* 397 */         addToDroplist(var3, var11);
/*     */       }
/*     */ 
/* 400 */       var1.setTypeId(var5, var6, var7, 0);
/* 401 */       setShort(var3, "fuelRemaining", getFuelRemaining(var3) - 1);
/*     */ 
/* 403 */       for (int var14 = -1; var14 <= 1; var14++)
/*     */       {
/* 405 */         for (int var13 = -1; var13 <= 1; var13++)
/*     */         {
/* 407 */           for (int var12 = -1; var12 <= 1; var12++)
/*     */           {
/* 409 */             if (var1.getTypeId(var5 + var14, var6 + var13, var7 + var12) == var4)
/*     */             {
/* 411 */               doBreakShovelAdd(var1, var2, var3, var4, var5 + var14, var6 + var13, var7 + var12);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 417 */       if (var1.random.nextInt(8) == 0)
/*     */       {
/* 419 */         var1.a("largesmoke", var5, var6 + 1, var7, 0.0D, 0.0D, 0.0D);
/*     */       }
/*     */ 
/* 422 */       if (var1.random.nextInt(8) == 0)
/*     */       {
/* 424 */         var1.a("explode", var5, var6 + 1, var7, 0.0D, 0.0D, 0.0D);
/*     */       }
/*     */ 
/* 427 */       ejectDropList(var1, var3, var5, var6, var7);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doBreakShovelAdd(World var1, EntityHuman var2, ItemStack var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 433 */     if (getFuelRemaining(var3) < 1)
/*     */     {
/* 435 */       ConsumeReagent(var3, var2, false);
/*     */     }
/*     */ 
/* 438 */     if (getFuelRemaining(var3) > 0)
/*     */     {
/* 440 */       int var8 = var1.getData(var5, var6, var7);
/* 441 */       ArrayList var9 = Block.byId[var4].getBlockDropped(var1, var5, var6, var7, var8, 0);
/* 442 */       Iterator var10 = var9.iterator();
/*     */ 
/* 444 */       while (var10.hasNext())
/*     */       {
/* 446 */         ItemStack var11 = (ItemStack)var10.next();
/* 447 */         addToDroplist(var3, var11);
/*     */       }
/*     */ 
/* 450 */       var1.setTypeId(var5, var6, var7, 0);
/* 451 */       setShort(var3, "fuelRemaining", getFuelRemaining(var3) - 1);
/*     */ 
/* 453 */       for (int var14 = -1; var14 <= 1; var14++)
/*     */       {
/* 455 */         for (int var13 = -1; var13 <= 1; var13++)
/*     */         {
/* 457 */           for (int var12 = -1; var12 <= 1; var12++)
/*     */           {
/* 459 */             if (var1.getTypeId(var5 + var14, var6 + var13, var7 + var12) == var4)
/*     */             {
/* 461 */               doBreakShovelAdd(var1, var2, var3, var4, var5 + var14, var6 + var13, var7 + var12);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 467 */       if (var1.random.nextInt(8) == 0)
/*     */       {
/* 469 */         var1.a("largesmoke", var5, var6 + 1, var7, 0.0D, 0.0D, 0.0D);
/*     */       }
/*     */ 
/* 472 */       if (var1.random.nextInt(8) == 0)
/*     */       {
/* 474 */         var1.a("explode", var5, var6 + 1, var7, 0.0D, 0.0D, 0.0D);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doAlternate(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 481 */     EEBase.updateToolMode(var3);
/*     */   }
/*     */ 
/*     */   public void doToggle(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemRedSpade
 * JD-Core Version:    0.6.2
 */