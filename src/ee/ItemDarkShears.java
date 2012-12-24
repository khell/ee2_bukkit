/*     */ package ee;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.BlockDeadBush;
/*     */ import net.minecraft.server.BlockFlower;
/*     */ import net.minecraft.server.BlockLeaves;
/*     */ import net.minecraft.server.BlockLongGrass;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityCow;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityItem;
/*     */ import net.minecraft.server.EntityLiving;
/*     */ import net.minecraft.server.EntityMushroomCow;
/*     */ import net.minecraft.server.EntitySheep;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class ItemDarkShears extends ItemDarkTool
/*     */ {
/*  20 */   private static Block[] blocksEffectiveAgainst = { Block.LEAVES, Block.WEB, Block.WOOL };
/*     */   private static boolean leafHit;
/*     */   private static boolean vineHit;
/*     */ 
/*     */   public ItemDarkShears(int var1)
/*     */   {
/*  26 */     super(var1, 2, 9, blocksEffectiveAgainst);
/*     */   }
/*     */ 
/*     */   public boolean a(ItemStack var1, int var2, int var3, int var4, int var5, EntityLiving var6)
/*     */   {
/*  31 */     boolean var7 = false;
/*     */ 
/*  33 */     if ((!EEMaps.isLeaf(var2)) && (var2 != Block.WEB.id) && (var2 != Block.VINE.id) && (var2 != BlockFlower.LONG_GRASS.id) && (var2 != BlockFlower.DEAD_BUSH.id))
/*     */     {
/*  35 */       var7 = true;
/*     */     }
/*     */ 
/*  38 */     if (!var7)
/*     */     {
/*  40 */       EEProxy.dropBlockAsItemStack(Block.byId[var2], var6, var3, var4, var5, new ItemStack(var2, 1, var2 == Block.LEAVES.id ? ((ItemEECharged)var1.getItem()).getShort(var1, "lastMeta") & 0x3 : ((ItemEECharged)var1.getItem()).getShort(var1, "lastMeta")));
/*     */     }
/*     */ 
/*  43 */     return super.a(var1, var2, var3, var4, var5, var6);
/*     */   }
/*     */ 
/*     */   public boolean canDestroySpecialBlock(Block var1)
/*     */   {
/*  51 */     return var1.id == Block.WEB.id;
/*     */   }
/*     */ 
/*     */   public float getDestroySpeed(ItemStack var1, Block var2)
/*     */   {
/*  60 */     return (var2.id != Block.VINE.id) && (var2.id != Block.LEAVES.id) && (var2.id != Block.WEB.id) ? super.getDestroySpeed(var1, var2) : var2.id == Block.WOOL.id ? 5.0F : 15.0F;
/*     */   }
/*     */ 
/*     */   public void doBreak(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*  65 */     if (chargeLevel(var1) > 0)
/*     */     {
/*  67 */       int var4 = (int)EEBase.playerX(var3);
/*  68 */       int var5 = (int)EEBase.playerY(var3);
/*  69 */       int var6 = (int)EEBase.playerZ(var3);
/*  70 */       cleanDroplist(var1);
/*     */ 
/*  72 */       if (chargeLevel(var1) < 1)
/*     */       {
/*  74 */         return;
/*     */       }
/*     */ 
/*  77 */       var3.C_();
/*  78 */       var2.makeSound(var3, "flash", 0.8F, 1.5F);
/*     */ 
/*  80 */       for (int var7 = -(chargeLevel(var1) + 2); var7 <= chargeLevel(var1) + 2; var7++)
/*     */       {
/*  82 */         for (int var8 = -(chargeLevel(var1) + 2); var8 <= chargeLevel(var1) + 2; var8++)
/*     */         {
/*  84 */           for (int var9 = -(chargeLevel(var1) + 2); var9 <= chargeLevel(var1) + 2; var9++)
/*     */           {
/*  86 */             int var10 = var2.getTypeId(var4 + var7, var5 + var8, var6 + var9);
/*     */ 
/*  88 */             if ((EEMaps.isLeaf(var10)) || (var10 == Block.VINE.id) || (var10 == Block.WEB.id) || (var10 == Block.LONG_GRASS.id) || (var10 == Block.DEAD_BUSH.id))
/*     */             {
/*  90 */               if (getFuelRemaining(var1) < 1)
/*     */               {
/*  92 */                 ConsumeReagent(var1, var3, false);
/*     */               }
/*     */ 
/*  95 */               if (getFuelRemaining(var1) > 0)
/*     */               {
/*  97 */                 int var11 = var2.getData(var4 + var7, var5 + var8, var6 + var9);
/*  98 */                 ItemStack var12 = new ItemStack(var10, 1, var11 & (var10 == Block.LEAVES.id ? 3 : 15));
/*  99 */                 addToDroplist(var1, var12);
/* 100 */                 setShort(var1, "fuelRemaining", getFuelRemaining(var1) - 1);
/* 101 */                 var2.setTypeId(var4 + var7, var5 + var8, var6 + var9, 0);
/*     */ 
/* 103 */                 if (var2.random.nextInt(8) == 0)
/*     */                 {
/* 105 */                   var2.a("largesmoke", var4 + var7, var5 + var8, var6 + var9, 0.0D, 0.0D, 0.0D);
/*     */                 }
/*     */ 
/* 108 */                 if (var2.random.nextInt(8) == 0)
/*     */                 {
/* 110 */                   var2.a("explode", var4 + var7, var5 + var8, var6 + var9, 0.0D, 0.0D, 0.0D);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 118 */       ejectDropList(var2, var1, var4, var5, var6);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 128 */     if (EEProxy.isClient(var3))
/*     */     {
/* 130 */       return false;
/*     */     }
/*     */ 
/* 134 */     if (chargeLevel(var1) > 0)
/*     */     {
/* 136 */       boolean var8 = false;
/* 137 */       cleanDroplist(var1);
/*     */ 
/* 139 */       if (chargeLevel(var1) < 1)
/*     */       {
/* 141 */         return false;
/*     */       }
/*     */ 
/* 144 */       var2.C_();
/* 145 */       var3.makeSound(var2, "flash", 0.8F, 1.5F);
/*     */ 
/* 147 */       for (int var9 = -(chargeLevel(var1) + 2); var9 <= chargeLevel(var1) + 2; var9++)
/*     */       {
/* 149 */         for (int var10 = -(chargeLevel(var1) + 2); var10 <= chargeLevel(var1) + 2; var10++)
/*     */         {
/* 151 */           for (int var11 = -(chargeLevel(var1) + 2); var11 <= chargeLevel(var1) + 2; var11++)
/*     */           {
/* 153 */             int var12 = var3.getTypeId(var4 + var9, var5 + var10, var6 + var11);
/*     */ 
/* 155 */             if (((EEMaps.isLeaf(var12)) || (var12 == Block.VINE.id) || (var12 == Block.WEB.id) || (var12 == Block.LONG_GRASS.id) || (var12 == Block.DEAD_BUSH.id)) && (getFuelRemaining(var1) < 1))
/*     */             {
/* 157 */               int var13 = var3.getData(var4 + var9, var5 + var10, var6 + var11);
/*     */ 
/* 159 */               if ((!EEMaps.isLeaf(var12)) && (var12 != Block.VINE.id) && (var12 != Block.WEB.id) && (var12 != Block.LONG_GRASS.id) && (var12 != Block.DEAD_BUSH.id))
/*     */               {
/* 161 */                 ArrayList var14 = Block.byId[var12].getBlockDropped(var3, var4 + var9, var5 + var10, var6 + var11, var13, 0);
/* 162 */                 Iterator var15 = var14.iterator();
/*     */ 
/* 164 */                 while (var15.hasNext())
/*     */                 {
/* 166 */                   ItemStack var16 = (ItemStack)var15.next();
/* 167 */                   addToDroplist(var1, var16);
/*     */                 }
/*     */               }
/* 170 */               else if (var12 == Block.LEAVES.id)
/*     */               {
/* 172 */                 addToDroplist(var1, new ItemStack(Block.LEAVES.id, 1, var13 & 0x3));
/*     */               }
/*     */               else
/*     */               {
/* 176 */                 addToDroplist(var1, new ItemStack(Block.byId[var12], 1, var13));
/*     */               }
/*     */ 
/* 179 */               setShort(var1, "fuelRemaining", getFuelRemaining(var1) - 1);
/* 180 */               var3.setTypeId(var4 + var9, var5 + var10, var6 + var11, 0);
/*     */ 
/* 182 */               if (var3.random.nextInt(8) == 0)
/*     */               {
/* 184 */                 var3.a("largesmoke", var4 + var9, var5 + var10, var6 + var11, 0.0D, 0.0D, 0.0D);
/*     */               }
/*     */ 
/* 187 */               if (var3.random.nextInt(8) == 0)
/*     */               {
/* 189 */                 var3.a("explode", var4 + var9, var5 + var10, var6 + var11, 0.0D, 0.0D, 0.0D);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 196 */       ejectDropList(var3, var1, var4, var5, var6);
/*     */     }
/*     */ 
/* 199 */     return false;
/*     */   }
/*     */ 
/*     */   public void doShear(ItemStack var1, World var2, EntityHuman var3, Entity var4)
/*     */   {
/* 208 */     if (chargeLevel(var1) > 0)
/*     */     {
/* 210 */       boolean var5 = false;
/* 211 */       int var6 = 0;
/*     */ 
/* 213 */       if (getFuelRemaining(var1) < 10)
/*     */       {
/* 215 */         ConsumeReagent(var1, var3, false);
/*     */       }
/*     */ 
/* 218 */       if (getFuelRemaining(var1) < 10)
/*     */       {
/* 220 */         ConsumeReagent(var1, var3, false);
/*     */       }
/*     */ 
/* 223 */       if (getFuelRemaining(var1) < 10)
/*     */       {
/* 225 */         ConsumeReagent(var1, var3, false);
/*     */       }
/*     */ 
/* 228 */       while ((getFuelRemaining(var1) >= 10) && (var6 < chargeLevel(var1)))
/*     */       {
/* 230 */         setShort(var1, "fuelRemaining", getFuelRemaining(var1) - 10);
/* 231 */         var6++;
/*     */ 
/* 233 */         if (getFuelRemaining(var1) < 10)
/*     */         {
/* 235 */           ConsumeReagent(var1, var3, false);
/*     */         }
/*     */ 
/* 238 */         if (getFuelRemaining(var1) < 10)
/*     */         {
/* 240 */           ConsumeReagent(var1, var3, false);
/*     */         }
/*     */ 
/* 243 */         if (getFuelRemaining(var1) < 10)
/*     */         {
/* 245 */           ConsumeReagent(var1, var3, false);
/*     */         }
/*     */       }
/*     */ 
/* 249 */       if (var6 > 0)
/*     */       {
/* 251 */         var3.C_();
/* 252 */         var2.makeSound(var3, "flash", 0.8F, 1.5F);
/* 253 */         int var7 = 1 * var6;
/*     */ 
/* 259 */         if ((var4 instanceof EntitySheep))
/*     */         {
/* 261 */           if (var2.random.nextInt(100) < var7)
/*     */           {
/* 263 */             EntitySheep var8 = new EntitySheep(var2);
/* 264 */             double var9 = var4.locX - var3.locX;
/* 265 */             double var11 = var4.locZ - var3.locZ;
/*     */ 
/* 267 */             if (var9 < 0.0D)
/*     */             {
/* 269 */               var9 *= -1.0D;
/*     */             }
/*     */ 
/* 272 */             if (var11 < 0.0D)
/*     */             {
/* 274 */               var11 *= -1.0D;
/*     */             }
/*     */ 
/* 277 */             var9 += var4.locX;
/* 278 */             var11 += var4.locZ;
/* 279 */             double var13 = var4.locY;
/*     */ 
/* 281 */             for (int var15 = -5; var15 <= 5; var15++)
/*     */             {
/* 283 */               if ((var2.getTypeId((int)var9, (int)var13 + var15, (int)var11) != 0) && (var2.getTypeId((int)var9, (int)var13 + var15 + 1, (int)var11) == 0))
/*     */               {
/* 285 */                 var8.setPosition(var9, var13 + var15 + 1.0D, var11);
/* 286 */                 var8.setColor(((EntitySheep)var4).getColor());
/* 287 */                 var2.addEntity(var8);
/* 288 */                 break;
/*     */               }
/*     */             }
/*     */           }
/*     */ 
/* 293 */           ((EntitySheep)var4).setSheared(true);
/* 294 */           int var19 = 3 + var2.random.nextInt(2) + chargeLevel(var1) / 8;
/* 295 */           EntityItem var21 = null;
/*     */ 
/* 297 */           for (int var10 = 0; var10 < var19; var10++)
/*     */           {
/* 299 */             var21 = new EntityItem(var2, var3.locX, var3.locY, var3.locZ, new ItemStack(Block.WOOL.id, var19, ((EntitySheep)var4).getColor()));
/*     */           }
/*     */ 
/* 302 */           var2.addEntity(var21);
/*     */         }
/* 304 */         else if ((var4 instanceof EntityMushroomCow))
/*     */         {
/* 306 */           if (var2.random.nextInt(100) < var7)
/*     */           {
/* 308 */             EntityMushroomCow var18 = new EntityMushroomCow(var2);
/* 309 */             double var9 = var4.locX - var3.locX;
/* 310 */             double var11 = var4.locZ - var3.locZ;
/*     */ 
/* 312 */             if (var9 < 0.0D)
/*     */             {
/* 314 */               var9 *= -1.0D;
/*     */             }
/*     */ 
/* 317 */             if (var11 < 0.0D)
/*     */             {
/* 319 */               var11 *= -1.0D;
/*     */             }
/*     */ 
/* 322 */             var9 += var4.locX;
/* 323 */             var11 += var4.locZ;
/* 324 */             double var13 = var4.locY;
/*     */ 
/* 326 */             for (int var15 = -5; var15 <= 5; var15++)
/*     */             {
/* 328 */               if ((var2.getTypeId((int)var9, (int)var13 + var15, (int)var11) != 0) && (var2.getTypeId((int)var9, (int)var13 + var15 + 1, (int)var11) == 0))
/*     */               {
/* 330 */                 var18.setPosition(var9, var13 + var15 + 1.0D, var11);
/* 331 */                 var2.addEntity(var18);
/* 332 */                 break;
/*     */               }
/*     */             }
/*     */           }
/*     */ 
/* 337 */           ((EntityMushroomCow)var4).die();
/* 338 */           EntityCow var20 = new EntityCow(var2);
/* 339 */           var20.setPositionRotation(var4.locX, var4.locY, var4.locZ, var4.yaw, var4.pitch);
/* 340 */           var20.setHealth(((EntityMushroomCow)var4).getHealth());
/* 341 */           var20.V = ((EntityMushroomCow)var4).V;
/* 342 */           var2.addEntity(var20);
/* 343 */           var2.a("largeexplode", var4.locX, var4.locY + var4.length / 2.0F, var4.locZ, 0.0D, 0.0D, 0.0D);
/* 344 */           int var23 = 5 + var2.random.nextInt(2) + chargeLevel(var1) / 8;
/* 345 */           Object var22 = null;
/*     */ 
/* 347 */           for (int var24 = 0; var24 < var23; var24++)
/*     */           {
/* 349 */             new EntityItem(var2, var3.locX, var3.locY, var3.locZ, new ItemStack(Block.RED_MUSHROOM, var23));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 354 */     else if ((var4 instanceof EntitySheep))
/*     */     {
/* 356 */       new EntitySheep(var2);
/* 357 */       ((EntitySheep)var4).setSheared(true);
/* 358 */       int var6 = 3 + var2.random.nextInt(2);
/* 359 */       EntityItem var17 = null;
/*     */ 
/* 361 */       for (int var19 = 0; var19 < var6; var19++)
/*     */       {
/* 363 */         var17 = new EntityItem(var2, var3.locX, var3.locY, var3.locZ, new ItemStack(Block.WOOL.id, var6, ((EntitySheep)var4).getColor()));
/*     */       }
/*     */ 
/* 366 */       var2.addEntity(var17);
/*     */     }
/* 368 */     else if ((var4 instanceof EntityMushroomCow))
/*     */     {
/* 370 */       ((EntityMushroomCow)var4).die();
/* 371 */       EntityCow var16 = new EntityCow(((EntityMushroomCow)var4).world);
/* 372 */       var16.setPositionRotation(((EntityMushroomCow)var4).locX, ((EntityMushroomCow)var4).locY, ((EntityMushroomCow)var4).locZ, ((EntityMushroomCow)var4).yaw, ((EntityMushroomCow)var4).pitch);
/* 373 */       var16.setHealth(((EntityMushroomCow)var4).getHealth());
/* 374 */       var16.V = ((EntityMushroomCow)var4).V;
/* 375 */       ((EntityMushroomCow)var4).world.addEntity(var16);
/* 376 */       ((EntityMushroomCow)var4).world.a("largeexplode", ((EntityMushroomCow)var4).locX, ((EntityMushroomCow)var4).locY + ((EntityMushroomCow)var4).length / 2.0F, ((EntityMushroomCow)var4).locZ, 0.0D, 0.0D, 0.0D);
/*     */ 
/* 378 */       for (int var6 = 0; var6 < 5; var6++)
/*     */       {
/* 380 */         ((EntityMushroomCow)var4).world.addEntity(new EntityItem(((EntityMushroomCow)var4).world, ((EntityMushroomCow)var4).locX, ((EntityMushroomCow)var4).locY + ((EntityMushroomCow)var4).length, ((EntityMushroomCow)var4).locZ, new ItemStack(Block.RED_MUSHROOM)));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int a(Entity var1)
/*     */   {
/* 390 */     return (!(var1 instanceof EntitySheep)) && (!(var1 instanceof EntityMushroomCow)) ? this.weaponDamage : 1;
/*     */   }
/*     */ 
/*     */   public boolean a(ItemStack var1, EntityLiving var2, EntityLiving var3)
/*     */   {
/* 399 */     if ((var3 instanceof EntityHuman))
/*     */     {
/* 401 */       EntityHuman var4 = (EntityHuman)var3;
/*     */ 
/* 403 */       if ((var2 instanceof EntitySheep))
/*     */       {
/* 405 */         if (!((EntitySheep)var2).isSheared())
/*     */         {
/* 407 */           doShear(var1, var4.world, var4, var2);
/*     */         }
/*     */ 
/* 410 */         var2.heal(1);
/*     */       }
/* 412 */       else if ((var2 instanceof EntityMushroomCow))
/*     */       {
/* 414 */         doShear(var1, var4.world, var4, var2);
/* 415 */         var2.heal(1);
/*     */       }
/*     */     }
/*     */ 
/* 419 */     return true;
/*     */   }
/*     */ 
/*     */   public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 427 */     if (EEProxy.isClient(var2))
/*     */     {
/* 429 */       return var1;
/*     */     }
/*     */ 
/* 433 */     doBreak(var1, var2, var3);
/* 434 */     return var1;
/*     */   }
/*     */ 
/*     */   public void doHeld(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void doRelease(ItemStack var1, World var2, EntityHuman var3) {
/* 442 */     doBreak(var1, var2, var3);
/*     */   }
/*     */ 
/*     */   public void doAlternate(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void doLeftClick(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void doToggle(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemDarkShears
 * JD-Core Version:    0.6.2
 */