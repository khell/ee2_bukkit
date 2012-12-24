/*     */ package ee;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.FoodMetaData;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class ItemBodyStone extends ItemEECharged
/*     */ {
/*     */   private int tickCount;
/*     */ 
/*     */   public ItemBodyStone(int var1)
/*     */   {
/*  14 */     super(var1, 0);
/*  15 */     this.weaponDamage = 1;
/*     */   }
/*     */ 
/*     */   public void ConsumeReagent(ItemStack var1, EntityHuman var2, boolean var3)
/*     */   {
/*  20 */     EEBase.updatePlayerEffect(var1.getItem(), 200, var2);
/*     */   }
/*     */ 
/*     */   public int getIconFromDamage(int var1)
/*     */   {
/*  25 */     return !isActivated(var1) ? this.textureId : this.textureId + 1;
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
/*  53 */     if ((EEProxy.getFoodStats(var3).a() < 19) && (burnFuel(var1, var3, true)))
/*     */     {
/*  55 */       EEProxy.getFoodStats(var3).eat(1, 1.0F);
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
/*  80 */     if (EEProxy.getFoodStats(var3).a() < 19)
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
/*     */   public void doLeftClick(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */   public void doChargeTick(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doUncharge(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doToggle(ItemStack var1, World var2, EntityHuman var3) {
/* 108 */     if (canActivate())
/*     */     {
/* 110 */       if ((var1.getData() & 0x1) == 1)
/*     */       {
/* 112 */         var1.setData(var1.getData() - 1);
/* 113 */         var2.makeSound(var3, "break", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
/*     */       }
/*     */       else
/*     */       {
/* 117 */         var2.makeSound(var3, "heal", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
/* 118 */         var1.setData(var1.getData() + 1);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean canActivate()
/*     */   {
/* 125 */     return true;
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemBodyStone
 * JD-Core Version:    0.6.2
 */