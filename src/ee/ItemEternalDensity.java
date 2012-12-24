/*     */ package ee;
/*     */ 
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.PlayerInventory;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class ItemEternalDensity extends ItemEECharged
/*     */ {
/*     */   public ItemEternalDensity(int var1)
/*     */   {
/*  15 */     super(var1, 0);
/*  16 */     this.maxStackSize = 1;
/*     */   }
/*     */ 
/*     */   public int getIconFromDamage(int var1)
/*     */   {
/*  21 */     return !isActivated(var1) ? this.textureId : this.textureId + 1;
/*     */   }
/*     */ 
/*     */   public boolean roomFor(ItemStack var1, EntityHuman var2)
/*     */   {
/*  26 */     if (var1 == null)
/*     */     {
/*  28 */       return false;
/*     */     }
/*     */ 
/*  32 */     for (int var3 = 0; var3 < var2.inventory.items.length; var3++)
/*     */     {
/*  34 */       if (var2.inventory.items[var3] == null)
/*     */       {
/*  36 */         return true;
/*     */       }
/*     */ 
/*  39 */       if ((var2.inventory.items[var3].doMaterialsMatch(var1)) && (var2.inventory.items[var3].count <= var1.getMaxStackSize() - var1.count))
/*     */       {
/*  41 */         return true;
/*     */       }
/*     */     }
/*     */ 
/*  45 */     return false;
/*     */   }
/*     */ 
/*     */   public void PushStack(ItemStack var1, EntityHuman var2)
/*     */   {
/*  51 */     if (var1 != null)
/*     */     {
/*  53 */       for (int var3 = 0; var3 < var2.inventory.items.length; var3++)
/*     */       {
/*  55 */         if (var2.inventory.items[var3] == null)
/*     */         {
/*  57 */           var2.inventory.items[var3] = var1.cloneItemStack();
/*  58 */           var1 = null;
/*  59 */           return;
/*     */         }
/*     */ 
/*  62 */         if ((var2.inventory.items[var3].doMaterialsMatch(var1)) && (var2.inventory.items[var3].count <= var1.getMaxStackSize() - var1.count))
/*     */         {
/*  64 */           var2.inventory.items[var3].count += var1.count;
/*  65 */           var1 = null;
/*  66 */           return;
/*     */         }
/*     */ 
/*  69 */         if (var2.inventory.items[var3].doMaterialsMatch(var1))
/*     */         {
/*  71 */           while ((var2.inventory.items[var3].count < var2.inventory.items[var3].getMaxStackSize()) && (var1 != null))
/*     */           {
/*  73 */             var2.inventory.items[var3].count += 1;
/*  74 */             var1.count -= 1;
/*     */ 
/*  76 */             if (var1.count <= 0)
/*     */             {
/*  78 */               var1 = null;
/*  79 */               return;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void dumpContents(ItemStack var1, EntityHuman var2)
/*     */   {
/*     */     while (true)
/*     */     {
/*  91 */       takeEMC(var1, EEMaps.getEMC(EEItem.redMatter.id));
/*  92 */       PushStack(new ItemStack(EEItem.redMatter, 1), var2);
/*     */ 
/*  89 */       if (emc(var1) >= EEMaps.getEMC(EEItem.redMatter.id)) if (!roomFor(new ItemStack(EEItem.redMatter, 1), var2))
/*     */         {
/*  95 */           break; }  
/*     */     }
/*     */     while (true) { takeEMC(var1, EEMaps.getEMC(EEItem.darkMatter.id));
/*  98 */       PushStack(new ItemStack(EEItem.darkMatter, 1), var2);
/*     */ 
/*  95 */       if (emc(var1) >= EEMaps.getEMC(EEItem.darkMatter.id)) if (!roomFor(new ItemStack(EEItem.darkMatter, 1), var2))
/*     */         {
/* 101 */           break;
/*     */         }  } while (true) {
/* 103 */       takeEMC(var1, EEMaps.getEMC(Item.DIAMOND.id));
/* 104 */       PushStack(new ItemStack(Item.DIAMOND, 1), var2);
/*     */ 
/* 101 */       if (emc(var1) >= EEMaps.getEMC(Item.DIAMOND.id)) if (!roomFor(new ItemStack(Item.DIAMOND, 1), var2))
/*     */         {
/* 107 */           break; }  
/*     */     }
/*     */     while (true) { takeEMC(var1, EEMaps.getEMC(Item.GOLD_INGOT.id));
/* 110 */       PushStack(new ItemStack(Item.GOLD_INGOT, 1), var2);
/*     */ 
/* 107 */       if (emc(var1) >= EEMaps.getEMC(Item.GOLD_INGOT.id)) if (!roomFor(new ItemStack(Item.GOLD_INGOT, 1), var2))
/*     */         {
/* 113 */           break;
/*     */         }  } do {
/* 115 */       takeEMC(var1, EEMaps.getEMC(Item.IRON_INGOT.id));
/* 116 */       PushStack(new ItemStack(Item.IRON_INGOT, 1), var2);
/*     */ 
/* 113 */       if (emc(var1) < EEMaps.getEMC(Item.IRON_INGOT.id)) break;  } while (roomFor(new ItemStack(Item.IRON_INGOT, 1), var2));
/*     */ 
/* 119 */     while ((emc(var1) >= EEMaps.getEMC(Block.COBBLESTONE.id)) && (roomFor(new ItemStack(Block.COBBLESTONE, 1), var2)))
/*     */     {
/* 121 */       takeEMC(var1, EEMaps.getEMC(Block.COBBLESTONE.id));
/* 122 */       PushStack(new ItemStack(Block.COBBLESTONE, 1), var2);
/*     */     }
/*     */   }
/*     */ 
/*     */   public ItemStack target(ItemStack var1)
/*     */   {
/* 128 */     return getInteger(var1, "targetID") != 0 ? new ItemStack(getInteger(var1, "targetID"), 1, 0) : getInteger(var1, "targetMeta") != 0 ? new ItemStack(getInteger(var1, "targetID"), 1, getInteger(var1, "targetMeta")) : null;
/*     */   }
/*     */ 
/*     */   public ItemStack product(ItemStack var1)
/*     */   {
/* 133 */     if (target(var1) != null)
/*     */     {
/* 135 */       int var2 = EEMaps.getEMC(target(var1));
/*     */ 
/* 137 */       if (var2 < EEMaps.getEMC(Item.IRON_INGOT.id))
/*     */       {
/* 139 */         return new ItemStack(Item.IRON_INGOT, 1);
/*     */       }
/*     */ 
/* 142 */       if (var2 < EEMaps.getEMC(Item.GOLD_INGOT.id))
/*     */       {
/* 144 */         return new ItemStack(Item.GOLD_INGOT, 1);
/*     */       }
/*     */ 
/* 147 */       if (var2 < EEMaps.getEMC(Item.DIAMOND.id))
/*     */       {
/* 149 */         return new ItemStack(Item.DIAMOND, 1);
/*     */       }
/*     */ 
/* 152 */       if (var2 < EEMaps.getEMC(EEItem.darkMatter.id))
/*     */       {
/* 154 */         return new ItemStack(EEItem.darkMatter, 1);
/*     */       }
/*     */ 
/* 157 */       if (var2 < EEMaps.getEMC(EEItem.redMatter.id))
/*     */       {
/* 159 */         return new ItemStack(EEItem.redMatter, 1);
/*     */       }
/*     */     }
/*     */ 
/* 163 */     return null;
/*     */   }
/*     */ 
/*     */   public void doCondense(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 168 */     if (!EEProxy.isClient(var2))
/*     */     {
/* 170 */       if ((product(var1) != null) && (emc(var1) >= EEMaps.getEMC(product(var1))) && (roomFor(product(var1), var3)))
/*     */       {
/* 172 */         PushStack(product(var1), var3);
/* 173 */         takeEMC(var1, EEMaps.getEMC(product(var1)));
/*     */       }
/*     */ 
/* 176 */       int var4 = 0;
/* 177 */       ItemStack[] var5 = var3.inventory.items;
/* 178 */       int var6 = var5.length;
/*     */ 
/* 182 */       for (int var7 = 0; var7 < var6; var7++)
/*     */       {
/* 184 */         ItemStack var8 = var5[var7];
/*     */ 
/* 186 */         if ((var8 != null) && (EEMaps.getEMC(var8) != 0) && (isValidMaterial(var8, var3)) && (EEMaps.getEMC(var8) > var4))
/*     */         {
/* 188 */           var4 = EEMaps.getEMC(var8);
/*     */         }
/*     */       }
/*     */ 
/* 192 */       var5 = var3.inventory.items;
/* 193 */       var6 = var5.length;
/*     */ 
/* 195 */       for (var7 = 0; var7 < var6; var7++)
/*     */       {
/* 197 */         ItemStack var8 = var5[var7];
/*     */ 
/* 199 */         if ((var8 != null) && (EEMaps.getEMC(var8) != 0) && (isValidMaterial(var8, var3)) && (EEMaps.getEMC(var8) <= var4))
/*     */         {
/* 201 */           var4 = EEMaps.getEMC(var8);
/* 202 */           setInteger(var1, "targetID", var8.id);
/* 203 */           setInteger(var1, "targetMeta", var8.getData());
/*     */         }
/*     */       }
/*     */ 
/* 207 */       if (target(var1) != null)
/*     */       {
/* 209 */         if (ConsumeMaterial(target(var1), var3))
/*     */         {
/* 211 */           addEMC(var1, EEMaps.getEMC(target(var1)));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean isLastCobbleStack(EntityHuman var1)
/*     */   {
/* 219 */     int var2 = 0;
/*     */ 
/* 221 */     for (int var3 = 0; var3 < var1.inventory.items.length; var3++)
/*     */     {
/* 223 */       if ((var1.inventory.items[var3] != null) && (var1.inventory.items[var3].id == Block.COBBLESTONE.id))
/*     */       {
/* 225 */         var2 += var1.inventory.items[var3].count;
/*     */       }
/*     */     }
/*     */ 
/* 229 */     if (var2 <= 64)
/*     */     {
/* 231 */       return true;
/*     */     }
/*     */ 
/* 235 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean isValidMaterial(ItemStack var1, EntityHuman var2)
/*     */   {
/* 241 */     if (EEMaps.getEMC(var1) == 0)
/*     */     {
/* 243 */       return false;
/*     */     }
/* 245 */     if ((var1.id == Block.COBBLESTONE.id) && (isLastCobbleStack(var2)))
/*     */     {
/* 247 */       return false;
/*     */     }
/*     */ 
/* 251 */     int var3 = var1.id;
/*     */ 
/* 253 */     if (var3 >= Block.byId.length)
/*     */     {
/* 255 */       if ((var3 != Item.IRON_INGOT.id) && (var3 != Item.GOLD_INGOT.id) && (var3 != Item.DIAMOND.id) && (var3 != EEItem.darkMatter.id))
/*     */       {
/* 257 */         return false;
/*     */       }
/*     */ 
/* 260 */       if (var3 == EEItem.redMatter.id)
/*     */       {
/* 262 */         return false;
/*     */       }
/*     */     }
/*     */ 
/* 266 */     return !EEMaps.isFuel(var1);
/*     */   }
/*     */ 
/*     */   private int emc(ItemStack var1)
/*     */   {
/* 272 */     return getInteger(var1, "emc");
/*     */   }
/*     */ 
/*     */   private void takeEMC(ItemStack var1, int var2)
/*     */   {
/* 277 */     setInteger(var1, "emc", emc(var1) - var2);
/*     */   }
/*     */ 
/*     */   private void addEMC(ItemStack var1, int var2)
/*     */   {
/* 282 */     setInteger(var1, "emc", emc(var1) + var2);
/*     */   }
/*     */ 
/*     */   public boolean ConsumeMaterial(ItemStack var1, EntityHuman var2)
/*     */   {
/* 287 */     return EEBase.Consume(var1, var2, false);
/*     */   }
/*     */ 
/*     */   public void ConsumeReagent(ItemStack var1, EntityHuman var2, boolean var3)
/*     */   {
/* 292 */     EEBase.updatePlayerEffect(var1.getItem(), 200, var2);
/*     */   }
/*     */ 
/*     */   public void doPassive(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 297 */     if (!isActivated(var1.getData()))
/*     */     {
/* 299 */       dumpContents(var1, var3);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doActive(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 305 */     doCondense(var1, var2, var3);
/*     */   }
/*     */ 
/*     */   public boolean canActivate()
/*     */   {
/* 310 */     return true;
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
 * Qualified Name:     ee.ItemEternalDensity
 * JD-Core Version:    0.6.2
 */