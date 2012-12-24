/*     */ package ee;
/*     */ 
/*     */ import ee.core.GuiIds;
/*     */ import forge.ITextureProvider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.AxisAlignedBB;
/*     */ import net.minecraft.server.BlockContainer;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityItem;
/*     */ import net.minecraft.server.EntityLiving;
/*     */ import net.minecraft.server.IBlockAccess;
/*     */ import net.minecraft.server.IInventory;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.Material;
/*     */ import net.minecraft.server.MathHelper;
/*     */ import net.minecraft.server.TileEntity;
/*     */ import net.minecraft.server.World;
/*     */ import net.minecraft.server.mod_EE;
/*     */ 
/*     */ public class BlockEEPedestal extends BlockContainer
/*     */   implements ITextureProvider
/*     */ {
/*     */   public boolean isActive;
/*     */   private Random furnaceRand;
/*     */ 
/*     */   public BlockEEPedestal(int var1)
/*     */   {
/*  30 */     super(var1, Material.ORE);
/*  31 */     c(EEBlock.eeStone.getHardness(5));
/*  32 */     a(0.2F, 0.15F, 0.2F, 0.8F, 0.7F, 0.8F);
/*  33 */     this.textureId = EEBase.dmBlockSide;
/*     */   }
/*     */ 
/*     */   public String getTextureFile()
/*     */   {
/*  38 */     return "/eqex/eqexterra.png";
/*     */   }
/*     */ 
/*     */   public int c()
/*     */   {
/*  46 */     return mod_EE.pedestalModelID;
/*     */   }
/*     */ 
/*     */   public void setItemName(int var1, String var2)
/*     */   {
/*  51 */     Item var3 = Item.byId[this.id];
/*  52 */     ((ItemBlockEEPedestal)var3).setMetaName(var1, "tile." + var2);
/*     */   }
/*     */ 
/*     */   public void addCreativeItems(ArrayList var1)
/*     */   {
/*  57 */     var1.add(EEBlock.pedestal);
/*     */   }
/*     */ 
/*     */   public int getLightValue(IBlockAccess var1, int var2, int var3, int var4)
/*     */   {
/*  62 */     return isBurning(var1, var2, var3, var4) ? 15 : lightEmission[this.id];
/*     */   }
/*     */ 
/*     */   public boolean isBurning(IBlockAccess var1, int var2, int var3, int var4)
/*     */   {
/*  67 */     TileEntity var5 = var1.getTileEntity(var2, var3, var4);
/*  68 */     return ((var5 instanceof TilePedestal)) && (((TilePedestal)var5).isInterdicting());
/*     */   }
/*     */ 
/*     */   public boolean a()
/*     */   {
/*  77 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean b()
/*     */   {
/*  85 */     return false;
/*     */   }
/*     */ 
/*     */   public TileEntity a_()
/*     */   {
/*  93 */     return new TilePedestal();
/*     */   }
/*     */ 
/*     */   public void postPlace(World var1, int var2, int var3, int var4, EntityLiving var5)
/*     */   {
/* 101 */     EntityHuman var6 = null;
/*     */ 
/* 103 */     if ((var5 instanceof EntityHuman))
/*     */     {
/* 105 */       var6 = (EntityHuman)var5;
/*     */     }
/*     */ 
/* 108 */     int var7 = MathHelper.floor(var5.yaw * 4.0F / 360.0F + 0.5D) & 0x3;
/* 109 */     int var8 = var1.getData(var2, var3, var4) & 0xC;
/*     */ 
/* 111 */     if (var7 == 0)
/*     */     {
/* 113 */       var8 |= 2;
/*     */     }
/*     */ 
/* 116 */     if (var7 == 1)
/*     */     {
/* 118 */       var8 |= 1;
/*     */     }
/*     */ 
/* 121 */     if (var7 == 2)
/*     */     {
/* 123 */       var8 |= 3;
/*     */     }
/*     */ 
/* 126 */     if (var7 == 3)
/*     */     {
/* 128 */       var8 |= 0;
/*     */     }
/*     */ 
/* 131 */     var1.setData(var2, var3, var4, var8);
/*     */ 
/* 133 */     if (var6 != null)
/*     */     {
/* 135 */       TilePedestal var9 = (TilePedestal)EEProxy.getTileEntity(var1, var2, var3, var4, TilePedestal.class);
/* 136 */       var9.setPlayer(var6);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void a(World var1, int var2, int var3, int var4, AxisAlignedBB var5, ArrayList var6)
/*     */   {
/* 146 */     int var7 = var1.getData(var2, var3, var4) & 0x3;
/*     */ 
/* 148 */     if ((var7 >= 0) && (var7 <= 3))
/*     */     {
/* 150 */       a(0.2F, 0.0F, 0.2F, 0.8F, 0.15F, 0.8F);
/* 151 */       super.a(var1, var2, var3, var4, var5, var6);
/* 152 */       a(0.4F, 0.15F, 0.4F, 0.6F, 0.65F, 0.6F);
/* 153 */       super.a(var1, var2, var3, var4, var5, var6);
/* 154 */       a(0.3F, 0.65F, 0.3F, 0.7F, 0.7F, 0.7F);
/* 155 */       super.a(var1, var2, var3, var4, var5, var6);
/*     */     }
/*     */ 
/* 158 */     a(0.2F, 0.0F, 0.2F, 0.8F, 0.7F, 0.8F);
/*     */   }
/*     */ 
/*     */   public void doPhysics(World var1, int var2, int var3, int var4, int var5)
/*     */   {
/* 167 */     TilePedestal var6 = (TilePedestal)var1.getTileEntity(var2, var3, var4);
/*     */ 
/* 169 */     if ((var6 != null) && (var1.isBlockIndirectlyPowered(var2, var3, var4)) && (var6.activationCooldown <= 0))
/*     */     {
/* 171 */       var6.activate();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean interact(World var1, int var2, int var3, int var4, EntityHuman var5)
/*     */   {
/* 181 */     if (EEProxy.isClient(var1))
/*     */     {
/* 183 */       return true;
/*     */     }
/*     */ 
/* 187 */     TilePedestal var6 = (TilePedestal)var1.getTileEntity(var2, var3, var4);
/*     */ 
/* 189 */     if (var6 != null)
/*     */     {
/* 191 */       if (var5.isSneaking())
/*     */       {
/* 193 */         var5.openGui(mod_EE.getInstance(), GuiIds.PEDESTAL, var1, var2, var3, var4);
/*     */       }
/*     */       else
/*     */       {
/* 197 */         var6.activate(var5);
/*     */       }
/*     */     }
/*     */ 
/* 201 */     return true;
/*     */   }
/*     */ 
/*     */   protected int getDropData(int var1)
/*     */   {
/* 210 */     return var1 & 0xC;
/*     */   }
/*     */ 
/*     */   public void remove(World var1, int var2, int var3, int var4)
/*     */   {
/* 218 */     IInventory var5 = (IInventory)var1.getTileEntity(var2, var3, var4);
/*     */ 
/* 220 */     for (int var6 = 0; var6 < var5.getSize(); var6++)
/*     */     {
/* 222 */       ItemStack var7 = var5.getItem(var6);
/*     */ 
/* 224 */       if (var7 != null)
/*     */       {
/* 226 */         float var8 = var1.random.nextFloat() * 0.8F + 0.1F;
/* 227 */         float var9 = var1.random.nextFloat() * 0.8F + 0.1F;
/* 228 */         float var10 = var1.random.nextFloat() * 0.8F + 0.1F;
/*     */ 
/* 230 */         while (var7.count > 0)
/*     */         {
/* 232 */           int var11 = var1.random.nextInt(21) + 10;
/*     */ 
/* 234 */           if (var11 > var7.count)
/*     */           {
/* 236 */             var11 = var7.count;
/*     */           }
/*     */ 
/* 239 */           var7.count -= var11;
/* 240 */           EntityItem var12 = new EntityItem(var1, var2 + var8, var3 + var9, var4 + var10, new ItemStack(var7.id, var11, var7.getData()));
/* 241 */           float var13 = 0.05F;
/* 242 */           var12.motX = ((float)var1.random.nextGaussian() * var13);
/* 243 */           var12.motY = ((float)var1.random.nextGaussian() * var13 + 0.2F);
/* 244 */           var12.motZ = ((float)var1.random.nextGaussian() * var13);
/* 245 */           var1.addEntity(var12);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.BlockEEPedestal
 * JD-Core Version:    0.6.2
 */