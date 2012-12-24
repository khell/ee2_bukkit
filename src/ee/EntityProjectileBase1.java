/*     */ package ee;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.AxisAlignedBB;
/*     */ import net.minecraft.server.DamageSource;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityLiving;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.MathHelper;
/*     */ import net.minecraft.server.MovingObjectPosition;
/*     */ import net.minecraft.server.NBTTagCompound;
/*     */ import net.minecraft.server.PlayerInventory;
/*     */ import net.minecraft.server.Vec3D;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public abstract class EntityProjectileBase1 extends Entity
/*     */ {
/*     */   public float speed;
/*     */   public float slowdown;
/*     */   public float curvature;
/*     */   public float precision;
/*     */   public float hitBox;
/*     */   public int dmg;
/*     */   public ItemStack item;
/*     */   public int ttlInGround;
/*     */   public int xTile;
/*     */   public int yTile;
/*     */   public int zTile;
/*     */   public int inTile;
/*     */   public int inData;
/*     */   public boolean inGround;
/*     */   public int arrowShake;
/*     */   public EntityLiving shooter;
/*     */   public int ticksInGround;
/*     */   public int ticksFlying;
/*     */   public boolean shotByPlayer;
/*     */ 
/*     */   public EntityProjectileBase1(World var1)
/*     */   {
/*  41 */     super(var1);
/*     */   }
/*     */ 
/*     */   public EntityProjectileBase1(World var1, double var2, double var4, double var6)
/*     */   {
/*  46 */     this(var1);
/*  47 */     setPosition(var2, var4, var6);
/*     */   }
/*     */ 
/*     */   public EntityProjectileBase1(World var1, EntityLiving var2)
/*     */   {
/*  52 */     this(var1);
/*  53 */     this.shooter = var2;
/*  54 */     this.shotByPlayer = (var2 instanceof EntityHuman);
/*  55 */     setPositionRotation(var2.locX, var2.locY + var2.getHeadHeight(), var2.locZ, var2.yaw + this.world.random.nextFloat(), var2.pitch + this.world.random.nextFloat());
/*  56 */     this.locX -= MathHelper.cos(this.yaw / 180.0F * 3.141593F) * 0.16F;
/*  57 */     this.locY -= 0.1000000014901161D;
/*  58 */     this.locZ -= MathHelper.sin(this.yaw / 180.0F * 3.141593F) * 0.16F;
/*  59 */     setPosition(this.locX, this.locY, this.locZ);
/*  60 */     this.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.141593F) * MathHelper.cos(this.pitch / 180.0F * 3.141593F));
/*  61 */     this.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.141593F) * MathHelper.cos(this.pitch / 180.0F * 3.141593F));
/*  62 */     this.motY = (-MathHelper.sin(this.pitch / 180.0F * 3.141593F));
/*  63 */     setArrowHeading(this.motX, this.motY, this.motZ, this.speed, this.precision);
/*     */   }
/*     */ 
/*     */   protected void b()
/*     */   {
/*  68 */     this.xTile = -1;
/*  69 */     this.yTile = -1;
/*  70 */     this.zTile = -1;
/*  71 */     this.inTile = 0;
/*  72 */     this.inGround = false;
/*  73 */     this.arrowShake = 0;
/*  74 */     this.ticksFlying = 0;
/*  75 */     b(0.5F, 0.5F);
/*  76 */     this.height = 0.0F;
/*  77 */     this.hitBox = 0.3F;
/*  78 */     this.speed = 1.0F;
/*  79 */     this.slowdown = 0.99F;
/*  80 */     this.curvature = 0.03F;
/*  81 */     this.dmg = 4;
/*  82 */     this.precision = 1.0F;
/*  83 */     this.ttlInGround = 1200;
/*  84 */     this.item = null;
/*     */   }
/*     */ 
/*     */   public void die()
/*     */   {
/*  92 */     this.shooter = null;
/*  93 */     super.die();
/*     */   }
/*     */ 
/*     */   public void setArrowHeading(double var1, double var3, double var5, float var7, float var8)
/*     */   {
/*  98 */     float var9 = MathHelper.sqrt(var1 * var1 + var3 * var3 + var5 * var5);
/*  99 */     var1 /= var9;
/* 100 */     var3 /= var9;
/* 101 */     var5 /= var9;
/* 102 */     var1 += this.random.nextGaussian() * 0.007499999832361937D * var8;
/* 103 */     var3 += this.random.nextGaussian() * 0.007499999832361937D * var8;
/* 104 */     var5 += this.random.nextGaussian() * 0.007499999832361937D * var8;
/* 105 */     var1 *= var7;
/* 106 */     var3 *= var7;
/* 107 */     var5 *= var7;
/* 108 */     this.motX = var1;
/* 109 */     this.motY = var3;
/* 110 */     this.motZ = var5;
/* 111 */     float var10 = MathHelper.sqrt(var1 * var1 + var5 * var5);
/* 112 */     this.lastYaw = (this.yaw = (float)(Math.atan2(var1, var5) * 180.0D / 3.141592653589793D));
/* 113 */     this.lastPitch = (this.pitch = (float)(Math.atan2(var3, var10) * 180.0D / 3.141592653589793D));
/* 114 */     this.ticksInGround = 0;
/*     */   }
/*     */ 
/*     */   public void setVelocity(double var1, double var3, double var5)
/*     */   {
/* 119 */     this.motX = var1;
/* 120 */     this.motY = var3;
/* 121 */     this.motZ = var5;
/*     */ 
/* 123 */     if ((this.lastPitch == 0.0F) && (this.lastYaw == 0.0F))
/*     */     {
/* 125 */       float var7 = MathHelper.sqrt(var1 * var1 + var5 * var5);
/* 126 */       this.lastYaw = (this.yaw = (float)(Math.atan2(var1, var5) * 180.0D / 3.141592653589793D));
/* 127 */       this.lastPitch = (this.pitch = (float)(Math.atan2(var3, var7) * 180.0D / 3.141592653589793D));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void F_()
/*     */   {
/* 136 */     super.F_();
/*     */ 
/* 138 */     if ((this.lastPitch == 0.0F) && (this.lastYaw == 0.0F))
/*     */     {
/* 140 */       float var1 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/* 141 */       this.lastYaw = (this.yaw = (float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.141592653589793D));
/* 142 */       this.lastPitch = (this.pitch = (float)(Math.atan2(this.motY, var1) * 180.0D / 3.141592653589793D));
/*     */     }
/*     */ 
/* 145 */     if (this.arrowShake > 0)
/*     */     {
/* 147 */       this.arrowShake -= 1;
/*     */     }
/*     */ 
/* 150 */     if (this.inGround)
/*     */     {
/* 152 */       int var15 = this.world.getTypeId(this.xTile, this.yTile, this.zTile);
/* 153 */       int var2 = this.world.getData(this.xTile, this.yTile, this.zTile);
/*     */ 
/* 155 */       if ((var15 == this.inTile) && (var2 == this.inData))
/*     */       {
/* 157 */         this.ticksInGround += 1;
/* 158 */         tickInGround();
/*     */ 
/* 160 */         if (this.ticksInGround == this.ttlInGround)
/*     */         {
/* 162 */           die();
/*     */         }
/*     */ 
/* 165 */         return;
/*     */       }
/*     */ 
/* 168 */       this.inGround = false;
/* 169 */       this.motX *= this.random.nextFloat() * 0.2F;
/* 170 */       this.motY *= this.random.nextFloat() * 0.2F;
/* 171 */       this.motZ *= this.random.nextFloat() * 0.2F;
/* 172 */       this.ticksInGround = 0;
/* 173 */       this.ticksFlying = 0;
/*     */     }
/*     */     else
/*     */     {
/* 177 */       this.ticksFlying += 1;
/*     */     }
/*     */ 
/* 180 */     tickFlying();
/* 181 */     Vec3D var16 = Vec3D.create(this.locX, this.locY, this.locZ);
/* 182 */     Vec3D var17 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 183 */     MovingObjectPosition var3 = this.world.a(var16, var17);
/* 184 */     var16 = Vec3D.create(this.locX, this.locY, this.locZ);
/* 185 */     var17 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/*     */ 
/* 187 */     if (var3 != null)
/*     */     {
/* 189 */       var17 = Vec3D.create(var3.pos.a, var3.pos.b, var3.pos.c);
/*     */     }
/*     */ 
/* 192 */     Entity var4 = null;
/* 193 */     List var5 = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
/* 194 */     double var6 = 0.0D;
/*     */ 
/* 196 */     for (int var8 = 0; var8 < var5.size(); var8++)
/*     */     {
/* 198 */       Entity var9 = (Entity)var5.get(var8);
/*     */ 
/* 200 */       if (canBeShot(var9))
/*     */       {
/* 202 */         float var10 = this.hitBox;
/* 203 */         AxisAlignedBB var11 = var9.boundingBox.grow(var10, var10, var10);
/* 204 */         MovingObjectPosition var12 = var11.a(var16, var17);
/*     */ 
/* 206 */         if (var12 != null)
/*     */         {
/* 208 */           double var13 = var16.b(var12.pos);
/*     */ 
/* 210 */           if ((var13 < var6) || (var6 == 0.0D))
/*     */           {
/* 212 */             var4 = var9;
/* 213 */             var6 = var13;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 219 */     if (var4 != null)
/*     */     {
/* 221 */       var3 = new MovingObjectPosition(var4);
/*     */     }
/*     */ 
/* 224 */     if ((var3 != null) && (onHit()))
/*     */     {
/* 226 */       Entity var18 = var3.entity;
/*     */ 
/* 228 */       if (var18 != null)
/*     */       {
/* 230 */         if (onHitTarget(var18))
/*     */         {
/* 232 */           if (((var18 instanceof EntityLiving)) && (!(var18 instanceof EntityHuman)))
/*     */           {
/* 234 */             EEProxy.setArmorRating((EntityLiving)var18, 0);
/*     */           }
/*     */ 
/* 237 */           var18.damageEntity(DamageSource.mobAttack(this.shooter), this.dmg);
/* 238 */           die();
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 243 */         this.xTile = var3.b;
/* 244 */         this.yTile = var3.c;
/* 245 */         this.zTile = var3.d;
/* 246 */         this.inTile = this.world.getTypeId(this.xTile, this.yTile, this.zTile);
/* 247 */         this.inData = this.world.getData(this.xTile, this.yTile, this.zTile);
/*     */ 
/* 249 */         if (onHitBlock(var3))
/*     */         {
/* 251 */           this.motX = ((float)(var3.pos.a - this.locX));
/* 252 */           this.motY = ((float)(var3.pos.b - this.locY));
/* 253 */           this.motZ = ((float)(var3.pos.c - this.locZ));
/* 254 */           float var19 = MathHelper.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ);
/* 255 */           this.locX -= this.motX / var19 * 0.0500000007450581D;
/* 256 */           this.locY -= this.motY / var19 * 0.0500000007450581D;
/* 257 */           this.locZ -= this.motZ / var19 * 0.0500000007450581D;
/* 258 */           this.inGround = true;
/* 259 */           this.arrowShake = 7;
/*     */         }
/*     */         else
/*     */         {
/* 263 */           this.inTile = 0;
/* 264 */           this.inData = 0;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 269 */     this.locX += this.motX;
/* 270 */     this.locY += this.motY;
/* 271 */     this.locZ += this.motZ;
/* 272 */     handleMotionUpdate();
/* 273 */     float var20 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/* 274 */     this.yaw = ((float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.141592653589793D));
/*     */ 
/* 276 */     for (this.pitch = ((float)(Math.atan2(this.motY, var20) * 180.0D / 3.141592653589793D)); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F);
/* 281 */     while (this.pitch - this.lastPitch >= 180.0F)
/*     */     {
/* 283 */       this.lastPitch += 360.0F;
/*     */     }
/*     */ 
/* 286 */     while (this.yaw - this.lastYaw < -180.0F)
/*     */     {
/* 288 */       this.lastYaw -= 360.0F;
/*     */     }
/*     */ 
/* 291 */     while (this.yaw - this.lastYaw >= 180.0F)
/*     */     {
/* 293 */       this.lastYaw += 360.0F;
/*     */     }
/*     */ 
/* 296 */     this.pitch = (this.lastPitch + (this.pitch - this.lastPitch) * 0.2F);
/* 297 */     this.yaw = (this.lastYaw + (this.yaw - this.lastYaw) * 0.2F);
/* 298 */     setPosition(this.locX, this.locY, this.locZ);
/*     */   }
/*     */ 
/*     */   public void handleMotionUpdate()
/*     */   {
/* 303 */     float var1 = this.slowdown;
/*     */ 
/* 305 */     if (h_())
/*     */     {
/* 307 */       for (int var2 = 0; var2 < 4; var2++)
/*     */       {
/* 309 */         float var3 = 0.25F;
/* 310 */         this.world.a("bubble", this.locX - this.motX * var3, this.locY - this.motY * var3, this.locZ - this.motZ * var3, this.motX, this.motY, this.motZ);
/*     */       }
/*     */ 
/* 313 */       var1 *= 0.8F;
/*     */     }
/*     */ 
/* 316 */     this.motX *= var1;
/* 317 */     this.motY *= var1;
/* 318 */     this.motZ *= var1;
/* 319 */     this.motY -= this.curvature;
/*     */   }
/*     */ 
/*     */   public void b(NBTTagCompound var1)
/*     */   {
/* 327 */     var1.setShort("xTile", (short)this.xTile);
/* 328 */     var1.setShort("yTile", (short)this.yTile);
/* 329 */     var1.setShort("zTile", (short)this.zTile);
/* 330 */     var1.setByte("inTile", (byte)this.inTile);
/* 331 */     var1.setByte("inData", (byte)this.inData);
/* 332 */     var1.setByte("shake", (byte)this.arrowShake);
/* 333 */     var1.setByte("inGround", (byte)(this.inGround ? 1 : 0));
/* 334 */     var1.setBoolean("player", this.shotByPlayer);
/*     */   }
/*     */ 
/*     */   public void a(NBTTagCompound var1)
/*     */   {
/* 342 */     this.xTile = var1.getShort("xTile");
/* 343 */     this.yTile = var1.getShort("yTile");
/* 344 */     this.zTile = var1.getShort("zTile");
/* 345 */     this.inTile = (var1.getByte("inTile") & 0xFF);
/* 346 */     this.inData = (var1.getByte("inData") & 0xFF);
/* 347 */     this.arrowShake = (var1.getByte("shake") & 0xFF);
/* 348 */     this.inGround = (var1.getByte("inGround") == 1);
/* 349 */     this.shotByPlayer = var1.getBoolean("player");
/*     */   }
/*     */ 
/*     */   public void a_(EntityHuman var1)
/*     */   {
/* 357 */     if (this.item != null)
/*     */     {
/* 359 */       if (!this.world.isStatic)
/*     */       {
/* 361 */         if ((this.inGround) && (this.shotByPlayer) && (this.arrowShake <= 0) && (var1.inventory.pickup(this.item.cloneItemStack())))
/*     */         {
/* 363 */           this.world.makeSound(this, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/* 364 */           var1.receive(this, 1);
/* 365 */           die();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean canBeShot(Entity var1)
/*     */   {
/* 373 */     return (var1.o_()) && ((var1 != this.shooter) || (this.ticksFlying >= 5)) && ((!(var1 instanceof EntityLiving)) || (((EntityLiving)var1).deathTicks <= 0));
/*     */   }
/*     */ 
/*     */   public boolean onHit()
/*     */   {
/* 378 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean onHitTarget(Entity var1)
/*     */   {
/* 383 */     this.world.makeSound(this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
/* 384 */     return true;
/*     */   }
/*     */   public void tickFlying() {
/*     */   }
/*     */ 
/*     */   public void tickInGround() {
/*     */   }
/*     */ 
/*     */   public boolean onHitBlock(MovingObjectPosition var1) {
/* 393 */     return onHitBlock();
/*     */   }
/*     */ 
/*     */   public boolean onHitBlock()
/*     */   {
/* 398 */     this.world.makeSound(this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
/* 399 */     return true;
/*     */   }
/*     */ 
/*     */   public float getShadowSize()
/*     */   {
/* 404 */     return 0.0F;
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.EntityProjectileBase1
 * JD-Core Version:    0.6.2
 */