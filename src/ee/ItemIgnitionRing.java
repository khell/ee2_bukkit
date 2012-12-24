/*     */ package ee;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.AxisAlignedBB;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.BlockFire;
/*     */ import net.minecraft.server.BlockLeaves;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityMonster;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.MathHelper;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class ItemIgnitionRing extends ItemEECharged
/*     */ {
/*     */   public boolean itemCharging;
/*     */ 
/*     */   public ItemIgnitionRing(int var1)
/*     */   {
/*  20 */     super(var1, 4);
/*     */   }
/*     */ 
/*     */   public int getIconFromDamage(int var1)
/*     */   {
/*  25 */     return !isActivated(var1) ? this.textureId : this.textureId + 1;
/*     */   }
/*     */ 
/*     */   public void doBreak(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*  30 */     if (chargeLevel(var1) > 0)
/*     */     {
/*  32 */       int var4 = chargeLevel(var1);
/*  33 */       var2.makeSound(var3, "wall", 1.0F, 1.0F);
/*  34 */       int var5 = (int)EEBase.playerX(var3);
/*  35 */       int var6 = (int)EEBase.playerY(var3);
/*  36 */       int var7 = (int)EEBase.playerZ(var3);
/*  37 */       double var8 = MathHelper.floor(var3.yaw * 4.0F / 360.0F + 0.5D) & 0x3;
/*     */ 
/*  39 */       for (int var10 = -1; var10 <= 1; var10++)
/*     */       {
/*  41 */         for (int var11 = -2; var11 <= 1; var11++)
/*     */         {
/*  43 */           for (int var12 = -var4 * 3; var12 <= var4 * 3; var12++)
/*     */           {
/*  45 */             if (var8 == 3.0D)
/*     */             {
/*  47 */               if (((var2.getTypeId(var5 + var10, var6 + var11, var7 + var12) == 0) || (var2.getTypeId(var5 + var10, var6 + var11, var7 + var12) == 78)) && (var2.getTypeId(var5 + var10, var6 + var11 - 1, var7 + var12) != 0))
/*     */               {
/*  49 */                 var2.setTypeId(var5 + var10, var6 + var11, var7 + var12, Block.FIRE.id);
/*     */               }
/*     */             }
/*  52 */             else if (var8 == 2.0D)
/*     */             {
/*  54 */               if (((var2.getTypeId(var5 + var12, var6 + var11, var7 - var10) == 0) || (var2.getTypeId(var5 + var12, var6 + var11, var7 - var10) == 78)) && (var2.getTypeId(var5 + var12, var6 + var11 - 1, var7 - var10) != 0))
/*     */               {
/*  56 */                 var2.setTypeId(var5 + var12, var6 + var11, var7 - var10, Block.FIRE.id);
/*     */               }
/*     */             }
/*  59 */             else if (var8 == 1.0D)
/*     */             {
/*  61 */               if (((var2.getTypeId(var5 - var10, var6 + var11, var7 + var12) == 0) || (var2.getTypeId(var5 - var10, var6 + var11, var7 + var12) == 78)) && (var2.getTypeId(var5 - var10, var6 + var11 - 1, var7 + var12) != 0))
/*     */               {
/*  63 */                 var2.setTypeId(var5 - var10, var6 + var11, var7 + var12, Block.FIRE.id);
/*     */               }
/*     */             }
/*  66 */             else if ((var8 == 0.0D) && ((var2.getTypeId(var5 + var12, var6 + var11, var7 + var10) == 0) || (var2.getTypeId(var5 + var12, var6 + var11, var7 + var10) == 78)) && (var2.getTypeId(var5 + var12, var6 + var11 - 1, var7 + var10) != 0))
/*     */             {
/*  68 */               var2.setTypeId(var5 + var12, var6 + var11, var7 + var10, Block.FIRE.id);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doBurn(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*  78 */     int var4 = (int)EEBase.playerX(var3);
/*  79 */     int var5 = (int)EEBase.playerY(var3);
/*  80 */     int var6 = (int)EEBase.playerZ(var3);
/*  81 */     List var7 = var2.a(EntityMonster.class, AxisAlignedBB.b(var3.locX - 5.0D, var3.locY - 5.0D, var3.locZ - 5.0D, var3.locX + 5.0D, var3.locY + 5.0D, var3.locZ + 5.0D));
/*     */ 
/*  84 */     for (int var8 = 0; var8 < var7.size(); var8++)
/*     */     {
/*  86 */       if (var2.random.nextInt(30) == 0)
/*     */       {
/*  88 */         Entity var9 = (Entity)var7.get(var8);
/*  89 */         EEProxy.dealFireDamage(var9, 5);
/*  90 */         var9.setOnFire(60);
/*     */       }
/*     */     }
/*     */ 
/*  94 */     for (var8 = -4; var8 <= 4; var8++)
/*     */     {
/*  96 */       for (int var13 = -4; var13 <= 4; var13++)
/*     */       {
/*  98 */         for (int var10 = -4; var10 <= 4; var10++)
/*     */         {
/* 100 */           if (((var8 <= -2) || (var8 >= 2) || (var13 != 0)) && ((var10 <= -2) || (var10 >= 2) || (var13 != 0)) && (var2.random.nextInt(120) == 0))
/*     */           {
/* 102 */             if ((var2.getTypeId(var4 + var8, var5 + var13, var6 + var10) == 0) && (var2.getTypeId(var4 + var8, var5 + var13 - 1, var6 + var10) != 0))
/*     */             {
/* 104 */               var2.setTypeId(var4 + var8, var5 + var13, var6 + var10, Block.FIRE.id);
/*     */             }
/*     */             else
/*     */             {
/* 108 */               boolean var11 = false;
/*     */ 
/* 111 */               for (int var12 = -1; var12 <= 1; var12++)
/*     */               {
/* 113 */                 if ((var2.getTypeId(var4 + var8 + var12, var5 + var13, var6 + var10) == Block.LEAVES.id) || (var2.getTypeId(var4 + var8 + var12, var5 + var13, var6 + var10) == Block.LOG.id))
/*     */                 {
/* 115 */                   var2.setTypeId(var4 + var8, var5 + var13, var6 + var10, Block.FIRE.id);
/* 116 */                   var11 = true;
/* 117 */                   break;
/*     */                 }
/*     */               }
/*     */ 
/* 121 */               if (!var11)
/*     */               {
/* 123 */                 for (var12 = -1; var12 <= 1; var12++)
/*     */                 {
/* 125 */                   if ((var2.getTypeId(var4 + var8, var5 + var13 + var12, var6 + var10) == Block.LEAVES.id) || (var2.getTypeId(var4 + var8, var5 + var13 + var12, var6 + var10) == Block.LOG.id))
/*     */                   {
/* 127 */                     var2.setTypeId(var4 + var8, var5 + var13, var6 + var10, Block.FIRE.id);
/* 128 */                     var11 = true;
/* 129 */                     break;
/*     */                   }
/*     */                 }
/*     */               }
/*     */ 
/* 134 */               if (!var11)
/*     */               {
/* 136 */                 for (var12 = -1; var12 <= 1; var12++)
/*     */                 {
/* 138 */                   if ((var2.getTypeId(var4 + var8, var5 + var13, var6 + var10 + var12) == Block.LEAVES.id) || (var2.getTypeId(var4 + var8, var5 + var13, var6 + var10 + var12) == Block.LOG.id))
/*     */                   {
/* 140 */                     var2.setTypeId(var4 + var8, var5 + var13, var6 + var10, Block.FIRE.id);
/* 141 */                     var11 = true;
/* 142 */                     break;
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 158 */     if (EEProxy.isClient(var2))
/*     */     {
/* 160 */       return var1;
/*     */     }
/*     */ 
/* 164 */     doBreak(var1, var2, var3);
/* 165 */     return var1;
/*     */   }
/*     */ 
/*     */   public void ConsumeReagent(ItemStack var1, EntityHuman var2, boolean var3)
/*     */   {
/* 171 */     EEBase.ConsumeReagentForDuration(var1, var2, var3);
/*     */   }
/*     */ 
/*     */   public void doPassive(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 176 */     for (int var4 = -1; var4 <= 1; var4++)
/*     */     {
/* 178 */       for (int var5 = -1; var5 <= 1; var5++)
/*     */       {
/* 180 */         if (var2.getTypeId((int)EEBase.playerX(var3) + var4, (int)EEBase.playerY(var3) - 1, (int)EEBase.playerZ(var3) + var5) == Block.FIRE.id)
/*     */         {
/* 182 */           var2.setTypeId((int)EEBase.playerX(var3) + var4, (int)EEBase.playerY(var3) - 1, (int)EEBase.playerZ(var3) + var5, 0);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doActive(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 190 */     doBurn(var1, var2, var3);
/*     */   }
/*     */ 
/*     */   public void doHeld(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doRelease(ItemStack var1, World var2, EntityHuman var3) {
/* 197 */     doBreak(var1, var2, var3);
/*     */   }
/*     */ 
/*     */   public void doAlternate(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doLeftClick(ItemStack var1, World var2, EntityHuman var3) {
/* 204 */     var3.C_();
/* 205 */     var2.makeSound(var3, "wall", 1.0F, 1.0F);
/* 206 */     var2.addEntity(new EntityPyrokinesis(var2, var3));
/*     */   }
/*     */ 
/*     */   public boolean canActivate()
/*     */   {
/* 211 */     return true;
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemIgnitionRing
 * JD-Core Version:    0.6.2
 */