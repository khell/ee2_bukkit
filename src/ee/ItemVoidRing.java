/*     */ package ee;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.AxisAlignedBB;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityExperienceOrb;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityItem;
/*     */ import net.minecraft.server.EnumMovingObjectType;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.Material;
/*     */ import net.minecraft.server.MathHelper;
/*     */ import net.minecraft.server.MovingObjectPosition;
/*     */ import net.minecraft.server.NBTTagCompound;
/*     */ import net.minecraft.server.PlayerInventory;
/*     */ import net.minecraft.server.Vec3D;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class ItemVoidRing extends ItemEECharged
/*     */ {
/*     */   private int ticksLastSpent;
/*     */ 
/*     */   public ItemVoidRing(int var1)
/*     */   {
/*  28 */     super(var1, 0);
/*     */   }
/*     */ 
/*     */   public int getIconFromDamage(int var1)
/*     */   {
/*  33 */     return var1 < 2 ? this.textureId + var1 : this.textureId;
/*     */   }
/*     */ 
/*     */   private void PullItems(Entity var1, EntityHuman var2)
/*     */   {
/*  46 */     if (var1.getClass().equals(EntityItem.class))
/*     */     {
/*  48 */       EntityItem var3 = (EntityItem)var1;
/*  49 */       double var4 = (float)var2.locX + 0.5F - var3.locX;
/*  50 */       double var6 = (float)var2.locY + 0.5F - var3.locY;
/*  51 */       double var8 = (float)var2.locZ + 0.5F - var3.locZ;
/*  52 */       double var10 = var4 * var4 + var6 * var6 + var8 * var8;
/*  53 */       var10 *= var10;
/*     */ 
/*  55 */       if (var10 <= Math.pow(6.0D, 4.0D))
/*     */       {
/*  57 */         double var12 = var4 * 0.01999999955296516D / var10 * Math.pow(6.0D, 3.0D);
/*  58 */         double var14 = var6 * 0.01999999955296516D / var10 * Math.pow(6.0D, 3.0D);
/*  59 */         double var16 = var8 * 0.01999999955296516D / var10 * Math.pow(6.0D, 3.0D);
/*     */ 
/*  61 */         if (var12 > 0.1D)
/*     */         {
/*  63 */           var12 = 0.1D;
/*     */         }
/*  65 */         else if (var12 < -0.1D)
/*     */         {
/*  67 */           var12 = -0.1D;
/*     */         }
/*     */ 
/*  70 */         if (var14 > 0.1D)
/*     */         {
/*  72 */           var14 = 0.1D;
/*     */         }
/*  74 */         else if (var14 < -0.1D)
/*     */         {
/*  76 */           var14 = -0.1D;
/*     */         }
/*     */ 
/*  79 */         if (var16 > 0.1D)
/*     */         {
/*  81 */           var16 = 0.1D;
/*     */         }
/*  83 */         else if (var16 < -0.1D)
/*     */         {
/*  85 */           var16 = -0.1D;
/*     */         }
/*     */ 
/*  88 */         var3.motX += var12 * 1.2D;
/*  89 */         var3.motY += var14 * 1.2D;
/*  90 */         var3.motZ += var16 * 1.2D;
/*     */       }
/*     */     }
/*  93 */     else if (var1.getClass().equals(EntityLootBall.class))
/*     */     {
/*  95 */       EntityLootBall var18 = (EntityLootBall)var1;
/*  96 */       double var4 = (float)var2.locX + 0.5F - var18.locX;
/*  97 */       double var6 = (float)var2.locY + 0.5F - var18.locY;
/*  98 */       double var8 = (float)var2.locZ + 0.5F - var18.locZ;
/*  99 */       double var10 = var4 * var4 + var6 * var6 + var8 * var8;
/* 100 */       var10 *= var10;
/*     */ 
/* 102 */       if (var10 <= Math.pow(6.0D, 4.0D))
/*     */       {
/* 104 */         double var12 = var4 * 0.01999999955296516D / var10 * Math.pow(6.0D, 3.0D);
/* 105 */         double var14 = var6 * 0.01999999955296516D / var10 * Math.pow(6.0D, 3.0D);
/* 106 */         double var16 = var8 * 0.01999999955296516D / var10 * Math.pow(6.0D, 3.0D);
/*     */ 
/* 108 */         if (var12 > 0.1D)
/*     */         {
/* 110 */           var12 = 0.1D;
/*     */         }
/* 112 */         else if (var12 < -0.1D)
/*     */         {
/* 114 */           var12 = -0.1D;
/*     */         }
/*     */ 
/* 117 */         if (var14 > 0.1D)
/*     */         {
/* 119 */           var14 = 0.1D;
/*     */         }
/* 121 */         else if (var14 < -0.1D)
/*     */         {
/* 123 */           var14 = -0.1D;
/*     */         }
/*     */ 
/* 126 */         if (var16 > 0.1D)
/*     */         {
/* 128 */           var16 = 0.1D;
/*     */         }
/* 130 */         else if (var16 < -0.1D)
/*     */         {
/* 132 */           var16 = -0.1D;
/*     */         }
/*     */ 
/* 135 */         var18.motX += var12 * 1.2D;
/* 136 */         var18.motY += var14 * 1.2D;
/* 137 */         var18.motZ += var16 * 1.2D;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doRelease(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 144 */     doTeleport(var1, var2, var3);
/*     */   }
/*     */ 
/*     */   private void doTeleport(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 149 */     float var4 = 1.0F;
/* 150 */     float var5 = var3.lastPitch + (var3.pitch - var3.lastPitch) * var4;
/* 151 */     float var6 = var3.lastYaw + (var3.yaw - var3.lastYaw) * var4;
/* 152 */     double var7 = var3.lastX + (var3.locX - var3.lastX) * var4;
/* 153 */     double var9 = var3.lastY + (var3.locY - var3.lastY) * var4 + 1.62D - var3.height;
/* 154 */     double var11 = var3.lastZ + (var3.locZ - var3.lastZ) * var4;
/* 155 */     Vec3D var13 = Vec3D.create(var7, var9, var11);
/* 156 */     float var14 = MathHelper.cos(-var6 * 0.01745329F - 3.141593F);
/* 157 */     float var15 = MathHelper.sin(-var6 * 0.01745329F - 3.141593F);
/* 158 */     float var16 = -MathHelper.cos(-var5 * 0.01745329F);
/* 159 */     float var17 = MathHelper.sin(-var5 * 0.01745329F);
/* 160 */     float var18 = var15 * var16;
/* 161 */     float var20 = var14 * var16;
/* 162 */     double var21 = 150.0D;
/* 163 */     Vec3D var23 = var13.add(var18 * var21, var17 * var21, var20 * var21);
/* 164 */     MovingObjectPosition var24 = var2.rayTrace(var13, var23, true);
/*     */ 
/* 166 */     if (var24 != null)
/*     */     {
/* 168 */       if (var24.type == EnumMovingObjectType.TILE)
/*     */       {
/* 170 */         int var25 = var24.b;
/* 171 */         int var26 = var24.c;
/* 172 */         int var27 = var24.d;
/* 173 */         int var28 = var24.face;
/*     */ 
/* 175 */         if (var28 == 0)
/*     */         {
/* 177 */           var26 -= 2;
/*     */         }
/*     */ 
/* 180 */         if (var28 == 1)
/*     */         {
/* 182 */           var26++;
/*     */         }
/*     */ 
/* 185 */         if (var28 == 2)
/*     */         {
/* 187 */           var27--;
/*     */         }
/*     */ 
/* 190 */         if (var28 == 3)
/*     */         {
/* 192 */           var27++;
/*     */         }
/*     */ 
/* 195 */         if (var28 == 4)
/*     */         {
/* 197 */           var25--;
/*     */         }
/*     */ 
/* 200 */         if (var28 == 5)
/*     */         {
/* 202 */           var25++;
/*     */         }
/*     */ 
/* 205 */         for (int var29 = 0; var29 < 32; var29++)
/*     */         {
/* 207 */           var3.world.a("portal", var25, var26 + var3.world.random.nextDouble() * 2.0D, var27, var3.world.random.nextGaussian(), 0.0D, var3.world.random.nextGaussian());
/*     */         }
/*     */ 
/* 210 */         if ((var3.world.getTypeId(var25, var26, var27) == 0) && (var3.world.getTypeId(var25, var26 + 1, var27) == 0))
/*     */         {
/* 212 */           var3.enderTeleportTo(var25, var26, var27);
/*     */         }
/*     */ 
/* 215 */         var3.fallDistance = 0.0F;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 225 */     if (EEProxy.isClient(var2))
/*     */     {
/* 227 */       return var1;
/*     */     }
/*     */ 
/* 231 */     doDisintegrate(var1, var2, var3);
/* 232 */     return var1;
/*     */   }
/*     */ 
/*     */   private void doDisintegrate(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 238 */     float var4 = 1.0F;
/* 239 */     float var5 = var3.lastPitch + (var3.pitch - var3.lastPitch) * var4;
/* 240 */     float var6 = var3.lastYaw + (var3.yaw - var3.lastYaw) * var4;
/* 241 */     double var7 = var3.lastX + (var3.locX - var3.lastX) * var4;
/* 242 */     double var9 = var3.lastY + (var3.locY - var3.lastY) * var4 + 1.62D - var3.height;
/* 243 */     double var11 = var3.lastZ + (var3.locZ - var3.lastZ) * var4;
/* 244 */     Vec3D var13 = Vec3D.create(var7, var9, var11);
/* 245 */     float var14 = MathHelper.cos(-var6 * 0.01745329F - 3.141593F);
/* 246 */     float var15 = MathHelper.sin(-var6 * 0.01745329F - 3.141593F);
/* 247 */     float var16 = -MathHelper.cos(-var5 * 0.01745329F);
/* 248 */     float var17 = MathHelper.sin(-var5 * 0.01745329F);
/* 249 */     float var18 = var15 * var16;
/* 250 */     float var20 = var14 * var16;
/* 251 */     double var21 = 5.0D;
/* 252 */     Vec3D var23 = var13.add(var18 * var21, var17 * var21, var20 * var21);
/* 253 */     MovingObjectPosition var24 = var2.rayTrace(var13, var23, true);
/*     */ 
/* 255 */     if (var24 != null)
/*     */     {
/* 257 */       if (var24.type == EnumMovingObjectType.TILE)
/*     */       {
/* 259 */         int var25 = var24.b;
/* 260 */         int var26 = var24.c;
/* 261 */         int var27 = var24.d;
/*     */ 
/* 263 */         if (var2.getMaterial(var25, var26, var27) == Material.WATER)
/*     */         {
/* 265 */           var2.setTypeId(var25, var26, var27, 0);
/*     */         }
/* 267 */         else if (var2.getMaterial(var25, var26 + 1, var27) == Material.WATER)
/*     */         {
/* 269 */           var2.setTypeId(var25, var26 + 1, var27, 0);
/*     */         }
/* 271 */         else if (var2.getMaterial(var25, var26, var27) == Material.LAVA)
/*     */         {
/* 273 */           var2.setTypeId(var25, var26, var27, 0);
/*     */         }
/* 275 */         else if (var2.getMaterial(var25, var26 + 1, var27) == Material.LAVA)
/*     */         {
/* 277 */           var2.setTypeId(var25, var26 + 1, var27, 0);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doPassive(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 285 */     if (isActivated(var1))
/*     */     {
/* 287 */       doAttraction(var1, var2, var3);
/* 288 */       doCondense(var1, var2, var3);
/*     */     }
/*     */ 
/* 291 */     if (!isActivated(var1.getData()))
/*     */     {
/* 293 */       dumpContents(var1, var3);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean roomFor(ItemStack var1, EntityHuman var2)
/*     */   {
/* 299 */     if (var1 == null)
/*     */     {
/* 301 */       return false;
/*     */     }
/*     */ 
/* 305 */     for (int var3 = 0; var3 < var2.inventory.items.length; var3++)
/*     */     {
/* 307 */       if (var2.inventory.items[var3] == null)
/*     */       {
/* 309 */         return true;
/*     */       }
/*     */ 
/* 312 */       if ((var2.inventory.items[var3].doMaterialsMatch(var1)) && (var2.inventory.items[var3].count <= var1.getMaxStackSize() - var1.count))
/*     */       {
/* 314 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 318 */     return false;
/*     */   }
/*     */ 
/*     */   public void PushStack(ItemStack var1, EntityHuman var2)
/*     */   {
/* 324 */     if (var1 != null)
/*     */     {
/* 326 */       for (int var3 = 0; var3 < var2.inventory.items.length; var3++)
/*     */       {
/* 328 */         if (var2.inventory.items[var3] == null)
/*     */         {
/* 330 */           var2.inventory.items[var3] = var1.cloneItemStack();
/* 331 */           var1 = null;
/* 332 */           return;
/*     */         }
/*     */ 
/* 335 */         if ((var2.inventory.items[var3].doMaterialsMatch(var1)) && (var2.inventory.items[var3].count <= var1.getMaxStackSize() - var1.count))
/*     */         {
/* 337 */           var2.inventory.items[var3].count += var1.count;
/* 338 */           var1 = null;
/* 339 */           return;
/*     */         }
/*     */ 
/* 342 */         if (var2.inventory.items[var3].doMaterialsMatch(var1))
/*     */         {
/* 344 */           while ((var2.inventory.items[var3].count < var2.inventory.items[var3].getMaxStackSize()) && (var1 != null))
/*     */           {
/* 346 */             var2.inventory.items[var3].count += 1;
/* 347 */             var1.count -= 1;
/*     */ 
/* 349 */             if (var1.count <= 0)
/*     */             {
/* 351 */               var1 = null;
/* 352 */               return;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void dumpContents(ItemStack var1, EntityHuman var2)
/*     */   {
/*     */     while (true)
/*     */     {
/* 364 */       takeEMC(var1, EEMaps.getEMC(EEItem.redMatter.id));
/* 365 */       PushStack(new ItemStack(EEItem.redMatter, 1), var2);
/*     */ 
/* 362 */       if (emc(var1) >= EEMaps.getEMC(EEItem.redMatter.id)) if (!roomFor(new ItemStack(EEItem.redMatter, 1), var2))
/*     */         {
/* 368 */           break; }  
/*     */     }
/*     */     while (true) { takeEMC(var1, EEMaps.getEMC(EEItem.darkMatter.id));
/* 371 */       PushStack(new ItemStack(EEItem.darkMatter, 1), var2);
/*     */ 
/* 368 */       if (emc(var1) >= EEMaps.getEMC(EEItem.darkMatter.id)) if (!roomFor(new ItemStack(EEItem.darkMatter, 1), var2))
/*     */         {
/* 374 */           break;
/*     */         }  } while (true) {
/* 376 */       takeEMC(var1, EEMaps.getEMC(Item.DIAMOND.id));
/* 377 */       PushStack(new ItemStack(Item.DIAMOND, 1), var2);
/*     */ 
/* 374 */       if (emc(var1) >= EEMaps.getEMC(Item.DIAMOND.id)) if (!roomFor(new ItemStack(Item.DIAMOND, 1), var2))
/*     */         {
/* 380 */           break; }  
/*     */     }
/*     */     while (true) { takeEMC(var1, EEMaps.getEMC(Item.GOLD_INGOT.id));
/* 383 */       PushStack(new ItemStack(Item.GOLD_INGOT, 1), var2);
/*     */ 
/* 380 */       if (emc(var1) >= EEMaps.getEMC(Item.GOLD_INGOT.id)) if (!roomFor(new ItemStack(Item.GOLD_INGOT, 1), var2))
/*     */         {
/* 386 */           break;
/*     */         }  } do {
/* 388 */       takeEMC(var1, EEMaps.getEMC(Item.IRON_INGOT.id));
/* 389 */       PushStack(new ItemStack(Item.IRON_INGOT, 1), var2);
/*     */ 
/* 386 */       if (emc(var1) < EEMaps.getEMC(Item.IRON_INGOT.id)) break;  } while (roomFor(new ItemStack(Item.IRON_INGOT, 1), var2));
/*     */ 
/* 392 */     while ((emc(var1) >= EEMaps.getEMC(Block.COBBLESTONE.id)) && (roomFor(new ItemStack(Block.COBBLESTONE, 1), var2)))
/*     */     {
/* 394 */       takeEMC(var1, EEMaps.getEMC(Block.COBBLESTONE.id));
/* 395 */       PushStack(new ItemStack(Block.COBBLESTONE, 1), var2);
/*     */     }
/*     */   }
/*     */ 
/*     */   public ItemStack target(ItemStack var1)
/*     */   {
/* 401 */     return getInteger(var1, "targetID") != 0 ? new ItemStack(getInteger(var1, "targetID"), 1, 0) : getInteger(var1, "targetMeta") != 0 ? new ItemStack(getInteger(var1, "targetID"), 1, getInteger(var1, "targetMeta")) : null;
/*     */   }
/*     */ 
/*     */   public ItemStack product(ItemStack var1)
/*     */   {
/* 406 */     if (target(var1) != null)
/*     */     {
/* 408 */       int var2 = EEMaps.getEMC(target(var1));
/*     */ 
/* 410 */       if (var2 < EEMaps.getEMC(Item.IRON_INGOT.id))
/*     */       {
/* 412 */         return new ItemStack(Item.IRON_INGOT, 1);
/*     */       }
/*     */ 
/* 415 */       if (var2 < EEMaps.getEMC(Item.GOLD_INGOT.id))
/*     */       {
/* 417 */         return new ItemStack(Item.GOLD_INGOT, 1);
/*     */       }
/*     */ 
/* 420 */       if (var2 < EEMaps.getEMC(Item.DIAMOND.id))
/*     */       {
/* 422 */         return new ItemStack(Item.DIAMOND, 1);
/*     */       }
/*     */ 
/* 425 */       if (var2 < EEMaps.getEMC(EEItem.darkMatter.id))
/*     */       {
/* 427 */         return new ItemStack(EEItem.darkMatter, 1);
/*     */       }
/*     */ 
/* 430 */       if (var2 < EEMaps.getEMC(EEItem.redMatter.id))
/*     */       {
/* 432 */         return new ItemStack(EEItem.redMatter, 1);
/*     */       }
/*     */     }
/*     */ 
/* 436 */     return null;
/*     */   }
/*     */ 
/*     */   public void doCondense(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 441 */     if (!EEProxy.isClient(var2))
/*     */     {
/* 443 */       if ((product(var1) != null) && (emc(var1) >= EEMaps.getEMC(product(var1))) && (roomFor(product(var1), var3)))
/*     */       {
/* 445 */         PushStack(product(var1), var3);
/* 446 */         takeEMC(var1, EEMaps.getEMC(product(var1)));
/*     */       }
/*     */ 
/* 449 */       int var4 = 0;
/* 450 */       ItemStack[] var5 = var3.inventory.items;
/* 451 */       int var6 = var5.length;
/*     */ 
/* 455 */       for (int var7 = 0; var7 < var6; var7++)
/*     */       {
/* 457 */         ItemStack var8 = var5[var7];
/*     */ 
/* 459 */         if ((var8 != null) && (EEMaps.getEMC(var8) != 0) && (isValidMaterial(var8, var3)) && (EEMaps.getEMC(var8) > var4))
/*     */         {
/* 461 */           var4 = EEMaps.getEMC(var8);
/*     */         }
/*     */       }
/*     */ 
/* 465 */       var5 = var3.inventory.items;
/* 466 */       var6 = var5.length;
/*     */ 
/* 468 */       for (int var7 = 0; var7 < var6; var7++)
/*     */       {
/* 470 */         ItemStack var8 = var5[var7];
/*     */ 
/* 472 */         if ((var8 != null) && (EEMaps.getEMC(var8) != 0) && (isValidMaterial(var8, var3)) && (EEMaps.getEMC(var8) <= var4))
/*     */         {
/* 474 */           var4 = EEMaps.getEMC(var8);
/* 475 */           setInteger(var1, "targetID", var8.id);
/* 476 */           setInteger(var1, "targetMeta", var8.getData());
/*     */         }
/*     */       }
/*     */ 
/* 480 */       if (target(var1) != null)
/*     */       {
/* 482 */         if (ConsumeMaterial(target(var1), var3))
/*     */         {
/* 484 */           addEMC(var1, EEMaps.getEMC(target(var1)));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean isLastCobbleStack(EntityHuman var1)
/*     */   {
/* 492 */     int var2 = 0;
/*     */ 
/* 494 */     for (int var3 = 0; var3 < var1.inventory.items.length; var3++)
/*     */     {
/* 496 */       if ((var1.inventory.items[var3] != null) && (var1.inventory.items[var3].id == Block.COBBLESTONE.id))
/*     */       {
/* 498 */         var2 += var1.inventory.items[var3].count;
/*     */       }
/*     */     }
/*     */ 
/* 502 */     if (var2 <= 64)
/*     */     {
/* 504 */       return true;
/*     */     }
/*     */ 
/* 508 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean isValidMaterial(ItemStack var1, EntityHuman var2)
/*     */   {
/* 514 */     if (EEMaps.getEMC(var1) == 0)
/*     */     {
/* 516 */       return false;
/*     */     }
/* 518 */     if ((var1.id == Block.COBBLESTONE.id) && (isLastCobbleStack(var2)))
/*     */     {
/* 520 */       return false;
/*     */     }
/*     */ 
/* 524 */     int var3 = var1.id;
/*     */ 
/* 526 */     if (var3 >= Block.byId.length)
/*     */     {
/* 528 */       if ((var3 != Item.IRON_INGOT.id) && (var3 != Item.GOLD_INGOT.id) && (var3 != Item.DIAMOND.id) && (var3 != EEItem.darkMatter.id))
/*     */       {
/* 530 */         return false;
/*     */       }
/*     */ 
/* 533 */       if (var3 == EEItem.redMatter.id)
/*     */       {
/* 535 */         return false;
/*     */       }
/*     */     }
/*     */ 
/* 539 */     return !EEMaps.isFuel(var1);
/*     */   }
/*     */ 
/*     */   private int emc(ItemStack var1)
/*     */   {
/* 545 */     return getInteger(var1, "emc");
/*     */   }
/*     */ 
/*     */   private void takeEMC(ItemStack var1, int var2)
/*     */   {
/* 550 */     setInteger(var1, "emc", emc(var1) - var2);
/*     */   }
/*     */ 
/*     */   private void addEMC(ItemStack var1, int var2)
/*     */   {
/* 555 */     setInteger(var1, "emc", emc(var1) + var2);
/*     */   }
/*     */ 
/*     */   public boolean ConsumeMaterial(ItemStack var1, EntityHuman var2)
/*     */   {
/* 560 */     return EEBase.Consume(var1, var2, false);
/*     */   }
/*     */ 
/*     */   public void doActive(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   private void doAttraction(ItemStack var1, World var2, EntityHuman var3) {
/* 567 */     if (!EEProxy.isClient(var2))
/*     */     {
/* 569 */       List var4 = var2.a(EntityItem.class, AxisAlignedBB.b(var3.locX - 10.0D, var3.locY - 10.0D, var3.locZ - 10.0D, var3.locX + 10.0D, var3.locY + 10.0D, var3.locZ + 10.0D));
/* 570 */       Iterator var6 = var4.iterator();
/*     */ 
/* 572 */       while (var6.hasNext())
/*     */       {
/* 574 */         Entity var5 = (Entity)var6.next();
/* 575 */         PullItems(var5, var3);
/*     */       }
/*     */ 
/* 578 */       List var11 = var2.a(EntityLootBall.class, AxisAlignedBB.b(var3.locX - 10.0D, var3.locY - 10.0D, var3.locZ - 10.0D, var3.locX + 10.0D, var3.locY + 10.0D, var3.locZ + 10.0D));
/* 579 */       Iterator var8 = var11.iterator();
/*     */ 
/* 581 */       while (var8.hasNext())
/*     */       {
/* 583 */         Entity var7 = (Entity)var8.next();
/* 584 */         PullItems(var7, var3);
/*     */       }
/*     */ 
/* 587 */       List var12 = var3.world.a(EntityExperienceOrb.class, AxisAlignedBB.b(var3.locX - 10.0D, var3.locY - 10.0D, var3.locZ - 10.0D, var3.locX + 10.0D, var3.locY + 10.0D, var3.locZ + 10.0D));
/* 588 */       Iterator var10 = var12.iterator();
/*     */ 
/* 590 */       while (var10.hasNext())
/*     */       {
/* 592 */         Entity var9 = (Entity)var10.next();
/* 593 */         PullItems(var9, var3);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doToggle(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 600 */     if (isActivated(var1))
/*     */     {
/* 602 */       var1.setData(0);
/* 603 */       var1.tag.setBoolean("active", false);
/* 604 */       var2.makeSound(var3, "break", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
/*     */     }
/*     */     else
/*     */     {
/* 608 */       var1.setData(1);
/* 609 */       var1.tag.setBoolean("active", true);
/* 610 */       var2.makeSound(var3, "heal", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean canActivate()
/*     */   {
/* 616 */     return true;
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

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemVoidRing
 * JD-Core Version:    0.6.2
 */