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
/*     */ public class ItemDarkSword extends ItemEECharged
/*     */ {
/*     */   private int weaponDamage;
/*     */ 
/*     */   public ItemDarkSword(int var1)
/*     */   {
/*  22 */     super(var1, 2);
/*  23 */     this.maxStackSize = 1;
/*  24 */     this.weaponDamage = 12;
/*     */   }
/*     */ 
/*     */   public boolean a(ItemStack var1, int var2, int var3, int var4, int var5, EntityLiving var6)
/*     */   {
/*  29 */     if (var2 == Block.WEB.id)
/*     */     {
/*  31 */       EEProxy.dropBlockAsItemStack(Block.byId[var2], var6, var3, var4, var5, new ItemStack(var2, 1, var6.world.getData(var3, var4, var5)));
/*     */     }
/*     */ 
/*  34 */     return super.a(var1, var2, var3, var4, var5, var6);
/*     */   }
/*     */ 
/*     */   public boolean canDestroySpecialBlock(Block var1)
/*     */   {
/*  42 */     return var1.id == Block.WEB.id;
/*     */   }
/*     */ 
/*     */   public int a(Entity var1)
/*     */   {
/*  50 */     return this.weaponDamage;
/*     */   }
/*     */ 
/*     */   public boolean a(ItemStack var1, EntityLiving var2, EntityLiving var3)
/*     */   {
/*  59 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean ConsumeReagent(int var1, ItemStack var2, EntityHuman var3, boolean var4)
/*     */   {
/*  64 */     if (getFuelRemaining(var2) >= 16)
/*     */     {
/*  66 */       setFuelRemaining(var2, getFuelRemaining(var2) - 16);
/*  67 */       return true;
/*     */     }
/*     */ 
/*  71 */     int var5 = getFuelRemaining(var2);
/*     */ 
/*  73 */     while (getFuelRemaining(var2) < 16)
/*     */     {
/*  75 */       ConsumeReagent(var2, var3, var4);
/*     */ 
/*  77 */       if (var5 == getFuelRemaining(var2))
/*     */       {
/*     */         break;
/*     */       }
/*     */ 
/*  82 */       var5 = getFuelRemaining(var2);
/*     */ 
/*  84 */       if (getFuelRemaining(var2) >= 16)
/*     */       {
/*  86 */         setFuelRemaining(var2, getFuelRemaining(var2) - 16);
/*  87 */         return true;
/*     */       }
/*     */     }
/*     */ 
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */   public void doBreak(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*  97 */     if (chargeLevel(var1) > 0)
/*     */     {
/*  99 */       boolean var4 = false;
/*     */ 
/* 102 */       for (int var5 = 1; var5 <= chargeLevel(var1); var5++)
/*     */       {
/* 104 */         if (var5 == chargeLevel(var1))
/*     */         {
/* 106 */           var4 = true;
/*     */         }
/*     */ 
/* 109 */         if (!ConsumeReagent(1, var1, var3, var4))
/*     */         {
/* 111 */           var5--;
/* 112 */           break;
/*     */         }
/*     */       }
/*     */ 
/* 116 */       if (var5 < 1)
/*     */       {
/* 118 */         return;
/*     */       }
/*     */ 
/* 121 */       var3.C_();
/* 122 */       var2.makeSound(var3, "flash", 0.8F, 1.5F);
/* 123 */       List var6 = var2.getEntities(var3, AxisAlignedBB.b((float)var3.locX - (var5 / 1.5D + 2.0D), var3.locY - (var5 / 1.5D + 2.0D), (float)var3.locZ - (var5 / 1.5D + 2.0D), (float)var3.locX + var5 / 1.5D + 2.0D, var3.locY + var5 / 1.5D + 2.0D, (float)var3.locZ + var5 / 1.5D + 2.0D));
/*     */ 
/* 125 */       for (int var7 = 0; var7 < var6.size(); var7++)
/*     */       {
/* 127 */         if (((var6.get(var7) instanceof EntityLiving)) && ((EEBase.getSwordMode(var3)) || ((var6.get(var7) instanceof EntityMonster))))
/*     */         {
/* 129 */           Entity var8 = (Entity)var6.get(var7);
/* 130 */           var8.damageEntity(DamageSource.playerAttack(var3), this.weaponDamage + chargeLevel(var1) * 2);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public float getDestroySpeed(ItemStack var1, Block var2)
/*     */   {
/* 142 */     return var2.id != Block.WEB.id ? 1.5F : 15.0F;
/*     */   }
/*     */ 
/*     */   public EnumAnimation d(ItemStack var1)
/*     */   {
/* 150 */     return EnumAnimation.d;
/*     */   }
/*     */ 
/*     */   public int c(ItemStack var1)
/*     */   {
/* 158 */     return 72000;
/*     */   }
/*     */ 
/*     */   public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 166 */     if (EEProxy.isClient(var2))
/*     */     {
/* 168 */       return var1;
/*     */     }
/*     */ 
/* 172 */     var3.a(var1, c(var1));
/* 173 */     return var1;
/*     */   }
/*     */ 
/*     */   public boolean isFull3D()
/*     */   {
/* 179 */     return true;
/*     */   }
/*     */ 
/*     */   public void doRelease(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 184 */     doBreak(var1, var2, var3);
/*     */   }
/*     */ 
/*     */   public void doToggle(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemDarkSword
 * JD-Core Version:    0.6.2
 */