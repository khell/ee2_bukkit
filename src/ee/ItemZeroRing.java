/*     */ package ee;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.AxisAlignedBB;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityMonster;
/*     */ import net.minecraft.server.EntitySnowball;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.Material;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class ItemZeroRing extends ItemEECharged
/*     */ {
/*     */   public ItemZeroRing(int var1)
/*     */   {
/*  19 */     super(var1, 4);
/*     */   }
/*     */ 
/*     */   public int getIconFromDamage(int var1)
/*     */   {
/*  24 */     return !isActivated(var1) ? this.textureId : this.textureId + 1;
/*     */   }
/*     */ 
/*     */   public void doBreak(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*  29 */     int var4 = chargeLevel(var1);
/*  30 */     var2.makeSound(var3, "wall", 1.0F, 1.0F);
/*  31 */     int var5 = (int)EEBase.playerX(var3);
/*  32 */     int var6 = (int)EEBase.playerY(var3);
/*  33 */     int var7 = (int)EEBase.playerZ(var3);
/*     */ 
/*  35 */     for (int var8 = -var4 - 1; var8 <= var4 + 1; var8++)
/*     */     {
/*  37 */       for (int var9 = -2; var9 <= 1; var9++)
/*     */       {
/*  39 */         for (int var10 = -var4 - 1; var10 <= var4 + 1; var10++)
/*     */         {
/*  41 */           int var11 = var2.getTypeId(var5 + var10, var6 + var9 - 1, var7 + var8);
/*     */ 
/*  43 */           if ((var11 != 0) && (Block.byId[var11].a()) && (var2.getMaterial(var5 + var10, var6 + var9 - 1, var7 + var8).isBuildable()) && (var2.getTypeId(var5 + var10, var6 + var9, var7 + var8) == 0))
/*     */           {
/*  45 */             var2.setTypeId(var5 + var10, var6 + var9, var7 + var8, Block.SNOW.id);
/*     */           }
/*     */ 
/*  48 */           if ((var2.getMaterial(var5 + var10, var6 + var9, var7 + var8) == Material.WATER) && (var2.getTypeId(var5 + var10, var6 + var9 + 1, var7 + var8) == 0))
/*     */           {
/*  50 */             var2.setTypeId(var5 + var10, var6 + var9, var7 + var8, Block.ICE.id);
/*     */           }
/*     */ 
/*  53 */           if ((var2.getMaterial(var5 + var10, var6 + var9, var7 + var8) == Material.LAVA) && (var2.getTypeId(var5 + var10, var6 + var9 + 1, var7 + var8) == 0) && (var2.getData(var5 + var10, var6 + var9, var7 + var8) == 0))
/*     */           {
/*  55 */             var2.setTypeId(var5 + var10, var6 + var9, var7 + var8, Block.OBSIDIAN.id);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doFreezeOverTime(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*  64 */     int var4 = (int)EEBase.playerX(var3);
/*  65 */     int var5 = (int)EEBase.playerY(var3);
/*  66 */     int var6 = (int)EEBase.playerZ(var3);
/*  67 */     List var7 = var2.a(EntityMonster.class, AxisAlignedBB.b(var3.locX - 5.0D, var3.locY - 5.0D, var3.locZ - 5.0D, var3.locX + 5.0D, var3.locY + 5.0D, var3.locZ + 5.0D));
/*     */ 
/*  70 */     for (int var8 = 0; var8 < var7.size(); var8++)
/*     */     {
/*  72 */       Entity var9 = (Entity)var7.get(var8);
/*     */ 
/*  74 */       if ((var9.motX > 0.0D) || (var9.motZ > 0.0D))
/*     */       {
/*  76 */         var9.motX *= 0.2D;
/*  77 */         var9.motZ *= 0.2D;
/*     */       }
/*     */     }
/*     */ 
/*  81 */     for (var8 = -4; var8 <= 4; var8++)
/*     */     {
/*  83 */       for (int var12 = -4; var12 <= 4; var12++)
/*     */       {
/*  85 */         for (int var10 = -4; var10 <= 4; var10++)
/*     */         {
/*  87 */           if (((var8 <= -2) || (var8 >= 2) || (var12 != 0)) && ((var10 <= -2) || (var10 >= 2) || (var12 != 0)))
/*     */           {
/*  89 */             if (var2.random.nextInt(20) == 0)
/*     */             {
/*  91 */               int var11 = var2.getTypeId(var4 + var8, var5 + var12 - 1, var6 + var10);
/*     */ 
/*  93 */               if ((var11 != 0) && (Block.byId[var11].a()) && (var2.getMaterial(var4 + var8, var5 + var12 - 1, var6 + var10).isBuildable()) && (var2.getTypeId(var4 + var8, var5 + var12, var6 + var10) == 0))
/*     */               {
/*  95 */                 var2.setTypeId(var4 + var8, var5 + var12, var6 + var10, Block.SNOW.id);
/*     */               }
/*     */             }
/*     */ 
/*  99 */             if ((var2.random.nextInt(3) == 0) && (var2.getMaterial(var4 + var8, var5 + var12, var6 + var10) == Material.WATER) && (var2.getTypeId(var4 + var8, var5 + var12 + 1, var6 + var10) == 0))
/*     */             {
/* 101 */               var2.setTypeId(var4 + var8, var5 + var12, var6 + var10, Block.ICE.id);
/*     */             }
/*     */ 
/* 104 */             if ((var2.random.nextInt(3) == 0) && (var2.getMaterial(var4 + var8, var5 + var12, var6 + var10) == Material.LAVA) && (var2.getTypeId(var4 + var8, var5 + var12 + 1, var6 + var10) == 0) && (var2.getData(var4 + var8, var5 + var12, var6 + var10) == 0))
/*     */             {
/* 106 */               var2.setTypeId(var4 + var8, var5 + var12, var6 + var10, Block.OBSIDIAN.id);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 119 */     if (EEProxy.isClient(var2))
/*     */     {
/* 121 */       return var1;
/*     */     }
/*     */ 
/* 125 */     doBreak(var1, var2, var3);
/* 126 */     return var1;
/*     */   }
/*     */ 
/*     */   public void ConsumeReagent(ItemStack var1, EntityHuman var2, boolean var3)
/*     */   {
/* 132 */     EEBase.ConsumeReagentForDuration(var1, var2, var3);
/*     */   }
/*     */ 
/*     */   public void doActive(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 137 */     doFreezeOverTime(var1, var2, var3);
/*     */   }
/*     */ 
/*     */   public void doHeld(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doRelease(ItemStack var1, World var2, EntityHuman var3) {
/* 144 */     doBreak(var1, var2, var3);
/*     */   }
/*     */ 
/*     */   public void doAlternate(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doLeftClick(ItemStack var1, World var2, EntityHuman var3) {
/* 151 */     var3.C_();
/* 152 */     var2.makeSound(var3, "random.bow", 0.5F, 0.4F / (c.nextFloat() * 0.4F + 0.8F));
/*     */ 
/* 154 */     if (!var2.isStatic)
/*     */     {
/* 156 */       var2.addEntity(new EntitySnowball(var2, var3));
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean canActivate()
/*     */   {
/* 162 */     return true;
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemZeroRing
 * JD-Core Version:    0.6.2
 */