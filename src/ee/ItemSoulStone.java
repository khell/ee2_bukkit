/*     */ package ee;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class ItemSoulStone extends ItemEECharged
/*     */ {
/*     */   private int tickCount;
/*     */ 
/*     */   public ItemSoulStone(int var1)
/*     */   {
/*  14 */     super(var1, 0);
/*  15 */     this.weaponDamage = -2;
/*     */   }
/*     */ 
/*     */   public int getIconFromDamage(int var1)
/*     */   {
/*  20 */     return !isActivated(var1) ? this.textureId : this.textureId + 1;
/*     */   }
/*     */ 
/*     */   public void ConsumeReagent(ItemStack var1, EntityHuman var2, boolean var3)
/*     */   {
/*  25 */     EEBase.updatePlayerEffect(var1.getItem(), 200, var2);
/*     */   }
/*     */ 
/*     */   public boolean burnFuel(ItemStack var1, EntityHuman var2, boolean var3)
/*     */   {
/*  30 */     if (getFuelRemaining(var1) >= 4)
/*     */     {
/*  32 */       setFuelRemaining(var1, getFuelRemaining(var1) - 4);
/*  33 */       return true;
/*     */     }
/*     */ 
/*  37 */     super.ConsumeReagent(var1, var2, var3);
/*     */ 
/*  39 */     if (getFuelRemaining(var1) >= 4)
/*     */     {
/*  41 */       setFuelRemaining(var1, getFuelRemaining(var1) - 4);
/*  42 */       return true;
/*     */     }
/*     */ 
/*  46 */     return false;
/*     */   }
/*     */ 
/*     */   public void doHeal(ItemStack var1, World var2, EntityHuman var3, int var4)
/*     */   {
/*  53 */     if ((EEProxy.getEntityHealth(var3) < 20) && (burnFuel(var1, var3, true)))
/*     */     {
/*  55 */       var3.heal(var4);
/*  56 */       var2.makeSound(var3, "heal", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
/*     */     }
/*     */   }
/*     */ 
/*     */   public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*  65 */     if (EEProxy.isClient(var2))
/*     */     {
/*  67 */       return var1;
/*     */     }
/*     */ 
/*  71 */     doHeal(var1, var2, var3, 2);
/*  72 */     return var1;
/*     */   }
/*     */ 
/*     */   public void doPassive(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void doActive(ItemStack var1, World var2, EntityHuman var3) {
/*  80 */     if (EEProxy.getEntityHealth(var3) < 20)
/*     */     {
/*  82 */       this.tickCount += 1;
/*     */ 
/*  84 */       if (this.tickCount % 10 == 0)
/*     */       {
/*  86 */         doHeal(var1, var2, var3, 1);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doHeld(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doRelease(ItemStack var1, World var2, EntityHuman var3) {
/*  95 */     doHeal(var1, var2, var3, 2);
/*     */   }
/*     */   public void doAlternate(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doLeftClick(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doToggle(ItemStack var1, World var2, EntityHuman var3) {
/* 104 */     if (canActivate())
/*     */     {
/* 106 */       if ((var1.getData() & 0x1) == 1)
/*     */       {
/* 108 */         var1.setData(var1.getData() - 1);
/* 109 */         var2.makeSound(var3, "break", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
/*     */       }
/*     */       else
/*     */       {
/* 113 */         var2.makeSound(var3, "heal", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
/* 114 */         var1.setData(var1.getData() + 1);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean canActivate()
/*     */   {
/* 121 */     return true;
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
 * Qualified Name:     ee.ItemSoulStone
 * JD-Core Version:    0.6.2
 */