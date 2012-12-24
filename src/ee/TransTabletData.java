/*     */ package ee;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.minecraft.server.EEProxy;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.EntityItem;
/*     */ import net.minecraft.server.IInventory;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.NBTTagCompound;
/*     */ import net.minecraft.server.NBTTagList;
/*     */ import net.minecraft.server.World;
/*     */ import net.minecraft.server.WorldMapBase;
/*     */ import org.bukkit.craftbukkit.entity.CraftHumanEntity;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ 
/*     */ public class TransTabletData extends WorldMapBase
/*     */   implements IInventory
/*     */ {
/*  26 */   public int latentEnergy = 0;
/*  27 */   public int currentEnergy = 0;
/*  28 */   public int learned = 0;
/*  29 */   public ItemStack[] items = new ItemStack[26];
/*     */   public boolean isMatterLocked;
/*     */   public boolean isFuelLocked;
/*     */   public EntityHuman player;
/*     */   private boolean readTome;
/*  34 */   private HashMap knowledge = new HashMap();
/*  35 */   public static List datas = new LinkedList();
/*     */ 
/*     */   public TransTabletData(String var1)
/*     */   {
/*  39 */     super(var1);
/*  40 */     datas.add(this);
/*     */   }
/*     */ 
/*     */   public void onUpdate(World var1, EntityHuman var2)
/*     */   {
/*  45 */     if (!EEProxy.isClient(var1))
/*     */     {
/*  47 */       if (this.player == null)
/*     */       {
/*  49 */         this.player = var2;
/*     */       }
/*     */ 
/*  52 */       if (this.currentEnergy + this.latentEnergy == 0)
/*     */       {
/*  54 */         unlock();
/*     */       }
/*     */ 
/*  57 */       calculateEMC();
/*  58 */       displayResults(this.currentEnergy + this.latentEnergy);
/*     */     }
/*     */   }
/*     */ 
/*     */   public ItemStack target()
/*     */   {
/*  64 */     return this.items[8];
/*     */   }
/*     */ 
/*     */   public boolean isOnGridBut(ItemStack var1, int var2)
/*     */   {
/*  69 */     for (int var3 = 10; var3 < this.items.length; var3++)
/*     */     {
/*  71 */       if ((var3 != var2) && (this.items[var3] != null) && (this.items[var3].doMaterialsMatch(var1)))
/*     */       {
/*  73 */         return true;
/*     */       }
/*     */     }
/*     */ 
/*  77 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isOnGrid(ItemStack var1)
/*     */   {
/*  82 */     for (int var2 = 10; var2 < this.items.length; var2++)
/*     */     {
/*  84 */       if ((this.items[var2] != null) && (this.items[var2].doMaterialsMatch(var1)))
/*     */       {
/*  86 */         return true;
/*     */       }
/*     */     }
/*     */ 
/*  90 */     return false;
/*     */   }
/*     */ 
/*     */   public int kleinEMCTotal()
/*     */   {
/*  95 */     int var1 = 0;
/*     */ 
/*  97 */     for (int var2 = 0; var2 < 8; var2++)
/*     */     {
/*  99 */       if ((this.items[var2] != null) && ((this.items[var2].getItem() instanceof ItemKleinStar)))
/*     */       {
/* 101 */         var1 += ((ItemKleinStar)this.items[var2].getItem()).getKleinPoints(this.items[var2]);
/*     */       }
/*     */     }
/*     */ 
/* 105 */     return var1;
/*     */   }
/*     */ 
/*     */   public void displayResults(int var1)
/*     */   {
/* 110 */     for (int var2 = 10; var2 < this.items.length; var2++)
/*     */     {
/* 112 */       if ((this.items[var2] != null) && ((var1 < EEMaps.getEMC(this.items[var2])) || (!matchesLock(this.items[var2])) || (isOnGridBut(this.items[var2], var2)) || ((target() != null) && (EEMaps.getEMC(this.items[var2]) > EEMaps.getEMC(target())))))
/*     */       {
/* 114 */         this.items[var2] = null;
/*     */       }
/*     */ 
/* 117 */       if ((var2 == 9) && (target() != null) && (EEMaps.getEMC(target()) > 0) && (var1 > EEMaps.getEMC(target())) && (matchesLock(target())))
/*     */       {
/* 119 */         this.items[var2] = new ItemStack(target().id, 1, target().getData());
/*     */       }
/*     */ 
/* 122 */       if ((var2 == 10) && (target() != null) && (EEMaps.getEMC(target()) > 0) && (var1 >= EEMaps.getEMC(target())) && (matchesLock(target())))
/*     */       {
/* 124 */         this.items[10] = new ItemStack(target().id, 1, target().getData());
/*     */       }
/*     */ 
/* 127 */       for (int var3 = 0; var3 < Item.byId.length; var3++)
/*     */       {
/* 129 */         if (Item.byId[var3] != null)
/*     */         {
/* 131 */           int var4 = EEMaps.getMeta(var3);
/*     */ 
/* 133 */           for (int var5 = 0; var5 <= var4; var5++)
/*     */           {
/* 135 */             ItemStack var6 = new ItemStack(var3, 1, var5);
/*     */ 
/* 137 */             if ((!isOnGrid(var6)) && (matchesLock(var6)))
/*     */             {
/* 139 */               int var7 = EEMaps.getEMC(var6);
/*     */ 
/* 141 */               if ((var7 != 0) && ((target() == null) || (var7 <= EEMaps.getEMC(target()))) && (playerKnows(var6.id, var6.getData())) && (var1 >= var7) && (var7 > EEMaps.getEMC(getItem(var2))))
/*     */               {
/* 143 */                 this.items[var2] = new ItemStack(var3, 1, var5);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 151 */     update();
/*     */   }
/*     */ 
/*     */   public void calculateEMC()
/*     */   {
/* 156 */     int var1 = 0;
/* 157 */     boolean var2 = false;
/*     */ 
/* 159 */     for (int var3 = 0; var3 < 8; var3++)
/*     */     {
/* 161 */       if (this.items[var3] != null)
/*     */       {
/* 163 */         if ((EEMaps.getEMC(this.items[var3]) == 0) && (!EEBase.isKleinStar(this.items[var3].id)))
/*     */         {
/* 165 */           rejectItem(var3);
/*     */         }
/* 167 */         else if (EEBase.isKleinStar(this.items[var3].id))
/*     */         {
/* 169 */           if ((!playerKnows(this.items[var3].id, this.items[var3].getData())) && (EEMaps.getEMC(this.items[var3]) > 0))
/*     */           {
/* 171 */             if (this.items[var3].id == EEItem.alchemyTome.id)
/*     */             {
/* 173 */               pushTome();
/*     */             }
/*     */ 
/* 176 */             pushKnowledge(this.items[var3].id, this.items[var3].getData());
/* 177 */             this.learned = 60;
/*     */           }
/*     */ 
/* 180 */           if (this.latentEnergy > 0)
/*     */           {
/* 182 */             int var4 = ((ItemKleinStar)this.items[var3].getItem()).getMaxPoints(this.items[var3]) - ((ItemKleinStar)this.items[var3].getItem()).getKleinPoints(this.items[var3]);
/*     */ 
/* 184 */             if (var4 > 0)
/*     */             {
/* 186 */               if (var4 > this.latentEnergy)
/*     */               {
/* 188 */                 var4 = this.latentEnergy;
/*     */               }
/*     */ 
/* 191 */               this.latentEnergy -= var4;
/* 192 */               EEBase.addKleinStarPoints(this.items[var3], var4);
/*     */             }
/*     */           }
/*     */ 
/* 196 */           var1 += ((ItemKleinStar)this.items[var3].getItem()).getKleinPoints(this.items[var3]);
/*     */         }
/*     */         else
/*     */         {
/* 200 */           if ((!playerKnows(this.items[var3].id, this.items[var3].getData())) && (EEMaps.getEMC(this.items[var3]) > 0))
/*     */           {
/* 202 */             if (this.items[var3].id == EEItem.alchemyTome.id)
/*     */             {
/* 204 */               pushTome();
/*     */             }
/*     */ 
/* 207 */             pushKnowledge(this.items[var3].id, this.items[var3].getData());
/* 208 */             this.learned = 60;
/*     */           }
/*     */ 
/* 211 */           if ((!var2) && (!isFuelLocked()) && (!isMatterLocked()))
/*     */           {
/* 213 */             if (EEMaps.isFuel(this.items[var3]))
/*     */             {
/* 215 */               fuelLock();
/*     */             }
/*     */             else
/*     */             {
/* 219 */               matterLock();
/*     */             }
/*     */           }
/*     */ 
/* 223 */           if (!matchesLock(this.items[var3]))
/*     */           {
/* 225 */             rejectItem(var3);
/*     */           }
/*     */           else
/*     */           {
/* 229 */             var1 += EEMaps.getEMC(this.items[var3]);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 235 */     this.currentEnergy = var1;
/*     */   }
/*     */ 
/*     */   public boolean matchesLock(ItemStack var1)
/*     */   {
/* 240 */     if (isFuelLocked())
/*     */     {
/* 242 */       if (EEMaps.isFuel(var1))
/*     */       {
/* 244 */         return true;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 249 */       if (!isMatterLocked())
/*     */       {
/* 251 */         return true;
/*     */       }
/*     */ 
/* 254 */       if (!EEMaps.isFuel(var1))
/*     */       {
/* 256 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 260 */     return false;
/*     */   }
/*     */ 
/*     */   public int getSize()
/*     */   {
/* 268 */     return this.items.length;
/*     */   }
/*     */ 
/*     */   public ItemStack getItem(int var1)
/*     */   {
/* 276 */     return this.items[var1];
/*     */   }
/*     */ 
/*     */   public ItemStack splitStack(int var1, int var2)
/*     */   {
/* 285 */     if (this.items[var1] != null)
/*     */     {
/* 289 */       if (this.items[var1].count <= var2)
/*     */       {
/* 291 */         ItemStack var3 = this.items[var1];
/* 292 */         this.items[var1] = null;
/* 293 */         update();
/* 294 */         return var3;
/*     */       }
/*     */ 
/* 298 */       ItemStack var3 = this.items[var1].a(var2);
/*     */ 
/* 300 */       if (this.items[var1].count == 0)
/*     */       {
/* 302 */         this.items[var1] = null;
/*     */       }
/*     */ 
/* 305 */       update();
/* 306 */       return var3;
/*     */     }
/*     */ 
/* 311 */     return null;
/*     */   }
/*     */ 
/*     */   public void setItem(int var1, ItemStack var2)
/*     */   {
/* 320 */     this.items[var1] = var2;
/*     */ 
/* 322 */     if ((var2 != null) && (var2.count > getMaxStackSize()))
/*     */     {
/* 324 */       var2.count = getMaxStackSize();
/*     */     }
/*     */ 
/* 327 */     update();
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 335 */     return "Trans Tablet";
/*     */   }
/*     */ 
/*     */   public int getMaxStackSize()
/*     */   {
/* 344 */     return 64;
/*     */   }
/*     */ 
/*     */   public void update()
/*     */   {
/* 352 */     a();
/*     */   }
/*     */ 
/*     */   public boolean a(EntityHuman var1)
/*     */   {
/* 360 */     return true;
/*     */   }
/*     */ 
/*     */   public void f()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void g()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void a(NBTTagCompound var1) {
/* 372 */     this.isMatterLocked = var1.getBoolean("matterLock");
/* 373 */     this.isFuelLocked = var1.getBoolean("fuelLock");
/* 374 */     this.currentEnergy = var1.getInt("currentEnergy");
/* 375 */     this.latentEnergy = var1.getInt("latentEnergy");
/* 376 */     NBTTagList var2 = var1.getList("Items");
/* 377 */     this.items = new ItemStack[getSize()];
/*     */ 
/* 379 */     for (int var3 = 0; var3 < var2.size(); var3++)
/*     */     {
/* 381 */       NBTTagCompound var4 = (NBTTagCompound)var2.get(var3);
/* 382 */       int var5 = var4.getByte("Slot") & 0xFF;
/*     */ 
/* 384 */       if ((var5 >= 0) && (var5 < this.items.length))
/*     */       {
/* 386 */         this.items[var5] = ItemStack.a(var4);
/*     */       }
/*     */     }
/*     */ 
/* 390 */     NBTTagList var8 = var1.getList("knowledge");
/* 391 */     this.knowledge = new HashMap();
/*     */ 
/* 393 */     for (int var9 = 0; var9 < var8.size(); var9++)
/*     */     {
/* 395 */       NBTTagCompound var10 = (NBTTagCompound)var8.get(var9);
/* 396 */       int var6 = var10.getInt("item");
/* 397 */       int var7 = var10.getInt("meta");
/* 398 */       pushKnowledge(var6, var7);
/*     */     }
/*     */ 
/* 401 */     this.readTome = var1.getBoolean("readTome");
/*     */   }
/*     */ 
/*     */   public void b(NBTTagCompound var1)
/*     */   {
/* 409 */     var1.setBoolean("matterLock", this.isMatterLocked);
/* 410 */     var1.setBoolean("fuelLock", this.isFuelLocked);
/* 411 */     var1.setInt("currentEnergy", this.currentEnergy);
/* 412 */     var1.setInt("latentEnergy", this.latentEnergy);
/* 413 */     NBTTagList var2 = new NBTTagList();
/*     */ 
/* 417 */     for (int var3 = 0; var3 < this.items.length; var3++)
/*     */     {
/* 419 */       if (this.items[var3] != null)
/*     */       {
/* 421 */         NBTTagCompound var4 = new NBTTagCompound();
/* 422 */         var4.setByte("Slot", (byte)var3);
/* 423 */         this.items[var3].save(var4);
/* 424 */         var2.add(var4);
/*     */       }
/*     */     }
/*     */ 
/* 428 */     var1.set("Items", var2);
/* 429 */     var1.setBoolean("readTome", this.readTome);
/*     */ 
/* 431 */     for (int var3 = 0; var3 < this.knowledge.size(); var3++)
/*     */     {
/* 433 */       if (this.knowledge.get(Integer.valueOf(var3)) != null)
/*     */       {
/* 435 */         NBTTagCompound var4 = new NBTTagCompound();
/* 436 */         var4.setInt("item", ((Integer[])this.knowledge.get(Integer.valueOf(var3)))[0].intValue());
/* 437 */         var4.setInt("meta", ((Integer[])this.knowledge.get(Integer.valueOf(var3)))[1].intValue());
/* 438 */         var2.add(var4);
/*     */       }
/*     */     }
/*     */ 
/* 442 */     var1.set("knowledge", var2);
/*     */   }
/*     */ 
/*     */   public HashMap getKnowledge()
/*     */   {
/* 447 */     return this.knowledge;
/*     */   }
/*     */ 
/*     */   public void pushKnowledge(int var1, int var2)
/*     */   {
/* 452 */     if (Item.byId[var1] != null)
/*     */     {
/* 454 */       if (Item.byId[var1].g())
/*     */       {
/* 456 */         var2 = 0;
/*     */       }
/*     */ 
/* 459 */       if (!playerKnows(var1, var2))
/*     */       {
/* 463 */         for (int var3 = 0; this.knowledge.get(Integer.valueOf(var3)) != null; var3++) {
/* 468 */         this.knowledge.put(Integer.valueOf(var3), new Integer[] { Integer.valueOf(var1), Integer.valueOf(var2) });
/* 469 */         a();
}
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean playerKnows(int var1, int var2)
/*     */   {
/* 476 */     if (this.readTome)
/*     */     {
/* 478 */       return true;
/*     */     }
/*     */ 
/* 482 */     ItemStack var3 = new ItemStack(var1, 1, var2);
/*     */ 
/* 484 */     if (var3.d())
/*     */     {
/* 486 */       var2 = 0;
/*     */     }
/*     */ 
/* 489 */     for (int var4 = 0; this.knowledge.get(Integer.valueOf(var4)) != null; var4++)
/*     */     {
/* 491 */       int var5 = ((Integer[])this.knowledge.get(Integer.valueOf(var4)))[0].intValue();
/* 492 */       int var6 = ((Integer[])this.knowledge.get(Integer.valueOf(var4)))[1].intValue();
/*     */ 
/* 494 */       if ((var5 == var1) && (var6 == var2))
/*     */       {
/* 496 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 500 */     return false;
/*     */   }
/*     */ 
/*     */   public void pushTome()
/*     */   {
/* 506 */     this.readTome = true;
/* 507 */     a();
/*     */   }
/*     */ 
/*     */   public long getDisplayEnergy()
/*     */   {
/* 512 */     return this.latentEnergy + this.currentEnergy;
/*     */   }
/*     */ 
/*     */   public int getLatentEnergy()
/*     */   {
/* 517 */     return this.latentEnergy;
/*     */   }
/*     */ 
/*     */   public void setLatentEnergy(int var1)
/*     */   {
/* 522 */     this.latentEnergy = var1;
/* 523 */     a();
/*     */   }
/*     */ 
/*     */   public int getCurrentEnergy()
/*     */   {
/* 528 */     return this.currentEnergy;
/*     */   }
/*     */ 
/*     */   public void setCurrentEnergy(int var1)
/*     */   {
/* 533 */     this.currentEnergy = var1;
/* 534 */     a();
/*     */   }
/*     */ 
/*     */   public boolean isFuelLocked()
/*     */   {
/* 539 */     return this.isFuelLocked;
/*     */   }
/*     */ 
/*     */   public void fuelUnlock()
/*     */   {
/* 544 */     this.isFuelLocked = false;
/* 545 */     a();
/*     */   }
/*     */ 
/*     */   public void fuelLock()
/*     */   {
/* 550 */     this.isFuelLocked = true;
/* 551 */     a();
/*     */   }
/*     */ 
/*     */   public boolean isMatterLocked()
/*     */   {
/* 556 */     return this.isMatterLocked;
/*     */   }
/*     */ 
/*     */   public void matterUnlock()
/*     */   {
/* 561 */     this.isMatterLocked = false;
/* 562 */     a();
/*     */   }
/*     */ 
/*     */   public void matterLock()
/*     */   {
/* 567 */     this.isMatterLocked = true;
/* 568 */     a();
/*     */   }
/*     */ 
/*     */   public void unlock()
/*     */   {
/* 573 */     fuelUnlock();
/* 574 */     matterUnlock();
/*     */   }
/*     */ 
/*     */   public void rejectItem(int var1)
/*     */   {
/* 579 */     if (this.player != null)
/*     */     {
/* 581 */       if (this.player.world != null)
/*     */       {
/* 583 */         if (!EEProxy.isClient(this.player.world))
/*     */         {
/* 585 */           if (getItem(var1) != null)
/*     */           {
/* 587 */             EntityItem var2 = new EntityItem(this.player.world, this.player.locX, this.player.locY - 0.5D, this.player.locZ, getItem(var1));
/* 588 */             nullStack(var1);
/* 589 */             var2.pickupDelay = 1;
/* 590 */             this.player.world.addEntity(var2);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void nullStack(int var1)
/*     */   {
/* 599 */     this.items[var1] = null;
/* 600 */     a();
/*     */   }
/*     */ 
/*     */   public ItemStack splitWithoutUpdate(int var1)
/*     */   {
/* 609 */     if (var1 <= 8)
/*     */     {
/* 611 */       if (this.items[var1] != null)
/*     */       {
/* 613 */         ItemStack var2 = this.items[var1];
/* 614 */         this.items[var1] = null;
/* 615 */         return var2;
/*     */       }
/*     */ 
/* 619 */       return null;
/*     */     }
/*     */ 
/* 624 */     return null;
/*     */   }
/*     */ 
/*     */   public ItemStack[] getContents()
/*     */   {
/* 630 */     return this.items;
/*     */   }
/*     */ 
/*     */   public void onOpen(CraftHumanEntity who)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void onClose(CraftHumanEntity who)
/*     */   {
/*     */   }
/*     */ 
/*     */   public List<HumanEntity> getViewers()
/*     */   {
/* 645 */     return new ArrayList(0);
/*     */   }
/*     */ 
/*     */   public InventoryHolder getOwner()
/*     */   {
/* 650 */     return null;
/*     */   }
/*     */ 
/*     */   public void setMaxStackSize(int size)
/*     */   {
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.TransTabletData
 * JD-Core Version:    0.6.2
 */