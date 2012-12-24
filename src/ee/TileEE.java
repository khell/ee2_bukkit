/*     */ package ee;
/*     */ 
/*     */ import ee.network.PacketHandler;
/*     */ import ee.network.PacketTypeHandler;
/*     */ import ee.network.TileEntityPacket;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityItem;
/*     */ import net.minecraft.server.EntityLiving;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.MathHelper;
/*     */ import net.minecraft.server.NBTTagCompound;
/*     */ import net.minecraft.server.Packet;
/*     */ import net.minecraft.server.TileEntity;
/*     */ import net.minecraft.server.TileEntityChest;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class TileEE extends TileEntity
/*     */ {
/*     */   public byte direction;
/*     */   public String player;
/*     */ 
/*     */   public void a(NBTTagCompound var1)
/*     */   {
/*  29 */     super.a(var1);
/*  30 */     this.direction = var1.getByte("direction");
/*  31 */     this.player = var1.getString("player");
/*     */   }
/*     */ 
/*     */   public void b(NBTTagCompound var1)
/*     */   {
/*  39 */     super.b(var1);
/*  40 */     var1.setByte("direction", this.direction);
/*     */ 
/*  42 */     if ((this.player != null) && (this.player != ""))
/*     */     {
/*  44 */       var1.setString("player", this.player);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getKleinLevel(int var1)
/*     */   {
/*  50 */     return var1 == EEItem.kleinStar6.id ? 6 : var1 == EEItem.kleinStar5.id ? 5 : var1 == EEItem.kleinStar4.id ? 4 : var1 == EEItem.kleinStar3.id ? 3 : var1 == EEItem.kleinStar2.id ? 2 : var1 == EEItem.kleinStar1.id ? 1 : 0;
/*     */   }
/*     */ 
/*     */   public float getWOFTReciprocal(float var1)
/*     */   {
/*  55 */     float var2 = 1.0F / var1;
/*  56 */     return var2 * (EEBase.getMachineFactor() / 16.0F);
/*     */   }
/*     */ 
/*     */   public static boolean putInChest(TileEntity var0, ItemStack var1)
/*     */   {
/*  61 */     if ((var1 != null) && (var1.id != 0))
/*     */     {
/*  63 */       if (var0 == null)
/*     */       {
/*  65 */         return false;
/*     */       }
/*     */ 
/*  72 */       if ((var0 instanceof TileEntityChest))
/*     */       {
/*  74 */         for (int var2 = 0; var2 < ((TileEntityChest)var0).getSize(); var2++)
/*     */         {
/*  76 */           ItemStack var3 = ((TileEntityChest)var0).getItem(var2);
/*     */ 
/*  78 */           if ((var3 != null) && (var3.doMaterialsMatch(var1)) && (var3.count + var1.count <= var3.getMaxStackSize()))
/*     */           {
/*  80 */             var3.count += var1.count;
/*  81 */             return true;
/*     */           }
/*     */         }
/*     */ 
/*  85 */         for (var2 = 0; var2 < ((TileEntityChest)var0).getSize(); var2++)
/*     */         {
/*  87 */           if (((TileEntityChest)var0).getItem(var2) == null)
/*     */           {
/*  89 */             ((TileEntityChest)var0).setItem(var2, var1);
/*  90 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*  94 */       else if ((var0 instanceof TileAlchChest))
/*     */       {
/*  96 */         for (int var2 = 0; var2 < ((TileAlchChest)var0).getSize(); var2++)
/*     */         {
/*  98 */           ItemStack var3 = ((TileAlchChest)var0).getItem(var2);
/*     */ 
/* 100 */           if ((var3 != null) && (var3.doMaterialsMatch(var1)) && (var3.count + var1.count <= var3.getMaxStackSize()) && (var3.getData() == var1.getData()))
/*     */           {
/* 102 */             var3.count += var1.count;
/* 103 */             return true;
/*     */           }
/*     */         }
/*     */ 
/* 107 */         for (var2 = 0; var2 < ((TileAlchChest)var0).getSize(); var2++)
/*     */         {
/* 109 */           if (((TileAlchChest)var0).getItem(var2) == null)
/*     */           {
/* 111 */             ((TileAlchChest)var0).setItem(var2, var1);
/* 112 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 117 */       return false;
/*     */     }
/*     */ 
/* 122 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean tryDropInChest(ItemStack var1)
/*     */   {
/* 128 */     if ((this.world != null) && (!EEProxy.isClient(this.world)))
/*     */     {
/* 130 */       TileEntity var2 = null;
/*     */ 
/* 132 */       if (isChest(this.world.getTileEntity(this.x, this.y + 1, this.z)))
/*     */       {
/* 134 */         var2 = this.world.getTileEntity(this.x, this.y + 1, this.z);
/* 135 */         return putInChest(var2, var1);
/*     */       }
/* 137 */       if (isChest(this.world.getTileEntity(this.x, this.y - 1, this.z)))
/*     */       {
/* 139 */         var2 = this.world.getTileEntity(this.x, this.y - 1, this.z);
/* 140 */         return putInChest(var2, var1);
/*     */       }
/* 142 */       if (isChest(this.world.getTileEntity(this.x + 1, this.y, this.z)))
/*     */       {
/* 144 */         var2 = this.world.getTileEntity(this.x + 1, this.y, this.z);
/* 145 */         return putInChest(var2, var1);
/*     */       }
/* 147 */       if (isChest(this.world.getTileEntity(this.x - 1, this.y, this.z)))
/*     */       {
/* 149 */         var2 = this.world.getTileEntity(this.x - 1, this.y, this.z);
/* 150 */         return putInChest(var2, var1);
/*     */       }
/* 152 */       if (isChest(this.world.getTileEntity(this.x, this.y, this.z + 1)))
/*     */       {
/* 154 */         var2 = this.world.getTileEntity(this.x, this.y, this.z + 1);
/* 155 */         return putInChest(var2, var1);
/*     */       }
/* 157 */       if (isChest(this.world.getTileEntity(this.x, this.y, this.z - 1)))
/*     */       {
/* 159 */         var2 = this.world.getTileEntity(this.x, this.y, this.z - 1);
/* 160 */         return putInChest(var2, var1);
/*     */       }
/*     */ 
/* 164 */       return false;
/*     */     }
/*     */ 
/* 169 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean isChest(TileEntity var1)
/*     */   {
/* 175 */     return ((var1 instanceof TileEntityChest)) || ((var1 instanceof TileAlchChest));
/*     */   }
/*     */ 
/*     */   public void setDefaultDirection()
/*     */   {
/* 180 */     if (!this.world.isStatic)
/*     */     {
/* 182 */       int var1 = this.world.getTypeId(this.x, this.y, this.z - 1);
/* 183 */       int var2 = this.world.getTypeId(this.x, this.y, this.z + 1);
/* 184 */       int var3 = this.world.getTypeId(this.x - 1, this.y, this.z);
/* 185 */       int var4 = this.world.getTypeId(this.x + 1, this.y, this.z);
/* 186 */       byte var5 = 2;
/*     */ 
/* 188 */       if ((net.minecraft.server.Block.n[var1] != 0) && (net.minecraft.server.Block.n[var2] == 0))
/*     */       {
/* 190 */         var5 = 3;
/*     */       }
/*     */ 
/* 193 */       if ((net.minecraft.server.Block.n[var2] != 0) && (net.minecraft.server.Block.n[var1] == 0))
/*     */       {
/* 195 */         var5 = 2;
/*     */       }
/*     */ 
/* 198 */       if ((net.minecraft.server.Block.n[var3] != 0) && (net.minecraft.server.Block.n[var4] == 0))
/*     */       {
/* 200 */         var5 = 5;
/*     */       }
/*     */ 
/* 203 */       if ((net.minecraft.server.Block.n[var4] != 0) && (net.minecraft.server.Block.n[var3] == 0))
/*     */       {
/* 205 */         var5 = 4;
/*     */       }
/*     */ 
/* 208 */       this.direction = var5;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onBlockPlacedBy(EntityLiving var1)
/*     */   {
/* 214 */     if ((var1 instanceof EntityHuman))
/*     */     {
/* 216 */       this.player = ((EntityHuman)var1).name;
/*     */     }
/*     */ 
/* 219 */     int var2 = MathHelper.floor(var1.yaw * 4.0F / 360.0F + 0.5D) & 0x3;
/*     */ 
/* 221 */     if (var2 == 0)
/*     */     {
/* 223 */       this.direction = 2;
/*     */     }
/*     */ 
/* 226 */     if (var2 == 1)
/*     */     {
/* 228 */       this.direction = 5;
/*     */     }
/*     */ 
/* 231 */     if (var2 == 2)
/*     */     {
/* 233 */       this.direction = 3;
/*     */     }
/*     */ 
/* 236 */     if (var2 == 3)
/*     */     {
/* 238 */       this.direction = 4;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getTextureForSide(int var1)
/*     */   {
/* 244 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getInventoryTexture(int var1)
/*     */   {
/* 249 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getLightValue()
/*     */   {
/* 254 */     return 0;
/*     */   }
/*     */ 
/*     */   public boolean onBlockActivated(EntityHuman var1)
/*     */   {
/* 259 */     return false;
/*     */   }
/*     */   public void onNeighborBlockChange(int var1) {
/*     */   }
/*     */ 
/*     */   public void onBlockClicked(EntityHuman var1) {
/*     */   }
/*     */ 
/*     */   public boolean clientFail() {
/* 268 */     return this.world.getTileEntity(this.x, this.y, this.z) != this ? true : this.world == null ? true : EEProxy.isClient(this.world);
/*     */   }
/*     */ 
/*     */   public void onBlockAdded() {
/*     */   }
/*     */ 
/*     */   public void onBlockRemoval() {
/* 275 */     for (int var1 = 0; var1 < getSizeInventory(); var1++)
/*     */     {
/* 277 */       ItemStack var2 = getStackInSlot(var1);
/*     */ 
/* 279 */       if (var2 != null)
/*     */       {
/* 281 */         float var3 = this.world.random.nextFloat() * 0.8F + 0.1F;
/* 282 */         float var4 = this.world.random.nextFloat() * 0.8F + 0.1F;
/* 283 */         float var5 = this.world.random.nextFloat() * 0.8F + 0.1F;
/*     */ 
/* 285 */         while (var2.count > 0)
/*     */         {
/* 287 */           int var6 = this.world.random.nextInt(21) + 10;
/*     */ 
/* 289 */           if (var6 > var2.count)
/*     */           {
/* 291 */             var6 = var2.count;
/*     */           }
/*     */ 
/* 294 */           var2.count -= var6;
/* 295 */           EntityItem var7 = new EntityItem(this.world, this.x + var3, this.y + var4, this.z + var5, new ItemStack(var2.id, var6, var2.getData()));
/*     */ 
/* 297 */           if (var7 != null)
/*     */           {
/* 299 */             float var8 = 0.05F;
/* 300 */             var7.motX = ((float)this.world.random.nextGaussian() * var8);
/* 301 */             var7.motY = ((float)this.world.random.nextGaussian() * var8 + 0.2F);
/* 302 */             var7.motZ = ((float)this.world.random.nextGaussian() * var8);
/*     */ 
/* 304 */             if ((var7.itemStack.getItem() instanceof ItemKleinStar))
/*     */             {
/* 306 */               ((ItemKleinStar)var7.itemStack.getItem()).setKleinPoints(var7.itemStack, ((ItemKleinStar)var2.getItem()).getKleinPoints(var2));
/*     */             }
/*     */ 
/* 309 */             this.world.addEntity(var7);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private ItemStack getStackInSlot(int var1)
/*     */   {
/* 318 */     return null;
/*     */   }
/*     */ 
/*     */   private int getSizeInventory()
/*     */   {
/* 323 */     return 0;
/*     */   }
/*     */   public void randomDisplayTick(Random var1) {
/*     */   }
/*     */   public void onBlockDestroyedByExplosion() {
/*     */   }
/*     */ 
/*     */   public void onBlockDestroyedByPlayer() {
/*     */   }
/*     */ 
/*     */   protected void dropBlockAsItem_do(ItemStack var1) {
/* 334 */     if (!this.world.isStatic)
/*     */     {
/* 336 */       float var2 = 0.7F;
/* 337 */       double var3 = this.world.random.nextFloat() * var2 + (1.0F - var2) * 0.5D;
/* 338 */       double var5 = this.world.random.nextFloat() * var2 + (1.0F - var2) * 0.5D;
/* 339 */       double var7 = this.world.random.nextFloat() * var2 + (1.0F - var2) * 0.5D;
/* 340 */       EntityItem var9 = new EntityItem(this.world, this.x + var3, this.y + var5, this.z + var7, var1);
/* 341 */       var9.pickupDelay = 10;
/* 342 */       this.world.addEntity(var9);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setDirection(byte var1)
/*     */   {
/* 348 */     this.direction = var1;
/*     */   }
/*     */ 
/*     */   public void setPlayerName(String var1)
/*     */   {
/* 353 */     this.player = var1;
/*     */   }
/*     */ 
/*     */   public Packet d()
/*     */   {
/* 361 */     TileEntityPacket var1 = (TileEntityPacket)PacketHandler.getPacket(PacketTypeHandler.TILE);
/* 362 */     var1.setCoords(this.x, this.y, this.z);
/* 363 */     var1.setOrientation(this.direction);
/* 364 */     var1.setPlayerName(this.player);
/* 365 */     return PacketHandler.getPacketForSending(var1);
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.TileEE
 * JD-Core Version:    0.6.2
 */