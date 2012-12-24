/*     */ package ee;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.Material;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class ItemRedAxe extends ItemRedTool
/*     */ {
/*     */   public boolean itemCharging;
/*  15 */   private static Block[] blocksEffectiveAgainst = { Block.WOOD, Block.BOOKSHELF, Block.LOG, Block.CHEST };
/*     */ 
/*     */   protected ItemRedAxe(int var1)
/*     */   {
/*  19 */     super(var1, 3, 12, blocksEffectiveAgainst);
/*     */   }
/*     */ 
/*     */   public float getDestroySpeed(ItemStack var1, Block var2)
/*     */   {
/*  28 */     return var2.material == Material.WOOD ? 18.0F + chargeLevel(var1) * 2 : super.getDestroySpeed(var1, var2);
/*     */   }
/*     */ 
/*     */   public void doBreak(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*  33 */     if (chargeLevel(var1) > 0)
/*     */     {
/*  35 */       double var4 = EEBase.playerX(var3);
/*  36 */       double var6 = EEBase.playerY(var3);
/*  37 */       double var8 = EEBase.playerZ(var3);
/*  38 */       boolean var10 = false;
/*  39 */       cleanDroplist(var1);
/*     */ 
/*  41 */       if (chargeLevel(var1) < 1)
/*     */       {
/*  43 */         return;
/*     */       }
/*     */ 
/*  46 */       var3.C_();
/*  47 */       var2.makeSound(var3, "flash", 0.8F, 1.5F);
/*     */ 
/*  49 */       for (int var11 = -(chargeLevel(var1) * 2) + 1; var11 <= chargeLevel(var1) * 2 - 1; var11++)
/*     */       {
/*  51 */         for (int var12 = chargeLevel(var1) * 2 + 1; var12 >= -2; var12--)
/*     */         {
/*  53 */           for (int var13 = -(chargeLevel(var1) * 2) + 1; var13 <= chargeLevel(var1) * 2 - 1; var13++)
/*     */           {
/*  55 */             int var14 = (int)(var4 + var11);
/*  56 */             int var15 = (int)(var6 + var12);
/*  57 */             int var16 = (int)(var8 + var13);
/*  58 */             int var17 = var2.getTypeId(var14, var15, var16);
/*     */ 
/*  60 */             if ((EEMaps.isWood(var17)) || (EEMaps.isLeaf(var17)))
/*     */             {
/*  62 */               if (getFuelRemaining(var1) < 1)
/*     */               {
/*  64 */                 if ((var11 == chargeLevel(var1)) && (var13 == chargeLevel(var1)))
/*     */                 {
/*  66 */                   ConsumeReagent(var1, var3, var10);
/*  67 */                   var10 = false;
/*     */                 }
/*     */                 else
/*     */                 {
/*  71 */                   ConsumeReagent(var1, var3, false);
/*     */                 }
/*     */               }
/*     */ 
/*  75 */               if (getFuelRemaining(var1) > 0)
/*     */               {
/*  77 */                 int var18 = var2.getData(var14, var15, var16);
/*  78 */                 ArrayList var19 = Block.byId[var17].getBlockDropped(var2, var14, var15, var16, var18, 0);
/*  79 */                 Iterator var20 = var19.iterator();
/*     */ 
/*  81 */                 while (var20.hasNext())
/*     */                 {
/*  83 */                   ItemStack var21 = (ItemStack)var20.next();
/*  84 */                   addToDroplist(var1, var21);
/*     */                 }
/*     */ 
/*  87 */                 var2.setTypeId(var14, var15, var16, 0);
/*     */ 
/*  89 */                 if (!EEMaps.isLeaf(var17))
/*     */                 {
/*  91 */                   setShort(var1, "fuelRemaining", getFuelRemaining(var1) - 1);
/*     */                 }
/*     */ 
/*  94 */                 if (var2.random.nextInt(8) == 0)
/*     */                 {
/*  96 */                   var2.a("largesmoke", var14, var15, var16, 0.0D, 0.0D, 0.0D);
/*     */                 }
/*     */ 
/*  99 */                 if (var2.random.nextInt(8) == 0)
/*     */                 {
/* 101 */                   var2.a("explode", var14, var15, var16, 0.0D, 0.0D, 0.0D);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 109 */       ejectDropList(var2, var1, var4, var6, var8);
/*     */     }
/*     */   }
/*     */ 
/*     */   public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 118 */     if (EEProxy.isClient(var2))
/*     */     {
/* 120 */       return var1;
/*     */     }
/*     */ 
/* 124 */     doBreak(var1, var2, var3);
/* 125 */     return var1;
/*     */   }
/*     */ 
/*     */   public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 135 */     if (EEProxy.isClient(var3))
/*     */     {
/* 137 */       return false;
/*     */     }
/*     */ 
/* 141 */     if (chargeLevel(var1) > 0)
/*     */     {
/* 143 */       double var8 = var4;
/* 144 */       double var10 = var5;
/* 145 */       double var12 = var6;
/* 146 */       boolean var14 = false;
/* 147 */       cleanDroplist(var1);
/*     */ 
/* 149 */       if (chargeLevel(var1) < 1)
/*     */       {
/* 151 */         return false;
/*     */       }
/*     */ 
/* 154 */       var2.C_();
/* 155 */       var3.makeSound(var2, "flash", 0.8F, 1.5F);
/*     */ 
/* 157 */       for (int var15 = -(chargeLevel(var1) * 2) + 1; var15 <= chargeLevel(var1) * 2 - 1; var15++)
/*     */       {
/* 159 */         for (int var16 = chargeLevel(var1) * 2 + 1; var16 >= -2; var16--)
/*     */         {
/* 161 */           for (int var17 = -(chargeLevel(var1) * 2) + 1; var17 <= chargeLevel(var1) * 2 - 1; var17++)
/*     */           {
/* 163 */             int var18 = (int)(var8 + var15);
/* 164 */             int var19 = (int)(var10 + var16);
/* 165 */             int var20 = (int)(var12 + var17);
/* 166 */             int var21 = var3.getTypeId(var18, var19, var20);
/*     */ 
/* 168 */             if ((EEMaps.isWood(var21)) || (EEMaps.isLeaf(var21)))
/*     */             {
/* 170 */               if (getFuelRemaining(var1) < 1)
/*     */               {
/* 172 */                 if ((var15 == chargeLevel(var1)) && (var17 == chargeLevel(var1)))
/*     */                 {
/* 174 */                   ConsumeReagent(var1, var2, var14);
/* 175 */                   var14 = false;
/*     */                 }
/*     */                 else
/*     */                 {
/* 179 */                   ConsumeReagent(var1, var2, false);
/*     */                 }
/*     */               }
/*     */ 
/* 183 */               if (getFuelRemaining(var1) > 0)
/*     */               {
/* 185 */                 int var22 = var3.getData(var18, var19, var20);
/* 186 */                 ArrayList var23 = Block.byId[var21].getBlockDropped(var3, var18, var19, var20, var22, 0);
/* 187 */                 Iterator var24 = var23.iterator();
/*     */ 
/* 189 */                 while (var24.hasNext())
/*     */                 {
/* 191 */                   ItemStack var25 = (ItemStack)var24.next();
/* 192 */                   addToDroplist(var1, var25);
/*     */                 }
/*     */ 
/* 195 */                 var3.setTypeId(var18, var19, var20, 0);
/*     */ 
/* 197 */                 if (!EEMaps.isLeaf(var21))
/*     */                 {
/* 199 */                   setShort(var1, "fuelRemaining", getFuelRemaining(var1) - 1);
/*     */                 }
/*     */ 
/* 202 */                 if (var3.random.nextInt(8) == 0)
/*     */                 {
/* 204 */                   var3.a("largesmoke", var18, var19, var20, 0.0D, 0.0D, 0.0D);
/*     */                 }
/*     */ 
/* 207 */                 if (var3.random.nextInt(8) == 0)
/*     */                 {
/* 209 */                   var3.a("explode", var18, var19, var20, 0.0D, 0.0D, 0.0D);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 217 */       ejectDropList(var3, var1, var8, var10, var12);
/*     */     }
/*     */ 
/* 220 */     return false;
/*     */   }
/*     */ 
/*     */   public void doToggle(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemRedAxe
 * JD-Core Version:    0.6.2
 */