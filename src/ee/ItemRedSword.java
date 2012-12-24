/*     */ package ee;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.server.AxisAlignedBB;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.DamageSource;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityLiving;
/*     */ import net.minecraft.server.EntityMonster;
/*     */ import net.minecraft.server.EnumAnimation;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class ItemRedSword extends ItemEECharged
/*     */ {
/*     */   public ItemRedSword(int var1)
/*     */   {
/*  20 */     super(var1, 3);
/*  21 */     this.maxStackSize = 1;
/*  22 */     this.weaponDamage = 14;
/*     */   }
/*     */ 
/*     */   public boolean a(ItemStack var1, int var2, int var3, int var4, int var5, EntityLiving var6)
/*     */   {
/*  27 */     if (var2 == Block.WEB.id)
/*     */     {
/*  29 */       EEProxy.dropBlockAsItemStack(Block.byId[var2], var6, var3, var4, var5, new ItemStack(var2, 1, var6.world.getData(var3, var4, var5)));
/*     */     }
/*     */ 
/*  32 */     return super.a(var1, var2, var3, var4, var5, var6);
/*     */   }
/*     */ 
/*     */   public boolean canDestroySpecialBlock(Block var1)
/*     */   {
/*  40 */     return var1.id == Block.WEB.id;
/*     */   }
/*     */ 
/*     */   public boolean ConsumeReagent(int var1, ItemStack var2, EntityHuman var3, boolean var4)
/*     */   {
/*  45 */     if (getFuelRemaining(var2) >= 16)
/*     */     {
/*  47 */       setFuelRemaining(var2, getFuelRemaining(var2) - 16);
/*  48 */       return true;
/*     */     }
/*     */ 
/*  52 */     int var5 = getFuelRemaining(var2);
/*     */ 
/*  54 */     while (getFuelRemaining(var2) < 16)
/*     */     {
/*  56 */       ConsumeReagent(var2, var3, var4);
/*     */ 
/*  58 */       if (var5 == getFuelRemaining(var2))
/*     */       {
/*     */         break;
/*     */       }
/*     */ 
/*  63 */       var5 = getFuelRemaining(var2);
/*     */ 
/*  65 */       if (getFuelRemaining(var2) >= 16)
/*     */       {
/*  67 */         setFuelRemaining(var2, getFuelRemaining(var2) - 16);
/*  68 */         return true;
/*     */       }
/*     */     }
/*     */ 
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */   public void doBreak(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*  78 */     if (chargeLevel(var1) > 0)
/*     */     {
/*  80 */       boolean var4 = false;
/*     */ 		int var5;
/*  83 */       for ( var5 = 1; var5 <= chargeLevel(var1); var5++)
/*     */       {
/*  85 */         if (var5 == chargeLevel(var1))
/*     */         {
/*  87 */           var4 = true;
/*     */         }
/*     */ 
/*  90 */         if (!ConsumeReagent(1, var1, var3, var4))
/*     */         {
/*  92 */           var5--;
/*  93 */           break;
/*     */         }
/*     */       }
/*     */ 
/*  97 */       if (var5 < 1)
/*     */       {
/*  99 */         return;
/*     */       }
/*     */ 
/* 102 */       var3.C_();
/* 103 */       var2.makeSound(var3, "flash", 0.8F, 1.5F);
/* 104 */       List var6 = var2.getEntities(var3, AxisAlignedBB.b((float)var3.locX - (var5 / 1.5D + 2.0D), var3.locY - (var5 / 1.5D + 2.0D), (float)var3.locZ - (var5 / 1.5D + 2.0D), (float)var3.locX + var5 / 1.5D + 2.0D, var3.locY + var5 / 1.5D + 2.0D, (float)var3.locZ + var5 / 1.5D + 2.0D));
/*     */ 
/* 106 */       for (int var7 = 0; var7 < var6.size(); var7++)
/*     */       {
/* 108 */         if (((var6.get(var7) instanceof EntityLiving)) && ((EEBase.getSwordMode(var3)) || ((var6.get(var7) instanceof EntityMonster))))
/*     */         {
/* 110 */           Entity var8 = (Entity)var6.get(var7);
/* 111 */           var8.damageEntity(DamageSource.playerAttack(var3), this.weaponDamage + chargeLevel(var1) * 2);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public float getDestroySpeed(ItemStack var1, Block var2)
/*     */   {
/* 123 */     return var2.id != Block.WEB.id ? 1.5F : 15.0F;
/*     */   }
/*     */ 
/*     */   public EnumAnimation d(ItemStack var1)
/*     */   {
/* 131 */     return EnumAnimation.d;
/*     */   }
/*     */ 
/*     */   public int c(ItemStack var1)
/*     */   {
/* 139 */     return 72000;
/*     */   }
/*     */ 
/*     */   public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 147 */     var3.a(var1, c(var1));
/* 148 */     return var1;
/*     */   }
/*     */ 
/*     */   public int a(Entity var1)
/*     */   {
/* 156 */     return this.weaponDamage;
/*     */   }
/*     */ 
/*     */   public boolean a(ItemStack var1, EntityLiving var2, EntityLiving var3)
/*     */   {
/* 165 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isFull3D()
/*     */   {
/* 170 */     return true;
/*     */   }
/*     */ 
/*     */   public void doRelease(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 175 */     doBreak(var1, var2, var3);
/*     */   }
/*     */ 
/*     */   public void doAlternate(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 180 */     EEBase.updateSwordMode(var3);
/*     */   }
/*     */ 
/*     */   public void doToggle(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemRedSword
 * JD-Core Version:    0.6.2
 */