/*     */ package ee;
/*     */ 
/*     */ import forge.ISpecialResistance;
/*     */ import forge.ITextureProvider;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.BlockContainer;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityLiving;
/*     */ import net.minecraft.server.IBlockAccess;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.Material;
/*     */ import net.minecraft.server.TileEntity;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class BlockEEStone extends BlockContainer
/*     */   implements ITextureProvider, ISpecialResistance
/*     */ {
/*  21 */   private Class[] tileEntityMap = new Class[15];
/*     */ 
/*     */   protected BlockEEStone(int var1)
/*     */   {
/*  25 */     super(var1, Material.STONE);
/*     */   }
/*     */ 
/*     */   public String getTextureFile()
/*     */   {
/*  30 */     return "/eqex/eqexterra.png";
/*     */   }
/*     */ 
/*     */   public TileEntity a_()
/*     */   {
/*  38 */     return null;
/*     */   }
/*     */ 
/*     */   public int getLightValue(IBlockAccess var1, int var2, int var3, int var4)
/*     */   {
/*  43 */     TileEE var5 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
/*  44 */     return var5 == null ? 0 : lightEmission[this.id] > 0 ? lightEmission[this.id] : var5.getLightValue();
/*     */   }
/*     */ 
/*     */   public void doPhysics(World var1, int var2, int var3, int var4, int var5)
/*     */   {
/*  53 */     TileEE var6 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
/*     */ 
/*  55 */     if (var6 == null)
/*     */     {
/*  57 */       var1.setTypeId(var2, var3, var4, 0);
/*     */     }
/*     */     else
/*     */     {
/*  61 */       var6.onNeighborBlockChange(var5);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void postPlace(World var1, int var2, int var3, int var4, EntityLiving var5)
/*     */   {
/*  70 */     TileEE var6 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
/*     */ 
/*  72 */     if (var6 != null)
/*     */     {
/*  74 */       var6.onBlockPlacedBy(var5);
/*     */     }
/*     */   }
/*     */ 
/*     */   public float getHardness(int var1)
/*     */   {
/*  80 */     switch (var1)
/*     */     {
/*     */     case 0:
/*  83 */       return 1.5F;
/*     */     case 1:
/*  85 */       return 1.5F;
/*     */     case 2:
/*  87 */       return 1.5F;
/*     */     case 3:
/*  89 */       return 3.0F;
/*     */     case 4:
/*  91 */       return 4.5F;
/*     */     case 5:
/*  93 */       return 5.0F;
/*     */     case 6:
/*  95 */       return 5.0F;
/*     */     case 7:
/*  97 */       return 5.0F;
/*     */     case 8:
/*  99 */       return 1000000.0F;
/*     */     case 9:
/* 101 */       return 2000000.0F;
/*     */     case 10:
/* 103 */       return 0.0F;
/*     */     case 11:
/* 105 */       return 0.0F;
/*     */     }
/* 107 */     return 3.0F;
/*     */   }
/*     */ 
/*     */   public int getDropType(int var1, Random var2, int var3)
/*     */   {
/* 116 */     return this.id;
/*     */   }
/*     */ 
/*     */   public int quantityDropped(int var1, int var2, Random var3)
/*     */   {
/* 121 */     return (var1 != 10) && (var1 != 11) ? 1 : 0;
/*     */   }
/*     */ 
/*     */   public int getDropData(int var1)
/*     */   {
/* 129 */     return var1;
/*     */   }
/*     */ 
/*     */   public void wasExploded(World var1, int var2, int var3, int var4)
/*     */   {
/* 137 */     TileEE var5 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
/*     */ 
/* 139 */     if (var5 != null)
/*     */     {
/* 141 */       var5.onBlockDestroyedByExplosion();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addTileEntityMapping(int var1, Class var2)
/*     */   {
/* 147 */     this.tileEntityMap[var1] = var2;
/*     */   }
/*     */ 
/*     */   public int a(int var1, int var2)
/*     */   {
/* 155 */     return ((TileEE)getBlockEntity(var2)).getInventoryTexture(var1);
/*     */   }
/*     */ 
/*     */   public int a(int var1)
/*     */   {
/* 163 */     return a(var1, 0);
/*     */   }
/*     */ 
/*     */   public void remove(World var1, int var2, int var3, int var4)
/*     */   {
/* 171 */     TileEE var5 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
/*     */ 
/* 173 */     if (var5 != null)
/*     */     {
/* 175 */       var5.onBlockRemoval();
/* 176 */       super.remove(var1, var2, var3, var4);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onPlace(World var1, int var2, int var3, int var4)
/*     */   {
/* 185 */     super.onPlace(var1, var2, var3, var4);
/* 186 */     TileEE var5 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
/*     */ 
/* 188 */     if (var5 != null)
/*     */     {
/* 190 */       var5.setDefaultDirection();
/* 191 */       var5.onBlockAdded();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void attack(World var1, int var2, int var3, int var4, EntityHuman var5)
/*     */   {
/* 200 */     TileEE var6 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
/*     */ 
/* 202 */     if (var6 == null)
/*     */     {
/* 204 */       super.attack(var1, var2, var3, var4, var5);
/*     */     }
/*     */     else
/*     */     {
/* 208 */       var6.onBlockClicked(var5);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean interact(World var1, int var2, int var3, int var4, EntityHuman var5)
/*     */   {
/* 218 */     if (var5.isSneaking())
/*     */     {
/* 220 */       return false;
/*     */     }
/*     */ 
/* 224 */     TileEE var6 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
/* 225 */     return var6 == null ? false : var6.onBlockActivated(var5);
/*     */   }
/*     */ 
/*     */   public void addCreativeItems(ArrayList var1)
/*     */   {
/* 231 */     var1.add(EEBlock.collector);
/* 232 */     var1.add(EEBlock.collector2);
/* 233 */     var1.add(EEBlock.collector3);
/* 234 */     var1.add(EEBlock.dmFurnace);
/* 235 */     var1.add(EEBlock.rmFurnace);
/* 236 */     var1.add(EEBlock.relay);
/* 237 */     var1.add(EEBlock.relay2);
/* 238 */     var1.add(EEBlock.relay3);
/* 239 */     var1.add(EEBlock.dmBlock);
/* 240 */     var1.add(EEBlock.rmBlock);
/* 241 */     var1.add(EEBlock.novaCatalyst);
/* 242 */     var1.add(EEBlock.novaCataclysm);
/*     */   }
/*     */ 
/*     */   public TileEntity getBlockEntity(int var1)
/*     */   {
/*     */     try
/*     */     {
/* 249 */       return (TileEntity)this.tileEntityMap[var1].getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
/*     */     }
/*     */     catch (Exception var3) {
/*     */     }
/* 253 */     return null;
/*     */   }
/*     */ 
/*     */   public void setItemName(int var1, String var2)
/*     */   {
/* 259 */     Item var3 = Item.byId[this.id];
/* 260 */     ((ItemBlockEEStone)var3).setMetaName(var1, "tile." + var2);
/*     */   }
/*     */ 
/*     */   public void postBreak(World var1, int var2, int var3, int var4, int var5)
/*     */   {
/* 268 */     if ((var5 != 10) && (var5 != 11))
/*     */     {
/* 270 */       super.postBreak(var1, var2, var3, var4, var5);
/*     */     }
/*     */     else
/*     */     {
/* 274 */       if (var1.isStatic)
/*     */       {
/* 276 */         return;
/*     */       }
/*     */ 
/* 279 */       a(var1, var2, var3, var4, new ItemStack(EEBlock.eeStone.id, 1, var5));
/*     */     }
/*     */   }
/*     */ 
/*     */   public float getSpecialExplosionResistance(World var1, int var2, int var3, int var4, double var5, double var7, double var9, Entity var11)
/*     */   {
/* 285 */     return getHardness(var1.getData(var2, var3, var4));
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.BlockEEStone
 * JD-Core Version:    0.6.2
 */