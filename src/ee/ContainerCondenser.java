/*     */ package ee;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.server.Container;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.ICrafting;
/*     */ import net.minecraft.server.IInventory;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.PlayerInventory;
/*     */ import net.minecraft.server.Slot;
/*     */ 
/*     */ public class ContainerCondenser extends Container
/*     */ {
/*  13 */   public long currentItemProgress = 0L;
/*  14 */   public int energy = 0;
/*  15 */   public long itemValue = 0L;
/*     */   public TileCondenser eCon;
/*     */ 
/*     */   public ContainerCondenser(PlayerInventory var1, TileCondenser var2)
/*     */   {
/*  20 */     this.eCon = var2;
/*  21 */     setPlayer(var1.player);
/*  22 */     this.eCon.f();
/*  23 */     a(new Slot(var2, 0, 12, 6));
/*     */ 
/*  27 */     for (int var3 = 0; var3 <= 6; var3++)
/*     */     {
/*  29 */       for (int var4 = 0; var4 <= 12; var4++)
/*     */       {
/*  31 */         a(new Slot(var2, 1 + var4 + var3 * 13, 12 + var4 * 18, 26 + var3 * 18));
/*     */       }
/*     */     }
/*     */ 
/*  35 */     for (int var3 = 0; var3 < 3; var3++)
/*     */     {
/*  37 */       for (int var4 = 0; var4 < 9; var4++)
/*     */       {
/*  39 */         a(new Slot(var1, var4 + var3 * 9 + 9, 48 + var4 * 18, 154 + var3 * 18));
/*     */       }
/*     */     }
/*     */ 
/*  43 */     for (int var3 = 0; var3 < 9; var3++)
/*     */     {
/*  45 */       a(new Slot(var1, var3, 48 + var3 * 18, 212));
/*     */     }
/*     */   }
/*     */ 
/*     */   public IInventory getInventory()
/*     */   {
/*  51 */     return this.eCon;
/*     */   }
/*     */ 
/*     */   public void a()
/*     */   {
/*  58 */     super.a();
/*     */ 
/*  60 */     for (int var1 = 0; var1 < this.listeners.size(); var1++)
/*     */     {
/*  62 */       ICrafting var2 = (ICrafting)this.listeners.get(var1);
/*     */ 
/*  64 */       if (this.currentItemProgress != this.eCon.currentItemProgress)
/*     */       {
/*  66 */         var2.setContainerData(this, 0, this.eCon.currentItemProgress);
/*     */       }
/*     */ 
/*  69 */       if (this.energy != this.eCon.scaledEnergy)
/*     */       {
/*  71 */         var2.setContainerData(this, 1, this.eCon.scaledEnergy & 0xFFFF);
/*     */       }
/*     */ 
/*  74 */       if (this.energy != this.eCon.scaledEnergy)
/*     */       {
/*  76 */         var2.setContainerData(this, 2, this.eCon.scaledEnergy >>> 16);
/*     */       }
/*     */     }
/*     */ 
/*  80 */     this.currentItemProgress = this.eCon.currentItemProgress;
/*  81 */     this.energy = this.eCon.scaledEnergy;
/*     */   }
/*     */ 
/*     */   public boolean b(EntityHuman var1)
/*     */   {
/*  86 */     return this.eCon.a(var1);
/*     */   }
/*     */ 
/*     */   public ItemStack a(int var1)
/*     */   {
/*  94 */     ItemStack var2 = null;
/*  95 */     Slot var3 = (Slot)this.e.get(var1);
/*     */ 
/*  97 */     if ((var3 != null) && (var3.c()))
/*     */     {
/*  99 */       ItemStack var4 = var3.getItem();
/* 100 */       var2 = var4.cloneItemStack();
/*     */ 
/* 102 */       if ((var1 >= 1) && (var1 <= 91))
/*     */       {
/* 104 */         if (!a(var4, 92, 127, false))
/*     */         {
/* 106 */           if (var4.count == 0)
/*     */           {
/* 108 */             var3.set(null);
/*     */           }
/*     */ 
/* 111 */           return null;
/*     */         }
/*     */       }
/* 114 */       else if ((var1 >= 92) && (var1 <= 127) && (!a(var4, 1, 91, false)))
/*     */       {
/* 116 */         if (var4.count == 0)
/*     */         {
/* 118 */           var3.set(null);
/*     */         }
/*     */ 
/* 121 */         return null;
/*     */       }
/*     */ 
/* 124 */       if (var4.count == 0)
/*     */       {
/* 126 */         var3.set(null);
/*     */       }
/*     */       else
/*     */       {
/* 130 */         var3.d();
/*     */       }
/*     */ 
/* 133 */       if (var4.count == var2.count)
/*     */       {
/* 135 */         return null;
/*     */       }
/*     */ 
/* 138 */       var3.c(var4);
/*     */     }
/*     */ 
/* 141 */     return var2;
/*     */   }
/*     */ 
/*     */   public void a(EntityHuman var1)
/*     */   {
/* 149 */     super.a(var1);
/* 150 */     this.eCon.g();
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ContainerCondenser
 * JD-Core Version:    0.6.2
 */