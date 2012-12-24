/*     */ package ee;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.AxisAlignedBB;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.MathHelper;
/*     */ import net.minecraft.server.MovingObjectPosition;
/*     */ import net.minecraft.server.NBTTagCompound;
/*     */ import net.minecraft.server.Vec3D;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class EntityHyperkinesis extends Entity
/*     */ {
/*     */   private float powerLevel;
/*     */   private int xTile;
/*     */   private int yTile;
/*     */   private int zTile;
/*     */   private int inTile;
/*     */   private int yawDir;
/*     */   private int cost;
/*  25 */   public static boolean grab = false;
/*     */   private boolean inGround;
/*     */   private EntityHuman player;
/*     */   private int ticksInAir;
/*     */ 
/*     */   public EntityHyperkinesis(World var1)
/*     */   {
/*  32 */     super(var1);
/*  33 */     this.bf = true;
/*  34 */     b(0.98F, 0.98F);
/*  35 */     this.height = (this.length / 2.0F);
/*     */   }
/*     */ 
/*     */   public EntityHyperkinesis(World var1, EntityHuman var2, int var3, int var4)
/*     */   {
/*  40 */     super(var1);
/*  41 */     this.player = var2;
/*  42 */     this.powerLevel = var3;
/*  43 */     this.cost = var4;
/*  44 */     this.xTile = -1;
/*  45 */     this.yTile = -1;
/*  46 */     this.zTile = -1;
/*  47 */     this.inTile = 0;
/*  48 */     this.ticksInAir = 0;
/*  49 */     this.inGround = false;
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
/*  62 */     calcVelo(this.motX, this.motY, this.motZ, 1.991F, 1.0F);
/*     */   }
/*     */ 
/*     */   public EntityHyperkinesis(World var1, double var2, double var4, double var6)
/*     */   {
/*  67 */     this(var1);
/*  68 */     setPosition(var2, var4, var6);
/*  69 */     this.ticksInAir = 0;
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
/* 148 */     if (h_())
/*     */     {
/* 150 */       for (int var2 = 0; var2 < 4; var2++)
/*     */       {
/* 152 */         float var3 = 0.25F;
/* 153 */         this.world.a("smoke", this.locX - this.motX * var3, this.locY - this.motY * var3, this.locZ - this.motZ * var3, this.motX, this.motY, this.motZ);
/*     */       }
/*     */ 
/* 156 */       this.world.makeSound(this, "random.fizz", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
/* 157 */       die();
/*     */     }
/*     */ 
/* 160 */     this.lastX = this.locX;
/* 161 */     this.lastY = this.locY;
/* 162 */     this.lastZ = this.locZ;
/* 163 */     this.locX += this.motX;
/* 164 */     this.locY += this.motY;
/* 165 */     this.locZ += this.motZ;
/* 166 */     setPosition(this.locX, this.locY, this.locZ);
/*     */ 
/* 168 */     if (this.ticksInAir >= 3)
/*     */     {
/* 170 */       Vec3D var17 = Vec3D.create(this.locX, this.locY, this.locZ);
/* 171 */       Vec3D var18 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 172 */       MovingObjectPosition var4 = this.world.rayTrace(var17, var18, grab);
/* 173 */       var17 = Vec3D.create(this.locX, this.locY, this.locZ);
/* 174 */       var18 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/*     */ 
/* 176 */       if (var4 != null)
/*     */       {
/* 178 */         var18 = Vec3D.create(var4.pos.a, var4.pos.b, var4.pos.c);
/*     */       }
/*     */ 
/* 181 */       Entity var5 = null;
/* 182 */       List var6 = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
/* 183 */       double var7 = 0.0D;
/*     */ 
/* 186 */       for (int var9 = 0; var9 < var6.size(); var9++)
/*     */       {
/* 188 */         Entity var10 = (Entity)var6.get(var9);
/*     */ 
/* 190 */         if ((var10.o_()) && (this.ticksInAir >= 2))
/*     */         {
/* 192 */           float var11 = 0.3F;
/* 193 */           AxisAlignedBB var12 = var10.boundingBox.grow(var11, var11, var11);
/* 194 */           MovingObjectPosition var13 = var12.a(var17, var18);
/*     */ 
/* 196 */           if (var13 != null)
/*     */           {
/* 198 */             double var14 = var17.b(var13.pos);
/*     */ 
/* 200 */             if ((var14 < var7) || (var7 == 0.0D))
/*     */             {
/* 202 */               var5 = var10;
/* 203 */               var7 = var14;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 209 */       if (var5 != null)
/*     */       {
/* 211 */         var4 = new MovingObjectPosition(var5);
/*     */       }
/*     */ 
/* 214 */       if (var4 != null)
/*     */       {
/* 216 */         List var19 = this.world.a(EntityHuman.class, AxisAlignedBB.b(this.locX - (2.0F + this.powerLevel), this.locY - (2.0F + this.powerLevel), this.locZ - (2.0F + this.powerLevel), this.locX + (2.0F + this.powerLevel), this.locY + (2.0F + this.powerLevel), this.locZ + (2.0F + this.powerLevel)));
/*     */ 
/* 218 */         if (var19.size() > 0)
/*     */         {
/* 220 */           for (int var20 = 0; var20 < 4; var20++)
/*     */           {
/* 222 */             float var11 = 0.25F;
/* 223 */             this.world.a("smoke", this.locX - this.motX * var11, this.locY - this.motY * var11, this.locZ - this.motZ * var11, this.motX, this.motY, this.motZ);
/*     */           }
/*     */ 
/* 226 */           this.world.makeSound(this, "random.fizz", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
/* 227 */           die();
/* 228 */           return;
/*     */         }
/*     */ 
/* 231 */         if (ConsumeReagent(true))
/*     */         {
/* 233 */           explode();
/*     */         }
/*     */ 
/* 236 */         die();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean ConsumeReagent(boolean var1)
/*     */   {
/* 243 */     return EEBase.Consume(new ItemStack(Item.REDSTONE, this.cost * 6), this.player, var1) ? true : EEBase.Consume(new ItemStack(Item.COAL, this.cost * 3, 0), this.player, var1) ? true : EEBase.Consume(new ItemStack(Item.SULPHUR, this.cost * 2), this.player, var1) ? true : EEBase.Consume(new ItemStack(Item.GLOWSTONE_DUST, this.cost), this.player, var1) ? true : EEBase.consumeKleinStarPoint(this.player, this.cost * 384) ? true : EEProxy.isClient(this.world) ? false : EEBase.Consume(new ItemStack(Item.COAL, this.cost * 12, 1), this.player, var1);
/*     */   }
/*     */ 
/*     */   private void explode()
/*     */   {
/* 248 */     float var1 = 1.0F + 2.0F * (this.powerLevel / 3.0F);
/* 249 */     newCombustion(null, this.locX, this.locY, this.locZ, var1);
/*     */   }
/*     */ 
/*     */   public Combustion newCombustion(EntityHuman var1, double var2, double var4, double var6, float var8)
/*     */   {
/* 254 */     Combustion var9 = new Combustion(this.world, var1, var2, var4, var6, var8);
/* 255 */     var9.doExplosionA();
/* 256 */     var9.doExplosionB(true);
/* 257 */     return var9;
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
/* 272 */     return 0.0F;
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.EntityHyperkinesis
 * JD-Core Version:    0.6.2
 */