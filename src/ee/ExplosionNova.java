/*     */ package ee;
/*     */ 
/*     */ import forge.ISpecialResistance;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.server.AxisAlignedBB;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.BlockFire;
/*     */ import net.minecraft.server.ChunkPosition;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.MathHelper;
/*     */ import net.minecraft.server.Vec3D;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class ExplosionNova
/*     */ {
/*  22 */   public boolean isFlaming = false;
/*     */   private Random ExplosionRNG;
/*     */   private World worldObj;
/*     */   private ItemStack[] dropList;
/*     */   public double explosionX;
/*     */   public double explosionY;
/*     */   public double explosionZ;
/*     */   public Entity exploder;
/*     */   public float explosionSize;
/*     */   public Set destroyedBlockPositions;
/*     */   private EntityHuman player;
/*     */ 
/*     */   public ExplosionNova(World var1, EntityHuman var2, double var3, double var5, double var7, float var9)
/*     */   {
/*  36 */     this.player = var2;
/*  37 */     this.ExplosionRNG = new Random();
/*  38 */     this.destroyedBlockPositions = new HashSet();
/*  39 */     this.dropList = new ItemStack[64];
/*  40 */     this.worldObj = var1;
/*  41 */     this.exploder = var2;
/*  42 */     this.explosionSize = var9;
/*  43 */     this.explosionX = var3;
/*  44 */     this.explosionY = var5;
/*  45 */     this.explosionZ = var7;
/*     */   }
/*     */ 
/*     */   public void doExplosionA()
/*     */   {
/*  50 */     float var1 = this.explosionSize;
/*  51 */     byte var2 = 16;
/*     */ 
/*  59 */     for (int var3 = 0; var3 < var2; var3++)
/*     */     {
/*  61 */       for (int var4 = 0; var4 < var2; var4++)
/*     */       {
/*  63 */         for (int var5 = 0; var5 < var2; var5++)
/*     */         {
/*  65 */           if ((var3 == 0) || (var3 == var2 - 1) || (var4 == 0) || (var4 == var2 - 1) || (var5 == 0) || (var5 == var2 - 1))
/*     */           {
/*  67 */             double var6 = var3 / (var2 - 1.0F) * 2.0F - 1.0F;
/*  68 */             double var8 = var4 / (var2 - 1.0F) * 2.0F - 1.0F;
/*  69 */             double var10 = var5 / (var2 - 1.0F) * 2.0F - 1.0F;
/*  70 */             double var12 = Math.sqrt(var6 * var6 + var8 * var8 + var10 * var10);
/*  71 */             var6 /= var12;
/*  72 */             var8 /= var12;
/*  73 */             var10 /= var12;
/*  74 */             float var14 = this.explosionSize * (0.7F + this.worldObj.random.nextFloat() * 0.8F);
/*  75 */             double var15 = this.explosionX;
/*  76 */             double var17 = this.explosionY;
/*  77 */             double var19 = this.explosionZ;
/*     */ 
/*  79 */             for (float var21 = 0.6F; var14 > 0.0F; var14 -= var21 * 0.75F)
/*     */             {
/*  81 */               int var22 = MathHelper.floor(var15);
/*  82 */               int var23 = MathHelper.floor(var17);
/*  83 */               int var24 = MathHelper.floor(var19);
/*  84 */               int var25 = this.worldObj.getTypeId(var22, var23, var24);
/*     */ 
/*  86 */               if (var25 > 0)
/*     */               {
/*  88 */                 if ((Block.byId[var25] instanceof ISpecialResistance))
/*     */                 {
/*  90 */                   ISpecialResistance var26 = (ISpecialResistance)Block.byId[var25];
/*  91 */                   var14 -= (var26.getSpecialExplosionResistance(this.worldObj, var22, var23, var24, this.explosionX, this.explosionY, this.explosionZ, this.exploder) + 0.3F) * var21;
/*     */                 }
/*     */                 else
/*     */                 {
/*  95 */                   var14 -= (Block.byId[var25].a(this.exploder) + 0.3F) * var21;
/*     */                 }
/*     */               }
/*     */ 
/*  99 */               if ((var14 > 0.0F) || (Block.byId[var25].a(this.exploder) < 30.0F))
/*     */               {
/* 101 */                 this.destroyedBlockPositions.add(new ChunkPosition(var22, var23, var24));
/*     */               }
/*     */ 
/* 104 */               var15 += var6 * var21;
/* 105 */               var17 += var8 * var21;
/* 106 */               var19 += var10 * var21;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 113 */     this.explosionSize *= 1.7F;
/* 114 */     int var3 = MathHelper.floor(this.explosionX - this.explosionSize - 1.0D);
/* 115 */     int var4 = MathHelper.floor(this.explosionX + this.explosionSize + 1.0D);
/* 116 */     int var5 = MathHelper.floor(this.explosionY - this.explosionSize - 1.0D);
/* 117 */     int var29 = MathHelper.floor(this.explosionY + this.explosionSize + 1.0D);
/* 118 */     int var7 = MathHelper.floor(this.explosionZ - this.explosionSize - 1.0D);
/* 119 */     int var30 = MathHelper.floor(this.explosionZ + this.explosionSize + 1.0D);
/* 120 */     List var9 = this.worldObj.getEntities(this.exploder, AxisAlignedBB.b(var3, var5, var7, var4, var29, var30));
/* 121 */     Vec3D var31 = Vec3D.create(this.explosionX, this.explosionY, this.explosionZ);
/*     */ 
/* 123 */     for (int var11 = 0; var11 < var9.size(); var11++)
/*     */     {
/* 125 */       Entity var32 = (Entity)var9.get(var11);
/* 126 */       double var13 = var32.f(this.explosionX, this.explosionY, this.explosionZ) / this.explosionSize;
/*     */ 
/* 128 */       if (var13 <= 1.0D)
/*     */       {
/* 130 */         double var15 = var32.locX - this.explosionX;
/* 131 */         double var17 = var32.locY - this.explosionY;
/* 132 */         double var19 = var32.locZ - this.explosionZ;
/* 133 */         double var40 = MathHelper.sqrt(var15 * var15 + var17 * var17 + var19 * var19);
/* 134 */         var15 /= var40;
/* 135 */         var17 /= var40;
/* 136 */         var19 /= var40;
/* 137 */         double var39 = this.worldObj.a(var31, var32.boundingBox);
/* 138 */         double var41 = (1.0D - var13) * var39;
/* 139 */         var32.motX += var15 * var41;
/* 140 */         var32.motY += var17 * var41;
/* 141 */         var32.motZ += var19 * var41;
/*     */       }
/*     */     }
/*     */ 
/* 145 */     this.explosionSize = var1;
/* 146 */     ArrayList var34 = new ArrayList();
/* 147 */     var34.addAll(this.destroyedBlockPositions);
/*     */ 
/* 149 */     if (this.isFlaming)
/*     */     {
/* 151 */       for (int var33 = var34.size() - 1; var33 >= 0; var33--)
/*     */       {
/* 153 */         ChunkPosition var35 = (ChunkPosition)var34.get(var33);
/* 154 */         int var37 = var35.x;
/* 155 */         int var36 = var35.y;
/* 156 */         int var16 = var35.z;
/* 157 */         int var38 = this.worldObj.getTypeId(var37, var36, var16);
/* 158 */         int var18 = this.worldObj.getTypeId(var37, var36 - 1, var16);
/*     */ 
/* 160 */         if ((var38 == 0) && (Block.n[var18]) && (this.ExplosionRNG.nextInt(3) == 0))
/*     */         {
/* 162 */           this.worldObj.setTypeId(var37, var36, var16, Block.FIRE.id);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doExplosionB()
/*     */   {
/* 170 */     for (int var1 = 0; var1 <= 63; var1++)
/*     */     {
/* 172 */       this.dropList[var1] = null;
/*     */     }
/*     */ 
/* 175 */     this.worldObj.makeSound(this.explosionX, this.explosionY, this.explosionZ, "nova", 4.0F, (1.0F + (this.worldObj.random.nextFloat() - this.worldObj.random.nextFloat()) * 0.2F) * 0.7F);
/* 176 */     this.worldObj.a("hugeexplosion", this.explosionX, this.explosionY, this.explosionZ, 0.0D, 0.0D, 0.0D);
/* 177 */     ArrayList var25 = new ArrayList();
/* 178 */     var25.addAll(this.destroyedBlockPositions);
/*     */ 
/* 180 */     for (int var2 = var25.size() - 1; var2 >= 0; var2--)
/*     */     {
/* 182 */       ChunkPosition var3 = (ChunkPosition)var25.get(var2);
/* 183 */       int var4 = var3.x;
/* 184 */       int var5 = var3.y;
/* 185 */       int var6 = var3.z;
/* 186 */       int var7 = this.worldObj.getTypeId(var4, var5, var6);
/*     */ 
/* 189 */       for (int var8 = 0; var8 < 1; var8++)
/*     */       {
/* 191 */         double var9 = var4 + this.worldObj.random.nextFloat();
/* 192 */         double var11 = var5 + this.worldObj.random.nextFloat();
/* 193 */         double var13 = var6 + this.worldObj.random.nextFloat();
/* 194 */         double var15 = var9 - this.explosionX;
/* 195 */         double var17 = var11 - this.explosionY;
/* 196 */         double var19 = var13 - this.explosionZ;
/* 197 */         double var21 = MathHelper.sqrt(var15 * var15 + var17 * var17 + var19 * var19);
/* 198 */         var15 /= var21;
/* 199 */         var17 /= var21;
/* 200 */         var19 /= var21;
/* 201 */         double var23 = 0.5D / (var21 / this.explosionSize + 0.1D);
/* 202 */         var23 *= (this.worldObj.random.nextFloat() * this.worldObj.random.nextFloat() + 0.3F);
/* 203 */         var15 *= var23;
/* 204 */         var17 *= var23;
/* 205 */         var19 *= var23;
/*     */ 
/* 207 */         if (this.worldObj.random.nextInt(8) == 0)
/*     */         {
/* 209 */           this.worldObj.a("explode", (var9 + this.explosionX * 1.0D) / 2.0D, (var11 + this.explosionY * 1.0D) / 2.0D, (var13 + this.explosionZ * 1.0D) / 2.0D, var15, var17, var19);
/*     */         }
/*     */ 
/* 212 */         if (this.worldObj.random.nextInt(8) == 0)
/*     */         {
/* 214 */           this.worldObj.a("smoke", var9, var11, var13, var15, var17, var19);
/*     */         }
/*     */       }
/*     */ 
/* 218 */       if (var7 > 0)
/*     */       {
/* 220 */         int var8 = this.worldObj.getData(var4, var5, var6);
/* 221 */         ArrayList var27 = Block.byId[var7].getBlockDropped(this.worldObj, var4, var5, var6, var8, 0);
/* 222 */         Iterator var10 = var27.iterator();
/*     */ 
/* 224 */         while (var10.hasNext())
/*     */         {
/* 226 */           ItemStack var28 = (ItemStack)var10.next();
/*     */ 
/* 228 */           for (int var12 = 0; var12 < this.dropList.length; var12++)
/*     */           {
/* 230 */             if (this.dropList[var12] == null)
/*     */             {
/* 232 */               this.dropList[var12] = var28.cloneItemStack();
/* 233 */               var28 = null;
/*     */             }
/* 235 */             else if ((this.dropList[var12].doMaterialsMatch(var28)) && (this.dropList[var12].count < this.dropList[var12].getMaxStackSize()))
/*     */             {
/* 237 */               while ((this.dropList[var12].count < this.dropList[var12].getMaxStackSize()) && (var28 != null))
/*     */               {
/* 239 */                 this.dropList[var12].count += 1;
/* 240 */                 var28.count -= 1;
/*     */ 
/* 242 */                 if (var28.count == 0)
/*     */                 {
/* 244 */                   var28 = null;
/*     */                 }
/*     */               }
/*     */             }
/*     */ 
/* 249 */             if (var28 == null)
/*     */             {
/*     */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 256 */         Block.byId[var7].wasExploded(this.worldObj, var4, var5, var6);
/* 257 */         this.worldObj.setTypeId(var4, var5, var6, 0);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 263 */     if (this.exploder != null)
/*     */     {
/* 265 */       if (this.dropList != null)
/*     */       {
/* 267 */         EntityLootBall var26 = new EntityLootBall(this.worldObj, EEBase.playerX(this.player), EEBase.playerY(this.player), EEBase.playerZ(this.player), this.dropList);
/*     */ 
/* 269 */         if (var26 != null)
/*     */         {
/* 271 */           this.worldObj.addEntity(var26);
/*     */         }
/*     */       }
/*     */     }
/* 275 */     else if (this.dropList != null)
/*     */     {
/* 277 */       EntityLootBall var26 = new EntityLootBall(this.worldObj, this.explosionX, this.explosionY, this.explosionZ, this.dropList);
/*     */ 
/* 279 */       if (var26 != null)
/*     */       {
/* 281 */         this.worldObj.addEntity(var26);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ExplosionNova
 * JD-Core Version:    0.6.2
 */