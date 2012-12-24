/*     */ package ee;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.AxisAlignedBB;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.BlockFire;
/*     */ import net.minecraft.server.DamageSource;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityItem;
/*     */ import net.minecraft.server.MathHelper;
/*     */ import net.minecraft.server.MovingObjectPosition;
/*     */ import net.minecraft.server.NBTTagCompound;
/*     */ import net.minecraft.server.Vec3D;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class EntityPyrokinesis extends Entity
/*     */ {
/*     */   private EntityHuman player;
/*     */   private Random rRand;
/*     */   private int xTile;
/*     */   private int yTile;
/*     */   private int zTile;
/*     */   private int inTile;
/*     */   private int yawDir;
/*  27 */   public static boolean grab = false;
/*     */   private boolean inGround;
/*     */   private int ticksInAir;
/*     */ 
/*     */   public EntityPyrokinesis(World var1)
/*     */   {
/*  33 */     super(var1);
/*  34 */     this.bf = true;
/*  35 */     b(0.98F, 0.98F);
/*  36 */     this.height = (this.length / 2.0F);
/*     */   }
/*     */ 
/*     */   public EntityPyrokinesis(World var1, double var2, double var4, double var6)
/*     */   {
/*  41 */     this(var1);
/*  42 */     setPosition(var2, var4, var6);
/*  43 */     this.ticksInAir = 0;
/*     */   }
/*     */ 
/*     */   public EntityPyrokinesis(World var1, EntityHuman var2)
/*     */   {
/*  48 */     super(var1);
/*  49 */     this.player = var2;
/*  50 */     this.xTile = -1;
/*  51 */     this.yTile = -1;
/*  52 */     this.zTile = -1;
/*  53 */     this.inTile = 0;
/*  54 */     this.ticksInAir = 0;
/*  55 */     this.inGround = false;
/*  56 */     this.yawDir = ((MathHelper.floor((var2.yaw + 180.0F) * 4.0F / 360.0F - 0.5D) & 0x3) + 1);
/*  57 */     b(0.5F, 0.5F);
/*  58 */     setPositionRotation(var2.locX, var2.locY + var2.getHeadHeight(), var2.locZ, var2.yaw, var2.pitch);
/*  59 */     this.locX -= MathHelper.cos(this.yaw / 180.0F * 3.141593F) * 0.16F;
/*  60 */     this.locY -= 0.1D;
/*  61 */     this.locZ -= MathHelper.sin(this.yaw / 180.0F * 3.141593F) * 0.16F;
/*  62 */     setPosition(this.locX, this.locY, this.locZ);
/*  63 */     this.length = 0.0F;
/*  64 */     this.be = 10.0D;
/*  65 */     this.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.141593F) * MathHelper.cos(this.pitch / 180.0F * 3.141593F));
/*  66 */     this.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.141593F) * MathHelper.cos(this.pitch / 180.0F * 3.141593F));
/*  67 */     this.motY = (-MathHelper.sin(this.pitch / 180.0F * 3.141593F));
/*  68 */     calcVelo(this.motX, this.motY, this.motZ, 1.999F, 1.0F);
/*  69 */     this.rRand = new Random();
/*     */   }
/*     */ 
/*     */   protected void b() {
/*     */   }
/*     */ 
/*     */   public void calcVelo(double var1, double var3, double var5, float var7, float var8) {
/*  76 */     float var9 = MathHelper.sqrt(var1 * var1 + var3 * var3 + var5 * var5);
/*  77 */     var1 /= var9;
/*  78 */     var3 /= var9;
/*  79 */     var5 /= var9;
/*  80 */     var1 *= var7;
/*  81 */     var3 *= var7;
/*  82 */     var5 *= var7;
/*  83 */     this.motX = var1;
/*  84 */     this.motY = var3;
/*  85 */     this.motZ = var5;
/*  86 */     float var10 = MathHelper.sqrt(var1 * var1 + var5 * var5);
/*  87 */     this.lastYaw = (this.yaw = (float)(Math.atan2(var1, var5) * 180.0D / 3.141592653589793D));
/*  88 */     this.lastPitch = (this.pitch = (float)(Math.atan2(var3, var10) * 180.0D / 3.141592653589793D));
/*     */   }
/*     */ 
/*     */   public void F_()
/*     */   {
/*  96 */     super.F_();
/*     */ 
/*  98 */     if ((!this.world.isStatic) && ((this.player == null) || (this.player.dead)))
/*     */     {
/* 100 */       die();
/*     */     }
/*     */ 
/* 103 */     if (this.inGround)
/*     */     {
/* 105 */       int var1 = this.world.getTypeId(this.xTile, this.yTile, this.zTile);
/*     */ 
/* 107 */       if (var1 == this.inTile)
/*     */       {
/* 109 */         return;
/*     */       }
/*     */ 
/* 112 */       this.inGround = false;
/* 113 */       this.motX *= this.random.nextFloat() * 0.2F;
/* 114 */       this.motY *= this.random.nextFloat() * 0.2F;
/* 115 */       this.motZ *= this.random.nextFloat() * 0.2F;
/*     */     }
/*     */     else
/*     */     {
/* 119 */       this.ticksInAir += 1;
/*     */     }
/*     */ 
/* 122 */     float var16 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/* 123 */     this.yaw = ((float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.141592653589793D));
/*     */ 
/* 125 */     for (this.pitch = ((float)(Math.atan2(this.motY, var16) * 180.0D / 3.141592653589793D)); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F);
/* 130 */     while (this.pitch - this.lastPitch >= 180.0F)
/*     */     {
/* 132 */       this.lastPitch += 360.0F;
/*     */     }
/*     */ 
/* 135 */     while (this.yaw - this.lastYaw < -180.0F)
/*     */     {
/* 137 */       this.lastYaw -= 360.0F;
/*     */     }
/*     */ 
/* 140 */     while (this.yaw - this.lastYaw >= 180.0F)
/*     */     {
/* 142 */       this.lastYaw += 360.0F;
/*     */     }
/*     */ 
/* 145 */     this.pitch = (this.lastPitch + (this.pitch - this.lastPitch) * 0.2F);
/* 146 */     this.yaw = (this.lastYaw + (this.yaw - this.lastYaw) * 0.2F);
/*     */ 
/* 149 */     if (h_())
/*     */     {
/* 151 */       for (int var2 = 0; var2 < 4; var2++)
/*     */       {
/* 153 */         float var3 = 0.25F;
/* 154 */         this.world.a("smoke", this.locX - this.motX * var3, this.locY - this.motY * var3, this.locZ - this.motZ * var3, this.motX, this.motY, this.motZ);
/*     */       }
/*     */ 
/* 157 */       this.world.makeSound(this, "heal", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
/* 158 */       die();
/*     */     }
/*     */ 
/* 161 */     this.lastX = this.locX;
/* 162 */     this.lastY = this.locY;
/* 163 */     this.lastZ = this.locZ;
/* 164 */     this.locX += this.motX;
/* 165 */     this.locY += this.motY;
/* 166 */     this.locZ += this.motZ;
/* 167 */     setPosition(this.locX, this.locY, this.locZ);
/* 168 */     this.world.a("smoke", this.locX, this.locY + 0.5D, this.locZ, 0.0D, 0.0D, 0.0D);
/*     */ 
/* 170 */     for (int var2 = 0; var2 <= 8; var2++)
/*     */     {
/* 172 */       this.world.a("flame", this.locX + (this.world.random.nextFloat() - 0.5D) / 3.5D, this.locY + (this.world.random.nextFloat() - 0.5D) / 3.5D, this.locZ + (this.world.random.nextFloat() - 0.5D) / 3.5D, this.motX * 0.8D, this.motY * 0.8D, this.motZ * 0.8D);
/*     */     }
/*     */ 
/* 175 */     if (this.ticksInAir >= 3)
/*     */     {
/* 177 */       Vec3D var18 = Vec3D.create(this.locX, this.locY, this.locZ);
/* 178 */       Vec3D var17 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 179 */       MovingObjectPosition var4 = this.world.rayTrace(var18, var17, grab);
/* 180 */       var18 = Vec3D.create(this.locX, this.locY, this.locZ);
/* 181 */       var17 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/*     */ 
/* 183 */       if (var4 != null)
/*     */       {
/* 185 */         var17 = Vec3D.create(var4.pos.a, var4.pos.b, var4.pos.c);
/*     */       }
/*     */ 
/* 188 */       Entity var5 = null;
/* 189 */       List var6 = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
/* 190 */       double var7 = 0.0D;
/*     */ 
/* 192 */       for (int var9 = 0; var9 < var6.size(); var9++)
/*     */       {
/* 194 */         Entity var10 = (Entity)var6.get(var9);
/*     */ 
/* 196 */         if ((!(var10 instanceof EntityHuman)) && (var10.o_()) && (this.ticksInAir >= 2))
/*     */         {
/* 198 */           float var11 = 0.3F;
/* 199 */           AxisAlignedBB var12 = var10.boundingBox.grow(var11, var11, var11);
/* 200 */           MovingObjectPosition var13 = var12.a(var18, var17);
/*     */ 
/* 202 */           if (var13 != null)
/*     */           {
/* 204 */             double var14 = var18.b(var13.pos);
/*     */ 
/* 206 */             if ((var14 < var7) || (var7 == 0.0D))
/*     */             {
/* 208 */               var5 = var10;
/* 209 */               var7 = var14;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 215 */       if (var5 != null)
/*     */       {
/* 217 */         var4 = new MovingObjectPosition(var5);
/* 218 */         var4.entity.damageEntity(DamageSource.LAVA, 2);
/* 219 */         var4.entity.setOnFire(100);
/* 220 */         fireBurst((float)var4.entity.locX, (float)var4.entity.locY, (float)var4.entity.locZ);
/*     */       }
/* 222 */       else if (var4 != null)
/*     */       {
/* 224 */         fireBurst(var4.b, var4.c, var4.d);
/* 225 */         die();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void fireBurst(float var1, float var2, float var3)
/*     */   {
/* 232 */     if (!EEProxy.isClient(this.world))
/*     */     {
/* 234 */       this.world.makeSound(var1 + 0.5F, var2 + 0.5F, var3 + 0.5F, "fire.ignite", 1.0F, this.rRand.nextFloat() * 0.4F + 0.8F);
/*     */ 
/* 237 */       if (this.player != null)
/*     */       {
/* 239 */         List var4 = this.world.getEntities(this.player, AxisAlignedBB.b(var1 - 2.0D, var2 - 2.0D, var3 - 2.0D, var1 + 2.0D, var2 + 2.0D, var3 + 2.0D));
/*     */ 
/* 241 */         for (int var5 = 0; var5 < var4.size(); var5++)
/*     */         {
/* 243 */           if (!(var4.get(var5) instanceof EntityItem))
/*     */           {
/* 245 */             Entity var6 = (Entity)var4.get(var5);
/* 246 */             EEProxy.dealFireDamage(var6, 5);
/* 247 */             var6.setOnFire(100);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 255 */       for (int var11 = -1; var11 <= 1; var11++)
/*     */       {
/* 257 */         for (int var5 = -1; var5 <= 1; var5++)
/*     */         {
/* 259 */           for (int var12 = -1; var12 <= 1; var12++)
/*     */           {
/* 261 */             if ((this.world.getTypeId((int)var1 + var11, (int)var2 + var5, (int)var3 + var12) == 0) || (this.world.getTypeId((int)var1 + var11, (int)var2 + var5, (int)var3 + var12) == 78))
/*     */             {
/* 263 */               this.world.setTypeId((int)var1 + var11, (int)var2 + var5, (int)var3 + var12, Block.FIRE.id);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 269 */       for (var11 = -2; var11 <= 2; var11++)
/*     */       {
/* 271 */         for (int var5 = -2; var5 <= 2; var5++)
/*     */         {
/* 273 */           for (int var12 = -2; var12 <= 2; var12++)
/*     */           {
/* 275 */             int var7 = (int)var1 + var11;
/* 276 */             int var8 = (int)var2 + var5;
/* 277 */             int var9 = (int)var3 + var12;
/* 278 */             int var10 = this.world.getTypeId(var7, var8, var9);
/*     */ 
/* 280 */             if (var10 == Block.OBSIDIAN.id)
/*     */             {
/* 282 */               this.world.setTypeIdAndData(var7, var8, var9, Block.LAVA.id, 0);
/*     */             }
/* 284 */             else if (var10 == Block.SAND.id)
/*     */             {
/* 286 */               this.world.setTypeId(var7, var8, var9, Block.GLASS.id);
/*     */             }
/* 288 */             else if (var10 == Block.ICE.id)
/*     */             {
/* 290 */               this.world.setTypeId(var7, var8, var9, Block.WATER.id);
/*     */             }
/*     */ 
/* 293 */             if ((this.world.random.nextInt(5) == 0) && (this.world.getTypeId(var7, var8 + 1, var9) == 0))
/*     */             {
/* 295 */               this.world.a("largesmoke", var7, var8 + 1, var9, 0.0D, 0.0D, 0.0D);
/* 296 */               this.world.a("flame", var7, var8 + 1, var9, 0.0D, 0.0D, 0.0D);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 302 */       this.world.a("largesmoke", var1, var2 + 1.0F, var3, 0.0D, 0.0D, 0.0D);
/* 303 */       this.world.a("flame", var1, var2, var3, 0.0D, 0.0D, 0.0D);
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
/* 319 */     return 0.0F;
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.EntityPyrokinesis
 * JD-Core Version:    0.6.2
 */