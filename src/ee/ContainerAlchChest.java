/*     */ package ee;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.server.Container;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.IInventory;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.PlayerInventory;
/*     */ import net.minecraft.server.Slot;
/*     */ 
/*     */ public class ContainerAlchChest extends Container
/*     */ {
/*     */   private boolean freeRoaming;
/*     */   private IInventory lowerChestInventory;
/*     */   private int numRows;
/*     */ 
/*     */   public ContainerAlchChest(IInventory var1, IInventory var2, boolean var3)
/*     */   {
/*  18 */     this.freeRoaming = (!var3);
/*  19 */     this.lowerChestInventory = var1;
/*  20 */     setPlayer(((PlayerInventory)var2).player);
/*  21 */     this.lowerChestInventory.f();
/*  22 */     this.numRows = (var1.getSize() / 13);
/*     */ 
/*  26 */     for (int var4 = 0; var4 < this.numRows; var4++)
/*     */     {
/*  28 */       for (int var5 = 0; var5 < 13; var5++)
/*     */       {
/*  30 */         a(new Slot(var1, var5 + var4 * 13, 12 + var5 * 18, 5 + var4 * 18));
/*     */       }
/*     */     }
/*     */ 
/*  34 */     for (int var4 = 0; var4 < 3; var4++)
/*     */     {
/*  36 */       for (int var5 = 0; var5 < 9; var5++)
/*     */       {
/*  38 */         a(new Slot(var2, var5 + var4 * 9 + 9, 48 + var5 * 18, 152 + var4 * 18));
/*     */       }
/*     */     }
/*     */ 
/*  42 */     for (int var4 = 0; var4 < 9; var4++)
/*     */     {
/*  44 */       a(new Slot(var2, var4, 48 + var4 * 18, 210));
/*     */     }
/*     */   }
/*     */ 
/*     */   public IInventory getInventory()
/*     */   {
/*  50 */     return this.lowerChestInventory;
/*     */   }
/*     */ 
/*     */   public boolean b(EntityHuman var1)
/*     */   {
/*  55 */     return this.freeRoaming ? true : this.lowerChestInventory.a(var1);
/*     */   }
/*     */ 
/*     */   public ItemStack a(int var1)
/*     */   {
/*  63 */     ItemStack var2 = null;
/*  64 */     Slot var3 = (Slot)this.e.get(var1);
/*     */ 
/*  66 */     if ((var3 != null) && (var3.c()))
/*     */     {
/*  68 */       ItemStack var4 = var3.getItem();
/*  69 */       var2 = var4.cloneItemStack();
/*     */ 
/*  71 */       if (var1 < this.numRows * 13)
/*     */       {
/*  73 */         if (!a(var4, this.numRows * 13, this.e.size(), true))
/*     */         {
/*  75 */           return null;
/*     */         }
/*     */       }
/*  78 */       else if (!a(var4, 0, this.numRows * 13, false))
/*     */       {
/*  80 */         return null;
/*     */       }
/*     */ 
/*  83 */       if (var4.count == 0)
/*     */       {
/*  85 */         var3.set(null);
/*     */       }
/*     */       else
/*     */       {
/*  89 */         var3.d();
/*     */       }
/*     */     }
/*     */ 
/*  93 */     return var2;
/*     */   }
/*     */ 
/*     */   public void a(EntityHuman var1)
/*     */   {
/* 101 */     super.a(var1);
/* 102 */     this.lowerChestInventory.g();
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ContainerAlchChest
 * JD-Core Version:    0.6.2
 */