/*     */ package ee;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.AxisAlignedBB;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityExperienceOrb;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityItem;
/*     */ import net.minecraft.server.EnumMovingObjectType;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.Material;
/*     */ import net.minecraft.server.MathHelper;
/*     */ import net.minecraft.server.MovingObjectPosition;
/*     */ import net.minecraft.server.Vec3D;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class ItemAttractionRing extends ItemEECharged
/*     */ {
/*     */   public ItemAttractionRing(int var1)
/*     */   {
/*  23 */     super(var1, 0);
/*  24 */     this.maxStackSize = 1;
/*     */   }
/*     */ 
/*     */   public int getIconFromDamage(int var1)
/*     */   {
/*  29 */     return !isActivated(var1) ? this.textureId : this.textureId + 1;
/*     */   }
/*     */ 
/*     */   private void PullItems(Entity var1, EntityHuman var2)
/*     */   {
/*  42 */     if (var1.getClass().equals(EntityItem.class))
/*     */     {
/*  44 */       EntityItem var3 = (EntityItem)var1;
/*  45 */       double var4 = (float)var2.locX + 0.5F - var3.locX;
/*  46 */       double var6 = (float)var2.locY + 0.5F - var3.locY;
/*  47 */       double var8 = (float)var2.locZ + 0.5F - var3.locZ;
/*  48 */       double var10 = var4 * var4 + var6 * var6 + var8 * var8;
/*  49 */       var10 *= var10;
/*     */ 
/*  51 */       if (var10 <= Math.pow(6.0D, 4.0D))
/*     */       {
/*  53 */         double var12 = var4 * 0.01999999955296516D / var10 * Math.pow(6.0D, 3.0D);
/*  54 */         double var14 = var6 * 0.01999999955296516D / var10 * Math.pow(6.0D, 3.0D);
/*  55 */         double var16 = var8 * 0.01999999955296516D / var10 * Math.pow(6.0D, 3.0D);
/*     */ 
/*  57 */         if (var12 > 0.1D)
/*     */         {
/*  59 */           var12 = 0.1D;
/*     */         }
/*  61 */         else if (var12 < -0.1D)
/*     */         {
/*  63 */           var12 = -0.1D;
/*     */         }
/*     */ 
/*  66 */         if (var14 > 0.1D)
/*     */         {
/*  68 */           var14 = 0.1D;
/*     */         }
/*  70 */         else if (var14 < -0.1D)
/*     */         {
/*  72 */           var14 = -0.1D;
/*     */         }
/*     */ 
/*  75 */         if (var16 > 0.1D)
/*     */         {
/*  77 */           var16 = 0.1D;
/*     */         }
/*  79 */         else if (var16 < -0.1D)
/*     */         {
/*  81 */           var16 = -0.1D;
/*     */         }
/*     */ 
/*  84 */         var3.motX += var12 * 1.2D;
/*  85 */         var3.motY += var14 * 1.2D;
/*  86 */         var3.motZ += var16 * 1.2D;
/*     */       }
/*     */     }
/*  89 */     else if (var1.getClass().equals(EntityLootBall.class))
/*     */     {
/*  91 */       EntityLootBall var18 = (EntityLootBall)var1;
/*  92 */       double var4 = (float)var2.locX + 0.5F - var18.locX;
/*  93 */       double var6 = (float)var2.locY + 0.5F - var18.locY;
/*  94 */       double var8 = (float)var2.locZ + 0.5F - var18.locZ;
/*  95 */       double var10 = var4 * var4 + var6 * var6 + var8 * var8;
/*  96 */       var10 *= var10;
/*     */ 
/*  98 */       if (var10 <= Math.pow(6.0D, 4.0D))
/*     */       {
/* 100 */         double var12 = var4 * 0.01999999955296516D / var10 * Math.pow(6.0D, 3.0D);
/* 101 */         double var14 = var6 * 0.01999999955296516D / var10 * Math.pow(6.0D, 3.0D);
/* 102 */         double var16 = var8 * 0.01999999955296516D / var10 * Math.pow(6.0D, 3.0D);
/*     */ 
/* 104 */         if (var12 > 0.1D)
/*     */         {
/* 106 */           var12 = 0.1D;
/*     */         }
/* 108 */         else if (var12 < -0.1D)
/*     */         {
/* 110 */           var12 = -0.1D;
/*     */         }
/*     */ 
/* 113 */         if (var14 > 0.1D)
/*     */         {
/* 115 */           var14 = 0.1D;
/*     */         }
/* 117 */         else if (var14 < -0.1D)
/*     */         {
/* 119 */           var14 = -0.1D;
/*     */         }
/*     */ 
/* 122 */         if (var16 > 0.1D)
/*     */         {
/* 124 */           var16 = 0.1D;
/*     */         }
/* 126 */         else if (var16 < -0.1D)
/*     */         {
/* 128 */           var16 = -0.1D;
/*     */         }
/*     */ 
/* 131 */         var18.motX += var12 * 1.2D;
/* 132 */         var18.motY += var14 * 1.2D;
/* 133 */         var18.motZ += var16 * 1.2D;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 143 */     if (EEProxy.isClient(var2))
/*     */     {
/* 145 */       return var1;
/*     */     }
/*     */ 
/* 149 */     float var4 = 1.0F;
/* 150 */     float var5 = var3.lastPitch + (var3.pitch - var3.lastPitch) * var4;
/* 151 */     float var6 = var3.lastYaw + (var3.yaw - var3.lastYaw) * var4;
/* 152 */     double var7 = var3.lastX + (var3.locX - var3.lastX) * var4;
/* 153 */     double var9 = var3.lastY + (var3.locY - var3.lastY) * var4 + 1.62D - var3.height;
/* 154 */     double var11 = var3.lastZ + (var3.locZ - var3.lastZ) * var4;
/* 155 */     Vec3D var13 = Vec3D.create(var7, var9, var11);
/* 156 */     float var14 = MathHelper.cos(-var6 * 0.01745329F - 3.141593F);
/* 157 */     float var15 = MathHelper.sin(-var6 * 0.01745329F - 3.141593F);
/* 158 */     float var16 = -MathHelper.cos(-var5 * 0.01745329F);
/* 159 */     float var17 = MathHelper.sin(-var5 * 0.01745329F);
/* 160 */     float var18 = var15 * var16;
/* 161 */     float var20 = var14 * var16;
/* 162 */     double var21 = 5.0D;
/* 163 */     Vec3D var23 = var13.add(var18 * var21, var17 * var21, var20 * var21);
/* 164 */     MovingObjectPosition var24 = var2.rayTrace(var13, var23, true);
/*     */ 
/* 166 */     if (var24 == null)
/*     */     {
/* 168 */       return var1;
/*     */     }
/*     */ 
/* 172 */     if (var24.type == EnumMovingObjectType.TILE)
/*     */     {
/* 174 */       int var25 = var24.b;
/* 175 */       int var26 = var24.c;
/* 176 */       int var27 = var24.d;
/*     */ 
/* 178 */       if (var2.getMaterial(var25, var26, var27) == Material.WATER)
/*     */       {
/* 180 */         var2.setTypeId(var25, var26, var27, 0);
/*     */       }
/* 182 */       else if (var2.getMaterial(var25, var26 + 1, var27) == Material.WATER)
/*     */       {
/* 184 */         var2.setTypeId(var25, var26 + 1, var27, 0);
/*     */       }
/* 186 */       else if (var2.getMaterial(var25, var26, var27) == Material.LAVA)
/*     */       {
/* 188 */         var2.setTypeId(var25, var26, var27, 0);
/*     */       }
/* 190 */       else if (var2.getMaterial(var25, var26 + 1, var27) == Material.LAVA)
/*     */       {
/* 192 */         var2.setTypeId(var25, var26 + 1, var27, 0);
/*     */       }
/*     */     }
/*     */ 
/* 196 */     return var1;
/*     */   }
/*     */ 
/*     */   public void doActive(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 203 */     if (!EEProxy.isClient(var2))
/*     */     {
/* 205 */       List var4 = var2.a(EntityItem.class, AxisAlignedBB.b(var3.locX - 10.0D, var3.locY - 10.0D, var3.locZ - 10.0D, var3.locX + 10.0D, var3.locY + 10.0D, var3.locZ + 10.0D));
/* 206 */       Iterator var6 = var4.iterator();
/*     */ 
/* 208 */       while (var6.hasNext())
/*     */       {
/* 210 */         Entity var5 = (Entity)var6.next();
/* 211 */         PullItems(var5, var3);
/*     */       }
/*     */ 
/* 214 */       List var11 = var2.a(EntityLootBall.class, AxisAlignedBB.b(var3.locX - 10.0D, var3.locY - 10.0D, var3.locZ - 10.0D, var3.locX + 10.0D, var3.locY + 10.0D, var3.locZ + 10.0D));
/* 215 */       Iterator var8 = var11.iterator();
/*     */ 
/* 217 */       while (var8.hasNext())
/*     */       {
/* 219 */         Entity var7 = (Entity)var8.next();
/* 220 */         PullItems(var7, var3);
/*     */       }
/*     */ 
/* 223 */       List var12 = var3.world.a(EntityExperienceOrb.class, AxisAlignedBB.b(var3.locX - 10.0D, var3.locY - 10.0D, var3.locZ - 10.0D, var3.locX + 10.0D, var3.locY + 10.0D, var3.locZ + 10.0D));
/* 224 */       Iterator var10 = var12.iterator();
/*     */ 
/* 226 */       while (var10.hasNext())
/*     */       {
/* 228 */         Entity var9 = (Entity)var10.next();
/* 229 */         PullItems(var9, var3);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void ConsumeReagent(ItemStack var1, EntityHuman var2, boolean var3)
/*     */   {
/* 236 */     EEBase.updatePlayerEffect(var1.getItem(), 200, var2);
/*     */   }
/*     */ 
/*     */   public void doToggle(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 241 */     if (isActivated(var1.getData()))
/*     */     {
/* 243 */       var1.setData(0);
/* 244 */       var2.makeSound(var3, "break", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
/*     */     }
/*     */     else
/*     */     {
/* 248 */       var1.setData(1);
/* 249 */       var2.makeSound(var3, "heal", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doChargeTick(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doUncharge(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/* 259 */   public boolean canActivate() { return true; }
/*     */ 
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemAttractionRing
 * JD-Core Version:    0.6.2
 */