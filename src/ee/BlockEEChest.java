/*     */ package ee;
/*     */ 
/*     */ import forge.ITextureProvider;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.BlockContainer;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityItem;
/*     */ import net.minecraft.server.EntityLiving;
/*     */ import net.minecraft.server.IBlockAccess;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.Material;
/*     */ import net.minecraft.server.NBTTagCompound;
/*     */ import net.minecraft.server.TileEntity;
/*     */ import net.minecraft.server.World;
/*     */ import net.minecraft.server.mod_EE;
/*     */ 
/*     */ public class BlockEEChest extends BlockContainer
/*     */   implements ITextureProvider
/*     */ {
/*  22 */   private Random random = new Random();
/*  23 */   private Class[] tileEntityMap = new Class[15];
/*     */ 
/*     */   protected BlockEEChest(int var1)
/*     */   {
/*  27 */     super(var1, Material.STONE);
/*     */   }
/*     */ 
/*     */   public String getTextureFile()
/*     */   {
/*  32 */     return "/eqex/eqexterra.png";
/*     */   }
/*     */ 
/*     */   public TileEntity a_()
/*     */   {
/*  40 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean a()
/*     */   {
/*  49 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean b()
/*     */   {
/*  57 */     return false;
/*     */   }
/*     */ 
/*     */   public int c()
/*     */   {
/*  65 */     return mod_EE.chestModelID;
/*     */   }
/*     */ 
/*     */   public int getLightValue(IBlockAccess var1, int var2, int var3, int var4)
/*     */   {
/*  70 */     TileEE var5 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
/*  71 */     return var5 == null ? lightEmission[this.id] : var5.getLightValue();
/*     */   }
/*     */ 
/*     */   public void doPhysics(World var1, int var2, int var3, int var4, int var5)
/*     */   {
/*  80 */     TileEE var6 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
/*     */ 
/*  82 */     if (var6 == null)
/*     */     {
/*  84 */       var1.setTypeId(var2, var3, var4, 0);
/*     */     }
/*     */     else
/*     */     {
/*  88 */       var6.onNeighborBlockChange(var5);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void postPlace(World var1, int var2, int var3, int var4, EntityLiving var5)
/*     */   {
/*  97 */     TileEE var6 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
/*     */ 
/*  99 */     if (var6 != null)
/*     */     {
/* 101 */       var6.onBlockPlacedBy(var5);
/*     */     }
/*     */   }
/*     */ 
/*     */   public float getHardness(int var1)
/*     */   {
/* 107 */     switch (var1)
/*     */     {
/*     */     case 0:
/* 110 */       return 1.5F;
/*     */     case 1:
/* 112 */       return 3.5F;
/*     */     }
/* 114 */     return 3.0F;
/*     */   }
/*     */ 
/*     */   public int getDropType(int var1, Random var2, int var3)
/*     */   {
/* 123 */     return this.id;
/*     */   }
/*     */ 
/*     */   public int a(Random var1)
/*     */   {
/* 131 */     return 1;
/*     */   }
/*     */ 
/*     */   public int getDropData(int var1)
/*     */   {
/* 139 */     return var1;
/*     */   }
/*     */ 
/*     */   public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5)
/*     */   {
/* 144 */     TileEE var6 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
/*     */ 
/* 146 */     if (var6 != null)
/*     */     {
/* 148 */       var6.randomDisplayTick(var5);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addTileEntityMapping(int var1, Class var2)
/*     */   {
/* 154 */     this.tileEntityMap[var1] = var2;
/*     */   }
/*     */ 
/*     */   public int a(int var1, int var2)
/*     */   {
/* 162 */     return ((TileEE)getBlockEntity(var2)).getInventoryTexture(var1);
/*     */   }
/*     */ 
/*     */   public int a(int var1)
/*     */   {
/* 170 */     return a(var1, 0);
/*     */   }
/*     */ 
/*     */   public int getBlockTexture(IBlockAccess var1, int var2, int var3, int var4, int var5)
/*     */   {
/* 175 */     TileEE var6 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
/* 176 */     return var6 == null ? 0 : var6.getTextureForSide(var5);
/*     */   }
/*     */ 
/*     */   public void remove(World var1, int var2, int var3, int var4)
/*     */   {
/* 193 */     if ((var1.getTileEntity(var2, var3, var4) instanceof TileAlchChest))
/*     */     {
/* 195 */       TileAlchChest var5 = (TileAlchChest)var1.getTileEntity(var2, var3, var4);
/*     */ 
/* 197 */       if (var5 != null)
/*     */       {
/* 199 */         for (int var6 = 0; var6 < var5.getSize(); var6++)
/*     */         {
/* 201 */           ItemStack var7 = var5.getItem(var6);
/*     */ 
/* 203 */           if (var7 != null)
/*     */           {
/* 205 */             float var8 = this.random.nextFloat() * 0.8F + 0.1F;
/* 206 */             float var9 = this.random.nextFloat() * 0.8F + 0.1F;
/*     */             EntityItem var10;
/* 208 */             for (float var11 = this.random.nextFloat() * 0.8F + 0.1F; var7.count > 0; var1.addEntity(var10))
/*     */             {
/* 210 */               int var12 = this.random.nextInt(21) + 10;
/*     */ 
/* 212 */               if (var12 > var7.count)
/*     */               {
/* 214 */                 var12 = var7.count;
/*     */               }
/*     */ 
/* 217 */               var7.count -= var12;
/* 218 */               var10 = new EntityItem(var1, var2 + var8, var3 + var9, var4 + var11, new ItemStack(var7.id, var12, var7.getData()));
/* 219 */               float var13 = 0.05F;
/* 220 */               var10.motX = ((float)this.random.nextGaussian() * var13);
/* 221 */               var10.motY = ((float)this.random.nextGaussian() * var13 + 0.2F);
/* 222 */               var10.motZ = ((float)this.random.nextGaussian() * var13);
/*     */ 
/* 224 */               if (var7.hasTag())
/*     */               {
/* 226 */                 var10.itemStack.setTag((NBTTagCompound)var7.getTag().clone());
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 233 */     else if ((var1.getTileEntity(var2, var3, var4) instanceof TileCondenser))
/*     */     {
/* 235 */       TileCondenser var14 = (TileCondenser)var1.getTileEntity(var2, var3, var4);
/*     */ 
/* 237 */       if (var14 != null)
/*     */       {
/* 239 */         for (int var6 = 0; var6 < var14.getSize(); var6++)
/*     */         {
/* 241 */           ItemStack var7 = var14.getItem(var6);
/*     */ 
/* 243 */           if (var7 != null)
/*     */           {
/* 245 */             float var8 = this.random.nextFloat() * 0.8F + 0.1F;
/* 246 */             float var9 = this.random.nextFloat() * 0.8F + 0.1F;
/*     */             EntityItem var10;
/* 248 */             for (float var11 = this.random.nextFloat() * 0.8F + 0.1F; var7.count > 0; var1.addEntity(var10))
/*     */             {
/* 250 */               int var12 = this.random.nextInt(21) + 10;
/*     */ 
/* 252 */               if (var12 > var7.count)
/*     */               {
/* 254 */                 var12 = var7.count;
/*     */               }
/*     */ 
/* 257 */               var7.count -= var12;
/* 258 */               var10 = new EntityItem(var1, var2 + var8, var3 + var9, var4 + var11, new ItemStack(var7.id, var12, var7.getData()));
/* 259 */               float var13 = 0.05F;
/* 260 */               var10.motX = ((float)this.random.nextGaussian() * var13);
/* 261 */               var10.motY = ((float)this.random.nextGaussian() * var13 + 0.2F);
/* 262 */               var10.motZ = ((float)this.random.nextGaussian() * var13);
/*     */ 
/* 264 */               if (var7.hasTag())
/*     */               {
/* 266 */                 var10.itemStack.setTag((NBTTagCompound)var7.getTag().clone());
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 274 */     super.remove(var1, var2, var3, var4);
/*     */   }
/*     */ 
/*     */   public void onPlace(World var1, int var2, int var3, int var4)
/*     */   {
/* 282 */     super.onPlace(var1, var2, var3, var4);
/* 283 */     TileEE var5 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
/*     */ 
/* 285 */     if (var5 != null)
/*     */     {
/* 287 */       var5.setDefaultDirection();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean interact(World var1, int var2, int var3, int var4, EntityHuman var5)
/*     */   {
/* 297 */     if (var5.isSneaking())
/*     */     {
/* 299 */       return false;
/*     */     }
/*     */ 
/* 303 */     TileEE var6 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
/* 304 */     return var6 == null ? false : var6.onBlockActivated(var5);
/*     */   }
/*     */ 
/*     */   public void addCreativeItems(ArrayList var1)
/*     */   {
/* 310 */     var1.add(EEBlock.alchChest);
/* 311 */     var1.add(EEBlock.condenser);
/*     */   }
/*     */ 
/*     */   public TileEntity getBlockEntity(int var1)
/*     */   {
/*     */     try
/*     */     {
/* 318 */       return (TileEntity)this.tileEntityMap[var1].getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
/*     */     }
/*     */     catch (Exception var3) {
/*     */     }
/* 322 */     return null;
/*     */   }
/*     */ 
/*     */   public void setItemName(int var1, String var2)
/*     */   {
/* 328 */     Item var3 = Item.byId[this.id];
/* 329 */     ((ItemBlockEEChest)var3).setMetaName(var1, "tile." + var2);
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.BlockEEChest
 * JD-Core Version:    0.6.2
 */