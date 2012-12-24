/*     */ package ee;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.BlockDeadBush;
/*     */ import net.minecraft.server.BlockFlower;
/*     */ import net.minecraft.server.BlockGrass;
/*     */ import net.minecraft.server.BlockLongGrass;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.Material;
/*     */ import net.minecraft.server.StepSound;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class ItemRedHoe extends ItemRedTool
/*     */ {
/*  15 */   private static Block[] blocksEffectiveAgainst = { Block.DIRT, Block.GRASS };
/*     */   private static boolean breakMode;
/*     */ 
/*     */   public ItemRedHoe(int var1)
/*     */   {
/*  20 */     super(var1, 3, 8, blocksEffectiveAgainst);
/*     */   }
/*     */ 
/*     */   public float getDestroySpeed(ItemStack var1, Block var2)
/*     */   {
/*  29 */     if ((var2.material != Material.EARTH) && (var2.material != Material.GRASS))
/*     */     {
/*  31 */       return super.getDestroySpeed(var1, var2);
/*     */     }
/*     */ 
/*  35 */     float var3 = 18.0F + chargeLevel(var1) * 4;
/*     */ 
/*  37 */     if (breakMode)
/*     */     {
/*  39 */       var3 /= 10.0F;
/*     */     }
/*     */ 
/*  42 */     return var3;
/*     */   }
/*     */ 
/*     */   public void doBreak(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*  48 */     if (chargeLevel(var1) > 0)
/*     */     {
/*  50 */       int var4 = (int)EEBase.playerX(var3);
/*  51 */       int var5 = (int)(EEBase.playerY(var3) - 2.0D);
/*  52 */       int var6 = (int)EEBase.playerZ(var3);
/*     */ 
/*  54 */       if (chargeLevel(var1) < 1)
/*     */       {
/*  56 */         return;
/*     */       }
/*     */ 
/*  59 */       var3.C_();
/*  60 */       var2.makeSound(var3, "flash", 0.8F, 1.5F);
/*     */ 
/*  62 */       for (int var7 = -(chargeLevel(var1) * chargeLevel(var1)) - 1; var7 <= chargeLevel(var1) * chargeLevel(var1) + 1; var7++)
/*     */       {
/*  64 */         for (int var8 = -(chargeLevel(var1) * chargeLevel(var1)) - 1; var8 <= chargeLevel(var1) * chargeLevel(var1) + 1; var8++)
/*     */         {
/*  66 */           int var9 = var4 + var7;
/*  67 */           int var11 = var6 + var8;
/*  68 */           int var12 = var2.getTypeId(var9, var5, var11);
/*  69 */           int var13 = var2.getTypeId(var9, var5 + 1, var11);
/*     */ 
/*  71 */           if ((var13 == BlockFlower.YELLOW_FLOWER.id) || (var13 == BlockFlower.RED_ROSE.id) || (var13 == BlockFlower.BROWN_MUSHROOM.id) || (var13 == BlockFlower.RED_MUSHROOM.id) || (var13 == BlockLongGrass.LONG_GRASS.id) || (var13 == BlockDeadBush.DEAD_BUSH.id))
/*     */           {
/*  73 */             Block.byId[var13].dropNaturally(var2, var9, var5 + 1, var11, var2.getData(var9, var5 + 1, var11), 1.0F, 1);
/*  74 */             var2.setTypeId(var9, var5 + 1, var11, 0);
/*     */           }
/*     */ 
/*  77 */           if ((var13 == 0) && ((var12 == Block.DIRT.id) || (var12 == Block.GRASS.id)))
/*     */           {
/*  79 */             if (getFuelRemaining(var1) < 1)
/*     */             {
/*  81 */               ConsumeReagent(var1, var3, false);
/*     */             }
/*     */ 
/*  84 */             if (getFuelRemaining(var1) > 0)
/*     */             {
/*  86 */               var2.setTypeId(var9, var5, var11, 60);
/*  87 */               setShort(var1, "fuelRemaining", getFuelRemaining(var1) - 1);
/*     */ 
/*  89 */               if (var2.random.nextInt(8) == 0)
/*     */               {
/*  91 */                 var2.a("largesmoke", var9, var5, var11, 0.0D, 0.0D, 0.0D);
/*     */               }
/*     */ 
/*  94 */               if (var2.random.nextInt(8) == 0)
/*     */               {
/*  96 */                 var2.a("explode", var9, var5, var11, 0.0D, 0.0D, 0.0D);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 111 */     if (EEProxy.isClient(var3))
/*     */     {
/* 113 */       return false;
/*     */     }
/*     */ 
/* 120 */     if (chargeLevel(var1) > 0)
/*     */     {
/* 122 */       var2.C_();
/* 123 */       var3.makeSound(var2, "flash", 0.8F, 1.5F);
/*     */ 
/* 125 */       if ((var3.getTypeId(var4, var5, var6) == BlockFlower.YELLOW_FLOWER.id) || (var3.getTypeId(var4, var5, var6) == BlockFlower.RED_ROSE.id) || (var3.getTypeId(var4, var5, var6) == BlockFlower.BROWN_MUSHROOM.id) || (var3.getTypeId(var4, var5, var6) == BlockFlower.RED_MUSHROOM.id) || (var3.getTypeId(var4, var5, var6) == BlockLongGrass.LONG_GRASS.id) || (var3.getTypeId(var4, var5, var6) == BlockDeadBush.DEAD_BUSH.id))
/*     */       {
/* 127 */         var5--;
/*     */       }
/*     */ 
/* 130 */       for (int var8 = -(chargeLevel(var1) * chargeLevel(var1)) - 1; var8 <= chargeLevel(var1) * chargeLevel(var1) + 1; var8++)
/*     */       {
/* 132 */         for (int var9 = -(chargeLevel(var1) * chargeLevel(var1)) - 1; var9 <= chargeLevel(var1) * chargeLevel(var1) + 1; var9++)
/*     */         {
/* 134 */           int var15 = var4 + var8;
/* 135 */           int var12 = var6 + var9;
/* 136 */           int var13 = var3.getTypeId(var15, var5, var12);
/* 137 */           int var14 = var3.getTypeId(var15, var5 + 1, var12);
/*     */ 
/* 139 */           if ((var14 == BlockFlower.YELLOW_FLOWER.id) || (var14 == BlockFlower.RED_ROSE.id) || (var14 == BlockFlower.BROWN_MUSHROOM.id) || (var14 == BlockFlower.RED_MUSHROOM.id) || (var14 == BlockLongGrass.LONG_GRASS.id) || (var14 == BlockDeadBush.DEAD_BUSH.id))
/*     */           {
/* 141 */             Block.byId[var14].dropNaturally(var3, var15, var5 + 1, var12, var3.getData(var15, var5 + 1, var12), 1.0F, 1);
/* 142 */             var3.setTypeId(var15, var5 + 1, var12, 0);
/* 143 */             var14 = 0;
/*     */           }
/*     */ 
/* 146 */           if ((var14 == 0) && ((var13 == Block.DIRT.id) || (var13 == Block.GRASS.id)))
/*     */           {
/* 148 */             if (getFuelRemaining(var1) < 1)
/*     */             {
/* 150 */               ConsumeReagent(var1, var2, false);
/*     */             }
/*     */ 
/* 153 */             if (getFuelRemaining(var1) > 0)
/*     */             {
/* 155 */               var3.setTypeId(var15, var5, var12, 60);
/* 156 */               setShort(var1, "fuelRemaining", getFuelRemaining(var1) - 1);
/*     */ 
/* 158 */               if (var3.random.nextInt(8) == 0)
/*     */               {
/* 160 */                 var3.a("largesmoke", var15, var5, var12, 0.0D, 0.0D, 0.0D);
/*     */               }
/*     */ 
/* 163 */               if (var3.random.nextInt(8) == 0)
/*     */               {
/* 165 */                 var3.a("explode", var15, var5, var12, 0.0D, 0.0D, 0.0D);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 172 */       return false;
/*     */     }
/* 174 */     if ((var2 != null) && (!var2.d(var4, var5, var6)))
/*     */     {
/* 176 */       return false;
/*     */     }
/*     */ 
/* 180 */     int var8 = var3.getTypeId(var4, var5, var6);
/* 181 */     int var9 = var3.getTypeId(var4, var5 + 1, var6);
/*     */ 
/* 183 */     if (((var7 == 0) || (var9 != 0) || (var8 != Block.GRASS.id)) && (var8 != Block.DIRT.id))
/*     */     {
/* 185 */       return false;
/*     */     }
/*     */ 
/* 189 */     Block var10 = Block.SOIL;
/* 190 */     var3.makeSound(var4 + 0.5F, var5 + 0.5F, var6 + 0.5F, var10.stepSound.getName(), (var10.stepSound.getVolume1() + 1.0F) / 2.0F, var10.stepSound.getVolume2() * 0.8F);
/*     */ 
/* 192 */     if (var3.isStatic)
/*     */     {
/* 194 */       return true;
/*     */     }
/*     */ 
/* 198 */     var3.setTypeId(var4, var5, var6, var10.id);
/* 199 */     return true;
/*     */   }
/*     */ 
/*     */   public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 211 */     if (EEProxy.isClient(var2))
/*     */     {
/* 213 */       return var1;
/*     */     }
/*     */ 
/* 217 */     doBreak(var1, var2, var3);
/* 218 */     return var1;
/*     */   }
/*     */ 
/*     */   public boolean isFull3D()
/*     */   {
/* 224 */     return true;
/*     */   }
/*     */ 
/*     */   public void doHeld(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doRelease(ItemStack var1, World var2, EntityHuman var3) {
/* 231 */     doBreak(var1, var2, var3);
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
 * Qualified Name:     ee.ItemRedHoe
 * JD-Core Version:    0.6.2
 */