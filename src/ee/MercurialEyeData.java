/*     */ package ee;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
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
/*     */ public class MercurialEyeData extends WorldMapBase
/*     */   implements IInventory
/*     */ {
/*     */   public boolean markForUpdate;
/*     */   public static final String prefix = "eye";
/*     */   public static final String prefix_ = "eye_";
/*  25 */   public ItemStack[] eyeContents = new ItemStack[2];
/*  26 */   public static List datas = new LinkedList();
/*     */   public EntityHuman player;
/*     */ 
/*     */   public MercurialEyeData(String var1)
/*     */   {
/*  31 */     super(var1);
/*  32 */     datas.add(this);
/*     */   }
/*     */ 
/*     */   public void onUpdate(World var1, EntityHuman var2)
/*     */   {
/*  37 */     this.player = var2;
/*     */ 
/*  39 */     if (this.markForUpdate)
/*     */     {
/*  41 */       a();
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getSize()
/*     */   {
/*  50 */     return 2;
/*     */   }
/*     */ 
/*     */   public ItemStack getItem(int var1)
/*     */   {
/*  58 */     return this.eyeContents[var1];
/*     */   }
/*     */ 
/*     */   public ItemStack splitStack(int var1, int var2)
/*     */   {
/*  67 */     if (this.eyeContents[var1] != null)
/*     */     {
/*  71 */       if (this.eyeContents[var1].count <= var2)
/*     */       {
/*  73 */         ItemStack var3 = this.eyeContents[var1];
/*  74 */         this.eyeContents[var1] = null;
/*  75 */         update();
/*  76 */         return var3;
/*     */       }
/*     */ 
/*  80 */       ItemStack var3 = this.eyeContents[var1].a(var2);
/*     */ 
/*  82 */       if (this.eyeContents[var1].count == 0)
/*     */       {
/*  84 */         this.eyeContents[var1] = null;
/*     */       }
/*     */ 
/*  87 */       update();
/*  88 */       return var3;
/*     */     }
/*     */ 
/*  93 */     return null;
/*     */   }
/*     */ 
/*     */   public void setItem(int var1, ItemStack var2)
/*     */   {
/* 102 */     if ((var2 != null) && (var2.id == EEItem.mercurialEye.id) && (this.player != null))
/*     */     {
/* 104 */       EntityItem var3 = new EntityItem(this.player.world, this.player.locX, this.player.locY, this.player.locZ, var2);
/* 105 */       this.player.world.addEntity(var3);
/* 106 */       var2 = null;
/*     */     }
/*     */     else
/*     */     {
/* 110 */       this.eyeContents[var1] = var2;
/*     */ 
/* 112 */       if ((var2 != null) && (var2.count > getMaxStackSize()))
/*     */       {
/* 114 */         var2.count = getMaxStackSize();
/*     */       }
/*     */ 
/* 117 */       update();
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 126 */     return "Mercurial Eye";
/*     */   }
/*     */ 
/*     */   public int getMaxStackSize()
/*     */   {
/* 135 */     return 64;
/*     */   }
/*     */ 
/*     */   public void update()
/*     */   {
/* 143 */     a();
/*     */   }
/*     */ 
/*     */   public boolean a(EntityHuman var1)
/*     */   {
/* 151 */     return true;
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
/* 163 */     NBTTagList var2 = var1.getList("Items");
/* 164 */     this.eyeContents = new ItemStack[2];
/*     */ 
/* 166 */     for (int var3 = 0; var3 < var2.size(); var3++)
/*     */     {
/* 168 */       NBTTagCompound var4 = (NBTTagCompound)var2.get(var3);
/* 169 */       int var5 = var4.getByte("Slot") & 0xFF;
/*     */ 
/* 171 */       if ((var5 >= 0) && (var5 < this.eyeContents.length))
/*     */       {
/* 173 */         this.eyeContents[var5] = ItemStack.a(var4);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void b(NBTTagCompound var1)
/*     */   {
/* 183 */     NBTTagList var2 = new NBTTagList();
/*     */ 
/* 185 */     for (int var3 = 0; var3 < this.eyeContents.length; var3++)
/*     */     {
/* 187 */       if (this.eyeContents[var3] != null)
/*     */       {
/* 189 */         NBTTagCompound var4 = new NBTTagCompound();
/* 190 */         var4.setByte("Slot", (byte)var3);
/* 191 */         this.eyeContents[var3].save(var4);
/* 192 */         var2.add(var4);
/*     */       }
/*     */     }
/*     */ 
/* 196 */     var1.set("Items", var2);
/*     */   }
/*     */ 
/*     */   public ItemStack splitWithoutUpdate(int var1)
/*     */   {
/* 205 */     return null;
/*     */   }
/*     */ 
/*     */   public ItemStack[] getContents() {
/* 209 */     return this.eyeContents;
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
/* 224 */     return new ArrayList(0);
/*     */   }
/*     */ 
/*     */   public InventoryHolder getOwner()
/*     */   {
/* 229 */     return null;
/*     */   }
/*     */ 
/*     */   public void setMaxStackSize(int size)
/*     */   {
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.MercurialEyeData
 * JD-Core Version:    0.6.2
 */