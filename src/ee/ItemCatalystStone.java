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
/*     */ public class ItemCatalystStone extends ItemEECharged
/*     */ {
/*     */   public ItemCatalystStone(int var1)
/*     */   {
/*  15 */     super(var1, 3);
/*     */   }
/*     */ 
/*     */   public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*  23 */     if (EEProxy.isClient(var2))
/*     */     {
/*  25 */       return var1;
/*     */     }
/*     */ 
/*  29 */     doRelease(var1, var2, var3);
/*  30 */     return var1;
/*     */   }
/*     */ 
/*     */   public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/*  40 */     if (EEProxy.isClient(var3))
/*     */     {
/*  42 */       return false;
/*     */     }
/*  44 */     if (getCooldown(var1) > 0)
/*     */     {
/*  46 */       return false;
/*     */     }
/*     */ 
/*  50 */     initCooldown(var1);
/*  51 */     var3.makeSound(var2, "destruct", 0.5F, 1.0F);
/*  52 */     cleanDroplist(var1);
/*  53 */     boolean var8 = true;
/*  54 */     int var9 = var4;
/*  55 */     int var10 = var5;
/*  56 */     int var11 = var6;
/*     */ 
/*  61 */     if (var7 == 0)
/*     */     {
/*  63 */       for (int var12 = 0; var12 < (chargeLevel(var1) + 1) * (chargeLevel(var1) + 1); var12++)
/*     */       {
/*  65 */         for (int var13 = -1; var13 <= 1; var13++)
/*     */         {
/*  67 */           for (int var14 = -1; var14 <= 1; var14++)
/*     */           {
/*  69 */             if (getFuelRemaining(var1) < 1)
/*     */             {
/*  71 */               if ((var12 == (chargeLevel(var1) + 1) * (chargeLevel(var1) + 1) - 1) && (var13 == 1) && (var14 == 1))
/*     */               {
/*  73 */                 ConsumeReagent(var1, var2, false);
/*  74 */                 var8 = false;
/*     */               }
/*     */               else
/*     */               {
/*  78 */                 ConsumeReagent(var1, var2, false);
/*     */               }
/*     */             }
/*     */ 
/*  82 */             if (getFuelRemaining(var1) > 0)
/*     */             {
/*  84 */               breakBlock(var1, var3, var9 + var13, var10 + var12, var11 + var14);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*  90 */     else if (var7 == 1)
/*     */     {
/*  92 */       for (int var12 = 0; var12 > -((chargeLevel(var1) + 1) * (chargeLevel(var1) + 1)); var12--)
/*     */       {
/*  94 */         for (int var13 = -1; var13 <= 1; var13++)
/*     */         {
/*  96 */           for (int var14 = -1; var14 <= 1; var14++)
/*     */           {
/*  98 */             if (getFuelRemaining(var1) < 1)
/*     */             {
/* 100 */               if ((var12 == -((chargeLevel(var1) + 1) * (chargeLevel(var1) + 1)) + 1) && (var13 == 1) && (var14 == 1))
/*     */               {
/* 102 */                 ConsumeReagent(var1, var2, false);
/* 103 */                 var8 = false;
/*     */               }
/*     */               else
/*     */               {
/* 107 */                 ConsumeReagent(var1, var2, false);
/*     */               }
/*     */             }
/*     */ 
/* 111 */             if (getFuelRemaining(var1) > 0)
/*     */             {
/* 113 */               breakBlock(var1, var3, var9 + var13, var10 + var12, var11 + var14);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 119 */     else if (var7 == 2)
/*     */     {
/* 121 */       for (int var12 = 0; var12 < (chargeLevel(var1) + 1) * (chargeLevel(var1) + 1); var12++)
/*     */       {
/* 123 */         for (int var13 = -1; var13 <= 1; var13++)
/*     */         {
/* 125 */           for (int var14 = -1; var14 <= 1; var14++)
/*     */           {
/* 127 */             if (getFuelRemaining(var1) < 1)
/*     */             {
/* 129 */               if ((var12 == (chargeLevel(var1) + 1) * (chargeLevel(var1) + 1) - 1) && (var13 == 1) && (var14 == 1))
/*     */               {
/* 131 */                 ConsumeReagent(var1, var2, false);
/* 132 */                 var8 = false;
/*     */               }
/*     */               else
/*     */               {
/* 136 */                 ConsumeReagent(var1, var2, false);
/*     */               }
/*     */             }
/*     */ 
/* 140 */             if (getFuelRemaining(var1) > 0)
/*     */             {
/* 142 */               breakBlock(var1, var3, var9 + var13, var10 + var14, var11 + var12);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 148 */     else if (var7 == 3)
/*     */     {
/* 150 */       for (int var12 = 0; var12 > -((chargeLevel(var1) + 1) * (chargeLevel(var1) + 1)); var12--)
/*     */       {
/* 152 */         for (int var13 = -1; var13 <= 1; var13++)
/*     */         {
/* 154 */           for (int var14 = -1; var14 <= 1; var14++)
/*     */           {
/* 156 */             if (getFuelRemaining(var1) < 1)
/*     */             {
/* 158 */               if ((var12 == -((chargeLevel(var1) + 1) * (chargeLevel(var1) + 1)) + 1) && (var13 == 1) && (var14 == 1))
/*     */               {
/* 160 */                 ConsumeReagent(var1, var2, false);
/* 161 */                 var8 = false;
/*     */               }
/*     */               else
/*     */               {
/* 165 */                 ConsumeReagent(var1, var2, false);
/*     */               }
/*     */             }
/*     */ 
/* 169 */             if (getFuelRemaining(var1) > 0)
/*     */             {
/* 171 */               breakBlock(var1, var3, var9 + var13, var10 + var14, var11 + var12);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 177 */     else if (var7 == 4)
/*     */     {
/* 179 */       for (int var12 = 0; var12 < (chargeLevel(var1) + 1) * (chargeLevel(var1) + 1); var12++)
/*     */       {
/* 181 */         for (int var13 = -1; var13 <= 1; var13++)
/*     */         {
/* 183 */           for (int var14 = -1; var14 <= 1; var14++)
/*     */           {
/* 185 */             if (getFuelRemaining(var1) < 1)
/*     */             {
/* 187 */               if ((var12 == (chargeLevel(var1) + 1) * (chargeLevel(var1) + 1) - 1) && (var13 == 1) && (var14 == 1))
/*     */               {
/* 189 */                 ConsumeReagent(var1, var2, false);
/* 190 */                 var8 = false;
/*     */               }
/*     */               else
/*     */               {
/* 194 */                 ConsumeReagent(var1, var2, false);
/*     */               }
/*     */             }
/*     */ 
/* 198 */             if (getFuelRemaining(var1) > 0)
/*     */             {
/* 200 */               breakBlock(var1, var3, var9 + var12, var10 + var14, var11 + var13);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 206 */     else if (var7 == 5)
/*     */     {
/* 208 */       for (int var12 = 0; var12 > -((chargeLevel(var1) + 1) * (chargeLevel(var1) + 1)); var12--)
/*     */       {
/* 210 */         for (int var13 = -1; var13 <= 1; var13++)
/*     */         {
/* 212 */           for (int var14 = -1; var14 <= 1; var14++)
/*     */           {
/* 214 */             if (getFuelRemaining(var1) < 1)
/*     */             {
/* 216 */               if ((var12 == -((chargeLevel(var1) + 1) * (chargeLevel(var1) + 1)) + 1) && (var14 == 1) && (var13 == 1))
/*     */               {
/* 218 */                 ConsumeReagent(var1, var2, false);
/* 219 */                 var8 = false;
/*     */               }
/*     */               else
/*     */               {
/* 223 */                 ConsumeReagent(var1, var2, false);
/*     */               }
/*     */             }
/*     */ 
/* 227 */             if (getFuelRemaining(var1) > 0)
/*     */             {
/* 229 */               breakBlock(var1, var3, var9 + var12, var10 + var14, var11 + var13);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 236 */     ejectDropList(var3, var1, var4, var5, var6);
/* 237 */     return true;
/*     */   }
/*     */ 
/*     */   public void breakBlock(ItemStack var1, World var2, int var3, int var4, int var5)
/*     */   {
/* 243 */     int var6 = var2.getTypeId(var3, var4, var5);
/* 244 */     int var7 = var2.getData(var3, var4, var5);
/*     */ 
/* 246 */     if (Block.byId[var6] != null)
/*     */     {
/* 248 */       if ((var6 != 0) && (Block.byId[var6].getHardness(var7) >= 0.0F) && (Block.byId[var6].getHardness(var7) <= 10.0F))
/*     */       {
/* 250 */         ArrayList var8 = Block.byId[var6].getBlockDropped(var2, var3, var4, var5, var7, 0);
/* 251 */         Iterator var9 = var8.iterator();
/*     */ 
/* 253 */         while (var9.hasNext())
/*     */         {
/* 255 */           ItemStack var10 = (ItemStack)var9.next();
/* 256 */           addToDroplist(var1, var10);
/*     */         }
/*     */ 
/* 259 */         setShort(var1, "fuelRemaining", getFuelRemaining(var1) - 1);
/* 260 */         var2.setTypeId(var3, var4, var5, 0);
/*     */ 
/* 262 */         if (var2.random.nextInt(8) == 0)
/*     */         {
/* 264 */           var2.a("largesmoke", var3, var4, var5, 0.0D, 0.0D, 0.0D);
/*     */         }
/*     */ 
/* 267 */         if (var2.random.nextInt(8) == 0)
/*     */         {
/* 269 */           var2.a("explode", var3, var4, var5, 0.0D, 0.0D, 0.0D);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doPassive(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 277 */     decCooldown(var1);
/*     */   }
/*     */   public void doActive(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doHeld(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doRelease(ItemStack var1, World var2, EntityHuman var3) {
/* 286 */     if (getCooldown(var1) <= 0)
/*     */     {
/* 288 */       initCooldown(var1);
/* 289 */       var2.makeSound(var3, "destruct", 0.5F, 1.0F);
/* 290 */       var3.C_();
/* 291 */       cleanDroplist(var1);
/* 292 */       boolean var4 = true;
/* 293 */       double var5 = EEBase.direction(var3);
/* 294 */       int var7 = (int)EEBase.playerX(var3);
/* 295 */       int var8 = (int)(EEBase.playerY(var3) + var3.getHeadHeight());
/* 296 */       int var9 = (int)EEBase.playerZ(var3);
/*     */ 
/* 301 */       if (var5 == 0.0D)
/*     */       {
/* 303 */         for (int var10 = -2; var10 >= -((chargeLevel(var1) + 1) * (chargeLevel(var1) + 1)) - 1; var10--)
/*     */         {
/* 305 */           for (int var11 = -1; var11 <= 1; var11++)
/*     */           {
/* 307 */             for (int var12 = -1; var12 <= 1; var12++)
/*     */             {
/* 309 */               if (getFuelRemaining(var1) < 1)
/*     */               {
/* 311 */                 if ((var10 == -((chargeLevel(var1) + 1) * (chargeLevel(var1) + 1)) - 1) && (var11 == 1) && (var12 == 1))
/*     */                 {
/* 313 */                   ConsumeReagent(var1, var3, var4);
/* 314 */                   var4 = false;
/*     */                 }
/*     */                 else
/*     */                 {
/* 318 */                   ConsumeReagent(var1, var3, false);
/*     */                 }
/*     */               }
/*     */ 
/* 322 */               if (getFuelRemaining(var1) > 0)
/*     */               {
/* 324 */                 breakBlock(var1, var2, var7 + var11, var8 + var10, var9 + var12);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 330 */       else if (var5 == 1.0D)
/*     */       {
/* 332 */         for (int var10 = 2; var10 <= (chargeLevel(var1) + 1) * (chargeLevel(var1) + 1) + 1; var10++)
/*     */         {
/* 334 */           for (int var11 = -1; var11 <= 1; var11++)
/*     */           {
/* 336 */             for (int var12 = -1; var12 <= 1; var12++)
/*     */             {
/* 338 */               if (getFuelRemaining(var1) < 1)
/*     */               {
/* 340 */                 if ((var10 == (chargeLevel(var1) + 1) * (chargeLevel(var1) + 1) + 1) && (var11 == 1) && (var12 == 1))
/*     */                 {
/* 342 */                   ConsumeReagent(var1, var3, var4);
/* 343 */                   var4 = false;
/*     */                 }
/*     */                 else
/*     */                 {
/* 347 */                   ConsumeReagent(var1, var3, false);
/*     */                 }
/*     */               }
/*     */ 
/* 351 */               if (getFuelRemaining(var1) > 0)
/*     */               {
/* 353 */                 breakBlock(var1, var2, var7 + var11, var8 + var10, var9 + var12);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 359 */       else if (var5 == 2.0D)
/*     */       {
/* 361 */         for (int var10 = 1; var10 <= (chargeLevel(var1) + 1) * (chargeLevel(var1) + 1); var10++)
/*     */         {
/* 363 */           for (int var11 = -1; var11 <= 1; var11++)
/*     */           {
/* 365 */             for (int var12 = -1; var12 <= 1; var12++)
/*     */             {
/* 367 */               if (getFuelRemaining(var1) < 1)
/*     */               {
/* 369 */                 if ((var10 == (chargeLevel(var1) + 1) * (chargeLevel(var1) + 1)) && (var11 == 1) && (var12 == 1))
/*     */                 {
/* 371 */                   ConsumeReagent(var1, var3, false);
/* 372 */                   var4 = false;
/*     */                 }
/*     */                 else
/*     */                 {
/* 376 */                   ConsumeReagent(var1, var3, false);
/*     */                 }
/*     */               }
/*     */ 
/* 380 */               if (getFuelRemaining(var1) > 0)
/*     */               {
/* 382 */                 breakBlock(var1, var2, var7 + var11, var8 + var12, var9 + var10);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 388 */       else if (var5 == 3.0D)
/*     */       {
/* 390 */         for (int var10 = -1; var10 >= -((chargeLevel(var1) + 1) * (chargeLevel(var1) + 1)); var10--)
/*     */         {
/* 392 */           for (int var11 = -1; var11 <= 1; var11++)
/*     */           {
/* 394 */             for (int var12 = -1; var12 <= 1; var12++)
/*     */             {
/* 396 */               if (getFuelRemaining(var1) < 1)
/*     */               {
/* 398 */                 if ((var10 == -((chargeLevel(var1) + 1) * (chargeLevel(var1) + 1))) && (var11 == 1) && (var12 == 1))
/*     */                 {
/* 400 */                   ConsumeReagent(var1, var3, false);
/* 401 */                   var4 = false;
/*     */                 }
/*     */                 else
/*     */                 {
/* 405 */                   ConsumeReagent(var1, var3, false);
/*     */                 }
/*     */               }
/*     */ 
/* 409 */               if (getFuelRemaining(var1) > 0)
/*     */               {
/* 411 */                 breakBlock(var1, var2, var7 + var10, var8 + var12, var9 + var11);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 417 */       else if (var5 == 4.0D)
/*     */       {
/* 419 */         for (int var10 = -1; var10 >= -((chargeLevel(var1) + 1) * (chargeLevel(var1) + 1)); var10--)
/*     */         {
/* 421 */           for (int var11 = -1; var11 <= 1; var11++)
/*     */           {
/* 423 */             for (int var12 = -1; var12 <= 1; var12++)
/*     */             {
/* 425 */               if (getFuelRemaining(var1) < 1)
/*     */               {
/* 427 */                 if ((var10 == -((chargeLevel(var1) + 1) * (chargeLevel(var1) + 1))) && (var11 == 1) && (var12 == 1))
/*     */                 {
/* 429 */                   ConsumeReagent(var1, var3, false);
/* 430 */                   var4 = false;
/*     */                 }
/*     */                 else
/*     */                 {
/* 434 */                   ConsumeReagent(var1, var3, false);
/*     */                 }
/*     */               }
/*     */ 
/* 438 */               if (getFuelRemaining(var1) > 0)
/*     */               {
/* 440 */                 breakBlock(var1, var2, var7 + var11, var8 + var12, var9 + var10);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 446 */       else if (var5 == 5.0D)
/*     */       {
/* 448 */         for (int var10 = 1; var10 <= (chargeLevel(var1) + 1) * (chargeLevel(var1) + 1); var10++)
/*     */         {
/* 450 */           for (int var11 = -1; var11 <= 1; var11++)
/*     */           {
/* 452 */             for (int var12 = -1; var12 <= 1; var12++)
/*     */             {
/* 454 */               if (getFuelRemaining(var1) < 1)
/*     */               {
/* 456 */                 if ((var10 == (chargeLevel(var1) + 1) * (chargeLevel(var1) + 1)) && (var11 == 1) && (var12 == 1))
/*     */                 {
/* 458 */                   ConsumeReagent(var1, var3, false);
/* 459 */                   var4 = false;
/*     */                 }
/*     */                 else
/*     */                 {
/* 463 */                   ConsumeReagent(var1, var3, false);
/*     */                 }
/*     */               }
/*     */ 
/* 467 */               if (getFuelRemaining(var1) > 0)
/*     */               {
/* 469 */                 breakBlock(var1, var2, var7 + var10, var8 + var12, var9 + var11);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 476 */       ejectDropList(var2, var1, var3.locX, var3.locY, var3.locZ);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doAlternate(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doLeftClick(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/* 486 */   public boolean canActivate() { return false; }
/*     */ 
/*     */   public void doToggle(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setCooldown(ItemStack var1, int var2) {
/* 493 */     setInteger(var1, "cooldown", var2);
/*     */   }
/*     */ 
/*     */   public int getCooldown(ItemStack var1)
/*     */   {
/* 498 */     return getInteger(var1, "cooldown");
/*     */   }
/*     */ 
/*     */   public void decCooldown(ItemStack var1)
/*     */   {
/* 503 */     setCooldown(var1, getCooldown(var1) - 1);
/*     */   }
/*     */ 
/*     */   public void initCooldown(ItemStack var1)
/*     */   {
/* 508 */     setCooldown(var1, 5);
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemCatalystStone
 * JD-Core Version:    0.6.2
 */