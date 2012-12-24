/*     */ package ee;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.AxisAlignedBB;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityWeatherLighting;
/*     */ import net.minecraft.server.EnumMovingObjectType;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.MathHelper;
/*     */ import net.minecraft.server.MovingObjectPosition;
/*     */ import net.minecraft.server.NBTTagCompound;
/*     */ import net.minecraft.server.Vec3D;
/*     */ import net.minecraft.server.World;
/*     */ import net.minecraft.server.WorldData;
/*     */ 
/*     */ public class EntityWindEssence extends Entity
/*     */ {
/*     */   private int xTile;
/*     */   private int yTile;
/*     */   private int zTile;
/*     */   private int inTile;
/*     */   private int yawDir;
/*  25 */   public static boolean grab = true;
/*     */   private boolean inGround;
/*     */   private EntityHuman player;
/*     */   private int ticksInAir;
/*     */ 
/*     */   public EntityWindEssence(World var1)
/*     */   {
/*  32 */     super(var1);
/*  33 */     this.bf = true;
/*  34 */     b(0.98F, 0.98F);
/*  35 */     this.height = (this.length / 2.0F);
/*     */   }
/*     */ 
/*     */   public EntityWindEssence(World var1, double var2, double var4, double var6)
/*     */   {
/*  40 */     this(var1);
/*  41 */     setPosition(var2, var4, var6);
/*     */   }
/*     */ 
/*     */   public EntityWindEssence(World var1, EntityHuman var2)
/*     */   {
/*  46 */     super(var1);
/*  47 */     this.player = var2;
/*  48 */     this.xTile = -1;
/*  49 */     this.yTile = -1;
/*  50 */     this.zTile = -1;
/*  51 */     this.inTile = 0;
/*  52 */     this.inGround = false;
/*  53 */     this.yawDir = ((MathHelper.floor((var2.yaw + 180.0F) * 4.0F / 360.0F - 0.5D) & 0x3) + 1);
/*  54 */     b(0.5F, 0.5F);
/*  55 */     setPositionRotation(var2.locX, var2.locY + var2.getHeadHeight(), var2.locZ, var2.yaw, var2.pitch);
/*  56 */     this.locX -= MathHelper.cos(this.yaw / 180.0F * 3.141593F) * 0.16F;
/*  57 */     this.locY -= 0.1D;
/*  58 */     this.locZ -= MathHelper.sin(this.yaw / 180.0F * 3.141593F) * 0.16F;
/*  59 */     setPosition(this.locX, this.locY, this.locZ);
/*  60 */     this.length = 0.0F;
/*  61 */     this.be = 10.0D;
/*  62 */     this.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.141593F) * MathHelper.cos(this.pitch / 180.0F * 3.141593F));
/*  63 */     this.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.141593F) * MathHelper.cos(this.pitch / 180.0F * 3.141593F));
/*  64 */     this.motY = (-MathHelper.sin(this.pitch / 180.0F * 3.141593F));
/*  65 */     calcVelo(this.motX, this.motY, this.motZ, 1.991F, 1.0F);
/*     */   }
/*     */ 
/*     */   protected void b() {
/*     */   }
/*     */ 
/*     */   public void calcVelo(double var1, double var3, double var5, float var7, float var8) {
/*  72 */     float var9 = MathHelper.sqrt(var1 * var1 + var3 * var3 + var5 * var5);
/*  73 */     var1 /= var9;
/*  74 */     var3 /= var9;
/*  75 */     var5 /= var9;
/*  76 */     var1 *= var7;
/*  77 */     var3 *= var7;
/*  78 */     var5 *= var7;
/*  79 */     this.motX = var1;
/*  80 */     this.motY = var3;
/*  81 */     this.motZ = var5;
/*  82 */     float var10 = MathHelper.sqrt(var1 * var1 + var5 * var5);
/*  83 */     this.lastYaw = (this.yaw = (float)(Math.atan2(var1, var5) * 180.0D / 3.141592653589793D));
/*  84 */     this.lastPitch = (this.pitch = (float)(Math.atan2(var3, var10) * 180.0D / 3.141592653589793D));
/*     */   }
/*     */ 
/*     */   public void F_()
/*     */   {
/*  92 */     super.F_();
/*     */ 
/*  94 */     if ((!this.world.isStatic) && ((this.player == null) || (this.player.dead)))
/*     */     {
/*  96 */       die();
/*     */     }
/*     */ 
/*  99 */     if (this.inGround)
/*     */     {
/* 101 */       int var1 = this.world.getTypeId(this.xTile, this.yTile, this.zTile);
/*     */ 
/* 103 */       if (var1 == this.inTile)
/*     */       {
/* 105 */         die();
/* 106 */         return;
/*     */       }
/*     */ 
/* 109 */       this.inGround = false;
/* 110 */       this.motX *= this.random.nextFloat() * 0.2F;
/* 111 */       this.motY *= this.random.nextFloat() * 0.2F;
/* 112 */       this.motZ *= this.random.nextFloat() * 0.2F;
/*     */     }
/*     */     else
/*     */     {
/* 116 */       this.ticksInAir += 1;
/*     */     }
/*     */ 
/* 119 */     float var19 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/* 120 */     this.yaw = ((float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.141592653589793D));
/*     */ 
/* 122 */     for (this.pitch = ((float)(Math.atan2(this.motY, var19) * 180.0D / 3.141592653589793D)); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F);
/* 127 */     while (this.pitch - this.lastPitch >= 180.0F)
/*     */     {
/* 129 */       this.lastPitch += 360.0F;
/*     */     }
/*     */ 
/* 132 */     while (this.yaw - this.lastYaw < -180.0F)
/*     */     {
/* 134 */       this.lastYaw -= 360.0F;
/*     */     }
/*     */ 
/* 137 */     while (this.yaw - this.lastYaw >= 180.0F)
/*     */     {
/* 139 */       this.lastYaw += 360.0F;
/*     */     }
/*     */ 
/* 142 */     this.pitch = (this.lastPitch + (this.pitch - this.lastPitch) * 0.2F);
/* 143 */     this.yaw = (this.lastYaw + (this.yaw - this.lastYaw) * 0.2F);
/*     */ 
/* 146 */     if (h_())
/*     */     {
/* 148 */       for (int var2 = 0; var2 < 4; var2++)
/*     */       {
/* 150 */         float var3 = 0.25F;
/* 151 */         this.world.a("smoke", this.locX - this.motX * var3, this.locY - this.motY * var3, this.locZ - this.motZ * var3, this.motX, this.motY, this.motZ);
/*     */       }
/*     */ 
/* 154 */       this.world.makeSound(this, "random.fizzle", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
/* 155 */       die();
/*     */     }
/*     */ 
/* 158 */     this.lastX = this.locX;
/* 159 */     this.lastY = this.locY;
/* 160 */     this.lastZ = this.locZ;
/* 161 */     this.locX += this.motX;
/* 162 */     this.locY += this.motY;
/* 163 */     this.locZ += this.motZ;
/* 164 */     setPosition(this.locX, this.locY, this.locZ);
/*     */ 
/* 166 */     if ((this.locY >= 127.0D) && (this.motY > 0.0D))
/*     */     {
/* 168 */       if (EEProxy.getWorldInfo(this.world).hasStorm())
/*     */       {
/* 170 */         if (EEBase.consumeKleinStarPoint(this.player, 64))
/*     */         {
/* 172 */           if (!EEProxy.getWorldInfo(this.world).isThundering())
/*     */           {
/* 174 */             EEProxy.getWorldInfo(this.world).setThundering(true);
/* 175 */             EEProxy.getWorldInfo(this.world).setThunderDuration(300);
/*     */           }
/*     */           else
/*     */           {
/* 179 */             EEProxy.getWorldInfo(this.world).setThunderDuration(EEProxy.getWorldInfo(this.world).getThunderDuration() + 300);
/*     */           }
/*     */ 
/* 182 */           die();
/*     */         }
/* 184 */         else if (EEBase.Consume(new ItemStack(Item.REDSTONE, 1), this.player, true))
/*     */         {
/* 186 */           if (!EEProxy.getWorldInfo(this.world).isThundering())
/*     */           {
/* 188 */             EEProxy.getWorldInfo(this.world).setThundering(true);
/* 189 */             EEProxy.getWorldInfo(this.world).setThunderDuration(300);
/*     */           }
/*     */           else
/*     */           {
/* 193 */             EEProxy.getWorldInfo(this.world).setThunderDuration(EEProxy.getWorldInfo(this.world).getThunderDuration() + 300);
/*     */           }
/*     */ 
/* 196 */           die();
/*     */         }
/*     */         else
/*     */         {
/* 200 */           die();
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 205 */         die();
/*     */       }
/*     */     }
/*     */ 
/* 209 */     if (this.ticksInAir >= 3)
/*     */     {
/* 211 */       int var2 = MathHelper.floor(this.locX);
/* 212 */       int var20 = MathHelper.floor(this.locY);
/* 213 */       int var4 = MathHelper.floor(this.locZ);
/* 214 */       Vec3D var5 = Vec3D.create(this.locX, this.locY, this.locZ);
/* 215 */       Vec3D var6 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 216 */       MovingObjectPosition var7 = this.world.rayTrace(var5, var6, true);
/* 217 */       var5 = Vec3D.create(this.locX, this.locY, this.locZ);
/* 218 */       var6 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 219 */       Entity var8 = null;
/* 220 */       List var9 = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
/* 221 */       double var10 = 0.0D;
/*     */ 
/* 224 */       for (int var12 = 0; var12 < var9.size(); var12++)
/*     */       {
/* 226 */         Entity var13 = (Entity)var9.get(var12);
/*     */ 
/* 228 */         if (var13.o_())
/*     */         {
/* 230 */           float var14 = 0.3F;
/* 231 */           AxisAlignedBB var15 = var13.boundingBox.grow(var14, var14, var14);
/* 232 */           MovingObjectPosition var16 = var15.a(var5, var6);
/*     */ 
/* 234 */           if (var16 != null)
/*     */           {
/* 236 */             double var17 = var5.b(var16.pos);
/*     */ 
/* 238 */             if ((var17 < var10) || (var10 == 0.0D))
/*     */             {
/* 240 */               var8 = var13;
/* 241 */               var10 = var17;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 247 */       if (var8 != null)
/*     */       {
/* 249 */         var7 = new MovingObjectPosition(var8);
/*     */       }
/*     */ 
/* 252 */       if (var7 != null)
/*     */       {
/* 254 */         if (var7.type == EnumMovingObjectType.ENTITY)
/*     */         {
/* 256 */           var2 = MathHelper.floor(var7.entity.locX);
/* 257 */           var20 = MathHelper.floor(var7.entity.locY);
/* 258 */           var4 = MathHelper.floor(var7.entity.locZ);
/*     */ 
/* 260 */           if (this.world.isChunkLoaded(var2, var20, var4))
/*     */           {
/* 262 */             if (EEProxy.getWorldInfo(this.world).isThundering())
/*     */             {
/* 264 */               if (ConsumeGSD(2))
/*     */               {
/* 266 */                 callLightning(var2, var20, var4);
/*     */ 
/* 268 */                 for (int var12 = 0; var12 <= this.world.random.nextInt(3); var12++)
/*     */                 {
/* 270 */                   callLightning(var2, var20, var4);
/*     */                 }
/*     */               }
/*     */             }
/* 274 */             else if (EEProxy.getWorldInfo(this.world).hasStorm())
/*     */             {
/* 276 */               if (ConsumeGSD(2))
/*     */               {
/* 278 */                 callLightning(var2, var20, var4);
/*     */               }
/*     */             }
/* 281 */             else if (ConsumeRSD(1))
/*     */             {
/* 283 */               var7.entity.motX += this.motX * 2.0D;
/* 284 */               var7.entity.motY += 1.0D;
/* 285 */               var7.entity.motZ += this.motZ * 2.0D;
/*     */             }
/*     */           }
/* 288 */           else if (ConsumeRSD(1))
/*     */           {
/* 290 */             var7.entity.motX += this.motX * 2.0D;
/* 291 */             var7.entity.motY += 1.0D;
/* 292 */             var7.entity.motZ += this.motZ * 2.0D;
/*     */           }
/*     */ 
/* 295 */           die();
/*     */         }
/*     */ 
/* 298 */         if (EEProxy.getWorldInfo(this.world).isThundering())
/*     */         {
/* 300 */           if ((this.world.isChunkLoaded(var2, var20, var4)) && (ConsumeGSD(2)))
/*     */           {
/* 302 */             callLightning(var2, var20, var4);
/*     */ 
/* 304 */             for (int var12 = 0; var12 <= this.world.random.nextInt(3); var12++)
/*     */             {
/* 306 */               callLightning(var2, var20, var4);
/*     */             }
/*     */           }
/*     */         }
/* 310 */         else if ((EEProxy.getWorldInfo(this.world).hasStorm()) && (this.world.isChunkLoaded(var2, var20, var4)) && (ConsumeGSD(2)))
/*     */         {
/* 312 */           callLightning(var2, var20, var4);
/*     */         }
/*     */ 
/* 315 */         die();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean ConsumeRSD(int var1)
/*     */   {
/* 322 */     return EEBase.Consume(new ItemStack(Item.REDSTONE, var1), this.player, false) ? true : EEBase.consumeKleinStarPoint(this.player, 64 * var1) ? true : EEBase.Consume(new ItemStack(Item.COAL, var1 * 2, 1), this.player, true);
/*     */   }
/*     */ 
/*     */   private boolean ConsumeGSD(int var1)
/*     */   {
/* 327 */     return EEBase.Consume(new ItemStack(Item.REDSTONE, var1 * 12), this.player, false) ? true : EEBase.Consume(new ItemStack(Item.COAL, var1 * 6, 0), this.player, false) ? true : EEBase.Consume(new ItemStack(Item.SULPHUR, var1 * 2), this.player, false) ? true : EEBase.Consume(new ItemStack(Item.GLOWSTONE_DUST, var1), this.player, false) ? true : EEBase.consumeKleinStarPoint(this.player, 384 * var1) ? true : EEBase.Consume(new ItemStack(Item.COAL, var1 * 24, 1), this.player, true);
/*     */   }
/*     */ 
/*     */   public void callLightning(int var1, int var2, int var3)
/*     */   {
/* 332 */     this.world.strikeLightning(new EntityWeatherLighting(this.world, var1, var2, var3));
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
/* 347 */     return 0.0F;
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.EntityWindEssence
 * JD-Core Version:    0.6.2
 */