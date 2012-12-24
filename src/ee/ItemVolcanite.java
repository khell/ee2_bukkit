/*     */ package ee;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.AxisAlignedBB;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EnumMovingObjectType;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.Material;
/*     */ import net.minecraft.server.MathHelper;
/*     */ import net.minecraft.server.MovingObjectPosition;
/*     */ import net.minecraft.server.StepSound;
/*     */ import net.minecraft.server.Vec3D;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class ItemVolcanite extends ItemEECharged
/*     */ {
/*     */   public boolean itemCharging;
/*     */ 
/*     */   public ItemVolcanite(int var1)
/*     */   {
/*  22 */     super(var1, 4);
/*     */   }
/*     */ 
/*     */   public boolean ConsumeReagent(EntityHuman var1, boolean var2)
/*     */   {
/*  27 */     return EEBase.Consume(new ItemStack(Item.REDSTONE, 1), var1, false) ? true : EEBase.consumeKleinStarPoint(var1, 64) ? true : EEBase.Consume(new ItemStack(Item.COAL, 2, 1), var1, true);
/*     */   }
/*     */ 
/*     */   public void doVaporize(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*  32 */     boolean var4 = true;
/*  33 */     var2.makeSound(var3, "transmute", 0.8F, 1.5F);
/*  34 */     int var5 = (int)EEBase.playerX(var3);
/*  35 */     int var6 = (int)EEBase.playerY(var3);
/*  36 */     int var7 = (int)EEBase.playerZ(var3);
/*     */ 
/*  38 */     for (int var8 = -(1 + chargeLevel(var1)); var8 <= 1 + chargeLevel(var1); var8++)
/*     */     {
/*  40 */       for (int var9 = -(1 + chargeLevel(var1)); var9 <= 1 + chargeLevel(var1); var9++)
/*     */       {
/*  42 */         for (int var10 = -(1 + chargeLevel(var1)); var10 <= 1 + chargeLevel(var1); var10++)
/*     */         {
/*  44 */           if (var2.getMaterial(var5 + var8, var6 + var9, var7 + var10) == Material.WATER)
/*     */           {
/*  46 */             var2.setTypeId(var5 + var8, var6 + var9, var7 + var10, 0);
/*  47 */             var2.a("smoke", var5 + var8, var6 + var9, var7 + var10, 0.0D, 0.1D, 0.0D);
/*     */ 
/*  49 */             if (var4)
/*     */             {
/*  51 */               var2.makeSound(var3, "random.fizz", 1.0F, 1.2F / (var2.random.nextFloat() * 0.2F + 0.9F));
/*  52 */               var4 = false;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*  65 */     if (EEProxy.isClient(var2))
/*     */     {
/*  67 */       return var1;
/*     */     }
/*     */ 
/*  71 */     float var4 = 1.0F;
/*  72 */     float var5 = var3.lastPitch + (var3.pitch - var3.lastPitch) * var4;
/*  73 */     float var6 = var3.lastYaw + (var3.yaw - var3.lastYaw) * var4;
/*  74 */     double var7 = var3.lastX + (var3.locX - var3.lastX) * var4;
/*  75 */     double var9 = var3.lastY + (var3.locY - var3.lastY) * var4 + 1.62D - var3.height;
/*  76 */     double var11 = var3.lastZ + (var3.locZ - var3.lastZ) * var4;
/*  77 */     Vec3D var13 = Vec3D.create(var7, var9, var11);
/*  78 */     float var14 = MathHelper.cos(-var6 * 0.01745329F - 3.141593F);
/*  79 */     float var15 = MathHelper.sin(-var6 * 0.01745329F - 3.141593F);
/*  80 */     float var16 = -MathHelper.cos(-var5 * 0.01745329F);
/*  81 */     float var17 = MathHelper.sin(-var5 * 0.01745329F);
/*  82 */     float var18 = var15 * var16;
/*  83 */     float var20 = var14 * var16;
/*  84 */     double var21 = 5.0D;
/*  85 */     Vec3D var23 = var13.add(var18 * var21, var17 * var21, var20 * var21);
/*  86 */     MovingObjectPosition var24 = var2.rayTrace(var13, var23, false);
/*     */ 
/*  88 */     if (var24 == null)
/*     */     {
/*  90 */       return var1;
/*     */     }
/*     */ 
/*  94 */     if (var24.type == EnumMovingObjectType.TILE)
/*     */     {
/*  96 */       int var25 = var24.b;
/*  97 */       int var26 = var24.c;
/*  98 */       int var27 = var24.d;
/*     */ 
/* 100 */       if (!var2.a(var3, var25, var26, var27))
/*     */       {
/* 102 */         return var1;
/*     */       }
/*     */ 
/* 105 */       if (var24.face == 0)
/*     */       {
/* 107 */         var26--;
/*     */       }
/*     */ 
/* 110 */       if (var24.face == 1)
/*     */       {
/* 112 */         var26++;
/*     */       }
/*     */ 
/* 115 */       if (var24.face == 2)
/*     */       {
/* 117 */         var27--;
/*     */       }
/*     */ 
/* 120 */       if (var24.face == 3)
/*     */       {
/* 122 */         var27++;
/*     */       }
/*     */ 
/* 125 */       if (var24.face == 4)
/*     */       {
/* 127 */         var25--;
/*     */       }
/*     */ 
/* 130 */       if (var24.face == 5)
/*     */       {
/* 132 */         var25++;
/*     */       }
/*     */ 
/* 135 */       if (!var3.d(var25, var26, var27))
/*     */       {
/* 137 */         return var1;
/*     */       }
/*     */ 
/* 140 */       if (((var2.isEmpty(var25, var26, var27)) || (!var2.getMaterial(var25, var26, var27).isBuildable())) && (ConsumeReagent(var3, true)))
/*     */       {
/* 142 */         var2.setTypeIdAndData(var25, var26, var27, 10, 0);
/*     */       }
/*     */     }
/*     */ 
/* 146 */     return var1;
/*     */   }
/*     */ 
/*     */   public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 157 */     if (EEProxy.isClient(var3))
/*     */     {
/* 159 */       return false;
/*     */     }
/*     */ 
/* 163 */     Block var8 = Block.byId[10];
/*     */ 
/* 165 */     if (chargeLevel(var1) > 0)
/*     */     {
/* 167 */       var3.makeSound(var2, "transmute", 0.8F, 1.5F);
/* 168 */       var2.C_();
/* 169 */       double var9 = EEBase.direction(var2);
/*     */       boolean var13;
/* 171 */       if (var3.getTypeId(var4, var5, var6) == Block.SNOW.id)
/*     */       {
/* 173 */         var13 = false;
/*     */       }
/*     */       else
/*     */       {
/* 177 */         if (var7 == 0)
/*     */         {
/* 179 */           var5--;
/*     */         }
/*     */ 
/* 182 */         if (var7 == 1)
/*     */         {
/* 184 */           var5++;
/*     */         }
/*     */ 
/* 187 */         if (var7 == 2)
/*     */         {
/* 189 */           var6--;
/*     */         }
/*     */ 
/* 192 */         if (var7 == 3)
/*     */         {
/* 194 */           var6++;
/*     */         }
/*     */ 
/* 197 */         if (var7 == 4)
/*     */         {
/* 199 */           var4--;
/*     */         }
/*     */ 
/* 202 */         if (var7 == 5)
/*     */         {
/* 204 */           var4++;
/*     */         }
/*     */       }
/*     */ 
/* 208 */       if (var5 == 127)
/*     */       {
/* 210 */         return false;
/*     */       }
/*     */ 
/* 216 */       if (var9 == 0.0D)
/*     */       {
/* 218 */         for (int var11 = -(chargeLevel(var1) / 7 + 1); var11 <= chargeLevel(var1) / 7 + 1; var11++)
/*     */         {
/* 220 */           for (int var12 = -(chargeLevel(var1) / 7 + 1); var12 <= chargeLevel(var1) / 7 + 1; var12++)
/*     */           {
/* 222 */             if ((var3.getTypeId(var4 + var11, var5, var6 + var12) == 0) || (var3.getTypeId(var4 + var11, var5, var6 + var12) == 78))
/*     */             {
/* 224 */               if (!ConsumeReagent(var2, true))
/*     */               {
/* 226 */                 resetCharge(var1, var3, var2, true);
/* 227 */                 return false;
/*     */               }
/*     */ 
/* 230 */               var3.setTypeId(var4 + var11, var5, var6 + var12, 10);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 235 */       else if (var9 == 1.0D)
/*     */       {
/* 237 */         for (int var11 = 0; var11 <= chargeLevel(var1); var11++)
/*     */         {
/* 239 */           if ((var3.getTypeId(var4, var5 + var11, var6) == 0) || (var3.getTypeId(var4, var5 + var11, var6) == 78))
/*     */           {
/* 241 */             if (!ConsumeReagent(var2, true))
/*     */             {
/* 243 */               resetCharge(var1, var3, var2, true);
/* 244 */               return false;
/*     */             }
/*     */ 
/* 247 */             var3.setTypeId(var4, var5 + var11, var6, 10);
/*     */           }
/*     */         }
/*     */       }
/* 251 */       else if (var9 == 2.0D)
/*     */       {
/* 253 */         for (int var11 = 0; var11 <= chargeLevel(var1); var11++)
/*     */         {
/* 255 */           if ((var3.getTypeId(var4, var5, var6 + var11) == 0) || (var3.getTypeId(var4, var5, var6 + var11) == 78))
/*     */           {
/* 257 */             if (!ConsumeReagent(var2, true))
/*     */             {
/* 259 */               resetCharge(var1, var3, var2, true);
/* 260 */               return false;
/*     */             }
/*     */ 
/* 263 */             var3.setTypeId(var4, var5, var6 + var11, 10);
/*     */           }
/*     */         }
/*     */       }
/* 267 */       else if (var9 == 3.0D)
/*     */       {
/* 269 */         for (int var11 = 0; var11 <= chargeLevel(var1); var11++)
/*     */         {
/* 271 */           if ((var3.getTypeId(var4 - var11, var5, var6) == 0) || (var3.getTypeId(var4 - var11, var5, var6) == 78))
/*     */           {
/* 273 */             if (!ConsumeReagent(var2, true))
/*     */             {
/* 275 */               resetCharge(var1, var3, var2, true);
/* 276 */               return false;
/*     */             }
/*     */ 
/* 279 */             var3.setTypeId(var4 - var11, var5, var6, 10);
/*     */           }
/*     */         }
/*     */       }
/* 283 */       else if (var9 == 4.0D)
/*     */       {
/* 285 */         for (int var11 = 0; var11 <= chargeLevel(var1); var11++)
/*     */         {
/* 287 */           if ((var3.getTypeId(var4, var5, var6 - var11) == 0) || (var3.getTypeId(var4, var5, var6 - var11) == 78))
/*     */           {
/* 289 */             if (!ConsumeReagent(var2, true))
/*     */             {
/* 291 */               resetCharge(var1, var3, var2, true);
/* 292 */               return false;
/*     */             }
/*     */ 
/* 295 */             var3.setTypeId(var4, var5, var6 - var11, 10);
/*     */           }
/*     */         }
/*     */       }
/* 299 */       else if (var9 == 5.0D)
/*     */       {
/* 301 */         for (int var11 = 0; var11 <= chargeLevel(var1); var11++)
/*     */         {
/* 303 */           if ((var3.getTypeId(var4 + var11, var5, var6) == 0) || (var3.getTypeId(var4 + var11, var5, var6) == 78))
/*     */           {
/* 305 */             if (!ConsumeReagent(var2, true))
/*     */             {
/* 307 */               resetCharge(var1, var3, var2, true);
/* 308 */               return false;
/*     */             }
/*     */ 
/* 311 */             var3.setTypeId(var4 + var11, var5, var6, 10);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 316 */       resetCharge(var1, var3, var2, false);
/* 317 */       return true;
/*     */     }
/*     */ 
/* 322 */     if (chargeLevel(var1) < 1)
/*     */     {
/* 324 */       if (var3.getTypeId(var4, var5, var6) == Block.SNOW.id)
/*     */       {
/* 326 */         var7 = 0;
/*     */       }
/*     */       else
/*     */       {
/* 330 */         if (var7 == 0)
/*     */         {
/* 332 */           var5--;
/*     */         }
/*     */ 
/* 335 */         if (var7 == 1)
/*     */         {
/* 337 */           var5++;
/*     */         }
/*     */ 
/* 340 */         if (var7 == 2)
/*     */         {
/* 342 */           var6--;
/*     */         }
/*     */ 
/* 345 */         if (var7 == 3)
/*     */         {
/* 347 */           var6++;
/*     */         }
/*     */ 
/* 350 */         if (var7 == 4)
/*     */         {
/* 352 */           var4--;
/*     */         }
/*     */ 
/* 355 */         if (var7 == 5)
/*     */         {
/* 357 */           var4++;
/*     */         }
/*     */       }
/*     */ 
/* 361 */       if (var5 == 127)
/*     */       {
/* 363 */         return false;
/*     */       }
/*     */ 
/* 366 */       if ((var3.mayPlace(10, var4, var5, var6, false, var7)) && (var3.getTypeId(var4, var5, var6) == 0))
/*     */       {
/* 368 */         if (ConsumeReagent(var2, true))
/*     */         {
/* 370 */           if (var3.setTypeIdAndData(var4, var5, var6, 10, filterData(var1.getData())))
/*     */           {
/* 372 */             Block.byId[10].postPlace(var3, var4, var5, var6, var7);
/* 373 */             Block.byId[10].postPlace(var3, var4, var5, var6, var2);
/* 374 */             var3.makeSound(var4 + 0.5F, var5 + 0.5F, var6 + 0.5F, var8.stepSound.getName(), (var8.stepSound.getVolume1() + 1.0F) / 2.0F, var8.stepSound.getVolume2() * 0.8F);
/* 375 */             return true;
/*     */           }
/*     */ 
/* 378 */           return false;
/*     */         }
/*     */ 
/* 381 */         resetCharge(var1, var3, var2, true);
/* 382 */         return false;
/*     */       }
/*     */     }
/*     */ 
/* 386 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean onItemUseFirst(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 393 */     return false;
/*     */   }
/*     */ 
/*     */   public void doPassive(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 398 */     if (!EEProxy.isEntityFireImmune(var3))
/*     */     {
/* 400 */       EEProxy.setPlayerFireImmunity(var3, true);
/*     */     }
/*     */ 
/* 403 */     AxisAlignedBB var4 = AxisAlignedBB.b(var3.boundingBox.a, var3.boundingBox.b - 0.2D, var3.boundingBox.c, var3.boundingBox.d, var3.boundingBox.e, var3.boundingBox.f);
/* 404 */     EEBase.updatePlayerInLava(var3, var2.b(var4, Material.LAVA));
/*     */   }
/*     */   public void doActive(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doHeld(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doRelease(ItemStack var1, World var2, EntityHuman var3) {
/* 413 */     var3.C_();
/* 414 */     var2.makeSound(var3, "transmute", 0.6F, 1.0F);
/* 415 */     var2.addEntity(new EntityLavaEssence(var2, var3, chargeLevel(var1) >= 8));
/*     */   }
/*     */ 
/*     */   public void doAlternate(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 420 */     doVaporize(var1, var2, var3);
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
 * Qualified Name:     ee.ItemVolcanite
 * JD-Core Version:    0.6.2
 */