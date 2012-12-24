/*     */ package ee;
/*     */ 
/*     */ import net.minecraft.server.Entity;
/*     */ import net.minecraft.server.EntityHuman;
/*     */ import net.minecraft.server.Item;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import net.minecraft.server.PlayerInventory;
/*     */ import net.minecraft.server.World;
/*     */ 
/*     */ public class ItemRepairCharm extends ItemModEE
/*     */ {
/*  11 */   public static Item[] repairItemArray = new Item[49];
/*  12 */   public static int[] covalenceValueArray = new int[49];
/*     */ 
/*     */   static
/*     */   {
/*  95 */     repairItemArray[0] = Item.WOOD_PICKAXE;
/*  96 */     covalenceValueArray[0] = 20;
/*  97 */     repairItemArray[1] = Item.WOOD_SPADE;
/*  98 */     covalenceValueArray[1] = 50;
/*  99 */     repairItemArray[2] = Item.WOOD_SWORD;
/* 100 */     covalenceValueArray[2] = 30;
/* 101 */     repairItemArray[3] = Item.WOOD_AXE;
/* 102 */     covalenceValueArray[3] = 20;
/* 103 */     repairItemArray[4] = Item.WOOD_HOE;
/* 104 */     covalenceValueArray[4] = 30;
/* 105 */     repairItemArray[5] = Item.STONE_PICKAXE;
/* 106 */     covalenceValueArray[5] = 44;
/* 107 */     repairItemArray[6] = Item.STONE_SPADE;
/* 108 */     covalenceValueArray[6] = 120;
/* 109 */     repairItemArray[7] = Item.STONE_SWORD;
/* 110 */     covalenceValueArray[7] = 65;
/* 111 */     repairItemArray[8] = Item.STONE_AXE;
/* 112 */     covalenceValueArray[8] = 44;
/* 113 */     repairItemArray[9] = Item.STONE_HOE;
/* 114 */     covalenceValueArray[9] = 65;
/* 115 */     repairItemArray[10] = Item.IRON_PICKAXE;
/* 116 */     covalenceValueArray[10] = 85;
/* 117 */     repairItemArray[11] = Item.IRON_SPADE;
/* 118 */     covalenceValueArray[11] = 250;
/* 119 */     repairItemArray[12] = Item.IRON_SWORD;
/* 120 */     covalenceValueArray[12] = 125;
/* 121 */     repairItemArray[13] = Item.IRON_AXE;
/* 122 */     covalenceValueArray[13] = 85;
/* 123 */     repairItemArray[14] = Item.IRON_HOE;
/* 124 */     covalenceValueArray[14] = 125;
/* 125 */     repairItemArray[15] = Item.GOLD_PICKAXE;
/* 126 */     covalenceValueArray[15] = 10;
/* 127 */     repairItemArray[16] = Item.GOLD_SPADE;
/* 128 */     covalenceValueArray[16] = 25;
/* 129 */     repairItemArray[17] = Item.GOLD_SWORD;
/* 130 */     covalenceValueArray[17] = 15;
/* 131 */     repairItemArray[18] = Item.GOLD_AXE;
/* 132 */     covalenceValueArray[18] = 10;
/* 133 */     repairItemArray[19] = Item.GOLD_HOE;
/* 134 */     covalenceValueArray[19] = 15;
/* 135 */     repairItemArray[20] = Item.DIAMOND_PICKAXE;
/* 136 */     covalenceValueArray[20] = 520;
/* 137 */     repairItemArray[21] = Item.DIAMOND_SPADE;
/* 138 */     covalenceValueArray[21] = 1550;
/* 139 */     repairItemArray[22] = Item.DIAMOND_SWORD;
/* 140 */     covalenceValueArray[22] = 785;
/* 141 */     repairItemArray[23] = Item.DIAMOND_AXE;
/* 142 */     covalenceValueArray[23] = 520;
/* 143 */     repairItemArray[24] = Item.DIAMOND_HOE;
/* 144 */     covalenceValueArray[24] = 785;
/* 145 */     repairItemArray[25] = Item.LEATHER_HELMET;
/* 146 */     covalenceValueArray[25] = 8;
/* 147 */     repairItemArray[26] = Item.LEATHER_CHESTPLATE;
/* 148 */     covalenceValueArray[26] = 6;
/* 149 */     repairItemArray[27] = Item.LEATHER_LEGGINGS;
/* 150 */     covalenceValueArray[27] = 7;
/* 151 */     repairItemArray[28] = Item.LEATHER_BOOTS;
/* 152 */     covalenceValueArray[28] = 7;
/* 153 */     repairItemArray[29] = Item.IRON_HELMET;
/* 154 */     covalenceValueArray[29] = 31;
/* 155 */     repairItemArray[30] = Item.IRON_CHESTPLATE;
/* 156 */     covalenceValueArray[30] = 24;
/* 157 */     repairItemArray[31] = Item.IRON_LEGGINGS;
/* 158 */     covalenceValueArray[31] = 26;
/* 159 */     repairItemArray[32] = Item.IRON_BOOTS;
/* 160 */     covalenceValueArray[32] = 33;
/* 161 */     repairItemArray[33] = Item.CHAINMAIL_HELMET;
/* 162 */     covalenceValueArray[33] = 16;
/* 163 */     repairItemArray[34] = Item.CHAINMAIL_CHESTPLATE;
/* 164 */     covalenceValueArray[34] = 12;
/* 165 */     repairItemArray[35] = Item.CHAINMAIL_LEGGINGS;
/* 166 */     covalenceValueArray[35] = 13;
/* 167 */     repairItemArray[36] = Item.CHAINMAIL_BOOTS;
/* 168 */     covalenceValueArray[36] = 16;
/* 169 */     repairItemArray[37] = Item.GOLD_HELMET;
/* 170 */     covalenceValueArray[37] = 16;
/* 171 */     repairItemArray[38] = Item.GOLD_CHESTPLATE;
/* 172 */     covalenceValueArray[38] = 12;
/* 173 */     repairItemArray[39] = Item.GOLD_LEGGINGS;
/* 174 */     covalenceValueArray[39] = 13;
/* 175 */     repairItemArray[40] = Item.GOLD_BOOTS;
/* 176 */     covalenceValueArray[40] = 16;
/* 177 */     repairItemArray[41] = Item.DIAMOND_HELMET;
/* 178 */     covalenceValueArray[41] = 78;
/* 179 */     repairItemArray[42] = Item.DIAMOND_CHESTPLATE;
/* 180 */     covalenceValueArray[42] = 46;
/* 181 */     repairItemArray[43] = Item.DIAMOND_LEGGINGS;
/* 182 */     covalenceValueArray[43] = 52;
/* 183 */     repairItemArray[44] = Item.DIAMOND_BOOTS;
/* 184 */     covalenceValueArray[44] = 64;
/* 185 */     repairItemArray[45] = Item.FLINT_AND_STEEL;
/* 186 */     covalenceValueArray[45] = 60;
/* 187 */     repairItemArray[46] = Item.FISHING_ROD;
/* 188 */     covalenceValueArray[46] = 60;
/* 189 */     repairItemArray[47] = Item.SHEARS;
/* 190 */     covalenceValueArray[47] = 235;
/* 191 */     repairItemArray[48] = Item.BOW;
/* 192 */     covalenceValueArray[48] = 128;
/*     */   }
/*     */ 
/*     */   public ItemRepairCharm(int var1)
/*     */   {
/*  16 */     super(var1);
/*  17 */     this.maxStackSize = 1;
/*     */   }
/*     */ 
/*     */   public void a(ItemStack var1, World var2, Entity var3, int var4, boolean var5)
/*     */   {
/*  26 */     if ((var3 instanceof EntityHuman))
/*     */     {
/*  28 */       doRepair((EntityHuman)var3, var2);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doRepair(EntityHuman var1, World var2)
/*     */   {
/*  38 */     for (int var3 = 0; var3 < var1.inventory.items.length; var3++)
/*     */     {
/*  40 */       if (var1.inventory.items[var3] != null)
/*     */       {
/*  42 */         ItemStack var4 = null;
/*     */ 
/*  44 */         for (int var5 = 0; var5 < repairItemArray.length; var5++)
/*     */         {
/*  46 */           if (var1.inventory.items[var3].getItem() == repairItemArray[var5])
/*     */           {
/*  48 */             var4 = var1.inventory.items[var3];
/*     */ 
/*  50 */             if ((var4.getData() >= covalenceValueArray[var5]) && (ConsumeMaterial(var1, new ItemStack(EEItem.covalenceDust, 1, getCovalenceType(var5)))))
/*     */             {
/*  52 */               var4.setData(var4.getData() - covalenceValueArray[var5]);
/*  53 */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  60 */     for (int var3 = 0; var3 < var1.inventory.armor.length; var3++)
/*     */     {
/*  62 */       if (var1.inventory.armor[var3] != null)
/*     */       {
/*  64 */         ItemStack var4 = null;
/*     */ 
/*  66 */         for (int var5 = 0; var5 < repairItemArray.length; var5++)
/*     */         {
/*  68 */           if (var1.inventory.armor[var3].getItem() == repairItemArray[var5])
/*     */           {
/*  70 */             var4 = var1.inventory.armor[var3];
/*     */ 
/*  72 */             if ((var4.getData() >= covalenceValueArray[var5]) && (ConsumeMaterial(var1, new ItemStack(EEItem.covalenceDust, 1, getCovalenceType(var5)))))
/*     */             {
/*  74 */               var4.setData(var4.getData() - covalenceValueArray[var5]);
/*  75 */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getCovalenceType(int var1)
/*     */   {
/*  85 */     return (var1 > 9) && ((var1 < 25) || (var1 > 28)) && (var1 != 46) ? 1 : ((var1 < 10) || (var1 > 19)) && ((var1 < 29) || (var1 > 40)) && (var1 != 45) && (var1 != 47) ? 2 : ((var1 < 20) || (var1 > 24)) && ((var1 < 41) || (var1 > 44)) ? 0 : 0;
/*     */   }
/*     */ 
/*     */   public boolean ConsumeMaterial(EntityHuman var1, ItemStack var2)
/*     */   {
/*  90 */     return EEBase.Consume(var2, var1, false);
/*     */   }
/*     */ }

/* Location:           E:\Downloads\EE2ServerV1.4.6.5-bukkit-mcpc-1.2.5-r5\
 * Qualified Name:     ee.ItemRepairCharm
 * JD-Core Version:    0.6.2
 */