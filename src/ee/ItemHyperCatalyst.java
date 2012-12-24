/*     */ package ee;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class ItemHyperCatalyst extends ItemEECharged
/*     */ {
/*     */   public ItemHyperCatalyst(int var1)
/*     */   {
/*  15 */     super(var1, 7);
/*     */   }
/*     */ 
/*     */   public void doBreak(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*  20 */     int var4 = 1;
/*     */ 
/*  22 */     if (chargeLevel(var1) > 2)
/*     */     {
/*  24 */       var4++;
/*     */     }
/*     */ 
/*  27 */     if (chargeLevel(var1) > 4)
/*     */     {
/*  29 */       var4++;
/*     */     }
/*     */ 
/*  32 */     if (chargeLevel(var1) > 6)
/*     */     {
/*  34 */       var4++;
/*     */     }
/*     */ 
/*  37 */     var2.makeSound(var3, "wall", 1.0F, 1.0F);
/*  38 */     var2.addEntity(new EntityHyperkinesis(var2, var3, (chargeLevel(var1) + 1) / 2, var4));
/*     */   }
/*     */ 
/*     */   public void doBreak2(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*  43 */     if (getCooldown(var1) <= 0)
/*     */     {
/*  45 */       initCooldown(var1);
/*  46 */       var2.makeSound(var3, "destruct", 0.5F, 1.0F);
/*  47 */       var3.C_();
/*  48 */       cleanDroplist(var1);
/*  49 */       boolean var4 = true;
/*  50 */       double var5 = EEBase.direction(var3);
/*  51 */       int var7 = (int)EEBase.playerX(var3);
/*  52 */       int var8 = (int)(EEBase.playerY(var3) + var3.getHeadHeight());
/*  53 */       int var9 = (int)EEBase.playerZ(var3);
/*     */ 
/*  58 */       if (var5 == 0.0D)
/*     */       {
/*  60 */         for (int var10 = -2; var10 >= -((chargeLevel(var1) + 1) * (chargeLevel(var1) + 1)) - 1; var10--)
/*     */         {
/*  62 */           for (int var11 = -1; var11 <= 1; var11++)
/*     */           {
/*  64 */             for (int var12 = -1; var12 <= 1; var12++)
/*     */             {
/*  66 */               if (getFuelRemaining(var1) < 1)
/*     */               {
/*  68 */                 if ((var10 == -((chargeLevel(var1) + 1) * (chargeLevel(var1) + 1)) - 1) && (var11 == 1) && (var12 == 1))
/*     */                 {
/*  70 */                   ConsumeReagent(var1, var3, var4);
/*  71 */                   var4 = false;
/*     */                 }
/*     */                 else
/*     */                 {
/*  75 */                   ConsumeReagent(var1, var3, false);
/*     */                 }
/*     */               }
/*     */ 
/*  79 */               if (getFuelRemaining(var1) > 0)
/*     */               {
/*  81 */                 breakBlock(var1, var2, var7 + var11, var8 + var10, var9 + var12);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*  87 */       else if (var5 == 1.0D)
/*     */       {
/*  89 */         for (int var10 = 2; var10 <= (chargeLevel(var1) + 1) * (chargeLevel(var1) + 1) + 1; var10++)
/*     */         {
/*  91 */           for (int var11 = -1; var11 <= 1; var11++)
/*     */           {
/*  93 */             for (int var12 = -1; var12 <= 1; var12++)
/*     */             {
/*  95 */               if (getFuelRemaining(var1) < 1)
/*     */               {
/*  97 */                 if ((var10 == (chargeLevel(var1) + 1) * (chargeLevel(var1) + 1) + 1) && (var11 == 1) && (var12 == 1))
/*     */                 {
/*  99 */                   ConsumeReagent(var1, var3, var4);
/* 100 */                   var4 = false;
/*     */                 }
/*     */                 else
/*     */                 {
/* 104 */                   ConsumeReagent(var1, var3, false);
/*     */                 }
/*     */               }
/*     */ 
/* 108 */               if (getFuelRemaining(var1) > 0)
/*     */               {
/* 110 */                 breakBlock(var1, var2, var7 + var11, var8 + var10, var9 + var12);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 116 */       else if (var5 == 2.0D)
/*     */       {
/* 118 */         for (int var10 = 1; var10 <= (chargeLevel(var1) + 1) * (chargeLevel(var1) + 1); var10++)
/*     */         {
/* 120 */           for (int var11 = -1; var11 <= 1; var11++)
/*     */           {
/* 122 */             for (int var12 = -1; var12 <= 1; var12++)
/*     */             {
/* 124 */               if (getFuelRemaining(var1) < 1)
/*     */               {
/* 126 */                 if ((var10 == (chargeLevel(var1) + 1) * (chargeLevel(var1) + 1)) && (var11 == 1) && (var12 == 1))
/*     */                 {
/* 128 */                   ConsumeReagent(var1, var3, var4);
/* 129 */                   var4 = false;
/*     */                 }
/*     */                 else
/*     */                 {
/* 133 */                   ConsumeReagent(var1, var3, false);
/*     */                 }
/*     */               }
/*     */ 
/* 137 */               if (getFuelRemaining(var1) > 0)
/*     */               {
/* 139 */                 breakBlock(var1, var2, var7 + var11, var8 + var12, var9 + var10);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 145 */       else if (var5 == 3.0D)
/*     */       {
/* 147 */         for (int var10 = -1; var10 >= -((chargeLevel(var1) + 1) * (chargeLevel(var1) + 1)); var10--)
/*     */         {
/* 149 */           for (int var11 = -1; var11 <= 1; var11++)
/*     */           {
/* 151 */             for (int var12 = -1; var12 <= 1; var12++)
/*     */             {
/* 153 */               if (getFuelRemaining(var1) < 1)
/*     */               {
/* 155 */                 if ((var10 == -((chargeLevel(var1) + 1) * (chargeLevel(var1) + 1))) && (var11 == 1) && (var12 == 1))
/*     */                 {
/* 157 */                   ConsumeReagent(var1, var3, var4);
/* 158 */                   var4 = false;
/*     */                 }
/*     */                 else
/*     */                 {
/* 162 */                   ConsumeReagent(var1, var3, false);
/*     */                 }
/*     */               }
/*     */ 
/* 166 */               if (getFuelRemaining(var1) > 0)
/*     */               {
/* 168 */                 breakBlock(var1, var2, var7 + var10, var8 + var12, var9 + var11);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 174 */       else if (var5 == 4.0D)
/*     */       {
/* 176 */         for (int var10 = -1; var10 >= -((chargeLevel(var1) + 1) * (chargeLevel(var1) + 1)); var10--)
/*     */         {
/* 178 */           for (int var11 = -1; var11 <= 1; var11++)
/*     */           {
/* 180 */             for (int var12 = -1; var12 <= 1; var12++)
/*     */             {
/* 182 */               if (getFuelRemaining(var1) < 1)
/*     */               {
/* 184 */                 if ((var10 == -((chargeLevel(var1) + 1) * (chargeLevel(var1) + 1))) && (var11 == 1) && (var12 == 1))
/*     */                 {
/* 186 */                   ConsumeReagent(var1, var3, var4);
/* 187 */                   var4 = false;
/*     */                 }
/*     */                 else
/*     */                 {
/* 191 */                   ConsumeReagent(var1, var3, false);
/*     */                 }
/*     */               }
/*     */ 
/* 195 */               if (getFuelRemaining(var1) > 0)
/*     */               {
/* 197 */                 breakBlock(var1, var2, var7 + var11, var8 + var12, var9 + var10);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 203 */       else if (var5 == 5.0D)
/*     */       {
/* 205 */         for (int var10 = 1; var10 <= (chargeLevel(var1) + 1) * (chargeLevel(var1) + 1); var10++)
/*     */         {
/* 207 */           for (int var11 = -1; var11 <= 1; var11++)
/*     */           {
/* 209 */             for (int var12 = -1; var12 <= 1; var12++)
/*     */             {
/* 211 */               if (getFuelRemaining(var1) < 1)
/*     */               {
/* 213 */                 if ((var10 == (chargeLevel(var1) + 1) * (chargeLevel(var1) + 1)) && (var11 == 1) && (var12 == 1))
/*     */                 {
/* 215 */                   ConsumeReagent(var1, var3, var4);
/* 216 */                   var4 = false;
/*     */                 }
/*     */                 else
/*     */                 {
/* 220 */                   ConsumeReagent(var1, var3, false);
/*     */                 }
/*     */               }
/*     */ 
/* 224 */               if (getFuelRemaining(var1) > 0)
/*     */               {
/* 226 */                 breakBlock(var1, var2, var7 + var10, var8 + var12, var9 + var11);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 233 */       ejectDropList(var2, var1, var3.locX, var3.locY, var3.locZ);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 243 */     if (EEProxy.isClient(var3))
/*     */     {
/* 245 */       return false;
/*     */     }
/* 247 */     if (getCooldown(var1) > 0)
/*     */     {
/* 249 */       return false;
/*     */     }
/*     */ 
/* 253 */     initCooldown(var1);
/* 254 */     var3.makeSound(var2, "destruct", 0.5F, 1.0F);
/* 255 */     cleanDroplist(var1);
/* 256 */     boolean var8 = true;
/* 257 */     int var9 = var4;
/* 258 */     int var10 = var5;
/* 259 */     int var11 = var6;
/*     */ 
/* 264 */     if (var7 == 0)
/*     */     {
/* 266 */       for (int var12 = 0; var12 < (chargeLevel(var1) + 1) * (chargeLevel(var1) + 1); var12++)
/*     */       {
/* 268 */         for (int var13 = -1; var13 <= 1; var13++)
/*     */         {
/* 270 */           for (int var14 = -1; var14 <= 1; var14++)
/*     */           {
/* 272 */             if (getFuelRemaining(var1) < 1)
/*     */             {
/* 274 */               if ((var12 == (chargeLevel(var1) + 1) * (chargeLevel(var1) + 1) - 1) && (var13 == 1) && (var14 == 1))
/*     */               {
/* 276 */                 ConsumeReagent(var1, var2, var8);
/* 277 */                 var8 = false;
/*     */               }
/*     */               else
/*     */               {
/* 281 */                 ConsumeReagent(var1, var2, false);
/*     */               }
/*     */             }
/*     */ 
/* 285 */             if (getFuelRemaining(var1) > 0)
/*     */             {
/* 287 */               breakBlock(var1, var3, var9 + var13, var10 + var12, var11 + var14);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 293 */     else if (var7 == 1)
/*     */     {
/* 295 */       for (int var12 = 0; var12 > -((chargeLevel(var1) + 1) * (chargeLevel(var1) + 1)); var12--)
/*     */       {
/* 297 */         for (int var13 = -1; var13 <= 1; var13++)
/*     */         {
/* 299 */           for (int var14 = -1; var14 <= 1; var14++)
/*     */           {
/* 301 */             if (getFuelRemaining(var1) < 1)
/*     */             {
/* 303 */               if ((var12 == -((chargeLevel(var1) + 1) * (chargeLevel(var1) + 1)) + 1) && (var13 == 1) && (var14 == 1))
/*     */               {
/* 305 */                 ConsumeReagent(var1, var2, var8);
/* 306 */                 var8 = false;
/*     */               }
/*     */               else
/*     */               {
/* 310 */                 ConsumeReagent(var1, var2, false);
/*     */               }
/*     */             }
/*     */ 
/* 314 */             if (getFuelRemaining(var1) > 0)
/*     */             {
/* 316 */               breakBlock(var1, var3, var9 + var13, var10 + var12, var11 + var14);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 322 */     else if (var7 == 2)
/*     */     {
/* 324 */       for (int var12 = 0; var12 < (chargeLevel(var1) + 1) * (chargeLevel(var1) + 1); var12++)
/*     */       {
/* 326 */         for (int var13 = -1; var13 <= 1; var13++)
/*     */         {
/* 328 */           for (int var14 = -1; var14 <= 1; var14++)
/*     */           {
/* 330 */             if (getFuelRemaining(var1) < 1)
/*     */             {
/* 332 */               if ((var12 == (chargeLevel(var1) + 1) * (chargeLevel(var1) + 1) - 1) && (var13 == 1) && (var14 == 1))
/*     */               {
/* 334 */                 ConsumeReagent(var1, var2, var8);
/* 335 */                 var8 = false;
/*     */               }
/*     */               else
/*     */               {
/* 339 */                 ConsumeReagent(var1, var2, false);
/*     */               }
/*     */             }
/*     */ 
/* 343 */             if (getFuelRemaining(var1) > 0)
/*     */             {
/* 345 */               breakBlock(var1, var3, var9 + var13, var10 + var14, var11 + var12);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 351 */     else if (var7 == 3)
/*     */     {
/* 353 */       for (int var12 = 0; var12 > -((chargeLevel(var1) + 1) * (chargeLevel(var1) + 1)); var12--)
/*     */       {
/* 355 */         for (int var13 = -1; var13 <= 1; var13++)
/*     */         {
/* 357 */           for (int var14 = -1; var14 <= 1; var14++)
/*     */           {
/* 359 */             if (getFuelRemaining(var1) < 1)
/*     */             {
/* 361 */               if ((var12 == -((chargeLevel(var1) + 1) * (chargeLevel(var1) + 1)) + 1) && (var13 == 1) && (var14 == 1))
/*     */               {
/* 363 */                 ConsumeReagent(var1, var2, var8);
/* 364 */                 var8 = false;
/*     */               }
/*     */               else
/*     */               {
/* 368 */                 ConsumeReagent(var1, var2, false);
/*     */               }
/*     */             }
/*     */ 
/* 372 */             if (getFuelRemaining(var1) > 0)
/*     */             {
/* 374 */               breakBlock(var1, var3, var9 + var13, var10 + var14, var11 + var12);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 380 */     else if (var7 == 4)
/*     */     {
/* 382 */       for (int var12 = 0; var12 < (chargeLevel(var1) + 1) * (chargeLevel(var1) + 1); var12++)
/*     */       {
/* 384 */         for (int var13 = -1; var13 <= 1; var13++)
/*     */         {
/* 386 */           for (int var14 = -1; var14 <= 1; var14++)
/*     */           {
/* 388 */             if (getFuelRemaining(var1) < 1)
/*     */             {
/* 390 */               if ((var12 == (chargeLevel(var1) + 1) * (chargeLevel(var1) + 1) - 1) && (var13 == 1) && (var14 == 1))
/*     */               {
/* 392 */                 ConsumeReagent(var1, var2, var8);
/* 393 */                 var8 = false;
/*     */               }
/*     */               else
/*     */               {
/* 397 */                 ConsumeReagent(var1, var2, false);
/*     */               }
/*     */             }
/*     */ 
/* 401 */             if (getFuelRemaining(var1) > 0)
/*     */             {
/* 403 */               breakBlock(var1, var3, var9 + var12, var10 + var14, var11 + var13);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 409 */     else if (var7 == 5)
/*     */     {
/* 411 */       for (int var12 = 0; var12 > -((chargeLevel(var1) + 1) * (chargeLevel(var1) + 1)); var12--)
/*     */       {
/* 413 */         for (int var13 = -1; var13 <= 1; var13++)
/*     */         {
/* 415 */           for (int var14 = -1; var14 <= 1; var14++)
/*     */           {
/* 417 */             if (getFuelRemaining(var1) < 1)
/*     */             {
/* 419 */               if ((var12 == -((chargeLevel(var1) + 1) * (chargeLevel(var1) + 1)) + 1) && (var14 == 1) && (var13 == 1))
/*     */               {
/* 421 */                 ConsumeReagent(var1, var2, var8);
/* 422 */                 var8 = false;
/*     */               }
/*     */               else
/*     */               {
/* 426 */                 ConsumeReagent(var1, var2, false);
/*     */               }
/*     */             }
/*     */ 
/* 430 */             if (getFuelRemaining(var1) > 0)
/*     */             {
/* 432 */               breakBlock(var1, var3, var9 + var12, var10 + var14, var11 + var13);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 439 */     ejectDropList(var3, var1, var4, var5, var6);
/* 440 */     return true;
/*     */   }
/*     */ 
/*     */   public void breakBlock(ItemStack var1, World var2, int var3, int var4, int var5)
/*     */   {
/* 446 */     int var6 = var2.getTypeId(var3, var4, var5);
/* 447 */     int var7 = var2.getData(var3, var4, var5);
/*     */ 
/* 449 */     if (Block.byId[var6] != null)
/*     */     {
/* 451 */       if ((var6 != 0) && (Block.byId[var6].getHardness(var7) >= 0.0F) && (Block.byId[var6].getHardness(var7) <= 10.0F))
/*     */       {
/* 453 */         ArrayList var8 = Block.byId[var6].getBlockDropped(var2, var3, var4, var5, var7, 0);
/* 454 */         Iterator var9 = var8.iterator();
/*     */ 
/* 456 */         while (var9.hasNext())
/*     */         {
/* 458 */           ItemStack var10 = (ItemStack)var9.next();
/* 459 */           addToDroplist(var1, var10);
/*     */         }
/*     */ 
/* 462 */         setShort(var1, "fuelRemaining", getFuelRemaining(var1) - 1);
/* 463 */         var2.setTypeId(var3, var4, var5, 0);
/*     */ 
/* 465 */         if (var2.random.nextInt(8) == 0)
/*     */         {
/* 467 */           var2.a("largesmoke", var3, var4, var5, 0.0D, 0.0D, 0.0D);
/*     */         }
/*     */ 
/* 470 */         if (var2.random.nextInt(8) == 0)
/*     */         {
/* 472 */           var2.a("explode", var3, var4, var5, 0.0D, 0.0D, 0.0D);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 483 */     if (EEProxy.isClient(var2))
/*     */     {
/* 485 */       return var1;
/*     */     }
/*     */ 
/* 489 */     doBreak2(var1, var2, var3);
/* 490 */     return var1;
/*     */   }
/*     */ 
/*     */   public void doToggle(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void doRelease(ItemStack var1, World var2, EntityHuman var3) {
/* 498 */     if (!EEProxy.isClient(var2))
/*     */     {
/* 500 */       doBreak(var1, var2, var3);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doPassive(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 506 */     decCooldown(var1);
/*     */   }
/*     */ 
/*     */   public void setCooldown(ItemStack var1, int var2)
/*     */   {
/* 511 */     setInteger(var1, "cooldown", var2);
/*     */   }
/*     */ 
/*     */   public int getCooldown(ItemStack var1)
/*     */   {
/* 516 */     return getInteger(var1, "cooldown");
/*     */   }
/*     */ 
/*     */   public void decCooldown(ItemStack var1)
/*     */   {
/* 521 */     setCooldown(var1, getCooldown(var1) - 1);
/*     */   }
/*     */ 
/*     */   public void initCooldown(ItemStack var1)
/*     */   {
/* 526 */     setCooldown(var1, 5);
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemHyperCatalyst
 * JD-Core Version:    0.6.2
 */