/*     */ package ee;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityItem;
/*     */ import net.minecraft.server.EntityLiving;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.NBTTagCompound;
/*     */ import net.minecraft.server.NBTTagList;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public abstract class ItemEECharged extends ItemModEE
/*     */ {
/*     */   protected int weaponDamage;
/*     */   private int maxCharge;
/*     */ 
/*     */   public void ConsumeReagent(ItemStack var1, EntityHuman var2, boolean var3)
/*     */   {
/*  22 */     EEBase.ConsumeReagent(var1, var2, var3);
/*     */   }
/*     */ 
/*     */   public void setFuelRemaining(ItemStack var1, int var2)
/*     */   {
/*  27 */     setShort(var1, "fuelRemaining", var2);
/*     */   }
/*     */ 
/*     */   public int getFuelRemaining(ItemStack var1)
/*     */   {
/*  32 */     return getShort(var1, "fuelRemaining");
/*     */   }
/*     */   public void doPassive(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */   public void doActive(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */   public void doHeld(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */   public void doRelease(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */   public void doAlternate(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doLeftClick(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   protected ItemEECharged(int var1, int var2) {
/*  49 */     super(var1);
/*  50 */     this.maxStackSize = 1;
/*  51 */     this.maxCharge = var2;
/*     */ 
/*  53 */     if (var2 == 0)
/*     */     {
/*  55 */       setMaxDurability(0);
/*     */     }
/*     */     else
/*     */     {
/*  59 */       setMaxDurability(10 * var2 + 1 + (canActivate() ? 1 : canActivate2() ? 2 : 0) << 1 * (canActivate() ? 1 : canActivate2() ? 2 : 0));
/*     */     }
/*     */ 
/*  62 */     this.weaponDamage = 1;
/*     */   }
/*     */ 
/*     */   public boolean isItemOut(ItemStack var1, EntityHuman var2)
/*     */   {
/*  67 */     return var1 == null ? false : EEBase.isCurrentItem(var1.getItem(), var2);
/*     */   }
/*     */ 
/*     */   public boolean isItemEquipped(ItemStack var1, EntityHuman var2)
/*     */   {
/*  72 */     return var1 == null ? false : EEBase.isOnQuickBar(var1.getItem(), var2);
/*     */   }
/*     */ 
/*     */   public int getMaxCharge()
/*     */   {
/*  77 */     return this.maxCharge;
/*     */   }
/*     */ 
/*     */   public void doCharge(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*  82 */     if (getShort(var1, "chargeGoal") < this.maxCharge)
/*     */     {
/*  84 */       setShort(var1, "chargeGoal", getShort(var1, "chargeGoal") + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void ejectDropList(World var1, ItemStack var2, double var3, double var5, double var7)
/*     */   {
/*  90 */     ItemStack[] var9 = getDroplist(var2);
/*     */ 
/*  92 */     if (var9 != null)
/*     */     {
/*  94 */       int var10 = 0;
/*  95 */       ItemStack[] var11 = var9;
/*  96 */       int var12 = var9.length;
/*     */ 
/*  99 */       for (int var13 = 0; var13 < var12; var13++)
/*     */       {
/* 101 */         ItemStack var14 = var11[var13];
/*     */ 
/* 103 */         if (var14 != null)
/*     */         {
/* 105 */           var10 += var14.count;
/*     */         }
/*     */       }
/*     */ 
/* 109 */       if (var10 != 0)
/*     */       {
/* 111 */         ItemStack var16 = new ItemStack(EEItem.lootBall);
/* 112 */         var16.setTag(new NBTTagCompound());
/* 113 */         cleanDroplist(var16);
/* 114 */         ItemStack[] var17 = var9;
/* 115 */         int var13 = var9.length;
/*     */ 
/* 117 */         for (int var18 = 0; var18 < var13; var18++)
/*     */         {
/* 119 */           ItemStack var15 = var17[var18];
/* 120 */           addToDroplist(var16, var15);
/*     */         }
/*     */ 
/* 123 */         dropItemInWorld(var1, var16, var3, var5, var7);
/* 124 */         cleanDroplist(var2);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static EntityItem dropItemInWorld(World var0, ItemStack var1, double var2, double var4, double var6)
/*     */   {
/* 131 */     if (var1 == null)
/*     */     {
/* 133 */       return null;
/*     */     }
/*     */ 
/* 137 */     EntityItem var8 = new EntityItem(var0, var2, var4 + 0.5D, var6, var1);
/* 138 */     var8.pickupDelay = 40;
/* 139 */     float var9 = 0.1F;
/* 140 */     Random var11 = new Random();
/* 141 */     var9 = 0.3F;
/* 142 */     var8.motX = ((float)(Math.random() * 0.2000000029802322D - 0.1000000014901161D));
/* 143 */     var8.motY = 0.2000000029802322D;
/* 144 */     var8.motZ = ((float)(Math.random() * 0.2000000029802322D - 0.1000000014901161D));
/* 145 */     var9 = 0.02F;
/* 146 */     float var10 = var11.nextFloat() * 3.141593F * 2.0F;
/* 147 */     var9 *= var11.nextFloat();
/* 148 */     var8.motX += Math.cos(var10) * var9;
/* 149 */     var8.motY += (var11.nextFloat() - var11.nextFloat()) * 0.1F;
/* 150 */     var8.motZ += Math.sin(var10) * var9;
/* 151 */     var0.addEntity(var8);
/* 152 */     return var8;
/*     */   }
/*     */ 
/*     */   public ItemStack[] getDroplist(ItemStack var1)
/*     */   {
/* 158 */     if (var1.tag == null)
/*     */     {
/* 160 */       var1.setTag(new NBTTagCompound());
/*     */     }
/*     */ 
/* 163 */     if (!var1.tag.hasKey("droplist"))
/*     */     {
/* 165 */       cleanDroplist(var1);
/*     */     }
/*     */ 
/* 168 */     NBTTagList var2 = var1.tag.getList("droplist");
/* 169 */     ItemStack[] var3 = new ItemStack[var2.size()];
/*     */ 
/* 171 */     for (int var4 = 0; var4 < var2.size(); var4++)
/*     */     {
/* 173 */       NBTTagCompound var5 = (NBTTagCompound)var2.get(var4);
/* 174 */       var3[var4] = ItemStack.a(var5);
/*     */     }
/*     */ 
/* 177 */     return var3;
/*     */   }
/*     */ 
/*     */   public void addToDroplist(ItemStack var1, ItemStack var2)
/*     */   {
/* 182 */     if (!var1.tag.hasKey("droplist"))
/*     */     {
/* 184 */       System.out.println("Forced to wipe droplist");
/* 185 */       cleanDroplist(var1);
/*     */     }
/*     */ 
/* 188 */     NBTTagList var3 = var1.tag.getList("droplist");
/* 189 */     ArrayList var4 = new ArrayList();
/* 190 */     boolean var5 = false;
/*     */ 
/* 192 */     for (int var6 = 0; var6 < var3.size(); var6++)
/*     */     {
/* 194 */       NBTTagCompound var7 = (NBTTagCompound)var3.get(var6);
/* 195 */       var4.add(ItemStack.a(var7));
/*     */     }
/*     */ 
/* 198 */     ArrayList var10 = sortArrayList(var4);
/*     */ 
/* 200 */     for (int var11 = 0; (var11 < var10.size()) && (var2 != null); var11++)
/*     */     {
/* 202 */       if (var10.get(var11) == null)
/*     */       {
/* 204 */         var10.set(var11, var2.cloneItemStack());
/* 205 */         var2 = null;
/* 206 */         var5 = true;
/*     */       }
/* 208 */       else if (((ItemStack)var10.get(var11)).doMaterialsMatch(var2))
/*     */       {
/* 210 */         if (((ItemStack)var10.get(var11)).count < ((ItemStack)var10.get(var11)).getMaxStackSize())
/*     */         {
/*     */           do
/*     */           {
/* 214 */             var10.set(var11, new ItemStack(((ItemStack)var10.get(var11)).id, ((ItemStack)var10.get(var11)).count + 1, ((ItemStack)var10.get(var11)).getData()));
/* 215 */             var2.count -= 1;
/*     */ 
/* 217 */             if (var2.count == 0)
/*     */             {
/* 219 */               var2 = null;
/* 220 */               var5 = true;
/*     */             }
/* 212 */             if (((ItemStack)var10.get(var11)).count >= ((ItemStack)var10.get(var11)).getMaxStackSize()) break;  } while (var2 != null);
/*     */         }
/*     */         else
/*     */         {
/* 226 */           var10.add(var2);
/* 227 */           var2 = null;
/* 228 */           var5 = true;
/*     */         }
/*     */       }
/* 231 */       else if (!var5)
/*     */       {
/* 233 */         var10.add(var2);
/* 234 */         var2 = null;
/* 235 */         var5 = true;
/*     */       }
/*     */     }
/*     */ 
/* 239 */     NBTTagList var12 = new NBTTagList();
/*     */ 
/* 241 */     for (int var8 = 0; var8 < var10.size(); var8++)
/*     */     {
/* 243 */       if (var10.get(var8) != null)
/*     */       {
/* 245 */         NBTTagCompound var9 = new NBTTagCompound();
/* 246 */         ((ItemStack)var10.get(var8)).save(var9);
/* 247 */         var12.add(var9);
/*     */       }
/*     */     }
/*     */ 
/* 251 */     var1.tag.set("droplist", var12);
/*     */   }
/*     */ 
/*     */   private ArrayList sortArrayList(ArrayList var1)
/*     */   {
/* 256 */     for (int var2 = 0; var2 < var1.size(); var2++)
/*     */     {
/* 258 */       if (var1.get(var2) != null)
/*     */       {
/* 260 */         for (int var3 = 0; var3 < var1.size(); var3++)
/*     */         {
/* 262 */           if ((var2 != var3) && (var1.get(var3) != null) && (((ItemStack)var1.get(var2)).doMaterialsMatch((ItemStack)var1.get(var3))) && (((ItemStack)var1.get(var2)).count < ((ItemStack)var1.get(var2)).getMaxStackSize()))
/*     */           {
/* 264 */             while ((((ItemStack)var1.get(var2)).count < ((ItemStack)var1.get(var2)).getMaxStackSize()) && (var1.get(var3) != null))
/*     */             {
/* 266 */               var1.set(var2, new ItemStack(((ItemStack)var1.get(var2)).id, ((ItemStack)var1.get(var2)).count + 1, ((ItemStack)var1.get(var2)).getData()));
/* 267 */               var1.set(var3, new ItemStack(((ItemStack)var1.get(var3)).id, ((ItemStack)var1.get(var3)).count - 1, ((ItemStack)var1.get(var3)).getData()));
/*     */ 
/* 269 */               if (((ItemStack)var1.get(var3)).count == 0)
/*     */               {
/* 271 */                 var1.set(var3, null);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 279 */     return var1;
/*     */   }
/*     */ 
/*     */   public void cleanDroplist(ItemStack var1)
/*     */   {
/* 284 */     NBTTagList var2 = new NBTTagList();
/* 285 */     NBTTagCompound var3 = new NBTTagCompound();
/* 286 */     var2.add(var3);
/* 287 */     var1.tag.set("droplist", var2);
/*     */   }
/*     */ 
/*     */   public void doChargeTick(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 292 */     if (chargeLevel(var1) < chargeGoal(var1))
/*     */     {
/* 294 */       if (chargeTicks(var1) < 10)
/*     */       {
/* 296 */         setShort(var1, "chargeTicks", chargeTicks(var1) + 1);
/* 297 */         var2.makeSound(var3, "chargetick", 1.0F, 0.5F + (var1.i() - var1.getData()) / var1.i());
/*     */       }
/*     */       else
/*     */       {
/* 301 */         setShort(var1, "chargeTicks", 0);
/* 302 */         setShort(var1, "chargeLevel", chargeLevel(var1) + 1);
/* 303 */         var2.makeSound(var3, "flash", 0.5F, 0.5F + chargeLevel(var1) / (var1.i() / 10));
/*     */       }
/*     */     }
/*     */ 
/* 307 */     var1.setData(var1.i() - (chargeLevel(var1) * 10 + chargeTicks(var1) << (canActivate() ? 1 : canActivate2() ? 2 : 0)));
/*     */ 
/* 309 */     if (canActivate())
/*     */     {
/* 311 */       if (isActivated(var1))
/*     */       {
/* 313 */         if ((var1.getData() & 0x1) == 0)
/*     */         {
/* 315 */           var1.setData(var1.getData() + 1);
/*     */         }
/*     */       }
/* 318 */       else if ((var1.getData() & 0x1) == 1)
/*     */       {
/* 320 */         var1.setData(var1.getData() - 1);
/*     */       }
/*     */     }
/*     */ 
/* 324 */     if (canActivate2())
/*     */     {
/* 326 */       if (isActivated2(var1))
/*     */       {
/* 328 */         if ((var1.getData() & 0x2) == 0)
/*     */         {
/* 330 */           var1.setData(var1.getData() + 2);
/*     */         }
/*     */       }
/* 333 */       else if ((var1.getData() & 0x2) == 2)
/*     */       {
/* 335 */         var1.setData(var1.getData() - 2);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doUncharge(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 342 */     if (chargeLevel(var1) > 0)
/*     */     {
/* 344 */       setShort(var1, "chargeLevel", chargeLevel(var1) - 1);
/*     */     }
/*     */ 
/* 347 */     if (chargeGoal(var1) > chargeLevel(var1))
/*     */     {
/* 349 */       setShort(var1, "chargeGoal", chargeLevel(var1));
/*     */     }
/*     */ 
/* 352 */     if (chargeTicks(var1) > 0)
/*     */     {
/* 354 */       setShort(var1, "chargeTicks", 0);
/*     */     }
/*     */ 
/* 357 */     var1.setData(var1.i() - (chargeLevel(var1) * 10 + chargeTicks(var1) << (canActivate() ? 1 : canActivate2() ? 2 : 0) + (isActivated(var1) ? 1 : 0) + (isActivated2(var1) ? 2 : 0)));
/* 358 */     var2.makeSound(var3, "break", 0.5F, 0.5F + chargeLevel(var1) / (var1.i() / 10));
/*     */   }
/*     */ 
/*     */   public int chargeLevel(ItemStack var1)
/*     */   {
/* 363 */     return getShort(var1, "chargeLevel");
/*     */   }
/*     */ 
/*     */   public int chargeTicks(ItemStack var1)
/*     */   {
/* 368 */     return getShort(var1, "chargeTicks");
/*     */   }
/*     */ 
/*     */   public int chargeGoal(ItemStack var1)
/*     */   {
/* 373 */     return getShort(var1, "chargeGoal");
/*     */   }
/*     */ 
/*     */   public void resetCharge(ItemStack var1, World var2, EntityHuman var3, boolean var4)
/*     */   {
/* 378 */     if (var1.d())
/*     */     {
/* 380 */       if (var4)
/*     */       {
/* 382 */         var2.makeSound(var3, "break", 0.5F, 1.0F);
/*     */       }
/*     */ 
/* 385 */       var1.setData(0);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void a(ItemStack var1, World var2, Entity var3, int var4, boolean var5)
/*     */   {
/* 395 */     if (!EEProxy.isClient(var2))
/*     */     {
/* 397 */       if (((var3 instanceof EntityHuman)) && (var1 != null))
/*     */       {
/* 399 */         EntityHuman var6 = (EntityHuman)var3;
/*     */ 
/* 401 */         if (this.maxCharge != 0)
/*     */         {
/* 403 */           doChargeTick(var1, var2, var6);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean onItemUseFirst(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/* 411 */     return false;
/*     */   }
/*     */ 
/*     */   public int a(Entity var1)
/*     */   {
/* 419 */     return this.weaponDamage;
/*     */   }
/*     */ 
/*     */   public boolean a(ItemStack var1, EntityLiving var2, EntityLiving var3)
/*     */   {
/* 428 */     return super.a(var1, var2, var3);
/*     */   }
/*     */ 
/*     */   public void doToggle(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 433 */     if (isActivated(var1))
/*     */     {
/* 435 */       var1.setData(var1.getData() - 1);
/* 436 */       var1.tag.setBoolean("active", false);
/* 437 */       var2.makeSound(var3, "break", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
/*     */     }
/*     */     else
/*     */     {
/* 441 */       var1.setData(var1.getData() + 1);
/* 442 */       var1.tag.setBoolean("active", true);
/* 443 */       var2.makeSound(var3, "heal", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doToggle2(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 449 */     if (isActivated(var1.getData()))
/*     */     {
/* 451 */       var1.setData(var1.getData() - 2);
/* 452 */       var1.tag.setBoolean("active2", false);
/* 453 */       var2.makeSound(var3, "break", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
/*     */     }
/*     */     else
/*     */     {
/* 457 */       var1.setData(var1.getData() + 2);
/* 458 */       var1.tag.setBoolean("active2", true);
/* 459 */       var2.makeSound(var3, "heal", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean canActivate()
/*     */   {
/* 465 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean canActivate2()
/*     */   {
/* 470 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isActivated(int var1)
/*     */   {
/* 475 */     return (var1 & 0x1) == 1;
/*     */   }
/*     */ 
/*     */   public boolean isActivated2(int var1)
/*     */   {
/* 480 */     return (var1 & 0x2) == 2;
/*     */   }
/*     */ 
/*     */   public boolean isActivated(ItemStack var1)
/*     */   {
/* 485 */     return getBoolean(var1, "active");
/*     */   }
/*     */ 
/*     */   public boolean isActivated2(ItemStack var1)
/*     */   {
/* 490 */     return getBoolean(var1, "active2");
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemEECharged
 * JD-Core Version:    0.6.2
 */