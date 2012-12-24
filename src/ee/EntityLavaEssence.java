/*     */ package ee;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.AxisAlignedBB;
/*     */ import net.minecraft.server.DamageSource;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EnumMovingObjectType;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.Material;
/*     */ import net.minecraft.server.MathHelper;
/*     */ import net.minecraft.server.MovingObjectPosition;
/*     */ import net.minecraft.server.NBTTagCompound;
/*     */ import net.minecraft.server.Vec3D;
/*     */ import net.minecraft.server.World;
/*     */ import net.minecraft.server.WorldData;
/*     */ 
/*     */ public class EntityLavaEssence extends Entity
/*     */ {
/*     */   private boolean chargeMax;
/*     */   private EntityHuman player;
/*     */   private int ticksInAir;
/*     */   private int xTile;
/*     */   private int yTile;
/*     */   private int zTile;
/*     */   private int inTile;
/*     */   private int yawDir;
/*  29 */   public static boolean grab = true;
/*     */   private boolean inGround;
/*     */ 
/*     */   public EntityLavaEssence(World var1)
/*     */   {
/*  34 */     super(var1);
/*  35 */     this.bf = true;
/*  36 */     b(0.98F, 0.98F);
/*  37 */     this.height = (this.length / 2.0F);
/*     */   }
/*     */ 
/*     */   public EntityLavaEssence(World var1, EntityHuman var2, boolean var3)
/*     */   {
/*  42 */     super(var1);
/*  43 */     this.player = var2;
/*  44 */     this.xTile = -1;
/*  45 */     this.yTile = -1;
/*  46 */     this.zTile = -1;
/*  47 */     this.inTile = 0;
/*  48 */     this.inGround = false;
/*  49 */     this.chargeMax = var3;
/*  50 */     this.yawDir = ((MathHelper.floor((var2.yaw + 180.0F) * 4.0F / 360.0F - 0.5D) & 0x3) + 1);
/*  51 */     b(0.5F, 0.5F);
/*  52 */     setPositionRotation(var2.locX, var2.locY + var2.getHeadHeight(), var2.locZ, var2.yaw, var2.pitch);
/*  53 */     this.locX -= MathHelper.cos(this.yaw / 180.0F * 3.141593F) * 0.16F;
/*  54 */     this.locY -= 0.1D;
/*  55 */     this.locZ -= MathHelper.sin(this.yaw / 180.0F * 3.141593F) * 0.16F;
/*  56 */     setPosition(this.locX, this.locY, this.locZ);
/*  57 */     this.length = 0.0F;
/*  58 */     this.be = 10.0D;
/*  59 */     this.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.141593F) * MathHelper.cos(this.pitch / 180.0F * 3.141593F));
/*  60 */     this.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.141593F) * MathHelper.cos(this.pitch / 180.0F * 3.141593F));
/*  61 */     this.motY = (-MathHelper.sin(this.pitch / 180.0F * 3.141593F));
/*  62 */     calcVelo(this.motX, this.motY, this.motZ, 0.991F, 1.0F);
/*     */   }
/*     */ 
/*     */   public EntityLavaEssence(World var1, double var2, double var4, double var6)
/*     */   {
/*  67 */     this(var1);
/*  68 */     setPosition(var2, var4, var6);
/*     */   }
/*     */ 
/*     */   protected void b() {
/*     */   }
/*     */ 
/*     */   public void calcVelo(double var1, double var3, double var5, float var7, float var8) {
/*  75 */     float var9 = MathHelper.sqrt(var1 * var1 + var3 * var3 + var5 * var5);
/*  76 */     var1 /= var9;
/*  77 */     var3 /= var9;
/*  78 */     var5 /= var9;
/*  79 */     var1 *= var7;
/*  80 */     var3 *= var7;
/*  81 */     var5 *= var7;
/*  82 */     this.motX = var1;
/*  83 */     this.motY = var3;
/*  84 */     this.motZ = var5;
/*  85 */     float var10 = MathHelper.sqrt(var1 * var1 + var5 * var5);
/*  86 */     this.lastYaw = (this.yaw = (float)(Math.atan2(var1, var5) * 180.0D / 3.141592653589793D));
/*  87 */     this.lastPitch = (this.pitch = (float)(Math.atan2(var3, var10) * 180.0D / 3.141592653589793D));
/*     */   }
/*     */ 
/*     */   public void F_()
/*     */   {
/*  95 */     super.F_();
/*     */ 
/*  97 */     if ((!this.world.isStatic) && ((this.player == null) || (this.player.dead)))
/*     */     {
/*  99 */       die();
/*     */     }
/*     */ 
/* 102 */     float var1 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/* 103 */     this.yaw = ((float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.141592653589793D));
/*     */ 
/* 105 */     for (this.pitch = ((float)(Math.atan2(this.motY, var1) * 180.0D / 3.141592653589793D)); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F);
/* 110 */     while (this.pitch - this.lastPitch >= 180.0F)
/*     */     {
/* 112 */       this.lastPitch += 360.0F;
/*     */     }
/*     */ 
/* 115 */     while (this.yaw - this.lastYaw < -180.0F)
/*     */     {
/* 117 */       this.lastYaw -= 360.0F;
/*     */     }
/*     */ 
/* 120 */     while (this.yaw - this.lastYaw >= 180.0F)
/*     */     {
/* 122 */       this.lastYaw += 360.0F;
/*     */     }
/*     */ 
/* 125 */     this.pitch = (this.lastPitch + (this.pitch - this.lastPitch) * 0.2F);
/* 126 */     this.yaw = (this.lastYaw + (this.yaw - this.lastYaw) * 0.2F);
/* 127 */     this.lastX = this.locX;
/* 128 */     this.lastY = this.locY;
/* 129 */     this.lastZ = this.locZ;
/* 130 */     this.locX += this.motX;
/* 131 */     this.locY += this.motY;
/* 132 */     this.locZ += this.motZ;
/* 133 */     setPosition(this.locX, this.locY, this.locZ);
/*     */ 
/* 136 */     if (this.inGround)
/*     */     {
/* 138 */       int var2 = this.world.getTypeId(this.xTile, this.yTile, this.zTile);
/*     */ 
/* 140 */       if (var2 == this.inTile)
/*     */       {
/* 142 */         return;
/*     */       }
/*     */ 
/* 145 */       this.inGround = false;
/* 146 */       this.motX *= this.random.nextFloat() * 0.2F;
/* 147 */       this.motY *= this.random.nextFloat() * 0.2F;
/* 148 */       this.motZ *= this.random.nextFloat() * 0.2F;
/*     */     }
/*     */     else
/*     */     {
/* 152 */       this.ticksInAir += 1;
/*     */     }
/*     */ 
/* 155 */     if ((this.locY > 256.0D) && (this.motY > 0.0D))
/*     */     {
/* 157 */       if (EEBase.consumeKleinStarPoint(this.player, 1))
/*     */       {
/* 159 */         if (EEProxy.getWorldInfo(this.world).isThundering())
/*     */         {
/* 161 */           EEProxy.getWorldInfo(this.world).setThundering(false);
/* 162 */           EEProxy.getWorldInfo(this.world).setThunderDuration(0);
/*     */         }
/*     */ 
/* 165 */         if (EEProxy.getWorldInfo(this.world).hasStorm())
/*     */         {
/* 167 */           EEProxy.getWorldInfo(this.world).setStorm(false);
/* 168 */           EEProxy.getWorldInfo(this.world).setWeatherDuration(0);
/*     */         }
/*     */ 
/* 171 */         die();
/*     */       }
/* 173 */       else if (EEBase.Consume(new ItemStack(Item.REDSTONE, 1), this.player, true))
/*     */       {
/* 175 */         if (EEProxy.getWorldInfo(this.world).isThundering())
/*     */         {
/* 177 */           EEProxy.getWorldInfo(this.world).setThundering(false);
/* 178 */           EEProxy.getWorldInfo(this.world).setThunderDuration(0);
/*     */         }
/*     */ 
/* 181 */         if (EEProxy.getWorldInfo(this.world).hasStorm())
/*     */         {
/* 183 */           EEProxy.getWorldInfo(this.world).setStorm(false);
/* 184 */           EEProxy.getWorldInfo(this.world).setWeatherDuration(0);
/*     */         }
/*     */ 
/* 187 */         die();
/*     */       }
/*     */       else
/*     */       {
/* 191 */         die();
/*     */       }
/*     */     }
/*     */ 
/* 195 */     int var2 = MathHelper.floor(this.locX);
/* 196 */     int var3 = MathHelper.floor(this.locY);
/* 197 */     int var4 = MathHelper.floor(this.locZ);
/*     */ 
/* 199 */     for (int var5 = -2; var5 <= 2; var5++)
/*     */     {
/* 201 */       for (int var6 = -2; var6 <= 2; var6++)
/*     */       {
/* 203 */         for (int var7 = -2; var7 <= 2; var7++)
/*     */         {
/* 205 */           if (this.world.getMaterial(var2 + var5, var3 + var6, var4 + var7) == Material.WATER)
/*     */           {
/* 207 */             this.world.setTypeId(var2 + var5, var3 + var6, var4 + var7, 0);
/* 208 */             this.world.a("smoke", var2 + var5, var3 + var6, var4 + var7, 0.0D, 0.1D, 0.0D);
/* 209 */             this.world.makeSound(this, "random.fizz", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 215 */     if (this.ticksInAir >= 3)
/*     */     {
/* 217 */       Vec3D var19 = Vec3D.create(this.locX, this.locY, this.locZ);
/* 218 */       Vec3D var20 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 219 */       MovingObjectPosition var21 = this.world.rayTrace(var19, var20, true);
/* 220 */       var19 = Vec3D.create(this.locX, this.locY, this.locZ);
/* 221 */       var20 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 222 */       Entity var8 = null;
/* 223 */       List var9 = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
/* 224 */       double var10 = 0.0D;
/*     */ 
/* 227 */       for (int var12 = 0; var12 < var9.size(); var12++)
/*     */       {
/* 229 */         Entity var13 = (Entity)var9.get(var12);
/*     */ 
/* 231 */         if ((var13.o_()) && ((this.player == null) || (var13 != this.player)))
/*     */         {
/* 233 */           float var14 = 0.3F;
/* 234 */           AxisAlignedBB var15 = var13.boundingBox.grow(var14, var14, var14);
/* 235 */           MovingObjectPosition var16 = var15.a(var19, var20);
/*     */ 
/* 237 */           if (var16 != null)
/*     */           {
/* 239 */             double var17 = var19.b(var16.pos);
/*     */ 
/* 241 */             if ((var17 < var10) || (var10 == 0.0D))
/*     */             {
/* 243 */               var8 = var13;
/* 244 */               var10 = var17;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 250 */       if (h_())
/*     */       {
/* 252 */         for (int var12 = 0; var12 < 4; var12++)
/*     */         {
/* 254 */           float var22 = 0.25F;
/* 255 */           this.world.a("smoke", this.locX - this.motX * var22, this.locY - this.motY * var22, this.locZ - this.motZ * var22, this.motX, this.motY, this.motZ);
/*     */         }
/*     */ 
/* 258 */         if (this.chargeMax)
/*     */         {
/* 260 */           this.world.makeSound(this, "random.fizz", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
/*     */ 
/* 262 */           if (this.world.getMaterial(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)) == Material.WATER)
/*     */           {
/* 264 */             this.world.setTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ), 0);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 269 */       if (var8 != null)
/*     */       {
/* 271 */         var21 = new MovingObjectPosition(var8);
/*     */       }
/*     */ 
/* 274 */       if (var21 != null)
/*     */       {
/* 276 */         if (var21.type == EnumMovingObjectType.ENTITY)
/*     */         {
/* 278 */           if (EEBase.consumeKleinStarPoint(this.player, 2))
/*     */           {
/* 280 */             if (!EEProxy.isEntityFireImmune(var21.entity))
/*     */             {
/* 282 */               var21.entity.damageEntity(DamageSource.LAVA, 12);
/* 283 */               var21.entity.setOnFire(600);
/*     */             }
/*     */ 
/* 286 */             die();
/*     */           }
/* 288 */           else if (EEBase.Consume(new ItemStack(Item.REDSTONE, 2), this.player, false))
/*     */           {
/* 290 */             if (!EEProxy.isEntityFireImmune(var21.entity))
/*     */             {
/* 292 */               var21.entity.damageEntity(DamageSource.LAVA, 12);
/* 293 */               var21.entity.setOnFire(600);
/*     */             }
/*     */ 
/* 296 */             die();
/*     */           }
/* 298 */           else if (!EEProxy.isEntityFireImmune(var21.entity))
/*     */           {
/* 300 */             var21.entity.damageEntity(DamageSource.LAVA, 1);
/* 301 */             var21.entity.setOnFire(10);
/*     */           }
/*     */ 
/* 304 */           die();
/*     */         }
/*     */         else
/*     */         {
/* 308 */           makeLava(var2, var3, var4);
/*     */         }
/*     */ 
/* 311 */         die();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void makeLava(int var1, int var2, int var3)
/*     */   {
/* 318 */     if (!EEProxy.isClient(this.world))
/*     */     {
/* 320 */       if (EEBase.consumeKleinStarPoint(this.player, 64))
/*     */       {
/* 322 */         if (this.world.getTypeId(var1, var2, var3) == 0)
/*     */         {
/* 324 */           this.world.setTypeId(var1, var2, var3, 10);
/*     */         }
/*     */       }
/* 327 */       else if (EEBase.Consume(new ItemStack(Item.REDSTONE, 1), this.player, false))
/*     */       {
/* 329 */         if (this.world.getTypeId(var1, var2, var3) == 0)
/*     */         {
/* 331 */           this.world.setTypeId(var1, var2, var3, 10);
/*     */         }
/*     */       }
/* 334 */       else if ((EEBase.Consume(new ItemStack(Item.COAL, 2, var1), this.player, false)) && (this.world.getTypeId(var1, var2, var3) == 0))
/*     */       {
/* 336 */         this.world.setTypeId(var1, var2, var3, 10);
/*     */       }
/*     */ 
/* 339 */       die();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void b(NBTTagCompound var1)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void a(NBTTagCompound var1)
/*     */   {
/*     */   }
/*     */ 
/*     */   public float getShadowSize()
/*     */   {
/* 355 */     return 0.0F;
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.EntityLavaEssence
 * JD-Core Version:    0.6.2
 */