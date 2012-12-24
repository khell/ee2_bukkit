/*    */ package ee;
/*    */ 
/*    */ import net.minecraft.server.Container;
/*    */ import net.minecraft.server.EntityHuman;
/*    */ import net.minecraft.server.IInventory;
/*    */ import net.minecraft.server.ItemStack;
/*    */ import net.minecraft.server.Slot;
/*    */ import net.minecraft.server.World;
/*    */ 
/*    */ public class ContainerMercurial extends Container
/*    */ {
/*    */   public EntityHuman player;
/*    */   public MercurialEyeData eye;
/*    */   private World worldObj;
/*    */ 
/*    */   public ContainerMercurial(IInventory var1, EntityHuman var2, MercurialEyeData var3)
/*    */   {
/* 18 */     this.player = var2;
/* 19 */     setPlayer(var2);
/* 20 */     this.eye = var3;
/* 21 */     a(new Slot(this.eye, 0, 51, 26));
/* 22 */     a(new SlotMercurialTarget(this.eye, 1, 105, 26));
/* 23 */     this.worldObj = this.player.world;
/*    */ 
/* 26 */     for (int var4 = 0; var4 < 3; var4++)
/*    */     {
/* 28 */       for (int var5 = 0; var5 < 9; var5++)
/*    */       {
/* 30 */         a(new Slot(this.player.inventory, var5 + var4 * 9 + 9, 6 + var5 * 18, 55 + var4 * 18));
/*    */       }
/*    */     }
/*    */ 
/* 34 */     for (var4 = 0; var4 < 9; var4++)
/*    */     {
/* 36 */       a(new Slot(this.player.inventory, var4, 6 + var4 * 18, 113));
/*    */     }
/*    */   }
/*    */ 
/*    */   public IInventory getInventory()
/*    */   {
/* 42 */     return this.eye;
/*    */   }
/*    */ 
/*    */   public int getKleinStarPoints(ItemStack var1)
/*    */   {
/* 47 */     return (var1.getItem() instanceof ItemKleinStar) ? ((ItemKleinStar)var1.getItem()).getKleinPoints(var1) : var1 == null ? 0 : 0;
/*    */   }
/*    */ 
/*    */   public boolean b(EntityHuman var1)
/*    */   {
/* 52 */     return true;
/*    */   }
/*    */ 
/*    */   public ItemStack a(int var1)
/*    */   {
/* 60 */     return null;
/*    */   }
/*    */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ContainerMercurial
 * JD-Core Version:    0.6.2
 */