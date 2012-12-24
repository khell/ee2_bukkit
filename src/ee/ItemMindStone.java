/*    */ package ee;
/*    */ 
/*    */ import net.minecraft.server.EEProxy;
/*    */ import net.minecraft.server.EntityHuman;
/*    */ import net.minecraft.server.ItemStack;
/*    */ import net.minecraft.server.World;
/*    */ 
/*    */ public class ItemMindStone extends ItemEECharged
/*    */ {
/*    */   private int tickCount;
/*    */ 
/*    */   public ItemMindStone(int var1)
/*    */   {
/* 14 */     super(var1, 0);
/* 15 */     this.weaponDamage = 1;
/*    */   }
/*    */ 
/*    */   public void ConsumeReagent(ItemStack var1, EntityHuman var2, boolean var3)
/*    */   {
/* 20 */     EEBase.updatePlayerEffect(var1.getItem(), 200, var2);
/*    */   }
/*    */ 
/*    */   public int getIconFromDamage(int var1)
/*    */   {
/* 25 */     return !isActivated(var1) ? this.textureId : this.textureId + 1;
/*    */   }
/*    */ 
/*    */   public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
/*    */   {
/* 33 */     if (EEProxy.isClient(var2))
/*    */     {
/* 35 */       return var1;
/*    */     }
/*    */ 
/* 39 */     playerTakeLevel(var1, var2, var3, 1);
/* 40 */     return var1;
/*    */   }
/*    */ 
/*    */   public void doPassive(ItemStack var1, World var2, EntityHuman var3)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void doActive(ItemStack var1, World var2, EntityHuman var3) {
/* 48 */     stoneTakeXP(var1, var2, var3);
/*    */   }
/*    */ 
/*    */   private void stoneTakeXP(ItemStack var1, World var2, EntityHuman var3)
/*    */   {
/* 53 */     if ((var3.expLevel > 0) && (var3.exp < 1.0F / var3.getExpToLevel()))
/*    */     {
/* 55 */       var3.levelDown(1);
/* 56 */       setLong(var1, "experience", getLong(var1, "experience") + var3.getExpToLevel());
/*    */     }
/*    */     else
/*    */     {
/* 60 */       var3.exp -= 1.0F / var3.getExpToLevel();
/* 61 */       setLong(var1, "experience", getLong(var1, "experience") + 1L);
/*    */     }
/*    */   }
/*    */ 
/*    */   private void playerTakeLevel(ItemStack var1, World var2, EntityHuman var3, int var4)
/*    */   {
/* 67 */     boolean var5 = false;
/*    */ 
/* 69 */     while ((getLong(var1, "experience") > 0L) && (!var5))
/*    */     {
/* 71 */       setLong(var1, "experience", getLong(var1, "experience") - 1L);
/* 72 */       var3.exp += 1.0F / var3.getExpToLevel();
/*    */ 
/* 74 */       if (var3.exp >= 1.0F)
/*    */       {
/* 76 */         var3.exp -= 1.0F;
/* 77 */         var3.expLevel += 1;
/* 78 */         var5 = true;
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public void doHeld(ItemStack var1, World var2, EntityHuman var3) {
/*    */   }
/*    */ 
/*    */   public void doAlternate(ItemStack var1, World var2, EntityHuman var3) {
/*    */   }
/*    */ 
/*    */   public void doLeftClick(ItemStack var1, World var2, EntityHuman var3) {  }
/*    */ 
/*    */ 
/* 91 */   public boolean canActivate() { return true; }
/*    */ 
/*    */ 
/*    */   public void doChargeTick(ItemStack var1, World var2, EntityHuman var3)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void doUncharge(ItemStack var1, World var2, EntityHuman var3)
/*    */   {
/*    */   }
/*    */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemMindStone
 * JD-Core Version:    0.6.2
 */