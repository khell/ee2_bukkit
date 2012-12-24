/*     */ package ee;
/*     */ 
/*     */ import net.minecraft.server.AxisAlignedBB;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EnumMovingObjectType;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.Material;
/*     */ import net.minecraft.server.MathHelper;
/*     */ import net.minecraft.server.MovingObjectPosition;
/*     */ import net.minecraft.server.StepSound;
/*     */ import net.minecraft.server.Vec3D;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class ItemEvertide extends ItemEECharged
/*     */ {
/*     */   public ItemEvertide(int var1)
/*     */   {
/*  19 */     super(var1, 4);
/*     */   }
/*     */ 
/*     */   public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/*  28 */     if (EEProxy.isClient(var3))
/*     */     {
/*  30 */       return false;
/*     */     }
/*     */ 
/*  34 */     Block var8 = Block.byId[8];
/*     */ 
/*  36 */     if (chargeLevel(var1) > 0)
/*     */     {
/*  38 */       var3.makeSound(var2, "waterball", 0.8F, 1.5F);
/*  39 */       var2.C_();
/*  40 */       double var9 = EEBase.direction(var2);
/*     */       boolean var13;
/*  42 */       if (var3.getTypeId(var4, var5, var6) == Block.SNOW.id)
/*     */       {
/*  44 */         var13 = false;
/*     */       }
/*     */       else
/*     */       {
/*  48 */         if (var7 == 0)
/*     */         {
/*  50 */           var5--;
/*     */         }
/*     */ 
/*  53 */         if (var7 == 1)
/*     */         {
/*  55 */           var5++;
/*     */         }
/*     */ 
/*  58 */         if (var7 == 2)
/*     */         {
/*  60 */           var6--;
/*     */         }
/*     */ 
/*  63 */         if (var7 == 3)
/*     */         {
/*  65 */           var6++;
/*     */         }
/*     */ 
/*  68 */         if (var7 == 4)
/*     */         {
/*  70 */           var4--;
/*     */         }
/*     */ 
/*  73 */         if (var7 == 5)
/*     */         {
/*  75 */           var4++;
/*     */         }
/*     */       }
/*     */ 
/*  79 */       if (var5 == 127)
/*     */       {
/*  81 */         return false;
/*     */       }
/*     */ 
/*  88 */       if (var9 == 0.0D)
/*     */       {
/*  90 */         for (int var11 = -(chargeLevel(var1) / 7 + 1); var11 <= chargeLevel(var1) / 7 + 1; var11++)
/*     */         {
/*  92 */           for (int var12 = -(chargeLevel(var1) / 7 + 1); var12 <= chargeLevel(var1) / 7 + 1; var12++)
/*     */           {
/*  94 */             if ((var3.getTypeId(var4 + var11, var5, var6 + var12) == 0) || (var3.getTypeId(var4 + var11, var5, var6 + var12) == 78))
/*     */             {
/*  96 */               var3.setTypeId(var4 + var11, var5, var6 + var12, 8);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 101 */       else if (var9 == 1.0D)
/*     */       {
/* 103 */         for (int var11 = 0; var11 <= chargeLevel(var1); var11++)
/*     */         {
/* 105 */           if ((var3.getTypeId(var4, var5 + var11, var6) == 0) || (var3.getTypeId(var4, var5 + var11, var6) == 78))
/*     */           {
/* 107 */             var3.setTypeId(var4, var5 + var11, var6, 8);
/*     */           }
/*     */ 
/* 110 */           if (chargeLevel(var1) == 7)
/*     */           {
/* 112 */             for (int var12 = 1; var12 < 4; var12++)
/*     */             {
/* 114 */               if ((var3.getTypeId(var4, var5 + var11 + var12, var6) == 0) || (var3.getTypeId(var4, var5 + var11, var6) == 78))
/*     */               {
/* 116 */                 var3.setTypeId(var4, var5 + var11 + var12, var6, 8);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 122 */       else if (var9 == 2.0D)
/*     */       {
/* 124 */         for (int var11 = 0; var11 <= chargeLevel(var1); var11++)
/*     */         {
/* 126 */           if ((var3.getTypeId(var4, var5, var6 + var11) == 0) || (var3.getTypeId(var4, var5, var6 + var11) == 78))
/*     */           {
/* 128 */             var3.setTypeId(var4, var5, var6 + var11, 8);
/*     */           }
/*     */ 
/* 131 */           if (chargeLevel(var1) == 7)
/*     */           {
/* 133 */             if ((var3.getTypeId(var4 - 1, var5, var6 + var11) == 0) || (var3.getTypeId(var4 - 1, var5, var6 + var11) == 78))
/*     */             {
/* 135 */               var3.setTypeId(var4 - 1, var5, var6 + var11, 8);
/*     */             }
/*     */ 
/* 138 */             if ((var3.getTypeId(var4 + 1, var5, var6 + var11) == 0) || (var3.getTypeId(var4 + 1, var5, var6 + var11) == 78))
/*     */             {
/* 140 */               var3.setTypeId(var4 + 1, var5, var6 + var11, 8);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 145 */       else if (var9 == 3.0D)
/*     */       {
/* 147 */         for (int var11 = 0; var11 <= chargeLevel(var1); var11++)
/*     */         {
/* 149 */           if ((var3.getTypeId(var4 - var11, var5, var6) == 0) || (var3.getTypeId(var4 - var11, var5, var6) == 78))
/*     */           {
/* 151 */             var3.setTypeId(var4 - var11, var5, var6, 8);
/*     */           }
/*     */ 
/* 154 */           if (chargeLevel(var1) == 7)
/*     */           {
/* 156 */             if ((var3.getTypeId(var4 - var11, var5, var6 - 1) == 0) || (var3.getTypeId(var4 - var11, var5, var6 - 1) == 78))
/*     */             {
/* 158 */               var3.setTypeId(var4 - var11, var5, var6 - 1, 8);
/*     */             }
/*     */ 
/* 161 */             if ((var3.getTypeId(var4 - var11, var5, var6 + 1) == 0) || (var3.getTypeId(var4 - var11, var5, var6 + 1) == 78))
/*     */             {
/* 163 */               var3.setTypeId(var4 - var11, var5, var6 + 1, 8);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 168 */       else if (var9 == 4.0D)
/*     */       {
/* 170 */         for (int var11 = 0; var11 <= chargeLevel(var1); var11++)
/*     */         {
/* 172 */           if ((var3.getTypeId(var4, var5, var6 - var11) == 0) || (var3.getTypeId(var4, var5, var6 - var11) == 78))
/*     */           {
/* 174 */             var3.setTypeId(var4, var5, var6 - var11, 8);
/*     */           }
/*     */ 
/* 177 */           if (chargeLevel(var1) == 7)
/*     */           {
/* 179 */             if ((var3.getTypeId(var4 - 1, var5, var6 - var11) == 0) || (var3.getTypeId(var4 - 1, var5, var6 - var11) == 78))
/*     */             {
/* 181 */               var3.setTypeId(var4 - 1, var5, var6 - var11, 8);
/*     */             }
/*     */ 
/* 184 */             if ((var3.getTypeId(var4 + 1, var5, var6 - var11) == 0) || (var3.getTypeId(var4 + 1, var5, var6 - var11) == 78))
/*     */             {
/* 186 */               var3.setTypeId(var4 + 1, var5, var6 - var11, 8);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 191 */       else if (var9 == 5.0D)
/*     */       {
/* 193 */         for (int var11 = 0; var11 <= chargeLevel(var1); var11++)
/*     */         {
/* 195 */           if ((var3.getTypeId(var4 + var11, var5, var6) == 0) || (var3.getTypeId(var4 + var11, var5, var6) == 78))
/*     */           {
/* 197 */             var3.setTypeId(var4 + var11, var5, var6, 8);
/*     */           }
/*     */ 
/* 200 */           if (chargeLevel(var1) == 7)
/*     */           {
/* 202 */             if ((var3.getTypeId(var4 + var11, var5, var6 - 1) == 0) || (var3.getTypeId(var4 + var11, var5, var6 - 1) == 78))
/*     */             {
/* 204 */               var3.setTypeId(var4 + var11, var5, var6 - 1, 8);
/*     */             }
/*     */ 
/* 207 */             if ((var3.getTypeId(var4 + var11, var5, var6 + 1) == 0) || (var3.getTypeId(var4 + var11, var5, var6 + 1) == 78))
/*     */             {
/* 209 */               var3.setTypeId(var4 + var11, var5, var6 + 1, 8);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 215 */       resetCharge(var1, var3, var2, false);
/* 216 */       return true;
/*     */     }
/*     */ 
/* 221 */     if (chargeLevel(var1) < 1)
/*     */     {
/* 223 */       if (var3.getTypeId(var4, var5, var6) == Block.SNOW.id)
/*     */       {
/* 225 */         var7 = 0;
/*     */       }
/*     */       else
/*     */       {
/* 229 */         if (var7 == 0)
/*     */         {
/* 231 */           var5--;
/*     */         }
/*     */ 
/* 234 */         if (var7 == 1)
/*     */         {
/* 236 */           var5++;
/*     */         }
/*     */ 
/* 239 */         if (var7 == 2)
/*     */         {
/* 241 */           var6--;
/*     */         }
/*     */ 
/* 244 */         if (var7 == 3)
/*     */         {
/* 246 */           var6++;
/*     */         }
/*     */ 
/* 249 */         if (var7 == 4)
/*     */         {
/* 251 */           var4--;
/*     */         }
/*     */ 
/* 254 */         if (var7 == 5)
/*     */         {
/* 256 */           var4++;
/*     */         }
/*     */       }
/*     */ 
/* 260 */       if (var5 == 127)
/*     */       {
/* 262 */         return false;
/*     */       }
/*     */ 
/* 265 */       if (var3.mayPlace(8, var4, var5, var6, false, var7))
/*     */       {
/* 267 */         if (var3.setTypeIdAndData(var4, var5, var6, 8, filterData(var1.getData())))
/*     */         {
/* 269 */           Block.byId[8].postPlace(var3, var4, var5, var6, var7);
/* 270 */           Block.byId[8].postPlace(var3, var4, var5, var6, var2);
/* 271 */           var3.makeSound(var4 + 0.5F, var5 + 0.5F, var6 + 0.5F, var8.stepSound.getName(), (var8.stepSound.getVolume1() + 1.0F) / 2.0F, var8.stepSound.getVolume2() * 0.8F);
/*     */         }
/*     */ 
/* 274 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 278 */     return false;
/*     */   }
/*     */ 
/*     */   public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 288 */     if (EEProxy.isClient(var2))
/*     */     {
/* 290 */       return var1;
/*     */     }
/*     */ 
/* 294 */     float var4 = 1.0F;
/* 295 */     float var5 = var3.lastPitch + (var3.pitch - var3.lastPitch) * var4;
/* 296 */     float var6 = var3.lastYaw + (var3.yaw - var3.lastYaw) * var4;
/* 297 */     double var7 = var3.lastX + (var3.locX - var3.lastX) * var4;
/* 298 */     double var9 = var3.lastY + (var3.locY - var3.lastY) * var4 + 1.62D - var3.height;
/* 299 */     double var11 = var3.lastZ + (var3.locZ - var3.lastZ) * var4;
/* 300 */     Vec3D var13 = Vec3D.create(var7, var9, var11);
/* 301 */     float var14 = MathHelper.cos(-var6 * 0.01745329F - 3.141593F);
/* 302 */     float var15 = MathHelper.sin(-var6 * 0.01745329F - 3.141593F);
/* 303 */     float var16 = -MathHelper.cos(-var5 * 0.01745329F);
/* 304 */     float var17 = MathHelper.sin(-var5 * 0.01745329F);
/* 305 */     float var18 = var15 * var16;
/* 306 */     float var20 = var14 * var16;
/* 307 */     double var21 = 5.0D;
/* 308 */     Vec3D var23 = var13.add(var18 * var21, var17 * var21, var20 * var21);
/* 309 */     MovingObjectPosition var24 = var2.rayTrace(var13, var23, false);
/*     */ 
/* 311 */     if (var24 == null)
/*     */     {
/* 313 */       return var1;
/*     */     }
/*     */ 
/* 317 */     if (var24.type == EnumMovingObjectType.TILE)
/*     */     {
/* 319 */       int var25 = var24.b;
/* 320 */       int var26 = var24.c;
/* 321 */       int var27 = var24.d;
/*     */ 
/* 323 */       if (!var2.a(var3, var25, var26, var27))
/*     */       {
/* 325 */         return var1;
/*     */       }
/*     */ 
/* 328 */       if (var24.face == 0)
/*     */       {
/* 330 */         var26--;
/*     */       }
/*     */ 
/* 333 */       if (var24.face == 1)
/*     */       {
/* 335 */         var26++;
/*     */       }
/*     */ 
/* 338 */       if (var24.face == 2)
/*     */       {
/* 340 */         var27--;
/*     */       }
/*     */ 
/* 343 */       if (var24.face == 3)
/*     */       {
/* 345 */         var27++;
/*     */       }
/*     */ 
/* 348 */       if (var24.face == 4)
/*     */       {
/* 350 */         var25--;
/*     */       }
/*     */ 
/* 353 */       if (var24.face == 5)
/*     */       {
/* 355 */         var25++;
/*     */       }
/*     */ 
/* 358 */       if (!var3.d(var25, var26, var27))
/*     */       {
/* 360 */         return var1;
/*     */       }
/*     */ 
/* 363 */       if ((var2.isEmpty(var25, var26, var27)) || (!var2.getMaterial(var25, var26, var27).isBuildable()))
/*     */       {
/* 365 */         var2.setTypeIdAndData(var25, var26, var27, 8, 0);
/*     */       }
/*     */     }
/*     */ 
/* 369 */     return var1;
/*     */   }
/*     */ 
/*     */   public boolean onItemUseFirst(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 376 */     return false;
/*     */   }
/*     */ 
/*     */   public void ConsumeReagent(ItemStack var1, EntityHuman var2, boolean var3) {
/*     */   }
/*     */ 
/*     */   public void doPassive(ItemStack var1, World var2, EntityHuman var3) {
/* 383 */     if (var3.getAirTicks() < 0)
/*     */     {
/* 385 */       var3.setAirTicks(0);
/*     */     }
/*     */ 
/* 388 */     AxisAlignedBB var4 = AxisAlignedBB.b(var3.boundingBox.a, var3.boundingBox.b - 0.2D, var3.boundingBox.c, var3.boundingBox.d, var3.boundingBox.e, var3.boundingBox.f);
/* 389 */     EEBase.updatePlayerInWater(var3, var2.b(var4, Material.WATER));
/*     */   }
/*     */   public void doActive(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doHeld(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doRelease(ItemStack var1, World var2, EntityHuman var3) {
/* 398 */     var3.C_();
/* 399 */     var2.makeSound(var3, "waterball", 0.6F, 1.0F);
/* 400 */     var2.addEntity(new EntityWaterEssence(var2, var3));
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
 * Qualified Name:     ee.ItemEvertide
 * JD-Core Version:    0.6.2
 */