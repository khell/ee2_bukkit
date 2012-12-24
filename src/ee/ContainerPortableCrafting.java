/*     */ package ee;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.server.Container;
/*     */ import net.minecraft.server.CraftingManager;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.IInventory;
/*     */ import net.minecraft.server.InventoryCraftResult;
/*     */ import net.minecraft.server.InventoryCrafting;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.Slot;
/*     */ import net.minecraft.server.SlotResult;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class ContainerPortableCrafting extends Container
/*     */ {
/*  16 */   public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
/*  17 */   public IInventory craftResult = new InventoryCraftResult();
/*     */   private World worldObj;
/*     */   private int posX;
/*     */   private int posY;
/*     */   private int posZ;
/*     */ 
/*     */   public ContainerPortableCrafting(IInventory var1, EntityHuman var2)
/*     */   {
/*  25 */     this.worldObj = var2.world;
/*  26 */     this.craftMatrix.resultInventory = this.craftResult;
/*  27 */     setPlayer(var2);
/*  28 */     a(new SlotResult(var2, this.craftMatrix, this.craftResult, 0, 124, 35));
/*     */ 
/*  32 */     for (int var3 = 0; var3 < 3; var3++)
/*     */     {
/*  34 */       for (int var4 = 0; var4 < 3; var4++)
/*     */       {
/*  36 */         a(new Slot(this.craftMatrix, var4 + var3 * 3, 30 + var4 * 18, 17 + var3 * 18));
/*     */       }
/*     */     }
/*     */ 
/*  40 */     for (var3 = 0; var3 < 3; var3++)
/*     */     {
/*  42 */       for (int var4 = 0; var4 < 9; var4++)
/*     */       {
/*  44 */         a(new Slot(var2.inventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
/*     */       }
/*     */     }
/*     */ 
/*  48 */     for (var3 = 0; var3 < 9; var3++)
/*     */     {
/*  50 */       a(new Slot(var2.inventory, var3, 8 + var3 * 18, 142));
/*     */     }
/*     */ 
/*  53 */     a(this.craftMatrix);
/*     */   }
/*     */ 
/*     */   public IInventory getInventory()
/*     */   {
/*  59 */     return this.craftMatrix;
/*     */   }
/*     */ 
/*     */   public void a(IInventory var1)
/*     */   {
/*  66 */     this.craftResult.setItem(0, CraftingManager.getInstance().craft(this.craftMatrix));
/*     */   }
/*     */ 
/*     */   public void a(EntityHuman var1)
/*     */   {
/*  74 */     super.a(var1);
/*     */ 
/*  76 */     if (!this.worldObj.isStatic)
/*     */     {
/*  78 */       for (int var2 = 0; var2 < 9; var2++)
/*     */       {
/*  80 */         ItemStack var3 = this.craftMatrix.getItem(var2);
/*     */ 
/*  82 */         if (var3 != null)
/*     */         {
/*  84 */           var1.drop(var3);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean b(EntityHuman var1)
/*     */   {
/*  92 */     return true;
/*     */   }
/*     */ 
/*     */   public ItemStack a(int var1)
/*     */   {
/* 100 */     ItemStack var2 = null;
/* 101 */     Slot var3 = (Slot)this.e.get(var1);
/*     */ 
/* 103 */     if ((var3 != null) && (var3.c()))
/*     */     {
/* 105 */       ItemStack var4 = var3.getItem();
/* 106 */       var2 = var4.cloneItemStack();
/*     */ 
/* 108 */       if (var1 == 0)
/*     */       {
/* 110 */         if (!a(var4, 10, 46, true))
/*     */         {
/* 112 */           return null;
/*     */         }
/*     */       }
/* 115 */       else if ((var1 >= 1) && (var1 < 10))
/*     */       {
/* 117 */         if (!a(var4, 10, 46, false))
/*     */         {
/* 119 */           return null;
/*     */         }
/*     */       }
/* 122 */       else if ((var1 >= 10) && (var1 < 46))
/*     */       {
/* 124 */         if (!a(var4, 1, 10, false))
/*     */         {
/* 126 */           return null;
/*     */         }
/*     */       }
/* 129 */       else if (!a(var4, 10, 46, false))
/*     */       {
/* 131 */         return null;
/*     */       }
/*     */ 
/* 134 */       if (var4.count == 0)
/*     */       {
/* 136 */         var3.set(null);
/*     */       }
/*     */       else
/*     */       {
/* 140 */         var3.d();
/*     */       }
/*     */ 
/* 143 */       if (var4.count == var2.count)
/*     */       {
/* 145 */         return null;
/*     */       }
/*     */ 
/* 148 */       var3.c(var4);
/*     */     }
/*     */ 
/* 151 */     return var2;
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ContainerPortableCrafting
 * JD-Core Version:    0.6.2
 */