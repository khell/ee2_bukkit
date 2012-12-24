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
/*     */ public abstract class EntityEEProjectile extends Entity
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
/*     */   public EntityEEProjectile(World var1)
/*     */   {
/*  41 */     super(var1);
/*     */   }
/*     */ 
/*     */   public EntityEEProjectile(World var1, double var2, double var4, double var6)
/*     */   {
/*  46 */     this(var1);
/*  47 */     setPosition(var2, var4, var6);
/*     */   }
/*     */ 
/*     */   public EntityEEProjectile(World var1, EntityLiving var2)
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
/* 138 */     if ((!this.world.isStatic) && ((this.shooter == null) || (this.shooter.dead)))
/*     */     {
/* 140 */       die();
/*     */     }
/*     */ 
/* 143 */     if ((this.lastPitch == 0.0F) && (this.lastYaw == 0.0F))
/*     */     {
/* 145 */       float var1 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/* 146 */       this.lastYaw = (this.yaw = (float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.141592653589793D));
/* 147 */       this.lastPitch = (this.pitch = (float)(Math.atan2(this.motY, var1) * 180.0D / 3.141592653589793D));
/*     */     }
/*     */ 
/* 150 */     if (this.arrowShake > 0)
/*     */     {
/* 152 */       this.arrowShake -= 1;
/*     */     }
/*     */ 
/* 155 */     if (this.inGround)
/*     */     {
/* 157 */       int var15 = this.world.getTypeId(this.xTile, this.yTile, this.zTile);
/* 158 */       int var2 = this.world.getData(this.xTile, this.yTile, this.zTile);
/*     */ 
/* 160 */       if ((var15 == this.inTile) && (var2 == this.inData))
/*     */       {
/* 162 */         this.ticksInGround += 1;
/* 163 */         tickInGround();
/*     */ 
/* 165 */         if (this.ticksInGround == this.ttlInGround)
/*     */         {
/* 167 */           die();
/*     */         }
/*     */ 
/* 170 */         return;
/*     */       }
/*     */ 
/* 173 */       this.inGround = false;
/* 174 */       this.motX *= this.random.nextFloat() * 0.2F;
/* 175 */       this.motY *= this.random.nextFloat() * 0.2F;
/* 176 */       this.motZ *= this.random.nextFloat() * 0.2F;
/* 177 */       this.ticksInGround = 0;
/* 178 */       this.ticksFlying = 0;
/*     */     }
/*     */     else
/*     */     {
/* 182 */       this.ticksFlying += 1;
/*     */     }
/*     */ 
/* 185 */     tickFlying();
/* 186 */     Vec3D var16 = Vec3D.create(this.locX, this.locY, this.locZ);
/* 187 */     Vec3D var17 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 188 */     MovingObjectPosition var3 = this.world.a(var16, var17);
/* 189 */     var16 = Vec3D.create(this.locX, this.locY, this.locZ);
/* 190 */     var17 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/*     */ 
/* 192 */     if (var3 != null)
/*     */     {
/* 194 */       var17 = Vec3D.create(var3.pos.a, var3.pos.b, var3.pos.c);
/*     */     }
/*     */ 
/* 197 */     Entity var4 = null;
/* 198 */     List var5 = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
/* 199 */     double var6 = 0.0D;
/*     */ 
/* 201 */     for (int var8 = 0; var8 < var5.size(); var8++)
/*     */     {
/* 203 */       Entity var9 = (Entity)var5.get(var8);
/*     */ 
/* 205 */       if (canBeShot(var9))
/*     */       {
/* 207 */         float var10 = this.hitBox;
/* 208 */         AxisAlignedBB var11 = var9.boundingBox.grow(var10, var10, var10);
/* 209 */         MovingObjectPosition var12 = var11.a(var16, var17);
/*     */ 
/* 211 */         if (var12 != null)
/*     */         {
/* 213 */           double var13 = var16.b(var12.pos);
/*     */ 
/* 215 */           if ((var13 < var6) || (var6 == 0.0D))
/*     */           {
/* 217 */             var4 = var9;
/* 218 */             var6 = var13;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 224 */     if (var4 != null)
/*     */     {
/* 226 */       var3 = new MovingObjectPosition(var4);
/*     */     }
/*     */ 
/* 229 */     if ((var3 != null) && (onHit()))
/*     */     {
/* 231 */       Entity var18 = var3.entity;
/*     */ 
/* 233 */       if (var18 != null)
/*     */       {
/* 235 */         if (onHitTarget(var18))
/*     */         {
/* 237 */           if (((var18 instanceof EntityLiving)) && (!(var18 instanceof EntityHuman)))
/*     */           {
/* 239 */             EEProxy.setArmorRating((EntityLiving)var18, 0);
/*     */           }
/*     */ 
/* 242 */           var18.damageEntity(DamageSource.mobAttack(this.shooter), this.dmg);
/* 243 */           die();
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 248 */         this.xTile = var3.b;
/* 249 */         this.yTile = var3.c;
/* 250 */         this.zTile = var3.d;
/* 251 */         this.inTile = this.world.getTypeId(this.xTile, this.yTile, this.zTile);
/* 252 */         this.inData = this.world.getData(this.xTile, this.yTile, this.zTile);
/*     */ 
/* 254 */         if (onHitBlock(var3))
/*     */         {
/* 256 */           this.motX = ((float)(var3.pos.a - this.locX));
/* 257 */           this.motY = ((float)(var3.pos.b - this.locY));
/* 258 */           this.motZ = ((float)(var3.pos.c - this.locZ));
/* 259 */           float var19 = MathHelper.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ);
/* 260 */           this.locX -= this.motX / var19 * 0.0500000007450581D;
/* 261 */           this.locY -= this.motY / var19 * 0.0500000007450581D;
/* 262 */           this.locZ -= this.motZ / var19 * 0.0500000007450581D;
/* 263 */           this.inGround = true;
/* 264 */           this.arrowShake = 7;
/*     */         }
/*     */         else
/*     */         {
/* 268 */           this.inTile = 0;
/* 269 */           this.inData = 0;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 274 */     this.locX += this.motX;
/* 275 */     this.locY += this.motY;
/* 276 */     this.locZ += this.motZ;
/* 277 */     handleMotionUpdate();
/* 278 */     float var20 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/* 279 */     this.yaw = ((float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.141592653589793D));
/*     */ 
/* 281 */     for (this.pitch = ((float)(Math.atan2(this.motY, var20) * 180.0D / 3.141592653589793D)); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F);
/* 286 */     while (this.pitch - this.lastPitch >= 180.0F)
/*     */     {
/* 288 */       this.lastPitch += 360.0F;
/*     */     }
/*     */ 
/* 291 */     while (this.yaw - this.lastYaw < -180.0F)
/*     */     {
/* 293 */       this.lastYaw -= 360.0F;
/*     */     }
/*     */ 
/* 296 */     while (this.yaw - this.lastYaw >= 180.0F)
/*     */     {
/* 298 */       this.lastYaw += 360.0F;
/*     */     }
/*     */ 
/* 301 */     this.pitch = (this.lastPitch + (this.pitch - this.lastPitch) * 0.2F);
/* 302 */     this.yaw = (this.lastYaw + (this.yaw - this.lastYaw) * 0.2F);
/* 303 */     setPosition(this.locX, this.locY, this.locZ);
/*     */   }
/*     */ 
/*     */   public void handleMotionUpdate()
/*     */   {
/* 308 */     float var1 = this.slowdown;
/*     */ 
/* 310 */     if (h_())
/*     */     {
/* 312 */       for (int var2 = 0; var2 < 4; var2++)
/*     */       {
/* 314 */         float var3 = 0.25F;
/* 315 */         this.world.a("bubble", this.locX - this.motX * var3, this.locY - this.motY * var3, this.locZ - this.motZ * var3, this.motX, this.motY, this.motZ);
/*     */       }
/*     */ 
/* 318 */       var1 *= 0.8F;
/*     */     }
/*     */ 
/* 321 */     this.motX *= var1;
/* 322 */     this.motY *= var1;
/* 323 */     this.motZ *= var1;
/* 324 */     this.motY -= this.curvature;
/*     */   }
/*     */ 
/*     */   public void b(NBTTagCompound var1)
/*     */   {
/* 332 */     var1.setShort("xTile", (short)this.xTile);
/* 333 */     var1.setShort("yTile", (short)this.yTile);
/* 334 */     var1.setShort("zTile", (short)this.zTile);
/* 335 */     var1.setByte("inTile", (byte)this.inTile);
/* 336 */     var1.setByte("inData", (byte)this.inData);
/* 337 */     var1.setByte("shake", (byte)this.arrowShake);
/* 338 */     var1.setByte("inGround", (byte)(this.inGround ? 1 : 0));
/* 339 */     var1.setBoolean("player", this.shotByPlayer);
/*     */   }
/*     */ 
/*     */   public void a(NBTTagCompound var1)
/*     */   {
/* 347 */     this.xTile = var1.getShort("xTile");
/* 348 */     this.yTile = var1.getShort("yTile");
/* 349 */     this.zTile = var1.getShort("zTile");
/* 350 */     this.inTile = (var1.getByte("inTile") & 0xFF);
/* 351 */     this.inData = (var1.getByte("inData") & 0xFF);
/* 352 */     this.arrowShake = (var1.getByte("shake") & 0xFF);
/* 353 */     this.inGround = (var1.getByte("inGround") == 1);
/* 354 */     this.shotByPlayer = var1.getBoolean("player");
/*     */   }
/*     */ 
/*     */   public void a_(EntityHuman var1)
/*     */   {
/* 362 */     if (this.item != null)
/*     */     {
/* 364 */       if (!this.world.isStatic)
/*     */       {
/* 366 */         if ((this.inGround) && (this.shotByPlayer) && (this.arrowShake <= 0) && (var1.inventory.pickup(this.item.cloneItemStack())))
/*     */         {
/* 368 */           this.world.makeSound(this, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/* 369 */           var1.receive(this, 1);
/* 370 */           die();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean canBeShot(Entity var1)
/*     */   {
/* 378 */     return (var1.o_()) && ((var1 != this.shooter) || (this.ticksFlying >= 5)) && ((!(var1 instanceof EntityLiving)) || (((EntityLiving)var1).deathTicks <= 0));
/*     */   }
/*     */ 
/*     */   public boolean onHit()
/*     */   {
/* 383 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean onHitTarget(Entity var1)
/*     */   {
/* 388 */     this.world.makeSound(this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
/* 389 */     return true;
/*     */   }
/*     */   public void tickFlying() {
/*     */   }
/*     */ 
/*     */   public void tickInGround() {
/*     */   }
/*     */ 
/*     */   public boolean onHitBlock(MovingObjectPosition var1) {
/* 398 */     return onHitBlock();
/*     */   }
/*     */ 
/*     */   public boolean onHitBlock()
/*     */   {
/* 403 */     this.world.makeSound(this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
/* 404 */     return true;
/*     */   }
/*     */ 
/*     */   public float getShadowSize()
/*     */   {
/* 409 */     return 0.0F;
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.EntityEEProjectile
 * JD-Core Version:    0.6.2
 */