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
/*     */ public class ItemRedShears extends ItemRedTool
/*     */ {
/*  20 */   private static Block[] blocksEffectiveAgainst = { Block.LEAVES, Block.WEB, Block.WOOL };
/*     */   private static boolean leafHit;
/*     */   private static boolean vineHit;
/*     */ 
/*     */   public ItemRedShears(int var1)
/*     */   {
/*  26 */     super(var1, 3, 12, blocksEffectiveAgainst);
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
/*     */ 
/*  99 */                 if ((!EEMaps.isLeaf(var10)) && (var10 != Block.VINE.id) && (var10 != Block.WEB.id) && (var10 != Block.LONG_GRASS.id) && (var10 != Block.DEAD_BUSH.id))
/*     */                 {
/* 101 */                   ArrayList var12 = Block.byId[var10].getBlockDropped(var2, var4 + var7, var5 + var8, var6 + var9, var11, 0);
/* 102 */                   Iterator var13 = var12.iterator();
/*     */ 
/* 104 */                   while (var13.hasNext())
/*     */                   {
/* 106 */                     ItemStack var14 = (ItemStack)var13.next();
/* 107 */                     addToDroplist(var1, var14);
/*     */                   }
/*     */                 }
/* 110 */                 else if (var10 == Block.LEAVES.id)
/*     */                 {
/* 112 */                   addToDroplist(var1, new ItemStack(Block.LEAVES.id, 1, var11 & 0x3));
/*     */                 }
/*     */                 else
/*     */                 {
/* 116 */                   addToDroplist(var1, new ItemStack(Block.byId[var10], 1, var11));
/*     */                 }
/*     */ 
/* 119 */                 setShort(var1, "fuelRemaining", getFuelRemaining(var1) - 1);
/* 120 */                 var2.setTypeId(var4 + var7, var5 + var8, var6 + var9, 0);
/*     */ 
/* 122 */                 if (var2.random.nextInt(8) == 0)
/*     */                 {
/* 124 */                   var2.a("largesmoke", var4 + var7, var5 + var8, var6 + var9, 0.0D, 0.0D, 0.0D);
/*     */                 }
/*     */ 
/* 127 */                 if (var2.random.nextInt(8) == 0)
/*     */                 {
/* 129 */                   var2.a("explode", var4 + var7, var5 + var8, var6 + var9, 0.0D, 0.0D, 0.0D);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 137 */       ejectDropList(var2, var1, var4, var5, var6);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 147 */     if (EEProxy.isClient(var3))
/*     */     {
/* 149 */       return false;
/*     */     }
/*     */ 
/* 153 */     if (chargeLevel(var1) > 0)
/*     */     {
/* 155 */       boolean var8 = false;
/* 156 */       cleanDroplist(var1);
/*     */ 
/* 158 */       if (chargeLevel(var1) < 1)
/*     */       {
/* 160 */         return false;
/*     */       }
/*     */ 
/* 163 */       var2.C_();
/* 164 */       var3.makeSound(var2, "flash", 0.8F, 1.5F);
/*     */ 
/* 166 */       for (int var9 = -(chargeLevel(var1) + 2); var9 <= chargeLevel(var1) + 2; var9++)
/*     */       {
/* 168 */         for (int var10 = -(chargeLevel(var1) + 2); var10 <= chargeLevel(var1) + 2; var10++)
/*     */         {
/* 170 */           for (int var11 = -(chargeLevel(var1) + 2); var11 <= chargeLevel(var1) + 2; var11++)
/*     */           {
/* 172 */             int var12 = var3.getTypeId(var4 + var9, var5 + var10, var6 + var11);
/*     */ 
/* 174 */             if (((EEMaps.isLeaf(var12)) || (var12 == Block.VINE.id) || (var12 == Block.WEB.id) || (var12 == Block.LONG_GRASS.id) || (var12 == Block.DEAD_BUSH.id)) && (getFuelRemaining(var1) < 1))
/*     */             {
/* 176 */               int var13 = var3.getData(var4 + var9, var5 + var10, var6 + var11);
/* 177 */               ArrayList var14 = Block.byId[var12].getBlockDropped(var3, var4 + var9, var5 + var10, var6 + var11, var13, 0);
/* 178 */               Iterator var15 = var14.iterator();
/*     */ 
/* 180 */               while (var15.hasNext())
/*     */               {
/* 182 */                 ItemStack var16 = (ItemStack)var15.next();
/* 183 */                 addToDroplist(var1, var16);
/*     */               }
/*     */ 
/* 186 */               setShort(var1, "fuelRemaining", getFuelRemaining(var1) - 1);
/* 187 */               var3.setTypeId(var4 + var9, var5 + var10, var6 + var11, 0);
/*     */ 
/* 189 */               if (var3.random.nextInt(8) == 0)
/*     */               {
/* 191 */                 var3.a("largesmoke", var4 + var9, var5 + var10, var6 + var11, 0.0D, 0.0D, 0.0D);
/*     */               }
/*     */ 
/* 194 */               if (var3.random.nextInt(8) == 0)
/*     */               {
/* 196 */                 var3.a("explode", var4 + var9, var5 + var10, var6 + var11, 0.0D, 0.0D, 0.0D);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 203 */       ejectDropList(var3, var1, var4, var5, var6);
/*     */     }
/*     */ 
/* 206 */     return false;
/*     */   }
/*     */ 
/*     */   public void doShear(ItemStack var1, World var2, EntityHuman var3, Entity var4)
/*     */   {
/* 215 */     if (chargeLevel(var1) > 0)
/*     */     {
/* 217 */       boolean var5 = false;
/* 218 */       int var6 = 0;
/*     */ 
/* 220 */       if (getFuelRemaining(var1) < 10)
/*     */       {
/* 222 */         ConsumeReagent(var1, var3, false);
/*     */       }
/*     */ 
/* 225 */       if (getFuelRemaining(var1) < 10)
/*     */       {
/* 227 */         ConsumeReagent(var1, var3, false);
/*     */       }
/*     */ 
/* 230 */       if (getFuelRemaining(var1) < 10)
/*     */       {
/* 232 */         ConsumeReagent(var1, var3, false);
/*     */       }
/*     */ 
/* 235 */       while ((getFuelRemaining(var1) >= 10) && (var6 < chargeLevel(var1)))
/*     */       {
/* 237 */         setShort(var1, "fuelRemaining", getFuelRemaining(var1) - 10);
/* 238 */         var6++;
/*     */ 
/* 240 */         if (getFuelRemaining(var1) < 10)
/*     */         {
/* 242 */           ConsumeReagent(var1, var3, false);
/*     */         }
/*     */ 
/* 245 */         if (getFuelRemaining(var1) < 10)
/*     */         {
/* 247 */           ConsumeReagent(var1, var3, false);
/*     */         }
/*     */ 
/* 250 */         if (getFuelRemaining(var1) < 10)
/*     */         {
/* 252 */           ConsumeReagent(var1, var3, false);
/*     */         }
/*     */       }
/*     */ 
/* 256 */       if (var6 > 0)
/*     */       {
/* 258 */         var3.C_();
/* 259 */         var2.makeSound(var3, "flash", 0.8F, 1.5F);
/* 260 */         int var7 = 2 * var6;
/*     */ 
/* 266 */         if ((var4 instanceof EntitySheep))
/*     */         {
/* 268 */           if (var2.random.nextInt(100) < var7)
/*     */           {
/* 270 */             EntitySheep var8 = new EntitySheep(var2);
/* 271 */             double var9 = var4.locX - var3.locX;
/* 272 */             double var11 = var4.locZ - var3.locZ;
/*     */ 
/* 274 */             if (var9 < 0.0D)
/*     */             {
/* 276 */               var9 *= -1.0D;
/*     */             }
/*     */ 
/* 279 */             if (var11 < 0.0D)
/*     */             {
/* 281 */               var11 *= -1.0D;
/*     */             }
/*     */ 
/* 284 */             var9 += var4.locX;
/* 285 */             var11 += var4.locZ;
/* 286 */             double var13 = var4.locY;
/*     */ 
/* 288 */             for (int var15 = -5; var15 <= 5; var15++)
/*     */             {
/* 290 */               if ((var2.getTypeId((int)var9, (int)var13 + var15, (int)var11) != 0) && (var2.getTypeId((int)var9, (int)var13 + var15 + 1, (int)var11) == 0))
/*     */               {
/* 292 */                 var8.setPosition(var9, var13 + var15 + 1.0D, var11);
/* 293 */                 var8.setColor(((EntitySheep)var4).getColor());
/* 294 */                 var2.addEntity(var8);
/* 295 */                 break;
/*     */               }
/*     */             }
/*     */           }
/*     */ 
/* 300 */           ((EntitySheep)var4).setSheared(true);
/* 301 */           int var19 = 3 + var2.random.nextInt(2) + chargeLevel(var1) / 4;
/* 302 */           EntityItem var21 = null;
/*     */ 
/* 304 */           for (int var10 = 0; var10 < var19; var10++)
/*     */           {
/* 306 */             var21 = new EntityItem(var2, var3.locX, var3.locY, var3.locZ, new ItemStack(Block.WOOL.id, var19, ((EntitySheep)var4).getColor()));
/*     */           }
/*     */ 
/* 309 */           var2.addEntity(var21);
/*     */         }
/* 311 */         else if ((var4 instanceof EntityMushroomCow))
/*     */         {
/* 313 */           if (var2.random.nextInt(100) < var7)
/*     */           {
/* 315 */             EntityMushroomCow var18 = new EntityMushroomCow(var2);
/* 316 */             double var9 = var4.locX - var3.locX;
/* 317 */             double var11 = var4.locZ - var3.locZ;
/*     */ 
/* 319 */             if (var9 < 0.0D)
/*     */             {
/* 321 */               var9 *= -1.0D;
/*     */             }
/*     */ 
/* 324 */             if (var11 < 0.0D)
/*     */             {
/* 326 */               var11 *= -1.0D;
/*     */             }
/*     */ 
/* 329 */             var9 += var4.locX;
/* 330 */             var11 += var4.locZ;
/* 331 */             double var13 = var4.locY;
/*     */ 
/* 333 */             for (int var15 = -5; var15 <= 5; var15++)
/*     */             {
/* 335 */               if ((var2.getTypeId((int)var9, (int)var13 + var15, (int)var11) != 0) && (var2.getTypeId((int)var9, (int)var13 + var15 + 1, (int)var11) == 0))
/*     */               {
/* 337 */                 var18.setPosition(var9, var13 + var15 + 1.0D, var11);
/* 338 */                 var2.addEntity(var18);
/* 339 */                 break;
/*     */               }
/*     */             }
/*     */           }
/*     */ 
/* 344 */           ((EntityMushroomCow)var4).die();
/* 345 */           EntityCow var20 = new EntityCow(var2);
/* 346 */           var20.setPositionRotation(var4.locX, var4.locY, var4.locZ, var4.yaw, var4.pitch);
/* 347 */           var20.setHealth(((EntityMushroomCow)var4).getHealth());
/* 348 */           var20.V = ((EntityMushroomCow)var4).V;
/* 349 */           var2.addEntity(var20);
/* 350 */           var2.a("largeexplode", var4.locX, var4.locY + var4.length / 2.0F, var4.locZ, 0.0D, 0.0D, 0.0D);
/* 351 */           int var23 = 5 + var2.random.nextInt(2) + chargeLevel(var1) / 4;
/* 352 */           Object var22 = null;
/*     */ 
/* 354 */           for (int var24 = 0; var24 < var23; var24++)
/*     */           {
/* 356 */             new EntityItem(var2, var3.locX, var3.locY, var3.locZ, new ItemStack(Block.RED_MUSHROOM, var23));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 361 */     else if ((var4 instanceof EntitySheep))
/*     */     {
/* 363 */       new EntitySheep(var2);
/* 364 */       ((EntitySheep)var4).setSheared(true);
/* 365 */       int var6 = 3 + var2.random.nextInt(2);
/* 366 */       EntityItem var17 = null;
/*     */ 
/* 368 */       for (int var19 = 0; var19 < var6; var19++)
/*     */       {
/* 370 */         var17 = new EntityItem(var2, var3.locX, var3.locY, var3.locZ, new ItemStack(Block.WOOL.id, var6, ((EntitySheep)var4).getColor()));
/*     */       }
/*     */ 
/* 373 */       var2.addEntity(var17);
/*     */     }
/* 375 */     else if ((var4 instanceof EntityMushroomCow))
/*     */     {
/* 377 */       ((EntityMushroomCow)var4).die();
/* 378 */       EntityCow var16 = new EntityCow(((EntityMushroomCow)var4).world);
/* 379 */       var16.setPositionRotation(((EntityMushroomCow)var4).locX, ((EntityMushroomCow)var4).locY, ((EntityMushroomCow)var4).locZ, ((EntityMushroomCow)var4).yaw, ((EntityMushroomCow)var4).pitch);
/* 380 */       var16.setHealth(((EntityMushroomCow)var4).getHealth());
/* 381 */       var16.V = ((EntityMushroomCow)var4).V;
/* 382 */       ((EntityMushroomCow)var4).world.addEntity(var16);
/* 383 */       ((EntityMushroomCow)var4).world.a("largeexplode", ((EntityMushroomCow)var4).locX, ((EntityMushroomCow)var4).locY + ((EntityMushroomCow)var4).length / 2.0F, ((EntityMushroomCow)var4).locZ, 0.0D, 0.0D, 0.0D);
/*     */ 
/* 385 */       for (int var6 = 0; var6 < 5; var6++)
/*     */       {
/* 387 */         ((EntityMushroomCow)var4).world.addEntity(new EntityItem(((EntityMushroomCow)var4).world, ((EntityMushroomCow)var4).locX, ((EntityMushroomCow)var4).locY + ((EntityMushroomCow)var4).length, ((EntityMushroomCow)var4).locZ, new ItemStack(Block.RED_MUSHROOM)));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int a(Entity var1)
/*     */   {
/* 397 */     return (!(var1 instanceof EntitySheep)) && (!(var1 instanceof EntityMushroomCow)) ? this.weaponDamage : 1;
/*     */   }
/*     */ 
/*     */   public boolean a(ItemStack var1, EntityLiving var2, EntityLiving var3)
/*     */   {
/* 406 */     if ((var3 instanceof EntityHuman))
/*     */     {
/* 408 */       EntityHuman var4 = (EntityHuman)var3;
/*     */ 
/* 410 */       if ((var2 instanceof EntitySheep))
/*     */       {
/* 412 */         if (!((EntitySheep)var2).isSheared())
/*     */         {
/* 414 */           doShear(var1, var4.world, var4, var2);
/*     */         }
/*     */ 
/* 417 */         var2.heal(1);
/*     */       }
/* 419 */       else if ((var2 instanceof EntityMushroomCow))
/*     */       {
/* 421 */         doShear(var1, var4.world, var4, var2);
/* 422 */         var2.heal(1);
/*     */       }
/*     */     }
/*     */ 
/* 426 */     return true;
/*     */   }
/*     */ 
/*     */   public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 434 */     if (EEProxy.isClient(var2))
/*     */     {
/* 436 */       return var1;
/*     */     }
/*     */ 
/* 440 */     doBreak(var1, var2, var3);
/* 441 */     return var1;
/*     */   }
/*     */ 
/*     */   public void doHeld(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void doRelease(ItemStack var1, World var2, EntityHuman var3) {
/* 449 */     doBreak(var1, var2, var3);
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

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemRedShears
 * JD-Core Version:    0.6.2
 */