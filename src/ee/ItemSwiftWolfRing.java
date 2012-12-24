/*     */ package ee;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.server.AxisAlignedBB;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityArrow;
/*     */ import net.minecraft.server.EntityFireball;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityMonster;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class ItemSwiftWolfRing extends ItemEECharged
/*     */ {
/*     */   private int ticksLastSpent;
/*     */   public boolean itemCharging;
/*     */ 
/*     */   public ItemSwiftWolfRing(int var1)
/*     */   {
/*  22 */     super(var1, 0);
/*     */   }
/*     */ 
/*     */   public int getIconFromDamage(int var1)
/*     */   {
/*  27 */     return (isActivated(var1)) && (!isActivated2(var1)) ? this.textureId + 1 : (isActivated2(var1)) && (!isActivated(var1)) ? this.textureId + 2 : (isActivated(var1)) && (isActivated2(var1)) ? this.textureId + 3 : this.textureId;
/*     */   }
/*     */ 
/*     */   public void doGale(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*  32 */     var2.makeSound(var3, "gust", 0.6F, 1.0F);
/*  33 */     var2.addEntity(new EntityWindEssence(var2, var3));
/*     */   }
/*     */ 
/*     */   public void doInterdiction(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*  38 */     List var4 = var2.a(EntityMonster.class, AxisAlignedBB.b((float)var3.locX - 5.0F, var3.locY - 5.0D, (float)var3.locZ - 5.0F, (float)var3.locX + 5.0F, var3.locY + 5.0D, (float)var3.locZ + 5.0F));
/*  39 */     Iterator var6 = var4.iterator();
/*     */ 
/*  41 */     while (var6.hasNext())
/*     */     {
/*  43 */       Entity var5 = (Entity)var6.next();
/*  44 */       PushEntities(var5, var3);
/*     */     }
/*     */ 
/*  47 */     List var11 = var2.a(EntityArrow.class, AxisAlignedBB.b((float)var3.locX - 5.0F, var3.locY - 5.0D, (float)var3.locZ - 5.0F, (float)var3.locX + 5.0F, var3.locY + 5.0D, (float)var3.locZ + 5.0F));
/*  48 */     Iterator var8 = var11.iterator();
/*     */ 
/*  50 */     while (var8.hasNext())
/*     */     {
/*  52 */       Entity var7 = (Entity)var8.next();
/*  53 */       PushEntities(var7, var3);
/*     */     }
/*     */ 
/*  56 */     List var12 = var2.a(EntityFireball.class, AxisAlignedBB.b((float)var3.locX - 5.0F, var3.locY - 5.0D, (float)var3.locZ - 5.0F, (float)var3.locX + 5.0F, var3.locY + 5.0D, (float)var3.locZ + 5.0F));
/*  57 */     Iterator var10 = var12.iterator();
/*     */ 
/*  59 */     while (var10.hasNext())
/*     */     {
/*  61 */       Entity var9 = (Entity)var10.next();
/*  62 */       PushEntities(var9, var3);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void PushEntities(Entity var1, EntityHuman var2)
/*     */   {
/*  68 */     if (!(var1 instanceof EntityHuman))
/*     */     {
/*  70 */       double var4 = var2.locX + 0.5D - var1.locX;
/*  71 */       double var6 = var2.locY + 0.5D - var1.locY;
/*  72 */       double var8 = var2.locZ + 0.5D - var1.locZ;
/*  73 */       double var10 = var4 * var4 + var6 * var6 + var8 * var8;
/*  74 */       var10 *= var10;
/*     */ 
/*  76 */       if (var10 <= Math.pow(6.0D, 4.0D))
/*     */       {
/*  78 */         double var12 = -(var4 * 0.01999999955296516D / var10) * Math.pow(6.0D, 3.0D);
/*  79 */         double var14 = -(var6 * 0.01999999955296516D / var10) * Math.pow(6.0D, 3.0D);
/*  80 */         double var16 = -(var8 * 0.01999999955296516D / var10) * Math.pow(6.0D, 3.0D);
/*     */ 
/*  82 */         if (var12 > 0.0D)
/*     */         {
/*  84 */           var12 = 0.12D;
/*     */         }
/*  86 */         else if (var12 < 0.0D)
/*     */         {
/*  88 */           var12 = -0.12D;
/*     */         }
/*     */ 
/*  91 */         if (var14 > 0.2D)
/*     */         {
/*  93 */           var14 = 0.12D;
/*     */         }
/*  95 */         else if (var14 < -0.1D)
/*     */         {
/*  97 */           var14 = 0.12D;
/*     */         }
/*     */ 
/* 100 */         if (var16 > 0.0D)
/*     */         {
/* 102 */           var16 = 0.12D;
/*     */         }
/* 104 */         else if (var16 < 0.0D)
/*     */         {
/* 106 */           var16 = -0.12D;
/*     */         }
/*     */ 
/* 109 */         var1.motX += var12;
/* 110 */         var1.motY += var14;
/* 111 */         var1.motZ += var16;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void ConsumeReagent(ItemStack var1, EntityHuman var2, boolean var3)
/*     */   {
/* 118 */     EEBase.ConsumeReagentForDuration(var1, var2, var3);
/*     */   }
/*     */ 
/*     */   public void doPassive(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 123 */     if (var3.fallDistance > 0.0F)
/*     */     {
/* 125 */       var3.fallDistance = 0.0F;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doActive(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 131 */     if (isActivated2(var1.getData()))
/*     */     {
/* 133 */       doInterdiction(var1, var2, var3);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doHeld(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doRelease(ItemStack var1, World var2, EntityHuman var3) {
/* 141 */     doGale(var1, var2, var3);
/*     */   }
/*     */ 
/*     */   public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 149 */     if (EEProxy.isClient(var2))
/*     */     {
/* 151 */       return var1;
/*     */     }
/*     */ 
/* 155 */     doGale(var1, var2, var3);
/* 156 */     return var1;
/*     */   }
/*     */ 
/*     */   public void doAlternate(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 162 */     doToggle2(var1, var2, var3);
/*     */   }
/*     */ 
/*     */   public void doLeftClick(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doToggle(ItemStack var1, World var2, EntityHuman var3) {
/* 169 */     super.doToggle(var1, var2, var3);
/*     */ 
/* 171 */     if ((!isActivated(var1)) && (!EEBase.isPlayerInWater(var3)) && (!EEBase.isPlayerInLava(var3)))
/*     */     {
/* 173 */       var3.abilities.isFlying = false;
/* 174 */       var3.updateAbilities();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean canActivate()
/*     */   {
/* 180 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean canActivate2()
/*     */   {
/* 185 */     return true;
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
 * Qualified Name:     ee.ItemSwiftWolfRing
 * JD-Core Version:    0.6.2
 */