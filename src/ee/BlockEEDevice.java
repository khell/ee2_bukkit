/*     */ package ee;
/*     */ 
/*     */ import forge.ITextureProvider;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.BlockContainer;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityLiving;
/*     */ import net.minecraft.server.IBlockAccess;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.Material;
/*     */ import net.minecraft.server.TileEntity;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class BlockEEDevice extends BlockContainer
/*     */   implements ITextureProvider
/*     */ {
/*  18 */   private Class[] tileEntityMap = new Class[15];
/*     */ 
/*     */   protected BlockEEDevice(int var1)
/*     */   {
/*  22 */     super(var1, Material.STONE);
/*  23 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.2F, 1.0F);
/*     */   }
/*     */ 
/*     */   public String getTextureFile()
/*     */   {
/*  28 */     return "/eqex/eqexterra.png";
/*     */   }
/*     */ 
/*     */   public TileEntity a_()
/*     */   {
/*  36 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean a()
/*     */   {
/*  45 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean b()
/*     */   {
/*  53 */     return false;
/*     */   }
/*     */ 
/*     */   public int getLightValue(IBlockAccess var1, int var2, int var3, int var4)
/*     */   {
/*  58 */     TileEE var5 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
/*  59 */     return var5 == null ? lightEmission[this.id] : var5.getLightValue();
/*     */   }
/*     */ 
/*     */   public void doPhysics(World var1, int var2, int var3, int var4, int var5)
/*     */   {
/*  68 */     TileEE var6 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
/*     */ 
/*  70 */     if (var6 == null)
/*     */     {
/*  72 */       var1.setTypeId(var2, var3, var4, 0);
/*     */     }
/*     */     else
/*     */     {
/*  76 */       var6.onNeighborBlockChange(var5);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void postPlace(World var1, int var2, int var3, int var4, EntityLiving var5)
/*     */   {
/*  85 */     TileEE var6 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
/*     */ 
/*  87 */     if (var6 != null)
/*     */     {
/*  89 */       var6.onBlockPlacedBy(var5);
/*     */     }
/*     */   }
/*     */ 
/*     */   public float getHardness(int var1)
/*     */   {
/*  95 */     switch (var1)
/*     */     {
/*     */     case 0:
/*  98 */       return 1.5F;
/*     */     case 1:
/* 100 */       return 3.5F;
/*     */     }
/* 102 */     return 3.0F;
/*     */   }
/*     */ 
/*     */   public int getDropType(int var1, Random var2, int var3)
/*     */   {
/* 111 */     return this.id;
/*     */   }
/*     */ 
/*     */   public int a(Random var1)
/*     */   {
/* 119 */     return 1;
/*     */   }
/*     */ 
/*     */   public int getDropData(int var1)
/*     */   {
/* 127 */     return var1;
/*     */   }
/*     */ 
/*     */   public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5)
/*     */   {
/* 132 */     TileEE var6 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
/*     */ 
/* 134 */     if (var6 != null)
/*     */     {
/* 136 */       var6.randomDisplayTick(var5);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addTileEntityMapping(int var1, Class var2)
/*     */   {
/* 142 */     this.tileEntityMap[var1] = var2;
/*     */   }
/*     */ 
/*     */   public int a(int var1, int var2)
/*     */   {
/* 150 */     return ((TileEE)getBlockEntity(var2)).getInventoryTexture(var1);
/*     */   }
/*     */ 
/*     */   public int a(int var1)
/*     */   {
/* 158 */     return a(var1, 0);
/*     */   }
/*     */ 
/*     */   public int getBlockTexture(IBlockAccess var1, int var2, int var3, int var4, int var5)
/*     */   {
/* 163 */     TileEE var6 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
/* 164 */     return var6 == null ? 0 : var6.getTextureForSide(var5);
/*     */   }
/*     */ 
/*     */   public void remove(World var1, int var2, int var3, int var4)
/*     */   {
/* 172 */     TileEE var5 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
/*     */ 
/* 174 */     if (var5 != null)
/*     */     {
/* 176 */       var5.onBlockRemoval();
/* 177 */       super.remove(var1, var2, var3, var4);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onPlace(World var1, int var2, int var3, int var4)
/*     */   {
/* 186 */     super.onPlace(var1, var2, var3, var4);
/* 187 */     TileEE var5 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
/*     */ 
/* 189 */     if (var5 != null)
/*     */     {
/* 191 */       var5.setDefaultDirection();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean interact(World var1, int var2, int var3, int var4, EntityHuman var5)
/*     */   {
/* 201 */     TileEE var6 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
/* 202 */     return var6 == null ? false : var6.onBlockActivated(var5);
/*     */   }
/*     */ 
/*     */   public void addCreativeItems(ArrayList var1)
/*     */   {
/* 207 */     var1.add(EEBlock.transTablet);
/*     */   }
/*     */ 
/*     */   public TileEntity getBlockEntity(int var1)
/*     */   {
/*     */     try
/*     */     {
/* 214 */       return (TileEntity)this.tileEntityMap[var1].getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
/*     */     }
/*     */     catch (Exception var3) {
/*     */     }
/* 218 */     return null;
/*     */   }
/*     */ 
/*     */   public void setItemName(int var1, String var2)
/*     */   {
/* 224 */     Item var3 = Item.byId[this.id];
/* 225 */     ((ItemBlockEEDevice)var3).setMetaName(var1, "tile." + var2);
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.BlockEEDevice
 * JD-Core Version:    0.6.2
 */