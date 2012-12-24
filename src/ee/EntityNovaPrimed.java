/*     */ package ee;
/*     */ 
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.MathHelper;
/*     */ import net.minecraft.server.NBTTagCompound;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class EntityNovaPrimed extends Entity
/*     */ {
/*     */   private EntityHuman player;
/*     */   public int fuse;
/*     */ 
/*     */   public EntityNovaPrimed(World var1)
/*     */   {
/*  16 */     super(var1);
/*  17 */     this.fuse = 120;
/*  18 */     this.bf = true;
/*  19 */     b(0.98F, 0.98F);
/*  20 */     this.height = (this.length / 2.0F);
/*     */   }
/*     */ 
/*     */   public EntityNovaPrimed(World var1, EntityHuman var2, double var3, double var5, double var7)
/*     */   {
/*  25 */     this(var1);
/*  26 */     this.player = var2;
/*  27 */     setPosition(var3, var5, var7);
/*  28 */     float var9 = (float)(Math.random() * 3.141592653589793D * 2.0D);
/*  29 */     this.motX = (-MathHelper.sin(var9 * 3.141593F / 180.0F) * 0.02F);
/*  30 */     this.motY = 0.2000000029802322D;
/*  31 */     this.motZ = (-MathHelper.cos(var9 * 3.141593F / 180.0F) * 0.02F);
/*  32 */     this.fuse = 120;
/*  33 */     this.lastX = var3;
/*  34 */     this.lastY = var5;
/*  35 */     this.lastZ = var7;
/*     */   }
/*     */ 
/*     */   public EntityNovaPrimed(World var1, double var2, double var4, double var6)
/*     */   {
/*  40 */     this(var1);
/*  41 */     setPosition(var2, var4, var6);
/*     */   }
/*     */ 
/*     */   protected void b()
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean o_()
/*     */   {
/*  51 */     return !this.dead;
/*     */   }
/*     */ 
/*     */   public void F_()
/*     */   {
/*  59 */     this.lastX = this.locX;
/*  60 */     this.lastY = this.locY;
/*  61 */     this.lastZ = this.locZ;
/*  62 */     this.motY -= 0.03999999910593033D;
/*  63 */     move(this.motX, this.motY, this.motZ);
/*  64 */     this.motX *= 0.9800000190734863D;
/*  65 */     this.motY *= 0.9800000190734863D;
/*  66 */     this.motZ *= 0.9800000190734863D;
/*     */ 
/*  68 */     if (this.onGround)
/*     */     {
/*  70 */       this.motX *= 0.699999988079071D;
/*  71 */       this.motZ *= 0.699999988079071D;
/*  72 */       this.motY *= -0.5D;
/*     */     }
/*     */ 
/*  75 */     if (this.fuse-- <= 0)
/*     */     {
/*  77 */       die();
/*  78 */       explode();
/*     */     }
/*     */     else
/*     */     {
/*  82 */       this.world.a("smoke", this.locX, this.locY + 0.5D, this.locZ, 0.0D, 0.0D, 0.0D);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void explode()
/*     */   {
/*  88 */     float var1 = 8.0F;
/*  89 */     newExplosionNova(null, this.locX, this.locY, this.locZ, var1, false);
/*     */   }
/*     */ 
/*     */   public ExplosionNova newExplosionNova(Entity var1, double var2, double var4, double var6, float var8, boolean var9)
/*     */   {
/*  94 */     ExplosionNova var10 = new ExplosionNova(this.world, this.player, var2, var4, var6, var8);
/*  95 */     var10.isFlaming = var9;
/*  96 */     var10.doExplosionA();
/*  97 */     var10.doExplosionB();
/*  98 */     return var10;
/*     */   }
/*     */ 
/*     */   protected void b(NBTTagCompound var1)
/*     */   {
/* 106 */     var1.setByte("Fuse", (byte)this.fuse);
/*     */   }
/*     */ 
/*     */   protected void a(NBTTagCompound var1)
/*     */   {
/* 114 */     this.fuse = var1.getByte("Fuse");
/*     */   }
/*     */ 
/*     */   public float getShadowSize()
/*     */   {
/* 119 */     return 0.0F;
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.EntityNovaPrimed
 * JD-Core Version:    0.6.2
 */