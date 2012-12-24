/*     */ package ee;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.AxisAlignedBB;
/*     */ import net.minecraft.server.Block;
/*     */ import net.minecraft.server.DamageSource;
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.Material;
/*     */ import net.minecraft.server.MathHelper;
/*     */ import net.minecraft.server.NBTTagCompound;
/*     */ import net.minecraft.server.NBTTagList;
/*     */ import net.minecraft.server.PlayerInventory;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class EntityLootBall extends Entity
/*     */ {
/*     */   public ItemStack[] items;
/*     */   private int field_803_e;
/*  18 */   public int age = 0;
/*     */   public int delayBeforeCanPickup;
/*  20 */   private int health = 5;
/*  21 */   public float field_804_d = (float)(Math.random() * 3.141592653589793D * 2.0D);
/*     */   private boolean beingPulled;
/*     */ 
/*     */   public EntityLootBall(World var1, double var2, double var4, double var6, ItemStack[] var8)
/*     */   {
/*  26 */     super(var1);
/*  27 */     b(0.25F, 0.25F);
/*  28 */     this.height = (this.length / 2.0F);
/*  29 */     setPosition(var2, var4, var6);
/*  30 */     this.items = var8;
/*  31 */     this.yaw = ((float)(Math.random() * 360.0D));
/*  32 */     this.motX = ((float)(Math.random() * 0.2000000029802322D - 0.1000000014901161D));
/*  33 */     this.motY = 0.2000000029802322D;
/*  34 */     this.motZ = ((float)(Math.random() * 0.2000000029802322D - 0.1000000014901161D));
/*     */   }
/*     */ 
/*     */   protected boolean g_()
/*     */   {
/*  43 */     return false;
/*     */   }
/*     */ 
/*     */   public EntityLootBall(World var1, double var2, double var4, double var6)
/*     */   {
/*  48 */     super(var1);
/*  49 */     b(0.25F, 0.25F);
/*  50 */     this.height = (this.length / 2.0F);
/*  51 */     setPosition(var2, var4, var6);
/*     */   }
/*     */ 
/*     */   public EntityLootBall(World var1)
/*     */   {
/*  56 */     super(var1);
/*  57 */     b(0.25F, 0.25F);
/*  58 */     this.height = (this.length / 2.0F);
/*     */   }
/*     */ 
/*     */   protected void b()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void F_()
/*     */   {
/*  68 */     super.F_();
/*     */ 
/*  70 */     if (this.delayBeforeCanPickup > 0)
/*     */     {
/*  72 */       this.delayBeforeCanPickup -= 1;
/*     */     }
/*     */ 
/*  75 */     this.lastX = this.locX;
/*  76 */     this.lastY = this.locY;
/*  77 */     this.lastZ = this.locZ;
/*  78 */     this.motY -= 0.03999999910593033D;
/*  79 */     g(this.locX, (this.boundingBox.b + this.boundingBox.e) / 2.0D, this.locZ);
/*  80 */     move(this.motX, this.motY, this.motZ);
/*  81 */     float var1 = 0.98F;
/*     */ 
/*  83 */     if (this.onGround)
/*     */     {
/*  85 */       var1 = 0.5880001F;
/*  86 */       int var2 = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ));
/*     */ 
/*  88 */       if (var2 > 0)
/*     */       {
/*  90 */         var1 = Block.byId[var2].frictionFactor * 0.98F;
/*     */       }
/*     */     }
/*     */ 
/*  94 */     this.motX *= var1;
/*  95 */     this.motY *= 0.9800000190734863D;
/*  96 */     this.motZ *= var1;
/*     */ 
/*  98 */     if (this.onGround)
/*     */     {
/* 100 */       this.motY *= -0.5D;
/*     */     }
/*     */ 
/* 103 */     this.field_803_e += 1;
/* 104 */     this.age += 1;
/*     */ 
/* 106 */     if (this.age >= 12000)
/*     */     {
/* 108 */       die();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean h_()
/*     */   {
/* 117 */     return this.world.a(this.boundingBox, Material.WATER, this);
/*     */   }
/*     */ 
/*     */   protected void burn(int var1)
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean damageEntity(DamageSource var1, int var2)
/*     */   {
/* 131 */     return false;
/*     */   }
/*     */ 
/*     */   public void b(NBTTagCompound var1)
/*     */   {
/* 139 */     var1.setShort("Health", (short)(byte)this.health);
/* 140 */     var1.setShort("Age", (short)this.age);
/* 141 */     NBTTagList var2 = new NBTTagList();
/*     */ 
/* 143 */     for (int var3 = 0; var3 < this.items.length; var3++)
/*     */     {
/* 145 */       if (this.items[var3] != null)
/*     */       {
/* 147 */         NBTTagCompound var4 = new NBTTagCompound();
/* 148 */         var4.setByte("Slot", (byte)var3);
/* 149 */         this.items[var3].save(var4);
/* 150 */         var2.add(var4);
/*     */       }
/*     */     }
/*     */ 
/* 154 */     var1.set("Items", var2);
/*     */   }
/*     */ 
/*     */   public void a(NBTTagCompound var1)
/*     */   {
/* 162 */     this.health = (var1.getShort("Health") & 0xFF);
/* 163 */     this.age = var1.getShort("Age");
/* 164 */     NBTTagList var2 = var1.getList("Items");
/* 165 */     this.items = new ItemStack[var2.size()];
/*     */ 
/* 167 */     for (int var3 = 0; var3 < var2.size(); var3++)
/*     */     {
/* 169 */       NBTTagCompound var4 = (NBTTagCompound)var2.get(var3);
/* 170 */       byte var5 = var4.getByte("Slot");
/*     */ 
/* 172 */       if ((var5 >= 0) && (var5 < this.items.length))
/*     */       {
/* 174 */         this.items[var5] = ItemStack.a(var4);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void a_(EntityHuman var1)
/*     */   {
/* 184 */     if (!this.world.isStatic)
/*     */     {
/* 186 */       if ((this.delayBeforeCanPickup == 0) && (!isBeingPulled()))
/*     */       {
/* 188 */         for (int var2 = 0; var2 < this.items.length; var2++)
/*     */         {
/* 190 */           if ((this.items[var2] != null) && (roomFor(this.items[var2], var1)))
/*     */           {
/* 192 */             this.world.makeSound(this, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/* 193 */             pushStack(this.items[var2], var1);
/* 194 */             this.items[var2] = null;
/*     */           }
/*     */         }
/*     */ 
/* 198 */         if (isEmpty())
/*     */         {
/* 200 */           die();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean roomFor(ItemStack var1, EntityHuman var2)
/*     */   {
/* 208 */     if (var1 == null)
/*     */     {
/* 210 */       return false;
/*     */     }
/*     */ 
/* 214 */     for (int var3 = 0; var3 < var2.inventory.items.length; var3++)
/*     */     {
/* 216 */       if (var2.inventory.items[var3] == null)
/*     */       {
/* 218 */         return true;
/*     */       }
/*     */ 
/* 221 */       if ((var2.inventory.items[var3].doMaterialsMatch(var1)) && (var2.inventory.items[var3].count <= var1.getMaxStackSize() - var1.count))
/*     */       {
/* 223 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 227 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isBeingPulled()
/*     */   {
/* 233 */     return this.beingPulled;
/*     */   }
/*     */ 
/*     */   public boolean setBeingPulled(boolean var1)
/*     */   {
/* 238 */     return this.beingPulled = var1;
/*     */   }
/*     */ 
/*     */   public void pushStack(ItemStack var1, EntityHuman var2)
/*     */   {
/* 243 */     if (var1 != null)
/*     */     {
/* 247 */       for (int var3 = 0; var3 < var2.inventory.items.length; var3++)
/*     */       {
/* 249 */         if (var2.inventory.items[var3] != null)
/*     */         {
/* 251 */           if ((var2.inventory.items[var3].doMaterialsMatch(var1)) && (var2.inventory.items[var3].count <= var1.getMaxStackSize() - var1.count))
/*     */           {
/* 253 */             var2.inventory.items[var3].count += var1.count;
/* 254 */             var1 = null;
/* 255 */             return;
/*     */           }
/*     */ 
/* 258 */           if (var2.inventory.items[var3].doMaterialsMatch(var1))
/*     */           {
/* 260 */             while ((var2.inventory.items[var3].count < var2.inventory.items[var3].getMaxStackSize()) && (var1 != null))
/*     */             {
/* 262 */               var2.inventory.items[var3].count += 1;
/* 263 */               var1.count -= 1;
/*     */ 
/* 265 */               if (var1.count <= 0)
/*     */               {
/* 267 */                 var1 = null;
/* 268 */                 return;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 275 */       if (var1 != null)
/*     */       {
/* 277 */         for (var3 = 0; var3 < var2.inventory.items.length; var3++)
/*     */         {
/* 279 */           if (var2.inventory.items[var3] == null)
/*     */           {
/* 281 */             var2.inventory.items[var3] = var1.cloneItemStack();
/* 282 */             var1 = null;
/* 283 */             return;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 292 */     boolean var1 = true;
/*     */ 
/* 294 */     for (int var2 = 0; var2 < this.items.length; var2++)
/*     */     {
/* 296 */       if (this.items[var2] != null)
/*     */       {
/* 298 */         var1 = false;
/*     */       }
/*     */     }
/*     */ 
/* 302 */     return var1;
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.EntityLootBall
 * JD-Core Version:    0.6.2
 */