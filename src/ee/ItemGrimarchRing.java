/*     */ package ee;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class ItemGrimarchRing extends ItemEECharged
/*     */ {
/*     */   public ItemGrimarchRing(int var1)
/*     */   {
/*  14 */     super(var1, 0);
/*     */   }
/*     */ 
/*     */   public int ConsumeReagent(EntityHuman var1)
/*     */   {
/*  19 */     return EEBase.Consume(new ItemStack(Block.COBBLESTONE, 14), var1, false) ? 1 : EEBase.Consume(new ItemStack(Block.SAND, 14), var1, false) ? 1 : EEBase.Consume(new ItemStack(Block.DIRT, 14), var1, false) ? 1 : EEBase.Consume(new ItemStack(Item.ARROW, 1), var1, false) ? 1 : EEBase.consumeKleinStarPoint(var1, 14) ? 1 : 0;
/*     */   }
/*     */ 
/*     */   public void doBreak(ItemStack var1, World var2, EntityHuman var3, int var4)
/*     */   {
/*  24 */     int var5 = var4;
/*     */     int var6 = -1;
/*  26 */     for (; var5 > 0 && 
/*  35 */       var6 > 0;)
/*     */     {
/*  28 */       var6 = ConsumeReagent(var3);
/*     */ 
/*  30 */       if (var6 == 0)
/*     */       {
/*  32 */         break;
/*     */ 		}
/*  37 */         var2.makeSound(var3, "random.bow", 0.8F, 0.8F / (c.nextFloat() * 0.4F + 0.8F));
/*  38 */         var2.addEntity(new EntityGrimArrow(var2, var3));
/*  39 */         var5--;
/*  40 */         var6--;
/*     */     }
/*     */   }
/*     */ 
/*     */   public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*  50 */     if (EEProxy.isClient(var2))
/*     */     {
/*  52 */       return var1;
/*     */     }
/*     */ 
/*  56 */     doBreak(var1, var2, var3, 1);
/*  57 */     return var1;
/*     */   }
/*     */ 
/*     */   public void ConsumeReagent(ItemStack var1, EntityHuman var2, boolean var3)
/*     */   {
/*  63 */     EEBase.updatePlayerEffect(var1.getItem(), 100, var2);
/*     */   }
/*     */ 
/*     */   public void doPassive(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doActive(ItemStack var1, World var2, EntityHuman var3) {
/*  70 */     doBreak(var1, var2, var3, 1);
/*     */   }
/*     */ 
/*     */   public void doHeld(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doRelease(ItemStack var1, World var2, EntityHuman var3) {
/*  77 */     if (canActivate())
/*     */     {
/*  79 */       if ((var1.getData() & 0x1) == 1)
/*     */       {
/*  81 */         var1.setData(var1.getData() - 1);
/*  82 */         var2.makeSound(var3, "break", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
/*     */       }
/*     */       else
/*     */       {
/*  86 */         var2.makeSound(var3, "heal", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
/*  87 */         var1.setData(var1.getData() + 1);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doChargeTick(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doUncharge(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doAlternate(ItemStack var1, World var2, EntityHuman var3) {  }
/*     */ 
/*     */ 
/* 100 */   public void doLeftClick(ItemStack var1, World var2, EntityHuman var3) { doBreak(var1, var2, var3, 10); }
/*     */ 
/*     */ 
/*     */   public boolean canActivate()
/*     */   {
/* 105 */     return true;
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemGrimarchRing
 * JD-Core Version:    0.6.2
 */