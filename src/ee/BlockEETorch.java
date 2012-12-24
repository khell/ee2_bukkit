/*     */ package ee;
/*     */ 
/*     */ import forge.ITextureProvider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.AxisAlignedBB;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityArrow;
/*     */ import net.minecraft.server.EntityFireball;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityMonster;
/*     */ import net.minecraft.server.IBlockAccess;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.Material;
/*     */ import net.minecraft.server.MovingObjectPosition;
/*     */ import net.minecraft.server.Vec3D;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class BlockEETorch extends Block
/*     */   implements ITextureProvider
/*     */ {
/*     */   private int powerCycle;
/*     */ 
/*     */   protected BlockEETorch(int var1)
/*     */   {
/*  28 */     super(var1, Material.ORIENTABLE);
/*  29 */     a(true);
/*  30 */     this.textureId = 16;
/*     */   }
/*     */ 
/*     */   public AxisAlignedBB e(World var1, int var2, int var3, int var4)
/*     */   {
/*  39 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean a()
/*     */   {
/*  48 */     return false;
/*     */   }
/*     */ 
/*     */   public int getLightValue(IBlockAccess var1, int var2, int var3, int var4)
/*     */   {
/*  53 */     return 15;
/*     */   }
/*     */ 
/*     */   public boolean b()
/*     */   {
/*  61 */     return false;
/*     */   }
/*     */ 
/*     */   public int c()
/*     */   {
/*  69 */     return 2;
/*     */   }
/*     */ 
/*     */   public void setItemName(int var1, String var2)
/*     */   {
/*  74 */     Item var3 = Item.byId[this.id];
/*  75 */     ((ItemBlockEETorch)var3).setMetaName(var1, "tile." + var2);
/*     */   }
/*     */ 
/*     */   public void addCreativeItems(ArrayList var1)
/*     */   {
/*  80 */     var1.add(EEBlock.iTorch);
/*     */   }
/*     */ 
/*     */   public String getTextureFile()
/*     */   {
/*  85 */     return "/eqex/eqexterra.png";
/*     */   }
/*     */ 
/*     */   private boolean canPlaceTorchOn(World var1, int var2, int var3, int var4)
/*     */   {
/*  90 */     return (var1.e(var2, var3, var4)) || (var1.getTypeId(var2, var3, var4) == Block.FENCE.id);
/*     */   }
/*     */ 
/*     */   public boolean canPlace(World var1, int var2, int var3, int var4)
/*     */   {
/*  98 */     return var1.e(var2, var3, var4 + 1) ? true : var1.e(var2, var3, var4 - 1) ? true : var1.e(var2 + 1, var3, var4) ? true : var1.e(var2 - 1, var3, var4) ? true : canPlaceTorchOn(var1, var2, var3 - 1, var4);
/*     */   }
/*     */ 
/*     */   public void postPlace(World var1, int var2, int var3, int var4, int var5)
/*     */   {
/* 107 */     var1.c(var2, var3, var4, this.id, 1);
/* 108 */     int var6 = var1.getData(var2, var3, var4);
/*     */ 
/* 110 */     if ((var5 == 1) && (canPlaceTorchOn(var1, var2, var3 - 1, var4)))
/*     */     {
/* 112 */       var6 = 5;
/*     */     }
/*     */ 
/* 115 */     if ((var5 == 2) && (var1.e(var2, var3, var4 + 1)))
/*     */     {
/* 117 */       var6 = 4;
/*     */     }
/*     */ 
/* 120 */     if ((var5 == 3) && (var1.e(var2, var3, var4 - 1)))
/*     */     {
/* 122 */       var6 = 3;
/*     */     }
/*     */ 
/* 125 */     if ((var5 == 4) && (var1.e(var2 + 1, var3, var4)))
/*     */     {
/* 127 */       var6 = 2;
/*     */     }
/*     */ 
/* 130 */     if ((var5 == 5) && (var1.e(var2 - 1, var3, var4)))
/*     */     {
/* 132 */       var6 = 1;
/*     */     }
/*     */ 
/* 135 */     var1.setData(var2, var3, var4, var6);
/*     */   }
/*     */ 
/*     */   public void a(World var1, int var2, int var3, int var4, Random var5)
/*     */   {
/* 143 */     super.a(var1, var2, var3, var4, var5);
/*     */ 
/* 145 */     if (this.powerCycle > 0)
/*     */     {
/* 147 */       doInterdiction(var1, var2, var3, var4, true);
/* 148 */       this.powerCycle -= 1;
/*     */     }
/*     */ 
/* 151 */     doInterdiction(var1, var2, var3, var4, false);
/*     */ 
/* 153 */     if (var1.getData(var2, var3, var4) == 0)
/*     */     {
/* 155 */       onPlace(var1, var2, var3, var4);
/*     */     }
/*     */ 
/* 158 */     var1.c(var2, var3, var4, this.id, 1);
/*     */   }
/*     */ 
/*     */   public void onPlace(World var1, int var2, int var3, int var4)
/*     */   {
/* 166 */     var1.c(var2, var3, var4, this.id, 1);
/*     */ 
/* 168 */     if (var1.e(var2 - 1, var3, var4))
/*     */     {
/* 170 */       var1.setData(var2, var3, var4, 1);
/*     */     }
/* 172 */     else if (var1.e(var2 + 1, var3, var4))
/*     */     {
/* 174 */       var1.setData(var2, var3, var4, 2);
/*     */     }
/* 176 */     else if (var1.e(var2, var3, var4 - 1))
/*     */     {
/* 178 */       var1.setData(var2, var3, var4, 3);
/*     */     }
/* 180 */     else if (var1.e(var2, var3, var4 + 1))
/*     */     {
/* 182 */       var1.setData(var2, var3, var4, 4);
/*     */     }
/* 184 */     else if (canPlaceTorchOn(var1, var2, var3 - 1, var4))
/*     */     {
/* 186 */       var1.setData(var2, var3, var4, 5);
/*     */     }
/*     */ 
/* 189 */     dropTorchIfCantStay(var1, var2, var3, var4);
/*     */   }
/*     */ 
/*     */   public void doPhysics(World var1, int var2, int var3, int var4, int var5)
/*     */   {
/* 198 */     var1.c(var2, var3, var4, this.id, 1);
/*     */ 
/* 201 */     if (var1.isBlockIndirectlyPowered(var2, var3, var4))
/*     */     {
/* 203 */       for (int var6 = 0; var6 <= 2; var6++)
/*     */       {
/* 205 */         doInterdiction(var1, var2, var3, var4, true);
/*     */       }
/*     */ 
/* 208 */       this.powerCycle = 16;
/*     */     }
/*     */ 
/* 211 */     if (dropTorchIfCantStay(var1, var2, var3, var4))
/*     */     {
/* 213 */       int var6 = var1.getData(var2, var3, var4);
/* 214 */       boolean var7 = false;
/*     */ 
/* 216 */       if ((!var1.e(var2 - 1, var3, var4)) && (var6 == 1))
/*     */       {
/* 218 */         var7 = true;
/*     */       }
/*     */ 
/* 221 */       if ((!var1.e(var2 + 1, var3, var4)) && (var6 == 2))
/*     */       {
/* 223 */         var7 = true;
/*     */       }
/*     */ 
/* 226 */       if ((!var1.e(var2, var3, var4 - 1)) && (var6 == 3))
/*     */       {
/* 228 */         var7 = true;
/*     */       }
/*     */ 
/* 231 */       if ((!var1.e(var2, var3, var4 + 1)) && (var6 == 4))
/*     */       {
/* 233 */         var7 = true;
/*     */       }
/*     */ 
/* 236 */       if ((!canPlaceTorchOn(var1, var2, var3 - 1, var4)) && (var6 == 5))
/*     */       {
/* 238 */         var7 = true;
/*     */       }
/*     */ 
/* 241 */       if (var7)
/*     */       {
/* 243 */         b(var1, var2, var3, var4, var1.getData(var2, var3, var4), 1);
/* 244 */         var1.setTypeId(var2, var3, var4, 0);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean dropTorchIfCantStay(World var1, int var2, int var3, int var4)
/*     */   {
/* 251 */     if (!canPlace(var1, var2, var3, var4))
/*     */     {
/* 253 */       b(var1, var2, var3, var4, var1.getData(var2, var3, var4), 1);
/* 254 */       var1.setTypeId(var2, var3, var4, 0);
/* 255 */       return false;
/*     */     }
/*     */ 
/* 259 */     return true;
/*     */   }
/*     */ 
/*     */   public MovingObjectPosition a(World var1, int var2, int var3, int var4, Vec3D var5, Vec3D var6)
/*     */   {
/* 269 */     int var7 = var1.getData(var2, var3, var4) & 0x7;
/* 270 */     float var8 = 0.15F;
/*     */ 
/* 272 */     if (var7 == 1)
/*     */     {
/* 274 */       a(0.0F, 0.2F, 0.5F - var8, var8 * 2.0F, 0.8F, 0.5F + var8);
/*     */     }
/* 276 */     else if (var7 == 2)
/*     */     {
/* 278 */       a(1.0F - var8 * 2.0F, 0.2F, 0.5F - var8, 1.0F, 0.8F, 0.5F + var8);
/*     */     }
/* 280 */     else if (var7 == 3)
/*     */     {
/* 282 */       a(0.5F - var8, 0.2F, 0.0F, 0.5F + var8, 0.8F, var8 * 2.0F);
/*     */     }
/* 284 */     else if (var7 == 4)
/*     */     {
/* 286 */       a(0.5F - var8, 0.2F, 1.0F - var8 * 2.0F, 0.5F + var8, 0.8F, 1.0F);
/*     */     }
/*     */     else
/*     */     {
/* 290 */       float var9 = 0.1F;
/* 291 */       a(0.5F - var9, 0.0F, 0.5F - var9, 0.5F + var9, 0.6F, 0.5F + var9);
/*     */     }
/*     */ 
/* 294 */     return super.a(var1, var2, var3, var4, var5, var6);
/*     */   }
/*     */ 
/*     */   public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5)
/*     */   {
/* 299 */     var1.c(var2, var3, var4, this.id, 1);
/* 300 */     int var6 = var1.getData(var2, var3, var4);
/* 301 */     double var7 = var2 + 0.5F;
/* 302 */     double var9 = var3 + 0.7F;
/* 303 */     double var11 = var4 + 0.5F;
/* 304 */     double var13 = 0.219999998807907D;
/* 305 */     double var15 = 0.2700000107288361D;
/*     */ 
/* 307 */     if (var6 == 1)
/*     */     {
/* 309 */       var1.a("smoke", var7 - var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
/*     */     }
/* 311 */     else if (var6 == 2)
/*     */     {
/* 313 */       var1.a("smoke", var7 + var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
/*     */     }
/* 315 */     else if (var6 == 3)
/*     */     {
/* 317 */       var1.a("smoke", var7, var9 + var13, var11 - var15, 0.0D, 0.0D, 0.0D);
/*     */     }
/* 319 */     else if (var6 == 4)
/*     */     {
/* 321 */       var1.a("smoke", var7, var9 + var13, var11 + var15, 0.0D, 0.0D, 0.0D);
/*     */     }
/*     */     else
/*     */     {
/* 325 */       var1.a("smoke", var7, var9, var11, 0.0D, 0.0D, 0.0D);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doInterdiction(World var1, int var2, int var3, int var4, boolean var5)
/*     */   {
/* 331 */     float var6 = 5.0F;
/* 332 */     List var7 = var1.a(EntityMonster.class, AxisAlignedBB.b(var2 - var6, var3 - var6, var4 - var6, var2 + var6, var3 + var6, var4 + var6));
/* 333 */     Iterator var9 = var7.iterator();
/*     */ 
/* 335 */     while (var9.hasNext())
/*     */     {
/* 337 */       Entity var8 = (Entity)var9.next();
/* 338 */       PushEntities(var8, var2, var3, var4);
/*     */     }
/*     */ 
/* 341 */     List var15 = var1.a(EntityArrow.class, AxisAlignedBB.b(var2 - var6, var3 - var6, var4 - var6, var2 + var6, var3 + var6, var4 + var6));
/* 342 */     Iterator var11 = var15.iterator();
/*     */ 
/* 344 */     while (var11.hasNext())
/*     */     {
/* 346 */       Entity var10 = (Entity)var11.next();
/* 347 */       PushEntities(var10, var2, var3, var4);
/*     */     }
/*     */ 
/* 350 */     List var14 = var1.a(EntityFireball.class, AxisAlignedBB.b(var2 - var6, var3 - var6, var4 - var6, var2 + var6, var3 + var6, var4 + var6));
/* 351 */     Iterator var13 = var14.iterator();
/*     */ 
/* 353 */     while (var13.hasNext())
/*     */     {
/* 355 */       Entity var12 = (Entity)var13.next();
/* 356 */       PushEntities(var12, var2, var3, var4);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void PushEntities(Entity var1, int var2, int var3, int var4)
/*     */   {
/* 362 */     if (!(var1 instanceof EntityHuman))
/*     */     {
/* 364 */       double var6 = var2 - var1.locX;
/* 365 */       double var8 = var3 - var1.locY;
/* 366 */       double var10 = var4 - var1.locZ;
/* 367 */       double var12 = var6 * var6 + var8 * var8 + var10 * var10;
/* 368 */       var12 *= var12;
/*     */ 
/* 370 */       if (var12 <= Math.pow(6.0D, 4.0D))
/*     */       {
/* 372 */         double var14 = -(var6 * 0.01999999955296516D / var12) * Math.pow(6.0D, 3.0D);
/* 373 */         double var16 = -(var8 * 0.01999999955296516D / var12) * Math.pow(6.0D, 3.0D);
/* 374 */         double var18 = -(var10 * 0.01999999955296516D / var12) * Math.pow(6.0D, 3.0D);
/*     */ 
/* 376 */         if (var14 > 0.0D)
/*     */         {
/* 378 */           var14 = 0.22D;
/*     */         }
/* 380 */         else if (var14 < 0.0D)
/*     */         {
/* 382 */           var14 = -0.22D;
/*     */         }
/*     */ 
/* 385 */         if (var16 > 0.2D)
/*     */         {
/* 387 */           var16 = 0.12D;
/*     */         }
/* 389 */         else if (var16 < -0.1D)
/*     */         {
/* 391 */           var16 = 0.12D;
/*     */         }
/*     */ 
/* 394 */         if (var18 > 0.0D)
/*     */         {
/* 396 */           var18 = 0.22D;
/*     */         }
/* 398 */         else if (var18 < 0.0D)
/*     */         {
/* 400 */           var18 = -0.22D;
/*     */         }
/*     */ 
/* 403 */         var1.motX += var14;
/* 404 */         var1.motY += var16;
/* 405 */         var1.motZ += var18;
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.BlockEETorch
 * JD-Core Version:    0.6.2
 */