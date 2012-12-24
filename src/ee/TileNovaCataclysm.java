/*     */ package ee;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.BlockFire;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityItem;
/*     */ import net.minecraft.server.EntityLiving;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.MathHelper;
/*     */ import net.minecraft.server.NBTTagCompound;
/*     */ import net.minecraft.server.TileEntity;
/*     */ import net.minecraft.server.TileEntityChest;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class TileNovaCataclysm extends TileEE
/*     */ {
/*     */   public boolean fuseLit;
/*     */ 
/*     */   public void a(NBTTagCompound var1)
/*     */   {
/*  24 */     super.a(var1);
/*  25 */     this.fuseLit = var1.getBoolean("fuseLit");
/*     */   }
/*     */ 
/*     */   public void b(NBTTagCompound var1)
/*     */   {
/*  33 */     super.b(var1);
/*  34 */     var1.setBoolean("fuseLit", this.fuseLit);
/*     */   }
/*     */ 
/*     */   public static boolean putInChest(TileEntity var0, ItemStack var1)
/*     */   {
/*  39 */     if ((var1 != null) && (var1.id != 0))
/*     */     {
/*  41 */       if (var0 == null)
/*     */       {
/*  43 */         return false;
/*     */       }
/*     */ 
/*  50 */       if ((var0 instanceof TileEntityChest))
/*     */       {
/*  52 */         for (int var2 = 0; var2 < ((TileEntityChest)var0).getSize(); var2++)
/*     */         {
/*  54 */           ItemStack var3 = ((TileEntityChest)var0).getItem(var2);
/*     */ 
/*  56 */           if ((var3 != null) && (var3.doMaterialsMatch(var1)) && (var3.count + var1.count <= var3.getMaxStackSize()))
/*     */           {
/*  58 */             var3.count += var1.count;
/*  59 */             return true;
/*     */           }
/*     */         }
/*     */ 
/*  63 */         for (var2 = 0; var2 < ((TileEntityChest)var0).getSize(); var2++)
/*     */         {
/*  65 */           if (((TileEntityChest)var0).getItem(var2) == null)
/*     */           {
/*  67 */             ((TileEntityChest)var0).setItem(var2, var1);
/*  68 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*  72 */       else if ((var0 instanceof TileAlchChest))
/*     */       {
/*  74 */         for (int var2 = 0; var2 < ((TileAlchChest)var0).getSize(); var2++)
/*     */         {
/*  76 */           ItemStack var3 = ((TileAlchChest)var0).getItem(var2);
/*     */ 
/*  78 */           if ((var3 != null) && (var3.doMaterialsMatch(var1)) && (var3.count + var1.count <= var3.getMaxStackSize()) && (var3.getData() == var1.getData()))
/*     */           {
/*  80 */             var3.count += var1.count;
/*  81 */             return true;
/*     */           }
/*     */         }
/*     */ 
/*  85 */         for (var2 = 0; var2 < ((TileAlchChest)var0).getSize(); var2++)
/*     */         {
/*  87 */           if (((TileAlchChest)var0).getItem(var2) == null)
/*     */           {
/*  89 */             ((TileAlchChest)var0).setItem(var2, var1);
/*  90 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*  95 */       return false;
/*     */     }
/*     */ 
/* 100 */     return true;
/*     */   }
/*     */ 
/*     */   public void q_()
/*     */   {
/* 110 */     for (int var1 = -1; var1 <= 1; var1 += 2)
/*     */     {
/* 112 */       if (this.world.getTypeId(this.x + var1, this.y, this.z) == Block.FIRE.id)
/*     */       {
/* 114 */         lightFuse();
/*     */       }
/*     */ 
/* 117 */       if (this.world.getTypeId(this.x, this.y + var1, this.z) == Block.FIRE.id)
/*     */       {
/* 119 */         lightFuse();
/*     */       }
/*     */ 
/* 122 */       if (this.world.getTypeId(this.x, this.y, this.z + var1) == Block.FIRE.id)
/*     */       {
/* 124 */         lightFuse();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean tryDropInChest(ItemStack var1)
/*     */   {
/* 131 */     TileEntity var2 = null;
/*     */ 
/* 133 */     if (isChest(this.world.getTileEntity(this.x, this.y + 1, this.z)))
/*     */     {
/* 135 */       var2 = this.world.getTileEntity(this.x, this.y + 1, this.z);
/* 136 */       return putInChest(var2, var1);
/*     */     }
/* 138 */     if (isChest(this.world.getTileEntity(this.x, this.y - 1, this.z)))
/*     */     {
/* 140 */       var2 = this.world.getTileEntity(this.x, this.y - 1, this.z);
/* 141 */       return putInChest(var2, var1);
/*     */     }
/* 143 */     if (isChest(this.world.getTileEntity(this.x + 1, this.y, this.z)))
/*     */     {
/* 145 */       var2 = this.world.getTileEntity(this.x + 1, this.y, this.z);
/* 146 */       return putInChest(var2, var1);
/*     */     }
/* 148 */     if (isChest(this.world.getTileEntity(this.x - 1, this.y, this.z)))
/*     */     {
/* 150 */       var2 = this.world.getTileEntity(this.x - 1, this.y, this.z);
/* 151 */       return putInChest(var2, var1);
/*     */     }
/* 153 */     if (isChest(this.world.getTileEntity(this.x, this.y, this.z + 1)))
/*     */     {
/* 155 */       var2 = this.world.getTileEntity(this.x, this.y, this.z + 1);
/* 156 */       return putInChest(var2, var1);
/*     */     }
/* 158 */     if (isChest(this.world.getTileEntity(this.x, this.y, this.z - 1)))
/*     */     {
/* 160 */       var2 = this.world.getTileEntity(this.x, this.y, this.z - 1);
/* 161 */       return putInChest(var2, var1);
/*     */     }
/*     */ 
/* 165 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean isChest(TileEntity var1)
/*     */   {
/* 171 */     return ((var1 instanceof TileEntityChest)) || ((var1 instanceof TileAlchChest));
/*     */   }
/*     */ 
/*     */   public void setDefaultDirection(int var1, int var2, int var3)
/*     */   {
/* 176 */     if (!this.world.isStatic)
/*     */     {
/* 178 */       int var4 = this.world.getTypeId(var1, var2, var3 - 1);
/* 179 */       int var5 = this.world.getTypeId(var1, var2, var3 + 1);
/* 180 */       int var6 = this.world.getTypeId(var1 - 1, var2, var3);
/* 181 */       int var7 = this.world.getTypeId(var1 + 1, var2, var3);
/* 182 */       byte var8 = 2;
/*     */ 
/* 184 */       if ((Block.n[var4] != 0) && (Block.n[var5] == 0))
/*     */       {
/* 186 */         var8 = 3;
/*     */       }
/*     */ 
/* 189 */       if ((Block.n[var5] != 0) && (Block.n[var4] == 0))
/*     */       {
/* 191 */         var8 = 2;
/*     */       }
/*     */ 
/* 194 */       if ((Block.n[var6] != 0) && (Block.n[var7] == 0))
/*     */       {
/* 196 */         var8 = 5;
/*     */       }
/*     */ 
/* 199 */       if ((Block.n[var7] != 0) && (Block.n[var6] == 0))
/*     */       {
/* 201 */         var8 = 4;
/*     */       }
/*     */ 
/* 204 */       this.direction = var8;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void lightFuse()
/*     */   {
/* 210 */     if (!this.fuseLit)
/*     */     {
/* 212 */       this.fuseLit = true;
/* 213 */       this.world.setTypeId(this.x, this.y, this.z, 0);
/* 214 */       onBlockDestroyedByPlayer();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onBlockPlacedBy(EntityLiving var1)
/*     */   {
/* 220 */     if ((var1 instanceof EntityHuman))
/*     */     {
/* 222 */       this.player = ((EntityHuman)var1).name;
/*     */     }
/*     */ 
/* 225 */     int var2 = MathHelper.floor(var1.yaw * 4.0F / 360.0F + 0.5D) & 0x3;
/*     */ 
/* 227 */     if (var2 == 0)
/*     */     {
/* 229 */       this.direction = 2;
/*     */     }
/*     */ 
/* 232 */     if (var2 == 1)
/*     */     {
/* 234 */       this.direction = 5;
/*     */     }
/*     */ 
/* 237 */     if (var2 == 2)
/*     */     {
/* 239 */       this.direction = 3;
/*     */     }
/*     */ 
/* 242 */     if (var2 == 3)
/*     */     {
/* 244 */       this.direction = 4;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getTextureForSide(int var1)
/*     */   {
/* 250 */     return var1 == 1 ? EEBase.novaCatalystTop : var1 == 0 ? EEBase.novaCatalystBottom : EEBase.novaCataclysmSide;
/*     */   }
/*     */ 
/*     */   public int getInventoryTexture(int var1)
/*     */   {
/* 255 */     return getTextureForSide(var1);
/*     */   }
/*     */ 
/*     */   public int getLightValue()
/*     */   {
/* 260 */     return 4;
/*     */   }
/*     */ 
/*     */   public void onBlockAdded()
/*     */   {
/* 265 */     super.onBlockAdded();
/*     */ 
/* 267 */     if (this.world.isBlockIndirectlyPowered(this.x, this.y, this.z))
/*     */     {
/* 269 */       lightFuse();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onNeighborBlockChange(int var1)
/*     */   {
/* 275 */     if ((var1 > 0) && (Block.byId[var1].isPowerSource()) && (this.world.isBlockIndirectlyPowered(this.x, this.y, this.z)))
/*     */     {
/* 277 */       lightFuse();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onBlockClicked(EntityHuman var1)
/*     */   {
/* 283 */     if ((var1.U() != null) && (var1.U().id == Item.FLINT_AND_STEEL.id))
/*     */     {
/* 285 */       lightFuse();
/*     */     }
/*     */ 
/* 288 */     super.onBlockClicked(var1);
/*     */   }
/*     */ 
/*     */   public boolean onBlockActivated(EntityHuman var1)
/*     */   {
/* 293 */     return false;
/*     */   }
/*     */   public void onBlockRemoval() {
/*     */   }
/*     */ 
/*     */   public void randomDisplayTick(Random var1) {
/*     */   }
/*     */ 
/*     */   public void onBlockDestroyedByExplosion() {
/* 302 */     EntityCataclysmPrimed var1 = null;
/*     */ 
/* 304 */     if ((this.player != null) && (this.player != "") && (this.world.a(this.player) != null))
/*     */     {
/* 306 */       var1 = new EntityCataclysmPrimed(this.world, this.world.a(this.player), this.x + 0.5F, this.y + 0.5F, this.z + 0.5F);
/*     */     }
/*     */     else
/*     */     {
/* 310 */       var1 = new EntityCataclysmPrimed(this.world, this.x + 0.5F, this.y + 0.5F, this.z + 0.5F);
/*     */     }
/*     */ 
/* 313 */     var1.fuse = (this.world.random.nextInt(var1.fuse / 2) + var1.fuse / 4);
/* 314 */     var1.fuse = 0;
/* 315 */     this.world.addEntity(var1);
/*     */   }
/*     */ 
/*     */   public void onBlockDestroyedByPlayer()
/*     */   {
/* 320 */     if (!this.world.isStatic)
/*     */     {
/* 322 */       if (!this.fuseLit)
/*     */       {
/* 324 */         dropBlockAsItem_do(new ItemStack(EEBlock.eeStone.id, 1, 11));
/*     */       }
/*     */       else
/*     */       {
/* 328 */         EntityCataclysmPrimed var1 = null;
/*     */ 
/* 330 */         if ((this.player != null) && (this.player != "") && (this.world.a(this.player) != null))
/*     */         {
/* 332 */           var1 = new EntityCataclysmPrimed(this.world, this.world.a(this.player), this.x + 0.5F, this.y + 0.5F, this.z + 0.5F);
/*     */         }
/*     */         else
/*     */         {
/* 336 */           var1 = new EntityCataclysmPrimed(this.world, this.x + 0.5F, this.y + 0.5F, this.z + 0.5F);
/*     */         }
/*     */ 
/* 339 */         var1.fuse = (this.world.random.nextInt(var1.fuse / 2) + var1.fuse / 4);
/* 340 */         this.world.addEntity(var1);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void dropBlockAsItem_do(ItemStack var1)
/*     */   {
/* 347 */     if (!this.world.isStatic)
/*     */     {
/* 349 */       float var2 = 0.7F;
/* 350 */       double var3 = this.world.random.nextFloat() * var2 + (1.0F - var2) * 0.5D;
/* 351 */       double var5 = this.world.random.nextFloat() * var2 + (1.0F - var2) * 0.5D;
/* 352 */       double var7 = this.world.random.nextFloat() * var2 + (1.0F - var2) * 0.5D;
/* 353 */       EntityItem var9 = new EntityItem(this.world, this.x + var3, this.y + var5, this.z + var7, var1);
/* 354 */       var9.pickupDelay = 10;
/* 355 */       this.world.addEntity(var9);
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.TileNovaCataclysm
 * JD-Core Version:    0.6.2
 */