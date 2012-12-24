/*     */ package ee;
/*     */ 
/*     */ import forge.ITextureProvider;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.NBTTagCompound;
/*     */ 
/*     */ public class ItemModEE extends Item
/*     */   implements ITextureProvider
/*     */ {
/*     */   protected ItemModEE(int var1)
/*     */   {
/*  12 */     super(var1);
/*  13 */     setNoRepair();
/*     */   }
/*     */ 
/*     */   public String getTextureFile()
/*     */   {
/*  18 */     return "/eqex/eqexsheet.png";
/*     */   }
/*     */ 
/*     */   public String getString(ItemStack var1, String var2)
/*     */   {
/*  23 */     if (var1.tag == null)
/*     */     {
/*  25 */       var1.setTag(new NBTTagCompound());
/*     */     }
/*     */ 
/*  28 */     if (!var1.tag.hasKey(var2))
/*     */     {
/*  30 */       setString(var1, var2, "");
/*     */     }
/*     */ 
/*  33 */     return var1.tag.getString(var2);
/*     */   }
/*     */ 
/*     */   public void setString(ItemStack var1, String var2, String var3)
/*     */   {
/*  38 */     if (var1.tag == null)
/*     */     {
/*  40 */       var1.setTag(new NBTTagCompound());
/*     */     }
/*     */ 
/*  43 */     var1.tag.setString(var2, var3);
/*     */   }
/*     */ 
/*     */   public boolean getBoolean(ItemStack var1, String var2)
/*     */   {
/*  48 */     if (var1.tag == null)
/*     */     {
/*  50 */       var1.setTag(new NBTTagCompound());
/*     */     }
/*     */ 
/*  53 */     if (!var1.tag.hasKey(var2))
/*     */     {
/*  55 */       setBoolean(var1, var2, false);
/*     */     }
/*     */ 
/*  58 */     return var1.tag.getBoolean(var2);
/*     */   }
/*     */ 
/*     */   public void setBoolean(ItemStack var1, String var2, boolean var3)
/*     */   {
/*  63 */     if (var1.tag == null)
/*     */     {
/*  65 */       var1.setTag(new NBTTagCompound());
/*     */     }
/*     */ 
/*  68 */     var1.tag.setBoolean(var2, var3);
/*     */   }
/*     */ 
/*     */   public short getShort(ItemStack var1, String var2)
/*     */   {
/*  73 */     if (var1.tag == null)
/*     */     {
/*  75 */       var1.setTag(new NBTTagCompound());
/*     */     }
/*     */ 
/*  78 */     if (!var1.tag.hasKey(var2))
/*     */     {
/*  80 */       setShort(var1, var2, 0);
/*     */     }
/*     */ 
/*  83 */     return var1.tag.getShort(var2);
/*     */   }
/*     */ 
/*     */   public void setShort(ItemStack var1, String var2, int var3)
/*     */   {
/*  88 */     if (var1.tag == null)
/*     */     {
/*  90 */       var1.setTag(new NBTTagCompound());
/*     */     }
/*     */ 
/*  93 */     var1.tag.setShort(var2, (short)var3);
/*     */   }
/*     */ 
/*     */   public int getInteger(ItemStack var1, String var2)
/*     */   {
/*  98 */     if (var1.tag == null)
/*     */     {
/* 100 */       var1.setTag(new NBTTagCompound());
/*     */     }
/*     */ 
/* 103 */     if (!var1.tag.hasKey(var2))
/*     */     {
/* 105 */       setInteger(var1, var2, 0);
/*     */     }
/*     */ 
/* 108 */     return var1.tag.getInt(var2);
/*     */   }
/*     */ 
/*     */   public void setInteger(ItemStack var1, String var2, int var3)
/*     */   {
/* 113 */     if (var1.tag == null)
/*     */     {
/* 115 */       var1.setTag(new NBTTagCompound());
/*     */     }
/*     */ 
/* 118 */     var1.tag.setInt(var2, var3);
/*     */   }
/*     */ 
/*     */   public byte getByte(ItemStack var1, String var2)
/*     */   {
/* 123 */     if (var1.tag == null)
/*     */     {
/* 125 */       var1.setTag(new NBTTagCompound());
/*     */     }
/*     */ 
/* 128 */     if (!var1.tag.hasKey(var2))
/*     */     {
/* 130 */       setByte(var1, var2, (byte)0);
/*     */     }
/*     */ 
/* 133 */     return var1.tag.getByte(var2);
/*     */   }
/*     */ 
/*     */   public void setByte(ItemStack var1, String var2, byte var3)
/*     */   {
/* 138 */     if (var1.tag == null)
/*     */     {
/* 140 */       var1.setTag(new NBTTagCompound());
/*     */     }
/*     */ 
/* 143 */     var1.tag.setByte(var2, var3);
/*     */   }
/*     */ 
/*     */   public long getLong(ItemStack var1, String var2)
/*     */   {
/* 148 */     if (var1.tag == null)
/*     */     {
/* 150 */       var1.setTag(new NBTTagCompound());
/*     */     }
/*     */ 
/* 153 */     if (!var1.tag.hasKey(var2))
/*     */     {
/* 155 */       setLong(var1, var2, 0L);
/*     */     }
/*     */ 
/* 158 */     return var1.tag.getLong(var2);
/*     */   }
/*     */ 
/*     */   public void setLong(ItemStack var1, String var2, long var3)
/*     */   {
/* 163 */     if (var1.tag == null)
/*     */     {
/* 165 */       var1.setTag(new NBTTagCompound());
/*     */     }
/*     */ 
/* 168 */     var1.tag.setLong(var2, var3);
/*     */   }
/*     */ }

/* Location:           E:\Downloads\tekkit_24122012\mods\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemModEE
 * JD-Core Version:    0.6.2
 */