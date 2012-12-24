/*     */ package ee;
/*     */ 
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
/*     */ import net.minecraft.server.DamageSource;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.MathHelper;
/*     */ import net.minecraft.server.Vec3D;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class Combustion
/*     */ {
/*  22 */   public boolean isFlaming = false;
/*  23 */   private Random ExplosionRNG = new Random();
/*     */   private World worldObj;
/*  25 */   private ItemStack[] dropList = new ItemStack[64];
/*     */   public double explosionX;
/*     */   public double explosionY;
/*     */   public double explosionZ;
/*     */   public EntityHuman exploder;
/*     */   public float explosionSize;
/*  31 */   public Set destroyedBlockPositions = new HashSet();
/*     */ 
/*     */   public Combustion(World var1, EntityHuman var2, double var3, double var5, double var7, float var9)
/*     */   {
/*  35 */     this.worldObj = var1;
/*  36 */     this.exploder = var2;
/*  37 */     this.explosionSize = var9;
/*  38 */     this.explosionX = var3;
/*  39 */     this.explosionY = var5;
/*  40 */     this.explosionZ = var7;
/*     */   }
/*     */ 
/*     */   public void doExplosionA()
/*     */   {
/*  45 */     float var1 = this.explosionSize;
/*  46 */     byte var2 = 16;
/*     */ 
/*  54 */     for (int var3 = 0; var3 < var2; var3++)
/*     */     {
/*  56 */       for (int var4 = 0; var4 < var2; var4++)
/*     */       {
/*  58 */         for (int var5 = 0; var5 < var2; var5++)
/*     */         {
/*  60 */           if ((var3 == 0) || (var3 == var2 - 1) || (var4 == 0) || (var4 == var2 - 1) || (var5 == 0) || (var5 == var2 - 1))
/*     */           {
/*  62 */             double var6 = var3 / (var2 - 1.0F) * 2.0F - 1.0F;
/*  63 */             double var8 = var4 / (var2 - 1.0F) * 2.0F - 1.0F;
/*  64 */             double var10 = var5 / (var2 - 1.0F) * 2.0F - 1.0F;
/*  65 */             double var12 = Math.sqrt(var6 * var6 + var8 * var8 + var10 * var10);
/*  66 */             var6 /= var12;
/*  67 */             var8 /= var12;
/*  68 */             var10 /= var12;
/*  69 */             float var14 = this.explosionSize * (0.7F + this.worldObj.random.nextFloat() * 0.6F);
/*  70 */             double var15 = this.explosionX;
/*  71 */             double var17 = this.explosionY;
/*  72 */             double var19 = this.explosionZ;
/*     */ 
/*  74 */             for (float var21 = 0.3F; var14 > 0.0F; var14 -= var21 * 0.75F)
/*     */             {
/*  76 */               int var22 = MathHelper.floor(var15);
/*  77 */               int var23 = MathHelper.floor(var17);
/*  78 */               int var24 = MathHelper.floor(var19);
/*  79 */               int var25 = this.worldObj.getTypeId(var22, var23, var24);
/*     */ 
/*  81 */               if ((var25 > 0) && (Block.byId[var25].a(this.exploder) > 12.0F))
/*     */               {
/*  83 */                 var14 -= Block.byId[var25].a(this.exploder) * var21;
/*     */               }
/*     */ 
/*  86 */               if (var14 > 0.0F)
/*     */               {
/*  88 */                 this.destroyedBlockPositions.add(new ChunkPosition(var22, var23, var24));
/*     */               }
/*     */ 
/*  91 */               var15 += var6 * var21;
/*  92 */               var17 += var8 * var21;
/*  93 */               var19 += var10 * var21;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 100 */     this.explosionSize *= 2.0F;
/* 101 */     int var3 = MathHelper.floor(this.explosionX - this.explosionSize - 1.0D);
/* 102 */     int var4 = MathHelper.floor(this.explosionX + this.explosionSize + 1.0D);
/* 103 */     int var5 = MathHelper.floor(this.explosionY - this.explosionSize - 1.0D);
/* 104 */     int var29 = MathHelper.floor(this.explosionY + this.explosionSize + 1.0D);
/* 105 */     int var7 = MathHelper.floor(this.explosionZ - this.explosionSize - 1.0D);
/* 106 */     int var30 = MathHelper.floor(this.explosionZ + this.explosionSize + 1.0D);
/* 107 */     List var9 = this.worldObj.getEntities(this.exploder, AxisAlignedBB.b(var3, var5, var7, var4, var29, var30));
/* 108 */     Vec3D var31 = Vec3D.create(this.explosionX, this.explosionY, this.explosionZ);
/*     */ 
/* 110 */     for (int var11 = 0; var11 < var9.size(); var11++)
/*     */     {
/* 112 */       Entity var32 = (Entity)var9.get(var11);
/* 113 */       double var13 = var32.f(this.explosionX, this.explosionY, this.explosionZ) / this.explosionSize;
/*     */ 
/* 115 */       if (var13 <= 1.0D)
/*     */       {
/* 117 */         double var15 = var32.locX - this.explosionX;
/* 118 */         double var17 = var32.locY - this.explosionY;
/* 119 */         double var19 = var32.locZ - this.explosionZ;
/* 120 */         double var40 = MathHelper.sqrt(var15 * var15 + var17 * var17 + var19 * var19);
/* 121 */         var15 /= var40;
/* 122 */         var17 /= var40;
/* 123 */         var19 /= var40;
/* 124 */         double var39 = this.worldObj.a(var31, var32.boundingBox);
/* 125 */         double var41 = (1.0D - var13) * var39;
/* 126 */         var32.damageEntity(DamageSource.EXPLOSION, (int)((var41 * var41 + var41) / 2.0D * 8.0D * this.explosionSize + 1.0D));
/* 127 */         var32.motX += var15 * var41;
/* 128 */         var32.motY += var17 * var41;
/* 129 */         var32.motZ += var19 * var41;
/*     */       }
/*     */     }
/*     */ 
/* 133 */     this.explosionSize = var1;
/* 134 */     ArrayList var34 = new ArrayList();
/* 135 */     var34.addAll(this.destroyedBlockPositions);
/*     */ 
/* 137 */     if (this.isFlaming)
/*     */     {
/* 139 */       for (int var33 = var34.size() - 1; var33 >= 0; var33--)
/*     */       {
/* 141 */         ChunkPosition var35 = (ChunkPosition)var34.get(var33);
/* 142 */         int var36 = var35.x;
/* 143 */         int var37 = var35.y;
/* 144 */         int var16 = var35.z;
/* 145 */         int var38 = this.worldObj.getTypeId(var36, var37, var16);
/* 146 */         int var18 = this.worldObj.getTypeId(var36, var37 - 1, var16);
/*     */ 
/* 148 */         if ((var38 == 0) && (Block.n[var18]) && (this.ExplosionRNG.nextInt(3) == 0))
/*     */         {
/* 150 */           this.worldObj.setTypeId(var36, var37, var16, Block.FIRE.id);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doExplosionB(boolean var1)
/*     */   {
/* 158 */     for (int var2 = 0; var2 <= 63; var2++)
/*     */     {
/* 160 */       this.dropList[var2] = null;
/*     */     }
/*     */ 
/* 163 */     this.worldObj.makeSound(this.explosionX, this.explosionY, this.explosionZ, "kinesis", 4.0F, (1.0F + (this.worldObj.random.nextFloat() - this.worldObj.random.nextFloat()) * 0.2F) * 0.7F);
/* 164 */     this.worldObj.a("hugeexplosion", this.explosionX, this.explosionY, this.explosionZ, 0.0D, 0.0D, 0.0D);
/* 165 */     ArrayList var25 = new ArrayList();
/* 166 */     var25.addAll(this.destroyedBlockPositions);
/*     */ 
/* 168 */     for (int var3 = var25.size() - 1; var3 >= 0; var3--)
/*     */     {
/* 170 */       ChunkPosition var4 = (ChunkPosition)var25.get(var3);
/* 171 */       int var5 = var4.x;
/* 172 */       int var6 = var4.y;
/* 173 */       int var7 = var4.z;
/* 174 */       int var8 = this.worldObj.getTypeId(var5, var6, var7);
/*     */ 
/* 176 */       if (var1)
/*     */       {
/* 178 */         double var9 = var5 + this.worldObj.random.nextFloat();
/* 179 */         double var11 = var6 + this.worldObj.random.nextFloat();
/* 180 */         double var13 = var7 + this.worldObj.random.nextFloat();
/* 181 */         double var15 = var9 - this.explosionX;
/* 182 */         double var17 = var11 - this.explosionY;
/* 183 */         double var19 = var13 - this.explosionZ;
/* 184 */         double var21 = MathHelper.sqrt(var15 * var15 + var17 * var17 + var19 * var19);
/* 185 */         var15 /= var21;
/* 186 */         var17 /= var21;
/* 187 */         var19 /= var21;
/* 188 */         double var23 = 0.5D / (var21 / this.explosionSize + 0.1D);
/* 189 */         var23 *= (this.worldObj.random.nextFloat() * this.worldObj.random.nextFloat() + 0.3F);
/* 190 */         var15 *= var23;
/* 191 */         var17 *= var23;
/* 192 */         var19 *= var23;
/*     */ 
/* 194 */         if (this.worldObj.random.nextInt(8) == 0)
/*     */         {
/* 196 */           this.worldObj.a("explode", (var9 + this.explosionX * 1.0D) / 2.0D, (var11 + this.explosionY * 1.0D) / 2.0D, (var13 + this.explosionZ * 1.0D) / 2.0D, var15, var17, var19);
/*     */         }
/*     */ 
/* 199 */         if (this.worldObj.random.nextInt(8) == 0)
/*     */         {
/* 201 */           this.worldObj.a("smoke", var9, var11, var13, var15, var17, var19);
/*     */         }
/*     */       }
/*     */ 
/* 205 */       if (var8 > 0)
/*     */       {
/* 207 */         int var27 = this.worldObj.getData(var5, var6, var7);
/* 208 */         ArrayList var10 = Block.byId[var8].getBlockDropped(this.worldObj, var5, var6, var7, var27, 0);
/* 209 */         Iterator var28 = var10.iterator();
/*     */ 
/* 211 */         while (var28.hasNext())
/*     */         {
/* 213 */           ItemStack var12 = (ItemStack)var28.next();
/*     */ 
/* 215 */           for (int var29 = 0; var29 < this.dropList.length; var29++)
/*     */           {
/* 217 */             if (this.dropList[var29] == null)
/*     */             {
/* 219 */               this.dropList[var29] = var12.cloneItemStack();
/* 220 */               var12 = null;
/*     */             }
/* 222 */             else if ((this.dropList[var29].doMaterialsMatch(var12)) && (this.dropList[var29].count < this.dropList[var29].getMaxStackSize()))
/*     */             {
/* 224 */               while ((this.dropList[var29].count < this.dropList[var29].getMaxStackSize()) && (var12 != null))
/*     */               {
/* 226 */                 this.dropList[var29].count += 1;
/* 227 */                 var12.count -= 1;
/*     */ 
/* 229 */                 if (var12.count == 0)
/*     */                 {
/* 231 */                   var12 = null;
/*     */                 }
/*     */               }
/*     */             }
/*     */ 
/* 236 */             if (var12 == null)
/*     */             {
/*     */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 243 */         this.worldObj.setTypeId(var5, var6, var7, 0);
/* 244 */         Block.byId[var8].wasExploded(this.worldObj, var5, var6, var7);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 250 */     if (this.exploder != null)
/*     */     {
/* 252 */       if (this.dropList != null)
/*     */       {
/* 254 */         EntityLootBall var26 = new EntityLootBall(this.worldObj, EEBase.playerX(this.exploder), EEBase.playerY(this.exploder), EEBase.playerZ(this.exploder), this.dropList);
/*     */ 
/* 256 */         if (var26 != null)
/*     */         {
/* 258 */           this.worldObj.addEntity(var26);
/*     */         }
/*     */       }
/*     */     }
/* 262 */     else if (this.dropList != null)
/*     */     {
/* 264 */       EntityLootBall var26 = new EntityLootBall(this.worldObj, this.explosionX, this.explosionY, this.explosionZ, this.dropList);
/*     */ 
/* 266 */       if (var26 != null)
/*     */       {
/* 268 */         this.worldObj.addEntity(var26);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.Combustion
 * JD-Core Version:    0.6.2
 */