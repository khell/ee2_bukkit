/*     */ package ee;
/*     */ 
/*     */ import ee.core.GuiIds;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.ModLoader;
/*     */ import net.minecraft.server.World;
/*     */ import net.minecraft.server.mod_EE;
/*     */ 
/*     */ public class ItemAlchemyBag extends ItemEECharged
/*     */ {
/*  15 */   public static MinecraftServer mc = ModLoader.getMinecraftServerInstance();
/*     */   public static final String prefix = "bag";
/*     */   public static final String prefix_ = "bag_";
/*     */ 
/*     */   public ItemAlchemyBag(int var1)
/*     */   {
/*  21 */     super(var1, 0);
/*  22 */     this.maxStackSize = 1;
/*  23 */     a(true);
/*     */   }
/*     */ 
/*     */   public int getIconFromDamage(int var1)
/*     */   {
/*  28 */     return (var1 >= 0) && (var1 < 16) ? this.textureId + var1 : this.textureId;
/*     */   }
/*     */ 
/*     */   public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/*  36 */     if (ModLoader.getMinecraftServerInstance() != null)
/*     */     {
/*  38 */       var3.openGui(mod_EE.getInstance(), GuiIds.ALCH_BAG, var2, var1.getData(), (int)var3.locY, (int)var3.locZ);
/*     */     }
/*     */ 
/*  41 */     return var1;
/*     */   }
/*     */ 
/*     */   private AlchemyBagData getBagData(ItemStack var1, World var2)
/*     */   {
/*  46 */     String var3 = "bag_global" + var1.getData();
/*  47 */     AlchemyBagData var4 = (AlchemyBagData)var2.a(AlchemyBagData.class, var3);
/*     */ 
/*  49 */     if (var4 == null)
/*     */     {
/*  51 */       var4 = new AlchemyBagData(var3);
/*  52 */       var4.a();
/*  53 */       var2.a(var3, var4);
/*     */     }
/*     */ 
/*  56 */     return var4;
/*     */   }
/*     */ 
/*     */   public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
/*     */   {
/*  65 */     if (ModLoader.getMinecraftServerInstance() != null)
/*     */     {
/*  67 */       var2.openGui(mod_EE.getInstance(), GuiIds.ALCH_BAG, var3, var1.getData(), (int)var2.locY, (int)var2.locZ);
/*     */     }
/*     */ 
/*  70 */     return true;
/*     */   }
/*     */   public void doChargeTick(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */   public void doToggle(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doUncharge(ItemStack var1, World var2, EntityHuman var3) {
/*     */   }
/*     */ 
/*     */   public void doAlternate(ItemStack var1, World var2, EntityHuman var3) {
/*  81 */     if (ModLoader.getMinecraftServerInstance() != null)
/*     */     {
/*  83 */       var3.openGui(mod_EE.getInstance(), GuiIds.ALCH_BAG, var2, var1.getData(), (int)var3.locY, (int)var3.locZ);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void a(ItemStack var1, World var2, Entity var3, int var4, boolean var5)
/*     */   {
/*  93 */     if (!EEProxy.isClient(var2))
/*     */     {
/*  95 */       if ((var3 instanceof EntityHuman))
/*     */       {
/*  97 */         EntityHuman var6 = (EntityHuman)var3;
/*  98 */         String var7 = var6.name;
/*  99 */         String var8 = "bag_" + var7 + var1.getData();
/* 100 */         AlchemyBagData var9 = (AlchemyBagData)var2.a(AlchemyBagData.class, var8);
/*     */ 
/* 102 */         if (var9 == null)
/*     */         {
/* 104 */           var9 = new AlchemyBagData(var8);
/* 105 */           var9.a();
/* 106 */           var2.a(var8, var9);
/*     */         }
/*     */ 
/* 109 */         var9.onUpdate(var2, var6);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static AlchemyBagData getBagData(ItemStack var0, EntityHuman var1, World var2)
/*     */   {
/* 116 */     String var3 = var1.name;
/* 117 */     String var4 = "bag_" + var3 + var0.getData();
/* 118 */     AlchemyBagData var5 = (AlchemyBagData)var2.a(AlchemyBagData.class, var4);
/*     */ 
/* 120 */     if (var5 == null)
/*     */     {
/* 122 */       var5 = new AlchemyBagData(var4);
/* 123 */       var5.a();
/* 124 */       var2.a(var4, var5);
/*     */     }
/*     */ 
/* 127 */     return var5;
/*     */   }
/*     */ 
/*     */   public static AlchemyBagData getBagData(int var0, EntityHuman var1, World var2)
/*     */   {
/* 132 */     String var3 = var1.name;
/* 133 */     String var4 = "bag_" + var3 + var0;
/* 134 */     AlchemyBagData var5 = (AlchemyBagData)var2.a(AlchemyBagData.class, var4);
/*     */ 
/* 136 */     if (var5 == null)
/*     */     {
/* 138 */       var5 = new AlchemyBagData(var4);
/* 139 */       var5.a();
/* 140 */       var2.a(var4, var5);
/*     */     }
/*     */ 
/* 143 */     return var5;
/*     */   }
/*     */ 
/*     */   public void d(ItemStack var1, World var2, EntityHuman var3)
/*     */   {
/* 151 */     if (!EEProxy.isClient(var2))
/*     */     {
/* 153 */       String var4 = var3.name;
/* 154 */       String var5 = "bag_" + var4 + var1.getData();
/* 155 */       AlchemyBagData var6 = (AlchemyBagData)var2.a(AlchemyBagData.class, var5);
/*     */ 
/* 157 */       if (var6 == null)
/*     */       {
/* 159 */         var6 = new AlchemyBagData(var5);
/* 160 */         var2.a(var5, var6);
/* 161 */         var6.a();
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemAlchemyBag
 * JD-Core Version:    0.6.2
 */