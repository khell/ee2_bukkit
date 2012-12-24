/*     */ package ee;
/*     */ 
/*     */ import ee.core.GuiIds;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.BlockDeadBush;
/*     */ import net.minecraft.server.BlockFlower;
/*     */ import net.minecraft.server.BlockGrass;
/*     */ import net.minecraft.server.BlockLeaves;
/*     */ import net.minecraft.server.BlockLongGrass;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EnumMovingObjectType;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.Material;
/*     */ import net.minecraft.server.MathHelper;
/*     */ import net.minecraft.server.MovingObjectPosition;
/*     */ import net.minecraft.server.Vec3D;
/*     */ import net.minecraft.server.World;
/*     */ import net.minecraft.server.WorldProvider;
/*     */ import net.minecraft.server.mod_EE;
/*     */ 
/*     */ public class ItemPhilosopherStone extends ItemEECharged
/*     */ {
/*     */   public ItemPhilosopherStone(int var1)
/*     */   {
/*  22 */     super(var1, 4);
/*     */   }
/*     */ 
/*     */   public void doExtra(World var1, ItemStack var2, EntityHuman var3)
/*     */   {
/*  27 */     var3.openGui(mod_EE.getInstance(), GuiIds.PORT_CRAFTING, var1, (int)var3.locX, (int)var3.locY, (int)var3.locZ);
/*     */   }
/*     */ 
/*     */   public void a(ItemStack var1, World var2, Entity var3, int var4, boolean var5)
/*     */   {
/*  36 */     if (cooldown(var1) > 0)
/*     */     {
/*  38 */       setCooldown(var1, cooldown(var1) - 1);
/*     */     }
/*     */ 
/*  41 */     super.a(var1, var2, var3, var4, var5);
/*     */   }
/*     */ 
/*     */   private void setCooldown(ItemStack var1, int var2)
/*     */   {
/*  46 */     setShort(var1, "cooldown", var2);
/*     */   }
/*     */ 
/*     */   private int cooldown(ItemStack var1)
/*     */   {
/*  51 */     return getShort(var1, "cooldown");
/*     */   }
/*     */ 
/*     */   public void doRelease(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*  56 */     var3.C_();
/*  57 */     var2.makeSound(var3, "transmute", 0.6F, 1.0F);
/*  58 */     var2.addEntity(new EntityPhilosopherEssence(var2, var3, chargeLevel(var1)));
/*     */   }
/*     */ 
/*     */   public void doAlternate(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*  63 */     doExtra(var2, var1, var3);
/*     */   }
/*     */ 
/*     */   public void doLeftClick(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doTransmute(World var1, int var2, int var3, int var4, int var5, EntityHuman var6) {
/*  70 */     int var7 = var1.getTypeId(var2, var3, var4);
/*     */ 
/*  72 */     if (var7 != var5)
/*     */     {
/*  74 */       if (((var5 == Block.DIRT.id) && (var7 != Block.DIRT.id) && (var7 != Block.GRASS.id)) || ((var5 == Block.GRASS.id) && (var7 != Block.DIRT.id) && (var7 != Block.GRASS.id)))
/*     */       {
/*  76 */         return;
/*     */       }
/*     */ 
/*  79 */       if ((var5 != Block.DIRT.id) && (var5 != Block.GRASS.id))
/*     */       {
/*  81 */         return;
/*     */       }
/*     */     }
/*     */ 
/*  85 */     int var8 = var1.getData(var2, var3, var4);
/*  86 */     int var9 = var1.getTypeId(var2, var3 + 1, var4);
/*  87 */     int var10 = var1.getData(var2, var3 + 1, var4);
/*  88 */     var1.getMaterial(var2, var3, var4);
/*     */ 
/*  90 */     if ((var7 != Block.DIRT.id) && (var7 != Block.GRASS.id))
/*     */     {
/*  92 */       if (var7 == Block.NETHERRACK.id)
/*     */       {
/*  94 */         var1.setTypeId(var2, var3, var4, Block.COBBLESTONE.id);
/*     */ 
/*  96 */         if (var1.random.nextInt(8) == 0)
/*     */         {
/*  98 */           var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
/*     */         }
/*     */       }
/* 101 */       else if (var7 == Block.GLASS.id)
/*     */       {
/* 103 */         if (var6.isSneaking())
/*     */         {
/* 105 */           var1.setTypeId(var2, var3, var4, Block.SAND.id);
/*     */         }
/*     */ 
/* 108 */         if (var1.random.nextInt(8) == 0)
/*     */         {
/* 110 */           var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
/*     */         }
/*     */       }
/* 113 */       else if (var7 == Block.COBBLESTONE.id)
/*     */       {
/* 115 */         if (var1.worldProvider.d)
/*     */         {
/* 117 */           var1.setTypeId(var2, var3, var4, Block.NETHERRACK.id);
/*     */         }
/* 119 */         else if (var6.isSneaking())
/*     */         {
/* 121 */           if (var9 == 0)
/*     */           {
/* 123 */             var1.setTypeId(var2, var3, var4, Block.GRASS.id);
/*     */           }
/*     */           else
/*     */           {
/* 127 */             var1.setTypeId(var2, var3, var4, Block.DIRT.id);
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 132 */           var1.setTypeId(var2, var3, var4, Block.STONE.id);
/*     */         }
/*     */ 
/* 135 */         if (var1.random.nextInt(8) == 0)
/*     */         {
/* 137 */           var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
/*     */         }
/*     */       }
/* 140 */       else if (var7 == Block.SAND.id)
/*     */       {
/* 142 */         if (var6.isSneaking())
/*     */         {
/* 144 */           var1.setTypeId(var2, var3, var4, Block.COBBLESTONE.id);
/*     */         }
/* 146 */         else if ((var9 == Block.DEAD_BUSH.id) && (var10 == 0))
/*     */         {
/* 148 */           var1.setRawTypeIdAndData(var2, var3 + 1, var4, Block.LONG_GRASS.id, 1);
/* 149 */           var1.setTypeId(var2, var3, var4, Block.GRASS.id);
/*     */         }
/* 151 */         else if (var9 == 0)
/*     */         {
/* 153 */           var1.setTypeId(var2, var3, var4, Block.GRASS.id);
/*     */         }
/*     */         else
/*     */         {
/* 157 */           var1.setTypeId(var2, var3, var4, Block.DIRT.id);
/*     */         }
/*     */ 
/* 160 */         if (var1.random.nextInt(8) == 0)
/*     */         {
/* 162 */           var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
/*     */         }
/*     */       }
/* 165 */       else if (var7 == Block.SANDSTONE.id)
/*     */       {
/* 167 */         var1.setTypeId(var2, var3, var4, Block.GRAVEL.id);
/*     */ 
/* 169 */         if (var1.random.nextInt(8) == 0)
/*     */         {
/* 171 */           var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
/*     */         }
/*     */       }
/* 174 */       else if ((var7 == Block.DEAD_BUSH.id) && (var8 == 0))
/*     */       {
/* 176 */         var1.setRawTypeIdAndData(var2, var3, var4, Block.LONG_GRASS.id, 1);
/* 177 */         var1.setTypeId(var2, var3 - 1, var4, Block.GRASS.id);
/*     */ 
/* 179 */         if (var1.random.nextInt(8) == 0)
/*     */         {
/* 181 */           var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
/*     */         }
/*     */       }
/* 184 */       else if ((var7 == Block.LONG_GRASS.id) && (var8 == 1))
/*     */       {
/* 186 */         var1.setRawTypeIdAndData(var2, var3, var4, Block.DEAD_BUSH.id, 0);
/* 187 */         var1.setTypeId(var2, var3 - 1, var4, Block.SAND.id);
/*     */ 
/* 189 */         if (var1.random.nextInt(8) == 0)
/*     */         {
/* 191 */           var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
/*     */         }
/*     */       }
/* 194 */       else if (var7 == Block.GRAVEL.id)
/*     */       {
/* 196 */         var1.setTypeId(var2, var3, var4, Block.SANDSTONE.id);
/*     */ 
/* 198 */         if (var1.random.nextInt(8) == 0)
/*     */         {
/* 200 */           var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
/*     */         }
/*     */       }
/* 203 */       else if (var7 == Block.SAPLING.id)
/*     */       {
/* 205 */         int var12 = 0;
/*     */ 
/* 207 */         if ((var8 & 0x3) != 2)
/*     */         {
/* 209 */           var12++;
/*     */         }
/*     */ 
/* 212 */         var1.setTypeIdAndData(var2, var3, var4, Block.SAPLING.id, var12);
/*     */ 
/* 214 */         if (var1.random.nextInt(8) == 0)
/*     */         {
/* 216 */           var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
/*     */         }
/*     */       }
/* 219 */       else if (var7 == Block.STONE.id)
/*     */       {
/* 221 */         if (var6.isSneaking())
/*     */         {
/* 223 */           if (var9 == 0)
/*     */           {
/* 225 */             var1.setTypeId(var2, var3, var4, Block.GRASS.id);
/*     */           }
/*     */           else
/*     */           {
/* 229 */             var1.setTypeId(var2, var3, var4, Block.DIRT.id);
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 234 */           var1.setTypeId(var2, var3, var4, Block.COBBLESTONE.id);
/*     */         }
/*     */ 
/* 237 */         if (var1.random.nextInt(8) == 0)
/*     */         {
/* 239 */           var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
/*     */         }
/*     */       }
/* 242 */       else if (var7 == Block.PUMPKIN.id)
/*     */       {
/* 244 */         var1.setTypeId(var2, var3, var4, Block.MELON.id);
/*     */ 
/* 246 */         if (var1.random.nextInt(8) == 0)
/*     */         {
/* 248 */           var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
/*     */         }
/*     */       }
/* 251 */       else if (var7 == Block.MELON.id)
/*     */       {
/* 253 */         var1.setTypeId(var2, var3, var4, Block.PUMPKIN.id);
/*     */ 
/* 255 */         if (var1.random.nextInt(8) == 0)
/*     */         {
/* 257 */           var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
/*     */         }
/*     */       }
/* 260 */       else if (var7 == BlockFlower.RED_ROSE.id)
/*     */       {
/* 262 */         var1.setTypeId(var2, var3, var4, BlockFlower.YELLOW_FLOWER.id);
/*     */ 
/* 264 */         if (var1.random.nextInt(8) == 0)
/*     */         {
/* 266 */           var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
/*     */         }
/*     */       }
/* 269 */       else if (var7 == BlockFlower.YELLOW_FLOWER.id)
/*     */       {
/* 271 */         var1.setTypeId(var2, var3, var4, BlockFlower.RED_ROSE.id);
/*     */ 
/* 273 */         if (var1.random.nextInt(8) == 0)
/*     */         {
/* 275 */           var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
/*     */         }
/*     */       }
/* 278 */       else if (var7 == BlockFlower.RED_MUSHROOM.id)
/*     */       {
/* 280 */         var1.setTypeId(var2, var3, var4, BlockFlower.BROWN_MUSHROOM.id);
/*     */ 
/* 282 */         if (var1.random.nextInt(8) == 0)
/*     */         {
/* 284 */           var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
/*     */         }
/*     */       }
/* 287 */       else if (var7 == BlockFlower.BROWN_MUSHROOM.id)
/*     */       {
/* 289 */         var1.setTypeId(var2, var3, var4, BlockFlower.RED_MUSHROOM.id);
/*     */ 
/* 291 */         if (var1.random.nextInt(8) == 0)
/*     */         {
/* 293 */           var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 299 */       if (var6.isSneaking())
/*     */       {
/* 301 */         var1.setTypeId(var2, var3, var4, Block.COBBLESTONE.id);
/*     */       }
/*     */       else
/*     */       {
/* 305 */         if ((var9 == Block.LONG_GRASS.id) && (var10 == 1))
/*     */         {
/* 307 */           var1.setRawTypeIdAndData(var2, var3 + 1, var4, Block.DEAD_BUSH.id, 0);
/*     */         }
/*     */ 
/* 310 */         var1.setTypeId(var2, var3, var4, Block.SAND.id);
/*     */       }
/*     */ 
/* 313 */       if (var1.random.nextInt(8) == 0)
/*     */       {
/* 315 */         var1.a("largesmoke", var2, var3 + 1, var4, 0.0D, 0.0D, 0.0D);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 326 */     if (EEProxy.isClient(var3))
/*     */     {
/* 328 */       return false;
/*     */     }
/* 330 */     if (cooldown(var1) > 0)
/*     */     {
/* 332 */       return false;
/*     */     }
/*     */ 
/* 336 */     setCooldown(var1, 10);
/* 337 */     var2.C_();
/* 338 */     var3.makeSound(var2, "transmute", 0.6F, 1.0F);
/*     */ 
/* 340 */     if ((var3.getTypeId(var4, var5, var6) == Block.SNOW.id) && (var7 == 1))
/*     */     {
/* 342 */       var5--;
/*     */     }
/*     */ 
/* 345 */     int var8 = var3.getTypeId(var4, var5, var6);
/*     */ 
/* 347 */     if ((var8 != Block.LOG.id) && (var8 != Block.LEAVES.id))
/*     */     {
/* 349 */       int var9 = chargeLevel(var1);
/*     */ 
/* 357 */       if (getMode(var1) == 0)
/*     */       {
/* 359 */         for (int var10 = -var9 * (var7 == 4 ? 0 : var7 == 5 ? 2 : 1); var10 <= var9 * (var7 == 5 ? 0 : var7 == 4 ? 2 : 1); var10++)
/*     */         {
/* 361 */           for (int var11 = -var9 * (var7 == 0 ? 0 : var7 == 1 ? 2 : 1); var11 <= var9 * (var7 == 1 ? 0 : var7 == 0 ? 2 : 1); var11++)
/*     */           {
/* 363 */             for (int var12 = -var9 * (var7 == 2 ? 0 : var7 == 3 ? 2 : 1); var12 <= var9 * (var7 == 3 ? 0 : var7 == 2 ? 2 : 1); var12++)
/*     */             {
/* 365 */               int var13 = var4 + var10;
/* 366 */               int var14 = var5 + var11;
/* 367 */               int var15 = var6 + var12;
/* 368 */               doTransmute(var3, var13, var14, var15, var8, var2);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 373 */       else if (getMode(var1) == 1)
/*     */       {
/* 375 */         for (int var10 = -1 * (var7 == 4 ? 0 : var7 == 5 ? var9 * var9 : 1); var10 <= 1 * (var7 == 5 ? 0 : var7 == 4 ? var9 * var9 : 1); var10++)
/*     */         {
/* 377 */           for (int var11 = -1 * (var7 == 0 ? 0 : var7 == 1 ? var9 * var9 : 1); var11 <= 1 * (var7 == 1 ? 0 : var7 == 0 ? var9 * var9 : 1); var11++)
/*     */           {
/* 379 */             for (int var12 = -1 * (var7 == 2 ? 0 : var7 == 3 ? var9 * var9 : 1); var12 <= 1 * (var7 == 3 ? 0 : var7 == 2 ? var9 * var9 : 1); var12++)
/*     */             {
/* 381 */               int var13 = var4 + var10;
/* 382 */               int var14 = var5 + var11;
/* 383 */               int var15 = var6 + var12;
/* 384 */               doTransmute(var3, var13, var14, var15, var8, var2);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 389 */       else if (getMode(var1) == 2)
/*     */       {
/* 391 */         for (int var10 = -1 * ((var7 != 4) && (var7 != 5) ? var9 : 0); var10 <= 1 * ((var7 != 4) && (var7 != 5) ? var9 : 0); var10++)
/*     */         {
/* 393 */           for (int var11 = -1 * ((var7 != 0) && (var7 != 1) ? var9 : 0); var11 <= 1 * ((var7 != 0) && (var7 != 1) ? var9 : 0); var11++)
/*     */           {
/* 395 */             for (int var12 = -1 * ((var7 != 2) && (var7 != 3) ? var9 : 0); var12 <= 1 * ((var7 != 2) && (var7 != 3) ? var9 : 0); var12++)
/*     */             {
/* 397 */               int var13 = var4 + var10;
/* 398 */               int var14 = var5 + var11;
/* 399 */               int var15 = var6 + var12;
/* 400 */               doTransmute(var3, var13, var14, var15, var8, var2);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 408 */       doTreeTransmute(var1, var2, var3, var4, var5, var6);
/*     */     }
/*     */ 
/* 411 */     return false;
/*     */   }
/*     */ 
/*     */   private void doTreeTransmute(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6)
/*     */   {
/* 417 */     int var7 = var3.getData(var4, var5, var6) & 0x3;
/* 418 */     byte var8 = 0;
/*     */ 
/* 420 */     if (var7 == 0)
/*     */     {
/* 422 */       var8 = 1;
/*     */     }
/* 424 */     else if (var7 == 1)
/*     */     {
/* 426 */       var8 = 2;
/*     */     }
/* 428 */     else if (var7 == 2)
/*     */     {
/* 430 */       var8 = 0;
/*     */     }
/*     */ 
/* 433 */     for (int var9 = -1; var9 <= 1; var9++)
/*     */     {
/* 435 */       for (int var10 = -1; var10 <= 1; var10++)
/*     */       {
/* 437 */         for (int var11 = -1; var11 <= 1; var11++)
/*     */         {
/* 439 */           if ((var9 == 0) && (var10 == 0) && (var11 == 0))
/*     */           {
/* 441 */             int var12 = var4 + var9;
/* 442 */             int var13 = var5 + var10;
/* 443 */             int var14 = var6 + var11;
/* 444 */             int var15 = var3.getTypeId(var12, var13, var14);
/* 445 */             int var16 = var3.getData(var12, var13, var14) & 0x3;
/*     */ 
/* 447 */             if ((var16 == var7) && ((var15 == Block.LOG.id) || (var15 == Block.LEAVES.id)))
/*     */             {
/* 449 */               var3.setData(var12, var13, var14, var8);
/* 450 */               doTreeSearch(var3, var12, var13, var14, var7, var8);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void doTreeSearch(World var1, int var2, int var3, int var4, int var5, int var6)
/*     */   {
/* 460 */     for (int var7 = -1; var7 <= 1; var7++)
/*     */     {
/* 462 */       for (int var8 = -1; var8 <= 1; var8++)
/*     */       {
/* 464 */         for (int var9 = -1; var9 <= 1; var9++)
/*     */         {
/* 466 */           if ((var7 == 0) && (var8 == 0) && (var9 == 0))
/*     */           {
/* 468 */             int var10 = var2 + var7;
/* 469 */             int var11 = var3 + var8;
/* 470 */             int var12 = var4 + var9;
/* 471 */             int var13 = var1.getTypeId(var10, var11, var12);
/* 472 */             int var14 = var1.getData(var10, var11, var12) & 0x3;
/*     */ 
/* 474 */             if ((var14 == var5) && ((var13 == Block.LOG.id) || (var13 == Block.LEAVES.id)))
/*     */             {
/* 476 */               var1.setData(var10, var11, var12, var6);
/* 477 */               doTreeSearch(var1, var10, var11, var12, var5, var6);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 490 */     if (EEProxy.isClient(var2))
/*     */     {
/* 492 */       return var1;
/*     */     }
/* 494 */     if (cooldown(var1) > 0)
/*     */     {
/* 496 */       return var1;
/*     */     }
/*     */ 
/* 500 */     setCooldown(var1, 10);
/* 501 */     float var4 = 1.0F;
/* 502 */     float var5 = var3.lastPitch + (var3.pitch - var3.lastPitch) * var4;
/* 503 */     float var6 = var3.lastYaw + (var3.yaw - var3.lastYaw) * var4;
/* 504 */     double var7 = var3.lastX + (var3.locX - var3.lastX) * var4;
/* 505 */     double var9 = var3.lastY + (var3.locY - var3.lastY) * var4 + 1.62D - var3.height;
/* 506 */     double var11 = var3.lastZ + (var3.locZ - var3.lastZ) * var4;
/* 507 */     Vec3D var13 = Vec3D.create(var7, var9, var11);
/* 508 */     float var14 = MathHelper.cos(-var6 * 0.01745329F - 3.141593F);
/* 509 */     float var15 = MathHelper.sin(-var6 * 0.01745329F - 3.141593F);
/* 510 */     float var16 = -MathHelper.cos(-var5 * 0.01745329F);
/* 511 */     float var17 = MathHelper.sin(-var5 * 0.01745329F);
/* 512 */     float var18 = var15 * var16;
/* 513 */     float var20 = var14 * var16;
/* 514 */     double var21 = 5.0D;
/* 515 */     Vec3D var23 = var13.add(var18 * var21, var17 * var21, var20 * var21);
/* 516 */     MovingObjectPosition var24 = var2.rayTrace(var13, var23, true);
/*     */ 
/* 518 */     if (var24 == null)
/*     */     {
/* 520 */       return var1;
/*     */     }
/*     */ 
/* 524 */     if (var24.type == EnumMovingObjectType.TILE)
/*     */     {
/* 526 */       int var25 = var24.b;
/* 527 */       int var26 = var24.c;
/* 528 */       int var27 = var24.d;
/* 529 */       Material var28 = var2.getMaterial(var25, var26, var27);
/*     */ 
/* 531 */       if ((var28 != Material.LAVA) && (var28 != Material.WATER))
/*     */       {
/* 533 */         return var1;
/*     */       }
/*     */ 
/* 536 */       for (int var29 = -1 * chargeLevel(var1); var29 <= chargeLevel(var1); var29++)
/*     */       {
/* 538 */         for (int var30 = -1 * chargeLevel(var1); var30 <= chargeLevel(var1); var30++)
/*     */         {
/* 540 */           for (int var31 = -1 * chargeLevel(var1); var31 <= chargeLevel(var1); var31++)
/*     */           {
/* 542 */             int var32 = var25 + var29;
/* 543 */             int var33 = var26 + var30;
/* 544 */             int var34 = var27 + var31;
/* 545 */             Material var35 = var2.getMaterial(var32, var33, var34);
/*     */ 
/* 547 */             if (var35 == var28)
/*     */             {
/* 549 */               int var36 = var2.getData(var32, var33, var34);
/*     */ 
/* 551 */               if (var35 == Material.WATER)
/*     */               {
/* 553 */                 if (var2.getTypeId(var32, var33 + 1, var34) == 0)
/*     */                 {
/* 555 */                   var2.setTypeId(var32, var33, var34, Block.ICE.id);
/*     */ 
/* 557 */                   if (var2.random.nextInt(8) == 0)
/*     */                   {
/* 559 */                     var2.a("largesmoke", var32, var33 + 1, var34, 0.0D, 0.0D, 0.0D);
/*     */                   }
/*     */                 }
/*     */               }
/* 563 */               else if ((var35 == Material.LAVA) && (var36 == 0) && (var2.getTypeId(var32, var33 + 1, var34) == 0))
/*     */               {
/* 565 */                 var2.setTypeId(var32, var33, var34, Block.OBSIDIAN.id);
/*     */ 
/* 567 */                 if (var2.random.nextInt(8) == 0)
/*     */                 {
/* 569 */                   var2.a("largesmoke", var32, var33 + 1, var34, 0.0D, 0.0D, 0.0D);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 578 */     return var1;
/*     */   }
/*     */ 
/*     */   public void doToggle(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 585 */     changeMode(var1, var3);
/*     */   }
/*     */ 
/*     */   public int getMode(ItemStack var1)
/*     */   {
/* 590 */     return getInteger(var1, "transmode");
/*     */   }
/*     */ 
/*     */   public void setMode(ItemStack var1, int var2)
/*     */   {
/* 595 */     setInteger(var1, "transmode", var2);
/*     */   }
/*     */ 
/*     */   public void changeMode(ItemStack var1, EntityHuman var2)
/*     */   {
/* 600 */     if (getMode(var1) == 2)
/*     */     {
/* 602 */       setMode(var1, 0);
/*     */     }
/*     */     else
/*     */     {
/* 606 */       setMode(var1, getMode(var1) + 1);
/*     */     }
/*     */ 
/* 609 */     var2.a("Philosopher Stone transmuting " + (getMode(var1) == 0 ? "in a cube" : getMode(var1) == 1 ? "in a line" : "in a panel") + ".");
/*     */   }
/*     */ 
/*     */   public boolean e(ItemStack var1)
/*     */   {
/* 618 */     return false;
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemPhilosopherStone
 * JD-Core Version:    0.6.2
 */