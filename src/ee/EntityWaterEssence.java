/*     */ package ee;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.AxisAlignedBB;
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
/*     */ public class EntityWaterEssence extends Entity
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
/*     */   public EntityWaterEssence(World var1)
/*     */   {
/*  32 */     super(var1);
/*  33 */     this.bf = true;
/*  34 */     b(0.98F, 0.98F);
/*  35 */     this.height = (this.length / 2.0F);
/*     */   }
/*     */ 
/*     */   public EntityWaterEssence(World var1, double var2, double var4, double var6)
/*     */   {
/*  40 */     this(var1);
/*  41 */     setPosition(var2, var4, var6);
/*     */   }
/*     */ 
/*     */   public EntityWaterEssence(World var1, EntityHuman var2)
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
/* 166 */     if ((this.locY > 256.0D) && (this.motY > 0.0D))
/*     */     {
/* 168 */       if (!EEProxy.getWorldInfo(this.world).hasStorm())
/*     */       {
/* 170 */         EEProxy.getWorldInfo(this.world).setStorm(true);
/* 171 */         EEProxy.getWorldInfo(this.world).setWeatherDuration(600);
/*     */       }
/*     */       else
/*     */       {
/* 175 */         EEProxy.getWorldInfo(this.world).setWeatherDuration(EEProxy.getWorldInfo(this.world).getWeatherDuration() + 600);
/*     */       }
/*     */ 
/* 178 */       die();
/*     */     }
/*     */ 
/* 181 */     int var2 = MathHelper.floor(this.locX);
/* 182 */     int var20 = MathHelper.floor(this.locY);
/* 183 */     int var4 = MathHelper.floor(this.locZ);
/*     */ 
/* 185 */     for (int var5 = -1; var5 <= 1; var5++)
/*     */     {
/* 187 */       for (int var6 = -1; var6 <= 1; var6++)
/*     */       {
/* 189 */         for (int var7 = -1; var7 <= 1; var7++)
/*     */         {
/* 191 */           if (this.world.getMaterial(var2 + var5, var20 + var6, var4 + var7) == Material.LAVA)
/*     */           {
/* 193 */             if (this.world.getData(var2 + var5, var20 + var6, var4 + var7) == 0)
/*     */             {
/* 195 */               this.world.setTypeId(var2 + var5, var20 + var6, var4 + var7, 49);
/* 196 */               this.world.a("smoke", var2 + var5, var20 + var6, var4 + var7, 0.0D, 0.1D, 0.0D);
/*     */             }
/* 198 */             else if (this.world.getData(var2 + var5, var20 + var6, var4 + var7) <= 4)
/*     */             {
/* 200 */               this.world.setTypeId(var2 + var5, var20 + var6, var4 + var7, 4);
/* 201 */               this.world.a("smoke", var2 + var5, var20 + var6, var4 + var7, 0.0D, 0.1D, 0.0D);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 208 */     if (this.ticksInAir >= 3)
/*     */     {
/* 210 */       Vec3D var21 = Vec3D.create(this.locX, this.locY, this.locZ);
/* 211 */       Vec3D var22 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 212 */       MovingObjectPosition var23 = this.world.rayTrace(var21, var22, true);
/* 213 */       var21 = Vec3D.create(this.locX, this.locY, this.locZ);
/* 214 */       var22 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 215 */       Entity var8 = null;
/* 216 */       List var9 = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
/* 217 */       double var10 = 0.0D;
/*     */ 
/* 219 */       for (int var12 = 0; var12 < var9.size(); var12++)
/*     */       {
/* 221 */         Entity var13 = (Entity)var9.get(var12);
/*     */ 
/* 223 */         if (var13.o_())
/*     */         {
/* 225 */           float var14 = 0.3F;
/* 226 */           AxisAlignedBB var15 = var13.boundingBox.grow(var14, var14, var14);
/* 227 */           MovingObjectPosition var16 = var15.a(var21, var22);
/*     */ 
/* 229 */           if (var16 != null)
/*     */           {
/* 231 */             double var17 = var21.b(var16.pos);
/*     */ 
/* 233 */             if ((var17 < var10) || (var10 == 0.0D))
/*     */             {
/* 235 */               var8 = var13;
/* 236 */               var10 = var17;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 242 */       if (var8 != null)
/*     */       {
/* 244 */         var23 = new MovingObjectPosition(var8);
/*     */       }
/*     */ 
/* 247 */       if (var23 != null)
/*     */       {
/* 249 */         if (var23.type == EnumMovingObjectType.ENTITY)
/*     */         {
/* 251 */           var2 = MathHelper.floor(var23.entity.locX);
/* 252 */           var20 = MathHelper.floor(var23.entity.locY);
/* 253 */           var4 = MathHelper.floor(var23.entity.locZ);
/* 254 */           makeWater(var2, var20, var4);
/* 255 */           die();
/*     */         }
/*     */ 
/* 258 */         makeWater(var2, var20, var4);
/* 259 */         die();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean ConsumeRSD(int var1)
/*     */   {
/* 266 */     return EEBase.Consume(new ItemStack(Item.REDSTONE, var1), this.player, false) ? true : EEBase.consumeKleinStarPoint(this.player, 64) ? true : EEBase.Consume(new ItemStack(Item.COAL, var1 * 2, 1), this.player, true);
/*     */   }
/*     */ 
/*     */   public void makeWater(int var1, int var2, int var3)
/*     */   {
/* 271 */     if (this.world.getTypeId(var1, var2, var3) == 0)
/*     */     {
/* 273 */       this.world.setTypeId(var1, var2, var3, 8);
/*     */     }
/* 275 */     else if ((this.world.getTypeId(var1, var2, var3) != 11) && ((this.world.getTypeId(var1, var2, var3) != 10) || (this.world.getData(var1, var2, var3) <= 14)))
/*     */     {
/* 277 */       if ((this.world.getTypeId(var1, var2, var3) == 10) || ((this.world.getTypeId(var1, var2, var3) == 11) && (this.world.getData(var1, var2, var3) < 15)))
/*     */       {
/* 279 */         this.world.setTypeId(var1, var2, var3, 4);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 284 */       this.world.setTypeId(var1, var2, var3, 49);
/*     */     }
/*     */ 
/* 287 */     die();
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
/* 302 */     return 0.0F;
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.EntityWaterEssence
 * JD-Core Version:    0.6.2
 */