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
/*     */ public class ItemDarkPickaxe extends ItemDarkTool
/*     */ {
/*  15 */   private static Block[] blocksEffectiveAgainst = { Block.COBBLESTONE, Block.DOUBLE_STEP, Block.STEP, Block.STONE, Block.SANDSTONE, Block.MOSSY_COBBLESTONE, Block.IRON_ORE, Block.IRON_BLOCK, Block.COAL_ORE, Block.GOLD_BLOCK, Block.GOLD_ORE, Block.DIAMOND_ORE, Block.DIAMOND_BLOCK, Block.REDSTONE_ORE, Block.GLOWING_REDSTONE_ORE, Block.ICE, Block.NETHERRACK, Block.LAPIS_ORE, Block.LAPIS_BLOCK, Block.OBSIDIAN };
/*  16 */   private static int breakMode = 0;
/*     */ 
/*     */   protected ItemDarkPickaxe(int var1)
/*     */   {
/*  20 */     super(var1, 2, 6, blocksEffectiveAgainst);
/*     */   }
/*     */ 
/*     */   public float getStrVsBlock(ItemStack var1, Block var2, int var3)
/*     */   {
/*  25 */     float var4 = 1.0F;
/*  26 */     return ((var2.id != EEBlock.eePedestal.id) || (var3 != 0)) && ((var2.id != EEBlock.eeStone.id) || (var3 != 8)) ? 12.0F / var4 : (var2.material != Material.STONE) && (var2.material != Material.ORE) ? super.getDestroySpeed(var1, var2) / var4 : ((var2.material == Material.STONE) || (var2.material == Material.ORE)) && (chargeLevel(var1) > 0) ? 12.0F + 12.0F * chargeLevel(var1) / var4 : 1200000.0F / var4;
/*     */   }
/*     */ 
/*     */   public boolean a(ItemStack var1, int var2, int var3, int var4, int var5, EntityLiving var6)
/*     */   {
/*  31 */     EntityHuman var7 = null;
/*     */ 
/*  33 */     if ((var6 instanceof EntityHuman))
/*     */     {
/*  35 */       var7 = (EntityHuman)var6;
/*     */ 
/*  37 */       if (EEBase.getToolMode(var7) != 0)
/*     */       {
/*  39 */         if (EEBase.getToolMode(var7) == 1)
/*     */         {
/*  41 */           doTallImpact(var7.world, var1, var7, var3, var4, var5, EEBase.direction(var7));
/*     */         }
/*  43 */         else if (EEBase.getToolMode(var7) == 2)
/*     */         {
/*  45 */           doWideImpact(var7.world, var1, var3, var4, var5, EEBase.heading(var7));
/*     */         }
/*  47 */         else if (EEBase.getToolMode(var7) == 3)
/*     */         {
/*  49 */           doLongImpact(var7.world, var1, var3, var4, var5, EEBase.direction(var7));
/*     */         }
/*     */       }
/*     */ 
/*  53 */       return true;
/*     */     }
/*     */ 
/*  57 */     return true;
/*     */   }
/*     */ 
/*     */   public void doLongImpact(World var1, ItemStack var2, int var3, int var4, int var5, double var6)
/*     */   {
/*  63 */     cleanDroplist(var2);
/*     */ 
/*  65 */     for (int var8 = 1; var8 <= 2; var8++)
/*     */     {
/*  67 */       int var9 = var3;
/*  68 */       int var10 = var4;
/*  69 */       int var11 = var5;
/*     */ 
/*  71 */       if (var6 == 0.0D)
/*     */       {
/*  73 */         var10 = var4 - var8;
/*     */       }
/*     */ 
/*  76 */       if (var6 == 1.0D)
/*     */       {
/*  78 */         var10 += var8;
/*     */       }
/*     */ 
/*  81 */       if (var6 == 2.0D)
/*     */       {
/*  83 */         var11 = var5 + var8;
/*     */       }
/*     */ 
/*  86 */       if (var6 == 3.0D)
/*     */       {
/*  88 */         var9 = var3 - var8;
/*     */       }
/*     */ 
/*  91 */       if (var6 == 4.0D)
/*     */       {
/*  93 */         var11 -= var8;
/*     */       }
/*     */ 
/*  96 */       if (var6 == 5.0D)
/*     */       {
/*  98 */         var9 += var8;
/*     */       }
/*     */ 
/* 101 */       int var12 = var1.getTypeId(var9, var10, var11);
/* 102 */       int var13 = var1.getData(var9, var10, var11);
/*     */ 
/* 104 */       if (canBreak(var12, var13))
/*     */       {
/* 106 */         scanBlockAndBreak(var1, var2, var9, var10, var11);
/*     */       }
/*     */     }
/*     */ 
/* 110 */     ejectDropList(var1, var2, var3, var4 + 0.5D, var5);
/*     */   }
/*     */ 
/*     */   public void doWideImpact(World var1, ItemStack var2, int var3, int var4, int var5, double var6)
/*     */   {
/* 115 */     cleanDroplist(var2);
/*     */ 
/* 117 */     for (int var8 = -1; var8 <= 1; var8++)
/*     */     {
/* 119 */       int var9 = var3;
/* 120 */       int var11 = var5;
/*     */ 
/* 122 */       if (var8 != 0)
/*     */       {
/* 124 */         if ((var6 != 2.0D) && (var6 != 4.0D))
/*     */         {
/* 126 */           var11 = var5 + var8;
/*     */         }
/*     */         else
/*     */         {
/* 130 */           var9 = var3 + var8;
/*     */         }
/*     */ 
/* 133 */         int var12 = var1.getTypeId(var9, var4, var11);
/* 134 */         int var13 = var1.getData(var9, var4, var11);
/*     */ 
/* 136 */         if (canBreak(var12, var13))
/*     */         {
/* 138 */           scanBlockAndBreak(var1, var2, var9, var4, var11);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 143 */     ejectDropList(var1, var2, var3, var4 + 0.5D, var5);
/*     */   }
/*     */ 
/*     */   public void doTallImpact(World var1, ItemStack var2, EntityHuman var3, int var4, int var5, int var6, double var7)
/*     */   {
/* 148 */     cleanDroplist(var2);
/*     */ 
/* 150 */     for (int var9 = -1; var9 <= 1; var9++)
/*     */     {
/* 152 */       int var10 = var4;
/* 153 */       int var11 = var5;
/* 154 */       int var12 = var6;
/*     */ 
/* 156 */       if (var9 != 0)
/*     */       {
/* 158 */         if ((var7 != 0.0D) && (var7 != 1.0D))
/*     */         {
/* 160 */           var11 = var5 + var9;
/*     */         }
/* 162 */         else if ((EEBase.heading(var3) != 2.0D) && (EEBase.heading(var3) != 4.0D))
/*     */         {
/* 164 */           var10 = var4 + var9;
/*     */         }
/*     */         else
/*     */         {
/* 168 */           var12 = var6 + var9;
/*     */         }
/*     */ 
/* 171 */         int var13 = var1.getTypeId(var10, var11, var12);
/* 172 */         int var14 = var1.getData(var10, var11, var12);
/*     */ 
/* 174 */         if (canBreak(var13, var14))
/*     */         {
/* 176 */           scanBlockAndBreak(var1, var2, var10, var11, var12);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 181 */     ejectDropList(var1, var2, var4, var5 + 0.5D, var6);
/*     */   }
/*     */ 
/*     */   public void doBreak(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 186 */     if (chargeLevel(var1) > 0)
/*     */     {
/* 188 */       int var4 = (int)EEBase.playerX(var3);
/* 189 */       int var5 = (int)EEBase.playerY(var3);
/* 190 */       int var6 = (int)EEBase.playerZ(var3);
/*     */ 
/* 192 */       for (int var7 = -2; var7 <= 2; var7++)
/*     */       {
/* 194 */         for (int var8 = -2; var8 <= 2; var8++)
/*     */         {
/* 196 */           for (int var9 = -2; var9 <= 2; var9++)
/*     */           {
/* 198 */             int var10 = var2.getTypeId(var4 + var7, var5 + var8, var6 + var9);
/*     */ 
/* 200 */             if (isOre(var10))
/*     */             {
/* 202 */               startSearch(var2, var1, var3, var10, var4 + var7, var5 + var8, var6 + var9, true);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void scanBlockAndBreak(World var1, ItemStack var2, int var3, int var4, int var5)
/*     */   {
/* 212 */     int var6 = var1.getTypeId(var3, var4, var5);
/* 213 */     int var7 = var1.getData(var3, var4, var5);
/* 214 */     ArrayList var8 = Block.byId[var6].getBlockDropped(var1, var3, var4, var5, var7, 0);
/* 215 */     Iterator var9 = var8.iterator();
/*     */ 
/* 217 */     while (var9.hasNext())
/*     */     {
/* 219 */       ItemStack var10 = (ItemStack)var9.next();
/* 220 */       addToDroplist(var2, var10);
/*     */     }
/*     */ 
/* 223 */     var1.setTypeId(var3, var4, var5, 0);
/*     */ 
/* 225 */     if (var1.random.nextInt(8) == 0)
/*     */     {
/* 227 */       var1.a("largesmoke", var3, var4, var5, 0.0D, 0.0D, 0.0D);
/*     */     }
/*     */ 
/* 230 */     if (var1.random.nextInt(8) == 0)
/*     */     {
/* 232 */       var1.a("explode", var3, var4, var5, 0.0D, 0.0D, 0.0D);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean canBreak(int var1, int var2)
/*     */   {
/* 238 */     if (Block.byId[var1] == null)
/*     */     {
/* 240 */       return false;
/*     */     }
/* 242 */     if (!Block.byId[var1].b())
/*     */     {
/* 244 */       return false;
/*     */     }
/* 246 */     if ((!Block.byId[var1].hasTileEntity(var2)) && (var1 != Block.BEDROCK.id))
/*     */     {
/* 248 */       if (Block.byId[var1].material == null)
/*     */       {
/* 250 */         return false;
/*     */       }
/*     */ 
/* 254 */       for (int var3 = 0; var3 < blocksEffectiveAgainst.length; var3++)
/*     */       {
/* 256 */         if (var1 == blocksEffectiveAgainst[var3].id)
/*     */         {
/* 258 */           return true;
/*     */         }
/*     */       }
/*     */ 
/* 262 */       if (canDestroySpecialBlock(Block.byId[var1]))
/*     */       {
/* 264 */         return true;
/*     */       }
/*     */ 
/* 268 */       return false;
/*     */     }
/*     */ 
/* 274 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 284 */     if (EEProxy.isClient(var3))
/*     */     {
/* 286 */       return false;
/*     */     }
/* 288 */     if (chargeLevel(var1) >= 1)
/*     */     {
/* 290 */       cleanDroplist(var1);
/* 291 */       int var8 = var3.getTypeId(var4, var5, var6);
/*     */ 
/* 293 */       if (isOre(var8))
/*     */       {
/* 295 */         startSearch(var3, var1, var2, var8, var4, var5, var6, false);
/*     */       }
/*     */ 
/* 298 */       return true;
/*     */     }
/*     */ 
/* 302 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean isOre(int var1)
/*     */   {
/* 308 */     return EEMaps.isOreBlock(var1);
/*     */   }
/*     */ 
/*     */   public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 316 */     if (EEProxy.isClient(var2))
/*     */     {
/* 318 */       return var1;
/*     */     }
/*     */ 
/* 322 */     doBreak(var1, var2, var3);
/* 323 */     return var1;
/*     */   }
/*     */ 
/*     */   public void startSearch(World var1, ItemStack var2, EntityHuman var3, int var4, int var5, int var6, int var7, boolean var8)
/*     */   {
/* 329 */     if (var4 == Block.BEDROCK.id)
/*     */     {
/* 331 */       var3.a("Nice try. You can't break bedrock.");
/*     */     }
/*     */     else
/*     */     {
/* 335 */       var1.makeSound(var3, "flash", 0.8F, 1.5F);
/*     */ 
/* 337 */       if (var8)
/*     */       {
/* 339 */         var3.C_();
/*     */       }
/*     */ 
/* 342 */       doBreakS(var1, var2, var3, var4, var5, var6, var7);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doBreakS(World var1, ItemStack var2, EntityHuman var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 348 */     scanBlockAndBreak(var1, var2, var5, var6, var7);
/*     */ 
/* 350 */     for (int var8 = -1; var8 <= 1; var8++)
/*     */     {
/* 352 */       for (int var9 = -1; var9 <= 1; var9++)
/*     */       {
/* 354 */         for (int var10 = -1; var10 <= 1; var10++)
/*     */         {
/* 356 */           if ((var4 != Block.REDSTONE_ORE.id) && (var4 != Block.GLOWING_REDSTONE_ORE.id))
/*     */           {
/* 358 */             if (var1.getTypeId(var5 + var8, var6 + var9, var7 + var10) == var4)
/*     */             {
/* 360 */               doBreakS(var1, var2, var3, var4, var5 + var8, var6 + var9, var7 + var10);
/*     */             }
/*     */           }
/* 363 */           else if ((var1.getTypeId(var5 + var8, var6 + var9, var7 + var10) == Block.GLOWING_REDSTONE_ORE.id) || (var1.getTypeId(var5 + var8, var6 + var9, var7 + var10) == Block.REDSTONE_ORE.id))
/*     */           {
/* 365 */             doBreakS(var1, var2, var3, var4, var5 + var8, var6 + var9, var7 + var10);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 371 */     ejectDropList(var1, var2, EEBase.playerX(var3), EEBase.playerY(var3), EEBase.playerZ(var3));
/*     */   }
/*     */ 
/*     */   public boolean canDestroySpecialBlock(Block var1)
/*     */   {
/* 379 */     return var1 == Block.OBSIDIAN;
/*     */   }
/*     */ 
/*     */   public void doRelease(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 384 */     doBreak(var1, var2, var3);
/*     */   }
/*     */ 
/*     */   public void doAlternate(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 389 */     EEBase.updateToolMode(var3);
/*     */   }
/*     */ 
/*     */   public void doToggle(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemDarkPickaxe
 * JD-Core Version:    0.6.2
 */