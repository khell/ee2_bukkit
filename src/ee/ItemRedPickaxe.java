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
/*     */ public class ItemRedPickaxe extends ItemRedTool
/*     */ {
/*  15 */   private static Block[] blocksEffectiveAgainst = { Block.COBBLESTONE, Block.DOUBLE_STEP, Block.STEP, Block.STONE, Block.SANDSTONE, Block.MOSSY_COBBLESTONE, Block.IRON_ORE, Block.IRON_BLOCK, Block.COAL_ORE, Block.GOLD_BLOCK, Block.GOLD_ORE, Block.DIAMOND_ORE, Block.DIAMOND_BLOCK, Block.REDSTONE_ORE, Block.GLOWING_REDSTONE_ORE, Block.ICE, Block.NETHERRACK, Block.LAPIS_ORE, Block.LAPIS_BLOCK, Block.OBSIDIAN };
/*     */ 
/*     */   protected ItemRedPickaxe(int var1)
/*     */   {
/*  19 */     super(var1, 3, 6, blocksEffectiveAgainst);
/*     */   }
/*     */ 
/*     */   public float getStrVsBlock(ItemStack var1, Block var2, int var3)
/*     */   {
/*  24 */     float var4 = 1.0F;
/*  25 */     return ((var2.id != EEBlock.eePedestal.id) || (var3 != 0)) && ((var2.id != EEBlock.eeStone.id) || ((var3 != 8) && (var3 != 9))) ? 16.0F / var4 : (var2.material != Material.STONE) && (var2.material != Material.ORE) ? super.getDestroySpeed(var1, var2) / var4 : ((var2.material == Material.STONE) || (var2.material == Material.ORE)) && (chargeLevel(var1) > 0) ? 16.0F + 16.0F * chargeLevel(var1) / var4 : 1200000.0F / var4;
/*     */   }
/*     */ 
/*     */   public boolean a(ItemStack var1, int var2, int var3, int var4, int var5, EntityLiving var6)
/*     */   {
/*  30 */     EntityHuman var7 = null;
/*     */ 
/*  32 */     if ((var6 instanceof EntityHuman))
/*     */     {
/*  34 */       var7 = (EntityHuman)var6;
/*     */ 
/*  36 */       if (EEBase.getToolMode(var7) != 0)
/*     */       {
/*  38 */         if (EEBase.getToolMode(var7) == 1)
/*     */         {
/*  40 */           doTallImpact(var7.world, var1, var7, var3, var4, var5, EEBase.direction(var7));
/*     */         }
/*  42 */         else if (EEBase.getToolMode(var7) == 2)
/*     */         {
/*  44 */           doWideImpact(var7.world, var1, var3, var4, var5, EEBase.heading(var7));
/*     */         }
/*  46 */         else if (EEBase.getToolMode(var7) == 3)
/*     */         {
/*  48 */           doLongImpact(var7.world, var1, var3, var4, var5, EEBase.direction(var7));
/*     */         }
/*     */       }
/*     */ 
/*  52 */       return true;
/*     */     }
/*     */ 
/*  56 */     return true;
/*     */   }
/*     */ 
/*     */   public void doLongImpact(World var1, ItemStack var2, int var3, int var4, int var5, double var6)
/*     */   {
/*  62 */     cleanDroplist(var2);
/*     */ 
/*  64 */     for (int var8 = 1; var8 <= 2; var8++)
/*     */     {
/*  66 */       int var9 = var3;
/*  67 */       int var10 = var4;
/*  68 */       int var11 = var5;
/*     */ 
/*  70 */       if (var6 == 0.0D)
/*     */       {
/*  72 */         var10 = var4 - var8;
/*     */       }
/*     */ 
/*  75 */       if (var6 == 1.0D)
/*     */       {
/*  77 */         var10 += var8;
/*     */       }
/*     */ 
/*  80 */       if (var6 == 2.0D)
/*     */       {
/*  82 */         var11 = var5 + var8;
/*     */       }
/*     */ 
/*  85 */       if (var6 == 3.0D)
/*     */       {
/*  87 */         var9 = var3 - var8;
/*     */       }
/*     */ 
/*  90 */       if (var6 == 4.0D)
/*     */       {
/*  92 */         var11 -= var8;
/*     */       }
/*     */ 
/*  95 */       if (var6 == 5.0D)
/*     */       {
/*  97 */         var9 += var8;
/*     */       }
/*     */ 
/* 100 */       int var12 = var1.getTypeId(var9, var10, var11);
/* 101 */       int var13 = var1.getData(var9, var10, var11);
/*     */ 
/* 103 */       if (canBreak(var12, var13))
/*     */       {
/* 105 */         scanBlockAndBreak(var1, var2, var9, var10, var11);
/*     */       }
/*     */     }
/*     */ 
/* 109 */     ejectDropList(var1, var2, var3, var4 + 0.5D, var5);
/*     */   }
/*     */ 
/*     */   public void doWideImpact(World var1, ItemStack var2, int var3, int var4, int var5, double var6)
/*     */   {
/* 114 */     cleanDroplist(var2);
/*     */ 
/* 116 */     for (int var8 = -1; var8 <= 1; var8++)
/*     */     {
/* 118 */       int var9 = var3;
/* 119 */       int var11 = var5;
/*     */ 
/* 121 */       if (var8 != 0)
/*     */       {
/* 123 */         if ((var6 != 2.0D) && (var6 != 4.0D))
/*     */         {
/* 125 */           var11 = var5 + var8;
/*     */         }
/*     */         else
/*     */         {
/* 129 */           var9 = var3 + var8;
/*     */         }
/*     */ 
/* 132 */         int var12 = var1.getTypeId(var9, var4, var11);
/* 133 */         int var13 = var1.getData(var9, var4, var11);
/*     */ 
/* 135 */         if (canBreak(var12, var13))
/*     */         {
/* 137 */           scanBlockAndBreak(var1, var2, var9, var4, var11);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 142 */     ejectDropList(var1, var2, var3, var4 + 0.5D, var5);
/*     */   }
/*     */ 
/*     */   public void doTallImpact(World var1, ItemStack var2, EntityHuman var3, int var4, int var5, int var6, double var7)
/*     */   {
/* 147 */     cleanDroplist(var2);
/*     */ 
/* 149 */     for (int var9 = -1; var9 <= 1; var9++)
/*     */     {
/* 151 */       int var10 = var4;
/* 152 */       int var11 = var5;
/* 153 */       int var12 = var6;
/*     */ 
/* 155 */       if (var9 != 0)
/*     */       {
/* 157 */         if ((var7 != 0.0D) && (var7 != 1.0D))
/*     */         {
/* 159 */           var11 = var5 + var9;
/*     */         }
/* 161 */         else if ((EEBase.heading(var3) != 2.0D) && (EEBase.heading(var3) != 4.0D))
/*     */         {
/* 163 */           var10 = var4 + var9;
/*     */         }
/*     */         else
/*     */         {
/* 167 */           var12 = var6 + var9;
/*     */         }
/*     */ 
/* 170 */         int var13 = var1.getTypeId(var10, var11, var12);
/* 171 */         int var14 = var1.getData(var10, var11, var12);
/*     */ 
/* 173 */         if (canBreak(var13, var14))
/*     */         {
/* 175 */           scanBlockAndBreak(var1, var2, var10, var11, var12);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 180 */     ejectDropList(var1, var2, var4, var5 + 0.5D, var6);
/*     */   }
/*     */ 
/*     */   public void doBreak(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 185 */     if (chargeLevel(var1) > 0)
/*     */     {
/* 187 */       int var4 = (int)EEBase.playerX(var3);
/* 188 */       int var5 = (int)EEBase.playerY(var3);
/* 189 */       int var6 = (int)EEBase.playerZ(var3);
/*     */ 
/* 191 */       for (int var7 = -2; var7 <= 2; var7++)
/*     */       {
/* 193 */         for (int var8 = -2; var8 <= 2; var8++)
/*     */         {
/* 195 */           for (int var9 = -2; var9 <= 2; var9++)
/*     */           {
/* 197 */             int var10 = var2.getTypeId(var4 + var7, var5 + var8, var6 + var9);
/*     */ 
/* 199 */             if (isOre(var10))
/*     */             {
/* 201 */               startSearch(var2, var1, var3, var10, var4 + var7, var5 + var8, var6 + var9, true);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void scanBlockAndBreak(World var1, ItemStack var2, int var3, int var4, int var5)
/*     */   {
/* 211 */     int var6 = var1.getTypeId(var3, var4, var5);
/* 212 */     int var7 = var1.getData(var3, var4, var5);
/* 213 */     ArrayList var8 = Block.byId[var6].getBlockDropped(var1, var3, var4, var5, var7, 0);
/* 214 */     Iterator var9 = var8.iterator();
/*     */ 
/* 216 */     while (var9.hasNext())
/*     */     {
/* 218 */       ItemStack var10 = (ItemStack)var9.next();
/* 219 */       addToDroplist(var2, var10);
/*     */     }
/*     */ 
/* 222 */     var1.setTypeId(var3, var4, var5, 0);
/*     */ 
/* 224 */     if (var1.random.nextInt(8) == 0)
/*     */     {
/* 226 */       var1.a("largesmoke", var3, var4, var5, 0.0D, 0.0D, 0.0D);
/*     */     }
/*     */ 
/* 229 */     if (var1.random.nextInt(8) == 0)
/*     */     {
/* 231 */       var1.a("explode", var3, var4, var5, 0.0D, 0.0D, 0.0D);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean canBreak(int var1, int var2)
/*     */   {
/* 237 */     if (Block.byId[var1] == null)
/*     */     {
/* 239 */       return false;
/*     */     }
/* 241 */     if (!Block.byId[var1].b())
/*     */     {
/* 243 */       return false;
/*     */     }
/* 245 */     if ((!Block.byId[var1].hasTileEntity(var2)) && (var1 != Block.BEDROCK.id))
/*     */     {
/* 247 */       if (Block.byId[var1].material == null)
/*     */       {
/* 249 */         return false;
/*     */       }
/*     */ 
/* 253 */       for (int var3 = 0; var3 < blocksEffectiveAgainst.length; var3++)
/*     */       {
/* 255 */         if (var1 == blocksEffectiveAgainst[var3].id)
/*     */         {
/* 257 */           return true;
/*     */         }
/*     */       }
/*     */ 
/* 261 */       if (canDestroySpecialBlock(Block.byId[var1]))
/*     */       {
/* 263 */         return true;
/*     */       }
/*     */ 
/* 267 */       return false;
/*     */     }
/*     */ 
/* 273 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 283 */     if (EEProxy.isClient(var3))
/*     */     {
/* 285 */       return false;
/*     */     }
/* 287 */     if (chargeLevel(var1) >= 1)
/*     */     {
/* 289 */       cleanDroplist(var1);
/* 290 */       int var8 = var3.getTypeId(var4, var5, var6);
/*     */ 
/* 292 */       if (isOre(var8))
/*     */       {
/* 294 */         startSearch(var3, var1, var2, var8, var4, var5, var6, false);
/*     */       }
/*     */ 
/* 297 */       return true;
/*     */     }
/*     */ 
/* 301 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean isOre(int var1)
/*     */   {
/* 307 */     return EEMaps.isOreBlock(var1);
/*     */   }
/*     */ 
/*     */   public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 315 */     if (EEProxy.isClient(var2))
/*     */     {
/* 317 */       return var1;
/*     */     }
/*     */ 
/* 321 */     doBreak(var1, var2, var3);
/* 322 */     return var1;
/*     */   }
/*     */ 
/*     */   public void startSearch(World var1, ItemStack var2, EntityHuman var3, int var4, int var5, int var6, int var7, boolean var8)
/*     */   {
/* 328 */     if (var4 == Block.BEDROCK.id)
/*     */     {
/* 330 */       var3.a("Nice try. You can't break bedrock.");
/*     */     }
/*     */     else
/*     */     {
/* 334 */       var1.makeSound(var3, "flash", 0.8F, 1.5F);
/*     */ 
/* 336 */       if (var8)
/*     */       {
/* 338 */         var3.C_();
/*     */       }
/*     */ 
/* 341 */       doBreakS(var1, var2, var3, var4, var5, var6, var7);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doBreakS(World var1, ItemStack var2, EntityHuman var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 347 */     scanBlockAndBreak(var1, var2, var5, var6, var7);
/*     */ 
/* 349 */     for (int var8 = -1; var8 <= 1; var8++)
/*     */     {
/* 351 */       for (int var9 = -1; var9 <= 1; var9++)
/*     */       {
/* 353 */         for (int var10 = -1; var10 <= 1; var10++)
/*     */         {
/* 355 */           if ((var4 != Block.REDSTONE_ORE.id) && (var4 != Block.GLOWING_REDSTONE_ORE.id))
/*     */           {
/* 357 */             if (var1.getTypeId(var5 + var8, var6 + var9, var7 + var10) == var4)
/*     */             {
/* 359 */               doBreakS(var1, var2, var3, var4, var5 + var8, var6 + var9, var7 + var10);
/*     */             }
/*     */           }
/* 362 */           else if ((var1.getTypeId(var5 + var8, var6 + var9, var7 + var10) == Block.GLOWING_REDSTONE_ORE.id) || (var1.getTypeId(var5 + var8, var6 + var9, var7 + var10) == Block.REDSTONE_ORE.id))
/*     */           {
/* 364 */             doBreakS(var1, var2, var3, var4, var5 + var8, var6 + var9, var7 + var10);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 370 */     ejectDropList(var1, var2, EEBase.playerX(var3), EEBase.playerY(var3), EEBase.playerZ(var3));
/*     */   }
/*     */ 
/*     */   public boolean canDestroySpecialBlock(Block var1)
/*     */   {
/* 378 */     return var1 == Block.OBSIDIAN;
/*     */   }
/*     */ 
/*     */   public void doRelease(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 383 */     doBreak(var1, var2, var3);
/*     */   }
/*     */ 
/*     */   public void doAlternate(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 388 */     EEBase.updateToolMode(var3);
/*     */   }
/*     */ 
/*     */   public void doToggle(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemRedPickaxe
 * JD-Core Version:    0.6.2
 */