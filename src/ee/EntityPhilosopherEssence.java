/*     */ package ee;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.AxisAlignedBB;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityBlaze;
/*     */ import net.minecraft.server.EntityCaveSpider;
/*     */ import net.minecraft.server.EntityChicken;
/*     */ import net.minecraft.server.EntityCow;
/*     */ import net.minecraft.server.EntityCreeper;
/*     */ import net.minecraft.server.EntityEnderman;
/*     */ import net.minecraft.server.EntityGhast;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityIronGolem;
/*     */ import net.minecraft.server.EntityMagmaCube;
/*     */ import net.minecraft.server.EntityMushroomCow;
/*     */ import net.minecraft.server.EntityOcelot;
/*     */ import net.minecraft.server.EntityPig;
/*     */ import net.minecraft.server.EntityPigZombie;
/*     */ import net.minecraft.server.EntitySheep;
/*     */ import net.minecraft.server.EntitySkeleton;
/*     */ import net.minecraft.server.EntitySlime;
/*     */ import net.minecraft.server.EntitySnowman;
/*     */ import net.minecraft.server.EntitySpider;
/*     */ import net.minecraft.server.EntityVillager;
/*     */ import net.minecraft.server.EntityWolf;
/*     */ import net.minecraft.server.EntityZombie;
/*     */ import net.minecraft.server.EnumMovingObjectType;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.MathHelper;
/*     */ import net.minecraft.server.MovingObjectPosition;
/*     */ import net.minecraft.server.NBTTagCompound;
/*     */ import net.minecraft.server.TileEntity;
/*     */ import net.minecraft.server.Vec3D;
/*     */ import net.minecraft.server.World;
/*     */ import net.minecraft.server.WorldProvider;
/*     */ 
/*     */ public class EntityPhilosopherEssence extends Entity
/*     */ {
/*     */   private EntityHuman player;
/*     */   private int xTile;
/*     */   private int yTile;
/*     */   private int zTile;
/*     */   private int inTile;
/*     */   private int yawDir;
/*     */   private int ticksInAir;
/*     */   public int chargeLevel;
/*  49 */   public static boolean grab = false;
/*     */   private boolean inGround;
/*     */ 
/*     */   public EntityPhilosopherEssence(World var1)
/*     */   {
/*  54 */     super(var1);
/*  55 */     this.bf = true;
/*  56 */     b(0.98F, 0.98F);
/*  57 */     this.height = (this.length / 2.0F);
/*     */   }
/*     */ 
/*     */   public EntityPhilosopherEssence(World var1, double var2, double var4, double var6)
/*     */   {
/*  62 */     this(var1);
/*  63 */     setPosition(var2, var4, var6);
/*     */   }
/*     */ 
/*     */   public EntityPhilosopherEssence(World var1, EntityHuman var2, int var3)
/*     */   {
/*  68 */     super(var1);
/*  69 */     this.chargeLevel = var3;
/*  70 */     this.xTile = -1;
/*  71 */     this.yTile = -1;
/*  72 */     this.zTile = -1;
/*  73 */     this.inTile = 0;
/*  74 */     this.inGround = false;
/*  75 */     this.player = var2;
/*  76 */     this.yawDir = ((MathHelper.floor((var2.yaw + 180.0F) * 4.0F / 360.0F - 0.5D) & 0x3) + 1);
/*  77 */     b(0.5F, 0.5F);
/*  78 */     setPositionRotation(var2.locX, var2.locY + var2.getHeadHeight(), var2.locZ, var2.yaw, var2.pitch);
/*  79 */     this.locX -= MathHelper.cos(this.yaw / 180.0F * 3.141593F) * 0.16F;
/*  80 */     this.locY -= 0.1D;
/*  81 */     this.locZ -= MathHelper.sin(this.yaw / 180.0F * 3.141593F) * 0.16F;
/*  82 */     setPosition(this.locX, this.locY, this.locZ);
/*  83 */     this.length = 0.0F;
/*  84 */     this.be = 10.0D;
/*  85 */     this.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.141593F) * MathHelper.cos(this.pitch / 180.0F * 3.141593F));
/*  86 */     this.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.141593F) * MathHelper.cos(this.pitch / 180.0F * 3.141593F));
/*  87 */     this.motY = (-MathHelper.sin(this.pitch / 180.0F * 3.141593F));
/*  88 */     calcVelo(this.motX, this.motY, this.motZ, 1.991F, 1.0F);
/*  89 */     this.ticksInAir = 0;
/*     */   }
/*     */ 
/*     */   protected void b() {
/*     */   }
/*     */ 
/*     */   public void calcVelo(double var1, double var3, double var5, float var7, float var8) {
/*  96 */     float var9 = MathHelper.sqrt(var1 * var1 + var3 * var3 + var5 * var5);
/*  97 */     var1 /= var9;
/*  98 */     var3 /= var9;
/*  99 */     var5 /= var9;
/* 100 */     var1 *= var7;
/* 101 */     var3 *= var7;
/* 102 */     var5 *= var7;
/* 103 */     this.motX = var1;
/* 104 */     this.motY = var3;
/* 105 */     this.motZ = var5;
/* 106 */     float var10 = MathHelper.sqrt(var1 * var1 + var5 * var5);
/* 107 */     this.lastYaw = (this.yaw = (float)(Math.atan2(var1, var5) * 180.0D / 3.141592653589793D));
/* 108 */     this.lastPitch = (this.pitch = (float)(Math.atan2(var3, var10) * 180.0D / 3.141592653589793D));
/*     */   }
/*     */ 
/*     */   public void F_()
/*     */   {
/* 116 */     super.F_();
/*     */ 
/* 118 */     if ((!this.world.isStatic) && ((this.player == null) || (this.player.dead)))
/*     */     {
/* 120 */       die();
/*     */     }
/*     */ 
/* 123 */     if (this.inGround)
/*     */     {
/* 125 */       int var1 = this.world.getTypeId(this.xTile, this.yTile, this.zTile);
/*     */ 
/* 127 */       if (var1 == this.inTile)
/*     */       {
/* 129 */         die();
/* 130 */         return;
/*     */       }
/*     */ 
/* 133 */       this.inGround = false;
/* 134 */       this.motX *= this.random.nextFloat() * 0.2F;
/* 135 */       this.motY *= this.random.nextFloat() * 0.2F;
/* 136 */       this.motZ *= this.random.nextFloat() * 0.2F;
/*     */     }
/*     */     else
/*     */     {
/* 140 */       this.ticksInAir += 1;
/*     */     }
/*     */ 
/* 143 */     float var16 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/* 144 */     this.yaw = ((float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.141592653589793D));
/*     */ 
/* 146 */     for (this.pitch = ((float)(Math.atan2(this.motY, var16) * 180.0D / 3.141592653589793D)); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F);
/* 151 */     while (this.pitch - this.lastPitch >= 180.0F)
/*     */     {
/* 153 */       this.lastPitch += 360.0F;
/*     */     }
/*     */ 
/* 156 */     while (this.yaw - this.lastYaw < -180.0F)
/*     */     {
/* 158 */       this.lastYaw -= 360.0F;
/*     */     }
/*     */ 
/* 161 */     while (this.yaw - this.lastYaw >= 180.0F)
/*     */     {
/* 163 */       this.lastYaw += 360.0F;
/*     */     }
/*     */ 
/* 166 */     this.pitch = (this.lastPitch + (this.pitch - this.lastPitch) * 0.2F);
/* 167 */     this.yaw = (this.lastYaw + (this.yaw - this.lastYaw) * 0.2F);
/*     */ 
/* 169 */     if (h_())
/*     */     {
/* 171 */       for (int var2 = 0; var2 < 4; var2++)
/*     */       {
/* 173 */         float var3 = 0.25F;
/* 174 */         this.world.a("smoke", this.locX - this.motX * var3, this.locY - this.motY * var3, this.locZ - this.motZ * var3, this.motX, this.motY, this.motZ);
/*     */       }
/*     */ 
/* 177 */       this.world.makeSound(this, "random.fizz", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
/* 178 */       die();
/*     */     }
/*     */ 
/* 181 */     this.lastX = this.locX;
/* 182 */     this.lastY = this.locY;
/* 183 */     this.lastZ = this.locZ;
/* 184 */     this.locX += this.motX;
/* 185 */     this.locY += this.motY;
/* 186 */     this.locZ += this.motZ;
/* 187 */     setPosition(this.locX, this.locY, this.locZ);
/* 188 */     this.ticksInAir += 1;
/*     */ 
/* 190 */     if (this.ticksInAir >= 120)
/*     */     {
/* 192 */       die();
/*     */     }
/*     */ 
/* 195 */     if (this.ticksInAir >= 3)
/*     */     {
/* 197 */       Vec3D var17 = Vec3D.create(this.locX, this.locY, this.locZ);
/* 198 */       Vec3D var18 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 199 */       MovingObjectPosition var4 = this.world.rayTrace(var17, var18, true);
/* 200 */       var17 = Vec3D.create(this.locX, this.locY, this.locZ);
/* 201 */       var18 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 202 */       Entity var5 = null;
/* 203 */       List var6 = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
/* 204 */       double var7 = 0.0D;
/*     */ 
/* 206 */       for (int var9 = 0; var9 < var6.size(); var9++)
/*     */       {
/* 208 */         Entity var10 = (Entity)var6.get(var9);
/*     */ 
/* 210 */         if (var10.o_())
/*     */         {
/* 212 */           float var11 = 0.3F;
/* 213 */           AxisAlignedBB var12 = var10.boundingBox.grow(var11, var11, var11);
/* 214 */           MovingObjectPosition var13 = var12.a(var17, var18);
/*     */ 
/* 216 */           if (var13 != null)
/*     */           {
/* 218 */             double var14 = var17.b(var13.pos);
/*     */ 
/* 220 */             if ((var14 < var7) || (var7 == 0.0D))
/*     */             {
/* 222 */               var5 = var10;
/* 223 */               var7 = var14;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 229 */       if (var5 != null)
/*     */       {
/* 231 */         var4 = new MovingObjectPosition(var5);
/*     */       }
/*     */ 
/* 234 */       if (var4 != null)
/*     */       {
/* 236 */         if (var4.type == EnumMovingObjectType.ENTITY)
/*     */         {
/* 238 */           transmuteMob(var4.entity);
/* 239 */           die();
/*     */         }
/* 241 */         else if (var4.type == EnumMovingObjectType.TILE)
/*     */         {
/* 243 */           TileEntity var19 = this.world.getTileEntity(var4.b, var4.c, var4.d);
/*     */ 
/* 245 */           if ((var19 instanceof TilePedestal))
/*     */           {
/* 247 */             if (this.player != null)
/*     */             {
/* 249 */               ((TilePedestal)var19).activate(this.player);
/*     */             }
/*     */ 
/* 252 */             die();
/*     */           }
/*     */           else
/*     */           {
/* 256 */             die();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void transmuteMob(Entity var1)
/*     */   {
/* 265 */     Object var2 = null;
/* 266 */     int var3 = this.world.random.nextInt(100);
/*     */ 
/* 268 */     if (EEBase.isNeutralEntity(var1))
/*     */     {
/* 270 */       if ((var1 instanceof EntitySnowman))
/*     */       {
/* 272 */         var2 = new EntityIronGolem(this.world);
/*     */       }
/* 274 */       else if (!(var1 instanceof EntityIronGolem))
/*     */       {
/* 276 */         if ((var3 < 10) && (!(var1 instanceof EntityWolf)))
/*     */         {
/* 278 */           var2 = new EntityWolf(this.world);
/*     */         }
/* 280 */         else if ((var3 < 30) && (!(var1 instanceof EntityChicken)))
/*     */         {
/* 282 */           var2 = new EntityChicken(this.world);
/*     */         }
/* 284 */         else if ((var3 < 50) && (!(var1 instanceof EntityCow)))
/*     */         {
/* 286 */           var2 = new EntityCow(this.world);
/*     */         }
/* 288 */         else if ((var3 < 70) && (!(var1 instanceof EntityPig)))
/*     */         {
/* 290 */           var2 = new EntityPig(this.world);
/*     */         }
/* 292 */         else if ((var3 < 90) && (!(var1 instanceof EntitySheep)))
/*     */         {
/* 294 */           var2 = new EntitySheep(this.world);
/* 295 */           ((EntitySheep)var2).setColor(this.world.random.nextInt(16));
/*     */         }
/* 297 */         else if ((var3 < 94) && (!(var1 instanceof EntityVillager)))
/*     */         {
/* 299 */           var2 = new EntityVillager(this.world);
/*     */         }
/* 301 */         else if ((var3 < 97) && (!(var1 instanceof EntityMushroomCow)))
/*     */         {
/* 303 */           var2 = new EntityMushroomCow(this.world);
/*     */         }
/* 305 */         else if ((var3 < 100) && (!(var1 instanceof EntityOcelot)))
/*     */         {
/* 307 */           var2 = new EntityOcelot(this.world);
/*     */         }
/*     */       }
/*     */     }
/* 311 */     else if (EEBase.isHostileEntity(var1))
/*     */     {
/* 313 */       if (!this.world.worldProvider.d)
/*     */       {
/* 315 */         if ((var3 < 15) && (!(var1 instanceof EntityCreeper)))
/*     */         {
/* 317 */           var2 = new EntityCreeper(this.world);
/*     */         }
/* 319 */         else if ((var3 < 30) && (!(var1 instanceof EntitySkeleton)))
/*     */         {
/* 321 */           var2 = new EntitySkeleton(this.world);
/*     */         }
/* 323 */         else if ((var3 < 45) && (!(var1 instanceof EntitySpider)))
/*     */         {
/* 325 */           var2 = new EntitySpider(this.world);
/*     */         }
/* 327 */         else if ((var3 < 60) && (!(var1 instanceof EntityZombie)))
/*     */         {
/* 329 */           var2 = new EntityZombie(this.world);
/*     */         }
/* 331 */         else if ((var3 < 75) && (!(var1 instanceof EntitySlime)))
/*     */         {
/* 333 */           var2 = new EntitySlime(this.world);
/* 334 */           ((EntitySlime)var2).setSize(1 << this.world.random.nextInt(3));
/*     */         }
/* 336 */         else if ((var3 < 90) && (!(var1 instanceof EntityEnderman)))
/*     */         {
/* 338 */           var2 = new EntityEnderman(this.world);
/*     */         }
/* 340 */         else if ((var3 < 100) && (!(var1 instanceof EntityCaveSpider)))
/*     */         {
/* 342 */           var2 = new EntityCaveSpider(this.world);
/*     */         }
/*     */       }
/* 345 */       else if ((var3 < 50) && (!(var1 instanceof EntityPigZombie)))
/*     */       {
/* 347 */         var2 = new EntityPigZombie(this.world);
/*     */       }
/* 349 */       else if ((var3 < 80) && (!(var1 instanceof EntityGhast)))
/*     */       {
/* 351 */         var2 = new EntityGhast(this.world);
/*     */       }
/* 353 */       else if ((var3 < 95) && (!(var1 instanceof EntityMagmaCube)))
/*     */       {
/* 355 */         var2 = new EntityMagmaCube(this.world);
/*     */       }
/* 357 */       else if ((var3 < 100) && (!(var1 instanceof EntityBlaze)))
/*     */       {
/* 359 */         var2 = new EntityBlaze(this.world);
/*     */       }
/*     */     }
/*     */ 
/* 363 */     if (var2 != null)
/*     */     {
/* 365 */       int var4 = 1;
/*     */ 
/* 368 */       if ((var2 instanceof EntityIronGolem))
/*     */       {
/* 370 */         int var5 = 4 * EEMaps.getEMC(Block.IRON_BLOCK.id) + EEMaps.getEMC(Block.PUMPKIN.id);
/* 371 */         int var6 = 2 * EEMaps.getEMC(Block.SNOW.id) + EEMaps.getEMC(Block.PUMPKIN.id);
/* 372 */         var4 = Math.round((var5 - var6) / EEMaps.getEMC(Item.GLOWSTONE_DUST.id));
/*     */       }
/*     */ 
/* 375 */       if (ConsumeGlowstone(var4, true))
/*     */       {
/* 377 */         ((Entity)var2).setPosition(var1.locX, var1.locY, var1.locZ);
/* 378 */         var1.die();
/* 379 */         this.world.makeSound((Entity)var2, "transmute", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
/* 380 */         this.world.addEntity((Entity)var2);
/*     */ 
/* 382 */         for (int var5 = 0; var5 < 4; var5++)
/*     */         {
/* 384 */           this.world.a("largesmoke", ((Entity)var2).locX, ((Entity)var2).locY, ((Entity)var2).locZ, 0.0D, 0.1D, 0.0D);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean ConsumeRedstone(int var1, boolean var2)
/*     */   {
/* 392 */     return EEBase.Consume(new ItemStack(Item.REDSTONE, var1), this.player, false) ? true : EEBase.consumeKleinStarPoint(this.player, 64 * var1) ? true : EEBase.Consume(new ItemStack(Item.COAL, var1 * 2, 1), this.player, true);
/*     */   }
/*     */ 
/*     */   public boolean ConsumeGlowstone(int var1, boolean var2)
/*     */   {
/* 397 */     return EEBase.Consume(new ItemStack(Item.REDSTONE, var1 * 12), this.player, false) ? true : EEBase.Consume(new ItemStack(Item.COAL, var1 * 6, 0), this.player, false) ? true : EEBase.Consume(new ItemStack(Item.SULPHUR, var1 * 2), this.player, false) ? true : EEBase.Consume(new ItemStack(Item.GLOWSTONE_DUST, var1), this.player, false) ? true : EEBase.consumeKleinStarPoint(this.player, 384 * var1) ? true : EEBase.Consume(new ItemStack(Item.COAL, var1 * 24, 1), this.player, true);
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
/* 412 */     return 0.0F;
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.EntityPhilosopherEssence
 * JD-Core Version:    0.6.2
 */