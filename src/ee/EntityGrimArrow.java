/*     */ package ee;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.AxisAlignedBB;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityCreature;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityLiving;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.Vec3D;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class EntityGrimArrow extends EntityEEProjectile
/*     */ {
/*     */   public String name;
/*     */   public int itemId;
/*     */   public int craftingResults;
/*     */   public Object tip;
/*     */   public String spriteFile;
/*     */   public Entity target;
/*     */   public boolean homing;
/*     */   public static final int ticksBeforeCollidable = 2;
/*  23 */   public static int[][] candidates = { new int[3], { 0, -1 }, { 0, 1 }, { -1 }, { 1 }, { 0, 0, -1 }, { 0, 0, 1 }, { -1, -1 }, { -1, 0, -1 }, { -1, 0, 1 }, { -1, 1 }, { 0, -1, -1 }, { 0, -1, 1 }, { 0, 1, -1 }, { 0, 1, 1 }, { 1, -1 }, { 1, 0, -1 }, { 1, 0, 1 }, { 1, 1 }, { -1, -1, -1 }, { -1, -1, 1 }, { -1, 1, -1 }, { -1, 1, 1 }, { 1, -1, -1 }, { 1, -1, 1 }, { 1, 1, -1 }, { 1, 1, 1 } };
/*     */ 
/*     */   public void b()
/*     */   {
/*  27 */     super.b();
/*  28 */     this.homing = true;
/*  29 */     this.name = "Arrow";
/*  30 */     this.itemId = Item.ARROW.id;
/*  31 */     this.craftingResults = 4;
/*  32 */     this.tip = Item.FLINT;
/*  33 */     this.spriteFile = null;
/*  34 */     this.curvature = 0.03F;
/*  35 */     this.slowdown = 0.99F;
/*  36 */     this.precision = (0.35F + this.world.random.nextFloat() * 5.0F);
/*  37 */     this.speed = 1.5F;
/*  38 */     this.height = 0.0F;
/*  39 */     b(0.5F, 0.5F);
/*  40 */     this.item = new ItemStack(this.itemId, 1, 0);
/*     */   }
/*     */ 
/*     */   public EntityGrimArrow newArrow(World var1, EntityLiving var2)
/*     */   {
/*     */     try
/*     */     {
/*  47 */       return (EntityGrimArrow)getClass().getConstructor(new Class[] { World.class, EntityLiving.class }).newInstance(new Object[] { var1, var2 });
/*     */     }
/*     */     catch (Throwable var4)
/*     */     {
/*  51 */       throw new RuntimeException("Could not construct arrow instance", var4);
/*     */     }
/*     */   }
/*     */ 
/*     */   public EntityGrimArrow newArrow(World var1)
/*     */   {
/*     */     try
/*     */     {
/*  59 */       return (EntityGrimArrow)getClass().getConstructor(new Class[] { World.class }).newInstance(new Object[] { var1 });
/*     */     }
/*     */     catch (Throwable var3)
/*     */     {
/*  63 */       throw new RuntimeException("Could not construct arrow instance", var3);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setupConfig() {
/*     */   }
/*     */ 
/*     */   public EntityGrimArrow(World var1) {
/*  71 */     super(var1);
/*  72 */     this.homing = true;
/*  73 */     b();
/*     */   }
/*     */ 
/*     */   public EntityGrimArrow(World var1, double var2, double var4, double var6)
/*     */   {
/*  78 */     super(var1, var2, var4, var6);
/*  79 */     this.homing = true;
/*  80 */     b();
/*     */   }
/*     */ 
/*     */   public EntityGrimArrow(World var1, EntityLiving var2)
/*     */   {
/*  85 */     super(var1, var2);
/*  86 */     this.homing = (this.shooter instanceof EntityHuman);
/*  87 */     b();
/*     */   }
/*     */ 
/*     */   public boolean attackEntityFrom(Entity var1, int var2)
/*     */   {
/*  92 */     if (var2 < 8)
/*     */     {
/*  94 */       return false;
/*     */     }
/*     */ 
/*  98 */     if ((!this.inGround) && (var1 != null))
/*     */     {
/* 100 */       Vec3D var3 = var1.aJ();
/*     */ 
/* 102 */       if (var3 != null)
/*     */       {
/* 104 */         if (this.homing)
/*     */         {
/* 106 */           this.target = this.shooter;
/*     */ 
/* 108 */           if ((var1 instanceof EntityLiving))
/*     */           {
/* 110 */             this.shooter = ((EntityLiving)var1);
/*     */           }
/*     */         }
/*     */ 
/* 114 */         this.motX = var3.a;
/* 115 */         this.motY = var3.b;
/* 116 */         this.motZ = var3.c;
/* 117 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 121 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isInSight(Entity var1)
/*     */   {
/* 127 */     return this.world.a(Vec3D.create(this.locX, this.locY + getHeadHeight(), this.locZ), Vec3D.create(var1.locX, var1.locY + var1.getHeadHeight(), var1.locZ)) == null;
/*     */   }
/*     */ 
/*     */   public void handleMotionUpdate()
/*     */   {
/* 132 */     if (!this.homing)
/*     */     {
/* 134 */       float var1 = this.slowdown;
/*     */ 
/* 136 */       if (h_())
/*     */       {
/* 138 */         for (int var2 = 0; var2 < 4; var2++)
/*     */         {
/* 140 */           float var3 = 0.25F;
/* 141 */           this.world.a("bubble", this.locX - this.motX * var3, this.locY - this.motY * var3, this.locZ - this.motZ * var3, this.motX, this.motY, this.motZ);
/*     */         }
/*     */ 
/* 144 */         var1 *= 0.8F;
/*     */       }
/*     */ 
/* 147 */       this.motX *= var1;
/* 148 */       this.motY *= var1;
/* 149 */       this.motZ *= var1;
/*     */     }
/*     */ 
/* 152 */     if (this.target == null)
/*     */     {
/* 154 */       this.motY -= this.curvature;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void tickFlying()
/*     */   {
/* 160 */     if ((this.ticksFlying > 3) && (this.homing))
/*     */     {
/* 162 */       if ((this.target != null) && (!this.target.dead) && ((!(this.target instanceof EntityLiving)) || (((EntityLiving)this.target).deathTicks == 0)))
/*     */       {
/* 164 */         if (((this.shooter instanceof EntityHuman)) && (!isInSight(this.target)))
/*     */         {
/* 166 */           this.target = getTarget(this.locX, this.locY, this.locZ, 16.0D);
/*     */         }
/*     */       }
/* 169 */       else if ((this.shooter instanceof EntityCreature))
/*     */       {
/* 171 */         this.target = ((EntityCreature)this.shooter).I();
/*     */       }
/*     */       else
/*     */       {
/* 175 */         this.target = getTarget(this.locX, this.locY, this.locZ, 16.0D);
/*     */       }
/*     */ 
/* 178 */       if (this.target != null)
/*     */       {
/* 180 */         double var1 = this.target.boundingBox.a + (this.target.boundingBox.d - this.target.boundingBox.a) / 2.0D - this.locX;
/* 181 */         double var3 = this.target.boundingBox.b + (this.target.boundingBox.e - this.target.boundingBox.b) / 2.0D - this.locY;
/* 182 */         double var5 = this.target.boundingBox.c + (this.target.boundingBox.f - this.target.boundingBox.c) / 2.0D - this.locZ;
/* 183 */         setArrowHeading(var1, var3, var5, this.speed, this.precision);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean o_()
/*     */   {
/* 193 */     return (!this.dead) && (!this.inGround) && (this.ticksFlying >= 2);
/*     */   }
/*     */ 
/*     */   public boolean canBeShot(Entity var1)
/*     */   {
/* 198 */     return (!(var1 instanceof EntityGrimArrow)) && (super.canBeShot(var1));
/*     */   }
/*     */ 
/*     */   private Entity getTarget(double var1, double var3, double var5, double var7)
/*     */   {
/* 203 */     float var9 = -1.0F;
/* 204 */     Entity var10 = null;
/* 205 */     List var11 = this.world.getEntities(this, this.boundingBox.grow(var7, var7, var7));
/*     */ 
/* 207 */     for (int var12 = 0; var12 < var11.size(); var12++)
/*     */     {
/* 209 */       Entity var13 = (Entity)var11.get(var12);
/*     */ 
/* 211 */       if (canTarget(var13))
/*     */       {
/* 213 */         float var14 = var13.i(this);
/*     */ 
/* 215 */         if ((var9 == -1.0F) || (var14 < var9))
/*     */         {
/* 217 */           var9 = var14;
/* 218 */           var10 = var13;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 223 */     return var10;
/*     */   }
/*     */ 
/*     */   public boolean canTarget(Entity var1)
/*     */   {
/* 228 */     return ((var1 instanceof EntityLiving)) && (var1 != this.shooter) && (isInSight(var1));
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.EntityGrimArrow
 * JD-Core Version:    0.6.2
 */