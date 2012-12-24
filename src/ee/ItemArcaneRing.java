/*      */ package ee;
/*      */ 
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import net.minecraft.server.AxisAlignedBB;
/*      */ import net.minecraft.server.Block;
/*      */ import net.minecraft.server.BlockFire;
/*      */ import net.minecraft.server.BlockFlower;
/*      */ import net.minecraft.server.BlockGrass;
/*      */ import net.minecraft.server.BlockLeaves;
/*      */ import net.minecraft.server.BlockLongGrass;
/*      */ import net.minecraft.server.EEProxy;
/*      */ import net.minecraft.server.Entity;
/*      */ import net.minecraft.server.EntityArrow;
/*      */ import net.minecraft.server.EntityFireball;
/*      */ import net.minecraft.server.EntityHuman;
/*      */ import net.minecraft.server.EntityMonster;
/*      */ import net.minecraft.server.EntitySnowball;
/*      */ import net.minecraft.server.EntityWeatherLighting;
/*      */ import net.minecraft.server.Item;
/*      */ import net.minecraft.server.ItemStack;
/*      */ import net.minecraft.server.Material;
/*      */ import net.minecraft.server.MathHelper;
/*      */ import net.minecraft.server.NBTTagCompound;
/*      */ import net.minecraft.server.World;
/*      */ import net.minecraft.server.WorldData;
/*      */ import net.minecraft.server.WorldGenBigTree;
/*      */ import net.minecraft.server.WorldGenForest;
/*      */ import net.minecraft.server.WorldGenTaiga2;
/*      */ import net.minecraft.server.WorldGenTrees;
/*      */ import net.minecraft.server.WorldGenerator;
/*      */ 
/*      */ public class ItemArcaneRing extends ItemEECharged
/*      */ {
/*      */   private boolean initialized;
/*      */ 
/*      */   public ItemArcaneRing(int var1)
/*      */   {
/*   33 */     super(var1, 0);
/*      */   }
/*      */ 
/*      */   public void doGale(ItemStack var1, World var2, EntityHuman var3)
/*      */   {
/*   38 */     var2.makeSound(var3, "gust", 0.6F, 1.0F);
/*   39 */     var2.addEntity(new EntityWindEssence(var2, var3));
/*      */   }
/*      */ 
/*      */   public void doInterdiction(ItemStack var1, World var2, EntityHuman var3)
/*      */   {
/*   44 */     List var4 = var2.a(EntityMonster.class, AxisAlignedBB.b((float)var3.locX - 5.0F, var3.locY - 5.0D, (float)var3.locZ - 5.0F, (float)var3.locX + 5.0F, var3.locY + 5.0D, (float)var3.locZ + 5.0F));
/*   45 */     Iterator var6 = var4.iterator();
/*      */ 
/*   47 */     while (var6.hasNext())
/*      */     {
/*   49 */       Entity var5 = (Entity)var6.next();
/*   50 */       PushEntities(var5, var3);
/*      */     }
/*      */ 
/*   53 */     List var11 = var2.a(EntityArrow.class, AxisAlignedBB.b((float)var3.locX - 5.0F, var3.locY - 5.0D, (float)var3.locZ - 5.0F, (float)var3.locX + 5.0F, var3.locY + 5.0D, (float)var3.locZ + 5.0F));
/*   54 */     Iterator var8 = var11.iterator();
/*      */ 
/*   56 */     while (var8.hasNext())
/*      */     {
/*   58 */       Entity var7 = (Entity)var8.next();
/*   59 */       PushEntities(var7, var3);
/*      */     }
/*      */ 
/*   62 */     List var12 = var2.a(EntityFireball.class, AxisAlignedBB.b((float)var3.locX - 5.0F, var3.locY - 5.0D, (float)var3.locZ - 5.0F, (float)var3.locX + 5.0F, var3.locY + 5.0D, (float)var3.locZ + 5.0F));
/*   63 */     Iterator var10 = var12.iterator();
/*      */ 
/*   65 */     while (var10.hasNext())
/*      */     {
/*   67 */       Entity var9 = (Entity)var10.next();
/*   68 */       PushEntities(var9, var3);
/*      */     }
/*      */ 
/*   71 */     if (!EEProxy.getWorldInfo(var2).isThundering())
/*      */     {
/*   73 */       EEProxy.getWorldInfo(var2).setThundering(true);
/*   74 */       EEProxy.getWorldInfo(var2).setThunderDuration(300);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void doThunder(ItemStack var1, World var2, EntityHuman var3)
/*      */   {
/*   80 */     List var4 = var2.a(EntityMonster.class, AxisAlignedBB.b((float)var3.locX - 5.0F, var3.locY - 5.0D, (float)var3.locZ - 5.0F, (float)var3.locX + 5.0F, var3.locY + 5.0D, (float)var3.locZ + 5.0F));
/*   81 */     Iterator var6 = var4.iterator();
/*      */ 
/*   83 */     while (var6.hasNext())
/*      */     {
/*   85 */       Entity var5 = (Entity)var6.next();
/*   86 */       doBolt(var5, var1, var3);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void doBolt(Entity var1, ItemStack var2, EntityHuman var3)
/*      */   {
/*   92 */     if ((getThunderCooldown(var2) <= 0) && (var3.world.isChunkLoaded((int)var1.locX, (int)var1.locY, (int)var1.locZ)))
/*      */     {
/*   94 */       var3.world.strikeLightning(new EntityWeatherLighting(var3.world, var1.locX, var1.locY, var1.locZ));
/*   95 */       resetThunderCooldown(var2);
/*      */     }
/*      */   }
/*      */ 
/*      */   private int getThunderCooldown(ItemStack var1)
/*      */   {
/*  101 */     return getShort(var1, "thunderCooldown");
/*      */   }
/*      */ 
/*      */   private void decThunderCooldown(ItemStack var1)
/*      */   {
/*  106 */     setShort(var1, "thunderCooldown", getThunderCooldown(var1) <= 0 ? 0 : getThunderCooldown(var1) - 1);
/*      */   }
/*      */ 
/*      */   private void resetThunderCooldown(ItemStack var1)
/*      */   {
/*  111 */     setShort(var1, "thunderCooldown", 20);
/*      */   }
/*      */ 
/*      */   private void PushEntities(Entity var1, EntityHuman var2)
/*      */   {
/*  116 */     if (!(var1 instanceof EntityHuman))
/*      */     {
/*  118 */       if ((var2.world.random.nextInt(1200) == 0) && (var2.world.isChunkLoaded((int)var1.locX, (int)var1.locY, (int)var1.locZ)))
/*      */       {
/*  120 */         var2.world.strikeLightning(new EntityWeatherLighting(var2.world, var1.locX, var1.locY, var1.locZ));
/*      */       }
/*      */ 
/*  123 */       double var4 = var2.locX + 0.5D - var1.locX;
/*  124 */       double var6 = var2.locY + 0.5D - var1.locY;
/*  125 */       double var8 = var2.locZ + 0.5D - var1.locZ;
/*  126 */       double var10 = var4 * var4 + var6 * var6 + var8 * var8;
/*  127 */       var10 *= var10;
/*      */ 
/*  129 */       if (var10 <= Math.pow(6.0D, 4.0D))
/*      */       {
/*  131 */         double var12 = -(var4 * 0.01999999955296516D / var10) * Math.pow(6.0D, 3.0D);
/*  132 */         double var14 = -(var6 * 0.01999999955296516D / var10) * Math.pow(6.0D, 3.0D);
/*  133 */         double var16 = -(var8 * 0.01999999955296516D / var10) * Math.pow(6.0D, 3.0D);
/*      */ 
/*  135 */         if (var12 > 0.0D)
/*      */         {
/*  137 */           var12 = 0.12D;
/*      */         }
/*  139 */         else if (var12 < 0.0D)
/*      */         {
/*  141 */           var12 = -0.12D;
/*      */         }
/*      */ 
/*  144 */         if (var14 > 0.2D)
/*      */         {
/*  146 */           var14 = 0.12D;
/*      */         }
/*  148 */         else if (var14 < -0.1D)
/*      */         {
/*  150 */           var14 = 0.12D;
/*      */         }
/*      */ 
/*  153 */         if (var16 > 0.0D)
/*      */         {
/*  155 */           var16 = 0.12D;
/*      */         }
/*  157 */         else if (var16 < 0.0D)
/*      */         {
/*  159 */           var16 = -0.12D;
/*      */         }
/*      */ 
/*  162 */         var1.motX += var12;
/*  163 */         var1.motY += var14;
/*  164 */         var1.motZ += var16;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean ConsumeReagentBonemeal(EntityHuman var1, boolean var2)
/*      */   {
/*  171 */     return EEBase.Consume(new ItemStack(Item.INK_SACK, 4, 15), var1, var2);
/*      */   }
/*      */ 
/*      */   public boolean ConsumeReagentSapling(ItemStack var1, EntityHuman var2, boolean var3)
/*      */   {
/*  176 */     if (EEBase.Consume(new ItemStack(Block.SAPLING, 1, 0), var2, var3))
/*      */     {
/*  178 */       tempMeta(var1, 0);
/*  179 */       return true;
/*      */     }
/*  181 */     if (EEBase.Consume(new ItemStack(Block.SAPLING, 1, 1), var2, var3))
/*      */     {
/*  183 */       tempMeta(var1, 1);
/*  184 */       return true;
/*      */     }
/*  186 */     if (EEBase.Consume(new ItemStack(Block.SAPLING, 1, 2), var2, var3))
/*      */     {
/*  188 */       tempMeta(var1, 2);
/*  189 */       return true;
/*      */     }
/*      */ 
/*  193 */     return false;
/*      */   }
/*      */ 
/*      */   private void tempMeta(ItemStack var1, int var2)
/*      */   {
/*  199 */     ((ItemEECharged)var1.getItem()).setInteger(var1, "tempMeta", var2);
/*      */   }
/*      */ 
/*      */   public void doPassiveHarvest(ItemStack var1, World var2, EntityHuman var3)
/*      */   {
/*  207 */     for (int var4 = -1; var4 <= 1; var4++)
/*      */     {
/*  209 */       for (int var5 = -1; var5 <= 1; var5++)
/*      */       {
/*  211 */         if (var2.getTypeId((int)EEBase.playerX(var3) + var4, (int)EEBase.playerY(var3) - 1, (int)EEBase.playerZ(var3) + var5) == Block.FIRE.id)
/*      */         {
/*  213 */           var2.setTypeId((int)EEBase.playerX(var3) + var4, (int)EEBase.playerY(var3) - 1, (int)EEBase.playerZ(var3) + var5, 0);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  218 */     int var4 = (int)EEBase.playerX(var3);
/*  219 */     int var5 = (int)EEBase.playerY(var3);
/*  220 */     int var6 = (int)EEBase.playerZ(var3);
/*      */ 
/*  222 */     for (int var7 = -5; var7 <= 5; var7++)
/*      */     {
/*  224 */       for (int var8 = -5; var8 <= 5; var8++)
/*      */       {
/*  226 */         for (int var9 = -5; var9 <= 5; var9++)
/*      */         {
/*  228 */           int var10 = var2.getTypeId(var4 + var7, var5 + var8, var6 + var9);
/*      */ 
/*  231 */           if (var10 == Block.CROPS.id)
/*      */           {
/*  233 */             int var11 = var2.getData(var4 + var7, var5 + var8, var6 + var9);
/*      */ 
/*  235 */             if ((var11 < 7) && (var2.random.nextInt(600) == 0))
/*      */             {
/*  237 */               var11++;
/*  238 */               var2.setData(var4 + var7, var5 + var8, var6 + var9, var11);
/*      */             }
/*      */           }
/*  241 */           else if ((var10 != BlockFlower.YELLOW_FLOWER.id) && (var10 != BlockFlower.RED_ROSE.id) && (var10 != BlockFlower.BROWN_MUSHROOM.id) && (var10 != BlockFlower.RED_MUSHROOM.id))
/*      */           {
/*  243 */             if ((var10 == Block.GRASS.id) && (var2.getTypeId(var4 + var7, var5 + var8 + 1, var6 + var9) == 0) && (var2.random.nextInt(4000) == 0))
/*      */             {
/*  245 */               var2.setTypeId(var4 + var7, var5 + var8 + 1, var6 + var9, BlockFlower.LONG_GRASS.id);
/*  246 */               var2.setData(var4 + var7, var5 + var8 + 1, var6 + var9, 1);
/*      */             }
/*      */ 
/*  249 */             if ((var10 == Block.DIRT.id) && (var2.getTypeId(var4 + var7, var5 + var8 + 1, var6 + var9) == 0) && (var2.random.nextInt(800) == 0))
/*      */             {
/*  251 */               var2.setTypeId(var4 + var7, var5 + var8, var6 + var9, Block.GRASS.id);
/*      */             }
/*  253 */             else if (((var10 == Block.SUGAR_CANE_BLOCK.id) || (var10 == Block.CACTUS.id)) && (var2.getTypeId(var4 + var7, var5 + var8 + 1, var6 + var9) == 0) && (var2.getTypeId(var4 + var7, var5 + var8 - 4, var6 + var9) != Block.SUGAR_CANE_BLOCK.id) && (var2.getTypeId(var4 + var7, var5 + var8 - 4, var6 + var9) != Block.CACTUS.id) && (var2.random.nextInt(600) == 0))
/*      */             {
/*  255 */               var2.setTypeId(var4 + var7, var5 + var8 + 1, var6 + var9, var10);
/*  256 */               var2.a("largesmoke", var4 + var7, var5 + var8, var6 + var9, 0.0D, 0.05D, 0.0D);
/*      */             }
/*      */           }
/*  259 */           else if (var2.random.nextInt(2) == 0)
/*      */           {
/*  261 */             for (int var11 = -1; var11 < 0; var11++)
/*      */             {
/*  263 */               if ((var2.getTypeId(var4 + var7 + var11, var5 + var8, var6 + var9) == 0) && (var2.getTypeId(var4 + var7 + var11, var5 + var8 - 1, var6 + var9) == Block.GRASS.id))
/*      */               {
/*  265 */                 if (var2.random.nextInt(800) == 0)
/*      */                 {
/*  267 */                   var2.setTypeId(var4 + var7 + var11, var5 + var8, var6 + var9, var10);
/*  268 */                   var2.a("largesmoke", var4 + var7 + var11, var5 + var8, var6 + var9, 0.0D, 0.05D, 0.0D);
/*      */                 }
/*      */               }
/*  271 */               else if ((var2.getTypeId(var4 + var7, var5 + var8, var6 + var9 + var11) == 0) && (var2.getTypeId(var4 + var7, var5 + var8 - 1, var6 + var9 + var11) == Block.GRASS.id) && (var2.random.nextInt(1800) == 0))
/*      */               {
/*  273 */                 var2.setTypeId(var4 + var7, var5 + var8, var6 + var9 + var11, var10);
/*  274 */                 var2.a("largesmoke", var4 + var7, var5 + var8, var6 + var9 + var11, 0.0D, 0.05D, 0.0D);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*      */   {
/*  289 */     if (EEProxy.isClient(var3))
/*      */     {
/*  291 */       return false;
/*      */     }
/*      */ 
/*  299 */     if (var3.getTypeId(var4, var5, var6) == 60)
/*      */     {
/*  301 */       for (int var14 = -5; var14 <= 5; var14++)
/*      */       {
/*  303 */         for (int var9 = -5; var9 <= 5; var9++)
/*      */         {
/*  305 */           int var10 = var3.getTypeId(var14 + var4, var5, var9 + var6);
/*  306 */           int var11 = var3.getTypeId(var14 + var4, var5 + 1, var9 + var6);
/*      */ 
/*  308 */           if ((var10 == 60) && (var11 == 0) && (ConsumeReagentSeeds(var2, false)))
/*      */           {
/*  310 */             var3.setTypeId(var14 + var4, var5 + 1, var9 + var6, Block.CROPS.id);
/*  311 */             var3.a("largesmoke", var14 + var4, var5, var9 + var6, 0.0D, 0.05D, 0.0D);
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  316 */       return true;
/*      */     }
/*      */ 
/*  322 */     if (var3.getTypeId(var4, var5, var6) == 12)
/*      */     {
/*  324 */       double var15 = EEBase.direction(var2);
/*      */ 
/*  326 */       if (var15 == 5.0D)
/*      */       {
/*  328 */         var4 += 5;
/*      */       }
/*  330 */       else if (var15 == 4.0D)
/*      */       {
/*  332 */         var6 -= 5;
/*      */       }
/*  334 */       else if (var15 == 3.0D)
/*      */       {
/*  336 */         var4 -= 5;
/*      */       }
/*  338 */       else if (var15 == 2.0D)
/*      */       {
/*  340 */         var6 += 5;
/*      */       }
/*      */ 
/*  343 */       for (int var10 = -5; var10 <= 5; var10++)
/*      */       {
/*  345 */         for (int var11 = -5; var11 <= 5; var11++)
/*      */         {
/*  347 */           int var12 = var3.getTypeId(var10 + var4, var5, var11 + var6);
/*  348 */           int var13 = var3.getTypeId(var10 + var4, var5 + 1, var11 + var6);
/*      */ 
/*  350 */           if ((var12 == 12) && (var13 == 0) && (var10 % 5 == 0) && (var11 % 5 == 0) && (ConsumeReagentCactus(var2, false)))
/*      */           {
/*  352 */             var3.setTypeId(var10 + var4, var5 + 1, var11 + var6, Block.CACTUS.id);
/*  353 */             var3.a("largesmoke", var10 + var4, var5, var11 + var6, 0.0D, 0.05D, 0.0D);
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  358 */       return true;
/*      */     }
/*      */ 
/*  362 */     boolean var8 = false;
/*      */ 
/*  364 */     if ((var3.getTypeId(var4, var5, var6) != Block.DIRT.id) && (var3.getTypeId(var4, var5, var6) != Block.GRASS.id) && (var3.getTypeId(var4, var5, var6) != BlockFlower.LONG_GRASS.id))
/*      */     {
/*  366 */       return false;
/*      */     }
/*      */ 
/*  370 */     if (var3.getTypeId(var4, var5, var6) == BlockFlower.LONG_GRASS.id)
/*      */     {
/*  372 */       Block.byId[BlockFlower.LONG_GRASS.id].dropNaturally(var3, var4, var5, var6, 1, 1.0F, 1);
/*  373 */       var3.setTypeId(var4, var5, var6, 0);
/*  374 */       var5--;
/*      */     }
/*      */ 
/*  377 */     if ((var3.getMaterial(var4 + 1, var5, var6) == Material.WATER) || (var3.getMaterial(var4 - 1, var5, var6) == Material.WATER) || (var3.getMaterial(var4, var5, var6 + 1) == Material.WATER) || (var3.getMaterial(var4, var5, var6 - 1) == Material.WATER))
/*      */     {
/*  379 */       var8 = true;
/*      */     }
/*      */ 
/*  382 */     for (int var9 = -8; var9 <= 8; var9++)
/*      */     {
/*  384 */       for (int var10 = -8; var10 <= 8; var10++)
/*      */       {
/*  386 */         int var11 = var3.getTypeId(var9 + var4, var5, var10 + var6);
/*  387 */         int var12 = var3.getTypeId(var9 + var4, var5 + 1, var10 + var6);
/*      */ 
/*  389 */         if (var8)
/*      */         {
/*  391 */           if (((var3.getMaterial(var9 + var4 + 1, var5, var10 + var6) == Material.WATER) || (var3.getMaterial(var9 + var4 - 1, var5, var10 + var6) == Material.WATER) || (var3.getMaterial(var9 + var4, var5, var10 + var6 + 1) == Material.WATER) || (var3.getMaterial(var9 + var4, var5, var10 + var6 - 1) == Material.WATER)) && ((var11 == Block.DIRT.id) || (var11 == Block.GRASS.id)) && (var12 == 0) && (ConsumeReagentReed(var2, false)))
/*      */           {
/*  393 */             var3.setTypeId(var9 + var4, var5 + 1, var10 + var6, Block.SUGAR_CANE_BLOCK.id);
/*      */           }
/*      */         }
/*  396 */         else if (((var11 == Block.DIRT.id) || (var11 == Block.GRASS.id)) && ((var12 == 0) || (var12 == BlockFlower.LONG_GRASS.id)) && (var9 % 4 == 0) && (var10 % 4 == 0) && (ConsumeReagentSapling(var1, var2, false)))
/*      */         {
/*  398 */           if (var12 == BlockFlower.LONG_GRASS.id)
/*      */           {
/*  400 */             Block.byId[var12].dropNaturally(var3, var9 + var4, var5 + 1, var10 + var6, 1, 1.0F, 1);
/*  401 */             var3.setTypeId(var9 + var4, var5 + 1, var10 + var6, 0);
/*      */           }
/*      */ 
/*  404 */           var3.setTypeIdAndData(var9 + var4, var5 + 1, var10 + var6, Block.SAPLING.id, getTempMeta(var1));
/*  405 */           var3.a("largesmoke", var9 + var4, var5, var10 + var6, 0.0D, 0.05D, 0.0D);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  410 */     return true;
/*      */   }
/*      */ 
/*      */   private int getTempMeta(ItemStack var1)
/*      */   {
/*  419 */     return ((ItemEECharged)var1.getItem()).getInteger(var1, "tempMeta");
/*      */   }
/*      */ 
/*      */   public void doActiveHarvest(ItemStack var1, World var2, EntityHuman var3)
/*      */   {
/*  424 */     int var4 = (int)EEBase.playerX(var3);
/*  425 */     int var5 = (int)EEBase.playerY(var3);
/*  426 */     int var6 = (int)EEBase.playerZ(var3);
/*      */ 
/*  428 */     for (int var7 = -5; var7 <= 5; var7++)
/*      */     {
/*  430 */       for (int var8 = -5; var8 <= 5; var8++)
/*      */       {
/*  432 */         for (int var9 = -5; var9 <= 5; var9++)
/*      */         {
/*  434 */           int var10 = var2.getTypeId(var4 + var7, var5 + var8, var6 + var9);
/*      */ 
/*  436 */           if (var10 == Block.CROPS.id)
/*      */           {
/*  438 */             int var11 = var2.getData(var4 + var7, var5 + var8, var6 + var9);
/*      */ 
/*  440 */             if (var11 >= 7)
/*      */             {
/*  442 */               Block.byId[var10].dropNaturally(var2, var4 + var7, var5 + var8, var6 + var9, var2.getData(var4 + var7, var5 + var8, var6 + var9), 0.05F, 1);
/*  443 */               Block.byId[var10].dropNaturally(var2, var4 + var7, var5 + var8, var6 + var9, var2.getData(var4 + var7, var5 + var8, var6 + var9), 1.0F, 1);
/*  444 */               var2.setTypeId(var4 + var7, var5 + var8, var6 + var9, 0);
/*  445 */               var2.a("largesmoke", var4 + var7, var5 + var8, var6 + var9, 0.0D, 0.05D, 0.0D);
/*      */             }
/*  447 */             else if (var2.random.nextInt(400) == 0)
/*      */             {
/*  449 */               var11++;
/*  450 */               var2.setData(var4 + var7, var5 + var8, var6 + var9, var11);
/*      */             }
/*      */           }
/*  453 */           else if ((var10 != BlockFlower.YELLOW_FLOWER.id) && (var10 != BlockFlower.RED_ROSE.id) && (var10 != BlockFlower.BROWN_MUSHROOM.id) && (var10 != BlockFlower.RED_MUSHROOM.id) && (var10 != BlockFlower.LONG_GRASS.id))
/*      */           {
/*  455 */             if (((var10 == Block.SUGAR_CANE_BLOCK.id) && (var2.getTypeId(var4 + var7, var5 + var8 - 4, var6 + var9) == Block.SUGAR_CANE_BLOCK.id)) || ((var10 == Block.CACTUS.id) && (var2.getTypeId(var4 + var7, var5 + var8 - 4, var6 + var9) == Block.CACTUS.id)))
/*      */             {
/*  457 */               if (var10 == Block.SUGAR_CANE_BLOCK.id)
/*      */               {
/*  459 */                 Block.byId[var10].dropNaturally(var2, var4 + var7, var5 + var8 - 3, var6 + var9, var2.getData(var4 + var7, var5 + var8, var6 + var9), 0.25F, 1);
/*  460 */                 Block.byId[var10].dropNaturally(var2, var4 + var7, var5 + var8 - 3, var6 + var9, var2.getData(var4 + var7, var5 + var8, var6 + var9), 1.0F, 1);
/*  461 */                 var2.setTypeId(var4 + var7, var5 + var8 - 3, var6 + var9, 0);
/*      */               }
/*      */               else
/*      */               {
/*  465 */                 Block.byId[var10].dropNaturally(var2, var4 + var7, var5 + var8 - 4, var6 + var9, var2.getData(var4 + var7, var5 + var8, var6 + var9), 0.25F, 1);
/*  466 */                 Block.byId[var10].dropNaturally(var2, var4 + var7, var5 + var8 - 4, var6 + var9, var2.getData(var4 + var7, var5 + var8, var6 + var9), 1.0F, 1);
/*  467 */                 var2.setTypeId(var4 + var7, var5 + var8 - 4, var6 + var9, 0);
/*      */               }
/*      */ 
/*  470 */               var2.a("largesmoke", var4 + var7, var5 + var8 - 3, var6 + var9, 0.0D, 0.05D, 0.0D);
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  475 */             Block.byId[var10].dropNaturally(var2, var4 + var7, var5 + var8, var6 + var9, var2.getData(var4 + var7, var5 + var8, var6 + var9), 0.05F, 1);
/*  476 */             Block.byId[var10].dropNaturally(var2, var4 + var7, var5 + var8, var6 + var9, var2.getData(var4 + var7, var5 + var8, var6 + var9), 1.0F, 1);
/*  477 */             var2.setTypeId(var4 + var7, var5 + var8, var6 + var9, 0);
/*  478 */             var2.a("largesmoke", var4 + var7, var5 + var8, var6 + var9, 0.0D, 0.05D, 0.0D);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean ConsumeReagentCactus(EntityHuman var1, boolean var2)
/*      */   {
/*  487 */     return EEBase.Consume(new ItemStack(Block.CACTUS, 1), var1, var2);
/*      */   }
/*      */ 
/*      */   public boolean ConsumeReagentSeeds(EntityHuman var1, boolean var2)
/*      */   {
/*  492 */     return EEBase.Consume(new ItemStack(Item.SEEDS, 1), var1, var2);
/*      */   }
/*      */ 
/*      */   public boolean ConsumeReagentReed(EntityHuman var1, boolean var2)
/*      */   {
/*  497 */     return EEBase.Consume(new ItemStack(Item.SUGAR_CANE, 1), var1, var2);
/*      */   }
/*      */ 
/*      */   public void doFertilize(ItemStack var1, World var2, EntityHuman var3)
/*      */   {
/*  502 */     boolean var4 = false;
/*  503 */     boolean var5 = true;
/*  504 */     boolean var6 = true;
/*  505 */     int var7 = (int)EEBase.playerX(var3);
/*  506 */     int var8 = (int)EEBase.playerY(var3);
/*  507 */     int var9 = (int)EEBase.playerZ(var3);
/*      */ 
/*  509 */     for (int var10 = -15; var10 <= 15; var10++)
/*      */     {
/*  511 */       for (int var11 = -15; var11 <= 15; var11++)
/*      */       {
/*  513 */         for (int var12 = -15; var12 <= 15; var12++)
/*      */         {
/*  515 */           int var13 = var2.getTypeId(var7 + var10, var8 + var11, var9 + var12);
/*      */ 
/*  518 */           if ((3 >= var10) && (var10 >= -3) && (3 >= var11) && (var11 >= -3) && (3 >= var12) && (var12 >= -3))
/*      */           {
/*  520 */             if (var13 == Block.CROPS.id)
/*      */             {
/*  522 */               int var14 = var2.getData(var7 + var10, var8 + var11, var9 + var12);
/*      */ 
/*  524 */               if (var14 < 7)
/*      */               {
/*  526 */                 if (!var4)
/*      */                 {
/*  528 */                   if (ConsumeReagentBonemeal(var3, var5))
/*      */                   {
/*  530 */                     if (var6)
/*      */                     {
/*  532 */                       var2.makeSound(var3, "flash", 0.7F, 1.0F);
/*  533 */                       var6 = false;
/*      */                     }
/*      */ 
/*  536 */                     var4 = true;
/*  537 */                     var14++;
/*  538 */                     var2.a("largesmoke", var7 + var10, var8 + var11, var9 + var12, 0.0D, 0.05D, 0.0D);
/*  539 */                     var2.setData(var7 + var10, var8 + var11, var9 + var12, var14);
/*      */                   }
/*      */ 
/*  542 */                   var5 = false;
/*      */                 }
/*      */                 else
/*      */                 {
/*  546 */                   if (var6)
/*      */                   {
/*  548 */                     var2.makeSound(var3, "flash", 0.7F, 1.0F);
/*  549 */                     var6 = false;
/*      */                   }
/*      */ 
/*  552 */                   var14++;
/*  553 */                   var2.a("largesmoke", var7 + var10, var8 + var11, var9 + var12, 0.0D, 0.05D, 0.0D);
/*  554 */                   var2.setData(var7 + var10, var8 + var11, var9 + var12, var14);
/*      */                 }
/*      */               }
/*      */             }
/*  558 */             else if ((var13 == Block.SUGAR_CANE_BLOCK.id) && (var2.getTypeId(var7 + var10, var8 + var11 - 4, var9 + var12) != Block.SUGAR_CANE_BLOCK.id) && (var2.getTypeId(var7 + var10, var8 + var11 + 1, var9 + var12) == 0))
/*      */             {
/*  560 */               if (!var4)
/*      */               {
/*  562 */                 if (ConsumeReagentBonemeal(var3, var5))
/*      */                 {
/*  564 */                   if (var6)
/*      */                   {
/*  566 */                     var2.makeSound(var3, "flash", 0.7F, 1.0F);
/*  567 */                     var6 = false;
/*      */                   }
/*      */ 
/*  570 */                   var4 = true;
/*  571 */                   var2.setTypeId(var7 + var10, var8 + var11 + 1, var9 + var12, Block.SUGAR_CANE_BLOCK.id);
/*      */                 }
/*      */ 
/*  574 */                 var5 = false;
/*      */               }
/*      */               else
/*      */               {
/*  578 */                 if (var6)
/*      */                 {
/*  580 */                   var2.makeSound(var3, "flash", 0.7F, 1.0F);
/*  581 */                   var6 = false;
/*      */                 }
/*      */ 
/*  584 */                 var2.setTypeId(var7 + var10, var8 + var11 + 1, var9 + var12, Block.SUGAR_CANE_BLOCK.id);
/*      */               }
/*      */             }
/*  587 */             else if ((var13 == Block.CACTUS.id) && (var2.getTypeId(var7 + var10, var8 + var11 - 4, var9 + var12) != Block.CACTUS.id) && (var2.getTypeId(var7 + var10, var8 + var11 + 1, var9 + var12) == 0))
/*      */             {
/*  589 */               if (!var4)
/*      */               {
/*  591 */                 if (ConsumeReagentBonemeal(var3, var5))
/*      */                 {
/*  593 */                   if (var6)
/*      */                   {
/*  595 */                     var2.makeSound(var3, "flash", 0.7F, 1.0F);
/*  596 */                     var6 = false;
/*      */                   }
/*      */ 
/*  599 */                   var4 = true;
/*  600 */                   var2.setTypeId(var7 + var10, var8 + var11 + 1, var9 + var12, Block.CACTUS.id);
/*      */                 }
/*      */ 
/*  603 */                 var5 = false;
/*      */               }
/*      */               else
/*      */               {
/*  607 */                 if (var6)
/*      */                 {
/*  609 */                   var2.makeSound(var3, "flash", 0.7F, 1.0F);
/*  610 */                   var6 = false;
/*      */                 }
/*      */ 
/*  613 */                 var2.setTypeId(var7 + var10, var8 + var11 + 1, var9 + var12, Block.CACTUS.id);
/*      */               }
/*      */             }
/*      */ 
/*  617 */             if ((var13 == BlockFlower.RED_ROSE.id) || (var13 == BlockFlower.YELLOW_FLOWER.id) || (var13 == BlockFlower.BROWN_MUSHROOM.id) || (var13 == BlockFlower.RED_MUSHROOM.id))
/*      */             {
/*  619 */               for (int var14 = -1; var14 <= 0; var14++)
/*      */               {
/*  621 */                 if (var2.getTypeId(var7 + var10 + var14, var8 + var11, var9 + var12) == 0)
/*      */                 {
/*  623 */                   if (!var4)
/*      */                   {
/*  625 */                     if (ConsumeReagentBonemeal(var3, var5))
/*      */                     {
/*  627 */                       if (var6)
/*      */                       {
/*  629 */                         var2.makeSound(var3, "flash", 0.7F, 1.0F);
/*  630 */                         var6 = false;
/*      */                       }
/*      */ 
/*  633 */                       var4 = true;
/*  634 */                       var2.setTypeId(var7 + var10 + var14, var8 + var11, var9 + var12, var13);
/*      */                     }
/*      */ 
/*  637 */                     var5 = false;
/*      */                   }
/*      */                   else
/*      */                   {
/*  641 */                     if (var6)
/*      */                     {
/*  643 */                       var2.makeSound(var3, "flash", 0.7F, 1.0F);
/*  644 */                       var6 = false;
/*      */                     }
/*      */ 
/*  647 */                     var2.setTypeId(var7 + var10 + var14, var8 + var11, var9 + var12, var13);
/*      */                   }
/*      */                 }
/*  650 */                 else if (var2.getTypeId(var7 + var10, var8 + var11, var9 + var12 + var14) == 0)
/*      */                 {
/*  652 */                   if (var4)
/*      */                   {
/*  654 */                     if (var6)
/*      */                     {
/*  656 */                       var2.makeSound(var3, "flash", 0.7F, 1.0F);
/*  657 */                       var6 = false;
/*      */                     }
/*      */ 
/*  660 */                     var2.setTypeId(var7 + var10, var8 + var11, var9 + var12 + var14, var13);
/*  661 */                     break;
/*      */                   }
/*      */ 
/*  664 */                   if (ConsumeReagentBonemeal(var3, var5))
/*      */                   {
/*  666 */                     if (var6)
/*      */                     {
/*  668 */                       var2.makeSound(var3, "flash", 0.7F, 1.0F);
/*  669 */                       var6 = false;
/*      */                     }
/*      */ 
/*  672 */                     var4 = true;
/*  673 */                     var2.setTypeId(var7 + var10, var8 + var11, var9 + var12 + var14, var13);
/*      */                   }
/*      */ 
/*  676 */                   var5 = false;
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */ 
/*  682 */           if (var13 == Block.SAPLING.id)
/*      */           {
/*  686 */             if (!var4)
/*      */             {
/*  688 */               if (ConsumeReagentBonemeal(var3, var5))
/*      */               {
/*  690 */                 if (var6)
/*      */                 {
/*  692 */                   var2.makeSound(var3, "flash", 0.7F, 1.0F);
/*  693 */                   var6 = false;
/*      */                 }
/*      */ 
/*  696 */                 var4 = true;
/*      */ 
/*  698 */                 if (var2.random.nextInt(100) < 25)
/*      */                 {
/*  700 */                   int var14 = var2.getData(var7 + var10, var8 + var11, var9 + var12) & 0x3;
/*  701 */                   var2.setRawTypeId(var7 + var10, var8 + var11, var9 + var12, 0);
/*  702 */                   Object var15 = null;
/*      */ 
/*  704 */                   if (var14 == 1)
/*      */                   {
/*  706 */                     var15 = new WorldGenTaiga2(true);
/*      */                   }
/*  708 */                   else if (var14 == 2)
/*      */                   {
/*  710 */                     var15 = new WorldGenForest(true);
/*      */                   }
/*      */                   else
/*      */                   {
/*  714 */                     var15 = new WorldGenTrees(true);
/*      */ 
/*  716 */                     if (var2.random.nextInt(10) == 0)
/*      */                     {
/*  718 */                       var15 = new WorldGenBigTree(true);
/*      */                     }
/*      */                   }
/*      */ 
/*  722 */                   var2.a("largesmoke", var7 + var10, var8 + var11, var9 + var12, 0.0D, 0.05D, 0.0D);
/*      */ 
/*  724 */                   if (!((WorldGenerator)var15).a(var2, var2.random, var7 + var10, var8 + var11, var9 + var12))
/*      */                   {
/*  726 */                     var2.setRawTypeIdAndData(var7 + var10, var8 + var11, var9 + var12, Block.SAPLING.id, var14);
/*      */                   }
/*      */                 }
/*      */               }
/*      */ 
/*  731 */               var5 = false;
/*      */             }
/*      */             else
/*      */             {
/*  735 */               if (var6)
/*      */               {
/*  737 */                 var2.makeSound(var3, "flash", 0.7F, 1.0F);
/*  738 */                 var6 = false;
/*      */               }
/*      */ 
/*  741 */               if (var2.random.nextInt(100) < 25)
/*      */               {
/*  743 */                 int var14 = var2.getData(var7 + var10, var8 + var11, var9 + var12) & 0x3;
/*  744 */                 var2.setRawTypeId(var7 + var10, var8 + var11, var9 + var12, 0);
/*  745 */                 Object var15 = null;
/*      */ 
/*  747 */                 if (var14 == 1)
/*      */                 {
/*  749 */                   var15 = new WorldGenTaiga2(true);
/*      */                 }
/*  751 */                 else if (var14 == 2)
/*      */                 {
/*  753 */                   var15 = new WorldGenForest(true);
/*      */                 }
/*      */                 else
/*      */                 {
/*  757 */                   var15 = new WorldGenTrees(true);
/*      */ 
/*  759 */                   if (var2.random.nextInt(10) == 0)
/*      */                   {
/*  761 */                     var15 = new WorldGenBigTree(true);
/*      */                   }
/*      */                 }
/*      */ 
/*  765 */                 var2.a("largesmoke", var7 + var10, var8 + var11, var9 + var12, 0.0D, 0.05D, 0.0D);
/*      */ 
/*  767 */                 if (!((WorldGenerator)var15).a(var2, var2.random, var7 + var10, var8 + var11, var9 + var12))
/*      */                 {
/*  769 */                   var2.setRawTypeIdAndData(var7 + var10, var8 + var11, var9 + var12, Block.SAPLING.id, var14);
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void doFireWall(ItemStack var1, World var2, EntityHuman var3)
/*      */   {
/*  781 */     byte var4 = 10;
/*  782 */     var2.makeSound(var3, "wall", 1.0F, 1.0F);
/*  783 */     int var5 = (int)EEBase.playerX(var3);
/*  784 */     int var6 = (int)EEBase.playerY(var3);
/*  785 */     int var7 = (int)EEBase.playerZ(var3);
/*  786 */     double var8 = MathHelper.floor(var3.yaw * 4.0F / 360.0F + 0.5D) & 0x3;
/*      */ 
/*  788 */     for (int var10 = -1; var10 <= 1; var10++)
/*      */     {
/*  790 */       for (int var11 = -2; var11 <= 1; var11++)
/*      */       {
/*  792 */         for (int var12 = -var4 * 3; var12 <= var4 * 3; var12++)
/*      */         {
/*  794 */           if (var8 == 3.0D)
/*      */           {
/*  796 */             if (((var2.getTypeId(var5 + var10, var6 + var11, var7 + var12) == 0) || (var2.getTypeId(var5 + var10, var6 + var11, var7 + var12) == 78)) && (var2.getTypeId(var5 + var10, var6 + var11 - 1, var7 + var12) != 0))
/*      */             {
/*  798 */               var2.setTypeId(var5 + var10, var6 + var11, var7 + var12, Block.FIRE.id);
/*      */             }
/*      */           }
/*  801 */           else if (var8 == 2.0D)
/*      */           {
/*  803 */             if (((var2.getTypeId(var5 + var12, var6 + var11, var7 - var10) == 0) || (var2.getTypeId(var5 + var12, var6 + var11, var7 - var10) == 78)) && (var2.getTypeId(var5 + var12, var6 + var11 - 1, var7 - var10) != 0))
/*      */             {
/*  805 */               var2.setTypeId(var5 + var12, var6 + var11, var7 - var10, Block.FIRE.id);
/*      */             }
/*      */           }
/*  808 */           else if (var8 == 1.0D)
/*      */           {
/*  810 */             if (((var2.getTypeId(var5 - var10, var6 + var11, var7 + var12) == 0) || (var2.getTypeId(var5 - var10, var6 + var11, var7 + var12) == 78)) && (var2.getTypeId(var5 - var10, var6 + var11 - 1, var7 + var12) != 0))
/*      */             {
/*  812 */               var2.setTypeId(var5 - var10, var6 + var11, var7 + var12, Block.FIRE.id);
/*      */             }
/*      */           }
/*  815 */           else if ((var8 == 0.0D) && ((var2.getTypeId(var5 + var12, var6 + var11, var7 + var10) == 0) || (var2.getTypeId(var5 + var12, var6 + var11, var7 + var10) == 78)) && (var2.getTypeId(var5 + var12, var6 + var11 - 1, var7 + var10) != 0))
/*      */           {
/*  817 */             var2.setTypeId(var5 + var12, var6 + var11, var7 + var10, Block.FIRE.id);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void ConsumeReagent(ItemStack var1, EntityHuman var2, boolean var3)
/*      */   {
/*  826 */     EEBase.ConsumeReagentForDuration(var1, var2, var3);
/*      */   }
/*      */ 
/*      */   public void a(ItemStack var1, World var2, Entity var3, int var4, boolean var5)
/*      */   {
/*  835 */     if (!EEProxy.isClient(var2))
/*      */     {
/*  837 */       if (!isInitialized(var1))
/*      */       {
/*  839 */         changeModes(var1);
/*  840 */         initialize(var1);
/*      */       }
/*      */ 
/*  843 */       updateIcon(var1);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void doBurnOverTime(ItemStack var1, World var2, EntityHuman var3)
/*      */   {
/*  849 */     int var4 = (int)EEBase.playerX(var3);
/*  850 */     int var5 = (int)EEBase.playerY(var3);
/*  851 */     int var6 = (int)EEBase.playerZ(var3);
/*  852 */     List var7 = var2.a(EntityMonster.class, AxisAlignedBB.b(var3.locX - 5.0D, var3.locY - 5.0D, var3.locZ - 5.0D, var3.locX + 5.0D, var3.locY + 5.0D, var3.locZ + 5.0D));
/*      */ 
/*  855 */     for (int var8 = 0; var8 < var7.size(); var8++)
/*      */     {
/*  857 */       if (var2.random.nextInt(30) == 0)
/*      */       {
/*  859 */         Entity var9 = (Entity)var7.get(var8);
/*  860 */         EEProxy.dealFireDamage(var9, 5);
/*  861 */         var9.setOnFire(60);
/*      */       }
/*      */     }
/*      */ 
/*  865 */     for (int var8 = -4; var8 <= 4; var8++)
/*      */     {
/*  867 */       for (int var13 = -4; var13 <= 4; var13++)
/*      */       {
/*  869 */         for (int var10 = -4; var10 <= 4; var10++)
/*      */         {
/*  871 */           if (((var8 <= -2) || (var8 >= 2) || (var13 != 0)) && ((var10 <= -2) || (var10 >= 2) || (var13 != 0)) && (var2.random.nextInt(120) == 0))
/*      */           {
/*  873 */             if ((var2.getTypeId(var4 + var8, var5 + var13, var6 + var10) == 0) && (var2.getTypeId(var4 + var8, var5 + var13 - 1, var6 + var10) != 0))
/*      */             {
/*  875 */               var2.setTypeId(var4 + var8, var5 + var13, var6 + var10, Block.FIRE.id);
/*      */             }
/*      */             else
/*      */             {
/*  879 */               boolean var11 = false;
/*      */ 
/*  882 */               for (int var12 = -1; var12 <= 1; var12++)
/*      */               {
/*  884 */                 if ((var2.getTypeId(var4 + var8 + var12, var5 + var13, var6 + var10) == Block.LEAVES.id) || (var2.getTypeId(var4 + var8 + var12, var5 + var13, var6 + var10) == Block.LOG.id))
/*      */                 {
/*  886 */                   var2.setTypeId(var4 + var8, var5 + var13, var6 + var10, Block.FIRE.id);
/*  887 */                   var11 = true;
/*  888 */                   break;
/*      */                 }
/*      */               }
/*      */ 
/*  892 */               if (!var11)
/*      */               {
/*  894 */                 for (int var12 = -1; var12 <= 1; var12++)
/*      */                 {
/*  896 */                   if ((var2.getTypeId(var4 + var8, var5 + var13 + var12, var6 + var10) == Block.LEAVES.id) || (var2.getTypeId(var4 + var8, var5 + var13 + var12, var6 + var10) == Block.LOG.id))
/*      */                   {
/*  898 */                     var2.setTypeId(var4 + var8, var5 + var13, var6 + var10, Block.FIRE.id);
/*  899 */                     var11 = true;
/*  900 */                     break;
/*      */                   }
/*      */                 }
/*      */               }
/*      */ 
/*  905 */               if (!var11)
/*      */               {
/*  907 */                 for (int var12 = -1; var12 <= 1; var12++)
/*      */                 {
/*  909 */                   if ((var2.getTypeId(var4 + var8, var5 + var13, var6 + var10 + var12) == Block.LEAVES.id) || (var2.getTypeId(var4 + var8, var5 + var13, var6 + var10 + var12) == Block.LOG.id))
/*      */                   {
/*  911 */                     var2.setTypeId(var4 + var8, var5 + var13, var6 + var10, Block.FIRE.id);
/*  912 */                     var11 = true;
/*  913 */                     break;
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void doFreezeOverTime(ItemStack var1, World var2, EntityHuman var3)
/*      */   {
/*  926 */     int var4 = (int)EEBase.playerX(var3);
/*  927 */     int var5 = (int)EEBase.playerY(var3);
/*  928 */     int var6 = (int)EEBase.playerZ(var3);
/*  929 */     List var7 = var2.a(EntityMonster.class, AxisAlignedBB.b(var3.locX - 5.0D, var3.locY - 5.0D, var3.locZ - 5.0D, var3.locX + 5.0D, var3.locY + 5.0D, var3.locZ + 5.0D));
/*      */ 
/*  932 */     for (int var8 = 0; var8 < var7.size(); var8++)
/*      */     {
/*  934 */       Entity var9 = (Entity)var7.get(var8);
/*      */ 
/*  936 */       if ((var9.motX > 0.0D) || (var9.motZ > 0.0D))
/*      */       {
/*  938 */         var9.motX *= 0.2D;
/*  939 */         var9.motZ *= 0.2D;
/*      */       }
/*      */     }
/*      */ 
/*  943 */     for (int var8 = -4; var8 <= 4; var8++)
/*      */     {
/*  945 */       for (int var12 = -4; var12 <= 4; var12++)
/*      */       {
/*  947 */         for (int var10 = -4; var10 <= 4; var10++)
/*      */         {
/*  949 */           if (((var8 <= -2) || (var8 >= 2) || (var12 != 0)) && ((var10 <= -2) || (var10 >= 2) || (var12 != 0)))
/*      */           {
/*  951 */             if (var2.random.nextInt(20) == 0)
/*      */             {
/*  953 */               int var11 = var2.getTypeId(var4 + var8, var5 + var12 - 1, var6 + var10);
/*      */ 
/*  955 */               if ((var11 != 0) && (Block.byId[var11].a()) && (var2.getMaterial(var4 + var8, var5 + var12 - 1, var6 + var10).isBuildable()) && (var2.getTypeId(var4 + var8, var5 + var12, var6 + var10) == 0))
/*      */               {
/*  957 */                 var2.setTypeId(var4 + var8, var5 + var12, var6 + var10, Block.SNOW.id);
/*      */               }
/*      */             }
/*      */ 
/*  961 */             if ((var2.random.nextInt(3) == 0) && (var2.getMaterial(var4 + var8, var5 + var12, var6 + var10) == Material.WATER) && (var2.getTypeId(var4 + var8, var5 + var12 + 1, var6 + var10) == 0))
/*      */             {
/*  963 */               var2.setTypeId(var4 + var8, var5 + var12, var6 + var10, Block.ICE.id);
/*      */             }
/*      */ 
/*  966 */             if ((var2.random.nextInt(3) == 0) && (var2.getMaterial(var4 + var8, var5 + var12, var6 + var10) == Material.LAVA) && (var2.getTypeId(var4 + var8, var5 + var12 + 1, var6 + var10) == 0) && (var2.getData(var4 + var8, var5 + var12, var6 + var10) == 0))
/*      */             {
/*  968 */               var2.setTypeId(var4 + var8, var5 + var12, var6 + var10, Block.OBSIDIAN.id);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void doHeld(ItemStack var1, World var2, EntityHuman var3) {
/*      */   }
/*      */ 
/*      */   public void doRelease(ItemStack var1, World var2, EntityHuman var3) {
/*  980 */     if (getMode(var1) == "wind")
/*      */     {
/*  982 */       doThunder(var1, var2, var3);
/*      */     }
/*      */ 
/*  985 */     if (getMode(var1) == "ice")
/*      */     {
/*  987 */       doFreeze(var1, var2, var3);
/*      */     }
/*      */ 
/*  990 */     if (getMode(var1) == "fire")
/*      */     {
/*  992 */       doFireWall(var1, var2, var3);
/*      */     }
/*      */ 
/*  995 */     if (getMode(var1) == "earth")
/*      */     {
/*  997 */       doFertilize(var1, var2, var3);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void doFreeze(ItemStack var1, World var2, EntityHuman var3)
/*      */   {
/* 1003 */     int var4 = chargeLevel(var1);
/* 1004 */     var2.makeSound(var3, "wall", 1.0F, 1.0F);
/* 1005 */     int var5 = (int)EEBase.playerX(var3);
/* 1006 */     int var6 = (int)EEBase.playerY(var3);
/* 1007 */     int var7 = (int)EEBase.playerZ(var3);
/*      */ 
/* 1009 */     for (int var8 = -var4 - 1; var8 <= var4 + 1; var8++)
/*      */     {
/* 1011 */       for (int var9 = -2; var9 <= 1; var9++)
/*      */       {
/* 1013 */         for (int var10 = -var4 - 1; var10 <= var4 + 1; var10++)
/*      */         {
/* 1015 */           int var11 = var2.getTypeId(var5 + var10, var6 + var9 - 1, var7 + var8);
/*      */ 
/* 1017 */           if ((var11 != 0) && (Block.byId[var11].a()) && (var2.getMaterial(var5 + var10, var6 + var9 - 1, var7 + var8).isBuildable()) && (var2.getTypeId(var5 + var10, var6 + var9, var7 + var8) == 0))
/*      */           {
/* 1019 */             var2.setTypeId(var5 + var10, var6 + var9, var7 + var8, Block.SNOW.id);
/*      */           }
/*      */ 
/* 1022 */           if ((var2.getMaterial(var5 + var10, var6 + var9, var7 + var8) == Material.WATER) && (var2.getTypeId(var5 + var10, var6 + var9 + 1, var7 + var8) == 0))
/*      */           {
/* 1024 */             var2.setTypeId(var5 + var10, var6 + var9, var7 + var8, Block.ICE.id);
/*      */           }
/*      */ 
/* 1027 */           if ((var2.getMaterial(var5 + var10, var6 + var9, var7 + var8) == Material.LAVA) && (var2.getTypeId(var5 + var10, var6 + var9 + 1, var7 + var8) == 0) && (var2.getData(var5 + var10, var6 + var9, var7 + var8) == 0))
/*      */           {
/* 1029 */             var2.setTypeId(var5 + var10, var6 + var9, var7 + var8, Block.OBSIDIAN.id);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
/*      */   {
/* 1041 */     if (EEProxy.isClient(var2))
/*      */     {
/* 1043 */       return var1;
/*      */     }
/*      */ 
/* 1047 */     if (getMode(var1) == "wind")
/*      */     {
/* 1049 */       doGale(var1, var2, var3);
/*      */     }
/*      */ 
/* 1052 */     if (getMode(var1) == "ice")
/*      */     {
/* 1054 */       doSnowball(var1, var2, var3);
/*      */     }
/*      */ 
/* 1057 */     if (getMode(var1) == "fire")
/*      */     {
/* 1059 */       doFireball(var1, var2, var3);
/*      */     }
/*      */ 
/* 1062 */     return var1;
/*      */   }
/*      */ 
/*      */   private void doSnowball(ItemStack var1, World var2, EntityHuman var3)
/*      */   {
/* 1068 */     var3.C_();
/* 1069 */     var2.makeSound(var3, "random.bow", 0.5F, 0.4F / (c.nextFloat() * 0.4F + 0.8F));
/*      */ 
/* 1071 */     if (!var2.isStatic)
/*      */     {
/* 1073 */       var2.addEntity(new EntitySnowball(var2, var3));
/*      */     }
/*      */   }
/*      */ 
/*      */   private void doFireball(ItemStack var1, World var2, EntityHuman var3)
/*      */   {
/* 1079 */     var3.C_();
/* 1080 */     var2.makeSound(var3, "wall", 1.0F, 1.0F);
/* 1081 */     var2.addEntity(new EntityPyrokinesis(var2, var3));
/*      */   }
/*      */   public void doChargeTick(ItemStack var1, World var2, EntityHuman var3) {
/*      */   }
/*      */ 
/*      */   public void doUncharge(ItemStack var1, World var2, EntityHuman var3) {
/*      */   }
/*      */ 
/*      */   public void doPassive(ItemStack var1, World var2, EntityHuman var3) {
/* 1090 */     if (var3.fallDistance >= 0.0F)
/*      */     {
/* 1092 */       var3.fallDistance = 0.0F;
/*      */     }
/*      */ 
/* 1095 */     decThunderCooldown(var1);
/*      */ 
/* 1097 */     if (isActivated(var1))
/*      */     {
/* 1099 */       if (getMode(var1) == "wind")
/*      */       {
/* 1101 */         if (EEBase.getPlayerEffect(var1.getItem(), var3) <= 0)
/*      */         {
/* 1103 */           ConsumeReagent(var1, var3, false);
/*      */         }
/*      */ 
/* 1106 */         if (EEBase.getPlayerEffect(var1.getItem(), var3) > 0)
/*      */         {
/* 1108 */           doInterdiction(var1, var2, var3);
/* 1109 */           EEBase.updatePlayerEffect(var1.getItem(), EEBase.getPlayerEffect(var1.getItem(), var3) - 1, var3);
/*      */         }
/*      */         else
/*      */         {
/* 1113 */           doToggle(var1, var2, var3);
/*      */         }
/*      */       }
/*      */ 
/* 1117 */       if (getMode(var1) == "ice")
/*      */       {
/* 1119 */         doFreezeOverTime(var1, var2, var3);
/*      */       }
/*      */ 
/* 1122 */       if (getMode(var1) == "fire")
/*      */       {
/* 1124 */         doBurnOverTime(var1, var2, var3);
/*      */       }
/*      */     }
/*      */ 
/* 1128 */     if (getMode(var1) == "earth")
/*      */     {
/* 1130 */       doPassiveHarvest(var1, var2, var3);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void doActive(ItemStack var1, World var2, EntityHuman var3)
/*      */   {
/* 1136 */     if (getMode(var1) == "earth")
/*      */     {
/* 1138 */       doActiveHarvest(var1, var2, var3);
/*      */     }
/*      */   }
/*      */ 
/*      */   private String getMode(ItemStack var1)
/*      */   {
/* 1144 */     if ((getString(var1, "mode") == "") || (getString(var1, "mode") == null))
/*      */     {
/* 1146 */       setMode(var1, "ice");
/*      */     }
/*      */ 
/* 1149 */     return getString(var1, "mode");
/*      */   }
/*      */ 
/*      */   private void setMode(ItemStack var1, String var2)
/*      */   {
/* 1154 */     setString(var1, "mode", var2);
/*      */   }
/*      */ 
/*      */   private boolean isInitialized(ItemStack var1)
/*      */   {
/* 1159 */     return getBoolean(var1, "init");
/*      */   }
/*      */ 
/*      */   private void initialize(ItemStack var1)
/*      */   {
/* 1164 */     setBoolean(var1, "init", true);
/*      */   }
/*      */ 
/*      */   private void changeModes(ItemStack var1)
/*      */   {
/* 1169 */     if (getMode(var1) == "ice")
/*      */     {
/* 1171 */       setMode(var1, "fire");
/* 1172 */       var1.setData(var1.getData() + 2);
/*      */     }
/* 1174 */     else if (getMode(var1) == "fire")
/*      */     {
/* 1176 */       setMode(var1, "wind");
/* 1177 */       var1.setData(var1.getData() + 2);
/*      */     }
/* 1179 */     else if (getMode(var1) == "wind")
/*      */     {
/* 1181 */       setMode(var1, "earth");
/* 1182 */       var1.setData(var1.getData() + 2);
/*      */     }
/*      */     else
/*      */     {
/* 1186 */       setMode(var1, "ice");
/* 1187 */       var1.setData(var1.getData() - 6);
/*      */     }
/*      */ 
/* 1190 */     updateIcon(var1);
/*      */   }
/*      */ 
/*      */   private void updateIcon(ItemStack var1)
/*      */   {
/* 1195 */     if ((getMode(var1) == "") || (getMode(var1) == null))
/*      */     {
/* 1197 */       var1.setData(0);
/* 1198 */       setMode(var1, "ice");
/*      */     }
/*      */ 
/* 1201 */     if (getMode(var1) == "earth")
/*      */     {
/* 1203 */       var1.setData(6);
/*      */     }
/*      */ 
/* 1206 */     if (getMode(var1) == "wind")
/*      */     {
/* 1208 */       var1.setData(4);
/*      */     }
/*      */ 
/* 1211 */     if (getMode(var1) == "fire")
/*      */     {
/* 1213 */       var1.setData(2);
/*      */     }
/*      */ 
/* 1216 */     if (getMode(var1) == "ice")
/*      */     {
/* 1218 */       var1.setData(0);
/*      */     }
/*      */ 
/* 1221 */     if ((isActivated(var1)) && ((var1.getData() & 0x1) == 0))
/*      */     {
/* 1223 */       var1.setData(var1.getData() + 1);
/*      */     }
/* 1225 */     else if ((!isActivated(var1)) && ((var1.getData() & 0x1) == 1))
/*      */     {
/* 1227 */       var1.setData(var1.getData() - 1);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void doAlternate(ItemStack var1, World var2, EntityHuman var3)
/*      */   {
/* 1233 */     changeModes(var1);
/*      */   }
/*      */ 
/*      */   public void doLeftClick(ItemStack var1, World var2, EntityHuman var3) {
/*      */   }
/*      */ 
/*      */   public void doToggle(ItemStack var1, World var2, EntityHuman var3) {
/* 1240 */     if (isActivated(var1))
/*      */     {
/* 1242 */       if ((var1.getData() & 0x1) == 0)
/*      */       {
/* 1244 */         var1.setData(var1.getData() + 1);
/*      */       }
/*      */ 
/* 1247 */       var1.tag.setBoolean("active", false);
/* 1248 */       var2.makeSound(var3, "break", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
/*      */     }
/*      */     else
/*      */     {
/* 1252 */       if ((var1.getData() & 0x1) == 1)
/*      */       {
/* 1254 */         var1.setData(var1.getData() - 1);
/*      */       }
/*      */ 
/* 1257 */       var1.tag.setBoolean("active", true);
/* 1258 */       var2.makeSound(var3, "heal", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
/*      */     }
/*      */ 
/* 1261 */     if (!EEProxy.isClient(var2))
/*      */     {
/* 1263 */       updateIcon(var1);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean canActivate()
/*      */   {
/* 1269 */     return true;
/*      */   }
/*      */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemArcaneRing
 * JD-Core Version:    0.6.2
 */