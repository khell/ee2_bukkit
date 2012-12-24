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
/*     */ public class ItemRedHammer extends ItemRedTool
/*     */ {
/*     */   public static boolean breakMode;
/*     */   private boolean haltImpact;
/*  17 */   private static Block[] blocksEffectiveAgainst = { Block.COBBLESTONE, Block.STONE, Block.SANDSTONE, Block.MOSSY_COBBLESTONE, Block.IRON_ORE, Block.IRON_BLOCK, Block.COAL_ORE, Block.GOLD_BLOCK, Block.GOLD_ORE, Block.DIAMOND_ORE, Block.DIAMOND_BLOCK, Block.REDSTONE_ORE, Block.GLOWING_REDSTONE_ORE, Block.ICE, Block.NETHERRACK, Block.LAPIS_ORE, Block.LAPIS_BLOCK, Block.OBSIDIAN };
/*     */ 
/*     */   protected ItemRedHammer(int var1)
/*     */   {
/*  21 */     super(var1, 3, 14, blocksEffectiveAgainst);
/*     */   }
/*     */ 
/*     */   public boolean a(ItemStack var1, EntityLiving var2, EntityLiving var3)
/*     */   {
/*  30 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean a(ItemStack var1, int var2, int var3, int var4, int var5, EntityLiving var6)
/*     */   {
/*  35 */     EntityHuman var7 = null;
/*     */ 
/*  37 */     if ((var6 instanceof EntityHuman))
/*     */     {
/*  39 */       var7 = (EntityHuman)var6;
/*     */ 
/*  41 */       if (EEBase.getHammerMode(var7))
/*     */       {
/*  43 */         doMegaImpact(var7.world, var1, var3, var4, var5, EEBase.direction(var7));
/*     */       }
/*     */ 
/*  46 */       return true;
/*     */     }
/*     */ 
/*  50 */     return true;
/*     */   }
/*     */ 
/*     */   public float getStrVsBlock(ItemStack var1, Block var2, int var3)
/*     */   {
/*  56 */     float var4 = 1.0F;
/*  57 */     return var2.material == Material.STONE ? 14.0F / var4 : (var2.material == Material.STONE) && (chargeLevel(var1) > 0) ? 30.0F / var4 : super.getDestroySpeed(var1, var2) / var4;
/*     */   }
/*     */ 
/*     */   public void scanBlockAndBreak(World var1, ItemStack var2, int var3, int var4, int var5)
/*     */   {
/*  62 */     int var6 = var1.getTypeId(var3, var4, var5);
/*  63 */     int var7 = var1.getData(var3, var4, var5);
/*  64 */     ArrayList var8 = Block.byId[var6].getBlockDropped(var1, var3, var4, var5, var7, 0);
/*  65 */     Iterator var9 = var8.iterator();
/*     */ 
/*  67 */     while (var9.hasNext())
/*     */     {
/*  69 */       ItemStack var10 = (ItemStack)var9.next();
/*  70 */       addToDroplist(var2, var10);
/*     */     }
/*     */ 
/*  73 */     var1.setTypeId(var3, var4, var5, 0);
/*     */ 
/*  75 */     if (var1.random.nextInt(8) == 0)
/*     */     {
/*  77 */       var1.a("largesmoke", var3, var4, var5, 0.0D, 0.0D, 0.0D);
/*     */     }
/*     */ 
/*  80 */     if (var1.random.nextInt(8) == 0)
/*     */     {
/*  82 */       var1.a("explode", var3, var4, var5, 0.0D, 0.0D, 0.0D);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean canBreak(int var1, int var2)
/*     */   {
/*  88 */     if (Block.byId[var1] == null)
/*     */     {
/*  90 */       return false;
/*     */     }
/*  92 */     if (!Block.byId[var1].b())
/*     */     {
/*  94 */       return false;
/*     */     }
/*  96 */     if ((!Block.byId[var1].hasTileEntity(var2)) && (var1 != Block.BEDROCK.id))
/*     */     {
/*  98 */       if (Block.byId[var1].material == null)
/*     */       {
/* 100 */         return false;
/*     */       }
/* 102 */       if (Block.byId[var1].material == Material.STONE)
/*     */       {
/* 104 */         return true;
/*     */       }
/*     */ 
/* 108 */       for (int var3 = 0; var3 < blocksEffectiveAgainst.length; var3++)
/*     */       {
/* 110 */         if (var1 == blocksEffectiveAgainst[var3].id)
/*     */         {
/* 112 */           return true;
/*     */         }
/*     */       }
/*     */ 
/* 116 */       return false;
/*     */     }
/*     */ 
/* 121 */     return false;
/*     */   }
/*     */ 
/*     */   public void doMegaImpact(World var1, ItemStack var2, int var3, int var4, int var5, double var6)
/*     */   {
/* 127 */     cleanDroplist(var2);
/*     */ 
/* 129 */     for (int var8 = -1; var8 <= 1; var8++)
/*     */     {
/* 131 */       for (int var9 = -1; var9 <= 1; var9++)
/*     */       {
/* 133 */         int var10 = var3;
/* 134 */         int var11 = var4;
/* 135 */         int var12 = var5;
/*     */ 
/* 137 */         if ((var8 != 0) || (var9 != 0))
/*     */         {
/* 139 */           if ((var6 != 0.0D) && (var6 != 1.0D))
/*     */           {
/* 141 */             if ((var6 != 2.0D) && (var6 != 4.0D))
/*     */             {
/* 143 */               if ((var6 == 3.0D) || (var6 == 5.0D))
/*     */               {
/* 145 */                 var11 = var4 + var8;
/* 146 */                 var12 = var5 + var9;
/*     */               }
/*     */             }
/*     */             else
/*     */             {
/* 151 */               var10 = var3 + var8;
/* 152 */               var11 = var4 + var9;
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 157 */             var10 = var3 + var8;
/* 158 */             var12 = var5 + var9;
/*     */           }
/*     */ 
/* 161 */           int var13 = var1.getTypeId(var10, var11, var12);
/* 162 */           int var14 = var1.getData(var10, var11, var12);
/*     */ 
/* 164 */           if (canBreak(var13, var14))
/*     */           {
/* 166 */             scanBlockAndBreak(var1, var2, var10, var11, var12);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 172 */     ejectDropList(var1, var2, var3, var4 + 0.5D, var5);
/*     */   }
/*     */ 
/*     */   public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 181 */     if (EEProxy.isClient(var3))
/*     */     {
/* 183 */       return false;
/*     */     }
/*     */ 
/* 187 */     boolean var8 = true;
/*     */ 
/* 189 */     if (chargeLevel(var1) > 0)
/*     */     {
/* 191 */       cleanDroplist(var1);
/* 192 */       var2.C_();
/* 193 */       var3.makeSound(var2, "flash", 0.8F, 1.5F);
/*     */ 
/* 195 */       for (int var9 = -(chargeLevel(var1) * (var7 == 4 ? 0 : var7 == 5 ? 2 : 1)); var9 <= chargeLevel(var1) * (var7 == 4 ? 2 : var7 == 5 ? 0 : 1); var9++)
/*     */       {
/* 197 */         for (int var10 = -(chargeLevel(var1) * (var7 == 0 ? 0 : var7 == 1 ? 2 : 1)); var10 <= chargeLevel(var1) * (var7 == 0 ? 2 : var7 == 1 ? 0 : 1); var10++)
/*     */         {
/* 199 */           for (int var11 = -(chargeLevel(var1) * (var7 == 2 ? 0 : var7 == 3 ? 2 : 1)); var11 <= chargeLevel(var1) * (var7 == 2 ? 2 : var7 == 3 ? 0 : 1); var11++)
/*     */           {
/* 201 */             int var12 = var4 + var9;
/* 202 */             int var13 = var5 + var10;
/* 203 */             int var14 = var6 + var11;
/* 204 */             int var15 = var3.getTypeId(var12, var13, var14);
/* 205 */             int var16 = var3.getData(var12, var13, var14);
/*     */ 
/* 207 */             if (canBreak(var15, var16))
/*     */             {
/* 209 */               if (getFuelRemaining(var1) < 1)
/*     */               {
/* 211 */                 ConsumeReagent(var1, var2, var8);
/* 212 */                 var8 = false;
/*     */               }
/*     */ 
/* 215 */               if (getFuelRemaining(var1) > 0)
/*     */               {
/* 217 */                 ArrayList var17 = Block.byId[var15].getBlockDropped(var3, var12, var13, var14, var16, 0);
/* 218 */                 Iterator var18 = var17.iterator();
/*     */ 
/* 220 */                 while (var18.hasNext())
/*     */                 {
/* 222 */                   ItemStack var19 = (ItemStack)var18.next();
/* 223 */                   addToDroplist(var1, var19);
/*     */                 }
/*     */ 
/* 226 */                 var3.setTypeId(var12, var13, var14, 0);
/*     */ 
/* 228 */                 if (var3.random.nextInt(8) == 0)
/*     */                 {
/* 230 */                   var3.a("largesmoke", var12, var13, var14, 0.0D, 0.0D, 0.0D);
/*     */                 }
/*     */ 
/* 233 */                 if (var3.random.nextInt(8) == 0)
/*     */                 {
/* 235 */                   var3.a("explode", var12, var13, var14, 0.0D, 0.0D, 0.0D);
/*     */                 }
/*     */ 
/* 238 */                 setShort(var1, "fuelRemaining", getFuelRemaining(var1) - 1);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 245 */       ejectDropList(var3, var1, var4, var5, var6);
/*     */     }
/*     */ 
/* 248 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean canDestroySpecialBlock(Block var1)
/*     */   {
/* 257 */     return var1 == Block.OBSIDIAN;
/*     */   }
/*     */ 
/*     */   public void doAlternate(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 262 */     EEBase.updateHammerMode(var3, true);
/*     */   }
/*     */ 
/*     */   public void doToggle(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemRedHammer
 * JD-Core Version:    0.6.2
 */