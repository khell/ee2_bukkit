/*     */ package ee;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.FurnaceRecipes;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class ItemDiviningRod extends ItemEECharged
/*     */ {
/*     */   protected ItemDiviningRod(int var1)
/*     */   {
/*  16 */     super(var1, 0);
/*  17 */     a(true);
/*     */   }
/*     */ 
/*     */   public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/*  26 */     if (EEProxy.isClient(var3))
/*     */     {
/*  28 */       return false;
/*     */     }
/*  30 */     if (getShort(var1, "cooldown") <= 0)
/*     */     {
/*  32 */       if (getFuelRemaining(var1) < (getMode(var1) == 1 ? 4 : getMode(var1) == 0 ? 2 : 8))
/*     */       {
/*  34 */         ConsumeReagent(var1, var2, true);
/*     */ 
/*  36 */         if (getFuelRemaining(var1) < (getMode(var1) == 1 ? 4 : getMode(var1) == 0 ? 2 : 8))
/*     */         {
/*  38 */           ConsumeReagent(var1, var2, true);
/*     */         }
/*     */       }
/*     */ 
/*  42 */       int var8 = getMode(var1) == 1 ? 16 : getMode(var1) == 0 ? 3 : 64;
/*  43 */       doDivining(var1, var2, var8, var4, var5, var6, var7);
/*  44 */       setShort(var1, "cooldown", 60 / (var1.getData() + 1));
/*  45 */       return true;
/*     */     }
/*     */ 
/*  49 */     return false;
/*     */   }
/*     */ 
/*     */   public void a(ItemStack var1, World var2, Entity var3, int var4, boolean var5)
/*     */   {
/*  59 */     if (getShort(var1, "cooldown") > 0)
/*     */     {
/*  61 */       setShort(var1, "cooldown", getShort(var1, "cooldown") - 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addCreativeItems(ArrayList var1)
/*     */   {
/*  67 */     var1.add(new ItemStack(EEItem.diviningRod, 1, 0));
/*  68 */     var1.add(new ItemStack(EEItem.diviningRod, 1, 1));
/*  69 */     var1.add(new ItemStack(EEItem.diviningRod, 1, 2));
/*     */   }
/*     */ 
/*     */   public void doDivining(ItemStack var1, EntityHuman var2, int var3, int var4, int var5, int var6, int var7)
/*     */   {
/*  74 */     setFuelRemaining(var1, getFuelRemaining(var1) - (getMode(var1) == 1 ? 4 : getMode(var1) == 0 ? 2 : 8));
/*  75 */     float var8 = 0.0F;
/*  76 */     int var9 = 0;
/*  77 */     boolean var10 = false;
/*  78 */     boolean var11 = false;
/*  79 */     int var12 = 0;
/*  80 */     int var13 = 0;
/*  81 */     int var14 = 0;
/*  82 */     int var15 = 0;
/*  83 */     int var16 = 0;
/*  84 */     World var17 = var2.world;
/*     */ 
/*  86 */     for (int var18 = -1 * (var7 == 5 ? var3 : 1); var18 <= 1 * (var7 == 4 ? var3 : 1); var18++)
/*     */     {
/*  88 */       for (int var19 = -1 * (var7 == 1 ? var3 : 1); var19 <= 1 * (var7 == 0 ? var3 : 1); var19++)
/*     */       {
/*  90 */         for (int var20 = -1 * (var7 == 3 ? var3 : 1); var20 <= 1 * (var7 == 2 ? var3 : 1); var20++)
/*     */         {
/*  92 */           int var23 = var17.getTypeId(var4 + var18, var5 + var19, var6 + var20);
/*  93 */           int var22 = var17.getData(var4 + var18, var5 + var19, var6 + var20);
/*     */ 
/*  95 */           if (EEMaps.getEMC(var23, var22) > 0)
/*     */           {
/*  97 */             if (EEMaps.getEMC(var23, var22) > var12)
/*     */             {
/*  99 */               var13 = var12;
/* 100 */               var12 = EEMaps.getEMC(var23, var22);
/*     */             }
/*     */ 
/* 103 */             if (var13 > var14)
/*     */             {
/* 105 */               var15 = var14;
/* 106 */               var14 = var13;
/*     */             }
/*     */ 
/* 109 */             if (var15 > var16)
/*     */             {
/* 111 */               var16 = var15;
/*     */             }
/*     */ 
/* 114 */             var8 += EEMaps.getEMC(var23, var22);
/* 115 */             var9++;
/*     */           }
/* 117 */           else if ((Block.byId[var23] != null) && (EEMaps.getEMC(Block.byId[var23].getDropType(var23, var17.random, 0), var22) * Block.byId[var23].quantityDropped(var22, 0, var17.random) > 0))
/*     */           {
/* 119 */             int var26 = EEMaps.getEMC(Block.byId[var23].getDropType(var23, var17.random, 0), var22) * Block.byId[var23].quantityDropped(var22, 0, var17.random);
/*     */ 
/* 121 */             if (var26 > var12)
/*     */             {
/* 123 */               var13 = var12;
/* 124 */               var12 = var26;
/*     */             }
/*     */ 
/* 127 */             if (var13 > var14)
/*     */             {
/* 129 */               var15 = var14;
/* 130 */               var14 = var13;
/*     */             }
/*     */ 
/* 133 */             if (var15 > var16)
/*     */             {
/* 135 */               var16 = var15;
/*     */             }
/*     */ 
/* 138 */             var8 += var26;
/* 139 */             var9++;
/*     */           }
/* 141 */           else if (FurnaceRecipes.getInstance().getSmeltingResult(new ItemStack(var23, 1, var22)) != null)
/*     */           {
/* 143 */             ItemStack var21 = FurnaceRecipes.getInstance().getSmeltingResult(new ItemStack(var23, 1, var22));
/*     */ 
/* 145 */             if (EEMaps.getEMC(var21.id, var21.getData()) != 0)
/*     */             {
/* 147 */               if (EEMaps.getEMC(var21.id, var21.getData()) > var12)
/*     */               {
/* 149 */                 var13 = var12;
/* 150 */                 var12 = EEMaps.getEMC(var21.id, var21.getData());
/*     */               }
/*     */ 
/* 153 */               if (var13 > var14)
/*     */               {
/* 155 */                 var15 = var14;
/* 156 */                 var14 = var13;
/*     */               }
/*     */ 
/* 159 */               if (var15 > var16)
/*     */               {
/* 161 */                 var16 = var15;
/*     */               }
/*     */ 
/* 164 */               var8 += EEMaps.getEMC(var21.id, var21.getData());
/* 165 */               var9++;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 172 */     String var24 = "Divining suggests a value around... " + Math.floor(var8 / var9);
/* 173 */     String var25 = "";
/*     */ 
/* 175 */     if (var1.getData() > 0)
/*     */     {
/* 177 */       var25 = " Best found: " + var12 + (var1.getData() == 2 ? " Second: " + var14 + " Third: " + var16 : "");
/*     */     }
/*     */ 
/* 180 */     var2.a(var24);
/*     */ 
/* 182 */     if (var25 != "")
/*     */     {
/* 184 */       var2.a(var25);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doToggle(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 190 */     if (var1.getData() > 0)
/*     */     {
/* 192 */       changeModes(var1);
/*     */     }
/*     */ 
/* 195 */     var3.a(getMode(var1) == 1 ? "Divining rod mid range (16x3x3)" : getMode(var1) == 0 ? "Divining rod short range (3x3x3)" : "Divining rod long range (64x3x3)");
/*     */   }
/*     */ 
/*     */   public void changeModes(ItemStack var1)
/*     */   {
/* 200 */     if (var1.getData() > 1)
/*     */     {
/* 202 */       if (getMode(var1) == 2)
/*     */       {
/* 204 */         setMode(var1, 0);
/*     */       }
/*     */       else
/*     */       {
/* 208 */         setMode(var1, getMode(var1) + 1);
/*     */       }
/*     */     }
/* 211 */     else if (getMode(var1) == 1)
/*     */     {
/* 213 */       setMode(var1, 0);
/*     */     }
/*     */     else
/*     */     {
/* 217 */       setMode(var1, getMode(var1) + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setMode(ItemStack var1, int var2)
/*     */   {
/* 223 */     setShort(var1, "mode", (short)var2);
/*     */   }
/*     */ 
/*     */   public int getMode(ItemStack var1)
/*     */   {
/* 228 */     return getShort(var1, "mode");
/*     */   }
/*     */ 
/*     */   public void doChargeTick(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void doUncharge(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemDiviningRod
 * JD-Core Version:    0.6.2
 */